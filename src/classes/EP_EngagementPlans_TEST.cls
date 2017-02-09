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
* @description Tests for Engagement Plan insert, validation, and Task creation.
*/
@isTest
private with sharing class EP_EngagementPlans_TEST {

    /*********************************************************************************************************
    * @description Creates a template with two EP Tasks, one of them dependent on the other. Then creates an
    * Engagement Plan using that template. Verifies that tasks are created, that fields are copied over, and
    * that both non-dependent and dependent tasks use the correct defaults.
    */
    static testMethod void testDependentPlan() {

        Id otherUserId = getOtherUserId();

        Contact con = new Contact(LastName = 'foo');
        insert con;

        Engagement_Plan_Template__c template = new Engagement_Plan_Template__c(
            Name = 'Template',
            Skip_Weekends__c = false,
            Default_Assignee__c = EP_EngagementPlans_UTIL.BLANK_ASSIGNMENT_USER_CREATING
        );
        insert template;

        Engagement_Plan_Task__c epTask = new Engagement_Plan_Task__c(
            Name = 'Call',
            Engagement_Plan_Template__c = template.id,
            Assigned_To__c = otherUserId,
            Comments__c = 'This comment should be copied correctly to the Task.',
            Reminder__c = true,
            Reminder_Time__c = '660',
            Send_Email__c = true,
            Days_After__c = 3,
            Type__c = 'Call'
        );
        insert epTask;

        Engagement_Plan_Task__c dependentEpTask = new Engagement_Plan_Task__c(
            Name = 'Email',
            Engagement_Plan_Template__c = template.id,
            Parent_Task__c = epTask.id,
            Comments__c = 'This comment should also be copied correctly to the Task.',
            Reminder__c = true,
            Reminder_Time__c = '780',
            Send_Email__c = true,
            Days_After__c = 10,
            Type__c = 'Email',
            Priority__c = 'Low'
        );
        insert dependentEpTask;

        Engagement_Plan__c plan = new Engagement_Plan__c(
            Contact__c = con.id,
            Engagement_Plan_Template__c = template.id
        );

        Test.startTest();
        insert plan;
        Test.stopTest();

        List<Task> results = [SELECT Id, OwnerId, WhoId, Type, Status, Subject, ActivityDate, Priority, Description,
                            IsReminderSet, ReminderDateTime FROM Task WHERE WhoId = :con.id ORDER BY ActivityDate];
        System.assertEquals(2, results.size(), 'Two tasks should be created.');

        System.assertEquals(epTask.Name, results[0].Subject, 'Subject field should be set based on EP Task Name.');
        System.assertEquals(epTask.Comments__c, results[0].Description, 'Comment field should be copied correctly.');
        
        System.assertEquals(System.Today().addDays(3), results[0].ActivityDate, 'Activity Date should be three days from today.');
        System.assert(results[0].IsReminderSet, 'Reminder should be set.');
        System.assertEquals(System.Today().addDays(3), results[0].ReminderDateTime.Date(), 'Reminder should be set three days from today.');
        System.assertEquals(UTIL_Describe.getDefaultSelectOption('Task', 'Priority'), results[0].Priority, 'Priority field left blank should be set based on default picklist value.');
        System.assertEquals(otherUserId, results[0].OwnerId, 'Task owner should be based on EP Task Assigned To field.');

        System.assertEquals(dependentEpTask.Name, results[1].Subject, 'Subject field should be set based on EP Task Name.');
        System.assertEquals(dependentEpTask.Comments__c, results[1].Description, 'Comment field should be copied correctly.');
        System.assertEquals(System.Today().addDays(13), results[1].ActivityDate, 'Activity Date should be set thirteen days from today.');
        System.assert(!results[1].IsReminderSet, 'Reminder should not be set for a dependent task.');
        System.assertEquals(dependentEpTask.Priority__c, results[1].Priority, 'Priority field should be set based on EP Task Priority.');
        System.assertEquals(UserInfo.getUserId(), results[1].OwnerId, 'Task owner left blank should be based on running user.');
    }

    /*********************************************************************************************************
    * @description Creates an EP Task with a delay of the number of days until the weekend, as part of a 
    * template set to delay until Monday. Verifies that the activity date is set on Monday.
    */
    static testMethod void testSkipWeekendsMonday() {

        Integer daysUntilWeekend = getDaysUntilWeekend(System.today());
        
        Contact con = new Contact(LastName = 'foo');
        insert con;

        Engagement_Plan_Template__c template = new Engagement_Plan_Template__c(
            Name = 'TemplateSkipWeekends',
            Skip_Weekends__c = true,
            Reschedule_To__c = EP_EngagementPlans_UTIL.SKIP_DAY_MONDAY,
            Default_Assignee__c = EP_EngagementPlans_UTIL.BLANK_ASSIGNMENT_USER_CREATING
        );
        insert template;

        Engagement_Plan_Task__c epTask = new Engagement_Plan_Task__c(
            Name = 'Call',
            Engagement_Plan_Template__c = template.id,
            Comments__c = 'This comment should be copied correctly to the Task.',
            Reminder__c = true,
            Reminder_Time__c = '660',
            Type__c = 'Call',
            Priority__c = 'Medium',
            //use the number of days until the weekend for the delay of this task
            Days_After__c = daysUntilWeekend
        );
        insert epTask;

        Engagement_Plan__c plan = new Engagement_Plan__c(
            Contact__c = con.id,
            Engagement_Plan_Template__c = template.id
        );

        Test.startTest();
        insert plan;
        Test.stopTest();

        List<Task> results = [SELECT Id, OwnerId, WhoId, Type, Status, Subject, ActivityDate, Priority, Description,
                            IsReminderSet, ReminderDateTime FROM Task WHERE WhoId = :con.id ORDER BY ActivityDate];
        System.assertEquals(1, results.size(), 'A task should be created.');

        System.assertEquals(epTask.Name, results[0].Subject, 'Subject field should be set based on EP Task Name.');
        System.assertEquals(epTask.Comments__c, results[0].Description, 'The comment should be copied correctly to the Task Description.');
        
        System.assert(
            results[0].ActivityDate > System.today().addDays(daysUntilWeekend), 
            'Activity Date ('+results[0].ActivityDate+') should be set after the weekend. ' + System.today().addDays(daysUntilWeekend)
        );
        System.assert(results[0].IsReminderSet, 'Reminder should be set.');
        System.assert(results[0].ReminderDateTime.Date() > System.now().addDays(daysUntilWeekend), 'Reminder should be set after the weekend.');
        System.assertEquals(results[0].ReminderDateTime.Date(), results[0].ActivityDate, 'Reminder date and activity date should be on the same day.');
        
        //verify minutes
        Time reminderTimeMidnight = Time.newInstance(0,0,0,0);
        DateTime reminderDateTimeMidnight = DateTime.newInstance(results[0].ActivityDate, reminderTimeMidnight);
        System.assertEquals(ReminderDateTimeMidnight.addMinutes(660), results[0].ReminderDateTime, 'Reminder should be set 660 minutes after midnight.');

        System.assertEquals(epTask.Priority__c, results[0].Priority, 'Priority field should be set based on EP Task Priority.');
    }

    /*********************************************************************************************************
    * @description Creates an EP Task with a delay of the number of days until the weekend, as part of a 
    * template set to advance the date to Friday. Verifies that the activity date is set on Friday.
    */
    static testMethod void testSkipWeekendsFriday() {

        Integer daysUntilWeekend = getDaysUntilWeekend(System.today());
        
        Contact con = new Contact(LastName = 'foo');
        insert con;

        Engagement_Plan_Template__c template = new Engagement_Plan_Template__c(
            Name = 'TemplateSkipWeekends',
            Skip_Weekends__c = true,
            Reschedule_To__c = EP_EngagementPlans_UTIL.SKIP_DAY_FRIDAY
        );
        insert template;

        Engagement_Plan_Task__c epTask = new Engagement_Plan_Task__c(
            Name = 'Call',
            Engagement_Plan_Template__c = template.id,
            Comments__c = 'This comment should be copied correctly to the Task.',
            Reminder__c = true,
            Reminder_Time__c = '660',
            Type__c = 'Call',
            Priority__c = 'Medium',
            //use the number of days until the weekend for the delay of this task
            Days_After__c = daysUntilWeekend
        );
        insert epTask;

        Engagement_Plan__c plan = new Engagement_Plan__c(
            Contact__c = con.id,
            Engagement_Plan_Template__c = template.id
        );

        Test.startTest();
        insert plan;
        Test.stopTest();

        List<Task> results = [SELECT Id, OwnerId, WhoId, Type, Status, Subject, ActivityDate, Priority, Description,
                            IsReminderSet, ReminderDateTime FROM Task WHERE WhoId = :con.id ORDER BY ActivityDate];
        System.assertEquals(1, results.size(), 'A task should be created.');

        System.assertEquals(epTask.Name, results[0].Subject, 'Subject field should be set based on EP Task Name.');
        System.assertEquals(epTask.Comments__c, results[0].Description, 'The comment should be copied correctly to the Task Description.');
        
        System.assert(
            results[0].ActivityDate < System.today().addDays(daysUntilWeekend), 
            'Activity Date ('+results[0].ActivityDate+') should be set before the weekend. ' + System.today().addDays(daysUntilWeekend)
        );
        System.assert(results[0].IsReminderSet, 'Reminder should be set.');
        System.assert(results[0].ReminderDateTime.Date() < System.now().addDays(daysUntilWeekend), 'Reminder should be set after the weekend.');
        System.assertEquals(results[0].ReminderDateTime.Date(), results[0].ActivityDate, 'Reminder date and activity date should be on the same day.');
        
        //verify minutes
        Time reminderTimeMidnight = Time.newInstance(0,0,0,0);
        DateTime reminderDateTimeMidnight = DateTime.newInstance(results[0].ActivityDate, reminderTimeMidnight);
        System.assertEquals(ReminderDateTimeMidnight.addMinutes(660), results[0].ReminderDateTime, 'Reminder should be set 660 minutes after midnight.');

        System.assertEquals(epTask.Priority__c, results[0].Priority, 'Priority field should be set based on EP Task Priority.');
    }


    /*********************************************************************************************************
    * @description Creates an EP Template with "Owner of Receiving Object" assignment rule, an EP Task, and an
    * Engagement Plan associated with an Account. Verifies that WhatId is used for Engagement Plans assigned
    * to objects other than Contact and Lead, and that the task assignment correctly uses the Account's owner.
    */
    static testMethod void testTaskAssignment() {

        Integer daysUntilWeekend = getDaysUntilWeekend(System.today());
        Id otherUserId = getOtherUserId();
        
        Account acc = new Account(Name = 'foo', OwnerId = otherUserId);
        insert acc;

        Engagement_Plan_Template__c template = new Engagement_Plan_Template__c(
            Name = 'TemplateTaskAssignment',
            Default_Assignee__c = EP_EngagementPlans_UTIL.BLANK_ASSIGNMENT_OWNER_RECEIVING
        );
        insert template;

        Engagement_Plan_Task__c epTask = new Engagement_Plan_Task__c(
            Name = 'Call',
            Engagement_Plan_Template__c = template.id,
            Type__c = 'Call',
            Priority__c = 'Medium'
        );
        insert epTask;

        Engagement_Plan__c plan = new Engagement_Plan__c(
            Account__c = acc.id,
            Engagement_Plan_Template__c = template.id
        );

        Test.startTest();
        insert plan;
        Test.stopTest();

        List<Task> results = [SELECT Id, OwnerId, WhatId, Type, Status, Subject, ActivityDate, Priority, Description,
                            IsReminderSet, ReminderDateTime FROM Task WHERE WhatId = :acc.id];
        System.assertEquals(1, results.size(), 'A task should be created.');
        System.assertEquals(acc.id, results[0].WhatId, 'WhatId should be used for task assignment of objects other than contact/lead.');
        System.assertEquals(acc.ownerId, results[0].OwnerId, 'Owner of Receiving Object assignment rule: the created task shoould be assigned to the account owner, not the running user.');
    }

    /*********************************************************************************************************
    * @description Inserts an Engagement Plan without an object lookup, verifies exception is thrown.
    */
    static testMethod void testPlanWithoutLookup() {

        Engagement_Plan_Template__c template = new Engagement_Plan_Template__c(Name = 'Template');
        insert template;

        Engagement_Plan__c plan = new Engagement_Plan__c(
            Engagement_Plan_Template__c = template.id
        );

        Exception caughtException;
        Test.startTest();
        try {
            insert plan;
        } catch (Exception ex) {
            caughtException = ex;
        }
        Test.stopTest();

        System.assertNotEquals(null, caughtException, 'An exception should be caught.');
        System.assert(caughtException.getMessage().contains(Label.engagementPlanNoLookups), 
            'The exception should have the correct message. Actual message: ' + caughtException.getMessage());
    }

    /*********************************************************************************************************
    * @description Inserts an Engagement Plan with two object lookups, verifies exception is thrown.
    */
    static testMethod void testPlanWithTwoLookups() {

        Account acc = new Account(Name='foo');
        insert acc;
        Contact con = new Contact(LastName='foo', AccountId = acc.id);
        insert con;
        Engagement_Plan_Template__c template = new Engagement_Plan_Template__c(Name = 'Template');
        insert template;

        Engagement_Plan__c plan = new Engagement_Plan__c(
            Engagement_Plan_Template__c = template.id,
            Contact__c = con.id,
            Account__c = acc.id
        );

        Exception caughtException;
        Test.startTest();
        try {
            insert plan;
        } catch (Exception ex) {
            caughtException = ex;
        }
        Test.stopTest();

        System.assertNotEquals(null, caughtException, 'An exception should be caught.');
        System.assert(caughtException.getMessage().contains(Label.engagementPlanTwoLookups));
    }

    /*********************************************************************************************************
    * @description Updates an engagement plan to change the value of the  lookup, verifies an exception is thrown.
    */
    static testMethod void testPlanUpdate() {

        Account acc = new Account(Name='foo');
        insert acc;
        Account acc2 = new Account(Name='foo2');
        insert acc2;
        Engagement_Plan_Template__c template = new Engagement_Plan_Template__c(Name = 'Template');
        insert template;

        Engagement_Plan__c plan = new Engagement_Plan__c(
            Engagement_Plan_Template__c = template.id,
            Account__c = acc.id
        );
        insert plan;

        Exception caughtException;
        Test.startTest();
        try {
            plan.Account__c = acc2.id;
            update plan;
        } catch (Exception ex) {
            caughtException = ex;
        }
        Test.stopTest();

        System.assertNotEquals(null, caughtException, 'An exception should be caught.');
        System.assert(caughtException.getMessage().contains(Label.engagementPlanCantEdit));
    }
    
    /*********************************************************************************************************
    * @description tests the helper method that finds a path to ownerId.
    */
    static testmethod void test_getsobjectparentpath() {
        System.assertEquals(new List<String>{'OwnerId'},EP_EngagementPlans_UTIL.getOwnerIdPath(Opportunity.SObjectType));
        System.assertEquals(new List<String>{UTIL_Namespace.StrTokenNSPrefix('Household_Account__r'),'OwnerId'},EP_EngagementPlans_UTIL.getOwnerIdPath(Address__c.SObjectType));
    }

    /*********************************************************************************************************
    * @description Returns the user id of an active user other than the current one if one exists, 
    * otherwise returns the current user id.
    */
    private static Id getOtherUserId() {
        List<User> otherUsers = [SELECT Id FROM User WHERE IsActive = true AND Id != :UserInfo.getUserId() AND UserType = 'Standard' LIMIT 1];
        if (otherUsers.isEmpty()) {
            return UserInfo.getUserId();
        } else {
            return otherUsers[0].Id;
        }
    }

    /*********************************************************************************************************
    * @description Calculates the number of days from today until the weekend.
    **/
    private static Integer getDaysUntilWeekend(Date originalDate) {
        Date refDate = Date.newInstance(1900, 1, 7);  // Jan 7 1900 = Sunday
        Integer dayOfWeek = Math.mod(refDate.daysBetween(originalDate), 7); // 0 = Sunday ... 6 = Saturday
        //if we're already on a saturday or sunday, no offset is needed
        if (dayOfWeek == 0 || dayOfWeek == 6) {
            return 0;
        //otherwise, return the number of days until Saturday
        } else {
            return 6 - dayOfWeek;
        }
    }
}