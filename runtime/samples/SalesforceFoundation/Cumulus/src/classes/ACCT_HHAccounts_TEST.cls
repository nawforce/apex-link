/*
    Copyright (c) 2013, Salesforce.org
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
* @date 2013
* @group Accounts
* @description tests specific to the Household Account model 
*/

@isTest
private with sharing class ACCT_HHAccounts_TEST {

    /*********************************************************************************************************
    * @description if you only want to run one test in this class, fill in its name here.
    * if you want to run all tests, then use '*'
    */
    private static string strTestOnly = '*';
    
    	
    /*********************************************************************************************************
    * @description
    * operation:
    *     create N new contacts, no accounts 
    * verify:
    *   N hh accounts created
	*	accounts' name and naming fields set
	*	hh accounts' primary contact set to new contact
    **********************************************************************************************************/            
    static testMethod void NewContactsNoAccounts() {
    	if (strTestOnly != '*' && strTestOnly != 'NewContactsNoAccounts') return;
    	
    	//Using the API class just to give it test coverage.
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettings_API.getContactsSettings();
    
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(5);
        system.assertEquals(null, listCon[0].AccountId); 
        
        Test.startTest();      
        insert listCon;
        test.stopTest();    // to flush any future calls.
        
        list<Account> listAcc = [select Name, npe01__SYSTEM_AccountType__c, npo02__SYSTEM_CUSTOM_NAMING__c, npe01__SYSTEMIsIndividual__c, 
            npe01__One2OneContact__c, npo02__Formal_Greeting__c, npo02__Informal_Greeting__c, Number_of_Household_Members__c from Account];
        system.assertEquals(5, listAcc.size());
        
        for (Account acc : listAcc) {
        	system.assertNotEquals(null, acc.npe01__One2OneContact__c);
            system.assertNotEquals(null, acc.npo02__Formal_Greeting__c);
            system.assertNotEquals(null, acc.npo02__Informal_Greeting__c);
            system.assertEquals(null, acc.npo02__SYSTEM_CUSTOM_NAMING__c);
            system.assertEquals(CAO_Constants.HH_ACCOUNT_TYPE, acc.npe01__SYSTEM_AccountType__c);
            system.assertEquals(true, acc.npe01__SYSTEMIsIndividual__c);
            system.assertEquals(1, acc.Number_of_Household_Members__c);
            if (acc.npe01__One2OneContact__c == listCon[1].Id) {
            	system.assertEquals(listCon[1].LastName + ' Household', acc.Name);
                system.assertEquals(listCon[1].FirstName, acc.npo02__Informal_Greeting__c);
                system.assertEquals(listCon[1].FirstName + ' ' + listCon[1].LastName, acc.npo02__Formal_Greeting__c);
            }
        }
    
    }
    
    /*********************************************************************************************************
    * @description
    * operation:
    *   create N new contacts, M empty hh account supplied 
    * verify:
    *    contacts added to existing hh accounts
    *    (N - M) accounts have multiple contacts
    *    accounts' name and naming fields updated
    *    hh accounts' primary contact updated to contact
    **********************************************************************************************************/            
    static testMethod void NewContactsEmptyHHAccounts() {
        if (strTestOnly != '*' && strTestOnly != 'NewContactsEmptyHHAccounts') return;

        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettings();
    
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(5);
        system.assertEquals(null, listCon[0].AccountId); 
        list<Account> listAcc = UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(3, CAO_Constants.HH_ACCOUNT_TYPE);
        insert listAcc;
        system.assertNotEquals(null, listAcc[0].Id);
        
        // specify the account for each contact, some sharing an account.
        listCon[0].AccountId = listAcc[0].Id;
        listCon[1].AccountId = listAcc[0].Id;
        listCon[2].AccountId = listAcc[1].Id;
        listCon[3].AccountId = listAcc[1].Id;
        listCon[4].AccountId = listAcc[2].Id;
        
        // lastname permutations
        system.assertEqualS(listCon[0].LastName, listCon[1].LastName);
        listCon[3].LastName = 'DifferentLastName';
        system.assertNotEquals(listCon[2].LastName, listCon[3].LastName);
        
        Test.startTest();      
        insert listCon;

        // Force the primary contact on each to ensure proper naming order
        // Update separately so that Name field doesn't accidentally get overwritten
        // Don't want to re-query first because the list may come back in a different order
        list<Account> updtAccts = new list<Account>{
            new Account(Id=listAcc[0].Id, npe01__One2OneContact__c = listCon[0].Id),
            new Account(Id=listAcc[1].Id, npe01__One2OneContact__c = listCon[2].Id),
            new Account(Id=listAcc[2].Id, npe01__One2OneContact__c = listCon[4].Id)
        };
        update updtAccts;        
        test.stopTest();    // to flush any future calls.

        listAcc = [select Name, npe01__SYSTEM_AccountType__c, npo02__SYSTEM_CUSTOM_NAMING__c, npe01__SYSTEMIsIndividual__c, 
            npe01__One2OneContact__c, npo02__Formal_Greeting__c, npo02__Informal_Greeting__c, Number_of_Household_Members__c from Account];
        system.assertEquals(3, listAcc.size());
        
        for (Account acc : listAcc) {
            system.assertNotEquals(null, acc.npe01__One2OneContact__c);
            system.assertNotEquals(null, acc.npo02__Formal_Greeting__c);
            system.assertNotEquals(null, acc.npo02__Informal_Greeting__c);
            system.assertEquals(null, acc.npo02__SYSTEM_CUSTOM_NAMING__c);
            system.assertEquals(CAO_Constants.HH_ACCOUNT_TYPE, acc.npe01__SYSTEM_AccountType__c);
            system.assertEquals(true, acc.npe01__SYSTEMIsIndividual__c);
            // single contact
            if (acc.Id == listCon[4].AccountId) {
            	system.assertEquals(listCon[4].Id, acc.npe01__One2OneContact__c);
                system.assertEquals(listCon[4].LastName + ' Household', acc.Name);
                system.assertEquals(listCon[4].FirstName, acc.npo02__Informal_Greeting__c);
                system.assertEquals(listCon[4].FirstName + ' ' + listCon[4].LastName, acc.npo02__Formal_Greeting__c);
                system.assertEquals(1, acc.Number_of_Household_Members__c);
            }
            // 2 contacts, same lastname
            if (acc.Id == listCon[0].AccountId) {
                system.assertEquals(listCon[0].LastName + ' Household', acc.Name);
                system.assertEquals(listCon[0].FirstName + ' and ' + listCon[1].FirstName, acc.npo02__Informal_Greeting__c);
                system.assertEquals(listCon[0].FirstName + ' and ' + listCon[1].FirstName + ' ' + listCon[0].LastName, acc.npo02__Formal_Greeting__c);
                system.assertEquals(2, acc.Number_of_Household_Members__c);
            }
            // 2 contacts, different lastname
            if (acc.Id == listCon[2].AccountId) {
                system.assertEquals(listCon[2].LastName + ' and ' + listCon[3].LastName + ' Household', acc.Name);
                system.assertEquals(listCon[2].FirstName + ' and ' + listCon[3].FirstName, acc.npo02__Informal_Greeting__c);
                system.assertEquals(listCon[2].FirstName + ' ' + listCon[2].LastName + ' and ' + listCon[3].FirstName + ' ' + listCon[3].LastName, acc.npo02__Formal_Greeting__c);
                system.assertEquals(2, acc.Number_of_Household_Members__c);
            }
        }
    
    }

    /*********************************************************************************************************
    * @description
    * operation:
    *    create N new contacts, M non-empty hh account supplied 
    * verify:
    *    contacts added to existing hh accounts
    *    accounts' name and naming fields updated
    *    hh accounts' primary contact not updated
    **********************************************************************************************************/            
    static testMethod void NewContactsNonEmptyHHAccounts() {
        if (strTestOnly != '*' && strTestOnly != 'NewContactsNonEmptyHHAccounts') return;

        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettings();
    
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(5);
        system.assertEquals(null, listCon[0].AccountId); 
        list<Account> listAcc = UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(3, CAO_Constants.HH_ACCOUNT_TYPE);
        insert listAcc;
        system.assertNotEquals(null, listAcc[0].Id);
        
        // specify the account for each contact, some sharing an account.
        listCon[0].AccountId = listAcc[0].Id;
        listCon[1].AccountId = listAcc[0].Id;
        listCon[2].AccountId = listAcc[1].Id;
        listCon[3].AccountId = listAcc[1].Id;
        listCon[4].AccountId = listAcc[2].Id;
        
        // lastname permutations
        system.assertEqualS(listCon[0].LastName, listCon[1].LastName);
        listCon[3].LastName = 'DifferentLastName';
        system.assertNotEquals(listCon[2].LastName, listCon[3].LastName);
        
        // first insert 3 of the contacts and let them hook up to their accounts.
        list<Contact> listConA = new list<Contact>{ listCon[0], listCon[2], listCon[4] };
        insert listConA;
        
        // now we test creating the last two contacts with existing non-empty households
        list<Contact> listConB = new list<Contact>{ listCon[1], listCon[3] };
        Test.startTest();      
        insert listConB;
        test.stopTest();    // to flush any future calls.
        
        listAcc = [select Name, npe01__SYSTEM_AccountType__c, npo02__SYSTEM_CUSTOM_NAMING__c, npe01__SYSTEMIsIndividual__c, 
            npe01__One2OneContact__c, npo02__Formal_Greeting__c, npo02__Informal_Greeting__c, Number_of_Household_Members__c from Account];
        system.assertEquals(3, listAcc.size());
        
        for (Account acc : listAcc) {
            system.assertNotEquals(null, acc.npe01__One2OneContact__c);
            system.assertNotEquals(null, acc.npo02__Formal_Greeting__c);
            system.assertNotEquals(null, acc.npo02__Informal_Greeting__c);
            system.assertEquals(null, acc.npo02__SYSTEM_CUSTOM_NAMING__c);
            system.assertEquals(CAO_Constants.HH_ACCOUNT_TYPE, acc.npe01__SYSTEM_AccountType__c);
            system.assertEquals(true, acc.npe01__SYSTEMIsIndividual__c);
            // single contact
            if (acc.Id == listCon[4].AccountId) {
                system.assertEquals(listCon[4].Id, acc.npe01__One2OneContact__c);
                system.assertEquals(listCon[4].LastName + ' Household', acc.Name);
                system.assertEquals(listCon[4].FirstName, acc.npo02__Informal_Greeting__c);
                system.assertEquals(listCon[4].FirstName + ' ' + listCon[4].LastName, acc.npo02__Formal_Greeting__c);
                system.assertEquals(1, acc.Number_of_Household_Members__c);
            }
            // 2 contacts, same lastname
            if (acc.Id == listCon[0].AccountId) {
                system.assertEquals(listCon[0].LastName + ' Household', acc.Name);
                system.assertEquals(listCon[0].FirstName + ' and ' + listCon[1].FirstName, acc.npo02__Informal_Greeting__c);
                system.assertEquals(listCon[0].FirstName + ' and ' + listCon[1].FirstName + ' ' + listCon[0].LastName, acc.npo02__Formal_Greeting__c);
                system.assertEquals(2, acc.Number_of_Household_Members__c);
            }
            // 2 contacts, different lastname
            if (acc.Id == listCon[2].AccountId) {
                system.assertEquals(listCon[2].LastName + ' and ' + listCon[3].LastName + ' Household', acc.Name);
                system.assertEquals(listCon[2].FirstName + ' and ' + listCon[3].FirstName, acc.npo02__Informal_Greeting__c);
                system.assertEquals(listCon[2].FirstName + ' ' + listCon[2].LastName + ' and ' + listCon[3].FirstName + ' ' + listCon[3].LastName, acc.npo02__Formal_Greeting__c);
                system.assertEquals(2, acc.Number_of_Household_Members__c);
            }
        }
    
    }

    /*********************************************************************************************************
    * @description
    * operation:
    *    create N new contacts, household object supplied 
    * verify:
    *    N hh accounts created
    *    accounts' name and naming fields set
    *    hh accounts' primary contact set to new contact
    **********************************************************************************************************/            
    static testMethod void NewContactsNoAccountsWithHHObj() {
        if (strTestOnly != '*' && strTestOnly != 'NewContactsNoAccountsWithHHObj') return;

        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettings();
    
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(5);
        system.assertEquals(null, listCon[0].AccountId); 
        
        npo02__household__c hh = new npo02__household__c();
        insert hh;
        
        // assign a HH object to some of the new contacts
        listCon[0].npo02__Household__c = hh.Id;
        listCon[1].npo02__Household__c = hh.Id;
        listCon[3].npo02__Household__c = hh.Id;        
        
        Test.startTest();      
        insert listCon;
        test.stopTest();    // to flush any future calls.
        
        set<Id> setConId = new set<Id>();
        for (Contact con : listCon) 
            setConId.add(con.Id);
        list<Account> listAcc = [select Name, npe01__SYSTEM_AccountType__c, npo02__SYSTEM_CUSTOM_NAMING__c, npe01__SYSTEMIsIndividual__c, 
            npe01__One2OneContact__c, npo02__Formal_Greeting__c, npo02__Informal_Greeting__c, Number_of_Household_Members__c from Account
            where npe01__One2OneContact__c in :setConId];
        system.assertEquals(5, listAcc.size());
        
        for (Account acc : listAcc) {
            system.assertNotEquals(null, acc.npe01__One2OneContact__c);
            system.assertNotEquals(null, acc.npo02__Formal_Greeting__c);
            system.assertNotEquals(null, acc.npo02__Informal_Greeting__c);
            system.assertEquals(null, acc.npo02__SYSTEM_CUSTOM_NAMING__c);
            system.assertEquals(CAO_Constants.HH_ACCOUNT_TYPE, acc.npe01__SYSTEM_AccountType__c);
            system.assertEquals(true, acc.npe01__SYSTEMIsIndividual__c);
            if (acc.npe01__One2OneContact__c == listCon[1].Id) {
                system.assertEquals(listCon[1].LastName + ' Household', acc.Name);
                system.assertEquals(listCon[1].FirstName, acc.npo02__Informal_Greeting__c);
                system.assertEquals(listCon[1].FirstName + ' ' + listCon[1].LastName, acc.npo02__Formal_Greeting__c);
                system.assertEquals(1, acc.Number_of_Household_Members__c);
            }
        }
        
        list<Contact> listCon2 = [select Id, Name, npo02__Household__c from Contact where npo02__Household__c = :hh.Id];
        system.assertEquals(3, listCon2.size());
    
    }

    /*********************************************************************************************************
    * @description
    * operation:
    *     update N existing contacts w/ HH accounts to M different HH accounts 
    * verify:
    *    N-M hh accounts originally created get deleted
    *    accounts' name and naming fields set
    *    hh accounts' primary contact set to one of the HH contacts
    **********************************************************************************************************/            
     static testMethod void UpdateContactsMergeHHAccounts() {
        if (strTestOnly != '*' && strTestOnly != 'UpdateContactsMergeHHAccounts') return;

        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettings();
    
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(5);
        system.assertEquals(null, listCon[0].AccountId); 
        insert listCon;

        list<Account> listAcc = [select Name, npe01__SYSTEM_AccountType__c, npo02__SYSTEM_CUSTOM_NAMING__c, npe01__SYSTEMIsIndividual__c, 
            npe01__One2OneContact__c, npo02__Formal_Greeting__c, npo02__Informal_Greeting__c from Account];
        system.assertEquals(5, listAcc.size());
        
        // refresh our contacts to get their accounts
        listCon = [select Name, FirstName, LastName, AccountId from Contact];
        system.assertEquals(5, listCon.size());
        system.assertNotEquals(null, listCon[0].AccountId);
        
        // now merge some HH Accounts
        listCon[0].AccountId = listAcc[0].Id;
        listCon[1].AccountId = listAcc[0].Id;
        listCon[2].AccountId = listAcc[1].Id;
        listCon[3].AccountId = listAcc[1].Id;
        listCon[4].AccountId = listAcc[4].Id;
        
        // lastname permutations
        system.assertEqualS(listCon[0].LastName, listCon[1].LastName);
        listCon[3].LastName = 'DifferentLastName';
        system.assertNotEquals(listCon[2].LastName, listCon[3].LastName);

        // Force the primary contact on each to ensure proper naming order
        listAcc[0].npe01__One2OneContact__c = listCon[0].Id;
        listAcc[1].npe01__One2OneContact__c = listCon[2].Id;
        listAcc[2].npe01__One2OneContact__c = listCon[4].Id;
        update listAcc;
                
        Test.startTest();      
        update listCon;
        test.stopTest();    // to flush any future calls.
        
        listAcc = [select Name, npe01__SYSTEM_AccountType__c, npo02__SYSTEM_CUSTOM_NAMING__c, npe01__SYSTEMIsIndividual__c, 
            npe01__One2OneContact__c, npo02__Formal_Greeting__c, npo02__Informal_Greeting__c, Number_of_Household_Members__c from Account where Number_of_Household_Members__c>0];
        system.assertEquals(3, listAcc.size());
        
        for (Account acc : listAcc) {
            system.assertNotEquals(null, acc.npe01__One2OneContact__c);
            system.assertNotEquals(null, acc.npo02__Formal_Greeting__c);
            system.assertNotEquals(null, acc.npo02__Informal_Greeting__c);
            system.assertEquals(null, acc.npo02__SYSTEM_CUSTOM_NAMING__c);
            system.assertEquals(CAO_Constants.HH_ACCOUNT_TYPE, acc.npe01__SYSTEM_AccountType__c);
            system.assertEquals(true, acc.npe01__SYSTEMIsIndividual__c);
            // single contact
            if (acc.Id == listCon[4].AccountId) {
                system.assertEquals(listCon[4].Id, acc.npe01__One2OneContact__c);
                system.assertEquals(listCon[4].LastName + ' Household', acc.Name);
                system.assertEquals(listCon[4].FirstName, acc.npo02__Informal_Greeting__c);
                system.assertEquals(listCon[4].FirstName + ' ' + listCon[4].LastName, acc.npo02__Formal_Greeting__c);
                system.assertEquals(1, acc.Number_of_Household_Members__c);
            }
            // 2 contacts, same lastname
            if (acc.Id == listCon[0].AccountId) {
                system.assertEquals(listCon[0].LastName + ' Household', acc.Name);
                system.assertEquals(listCon[0].FirstName + ' and ' + listCon[1].FirstName, acc.npo02__Informal_Greeting__c);
                system.assertEquals(listCon[0].FirstName + ' and ' + listCon[1].FirstName + ' ' + listCon[0].LastName, acc.npo02__Formal_Greeting__c);
                system.assertEquals(2, acc.Number_of_Household_Members__c);
            }
            // 2 contacts, different lastname
            if (acc.Id == listCon[2].AccountId) {
                system.assertEquals(listCon[2].LastName + ' and ' + listCon[3].LastName + ' Household', acc.Name);
                system.assertEquals(listCon[2].FirstName + ' and ' + listCon[3].FirstName, acc.npo02__Informal_Greeting__c);
                system.assertEquals(listCon[2].FirstName + ' ' + listCon[2].LastName + ' and ' + listCon[3].FirstName + ' ' + listCon[3].LastName, acc.npo02__Formal_Greeting__c);
                system.assertEquals(2, acc.Number_of_Household_Members__c);
            }
        }
    }
    
    /*********************************************************************************************************
    * @description
    * operation:
    *    split N multi member HH accounts into 2N HH accounts 
    * verify:
    *    2N HH accounts remain
    *    accounts' name and naming fields set
    *    hh accounts' primary contact set to one of the HH contacts
    **********************************************************************************************************/            
     static testMethod void UpdateContactsSplitHHAccounts() {
        if (strTestOnly != '*' && strTestOnly != 'UpdateContactsSplitHHAccounts') return;

        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettings();
    
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(5);
        system.assertEquals(null, listCon[0].AccountId); 

        list<Account> listAcc = UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(3, CAO_Constants.HH_ACCOUNT_TYPE);
        insert listAcc;
        system.assertNotEquals(null, listAcc[0].Id);
 
        // setup HH Accounts
        listCon[0].AccountId = listAcc[0].Id;
        listCon[0].npo02__Household_Naming_Order__c = 0;
        listCon[1].AccountId = listAcc[0].Id;
        listCon[1].npo02__Household_Naming_Order__c = 1;
        listCon[2].AccountId = listAcc[1].Id;
        listCon[2].npo02__Household_Naming_Order__c = 2;
        listCon[3].AccountId = listAcc[1].Id;
        listCon[3].npo02__Household_Naming_Order__c = 3;
        listCon[4].AccountId = listAcc[1].Id;
        listCon[4].npo02__Household_Naming_Order__c = 4;
        
        insert listCon;
        
        // refresh our contacts to get their accounts
        listCon = [select Name, FirstName, LastName, AccountId from Contact order by FirstName];
        system.assertEquals(5, listCon.size());
        system.assertNotEquals(null, listCon[0].AccountId);        
        
        // lastname permutations
        system.assertEqualS(listCon[0].LastName, listCon[1].LastName);
        listCon[3].LastName = 'DifferentLastName';
        system.assertNotEquals(listCon[2].LastName, listCon[3].LastName);
        
        // now split the HH's
        listCon[1].AccountId = listAcc[2].Id;
        listCon[4].AccountId = listAcc[2].Id;
        
        // Force the primary contact on each to ensure proper naming order
        // Update separately so that Name field doesn't accidentally get overwritten
        // Don't want to re-query first because the list may come back in a different order
        list<Account> updtAccts = new list<Account>{
            new Account(Id=listCon[0].AccountId, npe01__One2OneContact__c = listCon[0].Id),
            new Account(Id=listCon[1].AccountId, npe01__One2OneContact__c = listCon[1].Id),
            new Account(Id=listCon[2].AccountId, npe01__One2OneContact__c = listCon[2].Id)
        };
        update updtAccts;        
        
        Test.startTest();      
        update listCon;
        test.stopTest();    // to flush any future calls.
        
        listAcc = [select Name, npe01__SYSTEM_AccountType__c, npo02__SYSTEM_CUSTOM_NAMING__c, npe01__SYSTEMIsIndividual__c, 
            npe01__One2OneContact__c, npo02__Formal_Greeting__c, npo02__Informal_Greeting__c, Number_of_Household_Members__c from Account order by Name];
        system.assertEquals(3, listAcc.size());
        
        for (Account acc : listAcc) {
            system.assertNotEquals(null, acc.npe01__One2OneContact__c);
            system.assertNotEquals(null, acc.npo02__Formal_Greeting__c);
            system.assertNotEquals(null, acc.npo02__Informal_Greeting__c);
            system.assertEquals(null, acc.npo02__SYSTEM_CUSTOM_NAMING__c);
            system.assertEquals(CAO_Constants.HH_ACCOUNT_TYPE, acc.npe01__SYSTEM_AccountType__c);
            system.assertEquals(true, acc.npe01__SYSTEMIsIndividual__c);
            // 2 contacts, same lastname
            if (acc.Id == listCon[1].AccountId) {
                system.assertEquals(listCon[1].LastName + ' Household', acc.Name);
                system.assertEquals(listCon[1].FirstName + ' and ' + listCon[4].FirstName, acc.npo02__Informal_Greeting__c);
                system.assertEquals(listCon[1].FirstName + ' and ' + listCon[4].FirstName + ' ' + listCon[4].LastName, acc.npo02__Formal_Greeting__c);
                system.assertEquals(2, acc.Number_of_Household_Members__c);
            }
            // now single contact
            if (acc.Id == listCon[0].AccountId) {
                system.assertEquals(listCon[0].Id, acc.npe01__One2OneContact__c);
                system.assertEquals(listCon[0].LastName + ' Household', acc.Name);
                system.assertEquals(listCon[0].FirstName, acc.npo02__Informal_Greeting__c);
                system.assertEquals(listCon[0].FirstName + ' ' + listCon[0].LastName, acc.npo02__Formal_Greeting__c);
                system.assertEquals(1, acc.Number_of_Household_Members__c);
            }
            // 2 contacts, different lastname
            if (acc.Id == listCon[2].AccountId) {
                system.assertEquals(listCon[2].LastName + ' and ' + listCon[3].LastName + ' Household', acc.Name);
                system.assertEquals(listCon[2].FirstName + ' and ' + listCon[3].FirstName, acc.npo02__Informal_Greeting__c);
                system.assertEquals(listCon[2].FirstName + ' ' + listCon[2].LastName + ' and ' + listCon[3].FirstName + ' ' + listCon[3].LastName, acc.npo02__Formal_Greeting__c);
                system.assertEquals(2, acc.Number_of_Household_Members__c);
            }
        }
    }

    /*********************************************************************************************************
    * @description
    * operation:
    *    delete contacts from single and multi-contact HH Accounts
    * verify:
    *    HH Accounts with no remaining contacts are deleted
    *    HH Accounts with remaining contacts remain
    *    accounts' name and naming fields set
    *    hh accounts' primary contact set to one of the HH contacts
    **********************************************************************************************************/            
     static testMethod void DeleteContactsFromHHAccounts() {
        if (strTestOnly != '*' && strTestOnly != 'DeleteContactsFromHHAccounts') return;

        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettings();
    
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(5);
        system.assertEquals(null, listCon[0].AccountId); 

        list<Account> listAcc = UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(3, CAO_Constants.HH_ACCOUNT_TYPE);
        insert listAcc;
        system.assertNotEquals(null, listAcc[0].Id);
 
        // setup HH Accounts
        listCon[0].AccountId = listAcc[0].Id;
        listCon[1].AccountId = listAcc[0].Id;
        listCon[2].AccountId = listAcc[1].Id;
        listCon[3].AccountId = listAcc[1].Id;
        listCon[4].AccountId = listAcc[2].Id;
        
        insert listCon;
        
        // refresh our contacts to get their accounts
        listCon = [select Name, FirstName, LastName, AccountId from Contact];
        system.assertEquals(5, listCon.size());
        system.assertNotEquals(null, listCon[0].AccountId);        
        
        // lastname permutations
        system.assertEqualS(listCon[0].LastName, listCon[1].LastName);
        listCon[3].LastName = 'DifferentLastName';
        system.assertNotEquals(listCon[2].LastName, listCon[3].LastName);
        
        // create the list of Contacts to delete
        list<Contact> listConDel = new list<Contact>();
        listConDel.add(listCon[1]); // con0 remains in HH account
        listConDel.add(listCon[4]); // no contacts remain in single contact HH account
        listConDel.add(listCon[2]);
        listConDel.add(listCon[3]); // no contacts remain in multi-contact HH account
        
        Test.startTest();      
        delete listConDel;
        test.stopTest();    // to flush any future calls.
        
        listCon = [select Id, FirstName, LastName from Contact];
        system.assertEquals(1, listCon.size());
        
        listAcc = [select Name, npe01__SYSTEM_AccountType__c, npo02__SYSTEM_CUSTOM_NAMING__c, npe01__SYSTEMIsIndividual__c, 
            npe01__One2OneContact__c, npo02__Formal_Greeting__c, npo02__Informal_Greeting__c, Number_of_Household_Members__c from Account where Number_of_Household_Members__c>0];
        system.assertEquals(1, listAcc.size());
        
        Account acc = listAcc[0];
        system.assertEquals(null, acc.npo02__SYSTEM_CUSTOM_NAMING__c);
        system.assertEquals(CAO_Constants.HH_ACCOUNT_TYPE, acc.npe01__SYSTEM_AccountType__c);
        system.assertEquals(true, acc.npe01__SYSTEMIsIndividual__c);
        system.assertEquals(listCon[0].Id, acc.npe01__One2OneContact__c);
        system.assertEquals(listCon[0].LastName + ' Household', acc.Name);
        system.assertEquals(listCon[0].FirstName, acc.npo02__Informal_Greeting__c);
        system.assertEquals(listCon[0].FirstName + ' ' + listCon[0].LastName, acc.npo02__Formal_Greeting__c);
        system.assertEquals(1, acc.Number_of_Household_Members__c);
    }

    /*********************************************************************************************************
    * @description
    * operation:
    *    delete and then undelete contacts from single and multi-contact HH Accounts
    * verify:
    *    contacts added back to existing hh accounts
    *    accounts' name and naming fields updated
    *     hh accounts' primary contact valid
    **********************************************************************************************************/            
    static testMethod void UndeleteContactsFromHHAccounts() {
        if (strTestOnly != '*' && strTestOnly != 'UndeleteContactsFromHHAccounts') return;

        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettings();
    
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(5);
        system.assertEquals(null, listCon[0].AccountId); 

        list<Account> listAcc = UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(3, CAO_Constants.HH_ACCOUNT_TYPE);
        insert listAcc;
        system.assertNotEquals(null, listAcc[0].Id);
 
        // setup HH Accounts
        listCon[0].AccountId = listAcc[0].Id;
        listCon[1].AccountId = listAcc[0].Id;
        listCon[2].AccountId = listAcc[1].Id;
        listCon[3].AccountId = listAcc[1].Id;
        listCon[4].AccountId = listAcc[2].Id;
        
        insert listCon;
        
        // refresh our contacts to get their accounts
        listCon = [select Name, FirstName, LastName, AccountId from Contact order by Name];
        system.assertEquals(5, listCon.size());
        system.assertNotEquals(null, listCon[0].AccountId);        
        
        // lastname permutations
        system.assertEqualS(listCon[0].LastName, listCon[1].LastName);
        listCon[3].LastName = 'DifferentLastName';
        system.assertNotEquals(listCon[2].LastName, listCon[3].LastName);
        
        // create the list of Contacts to delete
        list<Contact> listConDel = new list<Contact>();
        listConDel.add(listCon[1]); // con0 remains in HH account
        listConDel.add(listCon[4]); // no contacts remain in single contact HH account
        listConDel.add(listCon[2]);
        listConDel.add(listCon[3]); // no contacts remain in multi-contact HH account
        
        Test.startTest();      
        delete listConDel;
        list<Contact> listCon2 = [select Id, Name from Contact order by Name];
        system.assertEquals(1, listCon2.size());
        list<Account> listAcc2 = [select Id, Name from Account where id = :listAcc[0].Id];
        system.assertEquals(1, listAcc2.size());
             
        // unfortunately, we can't undelete any contact whose account was also deleted.
        // Salesforce defaultly supports deleting the contacts of deleted accounts, and
        // undeleting them when the account is undeleted.
        // but due to our trigger on contact delete, also deleting the hh account,
        // there is no way to undelete the contact.  we do have an issue in gitHub for this.
        // so for this test, only undelete the contacts whose account wasn't deleted.
        undelete listCon[1];
        test.stopTest();    // to flush any future calls.
       
        listCon = [select Id, FirstName, LastName, AccountId from Contact order by Name];
        system.assertEquals(2, listCon.size());
        
        listAcc = [select Name, npe01__SYSTEM_AccountType__c, npo02__SYSTEM_CUSTOM_NAMING__c, npe01__SYSTEMIsIndividual__c, 
            npe01__One2OneContact__c, npo02__Formal_Greeting__c, npo02__Informal_Greeting__c, Number_of_Household_Members__c from Account where Number_of_Household_Members__c>0];
        system.assertEquals(1, listAcc.size());
        
        // Determine which contact is the primary contact
        Integer contact1 = (listAcc[0].npe01__One2OneContact__c == listCon[0].Id ? 0 : 1);
        Integer contact2 = (contact1 == 1 ? 0 : 1);

        for (Account acc : listAcc) {
            system.assertNotEquals(null, acc.npe01__One2OneContact__c);
            system.assertNotEquals(null, acc.npo02__Formal_Greeting__c);
            system.assertNotEquals(null, acc.npo02__Informal_Greeting__c);
            system.assertEquals(null, acc.npo02__SYSTEM_CUSTOM_NAMING__c);
            system.assertEquals(CAO_Constants.HH_ACCOUNT_TYPE, acc.npe01__SYSTEM_AccountType__c);
            system.assertEquals(true, acc.npe01__SYSTEMIsIndividual__c);
            // 2 contacts, same lastname
            if (acc.Id == listCon[0].AccountId) {
                system.assertEquals(listCon[contact1].LastName + ' Household', acc.Name);
                system.assertEquals(listCon[contact1].FirstName + ' and ' + listCon[contact2].FirstName, acc.npo02__Informal_Greeting__c);
                system.assertEquals(listCon[contact1].FirstName + ' and ' + listCon[contact2].FirstName + ' ' + listCon[contact1].LastName, acc.npo02__Formal_Greeting__c);
                system.assertEquals(2, acc.Number_of_Household_Members__c);
            }
        }
    
    }

    /*********************************************************************************************************
    * @description
    * operation:
    *    updating existing contacts from 1:1 and bucket model to HH Accounts
    * verify:
    *    1:1 accounts deleted, bucket account left alone
    *    accounts' name and naming fields updated
    *    hh accounts' primary contact valid
    **********************************************************************************************************/    
    static testmethod void UpdateOneToOneAndBucketContactsToHHAccounts() {
        if (strTestOnly != '*' && strTestOnly != 'UpdateOneToOneAndBucketContactsToHHAccounts') return;

        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettings();
    
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(6);
        system.assertEquals(null, listCon[0].AccountId); 

        list<Account> listAccOne2One = UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(3, CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE);
        insert listAccOne2One;
        system.assertNotEquals(null, listAccOne2One[0].Id);
 
        ID bucketId = CAO_Constants.setIndividualAccountForTests(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS);
        system.assertNotEquals(null, bucketId);
        
        // create our 1:1 and Bucket contacts
        listCon[0].AccountId = listAccOne2One[0].Id;
        listCon[1].AccountId = listAccOne2One[1].Id;
        listCon[2].AccountId = listAccOne2One[2].Id;
        listCon[3].AccountId = bucketId;
        listCon[4].AccountId = bucketId;
        listCon[5].AccountId = bucketId;
        insert listCon;

        // refresh our contacts to get their accounts
        listCon = [select Name, FirstName, LastName, AccountId from Contact];
        system.assertEquals(6, listCon.size());
        system.assertNotEquals(null, listCon[0].AccountId);   
        
        list<Account> listAcc = [select Name, npe01__SYSTEM_AccountType__c, npo02__SYSTEM_CUSTOM_NAMING__c, npe01__SYSTEMIsIndividual__c, 
            npe01__One2OneContact__c, npo02__Formal_Greeting__c, npo02__Informal_Greeting__c from Account];
        UTIL_Debug.debug('****' +listAcc);
        system.assertEquals(4, listAcc.size()); // 3 1:1 and 1 Bucket
        
        // how clear out their accounts to cause them to get an HH Account
        listCon[0].AccountId = null;
        listCon[1].AccountId = null;     
        listCon[2].AccountId = null;     
        listCon[3].AccountId = null;
        listCon[4].AccountId = null;     
        listCon[5].AccountId = null;
            
        Test.startTest();
        update listCon;
        Test.stopTest();
        
        listAcc = [select Name, npe01__SYSTEM_AccountType__c, npo02__SYSTEM_CUSTOM_NAMING__c, npe01__SYSTEMIsIndividual__c, 
            npe01__One2OneContact__c, npo02__Formal_Greeting__c, npo02__Informal_Greeting__c, Number_of_Household_Members__c from Account
            where Number_of_Household_Members__c>0];
        UTIL_Debug.debug('****' +listAcc);
        system.assertEquals(6, listAcc.size()); // 6 HH
        
        for (Account acc : listAcc) {
            system.assertNotEquals(null, acc.npe01__One2OneContact__c);
            system.assertNotEquals(null, acc.npo02__Formal_Greeting__c);
            system.assertNotEquals(null, acc.npo02__Informal_Greeting__c);
            system.assertEquals(null, acc.npo02__SYSTEM_CUSTOM_NAMING__c);
            system.assertEquals(CAO_Constants.HH_ACCOUNT_TYPE, acc.npe01__SYSTEM_AccountType__c);
            system.assertEquals(true, acc.npe01__SYSTEMIsIndividual__c);
            system.assertEquals(1, acc.Number_of_Household_Members__c);
            if (acc.npe01__One2OneContact__c == listCon[1].Id) {
                system.assertEquals(listCon[1].LastName + ' Household', acc.Name);
                system.assertEquals(listCon[1].FirstName, acc.npo02__Informal_Greeting__c);
                system.assertEquals(listCon[1].FirstName + ' ' + listCon[1].LastName, acc.npo02__Formal_Greeting__c);
            }
        }
    }

    /*********************************************************************************************************
    * @description
    * operation:
    *    change owner on N contacts with various types of accounts
    * verify:
    *    accounts' owner changed to match
    *    works for 1:1 and HH accounts
    *    no change for HH accounts with multiple contacts
    *    no change for any other type of account
    **********************************************************************************************************/                
    static testMethod void changeContactOwners() {
        if (strTestOnly != '*' && strTestOnly != 'changeContactOwners') return;
        
        Id ownerId1 = UserInfo.getUserId();             
        User u2 = UTIL_UnitTestData_TEST.CreateNewUserForTests('changeContactOnwerTest@bar.com');

        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettings();
        CAO_Constants.setIndividualAccountForTests(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS);
    
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(7);
        system.assertEquals(null, listCon[0].AccountId); 
        
        list<Account> listAcc = UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, CAO_Constants.HH_ACCOUNT_TYPE);
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE));
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(1, CAO_Constants.BUCKET_ORGANIZATION_TYPE));
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(1, null));
        insert listAcc;
        
        listCon[0].AccountId = listAcc[0].Id;   // single HH
        listCon[1].AccountId = listAcc[1].Id;   // multi HH
        listCon[2].AccountId = listAcc[1].Id;   // multi HH
        listCon[3].AccountId = listAcc[2].Id;   // 1:1
        listCon[4].AccountId = listAcc[3].Id;   // 1:1
        listCon[5].AccountId = listAcc[4].Id;   // Bucket
        listCon[6].AccountId = listAcc[5].Id;   // Org
        
        insert listCon;

        // Force the primary contact on each to ensure proper naming order
        // Update separately so that Name field doesn't accidentally get overwritten
        // Don't want to re-query first because the list may come back in a different order
        list<Account> updtAccts = new list<Account>{
            new Account(Id=listAcc[1].Id, npe01__One2OneContact__c = listCon[1].Id)
        };
        update updtAccts;        

        listCon[0].ownerId = u2.Id;   // single HH
        listCon[1].ownerId = u2.Id;   // multi HH
        //listCon[2].ownerId = u2.Id;   // multi HH
        //listCon[3].ownerId = u2.Id;   // 1:1
        listCon[4].ownerId = u2.Id;   // 1:1
        listCon[5].ownerId = u2.Id;   // Bucket
        listCon[6].ownerId = u2.Id;   // Org
        
        Test.startTest();
        update listCon;
        Test.stopTest();
               
        list<Contact> listCon2 = [select Name, OwnerId, Account.Id, Account.OwnerId from Contact where Id in :listCon];
        system.assertEquals(listCon2.size(), listCon.size());
        
        UTIL_Debug.debug('**** listCon2: ' + listCon2);
        for (Contact con : listCon2) {
            if (con.Id == listCon[0].Id || con.ID == listCon[4].Id) {
               system.assertEquals(con.OwnerId, con.Account.OwnerId);
               system.assertEquals(u2.Id, con.OwnerId);
            } else if (con.Id == listCon[2].Id || con.Id == listCon[3].Id) {
               system.assertEquals(con.OwnerId, con.Account.OwnerId);
               system.assertEquals(ownerId1, con.OwnerId);              
            } else {
               system.assertNotEquals(con.OwnerId, con.Account.OwnerId);
               system.assertEquals(u2.Id, con.OwnerId);                     
               system.assertEquals(ownerId1, con.Account.OwnerId);                     
            }
        }
    
    }

    /*********************************************************************************************************
    * @description
    * operation:
    *    change N contacts with HH accounts and primary opps to new HH accounts 
    * verify:
    *    closed opps stay with old HH accounts
    *    open opps move to new HH accounts
    **********************************************************************************************************/            
    static testMethod void changeContactsCheckOpps() {
        if (strTestOnly != '*' && strTestOnly != 'changeContactsCheckOpps') return;

       npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (
            npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR,
            npe01__Enable_Opportunity_Contact_Role_Trigger__c = true,
            npe01__Opportunity_Contact_Role_Default_role__c = 'Donor'
        ));
        
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
            new npo02__Households_Settings__c (
                npo02__Household_Rules__c = HH_Households.NO_HOUSEHOLDS_PROCESSOR,
                npo02__Always_Rollup_to_Primary_Contact__c = false,
                npo02__Enable_Opp_Rollup_Triggers__c = true,
                npo02__Excluded_Account_Opp_Rectypes__c = null,
                npo02__Excluded_Account_Opp_Types__c = null,
                npo02__Excluded_Contact_Opp_Rectypes__c = null,
                npo02__Excluded_Contact_Opp_Types__c = null,
                npo02__Membership_Record_Types__c = null
            ));
  
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(5);
        system.assertEquals(null, listCon[0].AccountId); 
        insert listCon;
        listCon = [select Id, AccountId, Firstname, Lastname from Contact];
        system.assertNotEquals(null, listCon[0].AccountId);
        
        // give the contacts a mixture of open and closed opps
        string strClosed = UTIL_UnitTestData_TEST.getClosedWonStage();
        string strOpen = UTIL_UnitTestData_TEST.getOpenStage();
        list<Opportunity> listOpp = UTIL_UnitTestData_TEST.OppsForContactList(listCon, null, strClosed, system.Today(), 100, null, null);       
        listOpp.addAll(UTIL_UnitTestData_TEST.OppsForContactList(listCon, null, strOpen, system.Today(), 100, null, null)); 
        listOpp[0].AccountId = listCon[0].AccountId;
        listOpp[1].AccountId = listCon[1].AccountId;
        listOpp[2].AccountId = listCon[2].AccountId;
        listOpp[3].AccountId = listCon[3].AccountId;
        listOpp[4].AccountId = listCon[4].AccountId;
        
        listOpp[5].AccountId = listCon[0].AccountId;
        listOpp[6].AccountId = listCon[1].AccountId;
        listOpp[7].AccountId = listCon[2].AccountId;
        listOpp[8].AccountId = listCon[3].AccountId;
        listOpp[9].AccountId = listCon[4].AccountId;
        insert listOpp;
        
        // now do some merging of households
        listCon[1].AccountId = listCon[0].AccountId;
        listCon[3].AccountId = listCon[2].AccountId;
        listCon[4].AccountId = null;

        Test.startTest();
        update listCon;
        Test.stopTest();

        // make sure the open opps didn't stay with old accounts.
        list<Opportunity> listOpp2 = [select Id, AccountId, Amount from Opportunity where IsClosed = false];
        system.assertEquals(5, listOpp2.size());
        for (Opportunity opp : listOpp2) {
            system.assertNotEquals(opp.AccountId, listOpp[6].AccountId);
            system.assertNotEquals(opp.AccountId, listOpp[8].AccountId);
        }  
            
    }

    /*********************************************************************************************************
    * @description
    * operation:
    *    add a contact to an exiting HH Account that has a Primary Contact
    * verify:
    *    the Account's Primary Contact doesn't change
    **********************************************************************************************************/            
    static testMethod void addContactCheckAccountPrimaryContact() {
        if (strTestOnly != '*' && strTestOnly != 'addContactCheckAccountPrimaryContact') return;

       npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (
            npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR,
            npe01__Enable_Opportunity_Contact_Role_Trigger__c = true,
            npe01__Opportunity_Contact_Role_Default_role__c = 'Donor'
        ));
        
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(6);
        system.assertEquals(null, listCon[0].AccountId);
        insert listCon;
        listCon = [select Id, AccountId, Account.npe01__One2OneContact__c from Contact];
        for (integer i = 0; i < 6; i++) {
            system.assertNotEquals(null, listCon[i].AccountId);        
            system.assertNotEquals(null, listCon[i].Account.npe01__One2OneContact__c);        
        } 
        
        list<Contact> listCon2 = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(6);
        system.assertEquals(null, listCon2[0].AccountId);
        for (integer i = 0; i < 6; i++) {
            listCon2[i].AccountId = listCon[i].AccountId;
        }
        insert listCon2;        
        listCon2 = [select Id, AccountId, Account.npe01__One2OneContact__c from Contact where Id in :listCon2];
        for (integer i = 0; i < 6; i++) {
            system.assertEquals(listCon2[i].AccountId, listCon[i].AccountId);        
            system.assertEquals(listCon2[i].Account.npe01__One2OneContact__c, listCon[i].Account.npe01__One2OneContact__c);        
        } 
    }
        
    /*********************************************************************************************************
    * @description
    * operation:
    *    create a new account with HH record type
    * verify:
    *    the system fields on the account are set correctly
    **********************************************************************************************************/            
    static testMethod void newHHAccountWithRecordTypeOnly() {
        if (strTestOnly != '*' && strTestOnly != 'newHHAccountWithRecordTypeOnly') return;
        
        ID rtIdAccountHH = ACCT_IndividualAccounts_TDTM.rtIdForAccountModel(true);
        if (rtIdAccountHH != null) {
            Account acc = new Account(name='test', RecordTypeId = rtIdAccountHH);
            insert acc;
            acc = [select Id, Name, RecordTypeId, npe01__SYSTEMISINDIVIDUAL__c, npe01__SYSTEM_AccountType__c 
                from Account where Id = :acc.Id];
            system.assertEquals(true, acc.npe01__SYSTEMISINDIVIDUAL__c);
            system.assertEquals(CAO_Constants.HH_ACCOUNT_TYPE, acc.npe01__SYSTEM_AccountType__c);
        }
    }

    /*********************************************************************************************************
    * @description
    * operation:
    *    update an existing account to the HH record type
    * verify:
    *    the system fields on the account are set correctly
    **********************************************************************************************************/            
    static testMethod void updateAccountToHHRecordType() {
        if (strTestOnly != '*' && strTestOnly != 'updateAccountToHHRecordType') return;
        
        ID rtIdAccountHH = ACCT_IndividualAccounts_TDTM.rtIdForAccountModel(true);
        if (rtIdAccountHH != null) {
            Account acc = new Account(name='test');
            insert acc;
            acc = [select Id, Name, RecordTypeId, npe01__SYSTEMISINDIVIDUAL__c, npe01__SYSTEM_AccountType__c 
                from Account where Id = :acc.Id];
            system.assertNotEquals(rtIdAccountHH, acc.RecordTypeId);
            system.assertEquals(false, acc.npe01__SYSTEMISINDIVIDUAL__c);
            system.assertEquals(null, acc.npe01__SYSTEM_AccountType__c);
            acc.RecordTypeId = rtIdAccountHH;
            update acc;
            acc = [select Id, Name, RecordTypeId, npe01__SYSTEMISINDIVIDUAL__c, npe01__SYSTEM_AccountType__c 
                from Account where Id = :acc.Id];
            system.assertEquals(true, acc.npe01__SYSTEMISINDIVIDUAL__c);
            system.assertEquals(CAO_Constants.HH_ACCOUNT_TYPE, acc.npe01__SYSTEM_AccountType__c);            
        }
    }

    /*********************************************************************************************************
    * @description
    * operation:
    *     add a contact to an exiting HH Account with auto-naming off
    * verify:
    *    the Account's Number of Household Members is still updated
    **********************************************************************************************************/            
    static testMethod void addContactCheckNumberHHMembers() {
        if (strTestOnly != '*' && strTestOnly != 'addContactCheckNumberHHMembers') return;

        // turn off household naming        
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
            new npo02__Households_Settings__c (
                npo02__Household_Rules__c = HH_Households.NO_HOUSEHOLDS_PROCESSOR,
                npo02__Advanced_Household_Naming__c = false
            ));
        
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(6);
        system.assertEquals(null, listCon[0].AccountId);
        insert listCon;
        listCon = [select Id, AccountId, Account.npe01__One2OneContact__c from Contact];
        for (integer i = 0; i < 6; i++) {
            system.assertNotEquals(null, listCon[i].AccountId);        
            system.assertNotEquals(null, listCon[i].Account.npe01__One2OneContact__c);        
        } 
        
        list<Contact> listCon2 = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(6);
        system.assertEquals(null, listCon2[0].AccountId);
        for (integer i = 0; i < 6; i++) {
            listCon2[i].AccountId = listCon[i].AccountId;
        }
        Test.startTest();
        insert listCon2;
        Test.stopTest(); // needed for FutureUpdateNames to update number of household members
                
        listCon2 = [select Id, AccountId, Account.npe01__One2OneContact__c, Account.Number_of_Household_Members__c from Contact where Id in :listCon2];
        list<Account> listAcc = [select Id, Name, Number_of_Household_Members__c from Account];
        system.assertEquals(6, listAcc.size());
        for (integer i = 0; i < 6; i++) {
            system.assertEquals(listCon2[i].AccountId, listCon[i].AccountId);        
            system.assertEquals(listCon2[i].Account.npe01__One2OneContact__c, listCon[i].Account.npe01__One2OneContact__c);
            system.assertEquals(2, listAcc[i].Number_of_Household_Members__c);
            system.assertEquals(2, listCon2[i].Account.Number_of_Household_Members__c);
        } 
    }

    /*********************************************************************************************************
    WHAT IS NOT TESTED:
        updating existing contacts from HH Accounts to 1:1 and bucket
        deleting a mixture of 1:1, bucket, and HH Accounts
        ensuring that in all tests cases, HH accounts with rollups get rolled up correctly
        ensuring that in account delete scenarios, HH accounts with opps don't get deleted
        more extensive testing with Private__c field
        scenarios with both a HH Account and a HH object for the same contact(s)
        
    **********************************************************************************************************/            
}