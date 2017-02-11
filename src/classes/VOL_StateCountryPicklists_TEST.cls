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

@isTest
public with sharing class VOL_StateCountryPicklists_TEST {

    /*******************************************************************************************************
    * @description test method to verify the handling of "State and Country Picklist" feature when enabled 
    * in Salesforce, when updating an existing contact.
    * @return void
    */ 
	static testmethod void testStateCountryPicklistUpdateExisting() {
		
        // create test bucket account
        Account acc = new Account(Name='Test Individual Bucket Account');
        insert acc;		
		
		// save a contact
		Contact con = new Contact();
		con.Firstname = 'test';
		con.Lastname = 'test';
		con.Email = 'test@test.com';
		con.AccountId = acc.Id;
		insert con;
		
		// create our in-memory contact who should match the one we saved
		Contact conEdit = new Contact();
		conEdit.Firstname = 'test';
		conEdit.Lastname = 'test';
		conEdit.Email = 'test@test.com';
		conEdit.MailingCity = 'Seattle';
		conEdit.MailingState = 'wa';
		
		// call our code to match and update the existing contact
		list<string> listStrFields = new list<string>{'Id','Firstname','Lastname','Email','MailingCity','MailingState','MailingCountry'};
		ID id = VOL_SharedCode.CreateOrUpdateContactFS(null, conEdit, null, listStrFields); 
		
		// verify the address fields copied correctly
		Contact conVerify = [select Id, Lastname, Email, MailingCity, MailingState, MailingCountry from Contact where Id = :id];
		system.assertEquals('Seattle', conVerify.MailingCity);
		if (VOL_StateCountryPicklists.isStateCountryPicklistsEnabled) {
			system.assertEquals('Washington', conVerify.MailingState);
			system.assertEquals('United States', conVerify.MailingCountry);
		} else {
			system.assertEquals('wa', conVerify.MailingState);
			system.assertEquals(null, conVerify.MailingCountry);			
		}
	}

    /*******************************************************************************************************
    * @description test method to verify the handling of "State and Country Picklist" feature when enabled 
    * in Salesforce, when creating a new contact.
    * @return void
    */ 
	static testmethod void testStateCountryPicklistInsertNew() {
		
        // create test bucket account
        Account acc = new Account(Name='Test Individual Bucket Account');
        insert acc;		
		
		// settings initialization
        Volunteers_Settings__c settings = new Volunteers_Settings__c();
        settings.Signup_Bucket_Account_On_Create__c = 'Test Individual Bucket Account';
        VOL_SharedCode.getVolunteersSettingsForTests(settings);
		
		// create our in-memory contact who should match no one.
		Contact conEdit = new Contact();
		conEdit.Firstname = 'testNew';
		conEdit.Lastname = 'testNew';
		conEdit.Email = 'test@test.com';
		conEdit.MailingCity = 'Seattle';
		conEdit.MailingState = 'wa';
		
		// call our code to match and update the existing contact
		list<string> listStrFields = new list<string>{'Id','Firstname','Lastname','Email','MailingCity','MailingState','MailingCountry'};
		ID id = VOL_SharedCode.CreateOrUpdateContactFS(null, conEdit, null, listStrFields); 
		
		// verify the address fields copied correctly
		Contact conVerify = [select Id, Lastname, Email, MailingCity, MailingState, MailingCountry from Contact where Id = :id];
		system.assertEquals('Seattle', conVerify.MailingCity);
		if (VOL_StateCountryPicklists.isStateCountryPicklistsEnabled) {
			system.assertEquals('Washington', conVerify.MailingState);
			system.assertEquals('United States', conVerify.MailingCountry);
		} else {
			system.assertEquals('wa', conVerify.MailingState);
			system.assertEquals(null, conVerify.MailingCountry);			
		}
	}

    /*******************************************************************************************************
    * @description test method to verify the handling of "State and Country Picklist" feature when enabled 
    * in Salesforce, when creating a new in memory contact for our Sites pages.  makes sure any state/country
    * code default values get copied to state & country fields
    * @return void
    */ 
    static testmethod void testStateCountryPicklistCodeCopy() {
        if (VOL_StateCountryPicklists.isStateCountryPicklistsEnabled) {
            
            Contact con = new Contact();
        
            // fake country code has default value
            con.put('MailingCountryCode', 'US');        
            con.put('OtherCountryCode', 'US');
            
            Test.startTest();        
            VOL_StateCountryPicklists.copyAddressStdSObj(con, 'Mailing', con, 'Mailing');
            VOL_StateCountryPicklists.copyAddressStdSObj(con, 'Other', con, 'Other');
            Test.stopTest();
            
            system.assertEquals('United States', con.MailingCountry);
            system.assertEquals('United States', con.OtherCountry);
        }
    }
}