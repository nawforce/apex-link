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
* @date 2009
* @group Affiliations
* @group-content ../../ApexDocContent/Affiliations.htm
* @description Test class for affiliations.
*/
@isTest
public class AFFL_Affiliations_TEST {
    
    // if you only want to run one test in this class, fill in its name here.
    // if you want to run all tests, then use '*'
    private static string strTestOnly = '*';
            
    /*******************************************************************************************************
    * @description Creates a new contact with an organizational account. Verifies an affiliation was created.
    */
    static testMethod void newContactOnOrgAccount() {
        if (strTestOnly != '*' && strTestOnly != 'newContactOnOrgAccount') return;
        
        npe5__Affiliations_Settings__c affiliationsSettingsForTests = UTIL_CustomSettingsFacade.getAffiliationsSettingsForTests(
            new npe5__Affiliations_Settings__c(npe5__Automatic_Affiliation_Creation_Turned_On__c = true));
        
        Account acc = new Account(Name='test77');
        insert acc;
        
        String title = 'CEO';
        Contact contact = getContact(acc.Id, title);
        insert contact;
        
        npe5__Affiliation__c[] createdAffiliations = [select id, npe5__Status__c, npe5__StartDate__c, npe5__Role__c, 
            npe5__Organization__c, npe5__EndDate__c, npe5__Contact__c from npe5__Affiliation__c 
            where npe5__Contact__c=:contact.id AND npe5__Organization__c = :acc.id];     
        Id firstAffiliationId = createdAffiliations[0].Id; //storing the id for later use
        
        System.assertEquals(1, createdAffiliations.size());
        system.assertEquals(system.label.npe5.DefaultCurrentStatus, createdAffiliations[0].npe5__Status__c);
        system.assertEquals(title, createdAffiliations[0].npe5__Role__c);
        system.assertEquals(System.Today(), createdAffiliations[0].npe5__StartDate__c);
        
        Account acc2 = new Account(Name='test88');
        insert acc2;
        
        Test.startTest();
        AFFL_Affiliations_TDTM.doNotRunForContacts = false;
        contact.AccountId = acc2.id;
        update contact;
        Test.stopTest();
            
        npe5__Affiliation__c[] createdAffiliations2 = [select id, npe5__Status__c, npe5__StartDate__c, npe5__Role__c, 
            npe5__Organization__c, npe5__EndDate__c, npe5__Contact__c from npe5__Affiliation__c 
            where npe5__Contact__c =:contact.id AND npe5__Organization__c = :acc2.id];        
        
        System.assertEquals(1, createdAffiliations2.size());
        system.assertEquals(system.label.npe5.DefaultCurrentStatus, createdAffiliations2[0].npe5__Status__c);
        system.assertEquals(title, createdAffiliations2[0].npe5__Role__c);
        system.assertEquals(System.Today(), createdAffiliations2[0].npe5__StartDate__c);
        
        createdAffiliations = [select id, npe5__Status__c, npe5__StartDate__c, npe5__Role__c, npe5__Organization__c, 
            npe5__EndDate__c, npe5__Contact__c from npe5__Affiliation__c where id=:firstAffiliationId];
        
        System.assertEquals(1, createdAffiliations.size());
        system.assertEquals(system.label.npe5.DefaultFormerStatus, createdAffiliations[0].npe5__Status__c);
        system.assertEquals(System.Today(), createdAffiliations[0].npe5__EndDate__c);
    }
    
    /*******************************************************************************************************
    * @description Creates a new account with automatic affiliation creation turned off. Verifies no 
    * affiliation was created.
    */

    static testMethod void newContactOnOrgAccountAutoOff() {
        if (strTestOnly != '*' && strTestOnly != 'newContactOnOrgAccountAutoOff') return;
        
        npe5__Affiliations_Settings__c affiliationsSettingsForTests = UTIL_CustomSettingsFacade.getAffiliationsSettingsForTests(
            new npe5__Affiliations_Settings__c(npe5__Automatic_Affiliation_Creation_Turned_On__c = false));
        
        Account acc = new Account(Name='test77');
        insert acc;
        
        Contact con = getContact(acc.Id, 'CEO');
        insert con;
        
        Id firstAffiliationId;
        
        npe5__Affiliation__c[] createdAffiliations = [select id, npe5__Status__c, npe5__StartDate__c, npe5__Role__c, npe5__Organization__c, 
            npe5__EndDate__c, npe5__Contact__c from npe5__Affiliation__c where npe5__Contact__c=:con.id AND npe5__Organization__c = :acc.id];
        
        system.assertEquals(0, createdAffiliations.size());        
    }


