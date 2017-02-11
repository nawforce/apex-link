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
 * @description Batch process for cleaning up a campaign list's temporary
 * Member__c records
 */
public with sharing class DeleteCampaignListMembersBatch
    extends BatchableSequence.Batch implements Database.Batchable<sObject> {
    /** @description The "campaign list" id */
    @TestVisible
    private Id rootSegmentId;

    /** @description Is this the final cleanup pass? (i.e. should we unlock the
     * Campaign when we're done?)
     */
    @TestVisible
    private Boolean isFinalCleanup = false;

    /** @description The id of the Campaign to populate */
    @TestVisible
    private Id campaignId;

    /**
     * @description Construct the DeleteCampaignListMembersBatch class to clean
     * up the given campaign list's Member__c records
     *
     * @param rootSegmentId The "campaign list" id
     */
    public DeleteCampaignListMembersBatch(Id rootSegmentId, Id campaignId) {
        this(rootSegmentId, campaignId, false);
    }

    /**
     * @description Construct the DeleteCampaignListMembersBatch class to clean
     * up the given campaign list's Member__c records
     *
     * @param rootSegmentId The "campaign list" id
     * @param isFinalCleanup Is this the final cleanup pass?
     */
    public DeleteCampaignListMembersBatch(Id rootSegmentId, Id campaignId, Boolean isFinalCleanup) {
        this.rootSegmentId = rootSegmentId;
        this.campaignId = campaignId;
        this.isFinalCleanup = isFinalCleanup;
    }

    /**
     * @description Implementation of start() from Database.Batchable
     * interface.
     *
     * @return Database.QueryLocator The Member__c records from the specified campaign list
     */
    public Database.QueryLocator start(Database.BatchableContext bc) {
        return Database.getQueryLocator(
            'SELECT Id FROM Member__c WHERE Root_Segment__c = :rootSegmentId'
        );
    }

    /**
     * @description Implementation of execute() from Database.Batchable.
     * Deletes the given Member__c records and empties them from the recycle
     * bin.
     *
     * @return void
     */
    public void execute(Database.BatchableContext bc, List<Member__c> scope) {
        try {
            delete scope;
            Database.emptyRecycleBin(scope);
        } catch (Exception e) {
            CampaignList.Service service = CampaignList.getService();
            service.unlockCampaignAndMarkFailed(
                campaignId,
                String.format(
                    Label.CampaignToolsDeleteCampaignListMembersExecuteException,
                    new List<String>{
                        rootSegmentId
                    }
                )
            );
            service.abortJob(service.getJobId(bc));
        }
    }

    /**
     * @description Implementation of finish() from Database.Batchable
     * interface.  This will call BatachableSequence's finish() method and then
     * release the mutex lock held on the campaign if this campaign list
     * cleanup is the final pass cleanup for the refresh process.
     *
     * @param bc The BatchableContext for this batch
     * @return void
     */
    public override void finish(Database.BatchableContext bc) {
        super.finish(bc);

        if (isFinalCleanup) {
            try {
                CampaignList.getService().updateCampaignStatus(
                    campaignId,
                    CampaignList.UpdateStatus.Completed
                );

                Mutex.MutexInterface m = CampaignList.getMutexFactory().create(campaignId, Campaign.Campaign_List_Mutex__c);
                Boolean lockReleased = m.releaseLock();

                if (!lockReleased) {
                    throw new CouldNotReleaseLockException(
                        String.format(
                            Label.CampaignToolsReleaseLockException,
                            new List<String>{campaignId}
                        )
                    );
                }

            } catch (Exception e) {
                throw new CouldNotUpdateCampaignException(
                    String.format(
                        Label.CampaignToolsCantFinishUpdateException,
                        new List<String>{campaignId}
                    ),
                    e
                );
            }
        }
    }

    /**
     * @description This will cause this batch to be executed, i.e.,
     * Database.executeBatch() will be called with this batch as the batch to
     * execute and this batch's scope.
     *
     * @return Id The id of the AsyncApexJob returned by Database.executeBatch()
     */
    public override Id executeBatch() {
        return Database.executeBatch(this, getScope());
    }

    public virtual class CustomException extends Exception {}
    public class CouldNotReleaseLockException extends CustomException {}
    public class CouldNotUpdateCampaignException extends CustomException {}
}