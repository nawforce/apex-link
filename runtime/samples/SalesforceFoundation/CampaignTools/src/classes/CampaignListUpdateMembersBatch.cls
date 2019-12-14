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
    * Neither the name of the Salesforce.org nor the names of its contributors
      may be used to endorse or promote products derived from this software
      without specific prior written permission.

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
 * @description Batch process for populating a campaign with CampaignMembers
 * from a campaign list's sources that match the campaign list's inclusion
 * criteria
 */
public with sharing class CampaignListUpdateMembersBatch
    extends BatchableSequence.Batch implements Database.Batchable<sObject> {
    /** @description The id of the Campaign to populate */
    @TestVisible
    private Id campaignId;

    /** @description The "campaign list" root segment */
    @TestVisible
    private CampaignList.Segment rootSegment;

    /**
     * @description Construct the batch process to update the given campaign's
     * CampaignMembers from the given campaign list's sources that match
     * the given CampaignListCriteria
     *
     * @param campaignId The id of the Campaign to populate
     * @param rootSegment The "campaign list" root segment
     */
    public CampaignListUpdateMembersBatch(Id campaignId, CampaignList.Segment rootSegment) {
        this.campaignId = campaignId;
        this.rootSegment = rootSegment;
    }

    /**
     * @description Implementation of start() from Database.Batchable
     * interface.
     *
     * @param bc
     * @return Database.QueryLocator The CampaignMember records from the specified campaign
     */
    public Database.QueryLocator start(Database.BatchableContext bc) {
        return Database.getQueryLocator(
            'SELECT CampaignId, LeadId, ContactId, Source_Names__c FROM CampaignMember WHERE CampaignId = :campaignId'
        );
    }

    /**
     * @description Implementation of execute() from Database.Batchable.
     * Updates or deletes CampaignMembers based on the existing Member__c
     * sObjects that have been prepared for this campaign list.  Each
     * CampaignMember will be checked to see if it has a corresponding
     * Member__c, and if it does not or if that corresponding Member__c does
     * not meet the campaign list criteria, then the CampaignMember will be
     * deleted.  Otherwise, the CampaignMember will be updated to reflect the
     * Source_Names__c from the corresponding Member__c record.
     *
     * @param bc
     * @param campaignMembers The CampaignMembers to update or delete
     * @return void
     */
    public void execute(Database.BatchableContext bc, List<CampaignMember> campaignMembers) {
        try {
            CampaignList.getMemberMapper().updateAndDeleteCampaignMembers(
                campaignId,
                rootSegment,
                campaignMembers
            );
        } catch (Exception e) {
            CampaignList.Service service = CampaignList.getService();
            service.unlockCampaignAndMarkFailed(
                campaignId,
                String.format(
                    Label.CampaignToolsListUpdateMembersExecuteException,
                    new List<String>{
                        rootSegment.getId()
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