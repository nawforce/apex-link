/*
    Copyright (c) 2011, Salesforce.org
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
* @date 2011 (1.x)
* @group Households
* @description This class contains unit tests for validating the behavior of the Households class 
*/
@isTest
public with sharing class HH_Households_TEST {
	
    /*********************************************************************************************************
    * @description Tests default constructor
    */
    static testMethod void Households_Test() {
        HH_Households testHouseholds = new HH_Households();
    }

    /*********************************************************************************************************
    * @description Tests insert individual with all contacts processor
    */
    static testMethod void newIndContactAllContactsProcessor() {
        
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(new npo02__Households_Settings__c (npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR));
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR));
                
            
        String street = '2345 Elm St.';
        String city = 'Seattle';
        String state = 'Washington';
        String zip = '88777';
        String country = 'United States';
        Contact con = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS,
            MailingStreet = street,
            MailingCity = city,
            MailingState = state,
            MailingPostalCode = zip,
            MailingCountry = country
        );
        Test.startTest();
        insert con;
        Test.stopTest();
        Contact[] createdContacts = [select npo02__Household__r.Name,npo02__Household__r.npo02__MailingStreet__c,
            npo02__Household__r.npo02__MailingCity__c,npo02__Household__r.npo02__MailingState__c,npo02__Household__r.npo02__MailingPostalCode__c,
            npo02__Household__r.npo02__MailingCountry__c,npo02__Household__c,npo02__household__r.Number_of_Household_Members__c
            from Contact where id=:con.id];
        
        Id firstHouseholdId = createdContacts[0].npo02__household__c;
        system.assertEquals(street,createdContacts[0].npo02__household__r.npo02__MailingStreet__c);
        system.assertEquals(city,createdContacts[0].npo02__household__r.npo02__MailingCity__c);
        system.assertEquals(state,createdContacts[0].npo02__household__r.npo02__MailingState__c);
        system.assertEquals(zip,createdContacts[0].npo02__household__r.npo02__MailingPostalCode__c);
        system.assertEquals(country,createdContacts[0].npo02__household__r.npo02__MailingCountry__c);
        system.assertEquals(CAO_Constants.CONTACT_LASTNAME_FOR_TESTS + ' Household',createdContacts[0].npo02__household__r.Name);
        system.assertEquals(1, createdContacts[0].npo02__household__r.Number_of_Household_Members__c);
        
        //blank out the HH
        con.npo02__household__c = null;
        update con;
        
        createdContacts = [select npo02__Household__r.Name,npo02__Household__r.npo02__MailingStreet__c,
            npo02__Household__r.npo02__MailingCity__c,npo02__Household__r.npo02__MailingState__c,npo02__Household__r.npo02__MailingPostalCode__c,
            npo02__Household__r.npo02__MailingCountry__c,npo02__Household__c,npo02__household__r.Number_of_Household_Members__c
            from Contact where id=:con.id];
        
        system.assertNotEquals(firstHouseholdId,createdContacts[0].npo02__household__c);
        system.assertEquals(street,createdContacts[0].npo02__household__r.npo02__MailingStreet__c);
        system.assertEquals(city,createdContacts[0].npo02__household__r.npo02__MailingCity__c);
        system.assertEquals(state,createdContacts[0].npo02__household__r.npo02__MailingState__c);
        system.assertEquals(zip,createdContacts[0].npo02__household__r.npo02__MailingPostalCode__c);
        system.assertEquals(country,createdContacts[0].npo02__household__r.npo02__MailingCountry__c);
        system.assertEquals(CAO_Constants.CONTACT_LASTNAME_FOR_TESTS + ' Household',createdContacts[0].npo02__household__r.Name);
        system.assertEquals(1, createdContacts[0].npo02__household__r.Number_of_Household_Members__c);
    }
    

    /*********************************************************************************************************
    * @description Test insert and update a contact and see household object naming change
    */
    static testMethod void testNewAndUpdateContactWithHHNaming() {

        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(new npo02__Households_Settings__c (npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR, npo02__Advanced_Household_Naming__c = true));

        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR));

        Household_Naming_Settings__c hhNamingSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdNamingSettingsForTests(
        	new Household_Naming_Settings__c(
                Household_Name_Format__c = '{!LastName} Household',
                Formal_Greeting_Format__c = '{!{!Title}} {!LastName}',
                Informal_Greeting_Format__c = '{!{!FirstName}}',
        		Name_Connector__c = label.npo02.HouseholdNameConnector,
       			Name_Overrun__c = label.npo02.HouseholdNameOverrun,
        		Contact_Overrun_Count__c = 9,
        		Implementing_Class__c = 'HH_NameSpec'
            )
        );

        Contact con = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            Title= 'Doctor',
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS,
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS
        );
        Test.startTest();
        insert con;
        Test.stopTest();

        Contact[] createdContacts = [select npo02__household__r.npo02__Formal_Greeting__c  from Contact where id=:con.id];
        system.assertEquals('Doctor '+CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,createdContacts[0].npo02__household__r.npo02__Formal_Greeting__c  );

        con.Title = 'Handy Man';
        update con;

        Contact[] updatedContacts = [select npo02__household__r.npo02__Formal_Greeting__c  from Contact where id=:con.id];
        system.assertEquals('Handy Man '+CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,updatedContacts[0].npo02__household__r.npo02__Formal_Greeting__c  );
    }    
    
    /*********************************************************************************************************
    * @description Tests insert private individual with all contacts processor
    */
    static testMethod void newPrivateContactAllContactsProcessor() {
        
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(new npo02__Households_Settings__c (npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR));
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR));
                
            
        String street = '2345 Elm St.';
        String city = 'Seattle';
        String state = 'Washington';
        String zip = '88777';
        String country = 'United States';
        Contact con = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            npe01__Private__c = true,
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS,
            MailingStreet = street,
            MailingCity = city,
            MailingState = state,
            MailingPostalCode = zip,
            MailingCountry = country
        );
        Test.startTest();
        insert con;
        Test.stopTest();
        Contact[] createdContacts = [select npo02__Household__r.Name,npo02__Household__r.npo02__MailingStreet__c,npo02__Household__r.npo02__MailingCity__c,npo02__Household__r.npo02__MailingState__c,npo02__Household__r.npo02__MailingPostalCode__c,npo02__Household__r.npo02__MailingCountry__c,npo02__Household__c from Contact where id=:con.id];
        
        Id firstHouseholdId = createdContacts[0].npo02__household__c;
        system.assertEquals(street,createdContacts[0].npo02__household__r.npo02__MailingStreet__c);
        system.assertEquals(city,createdContacts[0].npo02__household__r.npo02__MailingCity__c);
        system.assertEquals(state,createdContacts[0].npo02__household__r.npo02__MailingState__c);
        system.assertEquals(zip,createdContacts[0].npo02__household__r.npo02__MailingPostalCode__c);
        system.assertEquals(country,createdContacts[0].npo02__household__r.npo02__MailingCountry__c);
        system.assertEquals(CAO_Constants.CONTACT_LASTNAME_FOR_TESTS + ' Household',createdContacts[0].npo02__household__r.Name);
        
        //blank out the HH
        con.npo02__household__c = null;
        update con;
        
        createdContacts = [select npo02__Household__r.Name,npo02__Household__r.npo02__MailingStreet__c,npo02__Household__r.npo02__MailingCity__c,npo02__Household__r.npo02__MailingState__c,npo02__Household__r.npo02__MailingPostalCode__c,npo02__Household__r.npo02__MailingCountry__c,npo02__Household__c from Contact where id=:con.id];
        
        system.assertNotEquals(firstHouseholdId,createdContacts[0].npo02__household__c);
        system.assertEquals(street,createdContacts[0].npo02__household__r.npo02__MailingStreet__c);
        system.assertEquals(city,createdContacts[0].npo02__household__r.npo02__MailingCity__c);
        system.assertEquals(state,createdContacts[0].npo02__household__r.npo02__MailingState__c);
        system.assertEquals(zip,createdContacts[0].npo02__household__r.npo02__MailingPostalCode__c);
        system.assertEquals(country,createdContacts[0].npo02__household__r.npo02__MailingCountry__c);
        system.assertEquals(CAO_Constants.CONTACT_LASTNAME_FOR_TESTS + ' Household',createdContacts[0].npo02__household__r.Name);
    }

    /*********************************************************************************************************
    * @description Tests insert individual with individuals processor 
    */
    static testMethod void newIndContactIndividualsProcessor() {
        
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(new npo02__Households_Settings__c (npo02__Household_Rules__c = HH_Households.ALL_INDIVIDUALS_PROCESSOR));
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR));
                
        String street = '2345 Elm St.';
        String city = 'Seattle';
        String state = 'Washington';
        String zip = '88777';
        String country = 'United States';
        Contact con = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS,
            MailingStreet = street,
            MailingCity = city,
            MailingState = state,
            MailingPostalCode = zip,
            MailingCountry = country
        );
        Test.startTest();
        insert con;
        Test.stopTest();
        Contact[] createdContacts = [select npo02__Household__r.Name,npo02__Household__r.npo02__MailingStreet__c,npo02__Household__r.npo02__MailingCity__c,npo02__Household__r.npo02__MailingState__c,npo02__Household__r.npo02__MailingPostalCode__c,npo02__Household__r.npo02__MailingCountry__c,npo02__Household__c from Contact where id=:con.id];
        
        Id firstHouseholdId = createdContacts[0].npo02__household__c;
        system.assertEquals(street,createdContacts[0].npo02__household__r.npo02__MailingStreet__c);
        system.assertEquals(city,createdContacts[0].npo02__household__r.npo02__MailingCity__c);
        system.assertEquals(state,createdContacts[0].npo02__household__r.npo02__MailingState__c);
        system.assertEquals(zip,createdContacts[0].npo02__household__r.npo02__MailingPostalCode__c);
        system.assertEquals(country,createdContacts[0].npo02__household__r.npo02__MailingCountry__c);
        system.assertEquals(CAO_Constants.CONTACT_LASTNAME_FOR_TESTS + ' Household',createdContacts[0].npo02__household__r.Name);
        
        //blank out the HH
        con.npo02__household__c = null;
        update con;
        
        createdContacts = [select npo02__Household__r.Name,npo02__Household__r.npo02__MailingStreet__c,npo02__Household__r.npo02__MailingCity__c,npo02__Household__r.npo02__MailingState__c,npo02__Household__r.npo02__MailingPostalCode__c,npo02__Household__r.npo02__MailingCountry__c,npo02__Household__c from Contact where id=:con.id];
        
        system.assertNotEquals(firstHouseholdId,createdContacts[0].npo02__household__c);
        system.assertEquals(street,createdContacts[0].npo02__household__r.npo02__MailingStreet__c);
        system.assertEquals(city,createdContacts[0].npo02__household__r.npo02__MailingCity__c);
        system.assertEquals(state,createdContacts[0].npo02__household__r.npo02__MailingState__c);
        system.assertEquals(zip,createdContacts[0].npo02__household__r.npo02__MailingPostalCode__c);
        system.assertEquals(country,createdContacts[0].npo02__household__r.npo02__MailingCountry__c);
        system.assertEquals(CAO_Constants.CONTACT_LASTNAME_FOR_TESTS + ' Household',createdContacts[0].npo02__household__r.Name);
    }
    
    /*********************************************************************************************************
    * @description Tests insert individual with no household processor 
    */
    static testMethod void newNoHouseholdProcessor() {

        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR));
      
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(new npo02__Households_Settings__c (npo02__Household_Rules__c = HH_Households.NO_HOUSEHOLDS_PROCESSOR));
        String street = '2345 Elm St.';
        String city = 'Seattle';
        String state = 'Washington';
        String zip = '88777';
        String country = 'United States';

        Contact con = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS,
            MailingStreet = street,
            MailingCity = city,
            MailingState = state,
            MailingPostalCode = zip,
            MailingCountry = country
        );
        Test.startTest();
        insert con;
        Test.stopTest();
        Contact[] createdContacts = [select npo02__Household__r.Name,npo02__Household__r.npo02__MailingStreet__c,npo02__Household__r.npo02__MailingCity__c,npo02__Household__r.npo02__MailingState__c,npo02__Household__r.npo02__MailingPostalCode__c,npo02__Household__r.npo02__MailingCountry__c,npo02__Household__c from Contact where id=:con.id];
        
        
        system.assertEquals(null,createdContacts[0].npo02__household__c);
    }
    
    /*********************************************************************************************************
    * @description Tests insert org contact with all contacts processor 
    */
    static testMethod void newOrgContactAllContactsProcessor() {
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR));
        
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(new npo02__Households_Settings__c (npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR));
        
        Account acct = new Account(
            Name = 'Test Organization 888'
        );
        insert acct;
        
        String street = '2345 Elm St.';
        String city = 'Seattle';
        String state = 'Washington';
        String zip = '88777';
        String country = 'United States';
        Contact con = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS,
            MailingStreet = street,
            MailingCity = city,
            MailingState = state,
            MailingPostalCode = zip,
            MailingCountry = country,
            AccountId = acct.id
        );
        Test.startTest();
        insert con;
        Test.stopTest();
        Contact[] createdContacts = [select npo02__Household__r.Name,npo02__Household__r.npo02__MailingStreet__c,
            npo02__Household__r.npo02__MailingCity__c,npo02__Household__r.npo02__MailingState__c,npo02__Household__r.npo02__MailingPostalCode__c,
            npo02__Household__r.npo02__MailingCountry__c,npo02__Household__c,npo02__Household__r.Number_of_Household_Members__c 
            from Contact where id=:con.id];
        
        Id firstHouseholdId = createdContacts[0].npo02__household__c;
        system.assertEquals(street,createdContacts[0].npo02__household__r.npo02__MailingStreet__c);
        system.assertEquals(city,createdContacts[0].npo02__household__r.npo02__MailingCity__c);
        system.assertEquals(state,createdContacts[0].npo02__household__r.npo02__MailingState__c);
        system.assertEquals(zip,createdContacts[0].npo02__household__r.npo02__MailingPostalCode__c);
        system.assertEquals(country,createdContacts[0].npo02__household__r.npo02__MailingCountry__c);
        system.assertEquals(CAO_Constants.CONTACT_LASTNAME_FOR_TESTS + ' Household',createdContacts[0].npo02__household__r.Name);
        system.assertEquals(1, createdContacts[0].npo02__Household__r.Number_of_Household_Members__c);
        
        //blank out the HH
        con.npo02__household__c = null;
        update con;
        
        createdContacts = [select npo02__Household__r.Name,npo02__Household__r.npo02__MailingStreet__c,
            npo02__Household__r.npo02__MailingCity__c,npo02__Household__r.npo02__MailingState__c,npo02__Household__r.npo02__MailingPostalCode__c,
            npo02__Household__r.npo02__MailingCountry__c,npo02__Household__c,npo02__Household__r.Number_of_Household_Members__c 
            from Contact where id=:con.id];
        
        system.assertNotEquals(firstHouseholdId,createdContacts[0].npo02__household__c);
        system.assertEquals(street,createdContacts[0].npo02__household__r.npo02__MailingStreet__c);
        system.assertEquals(city,createdContacts[0].npo02__household__r.npo02__MailingCity__c);
        system.assertEquals(state,createdContacts[0].npo02__household__r.npo02__MailingState__c);
        system.assertEquals(zip,createdContacts[0].npo02__household__r.npo02__MailingPostalCode__c);
        system.assertEquals(country,createdContacts[0].npo02__household__r.npo02__MailingCountry__c);
        system.assertEquals(CAO_Constants.CONTACT_LASTNAME_FOR_TESTS + ' Household',createdContacts[0].npo02__household__r.Name);
        system.assertEquals(1, createdContacts[0].npo02__Household__r.Number_of_Household_Members__c);
    }

    /*********************************************************************************************************
    * @description Tests insert org contact with Individuals processor 
    */
    static testMethod void newOrgContactIndividualsProcessor() {
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR));
        
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(new npo02__Households_Settings__c (npo02__Household_Rules__c = HH_Households.ALL_INDIVIDUALS_PROCESSOR));
        
        Account acct = new Account(
            Name = 'Test Organization 888'
        );
        insert acct;
        
        String street = '2345 Elm St.';
        String city = 'Seattle';
        String state = 'Washington';
        String zip = '88777';
        String country = 'United States';
        Contact con = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS,
            MailingStreet = street,
            MailingCity = city,
            MailingState = state,
            MailingPostalCode = zip,
            MailingCountry = country,
            AccountId = acct.id
        );
        Test.startTest();
        insert con;
        Test.stopTest();
        Contact[] createdContacts = [select npo02__Household__c from Contact where id=:con.id];
        
        system.assertEquals(null,createdContacts[0].npo02__household__c);
        
        //blank out the HH
        con.MailingStreet = 'test';
        update con;
        
        createdContacts = [select npo02__Household__c from Contact where id=:con.id];
        
        system.assertEquals(null,createdContacts[0].npo02__household__c);
    }

    /*********************************************************************************************************
    * @description Tests delete a contact that has it's own household
    */
    static testMethod void contactDeleteWithOwnHousehold() {
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR));
        
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(new npo02__Households_Settings__c (npo02__Household_Rules__c = HH_Households.ALL_INDIVIDUALS_PROCESSOR));
        
        String lastname = 'SmithForTest99';
        Contact con = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS
        );
        insert con;
        Contact[] createdContacts = [select npo02__Household__c from Contact where id=:con.id];
        
        Id firstHouseholdId = createdContacts[0].npo02__household__c;
        
        Test.startTest();
        delete con;
        Test.stopTest();
        
        npo02__Household__c[] deletedHousehold = [select id from npo02__Household__c where id=:firstHouseholdId];
        system.assert(deletedHousehold.size()==0);
    }
    
    /*********************************************************************************************************
    * @description Tests delete a contact that has a shared household
    */
    static testMethod void contactDeleteWithSharedHousehold() {
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR));
        
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(new npo02__Households_Settings__c (npo02__Household_Rules__c = HH_Households.ALL_INDIVIDUALS_PROCESSOR));
        
        String lastname = 'SmithForTest99';
        Contact con = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS
        );
        insert con;
        Contact[] createdContacts = [select npo02__Household__c from Contact where id=:con.id];
        
        Id firstHouseholdId = createdContacts[0].npo02__household__c;
        
        Contact con2 = new Contact(
            FirstName='test again',
            LastName=lastname,
            npo02__Household__c = firstHouseholdId
        );
        insert con2;
        
        Test.startTest();
        delete con;
        Test.stopTest();
        
        npo02__Household__c[] remainingHousehold = [select id, Number_Of_Household_Members__c from npo02__Household__c where id=:firstHouseholdId];
        system.assert(remainingHousehold.size()==1);
        system.assertEquals(1, remainingHousehold[0].Number_Of_Household_Members__c);
    }

