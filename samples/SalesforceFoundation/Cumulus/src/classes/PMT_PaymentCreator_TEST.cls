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
* @group Payments
* @description tests for the PaymentCreator class 
*/
@isTest
private with sharing class PMT_PaymentCreator_TEST {

    /*********************************************************************************************************
    * @description if you only want to run one test in this class, fill in its name here.
    * if you want to run all tests, then use '*'
    */
    private static string strTestOnly = '*';
        
    /*********************************************************************************************************
    * @description
    operation:
        close an Opp as Lost that has a mixture of paid & unpaid payments, some written off 
    verify:
        opp is closed
        paid payments are left alone
        written off payments are left alone
        unpaid (and not written off) are deleted
    **********************************************************************************************************/            
    static testMethod void CloseLostOppWithMixOfPayments() {
        if (strTestOnly != '*' && strTestOnly != 'CloseLostOppWithMixOfPayments') return;
        
        npe01__Contacts_And_Orgs_Settings__c PaymentsSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_And_Orgs_Settings__c (npe01__Payments_Enabled__c= true));
        
        // create test data
        Account acct = new Account (
            Name='Test Account'
        );
        insert acct;
        list<Opportunity> testOppList = new list<Opportunity>();
        Opportunity opp1 = new Opportunity (
            Name='Test Opp',
            AccountId = acct.id,
            Amount = 150,
            CloseDate = date.today(), 
            StageName = 'Closed Won'
        );
        testOppList.add(opp1);
        Opportunity opp2 = new Opportunity (
            Name='Test Opp_noAmount',
            AccountId = acct.id,
        //    Amount = 150, for this test case the amount should be NULL   BUG #1285
            CloseDate = date.today(), 
            StageName = 'Qualification'
        );
        testOppList.add(opp2);
        insert(testOppList);
        //at this moment no payments should have been created for opp2, cause amount is null BUG #1285
        system.assertEquals(0, [select id,npe01__paid__c, npe01__payment_amount__c from npe01__OppPayment__c WHERE npe01__opportunity__c = :opp2.Id].size());
        opp2.amount = 150;
        update(opp2);
        //at this moment payments should have been created for opp2, cause amount is changed to >0 BUG #1285
        system.assertEquals(1, [select id,npe01__paid__c, npe01__payment_amount__c from npe01__OppPayment__c WHERE npe01__opportunity__c = :opp2.Id].size());

        
        npe01__OppPayment__c[] opmtUpdate = [select id,npe01__paid__c, npe01__payment_amount__c from npe01__OppPayment__c WHERE npe01__opportunity__c = :opp1.Id];
        opmtUpdate[0].npe01__payment_amount__c = 50;
        system.assertEquals(true, opmtUpdate[0].npe01__paid__c);
        update opmtUpdate;
        
        npe01__OppPayment__c pmtUnpaid = new npe01__OppPayment__c(
            npe01__Opportunity__c = opp1.id,
            npe01__Payment_Amount__c = 50,
            npe01__Paid__c = false,
            npe01__Payment_Date__c = date.today()
        );
        insert pmtUnpaid;
        npe01__OppPayment__c pmtWrittenOff = new npe01__OppPayment__c(
            npe01__Opportunity__c = opp1.id,
            npe01__Payment_Amount__c = 50,
            npe01__Paid__c = false,
            npe01__Written_Off__c = true,
            npe01__Payment_Date__c = date.today()
        );
        insert pmtWrittenOff;
        
        Test.startTest();
        opp1.StageName = 'Closed Lost';
        update opp1;
        Test.stopTest();
        
        // look for the data
        //should be two, one paid, one written off for the balance 
        npe01__OppPayment__c[] opmtAll = [select id, npe01__payment_method__c, npe01__payment_amount__c, npe01__paid__c, npe01__written_off__c from npe01__OppPayment__c WHERE npe01__opportunity__c = :opp1.Id];
        system.assertEquals(2, opmtAll.size());
        
        //the single payment should be written off
        npe01__OppPayment__c[] opmtOpen = [select id, npe01__payment_method__c, npe01__payment_amount__c, npe01__paid__c, npe01__written_off__c from npe01__OppPayment__c WHERE npe01__opportunity__c = :opp1.Id and npe01__paid__c = false and npe01__written_off__c = false];
        system.assertEquals(0, opmtOpen.size());
        
        npe01__OppPayment__c[] opmtPaid = [select id, npe01__payment_method__c, npe01__payment_amount__c, npe01__paid__c, npe01__written_off__c from npe01__OppPayment__c WHERE npe01__opportunity__c = :opp1.Id and npe01__paid__c = true and npe01__written_off__c = false];
        system.assertEquals(1, opmtPaid.size());
        system.assertEquals(50, opmtPaid[0].npe01__payment_amount__c);
        
        npe01__OppPayment__c[] opmtWrittenOff = [select id, npe01__payment_method__c, npe01__payment_amount__c, npe01__paid__c, npe01__written_off__c from npe01__OppPayment__c WHERE npe01__opportunity__c = :opp1.Id and npe01__paid__c = false and npe01__written_off__c = true];
        system.assertEquals(1, opmtWrittenOff.size());
        system.assertEquals(50, opmtWrittenOff[0].npe01__payment_amount__c);       
    }

    /*********************************************************************************************************
    * @description
    operation:
        close an Opp as Lost that has an existing payment and a null amount
    verify:
        payment is deleted, no infinite recursion takes place
    **********************************************************************************************************/            
    static testMethod void nullAmount() {
        if (strTestOnly != '*' && strTestOnly != 'nullAmount') return;
        
        npe01__Contacts_And_Orgs_Settings__c PaymentsSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_And_Orgs_Settings__c (npe01__Payments_Enabled__c= true));
        
        // create test data
        Account acct = new Account (
            Name='Test Account'
        );
        insert acct;

        Opportunity opp = new Opportunity (
            Name='Test Opp',
            AccountId = acct.id,
            CloseDate = date.today(), 
            Amount = 1,
            StageName = UTIL_UnitTestData_TEST.getOpenStage()
        );
        insert opp;

        list<npe01__OppPayment__c> opmtAll = [select id, npe01__payment_method__c, npe01__payment_amount__c, npe01__paid__c, npe01__written_off__c from npe01__OppPayment__c WHERE npe01__opportunity__c = :opp.Id];
        system.assertEquals(1, opmtAll.size());

        opp.Amount = null;
        opp.StageName = UTIL_UnitTestData_TEST.getClosedLostStage();
        update opp;

        opmtAll = [select id, npe01__payment_method__c, npe01__payment_amount__c, npe01__paid__c, npe01__written_off__c from npe01__OppPayment__c WHERE npe01__opportunity__c = :opp.Id];
        system.assertEquals(0, opmtAll.size());

    }

    /*********************************************************************************************************
    * @description
    operation:
        create an Opp whose Type value is NOT excluded in settings from creating payments
    verify:
        no payment created
    **********************************************************************************************************/            
    static testMethod void createOppWithTypeNotExcluded() {
        if (strTestOnly != '*' && strTestOnly != 'createOppWithTypeNotExcluded') return;
        
        npe01__Contacts_And_Orgs_Settings__c PaymentsSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_And_Orgs_Settings__c (npe01__Payments_Enabled__c= true, Opp_Types_Excluded_for_Payments__c='NO-PAYMENT'));
        
        // create test data
        Account acct = new Account (
            Name='Test Account'
        );
        insert acct;

        Opportunity opp = new Opportunity (
            Name='Test Opp',
            AccountId = acct.id,
            CloseDate = date.today(), 
            Amount = 1,
            StageName = UTIL_UnitTestData_TEST.getOpenStage(),
            Type = 'YES-PAYMENT'
        );
        Test.startTest();
        insert opp;
        Test.stopTest();

        list<npe01__OppPayment__c> opmtAll = [select id, npe01__payment_method__c, npe01__payment_amount__c, npe01__paid__c, npe01__written_off__c from npe01__OppPayment__c WHERE npe01__opportunity__c = :opp.Id];
        system.assertEquals(1, opmtAll.size());
    }

    /*********************************************************************************************************
    * @description
    operation:
        create an Opp whose Type value is excluded in settings from creating payments
    verify:
        no payment created
    **********************************************************************************************************/            
    static testMethod void createOppWithTypeExcluded() {
        if (strTestOnly != '*' && strTestOnly != 'createOppWithTypeExcluded') return;
        
        npe01__Contacts_And_Orgs_Settings__c PaymentsSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_And_Orgs_Settings__c (npe01__Payments_Enabled__c= true, Opp_Types_Excluded_for_Payments__c='NO-PAYMENT'));
        
        // create test data
        Account acct = new Account (
            Name='Test Account'
        );
        insert acct;

        Opportunity opp = new Opportunity (
            Name='Test Opp',
            AccountId = acct.id,
            CloseDate = date.today(), 
            Amount = 1,
            StageName = UTIL_UnitTestData_TEST.getOpenStage(),
            Type = 'NO-PAYMENT'
        );
        Test.startTest();
        insert opp;
        Test.stopTest();

        list<npe01__OppPayment__c> opmtAll = [select id, npe01__payment_method__c, npe01__payment_amount__c, npe01__paid__c, npe01__written_off__c from npe01__OppPayment__c WHERE npe01__opportunity__c = :opp.Id];
        system.assertEquals(0, opmtAll.size());
    }

    /*********************************************************************************************************
    * @description
    operation:
        create an Opp whose RecordType is excluded in settings from creating payments
    verify:
        no payment created
    **********************************************************************************************************/            
    static testMethod void createOppWithRecordTypeExcluded() {
        if (strTestOnly != '*' && strTestOnly != 'createOppWithRecordTypeExcluded') return;
        
        // see if this org has any record types we can use!
        Id rtIdGift = UTIL_RecordTypes.getRecordTypeIdForGiftsTests(Opportunity.sObjectType);

        npe01__Contacts_And_Orgs_Settings__c PaymentsSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_And_Orgs_Settings__c(
                npe01__Payments_Enabled__c = true,
                Opp_RecTypes_Excluded_for_Payments__c = rtIdGift
            )
        );

        // create test data
        Account acct = new Account (
            Name='Test Account'
        );
        insert acct;

        Opportunity opp = new Opportunity (
            Name='Test Opp',
            AccountId = acct.id,
            CloseDate = date.today(), 
            Amount = 1,
            StageName = UTIL_UnitTestData_TEST.getOpenStage()
        );

        if (rtIdGift != null) {
            opp.RecordTypeId = rtIdGift;
        }

        Test.startTest();
        insert opp;
        Test.stopTest();

        list<npe01__OppPayment__c> opmtAll = [select id, npe01__payment_method__c, npe01__payment_amount__c, npe01__paid__c, npe01__written_off__c from npe01__OppPayment__c WHERE npe01__opportunity__c = :opp.Id];
        if (rtIdGift != null)
            system.assertEquals(0, opmtAll.size());
        else
            system.assertEquals(1, opmtAll.size());
    }
    
    private static testMethod void testOpportunityWillAutoClosefterAllPaymentsReceived() {
        String closedWonStage = UTIL_UnitTestData_TEST.getClosedWonStage();
        String openStage = UTIL_UnitTestData_TEST.getOpenStage();

        if (String.isEmpty(closedWonStage) || String.isEmpty(openStage)) {
            System.debug(
                System.LoggingLevel.ERROR,
                'Organization settings do not contain the necessary opportunity stages to run this test. Not running test.'
            );
            return;
        }

        UTIL_CustomSettingsFacade.getContactsSettings().Payments_Auto_Close_Stage_Name__c = closedWonStage;

        OpportunityHelper oh = new OpportunityHelper(openStage);
        oh.refresh();

        List<npe01__OppPayment__c> payments = oh.getPayments();

        System.assertEquals(1, payments.size());
        System.assertEquals(oh.opp.Amount, payments.get(0).npe01__Payment_Amount__c);
        System.assertEquals(openStage, oh.opp.StageName);

        payments.get(0).npe01__Paid__c = true;

        Test.startTest();

        update payments;

        Test.stopTest();

        oh.refresh();

        System.assertEquals(oh.opp.Amount, oh.opp.npe01__Payments_Made__c);
        System.assertEquals(closedWonStage, oh.opp.StageName);
    }

    private static testMethod void testOpportunityWillAutoCloseIfAmountUpdatedToMatchPayments() {
        String closedWonStage = UTIL_UnitTestData_TEST.getClosedWonStage();
        String openStage = UTIL_UnitTestData_TEST.getOpenStage();

        if (String.isEmpty(closedWonStage) || String.isEmpty(openStage)) {
            System.debug(
                System.LoggingLevel.ERROR,
                'Organization settings do not contain the necessary opportunity stages to run this test. Not running test.'
            );
            return;
        }

        UTIL_CustomSettingsFacade.getContactsSettings().Payments_Auto_Close_Stage_Name__c = closedWonStage;

        OpportunityHelper oh = new OpportunityHelper(openStage);
        oh.refresh();

        oh.deletePayments();
        oh.setPayments(new List<npe01__OppPayment__c>{
            new npe01__OppPayment__c(
                npe01__Paid__c = true,
                npe01__Payment_Amount__c = 250
            ),
            new npe01__OppPayment__c(
                npe01__Paid__c = false,
                npe01__Payment_Amount__c = 250
            )
        });
        oh.refresh();

        System.assertEquals(500, oh.opp.Amount);
        System.assertEquals(250, oh.opp.npe01__Payments_Made__c);
        System.assertEquals(openStage, oh.opp.StageName);

        Test.startTest();

        oh.opp.Amount = 250;
        update oh.opp;

        Test.stopTest();

        oh.refresh();

        System.assertEquals(250, oh.opp.Amount);
        System.assertEquals(oh.opp.Amount, oh.opp.npe01__Payments_Made__c);
        System.assertEquals(closedWonStage, oh.opp.StageName);
    }

    private static testMethod void testOpportunityWillNotAutoCloseIfNotAllPaymentsReceived() {
        String closedWonStage = UTIL_UnitTestData_TEST.getClosedWonStage();
        String openStage = UTIL_UnitTestData_TEST.getOpenStage();

        if (String.isEmpty(closedWonStage) || String.isEmpty(openStage)) {
            System.debug(
                System.LoggingLevel.ERROR,
                'Organization settings do not contain the necessary opportunity stages to run this test. Not running test.'
            );
            return;
        }

        UTIL_CustomSettingsFacade.getContactsSettings().Payments_Auto_Close_Stage_Name__c = closedWonStage;

        OpportunityHelper oh = new OpportunityHelper(openStage);
        oh.refresh();

        oh.deletePayments();
        oh.setPayments(new List<npe01__OppPayment__c>{
            new npe01__OppPayment__c(
                npe01__Paid__c = false,
                npe01__Payment_Amount__c = 250
            ),
            new npe01__OppPayment__c(
                npe01__Paid__c = false,
                npe01__Payment_Amount__c = 250
            )
        });
        oh.refresh();

        System.assertEquals(500, oh.opp.Amount);
        System.assertEquals(0, oh.opp.npe01__Payments_Made__c);
        System.assertEquals(openStage, oh.opp.StageName);

        Test.startTest();

        oh.getPayments().get(0).npe01__Paid__c = true;
        update oh.getPayments();

        Test.stopTest();

        oh.refresh();

        System.assertEquals(500, oh.opp.Amount);
        System.assertEquals(250, oh.opp.npe01__Payments_Made__c);
        System.assertEquals(openStage, oh.opp.StageName);
    }

    private static testMethod void testOpportunityWillNotAutoCloseIfNoStageNameSet() {
        String openStage = UTIL_UnitTestData_TEST.getOpenStage();

        if (String.isEmpty(openStage)) {
            System.debug(
                System.LoggingLevel.ERROR,
                'Organization settings do not contain the necessary opportunity stages to run this test. Not running test.'
            );
            return;
        }

        UTIL_CustomSettingsFacade.getContactsSettings().Payments_Auto_Close_Stage_Name__c = '';

        OpportunityHelper oh = new OpportunityHelper(openStage);
        oh.refresh();

        List<npe01__OppPayment__c> payments = oh.getPayments();

        System.assertEquals(1, payments.size());
        System.assertEquals(oh.opp.Amount, payments.get(0).npe01__Payment_Amount__c);
        System.assertEquals(openStage, oh.opp.StageName);

        payments.get(0).npe01__Paid__c = true;

        Test.startTest();

        update payments;

        Test.stopTest();

        oh.refresh();

        System.assertEquals(oh.opp.Amount, oh.opp.npe01__Payments_Made__c);
        System.assertEquals(openStage, oh.opp.StageName);
    }

    private static testMethod void testOpportunityWillNotAutoCloseIfAlreadyClosed() {
        String closedWonStage = UTIL_UnitTestData_TEST.getClosedWonStage();
        String openStage = UTIL_UnitTestData_TEST.getOpenStage();
        String closedLostStage = UTIL_UnitTestData_TEST.getClosedLostStage();

        if (String.isEmpty(closedWonStage) || String.isEmpty(openStage) || String.isEmpty(closedLostStage)) {
            System.debug(
                System.LoggingLevel.ERROR,
                'Organization settings do not contain the necessary opportunity stages to run this test. Not running test.'
            );
            return;
        }

        UTIL_CustomSettingsFacade.getContactsSettings().Payments_Auto_Close_Stage_Name__c = closedWonStage;

        OpportunityHelper oh = new OpportunityHelper(openStage);
        oh.refresh();

        oh.deletePayments();
        oh.setPayments(new List<npe01__OppPayment__c>{
            new npe01__OppPayment__c(
                npe01__Paid__c = true,
                npe01__Payment_Amount__c = 250
            ),
            new npe01__OppPayment__c(
                npe01__Paid__c = false,
                npe01__Payment_Amount__c = 250
            )
        });
        oh.refresh();

        System.assertEquals(500, oh.opp.Amount);
        System.assertEquals(250, oh.opp.npe01__Payments_Made__c);
        System.assertEquals(openStage, oh.opp.StageName);

        Test.startTest();

        oh.opp.Amount = 250;
        oh.opp.StageName = closedLostStage;
        update oh.opp;

        Test.stopTest();

        oh.refresh();

        System.assertEquals(250, oh.opp.Amount);
        System.assertEquals(oh.opp.Amount, oh.opp.npe01__Payments_Made__c);
        System.assertEquals(closedLostStage, oh.opp.StageName);
    }

    private static testMethod void testOpportunityWillNotAutoCloseIfAllPaymentsWrittenOff() {
        String closedWonStage = UTIL_UnitTestData_TEST.getClosedWonStage();
        String openStage = UTIL_UnitTestData_TEST.getOpenStage();

        if (String.isEmpty(closedWonStage) || String.isEmpty(openStage)) {
            System.debug(
                System.LoggingLevel.ERROR,
                'Organization settings do not contain the necessary opportunity stages to run this test. Not running test.'
            );
            return;
        }

        UTIL_CustomSettingsFacade.getContactsSettings().Payments_Auto_Close_Stage_Name__c = closedWonStage;

        OpportunityHelper oh = new OpportunityHelper(openStage);
        oh.refresh();

        List<npe01__OppPayment__c> payments = oh.getPayments();

        System.assertEquals(1, payments.size());
        System.assertEquals(oh.opp.Amount, payments.get(0).npe01__Payment_Amount__c);
        System.assertEquals(0, oh.opp.npe01__Payments_Made__c);
        System.assertEquals(openStage, oh.opp.StageName);

        payments.get(0).npe01__Written_Off__c = true;

        Test.startTest();

        update payments;

        Test.stopTest();

        oh.refresh();

        System.assertEquals(0, oh.opp.npe01__Payments_Made__c);
        System.assertEquals(openStage, oh.opp.StageName);
    }

    private static testMethod void testOpportunityWillNotAutoCloseClosesIfAmountSetToZero() {
        String closedWonStage = UTIL_UnitTestData_TEST.getClosedWonStage();
        String openStage = UTIL_UnitTestData_TEST.getOpenStage();

        if (String.isEmpty(closedWonStage) || String.isEmpty(openStage)) {
            System.debug(
                System.LoggingLevel.ERROR,
                'Organization settings do not contain the necessary opportunity stages to run this test. Not running test.'
            );
            return;
        }

        UTIL_CustomSettingsFacade.getContactsSettings().Payments_Auto_Close_Stage_Name__c = closedWonStage;

        OpportunityHelper oh = new OpportunityHelper(openStage);
        oh.refresh();

        oh.deletePayments();
        oh.refresh();

        System.assertEquals(0, oh.getPayments().size());
        System.assertEquals(openStage, oh.opp.StageName);

        Test.startTest();

        oh.opp.Amount = 0;
        update oh.opp;

        Test.stopTest();

        oh.refresh();

        System.assertEquals(0, oh.opp.Amount);
        System.assertEquals(oh.opp.Amount, oh.opp.npe01__Payments_Made__c);
        System.assertEquals(openStage, oh.opp.StageName);
    }

    class OpportunityHelper {
        public Opportunity opp;
        public OpportunityHelper(String initialStageName) {
            opp = new Opportunity(
                Name = 'Test Opportunity',
                Amount = 500,
                CloseDate = Date.today(),
                StageName = initialStageName
            );
            insert opp;
        }
        public void refresh() {
            opp = [
                SELECT
                    Name,
                    Amount,
                    StageName,
                    IsClosed,
                    IsWon,
                    npe01__Number_Of_Payments__c,
                    npe01__Payments_Made__c,
                    (
                        SELECT
                            npe01__Paid__c,
                            npe01__Payment_Amount__c
                        FROM npe01__OppPayment__r
                    )
                FROM Opportunity
                WHERE Id = :this.opp.Id
            ];
        }
        public List<npe01__OppPayment__c> getPayments() {
            return opp.npe01__OppPayment__r;
        }
        public void deletePayments() {
            delete getPayments();
        }
        public void setPayments(List<npe01__OppPayment__c> payments) {
            for (npe01__OppPayment__c payment : payments) {
                payment.npe01__Opportunity__c = opp.Id;
            }
            upsert payments;
        }
    }
    /*********************************************************************************************************
    * @description
    operation:
        Create a number of opportunities without payment records, run batch payment creation process.
    verify:
        Excluded opps don't get payments.
        Included opps payments are created correctly.
    **********************************************************************************************************/            
    static testMethod void testBatchPaymentCreation() {
        if (strTestOnly != '*' && strTestOnly != 'testBatchPaymentCreation') return;
        
        npe01__Contacts_And_Orgs_Settings__c consSettings = new npe01__Contacts_And_Orgs_Settings__c (
            npe01__Payments_Enabled__c= true, 
            Opp_Types_Excluded_for_Payments__c='NO-PAYMENT'
        );
        
        Id rtIdGift = null;
        
        //only attempt a recordtype exclusion if we have a record type that isn't default
        for (string recTypeName : new list<string>{UTIL_RecordTypes.getrecordTypeNameForGiftsTests(Opportunity.sObjectType),UTIL_RecordTypes.getrecordTypeNameForMembershipTests(Opportunity.sObjectType)}) {
            if (!string.isBlank(recTypeName) && 
                UTIL_RecordTypes.isRecordTypeActive(Opportunity.sObjectType, recTypeName) && 
                !UTIL_RecordTypes.isRecordTypeDefault(Opportunity.sObjectType, recTypeName)) {

                rtIdGift = UTIL_RecordTypes.GetRecordTypeId(Opportunity.sObjectType, recTypeName);
                consSettings.Opp_RecTypes_Excluded_for_Payments__c=rtIdGift;
            }
        }
        
        UTIL_CustomSettingsFacade.getContactsSettingsForTests(consSettings);

        //create a Payment Field Mapping
        npe01__Payment_Field_Mapping_Settings__c pfmNew = new npe01__Payment_Field_Mapping_Settings__c(
            npe01__Opportunity_Field__c = 'Name',
            npe01__Payment_Field__c = 'npe01__Custom_Payment_Field__c',
            Name = 'test field mapping'
        );
        insert pfmNew;
        
        //create test data
        Account acct = new Account (
            Name='Test Account'
        );
        insert acct;

        list<Opportunity> testOppList = new list<Opportunity>();
        testOppList.add(new Opportunity (
            Name='Paid Payment',
            AccountId = acct.id,
            Amount = 8,
            CloseDate = date.today(), 
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage()
        ));
        testOppList.add(new Opportunity (
            Name='Unpaid Payment',
            AccountId = acct.id,
            Amount = 36,
            CloseDate = date.today(), 
            StageName = UTIL_UnitTestData_TEST.getOpenStage()
        ));
        testOppList.add(new Opportunity (
            Name='No payment: Closed Lost',
            AccountId = acct.id,
            Amount = 36,
            CloseDate = date.today(), 
            StageName = UTIL_UnitTestData_TEST.getClosedLostStage()
        ));
        testOppList.add(new Opportunity (
            Name='No payment: Zero amount',
            AccountId = acct.id,
            Amount = 0,
            CloseDate = date.today(), 
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage()
        ));
        testOppList.add(new Opportunity (
            Name='No payment: Excluded type',
            AccountId = acct.id,
            Amount = 1,
            CloseDate = date.today(), 
            Type = 'NO-PAYMENT',
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage()
        ));
        testOppList.add(new Opportunity (
            Name='No payment: npe01__Do_Not_Automatically_Create_Payment__c',
            AccountId = acct.id,
            Amount = 2,
            CloseDate = date.today(), 
            npe01__Do_Not_Automatically_Create_Payment__c = true,
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage()
        ));
        Opportunity opp = new Opportunity (
            Name='No pmt:rectype',
            AccountId = acct.id,
            Amount = 3,
            CloseDate = date.today(), 
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage()
        );
        if (rtIdGift != null)
            opp.RecordtypeId = rtIdGift;
        testOppList.add(opp);

        //disable TDTM so payments aren't created
        TDTM_TriggerHandler.disableTDTM = true;
        insert(testOppList);
        TDTM_TriggerHandler.disableTDTM = false;

        list<npe01__OppPayment__c> queryPmt = [select id,npe01__paid__c, npe01__payment_amount__c from npe01__OppPayment__c WHERE npe01__opportunity__c IN :testOppList];
        system.assertEquals(0, queryPmt.size(), 'No payments should be created as triggers are disabled.');

        test.startTest();
        id batchProcessId = database.executeBatch(new PMT_PaymentCreator_BATCH(), 200);
        test.stopTest();

        queryPmt = [select id, npe01__paid__c, npe01__payment_amount__c, npe01__Custom_Payment_Field__c from npe01__OppPayment__c WHERE npe01__opportunity__c IN :testOppList ORDER BY npe01__opportunity__c];

        if (rtIdGift != null) {
            system.assertEquals(2, queryPmt.size(), 'Two payments should be created by the batch process.');
        } else {
            system.assertEquals(3, queryPmt.size(), 'Three payments should be created by the batch process if opp record type is not available for filtering.');
        }

        system.assertEquals(true, queryPmt[0].npe01__Paid__c, 'Payment for closed/won opportunity should be marked paid.');
        system.assertEquals('Paid Payment', queryPmt[0].npe01__Custom_Payment_Field__c, 'Custom field mapping should be copied over.');
        system.assertEquals(false, queryPmt[1].npe01__Paid__c, 'Payment for open opportunity should not be marked paid.');
        system.assertEquals('Unpaid Payment', queryPmt[1].npe01__Custom_Payment_Field__c, 'Custom field mapping should be copied over.');
    }
}