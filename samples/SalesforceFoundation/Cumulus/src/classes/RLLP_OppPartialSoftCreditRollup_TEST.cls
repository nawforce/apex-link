/*
    Copyright (c) 2015 Salesforce.org
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
* @date 2015
* @group Opportunity
* @description test Soft Credit Rollups, both full and partial
*/

@isTest
private class RLLP_OppPartialSoftCreditRollup_TEST {

    /*********************************************************************************************************
    * @description If you only want to run a single test in this class, fill in its name here.
    * If you want to run all tests, then use '*'
    */
    private static string strTestOnly = '*';

    /*******************************************************************************************************
    * @description test data for Soft Credit and Partial Soft Credit tests
    */ 
    static integer cContactTest = 10;
    static integer cOppTest = 10;
    static integer amountTest = 100;
    static list<Contact> listCon;
    
    /*******************************************************************************************************
    * @description create test data for Soft Credit and Partial Soft Credit tests. This includes Household
    * Contacts, Opportunities, OCRs, and Partial Soft Credits.
    * @param cContact The number of Contacts to create
    * @param cOpp The number of Opportunities per Contact to create
    * @param amount The amount to make each Opportunity.  Partial soft credits will be made for amount/2.
    * @return void
    */ 
    private static void createPSCData(integer cContact, integer cOpp, integer amount) {
        
        // create some Contacts in Household Accounts
        listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(cContact);
        insert listCon;
        listCon = [select Id, Firstname,Lastname, AccountId from Contact];
        system.assertEquals(cContact, listCon.size());
        
        // create cOpp Opportunities for each Contact
        list<Opportunity> listOpp = new list<Opportunity>();
        for (integer i = 0; i < cOpp; i++) {
            listOpp.addAll(UTIL_UnitTestData_TEST.OppsForContactWithAccountList (listCon, null,             
                UTIL_UnitTestData_TEST.getClosedWonStage(), system.Today(), amount, null, null));
        } 
        insert listOpp;
        system.assertEquals(cContact * cOpp, listOpp.size());
        
        // primary OCR's automatically created
        list<OpportunityContactRole> listOCR = [select Id, ContactId, OpportunityId, Role, IsPrimary from OpportunityContactRole];
        system.assertEquals(listOpp.size(), listOCR.size());

        // change them to Soft Credit OCR's
        for (OpportunityContactRole ocr : listOCR) {
            ocr.Role = 'Soft Credit';        
            ocr.IsPrimary = false;
        }
        update listOCR;
        
        // create our Partial Soft Credits
        list<Partial_Soft_Credit__c> listPSC = new list<Partial_Soft_Credit__c>();
        for (OpportunityContactRole ocr : listOCR) {
            listPSC.add(new Partial_Soft_Credit__c(
                Contact__c = ocr.ContactId,
                Opportunity__c = ocr.OpportunityId,
                Role_Name__c = ocr.Role,
                Contact_Role_ID__c = ocr.Id,
                Amount__c = amount/2                
                ));
        }
        insert listPSC;
    }

    /*******************************************************************************************************
    * @description tests the original NPSP Soft Credit rollups for OCR's (not Partial Soft Credits)
    * @return void
    static testmethod void testOLDSoftCreditRollups() {
        if (strTestOnly != '*' && strTestOnly != 'testOLDSoftCreditRollups') return;
        createPSCData(cContactTest, cOppTest, amountTest);
        
        RLLP_OppRollup rllp = new RLLP_OppRollup();
        RLLP_OppRollup_UTIL util = new RLLP_OppRollup_UTIL();
        map<id, Contact> cmap = new map<id, Contact>((list<Contact>)Database.query(util.buildContactQuery() +
                                                      ' where id IN :listCon'));
        Test.startTest();
        UTIL_Debug.debugWithInfo('DJH: testCurrentSoftCreditRollups pre '); 
        rllp.rollupSoftCreditsOLDNoPartialSupport(cmap);
        UTIL_Debug.debugWithInfo('DJH: testCurrentSoftCreditRollups post '); 
        Test.stopTest();
        UTIL_Debug.debugWithInfo('DJH: testCurrentSoftCreditRollups after stopTest'); 
        
        // verify results
        listCon = [select Id, Lastname, npo02__Soft_Credit_This_Year__c, npo02__Soft_Credit_Total__c,
            Number_of_Soft_Credits_This_Year__c, Number_of_Soft_Credits__c from Contact];
        system.assertEquals(cContactTest, listCon.size());
        for (Contact con : listCon) {
            system.assertEquals(cOppTest * amountTest, con.npo02__Soft_Credit_This_Year__c);
            system.assertEquals(cOppTest * amountTest, con.npo02__Soft_Credit_Total__c);
            system.assertEquals(null, con.Number_of_Soft_Credits_This_Year__c);
            system.assertEquals(null, con.Number_of_Soft_Credits__c);
        }       
    }
    */ 


