/*
    Copyright (c) 2012, Salesforce.org
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
* @date 2012 (2.0)
* @group Recurring Donations
* @description Test class for the RecurringDonations class
*/
@isTest
private with sharing class RD_RecurringDonations_TEST2 {

    /*********************************************************************************************************
    * @description if you only want to run one test in this class, fill in its name here.
    * if you want to run all tests, then use '*'
    */
    private static string strTestOnly = '*';

    //test closing an opportunity for an open recurring donation
    static testMethod void closeOppForOpenEndedRecurringDonationYearly(){
        if (strTestOnly != '*' && strTestOnly != 'closeOppForOpenEndedRecurringDonationYearly') return;

        UTIL_CustomSettingsFacade.getRecurringDonationsSettingsForTest(
        new npe03__Recurring_Donations_Settings__c(
        npe03__Opportunity_Forecast_Months__c = 12,
        npe03__Maximum_Donations__c = 50,
        npe03__Open_Opportunity_Behavior__c = RD_RecurringDonations.RecurringDonationCloseOptions.Mark_Opportunities_Closed_Lost.name()        
        ));           
        
        Account a = new Account();
        a.Name = 'test Individual';
        insert a;
        
        Contact c = new Contact();
        c.FirstName = 'test';
        c.LastName = 'contact';
        c.AccountId = a.Id;
        insert c;       

        npe03__Recurring_Donation__c r1 = new npe03__Recurring_Donation__c();
        r1.Name = 'test';
        r1.npe03__Installments__c = 2;
        r1.npe03__Organization__c = a.id;
        r1.npe03__Amount__c = 100;
        r1.npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodYearly;
        r1.npe03__Date_Established__c = date.newinstance(1970,6,12);
        r1.npe03__Schedule_Type__c = system.label.npe03.RecurringDonationMultiplyValue;
        r1.npe03__Open_Ended_Status__c = system.label.npe03.RecurringDonationOpenStatus;
        r1.npe03__Next_Payment_Date__c = system.today();
        r1.OwnerId = system.Userinfo.getUserId();
        insert r1;
        
        list<Opportunity> originalOpps = new list<Opportunity>([select id, Name,amount,accountid,CloseDate from Opportunity where npe03__Recurring_Donation__c = :r1.id order by CloseDate]);
        system.assertEquals(1, originalOpps.size());          
        
        Opportunity o = originalOpps[0];
        string closedstage = [select masterlabel from opportunitystage where isActive = true and iswon = true and isClosed = true limit 1].masterlabel;
        o.StageName = closedstage;
        o.CloseDate = system.today();
        RD_ProcessControl.hasRun = false;
        Test.startTest();        
        //update opp
        update o;
        //call the update method
        RD_RecurringDonations.evaluateRecurringDonationsForNewOppInsert(new list<npe03__Recurring_Donation__c>{r1});
        Test.stopTest();
        
        
        npe03__Recurring_Donation__c rd = [select npe03__Last_Payment_Date__c from npe03__Recurring_Donation__c where id = :r1.id];     
        
        system.assertEquals(0, [select count() from Opportunity where npe03__Recurring_Donation__c = :r1.id and isClosed = false]);
        system.assertEquals(system.today(), rd.npe03__Last_Payment_Date__c);
    }
    
    static testMethod void closeOpenEndedRecurringDonationMarkOppsClosedLost(){
        if (strTestOnly != '*' && strTestOnly != 'closeOpenEndedRecurringDonationMarkOppsClosedLost') return;

        UTIL_CustomSettingsFacade.getRecurringDonationsSettingsForTest(
        new npe03__Recurring_Donations_Settings__c(
        npe03__Opportunity_Forecast_Months__c = 12,
        npe03__Maximum_Donations__c = 50,
        npe03__Open_Opportunity_Behavior__c = RD_RecurringDonations.RecurringDonationCloseOptions.Mark_Opportunities_Closed_Lost.name()        
        ));           
        
        Account a = new Account();
        a.Name = 'test Individual';
        insert a;
        
        Contact c = new Contact();
        c.FirstName = 'test';
        c.LastName = 'contact';
        c.AccountId = a.Id;
        insert c;       

        npe03__Recurring_Donation__c r1 = new npe03__Recurring_Donation__c();
        r1.Name = 'test';
        r1.npe03__Installments__c = 2;
        r1.npe03__Contact__c = c.Id;
        r1.npe03__Amount__c = 100;
        r1.npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodWeekly;
        r1.npe03__Date_Established__c = date.newinstance(1970,6,12);
        r1.npe03__Schedule_Type__c = system.label.npe03.RecurringDonationMultiplyValue;
        r1.npe03__Open_Ended_Status__c = system.label.npe03.RecurringDonationOpenStatus;
        r1.npe03__Next_Payment_Date__c = system.today().toStartOfMonth();
        insert r1;
        
        list<Opportunity> originalOpps = new list<Opportunity>([select id, Name,amount,accountid,CloseDate from Opportunity where npe03__Recurring_Donation__c = :r1.id order by CloseDate]);
        integer cOpp =  originalOpps.size();
        system.assert(cOpp == 52 || cOpp == 53);        
        RD_ProcessControl.hasRun = false;
        Test.startTest();
        r1.npe03__Open_Ended_Status__c = system.label.npe03.RecurringDonationClosedStatus;
        update r1;
        Test.stopTest();
        
        //assert all previously open opps are marked closed lost
        system.assertEquals(53, [select count() from Opportunity where isClosed = true and isWon = false and npe03__Recurring_Donation__c = :r1.id]);
        
    }
    
    static testMethod void updateRecurringDonationWithClosedOpp(){
        if (strTestOnly != '*' && strTestOnly != 'updateRecurringDonationWithClosedOpp') return;

        UTIL_CustomSettingsFacade.getRecurringDonationsSettingsForTest(
        new npe03__Recurring_Donations_Settings__c(
        npe03__Opportunity_Forecast_Months__c = 12,
        npe03__Maximum_Donations__c = 50, 
        npe03__Open_Opportunity_Behavior__c = RD_RecurringDonations.RecurringDonationCloseOptions.Mark_Opportunities_Closed_Lost.name()        
        ));           
        
        Account a = new Account();
        a.Name = 'test Individual';
        insert a;
        
        Contact c = new Contact();
        c.FirstName = 'test';
        c.LastName = 'contact';
        c.AccountId = a.Id;
        insert c;        

        npe03__Recurring_Donation__c r1 = new npe03__Recurring_Donation__c();
        r1.Name = 'test';
        r1.npe03__Installments__c = 2;
        r1.npe03__Contact__c = c.Id;
        r1.npe03__Amount__c = 100;
        r1.npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodWeekly;
        r1.npe03__Date_Established__c = date.newinstance(1970,6,12);
        r1.npe03__Schedule_Type__c = system.label.npe03.RecurringDonationMultiplyValue;
        r1.npe03__Open_Ended_Status__c = system.label.npe03.RecurringDonationOpenStatus;
        r1.npe03__Next_Payment_Date__c = system.today();
        insert r1;
        
        list<Opportunity> originalOpps = new list<Opportunity>([select id, Name,amount,accountid,CloseDate from Opportunity where npe03__Recurring_Donation__c = :r1.id order by CloseDate]);
        Opportunity o = originalOpps[0];
        string closedstage = [select masterlabel from opportunitystage where isActive = true and iswon = true and isClosed = true limit 1].masterlabel;
        o.StageName = closedstage;
        o.CloseDate = system.today();
        update o;
        
        //fake the last payment date close operation
        r1.npe03__Last_Payment_Date__c = system.today();
        update r1;
        RD_ProcessControl.hasRun = false;                
        Test.startTest();        
        r1.npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodYearly;
        update r1;
        Test.stopTest();        
        
        npe03__Recurring_Donation__c rd = [select npe03__Last_Payment_Date__c from npe03__Recurring_Donation__c where id = :r1.id];     
        
        system.assertEquals(1, [select count() from Opportunity where npe03__Recurring_Donation__c = :r1.id and isClosed = false]);
        system.assertEquals(system.today(), rd.npe03__Last_Payment_Date__c);
    }

    //test inserting a closed/won opportunity for an open recurring donation
    static testMethod void insertClosedOppForOpenEndedRecurringDonation(){
        if (strTestOnly != '*' && strTestOnly != 'closeOppForOpenEndedRecurringDonationQuarterly') return;

        UTIL_CustomSettingsFacade.getRecurringDonationsSettingsForTest(
        new npe03__Recurring_Donations_Settings__c(
        npe03__Opportunity_Forecast_Months__c = 12,
        npe03__Maximum_Donations__c = 50,
        npe03__Open_Opportunity_Behavior__c = RD_RecurringDonations.RecurringDonationCloseOptions.Mark_Opportunities_Closed_Lost.name()        
        ));           
        
        Account a = new Account();
        a.Name = 'test Individual';
        insert a;
        
        Contact c = new Contact();
        c.FirstName = 'test';
        c.LastName = 'contact';
        c.AccountId = a.Id;
        insert c;       

        npe03__Recurring_Donation__c r1 = new npe03__Recurring_Donation__c();
        r1.Name = 'test';
        r1.npe03__Installments__c = 0;
        r1.npe03__Contact__c = c.Id;
        r1.npe03__Amount__c = 100;
        r1.npe03__Date_Established__c = date.newinstance(1970,6,12);
        r1.npe03__Open_Ended_Status__c = system.label.npe03.RecurringDonationOpenStatus;
        r1.npe03__Next_Payment_Date__c = system.today().toStartOfMonth();
        r1.OwnerId = system.Userinfo.getUserId();
        insert r1;        
        
        Opportunity o = new Opportunity();
        string closedstage = [select masterlabel from opportunitystage where isActive = true and iswon = true and isClosed = true limit 1].masterlabel;
        o.StageName = closedstage;
        o.CloseDate = system.today();
        o.Amount = 50;
        o.AccountId = a.Id;
        o.Name = 'test';
        o.npe03__Recurring_Donation__c = r1.Id;
        RD_ProcessControl.hasRun = false;
        Test.startTest();        
        insert o;
        Test.stopTest();

        npe03__Recurring_Donation__c rd = [select npe03__Paid_Amount__c from npe03__Recurring_Donation__c where id = :r1.id];    

        system.assertEquals(50, rd.npe03__Paid_Amount__c);
    }
    
    //test closing an opportunity for an open recurring donation
    static testMethod void closeOppForOpenEndedRecurringDonationBadRecurrance(){
        if (strTestOnly != '*' && strTestOnly != 'closeOppForOpenEndedRecurringDonationBadRecurrance') return;

        UTIL_CustomSettingsFacade.getRecurringDonationsSettingsForTest(
        new npe03__Recurring_Donations_Settings__c(
        npe03__Opportunity_Forecast_Months__c = 12,
        npe03__Maximum_Donations__c = 50,
        npe03__Open_Opportunity_Behavior__c = RD_RecurringDonations.RecurringDonationCloseOptions.Mark_Opportunities_Closed_Lost.name()        
        ));           
        
        Account a = new Account();
        a.Name = 'test Individual';
        insert a;
        
        Contact c = new Contact();
        c.FirstName = 'test';
        c.LastName = 'contact';
        c.AccountId = a.Id;
        insert c;       
        
        Campaign camp = new Campaign(Name = 'MyTestCamp', isActive = true);
        insert camp;

        npe03__Recurring_Donation__c r1 = new npe03__Recurring_Donation__c();
        r1.Name = 'test';
        r1.npe03__Installments__c = 2;
        r1.npe03__Organization__c = a.id;
        r1.npe03__Amount__c = 100;
        r1.npe03__Recurring_Donation_Campaign__c = camp.id;
        r1.npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodYearly;
        r1.npe03__Date_Established__c = date.newinstance(1970,6,12);
        r1.npe03__Schedule_Type__c = system.label.npe03.RecurringDonationMultiplyValue;
        r1.npe03__Open_Ended_Status__c = system.label.npe03.RecurringDonationOpenStatus;
        r1.npe03__Next_Payment_Date__c = system.today();
        insert r1;
        
        list<Opportunity> originalOpps = new list<Opportunity>([select id, Name,amount,accountid,CloseDate from Opportunity where npe03__Recurring_Donation__c = :r1.id order by CloseDate]);
        Opportunity o = originalOpps[0];
        string closedstage = [select masterlabel from opportunitystage where isActive = true and iswon = true and isClosed = true limit 1].masterlabel;
        o.StageName = closedstage;
        o.CloseDate = system.today();
        r1.npe03__Installment_Period__c = 'GarbageDate';
        update r1;
        RD_ProcessControl.hasRun = false;
        Test.startTest();        
        update o;
        Test.stopTest();
    }
    
       //test closing an opportunity for an open recurring donation
    static testMethod void closeRecurringDonationDeleteOpps(){
        if (strTestOnly != '*' && strTestOnly != 'closeRecurringDonationDeleteOpps') return;

        UTIL_CustomSettingsFacade.getRecurringDonationsSettingsForTest(
        new npe03__Recurring_Donations_Settings__c(
        npe03__Opportunity_Forecast_Months__c = 12,
        npe03__Maximum_Donations__c = 50,
        npe03__Open_Opportunity_Behavior__c = RD_RecurringDonations.RecurringDonationCloseOptions.Delete_Open_Opportunities.name()        
        ));           
        
        Account a = new Account();
        a.Name = 'test Individual';
        insert a;
        
        Contact c = new Contact();
        c.FirstName = 'test';
        c.LastName = 'contact';
        c.AccountId = a.Id;
        insert c;       
        
        Campaign camp = new Campaign(Name = 'MyTestCamp', isActive = true);
        insert camp;

        npe03__Recurring_Donation__c r1 = new npe03__Recurring_Donation__c();
        r1.Name = 'test';
        r1.npe03__Installments__c = 2;
        r1.npe03__Organization__c = a.id;
        r1.npe03__Amount__c = 100;
        r1.npe03__Recurring_Donation_Campaign__c = camp.id;
        r1.npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodYearly;
        r1.npe03__Date_Established__c = date.newinstance(1970,6,12);
        r1.npe03__Schedule_Type__c = system.label.npe03.RecurringDonationMultiplyValue;
        r1.npe03__Open_Ended_Status__c = system.label.npe03.RecurringDonationOpenStatus;
        r1.npe03__Next_Payment_Date__c = system.today();
        insert r1;
        
        system.assertNotEquals(0, [select count() from Opportunity where npe03__Recurring_Donation__c = :r1.id and isClosed = false]);
        //reset the semaphore
        RD_ProcessControl.hasRun = false;
        Test.startTest();        
        r1.npe03__Open_Ended_Status__c = system.label.npe03.RecurringDonationClosedStatus;
        update r1;
        Test.stopTest();
        
        //there should be no open donations
        system.assertEquals(0, [select count() from Opportunity where npe03__Recurring_Donation__c = :r1.id and isClosed = false]);        
    }
    
    static testMethod void multipleRDInsert(){
        if (strTestOnly != '*' && strTestOnly != 'multipleRDInsert') return;

        UTIL_CustomSettingsFacade.getRecurringDonationsSettingsForTest(
        new npe03__Recurring_Donations_Settings__c(
        npe03__Opportunity_Forecast_Months__c = 6,
        npe03__Maximum_Donations__c = 50,
        npe03__Open_Opportunity_Behavior__c = RD_RecurringDonations.RecurringDonationCloseOptions.Mark_Opportunities_Closed_Lost.name()        
        ));           
        
        delete [select id from npe03__Custom_Field_Mapping__c];
        npe03__Custom_Field_Mapping__c cfm1 = new npe03__Custom_Field_Mapping__c(
        npe03__Recurring_Donation_Field__c = 'Name',
        npe03__Opportunity_Field__c = 'Description',
        Name = 'CFMName00001-Test'
        );
        insert cfm1;
        
        Account a = new Account();
        a.Name = 'test Individual';
        insert a;
        
        Contact c = new Contact();
        c.FirstName = 'test';
        c.LastName = 'contact';
        c.AccountId = a.Id;
        insert c;       

        list<npe03__Recurring_Donation__c> rdlist = new list<npe03__Recurring_Donation__c>();
        npe03__Recurring_Donation__c r1 = new npe03__Recurring_Donation__c();
        r1.Name = 'test';
        r1.npe03__Installments__c = 2;
        r1.npe03__Contact__c = c.Id;
        r1.npe03__Amount__c = 100;
        r1.npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodYearly;
        r1.npe03__Date_Established__c = date.newinstance(1970,6,12);
        r1.npe03__Schedule_Type__c = system.label.npe03.RecurringDonationMultiplyValue;
        r1.npe03__Open_Ended_Status__c = system.label.npe03.RecurringDonationOpenStatus;
        r1.npe03__Next_Payment_Date__c = system.today().toStartOfMonth();
        rdlist.add(r1);
                
        npe03__Recurring_Donation__c r2 = new npe03__Recurring_Donation__c();
        r2.Name = 'test';
        r2.npe03__Installments__c = 2;
        r2.npe03__Contact__c = c.Id;
        r2.npe03__Amount__c = 100;
        r2.npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodMonthly;
        r2.npe03__Date_Established__c = date.newinstance(1970,6,12);
        r2.npe03__Schedule_Type__c = system.label.npe03.RecurringDonationMultiplyValue;
        r2.npe03__Open_Ended_Status__c = system.label.npe03.RecurringDonationOpenStatus;
        r2.npe03__Next_Payment_Date__c = system.today().toStartOfMonth();
        rdlist.add(r2);
                                        
        Test.startTest();
        insert rdlist;
        rdlist[0].npe03__Amount__c = 50;
        rdlist[1].npe03__Amount__c = 50;
        rdlist[1].npe03__Next_Payment_Date__c = system.today().toStartOfMonth().addDays(5);
        RD_ProcessControl.hasRun = false;
        update rdlist;        
        Test.stopTest();
        
        list<Opportunity> originalOpps = new list<Opportunity>([select id, Name,amount,accountid,CloseDate,Description from Opportunity where npe03__Recurring_Donation__r.id = :r1.id or npe03__Recurring_Donation__c = :r2.id]);
        system.assertEquals(7, originalOpps.size());
        system.assertEquals(50, originalOpps[0].Amount);
        system.assertNotEquals(null, originalOpps[0].Description);
        system.assertNotEquals(null, originalOpps[1].Description);
    }
    static testMethod void tooManyInstallments(){
        if (strTestOnly != '*' && strTestOnly != 'tooManyInstallments') return;

        UTIL_CustomSettingsFacade.getRecurringDonationsSettingsForTest(
        new npe03__Recurring_Donations_Settings__c(
        npe03__Opportunity_Forecast_Months__c = 6,
        npe03__Maximum_Donations__c = 2,
        npe03__Open_Opportunity_Behavior__c = RD_RecurringDonations.RecurringDonationCloseOptions.Mark_Opportunities_Closed_Lost.name()        
        ));           
        
        Account a = new Account();
        a.Name = 'test Individual';
        insert a;
        
        Contact c = new Contact();
        c.FirstName = 'test';
        c.LastName = 'contact';
        c.AccountId = a.Id;
        insert c;       

        list<npe03__Recurring_Donation__c> rdlist = new list<npe03__Recurring_Donation__c>();
        npe03__Recurring_Donation__c r1 = new npe03__Recurring_Donation__c();
        r1.Name = 'test';
        r1.npe03__Installments__c = 50;
        r1.npe03__Contact__c = c.Id;
        r1.npe03__Amount__c = 100;
        r1.npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodYearly;
        r1.npe03__Date_Established__c = date.newinstance(1970,6,12);
        r1.npe03__Schedule_Type__c = system.label.npe03.RecurringDonationMultiplyValue;
        r1.npe03__Open_Ended_Status__c = 'None';
        r1.npe03__Next_Payment_Date__c = system.today();
        try{
            insert r1;
        }
        catch (exception e){}
        //insert should fail because of too many installments
        system.assertEquals(null,r1.id);
    }
    static testmethod void updateOppsFromMultipleRDs(){
        if (strTestOnly != '*' && strTestOnly != 'updateOppsFromMultipleRDs') return;

        UTIL_CustomSettingsFacade.getRecurringDonationsSettingsForTest(
        new npe03__Recurring_Donations_Settings__c(
        npe03__Opportunity_Forecast_Months__c = 6,
        npe03__Maximum_Donations__c = 50,
        npe03__Open_Opportunity_Behavior__c = RD_RecurringDonations.RecurringDonationCloseOptions.Mark_Opportunities_Closed_Lost.name()        
        ));           
        
        //remove any existing custom field mappings
        delete [select id from npe03__Custom_Field_Mapping__c];        
        npe03__Custom_Field_Mapping__c cfm = new npe03__Custom_Field_Mapping__c(
        npe03__Recurring_Donation_Field__c = 'Name',
        npe03__Opportunity_Field__c = 'Description',
        Name = 'CFMName00001-Test'
        );
        insert cfm;
        
        Account a = new Account(); 
        a.Name = 'test Individual';
        insert a;
        
        Contact c = new Contact();
        c.FirstName = 'test';
        c.LastName = 'contact';
        c.AccountId = a.Id;
        insert c;       
        
        list<npe03__Recurring_Donation__c> rdlist = new list<npe03__Recurring_Donation__c>();
        npe03__Recurring_Donation__c r1 = new npe03__Recurring_Donation__c();
        r1.Name = 'test';
        r1.npe03__Installments__c = 2;
        r1.npe03__Contact__c = c.Id;
        r1.npe03__Amount__c = 100;
        r1.npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodYearly;
        r1.npe03__Date_Established__c = date.newinstance(1970,6,12);
        r1.npe03__Schedule_Type__c = system.label.npe03.RecurringDonationMultiplyValue;
        r1.npe03__Open_Ended_Status__c = system.label.npe03.RecurringDonationOpenStatus;
        r1.npe03__Next_Payment_Date__c = system.today();
        insert r1;
        RD_ProcessControl.hasRun = false;
                
        npe03__Recurring_Donation__c r2 = new npe03__Recurring_Donation__c();
        r2.Name = 'test';
        r2.npe03__Installments__c = 2;
        r2.npe03__Contact__c = c.Id;
        r2.npe03__Amount__c = 100;
        r2.npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodMonthly;
        r2.npe03__Date_Established__c = date.newinstance(1970,6,12);
        r2.npe03__Schedule_Type__c = system.label.npe03.RecurringDonationMultiplyValue;
        r2.npe03__Open_Ended_Status__c = system.label.npe03.RecurringDonationOpenStatus;
        r2.npe03__Next_Payment_Date__c = system.today();
        insert r2;
        RD_ProcessControl.hasRun = false;
        list<Opportunity> originalOpps = new list<Opportunity>([select id, Name,amount,accountid,CloseDate from Opportunity where npe03__Recurring_Donation__r.id = :r1.id or npe03__Recurring_Donation__c = :r2.id]);                                
        string closedstage = [select masterlabel from opportunitystage where isActive = true and iswon = true and isClosed = true limit 1].masterlabel;
        system.assert(originalOpps.size() > 1);
        
        Test.startTest();
        originalOpps[0].Stagename = closedstage;
        originalOpps[1].stagename = closedstage;        
        update originalOpps;
        Test.stopTest();
        system.assert(originalOpps.size() > [select count() from Opportunity where isClosed = false and (npe03__Recurring_Donation__c = :r1.id or npe03__Recurring_Donation__c = :r2.id)]);        
    }
    
    static testMethod void testErrorHandlingAllSysAdmins(){
        if (strTestOnly != '*' && strTestOnly != 'testErrorHandlingAllSysAdmins') return;

        UTIL_CustomSettingsFacade.getRecurringDonationsSettingsForTest(new npe03__Recurring_Donations_Settings__c( 
            npe03__Error_Email_Notifications__c ='All Sys Admins',
            npe03__Opportunity_Forecast_Months__c = 12                
        ));         
        
        Account a = new Account(Name = 'Test');
        insert a;
        npe03__Recurring_Donation__c r1 = new npe03__Recurring_Donation__c();
        r1.Name = 'test';
        r1.npe03__Organization__c = a.id;
        r1.npe03__Amount__c = 100;
        r1.npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodWeekly;
        r1.npe03__Open_Ended_Status__c = system.label.npe03.RecurringDonationOpenStatus;
        r1.npe03__Next_Payment_Date__c = system.today();
        insert r1;    
        
        //create some opps
        list<Opportunity> olist = new list<Opportunity>();
        integer i;
        for (i = 0; i<=10; i++){
            Opportunity o = new Opportunity(
                StageName = 'Closed Won',
                Name = 'Opp ' + i,
                CloseDate = system.today(),
                Amount = 1.00,
                npe03__Recurring_Donation__c = r1.id                
            );
            olist.add(o);                       
        }
        //invalidate one opp        
        olist[0].Name = null;
        //insert all opps
        Database.saveresult[] lsr = Database.insert(olist, false);
        //process the DML results
        ERR_Handler.Errors errors = ERR_Handler.getErrors(lsr, olist);
        //check for errors
        System.assertEquals(true, errors.errorsExist);
        System.assertEquals(1, errors.errorRecords.size());
    }
    
    static testMethod void testErrorHandlingUser(){
        if (strTestOnly != '*' && strTestOnly != 'testErrorHandlingUser') return;

        User u = [select id from User limit 1];
        UTIL_CustomSettingsFacade.getRecurringDonationsSettingsForTest(new npe03__Recurring_Donations_Settings__c( 
            npe03__Error_Email_Notifications__c = u.id,
            npe03__Opportunity_Forecast_Months__c = 12
        ));  
        
        Account a = new Account(Name = 'Test');
        insert a;
        npe03__Recurring_Donation__c r1 = new npe03__Recurring_Donation__c();
        r1.Name = 'test';
        r1.npe03__Organization__c = a.id;
        r1.npe03__Amount__c = 100;
        r1.npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodWeekly;
        r1.npe03__Open_Ended_Status__c = system.label.npe03.RecurringDonationOpenStatus;
        r1.npe03__Next_Payment_Date__c = system.today();
        insert r1;      
        
         //create some opps
        list<Opportunity> olist = new list<Opportunity>();
        integer i;
        for (i = 0; i<=10; i++){
            Opportunity o = new Opportunity(
                StageName = 'Closed Won',
                Name = 'Opp ' + i,
                CloseDate = system.today(),
                Amount = 1.00,
                npe03__Recurring_Donation__c = r1.id                
            );
            olist.add(o);                       
        }
        //invalidate one opp        
        olist[0].Name = null;
        //perform DML
        Database.saveresult[] lsr = Database.insert(olist, false); 
        //process the DML results
        ERR_Handler.Errors errors = ERR_Handler.getErrors(lsr, olist);
        //check for errors
        System.assertEquals(true, errors.errorsExist);
        System.assertEquals(1, errors.errorRecords.size());
    }
    
    static testMethod void testErrorHandlingPublicGroup(){
        if (strTestOnly != '*' && strTestOnly != 'testErrorHandlingPublicGroup') return;

        Group g = new Group(Name = 'MyGroup');
        insert g;
        UTIL_Debug.debug('GID IS: ' + g.id);
        UTIL_CustomSettingsFacade.getRecurringDonationsSettingsForTest(new npe03__Recurring_Donations_Settings__c( 
            npe03__Error_Email_Notifications__c = g.id,
            npe03__Opportunity_Forecast_Months__c = 12
        ));         
        
        Account a = new Account(Name = 'Test');
        insert a;
        npe03__Recurring_Donation__c r1 = new npe03__Recurring_Donation__c();
        r1.Name = 'test';
        r1.npe03__Organization__c = a.id;
        r1.npe03__Amount__c = 100;
        r1.npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodWeekly;
        r1.npe03__Open_Ended_Status__c = system.label.npe03.RecurringDonationOpenStatus;
        r1.npe03__Next_Payment_Date__c = system.today();
        insert r1;                   
        
        //create some opps
        list<Opportunity> olist = new list<Opportunity>();
        integer i;
        for (i = 0; i<=10; i++){
            Opportunity o = new Opportunity(
                StageName = 'Closed Won',
                Name = 'Opp ' + i,
                CloseDate = system.today(),
                Amount = 1.00,
                npe03__Recurring_Donation__c = r1.id                
            );
            olist.add(o);                       
        }
        //invalidate one opp        
        olist[0].Name = null;
       //perform DML
        Database.saveresult[] lsr = Database.insert(olist, false); 
        //process the DML results
        ERR_Handler.Errors errors = ERR_Handler.getErrors(lsr, olist);
        //check for errors
        System.assertEquals(true, errors.errorsExist);
        System.assertEquals(1, errors.errorRecords.size());
    }     

    //--------------------TEST METHOD from RD_RecurringDonations_BATCH -----------------------
    static testMethod void batchStatusBarTest(){
        if (strTestOnly != '*' && strTestOnly != 'batchStatusBarTest') return;

        UTIL_JobProgress_CTRL controller = new UTIL_JobProgress_CTRL();
        controller.getBatchJobs();
    }

    //--------------- from RD_RecurringDonations_BATCH -----------------
    @isTest 
    public static void testBatch() {
        if (strTestOnly != '*' && strTestOnly != 'testBatch') return;
        
        UTIL_CustomSettingsFacade.getRecurringDonationsSettingsForTest(
        new npe03__Recurring_Donations_Settings__c(
        npe03__Opportunity_Forecast_Months__c = 12,
        npe03__Maximum_Donations__c = 50,
        npe03__Open_Opportunity_Behavior__c = RD_RecurringDonations.RecurringDonationCloseOptions.Mark_Opportunities_Closed_Lost.name()
        ));           
                    
        Account a = new Account();
        a.Name = 'test Individual';
        insert a;
        
        Contact c = new Contact();
        c.FirstName = 'test';
        c.LastName = 'contact';
        c.AccountId = a.Id;
        insert c;       

        npe03__Recurring_Donation__c r1 = new npe03__Recurring_Donation__c();
        r1.Name = 'test';
        r1.npe03__Installments__c = 2;
        r1.npe03__Organization__c = a.id;
        r1.npe03__Amount__c = 100;
        r1.npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodWeekly;
        r1.npe03__Date_Established__c = date.newinstance(1970,6,12);
        r1.npe03__Schedule_Type__c = system.label.npe03.RecurringDonationMultiplyValue;
        r1.npe03__Open_Ended_Status__c = system.label.npe03.RecurringDonationOpenStatus;
        r1.npe03__Next_Payment_Date__c = system.today();
        insert r1;
        
        list<Opportunity> originalOpps = new list<Opportunity>([select id, Name,amount,accountid,CloseDate from Opportunity where npe03__Recurring_Donation__c = :r1.id order by CloseDate]);
        
        Opportunity o = originalOpps[0];
        string closedstage = [select masterlabel from opportunitystage where isActive = true and iswon = true and isClosed = true limit 1].masterlabel;
        o.StageName = closedstage;
        o.CloseDate = system.today();
        update o;        
        
        //kick it off from the vf page
        Test.setCurrentPageReference(new PageReference('Page.RecurringDonationsSettings'));
        
        //pass the controller into the extension
        /****
        STTG_RecurringDonations_CTRL controller = new STTG_RecurringDonations_CTRL();
        controller.getSettingsFirstTime();
        test.startTest();
        controller.runBatch();
        test.stopTest();
        *********/  
        
        Test.StartTest();
        RD_RecurringDonations_BATCH rdbatch = new RD_RecurringDonations_BATCH();
        ID ApexJobId = Database.executeBatch(rdbatch, 10);
        Test.stopTest();
        
    }  

/******************************************************************    
    // Test Methods from STTG_RecurringDonations_CTRL   
    public static testMethod void TEST_RecurringDonationsSettings() {
        if (strTestOnly != '*' && strTestOnly != 'TEST_RecurringDonationsSettings') return;

        id rtid = [select id from RecordType where sobjecttype = 'Opportunity' limit 1].id;
        id uid = [select id from User limit 1].id;
        UTIL_CustomSettingsFacade.getRecurringDonationsSettingsForTest(new npe03__Recurring_Donations_Settings__c(
        npe03__Error_Email_Notifications__c = uid, npe03__Record_Type__c = rtid, npe03__Max_Package_Version__c = '2.0',
        npe03__Package_Update_URL__c = 'http://www.salesforce.com', npe03__Last_Update_Check__c = system.today(),
        npe03__Has_Package_Update__c = 'true'        
        ));
        
        npe03__Custom_Installment_Settings__c cis = new npe03__Custom_Installment_Settings__c(Name = 'TESTINSTALLMENT', npe03__Value__c = 1, npe03__Increment__c = 'Weeks');
        insert cis;
        
        Account a = new Account(Name='TestAccount');
        insert a;
        insert new npe03__Recurring_Donation__c(Name = 'TestRD', npe03__Organization__c = a.id, npe03__Installment_Period__c='TestValueForUnitTests');
 
        Test.setCurrentPageReference(new PageReference('Page.RecurringDonationsSettings'));
        
        //pass the controller into the extension
        STTG_RecurringDonations_CTRL controller = new STTG_RecurringDonations_CTRL(); 
        controller.getSettingsFirstTime();
        system.assertNotEquals(null,UTIL_CustomSettingsFacade.getRecurringDonationsSettings(
                                            RD_RecurringDonations.RecurringDonationCloseOptions.Mark_Opportunities_Closed_Lost));
        
        list<SelectOption> enumvalues  =  controller.closeOptions; 
        system.assertEquals(3,enumvalues.size());
        
        controller.testPackageIntegrity();
        controller.cancel();
        controller.editRDsettings();
        controller.rds.npe03__Open_Opportunity_Behavior__c = '-------------------';
        controller.saveRDSettings();   
        controller.rds.npe03__Open_Opportunity_Behavior__c = '1234';
        controller.saveRDSettings();
        controller.addCIRow();        
        controller.editCISettings();
        npe03__Custom_Installment_Settings__c c = new npe03__Custom_Installment_Settings__c(Name='TestValueForUnitTests', npe03__Value__c = 5.0, npe03__Increment__c = 'Weeks');
        controller.cis.add(c);
        controller.saveCISettings(); 
        controller.runDeleteWizard();
        controller.selectedInstallmentName = 'TestValueForUnitTests';
        controller.replacementInstallment = 'Weekly';        
        controller.deleteInstallment();        
        controller.RDField = 'npe03__Amount__c';
        controller.OppField = 'Amount';
        controller.saveFieldMapping();
        controller.fieldMappingName = 'TestNAME';
        controller.deleteFieldMapping();
        Recordtype rt = controller.RTName;
        Group g =  controller.EmailErrorDisplayName;
        g =  controller.EmailErrorDisplayName;        
        controller.addFieldMapping();
        list<SelectOption> eeolist = controller.ErrorEmailOptions;
        list<SelectOption> rtlist = controller.RecordTypeList;
        list<SelectOption> mylist = controller.validInstallments;
        list<SelectOption> rdfield = controller.availableRDFields;
        list<SelectOption> oppfield = controller.availableOpportunityFields;
        controller.trimOpportunities();
        controller.clearErrorQueue();
    }   
    //covering everything else 
    public static testMethod void TEST_RecurringDonationsSettings2(){
        if (strTestOnly != '*' && strTestOnly != 'TEST_RecurringDonationsSettings2') return;

        id rtid = [select id from RecordType where sobjecttype = 'Opportunity' limit 1].id;
        Group g = new Group(Name = 'Testgroup');
        insert g;        
        
        UTIL_CustomSettingsFacade.getRecurringDonationsSettingsForTest(new npe03__Recurring_Donations_Settings__c(
        npe03__Error_Email_Notifications__c = g.id, npe03__Record_Type__c = rtid, npe03__Max_Package_Version__c = '2.0',
        npe03__Package_Update_URL__c = 'http://www.salesforce.com', npe03__Last_Update_Check__c = null,
        npe03__Has_Package_Update__c = 'false'        
        ));   
        
        Test.setCurrentPageReference(new PageReference('Page.RecurringDonationsSettings'));
        //pass the controller into the extension
        STTG_RecurringDonations_CTRL controller = new STTG_RecurringDonations_CTRL(); 
        controller.getSettingsFirstTime();
        list<SelectOption> eeolist = controller.ErrorEmailOptions;
        controller.RDField = 'garbage';
        controller.OppField = 'garbage';
        controller.saveFieldMapping();
        controller.cis.add(new npe03__Custom_Installment_Settings__c(Name = 'CisSetTest', npe03__Value__c = 1, npe03__Increment__c = 'Weeks'));
        controller.saveCISettings();
    }
*************************************************************************/
    
    // from RD_AddDonationsBTN_CTRL
    public static testMethod void testBTN(){
        if (strTestOnly != '*' && strTestOnly != 'testBTN') return;

        Account a = new Account(Name = 'TestA');
        insert a;
        npe03__Recurring_Donation__c r1 = new npe03__Recurring_Donation__c();
        r1.Name = 'test';
        r1.npe03__Installments__c = 2;
        r1.npe03__Organization__c = a.Id;
        r1.npe03__Amount__c = 100;
        r1.npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodWeekly;
        r1.npe03__Date_Established__c = date.newinstance(1970,6,12);
        r1.npe03__Schedule_Type__c = system.label.npe03.RecurringDonationMultiplyValue;
        r1.npe03__Open_Ended_Status__c = system.label.npe03.RecurringDonationOpenStatus;
        r1.npe03__Next_Payment_Date__c = system.today();
        insert r1;
        
        delete[select id from npe03__Custom_Field_Mapping__c];
        insert new npe03__Custom_Field_Mapping__c(
        Name = 'CFM-' + system.now(),
        npe03__Opportunity_Field__c = 'closedate',
        npe03__Recurring_Donation_Field__c = 'Name'
        );
        
        ApexPages.StandardController scc = new ApexPages.StandardController(r1);
        RD_AddDonationsBTN_CTRL rdadb = new RD_AddDonationsBTN_CTRL(scc);
        system.assertEquals(r1.id, rdadb.sc.getId());
        rdadb.buttonClick();    
    }    

    /*******************************************************************************************************
    * @description test method for custom field mapping on insert and update of Fixed Length RDs
    */ 
    static testMethod void testRDCustomFieldMappingFixedRD()
    {
        if (strTestOnly != '*' && strTestOnly != 'testRDCustomFieldMappingFixedRD') return;
        
        UTIL_Debug.debug('multiply: ' + system.label.npe03.RecurringDonationMultiplyValue);
        UTIL_Debug.debug('divide: ' + system.label.npe03.RecurringDonationDivideValue);
        Account a = new Account();
        a.Name = 'test Individual';
        insert a;
        
        Contact c = new Contact();
        c.FirstName = 'test';
        c.LastName = 'contact';
        c.AccountId = a.Id;        
        insert c;
        
        //Create new CFM
        delete [select id from npe03__Custom_Field_Mapping__c];
        npe03__Custom_Field_Mapping__c cfm = new npe03__Custom_Field_Mapping__c(
        npe03__Recurring_Donation_Field__c = 'Name',
        npe03__Opportunity_Field__c = 'Description',
        Name = 'CFMName00001-Test'
        );
        insert cfm;


        npe03__Recurring_Donation__c r1 = new npe03__Recurring_Donation__c();
        r1.Name = 'TestRD CFM Insert';
        r1.npe03__Installments__c = 2;
        r1.npe03__Contact__c = c.Id;
        r1.npe03__Amount__c = 100;
        r1.npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodYearly;
        r1.npe03__Date_Established__c = date.newinstance(1970,6,12);
        r1.npe03__Schedule_Type__c = system.label.npe03.RecurringDonationMultiplyValue;
        r1.npe03__Open_Ended_Status__c = 'None'; 
        insert r1;
        
        //check that opps have picked up the value of the RD field set through custom field mapping
        Opportunity[] installments1 = [select Description from Opportunity where npe03__Recurring_Donation__c = :r1.id order by CloseDate];
        for(opportunity opp:installments1){
            system.assertEquals(r1.name, opp.description);                
        }

        Test.startTest();
            RD_ProcessControl.hasRun=false;
            r1.name='TestRD CFM Update';
            update r1;
        Test.stopTest();

        npe03__Recurring_Donation__c r2 = [select name from npe03__Recurring_Donation__c r1 where id=:r1.id];

        system.assertEquals(r2.name,'TestRD CFM Update');
        //check that opps have picked up the upated value of the RD field set through custom field mapping
        Opportunity[] installments2 = [select Description from Opportunity where npe03__Recurring_Donation__c = :r1.id order by CloseDate];
        for(opportunity opp:installments2){
            system.assertEquals(r2.name, opp.description);                
        }
    }

    /*******************************************************************************************************
    * @description test method for custom field mapping on insert and update on Open Ended RD's
    */ 
    static testMethod void testRDCustomFieldMappingOpenRD()
    {
        if (strTestOnly != '*' && strTestOnly != 'testRDCustomFieldMappingOpenRD') return;
        
        UTIL_Debug.debug('multiply: ' + system.label.npe03.RecurringDonationMultiplyValue);
        UTIL_Debug.debug('divide: ' + system.label.npe03.RecurringDonationDivideValue);
        Account a = new Account();
        a.Name = 'test Individual';
        insert a;
        
        Contact c = new Contact();
        c.FirstName = 'test';
        c.LastName = 'contact';
        c.AccountId = a.Id;        
        insert c;
        
        //Create new CFM
        delete [select id from npe03__Custom_Field_Mapping__c];
        npe03__Custom_Field_Mapping__c cfm = new npe03__Custom_Field_Mapping__c(
        npe03__Recurring_Donation_Field__c = 'Name',
        npe03__Opportunity_Field__c = 'Description',
        Name = 'CFMName00001-Test'
        );
        insert cfm;


        npe03__Recurring_Donation__c r1 = new npe03__Recurring_Donation__c();
        r1.Name = 'TestRD CFM Insert';
        r1.npe03__Contact__c = c.Id;
        r1.npe03__Amount__c = 100;
        r1.npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodMonthly;
        r1.npe03__Date_Established__c = system.today();
        r1.npe03__Schedule_Type__c = system.label.npe03.RecurringDonationMultiplyValue;
        r1.npe03__Open_Ended_Status__c = 'Open'; 
        insert r1;
        
        //check that opps have picked up the value of the RD field set through custom field mapping
        Opportunity[] installments1 = [select Description from Opportunity where npe03__Recurring_Donation__c = :r1.id order by CloseDate];
        system.assertNotEquals(0, installments1.size());
        for(opportunity opp:installments1){
            system.assertEquals(r1.name, opp.description);                
        }

        Test.startTest();
            RD_ProcessControl.hasRun=false;
            r1.name='TestRD CFM Update';
            update r1;
        Test.stopTest();

        npe03__Recurring_Donation__c r2 = [select name from npe03__Recurring_Donation__c r1 where id=:r1.id];

        system.assertEquals(r2.name,'TestRD CFM Update');
        //check that opps have picked up the upated value of the RD field set through custom field mapping
        Opportunity[] installments2 = [select Description from Opportunity where npe03__Recurring_Donation__c = :r1.id order by CloseDate];
        system.assertNotEquals(0, installments2.size());
        for(opportunity opp:installments2){
            system.assertEquals(r2.name, opp.description);                
        }
    }

    static testMethod void testInstallmentNumbers() {
        if (strTestOnly != '*' && strTestOnly != 'testInstallmentNumbers') return;

        UTIL_CustomSettingsFacade.getRecurringDonationsSettingsForTest(
        new npe03__Recurring_Donations_Settings__c(
        npe03__Opportunity_Forecast_Months__c = 12,
        npe03__Maximum_Donations__c = 50,
        npe03__Open_Opportunity_Behavior__c = RD_RecurringDonations.RecurringDonationCloseOptions.Delete_Open_Opportunities.name()        
        ));           
        
        Account a = new Account();
        a.Name = 'test Individual';
        insert a;
        
        Contact c = new Contact();
        c.FirstName = 'test';
        c.LastName = 'contact';
        c.AccountId = a.Id;
        insert c;       
        
        Campaign camp = new Campaign(Name = 'MyTestCamp', isActive = true);
        insert camp;

        npe03__Recurring_Donation__c r1 = new npe03__Recurring_Donation__c();
        r1.Name = 'test';
        r1.npe03__Installments__c = 10;
        r1.npe03__Organization__c = a.id;
        r1.npe03__Amount__c = 100;
        r1.npe03__Recurring_Donation_Campaign__c = camp.id;
        r1.npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodYearly;
        r1.npe03__Date_Established__c = date.newinstance(1970,6,12);
        r1.npe03__Schedule_Type__c = system.label.npe03.RecurringDonationMultiplyValue;
        r1.npe03__Open_Ended_Status__c = system.label.npe03.RecurringDonationOpenStatus;
        r1.npe03__Next_Payment_Date__c = system.today();
        insert r1;
        
        list<opportunity> queryopp = [SELECT Id, Recurring_Donation_Installment_Number__c FROM Opportunity ORDER BY CloseDate];

        for (integer i=0;i<queryopp.size();i++) {
            system.assertEquals(i+1,queryopp[i].Recurring_Donation_Installment_Number__c, 'Recurring Donation opportunities should have the correct installment number.');
            queryopp[i].Recurring_Donation_Installment_Number__c = null;
        }
        update queryopp;

        queryopp = [SELECT Id, Recurring_Donation_Installment_Number__c FROM Opportunity ORDER BY CloseDate];

        for (opportunity opp : queryopp)
            system.assertEquals(null, opp.Recurring_Donation_Installment_Number__c, 'Recurring Donation opportunities shouldn\'t have an installment number.');


        test.startTest();
        Test.testInstall(new STG_InstallScript(), new Version(3,39), true);
        test.stopTest();

        queryopp = [SELECT Id, Recurring_Donation_Installment_Number__c FROM Opportunity ORDER BY CloseDate];

        for (integer i=0;i<queryopp.size();i++) {
            system.assertEquals(i+1,queryopp[i].Recurring_Donation_Installment_Number__c, 'Recurring Donation opportunities should have the correct installment number.');
            queryopp[i].Recurring_Donation_Installment_Number__c = null;
        }
    }

    static testMethod void testOpenEndedMonthlyEndDates(){
        if (strTestOnly != '*' && strTestOnly != 'testOpenEndedMonthlyEndDates') return;

        UTIL_CustomSettingsFacade.getRecurringDonationsSettingsForTest(
            new npe03__Recurring_Donations_Settings__c(
            npe03__Opportunity_Forecast_Months__c = 12,
            npe03__Maximum_Donations__c = 50,
            npe03__Open_Opportunity_Behavior__c = RD_RecurringDonations.RecurringDonationCloseOptions.Mark_Opportunities_Closed_Lost.name()        
            ));             
        
        Account a = new Account();
        a.Name = 'test Individual';
        insert a;
        
        Contact c = new Contact();
        c.FirstName = 'test';
        c.LastName = 'contact';
        c.AccountId = a.Id;
        insert c;       
        
        Campaign camp = new Campaign(name = 'Campaign', isActive = true);
        insert camp;
        
        list<npe03__Recurring_Donation__c> rdlist = new list<npe03__Recurring_Donation__c>();
        
        npe03__Recurring_Donation__c RD = new npe03__Recurring_Donation__c();
        RD.Name = 'test28th';
        RD.npe03__Contact__c = c.Id;
        RD.npe03__Amount__c = 100;
        RD.npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodMonthly;
        RD.npe03__Date_Established__c = system.today().toStartOfMonth().addDays(27);
        RD.npe03__Schedule_Type__c = system.label.npe03.RecurringDonationMultiplyValue;
        RD.npe03__Open_Ended_Status__c = system.label.npe03.RecurringDonationOpenStatus;
        rdlist.add(RD);
        
        RD = RD.clone(false);
        RD.Name = 'testEndOfMonth';
        RD.npe03__Date_Established__c = system.today().toStartOfMonth();
        RD.npe03__Next_Payment_Date__c = system.today().addMonths(1).toStartOfMonth().addDays(-1);
        rdlist.add(RD);
                
        RD = RD.clone(false);
        RD.Name = 'test30th';
        integer iday = 30;
        if (date.daysInMonth(system.today().year(), system.today().Month()) < 30)
            iday = 28;
        RD.npe03__Date_Established__c = system.today().toStartOfMonth().addDays(iday-1);
        RD.npe03__Next_Payment_Date__c = null;
        rdlist.add(RD);

        test.startTest();
        insert rdlist;
        test.stopTest();
        
        list<Opportunity> listOpp28th = [select Id, CloseDate from Opportunity where npe03__Recurring_Donation__r.Name = 'test28th' order by CloseDate];
        system.assertEquals(12, listOpp28th.size());
        for (Opportunity opp : listOpp28th) {
            system.assertEquals(28, opp.CloseDate.Day());
        }
        
        list<Opportunity> listOpp30th = [select Id, CloseDate from Opportunity where npe03__Recurring_Donation__r.Name = 'test30th' order by CloseDate];
        system.assertEquals(12, listOpp30th.size());
        for (Opportunity opp : listOpp30th) {
            if (iday <= date.daysInMonth(opp.CloseDate.year(), opp.CloseDate.Month()))
                system.assertEquals(iday, opp.CloseDate.Day());
            else
                system.assertEquals(date.daysInMonth(opp.CloseDate.year(), opp.CloseDate.Month()), opp.CloseDate.Day());            
        }

        list<Opportunity> listOppEnd = [select Id, CloseDate from Opportunity where npe03__Recurring_Donation__r.Name = 'testEndOfMonth' order by CloseDate];
        system.assertEquals(12, listOppEnd.size());
        for (Opportunity opp : listOppEnd) {
            system.assertEquals(opp.CloseDate.addMonths(1).toStartOfMonth().addDays(-1).Day(), opp.CloseDate.Day());
        }
        
        for (integer i = 0; i < 12; i++) {
            system.assertEquals(listOpp28th[i].CloseDate.Month(), listOpp30th[i].CloseDate.Month());
            system.assertEquals(listOpp28th[i].CloseDate.Month(), listOppEnd[i].CloseDate.Month());
        }
    }

    static testMethod void testOrphanedRd() {
        if (strTestOnly != '*' && strTestOnly != 'testOrphanedRd') return;

        Contact con = new Contact(LastName='test');
        insert con;

        List<npe03__Recurring_Donation__c> rdList = new List<npe03__Recurring_Donation__c>();
        rdList.add(new npe03__Recurring_Donation__c(
            Name='Orphan',
            npe03__Open_Ended_Status__c= Label.npe03.RecurringDonationOpenStatus
        ));
        rdList.add(new npe03__Recurring_Donation__c(
            Name='Orphan2',
            npe03__Open_Ended_Status__c= Label.npe03.RecurringDonationOpenStatus
        ));
        rdList.add(new npe03__Recurring_Donation__c(
            Name = 'test',
            npe03__Contact__c = con.id,
            npe03__Amount__c = 100,
            npe03__Installments__c = 2,
            npe03__Installment_Period__c = Label.npe03.RecurringDonationInstallmentPeriodMonthly,
            npe03__Date_Established__c = Date.newinstance(1970,6,12),
            npe03__Schedule_Type__c = Label.npe03.RecurringDonationMultiplyValue,
            npe03__Open_Ended_Status__c = Label.npe03.RecurringDonationOpenStatus,
            npe03__Next_Payment_Date__c = System.today()
        ));


        //disable triggers to skip opp creation and validation, which would prevent the insertion of the orphan
        //orphaned RDs may exist due to a historical lack of RD cascade deletion when contacts and accounts were deleted
        TDTM_TriggerHandler.disableTDTM = true;
        insert rdList;
        TDTM_TriggerHandler.disableTDTM = false;

        Test.startTest();
        Database.executeBatch(new RD_RecurringDonations_BATCH(), 10);
        Test.stopTest();

        List<Opportunity> rdOpps = [SELECT Id FROM Opportunity WHERE npe03__Recurring_Donation__c IN :rdList];
        System.assertEquals(12,rdOpps.size(), 'Twelve opps should be created for the well formed recurring donation.');

    }
}