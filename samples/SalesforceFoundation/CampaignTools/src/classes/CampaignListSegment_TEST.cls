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
private with sharing class CampaignListSegment_TEST {
    private static testMethod void testAndSegment() {
        Id segmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id rootSegmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id parentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);

        CampaignListSegment.AndSegment segment = new CampaignListSegment.AndSegment(
            segmentId,
            rootSegmentId,
            parentId,
            false
        );

        System.assertEquals(segmentId, segment.getId());
        System.assertEquals(rootSegmentId, segment.getRootSegmentId());
        System.assertEquals(parentId, segment.getParentId());
        System.assertEquals(false, segment.isExclusion());
    }

    private static testMethod void testOrSegment() {
        Id segmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id rootSegmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id parentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);

        CampaignListSegment.OrSegment segment = new CampaignListSegment.OrSegment(
            segmentId,
            rootSegmentId,
            parentId,
            false
        );

        System.assertEquals(segmentId, segment.getId());
        System.assertEquals(rootSegmentId, segment.getRootSegmentId());
        System.assertEquals(parentId, segment.getParentId());
        System.assertEquals(false, segment.isExclusion());
    }

    private static testMethod void testCampaignSourceSegment() {
        Id targetCampaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id segmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id rootSegmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id parentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id sourceId = CampaignList_TEST.getNextId(Campaign.sObjectType);

        CampaignListSegment.CampaignSourceSegment segment = new CampaignListSegment.CampaignSourceSegment(
            segmentId,
            rootSegmentId,
            parentId,
            false,
            sourceId,
            ''
        );

        segment.setSourceName('Test Campaign');

        System.assertEquals(segmentId, segment.getId());
        System.assertEquals(rootSegmentId, segment.getRootSegmentId());
        System.assertEquals(parentId, segment.getParentId());
        System.assertEquals(sourceId, segment.getSourceId());
        System.assertEquals(false, segment.isExclusion());
        System.assertEquals('Test Campaign', segment.getSourceName());

        CampaignListFromCampaignBatch batch = (CampaignListFromCampaignBatch) segment.getBatchProcessor(targetCampaignId);
        System.assertEquals(rootSegmentId, batch.rootSegmentId);
        System.assertEquals(targetCampaignId, batch.targetCampaignId);
        System.assertEquals(sourceId, batch.sourceCampaignId);
        System.assertEquals('Campaign: Test Campaign', batch.sourceCampaignName);
    }

    private static testMethod void testReportSourceSegment() {
        Id targetCampaignId = CampaignList_TEST.getNextId(Campaign.sObjectType);
        Id segmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id rootSegmentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id parentId = CampaignList_TEST.getNextId(Segment__c.sObjectType);
        Id sourceId = CampaignList_TEST.getNextId(Report.sObjectType);

        CampaignListSegment.ReportSourceSegment segment = new CampaignListSegment.ReportSourceSegment(
            segmentId,
            rootSegmentId,
            parentId,
            false,
            sourceId,
            'test_column_name'
        );

        segment.setSourceName('Test Report');

        System.assertEquals(segmentId, segment.getId());
        System.assertEquals(rootSegmentId, segment.getRootSegmentId());
        System.assertEquals(parentId, segment.getParentId());
        System.assertEquals(sourceId, segment.getSourceId());
        System.assertEquals(false, segment.isExclusion());
        System.assertEquals('test_column_name', segment.getColumnName());
        System.assertEquals('Test Report', segment.getSourceName());

        CampaignListFromReportBatch batch = (CampaignListFromReportBatch) segment.getBatchProcessor(targetCampaignId);

        System.assertEquals(rootSegmentId, batch.rootSegmentId);
        System.assertEquals(targetCampaignId, batch.targetCampaignId);
        System.assertEquals(sourceId, batch.reportId);
        System.assertEquals('Report: Test Report', batch.reportName);
        System.assertEquals('test_column_name', batch.idColumnName);
    }
}