    /*******************************************************************************************************
    * @description test that soft credit rollups do not double count an opp when two OCRs exist.
    * @return void
    */ 
    static testmethod void testDuplicateOCRSoftCredits() {
      if (strTestOnly != '*' && strTestOnly != 'testDuplicateOCRSoftCredits') return;
      
      Contact con = new Contact(lastname='Testy');
      insert con;
      con = [select Id, lastname, AccountId from Contact];
      
      list<Opportunity> listOpp = new list<Opportunity>();
      String stage = UTIL_UnitTestData_TEST.getClosedWonStage();
      Date dt = system.Today();
      listOpp.add(new Opportunity(name='03', CloseDate=dt, Amount=100, Primary_Contact__c=con.Id, StageName=stage));
      insert listOpp;

      // primary OCR's automatically created
      list<OpportunityContactRole> listOCR = [select Id, ContactId, OpportunityId, Role, IsPrimary from OpportunityContactRole];
      system.assertEquals(listOpp.size(), listOCR.size());

      List<OpportunityContactRole> dupeOCR = new List<OpportunityContactRole>();
      // change them to Soft Credit OCR's
      for (OpportunityContactRole ocr : listOCR) {
          ocr.Role = 'Soft Credit';        
          ocr.IsPrimary = false;
          dupeOCR.add(new OpportunityContactRole(ContactId = ocr.ContactId, 
                                                 OpportunityId = ocr.OpportunityId, 
                                                 Role=ocr.Role, 
                                                 IsPrimary = ocr.IsPrimary));
      }
      update listOCR;
      insert dupeOCR;
      
      // run our Partial Soft Credit Rollups
      RLLP_OppRollup rllp = new RLLP_OppRollup();
      RLLP_OppRollup_UTIL util = new RLLP_OppRollup_UTIL();        
      listCon = (list<Contact>)Database.query(util.buildContactQuery());
      system.assertEquals(1, listCon.size());

      Test.startTest();
      rllp.rollupSoftCreditsWithPartialSupport(listCon);
      Test.stopTest();
      
      Contact resultContact = [SELECT Id,npo02__Soft_Credit_Total__c FROM Contact WHERE Id =: con.Id LIMIT 1];
      System.assertEquals(100, resultContact.npo02__Soft_Credit_Total__c);
    }

    /*******************************************************************************************************
    * @description tests the new NPSP Soft Credit rollups for OCR's and Partial Soft Credits.
    * @return void
    */ 
    static testmethod void testPartialSoftCreditRollups() {
        if (strTestOnly != '*' && strTestOnly != 'testPartialSoftCreditRollups') return;
        createPSCData(cContactTest, cOppTest, amountTest);

        RLLP_OppRollup rllp = new RLLP_OppRollup();
        RLLP_OppRollup_UTIL util = new RLLP_OppRollup_UTIL();        
        listCon = (list<Contact>)Database.query(util.buildContactQuery() + ' where id IN :listCon');

        Test.startTest();
        UTIL_Debug.debugWithInfo('DJH: testPartialSoftCreditRollups pre ');
        rllp.rollupSoftCreditsWithPartialSupport(listCon);
        UTIL_Debug.debugWithInfo('DJH: testPartialSoftCreditRollups post ');
        Test.stopTest();
        UTIL_Debug.debugWithInfo('DJH: testPartialSoftCreditRollups after stopTest');
        
        // verify results
        listCon = [select Id, Lastname, npo02__Soft_Credit_This_Year__c, npo02__Soft_Credit_Total__c,
            Number_of_Soft_Credits_This_Year__c, Number_of_Soft_Credits__c from Contact];
        system.assertEquals(cContactTest, listCon.size());
        for (Contact con : listCon) {
            system.assertEquals(cOppTest * amountTest/2, con.npo02__Soft_Credit_This_Year__c);
            system.assertEquals(cOppTest * amountTest/2, con.npo02__Soft_Credit_Total__c);
            system.assertEquals(cOppTest, con.Number_of_Soft_Credits_This_Year__c);
            system.assertEquals(cOppTest, con.Number_of_Soft_Credits__c);
        }
        
    }

