/*
    Copyright (c) 2014 Salesforce.org
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
* @date 2014 
* @group Utilities
* @description Calls all classes to execute at a specific run of the master scheduler.
*/
public without sharing class UTIL_MasterSchedulableHelper {

    /*******************************************************************************************************
    * @description Empty constructor.
    */
    public UTIL_MasterSchedulableHelper() {}
    
    /*******************************************************************************************************
    * @description Abort deprecated master scheduled jobs. They are all replaced by standard scheduled jobs.
    * @return void
    */
    public static void abortOldScheduledJobs() {
        //Abort the old NPSPv3 schedulable, since we have changed the name and are using a new class now (as of July 24 2014).
        //Abort the scheduled jobs from the old NPSP packages.
        List<String> jobNames = new List<String>{'NPSP Global Scheduler', 'Nightly Opportunity Roll Up', 'Recurring Donation Updates', 
            'NPSP Scheduler1', 'NPSP Scheduler2', 'NPSP Scheduler3', 'NPSP Scheduler 1', 'NPSP Scheduler 2', 'NPSP Scheduler 3'};
        List<CronTrigger> oldScheduledJobs = [select Id, CronJobDetail.Id from CronTrigger 
           where CronJobDetail.Name IN :jobNames and CronJobDetail.JobType = '7'];
        if(oldScheduledJobs != null && oldScheduledJobs.size() > 0) {
            for(CronTrigger job : oldScheduledJobs) {
                 System.abortJob(job.Id);
            }
        }
    }

    /*******************************************************************************************************
    * @description This map contains all necessary information for scheduling default NPSP jobs:
    * Job name as key, and a list containing the cron expression for that job and its class name.
    */
    public static Map<String, List<String>> defaultScheduledJobs = new Map<String, List<String>>{
        'NPSP 00 - Error Processing'                => new List<String>{'0 0 * * * ?',  'ERR_AsyncErrors_SCHED'},
        'NPSP 01 - Opportunity Account Rollups'     => new List<String>{'0 0 23 ? * *', 'RLLP_OppAccRollup_BATCH'},
        'NPSP 02 - Opportunity Contact Rollups'     => new List<String>{'0 0 23 ? * *', 'RLLP_OppContactRollup_BATCH'},
        'NPSP 03 - Opportunity Household Rollups'   => new List<String>{'0 0 23 ? * *', 'RLLP_OppHouseholdRollup_BATCH'},
        'NPSP 04 - Opportunity Soft Credit Rollups' => new List<String>{'0 0 23 ? * *', 'RLLP_OppSoftCreditRollup_BATCH'},
        'NPSP 05 - GAU Allocation Rollups'          => new List<String>{'0 0 23 ? * *', 'ALLO_Rollup_SCHED'},
        'NPSP 06 - Recurring Donation Updates'      => new List<String>{'0 0 23 ? * *', 'RD_RecurringDonations_BATCH'},
        'NPSP 07 - Seasonal Address Updates'        => new List<String>{'0 0 23 ? * *', 'ADDR_Seasonal_SCHED'},
        'NPSP 08 - Level Assignment Updates'        => new List<String>{'0 0 4 ? * *',  'LVL_LevelAssign_SCHED'}
    };

    /*******************************************************************************************************
    * @description Schedules all NPSP jobs based on whether they are marked Active in the old Schedulable__c
    * table, then clears the Schedulable__c table. If some jobs were disabled, sets the setting to prevent
    * NPSP from auto scheduling them in the future. This method is only called from the post install script. 
    * @return void
    */
    public static void scheduleJobsFromOldObject() {
        List<Schedulable__c> scheduleObjects = [SELECT Id, Class_Name__c, Active__c FROM Schedulable__c];

        Boolean allJobsActive = true;
        for (Schedulable__c sched : scheduleObjects) { 
            if (!sched.Active__c) {
                allJobsActive = false;
            }
        }

        //if all jobs are still active or no records are in the schedulable table, schedule them all. 
        //This will be the default once this method has run once, since we are
        //deleting all Schedulable__c records at the end of this method.
        if (scheduleObjects.isEmpty() || allJobsActive) {
            setScheduledJobs();
        } else {
            //there was no Schedulable__c object for error processing, so create it anyway
            createJob('NPSP 00 - Error Processing');
            for (Schedulable__c sched : scheduleObjects) {
                if (sched.Active__c) {
                    for (String jobName : defaultScheduledJobs.keySet()) {
                        if (sched.Class_Name__c.equalsIgnoreCase(defaultScheduledJobs.get(jobname)[1])) {
                            createJob(jobName);
                        }
                    }
                }
            }
            Error_Settings__c orgErrorSettings = UTIL_CustomSettingsFacade.getOrgErrorSettings();
            orgErrorSettings.Don_t_Auto_Schedule_Default_NPSP_Jobs__c = true;
            if (!Test.isRunningTest()) {
                update orgErrorSettings;
            }
        }

        delete scheduleObjects;
    }

    /*******************************************************************************************************
    * @description Schedules all NPSP jobs that are not already scheduled.
    * @return void
    */
    public static void setScheduledJobs() {

        Map<String, CronTrigger> scheduledJobs = new Map<String, CronTrigger>();
        
        //query for all scheduled apex
        for (CronTrigger ct : [SELECT Id, CronExpression, CronJobDetail.Name, CreatedBy.Name, CreatedBy.IsActive FROM CronTrigger WHERE CronJobDetail.JobType = '7']) {
            scheduledJobs.put(ct.CronJobDetail.Name, ct);
        }

        for (String jobName : defaultScheduledJobs.keySet()) {
            //schedule the job if it doesn't exist and they haven't disabled auto scheduling
            if (!scheduledJobs.containsKey(jobName)) {
                if (!UTIL_CustomSettingsFacade.getOrgErrorSettings().Don_t_Auto_Schedule_Default_NPSP_Jobs__c) {
                    createJob(jobName);
                }
            } else {
                //if it's already scheduled, but the ghost user or an inactve user scheduled it, 
                //then abort and reschedule
                CronTrigger currentJob = scheduledJobs.get(jobName);
                if (currentJob.CreatedBy.Name == 'Nonprofit Success Pack' || !currentJob.CreatedBy.isActive) {
                    System.abortJob(currentJob.Id);
                    
                    //reuse their CronExpression so it's scheduled at the time they chose
                    defaultScheduledJobs.get(jobName)[0] = currentJob.CronExpression;

                    createJob(jobName);
                }
            }
        }
    }

    /*******************************************************************************************************
    * @description Schedules a job based on that job's name.
    * @return void
    */
    private static void createJob(String jobName) {
        String cronExpression = defaultScheduledJobs.get(jobName)[0];
        String className = defaultScheduledJobs.get(jobName)[1];
    
        try {
            Schedulable classInstance = (Schedulable)Type.forName(className).newInstance();
            System.schedule(jobName, cronExpression, classInstance);
        } catch (Exception ex) {
            ERR_Handler.processError(ex, ERR_Handler_API.Context.STTG);
        }
    }
}