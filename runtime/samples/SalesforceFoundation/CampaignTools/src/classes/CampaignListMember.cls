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
/**
 * @author Salesforce.org
 * @group CampaignTools
 * @description A model class that represents a potential member of a campaign
 * list
 */
public with sharing class CampaignListMember implements CampaignList.Member {
    /** @description The id of the campaign list member, if persisted */
    private Id memberId;

    /** @description The id of the "campaign list" this member belongs to */
    private Id rootSegmentId;

    /**
     * @description The id of the related record this campaign list member
     * corresponds to
     */
    private Id relatedId;

    /**
     * @description The set of ids for the sources (i.e. Campaigns or Reports)
     * that this list member is found in
     */
    private Set<Id> sourceIds = new Set<Id>();

    /**
     * @description The set of names for the sources (i.e. Campaigns or
     * Reports) that this list member is found in
     */
    private Set<String> sourceNames = new Set<String>();

    /**
     * @description Construct a CampaignListMember from a Member__c sObject
     * @param memberSObject The Member__c that holds peristed data
     */
    public CampaignListMember(Member__c memberSObject) {
        if (null != memberSObject.Id) {
            memberId = memberSObject.Id;
        }

        if (null != memberSObject.Root_Segment__c) {
            rootSegmentId = memberSObject.Root_Segment__c;
        }

        if (null == memberSObject.Contact__c) {
            relatedId = memberSObject.Lead__c;
        } else {
            relatedId = memberSObject.Contact__c;
        }

        if (!String.isBlank(memberSObject.Sources__c)) {
            sourceIds = (Set<Id>) JSON.deserializeStrict(
                memberSObject.Sources__c,
                Set<Id>.class
            );
        }

        if (!String.isBlank(memberSObject.Source_Names__c)) {
            sourceNames = (Set<String>) JSON.deserializeStrict(
                memberSObject.Source_Names__c,
                Set<String>.class
            );
        }
    }

    /**
     * @description Construct a CampaignListMember from the id of a related
     * record (a Contact or a Lead, etc), and the campaign list this member
     * belongs to
     *
     * @param relatedId The id of the related Contact or Lead record
     * @param rootSegmentId The id of the campaign list this member belongs to
     */
    public CampaignListMember(Id relatedId, Id rootSegmentId) {
        this.relatedId = relatedId;
        this.rootSegmentId = rootSegmentId;
    }

    /**
     * @description Get the id of this member record
     *
     * @return Id
     */
    public Id getId() {
        return memberId;
    }

    /**
     * @description Get the id of the related record (a Contact, Lead, etc.)
     * that this member corresponds to
     *
     * @return Id
     */
    public Id getRelatedId() {
        return relatedId;
    }

    /**
     * @description Add a source to this member.
     *
     * @param sourceId The id of the source Campaign, Report, etc.
     * @param sourceName The name of the source Campaign, Report, etc.
     * @return void
     */
    public void addSource(Id sourceId, String sourceName) {
        sourceIds.add(sourceId);
        sourceNames.add(sourceName);
    }

    /**
     * @description Given the id of a source, determine if this member was
     * found in that source.
     *
     * @param sourceId The id of a source, i.e. a Report or a Campaign, etc.
     * @return Boolean (true if this member was found in the given source, false otherwise)
     */
    public Boolean isFromSource(Id sourceId) {
        return sourceIds.contains(sourceId);
    }

    /**
     * @description Does this CampaignListMember meet the criteria of the given CampaignListSegment?
     *
     * @param segment
     * @return Boolean
     */
    public Boolean meetsCriteria(CampaignList.Segment segment) {
        return segment.meetsCriteria(this);
    }

    /**
     * @description Get a serialized version of this member as a Member__c
     * sObject
     *
     * @return Member__c
     */
    public Member__c getSObject() {
        Member__c member = new Member__c(
            Id = memberId,
            Root_Segment__c = rootSegmentId,
            Sources__c = JSON.serialize(sourceIds),
            Source_Names__c = JSON.serialize(sourceNames)
        );

        if (null != relatedId) {
            if (Contact.sObjectType == relatedId.getSObjectType()) {
                member.Contact__c = relatedId;
            }
            if (Lead.sObjectType == relatedId.getSObjectType()) {
                member.Lead__c = relatedId;
            }
        }

        return member;
    }

    /**
     * @description Get a CampaignMember that corresponds to this
     * CampaignListMember, appropriate for inserting and adding to a campaign.
     * This will populate the ContactId or LeadId field, as well as the
     * Source_Names__c field of the CampaignMember sObject that is returned.
     *
     * @return CampaignMember
     */
    public CampaignMember getCampaignMember() {
        CampaignMember cm = new CampaignMember();

        if (null != relatedId) {
            if (Contact.sObjectType == relatedId.getSObjectType()) {
                cm.ContactId = relatedId;
            }
            if (Lead.sObjectType == relatedId.getSObjectType()) {
                cm.LeadId = relatedId;
            }
        }

        if (!sourceNames.isEmpty()) {
            cm.Source_Names__c = String.join(
                new List<String>(sourceNames),
                ';'
            );
        }

        return cm;
    }
}