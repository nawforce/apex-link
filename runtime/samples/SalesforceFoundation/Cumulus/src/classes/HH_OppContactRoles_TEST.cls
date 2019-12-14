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
* @description This class contains unit tests for validating the behavior of the Household Opportunity Contact Role class
*/
@isTest
private class HH_OppContactRoles_TEST {
    
    /*********************************************************************************************************
    * @description if you only want to run one test in this class, fill in its name here.
    * if you want to run all tests, then use '*'
    */
    private static string strTestOnly = '*';

    private static testMethod void opportunityWithHouseholdOne2One() {
    	if (strTestOnly != '*' && strTestOnly != 'opportunityWithHouseholdOne2One') return;
        opportunityWithHousehold(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    private static testMethod void opportunityWithHouseholdBucket() {
        if (strTestOnly != '*' && strTestOnly != 'opportunityWithHouseholdBucket') return;
        opportunityWithHousehold(CAO_Constants.BUCKET_PROCESSOR);
    }
    private static testMethod void opportunityWithHouseholdHHAccount() {
        if (strTestOnly != '*' && strTestOnly != 'opportunityWithHouseholdHHAccount') return;
        opportunityWithHousehold(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }
    /*********************************************************************************************************
    * @description Tests that household Opportunity Contact Roles are created under the different account models.
    * Verifies deceased contacts are not included.
    * @param strProcessor Which account model processor to test under.
    * @return void
    */
    private static void opportunityWithHousehold(string strProcessor) {
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Account_Processor__c = strProcessor,
                npe01__Opportunity_Contact_Role_Default_role__c = 'Donor'));
        
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
            new npo02__Households_Settings__c (
                npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR,
                npo02__Household_Contact_Roles_On__c = true,
                npo02__Household_Member_Contact_Role__c='Household Member'));
                
        //create contact
        Contact con = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS
        );
        insert con;
        Contact[] createdContacts = [select AccountId,npo02__Household__c from Contact where id=:con.id];
        Id accountId = createdContacts[0].AccountId;
        Id householdId = createdContacts[0].npo02__household__c;
        //create household member
        Contact con2 = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS,
            AccountId = (strProcessor == CAO_Constants.HH_ACCOUNT_PROCESSOR ? accountId : null),
            npo02__Household__c = householdId
        );
        insert con2;
        Contact con3 = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS,
            Deceased__c = true,
            AccountId = (strProcessor == CAO_Constants.HH_ACCOUNT_PROCESSOR ? accountId : null),
            npo02__Household__c = householdId
        );
        insert con3;
        //create opportunity
        Opportunity o = new Opportunity(
            Name='test oppty',
            CloseDate=date.newInstance(2001,12,12),
            StageName=UTIL_UnitTestData_TEST.getClosedWonStage(),
            Amount=100,
            AccountId=accountId
        );
        Test.startTest();
        insert o;
        Test.stopTest();
        //check for contact roles
        List<OpportunityContactRole> createdRoles = [select Role, ContactId, IsPrimary from OpportunityContactRole where OpportunityId = :o.id order by ContactId];

        if (strProcessor == CAO_Constants.BUCKET_PROCESSOR)
            system.AssertEquals(0,createdRoles.size());
        else {
	        system.AssertEquals(2,createdRoles.size());
	        system.AssertEquals('Donor',createdRoles[0].Role);
	        system.Assert(createdRoles[0].IsPrimary);
	        system.AssertEquals('Household Member',createdRoles[1].Role);
        }
    }


    private static testMethod void opportunityWithHouseholdNoRolesOne2One() {
        if (strTestOnly != '*' && strTestOnly != 'opportunityWithHouseholdNoRolesOne2One') return;
        opportunityWithHouseholdNoRoles(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    private static testMethod void opportunityWithHouseholdNoRolesBucket() {
        if (strTestOnly != '*' && strTestOnly != 'opportunityWithHouseholdNoRolesBucket') return;
        opportunityWithHouseholdNoRoles(CAO_Constants.BUCKET_PROCESSOR);
    }
    private static testMethod void opportunityWithHouseholdNoRolesHHAccount() {
        if (strTestOnly != '*' && strTestOnly != 'opportunityWithHouseholdNoRolesHHAccount') return;
        opportunityWithHouseholdNoRoles(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }
    /*********************************************************************************************************
    * @description Tests that household Opportunity Contact Roles are NOT created under the different account models
    * when the setting for contact roles is off.
    * @param strProcessor Which account model processor to test under.
    * @return void
    */
    private static void opportunityWithHouseholdNoRoles(string strProcessor) {
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = strProcessor));
        
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
            new npo02__Households_Settings__c (
                npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR, 
                npo02__Household_Contact_Roles_On__c = false));

        //create contact
        Contact con = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS
        );
        insert con;
        Contact[] createdContacts = [select AccountId,npo02__Household__c from Contact where id=:con.id];
        Id accountId = createdContacts[0].AccountId;
        Id householdId = createdContacts[0].npo02__household__c;
        //create household member
        Contact con2 = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS,
            AccountId = (strProcessor == CAO_Constants.HH_ACCOUNT_PROCESSOR ? accountId : null),
            npo02__Household__c = householdId
        );
        insert con2;
        //create opportunity
        Opportunity o = new Opportunity(
            Name='test oppty',
            CloseDate=date.newInstance(2001,12,12),
            StageName=UTIL_UnitTestData_TEST.getClosedWonStage(),
            Amount=100,
            AccountId=accountId
        );
        Test.startTest();
        insert o;
        Test.stopTest();
        //check for contact roles
        List<OpportunityContactRole> createdRoles = [select Role, ContactId, IsPrimary from OpportunityContactRole where OpportunityId = :o.id order by ContactId];
        if (strProcessor == CAO_Constants.BUCKET_PROCESSOR)
            system.AssertEquals(0,createdRoles.size());
        else
            system.AssertEquals(1,createdRoles.size());
    }
    
    
    private static testMethod void opportunityWithNoHouseholdOne2One() {
        if (strTestOnly != '*' && strTestOnly != 'opportunityWithNoHouseholdOne2One') return;
        opportunityWithNoHousehold(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    private static testMethod void opportunityWithNoHouseholdHHAccount() {
        if (strTestOnly != '*' && strTestOnly != 'opportunityWithNoHouseholdHHAccount') return;
        opportunityWithNoHousehold(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }
    /*********************************************************************************************************
    * @description Tests that household Opportunity Contact Roles are created under the different account models
    * when household objects is turned off.
    * @param strProcessor Which account model processor to test under.
    * @return void
    */
    private static void opportunityWithNoHousehold(string strProcessor) {
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = strProcessor));
                
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
            new npo02__Households_Settings__c (npo02__Household_Rules__c = HH_Households.NO_HOUSEHOLDS_PROCESSOR, 
                npo02__Household_Contact_Roles_On__c = true));
        
        //create contact
        Contact con = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS
        );
        insert con;
        
        Contact[] createdContacts = [select AccountId,npo02__Household__c from Contact where id=:con.id];
        Id accountId = createdContacts[0].AccountId;
        Id householdId = createdContacts[0].npo02__household__c;
        //create household member
        Contact con2 = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS,
            AccountId = null,
            npo02__Household__c = householdId
        );
        insert con2;
        //create opportunity
        Opportunity o = new Opportunity(
            Name='test oppty',
            CloseDate=date.newInstance(2001,12,12),
            StageName=UTIL_UnitTestData_TEST.getClosedWonStage(),
            Amount=100,
            AccountId=accountId
        );
        Test.startTest();
        insert o;
        Test.stopTest();
        //check for contact roles
        List<OpportunityContactRole> createdRoles = [select Role, ContactId, IsPrimary from OpportunityContactRole where OpportunityId = :o.id order by ContactId];
        system.AssertEquals(1,createdRoles.size());
    }
    
    
    private static testMethod void opportunityRegularAccountOne2One() {
        if (strTestOnly != '*' && strTestOnly != 'opportunityRegularAccountOne2One') return;
        opportunityRegularAccount(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    private static testMethod void opportunityRegularAccountHHAccount() {
        if (strTestOnly != '*' && strTestOnly != 'opportunityRegularAccountHHAccount') return;
        opportunityRegularAccount(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }
    /*********************************************************************************************************
    * @description Tests that no Opportunity Contact Roles are created under the different account models for
    * a normal organizational account.
    * @param strProcessor Which account model processor to test under.
    * @return void
    */
    private static void opportunityRegularAccount(string strProcessor) {
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = strProcessor));
                
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
            new npo02__Households_Settings__c (
                npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR, npo02__Household_Contact_Roles_On__c = true));
        
        //create contact
        Account acct = new Account(
            Name = 'fake account'
        );
        insert acct;
        Id accountId = acct.Id;

        Contact con = new Contact(
            FirstName=CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName=CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS,
            accountId = accountId
        );
        insert con;
        Contact[] createdContacts = [select AccountId,npo02__Household__c from Contact where id=:con.id];
        
        Id householdId = createdContacts[0].npo02__household__c;
        
        //create opportunity
        Opportunity o = new Opportunity(
            Name='test oppty',
            CloseDate=date.newInstance(2001,12,12),
            StageName=UTIL_UnitTestData_TEST.getClosedWonStage(),
            Amount=100,
            AccountId=accountId
        );
        Test.startTest();
        insert o;
        Test.stopTest();
        //check for contact roles
        List<OpportunityContactRole> createdRoles = [select Role, ContactId, IsPrimary from OpportunityContactRole where OpportunityId = :o.id order by ContactId];
        system.AssertEquals(0,createdRoles.size());
    }
    
    private static testmethod void ghIssue25() {
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Enable_Opportunity_Contact_Role_Trigger__c = true,
                npe01__Opportunity_Contact_Role_Default_role__c = 'Donor',
                npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR));
        
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
            new npo02__Households_Settings__c ( 
                npo02__Household_Contact_Roles_On__c = true,
                npo02__Household_Member_Contact_Role__c = 'Household Member'));
       
        npo02__Household__c hh = new npo02__Household__c(Name ='hh');
        insert hh;
        Contact con1= new Contact(LastName='test1',npo02__Household__c = hh.id);
        Contact con2= new Contact(LastName='test2');
        insert con1;
        insert con2;

        Test.startTest();
        Opportunity o1 = new Opportunity(
            Name = 'o1',
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage(),
            CloseDate = System.today(),
            Primary_Contact__c = (string)con1.id);
        insert o1;
        Opportunity o2 = new Opportunity(
            Name = 'o2',
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage(),
            CloseDate = System.today(),
            Primary_Contact__c = (string)con2.id);
        insert o2;        
        Test.stopTest();
        
        system.assertequals(2,(integer)[select count() from Opportunity]);
        system.assertequals(2,(integer)[select count() from OpportunityContactRole]);
    }  
    
    private static testmethod void updateOppHHAccountRefreshNone() {
        if (strTestOnly != '*' && strTestOnly != 'updateOppHHAccountRefreshNone') return;
        updateOppHHAccount('none');
    }
    private static testmethod void updateOppHHAccountRefreshOpen() {
        if (strTestOnly != '*' && strTestOnly != 'updateOppHHAccountRefreshOpen') return;
        updateOppHHAccount('Open Opportunities');
    }
	private static testmethod void updateOppHHAccountRefreshOpenAndClosed() {
        if (strTestOnly != '*' && strTestOnly != 'updateOppHHAccounRefreshOpenAndClosed') return;
        updateOppHHAccount('Open and Closed Opportunities');
	}
    /*********************************************************************************************************
    @description
    operation:
        move a contact with Opportunities to a different Household Account
    verify:
        both open and closed opps where the contact is primary, are moved to the new HH Account
        soft credit OCR's from the old Household are deleted
        soft credit OCR's for the new Household are created
    * @param strProcessor Which account model processor to test under.
    * @return void
    **********************************************************************************************************/            
    private static void updateOppHHAccount(string strRefresh) {
        if (strTestOnly != '*' && strTestOnly != 'updateOppHHAccount') return;
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR,
                npe01__Opportunity_Contact_Role_Default_role__c = 'Donor'));
        
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
            new npo02__Households_Settings__c (
                npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR,
                npo02__Household_Contact_Roles_On__c = true,
                npo02__Household_Member_Contact_Role__c='Household Member'));
        HH_OppContactRoles_TDTM.strRefreshSetting = strRefresh;
                
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(5);
        system.assertEquals(null, listCon[0].AccountId); 
        list<Account> listAcc = UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(3, CAO_Constants.HH_ACCOUNT_TYPE);
        insert listAcc;
        system.assertNotEquals(null, listAcc[0].Id);
        
        // specify the account for each contact, some sharing HH account.
        listCon[0].AccountId = listAcc[0].Id;
        listCon[1].AccountId = listAcc[0].Id;
        listCon[2].AccountId = listAcc[1].Id;
        listCon[3].AccountId = listAcc[1].Id;
        listCon[4].AccountId = listAcc[2].Id;
        insert listCon;
        
        list<Opportunity> listOppClosed = UTIL_UnitTestData_TEST.OppsForContactList(listCon, null, UTIL_UnitTestData_TEST.getClosedWonStage(),
            System.Today(), 100, null, null);
        insert listOppClosed;
                    
        list<Opportunity> listOppOpen = UTIL_UnitTestData_TEST.OppsForContactList(listCon, null, UTIL_UnitTestData_TEST.getOpenStage(),
            System.Today(), 10, null, null);
        insert listOppOpen;
        
        //check for contact roles
        List<OpportunityContactRole> listOCR = [select OpportunityId, Role, ContactId, IsPrimary from OpportunityContactRole];
        system.assertEquals(18, listOCR.size());
        
        // now let's move a contact to a different household
        listCon[0].AccountId = listAcc[1].Id;
        Test.startTest();
        update listCon[0];
        Test.stopTest();
        
        List<OpportunityContactRole> listOCR2 = [select OpportunityId, Role, ContactId, IsPrimary from OpportunityContactRole];
        list<integer> listPrimary = new integer[5];
        list<integer> listSoft = new integer[5];
        for (integer i = 0; i < 5; i++) {
        	listPrimary[i] = 0;
        	listSoft[i] = 0;
        }        
        
        for (OpportunityContactRole ocr : listOCR2) {
        	integer i = 0;
            if (ocr.ContactId == listCon[1].Id)
                i = 1;
            else if (ocr.ContactId == listCon[2].Id)
                i = 2;
            else if (ocr.ContactId == listCon[3].Id)
                i = 3;
            else if (ocr.ContactId == listCon[4].Id)
                i = 4;
            if (ocr.IsPrimary)
                listPrimary[i]++;
            else
                listSoft[i]++;                   
        }
        
        if (strRefresh == 'none') {
            system.assertEquals(18, listOCR2.size());
            system.assertEquals(2, listPrimary[0]);
            system.assertEquals(2, listSoft[0]);
            system.assertEquals(2, listPrimary[1]);
            system.assertEquals(2, listSoft[1]);
            system.assertEquals(2, listPrimary[2]);
            system.assertEquals(2, listSoft[2]);
            system.assertEquals(2, listPrimary[3]);
            system.assertEquals(2, listSoft[3]);
            system.assertEquals(2, listPrimary[4]);
            system.assertEquals(0, listSoft[4]);
        } else if (strRefresh == 'Open Opportunities') {        
            system.assertEquals(19, listOCR2.size());
            system.assertEquals(2, listPrimary[0]);
            system.assertEquals(2, listSoft[0]);
            system.assertEquals(2, listPrimary[1]);
            system.assertEquals(1, listSoft[1]);
            system.assertEquals(2, listPrimary[2]);
            system.assertEquals(3, listSoft[2]);
            system.assertEquals(2, listPrimary[3]);
            system.assertEquals(3, listSoft[3]);
            system.assertEquals(2, listPrimary[4]);
            system.assertEquals(0, listSoft[4]);
        } else {
            system.assertEquals(20, listOCR2.size());
            system.assertEquals(2, listPrimary[0]);
            system.assertEquals(2, listSoft[0]);
            system.assertEquals(2, listPrimary[1]);
            system.assertEquals(0, listSoft[1]);
            system.assertEquals(2, listPrimary[2]);
            system.assertEquals(4, listSoft[2]);
            system.assertEquals(2, listPrimary[3]);
            system.assertEquals(4, listSoft[3]);
            system.assertEquals(2, listPrimary[4]);
            system.assertEquals(0, listSoft[4]);
        }
    }

    /*********************************************************************************************************
    * @description Tests that an Opportunity Contact Role is created when the deceased member is set as the primary
    * @return void
    */
    private static testMethod void opportunityDeceasedPrimaryAccount() {
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Opportunity_Contact_Role_Default_role__c = 'Donor'));
        
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
            new npo02__Households_Settings__c (
                npo02__Household_Contact_Roles_On__c = true,
                npo02__Household_Member_Contact_Role__c='Household Member'));

        UTIL_UnitTestData_TEST.createAccountContactTestData(CAO_Constants.HH_ACCOUNT_TYPE, 1, 1, 1);
        Account acct = UTIL_UnitTestData_TEST.listAccT[0];
        Contact con = UTIL_UnitTestData_TEST.listConT[0];        
        con.Deceased__c = true;  
        update con;
                
        //create opportunity
        Opportunity o = new Opportunity(
            Name='test oppty',
            CloseDate=date.newInstance(2001,12,12),
            StageName=UTIL_UnitTestData_TEST.getClosedWonStage(),
            Amount=100,
            AccountId=acct.Id,
            Primary_Contact__c = con.Id
        );
        
        Test.startTest();
        insert o;
        Test.stopTest();
        //check for contact roles
        List<OpportunityContactRole> createdRoles = [select Role, ContactId, IsPrimary from OpportunityContactRole where OpportunityId = :o.id order by ContactId];
        System.assertEquals(1, createdRoles.size(), 'The deceased contact should be added as the primary Contact Role for the Opportunity.');
        System.assertEquals(contactSettingsForTests.npe01__Opportunity_Contact_Role_Default_role__c, createdRoles[0].Role, 'The Role should match the default contact role.');
        System.assert(createdRoles[0].IsPrimary, 'The Contact Role should be flagged as primary.');
    }

    /*******************************************************************************************************
    * @description Move an Opportunity to a different HH Account and verifies primary OCR remains
    */ 
    static testmethod void testOppsAcctChangeHHToHH() {
        if (strTestOnly != '*' && strTestOnly != 'testOppsAcctChangeHHToHH') return;
        testOppsAcctChange(CAO_Constants.HH_ACCOUNT_TYPE, CAO_Constants.HH_ACCOUNT_TYPE);
    }

    /*******************************************************************************************************
    * @description Move an Opportunity to a different One2One Account and verifies primary OCR remains
    */ 
    static testmethod void testOppsAcctChangeOne2OneToOne2One() {
        if (strTestOnly != '*' && strTestOnly != 'testOppsAcctChangeOne2OneToOne2One') return;
        testOppsAcctChange(CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE, CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE);
    }

    /*******************************************************************************************************
    * @description MMove an Opportunity from Org to HH Account and verifies primary OCR remains
    */ 
    static testmethod void testOppsAcctChangeOrgToHH() {
        if (strTestOnly != '*' && strTestOnly != 'testOppsAcctChangeOrgToHH') return;
        testOppsAcctChange(null, CAO_Constants.HH_ACCOUNT_TYPE);
    }

    /*******************************************************************************************************
    * @description Move an Opportunity from HH to Org Account and verifies primary OCR remains
    */ 
    static testmethod void testOppsAcctChangeHHToOrg() {
        if (strTestOnly != '*' && strTestOnly != 'testOppsAcctChangeHHToOrg') return;
        testOppsAcctChange(CAO_Constants.HH_ACCOUNT_TYPE, null);
    }

    /*******************************************************************************************************
    * @description Move an Opportunity from Org to One2One Account and verifies primary OCR remains
    */ 
    static testmethod void testOppsAcctChangeOrgToOne2One() {
        if (strTestOnly != '*' && strTestOnly != 'testOppsAcctChangeOrgToOne2One') return;
        testOppsAcctChange(null, CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE);
    }

    /*******************************************************************************************************
    * @description Move an Opportunity frome One2One to Org Account and verifies primary OCR remains
    */ 
    static testmethod void testOppsAcctChangeOne2OneToOrg() {
        if (strTestOnly != '*' && strTestOnly != 'testOppsAcctChangeOne2OneToOrg') return;
        testOppsAcctChange(CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE, null);
    }
    
    /*******************************************************************************************************
    * @description Move an Opportunity to a different Account type and verifies primary OCR remains
    */
    static void testOppsAcctChange(string accTypeSrc, string accTypeDst) {
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Opportunity_Contact_Role_Default_role__c = 'Donor'));
        
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
            new npo02__Households_Settings__c (
                npo02__Household_Contact_Roles_On__c = true,
                npo02__Household_Member_Contact_Role__c='Donor'));

        // create our source account and contacts
        UTIL_UnitTestData_TEST.createAccountContactTestData(accTypeSrc, 1, 2, 1);
        Account accSrc = UTIL_UnitTestData_TEST.listAccT[0];
        list<Contact> listConSrc = UTIL_UnitTestData_TEST.listConT;        
        
        // create our destination account and contacts
        UTIL_UnitTestData_TEST.createAccountContactTestData(accTypeDst, 1, 2, 3);
        Account accDst = UTIL_UnitTestData_TEST.listAccT[0];
        list<Contact> listConDst = UTIL_UnitTestData_TEST.listConT;        
        
        // create an opp for each contact
        list<Contact> listConAll = new list<Contact>();
        listConAll.addAll(listConSrc);
        listConAll.addAll(listConDst);
        list<Opportunity> listOpp = UTIL_UnitTestData_TEST.OppsForContactList(listConAll, null, 
            UTIL_UnitTestData_TEST.getclosedWonStage(), system.Today(), 100, null, null);
        insert listOpp;

        // move the first opportunity in the first account to the other account.
        Opportunity opp = listOpp[0];

        List<OpportunityContactRole> createdRoles = [select Role, ContactId, IsPrimary from OpportunityContactRole
            where OpportunityId = :opp.id and isPrimary = true order by ContactId];
        
        opp.AccountId = accDst.Id;
        Test.startTest();
        update opp;
        Test.stopTest();

        List<OpportunityContactRole> updatedRoles = [select Role, ContactId, IsPrimary from OpportunityContactRole
            where OpportunityId = :opp.id and isPrimary = true order by ContactId];
            
        // verify the primary ocr has not changed.
        System.assertEquals(createdRoles[0].ContactId, updatedRoles[0].ContactId);
    }
}