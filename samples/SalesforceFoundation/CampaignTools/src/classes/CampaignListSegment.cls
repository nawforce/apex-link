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
 * @description A model class that represents a single "segment" of a campaign
 * list.  A "segment" of a campaign list is one part of the criteria that
 * defines the campaign list.
 */
public abstract with sharing class CampaignListSegment implements CampaignList.Segment {
    /** @description This segment's id */
    private Id segmentId;

    /** @description The id of the "campaign list" this segment belongs to */
    private Id rootSegmentId;

    /**
     * @description The immediate parent of this segment in the segment tree
     */
    private Id parentId;

    /**
     * @description True if this source is intended to be used to exclude
     * members from the campaign list.  False, otherwise.
     */
    private Boolean isExclusion;

    /**
     * @description This segment's children segments, if it has any
     */
    private List<CampaignList.Segment> children = new List<CampaignList.Segment>();

    /**
     * @description Construct a CampaignListSegment
     * @param segmentId The id of this segment
     * @param rootSegmentId The id of the "campaign list" this segment belongs to
     * @param parentId The immediate parent of this segment
     */
    public CampaignListSegment(Id segmentId, Id rootSegmentId, Id parentId, Boolean isExclusion) {
        this.segmentId = segmentId;
        this.rootSegmentId = rootSegmentId;
        this.parentId = parentId;
        this.isExclusion = isExclusion;
    }

    /**
     * @description Return a Segment__c sObject that represents this CampaignListSegment
     *
     * @return Segment__c
     */
    public virtual Segment__c getSObject() {
        Segment__c s = new Segment__c(
            Id = segmentId,
            Parent_Segment__c = parentId,
            Root_Segment__c = rootSegmentId
        );
        if (null != isExclusion) {
            s.Exclude_Source__c = isExclusion;
        }
        return s;
    }

    /**
     * @description get this segment's id
     * @return Id
     */
    public Id getId() {
        return segmentId;
    }

    public void setId(Id segmentId) {
        this.segmentId = segmentId;
        for (CampaignList.Segment child : children) {
            child.setParentId(segmentId);
        }
    }

    /**
     * @description get "campaign list" id this segment belongs to
     * @return Id
     */
    public Id getCampaignListId() {
        if (null == rootSegmentId) {
            return segmentId;
        } else {
            return rootSegmentId;
        }
    }

    /**
     * @description get root segment id this segment belongs to
     * @return Id
     */
    public Id getRootSegmentId() {
        return rootSegmentId;
    }

    public void setRootSegmentId(Id rootSegmentId) {
        if (rootSegmentId != segmentId) {
            this.rootSegmentId = rootSegmentId;
        }
        for (CampaignList.Segment child : children) {
            child.setRootSegmentId(rootSegmentId);
        }
    }

    /**
     * @description get this segment's parent id
     * @return Id
     */
    public Id getParentId() {
        return parentId;
    }

    public void setParentId(Id parentId) {
        if (parentId != segmentId) {
            this.parentId = parentId;
        }
    }

    /**
     * @description Is this segment intended to exclude members or negate
     * child segments?
     *
     * @return Boolean
     */
    public Boolean isExclusion() {
        return isExclusion;
    }

    /**
     * @description Add a child segment to this segment
     * @param term The segment to add as a child.
     * @return void
     */
    public void addChild(CampaignList.Segment child) {
        if (null != segmentId) {
            child.setParentId(segmentId);
        }
        if (null != rootSegmentId) {
            child.setRootSegmentId(rootSegmentId);
        }
        children.add(child);
    }

    public List<CampaignList.Segment> getChildren() {
        return children;
    }

    /**
     * @description Determine whether the given CampaignListMember meets
     * the criteria of *this segment*.  If the segment is a grouping node, i.e.
     * 'And' and 'Or' segments, then this will evaluate the criteria
     * recursively for each term.
     *
     * @return Boolean (true if the CampaignListMember is eligible by the criteria of the criteria sub-tree represented by this segment.
     */
    public abstract Boolean meetsCriteria(CampaignList.Member m);

    public TreeIterator iterator() {
        return new TreeIterator(this);
    }

    public with sharing class TreeIterator implements Iterator<CampaignList.Segment> {
        private List<CampaignList.Segment> nextSegments = new List<CampaignList.Segment>();

        public TreeIterator(CampaignList.Segment rootSegment) {
            nextSegments.add(rootSegment);
        }

        public Boolean hasNext() {
            return !nextSegments.isEmpty();
        }

        public CampaignList.Segment next() {
            CampaignList.Segment next = nextSegments.remove(nextSegments.size() - 1);
            nextSegments.addAll(next.getChildren());
            return next;
        }
    }

    /**
     * @author Salesforce.org
     * @group CampaignTools
     * @description This is a specific type of CampaignListSegment that
     * represents an 'AND' segment
     */
    public with sharing class AndSegment extends CampaignListSegment {
        private final CampaignList.SegmentType segmentType = CampaignList.SegmentType.AND_SEGMENT;

        /**
         * @description Construct an AndSegment
         * @param segmentId The id of this segment
         * @param rootSegmentId The id of the "campaign list" this segment belongs to
         * @param parentId The immediate parent of this segment
         */
        public AndSegment(Id segmentId, Id rootSegmentId, Id parentId, Boolean isExclusion) {
            super(segmentId, rootSegmentId, parentId, isExclusion);
        }

        /**
         * @description Determine whether the given CampaignListMember meets
         * the criteria of all of the terms within this AND grouping of
         * segments.  A CampaignListSegment can only return a valid result if
         * it has at least one valid child.  If this CampaignListSegment is
         * not valid, then this method will return null.
         *
         * @param m The CampaignListMember to determine whether eligible for inclusion in a campaign list
         * @return Boolean (true if the given CampaignListMember is eligible by the criteria of this segment, false if it isn't eligible, or null if the segment is not valid)
         */
        public override Boolean meetsCriteria(CampaignList.Member m) {
            /*
             Since this node can only return a valid result if it has at
             least one valid child, we will keep track of the number of
             valid children we encounter while iterating over this node's
             children (if any).  As we ask each child node whether the given
             member meets its criteria, we check if the child node returns a
             valid result (i.e., not null).  If the child node returns null,
             indicating an invalid child node, we skip that child node in
             the iteration and do not increment the valid child node
             counter.  If we did get a valid result (i.e. a boolean) from
             the child node, we use that result to potentially invalidate
             our assumption that this node should return true (since a valid
             'and' node is false if at least one of its children is false,
             otherwise true).  Since the child node returned a valid result,
             we increment the valid child count.  Finally, after we've
             iterated over all of the child nodes, we return null if this
             node did not have at least one valid child, otherwise we
             return the accumulated result of this node, negating it if
             this node is negated (i.e. if isExclusion is true).
            */
            Boolean meetsCriteria = true;
            Integer numberOfValidChildren = 0;

            List<Boolean> childCriteria = new List<Boolean>();
            for (CampaignList.Segment child : children) {
                Boolean childMeetsCriteria = child.meetsCriteria(m);
                if (null == childMeetsCriteria) continue;
                meetsCriteria &= childMeetsCriteria;
                numberOfValidChildren++;
                if (!meetsCriteria) break;
            }

            if (numberOfValidChildren < 1) {
                return null;
            }

            return isExclusion ^ meetsCriteria;
        }

        /**
         * @description Return a Segment__c sObject that represents this CampaignListSegment
         *
         * @return Segment__c
         */
        public override Segment__c getSObject() {
            Segment__c s = super.getSObject();
            s.Operation__c = 'AND';
            return s;
        }
    }

    /**
     * @author Salesforce.org
     * @group CampaignTools
     * @description This is a specific type of CampaignListSegment that
     * represents an 'OR' segment
     */
    public with sharing class OrSegment extends CampaignListSegment {
        private final CampaignList.SegmentType segmentType = CampaignList.SegmentType.OR_SEGMENT;

        /**
         * @description Construct an OrSegment
         * @param segmentId The id of this segment
         * @param rootSegmentId The id of the "campaign list" this segment belongs to
         * @param parentId The immediate parent of this segment
         */
        public OrSegment(Id segmentId, Id rootSegmentId, Id parentId, Boolean isExclusion) {
            super(segmentId, rootSegmentId, parentId, isExclusion);
        }

        /**
         * @description Determine whether the given CampaignListMember meets
         * the criteria of all of the terms within this OR grouping of
         * segments.  A CampaignListSegment can only return a valid result if
         * it has at least one valid child.  If this CampaignListSegment is
         * not valid, then this method will return null.
         *
         * @param m The CampaignListMember to determine whether eligible for inclusion in a campaign list
         * @return Boolean (true if the given CampaignListMember is eligible by the criteria of this segment, false if it isn't eligible, or null if the segment is not valid)
         */
        public override Boolean meetsCriteria(CampaignList.Member m) {
            /*
             Since this node can only return a valid result if it has at
             least one valid child, we will keep track of the number of
             valid children we encounter while iterating over this node's
             children (if any).  As we ask each child node whether the given
             member meets its criteria, we check if the child node returns a
             valid result (i.e., not null).  If the child node returns null,
             indicating an invalid child node, we skip that child node in
             the iteration and do not increment the valid child node
             counter.  If we did get a valid result (i.e. a boolean) from
             the child node, we use that result to potentially invalidate
             our assumption that this node should return false (since a valid
             'or' node is true if at least one of its children is true,
             otherwise false).  Since the child node returned a valid result,
             we increment the valid child count.  Finally, after we've
             iterated over all of the child nodes, we return null if this
             node did not have at least one valid child, otherwise we
             return the accumulated result of this node, negating it if
             this node is negated (i.e. if isExclusion is true).
            */
            Boolean meetsCriteria = false;
            Integer numberOfValidChildren = 0;

            List<Boolean> childCriteria = new List<Boolean>();
            for (CampaignList.Segment child : children) {
                Boolean childMeetsCriteria = child.meetsCriteria(m);
                if (null == childMeetsCriteria) continue;
                meetsCriteria |= childMeetsCriteria;
                numberOfValidChildren++;
                if (meetsCriteria) break;
            }

            if (numberOfValidChildren < 1) {
                return null;
            }

            return isExclusion ^ meetsCriteria;
        }

        /**
         * @description Return a Segment__c sObject that represents this CampaignListSegment
         *
         * @return Segment__c
         */
        public override Segment__c getSObject() {
            Segment__c s = super.getSObject();
            s.Operation__c = 'OR';
            return s;
        }
    }

    /**
     * @author Salesforce.org
     * @group CampaignTools
     * @description This is a specific type of CampaignListSegment that
     * represents a 'SOURCE' segment
     */
    public abstract with sharing class SourceSegment extends CampaignListSegment {
        /**
         * @description The id of the source this segment corresponds to, i.e.
         * a Report id or a Campaign id, etc.
         */
        private Id sourceId;

        /**
         * @description The name associated with this source
         */
        private String sourceName;

        /**
         * @description Construct a SourceSegment
         * @param segmentId The id of this segment
         * @param rootSegmentId The id of the "campaign list" this segment belongs to
         * @param parentId The immediate parent of this segment
         */
        public SourceSegment(Id segmentId, Id rootSegmentId, Id parentId, Boolean isExclusion, Id sourceId) {
            super(segmentId, rootSegmentId, parentId, isExclusion);
            this.sourceId = sourceId;
        }

        /**
         * @description get the id of the source this segment corresponds to
         * @return Id
         */
        public Id getSourceId() {
            return sourceId;
        }

        /**
         * @description Determine whether the given CampaignListMember is
         * eligible for inclusion in a campaign list based on whether the
         * CampaignListMember was found in this segment's source.
         *
         * @param m The CampaignListMember to test for eligibility
         * @return Boolean (True if the CampaignListMember was found in this segment's source and this is not an exclusion source, or if the CampaignListMember was *not* found in this segment's source and this is an exclusion source. False, otherwise)
         */
        public override Boolean meetsCriteria(CampaignList.Member m) {
            if (isExclusion) {
                return !m.isFromSource(sourceId);
            } else {
                return m.isFromSource(sourceId);
            }
        }

        /**
         * @description Get the name of this source.
         *
         * @return String
         */
        public String getSourceName() {
                return sourceName;
        }

        /**
         * @description Set the name of the source this segment is associated with
         *
         * @param sourceName The name of the source
         * @return void
         */
        public void setSourceName(String sourceName) {
           this.sourceName = sourceName;
        }

        /**
         * @description Return a Segment__c sObject that represents this CampaignListSegment
         *
         * @return Segment__c
         */
        public virtual override Segment__c getSObject() {
            Segment__c s = super.getSObject();
            s.Operation__c = 'SOURCE';
            s.Source_ID__c = sourceId;
            return s;
        }

        /**
         * @description Get an appropriate BatchableSequence.Batch class that
         * can be executed to retrieve CampaignListMembers from this source and
         * persist those members to the database
         *
         * @param campaignId The id of the campaign to be refreshed
         * @return BatchableSequence.Batch
         */
        public abstract BatchableSequence.Batch getBatchProcessor(Id campaignId);
    }

    /**
     * @author Salesforce.org
     * @group CampaignTools
     * @description This is a specific type of SourceSegment that represents a
     * Campaign 'SOURCE' segment
     */
    public with sharing class CampaignSourceSegment extends SourceSegment {
        private final CampaignList.SegmentType segmentType = CampaignList.SegmentType.CAMPAIGN_SOURCE_SEGMENT;

        /**
         * @description the member status Ids of the campaign member to retrieve members
         * from the campaign
         */
        private String statusIds;

        /**
         * @description Construct a CampaignSourceSegment
         * @param segmentId The id of this segment
         * @param rootSegmentId The id of the "campaign list" this segment belongs to
         * @param parentId The immediate parent of this segment
         * @param sourceId The id of the source Campaign
         * @param isExclusion True if this source is intended to exclude members that are found in the given campaign.  False, otherwise.
         * @param statusIds the member status Ids of the campaign member to use to retrieve members from the campaign
         */
        public CampaignSourceSegment(Id segmentId, Id rootSegmentId, Id parentId, Boolean isExclusion, Id sourceId, String statusIds) {
            super(segmentId, rootSegmentId, parentId, isExclusion, sourceId);
            this.statusIds = statusIds;
        }

        /**
         * @description Get the status of the campaign member that this source
         * segment is intending to use to retrieve members from the campaign
         *
         * @return String
         */
        public String getStatusIds() {
           return statusIds;
        }

        /**
         * @description Get an appropriate BatchableSequence.Batch class that
         * can be executed to retrieve CampaignListMembers from this source and
         * persist those members to the database.  In this case, it will return
         * a BatchableSequence.Batch class appropriate for retrieving
         * CampaignListMembers from an existing Campaign.
         *
         * @param campaignId The id of the campaign to be refreshed
         * @return CampaignListFromCampaignBatch
         */
        public override BatchableSequence.Batch getBatchProcessor(Id campaignId) {
            CampaignListFromCampaignBatch batch = new CampaignListFromCampaignBatch(
                getCampaignListId(),
                campaignId,
                sourceId,
                'Campaign: ' + getSourceName(),
                statusIds
            );

            batch.setScope(10000);

            return batch;
        }

        /**
         * @description Return a Segment__c sObject that represents this CampaignListSegment
         *
         * @return Segment__c
         */
        public override Segment__c getSObject() {
            Segment__c s = super.getSObject();
            s.StatusIds__c = statusIds;
            return s;
        }
    }

    /**
     * @author Salesforce.org
     * @group CampaignTools
     * @description This is a specific type of SourceSegment that represents a
     * Report 'SOURCE' segment
     */
    public with sharing class ReportSourceSegment extends SourceSegment {
        private final CampaignList.SegmentType segmentType = CampaignList.SegmentType.REPORT_SOURCE_SEGMENT;

        /**
         * @description the name of the column in the source report that holds
         * the id of the related Contact or Lead
         */
        private String columnName;

        /**
         * @description Construct a ReportSourceSegment
         * @param segmentId The id of this segment
         * @param rootSegmentId The id of the "campaign list" this segment belongs to
         * @param parentId The immediate parent of this segment
         * @param sourceId The id of the source Report
         * @param isExclusion True if this source is intended to exclude members that are found in the given report.  False, otherwise.
         * @param columnName the name of the column in the source report that holds the id of the related Contact or Lead
         */
        public ReportSourceSegment(Id segmentId, Id rootSegmentId, Id parentId, Boolean isExclusion, Id sourceId, String columnName) {
            super(segmentId, rootSegmentId, parentId, isExclusion, sourceId);
            this.columnName = columnName;
        }

        /**
         * @description Get the column name of the report that this source
         * segment is intending to use to retrieve values from the report
         *
         * @return String
         */
        public String getColumnName() {
           return columnName;
        }

        /**
         * @description Get an appropriate BatchableSequence.Batch class that
         * can be executed to retrieve CampaignListMembers from this source and
         * persist those members to the database.  In this case, it will return
         * a BatchableSequence.Batch class appropriate for retrieving
         * CampaignListMembers from a Report.
         *
         * @param campaignId the id of the Campaign to be refreshed
         * @return CampaignListFromReportBatch
         */
        public override BatchableSequence.Batch getBatchProcessor(Id campaignId) {
            CampaignListFromReportBatch batch = new CampaignListFromReportBatch(
                getCampaignListId(),
                campaignId,
                sourceId,
                columnName,
                'Report: ' + getSourceName()
            );

            batch.setScope(2000);

            return batch;
        }

        /**
         * @description Return a Segment__c sObject that represents this CampaignListSegment
         *
         * @return Segment__c
         */
        public override Segment__c getSObject() {
            Segment__c s = super.getSObject();
            s.Report_Column_Name__c = columnName;
            return s;
        }

    }
}