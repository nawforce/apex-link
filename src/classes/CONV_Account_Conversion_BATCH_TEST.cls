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
* @description Tests the batch job for the Account Model converter
*/
@isTest
public with sharing class CONV_Account_Conversion_BATCH_TEST {

    /*******************************************************************************************************
    * @description Sets the test methods in the class you want to run for development testing purposes
    */
    private static string strTestOnly = '*';

    /*******************************************************************************************************
    * @description Tests the conversion process for 1to1 Contacts & Households to Household Contacts and Household Accounts
    */
    public testmethod static void testAccountConversion1to1(){
        if (strTestOnly != '*' && strTestOnly != 'testAccountConversion1to1') return;
        //setup 1x1 model and test account creation
        CONV_Account_Conversion_BATCH_TEST.setPreHHAccountModel(true);

        List<Contact> cons = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(150);
        insert cons;
        system.assertEquals(150, [select count() from Account where npe01__One2OneContact__c IN :cons]);

        String HHName = CAO_Constants.CONTACT_LASTNAME_FOR_TESTS + ' ' + system.label.npo02.DefaultHouseholdName;
        List<npo02__Household__c> hhlist = [select id from npo02__Household__c where Name = :HHName];
        system.assertEquals(150, hhlist.size());

        //add a second contact to a random household
        Contact c = new Contact(FirstName = 'Second', LastName = 'HouseholdMember', npo02__Household__c = hhlist[14].Id);
        insert c;

        //add opptys to the Contacts as well
        List<Opportunity> opps = UTIL_UnitTestData_TEST.OppsForContactList(cons, null, UTIL_UnitTestData_TEST.getClosedWonStage(), system.today(), 100, null, '');
        //Opportunity o = new Opportunity(Name = 'Test Oppty', Amount = 1000, StageName = UTIL_UnitTestData_TEST.getClosedWonStage(), CloseDate = system.today(), Primary_Contact__c = c.Id);
        insert opps;
        system.assertEquals(150, [select count() from OpportunityContactRole where isPrimary = true and ContactId IN :cons]);

        Task t = new Task(WhatId = c.npo02__Household__c, Subject = 'TestTask', Status = 'Open');
        insert t;

        //set the HH Account model before entering the batch
        CONV_Account_Conversion_BATCH_TEST.setHHAccountModel();

        Test.StartTest();

        String query = CONV_Account_Conversion_CTRL.getBatchQuery();

        CONV_Account_Conversion_BATCH cacv = new CONV_Account_Conversion_BATCH(query, null, null);

        ID batchprocessid = Database.executeBatch(cacv);
        Test.StopTest();

        //get the newly created Households
        List<Account> newHHAccounts = [select npo02__TotalOppAmount__c, Id from Account where Name = :HHName];
        //should be total HH count from above
        system.assertEquals(150, newHHAccounts.size());
        //total gifts should be the same for any of the 149
        system.assertEquals(newHHAccounts[64].npo02__TotalOppAmount__c, 100.00);
        //our two-person household was also successfully converted and set the correct primary contact
        Contact c2 = [select AccountId, Account.npe01__One2OneContact__c from Contact where Id = :c.Id];
        //the primary was set
        system.assertNotEquals(null, Account.npe01__One2OneContact__c);
        //the primary is the contact w/ a total opp amount
        system.assertNotEquals(c2.Id, c2.Account.npe01__One2OneContact__c);
        system.assertEquals(2, [select count() from Contact where AccountId = :c2.AccountId]);
        //assert all opptys have been moved over
        system.assertEquals(150, [select count() from Opportunity where AccountId IN :newHHAccounts]);
        //assert the task was ported as well
        system.assertEquals([select WhatId from Task where Subject = 'TestTask' limit 1].WhatId, c2.AccountId);
        system.assert(CAO_Constants.isHHAccountModel());
        //Using the API class just to give it test coverage.
        system.assertEquals(UTIL_CustomSettings_API.getHouseholdsSettings().npo02__Household_Rules__c, HH_Households.NO_HOUSEHOLDS_PROCESSOR);
        system.assertEquals(0, [select count() from npe5__Affiliation__c]);
    }
    /*******************************************************************************************************
    * @description Tests the conversion process for Bucket Contacts & Households to Household Contacts and Household Accounts
    */
    public testmethod static void testAccountConversionBucket(){
        if (strTestOnly != '*' && strTestOnly != 'testAccountConversionBucket') return;

        //setup bucket model and test account creation
        CONV_Account_Conversion_BATCH_TEST.setPreHHAccountModel(false);

        Contact c = new Contact(LastName = 'Contact', FirstName = 'Test');
        insert c;
        Id indID = [select id from Account where Name = 'Individual'].id;

        system.assertEquals(indID, CAO_Constants.GetIndividualAccountId());

        String HHName = 'Contact ' + system.label.npo02.DefaultHouseholdName;
        system.assertEquals(1, [select count() from npo02__Household__c where Name = :HHName]);

        //add an oppty to the Contact as well
        Opportunity o = new Opportunity(Name = 'Test Oppty', Amount = 1000, StageName = UTIL_UnitTestData_TEST.getClosedWonStage(),
                                        AccountId = indID, CloseDate = system.today(), Primary_Contact__c = c.Id);
        insert o;
        system.assertEquals('Individual', [select Account.Name from Opportunity where Name = 'Test Oppty'].Account.Name);
        system.assertEquals(1, [select count() from OpportunityContactRole where isPrimary = true and ContactId = :c.Id]);

/*
        //insert a larger set of contacts, comment-out pre-merge
        List<Contact> bulkList = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(195);
        insert bulkList;
*/

        //set the HH Account model before entering the batch
        CONV_Account_Conversion_BATCH_TEST.setHHAccountModel();

        Test.StartTest();

        String query = CONV_Account_Conversion_CTRL.getBatchQuery();

        CONV_Account_Conversion_BATCH cacv = new CONV_Account_Conversion_BATCH(query, null, null);

        ID batchprocessid = Database.executeBatch(cacv);
        Test.StopTest();

        //get the newly created Household
        Account newHHAccount = [select npo02__TotalOppAmount__c, Id from Account where Name = :HHName];
        system.assertNotEquals(null, newHHAccount);
        system.assertEquals(newHHAccount.id, [select AccountId from Contact where id = :c.id].AccountId);
        system.assertEquals(newHHAccount.npo02__TotalOppAmount__c, 1000.00);
        system.assertEquals(1, [select count() from Opportunity where AccountId = :newHHAccount.Id]);
        system.assert(CAO_Constants.isHHAccountModel());
        system.assertEquals(UTIL_CustomSettingsFacade.getHouseholdsSettings().npo02__Household_Rules__c, HH_Households.NO_HOUSEHOLDS_PROCESSOR);
        for (Contact con : [select id, Account.Name from Contact]){
            system.assertNotEquals('Individual', con.Account.Name);
        }
        system.assertEquals(0, [select count() from npe5__Affiliation__c]);
    }
    /*******************************************************************************************************
    * @description Tests the conversion process for Contacts with a single address
    */
    public testMethod static void testSingleContactAddress(){
        if (strTestOnly != '*' && strTestOnly != 'testSingleContactAddress') return;

        //setup individual model and test account creation
        CONV_Account_Conversion_BATCH_TEST.setPreHHAccountModel(false);

        Contact c = new Contact(LastName = 'Contact', FirstName = 'Test',
                                MailingStreet = '123 Main St.', MailingCity = 'San Francisco',
                                MailingPostalCode = '94105', MailingState = 'California', MailingCountry = 'United States');
        insert c;
        Id indID = [select id from Account where Name = 'Individual'].id;

        system.assertEquals(indID, CAO_Constants.GetIndividualAccountId());

        String HHName = 'Contact ' + system.label.npo02.DefaultHouseholdName;
        system.assertEquals(1, [select count() from npo02__Household__c where Name = :HHName]);
        npo02__Household__c hh = [select Id, npo02__MailingStreet__c from npo02__Household__c where Name =: HHName];
        system.assertEquals(c.MailingStreet, hh.npo02__MailingStreet__c);

        //set the HH Account model before entering the batch
        CONV_Account_Conversion_BATCH_TEST.setHHAccountModel();

        Test.StartTest();

        String query = CONV_Account_Conversion_CTRL.getBatchQuery();

        CONV_Account_Conversion_BATCH cacv = new CONV_Account_Conversion_BATCH(query, null, null);

        ID batchprocessid = Database.executeBatch(cacv);
        Test.StopTest();

        //get the newly created Household
        Account newHHAccount = [select npo02__TotalOppAmount__c, npe01__One2OneContact__c, Number_of_Household_Members__c, 
            BillingStreet, BillingCity, Id from Account where Name = :HHName];
        system.assertNotEquals(null, newHHAccount);
        system.assertEquals(1, newHHAccount.Number_of_Household_Members__c);
        system.assertEquals(c.MailingStreet, newHHAccount.BillingStreet);
        system.assertEquals(c.Id, newHHAccount.npe01__One2OneContact__c);
        Address__c add = [select Id, MailingStreet__c, Default_Address__c from Address__c where MailingStreet__c = '123 Main St.'];
        system.assertNotEquals(null, add);
        system.assertEquals(true, add.Default_Address__c);
        system.assertEquals(add.Id, [select Current_Address__c from Contact where id = :c.id].Current_Address__c);
        system.assert(CAO_Constants.isHHAccountModel());
        system.assertEquals(UTIL_CustomSettingsFacade.getHouseholdsSettings().npo02__Household_Rules__c, HH_Households.NO_HOUSEHOLDS_PROCESSOR);
        system.assertEquals(0, [select count() from npe5__Affiliation__c]);
    }
    /*******************************************************************************************************
    * @description Tests the conversion process for Contacts with two addresses
    */
    public testMethod static void test2ContactAddresses(){
        if (strTestOnly != '*' && strTestOnly != 'test2ContactAddresses') return;

        //setup individual model and test account creation
        CONV_Account_Conversion_BATCH_TEST.setPreHHAccountModel(true);

        Contact c = new Contact(LastName = 'Contact', FirstName = 'Test',
                                MailingStreet = '123 Main St.', MailingCity = 'San Francisco',
                                MailingPostalCode = '94105', MailingState = 'California', MailingCountry = 'United States');
        insert c;

        c = [select Account.Name, AccountId, npo02__Household__c, npo02__Household__r.npo02__MailingStreet__c, LastName, Id, MailingStreet, Account.BillingStreet from Contact where Id = :c.Id];

        system.assertEquals(c.Account.Name, 'Test Contact');
        system.assertEquals('123 Main St.', c.MailingStreet);

        Contact c2 = new Contact(LastName = 'Contact', FirstName = 'Test2',
                                MailingStreet = '1 1st Avenue', MailingCity = 'Baltimore',
                                MailingPostalCode = '21230', MailingState = 'Maryland', MailingCountry = 'United States', npo02__TotalOppAmount__c = 100.00,
                                npo02__Household__c = c.npo02__Household__c);
        insert c2;

        //clear the values automatically copied to the household
        npo02__Household__c h = [select id, npo02__MailingStreet__c, npo02__MailingCountry__c, npo02__MailingPostalCode__c, npo02__MailingCity__c, npo02__MailingState__c from npo02__Household__c where Id = :c.npo02__Household__c];
        h.npo02__MailingStreet__c = null;
        h.npo02__MailingCountry__c = null;
        h.npo02__MailingPostalCode__c = null;
        h.npo02__MailingCity__c = null;
        h.npo02__MailingState__c = null;
        update h;

        h = [select id, npo02__MailingStreet__c, npo02__MailingCountry__c, npo02__MailingPostalCode__c, npo02__MailingCity__c, npo02__MailingState__c from npo02__Household__c where Id = :c.npo02__Household__c];
        system.assertEquals(null, h.npo02__MailingStreet__c);
        system.assertEquals(null, h.npo02__MailingCity__c);
        system.assertEquals(null, h.npo02__MailingState__c);

        //set the HH Account model before entering the batch
        CONV_Account_Conversion_BATCH_TEST.setHHAccountModel();

        Test.StartTest();

        String query = CONV_Account_Conversion_CTRL.getBatchQuery();

        CONV_Account_Conversion_BATCH cacv = new CONV_Account_Conversion_BATCH(query, null, null);

        ID batchprocessid = Database.executeBatch(cacv);
        Test.StopTest();

        //get the newly created Household
        String HHName =  'Contact ' + System.label.npo02.DefaultHouseholdName;
        Account newHHAccount = [select npo02__TotalOppAmount__c, npe01__One2OneContact__c, Number_of_Household_Members__c, BillingStreet, BillingCity, Id from Account where Name = :HHName];
        system.assertNotEquals(null, newHHAccount);
        system.assertEquals(2, newHHAccount.Number_of_Household_Members__c);
        system.assertEquals(c2.MailingStreet, newHHAccount.BillingStreet);
        system.assertEquals(c2.Id, newHHAccount.npe01__One2OneContact__c);
        system.assertEquals(2, [select count() from Address__c]);
        c2 = [select id, is_Address_Override__c, Current_Address__c, Account.BillingStreet, MailingStreet from Contact where Id = :c2.Id and FirstName = 'Test2'];
        system.assertEquals('1 1st Avenue', c2.Account.BillingStreet);
        system.assertEquals('1 1st Avenue', c2.MailingStreet);
        system.assertEquals(false, c2.is_Address_Override__c);
        system.assertNotEquals(null, c2.Current_Address__c);
        c = [select id, is_Address_Override__c, Current_Address__c, Account.BillingStreet, MailingStreet from Contact where Id = :c.Id and FirstName = 'Test'];
        system.assertEquals('1 1st Avenue', c.Account.BillingStreet);
        system.assertEquals('123 Main St.', c.MailingStreet);
        system.assertEquals(true, c.is_Address_Override__c);
        system.assertNotEquals(null, c.Current_Address__c);
        system.assert(CAO_Constants.isHHAccountModel());
        system.assertEquals(UTIL_CustomSettingsFacade.getHouseholdsSettings().npo02__Household_Rules__c, HH_Households.NO_HOUSEHOLDS_PROCESSOR);
        system.assertEquals(0, [select count() from npe5__Affiliation__c]);
    }
    /*******************************************************************************************************
    * @description Tests the conversion process for Contacts with two addresses, but address verification disabled
    */
    public testMethod static void testContactDisableAddresses(){
        if (strTestOnly != '*' && strTestOnly != 'testContactDisableAddresses') return;

        //setup individual model and test account creation
        npe01__Contacts_And_Orgs_Settings__c contactsSettings = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_And_Orgs_Settings__c(Household_Account_Addresses_Disabled__c=true, npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR));
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(new npo02__Households_Settings__c (npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR, npo02__Enable_Opp_Rollup_Triggers__c = true));

        Contact c = new Contact(LastName = 'Contact', FirstName = 'Test',
                                MailingStreet = '123 Main St.', MailingCity = 'San Francisco',
                                MailingPostalCode = '94105', MailingState = 'California', MailingCountry = 'United States');
        insert c;

        c = [select Account.Name, AccountId, npo02__Household__c, npo02__Household__r.npo02__MailingStreet__c, LastName, Id, MailingStreet, Account.BillingStreet from Contact where Id = :c.Id];

        system.assertEquals(c.Account.Name, 'Test Contact');
        system.assertEquals('123 Main St.', c.MailingStreet);

        Contact c2 = new Contact(LastName = 'Contact', FirstName = 'Test2',
                                MailingStreet = '1 1st Avenue', MailingCity = 'Baltimore',
                                MailingPostalCode = '21230', MailingState = 'Maryland', MailingCountry = 'United States', npo02__TotalOppAmount__c = 100.00,
                                npo02__Household__c = c.npo02__Household__c);
        insert c2;

        //clear the values automatically copied to the household
        npo02__Household__c h = [select id, npo02__MailingStreet__c, npo02__MailingCountry__c, npo02__MailingPostalCode__c, npo02__MailingCity__c, npo02__MailingState__c from npo02__Household__c where Id = :c.npo02__Household__c];
        h.npo02__MailingStreet__c = null;
        h.npo02__MailingCountry__c = null;
        h.npo02__MailingPostalCode__c = null;
        h.npo02__MailingCity__c = null;
        h.npo02__MailingState__c = null;
        update h;

        h = [select id, npo02__MailingStreet__c, npo02__MailingCountry__c, npo02__MailingPostalCode__c, npo02__MailingCity__c, npo02__MailingState__c from npo02__Household__c where Id = :c.npo02__Household__c];
        system.assertEquals(null, h.npo02__MailingStreet__c);
        system.assertEquals(null, h.npo02__MailingCity__c);
        system.assertEquals(null, h.npo02__MailingState__c);

        //set the HH Account model before entering the batch
        //CONV_Account_Conversion_BATCH_TEST.setHHAccountModel();

        Test.StartTest();

        String query = CONV_Account_Conversion_CTRL.getBatchQuery();

        CONV_Account_Conversion_BATCH cacv = new CONV_Account_Conversion_BATCH(query, null, null);

        ID batchprocessid = Database.executeBatch(cacv);
        Test.StopTest();

        //get the newly created Household
        String HHName =  'Contact ' + System.label.npo02.DefaultHouseholdName;
        Account newHHAccount = [select npo02__TotalOppAmount__c, npe01__One2OneContact__c, Number_of_Household_Members__c, BillingStreet, BillingCity, Id from Account where Name = :HHName];
        system.assertNotEquals(null, newHHAccount);
        system.assertEquals(2, newHHAccount.Number_of_Household_Members__c);
        //system.assertEquals(null, newHHAccount.BillingStreet, 'The contact addresses shouldn\'t be copied over during the conversion.');
        system.assertEquals(c2.Id, newHHAccount.npe01__One2OneContact__c);
        system.assertEquals(0, [select count() from Address__c], 'No address objects should be created.');
        c2 = [select id, is_Address_Override__c, Current_Address__c, Account.BillingStreet, MailingStreet from Contact where Id = :c2.Id and FirstName = 'Test2'];
        system.assertEquals(null, c2.Account.BillingStreet);
        system.assertEquals('1 1st Avenue', c2.MailingStreet);
        system.assertEquals(false, c2.is_Address_Override__c);
        system.assertEquals(null, c2.Current_Address__c);
        c = [select id, is_Address_Override__c, Current_Address__c, Account.BillingStreet, MailingStreet from Contact where Id = :c.Id and FirstName = 'Test'];
        system.assertEquals(null, c.Account.BillingStreet);
        system.assertEquals('123 Main St.', c.MailingStreet);
        system.assertEquals(false, c.is_Address_Override__c);
        system.assertEquals(null, c.Current_Address__c);
        system.assert(CAO_Constants.isHHAccountModel());
        system.assertEquals(UTIL_CustomSettingsFacade.getHouseholdsSettings().npo02__Household_Rules__c, HH_Households.NO_HOUSEHOLDS_PROCESSOR);
        system.assertEquals(UTIL_CustomSettingsFacade.getContactsSettings().Household_Account_Addresses_Disabled__c, true);
        system.assertEquals(0, [select count() from npe5__Affiliation__c]);
    }
    /*******************************************************************************************************
    * @description Tests the conversion process when setting primary affiliation
    */
    public testMethod static void testSettingPrimaryAffiliation(){
        if (strTestOnly != '*' && strTestOnly != 'testSettingPrimaryAffiliation') return;

        //setup individual model and test account creation
        CONV_Account_Conversion_BATCH_TEST.setPreHHAccountModel(false);

        Contact c = new Contact(LastName = 'Contact', FirstName = 'Test',
                                MailingStreet = '123 Main St.', MailingCity = 'San Francisco',
                                MailingPostalCode = '94105', MailingState = 'California', MailingCountry = 'United States');
        insert c;

        Account a = new Account(Name = 'NonIndividualAccount');
        insert a;

        c.AccountId = a.Id;
        update c;
        //confirm we have one new affiliation
        system.assertEquals(1, [select count() from npe5__Affiliation__c]);
        npe5__Affiliation__c aff = [select npe5__Status__c, npe5__Primary__c, npe5__Organization__c, npe5__Contact__c from npe5__Affiliation__c limit 1];
        system.assertEquals('Current', aff.npe5__Status__c);

        system.assertEquals('NonIndividualAccount', [select Account.Name from Contact where Id = :c.Id].Account.Name);

        //set the HH Account model before entering the batch
        CONV_Account_Conversion_BATCH_TEST.setHHAccountModel();

        Test.StartTest();

        String query = CONV_Account_Conversion_CTRL.getBatchQuery();

        CONV_Account_Conversion_BATCH cacv = new CONV_Account_Conversion_BATCH(query, null, null);

        ID batchprocessid = Database.executeBatch(cacv);
        Test.StopTest();

        //confirm we still have one affiliation
        system.assertEquals(1, [select count() from npe5__Affiliation__c]);
        //select that affiliation and confirm its linking our account and contact
        aff = [select npe5__Status__c, npe5__Primary__c, npe5__Organization__c, npe5__Contact__c from npe5__Affiliation__c limit 1];
        system.assertEquals('Current', aff.npe5__Status__c);
        system.assert(aff.npe5__Primary__c == true);
        system.assert(aff.npe5__Contact__c == c.Id);
        system.assert(aff.npe5__Organization__c == a.Id);
        //confirm that the Primary Affiliation on the Contact is set to our affiliation
        system.assertEquals([select Primary_Affiliation__c from Contact where Id = :c.Id].Primary_Affiliation__c, aff.npe5__Organization__c);
    }

    /*******************************************************************************************************
    * @description Sets up pre-Household Account Model settings configurations
    */
    public static void setPreHHAccountModel(boolean isOne2One){

        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests;

        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(new npo02__Households_Settings__c (npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR, npo02__Enable_Opp_Rollup_Triggers__c = true));

        if (isOne2One)
            contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR));
        else
            contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.BUCKET_PROCESSOR));

        system.assert(!CAO_Constants.isHHAccountModel());
        if(isOne2One)
            system.assert(CAO_Constants.isOneToOne());
        else
            system.assert(CAO_Constants.isBucketModel());
    }
    /*******************************************************************************************************
    * @description Sets up Household Account Model settings configurations
    */
    public static void setHHAccountModel(){
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(new npo02__Households_Settings__c (npo02__Household_Rules__c = HH_Households.NO_HOUSEHOLDS_PROCESSOR, npo02__Enable_Opp_Rollup_Triggers__c = true));
        //get the HH account rectype
        Id hhAccountid = [select Id from RecordType where DeveloperName = 'HH_Account'].Id;
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR, npe01__HH_Account_RecordTypeID__c = HHAccountId));
        system.assert(CAO_Constants.isHHAccountModel());
    }
}