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
* @description Tests for OpportunityContactRoles
*/
@isTest
private class OPP_OpportunityContactRoles_TEST {

    /*********************************************************************************************************
    * @description if you only want to run one test in this class, fill in its name here.
    * if you want to run all tests, then use '*'
    */
    private static string strTestOnly = '*';
 
    /*******************************************************************************************************
    * @description Role values to use in settings.
    */    
    private static string donorRoleforTest = 'Donor';
    private static string HonoreeRoleforTest = 'Honoree';
    private static string NotificationRecipientRoleforTest = 'Notification Recipient';

    /*******************************************************************************************************
    * @description Test creation or upate of contact role.
    */
    static testMethod void createOrUpdateCR() {
        if (strTestOnly != '*' && strTestOnly != 'createOrUpdateCR') return;
        
        Account acc = new Account(Name='test77');
        insert acc;

        Contact con = UTIL_UnitTestData_TEST.getContact();
        con.AccountId = acc.id;
        insert con;

        //Simulate user interface creation of opp from button on contact screen, 
        //i.e. Role field is blank in the new contact role.
        Opportunity opp1 = new Opportunity(
            Name = 'Apex Test Opp1',
            AccountId = acc.Id,
            CloseDate = date.today(), StageName = UTIL_UnitTestData_TEST.getClosedWonStage()
        );
        insert opp1;
        
        //Make sure that we haven't created a contact role yet.
        OpportunityContactRole[] result = [select OpportunityId, ContactId, Role from OpportunityContactRole where OpportunityId = :opp1.Id];
        system.assertEquals(0, result.size());
        
        //Insert the contact role with a blank Role field.
        OpportunityContactRole cr = new OpportunityContactRole (OpportunityId = opp1.Id, ContactId = con.Id, IsPrimary = true);
        insert cr;
        
        result = [select OpportunityId, ContactId, Role from OpportunityContactRole where OpportunityId = :opp1.Id];
        system.assertEquals(con.Id, result[0].ContactId);
        //system.assertEquals(null, result[0].Role); there might be a default set
        
        //Now simulate import of an opp record with a contact ID.
        Opportunity opp2 = new Opportunity(
            Name = 'Apex Test Opp2',
            AccountId = acc.Id,
            Primary_Contact__c = con.Id,
            CloseDate = date.today(), StageName = UTIL_UnitTestData_TEST.getClosedWonStage()
        );
        insert opp2;

        result = [select OpportunityId, ContactId, Role from OpportunityContactRole where OpportunityId = :opp2.Id];
        system.assertEquals(con.Id, result[0].ContactId);
        system.assertEquals(donorRoleforTest, result[0].Role);
    }
    
