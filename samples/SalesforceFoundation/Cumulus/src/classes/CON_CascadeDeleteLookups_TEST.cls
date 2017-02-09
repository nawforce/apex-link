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
* @description Tests for cascade delete of Contacts
*/
@isTest
private class CON_CascadeDeleteLookups_TEST {

    /*********************************************************************************************************
    * @description Deletes contact without using the override. Does not cascade delete recurring dontation or 
    * allocation. Verifies results.
    */
    static testMethod void testContactCascadeWithoutOverride(){
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

        General_Accounting_Unit__c gau = new General_Accounting_Unit__c(Name='foo');
        insert gau;
        Allocation__c alloForInsert = new Allocation__c(Recurring_Donation__c=rd.id, Amount__c = 4, General_Accounting_Unit__c = gau.id);
        insert alloForInsert;

        Test.startTest();
        delete con;
        Test.stopTest();

        List<Error__c> errors = [SELECT Id, Object_Type__c, Full_Message__c, Stack_Trace__c FROM Error__c];
        System.assertEquals(0, errors.size(), errors);

        List<npe03__Recurring_Donation__c> queryRd = [SELECT Id FROM npe03__Recurring_Donation__c WHERE Id=:rd.Id];
        System.assertEquals(1, queryRd.size(), 'Recurring Donation shouldn\'t be cascade deleted when Contact is deleted without the override.');

        List<Allocation__c> queryAllo = [SELECT Id FROM Allocation__c WHERE Id=:alloForInsert.Id];
        System.assertEquals(1, queryAllo.size(), 'Allocation shouldn\'t be cascade deleted when recurring donation is deleted.');
    }

    /*********************************************************************************************************
    * @description Attempts to delete contact with closed/won and org opps, verifies correct exception is thrown.
    */
    static testMethod void testContactDeleteExceptionWithOverride(){
        //use a non-system account for the contact's account, so that the delete override doesn't redirect us to delete the account
        Account conAccount = new Account(Name='ConAccount');
        insert conAccount;

        Contact con = new Contact(LastName='foo', AccountId=conAccount.id);
        insert con;

        //org account for opportunity attribution
        Account org = new Account(Name='org');
        insert org;

        Opportunity closedWonOpp = new Opportunity(
            Amount = 8,
            Name = 'foo',
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage(),
            CloseDate = System.today(),
            Primary_Contact__c = con.id
        );
        insert closedWonOpp;

        Opportunity orgOpp = new Opportunity(
            Amount = 8,
            AccountId = org.id,
            Name = 'foo',
            StageName = UTIL_UnitTestData_TEST.getClosedLostStage(),
            CloseDate = System.today(),
            Primary_Contact__c = con.id
        );
        insert orgOpp;

        Test.setCurrentPage(Page.CON_DeleteContactOverride);
        ApexPages.currentPage().getParameters().put('id', con.id);
        CON_DeleteContactOverride_CTRL ctrl = new CON_DeleteContactOverride_CTRL(new ApexPages.StandardController(con));

        Test.startTest();
        ctrl.processDelete();
        Test.stopTest();

        System.assert(ApexPages.hasMessages(), 'An exception should be caught and added to page messages.');
        System.assert(ApexPages.getMessages()[0].getDetail().contains(closedWonOpp.id), 'Exception message should contain the closed won opportunity id.');
        System.assert(ApexPages.getMessages()[0].getDetail().contains(Label.ClosedWonOpportunities), 'Exception message should mention Closed Won Opportunities.');
        System.assert(ApexPages.getMessages()[0].getDetail().contains(orgOpp.id), 'Exception message should contain the org opportunity id.');
        System.assert(ApexPages.getMessages()[0].getDetail().contains(Label.OrganizationalOpportunities), 'Exception message should mention Organizational Opportunities.');

    }

    /*********************************************************************************************************
    * @description Verifies that the losing contact in a merge operation doesn't get child records cascade deleted.
    */
    static testMethod void testContactMergeNoCascade(){

        Contact conWinner = new Contact(LastName='foo');
        insert conWinner;

        Contact conLoser = new Contact(LastName='foo');
        insert conLoser;

        npe03__Recurring_Donation__c rd = new npe03__Recurring_Donation__c(
            Name = 'test',
            npe03__Installments__c = 2,
            npe03__Amount__c = 1,
            npe03__Installment_Period__c = system.label.npe03.RecurringDonationInstallmentPeriodYearly,
            npe03__Date_Established__c = date.newinstance(1970,6,12),
            npe03__Schedule_Type__c = system.label.npe03.RecurringDonationMultiplyValue,
            npe03__Contact__c = conLoser.id
        );
        insert rd;

        merge conWinner conLoser;

        List<npe03__Recurring_Donation__c> rdQuery = [SELECT Id, npe03__Contact__c FROM npe03__Recurring_Donation__c WHERE Id = :rd.id];
        System.assertEquals(1, rdQuery.size(), 'Recurring Donation should not be deleted as the result of losing a merge.');
        System.assertEquals(conWinner.id, rdQuery[0].npe03__Contact__c, 'Recurring Donation should be reparented to the winning contact.');

    }

    /*********************************************************************************************************
    * @description Verifies that deleting a contact with a relationship deletes both relationship objects.
    */
    static testMethod void testContactCascadeRelationshipWithoutOverride(){

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

        Test.startTest();
        delete con1;
        Test.stopTest();

        queryRel = [SELECT id FROM npe4__Relationship__c WHERE IsDeleted = true ALL ROWS];
        System.assertEquals(2, queryRel.size(), 'Both relationships should be deleted when the account is deleted.');

        //flag needs to be reset as the delete and undelete are in the same context for tests.
        UTIL_CascadeDeleteLookups_TDTM.disableCascade = false;
        undelete con1;

        queryRel = [SELECT id FROM npe4__Relationship__c];
        System.assertEquals(2, queryRel.size(), 'Both relationships should be undeleted when contact is undeleted.');
    }
}