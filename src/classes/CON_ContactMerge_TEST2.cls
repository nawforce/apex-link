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
* @description Tests for CON_ContactMerge_CTRL
* @group ContactMerge
* @group-content ../../ApexDocContent/ContactMerge.htm
*/
@isTest
private class CON_ContactMerge_TEST2 {

    /**
    * @description if you only want to run one test in this class, fill in its name here.
    * if you want to run all tests, then use '*'
    */
    private static string strTestOnly = '*';
    
    static testMethod void merge3OneToOneOne2One(){
        if (strTestOnly != '*' && strTestOnly != 'merge3OneToOneOne2One') return;
        merge3OneToOne(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    static testMethod void merge3OneToOneHHAccount(){
        if (strTestOnly != '*' && strTestOnly != 'merge3OneToOneHHAccount') return;
        merge3OneToOne(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }    
    static void merge3OneToOne(string strProcessor) {
        
        TDTM_DefaultConfig.startTestDataInit();
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = strProcessor));
        
        // note that we include addresses so the household pass will deal with merging household accounts with address objects.
        system.assertEquals(false, contactSettingsForTests.Household_Account_Addresses_Disabled__c);
        system.assertEquals(false, ADDR_Addresses_TDTM.hasRunAddrTrigger);
        
        Contact con = UTIL_UnitTestData_TEST.getContact();
        con.MailingStreet = '123 45th St';
        con.MailingCity = 'Seattle';
        
        Contact con2 = UTIL_UnitTestData_TEST.getContact();
        con2.LastName = '2Contact_forTests_Merge';
        con2.MailingStreet = '678 90th St';
        con2.MailingCity = 'Bellevue';
        
        Contact con3 = UTIL_UnitTestData_TEST.getContact();
        con3.LastName = '3Contact_forTests_Merge';
        con3.MailingStreet = '999 88th St';
        con3.MailingCity = 'Redmond';
        
        //insert new Contact[]{con, con2, con3};
        // insert our contacts seperately, to ensure they get different Lastmodified times.
        insert con;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false; 
        insert con2;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false; 
        insert con3;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false; 

        TDTM_DefaultConfig.stopTestDataInit();
        
        // make sure addresses created
        if (strProcessor == CAO_Constants.HH_ACCOUNT_PROCESSOR) {
            system.assertEquals(3, [select count() from Address__c]);
        }

        list<Contact> listCon = [Select id, accountId, FirstName, LastName, MailingStreet, MailingCity from Contact order by MailingStreet];
        con = listCon[0];
        con2 = listCon[1];
        con3 = listCon[2];
        
        Test.setCurrentPageReference(new PageReference('Page.CON_ContactMerge'));
        
        CON_ContactMerge_CTRL controller = new CON_ContactMerge_CTRL(new ApexPages.Standardsetcontroller(new list<Contact>()));
        
        controller.searchText = 'test';
        //SOSL always returns nothing in tests, unless you use the setFixedSearchResults value
        Id[] fixedSearchResults=new Id[3]; 
        fixedSearchResults[0]=con.Id; 
        fixedSearchResults[1]=con2.Id; 
        fixedSearchResults[2]=con3.Id; 
        
        Test.setFixedSearchResults(fixedSearchResults); 
        
        controller.search();
        
        //select the two contacts and grab them
        controller.searchResults[0].selected = true;
        controller.searchResults[1].selected = true;
        controller.searchResults[2].selected = true;
        
        controller.nextStep();
        
        CON_ContactMerge_TEST.selectMasterRecord(controller, con3.id);
        
        //merge them
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false; 
        Test.startTest();               
        controller.mergeContacts();
        // to flush our future calls that fix related data up!        
        Test.stopTest();
        
        //first and second contact should be deleted
        system.assertEquals(1,[select count() from Contact where id=:con3.id]);
        system.assertEquals(0,[select count() from Contact where id=:con2.id]);
        system.assertEquals(0,[select count() from Contact where id=:con.id]);
        //first and second contact's account should be deleted
        system.assertEquals(1, [select count() from Account]);
        system.assertEquals(0,[select count() from Account where id=:con2.AccountId]);
        system.assertEquals(0,[select count() from Account where id=:con.AccountId]);
        //Third contact's account id should not have changed
        system.assertEquals(1,[select count() from Account where id=:con3.AccountId]);
        system.assertEquals(con3.AccountId,[select AccountId from Contact where id=:con3.id].AccountId);
                
        if (strProcessor == CAO_Constants.HH_ACCOUNT_PROCESSOR) {
            // since the solo household account were merged, all addresses should still exist
            system.assertEquals(3, [select count() from Address__c]);
            // only one should be default
            system.assertEquals(1, [select count() from Address__c where Default_Address__c = true]);
        }
        
        // make sure winning contact's address is correct
        // had to take this out, because our code to keep the correct default address
        // relies on it being sorted first when sorting by LastModifiedDate.
        // unfortunately, soql only sorts by a granularity of seconds, not milliseconds,
        // so it was random which address would appear older.
        //con = [Select id, accountId, FirstName, LastName, MailingStreet, MailingCity from Contact];
        //system.assertEquals(con3.MailingStreet, con.MailingStreet);
        //system.assertEquals(con3.MailingCity, con.MailingCity);
        
    }
    
    static testMethod void merge2OneToOneInSameAccountOne2One(){
        if (strTestOnly != '*' && strTestOnly != 'merge2OneToOneInSameAccountOne2One') return;
        merge2OneToOneInSameAccount(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    static testMethod void merge2OneToOneInSameAccountHHAccount(){
        if (strTestOnly != '*' && strTestOnly != 'merge2OneToOneInSameAccountHHAccount') return;
        merge2OneToOneInSameAccount(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }    
    //merge two contacts that are both in the one-to-one account model
    //the second contact's org should be deleted
    static void merge2OneToOneInSameAccount(string strProcessor) {
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = strProcessor));

        Contact con = UTIL_UnitTestData_TEST.getContact();
        insert con;
        
        Id conAccountId = [select accountId from Contact where id=:con.id].accountId;
        
        Contact con2 = UTIL_UnitTestData_TEST.getContact();
        con2.LastName = '2Contact_forTests_Merge';
        con2.AccountId = conAccountId;
        
        insert con2;
        
        con = [Select id, accountId, FirstName, LastName, MailingStreet from Contact where Id = :con.id];
        con2 = [Select id, accountId, FirstName, LastName, MailingStreet from Contact where Id = :con2.id];
        
        Test.setCurrentPageReference(new PageReference('Page.CON_ContactMerge'));
        
        CON_ContactMerge_CTRL controller = new CON_ContactMerge_CTRL(new ApexPages.Standardsetcontroller(new list<Contact>()));
        
        controller.searchText = 'test';
        //SOSL always returns nothing in tests, unless you use the setFixedSearchResults value
        Id[] fixedSearchResults=new Id[2]; 
        fixedSearchResults[0]=con.Id; 
        fixedSearchResults[1]=con2.Id; 
        
        Test.setFixedSearchResults(fixedSearchResults); 
        //search for contacts
        controller.search();
        //select the two contacts and grab them
        controller.searchResults[0].selected = true;
        controller.searchResults[1].selected = true;
        
        controller.nextStep();
        
        CON_ContactMerge_TEST.selectMasterRecord(controller, con.id);
        
        //merge them
        Test.startTest();
        controller.mergeContacts();
        Test.stopTest();
        
        //second contact should be deleted
        system.assertEquals(0,[select count() from Contact where id=:con2.id]);
        system.assertEquals(1,[select count() from Contact where id=:con.id]);
        if (strProcessor == CAO_Constants.ONE_TO_ONE_PROCESSOR) {
            //second contact's account shouldn't be deleted
            system.assertEquals(0,[select count() from Account where id=:con2.AccountId]);
            system.assertEquals(1,[select count() from Account where id=:con.AccountId]);
        } else {
            //contacts have the same account which should not be deleted
            system.assertEquals(con.AccountId, con2.AccountId);
            system.assertEquals(1,[select count() from Account where id=:con.AccountId]);       
        }
        //First contact's account id should not have changed
        system.assertEquals(con.AccountId,[select AccountId from Contact where id=:con.id].AccountId);
    }
    
    static testMethod void ghIssue6One2One(){
        if (strTestOnly != '*' && strTestOnly != 'ghIssue6One2One') return;
        ghIssue6(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    static testMethod void ghIssue6HHAccount(){
        if (strTestOnly != '*' && strTestOnly != 'ghIssue6HHAccount') return;
        ghIssue6(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }    
    static void ghIssue6(string strProcessor){
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = strProcessor));
                
        Contact con = UTIL_UnitTestData_TEST.getContact();
        con.LastName = 'O\'Sullivan';
        
        Contact con2 = UTIL_UnitTestData_TEST.getContact();
        con2.LastName = 'O\'Sullivan';
        
        insert new Contact[]{con, con2};
        
        con = [Select id, accountId, FirstName, LastName, MailingStreet from Contact where Id = :con.id];
        system.assertEquals(con.LastName, 'O\'Sullivan');
                
        Test.setCurrentPageReference(new PageReference('Page.CON_ContactMerge'));
        CON_ContactMerge_CTRL controller = new CON_ContactMerge_CTRL(new ApexPages.Standardsetcontroller(new list<Contact>()));
        
        controller.searchText = 'O\'Sullivan';
        //SOSL always returns nothing in tests, unless you use the setFixedSearchResults value
        List<Id> fixedSearchResults=new List<Id>(); 
        fixedSearchResults.add(con.Id);
        fixedSearchResults.add(con2.Id);  
        Test.setFixedSearchResults(fixedSearchResults); 
        controller.search();
        
        //select the two contacts and grab them
        controller.searchResults[0].selected = true;
        controller.searchResults[1].selected = true;        
        
        controller.nextStep();
        
        CON_ContactMerge_TEST.selectMasterRecord(controller, con2.id);
        
        //merge them
        controller.mergeContacts();
    /*    
        //first and second contact should be deleted
        system.assertEquals(1,[select count() from Contact where id=:con3.id]);
        system.assertEquals(0,[select count() from Contact where id=:con2.id]);
        system.assertEquals(0,[select count() from Contact where id=:con.id]);
        //first and second contact's account should be deleted
        system.assertEquals(1,[select count() from Account where id=:con3.AccountId]);
        system.assertEquals(0,[select count() from Account where id=:con2.AccountId]);
        system.assertEquals(0,[select count() from Account where id=:con.AccountId]);
        //Third contact's account id should not have changed
        system.assertEquals(con3.AccountId,[select AccountId from Contact where id=:con3.id].AccountId);*/
        
        
    }
  
    public static testmethod  void householdDifferentFromMaster() {
        if (strTestOnly != '*' && strTestOnly != 'householdDifferentFromMaster') return;
        npe01__Contacts_And_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_And_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR));
        
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
            new npo02__Households_Settings__c (npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR));
        
        Contact con = UTIL_UnitTestData_TEST.getContact();
        
        Contact con2 = UTIL_UnitTestData_TEST.getContact();
        con2.LastName = '2Contact_forTests_Merge';
        
        insert new Contact[] {con, con2};
        
        Id hh1Id = [select npo02__Household__c from Contact where Id = :con.Id].npo02__Household__c;
        Id hh2Id = [select npo02__Household__c from Contact where Id = :con2.Id].npo02__Household__c;
        System.assertNotEquals(null, hh1Id);
        System.assertNotEquals(null, hh2Id);
         
        Test.setCurrentPageReference(new PageReference('Page.CON_ContactMerge'));
        
        CON_ContactMerge_CTRL controller = new CON_ContactMerge_CTRL(new ApexPages.Standardsetcontroller(new list<Contact>()));
        
        controller.searchText = 'test';
        //SOSL always returns nothing in tests, unless you use the setFixedSearchResults value
        Id[] fixedSearchResults=new Id[2]; 
        fixedSearchResults[0]=con.Id; 
        fixedSearchResults[1]=con2.Id; 
        
        Test.setFixedSearchResults(fixedSearchResults); 
        //search for contacts
        controller.search();
        //select the two contacts and grab them
        controller.searchResults[0].selected = true;
        controller.searchResults[1].selected = true;
        
        controller.nextStep();
        
        CON_ContactMerge_TEST.selectMasterRecord(controller, con.id);
        //Select the household of the one that is not the one we selected as master
        List<CON_ContactMerge_CTRL.FieldRow> fieldRows = controller.fieldRows;  
        for (CON_ContactMerge_CTRL.FieldRow row : fieldRows) {
            if(row.fieldName == 'npo02__household__c') {
                row.selectedValue = String.valueOf(con2.id);
                break;
            }
        }
        
        //merge them
        controller.mergeContacts();
        
        //Merged contact should reference the second contact's household
        System.assertEquals(hh2Id, [select npo02__Household__c from Contact where id=:con.id].npo02__Household__c);
    }  

    
    //check a standard set controller passed into the page is handled correctly
    static testMethod void testStandardSetSelected(){
        if (strTestOnly != '*' && strTestOnly != 'testStandardSetSelected') return;
        
        npe01__Contacts_And_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_And_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR));
        
        Contact con = UTIL_UnitTestData_TEST.getContact();        
        Contact con2 = UTIL_UnitTestData_TEST.getContact();
        insert new Contact[]{con, con2};
                
        Test.setCurrentPageReference(new PageReference('Page.CON_ContactMerge'));
        
        //create standard set controller with 2 records
        ApexPages.Standardsetcontroller ssc =new ApexPages.Standardsetcontroller(new list<Contact>{con,con2});
        
        //select these two records for merge
        ssc.setSelected(new list<Contact>{con,con2});
        
        CON_ContactMerge_CTRL controller = new CON_ContactMerge_CTRL(ssc);
        
        //check we ended up on step 3 ("choose winning contact") of the merge
        system.assertEquals(3, controller.step); 
        
        //check 2 records selected
        system.assertEquals(2, controller.selectedRecordsCount);
        
        //and that fields were populated
        system.assert(!controller.fieldRows.isEmpty());
    }
  
    //check a search term passed into the page is handled correctly
    static testMethod void testSearchSpecPassed(){
        if (strTestOnly != '*' && strTestOnly != 'testSearchSpecPassed') return;
        
        npe01__Contacts_And_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_And_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR));
        
        Contact con = UTIL_UnitTestData_TEST.getContact();        
        Contact con2 = UTIL_UnitTestData_TEST.getContact();
        insert new Contact[]{con, con2};
        
        list<Id> fixedSearchResults = new List<Id>(); 
        fixedSearchResults.add(con.Id);
        fixedSearchResults.add(con2.Id);  
        Test.setFixedSearchResults(fixedSearchResults); 
        
        PageReference pr = Page.CON_ContactMerge;
        pr.getParameters().put('srch', 'O\'Sullivan');
        
        Test.setCurrentPageReference(pr);
        
        CON_ContactMerge_CTRL controller = new CON_ContactMerge_CTRL(new ApexPages.Standardsetcontroller(new list<Contact>()));
        
        //check that after searching we ended up on step 2 ("select contacts") of the merge
        system.assertEquals(2, controller.step); 
        
        //check the 2 records already retrieved in search results
        system.assertEquals(2, controller.searchResults.size());
    }
    
    //check a comma separated list of Ids are handled correctly
    static testMethod void testGoodIdsPassed(){
        if (strTestOnly != '*' && strTestOnly != 'testGoodIdsPassed') return;
        
        npe01__Contacts_And_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_And_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR));
        
        Contact con = UTIL_UnitTestData_TEST.getContact();        
        Contact con2 = UTIL_UnitTestData_TEST.getContact();
        insert new Contact[]{con, con2};
        
        PageReference pr = Page.CON_ContactMerge;
        pr.getParameters().put('mergeIds', con.Id + ',' + con2.Id);
        
        Test.setCurrentPageReference(pr);
        
        CON_ContactMerge_CTRL controller = new CON_ContactMerge_CTRL(new ApexPages.Standardsetcontroller(new list<Contact>()));
        
        //check we ended up on step 3 (choose winning contact) of the merge
        system.assertEquals(3, controller.step); 
        
        //check 2 records selected
        system.assertEquals(2, controller.selectedRecordsCount);
        
        //and that fields were populated
        system.assert(!controller.fieldRows.isEmpty());
    }
    
    //check invalid Ids are handled
    static testMethod void testBadIdsPassed(){
        if (strTestOnly != '*' && strTestOnly != 'testBadIdsPassed') return;
        
        npe01__Contacts_And_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_And_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR));
        
        Contact con = UTIL_UnitTestData_TEST.getContact();        
        Contact con2 = UTIL_UnitTestData_TEST.getContact();
        insert new Contact[]{con, con2};
        
        list<Id> fixedSearchResults = new List<Id>(); 
        fixedSearchResults.add(con.Id);
        fixedSearchResults.add(con2.Id);  
        Test.setFixedSearchResults(fixedSearchResults); 
        
        PageReference pr = Page.CON_ContactMerge;
        pr.getParameters().put('mergeIds', 'blah');
        
        Test.setCurrentPageReference(pr);
        
        CON_ContactMerge_CTRL controller = new CON_ContactMerge_CTRL(new ApexPages.Standardsetcontroller(new list<Contact>()));
        
        //check we ended up on step 1 (search for contacts) of the merge
        system.assertEquals(1, controller.step); 
        
        //check error message logged to the page
        system.assert(ApexPages.hasMessages()); 
        
    }
    
    static testMethod void personAccountErrorDetection() {
        if (strTestOnly != '*' && strTestOnly != 'personAccountErrorDetection') return;
        
        // detect PersonAccounts
        boolean isPAEnabled = UTIL_Describe.isValidField('Account', 'isPersonAccount');
        if (!isPAEnabled)
            return;
        
        npe01__Contacts_And_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_And_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR));
        
        RecordType personAccountRecordType =  [SELECT Id FROM RecordType WHERE DeveloperName = 'PersonAccount' and SObjectType = 'Account'];
        Account pa1 = new Account();
        
        pa1.put('FirstName', 'Fred');
        pa1.put('LastName', 'Smith');
        pa1.RecordType = personAccountRecordType;
        insert pa1;
                
        Account pa2 = new Account();
        pa2.put('FirstName', 'Freddy');
        pa2.put('LastName', 'Smith');
        pa2.RecordType = personAccountRecordType;
        insert pa2;

        list<Id> fixedSearchResults = new List<Id>(); 
        fixedSearchResults.add(pa1.Id);
        fixedSearchResults.add(pa2.Id);  
        Test.setFixedSearchResults(fixedSearchResults); 
        
        PageReference pr = Page.CON_ContactMerge;
        pr.getParameters().put('mergeIds', pa1.Id + ',' + pa2.Id);
        
        Test.setCurrentPageReference(pr);
        
        CON_ContactMerge_CTRL controller = new CON_ContactMerge_CTRL(new ApexPages.Standardsetcontroller(new list<Contact>()));
        
        //check we ended up on step 1 (search for contacts) of the merge
        system.assertEquals(1, controller.step); 
        
        CON_ContactMerge_TEST.selectMasterRecord(controller, pa1.id);
        
        //merge them
        controller.mergeContacts();

        //check error message logged to the page
        system.assert(ApexPages.hasMessages());
        
        // no merge occurred
        system.assertEquals(2, [select count() from Account]); 

    }

    /*******************************************************************************************************
    * @description Merge two Contacts in different Accounts and verifies Opps, OCRs, Rollups, and Names
    * are updated as appropriate for the account types.
    */ 
    static testmethod void testNamesAndOppsMergeHHToHH() {
        if (strTestOnly != '*' && strTestOnly != 'testNamesAndOppsMergeHHToHH') return;
        testNamesAndOppsMerge(CAO_Constants.HH_ACCOUNT_TYPE, CAO_Constants.HH_ACCOUNT_TYPE);
    }

    /*******************************************************************************************************
    * @description Merge two Contacts in different Accounts and verifies Opps, OCRs, Rollups, and Names
    * are updated as appropriate for the account types.
    */ 
    static testmethod void testNamesAndOppsMergeOne2OneToOne2One() {
        if (strTestOnly != '*' && strTestOnly != 'testNamesAndOppsMergeOne2OneToOne2One') return;
        testNamesAndOppsMerge(CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE, CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE);
    }

    /*******************************************************************************************************
    * @description Merge two Contacts in different Accounts and verifies Opps, OCRs, Rollups, and Names
    * are updated as appropriate for the account types.
    */ 
    static testmethod void testNamesAndOppsMergeOrgToHH() {
        if (strTestOnly != '*' && strTestOnly != 'testNamesAndOppsMergeOrgToHH') return;
        testNamesAndOppsMerge(null, CAO_Constants.HH_ACCOUNT_TYPE);
    }

    /*******************************************************************************************************
    * @description Merge two Contacts in different Accounts and verifies Opps, OCRs, Rollups, and Names
    * are updated as appropriate for the account types.
    */ 
    static testmethod void testNamesAndOppsMergeHHToOrg() {
        if (strTestOnly != '*' && strTestOnly != 'testNamesAndOppsMergeHHToOrg') return;
        testNamesAndOppsMerge(CAO_Constants.HH_ACCOUNT_TYPE, null);
    }

    /*******************************************************************************************************
    * @description Merge two Contacts in different Accounts and verifies Opps, OCRs, Rollups, and Names
    * are updated as appropriate for the account types.
    */ 
    static testmethod void testNamesAndOppsMergeOrgToOne2One() {
        if (strTestOnly != '*' && strTestOnly != 'testNamesAndOppsMergeOrgToOne2One') return;
        testNamesAndOppsMerge(null, CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE);
    }

    /*******************************************************************************************************
    * @description Merge two Contacts in different Accounts and verifies Opps, OCRs, Rollups, and Names
    * are updated as appropriate for the account types.
    */ 
    static testmethod void testNamesAndOppsMergeOne2OneToOrg() {
        if (strTestOnly != '*' && strTestOnly != 'testNamesAndOppsMergeOne2OneToOrg') return;
        testNamesAndOppsMerge(CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE, null);
    }
    
    /*******************************************************************************************************
    * @description Merge two Contacts in different Accounts and verifies Opps, OCRs, Rollups, and Names
    * are updated as appropriate for the account types.
    */ 
    static testmethod void testNamesAndOppsMergeOrgToOrg() {
        if (strTestOnly != '*' && strTestOnly != 'testNamesAndOppsMergeOrgToOrg') return;
        testNamesAndOppsMerge(null, null);
    }

    /*******************************************************************************************************
    * @description Merge two Contacts in different Accounts and verifies Opps, OCRs, Rollups, and Names
    * are updated as appropriate for the account types.
    */ 
    static testmethod void testNamesAndOppsMergeOne2OneToHH() {
        if (strTestOnly != '*' && strTestOnly != 'testNamesAndOppsMergeOne2OneToOrg') return;
        testNamesAndOppsMerge(CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE, CAO_Constants.HH_ACCOUNT_TYPE);
    }

    /*******************************************************************************************************
    * @description Merge two Contacts in different Accounts and verifies Opps, OCRs, Rollups, and Names
    * are updated as appropriate for the account types.
    */ 
    static testmethod void testNamesAndOppsMergeHHToOne2One() {
        if (strTestOnly != '*' && strTestOnly != 'testNamesAndOppsMergeOne2OneToOrg') return;
        testNamesAndOppsMerge(CAO_Constants.HH_ACCOUNT_TYPE, CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE);
    }

    /*******************************************************************************************************
    * @description Merge two Contacts in different Accounts and verifies Opps, OCRs, Rollups, and Names
    * are updated as appropriate for the account types.
    */ 
    static testmethod void testNamesAndOppsMergeBucketToHH() {
        if (strTestOnly != '*' && strTestOnly != 'testNamesAndOppsMergeOne2OneToOrg') return;
        testNamesAndOppsMerge(CAO_Constants.BUCKET_ORGANIZATION_TYPE, CAO_Constants.HH_ACCOUNT_TYPE);
    }

    /*******************************************************************************************************
    * @description Merge two Contacts in different Accounts and verifies Opps, OCRs, Rollups, and Names
    * are updated as appropriate for the account types.
    */ 
    static testmethod void testNamesAndOppsMergeHHToBucket() {
        if (strTestOnly != '*' && strTestOnly != 'testNamesAndOppsMergeHHToBucket') return;
        testNamesAndOppsMerge(CAO_Constants.HH_ACCOUNT_TYPE, CAO_Constants.BUCKET_ORGANIZATION_TYPE);
    }

    /*******************************************************************************************************
    * @description Merge two Contacts in different Accounts and verifies Opps, OCRs, Rollups, and Names
    * are updated as appropriate for the account types.
    */ 
    static testmethod void testNamesAndOppsMergeBucketToOne2One() {
        if (strTestOnly != '*' && strTestOnly != 'testNamesAndOppsMergeBucketToOne2One') return;
        testNamesAndOppsMerge(CAO_Constants.BUCKET_ORGANIZATION_TYPE, CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE);
    }

    /*******************************************************************************************************
    * @description Merge two Contacts in different Accounts and verifies Opps, OCRs, Rollups, and Names
    * are updated as appropriate for the account types.
    */ 
    static testmethod void testNamesAndOppsMergeOne2OneToBucket() {
        if (strTestOnly != '*' && strTestOnly != 'testNamesAndOppsMergeHHToBucket') return;
        testNamesAndOppsMerge(CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE, CAO_Constants.BUCKET_ORGANIZATION_TYPE);
    }

    /*******************************************************************************************************
    * @description Merge two Contacts in different Accounts and verifies Opps, OCRs, Rollups, and Names
    *   the account's names and greetings are updated.
    *   Opps owned by the contact are moved over.
    *   Soft Credits for the moved Opps are recalculated.
    *   Soft Credits the contact previously had are still there.
    *   Opp Rollups are recalculated for both accounts.
    */
    static void testNamesAndOppsMerge(string accTypeLoser, string accTypeWinner) {

        // most test cases want to have 2 contacts in each account.
        integer cConLoser = 2;
        integer cConWinner = 2;
        integer iConLoser = 0;
        integer iConLoserSibling = 1;
        integer iConWinner = 2;
        integer iConWinnerSibling = 3;
        boolean isLoserAccountDeleted = false;
        
        // but when dealing with One2One accounts, they only should have one contact per account
        if (accTypeLoser == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE) {
            cConLoser = 1;
            iConLoserSibling = 0;
            iConWinner = 1;
            iConWinnerSibling = 2;
            isLoserAccountDeleted = true;
        }
    
        // create our losing account and contacts
        UTIL_UnitTestData_TEST.createAccountContactTestData(accTypeLoser, 1, cConLoser, 1);
        Account accLoser = UTIL_UnitTestData_TEST.listAccT[0];
        list<Contact> listConAll = UTIL_UnitTestData_TEST.listConT; 
        
        // create our winning account and contacts
        UTIL_UnitTestData_TEST.createAccountContactTestData(accTypeWinner, 1, cConWinner, 3);
        Account accWinner = UTIL_UnitTestData_TEST.listAccT[0];
        listConAll.addAll(UTIL_UnitTestData_TEST.listConT);        
        
        // create an opp for each contact
        // note that for Org accounts, these Opps won't generate Soft Credits.
        list<Opportunity> listOpp = UTIL_UnitTestData_TEST.OppsForContactWithAccountList(listConAll, null, 
            UTIL_UnitTestData_TEST.getclosedWonStage(), system.Today(), 100, null, null);
        insert listOpp;
        
        // Merge the first contact in the first account to the first contact in the other account.
        Contact conWinner = listConAll[iConWinner];
        Contact conLoser = listConAll[iConLoser];        

        Test.setCurrentPageReference(new PageReference('Page.CON_ContactMerge'));        
        CON_ContactMerge_CTRL controller = new CON_ContactMerge_CTRL(new ApexPages.Standardsetcontroller(new list<Contact>()));
        
        controller.searchText = 'test';
        //SOSL always returns nothing in tests, unless you use the setFixedSearchResults value
        Id[] fixedSearchResults=new Id[2]; 
        fixedSearchResults[0]=conLoser.Id; 
        fixedSearchResults[1]=conWinner.Id; 
        
        Test.setFixedSearchResults(fixedSearchResults); 
        //search for contacts
        controller.search();
        //select the two contacts and grab them
        controller.searchResults[0].selected = true;
        controller.searchResults[1].selected = true;
        
        //select the second as winner
        controller.nextStep();        
        CON_ContactMerge_TEST.selectMasterRecord(controller, conWinner.id);
        
        //list<OpportunityContactRole> listOCR = [select Opportunity.Name, Contact.Name, Role, IsPrimary from OpportunityContactRole];
        //system.debug('****DJH listOCR before: ' + listOCR);

        //merge them
        Test.startTest();
        controller.mergeContacts();
        Test.stopTest();
        
        //listOCR = [select Opportunity.Name, Contact.Name, Role, IsPrimary from OpportunityContactRole];
        //system.debug('****DJH listOCR after: ' + listOCR);

        // reload our contacts and accounts
        list<Account> listAcc = new list<Account>([select Id, Name, npo02__Formal_Greeting__c, npo02__Informal_Greeting__c, 
            npo02__NumberOfClosedOpps__c, 
            BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry 
            from Account order by Id]);
        list<Contact> listCon = [select Id, Firstname, Lastname, AccountId, 
            is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry 
            from Contact order by Firstname];
            
        // verify the contacts belong to correct account.
        iConLoserSibling--;
        iConWinner--;
        iConWinnerSibling--;
        if (isLoserAccountDeleted) {
            system.assertEquals(1, listAcc.size());
            accLoser = new Account(npo02__Formal_Greeting__c='');
            system.assertEquals(accWinner.Id, listAcc[0].Id);
            accWinner = listAcc[0];
            system.assertEquals(2, listCon.size());
        } else {
            system.assertEquals(2, listAcc.size());
            system.assertEquals(accLoser.Id, listAcc[0].Id);
            accLoser = listAcc[0];
            system.assertEquals(accWinner.Id, listAcc[1].Id);
            accWinner = listAcc[1];
            system.assertEquals(3, listCon.size());
            system.assertEquals(listCon[iConLoserSibling].AccountId, accLoser.Id);
        }
        system.assertEquals(conWinner.Id, listCon[iConWinner].Id);
        conWinner = listCon[iConWinner];
        system.assertEquals(conWinner.AccountId, accWinner.Id);
        system.assertEquals(listCon[iConWinnerSibling].AccountId, accWinner.Id);
            
        // verify the account's names and greetings are updated
        if (accTypeLoser == CAO_Constants.HH_ACCOUNT_TYPE)
            system.assertEquals(false, accLoser.npo02__Formal_Greeting__c.contains(conLoser.Firstname));        
        if (accTypeWinner == CAO_Constants.HH_ACCOUNT_TYPE) {
            system.assertEquals(false, accWinner.npo02__Formal_Greeting__c.contains(conLoser.Firstname));
            system.assertEquals(true, accWinner.npo02__Formal_Greeting__c.contains(conWinner.Firstname));
        }                
        if (accTypeWinner == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE)
            system.assertEquals(accWinner.name, conWinner.Firstname + ' ' + conWinner.Lastname);    
        
        integer cAccLoserOpps = [select count() from Opportunity where AccountId = :accLoser.Id];
        integer cAccWinnerOpps = [select count() from Opportunity where AccountId = :accWinner.Id];
        integer cOCRLoserSibling = iConLoserSibling >= 0 ? 
            [select count() from OpportunityContactRole where ContactId = :listCon[iConLoserSibling].Id] : 0;
        integer cOCRConWinner = [select count() from OpportunityContactRole where ContactId = :conWinner.Id];
        integer cOCRWinnerSibling = [select count() from OpportunityContactRole where ContactId = :listCon[iConWinnerSibling].Id];
        
        // verify Opps, OCR's, and Rollups are what we expect!
        if (accTypeLoser == CAO_Constants.HH_ACCOUNT_TYPE && accTypeWinner == CAO_Constants.HH_ACCOUNT_TYPE) {
            system.assertEquals(1, cAccLoserOpps);
            system.assertEquals(3, cAccWinnerOpps);
            system.assertEquals(1, cOCRLoserSibling);
            system.assertEquals(3, cOCRConWinner);
            system.assertEquals(3, cOCRWinnerSibling);
            system.assertEquals(1, accLoser.npo02__NumberOfClosedOpps__c);            
            system.assertEquals(3, accWinner.npo02__NumberOfClosedOpps__c);            
        }
        else if (accTypeLoser == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE && accTypeWinner == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE) {
            system.assertEquals(0, cAccLoserOpps);
            system.assertEquals(3, cAccWinnerOpps);
            system.assertEquals(0, cOCRLoserSibling);
            system.assertEquals(2, cOCRConWinner);
            system.assertEquals(1, cOCRWinnerSibling);
            system.assertEquals(null, accLoser.npo02__NumberOfClosedOpps__c);            
            system.assertEquals(3, accWinner.npo02__NumberOfClosedOpps__c);            
        }
        else if (accTypeLoser == null && accTypeWinner == CAO_Constants.HH_ACCOUNT_TYPE) {
            system.assertEquals(2, cAccLoserOpps);
            system.assertEquals(2, cAccWinnerOpps);
            system.assertEquals(1, cOCRLoserSibling);
            system.assertEquals(3, cOCRConWinner);
            system.assertEquals(2, cOCRWinnerSibling);
            system.assertEquals(2, accLoser.npo02__NumberOfClosedOpps__c);            
            system.assertEquals(2, accWinner.npo02__NumberOfClosedOpps__c);            
        }
        else if (accTypeLoser == CAO_Constants.HH_ACCOUNT_TYPE && accTypeWinner == null) {
            system.assertEquals(2, cAccLoserOpps);
            system.assertEquals(2, cAccWinnerOpps);
            system.assertEquals(1, cOCRLoserSibling);
            system.assertEquals(2, cOCRConWinner);
            system.assertEquals(1, cOCRWinnerSibling);
            system.assertEquals(2, accLoser.npo02__NumberOfClosedOpps__c);            
            system.assertEquals(2, accWinner.npo02__NumberOfClosedOpps__c);            
        }
        else if (accTypeLoser == null && accTypeWinner == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE) {
            system.assertEquals(2, cAccLoserOpps);
            system.assertEquals(2, cAccWinnerOpps);
            system.assertEquals(1, cOCRLoserSibling);
            system.assertEquals(2, cOCRConWinner);
            system.assertEquals(1, cOCRWinnerSibling);
            system.assertEquals(2, accLoser.npo02__NumberOfClosedOpps__c);            
            system.assertEquals(2, accWinner.npo02__NumberOfClosedOpps__c);            
        }
        else if (accTypeLoser == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE && accTypeWinner == null) {
            system.assertEquals(0, cAccLoserOpps);
            system.assertEquals(3, cAccWinnerOpps);
            system.assertEquals(0, cOCRLoserSibling);
            system.assertEquals(2, cOCRConWinner);
            system.assertEquals(1, cOCRWinnerSibling);
            system.assertEquals(null, accLoser.npo02__NumberOfClosedOpps__c);            
            system.assertEquals(3, accWinner.npo02__NumberOfClosedOpps__c);            
        }
        else if (accTypeLoser == null && accTypeWinner == null) {
            system.assertEquals(2, cAccLoserOpps);
            system.assertEquals(2, cAccWinnerOpps);
            system.assertEquals(1, cOCRLoserSibling);
            system.assertEquals(2, cOCRConWinner);
            system.assertEquals(1, cOCRWinnerSibling);
            system.assertEquals(2, accLoser.npo02__NumberOfClosedOpps__c);            
            system.assertEquals(2, accWinner.npo02__NumberOfClosedOpps__c);            
        }
        else if (accTypeLoser == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE && accTypeWinner == CAO_Constants.HH_ACCOUNT_TYPE) {
            system.assertEquals(0, cAccLoserOpps);
            system.assertEquals(3, cAccWinnerOpps);
            system.assertEquals(0, cOCRLoserSibling);
            system.assertEquals(3, cOCRConWinner);
            system.assertEquals(3, cOCRWinnerSibling);
            system.assertEquals(null, accLoser.npo02__NumberOfClosedOpps__c);            
            system.assertEquals(3, accWinner.npo02__NumberOfClosedOpps__c);            
        }
        else if (accTypeLoser ==  CAO_Constants.HH_ACCOUNT_TYPE && accTypeWinner == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE) {
            system.assertEquals(1, cAccLoserOpps);
            system.assertEquals(3, cAccWinnerOpps);
            system.assertEquals(1, cOCRLoserSibling);
            system.assertEquals(2, cOCRConWinner);
            system.assertEquals(1, cOCRWinnerSibling);
            system.assertEquals(1, accLoser.npo02__NumberOfClosedOpps__c);            
            system.assertEquals(3, accWinner.npo02__NumberOfClosedOpps__c);            
        }
        else if (accTypeLoser == CAO_Constants.BUCKET_ORGANIZATION_TYPE && accTypeWinner == CAO_Constants.HH_ACCOUNT_TYPE) {
            system.assertEquals(1, cAccLoserOpps);
            system.assertEquals(3, cAccWinnerOpps);
            system.assertEquals(1, cOCRLoserSibling);
            system.assertEquals(3, cOCRConWinner);
            system.assertEquals(3, cOCRWinnerSibling);
            system.assertEquals(0, accLoser.npo02__NumberOfClosedOpps__c);            
            system.assertEquals(3, accWinner.npo02__NumberOfClosedOpps__c);            
        }
        else if (accTypeLoser == CAO_Constants.HH_ACCOUNT_TYPE && accTypeWinner == CAO_Constants.BUCKET_ORGANIZATION_TYPE) {
            system.assertEquals(1, cAccLoserOpps);
            system.assertEquals(3, cAccWinnerOpps);
            system.assertEquals(1, cOCRLoserSibling);
            system.assertEquals(2, cOCRConWinner);
            system.assertEquals(1, cOCRWinnerSibling);
            system.assertEquals(1, accLoser.npo02__NumberOfClosedOpps__c);            
            system.assertEquals(0, accWinner.npo02__NumberOfClosedOpps__c);            
        }
        else if (accTypeLoser == CAO_Constants.BUCKET_ORGANIZATION_TYPE && accTypeWinner == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE) {
            system.assertEquals(1, cAccLoserOpps);
            system.assertEquals(3, cAccWinnerOpps);
            system.assertEquals(1, cOCRLoserSibling);
            system.assertEquals(2, cOCRConWinner);
            system.assertEquals(1, cOCRWinnerSibling);
            system.assertEquals(0, accLoser.npo02__NumberOfClosedOpps__c);            
            system.assertEquals(3, accWinner.npo02__NumberOfClosedOpps__c);            
        }
        else if (accTypeLoser == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE && accTypeWinner == CAO_Constants.BUCKET_ORGANIZATION_TYPE) {
            system.assertEquals(0, cAccLoserOpps);
            system.assertEquals(3, cAccWinnerOpps);
            system.assertEquals(0, cOCRLoserSibling);
            system.assertEquals(2, cOCRConWinner);
            system.assertEquals(1, cOCRWinnerSibling);
            system.assertEquals(null, accLoser.npo02__NumberOfClosedOpps__c);            
            system.assertEquals(0, accWinner.npo02__NumberOfClosedOpps__c);            
        }
        
    }
}