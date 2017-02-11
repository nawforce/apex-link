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
private with sharing class CampaignListMember_TEST {
    private static testMethod void testContactMemberFromSObject() {
        Id memberId = CampaignList_TEST.getNextId(Member__c.sObjectType);
        Id rootSegmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id contactId = CampaignList_TEST.getNextId(Contact.sObjectType);

        Id reportId = CampaignList_TEST.getNextId(Report.sObjectType);
        Id campaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id campaignId2 = CampaignList_TEST.getNextId(Campaign.sObjectType);

        Set<Id> sourceIds = new Set<Id>{reportId, campaignId};
        Set<String> sourceNames = new Set<String>{'Report Name', 'Campaign Name'};

        Member__c m = new Member__c(
            Id = memberId,
            Root_Segment__c = rootSegmentId,
            Contact__c = contactId,
            Sources__c = JSON.serialize(sourceIds),
            Source_Names__c = JSON.serialize(sourceNames)
        );

        CampaignListMember member = new CampaignListMember(m);

        System.assertEquals(memberId, member.getId());
        System.assertEquals(contactId, member.getRelatedId());
        System.assert(member.isFromSource(reportId));
        System.assert(member.isFromSource(campaignId));
        System.assert(!member.isFromSource(campaignId2));

        Member__c memberSObject = member.getSObject();

        System.assertEquals(memberId, memberSObject.Id);
        System.assertEquals(rootSegmentId, memberSObject.Root_Segment__c);
        System.assertEquals(contactId, memberSObject.Contact__c);
        System.assertEquals(null, memberSObject.Lead__c);
        System.assertEquals(
            sourceIds,
            (Set<Id>) JSON.deserialize(memberSObject.Sources__c, Set<Id>.class)
        );
        System.assertEquals(
            sourceNames,
            (Set<String>) JSON.deserialize(memberSObject.Source_Names__c, Set<String>.class)
        );

        CampaignMember cm = member.getCampaignMember();

        System.assertEquals(contactId, cm.ContactId);
        System.assertEquals(null, cm.LeadId);
        System.assertEquals(
            sourceNames,
            new Set<String>(cm.Source_Names__c.split(';'))
        );
    }

    private static testMethod void testLeadMemberFromSObject() {
        Id memberId = CampaignList_TEST.getNextId(Member__c.sObjectType);
        Id rootSegmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id leadId = CampaignList_TEST.getNextId(Lead.sObjectType);

        Id reportId = CampaignList_TEST.getNextId(Report.sObjectType);
        Id campaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id campaignId2 = CampaignList_TEST.getNextId(Campaign.sObjectType);

        Set<Id> sourceIds = new Set<Id>{reportId, campaignId};
        Set<String> sourceNames = new Set<String>{'Report Name', 'Campaign Name'};

        Member__c m = new Member__c(
            Id = memberId,
            Root_Segment__c = rootSegmentId,
            Lead__c = leadId,
            Sources__c = JSON.serialize(sourceIds),
            Source_Names__c = JSON.serialize(sourceNames)
        );

        CampaignListMember member = new CampaignListMember(m);

        System.assertEquals(memberId, member.getId());
        System.assertEquals(leadId, member.getRelatedId());
        System.assert(member.isFromSource(reportId));
        System.assert(member.isFromSource(campaignId));
        System.assert(!member.isFromSource(campaignId2));

        Member__c memberSObject = member.getSObject();

        System.assertEquals(memberId, memberSObject.Id);
        System.assertEquals(rootSegmentId, memberSObject.Root_Segment__c);
        System.assertEquals(leadId, memberSObject.Lead__c);
        System.assertEquals(null, memberSObject.Contact__c);
        System.assertEquals(
            sourceIds,
            (Set<Id>) JSON.deserialize(memberSObject.Sources__c, Set<Id>.class)
        );
        System.assertEquals(
            sourceNames,
            (Set<String>) JSON.deserialize(memberSObject.Source_Names__c, Set<String>.class)
        );

        CampaignMember cm = member.getCampaignMember();

        System.assertEquals(leadId, cm.LeadId);
        System.assertEquals(null, cm.ContactId);
        System.assertEquals(
            sourceNames,
            new Set<String>(cm.Source_Names__c.split(';'))
        );
    }

    private static testMethod void testContactMemberFromIds() {
        Id rootSegmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id contactId = CampaignList_TEST.getNextId(Contact.sObjectType);

        Id reportId = CampaignList_TEST.getNextId(Report.sObjectType);
        Id campaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);

        Set<Id> sourceIds = new Set<Id>{reportId, campaignId};
        Set<String> sourceNames = new Set<String>{'Report Name', 'Campaign Name'};

        CampaignListMember member = new CampaignListMember(contactId, rootSegmentId);

        System.assertEquals(contactId, member.getRelatedId());

        member.addSource(reportId, 'Report Name');
        member.addSource(campaignId, 'Campaign Name');

        System.assert(member.isFromSource(reportId));
        System.assert(member.isFromSource(campaignId));
        System.assert(!member.isFromSource(rootSegmentId));

        Member__c memberSObject = member.getSObject();

        System.assertEquals(null, memberSObject.Id);
        System.assertEquals(rootSegmentId, memberSObject.Root_Segment__c);
        System.assertEquals(contactId, memberSObject.Contact__c);
        System.assertEquals(null, memberSObject.Lead__c);
        System.assertEquals(
            sourceIds,
            (Set<Id>) JSON.deserialize(memberSObject.Sources__c, Set<Id>.class)
        );
        System.assertEquals(
            sourceNames,
            (Set<String>) JSON.deserialize(memberSObject.Source_Names__c, Set<String>.class)
        );
    }

    private static testMethod void testLeadMemberFromIds() {
        Id rootSegmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id leadId = CampaignList_TEST.getNextId(Lead.sObjectType);

        Id reportId = CampaignList_TEST.getNextId(Report.sObjectType);
        Id campaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);

        Set<Id> sourceIds = new Set<Id>{reportId, campaignId};
        Set<String> sourceNames = new Set<String>{'Report Name', 'Campaign Name'};

        CampaignListMember member = new CampaignListMember(leadId, rootSegmentId);

        System.assertEquals(leadId, member.getRelatedId());

        member.addSource(reportId, 'Report Name');
        member.addSource(campaignId, 'Campaign Name');

        System.assert(member.isFromSource(reportId));
        System.assert(member.isFromSource(campaignId));
        System.assert(!member.isFromSource(rootSegmentId));

        Member__c memberSObject = member.getSObject();

        System.assertEquals(null, memberSObject.Id);
        System.assertEquals(rootSegmentId, memberSObject.Root_Segment__c);
        System.assertEquals(leadId, memberSObject.Lead__c);
        System.assertEquals(null, memberSObject.Contact__c);
        System.assertEquals(
            sourceIds,
            (Set<Id>) JSON.deserialize(memberSObject.Sources__c, Set<Id>.class)
        );
        System.assertEquals(
            sourceNames,
            (Set<String>) JSON.deserialize(memberSObject.Source_Names__c, Set<String>.class)
        );
    }
}