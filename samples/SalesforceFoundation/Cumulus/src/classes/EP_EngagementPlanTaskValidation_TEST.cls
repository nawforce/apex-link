/*
    Copyright (c) 2016 Salesforce.org
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
* @description Test Trigger Handler on Engagement Plan Tasks 
*/
@isTest
public class EP_EngagementPlanTaskValidation_TEST {
            
    /*******************************************************************************************************
    * @description Insert a single Engagment Plan Task Dependent on an existing task.
    */ 
    static testmethod void testSingleEPDependentTask() {
        List<Engagement_Plan_Template__c> templateTests = UTIL_UnitTestData_TEST.createEPTemplates(1);
        insert templateTests;
        List<Engagement_Plan_Task__c> taskTests = UTIL_UnitTestData_TEST.createEPTasksForTemplates(2, templateTests);
        insert taskTests;
        Engagement_Plan_Task__c taskTest = new Engagement_Plan_Task__c(
            Engagement_Plan_Template__c = taskTests[0].Engagement_Plan_Template__c, Parent_Task__c = taskTests[0].Id);
        
        Test.startTest();
        insert taskTest;
        Test.stopTest();

        System.assertNotEquals(null, taskTest.Id, 'The EP task is valid and when inserted should have returned an Id.');
    }

    /*******************************************************************************************************
    * @description Insert multiple Engagment Plan Tasks Dependent on an existing task.
    */ 
    static testmethod void testMultipleEPDependentTask() {
        List<Engagement_Plan_Template__c> templateTests = UTIL_UnitTestData_TEST.createEPTemplates(1);
        insert templateTests;
        List<Engagement_Plan_Task__c> taskTests = UTIL_UnitTestData_TEST.createEPTasksForTemplates(10, templateTests);
        insert taskTests;
        List<Engagement_Plan_Task__c> dependentTaskTests = new List<Engagement_Plan_Task__c>();

        for (Engagement_Plan_Task__c taskTest : taskTests) {
            dependentTaskTests.add(new Engagement_Plan_Task__c(
                Name = taskTest.Name + ' Dependent',
                Engagement_Plan_Template__c = templateTests[0].Id,
                Parent_Task__c = taskTest.Id));
        }

        Test.startTest();
        insert dependentTaskTests;
        Test.stopTest();

        List<Engagement_Plan_Task__c> taskTestsNew = [SELECT Id, (SELECT Id FROM Engagement_Plan_Tasks__r)
            FROM Engagement_Plan_Template__c LIMIT 1].Engagement_Plan_Tasks__r;
        System.assertEquals(taskTests.size() * 2, taskTestsNew.size(), 'The total task size should have doubled.');
    }

    /*******************************************************************************************************
    * @description Update EP Tasks with redundant dependencies. e.g. task A is dependent on C, which is
    * dependent on B which is dependent on A
    */ 
    static testmethod void testRedundantEPDependentTask() {
        List<Engagement_Plan_Template__c> templateTests = UTIL_UnitTestData_TEST.createEPTemplates(1);
        insert templateTests;
        List<Engagement_Plan_Task__c> taskTests = UTIL_UnitTestData_TEST.createEPTasksForTemplates(4, templateTests);
        insert taskTests;
        taskTests[0].Parent_Task__c = taskTests[2].Id;
        taskTests[1].Parent_Task__c = taskTests[0].Id;
        taskTests[2].Parent_Task__c = taskTests[1].Id;

        Test.startTest();
        try {
            update taskTests;
            System.assert(false, 'An EP task with a circular dependency should fail insert.');
        } catch (Exception e) {
            System.assert(e.getMessage().contains(Label.EPTaskDependency));
        }
        Test.stopTest();
    }

    /*******************************************************************************************************
    * @description Insert EP Tasks with dependency task from a different template.
    */ 
    static testmethod void testCrossTemplateEPDependentTask() {
        List<Engagement_Plan_Template__c> templateTests = UTIL_UnitTestData_TEST.createEPTemplates(2);
        insert templateTests;
        List<Engagement_Plan_Task__c> taskTests =
            UTIL_UnitTestData_TEST.createEPTasksForTemplates(2, new List<Engagement_Plan_Template__c>{templateTests[0]});
        insert taskTests;

        Engagement_Plan_Task__c crossTemplateTask = taskTests[0].clone();
        crossTemplateTask.Parent_Task__c = taskTests[0].Id;
        crossTemplateTask.Engagement_Plan_Template__c = templateTests[1].Id;

        Test.startTest();
        try {
            insert crossTemplateTask;
            System.assert(false, 'An EP task should fail if dependent task belongs to a different template.');
        } catch (Exception e) {
            System.assert(e.getMessage().contains(Label.EPTaskTemplate));
        }
        Test.stopTest();
    }

    /*******************************************************************************************************
    * @description Update EP Task that other tasks are dependent on and change its template.
    */ 
    static testmethod void testUpdateCrossTemplateEPDependentTask() {
        List<Engagement_Plan_Template__c> templateTests = UTIL_UnitTestData_TEST.createEPTemplates(2);
        insert templateTests;
        List<Engagement_Plan_Task__c> taskTests =
            UTIL_UnitTestData_TEST.createEPTasksForTemplates(3, new List<Engagement_Plan_Template__c>{templateTests[0]});
        insert taskTests;

        taskTests[0].Parent_Task__c = taskTests[2].Id;
        taskTests[1].Parent_Task__c = taskTests[2].Id;
        update taskTests;

        taskTests[2].Engagement_Plan_Template__c = templateTests[1].Id;

        Test.startTest();
        try {
            update taskTests[2];
            System.assert(false, 'An EP task should fail if tasks that are dependent on it belongs to a different template.');
        } catch (Exception e) {
            System.assert(e.getMessage().contains(Label.EPTaskTemplate));
        }
        Test.stopTest();
    }

    /*******************************************************************************************************
    * @description Update EP Task template that is dependent on another tasks with a different template.
    */ 
    static testmethod void testUpdateTemplateWithEPDependentTask() {
        List<Engagement_Plan_Template__c> templateTests = UTIL_UnitTestData_TEST.createEPTemplates(2);
        insert templateTests;
        List<Engagement_Plan_Task__c> taskTests =
            UTIL_UnitTestData_TEST.createEPTasksForTemplates(3, new List<Engagement_Plan_Template__c>{templateTests[0]});
        insert taskTests;

        taskTests[0].Parent_Task__c = taskTests[2].Id;
        taskTests[1].Parent_Task__c = taskTests[2].Id;
        update taskTests;

        taskTests[1].Engagement_Plan_Template__c = templateTests[1].Id;

        Test.startTest();
        try {
            update taskTests[1];
            System.assert(false, 'An EP task should fail when template changes and it is dependent on another task from a different template.');
        } catch (Exception e) {
            System.assert(e.getMessage().contains(Label.EPTaskTemplate));
        }
        Test.stopTest();
    }
}