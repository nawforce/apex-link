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
* @description Test code for the Refresh Campaign List button on a campaign.
*/
@isTest
private with sharing class ProcessSegmentBTN_TEST {
    @isTest
    private static void testRefreshCampaignListActionCallsService() {
        Id campaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id rootSegmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);

        Campaign c = new Campaign(
            Id = campaignId,
            Campaign_List__c = rootSegmentId
        );

        CampaignList_TEST.ServiceStub serviceStub = new CampaignList_TEST.ServiceStub();
        CampaignList.setService(serviceStub);

        Test.startTest();

        ApexPages.StandardController sc = new ApexPages.StandardController(c);
        ProcessSegmentBTN_CTRL btn = new ProcessSegmentBTN_CTRL(sc);
        PageReference pr = btn.refreshCampaignList();

        Test.stopTest();

        System.assertEquals(null, pr);

        System.assertEquals(
            campaignId,
            serviceStub.updateCampaignFromCampaignListCampaignId
        );

        System.assertEquals(
            rootSegmentId,
            serviceStub.updateCampaignFromCampaignListRootSegmentId
        );
    }

    @isTest
    private static void testRefreshCampaignListActionWithoutPermissions() {
        Id campaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id rootSegmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);

        Campaign c = new Campaign(
            Id = campaignId,
            Campaign_List__c = rootSegmentId
        );

        CampaignList_TEST.ServiceStub serviceStub = new CampaignList_TEST.ServiceStub();
        CampaignList.setService(serviceStub);
        CampaignTools_UTIL.throwException = true;

        Test.startTest();

        ApexPages.StandardController sc = new ApexPages.StandardController(c);
        ProcessSegmentBTN_CTRL btn = new ProcessSegmentBTN_CTRL(sc);
        PageReference pr = btn.refreshCampaignList();

        Test.stopTest();

        List<Apexpages.Message> msgs = ApexPages.getMessages();
        for (Apexpages.Message msg : msgs) {
            System.assertEquals(ApexPages.Severity.ERROR, msg.getSeverity(), 'An Error message should be added to the page.');
        }
    }

    @isTest
    private static void testGetCampaignUpdateStatus() {
        Campaign testCampaign = new Campaign(Name='Test Campaign', Campaign_List_Update_Status__c = 'Completed');
        insert testCampaign;

        Test.startTest();

        String status = ProcessSegmentBTN_CTRL.getCampaignListUpdateStatus(testCampaign.Id);

        Test.stopTest();

        System.assertEquals('Completed', status, 'The test campaign status should be returned.');
    }

    @isTest
    private static void testGetCampaignUpdateStatusWithoutPerms() {
        Campaign testCampaign = new Campaign(Name='Test Campaign', Campaign_List_Update_Status__c = 'Completed');
        insert testCampaign;

        Test.startTest();
        CampaignTools_UTIL.throwException = true;
        String status = ProcessSegmentBTN_CTRL.getCampaignListUpdateStatus(testCampaign.Id);

        Test.stopTest();

        System.assertEquals(null, status, 'The test campaign status should return null when permissions are missing.');
    }

    @isTest
    private static void testCampaignWithNoListIssuesErrorAndDoesNotCallService() {
        Id campaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);

        Campaign c = new Campaign(
            Id = campaignId,
            Campaign_List__c = null
        );

        CampaignList_TEST.ServiceStub serviceStub = new CampaignList_TEST.ServiceStub();
        CampaignList.setService(serviceStub);

        Test.startTest();

        ApexPages.StandardController sc = new ApexPages.StandardController(c);
        ProcessSegmentBTN_CTRL btn = new ProcessSegmentBTN_CTRL(sc);

        PageReference pr = btn.refreshCampaignList();

        Test.stopTest();

        List<ApexPages.Message> pageMessages = ApexPages.getMessages();

        System.assertEquals(1, pageMessages.size());

        System.assertEquals(
            ApexPages.Severity.ERROR,
            pageMessages.get(0).getSeverity()
        );

        System.assertEquals(
            Label.CampaignToolsNoSavedCampaignList,
            pageMessages.get(0).getSummary()
        );

        System.assertEquals(null, pr);

        System.assertEquals(
            null,
            serviceStub.updateCampaignFromCampaignListCampaignId
        );

        System.assertEquals(
            null,
            serviceStub.updateCampaignFromCampaignListRootSegmentId
        );
    }

    @isTest
    private static void testCampaignAlreadyRunningIssuesError() {
        Id campaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id rootSegmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);

        Campaign c = new Campaign(
            Id = campaignId,
            Name = 'Test Campaign',
            Campaign_List__c = rootSegmentId
        );

        CampaignList_TEST.ServiceStub serviceStub = new CampaignList_TEST.ServiceStub();
        serviceStub.updateCampaignFromCampaignListException = new CampaignListService.CampaignListUpdateAlreadyRunningException();

        CampaignList.setService(serviceStub);

        Test.startTest();

        ApexPages.StandardController sc = new ApexPages.StandardController(c);
        ProcessSegmentBTN_CTRL btn = new ProcessSegmentBTN_CTRL(sc);

        PageReference pr = btn.refreshCampaignList();

        Test.stopTest();

        List<ApexPages.Message> pageMessages = ApexPages.getMessages();

        System.assertEquals(1, pageMessages.size());

        System.assertEquals(
            ApexPages.Severity.WARNING,
            pageMessages.get(0).getSeverity()
        );

        System.assertEquals(
            String.format(
                Label.CampaignToolsAlreadyRunningWarningSummary,
                new List<String>{'Test Campaign'}
            ),
            pageMessages.get(0).getSummary()
        );

        System.assertEquals(
            Label.CampaignToolsAlreadyRunningWarningDetail,
            pageMessages.get(0).getDetail()
        );

        System.assertEquals(null, pr);

        System.assertEquals(
            campaignId,
            serviceStub.updateCampaignFromCampaignListCampaignId
        );

        System.assertEquals(
            rootSegmentId,
            serviceStub.updateCampaignFromCampaignListRootSegmentId
        );
    }
}