    /*******************************************************************************************************
    * @description Creates a new contact with an individual account. Verifies an affiliation was created.
    */
    static testMethod void newContactOnIndividualAccount() {
        if (strTestOnly != '*' && strTestOnly != 'newContactOnIndividualAccount') return;
        
        npe5__Affiliations_Settings__c affiliationsSettingsForTests = UTIL_CustomSettingsFacade.getAffiliationsSettingsForTests(
            new npe5__Affiliations_Settings__c(npe5__Automatic_Affiliation_Creation_Turned_On__c = true));
        
        Account acc = new Account(Name='my bucket account', npe01__SYSTEMIsIndividual__c=true);
        insert acc;
        
        Contact con = getContact(acc.Id, 'CEO');
        insert con;
        
        npe5__Affiliation__c[] createdAffiliations = [select id, npe5__Status__c, npe5__StartDate__c, npe5__Role__c, npe5__Organization__c, 
            npe5__EndDate__c, npe5__Contact__c from npe5__Affiliation__c where npe5__Contact__c=:con.id AND npe5__Organization__c = :acc.id];
        
        system.assertEquals(0, createdAffiliations.size());    
    }
    
    /*******************************************************************************************************
    * @description Creates a contact with a primary affiliation. Updates primary affiliation to new account.
    * Verifies old affiliation marked as former, and new affiliation marked as current.
    */
    static testMethod void contactWithMultipleAffiliations_v2() {
        if (strTestOnly != '*' && strTestOnly != 'contactWithMultipleAffiliations_v2') return;
        
        npe5__Affiliations_Settings__c affiliationsSettingsForTests = UTIL_CustomSettingsFacade.getAffiliationsSettingsForTests(
            new npe5__Affiliations_Settings__c(npe5__Automatic_Affiliation_Creation_Turned_On__c = true));
        
        Account acc = new Account(Name='test77');        
        Account acc2 = new Account(Name='test88');
        insert new Account[] {acc, acc2};
        
        Contact con = getContact(acc.ID, 'CEO');
        insert con;
        
        npe5__Affiliation__c[] createdAffiliations = [select id from npe5__Affiliation__c where npe5__Contact__c=:con.id 
                                                        AND npe5__Organization__c = :acc.id];
        system.assertEquals(1, createdAffiliations.size());
        
        con.AccountId = acc2.id;
        Test.startTest();      
        AFFL_Affiliations_TDTM.doNotRunForContacts = false;
        update con;
        Test.stopTest();
          
        npe5__Affiliation__c[] createdAffiliations2 = [select id from npe5__Affiliation__c where npe5__Contact__c=:con.id 
                                                        AND npe5__Organization__c = :acc2.id];   
        
        createdAffiliations = [select id, npe5__Status__c, npe5__StartDate__c, npe5__Role__c, npe5__Organization__c, npe5__EndDate__c, 
            npe5__Contact__c from npe5__Affiliation__c where npe5__Contact__c=:con.id AND npe5__Organization__c = :acc.id];
        system.assertEquals(system.label.npe5.DefaultFormerStatus, createdAffiliations[0].npe5__Status__c);
        
        createdAffiliations2 = [select id, npe5__Status__c, npe5__StartDate__c, npe5__Role__c, npe5__Organization__c, npe5__EndDate__c, 
            npe5__Contact__c from npe5__Affiliation__c where npe5__Contact__c=:con.id AND npe5__Organization__c = :acc2.id];
        system.assertEquals(system.label.npe5.DefaultCurrentStatus, createdAffiliations2[0].npe5__Status__c);        
    }

