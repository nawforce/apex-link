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
* @description Tests for cascade delete of Account
*/
@isTest
private class ACCT_CascadeDeleteLookups_TEST {

    /*********************************************************************************************************
    * @description Tests cascade delete: deletes account, which deletes recurring dontation and allocation. 
    * Verifies results. Then undeletes account, which undeletes recurring donation and allocation. Verifies
    * results.
    */
    static testMethod void testAccountCascadeGrandchild(){
        Contact con = new Contact(LastName='foo');
        insert con;

        //select auto created hh account
        Account acc = [SELECT Id FROM Account WHERE Id IN (SELECT AccountId FROM Contact WHERE Id=:con.id)];

        npe03__Recurring_Donation__c rdCon = new npe03__Recurring_Donation__c(
            Name = 'test',
            npe03__Installments__c = 2,
            npe03__Amount__c = 1,
            npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodYearly,
            npe03__Date_Established__c = date.newinstance(1970,6,12),
            npe03__Schedule_Type__c = system.label.npe03.RecurringDonationMultiplyValue,
            npe03__Contact__c = con.id
        );
        insert rdCon;

        npe03__Recurring_Donation__c rdAcc = new npe03__Recurring_Donation__c(
            Name = 'test',
            npe03__Installments__c = 2,
            npe03__Amount__c = 1,
            npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodYearly,
            npe03__Date_Established__c = date.newinstance(1970,6,12),
            npe03__Schedule_Type__c = system.label.npe03.RecurringDonationMultiplyValue,
            npe03__Organization__c = acc.id
        );
        insert rdAcc;

        General_Accounting_Unit__c gau = new General_Accounting_Unit__c(Name='foo');
        insert gau;
        Allocation__c allo = new Allocation__c(Recurring_Donation__c=rdCon.id, Amount__c = 4, General_Accounting_Unit__c = gau.id);
        insert allo;

        Test.startTest();
        delete acc;
        Test.stopTest();

        List<Error__c> errors = [SELECT Id, Object_Type__c, Full_Message__c, Stack_Trace__c FROM Error__c];
        System.assertEquals(0, errors.size(), errors);

        List<npe03__Recurring_Donation__c> deletedRd = [SELECT Id FROM npe03__Recurring_Donation__c WHERE IsDeleted=true ALL ROWS];
        System.assertEquals(2, deletedRd.size(), 'Contact and Account Recurring Donation should be cascade deleted when Account is deleted.');

		List<Allocation__c> deletedAllo = [SELECT Id FROM Allocation__c WHERE Id=:allo.Id AND IsDeleted=true ALL ROWS];
        System.assertEquals(1, deletedAllo.size(), 'Allocation should be cascade deleted when recurring donation is deleted.');

        //flag needs to be reset as the delete and undelete are in the same context for tests.
        UTIL_CascadeDeleteLookups_TDTM.disableCascade = false;
        undelete acc;

        errors = [SELECT Id, Object_Type__c, Full_Message__c, Stack_Trace__c FROM Error__c];
        System.assertEquals(0, errors.size(), errors);

        List<npe03__Recurring_Donation__c> undeletedRd = [SELECT Id FROM npe03__Recurring_Donation__c WHERE IsDeleted=false ALL ROWS];
        System.assertEquals(2, undeletedRd.size(), 'Contact and Account Recurring Donations should be cascade undeleted when Account is undeleted.');

        List<Allocation__c> undeletedAllo = [SELECT Id FROM Allocation__c WHERE Id=:allo.Id AND IsDeleted=false ALL ROWS];
        System.assertEquals(1, undeletedAllo.size(), 'Allocation should be cascade undeleted.');
    }

    /*********************************************************************************************************
    * @description Verifies that the losing account in a merge operation doesn't get child records cascade deleted.
    */
    static testMethod void testAccountMergeNoCascade(){

        Account accWinner = new Account(Name='foo');
        insert accWinner;

        Account accLoser = new Account(Name='foo');
        insert accLoser;

        npe03__Recurring_Donation__c rd = new npe03__Recurring_Donation__c(
            Name = 'test',
            npe03__Installments__c = 2,
            npe03__Amount__c = 1,
            npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodYearly,
            npe03__Date_Established__c = date.newinstance(1970,6,12),
            npe03__Schedule_Type__c = system.label.npe03.RecurringDonationMultiplyValue,
            npe03__Organization__c = accLoser.id
        );
        insert rd;

        merge accWinner accLoser;

        List<npe03__Recurring_Donation__c> rdQuery = [SELECT Id, npe03__Organization__c FROM npe03__Recurring_Donation__c WHERE Id = :rd.id];
        System.assertEquals(1, rdQuery.size(), 'Recurring Donation should not be deleted as the result of losing a merge.');
        System.assertEquals(accWinner.id, rdQuery[0].npe03__Organization__c, 'Recurring Donation should be reparented to the winning account.');
    }
    
    /*********************************************************************************************************
    * @description Verifies that deleting an account whose contacts have relationships deletes both relationship objects.
    */
    static testMethod void testAccountCascadeRelationship(){

        Contact con1 = new Contact(LastName='foo1');
        insert con1;

        Contact con2 = new Contact(LastName='foo2');
        insert con2;

        npe4__Relationship__c rel = new npe4__Relationship__c(
              npe4__contact__c = con1.id, 
              npe4__relatedcontact__c = con2.id, 
              npe4__Type__c='Friend',
              npe4__Status__c='Active'
        );
        insert rel;
        
        List<npe4__Relationship__c> queryRel = [SELECT id FROM npe4__Relationship__c];
        System.assertEquals(2, queryRel.size(), 'Two relationships should be created: the inserted relationship and its mirror.');

        //select auto created hh account
        Account acc = [SELECT Id FROM Account WHERE Id IN (SELECT AccountId FROM Contact WHERE Id = :con1.id)];

        Test.startTest();
        delete acc;
        Test.stopTest();

        queryRel = [SELECT id FROM npe4__Relationship__c WHERE IsDeleted = true ALL ROWS];
        System.assertEquals(2, queryRel.size(), 'Both relationships should be deleted when the account is deleted.');

        //flag needs to be reset as the delete and undelete are in the same context for tests.
        UTIL_CascadeDeleteLookups_TDTM.disableCascade = false;
        undelete acc;

        queryRel = [SELECT id FROM npe4__Relationship__c];
        System.assertEquals(2, queryRel.size(), 'Both relationships should be undeleted when account is undeleted.');
    }
}