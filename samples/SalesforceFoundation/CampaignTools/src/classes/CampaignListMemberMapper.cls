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
 * @description A mapper class for CampaignListMember objects
 */
public virtual with sharing class CampaignListMemberMapper implements CampaignList.MemberMapper {
    /**
     * @description Given a list of related record ids, i.e. Contact or Lead
     * record ids, a campaign list id, and the id and name of a source, i.e. a
     * Report or Campaign, create Member__c sObjects that correspond to each
     * related record id.  If the related record already has a corresponding
     * Member__c record, that record will be updated to show that the related
     * record was also found in the given source.  If no existing Member__c
     * sObject exists, then one will be created.
     *
     * @param relatedIds A list of related record ids, i.e. Contact or Lead ids
     * @param rootSegmentId The "campaign list" id being operated on
     * @param sourceId The id of the source Report or Campaign
     * @param sourceName The name of the source Report or Campaign
     * @return void
     */
    public void updateByRelatedIdsAndSource(List<Id> relatedIds, Id rootSegmentId, Id sourceId, String sourceName) {
        Map<Id, CampaignList.Member> members = this.getByRelatedIds(relatedIds, rootSegmentId);

        for (Id id_x : relatedIds) {
            if (!members.containsKey(id_x)) {
                members.put(id_x, new CampaignListMember(id_x, rootSegmentId));
            }
            members.get(id_x).addSource(sourceId, sourceName);
        }

        this.upsertMembers(members.values());
    }

    /**
     * @description Given a list of temporary Member__c objects, if any of
     * these temporary member records need to have a corresponding
     * CampaignMember created for the given campaignId, then insert the
     * corresponding CampaignMember.  Delete the temporary member records that
     * have been processed.
     *
     * @param campaignId The id of the campaign to create CampaignMembers for
     * @param rootSegment The CampaignList.Segment object to use when determining eligibility for temporary member records
     * @param members The list of temporary member records to create CampaignMember records from (if any need to be created)
     */
    public void insertCampaignMembers(Id campaignId, CampaignList.Segment rootSegment, List<Member__c> members) {
        Set<Id> relatedIds = getRelatedIds(members);

        List<CampaignMember> existingMembers = queryCampaignMembersByRelatedIds(
            campaignId,
            relatedIds
        );

        Map<Id, Id> campaignMemberIdsByRelatedIds = getCampaignMemberIdsByRelatedIds(
            existingMembers
        );

        List<CampaignMember> campaignMembersToInsert = new List<CampaignMember>();
        List<Member__c> membersToDelete = new List<Member__c>();

        for (Member__c m : members) {
            CampaignList.Member member = new CampaignListMember(m);
            if (!campaignMemberIdsByRelatedIds.containsKey(member.getRelatedId())) {
                if (member.meetsCriteria(rootSegment)) {
                    CampaignMember cm = member.getCampaignMember();
                    cm.CampaignId = campaignId;
                    campaignMembersToInsert.add(cm);
                }
                membersToDelete.add(m);
            }
        }

        insert campaignMembersToInsert;
        if (membersToDelete.size() > 0) {
            delete membersToDelete;
            Database.emptyRecycleBin(membersToDelete);
        }
    }

    /**
     * @description Given a list of CampaignMembers, determine if any of these
     * records have a corresponding temporary member record that meets the
     * criteria of the given rootSegment, and update or delete the
     * campaignMember as necessary.  Delete the temporary member records that
     * have been processed.
     *
     * @param campaignId The id of the Campaign the CampaignMembers belong to
     * @param rootSegment The root segment to use when determining whether temporary member records meet criteria
     * @param campaignMembers The list of CampaignMember objects to maybe update or delete
     */
    public void updateAndDeleteCampaignMembers(Id campaignId, CampaignList.Segment rootSegment, List<CampaignMember> campaignMembers) {
        Map<Id, CampaignList.Member> relatedMembers = getByRelatedIds(
            new List<Id>(
                getCampaignMemberIdsByRelatedIds(campaignMembers).keySet()
            ),
            rootSegment.getId()
        );

        List<CampaignMember> campaignMembersToUpdate = new List<CampaignMember>();
        List<CampaignMember> campaignMembersToDelete = new List<CampaignMember>();

        for (CampaignMember cm : campaignMembers) {
            Id relatedId = getCampaignMemberRelatedId(cm);
            if (relatedMembers.containsKey(relatedId)) {
                CampaignList.Member relatedMember = relatedMembers.get(
                    relatedId
                );
                if (relatedMember.meetsCriteria(rootSegment)) {
                    CampaignMember relatedCampaignMember = relatedMember.getCampaignMember();
                    relatedCampaignMember.Id = cm.Id;
                    campaignMembersToUpdate.add(relatedCampaignMember);
                } else {
                    campaignMembersToDelete.add(cm);
                }
            } else {
                campaignMembersToDelete.add(cm);
            }
        }

        update campaignMembersToUpdate;
        delete campaignMembersToDelete;
        deleteMembers(relatedMembers.values());
    }

    /**
     * @description Extract a set of related Contact/Lead ids from the given list of Member__c objects
     *
     * @param members A list of Member__c objects from which to extract Contact/Lead ids
     * @return Set<Id>
     */
    @TestVisible
    private virtual Set<Id> getRelatedIds(List<Member__c> members) {
        Set<Id> relatedIds = new Set<Id>();

        for (Member__c m : members) {
            CampaignList.Member member = new CampaignListMember(m);
            relatedIds.add(member.getRelatedId());
        }

        return relatedIds;
    }

    /**
     * @description Query for CampaignMembers where the related Contact id or Lead id is in the given set of ids
     *
     * @param campaignId The id of the Campaign the CampaignMembers belong to
     * @param relatedIds The ids of the related Contact or Leads to use when finding CampaignMembers
     * @return List<CampaignMember>
     */
    @TestVisible
    private virtual List<CampaignMember> queryCampaignMembersByRelatedIds(Id campaignId, Set<Id> relatedIds) {
        return [
            SELECT
                CampaignId,
                ContactId,
                LeadId
            FROM CampaignMember
            WHERE CampaignId = :campaignId
            AND (
                ContactId IN :relatedIds
                OR LeadId IN :relatedIds
            )
        ];
    }

    /**
     * @description Get a map of related Contact or Lead id to the CampaignMember id that it comes from
     *
     * @param campaignMembers A list of campaign members to extract the related ids from
     * @return Map<Id, Id>
     */
    @TestVisible
    private virtual Map<Id, Id> getCampaignMemberIdsByRelatedIds(List<CampaignMember> campaignMembers) {
        Map<Id, Id> existingCampaignMembersByRelatedId = new Map<Id, Id>();

        for (CampaignMember cm : campaignMembers) {
            Id relatedId = getCampaignMemberRelatedId(cm);
            if (null != relatedId) {
                existingCampaignMembersByRelatedId.put(relatedId, cm.Id);
            }
        }

        return existingCampaignMembersByRelatedId;
    }


    /**
     * @description Given a CampaignMember record, extract and return its related id (i.e. the related ContactId or LeadId for the CampaignMember)
     *
     * @param cm The CampaignMember to extract the related id from
     * @return Id The ContactId or LeadId from the CampaignMember
     */
    @TestVisible
    private virtual Id getCampaignMemberRelatedId(CampaignMember cm) {
        if (null != cm.ContactId) {
            return cm.ContactId;
        } else if (null != cm.LeadId) {
            return cm.LeadId;
        } else {
            return null;
        }
    }

    /**
     * @description Given a list of related record ids (i.e., Contact or Lead
     * ids), build a collection of CampaignListMember objects that correspond
     * to those related ids. If a related id corresponds to a Member__c
     * sObject, then a CampaignListMember will be constructed from that
     * Member__c object (including deserializing the existing sources that
     * member was already found in).  Otherwise, no CampaignListMember object
     * for that related record will be included in the collection.  The
     * returned collection is keyed by the CampaignListMember's related record
     * id, to allow lookups into this collection by related record id.
     *
     * @param ids A list of Contact or Lead ids
     * @param rootSegmentId The "campaign list" id being operated on
     * @return Map<Id, CampaignListMember>
     */
    private Map<Id, CampaignList.Member> getByRelatedIds(List<Id> ids, Id rootSegmentId) {
        List<Member__c> members = [
            SELECT
                Contact__c,
                Lead__c,
                Root_Segment__c,
                Source_Names__c,
                Sources__c
            FROM Member__c
            WHERE Root_Segment__c = :rootSegmentId
            AND (
                Contact__c IN :ids
                OR Lead__c IN :ids
            )
        ];

        Map<Id, CampaignList.Member> memberMap = new Map<Id, CampaignList.Member>();

        for (Member__c memberSObject : members) {
            CampaignList.Member m = new CampaignListMember(memberSObject);
            memberMap.put(m.getRelatedId(), m);
        }

        return memberMap;
    }

    /**
     * @description Given a list of CampaignListMember objects, delete these
     * objects from the database.
     *
     * @param member The member objects to delete
     */
    private void deleteMembers(List<CampaignList.Member> members) {
        if (members.size() > 0) {
            List<Member__c> memberObjects = getMemberSObjects(members);
            delete memberObjects;
            Database.emptyRecycleBin(memberObjects);
        }
    }

    /**
     * @description Given a list of CampaignListMember objects, persist these
     * objects to the database as Member__c sObjects.
     *
     * @param members
     * @return void
     */
    private void upsertMembers(List<CampaignList.Member> members) {
        upsert getMemberSObjects(members);
    }

    /**
     * @description Given a list of CampaignListMember objects, deserialize
     * these objects into a list of corresponding Member__c sObjects
     *
     * @param members
     * @return List<Member__c>
     */
    private List<Member__c> getMemberSObjects(List<CampaignList.Member> members) {
        List<Member__c> memberSObjects = new List<Member__c>();

        for (CampaignList.Member member : members) {
            memberSObjects.add(member.getSObject());
        }

        return memberSObjects;
    }
}