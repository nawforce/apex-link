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
private with sharing class CampaignListInsertMembersBatch_TEST {
    @isTest
    private static void testConstructor() {
        // The constructor should persist the given campaignId and rootSegment

        Id campaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id segmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);

        CampaignList_TEST.SegmentStub rootSegment = new CampaignList_TEST.SegmentStub();
        rootSegment.getIdReturn = segmentId;

        CampaignListInsertMembersBatch imb = new CampaignListInsertMembersBatch(
            campaignId,
            rootSegment
        );

        System.assertEquals(campaignId, imb.campaignId);
        System.assertEquals(segmentId, imb.rootSegment.getId());
    }

    @isTest
    private static void testStart() {
        // start() should query for the Member__c objects related to the given
        // rootSegment

        Contact ct = new Contact(LastName = 'Test Contact');
        insert ct;

        Lead ld = new Lead(LastName = 'Test Lead', Company = 'Test Company');
        insert ld;

        Segment__c s1 = new Segment__c();
        insert s1;

        Segment__c s2 = new Segment__c();
        insert s2;

        Member__c m1 = new Member__c(
            Root_Segment__c = s1.Id,
            Contact__c = ct.Id,
            Sources__c = 'Test Sources 1',
            Source_Names__c = 'Test Source Names 1'
        );

        Member__c m2 = new Member__c(
            Root_Segment__c = s1.Id,
            Lead__c = ld.Id,
            Sources__c = 'Test Sources 2',
            Source_Names__c = 'Test Source Names 2'
        );

        Member__c m3 = new Member__c(
            Root_Segment__c = s2.Id,
            Contact__c = ct.Id,
            Sources__c = 'Test Sources 3',
            Source_Names__c = 'Test Source Names 3'
        );

        insert new List<Member__c>{m1, m2, m3};

        CampaignList_TEST.SegmentStub rootSegment = new CampaignList_TEST.SegmentStub();
        rootSegment.getIdReturn = s1.Id;

        Test.startTest();

        CampaignListInsertMembersBatch imb = new CampaignListInsertMembersBatch(
            null,
            rootSegment
        );

        Database.QueryLocator ql = imb.start(null);

        Test.stopTest();

        Map<Id, Member__c> queriedMembers = new Map<Id, Member__c>();

        Database.QueryLocatorIterator qli = ql.iterator();
        while (qli.hasNext()) {
            Member__c next = (Member__c) qli.next();
            queriedMembers.put(next.Id, next);
        }

        System.assertEquals(2, queriedMembers.size());

        System.assert(queriedMembers.containsKey(m1.Id));
        System.assert(queriedMembers.containsKey(m2.Id));

        System.assertEquals(
            s1.Id,
            queriedMembers.get(m1.Id).Root_Segment__c
        );

        System.assertEquals(
            ct.Id,
            queriedMembers.get(m1.Id).Contact__c
        );

        System.assertEquals(
            'Test Sources 1',
            queriedMembers.get(m1.Id).Sources__c
        );

        System.assertEquals(
            'Test Source Names 1',
            queriedMembers.get(m1.Id).Source_Names__c
        );

        System.assertEquals(
            s1.Id,
            queriedMembers.get(m2.Id).Root_Segment__c
        );

        System.assertEquals(
            ld.Id,
            queriedMembers.get(m2.Id).Lead__c
        );

        System.assertEquals(
            'Test Sources 2',
            queriedMembers.get(m2.Id).Sources__c
        );

        System.assertEquals(
            'Test Source Names 2',
            queriedMembers.get(m2.Id).Source_Names__c
        );
    }

    @isTest
    private static void testExecute() {
        // execute should delegate to insertCampaignMembers()

        CampaignList_TEST.MemberMapperStub mapper = new CampaignList_TEST.MemberMapperStub();
        CampaignList.setMemberMapper(mapper);

        Id campaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id segmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);

        CampaignList_TEST.SegmentStub rootSegment = new CampaignList_TEST.SegmentStub();
        rootSegment.getIdReturn = segmentId;

        List<Member__c> members = new List<Member__c>{
            new Member__c(Root_Segment__c = segmentId),
            new Member__c(Root_Segment__c = segmentId)
        };

        Test.startTest();

        CampaignListInsertMembersBatch imb = new CampaignListInsertMembersBatch(
            campaignId,
            rootSegment
        );

        imb.execute(null, members);

        Test.stopTest();

        System.assertEquals(
            campaignId,
            mapper.insertCampaignMembersCampaignId
        );

        System.assertEquals(
            segmentId,
            mapper.insertCampaignMembersRootSegment.getId()
        );

        System.assertEquals(
            members,
            mapper.insertCampaignMembersMembers
        );
    }

    @isTest
    private static void testExecuteHandlesException() {
        // if execute() catches an exception, it should update the campaign
        // with an error and unlock the campaign

        CampaignList_TEST.ServiceStub serviceStub = new CampaignList_TEST.ServiceStub();
        CampaignList_TEST.MemberMapperStub memberMapperStub = new CampaignList_TEST.MemberMapperStub();

        memberMapperStub.insertCampaignMembersException = new CampaignList_TEST.MockException();

        Id jobId = CampaignList_TEST.getNextId(AsyncApexJob.sObjectType);
        serviceStub.getJobIdReturn = jobId;

        CampaignList.setService(serviceStub);
        CampaignList.setMemberMapper(memberMapperStub);

        Id campaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id segmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);

        CampaignList_TEST.SegmentStub rootSegment = new CampaignList_TEST.SegmentStub();
        rootSegment.getIdReturn = segmentId;

        Test.startTest();

        CampaignListInsertMembersBatch imb = new CampaignListInsertMembersBatch(
            campaignId,
            rootSegment
        );

        imb.execute(null, new List<Member__c>());

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