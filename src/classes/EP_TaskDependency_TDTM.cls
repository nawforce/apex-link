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
* @date 2016
* @group Engagement Plans
* @group-content ../../ApexDocContent/EngagementPlans.htm
* @description When a Task created by an Engagement Plan is given a Closed status, checks for dependent Tasks
* and updates them by changing the status, updating the due date, and sending email.
*/
public with sharing class EP_TaskDependency_TDTM extends TDTM_Runnable {

    /*******************************************************************************************************
    * @description Trigger Handler on Task that checks for dependent Tasks based on EP Task dependencies,
    * updating the dependent task status and date, setting reminders, and sending email.
    * @param listNew the list of Engagement Plans from trigger new. 
    * @param listOld the list of Engagement Plans from trigger old. 
    * @param triggerAction which trigger event (BeforeInsert, AfterInsert, etc.). 
    * @param objResult the describe for Engagement Plans 
    * @return dmlWrapper.  
    ********************************************************************************************************/
    public override DmlWrapper run(List<SObject> newlist, List<SObject> oldlist, 
        TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {

        if (triggerAction == TDTM_Runnable.Action.AfterUpdate) {
            List<Task> tasksToCheckDependencies = new List<Task>();
            Map<Id, SObject> oldMap = new Map<Id, SObject>(oldlist);

            for (sObject newSObject : newList) {
                Task newTask = (Task) newSObject;
                Task oldTask = (Task) oldMap.get(newTask.id);
                if (newTask.Engagement_Plan_Task__c != null &&
                        newTask.Engagement_Plan__c!=null && 
                        newTask.isClosed && 
                        !oldTask.isClosed) {
                    
                    tasksToCheckDependencies.add(newTask);
                }
            }

            if (!tasksToCheckDependencies.isEmpty()) {
                updateDependentTasks(tasksToCheckDependencies);
            }
        }
        return null;
    }

    /** @description Map of EP Tasks that are direct dependents of Tasks in the trigger set, i.e. children. */
    private Map<Id, Engagement_Plan_Task__c> directDependentEPTasks = new Map<Id, Engagement_Plan_Task__c>();
    /** @description Map of EP Tasks that are indirect dependents of Tasks in the trigger set, i.e. grandchildren. */
    private Map<Id, Engagement_Plan_Task__c> indirectDependentEPTasks = new Map<Id, Engagement_Plan_Task__c>();
    /** @description Task utilities class. */
    private EP_Task_UTIL taskUtil;

    /*******************************************************************************************************
    * @description For a list of Tasks that have just been given a closed status, queries for all tasks
    * that are dependent on these tasks, activating direct children and updating the activity date of
    * indirect children.
    * @param tasksWithDependencies The tasks that have just been marked complete.
    * @return void This method inserts and updates Tasks directly, as DML options are needed for email sends.
    ********************************************************************************************************/
    private void updateDependentTasks(List<Task> tasksWithDependencies) {
        taskUtil = new EP_Task_UTIL(tasksWithDependencies);

        List<Task> taskUpdateEmail = new List<Task>();
        List<Task> taskUpdateNoEmail = new List<Task>();

        //set of engagement plans for the current trigger set, for filtering out Tasks not related to current EPs.
        Set<Id> engagementPlanIds = new Set<Id>();

        //gather all EP tasks that are direct children of EP tasks in the current trigger set
        //these will have their status, date, and reminder modified
        for (Task parentTask : tasksWithDependencies) {
            engagementPlanIds.add(parentTask.Engagement_Plan__c);
            Engagement_Plan_Task__c parentEPTask = taskUtil.epTaskMap.get(parentTask.Engagement_Plan_Task__c);
            for (Engagement_Plan_Task__c childEPTask : parentEPTask.Engagement_Plan_Tasks__r) {
                directDependentEPTasks.put(childEPTask.id, taskUtil.epTaskMap.get(childEPTask.id));
            }
        }

        //gather all EP tasks that are down the dependency chain, but not direct children of the current trigger set
        //these will just have their dates updated
        for (Engagement_Plan_Task__c parentTask : directDependentEPTasks.values()) {
            addChildrenRecursive(parentTask);
        }

        //query for all tasks that are dependent on the current trigger set of tasks
        //since multiple Tasks can point toward a single EP Task, 
        //we need to deal with lists of Tasks for each EP Task id.
        Map<Id, List<Task>> mapEpTaskIdTasks = new Map<Id, List<Task>>();
        for (Task taskForUpdate :  [SELECT Id, ActivityDate, Engagement_Plan_Task__c, Engagement_Plan__c
                                    FROM Task WHERE Engagement_Plan__c IN :engagementPlanIds AND
                                    (Engagement_Plan_Task__c IN :directDependentEPTasks.values() OR 
                                    Engagement_Plan_Task__c IN :indirectDependentEPTasks.values())]) {
            if (!mapEpTaskIdTasks.containsKey(taskForUpdate.Engagement_Plan_Task__c)) {
                mapEpTaskIdTasks.put(taskForUpdate.Engagement_Plan_Task__c, new List<Task>{taskForUpdate});
            } else {
                mapEpTaskIdTasks.get(taskForUpdate.Engagement_Plan_Task__c).add(taskForUpdate);
            }
        }
        
        //update directly dependent tasks that are now active; send email per the task setting
        for (Engagement_Plan_Task__c epTask : directDependentEPTasks.values()) {
            
            for (Task taskForUpdate : mapEpTaskIdTasks.get(epTask.Id)) {

                taskUtil.updateActivateTask(taskForUpdate);

                if (epTask.Send_Email__c) {
                    taskUpdateEmail.add(taskForUpdate);
                } else {
                    taskUpdateNoEmail.add(taskForUpdate);
                }
            }
        }

        //update indirect dependent Tasks if the template's delay type says to
        for (Engagement_Plan_Task__c epTask : indirectDependentEPTasks.values()) {
            Engagement_Plan_Template__c template = taskUtil.templateMap.get(epTask.Engagement_Plan_Template__c);
            if (template.Automatically_Update_Child_Task_Due_Date__c) {
                for (Task indirectDependentTask : mapEpTaskIdTasks.get(epTask.id)) {
                    taskUtil.updateInactiveTask(indirectDependentTask);
                    taskUpdateNoEmail.add(indirectDependentTask);
                }
            }
        }

        Database.DMLOptions options = new Database.DMLOptions();
        options.OptAllOrNone = true;

        if (!taskUpdateEmail.isEmpty()) {
            options.EmailHeader.triggerUserEmail = true;
            List<Database.SaveResult> results = Database.update(taskUpdateEmail, options);
        }

        if (!taskUpdateNoEmail.isEmpty()) {
            options.EmailHeader.triggerUserEmail = false;
            List<Database.SaveResult> results = Database.update(taskUpdateNoEmail, options);
        }
    }

    /*******************************************************************************************************
    * @description Follows the dependency chain of a given EP Task, adding its children to the map
    * indirectDependentEPTasks.
    * @param parentEPTask The task whose children should be added to the indirectDependentEPTasks map
    * @return void This method adds children tasks to the indirectDependentEPTasks map and returns nothing.
    ********************************************************************************************************/
    private void addChildrenRecursive(Engagement_Plan_Task__c parentEPTask) {
        for (Engagement_Plan_Task__c childEPTask : parentEPTask.Engagement_Plan_Tasks__r) {
            Engagement_Plan_Task__c epTask = taskUtil.epTaskMap.get(childEPTask.id);

            if (epTask.Days_After__c == null) {
                epTask.Days_After__c = 0;
            }

            if (parentEPTask.Days_After__c == null) {
                parentEPTask.Days_After__c = 0;
            }

            //use the days after field to calculate the total delay in memory; 
            //we won't be committing these results, just using them for updating Tasks
            epTask.Days_After__c += parentEPTask.Days_After__c;

            indirectDependentEPTasks.put(epTask.id, epTask);
            addChildrenRecursive(epTask);
        }
    }
}