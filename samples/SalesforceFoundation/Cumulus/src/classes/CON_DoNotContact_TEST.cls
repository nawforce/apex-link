/*
    Copyright (c) 2015, Salesforce.org
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
* @date 2015
* @group Contacts
* @group-content ../../ApexDocContent/Contacts.htm
* @description Handles changes to the deceased and do not contact fields on Contact.
*/
@isTest
private class CON_DoNotContact_TEST {

    /*********************************************************************************************************
    * @description if you only want to run one test in this class, fill in its name here.
    * if you want to run all tests, then use '*'
    */
    private static string strTestOnly = '*';

    /*******************************************************************************************************
    * @description Marks a sole contact in household as Deceased, verifies DoNotCall and HasOptedOutOfEmail
    * are set, but naming exclusions aren't set. Removes deceased flag, verifies DoNotCall and HasOptedOutOfEmail
    * have been removed, and naming exclusions are still not set.
    */
    static testMethod void testDeceasedAlone() {
        if (strTestOnly != '*' && strTestOnly != 'testDeceasedAlone') return;

        UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR));    
        
        Contact con = new Contact(LastName = CAO_Constants.CONTACT_LASTNAME_FOR_TESTS);
        insert con;

        list<Contact> queryCon = [SELECT Id, AccountId, HasOptedOutOfEmail, DoNotCall, npo02__Naming_Exclusions__c FROM Contact WHERE Id = :con.id];
        system.assertEquals(null, queryCon[0].npo02__Naming_Exclusions__c, 'Naming Exclusions field should be null.');
        system.assertNotEquals(null, queryCon[0].AccountId, 'Household should be created.');
        system.assert(!queryCon[0].HasOptedOutOfEmail, 'Contact should not be opted out of email.');

        //RIP
        queryCon[0].Deceased__c = true;
        Test.startTest();
        update queryCon[0];
        Test.stopTest();

        queryCon = [SELECT Id, Account.Name, HasOptedOutOfEmail, DoNotCall, npo02__Naming_Exclusions__c FROM Contact WHERE Id = :con.id];
        system.assertEquals(null, queryCon[0].npo02__Naming_Exclusions__c, 'Naming Exclusions field should be null.');
        system.assert(queryCon[0].HasOptedOutOfEmail, 'Contact should be opted out of email.');
        system.assert(queryCon[0].DoNotCall, 'Contact should be opted out of email.');
        system.assert(queryCon[0].Account.Name.contains(CAO_Constants.CONTACT_LASTNAME_FOR_TESTS), 'Household name should contain the contact last name.');

        queryCon[0].Deceased__c = false;
        update queryCon[0];

        queryCon = [SELECT Id, HasOptedOutOfEmail, DoNotCall, npo02__Naming_Exclusions__c FROM Contact WHERE Id = :con.id];
        system.assertEquals(null, queryCon[0].npo02__Naming_Exclusions__c, 'Naming Exclusions field should be null.');
        system.assert(!queryCon[0].HasOptedOutOfEmail, 'Contact should not be opted out of email.');
    }

    /*******************************************************************************************************
    * @description Marks the primary contact in a household as Deceased, verifies DoNotCall and 
    * HasOptedOutOfEmail are set, naming exclusions are set, and primary contact is changed to the remaining
    * household member.
    */
    static testMethod void testDeceasedInHousehold() {
        if (strTestOnly != '*' && strTestOnly != 'testDeceasedInHousehold') return;

        UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR));    
        
        Contact con = new Contact(LastName = CAO_Constants.CONTACT_LASTNAME_FOR_TESTS);
        insert con;

        list<Contact> queryCon = [SELECT Id, AccountId, Account.npe01__One2OneContact__c, HasOptedOutOfEmail, DoNotCall, npo02__Naming_Exclusions__c FROM Contact WHERE Id = :con.id];
        system.assertEquals(null, queryCon[0].npo02__Naming_Exclusions__c, 'Naming Exclusions field should be null.');
        system.assertNotEquals(null, queryCon[0].AccountId, 'Household should be created.');
        system.assertEquals(queryCon[0].Account.npe01__One2OneContact__c, queryCon[0].id, 'Contact should be marked primary.');
        system.assert(!queryCon[0].HasOptedOutOfEmail, 'Contact should not be opted out of email.');

        Contact con2 = new Contact(LastName = CAO_Constants.CONTACT_LASTNAME_FOR_TESTS, FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS, AccountId = queryCon[0].AccountId);
        insert con2;

        queryCon[0].Deceased__c = true;
        Test.startTest();
        update queryCon[0];
        Test.stopTest();

        queryCon = [SELECT Id, Account.Name, Account.npe01__One2OneContact__c, HasOptedOutOfEmail, DoNotCall, npo02__Naming_Exclusions__c, Exclude_from_Household_Name__c FROM Contact WHERE Id = :con.id];
        system.assertNotEquals(null, queryCon[0].npo02__Naming_Exclusions__c, 'Naming Exclusions field should be set.');
        system.assertNotEquals(queryCon[0].Account.npe01__One2OneContact__c, queryCon[0].id, 'Deceased Contact should no longer be marked primary.');
        system.assertEquals(queryCon[0].Account.npe01__One2OneContact__c, con2.id, 'Other household member should be elevated to primary.');
        system.assert(queryCon[0].Exclude_from_Household_Name__c, 'Exclude from HH Name field should be set.');
        system.assert(queryCon[0].HasOptedOutOfEmail, 'Contact should be opted out of email.');
        system.assert(queryCon[0].DoNotCall, 'Contact should be opted out of email.');
        system.assert(queryCon[0].Account.Name.contains(CAO_Constants.CONTACT_LASTNAME_FOR_TESTS), 'Household name should contain the contact last name.');
    }

    /*******************************************************************************************************
    * @description Marks a contact as Do Not Contact, verifies DoNotCall and HasOptedOutOfEmail are set.
    * Removes Do Not Contact flag, verifies DoNotCall and HasOptedOutOfEmail are no longer set.
    */
    static testMethod void testDoNotContact() {
        if (strTestOnly != '*' && strTestOnly != 'testDoNotContact') return;

        UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR));
        
        Contact con = new Contact(LastName = CAO_Constants.CONTACT_LASTNAME_FOR_TESTS);
        insert con;

        list<Contact> queryCon = [SELECT Id, HasOptedOutOfEmail, DoNotCall, npo02__Naming_Exclusions__c FROM Contact WHERE Id = :con.id];
        system.assert(!queryCon[0].DoNotCall, 'Contact should not be opted out of phone calls.');
        system.assert(!queryCon[0].HasOptedOutOfEmail, 'Contact should not be opted out of email.');

        queryCon[0].Do_Not_Contact__c = true;
        Test.startTest();
        update queryCon[0];
        Test.stopTest();
        
        queryCon = [SELECT Id, HasOptedOutOfEmail, DoNotCall, npo02__Naming_Exclusions__c FROM Contact WHERE Id = :con.id];
        system.assert(queryCon[0].DoNotCall, 'Contact should be opted out of phone calls.');
        system.assert(queryCon[0].HasOptedOutOfEmail, 'Contact should be opted out of email.');

        queryCon[0].Do_Not_Contact__c = false;
        update queryCon[0];

        queryCon = [SELECT Id, HasOptedOutOfEmail, DoNotCall, npo02__Naming_Exclusions__c FROM Contact WHERE Id = :con.id];
        system.assert(!queryCon[0].DoNotCall, 'Contact should not be opted out of phone calls.');
        system.assert(!queryCon[0].HasOptedOutOfEmail, 'Contact should not be opted out of email.');
    }	
}