    /*******************************************************************************************************
    * @description tests all of the summary fields supported for  Soft Credit rollups.
    * @return void
    */ 
    static testmethod void testPartialSoftCreditRollupsAllStats() {
        if (strTestOnly != '*' && strTestOnly != 'testPartialSoftCreditRollupsAllStats') return;
        
        // to deal with leap years, need to make N a little larger!
        npo02__Households_Settings__c hs = UTIL_CustomSettingsFacade.getHouseholdsSettings(); 
        hs.npo02__Rollup_N_Day_Value__c = 366;
        UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(hs);
        
        Contact con = new Contact(lastname='Testy');
        insert con;
        con = [select Id, lastname, AccountId from Contact];
        
        list<Opportunity> listOpp = new list<Opportunity>();
        String stage = UTIL_UnitTestData_TEST.getClosedWonStage();
        Date dt = system.Today();
        listOpp.add(new Opportunity(name='01', CloseDate=dt.addYears(-5), Amount=100, Primary_Contact__c=con.Id, StageName=stage));
        listOpp.add(new Opportunity(name='02', CloseDate=dt.addYears(-4), Amount=100, Primary_Contact__c=con.Id, StageName=stage));
        listOpp.add(new Opportunity(name='03', CloseDate=dt.addYears(-3), Amount=100, Primary_Contact__c=con.Id, StageName=stage));
        listOpp.add(new Opportunity(name='04', CloseDate=dt.addYears(-2), Amount=100, Primary_Contact__c=con.Id, StageName=stage));
        listOpp.add(new Opportunity(name='05', CloseDate=dt.addYears(-2), Amount=100, Primary_Contact__c=con.Id, StageName=stage));
        listOpp.add(new Opportunity(name='06', CloseDate=dt.addYears(-1), Amount=100, Primary_Contact__c=con.Id, StageName=stage));
        listOpp.add(new Opportunity(name='07', CloseDate=dt.addYears(-1), Amount=100, Primary_Contact__c=con.Id, StageName=stage));
        listOpp.add(new Opportunity(name='08', CloseDate=dt.addYears(+0), Amount=100, Primary_Contact__c=con.Id, StageName=stage));
        // set a future opp still in this year.
        Date dtTomorrow = dt;
        if (dt.dayOfYear() < 364)
            dtTomorrow = dt.addDays(1);
        listOpp.add(new Opportunity(name='09', CloseDate=dtTomorrow, Amount=100, Primary_Contact__c=con.Id, StageName=stage));
        listOpp.add(new Opportunity(name='10', CloseDate=dt.addYears(+1), Amount=100, Primary_Contact__c=con.Id, StageName=stage));
        listOpp.add(new Opportunity(name='11', CloseDate=dt.addYears(+1), Amount=100, Primary_Contact__c=con.Id, StageName=stage));
        insert listOpp;
        
        // primary OCR's automatically created
        list<OpportunityContactRole> listOCR = [select Id, ContactId, OpportunityId, Role, IsPrimary from OpportunityContactRole];
        system.assertEquals(listOpp.size(), listOCR.size());

        // change them to Soft Credit OCR's
        for (OpportunityContactRole ocr : listOCR) {
            ocr.Role = 'Soft Credit';        
            ocr.IsPrimary = false;
        }
        update listOCR;
        
        // create our Partial Soft Credits
        list<Partial_Soft_Credit__c> listPSC = new list<Partial_Soft_Credit__c>();
        for (OpportunityContactRole ocr : listOCR) {
            listPSC.add(new Partial_Soft_Credit__c(
                Contact__c = ocr.ContactId,
                Opportunity__c = ocr.OpportunityId,
                Role_Name__c = ocr.Role,
                Contact_Role_ID__c = ocr.Id,
                Amount__c = 10                
                ));
        }
        insert listPSC;
        
        // run our Partial Soft Credit Rollups
        RLLP_OppRollup rllp = new RLLP_OppRollup();
        RLLP_OppRollup_UTIL util = new RLLP_OppRollup_UTIL();        
        listCon = (list<Contact>)Database.query(util.buildContactQuery());
        system.assertEquals(1, listCon.size());

        Test.startTest();
        rllp.rollupSoftCreditsWithPartialSupport(listCon);
        Test.stopTest();
        
        // verify results
        con = [select Id, 
               First_Soft_Credit_Amount__c,
               First_Soft_Credit_Date__c,
               Largest_Soft_Credit_Amount__c,
               Largest_Soft_Credit_Date__c,
               Last_Soft_Credit_Amount__c,
               Last_Soft_Credit_Date__c,                                                                              
               npo02__Soft_Credit_Total__c,
               Number_of_Soft_Credits__c,
               npo02__Soft_Credit_This_Year__c,
               Number_of_Soft_Credits_This_Year__c,
               npo02__Soft_Credit_Last_Year__c,
               Number_of_Soft_Credits_Last_Year__c,
               npo02__Soft_Credit_Two_Years_Ago__c,
               Number_of_Soft_Credits_Two_Years_Ago__c,
               Soft_Credit_Last_N_Days__c,
               Number_of_Soft_Credits_Last_N_Days__c
                from Contact];
                
        system.assertEquals(10, con.First_Soft_Credit_Amount__c);
        system.assertEquals(dt.addYears(-5), con.First_Soft_Credit_Date__c);
        system.assertEquals(10, con.Largest_Soft_Credit_Amount__c);
        system.assertEquals(dt.addYears(-5), con.Largest_Soft_Credit_Date__c);
        system.assertEquals(10, con.Last_Soft_Credit_Amount__c);
        system.assertEquals(dt.addYears(+1), con.Last_Soft_Credit_Date__c);
        system.assertEquals(110, con.npo02__Soft_Credit_Total__c);
        system.assertEquals(11, con.Number_of_Soft_Credits__c);
        system.assertEquals(20, con.npo02__Soft_Credit_This_Year__c);
        system.assertEquals(2, con.Number_of_Soft_Credits_This_Year__c);
        system.assertEquals(20, con.npo02__Soft_Credit_Last_Year__c);
        system.assertEquals(2, con.Number_of_Soft_Credits_Last_Year__c);
        system.assertEquals(20, con.npo02__Soft_Credit_Two_Years_Ago__c);
        system.assertEquals(2, con.Number_of_Soft_Credits_Two_Years_Ago__c);
        system.assertEquals(60, con.Soft_Credit_Last_N_Days__c);
        system.assertEquals(6, con.Number_of_Soft_Credits_Last_N_Days__c);

    }

