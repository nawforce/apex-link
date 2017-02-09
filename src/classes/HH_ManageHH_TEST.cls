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
* @group Households
* @group-content ../../ApexDocContent/Households.htm
* @description Apex test class for all Apex code used in the Manage Household UI Lightning Component based page
*/
@isTest
private class HH_ManageHH_TEST {

    /*********************************************************************************************************
    * @description Tests opening the ManageHouseholdUI page on an existing HH Account
    */
    public static testMethod void testExistingHHAccount() { 
        
        Account hh = new Account(Name = 'test_hh', npe01__SYSTEM_AccountType__c = CAO_Constants.HH_ACCOUNT_TYPE);
        insert hh;
        
        Test.startTest();
        
        ApexPages.currentPage().getParameters().put('Id', hh.Id);             
        HH_ManageHH_CTRL ctrl = new HH_ManageHH_CTRL();
        
        system.assertEquals(hh.Id, ctrl.hhId);
        system.assertNotEquals(null, ctrl.hh);
        system.assertEquals(true, ctrl.isHHAccount);
        system.assertNotEquals(null, ctrl.hhFieldSet);
        if (UTIL_Namespace.getNamespace() != '')
            system.assertEquals(UTIL_Namespace.getNamespace(), ctrl.namespacePrefix);
        else
            system.assertEquals('', ctrl.namespacePrefix);
        system.assertEquals(null, ctrl.save());
        
       Test.stopTest();
    }

    /*********************************************************************************************************
    * @description Tests opening the ManageHouseholdUI page on an existing HH Object
    */
    public static testMethod void testExistingHHObject() { 
        
        npo02__Household__c hh = new npo02__Household__c(Name = 'test_hh');
        insert hh;
        
        Test.startTest();
        
        ApexPages.currentPage().getParameters().put('Id', hh.Id);             
        HH_ManageHH_CTRL ctrl = new HH_ManageHH_CTRL();
        
        system.assertEquals(hh.Id, ctrl.hhId);
        system.assertNotEquals(null, ctrl.hh);
        system.assertEquals(false, ctrl.isHHAccount);
        system.assertNotEquals(null, ctrl.hhFieldSet);
        if (UTIL_Namespace.getNamespace() != '')
            system.assertEquals(UTIL_Namespace.getNamespace(), ctrl.namespacePrefix);
        else
            system.assertEquals('', ctrl.namespacePrefix);
        system.assertEquals(null, ctrl.save());
        
        Test.stopTest();
    }

    /*********************************************************************************************************
    * @description Tests opening the ManageHouseholdUI page on an new HH Object
    */
    public static testMethod void testNewHHObject() { 
        
        Account acc = new Account(Name='some org account');
        insert acc;
        
        Contact con = new Contact(Lastname='foo', AccountId=acc.Id);
        insert con;
        
        con = [select Id, Lastname, AccountId, npo02__Household__c from Contact];
        system.assertEquals(null, con.npo02__Household__c);
        
        Test.startTest();
        
        ApexPages.currentPage().getParameters().put('cid', con.Id);             
        HH_ManageHH_CTRL ctrl = new HH_ManageHH_CTRL();
        system.assertEquals(null, ctrl.handleNewHousehold());
        system.assertNotEquals(null, ctrl.hh);
        system.assertEquals(false, ctrl.isHHAccount);
        system.assertNotEquals(null, ctrl.hhFieldSet);
        system.assertEquals(null, ctrl.save());
        
        Test.stopTest();

        con = [select Id, Lastname, AccountId, npo02__Household__c from Contact];
        system.assertNotEquals(null, con.npo02__Household__c);
    }
}