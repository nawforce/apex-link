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
* @group Allocations
* @group-content ../../ApexDocContent/Allocations.htm
* @description Tests specific to Allocations trigger based functionality. 
*/
@isTest
private with sharing class ALLO_Allocations_TEST {
    
    /*********************************************************************************************************
    * @description If you only want to run a single test in this class, fill in its name here.
    * If you want to run all tests, then use '*'
    */
    private static string strTestOnly = '*';
    
    /*******************************************************************************************************
    * @description Setup of allocations settings.
    * @param alloset An instance of Allocations_Settings__c custom setting. Calling this method with a null
    * parameter uses default settings.
    * @return void Modifies settings, returns nothing.
    ********************************************************************************************************/
    private static void setupSettings (Allocations_Settings__c alloset) {
        //set running user to avoid dml issue            
        System.runAs(new User(id = UserInfo.getUserId())){
            if (alloset!=null)
                UTIL_CustomSettingsFacade.getAllocationsSettingsForTests(alloset);
            else 
                UTIL_CustomSettingsFacade.getAllocationsSettingsForTests(new Allocations_Settings__c());

        }
    }

    /*******************************************************************************************************
    * @description When changing the amount of an Opportunity with Allocations: 
    * Increasing the amount will adjust the percentage allocations.
    * Decreasing the amount so that the total allocation amount is below the opportunity amount will adjust 
    * the percentage allocations.
    * Decreasing the amount so that the total allocation amount is greater than the opportunity amount, an 
    * error is thrown and the opportunity update is blocked.
    ********************************************************************************************************/
    static testMethod void oppAmountChange() {
        if (strTestOnly != '*' && strTestOnly != 'oppAmountChange') return;
        setupSettings(null);

        Account acc = new Account(Name='foo');
        insert acc;
        Opportunity opp = new Opportunity(Name='foo', Amount = 8, AccountID=acc.id, CloseDate=system.today(), StageName=UTIL_UnitTestData_TEST.getClosedWonStage());
        insert opp;
        General_Accounting_Unit__c gau = new General_Accounting_Unit__c(Name='foo');
        insert gau;

        //resetting these manually as a test is considered a single context for static variables
        ALLO_Allocations_TDTM.resetFlags(false);
        
        list<Allocation__c> alloForInsert = new list<Allocation__c>();

        Allocation__c percentAllo = new Allocation__c(Opportunity__c=opp.id, Percent__c = 50, General_Accounting_Unit__c = gau.id);
        alloForInsert.add(percentAllo);
        Allocation__c amountAllo = new Allocation__c(Opportunity__c=opp.id, Amount__c = 4, General_Accounting_Unit__c = gau.id);
        alloForInsert.add(amountAllo);
        insert alloForInsert;

        list<Allocation__c> queryAllo = [SELECT Id, Percent__c, Amount__c FROM Allocation__c ORDER BY Percent__c];
        system.assertEquals(50, queryAllo[1].Percent__c, 'The percentage allocation should be 50%.');
        system.assertEquals(4, queryAllo[1].Amount__c, 'The amount of the percentage allocation should be calculated based on the Opportunity amount.');
        system.assertEquals(4, queryAllo[0].Amount__c, 'The amount of the standard number allocation should be 4.');
        
        ALLO_Allocations_TDTM.resetFlags(false);
        opp.Amount = 10;
        Test.startTest();
        update opp;
        Test.stopTest();
        
        queryAllo = [SELECT Id, Percent__c, Amount__c FROM Allocation__c ORDER BY Percent__c];
        system.assertEquals(5, queryAllo[1].Amount__c, 'The amount of the percentage allocation should increase proportionately with the Opportunity amount.');
        system.assertEquals(4, queryAllo[0].Amount__c, 'The amount of the standard number allocation should be unchanged at 4.');
        
        ALLO_Allocations_TDTM.resetFlags(false);
        opp.Amount = 8;
        update opp;

        queryAllo = [SELECT Id, Percent__c, Amount__c FROM Allocation__c ORDER BY Percent__c];
        system.assertEquals(4, queryAllo[1].Amount__c, 'The amount of the percentage allocation should decrease proportionately with the Opportunity amount.');
        system.assertEquals(4, queryAllo[0].Amount__c, 'The amount of the standard number allocation should be unchanged at 4.');

        ALLO_Allocations_TDTM.resetFlags(false);
        opp.Amount = 1;
        
        Exception ex = null;
        try {
            update opp;
        } catch (Exception e) {
            ex = e;
        }
        system.assertNotEquals(null,ex,'An exception should be caught.');
        //system.assert(ex.getMessage().contains(), ex.getMessage());

        list<Opportunity> queryOpps = [SELECT Id, Amount FROM opportunity WHERE Id = :opp.id];
        system.assertEquals(8, queryOpps[0].Amount, 'The opportunity amount should remain unchanged after the exception is caught.');
    }

    /*******************************************************************************************************
    * @description With default General Accounting Units enabled, creating an Opportunity automatically 
    * creates a default allocation for the full amount of the opportunity.
    * Creating a new allocation for that opportunity decreases the default fund allocation.
    * Creating an allocation for the total amount deletes the default allocation.
    ********************************************************************************************************/
    static testMethod void defaultAllocations() {
        if (strTestOnly != '*' && strTestOnly != 'defaultAllocations') return;
        
        General_Accounting_Unit__c defaultGau = new General_Accounting_Unit__c(Name='General');
        insert defaultGau;

        setupSettings(new Allocations_Settings__c(Default_Allocations_Enabled__c = true, Default__c = defaultGau.id));

        Account acc = new Account(Name='foo');
        insert acc;
        Opportunity opp = new Opportunity(Name='foo', Amount = 8, AccountID=acc.id, CloseDate=system.today(), StageName=UTIL_UnitTestData_TEST.getClosedWonStage());
        insert opp;

        General_Accounting_Unit__c gau = new General_Accounting_Unit__c(Name='foo');
        insert gau;

        list<Allocation__c> queryAllo = [SELECT Id, Percent__c, Amount__c FROM Allocation__c ORDER BY Percent__c];
        system.assertEquals(1,queryAllo.size(), 'An allocation to the default General Accounting Unit should be created.');
        system.assertEquals(8,queryAllo[0].Amount__c, 'The default allocation should be for the total amount of the Opportunity.');

        //resetting these manually as a test is considered a single context for static variables
        ALLO_Allocations_TDTM.resetFlags(false);
        Allocation__c amountAllo = new Allocation__c(Opportunity__c=opp.id, Amount__c = 6, General_Accounting_Unit__c = gau.id);
        Test.startTest();
        insert amountAllo;
        Test.stopTest();

        queryAllo = [SELECT Id, Percent__c, Amount__c FROM Allocation__c ORDER BY Amount__c];
        system.debug('LOOK queryAllo' + queryAllo);
        system.assertEquals(2,queryAllo[0].Amount__c, 'The default allocation should be equal to the remainder of the total Opp amount after removing the new Allocation amount.');

        ALLO_Allocations_TDTM.resetFlags(false);
        amountAllo.Amount__c = 8;
        update amountAllo;

        queryAllo = [SELECT Id, Percent__c, Amount__c FROM Allocation__c ORDER BY Amount__c];
        system.assertEquals(1,queryAllo.size(), 'The default fund allocation should be deleted.');
    }

    /*******************************************************************************************************
    * @description Campaign Allocations test:
    * Opportunity created with campaign allocation: allocations are created, default allocation covers the
    * remainder.
    * Opportunity created where campaign allocations exceed opp amount: exception thrown, only default 
    * allocation created
    ********************************************************************************************************/
    static testMethod void campaignAllocations() {
        if (strTestOnly != '*' && strTestOnly != 'campaignAllocations') return;
        
        General_Accounting_Unit__c defaultgau = new General_Accounting_Unit__c(Name='General');
        insert defaultgau;

        setupSettings(new Allocations_Settings__c(Default_Allocations_Enabled__c = true, Default__c = defaultGau.id));

        Account acc = new Account(Name='foo');
        insert acc;
        General_Accounting_Unit__c campaigngau = new General_Accounting_Unit__c(Name='foo');
        insert campaigngau;
        Campaign cam = new Campaign(Name='Foo', IsActive=true);
        insert cam;

        list<Allocation__c> alloForInsert = new list<Allocation__c>();
        Allocation__c percentAllo = new Allocation__c(Campaign__c=cam.id, Percent__c = 50, General_Accounting_Unit__c = campaigngau.id);
        alloForInsert.add(percentAllo);
        Allocation__c amountAllo = new Allocation__c(Campaign__c=cam.id, Amount__c = 4, General_Accounting_Unit__c = campaigngau.id);
        alloForInsert.add(amountAllo);
        insert alloForInsert;

        ALLO_Allocations_TDTM.resetFlags(false);
        Opportunity opp = new Opportunity(Name='success', Amount = 10, AccountID=acc.id, CampaignId=cam.id, CloseDate=system.today(), StageName=UTIL_UnitTestData_TEST.getClosedWonStage());
        Test.startTest();
        insert opp;
        Test.stopTest();

        //verify campaign allocation and default allocation created
        list<Allocation__c> queryAllo = [SELECT Id, General_Accounting_Unit__c, General_Accounting_Unit__r.Name, Amount__c, Percent__c FROM Allocation__c WHERE Opportunity__c=:opp.id ORDER BY Amount__c];
        system.assertEquals(3,queryAllo.size(), 'An allocation for the Campaign Opportunity should be created.');
        system.assertEquals(defaultgau.id, queryAllo[0].General_Accounting_Unit__c, 'A default allocation should be created.');
        system.assertEquals(1, queryAllo[0].Amount__c, 'The default allocation should cover the remainder of the amount.');
        system.assertEquals(campaigngau.id, queryAllo[1].General_Accounting_Unit__c, 'An allocation mirroring the Campaign Allocation should be created.');
        system.assertEquals(4, queryAllo[1].Amount__c, 'The allocation amount should mirror the Campaign allocation, and the percentage should be calculated based on the opportunity amount.');
        system.assertEquals(campaigngau.id, queryAllo[2].General_Accounting_Unit__c, 'An allocation mirroring the Campaign Allocation should be created.');
        system.assertEquals(5, queryAllo[2].Amount__c, 'The allocation amount should mirror the Campaign allocation.');

        ALLO_Allocations_TDTM.resetFlags(false);
        Opportunity opp2 = new Opportunity(Name='error', Amount = 6, AccountID=acc.id, CampaignId=cam.id, CloseDate=system.today(), StageName=UTIL_UnitTestData_TEST.getClosedWonStage());
        insert opp2;

        //verify percent allocation created
        queryAllo = [SELECT Id, Amount__c FROM Allocation__c WHERE Opportunity__c=:opp2.id ORDER BY Percent__c];
        system.assertEquals(2, queryAllo.size(), 'Only the percent based allocation should be created in the case where the allocation amount exceeded the Opportunity amount.');
        system.assertEquals(3, queryAllo[0].Amount__c, 'The percent allocation should should equal half the opportunity amount.');
        system.assertEquals(3, queryAllo[1].Amount__c, 'The default allocation should cover the remainder of the amount.');

    }

    /*******************************************************************************************************
    * @description Recurring Donation Allocations test:
    * Create recurring donation. Pledged opportunity is generated automatically with a default allocation.
    * Create allocations for the recurring donation, verify opp allocations are created, default opp 
    * allocation is deleted.
    * Set opp to closed/won, modify recurring donation allocations: closed opportunity's allocations are 
    * unchanged.
    * Create new opportunity for the recurring donation schedule: RD allocations are created.
    * Create a recurring donation allocation to exceed the opportunity amount: open opportunity only gets 
    * percent based allocations.
    * Remove allocation that exceed's opportunity amount: all RD allocations are created.
    * Delete a recurring donation allocation: corresponding opportunity allocation is also deleted.
    ********************************************************************************************************/
    static testMethod void recurringDonationsValidation() {
        if (strTestOnly != '*' && strTestOnly != 'recurringDonationsValidation') return;

        General_Accounting_Unit__c defaultgau = new General_Accounting_Unit__c(Name='General');
        insert defaultgau;

        setupSettings(new Allocations_Settings__c(Default_Allocations_Enabled__c = true, Default__c = defaultGau.id));

        Account acc = new Account(Name='foo');
        insert acc;
        General_Accounting_Unit__c gau = new General_Accounting_Unit__c(Name = 'foo');
        insert gau;
        npe03__Recurring_Donation__c rd = new npe03__Recurring_Donation__c(
            Name = 'foo', 
            npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodYearly, 
            npe03__Date_Established__c= system.today(),  
            npe03__Organization__c = acc.id, 
            npe03__Amount__c = 20,
            npe03__Schedule_Type__c = system.label.npe03.RecurringDonationMultiplyValue,
            npe03__Open_Ended_Status__c = system.label.npe03.RecurringDonationOpenStatus
        );
        // this initial creation of the RD with default allocations set up, which get copied to the opp
        // will be what we soql limit profile in this test (and thus the startTest/stopTest).
        Test.startTest();
        insert rd;
        Test.stopTest();
        
        //query for the opportunity automatically created by the schedule
        list<Opportunity> queryOpp = [SELECT Id, npe03__Recurring_Donation__c, Amount, CloseDate, StageName FROM Opportunity WHERE npe03__Recurring_Donation__c = :rd.id];
        system.assertEquals(1,queryOpp.size(),'A single opportunity should be created.');

        list<Allocation__c> queryAllo = [SELECT Id, Amount__c, General_Accounting_Unit__c FROM Allocation__c WHERE Opportunity__c = :queryOpp[0].id];
        system.assertEquals(1,queryAllo.size(),'A single default allocation should be created.');
        system.assertEquals(defaultgau.id,queryAllo[0].General_Accounting_Unit__c,'The allocation should be assigned to the default General Accounting Unit.');
        system.assertEquals(20,queryAllo[0].Amount__c,'The default allocation should be for the total amount of the Opportunity.');
        
        ALLO_Allocations_TDTM.resetFlags(false);

        list<Allocation__c> alloForInsert = new list<Allocation__c>();
        Allocation__c percentAllo = new Allocation__c(Recurring_Donation__c = rd.id, Percent__c = 50, General_Accounting_Unit__c = gau.id);
        alloForInsert.add(percentAllo);
        Allocation__c amountAllo = new Allocation__c(Recurring_Donation__c = rd.id, Amount__c = 10, General_Accounting_Unit__c = gau.id);
        alloForInsert.add(amountAllo);
        insert alloForInsert;

        queryAllo = [SELECT Id, Amount__c, Percent__c, General_Accounting_Unit__c FROM Allocation__c WHERE Opportunity__c = :queryOpp[0].id ORDER BY Percent__c];
        system.assertEquals(2,queryAllo.size(), 'Allocations for the open Recurring Donation Opportunity should be created automatically after they are created for the Recurring Donation.');
        system.assertEquals(gau.id,queryAllo[0].General_Accounting_Unit__c, 'Allocations should be to the non-default GAU.');
        system.assertEquals(gau.id,queryAllo[1].General_Accounting_Unit__c, 'Allocations should be to the non-default GAU.');
        system.assertEquals(10,queryAllo[1].Amount__c, 'The percentage based allocation amount should be set based on the opportunity amount.');

        queryOpp[0].StageName = UTIL_UnitTestData_TEST.getClosedWonStage();
        update queryOpp[0];

        ALLO_Allocations_TDTM.resetFlags(false);

        percentAllo.Percent__c = 20;
        update percentAllo;
        queryAllo = [SELECT Id, Amount__c, Percent__c, General_Accounting_Unit__c FROM Allocation__c WHERE Opportunity__c = :queryOpp[0].id ORDER BY Percent__c];
        system.assertEquals(2,queryAllo.size(), 'Allocations for the closed/won opportunity should be unchanged.');
        system.assertEquals(50,queryAllo[1].Percent__c, 'The percentage based allocation should be unchanged.');

        ALLO_Allocations_TDTM.resetFlags(false);
        Opportunity opp = new Opportunity(Name='foo', npe03__Recurring_Donation__c = rd.id, Amount = 20, AccountID=acc.id, CloseDate=system.today(), StageName=UTIL_UnitTestData_TEST.getOpenStage());
        insert opp;

        queryAllo = [SELECT Id, Amount__c, Percent__c, General_Accounting_Unit__c FROM Allocation__c WHERE Opportunity__c = :opp.id ORDER BY Amount__c];
        system.assertEquals(3,queryAllo.size(), 'Allocations for the open Recurring Donation Opportunity should be created automatically when the opportunity is created.');

        ALLO_Allocations_TDTM.resetFlags(false);
        Allocation__c exceedAllo = new Allocation__c(Recurring_Donation__c = rd.id, Amount__c = 10, General_Accounting_Unit__c = gau.id);
        insert exceedAllo;
        
        queryAllo = [SELECT Id, Amount__c, Percent__c, General_Accounting_Unit__c FROM Allocation__c WHERE Opportunity__c = :opp.id ORDER BY Percent__c];
        system.debug('Look:' + queryAllo);
        system.assertEquals(2,queryAllo.size(), 'Only percent based Opportunity Allocation should exist.');
        system.assertEquals(16,queryAllo[0].Amount__c, 'The percentage based allocation amount should be set based on the opportunity amount.');

        ALLO_Allocations_TDTM.resetFlags(false);
        delete exceedAllo;

        queryAllo = [SELECT Id, Amount__c, Percent__c, General_Accounting_Unit__c FROM Allocation__c WHERE Opportunity__c = :opp.id ORDER BY Amount__c];
        system.assertEquals(3,queryAllo.size(), 'Allocations for the open Recurring Donation Opportunity should be created automatically when the opportunity is created.');

        ALLO_Allocations_TDTM.resetFlags(false);
        delete amountAllo;

        queryAllo = [SELECT Id, Amount__c, Percent__c, General_Accounting_Unit__c FROM Allocation__c WHERE Opportunity__c = :opp.id ORDER BY Percent__c];
        system.assertEquals(2,queryAllo.size(), 'Opportunity Allocation should be deleted along with the Recurring Donation allocation.');
        system.assertEquals(16,queryAllo[0].Amount__c, 'The percentage based allocation amount should be set based on the opportunity amount.');

    }
    
    /*******************************************************************************************************
    * @description Create a campaign with an allocation, and 100 opportunities, half of which are attributed
    * to the campaign. Verifies that default allocations and campaign allocations are created.
    * Updates all opportunity amounts to be equal to the campaign allocation amount, verifies that default
    * allocations were deleted when no longer needed.
    ********************************************************************************************************/
    static testMethod void bulkOppInsert() {
        if (strTestOnly != '*' && strTestOnly != 'bulkOppInsert') return;

        General_Accounting_Unit__c defaultgau = new General_Accounting_Unit__c(Name='General');
        insert defaultgau;

        setupSettings(new Allocations_Settings__c(Default_Allocations_Enabled__c = true, Default__c = defaultGau.id));

        Account acc = new Account(Name='foo');
        insert acc;
        General_Accounting_Unit__c campaigngau = new General_Accounting_Unit__c(Name='Campaign Allocation');
        insert campaigngau;
        Campaign cam = new Campaign(Name='Foo', IsActive=true);
        insert cam;

        list<Allocation__c> alloForInsert = new list<Allocation__c>();
        Allocation__c percentAllo = new Allocation__c(Campaign__c=cam.id, Amount__c = 4, General_Accounting_Unit__c = campaigngau.id);
        alloForInsert.add(percentAllo);
        insert alloForInsert;

        ALLO_Allocations_TDTM.resetFlags(false);
        list<Opportunity> oppList = new list<Opportunity>();
        for (integer i=0;i<100;i++)
            oppList.add(new Opportunity(Name='opp'+i, Amount = 8, AccountID=acc.id, CampaignId=math.mod(i,2)==0?cam.id:null, CloseDate=system.today(), StageName=UTIL_UnitTestData_TEST.getClosedWonStage()));
        Test.startTest();
        insert oppList;
        Test.stopTest();

        list<AggregateResult> queryAggResult = [SELECT count_distinct(Id), General_Accounting_Unit__r.Name FROM Allocation__c WHERE Opportunity__r.Name LIKE 'opp%' GROUP BY General_Accounting_Unit__r.Name ORDER BY count_distinct(Id)];
        system.assertEquals(50, queryAggResult[0].get('expr0'), 'Fifty campaign allocations should be created.');
        system.assertEquals(100, queryAggResult[1].get('expr0'), 'One hundred default allocations should be created.');

        for (Opportunity opp : oppList)
            opp.Amount = 4;

        ALLO_Allocations_TDTM.resetFlags(false);
        update oppList;

        queryAggResult = [SELECT count_distinct(Id), General_Accounting_Unit__r.Name FROM Allocation__c WHERE Opportunity__r.Name LIKE 'opp%' GROUP BY General_Accounting_Unit__r.Name ORDER BY count_distinct(Id)];
        system.assertEquals(50, queryAggResult[0].get('expr0'), 'Fifty campaign allocations should still exist.');
        system.assertEquals(50, queryAggResult[1].get('expr0'), 'Fifty default allocations should remain.');

    }

    /*******************************************************************************************************
    * @description Inserts opportunities which excluded types and record types. Verifies no default 
    * allocations are created.
    ********************************************************************************************************/
    static testMethod void testExcludedOpps() {
        if (strTestOnly != '*' && strTestOnly != 'testExcludedOpps') return;

        General_Accounting_Unit__c defaultgau = new General_Accounting_Unit__c(Name='General');
        insert defaultgau;

        string rtname = UTIL_RecordTypes.getRecordTypeNameForGiftsTests(Opportunity.sObjectType);
        id rtid = UTIL_RecordTypes.GetRecordTypeId(Opportunity.sObjectType,rtname);

        setupSettings(new Allocations_Settings__c(Default_Allocations_Enabled__c = true, Default__c = defaultGau.id, Excluded_Opp_Types__c='foo;bar', Excluded_Opp_RecTypes__c=rtid));
        Account acc = new Account(Name='foo');
        insert acc;

        list<Opportunity> oppForInsert = new list<Opportunity>();
        oppForInsert.add(new Opportunity(Name='excluded type', Type='foo', Amount = 10, AccountID=acc.id, CloseDate=system.today(), StageName=UTIL_UnitTestData_TEST.getClosedWonStage()));
        //only test the record type exclusion if one exists.
        if (rtid!=null)
            oppForInsert.add(new Opportunity(Name='excluded record type', RecordTypeId=rtid, Amount = 10, AccountID=acc.id, CloseDate=system.today(), StageName=UTIL_UnitTestData_TEST.getClosedWonStage()));
        Test.startTest();
        insert oppForInsert;
        Test.stopTest();

        list<Allocation__c> queryAllo = [SELECT Id, Amount__c, General_Accounting_Unit__c FROM Allocation__c];
        system.assertEquals(0,queryAllo.size(), 'No default allocation should be created for an excluded opportunity type or an excluded opportunity record type.');

    }

    /*******************************************************************************************************
    * @description Inserts an opportunity with a null amount, verifies no default allocation is created.
    * Updates the amount, verifies default allocation was created. Nulls out the amount, verifies the 
    * default allocation was deleted.
    ********************************************************************************************************/
    static testMethod void testOppNullAmount() {
        if (strTestOnly != '*' && strTestOnly != 'testOppNullAmount') return;

        General_Accounting_Unit__c defaultgau = new General_Accounting_Unit__c(Name='General');
        insert defaultgau;

        setupSettings(new Allocations_Settings__c(Default_Allocations_Enabled__c = true, Default__c = defaultGau.id));
        Account acc = new Account(Name='foo');
        insert acc;

        Opportunity opp = new Opportunity(Name='foo', Amount = null, AccountID=acc.id, CloseDate=system.today(), StageName=UTIL_UnitTestData_TEST.getClosedWonStage());
        Test.startTest();
        insert opp;
        Test.stopTest();

        list<Allocation__c> queryAllo = [SELECT Id, Amount__c, General_Accounting_Unit__c FROM Allocation__c WHERE Opportunity__c = :opp.id];
        system.assertEquals(0,queryAllo.size(),'No default allocation should be created for an opporunity with a null amount.');

        ALLO_Allocations_TDTM.resetFlags(false);
        opp.Amount = 8;
        update opp;

        queryAllo = [SELECT Id, Amount__c, General_Accounting_Unit__c FROM Allocation__c WHERE Opportunity__c = :opp.id];
        system.assertEquals(1,queryAllo.size(),'A default allocation should be created for the opporunity that now has an amount.');

        ALLO_Allocations_TDTM.resetFlags(false);
        opp.Amount = null;
        update opp;

        queryAllo = [SELECT Id, Amount__c, General_Accounting_Unit__c FROM Allocation__c WHERE Opportunity__c = :opp.id];
        system.assertEquals(0,queryAllo.size(),'A default allocation should be created for the opporunity that now has an amount.');
    }

    /*******************************************************************************************************
    * @description Creates a campaign allocation, marks that allocation's GAU as inactive. Insert an
    * opportunity assigned to the campaign, verify only default allocation is created and no error is
    * thrown. Deactivates the opportunity allocation's GAU, updates the opportunity amount, verifies the 
    * allocation to the inactive GAU isn't modified.
    ********************************************************************************************************/
    static testMethod void testInactiveGAU() {
        if (strTestOnly != '*' && strTestOnly != 'testInactiveGAU') return;

        setupSettings(null);

        Account acc = new Account(Name='foo');
        insert acc;

        list<General_Accounting_Unit__c> listGau = new list<General_Accounting_Unit__c>();
        listGau.add(new General_Accounting_Unit__c(Name='Campaign Allocation 1'));
        listGau.add(new General_Accounting_Unit__c(Name='Campaign Allocation 2'));
        insert listGau;

        Campaign cam = new Campaign(Name='Foo', IsActive=true);
        insert cam;

        list<Allocation__c> listAllo = new list<Allocation__c>();
        listAllo.add(new Allocation__c(Campaign__c=cam.id, Amount__c = 3, General_Accounting_Unit__c = listGau[0].id));
        listAllo.add(new Allocation__c(Campaign__c=cam.id, Percent__c = 50, General_Accounting_Unit__c = listGau[1].id));
        insert listAllo;

        listGau[0].Active__c = false;
        update listGau[0];

        ALLO_Allocations_TDTM.resetFlags(false);
        Opportunity opp = new Opportunity(Name='foo', CampaignId=cam.id, Amount = 8, AccountID=acc.id, CloseDate=system.today(), StageName=UTIL_UnitTestData_TEST.getClosedWonStage());
        Test.startTest();
        insert opp;
        Test.stopTest();

        list<Allocation__c>  queryAllo = [SELECT Id, Amount__c, Percent__c, General_Accounting_Unit__c FROM Allocation__c WHERE Opportunity__c = :opp.id];
        system.assertEquals(1,queryAllo.size(), 'A single allocation should be created.');
        system.assertEquals(listGau[1].id, queryAllo[0].General_Accounting_Unit__c, 'The single allocation should be assigned to the active GAU.');
        system.assertEquals(50, queryAllo[0].Percent__c, 'The allocation should be percent based.');

        listGau[1].Active__c = false;
        update listGau[1];

        ALLO_Allocations_TDTM.resetFlags(false);
        opp.Amount = 80;

        try {
            update opp;
        } catch(exception e) {
            // we know the update will fail, but lets make sure the allocations are not changed.
        }

        queryAllo = [SELECT Id, Amount__c, Percent__c, General_Accounting_Unit__c FROM Allocation__c WHERE Opportunity__c = :opp.id];
        system.assertEquals(4,queryAllo[0].Amount__c, 'Allocation to inactive GAU should not be modified.');
        
    }

    /*******************************************************************************************************
    * @description Tests the ALLO_MakeDefaultAllocations_BATCH class. Creates two opportunities, one without
    * allocations and one with an allocation. Runs batch class. Verifies two default allocations are created;
    * one for the total amount, and one for the unallocated remainder.
    ********************************************************************************************************/
    static testMethod void testMakeDefaultAllocations() {
        if (strTestOnly != '*' && strTestOnly != 'testMakeDefaultAllocations') return;
        
        General_Accounting_Unit__c defaultGau = new General_Accounting_Unit__c(Name='General');
        insert defaultGau;

        General_Accounting_Unit__c otherGau = new General_Accounting_Unit__c(Name='Other');
        insert otherGau;
        
        setupSettings(new Allocations_Settings__c(Default_Allocations_Enabled__c = true, Default__c = defaultGau.id));

        //setting flags to not create default allocations for the new opportunities
        ALLO_Allocations_TDTM.resetFlags(true);

        Account acc = new Account(Name='foo');
        insert acc;
        Opportunity opp1 = new Opportunity(Name='foo', Amount = 8, AccountID=acc.id, CloseDate=system.today(), StageName=UTIL_UnitTestData_TEST.getClosedWonStage());
        Opportunity opp2 = new Opportunity(Name='foo2', Amount = 8, AccountID=acc.id, CloseDate=system.today(), StageName=UTIL_UnitTestData_TEST.getClosedWonStage());
        insert opp1;
        insert opp2;

        list<Allocation__c>  queryAllo = [SELECT Id, Amount__c, Percent__c, General_Accounting_Unit__c FROM Allocation__c];
        system.assertEquals(0,queryAllo.size(), 'No default allocation should be created.');

        Allocation__c nonDefaultAllo = new Allocation__c(Opportunity__c=opp1.id, Amount__c = 4, General_Accounting_Unit__c = otherGau.id);
        insert nonDefaultAllo;

        Test.startTest();
        id batchProcessId = database.executeBatch(new ALLO_MakeDefaultAllocations_BATCH());
        Test.stopTest();

        queryAllo = [SELECT Id, Amount__c, Percent__c, General_Accounting_Unit__c FROM Allocation__c WHERE Opportunity__c = :opp1.id AND General_Accounting_Unit__c = :defaultGau.id];
        system.assertEquals(1,queryAllo.size(), 'A default allocation should be created.');
        system.assertEquals(4, queryAllo[0].Amount__c, 'The default allocation should be of the unallocated amount of the Opportunity.');

        queryAllo = [SELECT Id, Amount__c, Percent__c, General_Accounting_Unit__c FROM Allocation__c WHERE Opportunity__c = :opp2.id];
        system.assertEquals(1,queryAllo.size(), 'A default allocation should be created.');
        system.assertEquals(8, queryAllo[0].Amount__c, 'The default allocation should be of the total amount of the Opportunity.');
    }
}