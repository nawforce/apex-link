/*
    Copyright (c) 2014, Salesforce.org
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
* @group Account Model Converter
* @group-content ../../ApexDocContent/Conv.htm
* @description Test class to support the Account model conversion utility controller
*/

@isTest
public with sharing class CONV_Account_Conversion_CTRL_TEST {
    /*******************************************************************************************************
    * @description Envokes a successful run of the controller
    */
    public static testmethod void testConversionCtrl() {

        //set account model to 1:1 for the test and enable household creation
        CONV_Account_Conversion_BATCH_TEST.setPreHHAccountModel(true);

        Contact c = new Contact(LastName = 'Contact', FirstName = 'Test');
        insert c;
        system.assertEquals(1, [select count() from Account where Name = 'Test Contact' and npe01__One2OneContact__c = :c.id]);
        String HHName = 'Contact ' + system.label.npo02.DefaultHouseholdName;
        system.assertEquals(1, [select count() from npo02__Household__c where Name = :HHName]);

        //add an oppty to the Contact as well
        Opportunity o = new Opportunity(Name = 'Test Oppty', Amount = 1000, StageName = UTIL_UnitTestData_TEST.getClosedWonStage(), CloseDate = system.today(), Primary_Contact__c = c.Id);
        insert o;
        system.assertEquals(1, [select count() from OpportunityContactRole where isPrimary = true and ContactId = :c.Id]);

        Test.startTest();

        // create the page
        PageReference pageRef=Page.CONV_Account_Conversion;
        Test.setCurrentPage(pageRef);

        // instantiate the controller
        CONV_Account_Conversion_CTRL controller = new CONV_Account_Conversion_CTRL();
        AsyncApexJob aaj = controller.apexjob;
        system.assertEquals(null, aaj);
        List<SelectOption> testList = controller.getTargetAccountFields();

        // convert up everyone
        controller.convert();
        controller.cancel();

        Test.stopTest();

        aaj = controller.apexjob;
        system.assertNotEquals(null, aaj.Id);
        system.assertEquals('Completed', aaj.Status);
        Boolean testBool;
        testBool = CONV_Account_Conversion_CTRL.verifyFieldTypeForIds('garbagefield');
        system.assertEquals(false, testBool);

        testBool = CONV_Account_Conversion_CTRL.verifyFieldTypeForIds('Name');
        system.assert(testBool);
    }
    /*******************************************************************************************************
    * @description Envokes the controller and triggers an error state
    */
    public static testMethod void testConversionCTRLErrorState(){

        //set account model to 1:1 for the test and enable household creation
        CONV_Account_Conversion_BATCH_TEST.setPreHHAccountModel(true);
        //create a new user w/ sysadmin profile
        Id sysadminId = [select id from Profile where Name = 'System Administrator' limit 1].Id;
        User u = new User(LastName = 'TestUser', Email = 'UserEmail@email.com', ProfileId = sysadminId,
                            isActive = true, UserName = 'UserEmail@email.com',
                            Alias = 'tu093521', TimeZoneSidKey = 'America/Los_Angeles',
                            LocaleSidKey = 'en_US', LanguageLocaleKey = 'en_US',
                            EmailEncodingKey = 'ISO-8859-1');
        insert u;

        Contact c = new Contact(LastName = 'Contact', FirstName = 'Test', OwnerId = u.Id);
        insert c;

        Account a = new Account(OwnerId = u.Id, Name= 'Test Account');
        insert a;

        Task t = new Task(WhatId = a.Id, OwnerId = u.Id, Status = 'Completed', Subject = 'Other');
        insert t;

        system.assertEquals(u.Id, [select OwnerId from Account where Id = :a.Id].OwnerId);
        system.assertEquals(u.Id, [select OwnerId from Contact where Id = :c.Id].OwnerId);
        system.assertEquals(u.Id, [select OwnerId from Task where Id = :t.Id].OwnerId);

        //add an oppty to the Contact as well
        Opportunity o = new Opportunity(OwnerId = u.Id, Name = 'Test Oppty', Amount = 1000, StageName = UTIL_UnitTestData_TEST.getClosedWonStage(), CloseDate = system.today(), Primary_Contact__c = c.Id);
        insert o;
        system.assertEquals(1, [select count() from OpportunityContactRole where isPrimary = true and ContactId = :c.Id]);

        User thisUser = [SELECT Id FROM User WHERE Id = :UserInfo.getUserId()];

       // Insert account as current user context
        System.runAs (thisUser) {

            Test.startTest();
            //inactive the user
            u.isActive = false;
            update u;

            // create the page
            PageReference pageRef=Page.CONV_Account_Conversion;
            Test.setCurrentPage(pageRef);

            // instantiate the controller
            CONV_Account_Conversion_CTRL controller = new CONV_Account_Conversion_CTRL();
            controller.runCheck();

            Test.stopTest();

            //assert the warning exists
            system.assertEquals(true, controller.runCheckErrorState);        
        }
    }
}