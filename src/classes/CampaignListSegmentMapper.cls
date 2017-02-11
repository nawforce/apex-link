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
 * @description A mapper class for serializing and deserializing
 * CampaignListSegment objects to/from corresponding Segment__c sObjects
 */
public with sharing class CampaignListSegmentMapper implements CampaignList.SegmentMapper {
    /**
     * @description Given a list of report or campaign ids, return a map of names for those ids
     *
     * @param sourceIds a list of report or campaign ids
     * @return Map<Id, String>
     */
    @TestVisible
    private Map<Id, String> getSourceNamesByIds(List<Id> sourceIds) {
        Map<Id, String> names = new Map<Id, String>();

        List<Report> reportNames = [
            SELECT Name
            FROM Report
            WHERE Id IN :sourceIds
        ];

        List<Campaign> campaignNames = [
            SELECT Name
            FROM Campaign
            WHERE Id IN :sourceIds
        ];

        for (Report r : reportNames) {
            names.put(r.Id, r.Name);
        }

        for (Campaign c : campaignNames) {
            names.put(c.Id, c.Name);
        }

        return names;
    }

    /**
     * @description Given the id of the root segment of a campaign list, get the tree of segments that correspond to that campaign list.
     *
     * @param rootSegmentId The "campaign list" id
     * @return CampaignListSegment
     */
    public CampaignList.Segment getSegmentTreeByRootId(Id rootSegmentId) {
        CampaignList.Segment rootSegment;
        Map<Id, CampaignList.Segment> segments = new Map<Id, CampaignList.Segment>();

        for (Segment__c segment : queryByRootSegmentId(rootSegmentId)) {
            segments.put(segment.Id, create(segment));
        }

        for (CampaignList.Segment segment : segments.values()) {
            Id pid = segment.getParentId();

            if (null == pid) {
                rootSegment = segment;
            } else if (segments.containsKey(pid)) {
                segments.get(pid).addChild(segment);
            } else {
                throw new CampaignList.InvalidSegmentSObjectException(String.format(
                    'Unknown parent ({0}) found in segment {1}',
                    new List<String>{
                        pid,
                        segment.getId()
                    }
                ));
            }
        }

        setSourceNamesBySegmentTree(rootSegment);

        return rootSegment;
    }

    /**
     * @description Pluck out the id of the associated source for each source segment in the segment tree
     *
     * @param rootSegment The root segment of the segment tree
     * @return List<Id>
     */
    private List<Id> getSourceIdsFromSegmentTree(CampaignList.Segment rootSegment) {
        Iterator<CampaignList.Segment> it = rootSegment.iterator();

        Set<Id> sourceIds = new Set<Id>();

        while(it.hasNext()) {
            CampaignList.Segment next = it.next();
            if (next instanceof CampaignListSegment.SourceSegment) {
                CampaignListSegment.SourceSegment sourceSegment = (CampaignListSegment.SourceSegment) next;
                sourceIds.add(sourceSegment.getSourceId());
            }
        }

        return new List<Id>(sourceIds);
    }

    /**
     * @description Populate the given segment tree's source nodes with the name of the source they are associated with
     *
     * @param rootSegment The root segment of the segment tree
     * @return void
     */
    private void setSourceNamesBySegmentTree(CampaignList.Segment rootSegment) {
        List<Id> sourceIds = getSourceIdsFromSegmentTree(rootSegment);
        Map<Id, String> sourceNamesById = getSourceNamesByIds(sourceIds);

        Iterator<CampaignList.Segment> it = rootSegment.iterator();

        while (it.hasNext()) {
            CampaignList.Segment next = it.next();
            if (next instanceof CampaignListSegment.SourceSegment) {
                CampaignListSegment.SourceSegment sourceSegment = (CampaignListSegment.SourceSegment) next;
                sourceSegment.setSourceName(sourceNamesById.get(sourceSegment.getSourceId()));
            }
        }
    }

    /**
     * @description Given a "campaign list" id, query for all of the related
     * Segment__c sObjects associated with that campaign list
     *
     * @param rootSegmentId The "campaign list" id
     * @return List<Segment__c>
     */
    private List<Segment__c> queryByRootSegmentId(Id rootSegmentId) {
        return [
            SELECT
                Parent_Segment__c,
                Operation__c,
                Source_ID__c,
                Exclude_Source__c,
                Report_Column_Name__c,
                Root_Segment__c,
                StatusIds__c
            FROM Segment__c
            WHERE Root_Segment__c = :rootSegmentId
            OR Id = :rootSegmentId
        ];
    }

    /**
     * @description Given a Segment__c sObject, create a CampaignListSegment
     * data model instance that corresponds to the given Segment__c sObject.
     * The returned CampaignListSegment will be a specific child class of
     * CampaignListSegment appropriate for the given Segment__c sObject.  If
     * the given Segment__c sObject does not correspond to a known
     * CampaignListSegment type, then an InvalidSegmentSObjectException will be
     * thrown.
     *
     * @param segment The Segment__c sObject (including the Parent_Segment__c, Operation__c, Source_ID__c, Exclude_Source__c, Report_Column_Name__c, and Root_Segment__c fields)
     * @return CampaignListSegment
     */
    @TestVisible
    private CampaignListSegment create(Segment__c segment) {
        Id segmentId = segment.Id;
        Id rootSegmentId = segment.Root_Segment__c;
        Id parentId = segment.Parent_Segment__c;
        Boolean isExclusion = segment.Exclude_Source__c;

        if ('OR' == segment.Operation__c) {
            return new CampaignListSegment.OrSegment(segmentId, rootSegmentId, parentId, isExclusion);
        } else if ('AND' == segment.Operation__c) {
            return new CampaignListSegment.AndSegment(segmentId, rootSegmentId, parentId, isExclusion);
        } else if ('SOURCE' == segment.Operation__c) {
            Id sourceId = (Id) segment.Source_ID__c;
            if (Campaign.sObjectType == sourceId.getSObjectType()) {
                String statusIds = segment.StatusIds__c;
                return new CampaignListSegment.CampaignSourceSegment(segmentId, rootSegmentId, parentId, isExclusion, sourceId, statusIds);
            } else if (Report.sObjectType == sourceId.getSObjectType()) {
                String columnName = segment.Report_Column_Name__c;
                return new CampaignListSegment.ReportSourceSegment(segmentId, rootSegmentId, parentId, isExclusion, sourceId, columnName);
            }
        }
        throw new CampaignList.InvalidSegmentSObjectException('Invalid Segment__c object');
    }

    /**
     * @description Given a segment tree (by root segment), persist that tree
     * to the database by updating existing segments, inserting new ones, and
     * removing segments that are no longer a part of that segment tree.
     *
     * @param rootSegment The root segment of the updated segment tree
     * @return Id (The id of the root segment)
     */
    public Id upsertSegmentTreeByRootSegment(CampaignList.Segment rootSegment) {
        /*
        This method saves a segment tree to the database in three stages:

        First, the segments are saved without any information that relates them
        to each other.

        Second, those saved records are updated with lookups to their parent
        and root segments, using the ids that have been generated in the first
        step.

        Finally, any segments in the database that no longer belong to the
        segment tree are deleted.

        When saving a segment tree where every segment in the tree has
        previously existed in the database, and therefore has an id, then the
        two step update process is not necessary.  However, we have no guarantee
        that every segment in the segment tree has an id.  Some, or all, of the
        segments in the tree may have been created anew, and after inserting
        these new segments, the second step is necessary to update the lookups
        of segments that are children of these new nodes.

        This process will save a segment tree of thousands of segments and
        arbitrary structure, with all previously existing segments, all new
        segments, or a mix of both, in exactly 3 DML statements.
        */

        List<CampaignList.Segment> segments = new List<CampaignList.Segment>();
        List<Segment__c> segmentSObjects = new List<Segment__c>();

        /*
        To prepare to upsert the segment records without any lookups populated,
        we iterate over the segment tree.  For each segment model object, we
        clear that segment's relationship data, add that model object to a list
        for referencing later, and add a Segment__c sObject for each segment
        model object to a list for upserting.

        Clearing the lookup ids from each segment does not cause the actual
        relationship between segments in the tree to be lost, because each
        segment still holds a reference to its children.
        */
        Iterator<CampaignList.Segment> segmentIterator = rootSegment.iterator();
        while(segmentIterator.hasNext()) {
            CampaignList.Segment next = segmentIterator.next();
            next.setParentId(null);
            next.setRootSegmentId(null);
            segments.add(next);
            segmentSObjects.add(next.getSObject());
        }

        upsert segmentSObjects;

        /*
        Now that all of the segments in the segment tree have been saved to the
        database, they all have ids (where they may not have before).  For each
        saved Segment__c sObject, we update the id of the corresponding
        CampaignList.Segment model object.

        Note that setting the id of a segment object will cause the parentId of
        all of it's children to be updated as well.  This uses the structure of
        the segment tree, specifically that each segment holds references to its
        children, to repopulate the relationship data back into each segment.
        */
        for (Integer i = 0; i < segmentSObjects.size(); i++) {
            segments.get(i).setId(segmentSObjects.get(i).Id);
        }

        /*
        Setting the root segment id is a special case, since we only need to set
        it for the root segment model object, and it will be propagated to all
        other segments in the tree recursively.
        */
        Id rootSegmentId = segmentSObjects.get(0).Id;

        rootSegment.setRootSegmentId(rootSegmentId);

        /*
        Now that we've updated our in-memory segment tree with all lookup
        information, we prepare to update the database records with this
        relationship data.  We re-use the segmentSObject list here.
        */
        segmentSObjects.clear();

        /*
        Iterate over the segment tree and for each CampaignList.Segment model,
        add a corresponding Segment__c sObject to a list for updating.  Then,
        update those records in the database.
        */
        segmentIterator = rootSegment.iterator();

        while(segmentIterator.hasNext()) {
            segmentSObjects.add(segmentIterator.next().getSObject());
        }

        upsert segmentSObjects;

        /*
        Now that we've saved our segment tree to the database and updated all of
        the saved Segment__c sObjects with appropriate lookups to their parent
        segments, and their root segment, we need to handle the case where
        existing Segment__c sObjects may no longer belong to the segment tree
        (as represented in our in-memory segment tree).

        Since we know that the relationship data for all of the segments in our
        tree have been saved to the database, and that the segments in our
        in-memory tree are the only ones we want to remain in the database, we
        can use these facts to delete redundant segments.

        The Segment__c sObjects we want to delete are ones that used to be part
        of the segment tree (i.e., they referenced our current root segment as
        their root segment) but are not one of the segments in our in-memory
        tree.

        We select those Segment__c sObjects and then delete them.
        */
        List<Segment__c> redundantSegments = [
            SELECT Id
            FROM Segment__c
            WHERE Root_Segment__c = :rootSegmentId
            AND Id NOT IN :segmentSObjects
        ];

        delete redundantSegments;

        /*
        Finally, we return the id of the root segment of the segment tree to the
        caller.
        */
        return rootSegmentId;
    }

    /**
     * @description Given a JSON serialized version of a segment tree, this
     * method will deserialize it and return a constructed segment tree of
     * CampaignList.Segment objects.  This method returns the root segment of
     * that tree.
     *
     * @param serialized A JSON serialized version of a segment tree
     * @return CampaignList.Segment
     */
    public CampaignList.Segment deserializeSegmentFromJSON(String serialized) {
        SegmentData data = (SegmentData) JSON.deserialize(serialized, SegmentData.class);
        return data.getConcreteInstance();
    }

    /**
     * @author Salesforce.org
     * @group CampaignTools
     * @description This class maps to the JSON serialized structure of a
     * CampaignList.Segment, and is expected to be used during deserialization
     * of that JSON structure.  This class is not intended to be used for any
     * other purpose.  In addition to having properties defined necessary for
     * deserializing JSON objects, it also provides a method that will
     * recursively convert these deserialized objects to real
     * CampaignList.Segment objects.  Expected usage is to use the
     * JSON.deserialize() method to deserialize a JSON segment tree, and then
     * call the getConcreteInstance() of the first deserialized segment
     */
    private class SegmentData {
        public CampaignList.SegmentType segmentType;
        public Id segmentId;
        public Id rootSegmentId;
        public Id parentId;
        public Id sourceId;
        public Boolean isExclusion;
        public String columnName;
        public String sourceName;
        public String statusIds;
        public List<SegmentData> children;

        /**
         * @description This method converts this SegmentData object into the
         * appropriate CampaignListSegment object.  If any children segments
         * exist, those are recursively converted and added as children to the
         * constructed CampaignListSegment object.
         *
         * @return CampaignList.Segment
         */

        public CampaignList.Segment getConcreteInstance() {
            /*
            The data in this class came from a JSON serialized version of a
            segment.  The JSON serialized data will contain a property that
            determines what type of CampaignListSegment that object corresponds
            to.  We use this property to construct the appropriate
            CampaignListSegment object.  If this segment contains any children,
            those children are recursively converted into appropriate
            CampaignListSegment objects and added to the converted
            CampaignListSegment as children.
            */
            CampaignList.Segment segment;
            if (CampaignList.SegmentType.AND_SEGMENT == segmentType) {
                segment = new CampaignListSegment.AndSegment(segmentId, rootSegmentId, parentId, isExclusion);
            } else if (CampaignList.SegmentType.OR_SEGMENT == segmentType) {
                segment = new CampaignListSegment.OrSegment(segmentId, rootSegmentId, parentId, isExclusion);
            } else if (CampaignList.SegmentType.CAMPAIGN_SOURCE_SEGMENT == segmentType) {
                if (null == sourceId) return null;
                segment = new CampaignListSegment.CampaignSourceSegment(segmentId, rootSegmentId, parentId, isExclusion, sourceId, statusIds);
                ((CampaignListSegment.SourceSegment) segment).setSourceName(sourceName);
            } else if (CampaignList.SegmentType.REPORT_SOURCE_SEGMENT == segmentType) {
                if (null == sourceId) return null;
                segment = new CampaignListSegment.ReportSourceSegment(segmentId, rootSegmentId, parentId, isExclusion, sourceId, columnName);
                ((CampaignListSegment.SourceSegment) segment).setSourceName(sourceName);
            }

            if (null != children) {
                for (SegmentData child : children) {
                    CampaignList.Segment childSegment = child.getConcreteInstance();
                    if (null != childSegment) {
                        segment.addChild(childSegment);
                    }
                }
            }

            return segment;
        }
    }
}