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
* @description EP Task and Task utilities for Engagement Plans. Constructor creates maps of EP Tasks and EP Templates.
* Methods to create and update tasks, calculate 
*/
public with sharing class EP_Task_UTIL {

    /** @description Default status for active tasks. */
    public static String defaultTaskStatus {
        get {
            if (defaultTaskStatus == null) {
                List<TaskStatus> queryStatus = [SELECT MasterLabel FROM TaskStatus WHERE isDefault = true LIMIT 1];
                if (queryStatus.isEmpty()) {
                    defaultTaskStatus = '';
                } else {
                    defaultTaskStatus = queryStatus[0].MasterLabel;
                }
            }
            return defaultTaskStatus;
            
        }
        private set;
    }

    /** @description Map of all EP Tasks from templates associated to the trigger set. */
    public Map<Id, Engagement_Plan_Task__c> epTaskMap = new Map<Id, Engagement_Plan_Task__c>();
    /** @description MAp of all templates from the trigger set and their associated EP Tasks.*/
    public Map<Id, Engagement_Plan_Template__c> templateMap = new Map<Id, Engagement_Plan_Template__c>();

    /*******************************************************************************************************
    * @description Constructor initializes map properties.
    * @param engagementPlans a list of Engagement Plans to query and build maps for.
    * @return void This constructor instantiates class properties.
    ********************************************************************************************************/
    public EP_Task_UTIL(List<Engagement_Plan__c> engagementPlans) {
        initializeMaps(engagementPlans);
    }

    /*******************************************************************************************************
    * @description Task constructor queries for a list of engagement plans in order to initialize maps.
    * @param tasks a list of Tasks to query and build maps for.
    * @return void This constructor instantiates class properties.
    ********************************************************************************************************/
    public EP_Task_UTIL(List<Task> tasks) {
        Set<Id> planIds = new Set<Id>();
        for (Task task : tasks) {
            if (task.Engagement_Plan__c!=null) {
                planIds.add(task.Engagement_Plan__c);
            }
        }
        List<Engagement_Plan__c> engagementPlans = [
            SELECT Id, Engagement_Plan_Template__c FROM Engagement_Plan__c WHERE Id IN :planIds
        ];

        initializeMaps(engagementPlans);
    }

    /*******************************************************************************************************
    * @description Constructor initializes map properties based on a single template Id.
    * @param templateId The Engagement Plan Template to build maps for.
    * @return void This constructor instantiates class properties.
    ********************************************************************************************************/
    public EP_Task_UTIL(Id templateId) {
        initializeMaps(new Set<Id>{templateId});
    }


    /*******************************************************************************************************
    * @description Gathers a set of template Ids for a list of Engagement Plans
    * @param plans a list of Engagement Plans to query and build maps for.
    * @return void This method calls another method that instantiates class properties.
    ********************************************************************************************************/
    public void initializeMaps(List<Engagement_Plan__c> plans) {
        Set<Id> templateIds = new Set<Id>();
        for (Engagement_Plan__c plan : plans) {
            templateIds.add(plan.Engagement_Plan_Template__c);
        }

        initializeMaps(templateIds);
    }


    /*******************************************************************************************************
    * @description Queries and builds maps for templates and template tasks from a set of template Ids.
    * @param templateIds a set of Engagement Plan Template Ids to query and build maps for.
    * @return void This method instantiates class properties.
    ********************************************************************************************************/
    public void initializeMaps(Set<Id> templateIds) {

        //query our custom fields and fieldset fields for when this template is accessed from the UI
        Set<String> templateFields = new Set<String>{'Id', 'Name', 'Skip_Weekends__c', 'Reschedule_To__c', 'Automatically_Update_Child_Task_Due_Date__c', 'Default_Assignee__c', 'Description__c'};
        templateFields.addAll(UTIL_Describe.listStrFromFieldSet(UTIL_Namespace.StrTokenNSPrefix('Engagement_Plan_Template__c'), UTIL_Namespace.StrTokenNSPrefix('ManageEPTemplateFS')));

        String templateQuery = 'SELECT ';
        templateQuery += String.join(new List<String>(templateFields), ', ');
        templateQuery += ', (SELECT Id, Name, Engagement_Plan_Template__c, Assigned_To__c, Comments__c, Days_After__c, Type__c, ';
        templateQuery += 'Parent_Task__c, Priority__c, Reminder__c, Reminder_Time__c, Send_Email__c, Status__c ';
        templateQuery += 'FROM Engagement_Plan_Tasks__r) ';
        templateQuery += 'FROM Engagement_Plan_Template__c WHERE Id IN :templateIds';

        //query for templates and their child tasks
        templateMap = new Map<Id, Engagement_Plan_Template__c>((List<Engagement_Plan_Template__c>)Database.query(templateQuery));

        //query for tasks and their child tasks
        epTaskMap = new Map<Id, Engagement_Plan_Task__c>(
            [SELECT Id, Name, Engagement_Plan_Template__c, Assigned_To__c, Comments__c, Days_After__c, Type__c, 
            Parent_Task__c, Priority__c, Reminder__c, Reminder_Time__c, Send_Email__c, Status__c,
                (SELECT Id FROM Engagement_Plan_Tasks__r)
            FROM Engagement_Plan_Task__c WHERE Engagement_Plan_Template__c IN :templateIds]
        );        
    }


    /*******************************************************************************************************
    * @description Creates a Task based on an Engagement Plan Task. Copies fields, sets up dependent tasks
    * by providing a special status and determining delay in due date based on dependent tasks.
    * @param epTask An Engagement Plan Task from which to copy fields.
    * @return void This method updates class properties.
    ********************************************************************************************************/
    public Task createTask(Engagement_Plan_Task__c epTask, Engagement_Plan__c engagementPlan) {
        Engagement_Plan_Template__c template = templateMap.get(epTask.Engagement_Plan_Template__c);

        Map<String,String> taskEPTaskFieldMap = new Map<String,String>();
        //Task field name -> Engagement_Plan_Task__c field name
        taskEPTaskFieldMap.put('Engagement_Plan_Task__c', 'Id');
        taskEPTaskFieldMap.put('Subject', 'Name');
        taskEPTaskFieldMap.put('Priority', 'Priority__c');
        taskEPTaskFieldMap.put('OwnerId', 'Assigned_To__c');
        taskEPTaskFieldMap.put('Description', 'Comments__c');
        taskEPTaskFieldMap.put('Type', 'Type__c');
        taskEPTaskFieldMap.put('Status', 'Status__c');

        //copy fields
        Task taskResult = new Task();
        for (String taskField : taskEPTaskFieldMap.keySet()) {
            taskResult.put(taskField, epTask.get(taskEPTaskFieldMap.get(taskField)));
        }

        //set engagement plan lookup on task
        taskResult.Engagement_Plan__c = engagementPlan.Id;

        //modify the days after field in memory to facilitate calculating dates;
        //this will not be comitted to the database
        epTask.Days_After__c = getParentDelayRecursive(epTask);

        taskResult.ActivityDate = calcActivityDate(epTask, System.today());

        //default the Priority field if none is set
        if (String.isBlank(taskResult.Priority)) {
            taskResult.Priority = UTIL_Describe.getDefaultSelectOption('Task','Priority');
        }

        //Non Dependent Task defaults
        if (epTask.Parent_Task__c == null) {
            setReminder(taskResult);
            
            if (String.isBlank(taskResult.Status)) {
                taskResult.Status = defaultTaskStatus;
            }
        //Dependent Task defaults
        //Don't set reminder for dependent tasks; it will be set once the parent task is complete.
        } else {
            taskResult.Status = EP_EngagementPlans_UTIL.TASK_STATUS_WAITING;
        }

        return taskResult;
    }


    /*******************************************************************************************************
    * @description Gets the total delay for an EP Task, including all parent delays.
    * @param taskNeedingReminder The Task object needing reminder to be set.
    * @return Integer The total delay for this EP Task, including all parent EP Task delays.
    ********************************************************************************************************/
    private Integer getParentDelayRecursive(Engagement_Plan_Task__c epTask) {
        Integer result = 0;
        Engagement_Plan_Task__c currentTask = epTask;

        if (currentTask.Days_After__c != null) {
            result += (Integer)currentTask.Days_After__c;
        }

        //follow the dependency chain to get the total number of days delay
        Integer max_counter = 0;
        while (currentTask.Parent_Task__c != null
                && max_counter < EP_EngagementPlans_UTIL.MAX_ITERATIONS) {
            
            max_counter++;

            currentTask = epTaskMap.get(currentTask.Parent_Task__c);
            if (currentTask.Days_After__c != null) {
                result += (Integer)currentTask.Days_After__c;
            }
        }
        return result;
    }

    /*******************************************************************************************************
    * @description For a dependent task whose parent task has just completed, sets the status and updates
    * the activity date and reminder date based on today's date.
    * @param taskNeedingReminder The Task object needing reminder to be set.
    * @return void This method edits the Task parameter directly.
    ********************************************************************************************************/
    public void updateActivateTask(Task taskForUpdate) {
        Engagement_Plan_Task__c epTask = epTaskMap.get(taskForUpdate.Engagement_Plan_Task__c);
        Engagement_Plan_Template__c template = templateMap.get(epTask.Engagement_Plan_Template__c);

        if (epTask.Status__c != null) {
            taskForUpdate.Status = epTask.Status__c;
        } else {
            taskForUpdate.Status = defaultTaskStatus;
        }

        //update activity dates if the template is set up to use the parent task completion date
        if (template.Automatically_Update_Child_Task_Due_Date__c) {
            taskForUpdate.ActivityDate = calcActivityDate(epTask, System.today());
        }
        //activate reminder if set, and update reminder date based on ActivityDate
        setReminder(taskForUpdate);
    }

    /*******************************************************************************************************
    * @description For a dependent task still in an inactive state, update the activity date and reminder
    * date.
    * @param taskNeedingReminder The Task object needing reminder to be set.
    * @return void This method edits the Task parameter directly.
    ********************************************************************************************************/
    public void updateInactiveTask(Task taskForUpdate) {
        Engagement_Plan_Task__c epTask = epTaskMap.get(taskForUpdate.Engagement_Plan_Task__c);

        //update activity and reminder dates
        taskForUpdate.ActivityDate = calcActivityDate(epTask, System.today());
    }

    /*******************************************************************************************************
    * @description Sets a Task's reminder fields based on the associated EP Task
    * @param taskNeedingReminder The Task object needing reminder to be set.
    * @return void This method edits the Task parameter directly.
    ********************************************************************************************************/
    private void setReminder(Task taskNeedingReminder) {
        Engagement_Plan_Task__c epTask = epTaskMap.get((Id)taskNeedingReminder.Engagement_Plan_Task__c);

        if (epTask.Reminder__c) {
            taskNeedingReminder.IsReminderSet = true;

            Integer reminderMinutes;
            if (String.isBlank(epTask.Reminder_Time__c)) {
                //default to noon, i.e. 12*60
                reminderMinutes = 720;
            } else {
                reminderMinutes = Integer.valueOf(epTask.Reminder_Time__c);
            }

            //We've already calculated the the activity date for this task, including offset and skip weekend setting
            //so we'll use that date for ReminderDateTime as well
            taskNeedingReminder.ReminderDateTime = Datetime.newInstance(
                taskNeedingReminder.ActivityDate.year(), 
                taskNeedingReminder.ActivityDate.month(), 
                taskNeedingReminder.ActivityDate.day()
            );
            taskNeedingReminder.ReminderDateTime = taskNeedingReminder.ReminderDateTime.addMinutes(reminderMinutes);
        }
    }

    /*******************************************************************************************************
    * @description For a given Task and an Id of the record to assign the task to, determines if the Id is
    * a Lead or Contact, in which case the Task field WhoId is used. Otherwise, uses WhatId for assignemnt.
    * @param taskToAssign The Task object needing assignment.
    * @param objectId The object to assign the Task to.
    * @return void This method edits the Task parameter directly.
    ********************************************************************************************************/
    public void assignTask(Task taskToAssign, Id objectId) {
        if (taskToAssign == null || objectId == null) {
            return;
        }
        if (objectId.getSobjectType() == Contact.sObjectType 
            || objectId.getSobjectType() == Lead.sObjectType) {
            taskToAssign.WhoId = objectId;
        } else {
            taskToAssign.WhatId = objectId;
        }
    }

    /*******************************************************************************************************
    * @description For a given EP Task and a date, calculates the number of days delay including parent
    * task delays, and skips weekends based on the template settings.
    * @param epTask An Engagement Plan Task to calculate the delay for.
    * @return Date The calculated date including the delay and skip weekends setting.
    ********************************************************************************************************/
    public Date calcActivityDate(Engagement_Plan_Task__c epTask, Date startDate) {
        Engagement_Plan_Template__c template = templateMap.get(epTask.Engagement_Plan_Template__c);

        Date result = startDate;

        if (epTask.Days_After__c != null) {
            result = result.addDays((Integer)epTask.Days_After__c);
        }

        if (template.Skip_Weekends__c) {
            result = skipWeekend(result, template.Reschedule_To__c);
        }

        return result;
    }

    /*******************************************************************************************************
    * @description For a given date and skip day setting, moves dates that land on a weekend to either 
    * the previous Friday or the following Monday.
    * @param originalDate The date that may be on a weekend.
    * @param skipDay Which day to move the date to: Friday or Monday.
    * @return Date The original date moved to Friday or Monday.
    ********************************************************************************************************/
    private Date skipWeekend(Date originalDate, String skipDay) {
        // find if the due date falls on a Saturday or Sunday; add 1 day for Sunday and 2 for Saturday
        Date result = originalDate;
        Date refDate = Date.newInstance(1900, 1, 7);  // Jan 7 1900 = Sunday
        Integer dayOfWeek = Math.mod(refDate.daysBetween(originalDate), 7); // 0 = Sunday ... 6 = Saturday
        
        if (skipDay == EP_EngagementPlans_UTIL.SKIP_DAY_FRIDAY) {
            //subtract days to Friday
            if (dayOfWeek == 0) {
                result = result.addDays(-2);
            } else if (dayOfWeek == 6) {
                result = result.addDays(-1);
            }
        } else {
            //add days to Monday
            if (dayOfWeek == 0) {
                result = result.addDays(1);
            } else if (dayOfWeek == 6) {
                result = result.addDays(2);
            }
        }
        return result;
    }
}