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
* @date 2011
* @description test the batchable wrapper for Opportunity Rollup functionality
*/

@isTest
private class RLLP_OppRollupBATCH_TEST {

    // if you only want to run one test in this class, fill in its name here.
    // if you want to run all tests, then use '*'
    private static string strTestOnly = '*';
    
    static void setup() {
    	npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
            new npo02__Households_Settings__c (
                npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR,
                npo02__Always_Rollup_to_Primary_Contact__c = true,
                npo02__Enable_Opp_Rollup_Triggers__c = true,
                npo02__Excluded_Account_Opp_Rectypes__c = null,
                npo02__Excluded_Account_Opp_Types__c = null,
                npo02__Excluded_Contact_Opp_Rectypes__c = null,
                npo02__Excluded_Contact_Opp_Types__c = null,
                npo02__Membership_Record_Types__c = null,
                npo02__Soft_Credit_Roles__c = 'Influencer',
                npo02__Enable_Soft_Credit_Rollups__c = true
        ));
            
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR,
                npe01__Enable_Opportunity_Contact_Role_Trigger__c = true,
                npe01__Opportunity_Contact_Role_Default_role__c = 'Donor'
            )
        );
    }
    
    static testMethod void testOppAcctRollupBatch() {
    	if (strTestOnly != '*' && strTestOnly != 'testOppAcctRollupBatch') return;
    	setup();
    	
        //The record type is an optional parameter when creating opps
        String giftRecordTypeNameForTests = UTIL_RecordTypes.getRecordTypeNameForGiftsTests(Opportunity.sObjectType);
         
        // create & insert contact
        Contact contact = UTIL_UnitTestData_TEST.getContact(); 
        insert contact;
        
        Integer giftAmount = 100;
        // create new opps
        Opportunity[] opps = UTIL_UnitTestData_TEST.OppsForContactList(new Contact[]{contact}, null, 
                UTIL_UnitTestData_TEST.getClosedWonStage(), System.Today(), giftAmount, 
                giftRecordTypeNameForTests, null);   
        insert opps;
    
        RLLP_OppAccRollup_BATCH batch = new RLLP_OppAccRollup_BATCH();
             
        Test.startTest(); 
        id batchProcessId = Database.executeBatch(batch);
        Test.stopTest();
        
        Id acctId = [select AccountId from Contact where Id = :contact.Id][0].AccountId;
        Account acct = [select Id, npo02__TotalOppAmount__c from Account where Id = :acctId];
        System.assertEquals(acct.npo02__TotalOppAmount__c, giftAmount);
    }

    static testMethod void testOppContactRollupBatch() {
        if (strTestOnly != '*' && strTestOnly != 'testOppContactRollupBatch') return;
        setup();
        
        //The record type is an optional parameter when creating opps
        String giftRecordTypeNameForTests = UTIL_RecordTypes.getRecordTypeNameForGiftsTests(Opportunity.sObjectType);
         
        // create & insert contact
        Contact contact = UTIL_UnitTestData_TEST.getContact(); 
        insert contact;
        
        Integer giftAmount = 100;
        // create new opps
        Opportunity[] opps = new Opportunity[]{};
        opps.addAll(UTIL_UnitTestData_TEST.OppsForContactList(new Contact[]{contact}, null, 
                UTIL_UnitTestData_TEST.getClosedWonStage(), System.Today(), giftAmount, 
                giftRecordTypeNameForTests, null));   
        opps.addAll(UTIL_UnitTestData_TEST.OppsForContactList(new Contact[]{contact}, null, 
                UTIL_UnitTestData_TEST.getClosedWonStage(), System.Today(), giftAmount, 
                giftRecordTypeNameForTests, null));
        opps.addAll(UTIL_UnitTestData_TEST.OppsForContactList(new Contact[]{contact}, null, 
                UTIL_UnitTestData_TEST.getClosedWonStage(), System.Today(), giftAmount, 
                giftRecordTypeNameForTests, null));
        insert opps;
        
        RLLP_OppContactRollup_BATCH batch = new RLLP_OppContactRollup_BATCH();
        
        Test.startTest();
        Database.executeBatch(batch);
        Test.stopTest();
        
        contact = [select npo02__TotalOppAmount__c, npo02__NumberOfClosedOpps__c from Contact where Id=:contact.Id];
        System.assertEquals(300, contact.npo02__TotalOppAmount__c);
        System.assertEquals(3, contact.npo02__NumberOfClosedOpps__c);
    }
    
    static testMethod void testOppSoftCreditRollupBatch() {
        if (strTestOnly != '*' && strTestOnly != 'testOppSoftCreditRollupBatch') return;
        setup();
        
        String giftRecordTypeNameForTests = UTIL_RecordTypes.getRecordTypeNameForGiftsTests(Opportunity.sObjectType);
         
        // create & insert contact
        Contact contact18 = UTIL_UnitTestData_TEST.getContact();
        Contact contact19 = UTIL_UnitTestData_TEST.getContact();
        insert new Contact[] {contact18, contact19};
        
        Integer giftAmount = 100;
        // create new opps
        Opportunity[] opps = UTIL_UnitTestData_TEST.OppsForContactList(new Contact[]{contact19}, null, 
                UTIL_UnitTestData_TEST.getClosedWonStage(), System.Today(), giftAmount, 
                giftRecordTypeNameForTests, null);   
        insert opps;
        
        //give soft credit to contact2
        OpportunityContactRole[] oppRoles = new OpportunityContactRole[]{}; 
        for(Opportunity opp : opps) {
        	oppRoles.add(new OpportunityContactRole(ContactId = contact18.Id, OpportunityId = opp.Id, 
        	               Role = 'Influencer')); 
        }
        insert oppRoles;
        
        RLLP_OppSoftCreditRollup_BATCH batch = new RLLP_OppSoftCreditRollup_BATCH();
        
        Test.startTest();
        Database.executeBatch(batch);
        Test.stopTest();
        UTIL_Debug.debugWithInfo('DJH: testOppSoftCreditRollupBatch');

        contact18 = [select npo02__Soft_Credit_This_Year__c, npo02__Soft_Credit_Total__c,
                     npo02__OppsClosedThisYear__c, npo02__TotalOppAmount__c
                            from Contact where Id=:contact18.Id];
        System.assertEquals(giftAmount, contact18.npo02__Soft_Credit_This_Year__c);
        System.assertEquals(giftAmount, contact18.npo02__Soft_Credit_Total__c);
    }

    static testMethod void testOppPartialSoftCreditRollupBatch() {
        if (strTestOnly != '*' && strTestOnly != 'testOppPartialSoftCreditRollupBatch') return;
        setup();
        
        String giftRecordTypeNameForTests = UTIL_RecordTypes.getRecordTypeNameForGiftsTests(Opportunity.sObjectType);
         
        // create & insert contact
        Contact contact18 = UTIL_UnitTestData_TEST.getContact();
        Contact contact19 = UTIL_UnitTestData_TEST.getContact();
        insert new Contact[] {contact18, contact19};
        
        Integer giftAmount = 100;
        // create new opps
        Opportunity[] opps = UTIL_UnitTestData_TEST.OppsForContactList(new Contact[]{contact19}, null, 
                UTIL_UnitTestData_TEST.getClosedWonStage(), System.Today(), giftAmount, 
                giftRecordTypeNameForTests, null);   
        insert opps;
        
        //give soft credit to contact2
        OpportunityContactRole[] oppRoles = new OpportunityContactRole[]{}; 
        for(Opportunity opp : opps) {
            oppRoles.add(new OpportunityContactRole(ContactId = contact18.Id, OpportunityId = opp.Id, 
                           Role = 'Influencer')); 
        }
        insert oppRoles;
        //insert partial soft credits
        Partial_Soft_Credit__c[] oppPartialRoles = new Partial_Soft_Credit__c[]{}; 
        for(Opportunity opp : opps) {
            oppPartialRoles.add(new Partial_Soft_Credit__c(Contact__c = contact18.Id, Opportunity__c = opp.Id, 
                           Role_Name__c = 'Influencer', Amount__c = giftAmount/2, Contact_Role_ID__c = oppRoles[0].Id)); 
        }
        insert oppPartialRoles;
        
        RLLP_OppSoftCreditRollup_BATCH batch = new RLLP_OppSoftCreditRollup_BATCH();
        
        Test.startTest();
        Database.executeBatch(batch);
        Test.stopTest();
        UTIL_Debug.debugWithInfo('DJH: testOppPartialSoftCreditRollupBatch');

        contact18 = [select npo02__Soft_Credit_This_Year__c, npo02__Soft_Credit_Total__c,
            Number_of_Soft_Credits_This_Year__c, Number_of_Soft_Credits__c
                            from Contact where Id=:contact18.Id];
        System.assertEquals(giftAmount/2, contact18.npo02__Soft_Credit_This_Year__c);
        System.assertEquals(giftAmount/2, contact18.npo02__Soft_Credit_Total__c);
        system.assertEquals(1, contact18.Number_of_Soft_Credits__c);
        system.assertEquals(1, contact18.Number_of_Soft_Credits_This_Year__c);
    }
    
}