/************************************ Not Used ***************************************
    /// <name> getHouseholdTransactionTotal </name>
    /// <summary>  </summary>
    static testMethod void getHouseholdTransactionTotal_test()
    {
        npo02__Household__c h = new npo02__Household__c(Name='Test Household');
        insert h;
        Contact c = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS,
            npo02__Household__c=h.Id
        );
        insert c;
        Opportunity o = new Opportunity(Name='test oppty',CloseDate=System.Today(),StageName=UTIL_UnitTestData_TEST.getClosedWonStage(),Amount=100);
        insert o;
        OpportunityContactRole ocr = new OpportunityContactRole(OpportunityId=o.Id,ContactId=c.Id,Role='Test');
        insert ocr;
        system.assertEquals(system.label.npo02.DefaultHouseholdTransactionCurrency + '100.00',HH_Households.getHouseholdTransactionTotal(h.id));
    }
    
    /// <name> getHouseholdTransactionTotal </name>
    /// <summary>  </summary>
    static testMethod void getHouseholdLastTransaction_test()
    {
        Integer year = 2005;
        Integer month = 10;
        Integer day = 10;
        npo02__Household__c h = new npo02__Household__c(Name='Test Household');
        insert h;
        Contact c = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS,
            npo02__Household__c=h.Id
        );
        insert c;
        Opportunity o = new Opportunity(Name='test oppty',CloseDate=date.newInstance(year,month,day),StageName=UTIL_UnitTestData_TEST.getClosedWonStage(),Amount=100);
        insert o;
        OpportunityContactRole ocr = new OpportunityContactRole(OpportunityId=o.Id,ContactId=c.Id,Role='Test');
        insert ocr;
        system.assertEquals(String.valueOf(year)+'-'+String.valueOf(month)+'-'+String.valueOf(day),HH_Households.getHouseholdLastTransaction(h.id));
    }
*****************************************/
    
