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
* @description Apex test class for all Apex code used in HH_Container_LCTRL
*/
@isTest
private class HH_Container_TEST {

    private static Account hhA;
    private static npo02__Household__c hhO;
    private static Contact conA;
    private static Contact conO;
    
    /*********************************************************************************************************
    * @description initializes test data of a Household Account and a Household Object
    */
    private static void initTestData() { 
        
        // create a Household Account and contact and address object
        hhA = new Account(Name = 'test_hh', npe01__SYSTEM_AccountType__c = CAO_Constants.HH_ACCOUNT_TYPE);
        insert hhA;
        conA = new Contact(FirstName='cA', LastName='conA', AccountId=hhA.Id, MailingCity='conA city', MailingCountry='United States');
        insert conA;
        Address__c addr = new Address__c(Household_Account__c=hhA.Id, Default_Address__c=false, 
            MailingCity__c='addr city', MailingCountry__c='United States');
        insert addr;

        // create a 1:1 account, Household Object and contact
        Account acc = new Account(Name = 'one2one', npe01__SYSTEM_AccountType__c = CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE);
        insert acc;
        hhO = new npo02__Household__c(Name = 'test_hh', npo02__MailingCity__c ='conO city', npo02__MailingCountry__c ='United States');
        insert hhO;
        conO = new Contact(FirstName='cO', LastName='conO', AccountId=acc.Id, npo02__Household__c=hhO.Id, MailingCity='conO city', MailingCountry='United States');
        insert conO;
    }

    /*********************************************************************************************************
    * @description Tests getHH() and getContacts()
    */
    public static testMethod void testGetHHGetContacts() { 
        
        initTestData();

        Test.startTest();

        system.assertNotEquals(null, HH_Container_LCTRL.getHH(hhA.Id));
        system.assertNotEquals(null, HH_Container_LCTRL.getHH(hhO.Id));
        system.assertNotEquals(null, HH_Container_LCTRL.getContacts(hhA.Id));
        system.assertNotEquals(null, HH_Container_LCTRL.getContacts(hhO.Id));
                
        Test.stopTest();
    }

    /*********************************************************************************************************
    * @description Tests getAddresses()
    */
    public static testMethod void testGetAddresses() { 
        
        initTestData();

        Test.startTest();
        
        list<Address__c> listAddrA = HH_Container_LCTRL.getAddresses(hhA.Id, null);
        system.assertEquals(2, listAddrA.size());

        list<Address__c> listAddrO = HH_Container_LCTRL.getAddresses(hhO.Id, null);
        system.assertEquals(1, listAddrO.size());
                
        Test.stopTest();
    }

    /*********************************************************************************************************
    * @description Tests packageLabels()
    */
    public static testMethod void testPackageLabels() { 
        HH_Container_LCTRL.packageLabels();
    }
        
    /*********************************************************************************************************
    * @description Tests upsertContacts()
    */
    public static testMethod void testUpsertContacts() { 
        initTestData();

        conA.FirstName = 'upserted';
        conO.FirstName = 'upserted';
        Contact con = new Contact(FirstName='inserted', LastName='test_hh');
        list<Contact> listCon = new list<Contact>{conA, conO, con};

        Test.startTest();
                    
        HH_Container_LCTRL.upsertContacts(listCon);
        listCon = [select FirstName from Contact order by FirstName desc];
        system.assertEquals(3, listCon.size());
        system.assertEquals('upserted', listCon[0].FirstName);
        system.assertEquals('upserted', listCon[1].FirstName);
        system.assertEquals('inserted', listCon[2].FirstName);

        Test.stopTest();
        
    }

    /*********************************************************************************************************
    * @description Tests deleteContacts()
    public static testMethod void testDeleteContacts() { 
        initTestData();
        list<Contact> listCon = new list<Contact>{conA, conO};

        Test.startTest();
                    
        HH_Container_LCTRL.deleteContacts(listCon);
        listCon = [select FirstName from Contact order by FirstName desc];
        system.assertEquals(0, listCon.size());

        Test.stopTest();        
    }
    */

