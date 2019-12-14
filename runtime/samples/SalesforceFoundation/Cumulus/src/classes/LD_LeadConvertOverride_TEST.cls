/*
    Copyright (c) 2009, Salesforce.org
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
* @date 2011 (2.x)
* @group Lead Conversion
* @description Tests for the lead conversion override page and logic
*/
@isTest
private class LD_LeadConvertOverride_TEST {

    /*******************************************************************************************************
    * @description Sets the test methods in the class you want to run for development testing purposes
    */
    private static string strTestOnly = '*';

    /*******************************************************************************************************
    * @description Runs newLead_Conversion_Test with the 1x1 account model
    */
    static testMethod void newLeadConversion_TestOne2One(){
        newLeadConversion_Test(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs newLead_Conversion_Test with the Household account model
    */
    static testMethod void newLeadConversion_TestHHAccount(){
        newLeadConversion_Test(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs newLead_Conversion_Test with the Individual account model
    */
    static testMethod void newLeadConversion_TestBucketAccount(){
        newLeadConversion_Test(CAO_Constants.BUCKET_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs Lead Conversion with a new Contact selection for the conversion
    */
    public static void newLeadConversion_Test(string strProcessor) {
        if (strTestOnly != '*' && strTestOnly != 'newLeadConversion_Test') return;

        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = strProcessor));
        CAO_Constants.setIndividualAccountForTests(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS);

        Test.setCurrentPageReference(new PageReference('Page.LD_LeadConvertOverride'));
        string companyName = 'Self';
        String leadFirstName = 'Leady';
        String leadLastName = 'lead for tests_987';
        Lead l = new Lead();
        l.FirstName = leadFirstName;
        l.LastName = leadLastName;
        l.Company = companyName;
        insert l;

        system.currentPageReference().getParameters().put('id', l.id);

        //pass contact into the controller
        ApexPages.StandardController sc = new ApexPages.standardController(l);

        //pass the controller into the extension
        LD_LeadConvertOverride_CTRL ext = new LD_LeadConvertOverride_CTRL(sc);

        // fill the lists (for coverage)
        ext.getConList();
        system.assertEquals(2, ext.conList.size());
        system.assert(ext.lcStatuses.size() > 0);
        system.assertEquals(1, ext.listSOAccounts.size());
        system.assertEquals(1, ext.listSOAfflAccounts.size());

        // this selects the "New" option in the Contact box
        ext.strConId = 'NEW_CONTACT';
        ext.strAccountId = 'AUTO';

        Test.startTest();
        for (SelectOption lcStatus : ext.lcStatuses) {
            ext.l.Status = lcStatus.getValue();
            if (ext.convertLeadNow() != null)
                break;
        }
        Test.stopTest();

        Contact[] convertedContacts = [select id, firstname, lastname,Account.npe01__Systemisindividual__c, Account.Name from Contact where lastname = :leadLastName];
        //one contact should have been created
        system.assertEquals(1,convertedContacts.size());
        system.assertEquals(true,convertedContacts[0].Account.npe01__Systemisindividual__c);

        String accountName = convertedContacts[0].FirstName + ' ' + convertedContacts[0].lastname;
        if (CAO_Constants.isOneToOne()) {
            system.assertEquals(accountName, convertedContacts[0].Account.Name);
            List<Account> createdAccount2 = [select id from Account where name = :accountName];
            system.assertEquals(1,createdAccount2.size());
        } else if (CAO_Constants.isHHAccountModel()) {
            system.assertEquals(convertedContacts[0].lastname + ' Household', convertedContacts[0].Account.Name);
        } else if (CAO_Constants.isBucketModel()) {
            system.assertEquals(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS, convertedContacts[0].Account.Name);
        }
        
        // make sure no affilations created
        system.assertEquals(0, [select count() from npe5__Affiliation__c]);
    }

    /*******************************************************************************************************
    * @description Runs newLeadConversionMerge_Test with the 1x1 account model
    */
    static testMethod void newLeadConversionMerge_TestOne2One() {
        newLeadConversionMerge_Test(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs newLeadConversionMerge_Test with the Household account model
    */
    static testMethod void newLeadConversionMerge_TestHHAccount(){
        newLeadConversionMerge_Test(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs newLeadConversionMerge_Test with the Individual account model
    */
    static testMethod void newLeadConversionMerge_TestBucketAccount(){
        newLeadConversionMerge_Test(CAO_Constants.BUCKET_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs Lead conversion when a merge into an existing Contact is chosen by the user
    */
    public static void newLeadConversionMerge_Test(string strProcessor) {
        if (strTestOnly != '*' && strTestOnly != 'newLeadConversionMerge_Test') return;

        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = strProcessor));
        CAO_Constants.setIndividualAccountForTests(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS);

        Test.setCurrentPageReference(new PageReference('Page.LD_LeadConvertOverride'));

        // create a contact to merge into
        contact c = new contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS
        );
        insert c;

        Lead l = new Lead();
        l.FirstName = CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS;
        l.LastName = CAO_Constants.CONTACT_LASTNAME_FOR_TESTS;
        l.Company = 'self';
        insert l;

        system.currentPageReference().getParameters().put('id', l.id);

        //pass contact into the controller
        ApexPages.StandardController sc = new ApexPages.standardController(l);

        //pass the controller into the extension
        LD_LeadConvertOverride_CTRL ext = new LD_LeadConvertOverride_CTRL(sc);

        // fill the lists (for coverage)
        ext.getConList();
        system.assertEquals(3, ext.conList.size());
        system.assert(ext.lcStatuses.size() > 0);
        system.assertEquals(1, ext.listSOAccounts.size());
        system.assertEquals(1, ext.listSOAfflAccounts.size());

        // this selects the Merge to <contact> option in the Contact box
        ext.strConId = c.id;
        ext.strAccountId = 'AUTO';
        Test.startTest();
        for (SelectOption lcStatus : ext.lcStatuses) {
            ext.l.Status = lcStatus.getValue();
            if (ext.convertLeadNow() != null)
                break;
        }
        Test.stopTest();

        Contact[] convertedContacts = [select id, firstname, lastname,Account.npe01__Systemisindividual__c from Contact where lastname = :CAO_Constants.CONTACT_LASTNAME_FOR_TESTS];
        lead[] unconvertedLeads = [select id, firstname, lastname from lead where lastname = :CAO_Constants.CONTACT_LASTNAME_FOR_TESTS and isconverted = false];
        //no lead still unconverted
        system.assertEquals(0,unconvertedLeads.size());
        //one contact should have been created
        system.assertEquals(1,convertedContacts.size());
        //the account for this contact should be an individual account, no matter the model
        system.assertEquals(true, convertedContacts[0].Account.npe01__Systemisindividual__c);

        // make sure no affilations created
        system.assertEquals(0, [select count() from npe5__Affiliation__c]);
    }

    /*******************************************************************************************************
    * @description Runs newLeadConversionNewNameCompany with the 1x1 account model
    */
    static testMethod void newLeadConversionNewNamedCompany_TestOne2One(){
        newLeadConversionNewNamedCompany_Test(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs newLeadConversionNewNameCompany with the Household account model
    */
    static testMethod void newLeadConversionNewNamedCompany_TestHHAccount(){
        newLeadConversionNewNamedCompany_Test(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs newLeadConversionNewNameCompany with the Individual account model
    */
    static testMethod void newLeadConversionNewNamedCompany_TestBucketAccount(){
        newLeadConversionNewNamedCompany_Test(CAO_Constants.BUCKET_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Tests Lead conversion with a non-existant Account name in the Company field
    */
    public static void newLeadConversionNewNamedCompany_Test(string strProcessor) {
        if (strTestOnly != '*' && strTestOnly != 'newLeadConversionNewNamedCompany_Test') return;

        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = strProcessor));
        CAO_Constants.setIndividualAccountForTests(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS);

        Test.setCurrentPageReference(new PageReference('Page.LD_LeadConvertOverride'));

        string companyName = 'Junky company for tests';
        Lead l = new Lead();
        l.FirstName = CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS;
        l.LastName = CAO_Constants.CONTACT_LASTNAME_FOR_TESTS;
        l.Company = companyName;
        l.Status = 'Open';
        insert l;

        system.currentPageReference().getParameters().put('id', l.id);

        //pass contact into the controller
        ApexPages.StandardController sc = new ApexPages.standardController(l);
        //pass the controller into the extension
        LD_LeadConvertOverride_CTRL ext = new LD_LeadConvertOverride_CTRL(sc);        
        system.assertEquals(2, ext.listSOAccounts.size());
        system.assertEquals(2, ext.listSOAfflAccounts.size());
        
        // this selects the "New" option in the Contact box
        ext.strConId = 'NEW_CONTACT';
        ext.strAccountId = 'NEW';
        ext.strAfflAccountID = 'NEW'; 
        Test.startTest();
        for (SelectOption lcStatus : ext.lcStatuses) {
            ext.l.Status = lcStatus.getValue();
            if (ext.convertLeadNow() != null)
                break;
        }
        Test.stopTest();

        Contact[] convertedContacts = [select id, firstname, lastname, Account.npe01__Systemisindividual__c, Account.Name from Contact where lastname = :CAO_Constants.CONTACT_LASTNAME_FOR_TESTS];
        lead[] unconvertedLeads = [select id, firstname, lastname from lead where lastname = :CAO_Constants.CONTACT_LASTNAME_FOR_TESTS and isconverted = false];
        //no lead still unconverted
        system.assertEquals(0,unconvertedLeads.size());
        //one contact should have been created
        system.assertEquals(1,convertedContacts.size());
        //the account for this contact should be a normal account, no matter the model
        system.assertEquals(false, convertedContacts[0].Account.npe01__Systemisindividual__c);
        system.assertEquals(companyName, convertedContacts[0].Account.Name);

        // make sure affilation created (and only 1 even though strAfflAccountId also says New!)
        system.assertEquals(1, [select count() from npe5__Affiliation__c]);
    }

    /*******************************************************************************************************
    * @description Runs newLeadConversionExistingNamedCompany with the 1x1 account model
    */
    static testMethod void newLeadConversionExistingNamedCompany_TestOne2One(){
        newLeadConversionExistingNamedCompany_Test(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs newLeadConversionExistingNamedCompany with the Household account model
    */
    static testMethod void newLeadConversionExistingNamedCompany_TestHHAccount(){
        newLeadConversionExistingNamedCompany_Test(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs newLeadConversionExistingNamedCompany with the Individual account model
    */
    static testMethod void newLeadConversionExistingNamedCompany_TestBucketAccount(){
        newLeadConversionExistingNamedCompany_Test(CAO_Constants.BUCKET_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Tests merging a Lead into a an existing Account name set in the Company field
    */
    public static void newLeadConversionExistingNamedCompany_Test(string strProcessor) {
        if (strTestOnly != '*' && strTestOnly != 'newLeadConversionExistingNamedCompany_Test') return;

        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = strProcessor));
        CAO_Constants.setIndividualAccountForTests(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS);

        Test.setCurrentPageReference(new PageReference('Page.LD_LeadConvertOverride'));

        string companyName = 'Junky company for tests';
        //create a real company account
        Account acc = new Account();
        acc.Name = companyName;
        insert acc;

        Lead l = new Lead();
        l.FirstName = CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS;
        l.LastName = CAO_Constants.CONTACT_LASTNAME_FOR_TESTS;
        l.Company = companyName;
        l.Status = 'Open';
        insert l;

        system.currentPageReference().getParameters().put('id', l.id);

        //pass contact into the controller
        ApexPages.StandardController sc = new ApexPages.standardController(l);
        //pass the controller into the extension
        LD_LeadConvertOverride_CTRL ext = new LD_LeadConvertOverride_CTRL(sc);
        system.assertEquals(3, ext.listSOAccounts.size());
        system.assertEquals(3, ext.listSOAfflAccounts.size());

        // this selects the "New" option in the Contact box
        ext.strConId = 'NEW_CONTACT';
        ext.strAccountId = acc.Id;
        Test.startTest();
        for (SelectOption lcStatus : ext.lcStatuses) {
            ext.l.Status = lcStatus.getValue();
            if (ext.convertLeadNow() != null)
                break;
        }
        Test.stopTest();

        Contact[] convertedContacts = [select id, firstname, lastname, Account.npe01__Systemisindividual__c, Account.Name from Contact where lastname = :CAO_Constants.CONTACT_LASTNAME_FOR_TESTS];
        lead[] unconvertedLeads = [select id, firstname, lastname from lead where lastname = :CAO_Constants.CONTACT_LASTNAME_FOR_TESTS and isconverted = false];
        //no lead still unconverted
        system.assertEquals(0,unconvertedLeads.size());
        //one contact should have been created
        system.assertEquals(1,convertedContacts.size());
        //the account for this contact should be a normal account, no matter the model
        system.assertEquals(false, convertedContacts[0].Account.npe01__Systemisindividual__c);
        system.assertEquals(companyName, convertedContacts[0].Account.Name);

        // make sure affilation created
        system.assertEquals(1, [select count() from npe5__Affiliation__c]);
    }

    /*******************************************************************************************************
    * @description Runs newLeadConversionExistingNamedCompanyAffl with the 1x1 account model
    */
    static testMethod void newLeadConversionExistingNamedCompanyAffl_TestOne2One(){
        newLeadConversionExistingNamedCompanyAffl_Test(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs newLeadConversionExistingNamedCompanyAffl with the Household account model
    */
    static testMethod void newLeadConversionExistingNamedCompanyAffl_TestHHAccount(){
        newLeadConversionExistingNamedCompanyAffl_Test(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs newLeadConversionExistingNamedCompany with the Individual account model
    */
    static testMethod void newLeadConversionExistingNamedCompanyAffl_TestBucketAccount(){
        newLeadConversionExistingNamedCompanyAffl_Test(CAO_Constants.BUCKET_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Tests merging a Lead into auto created account and creating an affilation to
    * an existing Account name set in the Company field
    */
    public static void newLeadConversionExistingNamedCompanyAffl_Test(string strProcessor) {
        if (strTestOnly != '*' && strTestOnly != 'newLeadConversionExistingNamedCompanyAffl_Test') return;

        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = strProcessor));
        CAO_Constants.setIndividualAccountForTests(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS);

        Test.setCurrentPageReference(new PageReference('Page.LD_LeadConvertOverride'));

        string companyName = 'Junky company for tests';
        //create a real company account
        Account acc = new Account();
        acc.Name = companyName;
        insert acc;

        Lead l = new Lead();
        l.FirstName = CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS;
        l.LastName = CAO_Constants.CONTACT_LASTNAME_FOR_TESTS;
        l.Company = companyName;
        l.Status = 'Open';
        insert l;

        system.currentPageReference().getParameters().put('id', l.id);

        //pass contact into the controller
        ApexPages.StandardController sc = new ApexPages.standardController(l);
        //pass the controller into the extension
        LD_LeadConvertOverride_CTRL ext = new LD_LeadConvertOverride_CTRL(sc);
        system.assertEquals(3, ext.listSOAccounts.size());
        system.assertEquals(3, ext.listSOAfflAccounts.size());

        // this selects the "New" option in the Contact box
        ext.strConId = 'NEW_CONTACT';
        ext.strAccountId = 'AUTO';
        ext.strAfflAccountId = acc.Id;
        Test.startTest();
        for (SelectOption lcStatus : ext.lcStatuses) {
            ext.l.Status = lcStatus.getValue();
            if (ext.convertLeadNow() != null)
                break;
        }
        Test.stopTest();

        Contact[] convertedContacts = [select id, firstname, lastname, Account.npe01__Systemisindividual__c, Account.Name from Contact where lastname = :CAO_Constants.CONTACT_LASTNAME_FOR_TESTS];
        lead[] unconvertedLeads = [select id, firstname, lastname from lead where lastname = :CAO_Constants.CONTACT_LASTNAME_FOR_TESTS and isconverted = false];
        //no lead still unconverted
        system.assertEquals(0,unconvertedLeads.size());
        //one contact should have been created
        system.assertEquals(1,convertedContacts.size());
        //the account for this contact should an individual account
        system.assertEquals(true, convertedContacts[0].Account.npe01__Systemisindividual__c);
        system.assertNotEquals(companyName, convertedContacts[0].Account.Name);

        // make sure affilation created to the original company
        system.assertEquals(1, [select count() from npe5__Affiliation__c where npe5__Organization__c = :acc.Id]);
    }

    /*******************************************************************************************************
    * @description Runs newLeadConversionHHObj with the 1x1 account model
    */
    static testMethod void newLeadConversionHHObjOne2One(){
        newLeadConversionHHObj(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs newLeadConversionHHObj with the Individual account model
    */
    static testMethod void newLeadConversionHHObjBucket(){
        newLeadConversionHHObj(CAO_Constants.BUCKET_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Tests if it's a regular lead conversion and HH Objects are in use, we still get Household Object
    */
    public static void newLeadConversionHHObj(string strProcessor) {
        if (strTestOnly != '*' && strTestOnly != 'newLeadConversionHHObj') return;

        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = strProcessor));
        CAO_Constants.setIndividualAccountForTests(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS);

        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
            new npo02__Households_Settings__c (npo02__Household_Rules__c = HH_Households.ALL_INDIVIDUALS_PROCESSOR));

        Test.setCurrentPageReference(new PageReference('Page.LD_LeadConvertOverride'));

        string companyName = 'Self';
        Lead l = new Lead();
        l.FirstName = CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS;
        l.LastName = CAO_Constants.CONTACT_LASTNAME_FOR_TESTS;
        l.Company = companyName;
        insert l;

        system.currentPageReference().getParameters().put('id', l.id);

        //pass contact into the controller
        ApexPages.StandardController sc = new ApexPages.standardController(l);

        //pass the controller into the extension
        LD_LeadConvertOverride_CTRL ext = new LD_LeadConvertOverride_CTRL(sc);
        system.assertEquals(1, ext.listSOAccounts.size());
        system.assertEquals(1, ext.listSOAfflAccounts.size());

        // fill the lists (for coverage)
        ext.getConList();
        system.assertEquals(2, ext.conList.size());
        system.assert(ext.lcStatuses.size() > 0);

        // this selects the "New" option in the Contact box
        ext.strConId = 'NEW_CONTACT';
        ext.strAccountId = 'AUTO';
        Test.startTest();
        for (SelectOption lcStatus : ext.lcStatuses) {
            ext.l.Status = lcStatus.getValue();
            if (ext.convertLeadNow() != null)
                break;
        }
        Test.stopTest();

        Contact[] convertedContacts = [select id, firstname, lastname, npo02__Household__c, Account.npe01__Systemisindividual__c from Contact where lastname = :CAO_Constants.CONTACT_LASTNAME_FOR_TESTS];
        lead[] unconvertedLeads = [select id, firstname, lastname from lead where lastname = :CAO_Constants.CONTACT_LASTNAME_FOR_TESTS and isconverted = false];
        //no lead still unconverted
        system.assertEquals(0,unconvertedLeads.size());
        //one contact should have been created
        system.assertEquals(1,convertedContacts.size());
        //the account for this contact should be an individual account, no matter the model
        system.assertEquals(true, convertedContacts[0].Account.npe01__Systemisindividual__c);

        // make sure HH created
        list<npo02__Household__c> listHH = [select Id, Name from npo02__Household__c];
        system.assertEquals(1, listHH.size());
        system.assertEquals(convertedContacts[0].npo02__Household__c, listHH[0].Id);

        // make sure no affilation created
        system.assertEquals(0, [select count() from npe5__Affiliation__c]);
    }


    /*******************************************************************************************************
    * @description Runs newLead_Conversion_Test with the 1x1 account model
    */
    static testMethod void newNativeLeadConversion_TestOne2One(){
        if (strTestOnly != '*' && strTestOnly != 'newNativeLeadConversion_TestOne2One') return;
        newNativeLeadConversion_Test(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs newLead_Conversion_Test with the Household account model
    */
    static testMethod void newNativeLeadConversion_TestHHAccount(){
        if (strTestOnly != '*' && strTestOnly != 'newNativeLeadConversion_TestHHAccount') return;
        newNativeLeadConversion_Test(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs newLead_Conversion_Test with the Individual account model
    */
    static testMethod void newNativeLeadConversion_TestBucketAccount(){
        if (strTestOnly != '*' && strTestOnly != 'newNativeLeadConversion_TestBucketAccount') return;
        newNativeLeadConversion_Test(CAO_Constants.BUCKET_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs Native Lead Conversion with a new Contact selection for the conversion
    */
    public static void newNativeLeadConversion_Test(string strProcessor) {
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = strProcessor));
        //CAO_Constants.setIndividualAccountForTests(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS);

        Contact con = new Contact();
        con.Firstname = 'Test';
        con.Lastname = 'TestContact';
        //con.MailingStreet = '123';
        insert con;
        system.assertEquals(1, [select count() from Contact]);
        system.assertEquals(1, [select count() from Account]);
        con = [select Id, AccountId, Firstname, Lastname, MailingStreet, MailingCity, MailingPostalCode, Current_Address__c from Contact];
        system.assertEquals(null, con.Current_Address__c);

        Lead l = new Lead();
        l.FirstName = 'Test';
        l.LastName = 'TestContact';
        l.Company = 'TestContact Household';
        l.Street = '123 ABC';
        l.City = 'Bellevue';
        l.PostalCode = '98005';
        insert l;
		
		Database.LeadConvert lc = new Database.LeadConvert();
		lc.setLeadId(l.id);
		lc.setContactId(con.Id);
		lc.setAccountId(con.AccountId);
		
		List<LeadStatus> convertStatus = [SELECT Id, MasterLabel FROM LeadStatus WHERE IsConverted=true];
		
		Test.startTest();
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        system.debug('****ACTION: calling convertLead!');
        Database.LeadConvertResult lcr;
        Boolean brkLoop;
        for (LeadStatus cs : convertStatus) {
            brkLoop = true;
            lc.setConvertedStatus(cs.MasterLabel);
            try {
                lcr = Database.convertLead(lc);
            } catch(Exception ex) {
                  brkLoop = false;
            }
            if (brkLoop) break;
        }
		Test.stopTest();
		System.assert(lcr.isSuccess());
        
        Contact[] convertedContacts = [select id, firstname, lastname,Account.npe01__Systemisindividual__c, 
            Account.Name, MailingStreet, MailingCity, MailingPostalCode from Contact];
        //one contact should have been created
        system.assertEquals(1,convertedContacts.size());
        system.assertEquals(true,convertedContacts[0].Account.npe01__Systemisindividual__c);
        system.assertEquals(l.Street, convertedContacts[0].MailingStreet);
        system.assertEquals(l.City, convertedContacts[0].MailingCity);
        system.assertEquals(l.PostalCode, convertedContacts[0].MailingPostalCode);

        String accountName = convertedContacts[0].FirstName + ' ' + convertedContacts[0].lastname;
        if (CAO_Constants.isOneToOne()) {
            system.assertEquals(accountName, convertedContacts[0].Account.Name);
            List<Account> createdAccount2 = [select id from Account where name = :accountName];
            system.assertEquals(1,createdAccount2.size());
        } else if (CAO_Constants.isHHAccountModel()) {
            system.assertEquals(convertedContacts[0].lastname + ' Household', convertedContacts[0].Account.Name);
            system.assertEquals(1, [select count() from Address__c]);
            system.assertEquals(1, [select count() from Address__c where Default_Address__c = true]);
        } else if (CAO_Constants.isBucketModel()) {
            system.assertEquals(CAO_Constants.BUCKET_ACCOUNT_NAME, convertedContacts[0].Account.Name);
        }

        // make sure no affilation created
        system.assertEquals(0, [select count() from npe5__Affiliation__c]);
    }

    /*******************************************************************************************************
    * @description Runs Lead Conversion with a new Contact selection for the conversion, and verify
    * address object created correctly in the Household Account model.
    */
    public static void newLeadConvert_HHAddressTest() {
        if (strTestOnly != '*' && strTestOnly != 'newLeadConvert_HHAddressTest') return;

        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR));

        Test.setCurrentPageReference(new PageReference('Page.LD_LeadConvertOverride'));
        string companyName = 'Self';
        String leadFirstName = 'Leady';
        String leadLastName = 'lead for tests_987';
        Lead l = new Lead();
        l.FirstName = leadFirstName;
        l.LastName = leadLastName;
        l.Company = companyName;
        l.Street = '123 45th NE';
        l.City = 'Seattle';
        l.State = 'WA';
        l.PostalCode = '98005';
        insert l;

        system.currentPageReference().getParameters().put('id', l.id);

        //pass contact into the controller
        ApexPages.StandardController sc = new ApexPages.standardController(l);

        //pass the controller into the extension
        LD_LeadConvertOverride_CTRL ext = new LD_LeadConvertOverride_CTRL(sc);

        // fill the lists (for coverage)
        ext.getConList();
        system.assertEquals(2, ext.conList.size());
        system.assert(ext.lcStatuses.size() > 0);
        system.assertEquals(1, ext.listSOAccounts.size());
        system.assertEquals(1, ext.listSOAfflAccounts.size());

        // this selects the "New" option in the Contact box
        ext.strConId = 'NEW_CONTACT';
        ext.strAccountId = 'AUTO';
        Test.startTest();
        for (SelectOption lcStatus : ext.lcStatuses) {
            ext.l.Status = lcStatus.getValue();
            if (ext.convertLeadNow() != null)
                break;
        }
        Test.stopTest();

        Contact[] convertedContacts = [select id, firstname, lastname,Account.npe01__Systemisindividual__c, Account.Name,
            MailingStreet, MailingCity, MailingState, MailingPostalCode, Current_Address__c from Contact where lastname = :leadLastName];
        //one contact should have been created
        system.assertEquals(1,convertedContacts.size());
        system.assertEquals(true,convertedContacts[0].Account.npe01__Systemisindividual__c);

        String accountName = convertedContacts[0].FirstName + ' ' + convertedContacts[0].lastname;
        if (CAO_Constants.isOneToOne()) {
            system.assertEquals(accountName, convertedContacts[0].Account.Name);
            List<Account> createdAccount2 = [select id from Account where name = :accountName];
            system.assertEquals(1,createdAccount2.size());
        } else if (CAO_Constants.isHHAccountModel()) {
            system.assertEquals(convertedContacts[0].lastname + ' Household', convertedContacts[0].Account.Name);
        } else if (CAO_Constants.isBucketModel()) {
            system.assertEquals(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS, convertedContacts[0].Account.Name);
        }
        
        // make sure no affilations created
        system.assertEquals(0, [select count() from npe5__Affiliation__c]);
        
        // verify address
        list<Address__c> listAddr = [select Id, MailingStreet__c, MailingCity__c, MailingState__c, MailingPostalCode__c from Address__c];
        system.assertEquals(1, listAddr.size());
        system.assertEquals(convertedContacts[0].Current_Address__c, listAddr[0].Id);
        system.assertEquals(convertedContacts[0].MailingStreet, listAddr[0].MailingStreet__c);
        system.assertEquals(convertedContacts[0].MailingCity, listAddr[0].MailingCity__c);
        system.assertEquals(convertedContacts[0].MailingState, listAddr[0].MailingState__c);
        system.assertEquals(convertedContacts[0].MailingPostalCode, listAddr[0].MailingPostalCode__c);
    }

    /*******************************************************************************************************
    * @description Runs newLead_Conversion_Test with the 1x1 account model and verify Opp OCRs and Primary Contact are correct.
    */
    static testMethod void newLeadConversion_TestOne2OneOppOCR(){
        newLeadConversion_TestOppOCR(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs newLead_Conversion_Test with the Household account model and verify Opp OCRs and Primary Contact are correct.
    */
    static testMethod void newLeadConversion_TestHHAccountOppOCR(){
        newLeadConversion_TestOppOCR(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs newLead_Conversion_Test with the Individual account model and verify Opp OCRs and Primary Contact are correct.
    */
    static testMethod void newLeadConversion_TestBucketAccountOppOCR(){
        newLeadConversion_TestOppOCR(CAO_Constants.BUCKET_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs Lead Conversion with a new Contact selection for the conversion and verify Opp OCRs and Primary Contact are correct.
    */
    public static void newLeadConversion_TestOppOCR(string strProcessor) {
        if (strTestOnly != '*' && strTestOnly != 'newLeadConversion_TestOppOCR') return;

        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = strProcessor));
        CAO_Constants.setIndividualAccountForTests(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS);
        
        Test.setCurrentPageReference(new PageReference('Page.LD_LeadConvertOverride'));
        string companyName = 'Self';
        String leadFirstName = 'Leady';
        String leadLastName = 'lead for tests_987';
        Lead l = new Lead();
        l.FirstName = leadFirstName;
        l.LastName = leadLastName;
        l.Company = companyName;
        insert l;

        system.currentPageReference().getParameters().put('id', l.id);

        //pass contact into the controller
        ApexPages.StandardController sc = new ApexPages.standardController(l);

        //pass the controller into the extension
        LD_LeadConvertOverride_CTRL ext = new LD_LeadConvertOverride_CTRL(sc);

        // fill the lists (for coverage)
        ext.getConList();
        system.assertEquals(2, ext.conList.size());
        system.assert(ext.lcStatuses.size() > 0);
        system.assertEquals(1, ext.listSOAccounts.size());
        system.assertEquals(1, ext.listSOAfflAccounts.size());

        // this selects the "New" option in the Contact box
        ext.strConId = 'NEW_CONTACT';
        ext.strAccountId = 'AUTO';
        ext.OppName = 'Test Opp';
        ext.doNotCreateOpp = false;
        Test.startTest();
        for (SelectOption lcStatus : ext.lcStatuses) {
            ext.l.Status = lcStatus.getValue();
            if (ext.convertLeadNow() != null)
                break;
        }
        Test.stopTest();

        Contact[] convertedContacts = [select id, firstname, lastname,Account.npe01__Systemisindividual__c, Account.Name from Contact where lastname = :leadLastName];
        //one contact should have been created
        system.assertEquals(1,convertedContacts.size());
        system.assertEquals(true,convertedContacts[0].Account.npe01__Systemisindividual__c);

        String accountName = convertedContacts[0].FirstName + ' ' + convertedContacts[0].lastname;
        if (CAO_Constants.isOneToOne()) {
            system.assertEquals(accountName, convertedContacts[0].Account.Name);
            List<Account> createdAccount2 = [select id from Account where name = :accountName];
            system.assertEquals(1,createdAccount2.size());
        } else if (CAO_Constants.isHHAccountModel()) {
            system.assertEquals(convertedContacts[0].lastname + ' Household', convertedContacts[0].Account.Name);
        } else if (CAO_Constants.isBucketModel()) {
            system.assertEquals(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS, convertedContacts[0].Account.Name);
        }
        
        // verify Opp 
        list<Opportunity> listOpp = [select Id, AccountId, Name, Primary_Contact__c from Opportunity];
        system.assertEquals(1, listOpp.size());
        system.assertEquals(ext.OppName, listOpp[0].Name);        

        // verify Opp OCR's
        list<OpportunityContactRole> listOCR = [select Id, OpportunityId, ContactId, Role, IsPrimary from OpportunityContactRole];
        system.assertEquals(1, listOCR.size());        
        system.assertEquals(true, listOCR[0].IsPrimary);
        
        // verify Primary Contact
        system.assertEquals(convertedContacts[0].Id, listOpp[0].Primary_Contact__c);
    }

    /*******************************************************************************************************
    * @description Runs mergeLeadConversion_TestOppOCR with the 1x1 account model and verify Opp OCRs and Primary Contact are correct.
    */
    static testMethod void mergeLeadConversion_TestOne2OneOppOCR(){
        mergeLeadConversion_TestOppOCR(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs mergeLeadConversion_TestOppOCR with the Household account model and verify  Opp OCRs and Primary Contact are correct.
    */
    static testMethod void mergeLeadConversion_TestHHAccountOppOCR(){
        mergeLeadConversion_TestOppOCR(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs mergeLeadConversion_TestOppOCR with the Individual account model and verify Opp OCRs and Primary Contact are correct.
    */
    static testMethod void mergeLeadConversion_TestBucketAccountOppOCR(){
        mergeLeadConversion_TestOppOCR(CAO_Constants.BUCKET_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs Lead Conversion with a merge to existing Contact selection for the conversion and verify Opp OCRs and Primary Contact are correct.
    */
    public static void mergeLeadConversion_TestOppOCR(string strProcessor) {
        if (strTestOnly != '*' && strTestOnly != 'mergeLeadConversion_TestOppOCR') return;

        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = strProcessor));
        CAO_Constants.setIndividualAccountForTests(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS);
        
        // create an existing contact
        Contact con = new Contact();
        con.firstname = 'Testy';
        con.lastname = 'Testy';
        insert con;
        con = [select Id, AccountId, Firstname, Lastname from Contact];
        system.assertNotEquals(null, con.Id);
        system.assertNotEquals(null, con.AccountId);

        Test.setCurrentPageReference(new PageReference('Page.LD_LeadConvertOverride'));
        string companyName = 'Self';
        String leadFirstName = 'Testy';
        String leadLastName = 'Testy';
        Lead l = new Lead();
        l.FirstName = leadFirstName;
        l.LastName = leadLastName;
        l.Company = companyName;
        insert l;

        system.currentPageReference().getParameters().put('id', l.id);

        //pass lead into the controller
        ApexPages.StandardController sc = new ApexPages.standardController(l);

        //pass the controller into the extension
        LD_LeadConvertOverride_CTRL ext = new LD_LeadConvertOverride_CTRL(sc);

        // fill the lists (for coverage)
        ext.getConList();
        system.assertEquals(3, ext.conList.size());
        system.assert(ext.lcStatuses.size() > 0);
        system.assertEquals(1, ext.listSOAccounts.size());
        system.assertEquals(1, ext.listSOAfflAccounts.size());

        // this selects the existing contact option in the Contact box
        ext.strConId = con.id;
        ext.strAccountId = 'AUTO';        
        ext.OppName = 'Test Opp';
        ext.doNotCreateOpp = false;
        Test.startTest();
        for (SelectOption lcStatus : ext.lcStatuses) {
            ext.l.Status = lcStatus.getValue();
            if (ext.convertLeadNow() != null)
                break;
        }
        Test.stopTest();

        Contact[] convertedContacts = [select id, firstname, lastname,Account.npe01__Systemisindividual__c, Account.Name from Contact where lastname = :leadLastName];
        //one contact should have been created
        system.assertEquals(1,convertedContacts.size());
        system.assertEquals(true,convertedContacts[0].Account.npe01__Systemisindividual__c);

        String accountName = convertedContacts[0].FirstName + ' ' + convertedContacts[0].lastname;
        if (CAO_Constants.isOneToOne()) {
            system.assertEquals(accountName, convertedContacts[0].Account.Name);
            List<Account> createdAccount2 = [select id from Account where name = :accountName];
            system.assertEquals(1,createdAccount2.size());
        } else if (CAO_Constants.isHHAccountModel()) {
            system.assertEquals(convertedContacts[0].lastname + ' Household', convertedContacts[0].Account.Name);
        } else if (CAO_Constants.isBucketModel()) {
            system.assertEquals(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS, convertedContacts[0].Account.Name);
        }
        
        // verify Opp 
        list<Opportunity> listOpp = [select Id, AccountId, Name, Primary_Contact__c from Opportunity];
        system.assertEquals(1, listOpp.size());
        system.assertEquals(ext.OppName, listOpp[0].Name);        

        // verify Opp OCR's
        list<OpportunityContactRole> listOCR = [select Id, OpportunityId, ContactId, Role, IsPrimary from OpportunityContactRole];
        system.assertEquals(1, listOCR.size());        
        system.assertEquals(true, listOCR[0].IsPrimary);
        
        // verify Primary Contact
        system.assertEquals(convertedContacts[0].Id, listOpp[0].Primary_Contact__c);
    }
    
    
    /*******************************************************************************************************
    * @description Runs newLeadConversion_TestOppAFF with the 1x1 account model and verify Opp created for affiliation.
    */
    static testMethod void newLeadConversion_TestOne2OneOppAFF(){
        newLeadConversion_TestOppAFF(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs newLeadConversion_TestOppAFF with the Household account model and verify Opp created for affiliation.
    */
    static testMethod void newLeadConversion_TestHHAccountOppAFF(){
        newLeadConversion_TestOppAFF(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs newLeadConversion_TestOppAFF with the Individual account model and verify Opp created for affiliation.
    */
    static testMethod void newLeadConversion_TestBucketAccountOppAFF(){
        newLeadConversion_TestOppAFF(CAO_Constants.BUCKET_PROCESSOR);
    }
    /*******************************************************************************************************
    * @description Runs Lead Conversion with a new Contact selection for the conversion and verify Opp created for affiliation.
    */
    public static void newLeadConversion_TestOppAFF(string strProcessor) {
        if (strTestOnly != '*' && strTestOnly != 'newLeadConversion_TestOppAFF') return;

        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = strProcessor));
        CAO_Constants.setIndividualAccountForTests(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS);
        
        Test.setCurrentPageReference(new PageReference('Page.LD_LeadConvertOverride'));
        string companyName = 'Super Co';
        String leadFirstName = 'Leady';
        String leadLastName = 'lead for tests_987';
        Lead l = new Lead();
        l.FirstName = leadFirstName;
        l.LastName = leadLastName;
        l.Company = companyName;
        l.CompanyStreet__c = '112 Company St';
        l.CompanyCity__c = 'Home Town';
        l.CompanyState__c = 'WA';
        l.CompanyPostalCode__c = '98122';
        l.CompanyCountry__c = 'United States';
        insert l;

        system.currentPageReference().getParameters().put('id', l.id);

        //pass contact into the controller
        ApexPages.StandardController sc = new ApexPages.standardController(l);

        //pass the controller into the extension
        LD_LeadConvertOverride_CTRL ext = new LD_LeadConvertOverride_CTRL(sc);

        // fill the lists (for coverage)
        System.assertEquals(2, ext.getOppAccChoices().size());

        // this selects the "New" option in the Contact box
        ext.strConId = 'NEW_CONTACT';
        ext.strOppAccChoice = 'AFF_ACC';
        ext.strAccountId = 'AUTO';
        ext.OppName = 'Test Opp';
        ext.strAfflAccountId = 'NEW';
        ext.doNotCreateOpp = false;
        Test.startTest();
        for (SelectOption lcStatus : ext.lcStatuses) {
            ext.l.Status = lcStatus.getValue();
            if (ext.convertLeadNow() != null)
                break;
        }
        Test.stopTest();

        Contact[] convertedContacts = [select id, firstname, lastname,Account.npe01__Systemisindividual__c, Account.Name, Primary_Affiliation__c, Primary_Affiliation__r.BillingStreet from Contact where lastname = :leadLastName];
        //one contact should have been created
        system.assertEquals(1,convertedContacts.size());
        system.assertEquals(true,convertedContacts[0].Account.npe01__Systemisindividual__c);
        system.assertNotEquals(null, convertedContacts[0].Primary_Affiliation__c);
        system.assertEquals('112 Company St', convertedContacts[0].Primary_Affiliation__r.BillingStreet);

        String accountName = convertedContacts[0].FirstName + ' ' + convertedContacts[0].lastname;
        if (CAO_Constants.isOneToOne()) {
            system.assertEquals(accountName, convertedContacts[0].Account.Name);
            List<Account> createdAccount2 = [select id from Account where name = :accountName];
            system.assertEquals(1,createdAccount2.size());
        } else if (CAO_Constants.isHHAccountModel()) {
            system.assertEquals(convertedContacts[0].lastname + ' Household', convertedContacts[0].Account.Name);
        } else if (CAO_Constants.isBucketModel()) {
            system.assertEquals(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS, convertedContacts[0].Account.Name);
        }
        
        // verify Opp Affiliation
		npe5__Affiliation__c  aff = [select npe5__Organization__c  from npe5__Affiliation__c LIMIT 1][0];
        system.assertNotEquals(null, aff, 'Affiliation should be created.');

        list<Opportunity> listOpp = [select Id, AccountId, Name, Primary_Contact__c from Opportunity];
        system.assertEquals(1, listOpp.size());
        system.assertEquals(aff.npe5__Organization__c , listOpp[0].AccountId);        
    }
    
    /*******************************************************************************************************
    * @description Runs lead conversion to create a company and test Lead.CompanyAddress bhvr
    */
    public testmethod static void convertCompanyLead_Test() {
        if (strTestOnly != '*' && strTestOnly != 'convertCompanyLead_Test') return;

        Lead l = new Lead();
        l.FirstName = 'Test';
        l.LastName = 'TestContact';
        l.Company = 'TestContact Company';
        l.Street = '123 ABC';
        l.City = 'Bellevue';
        l.PostalCode = '98005';
        l.CompanyStreet__c = '112 Company St';
        l.CompanyCity__c = 'Home Town';
        l.CompanyState__c = 'WA';
        l.CompanyPostalCode__c = '98122';
        l.CompanyCountry__c = 'United States';
        insert l;
        
        Test.setCurrentPageReference(new PageReference('Page.LD_LeadConvertOverride'));
        system.currentPageReference().getParameters().put('id', l.id);

        //pass contact into the controller
        ApexPages.StandardController sc = new ApexPages.standardController(l);

        //pass the controller into the extension
        LD_LeadConvertOverride_CTRL ext = new LD_LeadConvertOverride_CTRL(sc);

        // fill the lists (for coverage)
        System.assertEquals(2, ext.getOppAccChoices().size());

        // this selects the "New" option in the Contact box
        ext.strConId = 'NEW_CONTACT';
        ext.strAccountId = 'NEW';
        ext.doNotCreateOpp = true;
        Test.startTest();
        for (SelectOption lcStatus : ext.lcStatuses) {
            ext.l.Status = lcStatus.getValue();
            if (ext.convertLeadNow() != null)
                break;
        }
        Test.stopTest();
        
        Account result = [SELECT Id,Name,BillingStreet FROM Account LIMIT 1][0];
        System.assertEquals(l.CompanyStreet__c,result.BillingStreet);
    }
}