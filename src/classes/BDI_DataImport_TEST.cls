/*
    Copyright (c) 2014 Salesforce.org
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
* @description tests specific to the Data Importer 
*/

@isTest
public with sharing class BDI_DataImport_TEST {

    /**
    * @description if you only want to run one test in this class, fill in its name here.
    * if you want to run all tests, then use '*'
    */
    private static string strTestOnly = '*';
    
    // utility to create a new di record    
    public static DataImport__c newDI(string firstname, string lastname) {
        return new DataImport__c(
           Contact1_Firstname__c = firstname,
           Contact1_Lastname__c = lastname,
           Contact1_Personal_Email__c = firstname + '@' + lastname + '.com'
        );
    }

    // utility to create a new di record    
    public static DataImport__c newDI(string firstname, string lastname, string firstname2, string lastname2) {
        return new DataImport__c(
           Contact1_Firstname__c = firstname,
           Contact1_Lastname__c = lastname,
           Contact1_Personal_Email__c = firstname + '@' + lastname + '.com',
           Contact2_Firstname__c = firstname2,
           Contact2_Lastname__c = lastname2,
           Contact2_Personal_Email__c = firstname2 + '@' + lastname2 + '.com'
        );
    }

    /*********************************************************************************************************
    operation:
        import matching contacts within the same di record 
    verify:
        only one contact created
    **********************************************************************************************************/            
    static testMethod void OneDIDuplicateNewContacts() {
        if (strTestOnly != '*' && strTestOnly != 'OneDIDuplicateNewContacts') return;
            
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(newDI('c1', 'C1', 'c1', 'C1'));
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results
        list<Contact> listCon = [select Id, Name from Contact];
        system.assertEquals(1, listCon.size());
        system.assertEquals('c1 C1', listCon[0].Name);
        
        listDI = [select Contact1Imported__c, Contact1ImportStatus__c, HouseholdAccountImported__c, Contact2Imported__c, Contact2ImportStatus__c from DataImport__c];
        system.assertEquals(1, listDI.size());
        system.assertEquals(listDI[0].Contact1Imported__c, listCon[0].Id);
        system.assertEquals(listDI[0].Contact2Imported__c, listCon[0].Id);
        system.assertNotEquals(null, listDI[0].HouseholdAccountImported__c);
        system.assertEquals(label.bdiCreated, listDI[0].Contact1ImportStatus__c);
        system.assertEquals(label.bdiMatched, listDI[0].Contact2ImportStatus__c);        
    }
    
    /*********************************************************************************************************
    operation:
        import different contacts within the same di record 
    verify:
        two contacts created
    **********************************************************************************************************/            
    static testMethod void OneDITwoNewContacts() {
        if (strTestOnly != '*' && strTestOnly != 'OneDITwoNewContacts') return;
            
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(newDI('c1', 'C1', 'c2', 'C2'));
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results
        list<Contact> listCon = [select Id, Name from Contact order by Lastname];
        system.assertEquals(2, listCon.size());
        system.assertNotEquals(listCon[1].Name, listCon[0].Name);

        listDI = [select Contact1Imported__c, Contact1ImportStatus__c, HouseholdAccountImported__c, 
            Contact2Imported__c, Contact2ImportStatus__c from DataImport__c];
        system.assertEquals(1, listDI.size());
        system.assertEquals(listDI[0].Contact1Imported__c, listCon[0].Id);
        system.assertEquals(listDI[0].Contact2Imported__c, listCon[1].Id);
        system.assertNotEquals(null, listDI[0].HouseholdAccountImported__c);        
        system.assertEquals(label.bdiCreated, listDI[0].Contact1ImportStatus__c);
        system.assertEquals(label.bdiCreated, listDI[0].Contact2ImportStatus__c);        
    }

    /*********************************************************************************************************
    operation:
        import matching contacts within multiple di records in the same batch 
    verify:
        only one contact created
    **********************************************************************************************************/            
    static testMethod void TwoDIDuplicateNewContacts() {
        if (strTestOnly != '*' && strTestOnly != 'TwoDIDuplicateNewContacts') return;
            
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(newDI('c1', 'C1'));
        listDI.add(newDI('c1', 'C1'));
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results
        list<Contact> listCon = [select Id, Name from Contact];
        system.assertEquals(1, listCon.size());
        system.assertEquals('c1 C1', listCon[0].Name);

        listDI = [select Contact1Imported__c, Contact1ImportStatus__c, HouseholdAccountImported__c, 
            Contact2Imported__c, Contact2ImportStatus__c from DataImport__c order by Contact1ImportStatus__c];
        system.assertEquals(2, listDI.size());
        system.assertEquals(listDI[0].Contact1Imported__c, listCon[0].Id);
        system.assertEquals(listDI[1].Contact1Imported__c, listCon[0].Id);
        system.assertNotEquals(null, listDI[0].HouseholdAccountImported__c);
        system.assertNotEquals(null, listDI[1].HouseholdAccountImported__c);
        system.assertEquals(label.bdiCreated, listDI[0].Contact1ImportStatus__c);
        system.assertEquals(label.bdiMatched, listDI[1].Contact1ImportStatus__c);        
    }
    
    /*********************************************************************************************************
    operation:
        import different contacts within multiple di records in the same batch
    verify:
        two contacts created
    **********************************************************************************************************/            
    static testMethod void TwoDITwoNewContacts() {
        if (strTestOnly != '*' && strTestOnly != 'TwoDITwoNewContacts') return;
            
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(newDI('c1', 'C1'));
        listDI.add(newDI('c2', 'C2'));
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results
        list<Contact> listCon = [select Id, Name from Contact order by Id];
        system.assertEquals(2, listCon.size());
        system.assertNotEquals(listCon[1].Name, listCon[0].Name);

        listDI = [select Contact1Imported__c, Contact1ImportStatus__c, HouseholdAccountImported__c, 
            Contact2Imported__c, Contact2ImportStatus__c from DataImport__c order by Contact1Imported__c];
        system.assertEquals(2, listDI.size());
        system.assertEquals(listDI[0].Contact1Imported__c, listCon[0].Id);
        system.assertEquals(listDI[1].Contact1Imported__c, listCon[1].Id);
        system.assertNotEquals(null, listDI[0].HouseholdAccountImported__c);
        system.assertNotEquals(null, listDI[1].HouseholdAccountImported__c);
        system.assertEquals(label.bdiCreated, listDI[0].Contact1ImportStatus__c);
        system.assertEquals(label.bdiCreated, listDI[1].Contact1ImportStatus__c);        
    }

    /*********************************************************************************************************
    operation:
        import matching contacts within multiple di records in the same batch 
    verify:
        only two contacts created
        only 1 household account created
    **********************************************************************************************************/            
    static testMethod void TwoDIDuplicateNewContactsDifferentOrder() {
        if (strTestOnly != '*' && strTestOnly != 'TwoDIDuplicateNewContactsDifferentOrder') return;
            
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(newDI('c1', 'C1', 'c2', 'C2'));
        listDI.add(newDI('c2', 'C2'));
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results
        list<Contact> listCon = [select Id, Name, AccountId from Contact order by Lastname];
        system.assertEquals(2, listCon.size());
        system.assertEquals('c1 C1', listCon[0].Name);
        system.assertEquals('c2 C2', listCon[1].Name);
        
        // verify only one household account
        list<Account> listAcc = [select Id, Name from Account order by Name desc];
        // because c2 is first created as a contact1, it will get a hh account, that will
        // later get orphaned when we move c2 to c1's hh account.
        system.assertEquals(2, listAcc.size());
        system.assertEquals(listAcc[0].Id, listCon[0].AccountId);
        system.assertEquals(listAcc[0].Id, listCon[1].AccountId);

        listDI = [select Contact1Imported__c, Contact1ImportStatus__c, HouseholdAccountImported__c, 
            Contact2Imported__c, Contact2ImportStatus__c from DataImport__c order by Name];
        system.assertEquals(2, listDI.size());
        system.assertEquals(listDI[0].Contact1Imported__c, listCon[0].Id);
        system.assertEquals(listDI[1].Contact1Imported__c, listCon[1].Id);
        system.assertNotEquals(null, listDI[0].HouseholdAccountImported__c);
        system.assertNotEquals(null, listDI[1].HouseholdAccountImported__c);
        system.assertEquals(label.bdiCreated, listDI[0].Contact1ImportStatus__c);
        system.assertEquals(label.bdiCreated, listDI[1].Contact1ImportStatus__c);        
        system.assertEquals(label.bdiMatched, listDI[0].Contact2ImportStatus__c);        
    }

    /*********************************************************************************************************
    operation:
        import existing contacts within the same di record 
    verify:
        two contacts matched
    **********************************************************************************************************/            
    static testMethod void OneDITwoExistingContacts() {
        if (strTestOnly != '*' && strTestOnly != 'OneDITwoExistingContacts') return;
            
        // existing contacts
        list<Contact> listConExisting = new list<Contact>();
        listConExisting.add(new Contact(Firstname='c1', Lastname='C1', Email='c1@C1.com'));
        listConExisting.add(new Contact(Firstname='c2', Lastname='C2', Email='c2@C2.com'));
        insert listConExisting;
        
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(newDI('c1', 'C1', 'c2', 'C2'));
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results
        list<Contact> listCon = [select Id, Name from Contact order by Lastname];
        system.assertEquals(2, listCon.size());
        system.assertNotEquals(listCon[1].Name, listCon[0].Name);
        
        listDI = [select Contact1Imported__c, Contact1ImportStatus__c, HouseholdAccountImported__c, 
            Contact2Imported__c, Contact2ImportStatus__c from DataImport__c];
        system.assertEquals(1, listDI.size());
        system.assertEquals(listDI[0].Contact1Imported__c, listCon[0].Id);
        system.assertEquals(listDI[0].Contact2Imported__c, listCon[1].Id);
        system.assertNotEquals(null, listDI[0].HouseholdAccountImported__c);
        system.assertEquals(label.bdiMatched, listDI[0].Contact1ImportStatus__c);
        system.assertEquals(label.bdiMatched, listDI[0].Contact2ImportStatus__c);        
    }

    /*********************************************************************************************************
    operation:
        import existing contacts that match a 1x1 contact
    verify:
        error given, di not processed.
    **********************************************************************************************************/            
    static testMethod void OneDIExistingContacts1x1() {
        if (strTestOnly != '*' && strTestOnly != 'OneDIExistingContacts1x1') return;
            
        // existing 1x1 contact
        UTIL_UnitTestData_TEST.createAccountContactTestData(CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE, 1, 1, 0);   
        list<Contact> listConExisting = [select Id, Firstname, Lastname, Name, AccountId, Account.npe01__SYSTEM_AccountType__c from Contact];
        system.assertEquals(1, listConExisting.size());
        system.assertEquals(CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE, listConExisting[0].Account.npe01__SYSTEM_AccountType__c);
        system.assertEquals('TestFirstname00 TestLastname00', listConExisting[0].Name);
        listConExisting[0].email = 'foo@bar.com';
        update listConExisting;
        
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(newDI('TestFirstname00', 'TestLastname00', 'c2', 'C2'));
        listDI[0].Contact1_Personal_Email__c = 'foo@bar.com';
        listDI[0].Home_City__c = 'Seattle';
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results
        list<Contact> listCon = [select Id, Name from Contact order by Lastname];
        system.assertEquals(1, listCon.size());
        system.assertEquals('TestFirstname00 TestLastname00', listCon[0].Name);
        
        listDI = [select Contact1Imported__c, Contact1ImportStatus__c, HouseholdAccountImported__c, 
            Contact2Imported__c, Contact2ImportStatus__c from DataImport__c];
        system.assertEquals(1, listDI.size());
        system.assertEquals(listDI[0].Contact1Imported__c, listCon[0].Id);
        system.assertEquals(listDI[0].Contact2Imported__c, null);
        system.assertEquals(null, listDI[0].HouseholdAccountImported__c);
        system.assertEquals(label.bdiErrorNonHHAccountContact, listDI[0].Contact1ImportStatus__c);
        system.assertEquals(null, listDI[0].Contact2ImportStatus__c);     
        list<Address__c> listAddr = [select Id from Address__c];   
        system.assertEquals(0, listAddr.size());
    }

    /*********************************************************************************************************
    operation:
        import different accounts within the same di record 
    verify:
        two accounts created
    **********************************************************************************************************/            
    static testMethod void OneDITwoNewAccounts() {
        if (strTestOnly != '*' && strTestOnly != 'OneDITwoNewAccounts') return;
            
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(new DataImport__c(Account1_Name__c='A1', Account2_Name__c='A2'));
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results
        list<Account> listAcc = [select Id, Name from Account order by Name];
        system.assertEquals(2, listAcc.size());
        system.assertNotEquals(listAcc[1].Name, listAcc[0].Name);

        listDI = [select Account1Imported__c, Account1ImportStatus__c, Account2Imported__c, Account2ImportStatus__c from DataImport__c];
        system.assertEquals(1, listDI.size());
        system.assertEquals(listDI[0].Account1Imported__c, listAcc[0].Id);
        system.assertEquals(listDI[0].Account2Imported__c, listAcc[1].Id);
        system.assertEquals(label.bdiCreated, listDI[0].Account1ImportStatus__c);
        system.assertEquals(label.bdiCreated, listDI[0].Account2ImportStatus__c);        
    }

    /*********************************************************************************************************
    operation:
        import matching accounts within the same di record 
    verify:
        one accounts created
    **********************************************************************************************************/            
    static testMethod void OneDITwoSameAccounts() {
        if (strTestOnly != '*' && strTestOnly != 'OneDITwoSameAccounts') return;
            
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(new DataImport__c(Account1_Name__c='A1', Account2_Name__c='A1'));
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results
        list<Account> listAcc = [select Id, Name from Account order by Name];
        system.assertEquals(1, listAcc.size());

        listDI = [select Account1Imported__c, Account1ImportStatus__c, Account2Imported__c, Account2ImportStatus__c from DataImport__c];
        system.assertEquals(1, listDI.size());
        system.assertEquals(listDI[0].Account1Imported__c, listAcc[0].Id);
        system.assertEquals(listDI[0].Account2Imported__c, listAcc[0].Id);
        system.assertEquals(label.bdiCreated, listDI[0].Account1ImportStatus__c);
        system.assertEquals(label.bdiMatched, listDI[0].Account2ImportStatus__c);        
    }

    /*********************************************************************************************************
    operation:
        import matching accounts in different di records 
    verify:
        one account created
    **********************************************************************************************************/            
    static testMethod void TwoDISameAccounts() {
        if (strTestOnly != '*' && strTestOnly != 'TwoDISameAccounts') return;
            
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(new DataImport__c(Account1_Name__c='A1'));
        listDI.add(new DataImport__c(Account1_Name__c='A1'));
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results
        list<Account> listAcc = [select Id, Name from Account order by Name];
        system.assertEquals(1, listAcc.size());

        listDI = [select Account1Imported__c, Account1ImportStatus__c, Account2Imported__c, Account2ImportStatus__c from DataImport__c order by Account1ImportStatus__c];
        system.assertEquals(2, listDI.size());
        system.assertEquals(listDI[0].Account1Imported__c, listAcc[0].Id);
        system.assertEquals(listDI[1].Account1Imported__c, listAcc[0].Id);
        system.assertEquals(label.bdiCreated, listDI[0].Account1ImportStatus__c);
        system.assertEquals(label.bdiMatched, listDI[1].Account1ImportStatus__c);        
    }

    /*********************************************************************************************************
    operation:
        import existing accounts 
    verify:
        no accounts created, just matched
    **********************************************************************************************************/            
    static testMethod void TwoDIExistingAccounts() {
        if (strTestOnly != '*' && strTestOnly != 'TwoDIExistingAccounts') return;
            
        list<Account> listAcc = new list<Account>();
        listAcc.add(new Account(name='A1'));
        listAcc.add(new Account(name='A2'));
        insert listAcc;    
                    
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(new DataImport__c(Account1_Name__c='A1'));
        listDI.add(new DataImport__c(Account1_Name__c='A2'));
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results
        listAcc = [select Id, Name from Account order by Name];
        system.assertEquals(2, listAcc.size());

        listDI = [select Account1Imported__c, Account1ImportStatus__c, Account2Imported__c, Account2ImportStatus__c from DataImport__c order by Id];
        system.assertEquals(2, listDI.size());
        system.assertEquals(listDI[0].Account1Imported__c, listAcc[0].Id);
        system.assertEquals(listDI[1].Account1Imported__c, listAcc[1].Id);
        system.assertEquals(label.bdiMatched, listDI[0].Account1ImportStatus__c);
        system.assertEquals(label.bdiMatched, listDI[1].Account1ImportStatus__c);        
    }

    /*********************************************************************************************************
    operation:
        import existing accounts, matched by Salesforce Id 
    verify:
        no accounts created, just matched
    **********************************************************************************************************/            
    static testMethod void TwoDIExistingAccountsSalesforceId() {
        if (strTestOnly != '*' && strTestOnly != 'TwoDIExistingAccountsSalesforceId') return;
            
        list<Account> listAcc = new list<Account>();
        listAcc.add(new Account(name='A1'));
        listAcc.add(new Account(name='A2'));
        insert listAcc;    
                    
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(new DataImport__c(Contact1_Firstname__c='c1', Contact1_Lastname__c='C1', 
            Account1Imported__c=listAcc[0].Id));
        listDI.add(new DataImport__c(Contact1_Firstname__c='c1', Contact1_Lastname__c='C2', Contact2_Firstname__c='c2', 
            Account1Imported__c=listAcc[0].Id, Account2Imported__c=listAcc[1].Id));
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results
        listAcc = [select Id, Name from Account where Type != 'Household' order by Name];
        system.assertEquals(2, listAcc.size());

        listDI = [select Account1Imported__c, Account1ImportStatus__c, Account2Imported__c, Account2ImportStatus__c from DataImport__c order by Contact1_Lastname__c];
        system.assertEquals(2, listDI.size());
        system.assertEquals(listDI[0].Account1Imported__c, listAcc[0].Id);
        system.assertEquals(listDI[1].Account1Imported__c, listAcc[0].Id);
        system.assertEquals(listDI[1].Account2Imported__c, listAcc[1].Id);
        system.assertEquals(label.bdiMatched, listDI[0].Account1ImportStatus__c);
        system.assertEquals(label.bdiMatched, listDI[1].Account1ImportStatus__c);        
        system.assertEquals(label.bdiMatched, listDI[1].Account2ImportStatus__c);        
    }

    /*********************************************************************************************************
    operation:
        import contacts with Preferred Email set
    verify:
        ensure the appropriate contact email field gets copied into the standard email field.
    **********************************************************************************************************/
    /** can't use this test because it relies on our workflow rules being active, which we can't enforce.
    static testMethod void ThreeDINewContactsWithPreferredEmail() {
        if (strTestOnly != '*' && strTestOnly != 'ThreeDINewContactsWithPreferredEmail') return;
            
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(newDI('c1', 'C1'));
        listDI.add(newDI('c2', 'C2'));
        listDI.add(newDI('c3', 'C3'));
        listDI[0].Contact1_Preferred_Email__c = 'Home';
        listDI[0].Contact1_Personal_Email__c = 'c1@home.com';
        listDI[1].Contact1_Preferred_Email__c = 'Work';
        listDI[1].Contact1_Work_Email__c = 'c2@work.com';
        listDI[2].Contact1_Preferred_Email__c = 'Alternate';
        listDI[2].Contact1_Personal_Email__c = 'c3@alt.com';
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results
        list<Contact> listCon = [select Id, Name, Email from Contact order by Lastname];
        system.assertEquals(3, listCon.size());
        system.assertEquals('c1 C1', listCon[0].Name);
        system.assertEquals('c1@home.com', listCon[0].Email);
        system.assertEquals('c2 C2', listCon[1].Name);
        system.assertEquals('c2@work.com', listCon[1].Email);
        system.assertEquals('c3 C3', listCon[2].Name);
        system.assertEquals('c3@alt.com', listCon[2].Email);        
    }
    ***************/

    /*********************************************************************************************************
    operation:
        import contacts with accounts
    verify:
        contacts created
        accounts created
        affiliations created between contacts and accounts
    **********************************************************************************************************/            
    static testMethod void TwoDIAffiliations() {
        if (strTestOnly != '*' && strTestOnly != 'TwoDIAffiliations') return;
            
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(newDI('c1', 'C1', 'c2', 'C2'));
        listDI.add(newDI('c3', 'C3', 'c4', 'C4'));
        listDI[0].Account1_Name__c = 'A1';
        listDI[1].Account2_Name__c = 'A2';
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results
        list<Contact> listCon = [select Id, Name, AccountId, Primary_Affiliation__c from Contact order by Lastname];
        system.assertEquals(4, listCon.size());
        system.assertEquals('c1 C1', listCon[0].Name);
        system.assertEquals('c2 C2', listCon[1].Name);
        system.assertEquals('c3 C3', listCon[2].Name);
        system.assertEquals('c4 C4', listCon[3].Name);
        system.assertNotEquals(null, listCon[0].AccountId);
        system.assertNotEquals(null, listCon[1].AccountId);
        system.assertNotEquals(null, listCon[2].AccountId);
        system.assertNotEquals(null, listCon[3].AccountId);
        
        list<Account> listAcc = [select Id, Name from Account where Type != 'Household' order by Name];
        system.assertEquals(2, listAcc.size());
        system.assertEquals(listCon[0].Primary_Affiliation__c, listAcc[0].Id);
        system.assertEquals(listCon[1].Primary_Affiliation__c, null);
        system.assertEquals(listCon[2].Primary_Affiliation__c, null);
        system.assertEquals(listCon[3].Primary_Affiliation__c, listAcc[1].Id);
        
        list<npe5__Affiliation__c> listAffl = [select Id, npe5__Contact__c, npe5__Organization__c from npe5__Affiliation__c order by npe5__Contact__r.Name];
        system.assertEquals(2, listAffl.size());
        system.assertEquals(listCon[0].Id, listAffl[0].npe5__Contact__c);
        system.assertEquals(listCon[3].Id, listAffl[1].npe5__Contact__c);
                
    }

    /*********************************************************************************************************
    operation:
        import contacts with home addresses 
    verify:
        address objects created
        contacts' mailing address set
        hh accounts' billing address set
    **********************************************************************************************************/            
    static testMethod void TwoDIHomeAddresses() {
        if (strTestOnly != '*' && strTestOnly != 'TwoDIHomeAddresses') return;
            
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(newDI('c1', 'C1', 'c2', 'C2'));
        listDI.add(newDI('c3', 'C3', 'c4', 'C4'));
        listDI[0].Home_City__c = 'Seattle';
        listDI[1].Home_City__c = 'Portland';
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results
        list<Contact> listCon = [select Id, Name, AccountId, MailingCity from Contact order by Lastname];
        system.assertEquals(4, listCon.size());
        system.assertEquals('c1 C1', listCon[0].Name);
        system.assertEquals('c2 C2', listCon[1].Name);
        system.assertEquals('c3 C3', listCon[2].Name);
        system.assertEquals('c4 C4', listCon[3].Name);
        system.assertNotEquals(null, listCon[0].AccountId);
        system.assertNotEquals(null, listCon[1].AccountId);
        system.assertNotEquals(null, listCon[2].AccountId);
        system.assertNotEquals(null, listCon[3].AccountId);
        system.assertEquals('Seattle', listCon[0].MailingCity);
        system.assertEquals('Seattle', listCon[1].MailingCity);
        system.assertEquals('Portland', listCon[2].MailingCity);
        system.assertEquals('Portland', listCon[3].MailingCity);
        
        list<Address__c> listAddr = [select Id, Household_Account__c, MailingCity__c from Address__c order by MailingCity__c]; 
        system.assertEquals(2, listAddr.size());
        system.assertEquals('Portland', listAddr[0].MailingCity__c);
        system.assertEquals('Seattle', listAddr[1].MailingCity__c);
        
        list<Account> listAcc = [select Id, Name, BillingCity from Account order by Name];
        system.assertEquals(2, listAcc.size());
        system.assertEquals('Seattle', listAcc[0].BillingCity);
        system.assertEquals('Portland', listAcc[1].BillingCity);
                
        listDI = [select HomeAddressImported__c, HomeAddressImportStatus__c, Home_City__c from DataImport__c order by Home_City__c];
        system.assertEquals(2, listDI.size());
        system.assertEquals(listDI[0].HomeAddressImported__c, listAddr[0].Id); 
        system.assertEquals(listDI[1].HomeAddressImported__c, listAddr[1].Id);
        system.assertEquals(label.bdiCreated, listDI[0].HomeAddressImportStatus__c);
        system.assertEquals(label.bdiCreated, listDI[1].HomeAddressImportStatus__c);        
    }
    
    /*********************************************************************************************************
    operation:
        import duplicate contacts with duplicate home addresses 
    verify:
        no duplicate address objects created
        contacts' mailing address set
        hh accounts' billing address set
    **********************************************************************************************************/            
    static testMethod void ManyDISameHomeAddresses() {
        if (strTestOnly != '*' && strTestOnly != 'ManyDISameHomeAddresses') return;
            
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(newDI('c1', 'C1', 'c2', 'C2'));
        listDI.add(newDI('c1', 'C1'));
        listDI.add(newDI('c1', 'C1'));
        listDI.add(newDI('c2', 'C2'));
        listDI.add(newDI('c2', 'C2'));

        // add same address to all
        for (DataImport__c di : listDI) {        
            di.Home_Street__c = '123 45th St NE';
            di.Home_City__c = 'Seattle';
            di.Home_State_Province__c = 'Washington';
            di.Home_Zip_Postal_Code__c = '98052';
            di.Home_Country__c = 'United States';
        }
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results
        list<Contact> listCon = [select Id, Name, AccountId, MailingStreet, MailingCity, MailingState, 
            MailingPostalCode, MailingCountry from Contact order by Lastname];
        system.assertEquals(2, listCon.size());
        for (Contact con : listCon) {        
            system.assertEquals(listDI[0].Home_Street__c, con.MailingStreet);
            system.assertEquals(listDI[0].Home_City__c, con.MailingCity);
            system.assertEquals(listDI[0].Home_State_Province__c, con.MailingState);
            system.assertEquals(listDI[0].Home_Zip_Postal_Code__c, con.MailingPostalCode);
            system.assertEquals(listDI[0].Home_Country__c, con.MailingCountry);
        }
        
        list<Address__c> listAddr = [select Id, Household_Account__c, MailingStreet__c, MailingCity__c, MailingState__c, 
            MailingPostalCode__c, MailingCountry__c from Address__c order by MailingCity__c]; 
        system.assertEquals(1, listAddr.size());
        system.assertEquals(listDI[0].Home_Street__c, listAddr[0].MailingStreet__c);
        system.assertEquals(listDI[0].Home_City__c, listAddr[0].MailingCity__c);
        system.assertEquals(listDI[0].Home_State_Province__c, listAddr[0].MailingState__c);
        system.assertEquals(listDI[0].Home_Zip_Postal_Code__c, listAddr[0].MailingPostalCode__c);
        system.assertEquals(listDI[0].Home_Country__c, listAddr[0].MailingCountry__c);
        
        list<Account> listAcc = [select Id, Name, BillingStreet, BillingCity, BillingState, 
            BillingPostalCode, BillingCountry from Account order by Name desc];
        // because c2 is first created as a contact1, it will get a hh account, that will
        // later get orphaned when we move c2 to c1's hh account.
        system.assertEquals(2, listAcc.size());
        system.assertEquals(listDI[0].Home_Street__c, listAcc[0].BillingStreet);
        system.assertEquals(listDI[0].Home_City__c, listAcc[0].BillingCity);
        system.assertEquals(listDI[0].Home_State_Province__c, listAcc[0].BillingState);
        system.assertEquals(listDI[0].Home_Zip_Postal_Code__c, listAcc[0].BillingPostalCode);
        system.assertEquals(listDI[0].Home_Country__c, listAcc[0].BillingCountry);                

        listDI = [select HomeAddressImported__c, HomeAddressImportStatus__c from DataImport__c order by Name];
        system.assertEquals(5, listDI.size());
        integer cCreated = 0;
        integer cMatched = 0;
        for (DataImport__c di : listDI) {
            system.assertEquals(di.HomeAddressImported__c, listAddr[0].Id);
            if (di.HomeAddressImportStatus__c == label.bdiCreated)
                cCreated++; 
            else if (di.HomeAddressImportStatus__c == label.bdiMatched)
                cMatched++; 
        }
        // we didn't have an easy way to track the specific DI record that caused the addr to be created vs matched
        // in these duplicate (but new) scenarios, so we will live with marking them all as created.
        system.assertEquals(5, cCreated);
        system.assertEquals(0, cMatched);
    }

    /*********************************************************************************************************
    operation:
        import contacts with home addresses, while address management is disabled
    verify:
        address objects NOT created
        contacts' mailing address set
        hh accounts' billing address set
    **********************************************************************************************************/            
    static testMethod void TwoDIHomeAddressesAddrMgmtOff() {
        if (strTestOnly != '*' && strTestOnly != 'TwoDIHomeAddressesAddrMgmtOff') return;
            
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR,
                Household_Account_Addresses_Disabled__c = true
        ));

        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(newDI('c1', 'C1', 'c2', 'C2'));
        listDI.add(newDI('c3', 'C3', 'c4', 'C4'));
        listDI[0].Home_City__c = 'Seattle';
        listDI[1].Home_City__c = 'Portland';
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results
        list<Contact> listCon = [select Id, Name, AccountId, MailingCity from Contact order by Lastname];
        system.assertEquals(4, listCon.size());
        system.assertEquals('c1 C1', listCon[0].Name);
        system.assertEquals('c2 C2', listCon[1].Name);
        system.assertEquals('c3 C3', listCon[2].Name);
        system.assertEquals('c4 C4', listCon[3].Name);
        system.assertNotEquals(null, listCon[0].AccountId);
        system.assertNotEquals(null, listCon[1].AccountId);
        system.assertNotEquals(null, listCon[2].AccountId);
        system.assertNotEquals(null, listCon[3].AccountId);
        system.assertEquals('Seattle', listCon[0].MailingCity);
        system.assertEquals('Seattle', listCon[1].MailingCity);
        system.assertEquals('Portland', listCon[2].MailingCity);
        system.assertEquals('Portland', listCon[3].MailingCity);
        
        list<Address__c> listAddr = [select Id, Household_Account__c, MailingCity__c from Address__c order by MailingCity__c]; 
        system.assertEquals(0, listAddr.size());
        
        list<Account> listAcc = [select Id, Name, BillingCity from Account order by Name];
        system.assertEquals(2, listAcc.size());
        system.assertEquals('Seattle', listAcc[0].BillingCity);
        system.assertEquals('Portland', listAcc[1].BillingCity);                
    }

    /*********************************************************************************************************
    operation:
        import existing contacts with the same home address
    verify:
        contacts matched
        address matched
    **********************************************************************************************************/            
    static testMethod void TwoDIMixedAddresses() {
        if (strTestOnly != '*' && strTestOnly != 'TwoDIMixedAddresses') return;
            
        // existing contacts
        list<Contact> listConExisting = new list<Contact>();
        listConExisting.add(new Contact(Firstname='c1', Lastname='C1', Email='c1@C1.com', MailingCity='Seattle'));
        listConExisting.add(new Contact(Firstname='c2', Lastname='C2', Email='c2@C2.com', MailingCity='Portland'));
        insert listConExisting;
        
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(newDI('c1', 'C1'));
        listDI.add(newDI('c2', 'C2'));
        listDI[0].Home_City__c = 'Seattle';
        listDI[1].Home_City__c = 'Bellevue';
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results
        list<Contact> listCon = [select Id, Name from Contact order by Lastname];
        system.assertEquals(2, listCon.size());
        
        list<Address__c> listAddr = [select Id, Household_Account__c, MailingCity__c from Address__c order by MailingCity__c]; 
        system.assertEquals(3, listAddr.size());
        system.assertEquals('Bellevue', listAddr[0].MailingCity__c);
        system.assertEquals('Portland', listAddr[1].MailingCity__c);
        system.assertEquals('Seattle', listAddr[2].MailingCity__c);
        
        listDI = [select Contact1Imported__c, Contact1ImportStatus__c, HouseholdAccountImported__c, 
            HomeAddressImported__c, HomeAddressImportStatus__c from DataImport__c order by Contact1_Lastname__c];
        system.assertEquals(2, listDI.size());
        system.assertEquals(listDI[0].Contact1Imported__c, listCon[0].Id);
        system.assertEquals(listDI[1].Contact1Imported__c, listCon[1].Id);
        system.assertNotEquals(null, listDI[0].HouseholdAccountImported__c);
        system.assertNotEquals(null, listDI[1].HouseholdAccountImported__c);
        system.assertEquals(label.bdiMatched, listDI[0].Contact1ImportStatus__c);
        system.assertEquals(label.bdiMatched, listDI[1].Contact1ImportStatus__c);
        system.assertEquals(listAddr[2].Id, listDI[0].HomeAddressImported__c);   
        system.assertEquals(listAddr[0].Id, listDI[1].HomeAddressImported__c);   
        system.assertEquals(label.bdiMatched, listDI[0].HomeAddressImportStatus__c);
        system.assertEquals(label.bdiCreated, listDI[1].HomeAddressImportStatus__c);            
    }
    
    /*********************************************************************************************************
    operation:
        import existing contacts with the same home address, with Address Management off
    verify:
        contacts matched
        no addresses created
        account addresses updated
        contact addresses updated
    **********************************************************************************************************/            
    static testMethod void TwoDIMixedAddressesAddrMgmtOff() {
        if (strTestOnly != '*' && strTestOnly != 'TwoDIMixedAddressesAddrMgmtOff') return;
            
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR,
                Household_Account_Addresses_Disabled__c = true
        ));

        // existing contacts
        list<Contact> listConExisting = new list<Contact>();
        listConExisting.add(new Contact(Firstname='c1', Lastname='C1', Email='c1@C1.com', MailingCity='Seattle'));
        listConExisting.add(new Contact(Firstname='c2', Lastname='C2', Email='c2@C2.com', MailingCity='Portland'));
        insert listConExisting;
        
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(newDI('c1', 'C1'));
        listDI.add(newDI('c2', 'C2'));
        listDI[0].Home_City__c = 'Bellevue';
        listDI[1].Home_City__c = 'Bellevue';
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results
        list<Contact> listCon = [select Id, Name, MailingCity from Contact order by Lastname];
        system.assertEquals(2, listCon.size());
        system.assertEquals('Bellevue', listCon[0].MailingCity);
        system.assertEquals('Bellevue', listCon[1].MailingCity);
        
        list<Address__c> listAddr = [select Id, Household_Account__c, MailingCity__c from Address__c order by MailingCity__c];
        system.assertEquals(0, listAddr.size()); 
        
        list<Account> listAcc = [select Id, Name, BillingCity from Account order by Name];
        system.assertEquals(2, listAcc.size());
        system.assertEquals('Bellevue', listAcc[0].BillingCity);
        system.assertEquals('Bellevue', listAcc[1].BillingCity);                
    }
    
    /*********************************************************************************************************
    operation:
        import contacts with donation & payment information 
    verify:
        opportunity objects created
        payment objects created
        ocr's created
        contacts' rollups updated
        hh account's rollups updated
    **********************************************************************************************************/            
    static testMethod void TwoDIWithDonations() {
        if (strTestOnly != '*' && strTestOnly != 'TwoDIWithDonations') return;
            
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(newDI('c1', 'C1', 'c2', 'C2'));
        listDI.add(newDI('c3', 'C3', 'c4', 'C4'));
        listDI[0].Donation_Amount__c = 100;
        listDI[1].Donation_Amount__c = 200;
        listDI[0].Payment_Check_Reference_Number__c = 'abc';
        listDI[1].Payment_Check_Reference_Number__c = '1234';
        listDI[0].Payment_Method__c = 'Check';
        listDI[1].Payment_Method__c = 'Visa';       
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results
        list<Contact> listCon = [select Id, Name, AccountId, npo02__TotalOppAmount__c from Contact order by Lastname];
        system.assertEquals(4, listCon.size());
        system.assertEquals('c1 C1', listCon[0].Name);
        system.assertEquals('c2 C2', listCon[1].Name);
        system.assertEquals('c3 C3', listCon[2].Name);
        system.assertEquals('c4 C4', listCon[3].Name);
        system.assertNotEquals(null, listCon[0].AccountId);
        system.assertNotEquals(null, listCon[1].AccountId);
        system.assertNotEquals(null, listCon[2].AccountId);
        system.assertNotEquals(null, listCon[3].AccountId);
        system.assertEquals(100, listCon[0].npo02__TotalOppAmount__c);
        system.assertEquals(0, listCon[1].npo02__TotalOppAmount__c);
        system.assertEquals(200, listCon[2].npo02__TotalOppAmount__c);
        system.assertEquals(0, listCon[3].npo02__TotalOppAmount__c);
        
        list<Account> listAcc = [select Id, Name, npo02__TotalOppAmount__c from Account order by Name];
        system.assertEquals(2, listAcc.size());
        system.assertEquals(100, listAcc[0].npo02__TotalOppAmount__c);
        system.assertEquals(200, listAcc[1].npo02__TotalOppAmount__c);
        
        list<Opportunity> listOpp = [select Id, Name, Amount, AccountId from Opportunity order by Name];
        system.assertEquals(2, listOpp.size());
        system.assertEquals(100, listOpp[0].Amount);
        system.assertEquals(200, listOpp[1].Amount);
        system.assertEquals(listAcc[0].Id, listOpp[0].AccountId);
        system.assertEquals(listAcc[1].Id, listOpp[1].AccountId);
                
        list<npe01__OppPayment__c> listPmt = [select Id, npe01__Payment_Amount__c, npe01__Payment_Method__c, npe01__Check_Reference_Number__c from npe01__OppPayment__c order by npe01__Opportunity__r.Name];
        system.assertEquals(2, listPmt.size());
        system.assertEquals(100, listPmt[0].npe01__Payment_Amount__c);
        system.assertEquals(200, listPmt[1].npe01__Payment_Amount__c);
        system.assertEquals('Check', listPmt[0].npe01__Payment_Method__c);
        system.assertEquals('Visa', listPmt[1].npe01__Payment_Method__c);
        system.assertEquals('abc', listPmt[0].npe01__Check_Reference_Number__c);
        system.assertEquals('1234', listPmt[1].npe01__Check_Reference_Number__c);

        list<OpportunityContactRole> listOCR = [select Id, ContactId, OpportunityId from OpportunityContactRole order by Contact.Name];
        system.assertEquals(4, listOCR.size());
        system.assertEquals(listCon[0].Id, listOCR[0].ContactId);
        system.assertEquals(listCon[1].Id, listOCR[1].ContactId);
        system.assertEquals(listCon[2].Id, listOCR[2].ContactId);
        system.assertEquals(listCon[3].Id, listOCR[3].ContactId);

        listDI = [select Id, DonationImported__c, DonationImportStatus__c from DataImport__c order by Contact1_Lastname__c];
        system.assertEquals(2, listDI.size());
        system.assertEquals(listDI[0].DonationImported__c, listOpp[0].Id); 
        system.assertEquals(listDI[1].DonationImported__c, listOpp[1].Id);
        system.assertEquals(label.bdiCreated, listDI[0].DonationImportStatus__c);
        system.assertEquals(label.bdiCreated, listDI[1].DonationImportStatus__c);        
    }

    /*********************************************************************************************************
    operation:
        import accounts with donation & payment information 
    verify:
        opportunity objects created
        payment objects created
        ocr's created
        account's rollups updated
    **********************************************************************************************************/            
    static testMethod void TwoDIWithAccountDonations() {
        if (strTestOnly != '*' && strTestOnly != 'TwoDIWithAccountDonations') return;
            
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(new DataImport__c(Account1_Name__c='A1'));
        listDI.add(new DataImport__c(Account1_Name__c='A2'));
        listDI[0].Donation_Donor__c = 'Account1';
        listDI[1].Donation_Donor__c = 'Account1';
        listDI[0].Donation_Amount__c = 100;
        listDI[1].Donation_Amount__c = 200;
        listDI[0].Payment_Check_Reference_Number__c = 'abc';
        listDI[1].Payment_Check_Reference_Number__c = '1234';
        listDI[0].Payment_Method__c = 'Check';
        listDI[1].Payment_Method__c = 'Visa';       
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results        
        list<Account> listAcc = [select Id, Name, npo02__TotalOppAmount__c from Account order by Name];
        system.assertEquals(2, listAcc.size());
        system.assertEquals(100, listAcc[0].npo02__TotalOppAmount__c);
        system.assertEquals(200, listAcc[1].npo02__TotalOppAmount__c);
        
        list<Opportunity> listOpp = [select Id, Name, Amount, AccountId from Opportunity order by Name];
        system.assertEquals(2, listOpp.size());
        system.assertEquals(100, listOpp[0].Amount);
        system.assertEquals(200, listOpp[1].Amount);
        system.assertEquals(listAcc[0].Id, listOpp[0].AccountId);
        system.assertEquals(listAcc[1].Id, listOpp[1].AccountId);
                
        list<npe01__OppPayment__c> listPmt = [select Id, npe01__Payment_Amount__c, npe01__Payment_Method__c, npe01__Check_Reference_Number__c from npe01__OppPayment__c order by npe01__Opportunity__r.Name];
        system.assertEquals(2, listPmt.size());
        system.assertEquals(100, listPmt[0].npe01__Payment_Amount__c);
        system.assertEquals(200, listPmt[1].npe01__Payment_Amount__c);
        system.assertEquals('Check', listPmt[0].npe01__Payment_Method__c);
        system.assertEquals('Visa', listPmt[1].npe01__Payment_Method__c);
        system.assertEquals('abc', listPmt[0].npe01__Check_Reference_Number__c);
        system.assertEquals('1234', listPmt[1].npe01__Check_Reference_Number__c);

        list<OpportunityContactRole> listOCR = [select Id, ContactId, OpportunityId from OpportunityContactRole order by Contact.Name];
        system.assertEquals(0, listOCR.size());

        listDI = [select Id, DonationImported__c, DonationImportStatus__c from DataImport__c order by Account1_Name__c];
        system.assertEquals(2, listDI.size());
        system.assertEquals(listDI[0].DonationImported__c, listOpp[0].Id); 
        system.assertEquals(listDI[1].DonationImported__c, listOpp[1].Id);
        system.assertEquals(label.bdiCreated, listDI[0].DonationImportStatus__c);
        system.assertEquals(label.bdiCreated, listDI[1].DonationImportStatus__c);        
    }

    /*********************************************************************************************************
    operation:
        import contacts with donation & campaign information 
    verify:
        opportunity objects created
        opportunities added to campaigns
    **********************************************************************************************************/            
    static testMethod void TwoDIWithDonationsAndCampaigns() {
        if (strTestOnly != '*' && strTestOnly != 'TwoDIWithDonationsAndCampaigns') return;
        
        list<Campaign> listC = new list<Campaign>();
        listC.add(new Campaign(Name='CmpExisting'));
        insert listC;
            
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(newDI('c1', 'C1'));
        listDI.add(newDI('c2', 'C2'));
        listDI[0].Donation_Amount__c = 100;
        listDI[1].Donation_Amount__c = 200;
        listDI[0].Donation_Campaign_Name__c = 'CmpExisting';
        listDI[1].Donation_Campaign_Name__c = 'CmpNew';
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results
        list<Contact> listCon = [select Id, Name, AccountId, npo02__TotalOppAmount__c from Contact order by Lastname];
        system.assertEquals(2, listCon.size());
        
        list<Account> listAcc = [select Id, Name, npo02__TotalOppAmount__c from Account order by Name];
        system.assertEquals(2, listAcc.size());
        system.assertEquals(100, listAcc[0].npo02__TotalOppAmount__c);
        system.assertEquals(200, listAcc[1].npo02__TotalOppAmount__c);
        
        list<Opportunity> listOpp = [select Id, Name, Amount, AccountId, CampaignId from Opportunity order by Name];
        system.assertEquals(2, listOpp.size());
        system.assertEquals(100, listOpp[0].Amount);
        system.assertEquals(200, listOpp[1].Amount);
        system.assertEquals(listAcc[0].Id, listOpp[0].AccountId);
        system.assertEquals(listAcc[1].Id, listOpp[1].AccountId);
                
        list<Campaign> listCmp = [select Id, Name from Campaign order by Name];
        system.assertEquals(2, listCmp.size());
        system.assertEquals(listOpp[0].CampaignId, listCmp[0].Id);
        system.assertEquals(listOpp[1].CampaignId, listCmp[1].Id);
        
        listDI = [select Id, DonationImported__c, DonationImportStatus__c from DataImport__c order by Contact1_Lastname__c];
        system.assertEquals(2, listDI.size());
        system.assertEquals(listDI[0].DonationImported__c, listOpp[0].Id); 
        system.assertEquals(listDI[1].DonationImported__c, listOpp[1].Id);
        system.assertEquals(label.bdiCreated, listDI[0].DonationImportStatus__c);
        system.assertEquals(label.bdiCreated, listDI[1].DonationImportStatus__c);        
    }

    /*********************************************************************************************************
    operation:
        import contacts with campaign information (no donation) 
    verify:
        opportunity objects created
        opportunities added to campaigns
    **********************************************************************************************************/            
    static testMethod void TwoDIWithCampaignMembers() {
        if (strTestOnly != '*' && strTestOnly != 'TwoDIWithCampaignMembers') return;
        
        list<Campaign> listC = new list<Campaign>();
        listC.add(new Campaign(Name='CmpExisting'));
        insert listC;
            
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(newDI('c1', 'C1'));
        listDI.add(newDI('c2', 'C2'));
        listDI[1].Campaign_Member_Status__c = 'zMyStatus';
        listDI[0].Donation_Campaign_Name__c = 'CmpExisting';
        listDI[1].Donation_Campaign_Name__c = 'CmpNew';
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results
        list<Contact> listCon = [select Id, Name, AccountId, npo02__TotalOppAmount__c from Contact order by Lastname];
        system.assertEquals(2, listCon.size());
        
        list<Account> listAcc = [select Id, Name, npo02__TotalOppAmount__c from Account order by Name];
        system.assertEquals(2, listAcc.size());
                        
        list<Campaign> listCmp = [select Id, Name from Campaign order by Name];
        system.assertEquals(2, listCmp.size());
        
        list<CampaignMember> listCM = [select Id, ContactId, Status from CampaignMember Order by  Status];
        system.assertEquals(2, listCM.size());
        system.assertEquals(listCon[0].Id, listCM[0].ContactId);
        system.assertNotEquals('zMyStatus', listCM[0].Status);
        system.assertEquals(listCon[1].Id, listCM[1].ContactId);
        system.assertEquals('zMyStatus', listCM[1].Status);
        
    }

    /*********************************************************************************************************
    operation:
        import accounts with invalid donation information 
    verify:
        opportunity objects created only for valid di
    **********************************************************************************************************/            
    static testMethod void TwoDIWithDonationErrors() {
        if (strTestOnly != '*' && strTestOnly != 'TwoDIWithDonationErrors') return;
            
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(new DataImport__c(Account1_Name__c='A1'));
        listDI.add(new DataImport__c(Account1_Name__c='A2'));
        listDI[0].Donation_Donor__c = 'Account1';
        listDI[1].Donation_Donor__c = 'illegal value here!';
        listDI[0].Donation_Amount__c = 100;
        listDI[1].Donation_Amount__c = 200;
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results        
        list<Account> listAcc = [select Id, Name, npo02__TotalOppAmount__c from Account order by Name];
        system.assertEquals(2, listAcc.size());
        system.assertEquals(100, listAcc[0].npo02__TotalOppAmount__c);
        system.assertEquals(0, listAcc[1].npo02__TotalOppAmount__c);
        
        list<Opportunity> listOpp = [select Id, Name, Amount, AccountId from Opportunity order by Name];
        system.assertEquals(1, listOpp.size());
        system.assertEquals(100, listOpp[0].Amount);
        system.assertEquals(listAcc[0].Id, listOpp[0].AccountId);
                
        listDI = [select Id, Status__c, DonationImported__c, DonationImportStatus__c from DataImport__c order by Account1_Name__c];
        system.assertEquals(2, listDI.size());
        system.assertEquals(listDI[0].DonationImported__c, listOpp[0].Id); 
        system.assertEquals(listDI[1].DonationImported__c, null);
        system.assertEquals(label.bdiCreated, listDI[0].DonationImportStatus__c);
        system.assertEquals(label.bdiErrorInvalidDonor, listDI[1].DonationImportStatus__c);
        system.assertEquals(label.bdiImported, listDI[0].Status__c);        
        system.assertEquals(label.bdiFailed, listDI[1].Status__c);        
    }

    /*********************************************************************************************************
    operation:
        import different contacts within multiple di records in the same batch setting Household fields
    verify:
        two contacts created
    **********************************************************************************************************/            
    static testMethod void TwoDITwoNewContactsHouseholdFields() {
        if (strTestOnly != '*' && strTestOnly != 'TwoDITwoNewContactsHouseholdFields') return;
            
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(new DataImport__c(Contact1_Lastname__c = 'c1', Household_Phone__c = '425-111-2222'));
        listDI.add(new DataImport__c(Contact1_Lastname__c = 'c2', Household_Phone__c = '425-111-2222'));
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results
        list<Contact> listCon = [select Id, Name, AccountId, Account.Phone from Contact order by Lastname];
        system.assertEquals(2, listCon.size());
        system.assertNotEquals(listCon[1].Name, listCon[0].Name);
        system.assertEquals(listDI[0].Household_Phone__c, listCon[0].Account.Phone);
        system.assertEquals(listDI[1].Household_Phone__c, listCon[1].Account.Phone);
        
        listDI = [select Contact1Imported__c, Contact1ImportStatus__c, HouseholdAccountImported__c, 
            Contact2Imported__c, Contact2ImportStatus__c from DataImport__c order by Contact1Imported__c];
        system.assertEquals(2, listDI.size());
        system.assertEquals(listDI[0].Contact1Imported__c, listCon[0].Id);
        system.assertEquals(listDI[1].Contact1Imported__c, listCon[1].Id);
        system.assertNotEquals(null, listDI[0].HouseholdAccountImported__c);
        system.assertNotEquals(null, listDI[1].HouseholdAccountImported__c);
        system.assertEquals(label.bdiCreated, listDI[0].Contact1ImportStatus__c);
        system.assertEquals(label.bdiCreated, listDI[1].Contact1ImportStatus__c);        
    }

    /*********************************************************************************************************
    operation:
        import new contacts into existing household accounts 
    verify:
        provided household account used
    **********************************************************************************************************/            
    static testMethod void TwoDINewContactsExistingsHousehold() {
        if (strTestOnly != '*' && strTestOnly != 'TwoDINewContactsExistingsHousehold') return;
            
        // existing contacts
        list<Contact> listConExisting = new list<Contact>();
        listConExisting.add(new Contact(Firstname='c1', Lastname='C1', Email='c1@C1.com'));
        listConExisting.add(new Contact(Firstname='c2', Lastname='C2', Email='c2@C2.com'));
        insert listConExisting;
        
        // get their households
        listConExisting = [select Id, Firstname, Lastname, Email, AccountId from Contact order by Firstname];
        system.assertEquals(2, listConExisting.size());
        
        list<DataImport__c> listDI = new list<DataImport__c>();
        listDI.add(new DataImport__c(Contact1_Firstname__c='a1', Contact1_Lastname__c='C1', 
            HouseholdAccountImported__c=listConExisting[0].AccountId));
        listDI.add(new DataImport__c(Contact1_Firstname__c='a2', Contact1_Lastname__c='C2', Contact2_Firstname__c='b2',
            HouseholdAccountImported__c=listConExisting[1].AccountId));
        insert listDI;
          
        //run batch data import
        Test.StartTest();
        BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
        ID ApexJobId = Database.executeBatch(bdi, 10);
        Test.stopTest();
    
        // verify expected results
        list<Contact> listCon = [select Id, Firstname, Lastname, Name, AccountId from Contact order by Lastname, Firstname];
        system.assertEquals(5, listCon.size());
        system.assertEquals(listConExisting[0].AccountId, listCon[0].AccountId);
        system.assertEquals(listConExisting[0].AccountId, listCon[1].AccountId);
        system.assertEquals(listConExisting[1].AccountId, listCon[2].AccountId);
        system.assertEquals(listConExisting[1].AccountId, listCon[3].AccountId);
        system.assertEquals(listConExisting[1].AccountId, listCon[4].AccountId);
        
        list<Account> listAcc = [select Id, Name from Account];
        system.assertEquals(2, listAcc.size());
    }
}