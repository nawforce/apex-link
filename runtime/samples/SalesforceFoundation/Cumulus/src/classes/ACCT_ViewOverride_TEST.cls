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
* @date 2011 (1.x)
* @group Accounts
* @description Tests for ACCT_ViewOverride_CTRL
*/
@isTest
private class ACCT_ViewOverride_TEST {

    /*******************************************************************************************************
    * @description Invoke the Account View Override Controller on an Account
    */ 
    public static testMethod void accountViewOverride() {
        
        UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR));
        
        Test.setCurrentPageReference(new PageReference('Page.ACCT_ViewOverride'));
        
        String newContactMailingStreet = '123 Elm St';
        Contact con = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            MailingStreet = newContactMailingStreet,
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS
        );
        insert con;
        
        Contact[] insertedContacts = [Select FirstName, LastName, AccountId  from Contact where id=:con.id];
        
        Account newAccount = [select id from Account where id=:insertedContacts[0].AccountId];
        
        system.currentPageReference().getParameters().put('id', newAccount.id);
        
        //pass contact into the controller
        ApexPages.StandardController sc = new ApexPages.standardController(newAccount);
        //pass the controller into the extension
        ACCT_ViewOverride_CTRL ext = new ACCT_ViewOverride_CTRL(sc); 
        system.assertNotEquals(null, ext.getAccount());

    }
    
    
    /*******************************************************************************************************
    * @description Invoke the Account View Override Controller without specifying an Account
    */ 
    public static testMethod void noAccount() {
        
        Test.setCurrentPageReference(new PageReference('Page.ACCT_ViewOverride'));
        
        Account newAccount = new Account();
        
        system.currentPageReference().getParameters().put('id', '');
        
        //pass contact into the controller
        ApexPages.StandardController sc = new ApexPages.standardController(newAccount);
        //pass the controller into the extension
        ACCT_ViewOverride_CTRL ext = new ACCT_ViewOverride_CTRL(sc); 
        system.assertEquals(null, ext.getAccount());
    }
}