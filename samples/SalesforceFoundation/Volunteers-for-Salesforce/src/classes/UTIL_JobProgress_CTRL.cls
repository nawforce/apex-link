/*
    Copyright (c) 2012, Salesforce.org
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
* @date 2012 (2.0)
* @group Utilities
* @description The controller for the VF progress indicator component  
*/
public with sharing class UTIL_JobProgress_CTRL {
 
    public List<BatchJob> batchJobs;
    public UTIL_JobProgress_CTRL.BatchJob bj {get{
        //just kickoff the getter for batchJobs since it will properly populate the bj var for 
        // us w/o needing new logic
        List<BatchJob> tempList = getBatchJobs();
        return bj;
    } set;}
    public Integer numberOfJobs {get; set;}
    public String batchComponentLabel {get; set;}
    public Boolean rerenderoncomplete {get; set;}
    
    public Boolean hasExtendedStatus {
    	get {
    		//ensure bj loaded!
    		if (bj != null)
    		  return hasExtendedStatus;
    		else 
    		  return false;
    	}
    	set;
    }
    	
    public Boolean allCompleted { 
    	get {
    	   // ensure bj loaded!
    	   if (bj != null)
    	       return allCompleted;
    	   else
    	       return true;
    	}
    	set;
    }

    public List<BatchJob> getBatchJobs() {
        //Create new list of BatchJobs, a wrapper class that includes the job and percent complete.
        batchJobs = new List<BatchJob>();
        hasExtendedStatus = false;
        allCompleted = false;

        //If number of jobs was not defined, default to 20
        if(numberOfJobs== null || numberofJobs <= 0) {
            numberofJobs = 20;
        }

        if(rerenderoncomplete == null)
            rerenderoncomplete = false;

        if(batchComponentLabel== null) {
            batchComponentLabel = 'Batch Apex Jobs';
        }

        Integer completedCount = 0;
        //Query the Batch apex jobs
        for(AsyncApexJob a : [select TotalJobItems, Status, ExtendedStatus, NumberOfErrors, MethodName, JobType, JobItemsProcessed, Id, 
                                    CreatedDate, CreatedById, CompletedDate, ApexClassId, ApexClass.Name, CreatedBy.Name
                                    From AsyncApexJob where JobType='BatchApex' order by CreatedDate desc limit :numberOfJobs]) {
            Double itemsProcessed = a.JobItemsProcessed;
            Double totalItems = a.TotalJobItems;

            BatchJob j = new BatchJob();
            j.job = a;

            j.percentComplete = 5;
            //Determine the pecent complete based on the number of batches complete
            if(a.status == 'Completed') {
                j.percentComplete = 100;
            //Determine the pecent complete based on the number of batches complete
            } else if (a.status == 'Queued') {
                j.percentComplete = 5;
            } else if (a.status == 'Preparing') {
                j.percentComplete = 10;
            } else if (totalItems == 0) {
                //A little check here as we don't want to divide by 0.
                j.percentComplete = 5;
            }
            else {
                j.percentComplete = ((itemsProcessed  / totalItems) * 100.0).intValue();
            }

            //don't let it go backwards from preparing/queued
            if (j.percentComplete == 0)
                j.percentComplete = 5;

            if (a.ExtendedStatus != null && a.ExtendedStatus != '')
                hasExtendedStatus = true;

            if (a.Status == 'Completed'){
                completedCount++; 
            }

            batchJobs.add(j);
        }
        if (completedCount == numberOfJobs)
            allCompleted = true;
        if (!batchJobs.isEmpty())
            bj = batchJobs[0];
        return batchJobs;
    }

    //This is the wrapper class the includes the job itself and a value for the percent complete
    public Class BatchJob{
        public AsyncApexJob job {get; set;}
        public Integer percentComplete {get; set;}
    }

    /**
     * @description A RemoteAction method for querying the AsyncApexJob table.
     *
     * @param numberOfJobs The limit of number of jobs that should be returned in the query
     * @return List<BatchJobStatus>
     */
    @RemoteAction
    public static List<BatchJobStatus> getBatchJobsStatus(Integer numberOfJobs) {
        List<AsyncApexJob> jobs = new List<AsyncApexJob>();
        List<BatchJobStatus> jobsStatus = new List<BatchJobStatus>();

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
            ORDER BY CreatedDate DESC
            LIMIT :numberOfJobs
        ];

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
            className = job.ApexClass.Name;
            createdByName = job.CreatedBy.Name;
            createdDate = job.CreatedDate;
            status = job.Status;
            extendedStatus = job.extendedStatus;
            completedDate = job.CompletedDate;
            jobItemsProcessed = job.JobItemsProcessed;
            totalJobItems = job.TotalJobItems;
            numberOfErrors = job.NumberOfErrors;

            percentCompleted = calculatePercentCompleted(jobItemsProcessed, totalJobItems, status);
        }

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