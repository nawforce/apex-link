/*
    Copyright (c) 2016 Salesforce.org
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the Salesforce.org nor the names of
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
* @description Button controller for the Refresh Campaign List button on a
* campaign.
*/
public with sharing class ProcessSegmentBTN_CTRL {

    /**
     * @description The campaign retrieved from the standard controller
     */
    private Campaign campaign;
    /**
     * @description True when permissions error occurs on page
     */
    private static Boolean hasPerms;
    /**
     * @description the count of batches that will be run
     */
    public Integer countBatches { get; private set; }
    /**
     * @description the minimum CreatedDate of Jobs to display progress on
     */
    public DateTime createdDateMin { get; private set; }

    /**
    * @description Constructor
    * @param controller StandardController to a Campaign
    */
    public ProcessSegmentBTN_CTRL(ApexPages.StandardController controller) {
        campaign = (Campaign) controller.getRecord();
        // Check if user has all required permissions for batch processing
        try {
            CampaignTools_UTIL.checkCampaignBatchPermission();
            CampaignTools_UTIL.checkObjectReadAccess('AsyncApexJob');
            hasPerms = true;
        } catch(CampaignTools_UTIL.PermsException ex) {
            hasPerms = false;
            ApexPages.addMessage(new ApexPages.Message(
                ApexPages.Severity.ERROR,
                ex.getMessage()
            ));
        }
    }

    /**
    * @description Action Method invoked by the Refresh Campaign List button on
    * a campaign.
    *
    * @return PageReference
    */
    public PageReference refreshCampaignList() {
        if (!hasPerms) {
            return null;
        }

        if (campaign.Campaign_List__c == null) {
            ApexPages.addMessage(new ApexPages.Message(
                ApexPages.Severity.ERROR,
                Label.CampaignToolsNoSavedCampaignList
            ));
            return null;
        }

        CampaignList.Service service = CampaignList.getService();

        try {
            createdDateMin = System.now();
            countBatches = service.updateCampaignFromCampaignList(
                campaign.Id,
                campaign.Campaign_List__c
            );
        } catch (CampaignListService.CampaignListUpdateAlreadyRunningException e) {
            ApexPages.addMessage(new ApexPages.Message(
                ApexPages.Severity.WARNING,
                String.format(
                    Label.CampaignToolsAlreadyRunningWarningSummary,
                    new List<String>{campaign.Name}
                ),
                Label.CampaignToolsAlreadyRunningWarningDetail
            ));
        }

        return null;
    }

    /**
     * @description A remote action for querying for the current campaign list
     * refresh status of a given campaign.  Returns one of the picklist values
     * defined on Campaign.Campaign_List_Update_Status__c, which correspond to
     * the values defined in the CampaignList.UpdateStatus enum.
     *
     * @return String
     */
    @RemoteAction
    public static String getCampaignListUpdateStatus(Id campaignId) {
        try {
            CampaignTools_UTIL.checkFieldReadAccess('Campaign', 'Campaign_List_Update_Status__c');
        } catch(CampaignTools_UTIL.PermsException ex) {
            ApexPages.addMessage(new ApexPages.Message(
                ApexPages.Severity.ERROR,
                ex.getMessage()
            ));
            return null;
        }
        Campaign c = [
            SELECT Campaign_List_Update_Status__c
            FROM Campaign
            WHERE Id = :campaignId
            LIMIT 1
        ];

        return c.Campaign_List_Update_Status__c;
    }
}