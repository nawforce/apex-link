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
private with sharing class CampaignListFromReportBatch_TEST {
    private static testMethod void testCampaignListFromReportBatchProcessesIdsFromIterable() {
        Id rootSegmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id testTargetCampaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id sourceId = CampaignList_TEST.getNextId(Report.sObjectType);
        String sourceName = 'Test';

        CampaignListFromReportBatch batch = new CampaignListFromReportBatch(
            rootSegmentId,
            testTargetCampaignId,
            sourceId,
            'Test_Column_Name',
            sourceName
        );

        List<Id> relatedIds = new List<Id>();

        for (Integer i = 1; i <= 200; i++) {
            relatedIds.add(CampaignList_TEST.getNextId(Contact.sObjectType));
        }

        IdIterableFactoryStub factoryStub = new IdIterableFactoryStub();
        factoryStub.createReturn = relatedIds;
        batch.idIterableFactory = factoryStub;

        CampaignList_TEST.MemberMapperStub listMemberStub = new CampaignList_TEST.MemberMapperStub();
        CampaignList.setMemberMapper(listMemberStub);

        batch.setScope(200);

        Test.startTest();

        batch.executeBatch();

        Test.stopTest();

        System.assertEquals(relatedIds, listMemberStub.updateByRelatedIdsAndSourceRelatedIds);
        System.assertEquals(rootSegmentId, listMemberStub.updateByRelatedIdsAndSourceRootSegmentId);
        System.assertEquals(sourceId, listMemberStub.updateByRelatedIdsAndSourceSourceId);
        System.assertEquals(sourceName, listMemberStub.updateByRelatedIdsAndSourceSourceName);
    }

    private static testMethod void testStartHandlesException() {
        Id rootSegmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id testTargetCampaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id sourceId = CampaignList_TEST.getNextId(Report.sObjectType);
        String sourceName = 'Test';
        Id jobId = CampaignList_TEST.getNextId(AsyncApexJob.sObjectType);

        CampaignListFromReportBatch batch = new CampaignListFromReportBatch(
            rootSegmentId,
            testTargetCampaignId,
            sourceId,
            'Test_Column_Name',
            sourceName
        );

        IdIterableFactoryStub factoryStub = new IdIterableFactoryStub();
        factoryStub.createException = new CampaignList_TEST.MockException();
        batch.idIterableFactory = factoryStub;

        CampaignList_TEST.ServiceStub serviceStub = new CampaignList_TEST.ServiceStub();

        serviceStub.getJobIdReturn = jobId;
        CampaignList.setService(serviceStub);

        batch.start(null);

        System.assertEquals(
            testTargetCampaignId,
            serviceStub.unlockCampaignAndMarkFailedCampaignId
        );

        System.assert(
            serviceStub.unlockCampaignAndMarkFailedErrorMessage.contains(
                sourceId
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

    private static testMethod void testExecuteHandlesException() {
        Id rootSegmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id testTargetCampaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id sourceId = CampaignList_TEST.getNextId(Report.sObjectType);
        String sourceName = 'Test';
        Id jobId = CampaignList_TEST.getNextId(AsyncApexJob.sObjectType);

        CampaignListFromReportBatch batch = new CampaignListFromReportBatch(
            rootSegmentId,
            testTargetCampaignId,
            sourceId,
            'Test_Column_Name',
            sourceName
        );

        CampaignList_TEST.MemberMapperStub memberMapperStub = new CampaignList_TEST.MemberMapperStub();
        memberMapperStub.updateByRelatedIdsAndSourceException = new CampaignList_TEST.MockException();
        CampaignList.setMemberMapper(memberMapperStub);

        CampaignList_TEST.ServiceStub serviceStub = new CampaignList_TEST.ServiceStub();
        serviceStub.getJobIdReturn = jobId;
        CampaignList.setService(serviceStub);

        batch.execute(null, new List<Id>());

        System.assertEquals(
            testTargetCampaignId,
            serviceStub.unlockCampaignAndMarkFailedCampaignId
        );

        System.assert(
            serviceStub.unlockCampaignAndMarkFailedErrorMessage.contains(
                sourceId
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

    private class IdIterableFactoryStub implements CampaignListFromReportBatch.IdIterableFactory {
        public Id createReportId;
        public String createIdColumnName;
        public Iterable<Id> createReturn;
        public Exception createException;
        public Iterable<Id> create(Id reportId, String idColumnName) {
            this.createReportId = reportId;
            this.createIdColumnName = idColumnName;
            if (null != this.createException) {
                throw this.createException;
            } else {
                return this.createReturn;
            }
        }
    }
}