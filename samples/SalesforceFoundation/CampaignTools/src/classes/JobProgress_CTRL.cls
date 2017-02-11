/*
    Copyright (c) 2016, Salesforce.org
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of Salesforce.org nor the names of
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
* @group Utilities
* @description The controller for the job progress visualforce component
*/
public with sharing class JobProgress_CTRL {
    /**
     * @description A RemoteAction method for querying the AsyncApexJob table.
     *
     * @param numberOfJobs The limit of number of jobs that should be returned in the query
     * @param createdDateMin The minimum CreatedDate for jobs to include
     * @return List<BatchJobStatus>
     */
    @RemoteAction
    public static List<BatchJobStatus> getBatchJobsStatus(Integer numberOfJobs, DateTime createdDateMin) {
        List<BatchJobStatus> jobsStatus = new List<BatchJobStatus>();
            List<AsyncApexJob> jobs = new List<AsyncApexJob>();
            
            if (createdDateMin == null)   
                createdDateMin = system.now().addDays(-1);

            try {
                CampaignTools_UTIL.checkObjectReadAccess('AsyncApexJob');
                jobs = [
                    SELECT
                    ApexClass.Name,
                    CreatedBy.Name,
                    CreatedDate,
                    Status,
                    ExtendedStatus,
                    CompletedDate,
                    JobItemsProcessed,
                    TotalJobItems,
                    NumberOfErrors
                    FROM AsyncApexJob
                    WHERE JobType = 'BatchApex'
                    AND CreatedDate >= :createdDateMin
                    ORDER BY CreatedDate DESC
                    LIMIT :numberOfJobs
                ];
            } catch (QueryException e) {
                // could not query jobs or no jobs found
            }

        for (AsyncApexJob job : jobs) {
            jobsStatus.add(new BatchJobStatus(job));
        }

        return jobsStatus;
    }

    /**
     * @author Salesforce.org
     * @group Utilities
     * @description This class is for serializing information queried from the
     * AsyncApexJob table.
     */
    public class BatchJobStatus {
        public Id jobId;
        public String className;
        public String createdByName;
        public DateTime createdDate;
        public String status;
        public String extendedStatus;
        public DateTime completedDate;
        public Integer jobItemsProcessed;
        public Integer totalJobItems;
        public Integer numberOfErrors;
        public Long percentCompleted;

        /**
         * @description Construct from an AsyncApexJob object.  This will
         * extract information from the AsyncApexJob and store it in this class
         * to be serialized, including calculating the percentage of the batch
         * job completed.
         *
         * @param job The AsyncApexJob to gather data from
         */
        public BatchJobStatus(AsyncApexJob job) {
            jobId = job.Id;
            className = job.ApexClass.Name;
            createdByName = job.CreatedBy.Name;
            createdDate = job.CreatedDate;
            status = job.Status;
            extendedStatus = job.ExtendedStatus;
            completedDate = job.CompletedDate;
            jobItemsProcessed = job.JobItemsProcessed;
            totalJobItems = job.TotalJobItems;
            numberOfErrors = job.NumberOfErrors;

            percentCompleted = calculatePercentCompleted(jobItemsProcessed, totalJobItems, status);
        }

        /**
         * @description Empty constructor, not for public use
         */
        @TestVisible
        private BatchJobStatus() {}

        /**
         * @description Calculate the percentage of the batch job completed.
         * This method maps certain job "Status" values to specified
         * percentages to, for example, show some progress when the job is
         * Queued, and more progress when Preparing, etc.
         *
         * @param jobItemsProcessed The number of job items processed, so far
         * @param totalJobItems The total number of job items in the batch job
         * @param status The status of the job.  Relevant values are "Completed", "Queued", and "Preparing".
         * @return Long (The percentage as a value between 5 and 100).
         */
        @TestVisible
        private Long calculatePercentCompleted(Integer jobItemsProcessed, Integer totalJobItems, String status) {
            Long percentCompleted = 5;

            if ('Completed' == status) {
                percentCompleted = 100;
            } else if ('Queued' == status) {
                percentCompleted = 5;
            } else if ('Preparing' == status) {
                percentCompleted = 10;
            } else if (0 == totalJobItems) {
                percentCompleted = 5;
            } else {
                percentCompleted = Math.max(
                    Math.roundToLong(100 * jobItemsProcessed / totalJobItems),
                    5
                );
            }

            return percentCompleted;
        }
    }
}