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
private with sharing class DeleteCampaignListMembersBatch_TEST {
    private static testMethod void testDeleteCampaignListMembersBatch() {
        Id campaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);

        Segment__c rootSegment = new Segment__c();
        insert rootSegment;

        List<Member__c> members = new List<Member__c>();

        for (Integer i = 0; i < 200; i++) {
            members.add(new Member__c(
                Root_Segment__c = rootSegment.Id
            ));
        }
        insert members;

        Integer listMemberCount = [
            SELECT COUNT()
            FROM Member__c
            WHERE Root_Segment__c = :rootSegment.Id
        ];

        System.assertEquals(200, listMemberCount);

        DeleteCampaignListMembersBatch batch = new DeleteCampaignListMembersBatch(
            rootSegment.Id,
            campaignId
        );
        batch.setScope(200);

        Test.startTest();

        batch.executeBatch();

        Test.stopTest();

        Integer remainingListMemberCount = [
            SELECT COUNT()
            FROM Member__c
            WHERE Root_Segment__c = :rootSegment.Id
        ];

        System.assertEquals(0, remainingListMemberCount);
    }

    public static testMethod void testFinish() {
        Id rootSegmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id campaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);

        DeleteCampaignListMembersBatch batch = new DeleteCampaignListMembersBatch(
            rootSegmentId,
            campaignId
        );

        CampaignList_TEST.ServiceStub listServiceStub = new CampaignList_TEST.ServiceStub();

        CampaignList.setService(listServiceStub);

        CampaignList_TEST.MutexStub mutexStub = new CampaignList_TEST.MutexStub();
        mutexStub.releaseLock = true;

        CampaignList_TEST.MutexStubFactory mutexFactory = new CampaignList_TEST.MutexStubFactory();
        mutexFactory.createReturn = mutexStub;
        CampaignList.setMutexFactory(mutexFactory);

        batch.finish(null);

        System.assertEquals(
            null,
            listServiceStub.updateCampaignStatusCampaignId
        );

        System.assertEquals(
            null,
            listServiceStub.updateCampaignStatusStatus
        );
    }

    public static testMethod void testFinishFinalCleanup() {
        Id rootSegmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id campaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);

        DeleteCampaignListMembersBatch batch = new DeleteCampaignListMembersBatch(rootSegmentId, campaignId, true);

        CampaignList_TEST.ServiceStub listServiceStub = new CampaignList_TEST.ServiceStub();

        CampaignList.setService(listServiceStub);

        CampaignList_TEST.MutexStub mutexStub = new CampaignList_TEST.MutexStub();
        mutexStub.releaseLock = true;

        CampaignList_TEST.MutexStubFactory mutexFactory = new CampaignList_TEST.MutexStubFactory();
        mutexFactory.createReturn = mutexStub;
        CampaignList.setMutexFactory(mutexFactory);

        batch.finish(null);

        System.assertEquals(
            campaignId,
            listServiceStub.updateCampaignStatusCampaignId
        );

        System.assertEquals(
            CampaignList.UpdateStatus.Completed,
            listServiceStub.updateCampaignStatusStatus
        );
    }

    private static testMethod void testExecuteHandlesException() {
        Id rootSegmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id campaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id jobId = CampaignList_TEST.getNextId(AsyncApexJob.sObjectType);

        CampaignList_TEST.ServiceStub service = new CampaignList_TEST.ServiceStub();
        service.getJobIdReturn = jobId;
        CampaignList.setService(service);

        DeleteCampaignListMembersBatch batch = new DeleteCampaignListMembersBatch(
            rootSegmentId,
            campaignId
        );

        batch.execute(null, null);

        System.assertEquals(
            campaignId,
            service.unlockCampaignAndMarkFailedCampaignId
        );

        System.assert(
            service.unlockCampaignAndMarkFailedErrorMessage.contains(
                rootSegmentId
            )
        );

        System.assertEquals(
            jobId,
            service.abortJobJobId
        );
    }
}