/**** deprecated in Cumulus
     /// visualforce and apex methods for totals
    static testMethod void getHouseholdTransactionAmount_test()
    {
        
        npo02__Household__c h = new npo02__Household__c(Name='Test Household');
        insert h;
        Contact c = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS,
            npo02__Household__c=h.Id
        );
        insert c;
        Opportunity o = new Opportunity(Name='test oppty',CloseDate=date.newInstance(2007,3,3),StageName=UTIL_UnitTestData_TEST.getClosedWonStage(),Amount=100);
        insert o;
        OpportunityContactRole ocr = new OpportunityContactRole(OpportunityId=o.Id,ContactId=c.Id,Role='Test');
        insert ocr;
        
        //pass contact into the controller
        ApexPages.StandardController sc = new ApexPages.standardController(h);
        //pass the controller into the extension
        HouseholdTransactionHistory_EXT ext = new HouseholdTransactionHistory_EXT(sc);  
        
        system.assertEquals(system.label.npo02.DefaultHouseholdTransactionCurrency + '100.00',ext.getHouseholdTransactionTotal());
    }
    
    /// visualforce and apex methods for totals
    static testMethod void getHouseholdLastTransactionDate_test()
    {
        Integer year = 2005;
        Integer month = 10;
        Integer day = 10;
        
        npo02__Household__c h = new npo02__Household__c(Name='Test Household');
        insert h;
        Contact c = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS,
            npo02__Household__c=h.Id
        );
        insert c;
        Opportunity o = new Opportunity(Name='test oppty',CloseDate=date.newInstance(year,month,day),StageName=UTIL_UnitTestData_TEST.getClosedWonStage(),Amount=100);
        insert o;
        OpportunityContactRole ocr = new OpportunityContactRole(OpportunityId=o.Id,ContactId=c.Id,Role='Test');
        insert ocr;
        
        //pass contact into the controller
        ApexPages.StandardController sc = new ApexPages.standardController(h);
        //pass the controller into the extension
        HouseholdTransactionHistory_EXT ext = new HouseholdTransactionHistory_EXT(sc);
        
        system.assertEquals(String.valueOf(day) + '/' + String.valueOf(month) + '/' + String.valueOf(year),ext.getLastTransactionDate());
    }
****/
    
    /*********************************************************************************************************
    * @description Tests moving a contact from one household to another, leaving the old household empty.
    */
    static testMethod void deleteEmptyHouseholds_test(){
        
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(new npo02__Households_Settings__c (npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR, npo02__Advanced_Household_Naming__c = true));
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR));
         
        Contact c1 = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS            
        );          

        Contact c2 = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName='c2test',
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS            
        );       
        insert new Contact[] {c1, c2};       
        
        Contact c2t = [select id, npo02__Household__c from Contact where id = :c2.id];
        system.assert(c2t.npo02__household__c != null);
        Contact c1t = [select id, npo02__Household__c from Contact where id = :c1.id];
        system.assert(c1t.npo02__household__c != null);
        
        //move the c2 contact into c1's household
        id c2thhid = c2t.npo02__household__c;
        c2t.npo02__household__c = c1t.npo02__household__c;
        
        Test.startTest();
        update c2t;
        Test.stopTest();
        
        //get c1s household
        npo02__Household__c hh1 = [select Name, Number_Of_Household_Members__c from npo02__Household__c where id = :c1t.npo02__household__c];
        //assert the naming worked as c2t was moved into the c1 households
        system.assert(hh1.Name.contains('c2test')); 
        system.assertEquals(2, hh1.Number_Of_Household_Members__c);
        
        //the second household should be deleted
        system.assertEquals(0, [select count() from npo02__Household__c where id = :c2thhid]);     
    }
    
    /*********************************************************************************************************
    * @description Tests merging a contact into a Household updates rollups on the Household
    */
    static testMethod void testMergeRollupSupport(){
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR, npe01__Enable_Opportunity_Contact_Role_Trigger__c = true));
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(new npo02__Households_Settings__c (npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR, npo02__Advanced_Household_Naming__c = true, npo02__Enable_Opp_Rollup_Triggers__c = false));
         
        Contact c1 = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS            
        );            
        insert c1;  
        
        id rtid = UTIL_RecordTypes.GetRecordTypeId (Opportunity.sObjectType,UTIL_RecordTypes.getRecordTypeNameForGiftsTests(Opportunity.sObjectType));
        Opportunity newOpp = New Opportunity (
                Name = 'Test Opp ',
                Amount = 100,
                CloseDate = system.today(),
                StageName = UTIL_UnitTestData_TEST.getClosedWonStage(),
                Primary_Contact__c = c1.Id
            );  
        if(rtid != null)
            newOpp.put('RecordTypeId',rtid);
        
        insert newOpp;

        system.assertEquals(1, [select count() from OpportunityContactRole where isPrimary = true and ContactId = :c1.id]);
                
        Test.startTest();        
        HH_Households_TDTM.contactMergeUpdate(new Set<Id>{c1.Id});  
        Test.stopTest();
        c1 = [select npo02__TotalOppAmount__c, npo02__Household__r.npo02__TotalOppAmount__c from Contact where Id = :c1.id];
        system.assertEquals(100, c1.npo02__TotalOppAmount__c);
        system.assertEquals(100, c1.npo02__Household__r.npo02__TotalOppAmount__c);
    }

    /*********************************************************************************************************
    * @description Tests creating many contacts get a Household object.
    */
    static testMethod void testBulk() {
        
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(new npo02__Households_Settings__c (npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR));
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR));
                
        String street = '2345 Elm St.';
        String city = 'Seattle';
        String state = 'Washington';
        String zip = '88777';
        String country = 'United States';
        String lastname = 'SmithForTest99';
        
        List<Contact> cons = new List<contact>();
        Integer conCount = 200;
        for(Integer i=0;i<conCount;i++){
            Contact con = new Contact(
                FirstName='test',
                LastName=lastname,
                MailingStreet = street,
                MailingCity = city,
                MailingState = state,
                MailingPostalCode = zip,
                MailingCountry = country,
                npo02__SystemHouseholdProcessor__c = 'All New or Edited Contacts'
            );
            cons.add(con);
        }
        
        insert cons;
        Contact[] createdContacts = [select AccountId,npo02__Household__c,
        npo02__Household__r.npo02__MailingStreet__c, npo02__Household__r.npo02__MailingCity__c,
        npo02__Household__r.npo02__MailingState__c, npo02__Household__r.npo02__MailingPostalCode__c,
        npo02__Household__r.npo02__MailingCountry__c, npo02__Household__r.Name from Contact where id IN :cons];
        
        List<Opportunity> opps = new List<Opportunity>();
        
        for(Contact myContact : createdContacts){
            Opportunity opp = new Opportunity(
                Name='opp1',
                closeDate=System.today(),
                StageName='closed won',
                AccountId= myContact.AccountId
            );
            opps.add(opp);
        }
        
        Id firstHouseholdId = createdContacts[0].npo02__household__c;
        system.assertEquals(street,createdContacts[0].npo02__household__r.npo02__MailingStreet__c);
        system.assertEquals(city,createdContacts[0].npo02__household__r.npo02__MailingCity__c);
        system.assertEquals(state,createdContacts[0].npo02__household__r.npo02__MailingState__c);
        system.assertEquals(zip,createdContacts[0].npo02__household__r.npo02__MailingPostalCode__c);
        system.assertEquals(country,createdContacts[0].npo02__household__r.npo02__MailingCountry__c);
        system.assertEquals(lastname + ' Household',createdContacts[0].npo02__household__r.Name);
        Test.startTest();
        //blank out the HH
        for(Contact thisContact : createdContacts){
            thisContact.npo02__household__c = null;
        }
        update createdContacts;
        Test.stopTest();
        createdContacts = [select npo02__Household__r.Name,npo02__Household__r.npo02__MailingStreet__c,npo02__Household__r.npo02__MailingCity__c,npo02__Household__r.npo02__MailingState__c,npo02__Household__r.npo02__MailingPostalCode__c,npo02__Household__r.npo02__MailingCountry__c,npo02__Household__c from Contact where id IN :cons];
        
        system.assertNotEquals(firstHouseholdId,createdContacts[0].npo02__household__c);
        system.assertEquals(street,createdContacts[0].npo02__household__r.npo02__MailingStreet__c);
        system.assertEquals(city,createdContacts[0].npo02__household__r.npo02__MailingCity__c);
        system.assertEquals(state,createdContacts[0].npo02__household__r.npo02__MailingState__c);
        system.assertEquals(zip,createdContacts[0].npo02__household__r.npo02__MailingPostalCode__c);
        system.assertEquals(country,createdContacts[0].npo02__household__r.npo02__MailingCountry__c);
        system.assertEquals(lastname + ' Household',createdContacts[0].npo02__household__r.Name);
    }

    /*********************************************************************************************************
    * @description Insert a contact with naming exclusion checkboxes checked. verify multiselect picklist 
    * filled. Update MSP. verify checkboxes changed.  
    */
    static testMethod void testHouseholdExclusion() {

        Contact con = new Contact(LastName = CAO_Constants.CONTACT_LASTNAME_FOR_TESTS, Exclude_from_Household_Name__c=true, Exclude_from_Household_Formal_Greeting__c=true);
        insert con;

        list<Contact> queryCon = [SELECT Id, npo02__Naming_Exclusions__c FROM Contact WHERE Id = :con.id];
        system.assert(queryCon[0].npo02__Naming_Exclusions__c.contains('Household__c.Name'), 'Naming Exclusions field should include Household Name.');
        system.assert(queryCon[0].npo02__Naming_Exclusions__c.contains('Household__c.Formal_Greeting__c'), 'Naming Exclusions field should include Household Formal Greeting.');
        system.assert(!queryCon[0].npo02__Naming_Exclusions__c.contains('Household__c.Informal_Greeting__c'), 'Naming Exclusions field should not include Household Informal Greeting.');

        queryCon[0].npo02__Naming_Exclusions__c = 'Household__c.Name';
        Test.startTest();
        update queryCon[0];
        Test.stopTest();

        queryCon = [SELECT Id, Exclude_from_Household_Name__c, Exclude_from_Household_Formal_Greeting__c, Exclude_from_Household_Informal_Greeting__c FROM Contact WHERE Id = :con.id];
        system.assert(queryCon[0].Exclude_from_Household_Name__c, 'Exclude from Household Name checkbox should be checked.');
        system.assert(!queryCon[0].Exclude_from_Household_Formal_Greeting__c, 'Exclude from Household Formal Greeting checkbox should not be checked.');
        system.assert(!queryCon[0].Exclude_from_Household_Informal_Greeting__c, 'Exclude from Household Informal Greeting checkbox not should be checked.');

    }

    /*********************************************************************************************************
    * @description tests that case sensitive name changes on contacts update the Household name and greetings
    */
    public static testMethod void testCaseChanges() {
        
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests
            (new npo02__Households_Settings__c (npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR, npo02__Advanced_Household_Naming__c = true));
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests
            (new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR));
  
        Contact con = new Contact(firstname='joe', lastname='smith');
        insert con;
  
        npo02__Household__c hh = [select Id, Name, npo02__Formal_greeting__c, npo02__Informal_greeting__c from npo02__Household__c];
    
        system.assertEquals('smith ' + system.label.npo02.DefaultHouseholdName, hh.Name);
        system.assertEquals('joe smith', hh.npo02__Formal_greeting__c);
        system.assertEquals('joe', hh.npo02__Informal_greeting__c);
  
        con.firstname = 'Joe';
        con.lastname = 'Smith';
        Test.startTest();
        update con;
        Test.stopTest();
  
        hh = [select Id, Name, npo02__Formal_greeting__c, npo02__Informal_greeting__c from npo02__Household__c];
    
        system.assertEquals('Smith ' + system.label.npo02.DefaultHouseholdName, hh.Name);
        system.assertEquals('Joe Smith', hh.npo02__Formal_greeting__c);
        system.assertEquals('Joe', hh.npo02__Informal_greeting__c);
    }
}