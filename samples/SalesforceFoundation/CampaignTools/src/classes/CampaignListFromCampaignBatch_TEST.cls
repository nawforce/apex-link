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
@isTest
private with sharing class CampaignListFromCampaignBatch_TEST {
    private static testMethod void testCampaignListFromCampaignBatch() {
        Id rootSegmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id targetCampaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignList_TEST.TestCampaign testCampaign = new CampaignList_TEST.TestCampaign('Test', 100, 100, '');

        CampaignListFromCampaignBatch batch = new CampaignListFromCampaignBatch(
            rootSegmentId,
            targetCampaignId,
            testCampaign.campaign.Id,
            testCampaign.campaign.Name
        );

        CampaignList_TEST.MemberMapperStub memberMapperStub = new CampaignList_TEST.MemberMapperStub();
        CampaignList.setMemberMapper(memberMapperStub);

        batch.setScope(200);

        Test.startTest();

        batch.executeBatch();

        Test.stopTest();

        Set<Id> relatedIds = new Set<Id>();

        for (Contact c : testCampaign.contacts) {
            relatedIds.add(c.Id);
        }

        for (Lead l : testCampaign.leads) {
            relatedIds.add(l.Id);
        }

        System.assertEquals(
            relatedIds,
            new Set<Id>(memberMapperStub.updateByRelatedIdsAndSourceRelatedIds)
        );

        System.assertEquals(
            rootSegmentId,
            memberMapperStub.updateByRelatedIdsAndSourceRootSegmentId
        );

        System.assertEquals(
            testCampaign.campaign.Id,
            memberMapperStub.updateByRelatedIdsAndSourceSourceId
        );

        System.assertEquals(
            testCampaign.campaign.Name,
            memberMapperStub.updateByRelatedIdsAndSourceSourceName
        );
    }
    private static testMethod void testCampaignListFromCampaignBatchHandlesException() {
        CampaignList_TEST.ServiceStub serviceStub = new CampaignList_TEST.ServiceStub();
        CampaignList_TEST.MemberMapperStub memberMapperStub = new CampaignList_TEST.MemberMapperStub();

        memberMapperStub.updateByRelatedIdsAndSourceException = new CampaignList_TEST.MockException();

        Id jobId = CampaignList_TEST.getNextId(AsyncApexJob.sObjectType);
        serviceStub.getJobIdReturn = jobId;

        CampaignList.setService(serviceStub);
        CampaignList.setMemberMapper(memberMapperStub);

        Id rootSegmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id targetCampaignId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id sourceCampaignId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        String sourceCampaignName = 'Test Source Campaign';

        CampaignListFromCampaignBatch batch = new CampaignListFromCampaignBatch(
            rootSegmentId,
            targetCampaignId,
            sourceCampaignId,
            sourceCampaignName
        );

        Test.startTest();

        batch.execute(null, new List<CampaignMember>());

        Test.stopTest();

        System.assertEquals(
            targetCampaignId,
            serviceStub.unlockCampaignAndMarkFailedCampaignId
        );

        System.assert(
            serviceStub.unlockCampaignAndMarkFailedErrorMessage.contains(
                sourceCampaignId
            )
        );

        System.assert(
            serviceStub.unlockCampaignAndMarkFailedErrorMessage.contains(
                rootSegmentId
            )
        );

        System.assertEquals(
            jobId,
            serviceStub.abortJobJobId
        );
    }
    private static testMethod void testCampaignListFromCampaignBatchHandlesStatus() {
        CampaignList_TEST.ServiceStub serviceStub = new CampaignList_TEST.ServiceStub();
        Id memberStatusId = CampaignList_TEST.getNextId(CampaignMemberStatus.sObjectType);
        String memberStatus = 'Sent';
        serviceStub.statusesMap = new Map<String,String>{memberStatusId => memberStatus};
        CampaignList_TEST.TestCampaign testCampaign = new CampaignList_TEST.TestCampaign('Test', 100, 100, memberStatus);

        CampaignList.setService(serviceStub);

        Id rootSegmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id targetCampaignId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id sourceCampaignId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        String sourceCampaignName = 'Test Source Campaign';

        CampaignListFromCampaignBatch batch = new CampaignListFromCampaignBatch(
            rootSegmentId,
            targetCampaignId,
            testCampaign.campaign.Id,
            testCampaign.campaign.Name,
            memberStatusId
        );
        CampaignList_TEST.MemberMapperStub memberMapperStub = new CampaignList_TEST.MemberMapperStub();
        CampaignList.setMemberMapper(memberMapperStub);

        batch.setScope(200);
        Test.startTest();
        batch.executeBatch();
        Test.stopTest();

        Set<Id> relatedIds = new Set<Id>();

        for (Contact c : testCampaign.contacts) {
            relatedIds.add(c.Id);
        }

        for (Lead l : testCampaign.leads) {
            relatedIds.add(l.Id);
        }

        System.assertEquals(
            relatedIds,
            new Set<Id>(memberMapperStub.updateByRelatedIdsAndSourceRelatedIds)
        );

        System.assertEquals(
            rootSegmentId,
            memberMapperStub.updateByRelatedIdsAndSourceRootSegmentId
        );

        System.assertEquals(
            testCampaign.campaign.Id,
            memberMapperStub.updateByRelatedIdsAndSourceSourceId
        );

        System.assertEquals(
            testCampaign.campaign.Name,
            memberMapperStub.updateByRelatedIdsAndSourceSourceName
        );
    }
}