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
private with sharing class CampaignListSegmentMapper_TEST {
    @isTest(SeeAllData=True)
    private static void testGetSourceNamesByIds() {
        Campaign testCampaign = new Campaign(Name='Test Campaign');
        insert testCampaign;

        Report testReport = [
            SELECT Name
            FROM Report
            WHERE DeveloperName = 'NPSP_Test_Contact_Report'
            LIMIT 1
        ];

        List<Id> sourceIds = new List<Id>{
            testCampaign.Id,
            testReport.Id
        };

        CampaignListSegmentMapper segmentMapper = new CampaignListSegmentMapper();

        Map<Id, String> sourceNames = segmentMapper.getSourceNamesByIds(sourceIds);

        System.assert(sourceNames.containsKey(testCampaign.Id));
        System.assertEquals('Test Campaign', sourceNames.get(testCampaign.Id));
        System.assert(sourceNames.containsKey(testReport.Id));
        System.assertEquals('NPSP Test Contact Report', sourceNames.get(testReport.Id));
    }

    private static testMethod void testGetByRootSegmentId() {
        Segment__c orSegment = new Segment__c(
            Operation__c = 'OR'
        );
        insert orSegment;

        Id rootSegmentId = orSegment.Id;

        Segment__c andSegment = new Segment__c(
            Operation__c = 'AND',
            Parent_Segment__c = orSegment.Id,
            Root_Segment__c = rootSegmentId
        );
        insert andSegment;

        Id campaignSourceId = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Segment__c campaignSegment = new Segment__c(
            Operation__c = 'SOURCE',
            Source_ID__c = campaignSourceId,
            Exclude_Source__c = true,
            Parent_Segment__c = orSegment.Id,
            Root_Segment__c = rootSegmentId
        );
        insert campaignSegment;

        Id reportSourceId = CampaignList_TEST.getNextId(Report.sObjectType);
        Segment__c reportSegment = new Segment__c(
            Operation__c = 'SOURCE',
            Source_ID__c = reportSourceId,
            Exclude_Source__c = false,
            Report_Column_Name__c = 'test_column_name',
            Parent_Segment__c = andSegment.Id,
            Root_Segment__c = rootSegmentId
        );
        insert reportSegment;

        CampaignListSegmentMapper segmentMapper = new CampaignListSegmentMapper();

        CampaignList.Segment segmentTree = segmentMapper.getSegmentTreeByRootId(
            rootSegmentId
        );

        Map<Id, CampaignList.Segment> segmentModelsById = new Map<Id, CampaignList.Segment>();

        Iterator<CampaignList.Segment> segmentTreeIterator = segmentTree.iterator();

        while (segmentTreeIterator.hasNext()) {
            CampaignList.Segment next = segmentTreeIterator.next();
            segmentModelsById.put(next.getId(), next);
        }

        System.assertEquals(4, segmentModelsById.size());

        CampaignListSegment.OrSegment orSegmentModel = (CampaignListSegment.OrSegment) segmentModelsById.get(orSegment.Id);
        System.assertEquals(null, orSegmentModel.getRootSegmentId());
        System.assertEquals(null, orSegmentModel.getParentId());

        CampaignListSegment.AndSegment andSegmentModel = (CampaignListSegment.AndSegment) segmentModelsById.get(andSegment.Id);
        System.assertEquals(rootSegmentId, andSegmentModel.getRootSegmentId());
        System.assertEquals(orSegment.Id, andSegmentModel.getParentId());

        CampaignListSegment.CampaignSourceSegment campaignSegmentModel = (CampaignListSegment.CampaignSourceSegment) segmentModelsById.get(campaignSegment.Id);

        System.assertEquals(rootSegmentId, campaignSegmentModel.getRootSegmentId());
        System.assertEquals(orSegment.Id, campaignSegmentModel.getParentId());
        System.assertEquals(campaignSourceId, campaignSegmentModel.getSourceId());
        System.assertEquals(true, campaignSegmentModel.isExclusion());

        CampaignListSegment.ReportSourceSegment reportSegmentModel = (CampaignListSegment.ReportSourceSegment) segmentModelsById.get(reportSegment.Id);
        System.assertEquals(rootSegmentId, reportSegmentModel.getRootSegmentId());
        System.assertEquals(andSegment.Id, reportSegmentModel.getParentId());
        System.assertEquals(reportSourceId, reportSegmentModel.getSourceId());
        System.assertEquals(false, reportSegmentModel.isExclusion());
        System.assertEquals('test_column_name', reportSegmentModel.getColumnName());
    }

    private static testMethod void testGetSegmentTreeDetectsSegmentWithInvalidParent() {
        Segment__c parentSegment1 = new Segment__c(
            Operation__c = 'OR'
        );
        insert parentSegment1;

        Segment__c parentSegment2 = new Segment__c(
            Operation__c = 'AND'
        );
        insert parentSegment2;

        Id rootSegmentId = parentSegment1.Id;

        Id campaignSourceId = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Segment__c campaignSegment = new Segment__c(
            Operation__c = 'SOURCE',
            Source_ID__c = campaignSourceId,
            Exclude_Source__c = true,
            Parent_Segment__c = parentSegment1.Id,
            Root_Segment__c = rootSegmentId
        );
        insert campaignSegment;

        Id reportSourceId = CampaignList_TEST.getNextId(Report.sObjectType);
        Segment__c reportSegment = new Segment__c(
            Operation__c = 'SOURCE',
            Source_ID__c = reportSourceId,
            Exclude_Source__c = false,
            Report_Column_Name__c = 'test_column_name',
            Parent_Segment__c = parentSegment2.Id,
            Root_Segment__c = rootSegmentId
        );
        insert reportSegment;

        CampaignListSegmentMapper segmentMapper = new CampaignListSegmentMapper();

        Boolean exceptionThrown = false;

        try {
            CampaignList.Segment segmentTree = segmentMapper.getSegmentTreeByRootId(
                rootSegmentId
            );
        } catch (CampaignList.InvalidSegmentSObjectException e) {
            exceptionThrown = true;
        }

        System.assert(exceptionThrown);
    }

    private static testMethod void testCreateCampaignListSegmentWithInvalidSegmentOperation() {
        Segment__c invalidSegment = new Segment__c(
            Operation__c = 'INVALID'
        );

        CampaignListSegmentMapper segmentMapper = new CampaignListSegmentMapper();

        Boolean invalidSegmentExceptionThrown = false;

        try {
            segmentMapper.create(invalidSegment);
        } catch (CampaignList.InvalidSegmentSObjectException e) {
            invalidSegmentExceptionThrown = true;
        }

        System.assert(invalidSegmentExceptionThrown);
    }

    private static testMethod void testCreateCampaignListSegmentWithInvalidSegmentSouceType() {
        Segment__c invalidSegment = new Segment__c(
            Operation__c = 'SOURCE',
            Source_ID__c = CampaignList_TEST.getNextId(Schema.sObjectType.ListView.getSObjectType()),
            Exclude_Source__c = false
        );

        CampaignListSegmentMapper segmentMapper = new CampaignListSegmentMapper();

        Boolean invalidSegmentExceptionThrown = false;

        try {
            segmentMapper.create(invalidSegment);
        } catch (CampaignList.InvalidSegmentSObjectException e) {
            invalidSegmentExceptionThrown = true;
        }

        System.assert(invalidSegmentExceptionThrown);
    }

    private static testMethod void testUpsertSegmentTreeByRootSegmentForNewSegments() {
        Id campaignId1 = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id campaignId2 = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id reportId = CampaignList_TEST.getNextId(Report.sObjectType);
        CampaignList.Segment rootSegment = new CampaignListSegment.AndSegment(null, null, null, false);
        CampaignList.Segment orSegment = new CampaignListSegment.OrSegment(null, null, null, false);
        CampaignList.Segment campaignSourceSegment1 = new CampaignListSegment.CampaignSourceSegment(null, null, null, false, campaignId1, '');
        CampaignList.Segment campaignSourceSegment2 = new CampaignListSegment.CampaignSourceSegment(null, null, null, true, campaignId2, '');
        CampaignList.Segment reportSourceSegment = new CampaignListSegment.ReportSourceSegment(null, null, null, false, reportId, 'TEST_COLUMN_NAME');

        orSegment.addChild(campaignSourceSegment2);
        orSegment.addChild(reportSourceSegment);
        rootSegment.addChild(campaignSourceSegment1);
        rootSegment.addChild(orSegment);

        CampaignListSegmentMapper mapper = new CampaignListSegmentMapper();

        Test.startTest();

        Id rootSegmentId = mapper.upsertSegmentTreeByRootSegment(rootSegment);

        Test.stopTest();

        Integer createdSegmentCount = [
            SELECT COUNT()
            FROM Segment__c
            WHERE Root_Segment__c = :rootSegmentId
            OR Id = :rootSegmentId
        ];

        System.assertEquals(5, createdSegmentCount);

        Segment__c actualRootSegment = [
            SELECT
                Parent_Segment__c,
                Operation__c,
                Source_ID__c,
                Exclude_Source__c,
                Report_Column_Name__c,
                Root_Segment__c
            FROM Segment__c
            WHERE Id = :rootSegmentId
        ];

        System.assertEquals(null, actualRootSegment.Parent_Segment__c);
        System.assertEquals('AND', actualRootSegment.Operation__c);
        System.assertEquals(null, actualRootSegment.Source_ID__c);
        System.assertEquals(false, actualRootSegment.Exclude_Source__c);
        System.assertEquals(null, actualRootSegment.Report_Column_Name__c);
        System.assertEquals(null, actualRootSegment.Root_Segment__c);

        Segment__c actualOrSegment = [
            SELECT
                Parent_Segment__c,
                Operation__c,
                Source_ID__c,
                Exclude_Source__c,
                Report_Column_Name__c,
                Root_Segment__c
            FROM Segment__c
            WHERE Parent_Segment__c = :rootSegmentId
            AND Operation__c = 'OR'
        ];

        System.assertEquals(rootSegmentId, actualOrSegment.Parent_Segment__c);
        System.assertEquals('OR', actualOrSegment.Operation__c);
        System.assertEquals(null, actualOrSegment.Source_ID__c);
        System.assertEquals(false, actualOrSegment.Exclude_Source__c);
        System.assertEquals(null, actualOrSegment.Report_Column_Name__c);
        System.assertEquals(rootSegmentId, actualOrSegment.Root_Segment__c);

        Segment__c actualCampaignSourceSegment1 = [
            SELECT
                Parent_Segment__c,
                Operation__c,
                Source_ID__c,
                Exclude_Source__c,
                Report_Column_Name__c,
                Root_Segment__c
            FROM Segment__c
            WHERE Source_ID__c = :campaignId1
        ];

        System.assertEquals(rootSegmentId, actualCampaignSourceSegment1.Parent_Segment__c);
        System.assertEquals('SOURCE', actualCampaignSourceSegment1.Operation__c);
        System.assertEquals(campaignId1, actualCampaignSourceSegment1.Source_ID__c);
        System.assertEquals(false, actualCampaignSourceSegment1.Exclude_Source__c);
        System.assertEquals(null, actualCampaignSourceSegment1.Report_Column_Name__c);
        System.assertEquals(rootSegmentId, actualCampaignSourceSegment1.Root_Segment__c);

        Segment__c actualCampaignSourceSegment2 = [
            SELECT
                Parent_Segment__c,
                Operation__c,
                Source_ID__c,
                Exclude_Source__c,
                Report_Column_Name__c,
                Root_Segment__c
            FROM Segment__c
            WHERE Source_ID__c = :campaignId2
        ];

        System.assertEquals(actualOrSegment.Id, actualCampaignSourceSegment2.Parent_Segment__c);
        System.assertEquals('SOURCE', actualCampaignSourceSegment2.Operation__c);
        System.assertEquals(campaignId2, actualCampaignSourceSegment2.Source_ID__c);
        System.assertEquals(true, actualCampaignSourceSegment2.Exclude_Source__c);
        System.assertEquals(null, actualCampaignSourceSegment2.Report_Column_Name__c);
        System.assertEquals(rootSegmentId, actualCampaignSourceSegment2.Root_Segment__c);

        Segment__c actualReportSourceSegment = [
            SELECT
                Parent_Segment__c,
                Operation__c,
                Source_ID__c,
                Exclude_Source__c,
                Report_Column_Name__c,
                Root_Segment__c
            FROM Segment__c
            WHERE Source_ID__c = :reportId
        ];

        System.assertEquals(actualOrSegment.Id, actualReportSourceSegment.Parent_Segment__c);
        System.assertEquals('SOURCE', actualReportSourceSegment.Operation__c);
        System.assertEquals(reportId, actualReportSourceSegment.Source_ID__c);
        System.assertEquals(false, actualReportSourceSegment.Exclude_Source__c);
        System.assertEquals('TEST_COLUMN_NAME', actualReportSourceSegment.Report_Column_Name__c);
        System.assertEquals(rootSegmentId, actualReportSourceSegment.Root_Segment__c);
    }

    public static testMethod void testUpsertSegmentTreeByRootSegmentForExistingSegments() {
        Id campaignId1 = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id campaignId2 = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id reportId = CampaignList_TEST.getNextId(Report.sObjectType);

        Segment__c existingRootSegment = new Segment__c(
            Operation__c = 'AND',
            Exclude_Source__c = false
        );

        insert existingRootSegment;

        Segment__c existingOrSegment = new Segment__c(
            Parent_Segment__c = existingRootSegment.Id,
            Root_Segment__c = existingRootSegment.Id,
            Operation__c = 'OR',
            Exclude_Source__c = false
        );

        insert existingOrSegment;

        Segment__c existingCampaignSourceSegment1 = new Segment__c(
            Parent_Segment__c = existingRootSegment.Id,
            Root_Segment__c = existingRootSegment.Id,
            Operation__c = 'SOURCE',
            Exclude_Source__c = false,
            Source_ID__c = campaignId1
        );

        insert existingCampaignSourceSegment1;

        Segment__c existingCampaignSourceSegment2 = new Segment__c(
            Parent_Segment__c = existingOrSegment.Id,
            Root_Segment__c = existingRootSegment.Id,
            Operation__c = 'SOURCE',
            Exclude_Source__c = true,
            Source_ID__c = campaignId2
        );

        insert existingCampaignSourceSegment2;

        Segment__c existingReportSourceSegment = new Segment__c(
            Parent_Segment__c = existingOrSegment.Id,
            Root_Segment__c = existingRootSegment.Id,
            Operation__c = 'SOURCE',
            Exclude_Source__c = false,
            Source_ID__c = reportId,
            Report_Column_Name__c = 'TEST_COLUMN_NAME'
        );

        insert existingReportSourceSegment;

        /*
         * We're going to re-use some of the above segments, but not all of them, and also create some new segments
         */

        CampaignList.Segment rootSegment = new CampaignListSegment.AndSegment(
            existingRootSegment.Id,
            null,
            null,
            false
        );

        CampaignList.Segment orSegment = new CampaignListSegment.OrSegment(
            existingOrSegment.Id,
            existingRootSegment.Id,
            existingRootSegment.Id,
            false
        );

        CampaignList.Segment campaignSourceSegment2 = new CampaignListSegment.CampaignSourceSegment(
            existingCampaignSourceSegment2.Id,
            existingRootSegment.Id,
            existingOrSegment.Id,
            false, // note this is being changed
            campaignId2,
            ''
        );

        CampaignList.Segment reportSourceSegment = new CampaignListSegment.ReportSourceSegment(
            null, // this is a completely new segment
            existingRootSegment.Id,
            existingOrSegment.Id,
            false,
            reportId,
            'TEST_COLUMN_NAME_2'
        );

        orSegment.addChild(campaignSourceSegment2);
        orSegment.addChild(reportSourceSegment);
        rootSegment.addChild(orSegment);

        CampaignListSegmentMapper mapper = new CampaignListSegmentMapper();

        Test.startTest();

        Id rootSegmentId = mapper.upsertSegmentTreeByRootSegment(rootSegment);

        Test.stopTest();

        System.assertEquals(rootSegmentId, existingRootSegment.Id);

        Integer createdSegmentCount = [
            SELECT COUNT()
            FROM Segment__c
            WHERE Root_Segment__c = :rootSegmentId
            OR Id = :rootSegmentId
        ];

        System.assertEquals(4, createdSegmentCount);

        Segment__c actualRootSegment = [
            SELECT
                Parent_Segment__c,
                Operation__c,
                Source_ID__c,
                Exclude_Source__c,
                Report_Column_Name__c,
                Root_Segment__c
            FROM Segment__c
            WHERE Id = :rootSegmentId
        ];

        System.assertEquals(null, actualRootSegment.Parent_Segment__c);
        System.assertEquals('AND', actualRootSegment.Operation__c);
        System.assertEquals(null, actualRootSegment.Source_ID__c);
        System.assertEquals(false, actualRootSegment.Exclude_Source__c);
        System.assertEquals(null, actualRootSegment.Report_Column_Name__c);
        System.assertEquals(null, actualRootSegment.Root_Segment__c);

        Segment__c actualOrSegment = [
            SELECT
                Parent_Segment__c,
                Operation__c,
                Source_ID__c,
                Exclude_Source__c,
                Report_Column_Name__c,
                Root_Segment__c
            FROM Segment__c
            WHERE Id = :existingOrSegment.Id
        ];

        System.assertEquals(rootSegmentId, actualOrSegment.Parent_Segment__c);
        System.assertEquals('OR', actualOrSegment.Operation__c);
        System.assertEquals(null, actualOrSegment.Source_ID__c);
        System.assertEquals(false, actualOrSegment.Exclude_Source__c);
        System.assertEquals(null, actualOrSegment.Report_Column_Name__c);
        System.assertEquals(rootSegmentId, actualOrSegment.Root_Segment__c);

        Segment__c actualCampaignSourceSegment2 = [
            SELECT
                Parent_Segment__c,
                Operation__c,
                Source_ID__c,
                Exclude_Source__c,
                Report_Column_Name__c,
                Root_Segment__c
            FROM Segment__c
            WHERE Id = :existingCampaignSourceSegment2.Id
        ];

        System.assertEquals(actualOrSegment.Id, actualCampaignSourceSegment2.Parent_Segment__c);
        System.assertEquals('SOURCE', actualCampaignSourceSegment2.Operation__c);
        System.assertEquals(campaignId2, actualCampaignSourceSegment2.Source_ID__c);
        System.assertEquals(false, actualCampaignSourceSegment2.Exclude_Source__c);
        System.assertEquals(null, actualCampaignSourceSegment2.Report_Column_Name__c);
        System.assertEquals(rootSegmentId, actualCampaignSourceSegment2.Root_Segment__c);

        Segment__c actualReportSourceSegment = [
            SELECT
                Parent_Segment__c,
                Operation__c,
                Source_ID__c,
                Exclude_Source__c,
                Report_Column_Name__c,
                Root_Segment__c
            FROM Segment__c
            WHERE Source_ID__c = :reportId
        ];

        System.assertNotEquals(existingReportSourceSegment.Id, actualReportSourceSegment.Id);
        System.assertEquals(actualOrSegment.Id, actualReportSourceSegment.Parent_Segment__c);
        System.assertEquals('SOURCE', actualReportSourceSegment.Operation__c);
        System.assertEquals(reportId, actualReportSourceSegment.Source_ID__c);
        System.assertEquals(false, actualReportSourceSegment.Exclude_Source__c);
        System.assertEquals('TEST_COLUMN_NAME_2', actualReportSourceSegment.Report_Column_Name__c);
        System.assertEquals(rootSegmentId, actualReportSourceSegment.Root_Segment__c);
    }

    public static testMethod void testDeserializeSegmentFromJSON() {
        Id rootSegmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id orSegmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id reportSourceSegmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id campaignSourceSegmentId1 = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id campaignSourceSegmentId2 = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id reportId = CampaignList_TEST.getNextId(Report.sObjectType);
        Id campaignId1 = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id campaignId2 = CampaignList_TEST.getNextId(Campaign.sObjectType);

        String serialized = '';
        serialized += '{';
        serialized += '    "segmentType": "AND_SEGMENT",';
        serialized += '    "segmentId": "' + rootSegmentId + '",';
        serialized += '    "isExclusion": false,';
        serialized += '    "children": [';
        serialized += '        {';
        serialized += '            "segmentType": "OR_SEGMENT",';
        serialized += '            "segmentId": "' + orSegmentId + '",';
        serialized += '            "isExclusion": false,';
        serialized += '            "children": [';
        serialized += '                {';
        serialized += '                    "segmentType": "CAMPAIGN_SOURCE_SEGMENT",';
        serialized += '                    "segmentId": "' + campaignSourceSegmentId2 + '",';
        serialized += '                    "isExclusion": true,';
        serialized += '                    "sourceId": "' + campaignId2 + '",';
        serialized += '                    "sourceName": "Campaign 2"';
        serialized += '                },';
        serialized += '                {';
        serialized += '                    "segmentType": "REPORT_SOURCE_SEGMENT",';
        serialized += '                    "segmentId": "' + reportSourceSegmentId + '",';
        serialized += '                    "isExclusion": false,';
        serialized += '                    "sourceId": "' + reportId + '",';
        serialized += '                    "sourceName": "Report",';
        serialized += '                    "columnName": "TEST_COLUMN_NAME"';
        serialized += '                }';
        serialized += '            ]';
        serialized += '        },';
        serialized += '        {';
        serialized += '            "segmentType": "CAMPAIGN_SOURCE_SEGMENT",';
        serialized += '            "segmentId": "' + campaignSourceSegmentId1 + '",';
        serialized += '            "isExclusion": false,';
        serialized += '            "sourceId": "' + campaignId1 + '",';
        serialized += '            "sourceName": "Campaign 1"';
        serialized += '        }';
        serialized += '    ]';
        serialized += '}';

        CampaignListSegmentMapper mapper = new CampaignListSegmentMapper();

        CampaignListSegment.AndSegment rootSegment = (CampaignListSegment.AndSegment) mapper.deserializeSegmentFromJSON(serialized);

        System.assertEquals(rootSegmentId, rootSegment.getId());
        System.assertEquals(false, rootSegment.isExclusion());
        System.assertEquals(2, rootSegment.getChildren().size());

        CampaignListSegment.OrSegment orSegment = (CampaignListSegment.OrSegment) rootSegment.getChildren().get(0);

        System.assertEquals(orSegmentId, orSegment.getId());
        System.assertEquals(false, orSegment.isExclusion());
        System.assertEquals(2, orSegment.getChildren().size());

        CampaignListSegment.CampaignSourceSegment campaignSourceSegment2 = (CampaignListSegment.CampaignSourceSegment) orSegment.getChildren().get(0);

        System.assertEquals(campaignSourceSegmentId2, campaignSourceSegment2.getId());
        System.assertEquals(true, campaignSourceSegment2.isExclusion());
        System.assertEquals(campaignId2, campaignSourceSegment2.getSourceId());
        System.assertEquals('Campaign 2', campaignSourceSegment2.getSourceName());

        CampaignListSegment.ReportSourceSegment reportSourceSegment = (CampaignListSegment.ReportSourceSegment) orSegment.getChildren().get(1);

        System.assertEquals(reportSourceSegmentId, reportSourceSegment.getId());
        System.assertEquals(false, reportSourceSegment.isExclusion());
        System.assertEquals(reportId, reportSourceSegment.getSourceId());
        System.assertEquals('Report', reportSourceSegment.getSourceName());
        System.assertEquals('TEST_COLUMN_NAME', reportSourceSegment.getColumnName());

        CampaignListSegment.CampaignSourceSegment campaignSourceSegment1 = (CampaignListSegment.CampaignSourceSegment) rootSegment.getChildren().get(1);

        System.assertEquals(campaignSourceSegmentId1, campaignSourceSegment1.getId());
        System.assertEquals(false, campaignSourceSegment1.isExclusion());
        System.assertEquals(campaignId1, campaignSourceSegment1.getSourceId());
        System.assertEquals('Campaign 1', campaignSourceSegment1.getSourceName());
    }
}