    private static Contact getContact(Id accountId, String title) {
    	return new Contact(FirstName = 'test', LastName = 'testerson', AccountId = accountId, Title = title);
    }
   
   
    /*******************************************************************************************************
    * @description Creates contacts with a primary affiliation. Verifies primary affiliations are created.
    */
    static testMethod void newContactsWithPrimaryAffiliation() {
        if (strTestOnly != '*' && strTestOnly != 'newContactsWithPrimaryAffiliation') return;
        
        npe5__Affiliations_Settings__c affiliationsSettingsForTests = UTIL_CustomSettingsFacade.getAffiliationsSettingsForTests(
            new npe5__Affiliations_Settings__c(npe5__Automatic_Affiliation_Creation_Turned_On__c = true));
            
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR));
        CAO_Constants.setIndividualAccountForTests(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS);
    
        // make sure household objects also created, since their trigger was interferring with the affilations trigger.
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
            new npo02__Households_Settings__c (npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR));

        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(8);
        system.assertEquals(null, listCon[0].AccountId); 
        
        list<Account> listAcc = UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, CAO_Constants.HH_ACCOUNT_TYPE);
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE));
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, null));
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(1, CAO_Constants.BUCKET_ORGANIZATION_TYPE));
        insert listAcc;
        
        listCon[0].AccountId = listAcc[0].Id;   // HH account
        listCon[1].AccountId = listAcc[1].Id;   // HH account
        listCon[2].AccountId = listAcc[2].Id;   // 1:1 account
        listCon[3].AccountId = listAcc[3].Id;   // 1:1 account
        listCon[4].AccountId = listAcc[4].Id;   // Org1 account
        listCon[5].AccountId = listAcc[5].Id;   // Org2 account
        listCon[6].AccountId = listAcc[6].Id;   // Bucket account
        listCon[7].AccountId = listAcc[6].Id;   // Bucket account
        
        for (Contact con : listCon) 
            con.Primary_Affiliation__c = listAcc[4].Id; // Org1  
            
        insert listCon;
        
        npe5__Affiliation__c[] listAffl = [select id, npe5__Status__c, npe5__StartDate__c, npe5__Role__c, 
            npe5__Organization__c, npe5__EndDate__c, npe5__Contact__c, npe5__Primary__c from npe5__Affiliation__c];
        
        System.assertEquals(9, listAffl.size());
        for (npe5__Affiliation__c affl : listAffl) {
            system.assertEquals(system.label.npe5.DefaultCurrentStatus, affl.npe5__Status__c);
            system.assertEquals(System.Today(), affl.npe5__StartDate__c);
            if (affl.npe5__Organization__c == listAcc[5].Id) 
                system.assertEquals(false, affl.npe5__Primary__c);
            else
                system.assertEquals(true, affl.npe5__Primary__c);
        }
   }

    /*******************************************************************************************************
    * @description Creates a new contact with a one-to-one account. Verifies an affiliation was created.
    */
    static testMethod void newContactsWithPrimaryAffiliationOnetoOne() {
        if (strTestOnly != '*' && strTestOnly != 'newContactsWithPrimaryAffiliationOnetoOne') return;
        
        npe5__Affiliations_Settings__c affiliationsSettingsForTests = UTIL_CustomSettingsFacade.getAffiliationsSettingsForTests(
            new npe5__Affiliations_Settings__c(npe5__Automatic_Affiliation_Creation_Turned_On__c = true));
            
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR));
        CAO_Constants.setIndividualAccountForTests(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS);
    
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(8);
        system.assertEquals(null, listCon[0].AccountId); 
        
        list<Account> listAcc = UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, CAO_Constants.HH_ACCOUNT_TYPE);
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE));
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, null));
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(1, CAO_Constants.BUCKET_ORGANIZATION_TYPE));
        insert listAcc;
        
        listCon[0].AccountId = listAcc[0].Id;   // HH account
        listCon[1].AccountId = listAcc[1].Id;   // HH account
        listCon[2].AccountId = listAcc[2].Id;   // 1:1 account
        listCon[3].AccountId = listAcc[3].Id;   // 1:1 account
        listCon[4].AccountId = listAcc[4].Id;   // Org1 account
        listCon[5].AccountId = listAcc[5].Id;   // Org2 account
        listCon[6].AccountId = listAcc[6].Id;   // Bucket account
        listCon[7].AccountId = listAcc[6].Id;   // Bucket account
        
        for (Contact con : listCon) 
            con.Primary_Affiliation__c = listAcc[4].Id; // Org1  
            
        insert listCon;
        
        npe5__Affiliation__c[] listAffl = [select id, npe5__Status__c, npe5__StartDate__c, npe5__Role__c, 
            npe5__Organization__c, npe5__EndDate__c, npe5__Contact__c, npe5__Primary__c from npe5__Affiliation__c];
        
        System.assertEquals(9, listAffl.size());
        for (npe5__Affiliation__c affl : listAffl) {
            system.assertEquals(system.label.npe5.DefaultCurrentStatus, affl.npe5__Status__c);
            system.assertEquals(System.Today(), affl.npe5__StartDate__c);
            if (affl.npe5__Organization__c == listAcc[5].Id) 
                system.assertEquals(false, affl.npe5__Primary__c);
            else
                system.assertEquals(true, affl.npe5__Primary__c);
        }
    }
    /*******************************************************************************************************
    * @description Updates contacts' primary affiliation, verifies new primary affiliations are created and
    * old affiliations are not marked primary.
    */
    static testMethod void updateContactsWithPrimaryAffiliation() {
        if (strTestOnly != '*' && strTestOnly != 'updateContactsWithPrimaryAffiliation') return;
        
        npe5__Affiliations_Settings__c affiliationsSettingsForTests = UTIL_CustomSettingsFacade.getAffiliationsSettingsForTests(
            new npe5__Affiliations_Settings__c(npe5__Automatic_Affiliation_Creation_Turned_On__c = true));
            
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR));
        CAO_Constants.setIndividualAccountForTests(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS);
    
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(8);
        system.assertEquals(null, listCon[0].AccountId); 
        
        list<Account> listAcc = UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, CAO_Constants.HH_ACCOUNT_TYPE);
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE));
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, null));
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(1, CAO_Constants.BUCKET_ORGANIZATION_TYPE));
        insert listAcc;
        
        listCon[0].AccountId = listAcc[0].Id;   // HH account
        listCon[1].AccountId = listAcc[1].Id;   // HH account
        listCon[2].AccountId = listAcc[2].Id;   // 1:1 account
        listCon[3].AccountId = listAcc[3].Id;   // 1:1 account
        listCon[4].AccountId = listAcc[4].Id;   // Org1 account
        listCon[5].AccountId = listAcc[5].Id;   // Org2 account
        listCon[6].AccountId = listAcc[6].Id;   // Bucket account
        listCon[7].AccountId = listAcc[6].Id;   // Bucket account
        
        insert listCon;

        // 2 org affiliations should get created
        npe5__Affiliation__c[] listAffl = [select id, npe5__Status__c, npe5__StartDate__c, npe5__Role__c, 
            npe5__Organization__c, npe5__EndDate__c, npe5__Contact__c, npe5__Primary__c from npe5__Affiliation__c];       
        System.assertEquals(2, listAffl.size());

        Test.startTest();
        AFFL_Affiliations_TDTM.doNotRunForContacts = false;
        // now update our contacts with a Primary Affiliation
        for (Contact con : listCon) 
            con.Primary_Affiliation__c = listAcc[4].Id; // Org1  
        update listCon;            
        Test.stopTest();
        
        listAffl = [select id, npe5__Status__c, npe5__StartDate__c, npe5__Role__c, 
            npe5__Organization__c, npe5__EndDate__c, npe5__Contact__c, npe5__Primary__c from npe5__Affiliation__c];
        
        System.assertEquals(9, listAffl.size());
        for (npe5__Affiliation__c affl : listAffl) {
            system.assertEquals(system.label.npe5.DefaultCurrentStatus, affl.npe5__Status__c);
            system.assertEquals(System.Today(), affl.npe5__StartDate__c);
            if (affl.npe5__Organization__c == listAcc[5].Id) 
                system.assertEquals(false, affl.npe5__Primary__c);
            else
                system.assertEquals(true, affl.npe5__Primary__c);
        }
   }
     
    /*******************************************************************************************************
    * @description Updates contacts' primary affiliation and account, verifies new primary affiliations are created and
    * old affiliations are not marked primary.
    */
    static testMethod void updateContactsWithPrimaryAffiliationAndAccount() {
        if (strTestOnly != '*' && strTestOnly != 'updateContactsWithPrimaryAffiliationAndAccount') return;
        
        npe5__Affiliations_Settings__c affiliationsSettingsForTests = UTIL_CustomSettingsFacade.getAffiliationsSettingsForTests(
            new npe5__Affiliations_Settings__c(npe5__Automatic_Affiliation_Creation_Turned_On__c = true));
            
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR));
        CAO_Constants.setIndividualAccountForTests(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS);
    
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(8);
        system.assertEquals(null, listCon[0].AccountId); 
        
        list<Account> listAcc = UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, CAO_Constants.HH_ACCOUNT_TYPE);
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE));
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, null));
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(1, CAO_Constants.BUCKET_ORGANIZATION_TYPE));
        insert listAcc;
        
        listCon[0].AccountId = listAcc[0].Id;   // HH account
        listCon[1].AccountId = listAcc[1].Id;   // HH account
        listCon[2].AccountId = listAcc[2].Id;   // 1:1 account
        listCon[3].AccountId = listAcc[3].Id;   // 1:1 account
        listCon[4].AccountId = listAcc[4].Id;   // Org1 account
        listCon[5].AccountId = listAcc[5].Id;   // Org2 account
        listCon[6].AccountId = listAcc[6].Id;   // Bucket account
        listCon[7].AccountId = listAcc[6].Id;   // Bucket account
        
        insert listCon;

        // 2 org affiliations should get created
        npe5__Affiliation__c[] listAffl = [select id, npe5__Status__c, npe5__StartDate__c, npe5__Role__c, 
            npe5__Organization__c, npe5__EndDate__c, npe5__Contact__c, npe5__Primary__c from npe5__Affiliation__c];       
        System.assertEquals(2, listAffl.size());

        Test.startTest();
        AFFL_Affiliations_TDTM.doNotRunForContacts = false;
        // now update our contacts with a Primary Affiliation and a different account
        for (Contact con : listCon) {
            con.AccountId = listAcc[4].Id; // Org1  
            con.Primary_Affiliation__c = listAcc[4].Id; // Org1
        }  
        update listCon;            
        Test.stopTest();
        
        listAffl = [select id, npe5__Status__c, npe5__StartDate__c, npe5__Role__c, 
            npe5__Organization__c, npe5__EndDate__c, npe5__Contact__c, npe5__Primary__c from npe5__Affiliation__c];
        
        System.assertEquals(9, listAffl.size());
        for (npe5__Affiliation__c affl : listAffl) {
            system.assertEquals(System.Today(), affl.npe5__StartDate__c);
            if (affl.npe5__Organization__c == listAcc[5].Id) { 
                system.assertEquals(false, affl.npe5__Primary__c);
                system.assertEquals(System.Today(), affl.npe5__EndDate__c);
                system.assertEquals(system.label.npe5.DefaultFormerStatus, affl.npe5__Status__c);
            } else {
                system.assertEquals(true, affl.npe5__Primary__c);
                system.assertEquals(system.label.npe5.DefaultCurrentStatus, affl.npe5__Status__c);
            }
        }
    }
    
    /*******************************************************************************************************
    * @description Updates contacts' primary affiliation, verifies new primary affiliations are created and
    * old affiliations are not marked primary.
    */
    static testMethod void updateContactsWithPrimaryAffiliationToDiffAccount() {
        if (strTestOnly != '*' && strTestOnly != 'updateContactsWithPrimaryAffiliationToDiffAccount') return;
        
        npe5__Affiliations_Settings__c affiliationsSettingsForTests = UTIL_CustomSettingsFacade.getAffiliationsSettingsForTests(
            new npe5__Affiliations_Settings__c(npe5__Automatic_Affiliation_Creation_Turned_On__c = true));
            
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR));
        CAO_Constants.setIndividualAccountForTests(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS);
    
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(8);
        system.assertEquals(null, listCon[0].AccountId); 
        
        list<Account> listAcc = UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, CAO_Constants.HH_ACCOUNT_TYPE);
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE));
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, null));
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(1, CAO_Constants.BUCKET_ORGANIZATION_TYPE));
        insert listAcc;
        
        listCon[0].AccountId = listAcc[0].Id;   // HH account
        listCon[1].AccountId = listAcc[1].Id;   // HH account
        listCon[2].AccountId = listAcc[2].Id;   // 1:1 account
        listCon[3].AccountId = listAcc[3].Id;   // 1:1 account
        listCon[4].AccountId = listAcc[4].Id;   // Org1 account
        listCon[5].AccountId = listAcc[5].Id;   // Org2 account
        listCon[6].AccountId = listAcc[6].Id;   // Bucket account
        listCon[7].AccountId = listAcc[6].Id;   // Bucket account
        
        for (Contact con : listCon) {
            con.Primary_Affiliation__c = listAcc[4].Id; // Org1
        }  
        insert listCon;

        // 9 org affiliations should get created
        npe5__Affiliation__c[] listAffl = [select id, npe5__Status__c, npe5__StartDate__c, npe5__Role__c, 
            npe5__Organization__c, npe5__EndDate__c, npe5__Contact__c, npe5__Primary__c from npe5__Affiliation__c];       
        System.assertEquals(9, listAffl.size());

        Test.startTest();
        AFFL_Affiliations_TDTM.doNotRunForContacts = false;
        // now update our contacts to a different account
        for (Contact con : listCon) {
            con.AccountId = listAcc[5].Id; // Org2  
        }  
        update listCon;            
        Test.stopTest();
        
        listAffl = [select id, npe5__Status__c, npe5__StartDate__c, npe5__Role__c, 
            npe5__Organization__c, npe5__EndDate__c, npe5__Contact__c, npe5__Primary__c from npe5__Affiliation__c];
        
        // should end up with 8 primary affiliations to Org1 (one of which is now former)
        // 8 current affilations to org2
        System.assertEquals(16, listAffl.size());
        integer cOrg1 = 0;
        integer cOrg2 = 0;
        for (npe5__Affiliation__c affl : listAffl) {
            system.assertEquals(System.Today(), affl.npe5__StartDate__c);
            if (affl.npe5__Status__c == system.label.npe5.DefaultFormerStatus) { 
                system.assertEquals(true, affl.npe5__Primary__c);
                system.assertEquals(System.Today(), affl.npe5__EndDate__c);
                system.assertEquals(system.label.npe5.DefaultFormerStatus, affl.npe5__Status__c);
            } else {
                system.assertEquals(system.label.npe5.DefaultCurrentStatus, affl.npe5__Status__c);
            }
            if (affl.npe5__Organization__c == listAcc[4].id) cOrg1++;
            if (affl.npe5__Organization__c == listAcc[5].id) cOrg2++;            
        }
        system.assertEquals(8, cOrg1);
        system.assertEquals(8, cOrg2);
    }

    /*******************************************************************************************************
    * @description Creates contacts with primary affiliations. Verifies primary affiliations are created. 
    * Removes primary affiliation lookup from contacts. Verifies affiliations no longer marked primary.
    */
    static testMethod void updateContactsWithPrimaryAffiliationClearPrimary() {
        if (strTestOnly != '*' && strTestOnly != 'updateContactsWithPrimaryAffiliationClearPrimary') return;
        
        npe5__Affiliations_Settings__c affiliationsSettingsForTests = UTIL_CustomSettingsFacade.getAffiliationsSettingsForTests(
            new npe5__Affiliations_Settings__c(npe5__Automatic_Affiliation_Creation_Turned_On__c = true));
            
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR));
        CAO_Constants.setIndividualAccountForTests(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS);
    
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(8);
        system.assertEquals(null, listCon[0].AccountId); 
        
        list<Account> listAcc = UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, CAO_Constants.HH_ACCOUNT_TYPE);
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE));
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, null));
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(1, CAO_Constants.BUCKET_ORGANIZATION_TYPE));
        insert listAcc;
        
        listCon[0].AccountId = listAcc[0].Id;   // HH account
        listCon[1].AccountId = listAcc[1].Id;   // HH account
        listCon[2].AccountId = listAcc[2].Id;   // 1:1 account
        listCon[3].AccountId = listAcc[3].Id;   // 1:1 account
        listCon[4].AccountId = listAcc[4].Id;   // Org1 account
        listCon[5].AccountId = listAcc[5].Id;   // Org2 account
        listCon[6].AccountId = listAcc[6].Id;   // Bucket account
        listCon[7].AccountId = listAcc[6].Id;   // Bucket account
        
        for (Contact con : listCon) {
            con.Primary_Affiliation__c = listAcc[4].Id; // Org1
        }  
        insert listCon;

        // 9 org affiliations should get created
        npe5__Affiliation__c[] listAffl = [select id, npe5__Status__c, npe5__StartDate__c, npe5__Role__c, 
            npe5__Organization__c, npe5__EndDate__c, npe5__Contact__c, npe5__Primary__c from npe5__Affiliation__c];       
        System.assertEquals(9, listAffl.size());

        Test.startTest();
        AFFL_Affiliations_TDTM.doNotRunForContacts = false;
        // now clear our primary affilation
        for (Contact con : listCon) {
            con.Primary_Affiliation__c = null; 
        }  
        update listCon;
        Test.stopTest();
        
        listAffl = [select id, npe5__Status__c, npe5__StartDate__c, npe5__Role__c, 
            npe5__Organization__c, npe5__EndDate__c, npe5__Contact__c, npe5__Primary__c from npe5__Affiliation__c];
        
        // should end up with 9 affiliations, none of the primary
        System.assertEquals(9, listAffl.size());
        for (npe5__Affiliation__c affl : listAffl) {
            system.assertEquals(System.Today(), affl.npe5__StartDate__c);
            system.assertEquals(false, affl.npe5__Primary__c);
        }
   }

    /*******************************************************************************************************
    * @description Creates new primary affiliations for contacts with existing primary affiliations. 
    * Verifies that the contacts' Primary Affiliation field is updated. Unchecks primary for these
    * affiliations, then verifies that the Primary Affiliation field has been nulled out on the contacts.
    */
    static testMethod void newPrimaryAffiliations() {
        if (strTestOnly != '*' && strTestOnly != 'newPrimaryAffiliations') return;
        
        npe5__Affiliations_Settings__c affiliationsSettingsForTests = UTIL_CustomSettingsFacade.getAffiliationsSettingsForTests(
            new npe5__Affiliations_Settings__c(npe5__Automatic_Affiliation_Creation_Turned_On__c = true));
            
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR));
        CAO_Constants.setIndividualAccountForTests(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS);
    
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(8);
        system.assertEquals(null, listCon[0].AccountId); 
        
        list<Account> listAcc = UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, CAO_Constants.HH_ACCOUNT_TYPE);
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE));
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, null));
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(1, CAO_Constants.BUCKET_ORGANIZATION_TYPE));
        insert listAcc;
        
        listCon[0].AccountId = listAcc[0].Id;   // HH account
        listCon[1].AccountId = listAcc[1].Id;   // HH account
        listCon[2].AccountId = listAcc[2].Id;   // 1:1 account
        listCon[3].AccountId = listAcc[3].Id;   // 1:1 account
        listCon[4].AccountId = listAcc[4].Id;   // Org1 account
        listCon[5].AccountId = listAcc[5].Id;   // Org2 account
        listCon[6].AccountId = listAcc[6].Id;   // Bucket account
        listCon[7].AccountId = listAcc[6].Id;   // Bucket account        
        insert listCon;
        
        npe5__Affiliation__c[] listAffl = [select id, npe5__Status__c, npe5__StartDate__c, npe5__Role__c, 
            npe5__Organization__c, npe5__EndDate__c, npe5__Contact__c, npe5__Primary__c from npe5__Affiliation__c];
        System.assertEquals(2, listAffl.size());
        
        // now create our affiliation records directly
        listAffl = new list<npe5__Affiliation__c>();
        for (integer i = 0; i < 8; i++) {
        	npe5__Affiliation__c affl = new npe5__Affiliation__c();
        	affl.npe5__Contact__c = listCon[i].Id;
        	affl.npe5__Organization__c = listAcc[4].Id;
        	affl.npe5__StartDate__c = System.Today();
        	affl.npe5__Primary__c = true;
        	affl.npe5__Status__c = system.label.npe5.DefaultCurrentStatus;
        	listAffl.add(affl);
        }
        // must manually clear this, since our context is still active
        AFFL_Affiliations_TDTM.hasRunForAffiliations = false;
        insert listAffl;
        
        // make sure primary affiliation set on Contacts
        listCon = [Select Id, Primary_Affiliation__c, AccountId from Contact];
        system.assertEquals(8, listCon.size());        
        for (Contact con : listCon) {
        	system.assertEquals(listAcc[4].Id, con.Primary_Affiliation__c);
        }

        // test2, let's clear the primary flags.
        for (npe5__Affiliation__c affl : listAffl) {
        	affl.npe5__Primary__c = false;
        }
        // must manually clear this, since our context is still active
        AFFL_Affiliations_TDTM.hasRunForAffiliations = false;
        update listAffl;
        
        // make sure cleared on the contacts
        listCon = [Select Id, Primary_Affiliation__c, AccountId from Contact];
        system.assertEquals(8, listCon.size());        
        for (Contact con : listCon) {
            system.assertEquals(null, con.Primary_Affiliation__c);
        }
        
   }

    /*******************************************************************************************************
    * @description Creates new primary affiliations for contacts with existing primary affiliations. 
    * Verifies that the contacts' Primary Affiliation field is updated, and the formerly primary 
    * affiliations are no longer marked as primary.
    */
    static testMethod void newPrimaryAffiliationsFromAffl() {
        if (strTestOnly != '*' && strTestOnly != 'newPrimaryAffiliationsFromAffl') return;
        
        npe5__Affiliations_Settings__c affiliationsSettingsForTests = UTIL_CustomSettingsFacade.getAffiliationsSettingsForTests(
            new npe5__Affiliations_Settings__c(npe5__Automatic_Affiliation_Creation_Turned_On__c = true));
            
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR));
        CAO_Constants.setIndividualAccountForTests(CAO_Constants.INDIVIDUAL_ACCOUNT_NAME_FOR_TESTS);
    
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(8);
        system.assertEquals(null, listCon[0].AccountId); 
        
        list<Account> listAcc = UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, CAO_Constants.HH_ACCOUNT_TYPE);
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE));
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, null));
        listAcc.addAll(UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(1, CAO_Constants.BUCKET_ORGANIZATION_TYPE));
        insert listAcc;
        
        listCon[0].AccountId = listAcc[0].Id;   // HH account
        listCon[1].AccountId = listAcc[1].Id;   // HH account
        listCon[2].AccountId = listAcc[2].Id;   // 1:1 account
        listCon[3].AccountId = listAcc[3].Id;   // 1:1 account
        listCon[4].AccountId = listAcc[4].Id;   // Org1 account
        listCon[5].AccountId = listAcc[5].Id;   // Org2 account
        listCon[6].AccountId = listAcc[6].Id;   // Bucket account
        listCon[7].AccountId = listAcc[6].Id;   // Bucket account        
        insert listCon;
        
        //Which ones cause an affiliation to be created? Is it listAcc[4] and listAcc[5]? 
        npe5__Affiliation__c[] listAffl = [select id, npe5__Status__c, npe5__StartDate__c, npe5__Role__c, 
            npe5__Organization__c, npe5__EndDate__c, npe5__Contact__c, npe5__Primary__c from npe5__Affiliation__c];
        System.assertEquals(2, listAffl.size());
        
        // now create our affiliation records directly
        listAffl = new list<npe5__Affiliation__c>();
        for (integer i = 0; i < 8; i++) {
            npe5__Affiliation__c affl = new npe5__Affiliation__c();
            affl.npe5__Contact__c = listCon[i].Id;
            affl.npe5__Organization__c = listAcc[4].Id;
            affl.npe5__StartDate__c = System.Today();
            affl.npe5__Primary__c = true;
            affl.npe5__Status__c = system.label.npe5.DefaultCurrentStatus;
            listAffl.add(affl);
        }
        // must manually clear this, since our context is still active
        AFFL_Affiliations_TDTM.hasRunForAffiliations = false;
        insert listAffl;
        
        // make sure primary affiliation set on Contacts
        listCon = [Select Id, Primary_Affiliation__c, AccountId from Contact];
        system.assertEquals(8, listCon.size());        
        for (Contact con : listCon) {
            system.assertEquals(listAcc[4].Id, con.Primary_Affiliation__c);
        }

        // now set a new primary affiliations record for each
        listAffl = new list<npe5__Affiliation__c>();
        for (integer i = 0; i < 8; i++) {
            npe5__Affiliation__c affl = new npe5__Affiliation__c();
            affl.npe5__Contact__c = listCon[i].Id;
            affl.npe5__Organization__c = listAcc[5].Id;
            affl.npe5__StartDate__c = System.Today();
            affl.npe5__Primary__c = true;
            affl.npe5__Status__c = system.label.npe5.DefaultCurrentStatus;
            listAffl.add(affl);
        }
        // must manually clear this, since our context is still active
        AFFL_Affiliations_TDTM.hasRunForAffiliations = false;
        AFFL_Affiliations_TDTM.doNotRunForContacts = false;
        insert listAffl;
                
        // make sure primary affiliation set on Contacts
        listCon = [Select Id, Primary_Affiliation__c, AccountId from Contact];
        system.assertEquals(8, listCon.size());        
        for (Contact con : listCon) {
            system.assertEquals(listAcc[5].Id, con.Primary_Affiliation__c);
        }
        
        // make sure other affiliations are not primary
        listAffl = [select id, npe5__Status__c, npe5__StartDate__c, npe5__Role__c, 
            npe5__Organization__c, npe5__EndDate__c, npe5__Contact__c, npe5__Primary__c from npe5__Affiliation__c];
        integer cPrimary = 0;
        for (npe5__Affiliation__c affl : listAffl) {
        	if (affl.npe5__Primary__c) {
        	   system.assertEquals(listAcc[5].Id, affl.npe5__Organization__c);
        	   cPrimary++; 
        	}
        }
        system.assertEquals(8, cPrimary);
   }

    /*******************************************************************************************************
    * @description Adds a new account with primary affiliation to existing contact, then updates the 
    * account's primary contact. Verifies that an affiliation was created between new account and existing
    * contact, and a primary affiliation created with the account's new primary contact.
    */
    static testMethod void newAccountNewAffl() {
        if (strTestOnly != '*' && strTestOnly != 'newAccountNewAffl') return;
        
        UTIL_CustomSettingsFacade.getAffiliationsSettingsForTests(new npe5__Affiliations_Settings__c(npe5__Automatic_Affiliation_Creation_Turned_On__c = true));
        
        list<contact> listCon = new list<Contact>();
        listCon.add(new Contact(LastName = 'foo1', Title='Developer'));
        listCon.add(new Contact(LastName = 'foo2', Title='CEO'));
        insert listCon;

        test.StartTest();
        AFFL_Affiliations_TDTM.hasRunForAccounts = false;
        Account acc = new Account(Name='foo', npe01__One2OneContact__c=listCon[0].id, npe01__SYSTEMIsIndividual__c=false);
        insert acc;
        
        list<npe5__Affiliation__c> queryAffl = [select id, npe5__Status__c, npe5__StartDate__c, npe5__Role__c, 
            npe5__Organization__c, npe5__EndDate__c, npe5__Contact__c from npe5__Affiliation__c 
            where npe5__Organization__c = :acc.id];
        
        System.assertEquals(1, queryAffl.size(), 'An affiliation should be created.');
        system.assertEquals(system.label.npe5.DefaultCurrentStatus, queryAffl[0].npe5__Status__c, 'The affiliation\'s status should be set to the default current status.');
        system.assertEquals(listcon[0].title, queryAffl[0].npe5__Role__c, 'The affiliation\'s role should match the first contact\'s title.');
        system.assertEquals(System.Today(), queryAffl[0].npe5__StartDate__c, 'The affiliation\'s start date should be today.');
        
        AFFL_Affiliations_TDTM.hasRunForAccounts = false;
        acc.npe01__One2OneContact__c = listCon[1].id;
        update acc;
        test.StopTest();
        
        queryAffl = [select id, npe5__Status__c, npe5__StartDate__c, npe5__Role__c, 
            npe5__Organization__c, npe5__EndDate__c, npe5__Contact__c from npe5__Affiliation__c 
            where npe5__Organization__c = :acc.id ORDER BY npe5__Contact__r.LastName];        
        
        System.assertEquals(2, queryAffl.size(), 'Two affiliations should exist.');
        system.assertEquals(system.label.npe5.DefaultCurrentStatus, queryAffl[0].npe5__Status__c, 'The existing affiliation\'s status should be unchanged.');
        system.assertEquals(system.label.npe5.DefaultCurrentStatus, queryAffl[1].npe5__Status__c, 'The new affiliation\'s status should be set to the default current status.');
        system.assertEquals(listcon[1].title, queryAffl[1].npe5__Role__c, 'The new affiliation\'s role should match the second contact\'s title.');
        system.assertEquals(System.Today(), queryAffl[0].npe5__StartDate__c, 'The affiliation\'s start date should be today.');

    }
}