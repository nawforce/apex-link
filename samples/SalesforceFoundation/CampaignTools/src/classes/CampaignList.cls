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
 * @description This class holds interface definitions for other objects in the
 * CampaignTools group, as well as some service locator methods.
 */
public abstract with sharing class CampaignList {

    public interface Service {
        Map<String, String> getStatuses(Id campaignId);
        Integer updateCampaignFromCampaignList(Id campaignId, Id rootSegmentId);
        Integer updateCampaignFromCampaignList(Id campaignId, Id rootSegmentId, Boolean acquireLock);
        void updateCampaignStatus(Id campaignId, UpdateStatus status);
        void unlockCampaignAndMarkFailed(Id campaignId, String errorMessage);
        Integer getFlexQueueCapacity(Integer maxFreeSlots);
        Id enqueueJob(Queueable job);
        Id getJobId(Database.BatchableContext bc);
        void abortJob(Id jobId);
    }

    public interface ReportService {
        Map<String, String> getIdColumns(Id reportId);
    }

    public interface Member {
        Id getId();
        Id getRelatedId();
        void addSource(Id sourceId, String sourceName);
        Boolean isFromSource(Id sourceId);
        Boolean meetsCriteria(Segment segment);
        Member__c getSObject();
        CampaignMember getCampaignMember();
    }

    public interface MemberMapper {
        void updateAndDeleteCampaignMembers(Id campaignId, Segment rootSegment, List<CampaignMember> campaignMembers);
        void insertCampaignMembers(Id campaignId, Segment rootSegment, List<Member__c> members);
        void updateByRelatedIdsAndSource(List<Id> relatedIds, Id rootSegmentId, Id sourceId, String sourceName);
    }

    public interface Segment extends Iterable<Segment>{
        Id getId();
        Id getParentId();
        Id getRootSegmentId();
        void setId(Id segmentId);
        void setParentId(Id parentId);
        void setRootSegmentId(Id rootSegmentId);
        Segment__c getSObject();
        void addChild(CampaignList.Segment child);
        List<Segment> getChildren();
        Boolean meetsCriteria(Member member);
    }

    public interface SegmentMapper {
        Segment getSegmentTreeByRootId(Id rootSegmentId);
        Id upsertSegmentTreeByRootSegment(CampaignList.Segment rootSegment);
        Segment deserializeSegmentFromJSON(String serialized);
    }

    public abstract class CustomException extends System.Exception {}
    public abstract class SegmentMapperException extends CustomException {}
    public class InvalidSegmentSObjectException extends SegmentMapperException {}

    /**
     * @description This enum has values that correspond the state of a
     * Campaign as it is being updated from a campaign list.  Processing means
     * that the Campaign is currently being updated.  Completed means that the
     * last time the Campaign was updated from a campaign list, there were no
     * issues.  Failed means that the last time the Campaign was attempted to
     * be updated from a campaign list, there was an error that prevented
     * successful update.
     */
    public enum UpdateStatus {
        Queued,
        Processing,
        Aborted,
        Completed,
        Failed
    }

    public enum SegmentType {
        AND_SEGMENT,
        OR_SEGMENT,
        CAMPAIGN_SOURCE_SEGMENT,
        REPORT_SOURCE_SEGMENT
    }

    private static Service service_x = new CampaignListService();
    private static ReportService reportService_x = (ReportService) Type.forName('ReportService').newInstance();
    private static MemberMapper memberMapper_x = new CampaignListMemberMapper();
    private static SegmentMapper segmentMapper_x = new CampaignListSegmentMapper();
    private static Mutex.Factory mutexFactory_x = new Mutex.DefaultFactory();
    private static BatchableSequence.Factory bsFactory_x = new BatchableSequence.DefaultFactory();

    public static void setService(Service s) {
        service_x = s;
    }

    public static Service getService() {
        return service_x;
    }

    public static void setReportService(ReportService rs) {
        reportService_x = rs;
    }

    public static ReportService getReportService() {
        return reportService_x;
    }

    public static void setMemberMapper(MemberMapper mm) {
        memberMapper_x = mm;
    }

    public static MemberMapper getMemberMapper() {
        return memberMapper_x;
    }

    public static void setSegmentMapper(SegmentMapper sm) {
        segmentMapper_x = sm;
    }

    public static SegmentMapper getSegmentMapper() {
        return segmentMapper_x;
    }

    public static void setMutexFactory(Mutex.Factory mutexFactory) {
        mutexFactory_x = mutexFactory;
    }

    public static Mutex.Factory getMutexFactory() {
        return mutexFactory_x;
    }

    public static void setBatchableSequenceFactory(BatchableSequence.Factory bsFactory) {
        bsFactory_x = bsFactory;
    }

    public static BatchableSequence.Factory getBatchableSequenceFactory() {
        return bsFactory_x;
    }
}