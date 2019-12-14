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
* @date 2013
* @group Address Management
* @description Tests specific to Address Management. 
*/

@isTest
public with sharing class ADDR_Addresses_TEST {

    /*********************************************************************************************************
    * @description if you only want to run one test in this class, fill in its name here.
    * if you want to run all tests, then use '*'
    */
    private static string strTestOnly = '*';

    /*********************************************************************************************************
    * @description Holds the number of Households to create in tests.
    */
    static integer cHH = 2;

    /*********************************************************************************************************
    * @description Holds the number of Contacts per Household to create in tests.
    */
    static integer cCon = 2;    

    /*********************************************************************************************************
    * @description The list of created test Contacts.
    */
    public static list<Contact> listConT;

    /*********************************************************************************************************
    * @description The list of created test Accounts.
    */
    public static list<Account> listAccT;            

    /*********************************************************************************************************
    * @description The list of created test Addresses.
    */
    public static list<Address__c> listAddrT;

    /*********************************************************************************************************
    * @description
    * utility to set up common test data for the Address tests.
    *    - this will create & insert N household Accounts, each with M contacts
    *    - also will create a Default Addresses for each HH Account
    *    - will also setup the default Account Processor to be HH Accounts
    * @param cHHT the number of Household Accounts to create
    * @param cConT the number of Contacts to create per Household
    * @return  void
    **********************************************************************************************************/
    public static void createHHTestData(integer cHHT, integer cConT) {
        
        // we are creating a bunch of test data that relies on no async behavior.
        TDTM_DefaultConfig.startTestDataInit();
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettings();
            
        listConT = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(cHHT * cConT);
        listAccT = UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(cHHT, CAO_Constants.HH_ACCOUNT_TYPE);
        insert listAccT;
        
        for (integer i = 0; i < cHHT; i++) {
        	for (integer j = 0; j < cConT; j++) {
        		listConT[ (i * cConT) + j].AccountId = listAccT[i].Id;
        	}
        }        
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        insert listConT;    	

        initTestAddr(cHHT);
        for (integer i = 0; i < cHHT; i++) {
            listAddrT[i].Household_Account__c = listAccT[i].Id;
            listAddrT[i].Default_Address__c = true;
        }
        // clear our triggers recursion prevention since we starting a new test
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        insert listAddrT;
        // clear our triggers recursion prevention since we are starting a new test
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        // restore async behavior as appropriate
        TDTM_DefaultConfig.stopTestDataInit();
    }
    
    /*********************************************************************************************************
    * @description Initializes (but not inserts) N new Address objects.
    * @param cAddr the number of Address objects to create
    * @return  void
    **********************************************************************************************************/
    public static void initTestAddr(integer cAddr) {
    	listAddrT = new list<Address__c>();
    	for (integer i = 0; i < cAddr; i++) {
    		Address__c addr = new Address__c();
    		addr.Address_Type__c = 'Home';
    		addr.MailingStreet__c = 'Street' + i;
    		addr.MailingCity__c = 'City' + i;
            addr.MailingState__c = 'Washington';
            addr.MailingPostalCode__c = 'Zip' + i;
            addr.MailingCountry__c = 'United States';
            addr.Geolocation__Latitude__s = 47.6097 + i;
            addr.Geolocation__Longitude__s = -122.3331 + i;
            listAddrT.add(addr);    		
    	}
    }
    
    /*********************************************************************************************************
    * @description Returns whether an Account Billing address matches a Contact Mailing address
    * @return  boolean 
    **********************************************************************************************************/
    public static boolean isMatchAddressAccCon(Account acc, Contact con) {
        return (acc.BillingStreet == con.MailingStreet &&          
            acc.BillingCity == con.MailingCity &&          
            acc.BillingState == con.MailingState &&         
            acc.BillingPostalCode == con.MailingPostalCode &&          
            acc.BillingCountry == con.MailingCountry && 
            acc.BillingLatitude == con.MailingLatitude &&
            acc.BillingLongitude == con.MailingLongitude
        );                
    }
        
    /*********************************************************************************************************
    @description
        create N new default Addresses for N Households (with M Contacts)  
    verify:
        N addresses created
        N household billing address fields updated
        M contact Mailing address fields updated
    **********************************************************************************************************/            
    static testMethod void newDefaultAddr() {
        if (strTestOnly != '*' && strTestOnly != 'newDefaultAddr') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);
        Test.startTest();
        Test.stopTest();

        // verify that the HH and Contacts share the same address
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, isMatchAddressAccCon(acc, con));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
    }

    /*********************************************************************************************************
    @description
        insert contact w/ address
    verify:
        new default Address created
        contact address lookup updated to new address
        the contact gets the new Address
    **********************************************************************************************************/            
    static testMethod void insertContactWithAddress() {
        if (strTestOnly != '*' && strTestOnly != 'insertContactWithAddress') return;
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettings();
            
        listConT = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(cCon);
        for (Contact con : listConT) {
            con.MailingStreet = 'new street';
            con.MailingCity = 'new city';
            con.npe01__Primary_Address_Type__c = 'Other';
        }
        Test.startTest();
        insert listConT; 
        Test.stopTest();       

        // verify that the HH and Contacts share the same address
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, npe01__Primary_Address_Type__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, isMatchAddressAccCon(acc, con));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
            system.assertEquals('Other', con.npe01__Primary_Address_Type__c);
        }
        
        list<Address__c> listAddr = [select Id, Default_Address__c, MailingStreet__c, Household_Account__c, Address_Type__c 
            from Address__c where Default_Address__c = true];
        system.assertEquals(cCon, listAddr.size());
        for (Address__c addr : listAddr) {
            system.assertEquals('Other', addr.Address_Type__c);
        }
    }
        
    /*********************************************************************************************************
    @description
        insert contact w/ address, and force recreate of hh account
    verify:
        new default Address created
        contact address lookup updated to new address
        the contact gets the new Address
        the household account gets the new Address
    **********************************************************************************************************/            
    static testMethod void insertContactWithAddressRecreateAccount() {
        if (strTestOnly != '*' && strTestOnly != 'insertContactWithAddressRecreateAccount') return;
        
        TDTM_DefaultConfig.startTestDataInit();
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettings();
            
        listConT = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(cCon);
        for (Contact con : listConT) {
            con.MailingStreet = 'new street';
            con.MailingCity = 'new city';
        }
        insert listConT;        
        TDTM_DefaultConfig.stopTestDataInit();

        // null out account, so a new account will be created.
        Test.startTest();
        for (Contact con : listConT) 
            con.AccountId = null;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        update listConT; 
        Test.stopTest();       

        // verify that the HH and Contacts share the same address
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, isMatchAddressAccCon(acc, con));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
    }

    /*********************************************************************************************************
    @description
        insert contact w/ new address override
    verify:
        new non-default Address created
        contact address lookup updated to new address
        the contact gets the new Address
    **********************************************************************************************************/            
    static testMethod void insertContactWithAddressOverride() {
        if (strTestOnly != '*' && strTestOnly != 'insertContactWithAddressOverride') return;
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettings();
            
        listConT = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(cCon);
        integer i = 1;
        for (Contact con : listConT) {
            con.MailingStreet = 'new street' + i;
            con.MailingCity = 'new city' + i;
            con.is_Address_Override__c = true;
            i++;
        }
        Test.startTest();
        insert listConT; 
        Test.stopTest();       

        // verify that the HH and Contacts share the same address
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(false, isMatchAddressAccCon(acc, con));
            system.assertEquals(null, acc.BillingStreet);
            system.assertEquals(true, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
    }
        
    /*********************************************************************************************************
    @description
        update existing default Addresses  
    verify:
        household billing address fields updated
        contact Mailing address fields updated
    **********************************************************************************************************/            
    static testMethod void updateDefaultAddr() {
        if (strTestOnly != '*' && strTestOnly != 'updateDefaultAddr') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);
        
        // now let's update the Addresses
        for (integer i = 0; i < cHH; i++) {
        	Address__c addr = listAddrT[i];
        	addr.MailingStreet__c = 'New Street' + i;
        	addr.MailingCity__c = 'New City' + i;
        }
        system.assertEquals(false, ADDR_Addresses_TDTM.hasRunAddrTrigger);
        Test.startTest();
        update listAddrT;
        Test.stopTest();
    
        // verify that the HH and Contacts share the same address and it's new!
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        system.assertEquals(cHH * cCon, listCon.size());
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, isMatchAddressAccCon(acc, con));
            system.assert(con.MailingStreet.contains('New Street'));
            system.assert(con.MailingCity.contains('New City'));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
    }

    /*********************************************************************************************************
    @description
        delete existing default Addresses  
    verify:
        household billing address fields cleared
        contact Mailing address fields cleared
    **********************************************************************************************************/            
    static testMethod void deleteDefaultAddr() {
        if (strTestOnly != '*' && strTestOnly != 'deleteDefaultAddr') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);
        
        // now let's delete the Addresses
        system.assertEquals(false, ADDR_Addresses_TDTM.hasRunAddrTrigger);
        Test.startTest();
        delete listAddrT;
        Test.stopTest();
    
        // verify that the HH and Contacts' address fields are cleared
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        system.assertEquals(cHH * cCon, listCon.size());
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, isMatchAddressAccCon(acc, con));
            system.assertEquals(null, con.MailingStreet);
            system.assertEquals(null, con.MailingCity);
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertEquals(null, con.Current_Address__c);
            system.assertEquals(null, acc.BillingStreet);
            system.assertEquals(null, acc.BillingCity);
        }
    }

    /*********************************************************************************************************
    @description
        delete existing non-default override Addresses  
    verify:
        household billing address fields not changed
        contact Mailing address fields reset to default
    **********************************************************************************************************/            
    static testMethod void deleteNonDefaultOverrideAddr() {
        if (strTestOnly != '*' && strTestOnly != 'deleteNonDefaultOverrideAddr') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);
        
        // create additional addresses
        initTestAddr(cHH);
        for (integer i = 0; i < cHH; i++) {
            listAddrT[i].Household_Account__c = listAccT[i].Id;
            listAddrT[i].Default_Address__c = false;
            listAddrT[i].MailingStreet__c = 'override' + i;
            listAddrT[i].MailingCity__c = 'override' + i;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        insert listAddrT;
        
        // set the contacts' address overrides
        list<Contact> listCon = new list<Contact>();
        for (integer i = 0; i < cHH; i++) {
            Contact con = listConT[i*cHH + i];
            con.Current_Address__c = listAddrT[i].Id;
            con.is_Address_Override__c = true;
            listCon.add(con);
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        update listCon;        
        
        // now let's delete the override Addresses
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        delete listAddrT;
        Test.stopTest();
    
        // verify that the HH and Contacts' address fields the default
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        system.assertEquals(cHH * cCon, listCon.size());
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, isMatchAddressAccCon(acc, con));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
            system.assert(con.MailingStreet.contains('Street'));
            system.assert(con.MailingCity.contains('City'));            
        }
    }

    /*********************************************************************************************************
    @description
        update existing non-default Addresses  
    verify:
        no change to HH and contact addresses
    **********************************************************************************************************/            
    static testMethod void updateNonDefaultAddr() {
        if (strTestOnly != '*' && strTestOnly != 'updateNonDefaultAddr') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);
        
        // now let's update the Addresses
        for (integer i = 0; i < cHH; i++) {
            Address__c addr = listAddrT[i];
            addr.Default_Address__c = false;
            addr.MailingStreet__c = 'New Street' + i;
            addr.MailingCity__c = 'New City' + i;
        }
        system.assertEquals(false, ADDR_Addresses_TDTM.hasRunAddrTrigger);
        Test.startTest();
        update listAddrT;
        Test.stopTest();
    
        // verify that the HH and Contacts share the same address and it's new!
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        system.assertEquals(cHH * cCon, listCon.size());
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, isMatchAddressAccCon(acc, con));
            system.assert(!con.MailingStreet.contains('New Street'));
            system.assert(!con.MailingCity.contains('New City'));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
    }

    /*********************************************************************************************************
    @description
        set contact address override  
    verify:
        contact's address matches override
    **********************************************************************************************************/            
    static testMethod void setConAddrOverride() {
        if (strTestOnly != '*' && strTestOnly != 'setConAddrOverride') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);
        
        // create a new non-default address
        Address__c addrNew = new Address__c();
        addrNew.MailingStreet__c = 'street override';
        addrNew.MailingCity__c = 'city override';
        addrNew.Default_Address__c = false;
        addrNew.Household_Account__c = listAccT[0].Id;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        insert addrNew;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        
        // set a contact's address override
        listConT[0].Current_Address__c = addrNew.Id;
        listConT[0].is_Address_Override__c = true;
        update listConT[0];
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
    
        // now let's update the default Addresses
        for (integer i = 0; i < cHH; i++) {
            Address__c addr = listAddrT[i];
            addr.MailingStreet__c = 'New Street' + i;
            addr.MailingCity__c = 'New City' + i;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        update listAddrT;
        Test.stopTest();
        
        // verify that the HH and Contacts share the same address and it's new!
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        system.assertEquals(cHH * cCon, listCon.size());
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
        	if (con.is_Address_Override__c) {
        		system.assertEquals(con.Current_Address__c, addrNew.Id);
	            system.assertEquals(false, isMatchAddressAccCon(acc, con));
	            system.assert(!con.MailingStreet.contains('New Street'));
	            system.assert(!con.MailingCity.contains('New City'));        		
        	} else {
	            system.assertEquals(true, isMatchAddressAccCon(acc, con));
	            system.assert(con.MailingStreet.contains('New Street'));
	            system.assert(con.MailingCity.contains('New City'));
	            system.assertEquals(false, con.is_Address_Override__c);
	            system.assertNotEquals(null, con.Current_Address__c);
        	}
        }
    }

    /*********************************************************************************************************
    @description
        set contact current address, but not override  
    verify:
        contact's address updated, but not override
    **********************************************************************************************************/            
    static testMethod void setConAddrNoOverride() {
        if (strTestOnly != '*' && strTestOnly != 'setConAddrNoOverride') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);

        // create additional addresses
        initTestAddr(cHH);
        for (integer i = 0; i < cHH; i++) {
            listAddrT[i].Household_Account__c = listAccT[i].Id;
            listAddrT[i].Default_Address__c = false;
            listAddrT[i].MailingStreet__c = 'not override' + i;
            listAddrT[i].MailingCity__c = 'not override' + i;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        insert listAddrT;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        
        // set the contacts' address overrides
        list<Contact> listCon = new list<Contact>();
        for (integer i = 0; i < cHH; i++) {
            Contact con = listConT[i*cHH + i];
            con.Current_Address__c = listAddrT[i].Id;
            con.is_Address_Override__c = false;
            listCon.add(con);
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        update listCon;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.stopTest();
        
        // verify that the HH and Contacts share the same address, except for the contacts we modified
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, Current_Address__r.Default_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        
        integer cNotOverride = 0;
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            if (con.MailingStreet.contains('not override') == true) {
                cNotOverride++;
                system.assertEquals(false, isMatchAddressAccCon(acc, con));
                system.assert(con.MailingStreet.contains('not override'));
                system.assert(con.MailingCity.contains('not override'));
                system.assertEquals(false, con.is_Address_Override__c);
                system.assertNotEquals(null, con.Current_Address__c);            
            } else {
                system.assertEquals(true, con.Current_Address__r.Default_Address__c);
                system.assertEquals(true, isMatchAddressAccCon(acc, con));
                system.assert(con.MailingStreet.contains('Street'));
                system.assert(con.MailingCity.contains('City'));
                system.assertEquals(false, con.is_Address_Override__c);
                system.assertNotEquals(null, con.Current_Address__c);
            }
        }
        system.assertEquals(cHH, cNotOverride);
    }

    /*********************************************************************************************************
    @description
        insert new default addresses to HH w/ existing default addresses  
    verify:
        contact's && hh address matches new default
        old default addresses no longer marked default
    **********************************************************************************************************/            
    static testMethod void insertNewDefaultAddr() {
        if (strTestOnly != '*' && strTestOnly != 'insertNewDefaultAddr') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);
        
        // create additional addresses
        initTestAddr(cHH);
        for (integer i = 0; i < cHH; i++) {
            listAddrT[i].Household_Account__c = listAccT[i].Id;
            listAddrT[i].Default_Address__c = true;
            listAddrT[i].MailingStreet__c = 'New Default Street' + i;
            listAddrT[i].MailingCity__c = 'New Default City' + i;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        insert listAddrT;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;        
        Test.stopTest();

        // verify that the HH and Contacts share the same address and it's new!
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        system.assertEquals(cHH * cCon, listCon.size());
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, isMatchAddressAccCon(acc, con));
            system.assert(con.MailingStreet.contains('New Default Street'));
            system.assert(con.MailingCity.contains('New Default City'));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
        
        // verify the previous addresses got Default cleared.
        // and verify latest start date and latest end date appropriately set.
        list<Address__c> listAddr = [select Id, Default_Address__c, MailingStreet__c, Household_Account__c, Latest_Start_Date__c, Latest_End_Date__c from Address__c];
        system.assertEquals(cHH * 2, listAddr.size());
        for (Address__c addr : listAddr) {
        	boolean fNewDefault = (addr.MailingStreet__c.contains('New Default Street'));
       		system.assertEquals(fNewDefault, addr.Default_Address__c);
       		if (fNewDefault) {
       		   system.assertEquals(system.today(), addr.Latest_Start_Date__c);
       		   system.assertEquals(null, addr.Latest_End_Date__c);
       		} else {
               system.assertEquals(system.today(), addr.Latest_End_Date__c);       			
       		}
        }        
    }
    
    /*********************************************************************************************************
    @description
        insert new default addresses to HH w/ existing default addresses, and then go back  
    verify:
        contact's && hh address matches original default
        new default addresses no longer marked default
    **********************************************************************************************************/            
    static testMethod void insertNewDefaultAddrAndReset() {
        if (strTestOnly != '*' && strTestOnly != 'insertNewDefaultAddrAndReset') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);
        list<Address__c> listAddrOriginal = listAddrT;
        
        // create additional addresses
        initTestAddr(cHH);
        for (integer i = 0; i < cHH; i++) {
            listAddrT[i].Household_Account__c = listAccT[i].Id;
            listAddrT[i].Default_Address__c = true;
            listAddrT[i].MailingStreet__c = 'New Default Street' + i;
            listAddrT[i].MailingCity__c = 'New Default City' + i;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        insert listAddrT;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        
        // go back to original default addresses
        // added this extra set to test fix where we didn't use the correct default
        for (integer i = 0; i < cHH; i++) {
            listAddrOriginal[i].Default_Address__c = true;
            listAddrOriginal[i].MailingStreet__c = 'Original Default Street' + i;
            listAddrOriginal[i].MailingCity__c = 'Original Default City' + i;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        update listAddrOriginal;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.stopTest();

        // verify that the HH and Contacts share the same address and it's new!
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        system.assertEquals(cHH * cCon, listCon.size());
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, isMatchAddressAccCon(acc, con));
            system.assert(con.MailingStreet.contains('Original Default Street'));
            system.assert(con.MailingCity.contains('Original Default City'));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
        
        // verify the previous addresses got Default cleared.
        // and verify latest start date and latest end date appropriately set.
        list<Address__c> listAddr = [select Id, Default_Address__c, MailingStreet__c, Household_Account__c, Latest_Start_Date__c, Latest_End_Date__c from Address__c];
        system.assertEquals(cHH * 2, listAddr.size());
        for (Address__c addr : listAddr) {
            boolean fNewDefault = (addr.MailingStreet__c.contains('Original Default Street'));
            system.assertEquals(fNewDefault, addr.Default_Address__c);
            if (fNewDefault) {
               system.assertEquals(system.today(), addr.Latest_Start_Date__c);
               system.assertEquals(null, addr.Latest_End_Date__c);
            } else {
               system.assertEquals(system.today(), addr.Latest_End_Date__c);                
            }
        }        
    }

    /*********************************************************************************************************
    @description
        update contacts' Address Override  
    verify:
        contacts' Mailing address fields updated to the Address Override's fields.
    **********************************************************************************************************/            
    static testMethod void updateAddrOverride() {
        if (strTestOnly != '*' && strTestOnly != 'updateAddrOverride') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);

        // create additional addresses
        initTestAddr(cHH);
        for (integer i = 0; i < cHH; i++) {
            listAddrT[i].Household_Account__c = listAccT[i].Id;
            listAddrT[i].Default_Address__c = false;
            listAddrT[i].MailingStreet__c = 'override' + i;
            listAddrT[i].MailingCity__c = 'override' + i;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        insert listAddrT;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        
        // set the contacts' address overrides
        list<Contact> listCon = new list<Contact>();
        for (integer i = 0; i < cHH; i++) {
        	Contact con = listConT[i*cHH + i];
            con.Current_Address__c = listAddrT[i].Id;
            con.is_Address_Override__c = true;
            listCon.add(con);
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        update listCon;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.stopTest();
        
        // verify that the HH and Contacts share the same address
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            if (con.is_Address_Override__c) {
                system.assertNotEquals(null, con.Current_Address__c);
                system.assertEquals(false, isMatchAddressAccCon(acc, con));
                system.assert(con.MailingStreet.contains('override'));
                system.assert(con.MailingCity.contains('override'));               
            } else {
                system.assertEquals(true, isMatchAddressAccCon(acc, con));
                system.assert(con.MailingStreet.contains('Street'));
                system.assert(con.MailingCity.contains('City'));
	            system.assertEquals(false, con.is_Address_Override__c);
	            system.assertNotEquals(null, con.Current_Address__c);
            }
        }

        // verify address overrides have latest start date and latest end date appropriately set.
        list<Address__c> listAddr = [select Id, Latest_Start_Date__c, Latest_End_Date__c from Address__c where Id in :listAddrT];
        system.assertEquals(cHH, listAddr.size());
        for (Address__c addr : listAddr) {
           system.assertEquals(system.today(), addr.Latest_Start_Date__c);
           system.assertEquals(null, addr.Latest_End_Date__c);
        }        
    }
        
    /*********************************************************************************************************
    @description
        clear contacts' Address Override  
    verify:
        contacts' Mailing address fields updated to the HH default address
    **********************************************************************************************************/            
    static testMethod void clearAddrOverride() {
        if (strTestOnly != '*' && strTestOnly != 'clearAddrOverride') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);

        // create additional addresses
        initTestAddr(cHH);
        for (integer i = 0; i < cHH; i++) {
            listAddrT[i].Household_Account__c = listAccT[i].Id;
            listAddrT[i].Default_Address__c = false;
            listAddrT[i].MailingStreet__c = 'override' + i;
            listAddrT[i].MailingCity__c = 'override' + i;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        insert listAddrT;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        
        // set the contacts' address overrides
        list<Contact> listCon = new list<Contact>();
        for (integer i = 0; i < cHH; i++) {
            Contact con = listConT[i*cHH + i];
            con.Current_Address__c = listAddrT[i].Id;
            con.is_Address_Override__c = true;
            listCon.add(con);
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        update listCon;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;

        // now clear out the contacts' address overrides
        for (Contact con : listCon) {
            con.is_Address_Override__c = false;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        update listCon;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.stopTest();
        
        // verify that the HH and Contacts share the same address
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
            system.assertEquals(true, isMatchAddressAccCon(acc, con));
            system.assert(con.MailingStreet.contains('Street'));
            system.assert(con.MailingCity.contains('City'));
        }

        // verify address overrides have latest start date and latest end date appropriately set.
        list<Address__c> listAddr = [select Id, Latest_Start_Date__c, Latest_End_Date__c from Address__c where Id in :listAddrT];
        system.assertEquals(cHH, listAddr.size());
        for (Address__c addr : listAddr) {
           system.assertEquals(system.today(), addr.Latest_Start_Date__c);
           system.assertEquals(system.today(), addr.Latest_End_Date__c);
        }        
    }

   /*********************************************************************************************************
    @description
        clear contacts' Address Override and modify a new address 
    verify:
        new default address created
    **********************************************************************************************************/            
    static testMethod void clearAddrOverrideNewAddr() {
        if (strTestOnly != '*' && strTestOnly != 'clearAddrOverrideNewAddr') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);

        // create additional addresses
        initTestAddr(cHH);
        for (integer i = 0; i < cHH; i++) {
            listAddrT[i].Household_Account__c = listAccT[i].Id;
            listAddrT[i].Default_Address__c = false;
            listAddrT[i].MailingStreet__c = 'override' + i;
            listAddrT[i].MailingCity__c = 'override' + i;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        insert listAddrT;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        
        // set the contacts' address overrides
        list<Contact> listCon = new list<Contact>();
        for (integer i = 0; i < cHH; i++) {
            Contact con = listConT[i*cHH + i];
            con.Current_Address__c = listAddrT[i].Id;
            con.is_Address_Override__c = true;
            listCon.add(con);
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        update listCon;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;

        // now clear out the contacts' address overrides,
        // but modify the address also!
        for (Contact con : listCon) {
            con.is_Address_Override__c = false;
            con.MailingStreet = 'New Street';
            con.MailingCity = 'New City';
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        update listCon;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.stopTest();
        
        // verify that the HH and Contacts share the same address
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
            system.assertEquals(true, isMatchAddressAccCon(acc, con));
            system.assert(con.MailingStreet.contains('Street'));
            system.assert(con.MailingCity.contains('City'));
        }

    }

    /*********************************************************************************************************
    @description
        update contacts' mailing address to cause creation of new default Address object  
    verify:
        new Default Address created
        HH and Contacts get new Address
    **********************************************************************************************************/            
    static testMethod void updateConAddrNew() {
        if (strTestOnly != '*' && strTestOnly != 'updateConAddrNew') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);

        // modify some of the contact addresses directly
        list<Contact> listCon = new list<Contact>();
        for (integer i = 0; i < cHH; i++) {
            Contact con = listConT[i*cHH + i];
            con.MailingStreet = 'Direct Street Edit';
            con.MailingCity = 'Direct City Edit';
            listCon.add(con);
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        update listCon;

        for (integer i = 0; i < cHH; i++) {
            Contact con = listConT[i*cHH + i];
            con.MailingStreet = 'another Street Edit';
            con.MailingCity = 'another City Edit';
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        update listCon;

        for (integer i = 0; i < cHH; i++) {
            Contact con = listConT[i*cHH + i];
            con.MailingStreet = 'final Street Edit';
            con.MailingCity = 'final City Edit';
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        update listCon;
        Test.stopTest();
        
        // verify that the HH and Contacts share the same address
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, isMatchAddressAccCon(acc, con));
            system.assert(con.MailingStreet.contains('final Street Edit'));
            system.assert(con.MailingCity.contains('final City Edit'));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
 
         // verify the previous addresses got Default cleared.
        list<Address__c> listAddr = [select Id, Default_Address__c, MailingStreet__c, Household_Account__c from Address__c];
        // each set of edits created a new address object 
        system.assertEquals(cHH * 4, listAddr.size());
        for (Address__c addr : listAddr) {
            boolean fNewDefault = (addr.MailingStreet__c.contains('final Street Edit'));
            system.assertEquals(fNewDefault, addr.Default_Address__c);
        }                
    }

    /*********************************************************************************************************
    @description
        update contacts' mailing address and mark it as override, to cause creation of new non-default Address object  
    verify:
        new Address created
        HH sticks with default Address
        contact gets new address
    **********************************************************************************************************/            
    static testMethod void updateConAddrMarkOverride() {
        if (strTestOnly != '*' && strTestOnly != 'updateConAddrMarkOverride') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);

        // modify the contact addresses directly, and set override
        list<Contact> listCon = new list<Contact>();
        for (integer i = 0; i < cHH*cCon; i++) {
            Contact con = listConT[i];
            con.MailingStreet = 'Direct Street Edit';
            con.MailingCity = 'Direct City Edit';
            con.is_Address_Override__c = true;
            listCon.add(con);
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        update listCon;
        Test.stopTest();
        
        // verify that the HH and Contacts do not share the same address
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(false, isMatchAddressAccCon(acc, con));
            system.assertEquals(true, con.MailingStreet.contains('Direct Street Edit'));
            system.assertEquals(true, con.MailingCity.contains('Direct City Edit'));
            system.assertEquals(false, acc.BillingStreet.contains('Direct Street Edit'));
            system.assertEquals(false, acc.BillingCity.contains('Direct City Edit'));
            system.assertEquals(true, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
 
         // verify the previous addresses got Default cleared.
        list<Address__c> listAddr = [select Id, Default_Address__c, MailingStreet__c, Household_Account__c from Address__c];
        system.assertEquals(cHH * cCon + cHH, listAddr.size());
        for (Address__c addr : listAddr) {
            boolean isNewEdit = (addr.MailingStreet__c.contains('Direct Street Edit'));
            system.assertNotEquals(isNewEdit, addr.Default_Address__c);
        }                
    }

    /*********************************************************************************************************
    @description
        update contacts' mailing address to cause an update to default Address object  
    verify:
        existing Default Address updated
        HH and Contacts get updated Address
    **********************************************************************************************************/            
    static testMethod void updateConAddrExisting() {
        if (strTestOnly != '*' && strTestOnly != 'updateConAddrExisting') return;
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR,
                Simple_Address_Change_Treated_as_Update__c = true
        ));
                
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);

        // modify some of the contact addresses directly
        // NOTE: we only modify 1 field, so it will be treated as an update to an existing address!
        list<Contact> listCon = new list<Contact>();
        for (integer i = 0; i < cHH; i++) {
            Contact con = listConT[i*cHH + i];
            con.MailingStreet = 'Direct Street Edit';
            listCon.add(con);
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        update listCon;

        for (integer i = 0; i < cHH; i++) {
            Contact con = listConT[i*cHH + i];
            con.MailingState = 'Washington';
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        update listCon;

        for (integer i = 0; i < cHH; i++) {
            Contact con = listConT[i*cHH + i];
            con.MailingStreet = ' direct street  edit ';  // whitespace and casing should not count as a real edit.
            con.MailingState = 'Oregon';
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        update listCon;

        Test.stopTest();
        
        // verify that the HH and Contacts share the same address
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, isMatchAddressAccCon(acc, con));
            system.assert(con.MailingStreet.contains('direct street  edit'));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
 
        // verify that no new address was created
        list<Address__c> listAddr = [select Id, Default_Address__c, MailingStreet__c, Household_Account__c from Address__c];
        system.assertEquals(cHH, listAddr.size());
        for (Address__c addr : listAddr) {
            system.assertEquals('direct street  edit', addr.MailingStreet__c);
        }                
    }

    /*********************************************************************************************************
    @description
        update contacts' address type to cause an update to default Address object  
    verify:
        existing Default Address updated
        HH and Contacts get updated Address
    **********************************************************************************************************/            
    static testMethod void updateConAddrExistingAddrType() {
        if (strTestOnly != '*' && strTestOnly != 'updateConAddrExistingAddrType') return;
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR,
                Simple_Address_Change_Treated_as_Update__c = true
        ));
                
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);

        // modify some of the contact addresses directly
        // NOTE: we only modify 1 field, so it will be treated as an update to an existing address!
        list<Contact> listCon = new list<Contact>();
        for (integer i = 0; i < cHH; i++) {
            Contact con = listConT[i*cHH + i];
            con.npe01__Primary_Address_Type__c = 'Other';
            listCon.add(con);
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        update listCon;

        for (integer i = 0; i < cHH; i++) {
            Contact con = listConT[i*cHH + i];
            con.MailingStreet = ' direct street edit ';
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        update listCon;

        for (integer i = 0; i < cHH; i++) {
            Contact con = listConT[i*cHH + i];
            con.MailingState = 'Washington';
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        update listCon;

        for (integer i = 0; i < cHH; i++) {
            Contact con = listConT[i*cHH + i];
            con.MailingStreet = ' direct street  edit ';  // whitespace and casing should not count as a real edit.
            con.MailingState = 'Oregon';
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        update listCon;

        Test.stopTest();
        
        // verify that the HH and Contacts share the same address
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, npe01__Primary_Address_Type__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, isMatchAddressAccCon(acc, con));
            system.assert(con.MailingStreet.contains('direct street  edit'));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
            system.assertEquals('Other', con.npe01__Primary_Address_Type__c);
        }
 
        // verify that no new address was created
        list<Address__c> listAddr = [select Id, Default_Address__c, MailingStreet__c, Household_Account__c, Address_Type__c from Address__c];
        system.assertEquals(cHH, listAddr.size());
        for (Address__c addr : listAddr) {
            system.assertEquals('direct street  edit', addr.MailingStreet__c);
            system.assertEquals('Other', addr.Address_Type__c);
        }                
    }

    /*********************************************************************************************************
    @description
        update contacts' mailing address with just a case change, to cause an update to default Address object  
    verify:
        existing Default Address updated
        HH and Contacts get updated Address
    **********************************************************************************************************/            
    static testMethod void updateConAddrExistingCaseOnly() {
        if (strTestOnly != '*' && strTestOnly != 'updateConAddrExistingCaseOnly') return;
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR,
                Simple_Address_Change_Treated_as_Update__c = true
        ));
                
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);

        // modify some of the contact addresses directly
        // NOTE: we only modify 1 field, so it will be treated as an update to an existing address!
        list<Contact> listCon = new list<Contact>();
        for (integer i = 0; i < cHH; i++) {
            Contact con = listConT[i*cHH + i];
            con.MailingStreet = 'Direct Street Edit';
            listCon.add(con);
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        update listCon;

        for (integer i = 0; i < cHH; i++) {
            Contact con = listConT[i*cHH + i];
            con.MailingState = 'Washington';
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        update listCon;

        for (integer i = 0; i < cHH; i++) {
            Contact con = listConT[i*cHH + i];
            con.MailingStreet = ' direct street  edit ';  // whitespace and casing should not count as a real edit.
            con.MailingState = 'Oregon';
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        update listCon;

        for (integer i = 0; i < cHH; i++) {
            Contact con = listConT[i*cHH + i];
            con.MailingStreet = ' DIRECT STREET  EDIT ';  // only change casing.
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        update listCon;
        
        // verify that the HH and Contacts share the same address
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, isMatchAddressAccCon(acc, con));
            system.assert(con.MailingStreet.equals('DIRECT STREET  EDIT'));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
 
        // verify that no new address was created
        list<Address__c> listAddr = [select Id, Default_Address__c, MailingStreet__c, Household_Account__c from Address__c];
        system.assertEquals(cHH, listAddr.size());
        for (Address__c addr : listAddr) {
            system.assert(addr.MailingStreet__c.equals('DIRECT STREET  EDIT'));
        }
        
        // now test that changing the case from the account updates all addresses
        for (integer i = 0; i < cHH; i++) {
        	Account acc = listAccT[i];
        	acc.BillingStreet = 'direct street  edit';
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        update listAccT;
        Test.stopTest();
                
        // verify that the HH and Contacts share the same address
        mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, isMatchAddressAccCon(acc, con));
            system.assert(con.MailingStreet.equals('direct street  edit'));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
 
        // verify that no new address was created
        listAddr = [select Id, Default_Address__c, MailingStreet__c, Household_Account__c from Address__c];
        system.assertEquals(cHH, listAddr.size());
        for (Address__c addr : listAddr) {
            system.assert(addr.MailingStreet__c.equals('direct street  edit'));
        }
        
                        
    }

    /*********************************************************************************************************
    @description
        update accounts' billing address to cause creation of new default Address object  
    verify:
        new Default Address created
        HH and Contacts get new Address
    **********************************************************************************************************/            
    static testMethod void updateAccAddrNew() {
        if (strTestOnly != '*' && strTestOnly != 'updateAccAddrNew') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);

        // modify some of the account addresses directly
        for (integer i = 0; i < cHH; i++) {
            Account acc = listAccT[i];
            acc.BillingStreet = 'Direct Street Edit';
            acc.BillingCity = 'Direct City Edit';
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        update listAccT;

        for (integer i = 0; i < cHH; i++) {
            Account acc = listAccT[i];
            acc.BillingStreet = 'another Street Edit';
            acc.BillingCity = 'another City Edit';
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        update listAccT;

        for (integer i = 0; i < cHH; i++) {
            Account acc = listAccT[i];
            acc.BillingStreet = 'final Street Edit';
            acc.BillingCity = 'final City Edit';
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        update listAccT;
        Test.stopTest();
        
        // verify that the HH and Contacts share the same address
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, isMatchAddressAccCon(acc, con));
            system.assert(acc.BillingStreet.contains('final Street Edit'));
            system.assert(con.MailingStreet.contains('final Street Edit')); 
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
 
         // verify the previous addresses got Default cleared.
        list<Address__c> listAddr = [select Id, Default_Address__c, MailingStreet__c, Household_Account__c from Address__c];
        // each set of edits created a new address object 
        system.assertEquals(cHH * 4, listAddr.size());
        for (Address__c addr : listAddr) {
            boolean fNewDefault = (addr.MailingStreet__c.contains('final Street Edit'));
            system.assertEquals(fNewDefault, addr.Default_Address__c);
        }        
    }

    /*********************************************************************************************************
    @description
        update accounts' billing address to cause an update of the default Address object  
    verify:
        existing Default Address updated
        HH and Contacts get updated Address
    **********************************************************************************************************/            
    static testMethod void updateAccAddrExisting() {
        if (strTestOnly != '*' && strTestOnly != 'updateAccAddrExisting') return;
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR,
                Simple_Address_Change_Treated_as_Update__c = true
        ));
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);

        // modify some of the account addresses directly
        // NOTE: we only modify 1 field, so it will be treated as an update to an existing address!
        for (integer i = 0; i < cHH; i++) {
            Account acc = listAccT[i];
            acc.BillingStreet = 'Direct Street Edit';
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        update listAccT;

        for (integer i = 0; i < cHH; i++) {
            Account acc = listAccT[i];
            acc.BillingState = 'Washington';
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        update listAccT;

        for (integer i = 0; i < cHH; i++) {
            Account acc = listAccT[i];
            acc.BillingStreet = ' direct street  edit ';    // whitespace and casing should not count as real edit.
            acc.BillingState = 'Oregon';
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        update listAccT;
        Test.stopTest();
        
        // verify that the HH and Contacts share the same address
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, isMatchAddressAccCon(acc, con));
            system.assert(acc.BillingStreet.contains('direct street  edit'));
            system.assert(con.MailingStreet.contains('direct street  edit')); 
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
 
        // verify that no new address was created
        list<Address__c> listAddr = [select Id, Default_Address__c, MailingStreet__c, Household_Account__c from Address__c];
        system.assertEquals(cHH, listAddr.size());
        for (Address__c addr : listAddr) {
            system.assertEquals('direct street  edit', addr.MailingStreet__c);
        }        
    }

    /*********************************************************************************************************
    @description
        create an Address with no Account 
    verify:
        runtime error
    **********************************************************************************************************/            
    static testMethod void newAddrNoAccount() {
        if (strTestOnly != '*' && strTestOnly != 'newAddrNoAccount') return;
        
        initTestAddr(cHH);
        for (integer i = 0; i < cHH; i++) {
        	system.assertEquals(null, listAddrT[i].Household_Account__c);
        }
        
        try {
        	insert listAddrT;
        } catch (Exception ex) {
            system.assert(ex.getMessage().contains(Label.addrHHAccountOnly));
            return;
        }
        system.assert(false); //we shouldn't get here!
    }


    /*********************************************************************************************************
    @description
        create an Address with a NON-Household Account 
    verify:
        runtime error
    **********************************************************************************************************/            
    static testMethod void newAddrNotHHAccount() {
        if (strTestOnly != '*' && strTestOnly != 'newAddrNotHHAccount') return;
        
        listAccT = UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(cHH, CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE);
        insert listAccT;
 
        initTestAddr(cHH);
        for (integer i = 0; i < cHH; i++) {
            system.assertEquals(null, listAddrT[i].Household_Account__c);
            listAddrT[i].Household_Account__c = listAccT[i].Id;
        }
        
        try {
        	Test.startTest();
            insert listAddrT;
            Test.stopTest();
        } catch (Exception ex) {
            system.assert(ex.getMessage().contains(Label.addrHHAccountOnly));
        }
        Integer insertedAddrCount = [select count() from Address__c];
        System.assertEquals(0, insertedAddrCount);
        
        /* We cannot verify error record was created because the usage of addError causes the whole transaction
        to be rolled back. 
        List<Error__c> errors = [select Id, Full_Message__c, Stack_Trace__c, Email_Sent__c from Error__c];
        UTIL_Debug.debug('****Errors: ' + JSON.serializePretty(errors));
        System.assertEquals(1, errors.size());*/
    }

    /*********************************************************************************************************
    @description
        insert new seasonal addresses to HH w/ existing default addresses  
    verify:
        contact's && hh address matches new seasonal
        old default addresses still marked default
    **********************************************************************************************************/            
    static testMethod void insertNewSeasonalAddr() {
        if (strTestOnly != '*' && strTestOnly != 'insertNewSeasonalAddr') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);
        
        // create additional addresses
        initTestAddr(cHH);
        for (integer i = 0; i < cHH; i++) {
            listAddrT[i].Household_Account__c = listAccT[i].Id;
            listAddrT[i].Default_Address__c = false;
            listAddrT[i].Seasonal_Start_Month__c = string.valueOf(system.today().addMonths(-1).month());
            listAddrT[i].Seasonal_Start_Day__c = '1';
            listAddrT[i].Seasonal_End_Month__c = string.valueOf(system.today().addMonths(1).month());
            listAddrT[i].Seasonal_End_Day__c = '28';
            listAddrT[i].MailingStreet__c = 'New Seasonal Street' + i;
            listAddrT[i].MailingCity__c = 'New Seasonal City' + i;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        insert listAddrT;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.stopTest();
        
        // verify that the HH and Contacts share the same address and it's new!
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        system.assertEquals(cHH * cCon, listCon.size());
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, isMatchAddressAccCon(acc, con));
            system.assert(con.MailingStreet.contains('New Seasonal Street'));
            system.assert(con.MailingCity.contains('New Seasonal City'));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
        
        // verify the previous addresses still are Default
        list<Address__c> listAddr = [select Id, Default_Address__c, MailingStreet__c, Household_Account__c, Latest_Start_Date__c, Latest_End_Date__c from Address__c];
        system.assertEquals(cHH * 2, listAddr.size());
        for (Address__c addr : listAddr) {
            boolean fSeasonal = (addr.MailingStreet__c.contains('New Seasonal Street'));
            system.assertEquals(!fSeasonal, addr.Default_Address__c);
        }        
    }

    /*********************************************************************************************************
    @description
        insert new seasonal addresses, not current, to HH w/ existing default addresses  
    verify:
        contact's && hh address stay with default
        old default addresses still marked default
    **********************************************************************************************************/            
    static testMethod void insertNewSeasonalAddrNotCurrent() {
        if (strTestOnly != '*' && strTestOnly != 'insertNewSeasonalAddrNotCurrent') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);
        
        // create additional addresses
        initTestAddr(cHH);
        for (integer i = 0; i < cHH; i++) {
            listAddrT[i].Household_Account__c = listAccT[i].Id;
            listAddrT[i].Default_Address__c = false;
            listAddrT[i].Seasonal_Start_Month__c = string.valueOf(system.today().addMonths(1).month());
            listAddrT[i].Seasonal_Start_Day__c = '1';
            listAddrT[i].Seasonal_End_Month__c = string.valueOf(system.today().addMonths(2).month());
            listAddrT[i].Seasonal_End_Day__c = '28';
            listAddrT[i].MailingStreet__c = 'New Seasonal Street' + i;
            listAddrT[i].MailingCity__c = 'New Seasonal City' + i;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        insert listAddrT;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.stopTest();
        
        // verify that the HH and Contacts share the same address and it's new!
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        system.assertEquals(cHH * cCon, listCon.size());
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, isMatchAddressAccCon(acc, con));
            system.assert(!con.MailingStreet.contains('New Seasonal Street'));
            system.assert(!con.MailingCity.contains('New Seasonal City'));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
        
        // verify the previous addresses still are Default
        list<Address__c> listAddr = [select Id, Default_Address__c, MailingStreet__c, Household_Account__c, Latest_Start_Date__c, Latest_End_Date__c from Address__c];
        system.assertEquals(cHH * 2, listAddr.size());
        for (Address__c addr : listAddr) {
            boolean fSeasonal = (addr.MailingStreet__c.contains('New Seasonal Street'));
            system.assertEquals(!fSeasonal, addr.Default_Address__c);
        }        
    }   
}