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
private with sharing class CampaignListMemberMapper_TEST {
    private static testMethod void testUpdateByRelatedIdsUpdatesMembersAndUpserts() {
        Contact contact1 = new Contact(LastName='Test Contact 1');
        insert contact1;

        Contact contact2 = new Contact(LastName='Test Contact 2');
        insert contact2;

        Lead lead1 = new Lead(
            LastName = 'Test Lead 1',
            Company = 'Test Company'
        );
        insert lead1;

        Lead lead2 = new Lead(
            LastName='Test Lead 2',
            Company = 'Test Company'
        );
        insert lead2;

        Segment__c rootSegment = new Segment__c();
        insert rootSegment;

        Segment__c otherRootSegment = new Segment__c();
        insert otherRootSegment;

        Id sourceId1 = CampaignList_TEST.getNextId(Report.sObjectType);
        String sourceName1 = 'Test Report';

        Id sourceId2 = CampaignList_TEST.getNextId(Campaign.sObjectType);
        String sourceName2 = 'Test Campaign';

        Member__c existingContactMember = new Member__c(
            Contact__c = contact1.Id,
            Root_Segment__c = rootSegment.Id,
            Sources__c = JSON.serialize(new Set<Id>{sourceId2}),
            Source_Names__c = JSON.serialize(new Set<String>{sourceName2})
        );
        insert existingContactMember;

        Member__c existingLeadMember = new Member__c(
            Lead__c = lead1.Id,
            Root_Segment__c = rootSegment.Id
        );
        insert existingLeadMember;

        // these two members belong to a different campaign list, but point to the same contact and leads:

        Member__c existingContactMember2 = new Member__c(
            Contact__c = contact1.Id,
            Root_Segment__c = otherRootSegment.Id,
            Sources__c = JSON.serialize(new Set<Id>{sourceId2}),
            Source_Names__c = JSON.serialize(new Set<String>{sourceName2})
        );
        insert existingContactMember2;

        Member__c existingLeadMember2 = new Member__c(
            Lead__c = lead1.Id,
            Root_Segment__c = otherRootSegment.Id
        );
        insert existingLeadMember2;

        List<Id> relatedIds = new List<Id>{
            contact1.Id,
            contact2.Id,
            lead1.Id,
            lead2.Id
        };

        CampaignListMemberMapper memberMapper = new CampaignListMemberMapper();

        memberMapper.updateByRelatedIdsAndSource(
            relatedIds,
            rootSegment.Id,
            sourceId1,
            sourceName1
        );

        Integer updatedMemberCount = [
            SELECT COUNT()
            FROM Member__c
            WHERE Root_Segment__c = :rootSegment.Id
        ];

        System.assertEquals(4, updatedMemberCount);

        Member__c updatedContactMember = [
            SELECT
                Sources__c,
                Source_Names__c
            FROM Member__c
            WHERE Contact__c = :contact1.Id
            LIMIT 1
        ];

        System.assertEquals(
            new Set<Id>{sourceId1, sourceId2},
            JSON.deserialize(updatedContactMember.Sources__c, Set<Id>.class)
        );
        System.assertEquals(
            new Set<String>{sourceName1, sourceName2},
            JSON.deserialize(updatedContactMember.Source_Names__c, Set<String>.class)
        );

        Member__c insertedContactMember = [
            SELECT
                Sources__c,
                Source_Names__c
            FROM Member__c
            WHERE Contact__c = :contact2.Id
            LIMIT 1
        ];

        System.assertEquals(
            new Set<Id>{sourceId1},
            JSON.deserialize(insertedContactMember.Sources__c, Set<Id>.class)
        );
        System.assertEquals(
            new Set<String>{sourceName1},
            JSON.deserialize(insertedContactMember.Source_Names__c, Set<String>.class)
        );

        Member__c updatedLeadMember = [
            SELECT
                Sources__c,
                Source_Names__c
            FROM Member__c
            WHERE Lead__c = :lead1.Id
            LIMIT 1
        ];

        System.assertEquals(
            new Set<Id>{sourceId1},
            JSON.deserialize(updatedLeadMember.Sources__c, Set<Id>.class)
        );
        System.assertEquals(
            new Set<String>{sourceName1},
            JSON.deserialize(updatedLeadMember.Source_Names__c, Set<String>.class)
        );

        Member__c insertedLeadMember = [
            SELECT
                Sources__c,
                Source_Names__c
            FROM Member__c
            WHERE Lead__c = :lead2.Id
            LIMIT 1
        ];

        System.assertEquals(
            new Set<Id>{sourceId1},
            JSON.deserialize(insertedLeadMember.Sources__c, Set<Id>.class)
        );
        System.assertEquals(
            new Set<String>{sourceName1},
            JSON.deserialize(insertedLeadMember.Source_Names__c, Set<String>.class)
        );
    }

    private static testMethod void testQueryCampaignMembersByRelatedIds() {
        // calling queryCampaignMembersByRelatedIds should return
        // CampaignMembers that have ContactId or LeadId in the given set of
        // ids

        Campaign campaign1 = new Campaign(Name = 'Test Campaign 1');
        Campaign campaign2 = new Campaign(Name = 'Test Campaign 2');

        insert campaign1;
        insert campaign2;

        Contact contact1 = new Contact(LastName = 'Test Contact 1');
        Contact contact2 = new Contact(LastName = 'Test Contact 2');

        insert contact1;
        insert contact2;

        Lead lead1 = new Lead(LastName = 'Test Lead 1', Company = 'Test');
        Lead lead2 = new Lead(LastName = 'Test Lead 2', Company = 'Test');

        insert lead1;
        insert lead2;

        CampaignMember cm1 = new CampaignMember(CampaignId = campaign1.Id, ContactId = contact1.Id);
        CampaignMember cm2 = new CampaignMember(CampaignId = campaign1.Id, ContactId = contact2.Id);
        CampaignMember cm3 = new CampaignMember(CampaignId = campaign2.Id, ContactId = contact1.Id);
        CampaignMember cm4 = new CampaignMember(CampaignId = campaign1.Id, LeadId = lead1.Id);
        CampaignMember cm5 = new CampaignMember(CampaignId = campaign1.Id, LeadId = lead2.Id);
        CampaignMember cm6 = new CampaignMember(CampaignId = campaign2.Id, LeadId = lead1.Id);

        insert new List<CampaignMember>{cm1, cm2, cm3, cm4, cm5, cm6};

        Set<Id> relatedIds = new Set<Id>{contact1.Id, lead1.Id};

        CampaignListMemberMapper mm = new CampaignListMemberMapper();
        List<CampaignMember> campaignMembers = mm.queryCampaignMembersByRelatedIds(campaign1.Id, relatedIds);


        System.assertEquals(2, campaignMembers.size());

        Set<Id> actualRelatedIds = new Set<Id>();

        for (CampaignMember cm : campaignMembers) {
            System.assertEquals(campaign1.Id, cm.CampaignId);

            if (null != cm.ContactId) {
                actualRelatedIds.add(cm.ContactId);
            }

            if (null != cm.LeadId) {
                actualRelatedIds.add(cm.LeadId);
            }
        }

        System.assertEquals(relatedIds, actualRelatedIds);
    }

    @isTest
    private static void testInsertCampaignMembers() {
        // Given 4 Member__c records:
        // m1 meets criteria and is associated with cm1
        // m2 does not meet criteria and has no associated CampaignMember
        // m3 meets criteria and has no associated CampaignMember
        // m4 does not meet criteria and is associated with cm2

        // and 3 CampaignMember records:
        // cm1 is associated with m1
        // cm2 is associated m4
        // cm3 doesn't have an associated Member__c

        // after calling insertCampaignMembers()

        // A CampaignMember associated with m4 should have been inserted.  The
        // remaining CampaignMember records should not have been modified.  The
        // m2 and m3 records should have been deleted, since they've been
        // processed and any necessary CampaignMembers have been inserted.

        TestData td = new TestData();

        CampaignListMemberMapper mapper = new CampaignListMemberMapper();

        Test.startTest();

        mapper.insertCampaignMembers(
            td.c.Id,
            td.rootSegment,
            new List<Member__c>{td.m1, td.m2, td.m3, td.m4}
        );

        Test.stopTest();


        Integer numberOfCampaignMembers = [SELECT COUNT() FROM CampaignMember];

        CampaignMember cm1 = [SELECT Id FROM CampaignMember WHERE Id = :td.cm1.Id];
        CampaignMember cm2 = [SELECT Id FROM CampaignMember WHERE Id = :td.cm2.Id];
        CampaignMember cm3 = [SELECT Id FROM CampaignMember WHERE Id = :td.cm3.Id];
        CampaignMember cm4 = [
            SELECT
                CampaignId,
                LeadId,
                Source_Names__c
            FROM CampaignMember
            WHERE Id NOT IN (:td.cm1.Id, :td.cm2.Id, :td.cm3.Id)
        ];

        System.assertEquals(4, numberOfCampaignMembers);

        System.assertEquals(td.cm1.Id, cm1.Id);
        System.assertEquals(td.cm2.Id, cm2.Id);
        System.assertEquals(td.cm3.Id, cm3.Id);

        System.assertEquals(td.c.Id, cm4.CampaignId);
        System.assertEquals(td.m3.Lead__c, cm4.LeadId);
        System.assertEquals('Campaign: Test Source 1', cm4.Source_Names__c);

        Integer numberOfMembers = [SELECT COUNT() FROM Member__c];

        Member__c m1 = [SELECT Id FROM Member__c WHERE Id = :td.m1.Id];
        Member__c m4 = [SELECT Id FROM Member__c WHERe Id = :td.m4.Id];

        System.assertEquals(2, numberOfMembers);
        System.assertEquals(td.m1.Id, m1.Id);
        System.assertEquals(td.m4.Id, m4.Id);
    }

    @isTest
    private static void testUpdateAndDeleteCampaignMembers() {
        // Given 4 Member__c records:
        // m1 meets criteria and is associated with cm1
        // m2 does not meet criteria and has no associated CampaignMember
        // m3 meets criteria and has no associated CampaignMember
        // m4 does not meet criteria and is associated with cm2

        // and 3 CampaignMember records:
        // cm1 is associated with m1
        // cm2 is associated m4
        // cm3 doesn't have an associated Member__c

        // after calling updateAndDeleteCampaignMembers()

        // cm1 should have been updated to reflect m1
        // cm2 should have been deleted since m4 does not meet criteria
        // cm3 should have been deleted since it does not have a corresponding member
        // m1 and m4 should have been deleted, since they've been processed

        TestData td = new TestData();

        CampaignListMemberMapper mapper = new CampaignListMemberMapper();

        Test.startTest();

        mapper.updateAndDeleteCampaignMembers(
            td.c.Id,
            td.rootSegment,
            new List<CampaignMember>{td.cm1, td.cm2, td.cm3}
        );

        Test.stopTest();

        Integer numberOfCampaignMembers = [SELECT COUNT() FROM CampaignMember];

        CampaignMember cm1 = [
            SELECT
                CampaignId,
                ContactId,
                Source_Names__c
            FROM CampaignMember
            WHERE Id = :td.cm1.Id
        ];

        System.assertEquals(1, numberOfCampaignMembers);

        System.assertEquals(td.c.Id, cm1.CampaignId);
        System.assertEquals(td.m1.Contact__c, cm1.ContactId);
        System.assertEquals('Campaign: Test Source 1', cm1.Source_Names__c);

        Integer numberOfMembers = [SELECT COUNT() FROM Member__c];

        Member__c m2 = [SELECT Id FROM Member__c WHERE Id = :td.m2.Id];
        Member__c m3 = [SELECT Id FROM Member__c WHERe Id = :td.m3.Id];

        System.assertEquals(2, numberOfMembers);
        System.assertEquals(td.m2.Id, m2.Id);
        System.assertEquals(td.m3.Id, m3.Id);
    }

    /**
     * @description A class that inserts and allows referencing of test data
     */
    private class TestData {
        public Id sourceId;
        public Segment__c s;
        public CampaignList.Segment rootSegment;
        public Campaign c;
        public Contact ct1;
        public Contact ct2;
        public Contact ct3;
        public Lead ld1;
        public Lead ld2;
        public Member__c m1;
        public Member__c m2;
        public Member__c m3;
        public Member__c m4;
        public CampaignMember cm1;
        public CampaignMember cm2;
        public CampaignMember cm3;

        public TestData() {
            // will create and insert:

            // 4 Member__c records:
            // m1 meets criteria and is associated with cm1
            // m2 does not meet criteria and has no associated CampaignMember
            // m3 meets criteria and has no associated CampaignMember
            // m4 does not meet criteria and is associated with cm2

            // 3 CampaignMember records:
            // cm1 is associated with m1
            // cm2 is associated m4
            // cm3 doesn't have an associated Member__c

            sourceId = CampaignList_TEST.getNextId(Campaign.sObjectType);

            s = new Segment__c(
                Operation__c = 'SOURCE',
                Exclude_Source__c = false,
                Source_ID__c = sourceId
            );
            insert s;

            rootSegment = new CampaignListSegment.CampaignSourceSegment(
                s.Id,
                s.Id,
                s.Id,
                false,
                sourceId,
                ''
            );

            c = new Campaign(Name = 'Test Campaign');
            insert c;

            ct1 = new Contact(LastName = 'Test Contact 1');
            ct2 = new Contact(LastName = 'Test Contact 2');
            ct3 = new Contact(LastName = 'Test Contact 3');
            ld1 = new Lead(LastName = 'Test Lead 1', Company = 'Test Company');
            ld2 = new Lead(LastName = 'Test Lead 2', Company = 'Test Company');

            insert new List<sObject>{ct1, ct2, ct3, ld1, ld2};

            m1 = new Member__c(
                Contact__c = ct1.Id,
                Root_Segment__c = s.Id,
                Source_Names__c = JSON.serialize(new Set<String>{'Campaign: Test Source 1'}),
                Sources__c = JSON.serialize(new Set<Id>{sourceId})
            );

            m2 = new Member__c(
                Contact__c = ct2.Id,
                Root_Segment__c = s.Id
            );

            m3 = new Member__c(
                Lead__c = ld1.Id,
                Root_Segment__c = s.Id,
                Source_Names__c = JSON.serialize(new Set<String>{'Campaign: Test Source 1'}),
                Sources__c = JSON.serialize(new Set<Id>{sourceId})
            );

            m4 = new Member__c(
                Lead__c = ld2.Id,
                Root_Segment__c = s.Id
            );

            insert new List<Member__c>{m1, m2, m3, m4};

            cm1 = new CampaignMember(
                CampaignId = c.Id,
                ContactId = ct1.Id
            );

            cm2 = new CampaignMember(
                CampaignId = c.Id,
                LeadId = ld2.Id
            );

            cm3 = new CampaignMember(
                CampaignId = c.Id,
                ContactId = ct3.Id
            );

            insert new List<CampaignMember>{cm1, cm2, cm3};
        }
    }
}