    /*******************************************************************************************************
    * @description Disables OCR and verifies contact role isn't created.
    */
    static testMethod void oppCRTriggerTurnedOff() {
        if (strTestOnly != '*' && strTestOnly != 'oppCRTriggerTurnedOff') return;
        
        npe01__Contacts_and_Orgs_Settings__c testSettings = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR, 
                npe01__Opportunity_Contact_Role_Default_role__c = donorRoleforTest));

        Contact con = UTIL_UnitTestData_TEST.getContact();
        insert con;
        
        Trigger_Handler__c triggerHandler = [select Active__c from Trigger_Handler__c where Class__c = 'OPP_OpportunityContactRoles_TDTM'];
        triggerHandler.Active__c = false;
        update triggerHandler;
        TDTM_ObjectDataGateway.ClearCachedTriggerHandlersForTest();
        
        Opportunity opp1 = new Opportunity(
            Name = 'Apex Test Opp1',
            npe01__Contact_Id_for_Role__c = con.Id,
            CloseDate = date.today(),
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage()
        );
        Test.startTest();
        insert opp1;
        Test.stopTest();
        OpportunityContactRole[] result = [select OpportunityId, ContactId, isPrimary, Role from OpportunityContactRole where OpportunityId = :opp1.Id];
        //should be a contact role
        system.assertEquals(0, result.size());
        
        Opportunity[] oppResult = [select AccountId from Opportunity where Id = :opp1.Id];
        //should have the contact's one to one account
        system.assertEquals(null, oppResult[0].AccountId);
    }

    /*******************************************************************************************************
    * @description Tests contact role creation for 1:1 model, using contact attribution.
    */
    static testMethod void oppRolesForOneToOneContact() {
        if (strTestOnly != '*' && strTestOnly != 'oppRolesForOneToOneContact') return;

        npe01__Contacts_and_Orgs_Settings__c testSettings = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR,npe01__Enable_Opportunity_Contact_Role_Trigger__c = true, npe01__Opportunity_Contact_Role_Default_role__c = donorRoleforTest));

        Contact con = UTIL_UnitTestData_TEST.getContact();
        insert con;
        
        Contact[] createdContacts = [select AccountId from Contact where Id = :con.id];

        Opportunity opp1 = new Opportunity(
            Name = 'Apex Test Opp1',
            Primary_Contact__c = con.Id,
            CloseDate = date.today(),
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage()
        );
        Test.startTest();
        insert opp1;
        Test.stopTest();
        OpportunityContactRole[] result = [select OpportunityId, ContactId, isPrimary, Role from OpportunityContactRole where OpportunityId = :opp1.Id];
        //should be a contact role
        system.assertEquals(1, result.size());
        system.assertEquals(donorRoleforTest, result[0].Role);
        system.assertEquals(true, result[0].isPrimary);
        
       Opportunity[] oppResult = [select AccountId from Opportunity where Id = :opp1.Id];
       //should have the contact's one to one account
       system.assertEquals(createdContacts[0].AccountId, oppResult[0].AccountId);
    }
    
    /*******************************************************************************************************
    * @description Tests contact role creation for individual/bucket model using contact attribution.
    */
    static testMethod void oppRolesForIndividualContact() {
        if (strTestOnly != '*' && strTestOnly != 'oppRolesForIndividualContact') return;

        npe01__Contacts_and_Orgs_Settings__c testSettings = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.BUCKET_PROCESSOR,npe01__Enable_Opportunity_Contact_Role_Trigger__c = true, npe01__Opportunity_Contact_Role_Default_role__c = donorRoleforTest));
              
        Contact con = UTIL_UnitTestData_TEST.getContact();
        insert con;
        
        Contact[] createdContacts = [select AccountId from Contact where Id = :con.id];

        Opportunity opp1 = new Opportunity(
            Name = 'Apex Test Opp1',
            Primary_Contact__c = con.Id,
            CloseDate = date.today(),
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage()
        );
        Test.startTest();
        insert opp1;
        Test.stopTest();

        OpportunityContactRole[] result = [select OpportunityId, ContactId, isPrimary,Role from OpportunityContactRole where OpportunityId = :opp1.Id];
        //should be one role
        system.assertEquals(1, result.size());
        system.assertEquals(donorRoleforTest, result[0].Role);
        system.assertEquals(true, result[0].isPrimary);
        
       Opportunity[] oppResult = [select AccountId from Opportunity where Id = :opp1.Id];
       //should be null account
       system.assertEquals(null, oppResult[0].AccountId);

    }
    
    /*******************************************************************************************************
    * @description Tests contact role creation for 1:1 model, using account attribution.
    */
    static testMethod void oppRolesForOneToOneAccount() {
        if (strTestOnly != '*' && strTestOnly != 'oppRolesForOneToOneAccount') return;

        npe01__Contacts_and_Orgs_Settings__c testSettings = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR,npe01__Enable_Opportunity_Contact_Role_Trigger__c = true, npe01__Opportunity_Contact_Role_Default_role__c = donorRoleforTest));
                
        Contact con = UTIL_UnitTestData_TEST.getContact();
        insert con;
        
        Contact[] createdContacts = [select AccountId from Contact where Id = :con.id];

        Opportunity opp1 = new Opportunity(
            Name = 'Apex Test Opp1',
            AccountId = createdContacts[0].AccountId,
            CloseDate = date.today(),
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage()
        );
        Test.startTest();
        insert opp1;
        Test.stopTest();
        OpportunityContactRole[] result = [select OpportunityId, ContactId, isPrimary, Role from OpportunityContactRole where OpportunityId = :opp1.Id];
        //should be a contact role
        system.assertEquals(1, result.size());
        system.assertEquals(donorRoleforTest, result[0].Role);
        system.assertEquals(true, result[0].isPrimary);
        
    }
    
    /*******************************************************************************************************
    * @description Tests contact role creation for 1:1 model, using contact attribution.
    */
    static testMethod void oppRolesForIndividualAccount() {
        if (strTestOnly != '*' && strTestOnly != 'oppRolesForIndividualAccount') return;

        npe01__Contacts_and_Orgs_Settings__c testSettings = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (npe01__Account_Processor__c = CAO_Constants.BUCKET_PROCESSOR,npe01__Enable_Opportunity_Contact_Role_Trigger__c = true, npe01__Opportunity_Contact_Role_Default_role__c = donorRoleforTest));
              
        Contact con = UTIL_UnitTestData_TEST.getContact();
        insert con;
        
        Contact[] createdContacts = [select AccountId from Contact where Id = :con.id];

        Opportunity opp1 = new Opportunity(
            Name = 'Apex Test Opp1',
            npe01__Contact_Id_for_Role__c = con.Id,
            CloseDate = date.today(),
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage()
        );
        Test.startTest();
        insert opp1;
        Test.stopTest();

        OpportunityContactRole[] result = [select OpportunityId, ContactId, isPrimary,Role from OpportunityContactRole where OpportunityId = :opp1.Id];
        //should be one role
        system.assertEquals(1, result.size());
        system.assertEquals(donorRoleforTest, result[0].Role);
        system.assertEquals(true, result[0].isPrimary);
        
        Opportunity[] oppResult = [select AccountId from Opportunity where Id = :opp1.Id];
        //should be null account
        system.assertEquals(null, oppResult[0].AccountId);

    }
    
    /*******************************************************************************************************
    * @description Puts a bad ID vaue in the Contact ID for Role field, verifies exception is thrown.
    */
    static testMethod void oppRolesForBadId() {
        if (strTestOnly != '*' && strTestOnly != 'oppRolesForBadId') return;

        Opportunity opp1 = new Opportunity(
            Name = 'Apex Test Opp1',
            npe01__Contact_Id_for_Role__c = 'junk',
            CloseDate = date.today(),
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage()
        );

        Test.startTest();
        try {
            insert opp1;            
            
            OpportunityContactRole[] result = [select OpportunityId, ContactId, Role from OpportunityContactRole where OpportunityId = :opp1.Id];
            //shouldn't be a contact role
            system.assertEquals(0, result.size());
            
            Opportunity[] oppResult = [select AccountId from Opportunity where Id = :opp1.Id];
            //shouldn't be an account
            system.assertEquals(null, oppResult[0].AccountId);
        } catch (exception e) {
            system.assert(e.getMessage().contains(Label.npe01.Opportunity_Contact_Role_Error_Bad_Contact_Id));
        }
        Test.stopTest();
    }

    /*******************************************************************************************************
    * @description Leaves Honoree and Notification Recipient contact role settings blank. Adds contacts for 
    * Honoree and Notification recipient contacts. Verifies Name fields updated when blank, verifies no
    * contact roles are created.
    */
    static testMethod void honoreeNotificationBlankSettings(){
        if (strTestOnly != '*' && strTestOnly != 'honoreeNotificationBlankSettings') return;
        
        list<contact> testCons = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(3);
        insert testCons;

        Opportunity opp = new Opportunity(
            Name = 'Test',
            Primary_Contact__c = testCons[0].id,
            Honoree_Contact__c = testCons[1].id,
            Notification_Recipient_Contact__c = testCons[2].id,
            Notification_Recipient_Name__c = 'do not overwrite me',
            CloseDate = date.today(),
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage()
        );

        Test.startTest();
        insert opp;
        Test.stopTest();

        list<Contact> queryCon = [SELECT Id, Name FROM Contact WHERE Id IN :testCons];
        list<Opportunity> queryOpp = [SELECT Id, Notification_Recipient_Name__c, Honoree_Name__c FROM Opportunity WHERE Id = :opp.id];
        system.assertEquals(queryCon[1].Name, queryOpp[0].Honoree_Name__c, 'Honoree Name should be populated.');
        system.assertEquals('do not overwrite me', queryOpp[0].Notification_Recipient_Name__c, 'Notifcation Recipient Name should not be overwritten.');

        list<OpportunityContactRole> queryOCR = [SELECT Id, ContactId, OpportunityId, isPrimary, Role FROM OpportunityContactRole WHERE OpportunityId =:opp.id];
        system.assertEquals(1,queryOCR.size(), 'Only one Opportunity Contact Role should be created.');
        system.assertEquals(testCons[0].id, queryOCR[0].ContactId, 'Contact role should be for contact designated as primary.');
        system.assertEquals(true, queryOCR[0].isPrimary, 'Contact role marked primary.');
    }

    /*******************************************************************************************************
    * @description Populates Honoree and Notification Recipient contact role settings. Adds contacts for 
    * Honoree and Notification recipient contacts. Verifies contact roles are created correctly.
    */
    static testMethod void honoreeNotificationInsert(){
        if (strTestOnly != '*' && strTestOnly != 'honoreeNotificationInsert') return;
        
        npe01__Contacts_and_Orgs_Settings__c testSettings = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c(
                npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR,
                npe01__Opportunity_Contact_Role_Default_role__c = donorRoleforTest,
                Honoree_Opportunity_Contact_Role__c = HonoreeRoleforTest, 
                Notification_Recipient_Opp_Contact_Role__c = NotificationRecipientRoleforTest
            )
        );

        list<contact> testCons = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(3);
        insert testCons;

        Opportunity opp = new Opportunity(
            Name = 'Test',
            Primary_Contact__c = testCons[0].id,
            Honoree_Contact__c = testCons[1].id,
            Notification_Recipient_Contact__c = testCons[2].id,
            CloseDate = date.today(),
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage()
        );

        Test.startTest();
        insert opp;
        Test.stopTest();

        list<Contact> queryCon = [SELECT Id, Name FROM Contact WHERE Id IN :testCons];
        list<Opportunity> queryOpp = [SELECT Id, Notification_Recipient_Name__c, Honoree_Name__c FROM Opportunity WHERE Id = :opp.id];
        system.assertEquals(queryCon[1].Name, queryOpp[0].Honoree_Name__c, 'Honoree Name should be populated.');
        system.assertEquals(queryCon[2].Name, queryOpp[0].Notification_Recipient_Name__c, 'Notifcation Recipient Name should be populated.');

        list<OpportunityContactRole> queryOCR = [SELECT Id, ContactId, OpportunityId, isPrimary, Role FROM OpportunityContactRole WHERE OpportunityId =:opp.id ORDER BY ContactId];
        system.assertEquals(3,queryOCR.size(), 'Three Opportunity Contact Roles should be created.');
        
        system.assertEquals(testCons[0].id,queryOCR[0].ContactId, 'First OCR is for primary contact.');
        system.assertEquals(true,queryOCR[0].isPrimary, 'Primary OCR is marked primary.');
        system.assertEquals(donorRoleforTest,queryOCR[0].Role, 'Primary OCR has the correct role.');

        system.assertEquals(testCons[1].id,queryOCR[1].ContactId, 'Second OCR is for honoree contact.');
        system.assertEquals(false,queryOCR[1].isPrimary, 'Honoree OCR is not marked primary.');
        system.assertEquals(HonoreeRoleforTest,queryOCR[1].Role, 'Honoree OCR has the correct role.');

        system.assertEquals(testCons[2].id,queryOCR[2].ContactId, 'Third OCR is for notification recipient contact.');
        system.assertEquals(false,queryOCR[2].isPrimary, 'Notification recipient OCR is not marked primary.');
        system.assertEquals(NotificationRecipientRoleforTest,queryOCR[2].Role, 'Notification recipient OCR has the correct role.');
    }

    /*******************************************************************************************************
    * @description Updates an opportunity with Honoree and Notification Recipient contacts. Honoree is 
    * updated from one contact to another. Notification recipient is the same as the donor. Verifies that 
    * contact role deleted for first honoree, and created for the second honoree, but that no duplicate role
    * is created for the donor/notification recipient.
    */
    static testMethod void honoreeNotificationUpdate(){
        if (strTestOnly != '*' && strTestOnly != 'honoreeNotificationUpdate') return;
        
        npe01__Contacts_and_Orgs_Settings__c testSettings = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c(
                npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR,
                npe01__Opportunity_Contact_Role_Default_role__c = donorRoleforTest,
                Honoree_Opportunity_Contact_Role__c = HonoreeRoleforTest, 
                Notification_Recipient_Opp_Contact_Role__c = NotificationRecipientRoleforTest
            )
        );

        list<contact> testCons = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(3);
        insert testCons;

        Opportunity opp = new Opportunity(
            Name = 'Test',
            Primary_Contact__c = testCons[0].id,
            Honoree_Contact__c = testCons[2].id,
            CloseDate = date.today(),
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage()
        );
        insert opp;

        list<Contact> queryCon = [SELECT Id, Name FROM Contact WHERE Id IN :testCons];
        list<Opportunity> queryOpp = [SELECT Id, Notification_Recipient_Name__c, Honoree_Name__c FROM Opportunity WHERE Id = :opp.id];
        system.assertEquals(queryCon[2].Name, queryOpp[0].Honoree_Name__c, 'Honoree Name should be populated.');

        list<OpportunityContactRole> queryOCR = [SELECT Id, ContactId, OpportunityId, isPrimary, Role FROM OpportunityContactRole WHERE OpportunityId =:opp.id ORDER BY ContactId];
        system.assertEquals(2,queryOCR.size(), 'Two Opportunity Contact Roles should be created.');

        system.assertEquals(testCons[0].id,queryOCR[0].ContactId, 'First OCR is for primary contact.');
        system.assertEquals(true,queryOCR[0].isPrimary, 'Primary OCR is marked primary.');
        system.assertEquals(donorRoleforTest,queryOCR[0].Role, 'Primary OCR has the correct role.');

        system.assertEquals(testCons[2].id,queryOCR[1].ContactId, 'Second OCR is for honoree contact.');
        system.assertEquals(false,queryOCR[1].isPrimary, 'Honoree OCR is not marked primary.');
        system.assertEquals(HonoreeRoleforTest,queryOCR[1].Role, 'Honoree OCR has the correct role.');

        opp.Honoree_Contact__c = testCons[1].id;
        opp.Notification_Recipient_Contact__c = testCons[0].id;

        Test.startTest();
        update opp;
        Test.stopTest();

        queryOpp = [SELECT Id, Notification_Recipient_Name__c, Honoree_Name__c FROM Opportunity WHERE Id = :opp.id];
        system.assertEquals(queryCon[1].Name, queryOpp[0].Honoree_Name__c, 'Honoree Name should be populated.');
        system.assertEquals(queryCon[0].Name, queryOpp[0].Notification_Recipient_Name__c, 'Notifcation Recipient Name should be populated.');

        queryOCR = [SELECT Id, ContactId, OpportunityId, isPrimary, Role FROM OpportunityContactRole WHERE OpportunityId =:opp.id ORDER BY ContactId];
        system.assertEquals(2,queryOCR.size(), 'Two Opportunity Contact Roles should be created.');
        
        system.assertEquals(testCons[0].id,queryOCR[0].ContactId, 'First OCR is for primary contact.');
        system.assertEquals(true,queryOCR[0].isPrimary, 'Primary OCR is marked primary.');
        system.assertEquals(donorRoleforTest,queryOCR[0].Role, 'Primary OCR has the correct role.');

        system.assertEquals(testCons[1].id,queryOCR[1].ContactId, 'Second OCR is for honoree contact.');
        system.assertEquals(false,queryOCR[1].isPrimary, 'Honoree OCR is not marked primary.');
        system.assertEquals(HonoreeRoleforTest,queryOCR[1].Role, 'Honoree OCR has the correct role.');

        opp.Honoree_Contact__c = null;
        update opp;

        queryOpp = [SELECT Id, Notification_Recipient_Name__c, Honoree_Name__c FROM Opportunity WHERE Id = :opp.id];
        system.assert(String.isBlank(queryOpp[0].Honoree_Name__c), 'Honoree Name should be deleted.');

    }

    /*******************************************************************************************************
    * @description Verifies that contact roles for household members aren't created as duplicates to contact
    * roles
    */
    static testMethod void avoidDuplicateCRs(){
        if (strTestOnly != '*' && strTestOnly != 'avoidDuplicateCRs') return;
        
        npe01__Contacts_and_Orgs_Settings__c testSettings = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c(
                npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR,
                npe01__Opportunity_Contact_Role_Default_role__c = donorRoleforTest,
                Honoree_Opportunity_Contact_Role__c = HonoreeRoleforTest, 
                Notification_Recipient_Opp_Contact_Role__c = NotificationRecipientRoleforTest
            )
        );

        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
            new npo02__Households_Settings__c (
                npo02__Household_Rules__c = HH_Households.NO_HOUSEHOLDS_PROCESSOR,
                npo02__Household_Contact_Roles_On__c = true,
                npo02__Household_Member_Contact_Role__c='Household Member'
            )
        );

        Contact primaryCon = UTIL_UnitTestData_TEST.getContact();
        insert primaryCon;

        list<Contact> queryCon = [select AccountId from Contact where Id = :primaryCon.id];
        Id hhAccountId = queryCon[0].AccountId;

        list<contact> householdMembers = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(2);
        householdMembers[0].AccountId = hhAccountId;
        householdMembers[1].AccountId = hhAccountId;
        insert householdMembers;

        queryCon = [SELECT Id, AccountId, Name FROM Contact WHERE Id IN :householdMembers OR Id = :primaryCon.id];
        for (integer i=0; i<queryCon.size(); i++)
            system.assertEquals(hhAccountId, queryCon[i].AccountId, 'Contacts should be in the same household.');

        Opportunity opp = new Opportunity(
            Name = 'Test',
            Primary_Contact__c = primaryCon.id,
            Honoree_Contact__c = householdMembers[0].id,
            CloseDate = date.today(),
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage()
        );

        Test.startTest();
        insert opp;
        Test.stopTest();

        list<Opportunity> queryOpp = [SELECT Id, Notification_Recipient_Name__c, Honoree_Name__c FROM Opportunity WHERE Id = :opp.id];
        system.assertEquals(queryCon[1].Name, queryOpp[0].Honoree_Name__c, 'Honoree Name should be populated.');

        list<OpportunityContactRole> queryOCR = [SELECT Id, ContactId, OpportunityId, isPrimary, Role FROM OpportunityContactRole WHERE OpportunityId =:opp.id ORDER BY ContactId];
        system.assertEquals(3,queryOCR.size(), 'Three Opportunity Contact Roles should be created.');
        
        system.assertEquals(primaryCon.id,queryOCR[0].ContactId, 'First OCR is for primary contact.' + queryOCR[0]);
        system.assertEquals(true,queryOCR[0].isPrimary, 'Primary OCR is marked primary.' + queryOCR[0]);
        system.assertEquals(donorRoleforTest,queryOCR[0].Role, 'Primary OCR has the correct role.' + queryOCR[0]);

        system.assertEquals(householdMembers[0].id,queryOCR[1].ContactId, 'Second OCR is for honoree contact.');
        system.assertEquals(false,queryOCR[1].isPrimary, 'Honoree OCR is not marked primary.');
        system.assertEquals(HonoreeRoleforTest,queryOCR[1].Role, 'Honoree OCR has the correct role.');

        system.assertEquals(householdMembers[1].id,queryOCR[2].ContactId, 'Third OCR is for household member contact.');
        system.assertEquals(false,queryOCR[2].isPrimary, 'Household member OCR is not marked primary.');
        system.assertEquals('Household Member',queryOCR[2].Role, 'Household member OCR has the correct role.');
    }
}