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
* @date 2011 (2.3)
* @group Address Management
* @description test class for the Copy Address button controller
*/
@isTest
private with sharing class ADDR_CopyAddrHHObjBTN_TEST {

    /*******************************************************************************************************
    * @description tests the Copy Address button controller
    * @return void
    */
    static testMethod void TEST_AddressUtility(){
       
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR));
        
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
            new npo02__Households_Settings__c (npo02__Household_Rules__c = HH_Households.NO_HOUSEHOLDS_PROCESSOR));
                
        npo02__Household__c hh = new npo02__Household__c(
           npo02__MailingStreet__c = '123 Main St.',
           npo02__MailingState__c = 'Pennsylvania',
           npo02__MailingPostalCode__c = '18940',
           npo02__MailingCity__c = 'Newtown',
           npo02__MailingCountry__c = 'United States'
        );        
        insert hh;
        
        Contact cc = new Contact(
           npo02__Household__c = hh.id,
           LastName = 'TestLName',
           MailingStreet = '1 Oak Lane',
           MailingState = 'British Columbia',
           MailingPostalCode = '98122',
           MailingCity = 'Vancouver',
           MailingCountry = 'Canada'
        );        
        insert cc;
        
        // instantiate the controller w/ the contact
        Test.setCurrentPageReference(new PageReference('Page.ADDR_CopyAddrHHObjBTN'));
        system.currentPageReference().getParameters().put('id', cc.id);
        ADDR_CopyAddrHHObjBTN_CTRL cacbc = new ADDR_CopyAddrHHObjBTN_CTRL();
        PageReference prc = cacbc.buttonClick();
        system.assertEquals(null, prc);
        npo02__Household__c h = [select id, npo02__MailingStreet__c, npo02__MailingState__c, npo02__MailingPostalCode__c, npo02__MailingCity__c, 
            npo02__MailingCountry__c from npo02__Household__c where id = :hh.id];
        system.assertEquals(h.npo02__MailingStreet__c, cc.MailingStreet);
        system.assertEquals(1, [select count() from Note where ParentID = :h.id]);
        
        //modify our household to a diff address
        hh.npo02__MailingStreet__c = '8675 ThreeOhNine Lane';
        update hh;
        
        //instantiate the controller with the new hh address
        Test.setCurrentPageReference(new PageReference('Page.ADDR_CopyAddrHHObjBTN'));
        system.currentPageReference().getParameters().put('id', hh.id);
        cacbc = new ADDR_CopyAddrHHObjBTN_CTRL();
        prc = cacbc.buttonClick();
        system.assertEquals(null, prc);
        Contact c1 = [select id, MailingStreet from Contact where npo02__Household__c = :hh.id]; 
        system.assertEquals(hh.npo02__MailingStreet__c, c1.MailingStreet);
        system.assertEquals(1, [select count() from Note where ParentID = :c1.id]);
                
        //test error conditions
        
        //unsupported object test
        Account a = new Account(Name = 'TESTACCOUNT');
        insert a;        
        Test.setCurrentPageReference(new PageReference('Page.ADDR_CopyAddrHHObjBTN'));
        system.currentPageReference().getParameters().put('id', a.id);
        cacbc = new ADDR_CopyAddrHHObjBTN_CTRL();
        prc = cacbc.buttonClick();
        system.assert(prc == null);
        
        //contact w/o HH
        Contact c = new Contact(Lastname = 'NullHH');
        insert c;
        Test.setCurrentPageReference(new PageReference('Page.ADDR_CopyAddrHHObjBTN'));
        system.currentPageReference().getParameters().put('id', null);
        cacbc = new ADDR_CopyAddrHHObjBTN_CTRL();
        prc = cacbc.buttonClick();
        system.assert(prc == null);
    }
}