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
 * @description Batch process for gathering potential campaign list members
 * from a given report.
 */
public with sharing class CampaignListFromReportBatch
    extends BatchableSequence.Batch implements Database.Batchable<Id> {
    /**
     * @description The id of the "campaign list" which the members should be
     * added to
     */
    @TestVisible
    private Id rootSegmentId;

    /**
     * @description The id of the Campaign to be refreshed
     */
    @TestVisible
    private Id targetCampaignId;

    /**
     * @description The id of the Report from which to gather potential
     * members
     */
    @TestVisible
    private Id reportId;

    /**
     * @description The name of the column in the report that holds the id of
     * the Contact or Lead to be included in the campaign list
     */
    @TestVisible
    private String idColumnName;

    /**
     * @description The name of the Report from which to gather potential
     * members
     */
    @TestVisible
    private String reportName;

    /**
     * @description A factory for generating an Id iterable
     */
    @TestVisible
    private IdIterableFactory idIterableFactory = new DefaultIdIterableFactory();

    /**
     * @description Construct the batch process to gather potential members
     * from a given report for a given campaign list.
     *
     * @param rootSegmentId The id of the "campaign list" which the members should be added to
     * @param targetCampaignId The id of the Campaign to be refreshed
     * @param reportId The id of the Report from which to gather potential members
     * @param idColumnName The name of the column in the report to retrieve id values from
     * @param reportName The name of the Report from which to gather potential members
     */
    public CampaignListFromReportBatch(Id rootSegmentId, Id targetCampaignId, Id reportId, String idColumnName, String reportName) {
        this.rootSegmentId = rootSegmentId;
        this.targetCampaignId = targetCampaignId;
        this.reportId = reportId;
        this.idColumnName = idColumnName;
        this.reportName = reportName;
    }

    /**
     * @description A factory for generating an Id iterable
     */
    public interface IdIterableFactory {
        Iterable<Id> create(Id reportId, String idColumnName);
    }

    /**
     * @description A default implementation of a factory for generating an Id
     * iterable.  This will create a ReportService.ReportRowIdIterableIterator.
     */
    @TestVisible
    private with sharing class DefaultIdIterableFactory implements IdIterableFactory {
        /**
         * @description Create a ReportService.ReportRowIdIterableIterator
         * using the given parameters.
         *
         * @param reportId The id of the report to iterate over
         * @param idColumnName The name of the column in the report to return from the iterator
         * @return Iterable<Id>
         */
        public Iterable<Id> create(Id reportId, String idColumnName) {
            return new ReportService.ReportRowIdIterableIterator(
                reportId,
                idColumnName
            );
        }
    }

    /**
     * @description Implementation of start() from Database.Batchable
     * interface.
     *
     * @return Iterable<Id> An iterable that will provide values from the id column of the given report
     */
    public Iterable<Id> start(Database.BatchableContext bc) {
        try {
            return idIterableFactory.create(reportId, idColumnName);
        } catch (Exception e) {
            CampaignList.Service service = CampaignList.getService();
            service.unlockCampaignAndMarkFailed(
                targetCampaignId,
                String.format(
                    Label.CampaignToolsListFromReportStartException,
                    new List<String>{
                        reportId,
                        rootSegmentId
                    }
                )
            );
            service.abortJob(service.getJobId(bc));
            return null;
        }
    }

    /**
     * @description Implementation of execute() from Database.Batchable.
     * Creates CampaignListMembers from the report values and persists them
     * to the database.  Each CampaignListMember created will include this
     * report in its list of sources.
     *
     * @return void
     */
    public void execute(Database.BatchableContext bc, List<Id> ids) {
        try {
            CampaignList.getMemberMapper().updateByRelatedIdsAndSource(
                ids,
                rootSegmentId,
                reportId,
                reportName
            );
        } catch (Exception e) {
            CampaignList.Service service = CampaignList.getService();
            service.unlockCampaignAndMarkFailed(
                targetCampaignId,
                String.format(
                    Label.CampaignToolsListFromReportExecuteException,
                    new List<String>{
                        reportId,
                        rootSegmentId
                    }
                )
            );
            service.abortJob(service.getJobId(bc));
        }
    }

    /**
     * @description This will cause this batch to be executed, i.e.,
     * Database.executeBatch() will be called with this batch as the batch to
     * execute and this batch's scope.
     *
     * @return Id The id of the AsyncApexJob returned by Database.executeBatch()
     */
    public override Id executeBatch() {
        return Database.executeBatch(this, getScope());
    }
}