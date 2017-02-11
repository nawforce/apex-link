/*
    Copyright (c) 2015 Salesforce.org
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the Salesforce.com Foundation nor the names of
      its contributors may be used to endorse or promote products derived
      from this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
    FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
    COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
    BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
    LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
    CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
    LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
    ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
    POSSIBILITY OF SUCH DAMAGE.
*/
/**
 * @author Salesforce.org
 * @group CampaignTools
 * @description A service class that provides methods to perform actions
 * related to campaign lists.
 */
public with sharing class CampaignListService implements CampaignList.Service {
    /**
     * @description Retrieve the status available for the source campaign
     * Returns a Map<String, String> where the campaign member status id is the
     * key and the label is the value.
     *
     * @param campaignId The id of the campaign to find statuses
     * @return Map<String, String>
     */
    public Map<String, String> getStatuses(Id campaignId) {
        Map<String, String> statuses = new Map<String, String>();
        for(CampaignMemberStatus status: [SELECT Label FROM CampaignMemberStatus WHERE CampaignId = :campaignId ORDER BY SortOrder ASC]) {
            statuses.put(
                status.Id,
                status.Label
            );
        }

        return statuses;
    }

    /**
     * @description Given a Campaign id and a corresponding "campaign list" id,
     * clear the Campaign's CampaignMembers and repopulate the Campaign with
     * CampaignMembers sourced from the campaign list's defined sources using
     * the defined campaign list criteria.
     *
     * @param campaignId The id of the Campaign to be updated (note existing CampaignMember records will be deleted and replaced)
     * @param rootSegmentId The "campaign list" id
     * @return Integer the number of batches to be run
     */
    public Integer updateCampaignFromCampaignList(Id campaignId, Id rootSegmentId) {
        return updateCampaignFromCampaignList(campaignId, rootSegmentId, true);
    }

    /**
     * @description Given a Campaign id and a corresponding "campaign list" id,
     * clear the Campaign's CampaignMembers and repopulate the Campaign with
     * CampaignMembers sourced from the campaign list's defined sources using
     * the defined campaign list criteria.
     *
     * @param campaignId The id of the Campaign to be updated (note existing CampaignMember records will be deleted and replaced)
     * @param rootSegmentId The "campaign list" id
     * @param acquireLock Whether to attempt to maintain lock on campaign
     * @return Integer the number of batches to be run
     */
    public integer updateCampaignFromCampaignList(Id campaignId, Id rootSegmentId, Boolean acquireLock) {
        BatchableSequence.Interface_x batchSequence = CampaignList.getBatchableSequenceFactory().create();
        CampaignList.Segment segmentTree = CampaignList.getSegmentMapper().getSegmentTreeByRootId(rootSegmentId);

        batchSequence.add(new DeleteCampaignListMembersBatch(rootSegmentId, campaignId), 5000);

        Iterator<CampaignList.Segment> segmentTreeIterator = segmentTree.iterator();

        while (segmentTreeIterator.hasNext()) {
            CampaignList.Segment next = segmentTreeIterator.next();
            if (next instanceof CampaignListSegment.SourceSegment) {
                CampaignListSegment.SourceSegment sourceSegment = (CampaignListSegment.SourceSegment) next;
                batchSequence.add(sourceSegment.getBatchProcessor(campaignId));
            }
        }

        batchSequence.add(new CampaignListUpdateMembersBatch(campaignId, segmentTree), 3000);
        batchSequence.add(new CampaignListInsertMembersBatch(campaignId, segmentTree), 3000);
        batchSequence.add(new DeleteCampaignListMembersBatch(rootSegmentId, campaignId, true), 5000);

        if (acquireLock) {
            try {
                Mutex.MutexInterface m = CampaignList.getMutexFactory().create(campaignId, Campaign.Campaign_List_Mutex__c);
                Boolean lockAcquired = m.acquireLock();

                if (!lockAcquired) {
                    throw new CampaignListUpdateAlreadyRunningException(
                        String.format(
                            Label.CampaignToolsAlreadyRunningException,
                            new List<String>{campaignId}
                        )
                    );
                }
            } catch (Exception e) {
                throw new CampaignListUpdateAlreadyRunningException(
                    String.format(
                        Label.CampaignToolsCantUpdateException,
                        new List<String>{campaignId}
                    ),
                    e
                );
            }
        }

        CampaignList.getService().updateCampaignStatus(campaignId, CampaignList.UpdateStatus.Processing);
        batchSequence.run();
        return batchSequence.count();
    }

    /**
     * @description Update the specified campaign to indicate the status of the
     * campaign being populated from a campaign list @param campaignId The id
     * of the campaign to be updated
     *
     * @param status The status to update the campaign to
     * @return void
     */
    public void updateCampaignStatus(Id campaignId, CampaignList.UpdateStatus status) {
        Campaign c = new Campaign(
            Id = campaignId,
            Campaign_List_Update_Status__c = status.name()
        );

        if (CampaignList.UpdateStatus.Completed == status) {
            c.Campaign_List_Last_Updated__c = DateTime.now();
            c.Campaign_List_Error_Message__c = '';
        }

        update c;
    }

    /**
     * @description In the event of an error during a Campaign refresh, we need to unlock the Campaign, mark it as failed, and log the error on the Campaign.
     *
     * @param campaignId The id of the Campaign to unlock and mark as failed
     * @param errorMessage The error message to log to the Campaign record (max 255 chars)
     */
    public void unlockCampaignAndMarkFailed(Id campaignId, String errorMessage) {
        Campaign c = [
            SELECT
                Campaign_List_Mutex__c,
                Campaign_List_Update_Status__c,
                Campaign_List_Error_Message__c
            FROM Campaign
            WHERE Id = :campaignId
            FOR UPDATE
        ];

        c.Campaign_List_Mutex__c = null;
        c.Campaign_List_Update_Status__c = 'Failed';
        c.Campaign_List_Error_Message__c = errorMessage;

        update c;
    }

    /**
     * @description Get the number of slots available in the FlexQueue, if we
     * wanted to make sure at least maxFreeSlots would be left available.  This
     * will query the number of 'Holding' jobs in the FlexQueue, calculate how
     * many slots are available in the FlexQueue without exceeding the
     * 'Holding' limit, while reserving space for maxFreeSlots to remain
     * available.
     *
     * @param maxFreeSlots How many slots we want to reserve as being available
     * @return Integer The number of jobs that can be added to the FlexQueue while leaving at least maxFreeSlots remaining
     */
    public Integer getFlexQueueCapacity(Integer maxFreeSlots) {
        Integer holdingJobCount = queryHoldingJobCount();
        Integer freeSlots = Math.max(100 - holdingJobCount, 0);
        Integer capacity = Math.min(maxFreeSlots, freeSlots);
        return capacity;
    }

    /**
     * @description Query the number of jobs in the FlexQueue that are in
     * 'Holding' status.
     *
     * @return Integer
     */
    private Integer queryHoldingJobCount() {
        return [
            SELECT COUNT()
            FROM AsyncApexJob
            WHERE Status = 'Holding'
        ];
    }

    /**
     * @description Wrapper around System.enqueueJob().
     *
     * @param job The Queueable to pass to System.enqueueJob()
     * @return Id The Id of the AsyncApexJob that was enqueued.
     */
    public Id enqueueJob(Queueable job) {
        return System.enqueueJob(job);
    }

    /**
     * @description Wrapper around Database.BatchableContext.getJobId()
     *
     * @param bc The BatchableContext to get the id of
     * @return Id
     */
    public Id getJobId(Database.BatchableContext bc) {
        return bc.getJobId();
    }

    /**
     * @description Wrapper around System.abortJob()
     *
     * @param jobId The id of the AsyncApexJob to abort
     */
    public void abortJob(Id jobId) {
        System.abortJob(jobId);
    }

    public virtual class CustomException extends Exception {}
    public class CampaignListUpdateAlreadyRunningException extends CustomException {}
}