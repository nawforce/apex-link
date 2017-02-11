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
public with sharing class CampaignList_TEST {
    private static Integer nextId = 1;

    public static Id getNextId(Schema.SObjectType sObjectType) {
        String id_x = '' + nextId++;
        while (id_x.length() < 12) {
            id_x = '0' + id_x;
        }
        id_x = sObjectType.getDescribe().getKeyPrefix() + id_x;
        return id_x;
    }

    public virtual class SegmentStub implements CampaignList.Segment {
        public CampaignList.Member meetsCriteraM;
        public Boolean meetsCriteriaReturn;
        public Id getIdReturn;
        public Id setIdSegmentId;
        public Id getParentIdReturn;
        public Id setParentIdParentId;
        public Id getRootSegmentIdReturn;
        public Id setRootSegmentIdRootSegmentId;
        public List<CampaignList.Segment> getChildrenReturn;
        public Iterator<CampaignList.Segment> iteratorReturn;
        public Segment__c getSObjectReturn;
        public CampaignList.Segment addChildChild;
        public virtual Boolean meetsCriteria(CampaignList.Member m) {
            meetsCriteraM = m;
            return this.meetsCriteriaReturn;
        }
        public virtual Id getId() {
            return this.getIdReturn;
        }
        public virtual void setId(Id segmentId) {
            this.setIdSegmentId = segmentId;
        }
        public virtual Id getParentId() {
            return this.getParentIdReturn;
        }
        public virtual void setParentId(Id parentId) {
            this.setParentIdParentId = parentId;
        }
        public virtual Id getRootSegmentId() {
            return this.getRootSegmentIdReturn;
        }
        public virtual void setRootSegmentId(Id rootSegmentId) {
            this.setRootSegmentIdRootSegmentId = rootSegmentId;
        }
        public virtual List<CampaignList.Segment> getChildren() {
            return this.getChildrenReturn;
        }
        public virtual Iterator<CampaignList.Segment> iterator() {
            return this.iteratorReturn;
        }
        public virtual Segment__c getSObject() {
            return this.getSObjectReturn;
        }
        public virtual void addChild(CampaignList.Segment child) {
            this.addChildChild = child;
        }
    }

    public virtual class MemberMapperStub implements CampaignList.MemberMapper {
        public Id updateAndDeleteCampaignMembersCampaignId;
        public CampaignList.Segment updateAndDeleteCampaignMembersRootSegment;
        public List<CampaignMember> updateAndDeleteCampaignMembersCampaignMembers;
        public Exception updateAndDeleteCampaignMembersException;
        public Id insertCampaignMembersCampaignId;
        public CampaignList.Segment insertCampaignMembersRootSegment;
        public List<Member__c> insertCampaignMembersMembers;
        public Exception insertCampaignMembersException;
        public List<Id> updateByRelatedIdsAndSourceRelatedIds;
        public Id updateByRelatedIdsAndSourceRootSegmentId;
        public Id updateByRelatedIdsAndSourceSourceId;
        public String updateByRelatedIdsAndSourceSourceName;
        public Exception updateByRelatedIdsAndSourceException;
        public void updateAndDeleteCampaignMembers(Id campaignId, CampaignList.Segment rootSegment, List<CampaignMember> campaignMembers) {
            this.updateAndDeleteCampaignMembersCampaignId = campaignId;
            this.updateAndDeleteCampaignMembersRootSegment = rootSegment;
            this.updateAndDeleteCampaignMembersCampaignMembers = campaignMembers;
            if (null != this.updateAndDeleteCampaignMembersException) {
                throw this.updateAndDeleteCampaignMembersException;
            }
        }
        public void insertCampaignMembers(Id campaignId, CampaignList.Segment rootSegment, List<Member__c> members) {
            this.insertCampaignMembersCampaignId = campaignId;
            this.insertCampaignMembersRootSegment = rootSegment;
            this.insertCampaignMembersMembers = members;
            if (null != this.insertCampaignMembersException) {
                throw this.insertCampaignMembersException;
            }
        }
        public void updateByRelatedIdsAndSource(List<Id> relatedIds, Id rootSegmentId, Id sourceId, String sourceName) {
            this.updateByRelatedIdsAndSourceRelatedIds = relatedIds;
            this.updateByRelatedIdsAndSourceRootSegmentId = rootSegmentId;
            this.updateByRelatedIdsAndSourceSourceId = sourceId;
            this.updateByRelatedIdsAndSourceSourceName = sourceName;
            if (null != this.updateByRelatedIdsAndSourceException) {
                throw this.updateByRelatedIdsAndSourceException;
            }
        }

    }

    public virtual class SegmentMapperStub implements CampaignList.SegmentMapper {
        public Id getSourceNameByIdSourceId;
        public String getSourceNameByIdReturn;
        public Id getSegmentTreeByRootIdRootSegmentId;
        public CampaignList.Segment getSegmentTreeByRootIdReturn;
        public CampaignList.Segment upsertSegmentTreeByRootSegmentRootSegment;
        public Id upsertSegmentTreeByRootSegmentReturn;
        public String deserializeSegmentFromJSONSerialized;
        public CampaignList.Segment deserializeSegmentFromJSONReturn;
        public virtual String getSourceNameById(Id sourceId) {
            this.getSourceNameByIdSourceId = sourceId;
            return this.getSourceNameByIdReturn;
        }
        public virtual CampaignList.Segment getSegmentTreeByRootId(Id rootSegmentId) {
            this.getSegmentTreeByRootIdRootSegmentId = rootSegmentId;
            return this.getSegmentTreeByRootIdReturn;
        }
        public virtual Id upsertSegmentTreeByRootSegment(CampaignList.Segment rootSegment) {
            this.upsertSegmentTreeByRootSegmentRootSegment = rootSegment;
            return this.upsertSegmentTreeByRootSegmentReturn;
        }
        public virtual CampaignList.Segment deserializeSegmentFromJSON(String serialized) {
            this.deserializeSegmentFromJSONSerialized = serialized;
            return this.deserializeSegmentFromJSONReturn;
        }
    }

    public virtual class ServiceStub implements CampaignList.Service {
        public Id updateCampaignFromCampaignListCampaignId;
        public Id updateCampaignFromCampaignListRootSegmentId;
        public Exception updateCampaignFromCampaignListException;
        public Boolean updateCampaignFromCampaignListAcquireLock;
        public Id updateCampaignStatusCampaignId;
        public CampaignList.UpdateStatus updateCampaignStatusStatus;
        public Id unlockCampaignAndMarkFailedCampaignId;
        public String unlockCampaignAndMarkFailedErrorMessage;
        public Integer getFlexQueueCapacityMaxFreeSlots;
        public Integer getFlexQueueCapacityReturn;
        public Queueable enqueueJobJob;
        public Id enqueueJobReturn;
        public Database.BatchableContext getJobIdBc;
        public Id getJobIdReturn;
        public Id abortJobJobId;
        public Map<String,String> statusesMap;

        public virtual Integer updateCampaignFromCampaignList(Id campaignId, Id rootSegmentId) {
            this.updateCampaignFromCampaignListCampaignId = campaignId;
            this.updateCampaignFromCampaignListRootSegmentId = rootSegmentId;
            if (null != this.updateCampaignFromCampaignListException) {
                throw this.updateCampaignFromCampaignListException;
            }
            return 1;
        }
        public virtual Integer updateCampaignFromCampaignList(Id campaignId, Id rootSegmentId, Boolean acquireLock) {
            this.updateCampaignFromCampaignListCampaignId = campaignId;
            this.updateCampaignFromCampaignListRootSegmentId = rootSegmentId;
            this.updateCampaignFromCampaignListAcquireLock = acquireLock;
            if (null != this.updateCampaignFromCampaignListException) {
                throw this.updateCampaignFromCampaignListException;
            }
            return 1;
        }
        public virtual void updateCampaignStatus(Id campaignId, CampaignList.UpdateStatus status) {
            this.updateCampaignStatusCampaignId = campaignId;
            this.updateCampaignStatusStatus = status;
        }
        public virtual void unlockCampaignAndMarkFailed(Id campaignId, String errorMessage) {
            this.unlockCampaignAndMarkFailedCampaignId = campaignId;
            this.unlockCampaignAndMarkFailedErrorMessage = errorMessage;
        }
        public virtual Integer getFlexQueueCapacity(Integer maxFreeSlots) {
            this.getFlexQueueCapacityMaxFreeSlots = maxFreeSlots;
            return this.getFlexQueueCapacityReturn;
        }
        public virtual Id enqueueJob(Queueable job) {
            this.enqueueJobJob = job;
            return this.enqueueJobReturn;
        }
        public virtual Id getJobId(Database.BatchableContext bc) {
            this.getJobIdBc = bc;
            return this.getJobIdReturn;
        }
        public virtual void abortJob(Id jobId) {
            this.abortJobJobId = jobId;
        }
        public virtual Map<String, String> getStatuses(Id campaignId) {
            return this.statusesMap;
        }
    }

    public virtual class MemberStub implements CampaignList.Member {
        public Id getIdReturn;
        public Id getRelatedIdReturn;
        public Id addSourceSourceId;
        public String addSourceSourceName;
        public Id isFromSourceSourceId;
        public Boolean isFromSourceReturn;
        public CampaignList.Segment meetsCriteriaSegment;
        public Boolean meetsCriteriaReturn;
        public Member__c getSObjectReturn;
        public CampaignMember getCampaignMemberReturn;
        public Id getId() {
            return getIdReturn;
        }
        public Id getRelatedId() {
            return getRelatedIdReturn;
        }
        public void addSource(Id sourceId, String sourceName) {
            addSourceSourceId = sourceId;
            addSourceSourceName = sourceName;
        }
        public Boolean isFromSource(Id sourceId) {
            isFromSourceSourceId = sourceId;
            return isFromSourceReturn;
        }
        public Boolean meetsCriteria(CampaignList.Segment segment) {
            meetsCriteriaSegment = segment;
            return meetsCriteriaReturn;
        }
        public Member__c getSObject() {
            return getSObjectReturn;
        }
        public CampaignMember getCampaignMember() {
            return getCampaignMemberReturn;
        }
    }

    public virtual class MutexStubFactory implements Mutex.Factory {
        public Id createRecordId;
        public Schema.SObjectField createMutexField;
        public Mutex.MutexInterface createReturn;
        public virtual Mutex.MutexInterface create(Id recordId, Schema.SObjectField mutexField) {
            this.createRecordId = recordId;
            this.createMutexField = mutexField;
            return this.createReturn;
        }
    }

    public virtual class MutexStub implements Mutex.MutexInterface {
        public Integer acquireLockCalls = 0;
        public Integer releaseLockCalls = 0;
        public Integer getLockTimeCalls = 0;
        public Boolean acquireLock;
        public Boolean releaseLock;
        public DateTime lockTime;
        public virtual Boolean acquireLock() {
            acquireLockCalls++;
            return acquireLock;
        }
        public virtual Boolean releaseLock() {
            releaseLockCalls++;
            return releaseLock;
        }
        public virtual DateTime getLockTime() {
            getLockTimeCalls++;
            return lockTime;
        }
    }

    public class TestCampaign {
        public Campaign campaign;
        public List<Contact> contacts;
        public List<Lead> leads;
        public List<CampaignMember> campaignMembers;

        public TestCampaign(String name, Integer numberOfContacts, Integer numberOfLeads, String memberStatus) {
            campaign = new Campaign(Name = name);
            insert campaign;

            contacts = new List<Contact>();
            leads = new List<Lead>();

            for (Integer i = 0; i < numberOfContacts; i++) {
                contacts.add(new Contact(
                    LastName = 'Test' + i
                ));
            }

            insert contacts;

            for (Integer i = 0; i < numberOfLeads; i++) {
                leads.add(new Lead(
                    LastName = 'Test' + i,
                    Company = 'Test Company'
                ));
            }

            insert leads;

            campaignMembers = new List<CampaignMember>();

            for (Contact c : contacts) {
                campaignMembers.add(new CampaignMember(
                    CampaignId = campaign.Id,
                    ContactId = c.Id,
                    Status = memberStatus
                ));
            }

            for (Lead l : leads) {
                campaignMembers.add(new CampaignMember(
                    CampaignId = campaign.Id,
                    LeadId = l.Id,
                    Status = memberStatus
                ));
            }

            insert campaignMembers;
        }
    }

    public class MockException extends Exception {}
}