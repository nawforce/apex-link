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
* @description Test class for batch address verification.
*/
@isTest
public with sharing class ADDR_Validator_Batch_TEST {

    // if you only want to run one test in this class, fill in its name here.
    // if you want to run all tests, then use '*'
    private static string strTestOnly = '*';
    
    public static void createDefaultSettings() {
    	// use the household account model
        UTIL_CustomSettingsFacade.getContactsSettings();

        // create the basic api settings - SmartyStreets off
        Addr_Verification_Settings__c settings = UTIL_CustomSettingsFacade.getAddressVerificationSettings();
        settings.Auth_ID__c = 'hey';
        settings.Auth_Token__c = 'there';
        settings.Address_Verification_Endpoint__c = 'api.smartystreets.com/street-address';
        settings.Zipcode_Verification_Endpoint__c = 'api.smartystreets.com/zipcode';
        settings.Class__c = 'ADDR_SmartyStreets_Validator';
        settings.Enable_Automatic_Verification__c = false;
        settings.Reject_Ambiguous_Addresses__c = true;
   
        Test.setMock(HttpCalloutMock.class, new ADDR_MockHttpRespGenerator_TEST());
    }
    
    public static testmethod void oneValidAddress() {
    	if (strTestOnly != '*' && strTestOnly != 'oneValidAddress') return;

    	TDTM_DefaultConfig.startTestDataInit();
    	createDefaultSettings();
        
        //create contact 
        Contact contact = new contact(firstname='Test', lastname='Person');
        contact.MailingStreet = 'single-address';
        contact.MailingCity = 'Cupertino';
        contact.MailingState = 'California';
        contact.MailingPostalCode = '95014'; 
        contact.MailingCountry = 'United States';
        insert contact;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        
        //check household got created for contact
        contact = [select Account.Id from Contact where Id = :contact.Id];
        Id accountId = contact.Account.Id;
        System.assertNotEquals(null, accountId);
        
        //check address record got created for household
        Address__c[] av = [select Id, MailingStreet__c, MailingState__c, MailingPostalCode__c, Verified__c
                             from Address__c where Household_Account__c = :accountId];
        System.assertEquals(1, av.size());
        System.assertEquals('single-address', av[0].MailingStreet__c);
        System.assertEquals('California', av[0].MailingState__c);
        System.assertEquals('95014', av[0].MailingPostalCode__c);
        System.assertEquals(false, av[0].Verified__c);
        
        //run batch address verification
        TDTM_DefaultConfig.stopTestDataInit();
        Test.StartTest();
        ADDR_Validator_Batch b = new ADDR_Validator_Batch(true);
        Database.executeBatch(b);
        Test.stopTest();
        
        //check address record was verified
        av = [select MailingStreet__c, MailingState__c, MailingPostalCode__c, Verified__c 
                from Address__c where Household_Account__c = :accountId];
        System.assertEquals(1, av.size());
        System.assertEquals('1 Infinite Loop', av[0].MailingStreet__c);
        System.assertEquals('CA', av[0].MailingState__c);
        System.assertEquals('95014-2083', av[0].MailingPostalCode__c);
        System.assertEquals(true, av[0].Verified__c);
        
        /*
        //check verified data got pushed to contact 
        contact = [select id, MailingStreet, MailingCity, MailingPostalCode, MailingState
                         from contact where id = :contact.id];
        System.assertEquals('1 Infinite Loop', contact.MailingStreet);
        System.assertEquals('Cupertino', contact.MailingCity);
        System.assertEquals('95014-2083', contact.MailingPostalCode);
        System.assertEquals('CA', contact.MailingState);
        
        //check verified data got pushed to account
        Account account = [select id, BillingStreet, BillingCity, BillingPostalCode, BillingState
                         from Account where id = :accountId];
        System.assertEquals('1 Infinite Loop', account.BillingStreet);
        System.assertEquals('Cupertino', account.BillingCity);
        System.assertEquals('95014-2083', account.BillingPostalCode);
        System.assertEquals('CA', account.BillingState);*/
    }
    
    public static testmethod void multipleValidAddresses() {
    	if (strTestOnly != '*' && strTestOnly != 'multipleValidAddresses') return;

        TDTM_DefaultConfig.startTestDataInit();
    	createDefaultSettings();
        
        //create contacts
        List<Contact> contacts = new List<Contact>();
        for(integer i = 0; i< 200; i++) {
	        Contact contact = new contact(firstname='Test', lastname='Person' + i);
	        contact.MailingStreet = 'multiple-address';
	        contact.MailingCity = 'Cupertino';
	        contact.MailingState = 'California';
	        contact.MailingPostalCode = '95014';
	        contact.MailingCountry = 'United States';
	        contacts.add(contact);
        }
        insert contacts;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        
        //check households got created for contacts
        List<Id> AccountIDs = new List<Id>();
        List<Contact> contactsWithAccId = [select Account.Id from Contact where Id IN :contacts];
        System.assertEquals(200, contactsWithAccId.size());
        for(Contact c : contactsWithAccId) {    
	        Id accountId = c.Account.Id;
	        System.assertNotEquals(null, accountId);
	        AccountIDs.add(accountId);
        }
        
        //check address records got created for households, but have not yet been verified
        List<Address__c> addresses = [select Verified__c from Address__c where Household_Account__c IN :AccountIDs];
        System.assertEquals(200, addresses.size());
        for(Address__c addr : addresses) {
        	System.assertEquals(false, addr.Verified__c);
        }
        
        //run batch address verification
        TDTM_DefaultConfig.stopTestDataInit();
        Test.StartTest();
        ADDR_Validator_Batch b = new ADDR_Validator_Batch(true);
        Database.executeBatch(b);
        Test.stopTest();
        
        //check address records were verified
        addresses = [select MailingStreet__c, MailingState__c, MailingPostalCode__c, Verified__c 
                    from Address__c where Household_Account__c IN :AccountIDs];
        System.assertEquals(200, addresses.size());
        Integer i = 1;
        for(Address__c addr : addresses) {
        	UTIL_Debug.debug(LoggingLevel.WARN, '****Asserting address record #' + i);
	        System.assertEquals('1 Infinite Loop', addr.MailingStreet__c);
	        System.assertEquals('CA', addr.MailingState__c);
	        System.assertEquals('95014-2083', addr.MailingPostalCode__c);
	        System.assertEquals(true, addr.Verified__c);
	        i++;
        }
    }
    
    public static testmethod void oneValidZipcode() {
        if (strTestOnly != '*' && strTestOnly != 'oneValidZipcode') return;

        TDTM_DefaultConfig.startTestDataInit();
        createDefaultSettings();
        
        Contact contact = new contact(firstname='Test', lastname='Person'); 
        contact.MailingPostalCode = '90210';
        contact.MailingCity = 'single-zip';
        insert contact;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        
        //check household got created for contact
        contact = [select Account.Id from Contact where Id = :contact.Id];
        Id accountId = contact.Account.Id;
        System.assertNotEquals(null, accountId);
        
        //check address record got created for household
        Address__c[] av = [select Id, MailingStreet__c, MailingState__c, MailingPostalCode__c, Verified__c
                             from Address__c where Household_Account__c = :accountId];
        System.assertEquals(1, av.size());
        System.assertEquals('90210', av[0].MailingPostalCode__c);
        System.assertEquals(false, av[0].Verified__c);
        
        //run batch address verification
        TDTM_DefaultConfig.stopTestDataInit();
        Test.StartTest();
        ADDR_Validator_Batch b = new ADDR_Validator_Batch(true);
        Database.executeBatch(b);
        Test.stopTest();
        
        //check address record was verified
        av = [select MailingCity__c, MailingState__c, MailingPostalCode__c, Verified__c 
                from Address__c where Household_Account__c = :accountId];
        System.assertEquals(1, av.size());
        System.assertEquals('98236', av[0].MailingPostalCode__c);
        System.assertEquals('Clinton', av[0].MailingCity__c);
        System.assertEquals('WA', av[0].MailingState__c);
        System.assertEquals(true, av[0].Verified__c);
    }
    
    public static testmethod void multipleValidZipcodes() {
        if (strTestOnly != '*' && strTestOnly != 'multipleValidZipcodes') return;
        createDefaultSettings();
    }
    
    public static testmethod void multipleValidAddressessZipcodes() {
    	if (strTestOnly != '*' && strTestOnly != 'multipleValidAddressessZipcodes') return;  

        TDTM_DefaultConfig.startTestDataInit();
    	createDefaultSettings();
    	
    	//create contacts 
        Contact addressContact = new contact(firstname='Test', lastname='Person');
        addressContact.MailingStreet = 'single-address';
        addressContact.MailingCity = 'Cupertino';
        addressContact.MailingState = 'California';
        addressContact.MailingPostalCode = '95014'; 
        addressContact.MailingCountry = 'United States';
        
        Contact zipContact = new contact(firstname='Test', lastname='Person'); 
        zipContact.MailingCity = 'single-zip';
        zipContact.MailingPostalCode = '90210';
        
        insert new Contact[] {addressContact, zipContact};
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false; 
        
        //check household got created for contact
        addressContact = [select Account.Id from Contact where Id = :addressContact.Id];
        Id addrAccountId = addressContact.Account.Id;
        System.assertNotEquals(null, addrAccountId);
        
        //check household got created for contact
        zipContact = [select Account.Id from Contact where Id = :zipContact.Id];
        Id zipAccountId = zipContact.Account.Id;
        System.assertNotEquals(null, zipAccountId);
        
        //check address record got created for household
        Address__c[] av1 = [select Id, MailingStreet__c, MailingState__c, MailingPostalCode__c, Verified__c
                             from Address__c where Household_Account__c = :addrAccountId];
        System.assertEquals(1, av1.size());
        System.assertEquals('single-address', av1[0].MailingStreet__c);
        System.assertEquals('California', av1[0].MailingState__c);
        System.assertEquals('95014', av1[0].MailingPostalCode__c);
        System.assertEquals(false, av1[0].Verified__c);

        //check address record got created for household
        Address__c[] av2 = [select Id, MailingStreet__c, MailingState__c, MailingPostalCode__c, Verified__c
                             from Address__c where Household_Account__c = :zipAccountId];
        System.assertEquals(1, av2.size());
        System.assertEquals('90210', av2[0].MailingPostalCode__c);
        System.assertEquals(false, av2[0].Verified__c);
        
        //run batch address verification
        TDTM_DefaultConfig.stopTestDataInit();
        Test.StartTest();
        ADDR_Validator_Batch b = new ADDR_Validator_Batch(true);
        Database.executeBatch(b);
        Test.stopTest();
        
        //check address record was verified
        av1 = [select MailingStreet__c, MailingState__c, MailingPostalCode__c, Verified__c 
                from Address__c where Household_Account__c = :addrAccountId];
        System.assertEquals(1, av1.size());
        System.assertEquals('1 Infinite Loop', av1[0].MailingStreet__c);
        System.assertEquals('CA', av1[0].MailingState__c);
        System.assertEquals('95014-2083', av1[0].MailingPostalCode__c);
        System.assertEquals(true, av1[0].Verified__c);
        
        //check address record was verified
        av2 = [select MailingCity__c, MailingState__c, MailingPostalCode__c, Verified__c 
                from Address__c where Household_Account__c = :zipAccountId];
        System.assertEquals(1, av2.size());
        System.assertEquals('98236', av2[0].MailingPostalCode__c);
        System.assertEquals('Clinton', av2[0].MailingCity__c);
        System.assertEquals('WA', av2[0].MailingState__c);
        System.assertEquals(true, av2[0].Verified__c);
    }
    
    public static testmethod void validAddressInvalidZipcode() {
        if (strTestOnly != '*' && strTestOnly != 'validAddressInvalidZipcode') return;     
        createDefaultSettings();
    }     
}