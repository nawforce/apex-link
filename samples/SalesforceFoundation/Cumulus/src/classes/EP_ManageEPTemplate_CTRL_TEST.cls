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
* @description Tests for Manage Engagement Plan Template UI.
*/
@isTest
private with sharing class EP_ManageEPTemplate_CTRL_TEST {

    /*********************************************************************************************************
    * @description Creates a template with two tasks, one the child of the other. Tests out buttons and 
    * verifies results.
    */
    static testMethod void testDependentPlan() {
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
            //Assigned_To__c = otherUserId,
            Comments__c = 'This comment should be copied correctly to the Task.',
            Reminder__c = true,
            Reminder_Time__c = '660',
            Send_Email__c = true,
            Days_After__c = 3,
            Type__c = 'Call',
            Priority__c = 'Medium'
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

        Test.startTest();
        PageReference pageRef = Page.EP_ManageEPTemplate;
        pageRef.getParameters().put('id', template.Id);
        Test.setCurrentPage(pageRef);

        ApexPages.StandardController standardController = new ApexPages.StandardController(template);
        EP_ManageEPTemplate_CTRL ctrl = new EP_ManageEPTemplate_CTRL(standardController);

        //get the dynamic component to initialize everything
        ctrl.getTaskTree();
        Test.stopTest();

        system.assert(ctrl.mapTaskWrappers.containsKey('1'), 'EP Task with level of 1 should exist.');
        system.assert(ctrl.mapTaskWrappers.containsKey('1-1'), 'EP Task with level of 1-1 should exist.');
        system.assert(!ctrl.mapTaskWrappers.containsKey('2'), 'EP Task with level of 2 should not exist yet.');

        //add parent level task and verify it exists
        ctrl.addTask();

        //since getTaskTree() is run on every rerender, we have to run it manually to fill mapTaskWrapper
        ctrl.getTaskTree();
        system.assert(ctrl.mapTaskWrappers.containsKey('2'), 'EP Task with level of 2 should exist now.');
        ctrl.mapTaskWrappers.get('2').detail.put('Name', 'foo');

        ctrl.currentTaskLevel = '1-1';
        ctrl.addDependentTask();
        ctrl.getTaskTree();
        system.assert(ctrl.mapTaskWrappers.containsKey('1-1-1'), 'EP Task with level of 1-1-1 should be created.');
        ctrl.mapTaskWrappers.get('1-1-1').detail.put('Name', 'stuff');

        ctrl.currentTaskLevel = '1-1-1';
        ctrl.addDependentTask();
        ctrl.getTaskTree();
        system.assert(ctrl.mapTaskWrappers.containsKey('1-1-1-1'), 'EP Task with level of 1-1-1-1 should be created.');
        ctrl.mapTaskWrappers.get('1-1-1-1').detail.put('Name', 'thing');

        ctrl.currentTaskLevel = '1-1';
        ctrl.deleteTask();
        ctrl.getTaskTree();
        system.assert(!ctrl.mapTaskWrappers.containsKey('1-1-1-1'), 
            'EP Task with level of 1-1-1-1 should be gone as its grandparent was deleted.');

        ctrl.saveClose();

        Map<Id,Engagement_Plan_Task__c> results = new Map<Id,Engagement_Plan_Task__c>(
            [SELECT Id, Name FROM Engagement_Plan_Task__c]
        );

        System.assertEquals(4,results.values().size(), 'Four Engagement Plan Tasks should be saved.');
        System.assert(!results.containsKey(dependentEpTask.id), 'Deleted task should no longer exist.');

    }

    /*********************************************************************************************************
    * @description Creates a template with two tasks, one the child of the other. Clones it, and verifies
    * everything was cloned correctly.
    */
    static testMethod void testClone() {
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
            //Assigned_To__c = otherUserId,
            Comments__c = 'This comment should be copied correctly to the Task.',
            Reminder__c = true,
            Reminder_Time__c = '660',
            Send_Email__c = true,
            Days_After__c = 3,
            Type__c = 'Call',
            Priority__c = 'Medium'
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

        Test.startTest();
        PageReference pageRef = Page.EP_ManageEPTemplate;
        pageRef.getParameters().put('id', template.Id);
        pageRef.getParameters().put('clone', '1');
        Test.setCurrentPage(pageRef);

        ApexPages.StandardController standardController = new ApexPages.StandardController(template);
        EP_ManageEPTemplate_CTRL ctrl = new EP_ManageEPTemplate_CTRL(standardController);

        //get the dynamic component to initialize everything
        ctrl.getTaskTree();
        ctrl.saveClose();
        Test.stopTest();

        List<Engagement_Plan_Template__c> clonedTemplate = [SELECT Id, Name, Skip_Weekends__c, Default_Assignee__c
                                                            FROM Engagement_Plan_Template__c WHERE Id != :template.id];
        System.assertEquals(1, clonedTemplate.size(), 'A new cloned template should exist.');
        System.assert(clonedTemplate[0].Name.contains(Label.lblCloned), 'Cloned template should have the Cloned label.');
        System.assertEquals(template.Skip_Weekends__c, clonedTemplate[0].Skip_Weekends__c, 
            'Clone should have the same Skip Weekend field value as the original.');
        System.assertEquals(template.Default_Assignee__c, clonedTemplate[0].Default_Assignee__c, 
            'Cloned Template should have the same Default Assignee field value as the original.');

        List<Engagement_Plan_Task__c> tasks = [SELECT Id, Parent_Task__c, Name FROM Engagement_Plan_Task__c 
                                               WHERE Id != :epTask.id AND Id != :dependentEpTask.id 
                                               ORDER BY Parent_Task__c NULLS FIRST];
        System.assertEquals(2, tasks.size(), 'Two cloned tasks should exist. ' + epTask.id + ' ' + ctrl.mapTaskWrappers.values()[0].detail);
        System.assertEquals(epTask.Name, tasks[0].Name, 'Cloned task should have the same name as the original.');
        System.assertEquals(dependentEpTask.Name, tasks[1].Name, 
            'Cloned task should have the same name as the original.');
        System.assertEquals(tasks[0].id, tasks[1].Parent_Task__c, 
            'Dependent task should have the cloned task as parent.');
    }
}