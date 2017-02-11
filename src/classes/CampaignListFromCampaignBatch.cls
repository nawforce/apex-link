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
 * @description Batch process for gathering potential campaign list members
 * from a given campaign.
 */
public with sharing class CampaignListFromCampaignBatch
    extends BatchableSequence.Batch implements Database.Batchable<sObject> {
    /**
     * @description The id of the "campaign list" which the members should be
     * added to
     */
    @TestVisible
    private Id rootSegmentId;

    /**
     * @description The id of the Campaign being refreshed
     */
    @TestVisible
    private Id targetCampaignId;

    /**
     * @description The id of the Campaign from which to gather potential
     * members
     */
    @TestVisible
    private Id sourceCampaignId;

    /**
     * @description The name of the Campaign from which to gather potential
     * members
     */
    @TestVisible
    private String sourceCampaignName;

    /**
     * @description The member status Ids of the campaign members from the
     * source campaign of potential members
     */
    @TestVisible
    private String statusIds;

    /**
     * @description Construct the batch process to gather potential members
     * from a given campaign for a given campaign list.
     *
     * @param rootSegmentId The id of the "campaign list" which the members should be added to
     * @param targetCampaignId The id of the Campaign being refreshed
     * @param sourceCampaignId The id of the Campaign from which to gather potential members
     * @param sourceCampaignName The name of the Campaign from which to gather potential members
     */
    public CampaignListFromCampaignBatch(Id rootSegmentId, Id targetCampaignId, Id sourceCampaignId, String sourceCampaignName) {
        this.rootSegmentId = rootSegmentId;
        this.targetCampaignId = targetCampaignId;
        this.sourceCampaignId = sourceCampaignId;
        this.sourceCampaignName = sourceCampaignName;
    }

    /**
     * @description Construct the batch process to gather potential members
     * from a given campaign for a given campaign list.
     *
     * @param rootSegmentId The id of the "campaign list" which the members should be added to
     * @param targetCampaignId The id of the Campaign being refreshed
     * @param sourceCampaignId The id of the Campaign from which to gather potential members
     * @param sourceCampaignName The name of the Campaign from which to gather potential members
     * @param status The member status Ids of the campaign members from the source campaign of potential members
     */
    public CampaignListFromCampaignBatch(Id rootSegmentId, Id targetCampaignId, Id sourceCampaignId, String sourceCampaignName, String statusIds) {
        this.rootSegmentId = rootSegmentId;
        this.targetCampaignId = targetCampaignId;
        this.sourceCampaignId = sourceCampaignId;
        this.sourceCampaignName = sourceCampaignName;
        this.statusIds = statusIds;
    }

    /**
     * @description Implementation of start() from Database.Batchable
     * interface.
     *
     * @return Database.QueryLocator The CampaignMembers from the specified source campaign
     */
    public Database.QueryLocator start(Database.BatchableContext bc) {
        String queryStr = 'SELECT ContactId, LeadId FROM CampaignMember WHERE CampaignId = :sourceCampaignId';
        if (!String.isEmpty(statusIds)) {
            List<String> statusIdList = statusIds.split(';');
            List<String> statusList = new List<String>();
            Map<String,String> statuses = CampaignList.getService().getStatuses(sourceCampaignId);
            for (String statusId : statusIdList) {
                if (statuses.containsKey(statusId))
                    statusList.add(statuses.get(statusId));
            }
            queryStr += ' AND Status IN :statusList';
        }
        return Database.getQueryLocator(
            queryStr
        );
    }

    /**
     * @description Implementation of execute() from Database.Batchable.
     * Creates CampaignListMembers from the CampaignMembers and persists them
     * to the database.  Each CampaignListMember created will include this
     * campaign in its list of sources.
     *
     * @return void
     */
    public void execute(Database.BatchableContext bc, List<CampaignMember> campaignMembers) {
        try {
            Set<Id> ids = new Set<Id>();

            for (CampaignMember cm : campaignMembers) {
                if (null != cm.ContactId) {
                    ids.add(cm.ContactId);
                }
                if (null != cm.LeadId) {
                    ids.add(cm.LeadId);
                }
            }
            CampaignList.getMemberMapper().updateByRelatedIdsAndSource(
                new List<Id>(ids),
                rootSegmentId,
                sourceCampaignId,
                sourceCampaignName
            );
        } catch (Exception e) {
            CampaignList.Service service = CampaignList.getService();
            service.unlockCampaignAndMarkFailed(
                targetCampaignId,
                String.format(
                    Label.CampaignToolsListFromCampaignExecuteException,
                    new List<String>{
                        sourceCampaignId,
                        rootSegmentId
                    }
                )
            );
            service.abortJob(service.getJobId(bc));
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
}