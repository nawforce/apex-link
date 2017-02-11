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
@isTest
private with sharing class CampaignListUpdateMembersBatch_TEST {
    @isTest
    private static void testConstructor() {
        // The constructor should persist the given campaignId and rootSegment

        Id campaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id segmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);

        CampaignList_TEST.SegmentStub rootSegment = new CampaignList_TEST.SegmentStub();
        rootSegment.getIdReturn = segmentId;

        CampaignListUpdateMembersBatch umb = new CampaignListUpdateMembersBatch(
            campaignId,
            rootSegment
        );

        System.assertEquals(campaignId, umb.campaignId);
        System.assertEquals(segmentId, umb.rootSegment.getId());
    }

    @isTest
    private static void testStart() {
        // start() should query for the CampaignMember objects related to the
        // given campaign

        Campaign c = new Campaign(Name = 'Test Campaign');
        insert c;

        Contact ct = new Contact(LastName = 'Test Contact');
        insert ct;

        Lead ld = new Lead(LastName = 'Test Lead', Company = 'Test Company');
        insert ld;

        CampaignMember cm1 = new CampaignMember(
            CampaignId = c.Id,
            ContactId = ct.Id,
            Source_Names__c = 'Test Source Names 1'
        );

        CampaignMember cm2 = new CampaignMember(
            CampaignId = c.Id,
            LeadId = ld.Id,
            Source_Names__c = 'Test Source Names 2'
        );

        insert new List<CampaignMember>{cm1, cm2};

        Test.startTest();

        CampaignListUpdateMembersBatch umb = new CampaignListUpdateMembersBatch(
            c.Id,
            null
        );

        Database.QueryLocator ql = umb.start(null);

        Test.stopTest();


        Map<Id, CampaignMember> queriedMembers = new Map<Id, CampaignMember>();

        Database.QueryLocatorIterator qli = ql.iterator();
        while (qli.hasNext()) {
            CampaignMember next = (CampaignMember) qli.next();
            queriedMembers.put(next.Id, next);
        }

        System.assertEquals(2, queriedMembers.size());
        System.assert(queriedMembers.containsKey(cm1.Id));
        System.assert(queriedMembers.containsKey(cm2.Id));
        System.assertEquals(c.Id, queriedMembers.get(cm1.Id).CampaignId);
        System.assertEquals(ct.Id, queriedMembers.get(cm1.Id).ContactId);
        System.assertEquals(
            'Test Source Names 1',
            queriedMembers.get(cm1.Id).Source_Names__c
        );
        System.assertEquals(c.Id, queriedMembers.get(cm2.Id).CampaignId);
        System.assertEquals(ld.Id, queriedMembers.get(cm2.Id).LeadId);
        System.assertEquals(
            'Test Source Names 2',
            queriedMembers.get(cm2.Id).Source_Names__C
        );
    }

    @isTest
    private static void testExecute() {
        // execute should delegate to updateAndDeleteCampaignMembers()

        CampaignList_TEST.MemberMapperStub mapper = new CampaignList_TEST.MemberMapperStub();
        CampaignList.setMemberMapper(mapper);

        Id campaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id segmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);

        CampaignList_TEST.SegmentStub rootSegment = new CampaignList_TEST.SegmentStub();
        rootSegment.getIdReturn = segmentId;

        List<CampaignMember> campaignMembers = new List<CampaignMember>{
            new CampaignMember(CampaignId = campaignId),
            new CampaignMember(CampaignId = campaignId)
        };

        Test.startTest();

        CampaignListUpdateMembersBatch umb = new CampaignListUpdateMembersBatch(
            campaignId,
            rootSegment
        );

        umb.execute(null, campaignMembers);

        Test.stopTest();

        System.assertEquals(
            campaignId,
            mapper.updateAndDeleteCampaignMembersCampaignId
        );

        System.assertEquals(
            segmentId,
            mapper.updateAndDeleteCampaignMembersRootSegment.getId()
        );

        System.assertEquals(
            campaignMembers,
            mapper.updateAndDeleteCampaignMembersCampaignMembers
        );
    }

    @isTest
    private static void testExecuteHandlesException() {
        // if execute() catches an exception, it should update the campaign
        // with an error and unlock the campaign

        CampaignList_TEST.ServiceStub serviceStub = new CampaignList_TEST.ServiceStub();
        CampaignList_TEST.MemberMapperStub memberMapperStub = new CampaignList_TEST.MemberMapperStub();

        memberMapperStub.updateAndDeleteCampaignMembersException = new CampaignList_TEST.MockException();

        Id jobId = CampaignList_TEST.getNextId(AsyncApexJob.sObjectType);
        serviceStub.getJobIdReturn = jobId;

        CampaignList.setService(serviceStub);
        CampaignList.setMemberMapper(memberMapperStub);

        Id campaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id segmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);

        CampaignList_TEST.SegmentStub rootSegment = new CampaignList_TEST.SegmentStub();
        rootSegment.getIdReturn = segmentId;

        Test.startTest();

        CampaignListUpdateMembersBatch umb = new CampaignListUpdateMembersBatch(
            campaignId,
            rootSegment
        );

        umb.execute(null, new List<CampaignMember>());

        Test.stopTest();

        System.assertEquals(
            campaignId,
            serviceStub.unlockCampaignAndMarkFailedCampaignId
        );

        System.assert(
            serviceStub.unlockCampaignAndMarkFailedErrorMessage.contains(
                segmentId
            )
        );

        System.assertEquals(
            jobId,
            serviceStub.abortJobJobId
        );
    }
}