    /*******************************************************************************************************
    * @description Tests the NPSP Soft Credit rollups in multicurrency enabled orgs.
    * @return void
    */ 
    static testmethod void testMulticurrencyPartialSoftCreditRollups() {
        if (strTestOnly != '*' && strTestOnly != 'testMulticurrencyPartialSoftCreditRollups') return;
        //only run test in multicurrency orgs
        if (!RLLP_OppRollup_UTIL.isMultiCurrency())
            return;

        sObject nonDefaultCurrencyType;
        sObject defaultCurrencyType;
        
        for (sObject ct : database.query('select IsoCode, ConversionRate, IsCorporate from CurrencyType')) {
            if ((boolean)ct.get('IsCorporate')) 
                defaultCurrencyType = ct;
            else
                nonDefaultCurrencyType = ct;
        }

        //if we don't have a non default currency, we can't run our test
        if (nonDefaultCurrencyType==null)
            return;

        // create a Contact
        Contact con = UTIL_UnitTestData_TEST.getContact();
        insert con;
        
        // create cOpp Opportunities for this Contact
        list<Opportunity> listOpp = new list<Opportunity>();
        for (integer i = 0; i < 4; i++) {
            //make half our opps of non-default currency
            string isoCode = math.mod(i,2)==0 ? (string)defaultCurrencyType.get('IsoCode') : (string)nonDefaultCurrencyType.get('IsoCode');

            Opportunity opp = new Opportunity(Name = 'foo', Amount = 10, Primary_Contact__c = con.id, CloseDate = system.Today(), StageName = UTIL_UnitTestData_TEST.getClosedWonStage());
            opp.put('CurrencyIsoCode', isoCode);
            listOpp.add(opp);
        } 
        insert listOpp;
        
        // primary OCR's automatically created
        list<OpportunityContactRole> listOCR = [select Id, ContactId, OpportunityId, Role, IsPrimary from OpportunityContactRole];
        system.assertEquals(listOpp.size(), listOCR.size());

        // change them to Soft Credit OCR's
        for (OpportunityContactRole ocr : listOCR) {
            ocr.Role = 'Soft Credit';
            ocr.IsPrimary = false;
        }
        update listOCR;
        
        // create our Partial Soft Credits
        list<Partial_Soft_Credit__c> listPSC = new list<Partial_Soft_Credit__c>();
        for (OpportunityContactRole ocr : listOCR) {
            listPSC.add(new Partial_Soft_Credit__c(
                Contact__c = ocr.ContactId,
                Opportunity__c = ocr.OpportunityId,
                Role_Name__c = ocr.Role,
                Contact_Role_ID__c = ocr.Id,
                Amount__c = 5
            ));
        }
        insert listPSC;

        //verify currency iso code was correctly transfered to PSCs
        list<Partial_Soft_Credit__c> queryPSC = database.query('SELECT Id, CurrencyIsoCode, Opportunity__r.CurrencyIsoCode FROM Partial_Soft_Credit__c WHERE Id IN: listPSC');
        for (Partial_Soft_Credit__c psc : queryPSC)
            system.assertEquals(psc.get('CurrencyIsoCode'), psc.getSObject('Opportunity__r').get('CurrencyIsoCode'), 'Currency ISO codes should match the opportunity iso code.');

        RLLP_OppRollup rllp = new RLLP_OppRollup();
        RLLP_OppRollup_UTIL util = new RLLP_OppRollup_UTIL();        
        id conid = con.id;
        listCon = (list<Contact>)Database.query(util.buildContactQuery() + ' where id = :conid');

        Test.startTest();
        UTIL_Debug.debugWithInfo('DJH: testPartialSoftCreditRollups pre ');
        rllp.rollupSoftCreditsWithPartialSupport(listCon);
        UTIL_Debug.debugWithInfo('DJH: testPartialSoftCreditRollups post ');
        Test.stopTest();
        UTIL_Debug.debugWithInfo('DJH: testPartialSoftCreditRollups after stopTest');
        
        // verify results
        listCon = [select Id, Lastname, npo02__Soft_Credit_This_Year__c, npo02__Soft_Credit_Total__c,
            Number_of_Soft_Credits_This_Year__c, Number_of_Soft_Credits__c from Contact];
        system.assertEquals(1, listCon.size());
        
        decimal convertedAmount = 2 * RLLP_OppRollup_UTIL.convertCurrency((string)nonDefaultCurrencyType.get('IsoCode'), (string)defaultCurrencyType.get('IsoCode'), 5);

        system.assertEquals(10+convertedAmount, listCon[0].npo02__Soft_Credit_This_Year__c);
        system.assertEquals(10+convertedAmount, listCon[0].npo02__Soft_Credit_Total__c);
        system.assertEquals(4, listCon[0].Number_of_Soft_Credits_This_Year__c);
        system.assertEquals(4, listCon[0].Number_of_Soft_Credits__c);

        //update opportunity with default currency to non-default currency, verify PSC currency is updated
        listOpp[0].put('CurrencyIsoCode', nonDefaultCurrencyType.get('IsoCode'));
        update listOpp[0];

        id oppid = listOpp[0].id;
        queryPSC = database.query('SELECT Id, CurrencyIsoCode FROM Partial_Soft_Credit__c WHERE Opportunity__c = :oppid');
        system.assertEquals(nonDefaultCurrencyType.get('IsoCode'), queryPSC[0].get('CurrencyIsoCode'), 'Currency ISO code should match the opportunity iso code.');

        //try to change PSC currency after resetting flag, verify that the operation is blocked
        queryPSC[0].put('CurrencyIsoCode', defaultCurrencyType.get('IsoCode'));
        PSC_PartialSoftCredit_TDTM.blockCurrencyChange = true;
        
        Exception ex = null;
        try {
            update queryPSC[0];
        } catch (Exception e) {
            ex = e;
        }
        system.assertNotEquals(null,ex,'An exception should be caught.');
    }
}