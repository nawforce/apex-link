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
* @date 2015
* @description Tests for Moving a Contact from one Account to another Account
* @group ContactMerge
*/

@isTest
public with sharing class CON_ChangeAccount_TEST {

    /*********************************************************************************************************
    * @description if you only want to run one test in this class, fill in its name here.
    * if you want to run all tests, then use '*'
    */
    private static string strTestOnly = '*';
            
    /*******************************************************************************************************
    * @description Move a Contact to a different Account and verifies Opps, OCRs, Rollups, and Names
    * are updated as appropriate for the account types.
    */ 
    static testmethod void testNamesAndOppsUpdatedHHToHH() {
        if (strTestOnly != '*' && strTestOnly != 'testNamesAndOppsUpdatedHHToHH') return;
        testNamesAndOppsUpdated(CAO_Constants.HH_ACCOUNT_TYPE, CAO_Constants.HH_ACCOUNT_TYPE);
    }

    /*******************************************************************************************************
    * @description Move a Contact to a different Account and verifies Opps, OCRs, Rollups, and Names
    * are updated as appropriate for the account types.
    */ 
    static testmethod void testNamesAndOppsUpdatedOne2OneToOne2One() {
        if (strTestOnly != '*' && strTestOnly != 'testNamesAndOppsUpdatedOne2OneToOne2One') return;
        testNamesAndOppsUpdated(CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE, CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE);
    }

    /*******************************************************************************************************
    * @description Move a Contact to a different Account and verifies Opps, OCRs, Rollups, and Names
    * are updated as appropriate for the account types.
    */ 
    static testmethod void testNamesAndOppsUpdatedOrgToHH() {
        if (strTestOnly != '*' && strTestOnly != 'testNamesAndOppsUpdatedOrgToHH') return;
        testNamesAndOppsUpdated(null, CAO_Constants.HH_ACCOUNT_TYPE);
    }

    /*******************************************************************************************************
    * @description Move a Contact to a different Account and verifies Opps, OCRs, Rollups, and Names
    * are updated as appropriate for the account types.
    */ 
    static testmethod void testNamesAndOppsUpdatedHHToOrg() {
        if (strTestOnly != '*' && strTestOnly != 'testNamesAndOppsUpdatedHHToOrg') return;
        testNamesAndOppsUpdated(CAO_Constants.HH_ACCOUNT_TYPE, null);
    }

    /*******************************************************************************************************
    * @description Move a Contact to a different Account and verifies Opps, OCRs, Rollups, and Names
    * are updated as appropriate for the account types.
    */ 
    static testmethod void testNamesAndOppsUpdatedOrgToOne2One() {
        if (strTestOnly != '*' && strTestOnly != 'testNamesAndOppsUpdatedOrgToOne2One') return;
        testNamesAndOppsUpdated(null, CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE);
    }

    /*******************************************************************************************************
    * @description Move a Contact to a different Account and verifies Opps, OCRs, Rollups, and Names
    * are updated as appropriate for the account types.
    */ 
    static testmethod void testNamesAndOppsUpdatedOne2OneToOrg() {
        if (strTestOnly != '*' && strTestOnly != 'testNamesAndOppsUpdatedOne2OneToOrg') return;
        testNamesAndOppsUpdated(CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE, null);
    }
    
    /*******************************************************************************************************
    * @description Move a Contact to a different Account, and verify: 
    *   the account's names and greetings are updated.
    *   Opps owned by the contact are moved over.
    *   Soft Credits for the moved Opps are recalculated.
    *   Soft Credits the contact previously had are still there.
    *   Opp Rollups are recalculated for both accounts.
    */
    static void testNamesAndOppsUpdated(string accTypeSrc, string accTypeDst) {
    
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
        
        // move the first contact in the first account to the other account.
        Contact conSrc = listConSrc[0];
        conSrc.AccountId = accDst.Id;
        Test.startTest();
        update conSrc;
        Test.stopTest();

        // reload our contacts and accounts
        list<Account> listAcc = new list<Account>([select Id, Name, npo02__Formal_Greeting__c, npo02__Informal_Greeting__c, 
            npo02__NumberOfClosedOpps__c, 
            BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry 
            from Account order by Id]);
        list<Contact> listCon = [select Id, Firstname, AccountId, 
            is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry 
            from Contact order by Firstname];
            
        // verify the contacts belong to correct account.
        system.assertEquals(conSrc.Id, listCon[0].Id);
        system.assertEquals(accSrc.Id, listAcc[0].Id);
        system.assertEquals(accDst.Id, listAcc[1].Id);
        system.assertEquals(listCon[0].AccountId, listAcc[1].Id);
        system.assertEquals(listCon[1].AccountId, listAcc[0].Id);
        system.assertEquals(listCon[2].AccountId, listAcc[1].Id);
        system.assertEquals(listCon[3].AccountId, listAcc[1].Id);
            
        // verify the account's names and greetings are updated
        if (accTypeSrc == CAO_Constants.HH_ACCOUNT_TYPE)
            system.assertEquals(false, listAcc[0].npo02__Formal_Greeting__c.contains(listCon[0].Firstname));        
        if (accTypeDst == CAO_Constants.HH_ACCOUNT_TYPE)
            system.assertEquals(true, listAcc[1].npo02__Formal_Greeting__c.contains(listCon[0].Firstname));    
        
        integer cAccSrcOpps = [select count() from Opportunity where AccountId = :listAcc[0].Id];
        integer cAccDstOpps = [select count() from Opportunity where AccountId = :listAcc[1].Id];
        integer cOCRSrcCon = [select count() from OpportunityContactRole where ContactId = :listCon[0].Id];
        integer cOCRSrcCon2 = [select count() from OpportunityContactRole where ContactId = :listCon[1].Id];
        integer cOCRDstCon = [select count() from OpportunityContactRole where ContactId = :listCon[2].Id];
        
        // verify Opps, OCR's, and Rollups are what we expect!
        if (accTypeSrc == CAO_Constants.HH_ACCOUNT_TYPE && accTypeDst == CAO_Constants.HH_ACCOUNT_TYPE) {
            system.assertEquals(1, cAccSrcOpps);
            system.assertEquals(3, cAccDstOpps);
            system.assertEquals(2, cOCRSrcCon);
            system.assertEquals(1, cOCRSrcCon2);
            system.assertEquals(3, cOCRDstCon);
	        system.assertEquals(1, listAcc[0].npo02__NumberOfClosedOpps__c);            
	        system.assertEquals(3, listAcc[1].npo02__NumberOfClosedOpps__c);            
        }
        else if (accTypeSrc == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE && accTypeDst == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE) {
            system.assertEquals(2, cAccSrcOpps);
            system.assertEquals(2, cAccDstOpps);
            system.assertEquals(1, cOCRSrcCon);
            system.assertEquals(1, cOCRSrcCon2);
            system.assertEquals(1, cOCRDstCon);
            system.assertEquals(2, listAcc[0].npo02__NumberOfClosedOpps__c);            
            system.assertEquals(2, listAcc[1].npo02__NumberOfClosedOpps__c);            
        }
        else if (accTypeSrc == null && accTypeDst == CAO_Constants.HH_ACCOUNT_TYPE) {
            system.assertEquals(0, cAccSrcOpps);
            system.assertEquals(2, cAccDstOpps);
            system.assertEquals(1, cOCRSrcCon);
            system.assertEquals(1, cOCRSrcCon2);
            system.assertEquals(2, cOCRDstCon);
            system.assertEquals(0, listAcc[0].npo02__NumberOfClosedOpps__c);            
            system.assertEquals(2, listAcc[1].npo02__NumberOfClosedOpps__c);            
        }
        else if (accTypeSrc == CAO_Constants.HH_ACCOUNT_TYPE && accTypeDst == null) {
            system.assertEquals(2, cAccSrcOpps);
            system.assertEquals(0, cAccDstOpps);
            system.assertEquals(2, cOCRSrcCon);
            system.assertEquals(2, cOCRSrcCon2);
            system.assertEquals(1, cOCRDstCon);
            system.assertEquals(2, listAcc[0].npo02__NumberOfClosedOpps__c);            
            system.assertEquals(0, listAcc[1].npo02__NumberOfClosedOpps__c);            
        }
        else if (accTypeSrc == null && accTypeDst == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE) {
            system.assertEquals(0, cAccSrcOpps);
            system.assertEquals(2, cAccDstOpps);
            system.assertEquals(1, cOCRSrcCon);
            system.assertEquals(1, cOCRSrcCon2);
            system.assertEquals(1, cOCRDstCon);
            system.assertEquals(0, listAcc[0].npo02__NumberOfClosedOpps__c);            
            system.assertEquals(2, listAcc[1].npo02__NumberOfClosedOpps__c);            
        }
        else if (accTypeSrc == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE && accTypeDst == null) {
            system.assertEquals(2, cAccSrcOpps);
            system.assertEquals(0, cAccDstOpps);
            system.assertEquals(1, cOCRSrcCon);
            system.assertEquals(1, cOCRSrcCon2);
            system.assertEquals(1, cOCRDstCon);
            system.assertEquals(2, listAcc[0].npo02__NumberOfClosedOpps__c);            
            system.assertEquals(0, listAcc[1].npo02__NumberOfClosedOpps__c);            
        }
        
    }

    /*******************************************************************************************************
    * @description Move a Contact to a different Account, and verify: 
    *   the contact is updated with the new account's default address if appropriate
    */
    static testmethod void testGetsDefaultAddressHHToHH() {
        if (strTestOnly != '*' && strTestOnly != 'testGetsDefaultAddressHHToHH') return;
        testGetsDefaultAddress(CAO_Constants.HH_ACCOUNT_TYPE, CAO_Constants.HH_ACCOUNT_TYPE);
    }

    /*******************************************************************************************************
    * @description Move a Contact to a different Account, and verify: 
    *   the contact is updated with the new account's default address if appropriate
    */
    static testmethod void testGetsDefaultAddressOne2OneToOne2One() {
        if (strTestOnly != '*' && strTestOnly != 'testGetsDefaultAddressOne2OneToOne2One') return;
        testGetsDefaultAddress(CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE, CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE);
    }

    /*******************************************************************************************************
    * @description Move a Contact to a different Account, and verify: 
    *   the contact is updated with the new account's default address if appropriate
    */
    static testmethod void testGetsDefaultAddressOrgToHH() {
        if (strTestOnly != '*' && strTestOnly != 'testGetsDefaultAddressOrgToHH') return;
        testGetsDefaultAddress(null, CAO_Constants.HH_ACCOUNT_TYPE);
    }

    /*******************************************************************************************************
    * @description Move a Contact to a different Account, and verify: 
    *   the contact is updated with the new account's default address if appropriate
    */
    static testmethod void testGetsDefaultAddressHHToOrg() {
        if (strTestOnly != '*' && strTestOnly != 'testGetsDefaultAddressHHToOrg') return;
        testGetsDefaultAddress(CAO_Constants.HH_ACCOUNT_TYPE, null);
    }

    /*******************************************************************************************************
    * @description Move a Contact to a different Account, and verify: 
    *   the contact is updated with the new account's default address if appropriate
    */
    static testmethod void testGetsDefaultAddressOrgToOne2One() {
        if (strTestOnly != '*' && strTestOnly != 'testGetsDefaultAddressOrgToOne2One') return;
        testGetsDefaultAddress(null, CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE);
    }

    /*******************************************************************************************************
    * @description Move a Contact to a different Account, and verify: 
    *   the contact is updated with the new account's default address if appropriate
    */
    static testmethod void testGetsDefaultAddressOne2OneToOrg() {
        if (strTestOnly != '*' && strTestOnly != 'testGetsDefaultAddressOne2OneToOrg') return;
        testGetsDefaultAddress(CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE, null);
    }
    
    /*******************************************************************************************************
    * @description Move a Contact to a different Account, and verify: 
    *   the contact is updated with the new account's default address if appropriate
    */
    static void testGetsDefaultAddress(string accTypeSrc, string accTypeDst) {
        
        TDTM_DefaultConfig.startTestDataInit();
        
        // create our source account and contacts
        UTIL_UnitTestData_TEST.createAccountContactTestData(accTypeSrc, 1, 2, 1);
        Account accSrc = UTIL_UnitTestData_TEST.listAccT[0];
        list<Contact> listConSrc = UTIL_UnitTestData_TEST.listConT;  
        
        // create our destination account and contacts
        UTIL_UnitTestData_TEST.createAccountContactTestData(accTypeDst, 1, 2, 3);
        Account accDst = UTIL_UnitTestData_TEST.listAccT[0];
        list<Contact> listConDst = UTIL_UnitTestData_TEST.listConT;        
        
        // move the first contact in the first account to the other account.
        Contact conSrc = new Contact(Id=listConSrc[0].Id);
        conSrc.AccountId = accDst.Id;
        TDTM_DefaultConfig.stopTestDataInit();
        Test.startTest();
        update conSrc;
        Test.stopTest();

        // reload our contacts and accounts
        list<Account> listAcc = new list<Account>([select Id, Name, npo02__Formal_Greeting__c, npo02__Informal_Greeting__c, 
            BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry 
            from Account order by Id]);
        list<Contact> listCon = [select Id, Firstname, AccountId, 
            is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry 
            from Contact order by Firstname];
            
        // verify the contacts belong to correct account.
        system.assertEquals(conSrc.Id, listCon[0].Id);
        system.assertEquals(accSrc.Id, listAcc[0].Id);
        system.assertEquals(accDst.Id, listAcc[1].Id);
        system.assertEquals(listCon[0].AccountId, listAcc[1].Id);
        system.assertEquals(listCon[1].AccountId, listAcc[0].Id);
        system.assertEquals(listCon[2].AccountId, listAcc[1].Id);
        system.assertEquals(listCon[3].AccountId, listAcc[1].Id);
                    
        // load any addresses
        list<Address__c> listAddr = [select Id, Household_Account__c, Default_Address__c, 
            MailingStreet__c, MailingCity__c, MailingState__c from Address__c];
            
        // verify the addresses and that the contact's address is updated as appropriate
        if (accTypeSrc == CAO_Constants.HH_ACCOUNT_TYPE && accTypeDst == CAO_Constants.HH_ACCOUNT_TYPE) {
            system.assertEquals(4, listAddr.size());
            system.assertNotEquals(listCon[0].MailingStreet, listAcc[0].BillingStreet);
            system.assertEquals(listCon[0].MailingStreet, listAcc[1].BillingStreet);
        }
        else if (accTypeSrc == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE && accTypeDst == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE) {
            system.assertEquals(0, listAddr.size());
            system.assertNotEquals(listCon[0].MailingStreet, listAcc[0].BillingStreet);
            system.assertEquals(listCon[0].MailingStreet, listAcc[1].BillingStreet);
        }
        else if (accTypeSrc == null && accTypeDst == CAO_Constants.HH_ACCOUNT_TYPE) {
            system.assertEquals(2, listAddr.size());
            system.assertNotEquals(listCon[0].MailingStreet, listAcc[0].BillingStreet);
            system.assertEquals(listCon[0].MailingStreet, listAcc[1].BillingStreet);
        }
        else if (accTypeSrc == CAO_Constants.HH_ACCOUNT_TYPE && accTypeDst == null) {
            system.assertEquals(2, listAddr.size());
            // I haven't been able to consistently predict which addresss from con1 or con2 will get put in the account originally.
            //system.assertEquals(listCon[0].MailingStreet, listAcc[0].BillingStreet);
            system.assertNotEquals(listCon[0].MailingStreet, listAcc[1].BillingStreet);
        }
        else if (accTypeSrc == null && accTypeDst == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE) {
            system.assertEquals(0, listAddr.size());
            system.assertNotEquals(listCon[0].MailingStreet, listAcc[0].BillingStreet);
            system.assertEquals(listCon[0].MailingStreet, listAcc[1].BillingStreet);
        }
        else if (accTypeSrc == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE && accTypeDst == null) {
            system.assertEquals(0, listAddr.size());
            system.assertNotEquals(listCon[0].MailingStreet, listAcc[0].BillingStreet);
            system.assertNotEquals(listCon[0].MailingStreet, listAcc[1].BillingStreet);
        }
        
    }

    /*******************************************************************************************************
    * @description Move a Contact with an Address Override to a different Account, and verify: 
    *   the Address Override is copied to the new Account
    *   the Contact's Address Override lookup is updated
    *   the Contact's mailing address matches the override
    */
    static testMethod void testAddressOverride() {
        if (strTestOnly != '*' && strTestOnly != 'testAddressOverride') return;
        
        integer cHH = 2;
        integer cCon = 2;
            
        // this creates 2 Household Accounts, each with 2 Contacts, and a default Address for each HH
        ADDR_Addresses_TEST.createHHTestData(cHH, cCon);
        
        Contact con = ADDR_Addresses_TEST.listConT[0];
        
        // create their own address override
        con.MailingStreet = 'street override';
        con.MailingCity = 'city override';
        con.is_Address_Override__c = true;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        update con;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        
        // move the first contact in the first account to the other account.
        con.AccountId = ADDR_Addresses_TEST.listAccT[1].Id;
        Test.startTest();
        update con;
        Test.stopTest();

        // reload our contacts and accounts
        list<Account> listAcc = new list<Account>([select Id, Name, npo02__Formal_Greeting__c, npo02__Informal_Greeting__c, 
            BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry 
            from Account order by Id]);
        list<Contact> listCon = [select Id, Firstname, AccountId, 
            is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry 
            from Contact order by Firstname];
            
        // verify the contacts belong to correct account.
        system.assertEquals(ADDR_Addresses_TEST.listConT[0].Id, listCon[0].Id);
        system.assertEquals(ADDR_Addresses_TEST.listAccT[0].Id, listAcc[0].Id);
        system.assertEquals(listCon[0].AccountId, listAcc[1].Id);
        system.assertEquals(listCon[1].AccountId, listAcc[0].Id);
        system.assertEquals(listCon[2].AccountId, listAcc[1].Id);
        system.assertEquals(listCon[3].AccountId, listAcc[1].Id);
        
        // verify we have the expected number of addresses (2 + override we created + override copied to new account)
        system.assertEquals(4, [select count() from Address__c]);
        system.assertEquals(2, [select count() from Address__c where Default_Address__c = true]);
        system.assertEquals(1, [select count() from Contact where is_Address_Override__c = true]);
        
        // verify the correct contact has the override
        system.assertEquals(true, listCon[0].is_Address_Override__c);
        Address__c addr = [select Id, Household_Account__c, MailingStreet__c, MailingCity__c from Address__c where Id = :listCon[0].Current_Address__c];
        
        // verify the address was moved to the new acccount
        system.assertEquals(listCon[0].AccountId, addr.Household_Account__c);
        system.assertNotEquals(ADDR_Addresses_TEST.listConT[0].Current_Address__c, listCon[0].Current_Address__c);
            
        // verify the contact's address is still the address override
        system.assertEquals(listCon[0].MailingStreet, addr.MailingStreet__c);
        system.assertEquals(listCon[0].MailingCity, addr.MailingCity__c);
    }
}