    /*********************************************************************************************************
    * @description Tests updateHousehold()
    */
    public static testMethod void testUpdateHousehold() { 
        initTestData();

        Test.startTest();

        hhA.Name = 'updated';            
        HH_Container_LCTRL.updateHousehold(hhA);
        hhA = [select Name from Account where Id = :hhA.Id];
        system.assertEquals('updated', hhA.Name);

        hhO.Name = 'updated';            
        HH_Container_LCTRL.updateHousehold(hhO);
        hhO = [select Name from npo02__Household__c where Id = :hhO.Id];
        system.assertEquals('updated', hhO.Name);

        Test.stopTest();        
    }

    /*********************************************************************************************************
    * @description Tests mergeHouseholds()
    */
    public static testMethod void testMergeHouseholds() { 
        initTestData();
        Account hhA2 = new Account(Name = 'test_hh2', npe01__SYSTEM_AccountType__c = CAO_Constants.HH_ACCOUNT_TYPE);
        Account hhA3 = new Account(Name = 'test_hh3', npe01__SYSTEM_AccountType__c = CAO_Constants.HH_ACCOUNT_TYPE);
        list<Account> listAcc = new list<Account>{hhA2, hhA3};
        insert listAcc;
 
        Test.startTest();

        HH_Container_LCTRL.mergeHouseholds(hhA, listAcc);
        system.assertEquals(0, [select count() from Account where Id in :listAcc]);
        
        Test.stopTest();        
    }
    
    /*********************************************************************************************************
    * @description Tests getHHNamesGreetings()
    */
    public static testMethod void testGetHHNamesGreetings() { 
        initTestData();
        list<Contact> listCon = new list<Contact>{conA, conO};
 
        Test.startTest();

        hhA = (Account)HH_Container_LCTRL.getHHNamesGreetings(hhA, listCon);
        system.assertEquals('conA and conO Household', hhA.Name);
        system.assertEquals('cA conA and cO conO', hhA.npo02__Formal_Greeting__c );
        system.assertEquals('cA and cO', hhA.npo02__Informal_Greeting__c );
        
        Test.stopTest();        
    }
    
    /*********************************************************************************************************
    * @description Tests addContactAddresses()
    */
    public static testMethod void testAddContactAddresses() { 
        
        initTestData();

        Test.startTest();
        
        list<Address__c> listAddrA = HH_Container_LCTRL.getAddresses(hhA.Id, null);
        // conA city, addr city
        system.assertEquals(2, listAddrA.size());

        list<Contact> listCon = new list<Contact>();
        for (integer i = 0; i < 5; i++) {
            Contact con = new Contact(FirstName='c'+i, LastName='con'+i, AccountId=hhA.Id, MailingCity='con' + i + ' city', MailingCountry='United States');
            listCon.add(con);
        }
        Contact conADup = new Contact(FirstName='cA', LastName='conA', AccountId=hhA.Id, MailingCity='conA city', MailingCountry='United States');
        listCon.add(conADup);       

        list<Address__c> listAddr = HH_Container_LCTRL.addContactAddresses(listCon, listAddrA);
        system.assertEquals(7, listAddr.size());
        
        Test.stopTest();
    }

    /*********************************************************************************************************
    * @description Tests getSalutations()
    */
    public static testMethod void testGetSalutations() { 
        list<HH_Container_LCTRL.SelectOption> listSO = HH_Container_LCTRL.getSalutations();
        system.assert(listSO.size() > 0);
    }
    
    /*********************************************************************************************************
    * @description Tests HH_AutoCompleteDataProvider_LCTRL
    */
    public static testMethod void testAutoCompleteDataProvider() { 
        
        initTestData();

        Test.startTest();
        
        list<HH_AutoCompleteDataProvider_LCTRL.ProviderResult> listPR = HH_AutoCompleteDataProvider_LCTRL.queryObjects('con', null);
        system.assertEquals(2, listPR.size());

       	list<Contact> listCon = new list<Contact>();
        listCon.add(conA);
        listCon.add(conO);
        listPR = HH_AutoCompleteDataProvider_LCTRL.queryObjects('con', listCon);
        system.assertEquals(0, listPR.size());
        
        Test.stopTest();
    }
}