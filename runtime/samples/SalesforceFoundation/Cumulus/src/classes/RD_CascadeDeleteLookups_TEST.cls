/*
    Copyright (c) 2016, Salesforce.org
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
* @date 2016
* @group Util
* @description Tests for cascade delete of Recurring Donations
*/
@isTest
private class RD_CascadeDeleteLookups_TEST {

    /*********************************************************************************************************
    * @description Deletes recurring donation, which deletes opportunities and allocations. Verifies results.
    * Then undeletes recurring donation, which undeletes opportunities and allocation. Verifies results.
    */
    static testMethod void testRDCascade(){
        Contact con = new Contact(LastName='foo');
        insert con;

        npe03__Recurring_Donation__c rd = new npe03__Recurring_Donation__c(
            Name = 'test',
            npe03__Installments__c = 2,
            npe03__Amount__c = 1,
            npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodYearly,
            npe03__Date_Established__c = date.newinstance(1970,6,12),
            npe03__Schedule_Type__c = system.label.npe03.RecurringDonationMultiplyValue,
            npe03__Contact__c = con.id
        );
        insert rd;

        List<Opportunity> queryOpps = [SELECT Id FROM Opportunity WHERE npe03__Recurring_Donation__c = :rd.id];
        System.assertEquals(2, queryOpps.size(), 'Two opps should be auto created by recurring donation.');

        General_Accounting_Unit__c gau = new General_Accounting_Unit__c(Name='foo');
        insert gau;
        Allocation__c alloForInsert = new Allocation__c(Recurring_Donation__c=rd.id, Amount__c = 4, General_Accounting_Unit__c = gau.id);
        insert alloForInsert;

        Test.startTest();
        delete rd;
        Test.stopTest();

        queryOpps = [SELECT Id FROM Opportunity WHERE IsDeleted=true ALL ROWS];
        System.assertEquals(2, queryOpps.size(), 'Opportunities should be cascade deleted with the recurring donation.');

        List<Error__c> errors = [SELECT Id, Object_Type__c, Full_Message__c, Stack_Trace__c FROM Error__c];
        System.assertEquals(0, errors.size(), errors);

        List<Allocation__c> deletedAllo = [SELECT Id FROM Allocation__c WHERE Id=:alloForInsert.Id AND IsDeleted=true ALL ROWS];
        System.assertEquals(1, deletedAllo.size(), 'Allocation should be cascade deleted when recurring donation is deleted.');

        //flag needs to be reset as the delete and undelete are in the same context for tests.
        UTIL_CascadeDeleteLookups_TDTM.disableCascade = false;
        undelete rd;

        queryOpps = [SELECT Id FROM Opportunity WHERE npe03__Recurring_Donation__c = :rd.id];
        System.assertEquals(2, queryOpps.size(), 'Two opps should be cascade undeleted.');

        List<Allocation__c> undeletedAllo = [SELECT Id FROM Allocation__c WHERE Id=:alloForInsert.Id AND IsDeleted=false ALL ROWS];
        System.assertEquals(1, undeletedAllo.size(), 'Allocation should be cascade undeleted.');
    }

    /*********************************************************************************************************
    * @description Attempts to delete recurring donation with closed/won and org opps, verifies correct 
    * exception is thrown.
    */
    static testMethod void testRDDeleteException(){
        Contact con = new Contact(LastName='foo');
        insert con;

        npe03__Recurring_Donation__c rd = new npe03__Recurring_Donation__c(
            Name = 'test',
            npe03__Installments__c = 2,
            npe03__Amount__c = 1,
            npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodYearly,
            npe03__Date_Established__c = date.newinstance(1970,6,12),
            npe03__Schedule_Type__c = system.label.npe03.RecurringDonationMultiplyValue,
            npe03__Contact__c = con.id
        );
        insert rd;

        List<Opportunity> queryOpps = [SELECT Id FROM Opportunity WHERE npe03__Recurring_Donation__c = :rd.id];
        System.assertEquals(2, queryOpps.size(), 'Two opps should be auto created by recurring donation.');

        queryOpps[0].StageName = UTIL_UnitTestData_TEST.getClosedWonStage();
        update queryOpps[0];

        Exception caughException;

        Test.startTest();
        try{
            delete rd;
        } catch (Exception ex) {
            caughException = ex;
        }
        Test.stopTest();

        System.assertNotEquals(null, caughException, 'An exception should be caught.');
        System.assert(caughException.getMessage().contains(queryOpps[0].id), 'Exception message should contain the closed won opportunity id.');
        System.assert(caughException.getMessage().contains(Label.ClosedWonOpportunities), 'Exception message should mention Closed Won Opportunities.');
    }
}