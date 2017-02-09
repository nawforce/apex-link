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
* @description Tests for Task dependency resolution: when Tasks created by Engagement Plans are closed, their child
* Tasks are activated, and their grandchildren may have the activity date updated, depending on the template setting.
*/
@isTest
private with sharing class EP_TaskDependency_TEST {

    /*********************************************************************************************************
    * @description Creates a template with four EP Tasks, one parent, two children, and one grandchild. Then
    * creates an Engagement Plan using the template. Verifies that tasks are created correctly. Updates the
    * parent task to closed, and verifies that the direct children are updated to open, and that grandchildren
    * have their date updated due to the template "Delay Type" setting of "After Date Completed".
    */
    static testMethod void testDependentPlan() {

        Contact con = new Contact(LastName = 'foo');
        insert con;

        Engagement_Plan_Template__c template = new Engagement_Plan_Template__c(
            Name = 'Template',
            Skip_Weekends__c = false,
            Default_Assignee__c = EP_EngagementPlans_UTIL.BLANK_ASSIGNMENT_USER_CREATING,
            Automatically_Update_Child_Task_Due_Date__c = true
        );
        insert template;

        Engagement_Plan_Task__c epTask = new Engagement_Plan_Task__c(
            Name = 'Call',
            Engagement_Plan_Template__c = template.id,
            Comments__c = 'This comment should be copied correctly to the Task.',
            Reminder__c = true,
            Send_Email__c = true,
            Days_After__c = 0,
            Type__c = 'Call',
            Priority__c = 'Medium'
        );
        insert epTask;

        List<Engagement_Plan_Task__c> childEpTasks = new List<Engagement_Plan_Task__c>();
        childEpTasks.add(new Engagement_Plan_Task__c(
            Name = 'Email',
            Engagement_Plan_Template__c = template.id,
            Parent_Task__c = epTask.id,
            Comments__c = 'This comment should also be copied correctly to the Task.',
            Reminder__c = true,
            Send_Email__c = true,
            Days_After__c = 1,
            Type__c = 'Email',
            Priority__c = 'Low'
        ));
        childEpTasks.add(new Engagement_Plan_Task__c(
            Name = 'Meeting',
            Engagement_Plan_Template__c = template.id,
            Parent_Task__c = epTask.id,
            Comments__c = 'This comment should also be copied correctly to the Task.',
            Reminder__c = true,
            Send_Email__c = true,
            Days_After__c = 2,
            Type__c = 'Meeting',
            Priority__c = 'Low'
        ));
        insert childEpTasks;

        Engagement_Plan_Task__c grandchildEpTask = new Engagement_Plan_Task__c(
            Name = 'Other',
            Engagement_Plan_Template__c = template.id,
            Parent_Task__c = childEpTasks[0].id,
            Comments__c = 'This comment should also be copied correctly to the Task.',
            Reminder__c = true,
            Send_Email__c = true,
            Days_After__c = 2,
            Type__c = 'Other',
            Priority__c = 'High'
        );
        insert grandchildEpTask;

        Engagement_Plan__c plan = new Engagement_Plan__c(
            Contact__c = con.id,
            Engagement_Plan_Template__c = template.id
        );
        insert plan;

        List<Task> results = [SELECT Id, OwnerId, WhoId, Type, Status, Subject, ActivityDate, Priority, Description,
                            IsReminderSet, ReminderDateTime FROM Task WHERE WhoId = :con.id ORDER BY ActivityDate];
        System.assertEquals(4, results.size(), 'Four tasks should be created.');

        System.assertEquals('Call', results[0].Subject, 'Subject field should be set based on EP Task Name.');
        System.assertEquals('Email', results[1].Subject, 'Subject field should be set based on EP Task Name.');
        System.assertEquals('Meeting', results[2].Subject, 'Subject field should be set based on EP Task Name.');
        System.assertEquals('Other', results[3].Subject, 'Subject field should be set based on EP Task Name.');
        
        System.assertEquals(System.Today(), results[0].ActivityDate, 'Activity Date should be today.');
        System.assertEquals(System.Today().addDays(1), results[1].ActivityDate, 'Activity Date one day from today.');
        System.assertEquals(System.Today().addDays(2), results[2].ActivityDate, 'Activity Date should be two days from today.');
        System.assertEquals(System.Today().addDays(3), results[3].ActivityDate, 'Activity Date should be three days from today.');

        System.assert(results[0].IsReminderSet, 'Reminder should be set.');
        System.assertEquals(System.Today(), results[0].ReminderDateTime.Date(), 'Reminder should be set today.');

        System.assert(!results[1].IsReminderSet, 'Reminder should not be set for dependent tasks.');
        System.assert(!results[2].IsReminderSet, 'Reminder should not be set for dependent tasks.');
        System.assert(!results[3].IsReminderSet, 'Reminder should not be set for dependent tasks.');

        //update the grandchild days so that we can verify it is recalculated when the grandparent is marked closed
        grandchildEpTask.Days_After__c = 3;
        update grandchildEpTask;

        results[0].Status = UTIL_UnitTestData_TEST.getClosedTaskStatus();
        Test.startTest();
        update results[0];
        Test.stopTest();

        results = [SELECT Id, OwnerId, WhoId, Type, Status, Subject, ActivityDate, Priority, Description,
                   IsReminderSet, ReminderDateTime FROM Task WHERE WhoId = :con.id ORDER BY ActivityDate];

        System.assertEquals(System.Today().addDays(4), results[3].ActivityDate, 'Grandchild Task\'s Activity Date should be updated to four days from today.');

        System.assert(results[1].IsReminderSet, 'Reminder should be set for child tasks.');
        System.assert(results[2].IsReminderSet, 'Reminder should be set for child tasks.');
        System.assert(!results[3].IsReminderSet, 'Reminder should not be set for grandchild tasks.');

    }

    /*********************************************************************************************************
    * @description Creates a template with two EP Tasks, one parent and one child. Then creates an Engagement 
    * Plan using the template. Verifies that tasks are created correctly. Updates the parent task to closed, 
    * and verifies that the child is updated to open, but that the activity date is not updated due to the
    * template Delay Type setting of "After Due Date".
    */
    static testMethod void testDelayType() {

        Contact con = new Contact(LastName = 'foo');
        insert con;

        Engagement_Plan_Template__c template = new Engagement_Plan_Template__c(
            Name = 'Template',
            Skip_Weekends__c = false,
            Default_Assignee__c = EP_EngagementPlans_UTIL.BLANK_ASSIGNMENT_USER_CREATING,
            Automatically_Update_Child_Task_Due_Date__c = false
        );
        insert template;

        Engagement_Plan_Task__c epTask = new Engagement_Plan_Task__c(
            Name = 'Call',
            Engagement_Plan_Template__c = template.id,
            Comments__c = 'This comment should be copied correctly to the Task.',
            Reminder__c = true,
            Send_Email__c = true,
            Days_After__c = 0,
            Type__c = 'Call',
            Priority__c = 'Medium'
        );
        insert epTask;

        Engagement_Plan_Task__c childEpTask = new Engagement_Plan_Task__c(
            Name = 'Email',
            Engagement_Plan_Template__c = template.id,
            Parent_Task__c = epTask.id,
            Comments__c = 'This comment should also be copied correctly to the Task.',
            Reminder__c = true,
            Send_Email__c = true,
            Days_After__c = 1,
            Type__c = 'Email',
            Priority__c = 'Low'
        );
        insert childEpTask;

        Engagement_Plan__c plan = new Engagement_Plan__c(
            Contact__c = con.id,
            Engagement_Plan_Template__c = template.id
        );
        insert plan;

        List<Task> results = [SELECT Id, OwnerId, WhoId, Type, Status, Subject, ActivityDate, Priority, Description,
                            IsReminderSet, ReminderDateTime FROM Task WHERE WhoId = :con.id ORDER BY ActivityDate];
        System.assertEquals(2, results.size(), 'Two tasks should be created.');

        System.assertEquals('Call', results[0].Subject, 'Subject field should be set based on EP Task Name.');
        System.assertEquals('Email', results[1].Subject, 'Subject field should be set based on EP Task Name.');

        System.assertEquals(System.Today(), results[0].ActivityDate, 'Activity Date should be today.');
        System.assertEquals(System.Today().addDays(1), results[1].ActivityDate, 'Activity Date one day from today.');

        //update the child days so that we can verify it is not recalculated when the parent is marked closed
        childEpTask.Days_After__c = 3;
        update childEpTask;

        results[0].Status = UTIL_UnitTestData_TEST.getClosedTaskStatus();
        Test.startTest();
        update results[0];
        Test.stopTest();

        results = [SELECT Id, OwnerId, WhoId, Type, Status, Subject, ActivityDate, Priority, Description,
                   IsReminderSet, ReminderDateTime FROM Task WHERE WhoId = :con.id ORDER BY ActivityDate];

        System.assertEquals(System.Today().addDays(1), results[1].ActivityDate, 'Child Task\'s Activity Date should not have changed.');
        System.assert(results[1].IsReminderSet, 'Reminder should be set for child task.');
    }

    /*********************************************************************************************************
    * @description Creates a template with two EP Tasks, one parent and one child. Then creates an Engagement 
    * Plan using the template. Verifies that tasks are created correctly. Deletes the Engagement Plan, then 
    * updates the parent task to closed, and verifies that no exception is thrown.
    */
    static testMethod void testDeletedPlan() {

        Contact con = new Contact(LastName = 'foo');
        insert con;

        Engagement_Plan_Template__c template = new Engagement_Plan_Template__c(
            Name = 'Template',
            Skip_Weekends__c = false,
            Default_Assignee__c = EP_EngagementPlans_UTIL.BLANK_ASSIGNMENT_USER_CREATING,
            Automatically_Update_Child_Task_Due_Date__c = false
        );
        insert template;

        Engagement_Plan_Task__c epTask = new Engagement_Plan_Task__c(
            Name = 'Call',
            Engagement_Plan_Template__c = template.id,
            Comments__c = 'This comment should be copied correctly to the Task.',
            Reminder__c = true,
            Send_Email__c = true,
            Days_After__c = 0,
            Type__c = 'Call',
            Priority__c = 'Medium'
        );
        insert epTask;

        Engagement_Plan_Task__c childEpTask = new Engagement_Plan_Task__c(
            Name = 'Email',
            Engagement_Plan_Template__c = template.id,
            Parent_Task__c = epTask.id,
            Comments__c = 'This comment should also be copied correctly to the Task.',
            Reminder__c = true,
            Send_Email__c = true,
            Days_After__c = 1,
            Type__c = 'Email',
            Priority__c = 'Low'
        );
        insert childEpTask;

        Engagement_Plan__c plan = new Engagement_Plan__c(
            Contact__c = con.id,
            Engagement_Plan_Template__c = template.id
        );
        insert plan;

        List<Task> results = [SELECT Id, OwnerId, WhoId, Type, Status, Subject, ActivityDate, Priority, Description,
                            IsReminderSet, ReminderDateTime FROM Task WHERE WhoId = :con.id ORDER BY ActivityDate];
        System.assertEquals(2, results.size(), 'Two tasks should be created.');

        delete plan;

        results = [SELECT Engagement_Plan__c, Engagement_Plan_Task__c FROM Task WHERE WhoId = :con.id ORDER BY ActivityDate];
        System.assertEquals(2, results.size(), 'Both tasks should still exist after the plan is deleted.');
        System.assertEquals(null, results[0].Engagement_Plan__c, 'Engagement Plan lookup should be null.');
        System.assertEquals(null, results[1].Engagement_Plan__c, 'Engagement Plan lookup should be null.');
        System.assertNotEquals(null, results[0].Engagement_Plan_Task__c, 'Engagement Plan Task lookup should still exist.');
        System.assertNotEquals(null, results[1].Engagement_Plan_Task__c, 'Engagement Plan Task lookup should still exist.');

        //update the child days so that we can verify it is not recalculated when the parent is marked closed
        results[0].Status = UTIL_UnitTestData_TEST.getClosedTaskStatus();

        Exception ex;

        Test.startTest();
        try {
            update results[0];
        } catch (Exception e) {
            ex = e;
        }
        Test.stopTest();

        System.assertEquals(null, ex, 'No exception should be thrown when the engagement plan is deleted and its task is later updated.');
    }
}