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
* @date 2013
* @group Address Management
* @description Tests specific to Address Management. 
*/

@isTest
public with sharing class ADDR_Addresses_TEST2 {
    /*********************************************************************************************************
    * @description if you only want to run one test in this class, fill in its name here.
    * if you want to run all tests, then use '*'
    */
    private static string strTestOnly = '*';

    /*********************************************************************************************************
    * @description Holds the number of Households to create in tests.
    */
    static integer cHH = 2;

    /*********************************************************************************************************
    * @description Holds the number of Contacts per Household to create in tests.
    */
    static integer cCon = 2;    

    /*********************************************************************************************************
    * @description The list of created test Contacts.
    */
    private static list<Contact> listConT;

    /*********************************************************************************************************
    * @description The list of created test Accounts.
    */
    private static list<Account> listAccT;            

    /*********************************************************************************************************
    * @description The list of created test Addresses.
    */
    private static list<Address__c> listAddrT;

    /*********************************************************************************************************
    * @description
    * utility to set up common test data for the Address tests.
    *    - this will create & insert N household Accounts, each with M contacts
    *    - also will create a Default Addresses for each HH Account
    *    - will also setup the default Account Processor to be HH Accounts
    * @param cHHT the number of Household Accounts to create
    * @param cConT the number of Contacts to create per Household
    * @return  void
    **********************************************************************************************************/
    private static void createHHTestData(integer cHHT, integer cConT) {
        ADDR_Addresses_TEST.createHHTestData(cHHT, cConT);
        listConT = ADDR_Addresses_TEST.listConT;
        listAccT = ADDR_Addresses_TEST.listAccT;
        listAddrT = ADDR_Addresses_TEST.listAddrT;
    }

    /*********************************************************************************************************
    * @description Initializes (but not inserts) N new Address objects.
    * @param cAddr the number of Address objects to create
    * @return  void
    **********************************************************************************************************/
    private static void initTestAddr(integer cAddr) {
        ADDR_Addresses_TEST.initTestAddr(cAddr);
        listAddrT = ADDR_Addresses_TEST.listAddrT;
    }
    
    /*********************************************************************************************************
    @description
        update seasonal addresses to current, on HH w/ existing default addresses  
    verify:
        contact's && hh address updated to seasonal address
        old default addresses still marked default
    **********************************************************************************************************/            
    static testMethod void updateSeasonalAddr() {
        if (strTestOnly != '*' && strTestOnly != 'updateSeasonalAddr') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);
        
        // create additional seasonal addresses not current
        initTestAddr(cHH);
        for (integer i = 0; i < cHH; i++) {
            listAddrT[i].Household_Account__c = listAccT[i].Id;
            listAddrT[i].Default_Address__c = false;
            listAddrT[i].Seasonal_Start_Month__c = string.valueOf(system.today().addMonths(1).month());
            listAddrT[i].Seasonal_Start_Day__c = '1';
            listAddrT[i].Seasonal_End_Month__c = string.valueOf(system.today().addMonths(2).month());
            listAddrT[i].Seasonal_End_Day__c = '28';
            listAddrT[i].MailingStreet__c = 'New Seasonal Street' + i;
            listAddrT[i].MailingCity__c = 'New Seasonal City' + i;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        insert listAddrT;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        
        // now make them current
        for (integer i = 0; i < cHH; i++) {
            listAddrT[i].Seasonal_Start_Month__c = string.valueOf(system.today().addMonths(-1).month());
        }
        Test.startTest();
        update listAddrT;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;                
        Test.stopTest();
        
        // verify that the HH and Contacts share the same address and it's new!
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        system.assertEquals(cHH * cCon, listCon.size());
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, ADDR_Addresses_TEST.isMatchAddressAccCon(acc, con));
            system.assert(con.MailingStreet.contains('New Seasonal Street'));
            system.assert(con.MailingCity.contains('New Seasonal City'));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
        
        // verify the previous addresses still are Default
        list<Address__c> listAddr = [select Id, Default_Address__c, MailingStreet__c, Household_Account__c, Latest_Start_Date__c, Latest_End_Date__c from Address__c];
        system.assertEquals(cHH * 2, listAddr.size());
        for (Address__c addr : listAddr) {
            boolean fSeasonal = (addr.MailingStreet__c.contains('New Seasonal Street'));
            system.assertEquals(!fSeasonal, addr.Default_Address__c);
        }        
    }

    /*********************************************************************************************************
    @description
        delete current seasonal addresses  
    verify:
        contact's && hh address updated to default
    **********************************************************************************************************/            
    static testMethod void deleteSeasonalAddr() {
        if (strTestOnly != '*' && strTestOnly != 'deleteSeasonalAddr') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);
        
        // create additional seasonal addresses current
        initTestAddr(cHH);
        for (integer i = 0; i < cHH; i++) {
            listAddrT[i].Household_Account__c = listAccT[i].Id;
            listAddrT[i].Default_Address__c = false;
            listAddrT[i].Seasonal_Start_Month__c = string.valueOf(system.today().addMonths(-1).month());
            listAddrT[i].Seasonal_Start_Day__c = '1';
            listAddrT[i].Seasonal_End_Month__c = string.valueOf(system.today().addMonths(2).month());
            listAddrT[i].Seasonal_End_Day__c = '28';
            listAddrT[i].MailingStreet__c = 'New Seasonal Street' + i;
            listAddrT[i].MailingCity__c = 'New Seasonal City' + i;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        insert listAddrT;

        // verify that the HH and Contacts share the same address and it's new!
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        system.assertEquals(cHH * cCon, listCon.size());
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, ADDR_Addresses_TEST.isMatchAddressAccCon(acc, con));
            system.assert(con.MailingStreet.contains('New Seasonal Street'));
            system.assert(con.MailingCity.contains('New Seasonal City'));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
        
        // now delete the seasonal addresses
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        delete listAddrT;                
        Test.stopTest();
        
        // verify the default address is restored
        mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        system.assertEquals(cHH * cCon, listCon.size());
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, ADDR_Addresses_TEST.isMatchAddressAccCon(acc, con));
            system.assertEquals(false, con.MailingStreet.contains('New Seasonal Street'));
            system.assertEquals(false, con.MailingCity.contains('New Seasonal City'));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
    }

    /*********************************************************************************************************
    @description
        create multiple seasonal address that overlap with eachother  
    verify:
        overlap detected, seasonal addresses don't save
    **********************************************************************************************************/            
    static testMethod void insertSeasonalAddrWithOverlap() {
        if (strTestOnly != '*' && strTestOnly != 'insertSeasonalAddrWithOverlap') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);
        
        // create additional seasonal addresses not current
        initTestAddr(cHH * 2);
        for (integer i = 0; i < cHH * 2; i++) {
            integer iacc = i < cHH ? i : i-cHH;
            if (math.mod(i, 2) == 0) {
                listAddrT[i].Household_Account__c = listAccT[iacc].Id;
                listAddrT[i].Seasonal_Start_Month__c = string.valueOf(system.today().addMonths(1).month());
                listAddrT[i].Seasonal_Start_Day__c = '1';
                listAddrT[i].Seasonal_End_Month__c = string.valueOf(system.today().addMonths(3).month());
                listAddrT[i].Seasonal_End_Day__c = '28';
            } else {
                listAddrT[i].Household_Account__c = listAccT[iacc-1].Id;
                listAddrT[i].Seasonal_Start_Month__c = string.valueOf(system.today().addMonths(-1).month());
                listAddrT[i].Seasonal_Start_Day__c = '1';
                listAddrT[i].Seasonal_End_Month__c = string.valueOf(system.today().addMonths(2).month());
                listAddrT[i].Seasonal_End_Day__c = '28';            
            }
            listAddrT[i].Default_Address__c = false;
            listAddrT[i].MailingStreet__c = 'New Seasonal Street' + i;
            listAddrT[i].MailingCity__c = 'New Seasonal City' + i;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        try {
            Test.startTest();
            insert listAddrT;
            Test.stopTest();
        } catch (Exception ex) {
            system.assert(ex.getMessage().contains(Label.addrSeasonalOverlap));
            return;
        }
        system.assert(false); //we shouldn't get here!
        
    }

    /*********************************************************************************************************
    @description
        create multiple seasonal address that overlap with eachother  
    verify:
        overlap detected
    **********************************************************************************************************/            
    static testMethod void testSeasonalAddrOverlapLogic() {
        if (strTestOnly != '*' && strTestOnly != 'testSeasonalAddrOverlapLogic') return;
        
        Address__c a1 = new Address__c();
        Address__c a2 = new Address__c();

        // test both increasing ranges, no overlap
        a1.Seasonal_Start_Month__c = '1';
        a1.Seasonal_Start_Day__c = '1';
        a1.Seasonal_End_Month__c = '3';
        a1.Seasonal_End_Day__c = '28';
        a2.Seasonal_Start_Month__c = '4';
        a2.Seasonal_Start_Day__c = '1';
        a2.Seasonal_End_Month__c = '6';
        a2.Seasonal_End_Day__c = '28';
        system.assertEquals(false, ADDR_Addresses_TDTM.isSeasonalOverlap(a1, a2));
        system.assertEquals(false, ADDR_Addresses_TDTM.isSeasonalOverlap(a2, a1));
        
        // test both increasing ranges, with overlap
        a2.Seasonal_Start_Month__c = '2';
        a2.Seasonal_End_Month__c = '6';
        system.assertEquals(true, ADDR_Addresses_TDTM.isSeasonalOverlap(a1, a2));
        system.assertEquals(true, ADDR_Addresses_TDTM.isSeasonalOverlap(a2, a1));
        
        // test one increasing range, one decreasing range, no overlap
        a1.Seasonal_Start_Month__c = '3';
        a1.Seasonal_Start_Day__c = '1';
        a1.Seasonal_End_Month__c = '5';
        a1.Seasonal_End_Day__c = '28';
        a2.Seasonal_Start_Month__c = '11';
        a2.Seasonal_Start_Day__c = '1';
        a2.Seasonal_End_Month__c = '2';
        a2.Seasonal_End_Day__c = '28';
        system.assertEquals(false, ADDR_Addresses_TDTM.isSeasonalOverlap(a1, a2));
        system.assertEquals(false, ADDR_Addresses_TDTM.isSeasonalOverlap(a2, a1));

        // test one increasing range, one decreasing range, with overlap
        a2.Seasonal_Start_Month__c = '11';
        a2.Seasonal_Start_Day__c = '1';
        a2.Seasonal_End_Month__c = '4';
        a2.Seasonal_End_Day__c = '28';
        system.assertEquals(true, ADDR_Addresses_TDTM.isSeasonalOverlap(a1, a2));
        system.assertEquals(true, ADDR_Addresses_TDTM.isSeasonalOverlap(a2, a1));

        // test both decreasing ranges, always overlap
        a1.Seasonal_Start_Month__c = '9';
        a1.Seasonal_Start_Day__c = '1';
        a1.Seasonal_End_Month__c = '2';
        a1.Seasonal_End_Day__c = '28';
        a2.Seasonal_Start_Month__c = '12';
        a2.Seasonal_Start_Day__c = '1';
        a2.Seasonal_End_Month__c = '3';
        a2.Seasonal_End_Day__c = '28';
        system.assertEquals(true, ADDR_Addresses_TDTM.isSeasonalOverlap(a1, a2));
        system.assertEquals(true, ADDR_Addresses_TDTM.isSeasonalOverlap(a2, a1));
        
        // test both increasing ranges in the same month, no overlap
        a1.Seasonal_Start_Month__c = '2';
        a1.Seasonal_Start_Day__c = '1';
        a1.Seasonal_End_Month__c = '2';
        a1.Seasonal_End_Day__c = '10';
        a2.Seasonal_Start_Month__c = '2';
        a2.Seasonal_Start_Day__c = '11';
        a2.Seasonal_End_Month__c = '2';
        a2.Seasonal_End_Day__c = '28';
        system.assertEquals(false, ADDR_Addresses_TDTM.isSeasonalOverlap(a1, a2));
        system.assertEquals(false, ADDR_Addresses_TDTM.isSeasonalOverlap(a2, a1));

        // test both increasing ranges in the same month, with overlap
        a1.Seasonal_Start_Month__c = '2';
        a1.Seasonal_Start_Day__c = '1';
        a1.Seasonal_End_Month__c = '2';
        a1.Seasonal_End_Day__c = '10';
        a2.Seasonal_Start_Month__c = '2';
        a2.Seasonal_Start_Day__c = '10';
        a2.Seasonal_End_Month__c = '2';
        a2.Seasonal_End_Day__c = '28';
        system.assertEquals(true, ADDR_Addresses_TDTM.isSeasonalOverlap(a1, a2));
        system.assertEquals(true, ADDR_Addresses_TDTM.isSeasonalOverlap(a2, a1));

        // test one increasing range, one decreasing range in the same month, no overlap
        a1.Seasonal_Start_Month__c = '2';
        a1.Seasonal_Start_Day__c = '1';
        a1.Seasonal_End_Month__c = '2';
        a1.Seasonal_End_Day__c = '10';
        a2.Seasonal_Start_Month__c = '2';
        a2.Seasonal_Start_Day__c = '11';
        a2.Seasonal_End_Month__c = '1';
        a2.Seasonal_End_Day__c = '28';
        system.assertEquals(false, ADDR_Addresses_TDTM.isSeasonalOverlap(a1, a2));
        system.assertEquals(false, ADDR_Addresses_TDTM.isSeasonalOverlap(a2, a1));

        // test one increasing range, one decreasing range in the same month, with overlap
        a1.Seasonal_Start_Month__c = '2';
        a1.Seasonal_Start_Day__c = '1';
        a1.Seasonal_End_Month__c = '2';
        a1.Seasonal_End_Day__c = '10';
        a2.Seasonal_Start_Month__c = '2';
        a2.Seasonal_Start_Day__c = '11';
        a2.Seasonal_End_Month__c = '2';
        a2.Seasonal_End_Day__c = '1';
        system.assertEquals(true, ADDR_Addresses_TDTM.isSeasonalOverlap(a1, a2));
        system.assertEquals(true, ADDR_Addresses_TDTM.isSeasonalOverlap(a2, a1));
    }

    /*********************************************************************************************************
    @description
        create multiple seasonal address that overlap existing seasonal addresses  
    verify:
        overlap detected, seasonal addresses don't save
    **********************************************************************************************************/            
    static testMethod void insertSeasonalAddrOverlapExisting() {
        if (strTestOnly != '*' && strTestOnly != 'insertSeasonalAddrOverlapExisting') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);
        
        // create additional seasonal addresses not current
        initTestAddr(cHH);
        Date dtTest = Date.newInstance(system.today().year(), 6, 1);
        for (integer i = 0; i < cHH; i++) {
            listAddrT[i].Household_Account__c = listAccT[i].Id;
            listAddrT[i].Seasonal_Start_Month__c = string.valueOf(dtTest.addMonths(1).month());
            listAddrT[i].Seasonal_Start_Day__c = '1';
            listAddrT[i].Seasonal_End_Month__c = string.valueOf(dtTest.addMonths(3).month());
            listAddrT[i].Seasonal_End_Day__c = '28';
            listAddrT[i].Default_Address__c = false;
            listAddrT[i].MailingStreet__c = 'New Seasonal Street' + i;
            listAddrT[i].MailingCity__c = 'New Seasonal City' + i;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        insert listAddrT;
        
        // create additional seasonal addresses that overlap existing seasonal addresses
        initTestAddr(cHH);
        for (integer i = 0; i < cHH; i++) {
            listAddrT[i].Household_Account__c = listAccT[i].Id;
            listAddrT[i].Seasonal_Start_Month__c = string.valueOf(dtTest.addMonths(2).month());
            listAddrT[i].Seasonal_Start_Day__c = '1';
            listAddrT[i].Seasonal_End_Month__c = string.valueOf(dtTest.addMonths(4).month());
            listAddrT[i].Seasonal_End_Day__c = '28';
            listAddrT[i].Default_Address__c = false;
            listAddrT[i].MailingStreet__c = 'New Seasonal Street' + i;
            listAddrT[i].MailingCity__c = 'New Seasonal City' + i;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        try {
            Test.startTest();
            insert listAddrT;
            Test.stopTest();
        } catch (Exception ex) {
            system.assert(ex.getMessage().contains(Label.addrSeasonalOverlap));
            return;
        }
        system.assert(false); //we shouldn't get here!
        
    }

    /*********************************************************************************************************
    @description
        create multiple partial seasonal address  
    verify:
        partial seasonal detected, seasonal addresses don't save
    **********************************************************************************************************/            
    static testMethod void insertPartialSeasonalAddr() {
        if (strTestOnly != '*' && strTestOnly != 'insertPartialSeasonalAddr') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);
        
        // create additional seasonal addresses not current
        initTestAddr(cHH);
        for (integer i = 0; i < cHH; i++) {
            listAddrT[i].Household_Account__c = listAccT[i].Id;
            listAddrT[i].Seasonal_Start_Month__c = string.valueOf(system.today().addMonths(1).month());
            listAddrT[i].Seasonal_Start_Day__c = '1';
            listAddrT[i].Default_Address__c = false;
            listAddrT[i].MailingStreet__c = 'New Seasonal Street' + i;
            listAddrT[i].MailingCity__c = 'New Seasonal City' + i;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        try {
            Test.startTest();
            insert listAddrT;
            Test.stopTest();
        } catch (Exception ex) {
            system.assert(ex.getMessage().contains(Label.addrSeasonalPartial));
            return;
        }
        system.assert(false); //we shouldn't get here!
        
    }

    /*********************************************************************************************************
    @description
        schedule a seasonal address update 
    verify:
        contact's && hh address matches new seasonal
        old default addresses still marked default
    **********************************************************************************************************/            
    static testMethod void scheduleSeasonalAddrUpdate() {
        if (strTestOnly != '*' && strTestOnly != 'scheduleSeasonalAddrUpdate') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);
        
        // create additional addresses
        initTestAddr(cHH);
        for (integer i = 0; i < cHH; i++) {
            listAddrT[i].Household_Account__c = listAccT[i].Id;
            listAddrT[i].Default_Address__c = false;
            listAddrT[i].Seasonal_Start_Month__c = string.valueOf(system.today().month());
            listAddrT[i].Seasonal_Start_Day__c = string.valueOf(system.today().day());
            listAddrT[i].Seasonal_End_Month__c = string.valueOf(system.today().addMonths(1).month());
            listAddrT[i].Seasonal_End_Day__c = '28';
            listAddrT[i].MailingStreet__c = 'New Seasonal Street' + i;
            listAddrT[i].MailingCity__c = 'New Seasonal City' + i;
        }
        
        // Hack!  by setting our trigger handler as run,
        // we can insert the new seasonal addresses but not have them processed.
        // this way we can test the scheduled job!
        ADDR_Addresses_TDTM.hasRunAddrTrigger = true;
        insert listAddrT;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;

        // run the scheduled batch directly
        Test.startTest();
        Database.executeBatch(new ADDR_Seasonal_SCHED(), 10);
        Test.stopTest();
        
        // verify that the HH and Contacts share the same address and it's new!
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        system.assertEquals(cHH * cCon, listCon.size());
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, ADDR_Addresses_TEST.isMatchAddressAccCon(acc, con));
            system.assert(con.MailingStreet.contains('New Seasonal Street'));
            system.assert(con.MailingCity.contains('New Seasonal City'));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
        
        // verify the previous addresses still are Default
        list<Address__c> listAddr = [select Id, Default_Address__c, MailingStreet__c, Household_Account__c, Latest_Start_Date__c, Latest_End_Date__c from Address__c];
        system.assertEquals(cHH * 2, listAddr.size());
        for (Address__c addr : listAddr) {
            boolean fSeasonal = (addr.MailingStreet__c.contains('New Seasonal Street'));
            system.assertEquals(!fSeasonal, addr.Default_Address__c);
        }        
    }

    /*********************************************************************************************************
    @description
        reset a seasonal address thru the scheduler 
    verify:
        contact's && hh address matches new seasonal
        old default addresses still marked default
    **********************************************************************************************************/            
    static testMethod void scheduleSeasonalAddrReset() {
        if (strTestOnly != '*' && strTestOnly != 'scheduleSeasonalAddrReset') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);
        
        // create additional addresses
        initTestAddr(cHH);
        for (integer i = 0; i < cHH; i++) {
            listAddrT[i].Household_Account__c = listAccT[i].Id;
            listAddrT[i].Default_Address__c = false;
            listAddrT[i].Seasonal_Start_Month__c = string.valueOf(system.today().addMonths(-2).month());
            listAddrT[i].Seasonal_Start_Day__c = '1';
            listAddrT[i].Seasonal_End_Month__c = string.valueOf(system.today().addMonths(1).month());
            listAddrT[i].Seasonal_End_Day__c = '1';
            listAddrT[i].MailingStreet__c = 'New Seasonal Street' + i;
            listAddrT[i].MailingCity__c = 'New Seasonal City' + i;
        }
        
        // this should update the hh and contacts to the seasonal addresses
        insert listAddrT;
        
        // verify that the HH and Contacts share the same address and it's new!
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        system.assertEquals(cHH * cCon, listCon.size());
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, ADDR_Addresses_TEST.isMatchAddressAccCon(acc, con));
            system.assert(con.MailingStreet.contains('New Seasonal Street'));
            system.assert(con.MailingCity.contains('New Seasonal City'));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }

        // now let's update the seasonal addresses to no longer be active, but disable triggers for now
        // this way we can test the scheduled job!
        for (Address__c addr : listAddrT) {
            addr.Seasonal_End_Month__c = string.valueOf(system.today().addMonths(-1).month());
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = true;
        update listAddrT;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;

        // run the scheduled batch directly
        Test.startTest();
        Database.executeBatch(new ADDR_Seasonal_SCHED(), 10);
        Test.stopTest();
        
        // verify that the HH and Contacts share the same address and it's the old default!
        mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        system.assertEquals(cHH * cCon, listCon.size());
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, ADDR_Addresses_TEST.isMatchAddressAccCon(acc, con));
            system.assert(con.MailingStreet.startsWith('Street'));
            system.assert(con.MailingCity.startsWith('City'));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
        
        // verify the previous addresses still are Default
        list<Address__c> listAddr = [select Id, Default_Address__c, MailingStreet__c, Household_Account__c, Latest_Start_Date__c, Latest_End_Date__c from Address__c];
        system.assertEquals(cHH * 2, listAddr.size());
        for (Address__c addr : listAddr) {
            boolean fSeasonal = (addr.MailingStreet__c.contains('New Seasonal Street'));
            system.assertEquals(!fSeasonal, addr.Default_Address__c);
        }        
    }

    /*********************************************************************************************************
    @description
        create N new duplicate Addresses from Account  
    verify:
        duplicates not created
    **********************************************************************************************************/            
    static testMethod void newDupeAccountAddr() {
        if (strTestOnly != '*' && strTestOnly != 'newDupeAccountAddr') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);

        // create additional addresses thru the account.
        for (integer i = 0; i < cHH; i++) {
            listAccT[i].BillingStreet = listAddrT[i].MailingStreet__c;
            listAccT[i].BillingCity = listAddrT[i].MailingCity__c;
            listAccT[i].BillingState = listAddrT[i].MailingState__c;
            listAccT[i].BillingPostalCode = listAddrT[i].MailingPostalCode__c;
            listAccT[i].BillingCountry = listAddrT[i].MailingCountry__c;
        }        
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        update listAccT;
        Test.stopTest();
        
        // verify that we don't have new Addresses
        list<Address__c> listAddr = [select Id from Address__c];
        system.assertEquals(cHH, listAddr.size());
    }

    /*********************************************************************************************************
    @description
        create N new duplicate Addresses from Contact  
    verify:
        duplicates not created
    **********************************************************************************************************/            
    static testMethod void newDupeContactAddr() {
        if (strTestOnly != '*' && strTestOnly != 'newDupeContactAddr') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);

        // create additional addresses thru the contact.
        for (integer i = 0; i < cHH; i++) {
            for (integer j = 0; j < cCon; j++) {
                listConT[(i * cHH) + j].MailingStreet = listAddrT[i].MailingStreet__c;
                listConT[(i * cHH) + j].MailingCity = listAddrT[i].MailingCity__c;
                listConT[(i * cHH) + j].MailingState = listAddrT[i].MailingState__c;
                listConT[(i * cHH) + j].MailingPostalCode = listAddrT[i].MailingPostalCode__c;
                listConT[(i * cHH) + j].MailingCountry = listAddrT[i].MailingCountry__c;
            }
        }        
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        update listConT;
        Test.stopTest();
        
        // verify that we don't have new Addresses
        list<Address__c> listAddr = [select Id from Address__c];
        system.assertEquals(cHH, listAddr.size());
    }

    /*********************************************************************************************************
    @description
        create N new duplicate Addresses from Account, with non-default existing  
    verify:
        duplicates not created
        found match becomes Default
    **********************************************************************************************************/            
    static testMethod void newDupeAccountAddrNonDefault() {
        if (strTestOnly != '*' && strTestOnly != 'newDupeAccountAddrNonDefault') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);

         // create additional non-default addresses
        initTestAddr(cHH);
        for (integer i = 0; i < cHH; i++) {
            listAddrT[i].Household_Account__c = listAccT[i].Id;
            listAddrT[i].Default_Address__c = false;
            listAddrT[i].MailingStreet__c = 'New Street' + i;
            listAddrT[i].MailingCity__c = 'New City' + i;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        insert listAddrT;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;        
 
        // create additional addresses thru the account.
        for (integer i = 0; i < cHH; i++) {
            listAccT[i].BillingStreet = listAddrT[i].MailingStreet__c;
            listAccT[i].BillingCity = listAddrT[i].MailingCity__c;
            listAccT[i].BillingState = listAddrT[i].MailingState__c;
            listAccT[i].BillingPostalCode = listAddrT[i].MailingPostalCode__c;
            listAccT[i].BillingCountry = listAddrT[i].MailingCountry__c;
        }        
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        update listAccT;
        Test.stopTest();
        
        // verify that the HH and Contacts share the same address and it's new!
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        system.assertEquals(cHH * cCon, listCon.size());
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, ADDR_Addresses_TEST.isMatchAddressAccCon(acc, con));
            system.assert(con.MailingStreet.contains('New Street'));
            system.assert(con.MailingCity.contains('New City'));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
        
        // verify the new addresses still are Default and we don't have too many
        list<Address__c> listAddr = [select Id, Default_Address__c, MailingStreet__c, Household_Account__c, Latest_Start_Date__c, Latest_End_Date__c from Address__c];
        system.assertEquals(cHH * 2, listAddr.size());
        for (Address__c addr : listAddr) {
            boolean isNew = (addr.MailingStreet__c.contains('New Street'));
            system.assertEquals(isNew, addr.Default_Address__c);
        }        
 
    }

    /*********************************************************************************************************
    @description
        create N new duplicate Addresses from Contact, with non-default existing  
    verify:
        duplicates not created
        found match becomes Default
    **********************************************************************************************************/            
    static testMethod void newDupeContactAddrNonDefault() {
        if (strTestOnly != '*' && strTestOnly != 'newDupeContactAddrNonDefault') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);

         // create additional non-default addresses
        initTestAddr(cHH);
        for (integer i = 0; i < cHH; i++) {
            listAddrT[i].Household_Account__c = listAccT[i].Id;
            listAddrT[i].Default_Address__c = false;
            listAddrT[i].MailingStreet__c = 'New Street' + i;
            listAddrT[i].MailingCity__c = 'New City' + i;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        insert listAddrT;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;        
 
        // create additional addresses thru the contact.
        for (integer i = 0; i < cHH; i++) {
            for (integer j = 0; j < cCon; j++) {
                listConT[(i * cHH) + j].MailingStreet = listAddrT[i].MailingStreet__c;
                listConT[(i * cHH) + j].MailingCity = listAddrT[i].MailingCity__c;
                listConT[(i * cHH) + j].MailingState = listAddrT[i].MailingState__c;
                listConT[(i * cHH) + j].MailingPostalCode = listAddrT[i].MailingPostalCode__c;
                listConT[(i * cHH) + j].MailingCountry = listAddrT[i].MailingCountry__c;
            }
        }        
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        update listConT;
        Test.stopTest();
        
        // verify that the HH and Contacts share the same address and it's new!
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        system.assertEquals(cHH * cCon, listCon.size());
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, ADDR_Addresses_TEST.isMatchAddressAccCon(acc, con));
            system.assert(con.MailingStreet.contains('New Street'));
            system.assert(con.MailingCity.contains('New City'));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
        
        // verify the new addresses still are Default and we don't have too many
        list<Address__c> listAddr = [select Id, Default_Address__c, MailingStreet__c, Household_Account__c, Latest_Start_Date__c, Latest_End_Date__c from Address__c];
        system.assertEquals(cHH * 2, listAddr.size());
        for (Address__c addr : listAddr) {
            boolean isNew = (addr.MailingStreet__c.contains('New Street'));
            system.assertEquals(isNew, addr.Default_Address__c);
        }        
 
    }

    /*********************************************************************************************************
    @description
        update contacts' mailing address when they have an address override
    verify:
        new non-default Address created
        contact override updated to new address
        only the contact gets the new Address
    **********************************************************************************************************/            
    static testMethod void updateConAddrWithOverride() {
        if (strTestOnly != '*' && strTestOnly != 'updateConAddrWithOverride') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);

        // create additional addresses
        initTestAddr(cHH);
        for (integer i = 0; i < cHH; i++) {
            listAddrT[i].Household_Account__c = listAccT[i].Id;
            listAddrT[i].Default_Address__c = false;
            listAddrT[i].MailingStreet__c = 'override' + i;
            listAddrT[i].MailingCity__c = 'override' + i;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        insert listAddrT;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        
        // set the contacts' address overrides
        list<Contact> listCon = new list<Contact>();
        for (integer i = 0; i < cHH; i++) {
            Contact con = listConT[i*cHH + i];
            con.Current_Address__c = listAddrT[i].Id;
            con.is_Address_Override__c = true;
            listCon.add(con);
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        update listCon;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
 
        // modify some of the contact addresses directly
        for (Contact con : listCon) {
            con.MailingStreet = 'Direct Street Edit';
            con.MailingCity = 'Direct City Edit';
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        update listCon;
        Test.stopTest();
        
        // verify that the HH and Contacts share the same address
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            if (con.is_Address_Override__c == false) {
                system.assertNotEquals(null, con.Current_Address__c);
                system.assertEquals(true, ADDR_Addresses_TEST.isMatchAddressAccCon(acc, con));
                system.assert(!con.MailingStreet.contains('Direct Street Edit'));
                system.assert(!con.MailingCity.contains('Direct City Edit'));
                system.assert(!con.MailingStreet.contains('override'));
                system.assert(!con.MailingCity.contains('override'));
            } else {
                system.assertEquals(false, ADDR_Addresses_TEST.isMatchAddressAccCon(acc, con));
                system.assert(con.MailingStreet.contains('Direct Street Edit'));
                system.assert(con.MailingCity.contains('Direct City Edit'));
                system.assertEquals(true, con.is_Address_Override__c);
                system.assertNotEquals(null, con.Current_Address__c);
            }
        }
 
         // verify the new overrides are not marked default, and that the contact points to them
        list<Address__c> listAddr = [select Id, Default_Address__c, MailingStreet__c, Household_Account__c from Address__c];
        system.assertEquals(cHH * 3, listAddr.size());
        for (Address__c addr : listAddr) {
            boolean fOverride = (addr.MailingStreet__c.contains('Direct Street Edit'));
            if (fOverride) {
                system.assertEquals(false, addr.Default_Address__c);
                boolean isSetOnContact = false;
                for (Contact con : listCon) {
                    if (con.is_Address_Override__c && con.Current_Address__c == addr.Id)
                        isSetOnContact = true;
                }
                system.assertEquals(true, isSetOnContact);
            }
        }                
    }

    /*********************************************************************************************************
    @description
        update contact's address override to an address in another account
    verify:
        new non-default Address created
        contact override updated to new address
        only the contact gets the new Address
    **********************************************************************************************************/            
    static testMethod void updateConAddrWithExternalOverride() {
        if (strTestOnly != '*' && strTestOnly != 'updateConAddrWithExternalOverride') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);

        // create additional addresses
        initTestAddr(cHH);
        for (integer i = 0; i < cHH; i++) {
            listAddrT[i].Household_Account__c = listAccT[i].Id;
            listAddrT[i].Default_Address__c = false;
            listAddrT[i].MailingStreet__c = 'override' + i;
            listAddrT[i].MailingCity__c = 'override' + i;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        insert listAddrT;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        
        // set the contacts' address overrides to an address in a different household
        list<Contact> listCon = new list<Contact>();
        for (integer i = 0; i < cHH; i++) {
            Contact con = listConT[i*cHH];  // first contact in the household
            con.Current_Address__c = listAddrT[i == cHH-1 ? 0 : i + 1].Id;  // address in the next household
            con.is_Address_Override__c = true;
            listCon.add(con);
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        update listCon;
        Test.stopTest();
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
 
        // verify that the HH and Contacts share the same address
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        list<Address__c> listAddr = [select Id, Default_Address__c, MailingStreet__c, MailingCity__c, Household_Account__c from Address__c];
        system.debug('****DJH: mapAccIdAcc: ' + mapAccIdAcc);
        system.debug('****DJH: listCon: ' + listCon);
        system.debug('****DJH: listAddr: ' + listAddr);
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            if (con.is_Address_Override__c == false) {
                system.assertNotEquals(null, con.Current_Address__c);
                system.assertEquals(true, ADDR_Addresses_TEST.isMatchAddressAccCon(acc, con));
                system.assert(!con.MailingStreet.contains('override'));
                system.assert(!con.MailingCity.contains('override'));
            } else {
                //system.assertEquals(false, ADDR_Addresses_TEST.isMatchAddressAccCon(acc, con));
                system.assert(con.MailingStreet.contains('override'));
                system.assert(con.MailingCity.contains('override'));
                system.assertEquals(true, con.is_Address_Override__c);
                system.assertNotEquals(null, con.Current_Address__c);
            }
        }
 
         // verify the overrides were copied to their new households
        system.assertEquals(cHH * 3, listAddr.size());
        for (Address__c addr : listAddr) {
            boolean fOverride = (addr.MailingStreet__c.contains('override'));
            if (fOverride) {
                system.assertEquals(false, addr.Default_Address__c);
                boolean isSetOnContact = false;
                for (Contact con : listCon) {
                    if (con.is_Address_Override__c && con.Current_Address__c == addr.Id)
                        system.assertEquals(con.AccountId, addr.Household_Account__c);
                }
            }
        }                
    }

    /*********************************************************************************************************
    @description
        insert new contacts with no addresses to existing households that have default addresses
    verify:
        new contacts get the default hh address
    **********************************************************************************************************/            
    static testMethod void insertConWithNoAddrToExistingHH() {
        if (strTestOnly != '*' && strTestOnly != 'insertConWithNoAddrToExistingHH') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);
        
        // insert new contacts to the HH without any addresses
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(cHH);
        for (integer i = 0; i < cHH; i++) {
            listCon[i].AccountId = listAccT[i].Id;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        insert listCon;         
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.stopTest();
        
        // verify that the HH and Contacts share the same address
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, ADDR_Addresses_TEST.isMatchAddressAccCon(acc, con));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
    }

    /*********************************************************************************************************
    @description
        insert new contacts with addresses to existing households that have default addresses
    verify:
        all contacts and hh get the new default hh address from the new contacts
    **********************************************************************************************************/            
    static testMethod void insertConWithAddrToExistingHH() {
        if (strTestOnly != '*' && strTestOnly != 'insertConWithAddrToExistingHH') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);
        
        // insert new contacts to the HH with new addresses
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(cHH);
        for (integer i = 0; i < cHH; i++) {
            listCon[i].AccountId = listAccT[i].Id;
            listCon[i].MailingStreet = 'New Mailing Street' + i;
            listCon[i].MailingCity = 'New Mailing City' + i;
            listCon[i].MailingState = 'California';
            listCon[i].MailingCountry = 'United States';
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        insert listCon;         
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.stopTest();
        
        // verify that the HH and Contacts share the same address
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, ADDR_Addresses_TEST.isMatchAddressAccCon(acc, con));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
            system.assert(con.MailingStreet.contains('New Mailing Street'));
            system.assert(con.MailingCity.contains('New Mailing City'));
        }
    }

    /*********************************************************************************************************
    @description
        insert new contacts with default addresses to existing households that have default addresses       
        (this is to mimic the behavior of doing New Contact from the HH Account, in which case Salesforce
        brings up the new contact page in edit mode, with the account's billing address copied into the 
        contact's mailing address fields).
    verify:
        no new addresses created
        new contacts point at the default address
    **********************************************************************************************************/            
    static testMethod void insertConWithDefaultAddrToExistingHH() {
        if (strTestOnly != '*' && strTestOnly != 'insertConWithDefaultAddrToExistingHH') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);
        
        // reload accounts to get their default addresses
        listAccT = [select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account];
        system.assertEquals(cHH, listAccT.size());
        
        // insert new contacts to the HH with the current default addresses
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(cHH);
        for (integer i = 0; i < cHH; i++) {
            listCon[i].AccountId = listAccT[i].Id;
            listCon[i].MailingStreet = listAccT[i].BillingStreet;
            listCon[i].MailingCity = listAccT[i].BillingCity;
            listCon[i].MailingState = listAccT[i].BillingState;
            listCon[i].MailingPostalCode = listAccT[i].BillingPostalCode;
            listCon[i].MailingCountry = listAccT[i].BillingCountry;            
            listCon[i].MailingLatitude = listAccT[i].BillingLatitude;
            listCon[i].MailingLongitude = listAccT[i].BillingLongitude;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        insert listCon;         
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.stopTest();
        
        // verify that the HH and Contacts share the same address
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        system.assertEquals(cHH * (cCon + 1), listCon.size());
        
        integer cAddr = [select count() from Address__c];
        system.assertEquals(cHH, cAddr);
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, ADDR_Addresses_TEST.isMatchAddressAccCon(acc, con));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
    }

    /*********************************************************************************************************
    @description
        insert contact w/ multiline street address
    verify:
        new default Address created and the street address is broken up into street and street2
        the HH Account gets the new multiline address
    **********************************************************************************************************/            
    static testMethod void insertContactWithMultilineStreetAddress() {
        if (strTestOnly != '*' && strTestOnly != 'insertContactWithMultilineStreetAddress') return;
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettings();
            
        listConT = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(cCon);
        for (Contact con : listConT) {
            con.MailingStreet = 'new street\r\nsecond line';
            con.MailingCity = 'new city';
        }
        Test.startTest();
        insert listConT; 
        Test.stopTest();       
        
        // verify the Address objects split street.
        list<Address__c> listAddr = [select Id, MailingStreet__c, MailingStreet2__c, Formula_MailingStreetAddress__c from Address__c];
        for (Address__c addr : listAddr) {
            system.assertEquals('new street', addr.MailingStreet__c);
            system.assertEquals('second line', addr.MailingStreet2__c);
            system.assertEquals('new street, second line', addr.Formula_MailingStreetAddress__c);
        }

        // verify that the HH and Contacts share the same address
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals('new street\r\nsecond line', con.MailingStreet);
            system.assertEquals(true, ADDR_Addresses_TEST.isMatchAddressAccCon(acc, con));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
    }

    /*********************************************************************************************************
    @description
        insert new default multiline street addresses to HH w/ existing default addresses  
    verify:
        contact's && hh address matches new default
        street & street2 combined into contact and account single street field
    **********************************************************************************************************/            
    static testMethod void insertNewDefaultMultilineStreetAddr() {
        if (strTestOnly != '*' && strTestOnly != 'insertNewDefaultMultilineStreetAddr') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);
        
        // create additional addresses
        initTestAddr(cHH);
        for (integer i = 0; i < cHH; i++) {
            listAddrT[i].Household_Account__c = listAccT[i].Id;
            listAddrT[i].Default_Address__c = true;
            listAddrT[i].MailingStreet__c = 'New Default Street';
            listAddrT[i].MailingStreet2__c = 'Second Line';
            listAddrT[i].MailingCity__c = 'New Default City' + i;
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        insert listAddrT;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;        
        Test.stopTest();

        // verify that the HH and Contacts share the same address and it's new!
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        system.assertEquals(cHH * cCon, listCon.size());
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, ADDR_Addresses_TEST.isMatchAddressAccCon(acc, con));
            system.assertEquals('New Default Street\r\nSecond Line', con.MailingStreet);
            system.assertEquals('New Default Street\r\nSecond Line', acc.BillingStreet);
        }
        
    }
    
    /*********************************************************************************************************
    @description
        update accounts' billing address with multiline street address  
    verify:
        new Default Address created
        HH and Contacts get new Address
        new Addresss has multiline street split into Street and Street2 fields
    **********************************************************************************************************/            
    static testMethod void updateAccMultilineStreetAddr() {
        if (strTestOnly != '*' && strTestOnly != 'updateAccMultilineStreetAddr') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);

        // modify some of the account addresses directly
        for (integer i = 0; i < cHH; i++) {
            Account acc = listAccT[i];
            acc.BillingStreet = 'Direct Street Edit\r\nSecond Line';
            acc.BillingCity = 'Direct City Edit';
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        update listAccT;
        Test.stopTest();
        
        // verify that the HH and Contacts share the same address
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, ADDR_Addresses_TEST.isMatchAddressAccCon(acc, con));
            system.assertEquals('Direct Street Edit\r\nSecond Line', acc.BillingStreet);
            system.assertEquals('Direct Street Edit\r\nSecond Line', con.MailingStreet);
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
 
        // verify the Address objects split street.
        list<Address__c> listAddr = [select Id, MailingStreet__c, MailingStreet2__c, Formula_MailingStreetAddress__c, Default_Address__c from Address__c];
        for (Address__c addr : listAddr) {
            if (addr.Default_Address__c) {
                system.assertEquals('Direct Street Edit', addr.MailingStreet__c);
                system.assertEquals('Second Line', addr.MailingStreet2__c);
                system.assertEquals('Direct Street Edit, Second Line', addr.Formula_MailingStreetAddress__c);
            }
        }

    }

    /*********************************************************************************************************
    @description
        update accounts' billing address with multiline street address  (this version only has Newline only)
    verify:
        new Default Address created
        HH and Contacts get new Address
        new Addresss has multiline street split into Street and Street2 fields
    **********************************************************************************************************/            
    static testMethod void updateAccMultilineStreetAddrNL() {
        if (strTestOnly != '*' && strTestOnly != 'updateAccMultilineStreetAddrNL') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);

        // modify some of the account addresses directly
        for (integer i = 0; i < cHH; i++) {
            Account acc = listAccT[i];
            acc.BillingStreet = 'Direct Street Edit\nSecond Line';
            acc.BillingCity = 'Direct City Edit';
        }
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        update listAccT;
        Test.stopTest();
        
        // verify that the HH and Contacts share the same address
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, ADDR_Addresses_TEST.isMatchAddressAccCon(acc, con));
            system.assertEquals('Direct Street Edit\r\nSecond Line', acc.BillingStreet);
            system.assertEquals('Direct Street Edit\r\nSecond Line', con.MailingStreet);
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertNotEquals(null, con.Current_Address__c);
        }
 
        // verify the Address objects split street.
        list<Address__c> listAddr = [select Id, MailingStreet__c, MailingStreet2__c, Formula_MailingStreetAddress__c, Default_Address__c from Address__c];
        for (Address__c addr : listAddr) {
            if (addr.Default_Address__c) {
                system.assertEquals('Direct Street Edit', addr.MailingStreet__c);
                system.assertEquals('Second Line', addr.MailingStreet2__c);
                system.assertEquals('Direct Street Edit, Second Line', addr.Formula_MailingStreetAddress__c);
            }
        }

    }

    /*********************************************************************************************************
    @description
        create N new organizational accounts with billing addresses 
    verify:
        N addresses created
        account address matches address object address
    **********************************************************************************************************/            
    static testMethod void newOrgAccounts() {
        if (strTestOnly != '*' && strTestOnly != 'newOrgAccounts') return;
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR,
                Organizational_Account_Addresses_Enabled__c = true
        ));
 
        integer cAcc = 3;
        list<Account> listAcc = UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(cAcc, null);
        for (Account acc : listAcc) {
            acc.BillingStreet = '123 45th';
            acc.BillingCity = 'Seattle';
        }
        Test.startTest();
        insert listAcc;
        Test.stopTest();

        // verify results
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        system.assertEquals(cAcc, mapAccIdAcc.size());
        list<Address__c> listAddr = [select Id, Default_Address__c, MailingStreet__c, MailingCity__c, Household_Account__c, Latest_Start_Date__c, Latest_End_Date__c from Address__c];
        system.assertEquals(cAcc, listAddr.size());
        
        for (Address__c addr : listAddr) {
            Account acc = mapAccIdAcc.get(addr.Household_Account__c);
            system.assertEquals(acc.BillingStreet, addr.MailingStreet__c);
            system.assertEquals(acc.BillingCity, addr.MailingCity__c);
            system.assertEquals(true, addr.Default_Address__c);
        }
    }

    /*********************************************************************************************************
    @description
        update N organizational accounts with billing addresses 
    verify:
        N addresses created
        account addresses updated
        contact addresses not updated
    **********************************************************************************************************/            
    static testMethod void updateOrgAccounts() {
        if (strTestOnly != '*' && strTestOnly != 'updateOrgAccounts') return;
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR,
                Organizational_Account_Addresses_Enabled__c = true
        ));
 
        // create accounts without addresses
        integer cAcc = 3;
        list<Account> listAcc = UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(cAcc, null);
        insert listAcc;
        
        // add a contact to each account.
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(cAcc);
        for (integer i = 0; i < cAcc; i++) {
            listCon[i].AccountId = listAcc[i].Id;
        }
        insert listCon;

        // update the accounts' address
        for (Account acc : listAcc) {
            acc.BillingStreet = '123 45th';
            acc.BillingCity = 'Seattle';
        }
        Test.startTest();
        update listAcc;
        Test.stopTest();

        // verify results
        listAcc = [select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account order by Account.Name];
        system.assertEquals(cAcc, listAcc.size());
        list<Address__c> listAddr = [select Id, Default_Address__c, MailingStreet__c, MailingCity__c, Household_Account__c, Latest_Start_Date__c, Latest_End_Date__c from Address__c order by Household_Account__r.Name];
        system.assertEquals(cAcc, listAddr.size());
        listCon = [select Id, AccountId, Name, Current_Address__c, MailingStreet, MailingCity from Contact order by Account.Name];
        system.assertEquals(cAcc, listCon.size());        
        
        for (integer i = 0; i < cAcc; i++) {
            Account acc = listAcc[i];
            Address__c addr = listAddr[i];
            Contact con = listCon[i];
            system.assertEquals(acc.BillingStreet, addr.MailingStreet__c);
            system.assertEquals(acc.BillingCity, addr.MailingCity__c);
            system.assertEquals(true, addr.Default_Address__c);
            system.assertEquals(null, con.Current_Address__c);
            system.assertEquals(null, con.MailingStreet);
            system.assertEquals(null, con.MailingCity);
        }
    }
    
    /*********************************************************************************************************
    @description
        update N organizational accounts with address objects 
    verify:
        account addresses updated
        contact addresses not updated
    **********************************************************************************************************/            
    static testMethod void newAddrForOrgAccounts() {
        if (strTestOnly != '*' && strTestOnly != 'newAddrForOrgAccounts') return;

        TDTM_DefaultConfig.startTestDataInit();
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR,
                Organizational_Account_Addresses_Enabled__c = true
        ));
 
        // create accounts without addresses
        integer cAcc = 3;
        list<Account> listAcc = UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(cAcc, null);
        insert listAcc;
        
        // add a contact to each account.
        list<Contact> listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(cAcc);
        for (integer i = 0; i < cAcc; i++) {
            listCon[i].AccountId = listAcc[i].Id;
        }
        insert listCon;

         // create addresses for the accounts
        initTestAddr(cAcc);
        for (integer i = 0; i < cAcc; i++) {
            listAddrT[i].Household_Account__c = listAcc[i].Id;
            listAddrT[i].Default_Address__c = true;
        }
        TDTM_DefaultConfig.stopTestDataInit();
        Test.startTest();
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        insert listAddrT;
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.stopTest();
        
        // verify results
        listAcc = [select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account order by Account.Name];
        system.assertEquals(cAcc, listAcc.size());
        listAddrT = [select Id, Default_Address__c, MailingStreet__c, MailingCity__c, Household_Account__c, Latest_Start_Date__c, Latest_End_Date__c from Address__c order by Household_Account__r.Name];
        system.assertEquals(cAcc, listAddrT.size());
        listCon = [select Id, AccountId, Name, Current_Address__c, MailingStreet, MailingCity from Contact order by Account.Name];
        system.assertEquals(cAcc, listCon.size());        
        
        for (integer i = 0; i < cAcc; i++) {
            Account acc = listAcc[i];
            Address__c addr = listAddrT[i];
            Contact con = listCon[i];
            system.assertEquals(acc.BillingStreet, addr.MailingStreet__c);
            system.assertEquals(acc.BillingCity, addr.MailingCity__c);
            system.assertEquals(true, addr.Default_Address__c);
            system.assertEquals(null, con.Current_Address__c);
            system.assertEquals(null, con.MailingStreet);
            system.assertEquals(null, con.MailingCity);
        }
    }

    /*********************************************************************************************************
    @description
        create N contacts with Household Accounts when the setting for Address Mgmt is off. 
    verify:
        no Address objects created
        HH Account addresses set
        Contact addresses set
    **********************************************************************************************************/            
    static testMethod void testDisabledHHAccountAddr() {
        if (strTestOnly != '*' && strTestOnly != 'testDisabledHHAccountAddr') return;
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR,
                Household_Account_Addresses_Disabled__c = true
        ));
 
        listConT = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(cCon);
        for (Contact con : listConT) {
            con.MailingStreet = 'new street';
            con.MailingCity = 'new city';
        }
        Test.startTest();
        insert listConT; 
        Test.stopTest();       

        // verify that the HH and Contacts share the same address
        map<Id, Account> mapAccIdAcc = new map<Id, Account>([select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        list<Contact> listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        
        for (Contact con : listCon) {
            Account acc = mapAccIdAcc.get(con.AccountId);
            system.assertEquals(true, ADDR_Addresses_TEST.isMatchAddressAccCon(acc, con));
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertEquals(null, con.Current_Address__c);
        }
        
        // verify no address objects created
        list<Address__c> listAddr = [select Id from Address__c];
        system.assertEquals(0, listAddr.size());
    }

    /*********************************************************************************************************
    @description
        update contacts' mailing address fields to empty cause clearing the default address  
    verify:
        no new Address created
        existing Default Address updated to be non-default
        HH and Contacts get updated to empty address fields
    **********************************************************************************************************/            
    static testMethod void clearConAddrExisting() {
        if (strTestOnly != '*' && strTestOnly != 'clearConAddrExisting') return;
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR,
                Simple_Address_Change_Treated_as_Update__c = true
        ));
                
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);

        // clear some of the contact addresses directly
        list<Contact> listCon = new list<Contact>();
        for (integer i = 0; i < cHH; i++) {
            Contact con = listConT[i*cHH + i];
            con.MailingStreet = null;
            con.MailingCity = null;
            con.MailingPostalCode = null;
            con.MailingState = null;
            con.MailingCountry = null;
	        if (ADDR_Addresses_TDTM.isStateCountryPicklistsEnabled) {
	            con.put('MailingStateCode', null);
	            con.put('MailingCountryCode', null);
	        }                
            listCon.add(con);
        }

        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        update listCon;
        Test.stopTest();
        
        // verify that the HH and Contacts have empty addresses
        list<Account> listAcc = [select Id, Name, BillingStreet, BillingCity, BillingState, BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account];
        listCon = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact];
        
        for (Contact con : listCon) {
            system.assertEquals(false, con.is_Address_Override__c);
            system.assertEquals(null, con.Current_Address__c);
            system.assertEquals(null, con.MailingStreet);
            system.assertEquals(null, con.MailingCity);
            system.assertEquals(null, con.MailingPostalCode);
            system.assertEquals(null, con.MailingState);
            system.assertEquals(null, con.MailingCountry);
        }
 
        for (Account acc : listAcc) {
            system.assertEquals(null, acc.BillingStreet);
            system.assertEquals(null, acc.BillingCity);
            system.assertEquals(null, acc.BillingPostalCode);
            system.assertEquals(null, acc.BillingState);
            system.assertEquals(null, acc.BillingCountry);
        }
        
        // verify that no new address was created
        list<Address__c> listAddr = [select Id, Default_Address__c, MailingStreet__c, Household_Account__c from Address__c];
        system.assertEquals(cHH, listAddr.size());
        for (Address__c addr : listAddr) {
            system.assertEquals(false, addr.Default_Address__c);
        }                
    }

    /*********************************************************************************************************
    @description
        set contact address override and address lookup on a new household contact  
    verify:
        contact's address fields correctly set
    **********************************************************************************************************/            
    static testMethod void setConAddrOverrideAndLookupOnNewContact() {
        if (strTestOnly != '*' && strTestOnly != 'setConAddrOverrideAndLookupOnNewContact') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);
        
        Contact con = new Contact();
        con.lastname = 'test';
        con.AccountId = listAccT[0].Id;
        con.is_Address_Override__c = true;
        con.Current_Address__c = listAddrT[0].Id;
        
        Test.startTest();
        insert con;
        Test.stopTest();
        
        con = [select Id, Name, AccountId, is_Address_Override__c, Current_Address__c, MailingStreet, MailingCity, 
            MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude from Contact where Id = :con.Id];
        system.assertEquals(true, con.is_Address_Override__c);
        system.assertEquals(listAddrT[0].Id, con.Current_Address__c);
        system.assertEquals(listAddrT[0].MailingStreet__c, con.MailingStreet);
        system.assertEquals(listAddrT[0].MailingCity__c, con.MailingCity);
        system.assertEquals(listAddrT[0].MailingPostalCode__c, con.MailingPostalCode);
        system.assertEquals(listAddrT[0].MailingState__c, con.MailingState);
        system.assertEquals(listAddrT[0].MailingCountry__c, con.MailingCountry);
    }

    /*********************************************************************************************************
    @description
        update an org contact to be a household contact 
    verify:
        HH address created
        account address matches address object address
        contact address matches address object address
    **********************************************************************************************************/            
    static testMethod void updateOrgContactToHH() {
        if (strTestOnly != '*' && strTestOnly != 'updateOrgContactToHH') return;
        
        Account acc = new Account(Name='Org Account');
        insert acc;
        Contact con = new Contact(Lastname='Testy', AccountId=acc.Id);
        insert con;
        
        // now give the contact an address and change it to get a hh account
        con.AccountId = null;
        con.MailingStreet = '123 abc';
        Test.startTest();
        update con;
        Test.stopTest();

        // verify results
        acc = [select Id, Name, BillingStreet from Account where Name like '%Testy%'];
        list<Address__c> listAddr = [select Id, Default_Address__c, MailingStreet__c from Address__c];
        system.assertEquals(1, listAddr.size());
        con = [select Id, Lastname, MailingStreet, Current_Address__c from Contact];
        system.assertEquals('Testy', con.Lastname);
        system.assertEquals('123 abc', con.MailingStreet);
        system.assertEquals(listAddr[0].Id, con.Current_Address__c);
        system.assertEquals(listAddr[0].MailingStreet__c, con.MailingStreet);
        system.assertEquals(listAddr[0].MailingStreet__c, acc.BillingStreet);
        
    }

    /*********************************************************************************************************
    * @description update a household contact to be removed from existing household and verify that a new 
    * household is created and the contact address matches the new HH Account Address and new Address created
    **********************************************************************************************************/            
    static testMethod void updateHHContactToNewHH() {
        if (strTestOnly != '*' && strTestOnly != 'updateOrgContactToHH') return;
        
        // this creates a default Address for each HH
        createHHTestData(cHH, cCon);
        
        // update existing contacts to new HH with new addresses
        for (Contact conT : listConT) {
            conT.AccountId = null;
        }

        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.startTest();
        update listConT;         
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;
        Test.stopTest();
        
        // verify that the new HH and Contacts share the same address
        Map<Id, Account> mapAccIdAcc = new Map<Id, Account>([SELECT Id, Name, BillingStreet, BillingCity, BillingState,
            BillingPostalCode, BillingCountry, BillingLatitude, BillingLongitude from Account]);
        Map<Id, Contact> mapConIdCon = new Map<Id, Contact>([SELECT Id, Name, AccountId, is_Address_Override__c, Current_Address__c,
            MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude FROM Contact]);
        Map<Id, Address__c> mapAddrIdAddr = new Map<Id, Address__c>([SELECT Id, Default_Address__c, MailingStreet__c, Household_Account__c FROM Address__c]);

        // verify new accounts with new addresses were created and previous accounts and addresses were removed
        for (Contact conT : listConT) {
            Contact updatedCon = mapConIdCon.get(conT.Id);
            Account updatedAcc = mapAccIdAcc.get(updatedCon.AccountId);

            System.assertNotEquals(conT.AccountId, updatedCon.AccountId, 'New Account should be created for the Contact.');
            System.assert(!mapAccIdAcc.containsKey(conT.AccountId), 'The original account should have been deleted.');
            System.assert(!mapAddrIdAddr.containsKey(conT.Current_Address__c), 'The original address should have been deleted.');
            System.assert(mapAddrIdAddr.containsKey(updatedCon.Current_Address__c), 'A new address should have been created.');
            System.assertEquals(true, ADDR_Addresses_TEST.isMatchAddressAccCon(updatedAcc, updatedCon), 'The new account address should match the contact.');
            System.assertEquals(false, updatedCon.is_Address_Override__c, 'The contact\'s override should not be set.');
        }
    }
}