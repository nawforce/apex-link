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
* @group Opportunity
* @description Test class for the Opportunity Primary Contact lookup.
*/
@isTest
public with sharing class OPP_PrimaryContact_TEST {

    /*********************************************************************************************************
    * @description If you only want to run a single test in this class, fill in its name here.
    * If you want to run all tests, then use '*'
    */
    private static string strTestOnly = '*';
    
    /*******************************************************************************************************
    * @description Tests insert, update, and delete of the primary opportunity contact role based on the opportunity
    * Primary Contact field.
    ********************************************************************************************************/
    static testMethod void testCreateUpdateOCR() {
        if (strTestOnly != '*' && strTestOnly != 'testCreateUpdateOCR') return;
        list<Contact> conList = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(2);
        insert conList;

        Opportunity opp = new Opportunity(
            Name = 'foo',
            Primary_Contact__c = conList[0].id,
            CloseDate = system.today(),
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage(),
            Amount = 8
        );
        insert opp;

        list<OpportunityContactRole> queryOCR = [SELECT Id, ContactId, IsPrimary FROM OpportunityContactRole WHERE OpportunityId = :opp.Id];
        system.assertEquals(1,queryOCR.size(), 'One primary contact role should be created for the opportunity.');
        system.assertEquals(conList[0].id, queryOCR[0].ContactId,'The first contact should have the primary contact role.');

        opp.Primary_Contact__c = conList[1].id;
        Test.startTest();
        update opp;
        Test.stopTest();
        
        queryOCR = [SELECT Id, ContactId, IsPrimary FROM OpportunityContactRole WHERE OpportunityId = :opp.Id];
        system.assertEquals(1,queryOCR.size(), 'One primary contact role should exist for the opportunity.');
        system.assertEquals(conList[1].id, queryOCR[0].ContactId,'The second contact should now have the primary contact role.');

        opp.Primary_Contact__c = null;
        update opp;
        
        queryOCR = [SELECT Id, ContactId, IsPrimary FROM OpportunityContactRole WHERE OpportunityId = :opp.Id];
        system.assertEquals(0,queryOCR.size(), 'The primary contact role should be deleted.');
    }
    
    /*******************************************************************************************************
    * @description Creates opportunities with primary contact roles and with a blank Primary Contact field.
    * Runs the batch utility and verifies the Primary Contact field has been populated.
    ********************************************************************************************************/
    static testMethod void testBatch() {
        if (strTestOnly != '*' && strTestOnly != 'testBatch') return;
        list<Contact> conList = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(1);
        insert conList;

        Opportunity opp = new Opportunity(
            Name = 'foo',
            Primary_Contact__c = conList[0].id,
            CloseDate = system.today(),
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage(),
            Amount = 8
        );
        insert opp;

        list<OpportunityContactRole> queryOCR = [SELECT Id, ContactId, IsPrimary FROM OpportunityContactRole WHERE OpportunityId = :opp.Id];
        system.assertEquals(1,queryOCR.size(), 'An OCR should be created.');

        //null out the field without deleting the OCR
        TDTM_TriggerHandler.disableTDTM = true;
        opp.Primary_Contact__c = null;
        update opp;
        TDTM_TriggerHandler.disableTDTM = false;

        list<Opportunity> queryOpp = [SELECT Id, Primary_Contact__c FROM Opportunity WHERE Id = :opp.id];
        system.assertEquals(null,queryOpp[0].Primary_Contact__c, 'The primary contact field should be blank.');

        test.startTest();
        database.executeBatch(new OPP_PrimaryContact_BATCH());
        test.stopTest();

        queryOpp = [SELECT Id, Primary_Contact__c FROM Opportunity WHERE Id = :opp.id];
        system.assertEquals(conList[0].id,queryOpp[0].Primary_Contact__c, 'The primary contact field should have the contact id.');
    }

    /*******************************************************************************************************
    * @description Bulk creates opportunities with Primary Contact set, verifies Contact Roles are created.
    ********************************************************************************************************/
    static testMethod void testBulkInsert() {
        if (strTestOnly != '*' && strTestOnly != 'testBulk') return;
        
        integer bulkSize = 50;
        
        list<Contact> conList = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(bulkSize);
        insert conList;
        
        list<Opportunity> oppList = UTIL_UnitTestData_TEST.OppsForContactList(conList, null, UTIL_UnitTestData_TEST.getclosedWonStage(), system.Today(), 8, null, null);
        Test.startTest();
        insert oppList;
        Test.stopTest();

        list<OpportunityContactRole> queryOCR = [SELECT Id, ContactId, IsPrimary FROM OpportunityContactRole WHERE OpportunityId IN :oppList];
        system.assertEquals(50,queryOCR.size(), 'An OCR should be created for each Opportunity.');

    }

    /*******************************************************************************************************
    * @description Update an opportunity primary contact to a contact with an existing ocr. Verify that the
    * old OCR is deleted.
    ********************************************************************************************************/
    static testMethod void testExistingConRoles() {
        if (strTestOnly != '*' && strTestOnly != 'testExistingConRoles') return;
        
        list<Contact> conList = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(2);
        insert conList;
        
        Opportunity opp = new Opportunity(
            Name = 'foo',
            Primary_Contact__c = conList[0].id,
            CloseDate = system.today(),
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage(),
            Amount = 8
        );
        insert opp;

        OpportunityContactRole secondaryOCR = new OpportunityContactRole(
            ContactId = conList[1].Id,
            OpportunityId = opp.Id,
            Role = 'foo'
        );
        insert secondaryOCR;

        list<OpportunityContactRole> queryOCR = [SELECT Id, ContactId, IsPrimary FROM OpportunityContactRole WHERE OpportunityId = :opp.id ORDER BY IsPrimary DESC];
        system.assertEquals(2,queryOCR.size(), 'Two OCRs should exist.');
        system.assertEquals(conList[0].id, queryOCR[0].ContactId, 'Contact 0 should be primary.');

        opp.Primary_Contact__c = conList[1].id;
        Test.startTest();
        update opp;
        Test.stopTest();

        queryOCR = [SELECT Id, ContactId, IsPrimary FROM OpportunityContactRole WHERE OpportunityId = :opp.id ORDER BY IsPrimary DESC];
        system.assertEquals(1,queryOCR.size(), 'Two OCRs should exist.');
        system.assertEquals(conList[1].id, queryOCR[0].ContactId, 'Contact 0 should be primary.');

    }
}