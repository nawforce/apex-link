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
private class OPP_CascadeDeleteLookups_TEST {

    /*********************************************************************************************************
    * @description Deletes an opportunity which deletes an allocation. Verifies results.
    * Then undeletes the opportunity, which undeletes the allocation. Verifies results.
    */
    static testMethod void testOppCascade(){
        Account org = new Account(Name='org');
        insert org;

        Opportunity orgOpp = new Opportunity(
            Amount = 8,
            AccountId = org.id,
            Name = 'foo',
            StageName = UTIL_UnitTestData_TEST.getClosedLostStage(),
            CloseDate = System.today()
        );
        insert orgOpp;

        General_Accounting_Unit__c gau = new General_Accounting_Unit__c(Name='foo');
        insert gau;
        Allocation__c alloForInsert = new Allocation__c(Opportunity__c=orgOpp.id, Amount__c = 4, General_Accounting_Unit__c = gau.id);
        insert alloForInsert;

        Test.startTest();
        delete orgOpp;
        Test.stopTest();

        List<Allocation__c> deletedAllo = [SELECT Id FROM Allocation__c WHERE Id=:alloForInsert.Id AND IsDeleted=true ALL ROWS];
        System.assertEquals(1, deletedAllo.size(), 'Allocation should be cascade deleted when opportunity is deleted.');

        //flag needs to be reset as the delete and undelete are in the same context for tests.
        UTIL_CascadeDeleteLookups_TDTM.disableCascade = false;
        undelete orgOpp;

        List<Allocation__c> undeletedAllo = [SELECT Id FROM Allocation__c WHERE Id=:alloForInsert.Id AND IsDeleted=false ALL ROWS];
        System.assertEquals(1, undeletedAllo.size(), 'Allocation should be cascade undeleted.');
    }
}