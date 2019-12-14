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
private with sharing class CampaignListService_TEST {
    private static testMethod void testUpdateCampaignFromCampaignList() {
        Id sourceReportId = CampaignList_TEST.getNextId(Report.sObjectType);
        Id sourceCampaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id destinationCampaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);

        Segment__c rootSegment = new Segment__c(
            Operation__c = 'AND'
        );
        insert rootSegment;

        Segment__c reportSourceSegment = new Segment__c(
            Operation__c = 'SOURCE',
            Parent_Segment__c = rootSegment.Id,
            Root_Segment__c = rootSegment.Id,
            Source_ID__c = sourceReportId,
            Report_Column_Name__c = 'test_column_name',
            Exclude_Source__c = false
        );
        insert reportSourceSegment;

        Segment__c campaignSourceSegment = new Segment__c(
            Operation__c = 'SOURCE',
            Parent_Segment__c = rootSegment.Id,
            Root_Segment__c = rootSegment.Id,
            Source_ID__c = sourceCampaignId,
            Exclude_Source__c = false
        );
        insert campaignSourceSegment;

        BatchableSequenceStub bsStub = new BatchableSequenceStub();
        BatchableSequenceStubFactory bsStubFactory= new BatchableSequenceStubFactory();
        bsStubFactory.bs = bsStub;
        CampaignList.setBatchableSequenceFactory(bsStubFactory);

        CampaignList_TEST.ServiceStub serviceStub = new CampaignList_TEST.ServiceStub();
        CampaignList.setService(serviceStub);

        CampaignList_TEST.MutexStub mutexStub = new CampaignList_TEST.MutexStub();
        mutexStub.acquireLock = true;

        CampaignList_TEST.MutexStubFactory mutexFactory = new CampaignList_TEST.MutexStubFactory();
        mutexFactory.createReturn = mutexStub;
        CampaignList.setMutexFactory(mutexFactory);

        CampaignListService service = new CampaignListService();
        service.updateCampaignFromCampaignList(destinationCampaignId, rootSegment.Id);
        // assert that the destination campaign was updated to indicate a
        // running update

        System.assertEquals(destinationCampaignId, serviceStub.updateCampaignStatusCampaignId);
        System.assertEquals(destinationCampaignId, mutexFactory.createRecordId);
        System.assertEquals(Campaign.Campaign_List_Mutex__c, mutexFactory.createMutexField);
        System.assertEquals(1, mutexStub.acquireLockCalls);
        System.assertEquals(CampaignList.UpdateStatus.Processing, serviceStub.updateCampaignStatusStatus);

        // assert that the batches in this sequence exist in the expected
        // order, with a report source batch and a campaign source batch (in
        // either order)

        List<BatchableSequence.Batch> batches = bsStub.bs.batches;

        System.assert(batches.get(0) instanceof DeleteCampaignListMembersBatch);
        System.assert(batches.get(3) instanceof CampaignListUpdateMembersBatch);
        System.assert(batches.get(4) instanceof CampaignListInsertMembersBatch);
        System.assert(batches.get(5) instanceof DeleteCampaignListMembersBatch);

        DeleteCampaignListMembersBatch deleteCampaignListMembersBatch1 = (DeleteCampaignListMembersBatch) batches.get(0);

        CampaignListFromReportBatch reportBatch;
        CampaignListFromCampaignBatch campaignBatch;

        if (batches.get(1) instanceof CampaignListFromReportBatch) {
            reportBatch = (CampaignListFromReportBatch) batches.get(1);
            campaignBatch = (CampaignListFromCampaignBatch) batches.get(2);
        } else {
            reportBatch = (CampaignListFromReportBatch) batches.get(2);
            campaignBatch = (CampaignListFromCampaignBatch) batches.get(1);
        }

        System.assertNotEquals(null, reportBatch);
        System.assertNotEquals(null, campaignBatch);

        CampaignListUpdateMembersBatch campaignListUpdateMembersBatch = (CampaignListUpdateMembersBatch) batches.get(3);
        CampaignListInsertMembersBatch campaignListInsertMembersBatch = (CampaignListInsertMembersBatch) batches.get(4);
        DeleteCampaignListMembersBatch deleteCampaignListMembersBatch2 = (DeleteCampaignListMembersBatch) batches.get(5);

        System.assertEquals(rootSegment.Id, deleteCampaignListMembersBatch1.rootSegmentId);
        System.assertEquals(rootSegment.Id, reportBatch.rootSegmentId);
        System.assertEquals(sourceReportId, reportBatch.reportId);
        System.assertEquals('test_column_name', reportBatch.idColumnName);
        System.assertEquals(rootSegment.Id, campaignBatch.rootSegmentId);
        System.assertEquals(destinationCampaignId, campaignBatch.targetCampaignId);
        System.assertEquals(sourceCampaignId, campaignBatch.sourceCampaignId);
        System.assertEquals(rootSegment.Id, campaignListUpdateMembersBatch.rootSegment.getId());
        System.assertEquals(destinationCampaignId, campaignListUpdateMembersBatch.campaignId);
        System.assertEquals(rootSegment.Id, campaignListInsertMembersBatch.rootSegment.getId());
        System.assertEquals(destinationCampaignId, campaignListInsertMembersBatch.campaignId);
        System.assertEquals(rootSegment.Id, deleteCampaignListMembersBatch2.rootSegmentId);
    }

    private class BatchableSequenceStubFactory implements BatchableSequence.Factory {
        public BatchableSequence.Interface_x bs;
        public BatchableSequence.Interface_x create() {
            return bs;
        }
    }

    private class BatchableSequenceStub implements BatchableSequence.Interface_x {
        public BatchableSequence bs = new BatchableSequence();
        public BatchableSequence.Batch add(BatchableSequence.Batch b) {
            return bs.add(b);
        }
        public BatchableSequence.Batch add(BatchableSequence.Batch b, Integer scope) {
            return bs.add(b, scope);
        }
        public Id run() {
            return null;
        }
        public Integer count() {
            return 0;
        }
    }

    private static testMethod void testUpdateCampaignStatus() {
        Campaign c = new Campaign(Name = 'Test Campaign');
        insert c;

        CampaignListService listService = new CampaignListService();

        listService.updateCampaignStatus(c.Id, CampaignList.UpdateStatus.Completed);

        c = [
            SELECT Campaign_List_Update_Status__c
            FROM Campaign
            WHERE Id = :c.Id
            LIMIT 1
        ];

        System.assertEquals('Completed', c.Campaign_List_Update_Status__c);
    }
}