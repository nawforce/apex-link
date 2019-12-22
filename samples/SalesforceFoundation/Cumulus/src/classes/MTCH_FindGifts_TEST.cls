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
 * @date 12/8/2015
 * @group Opportunity
 * @description test class for the FindGifts VF page allowing users to find matched gifts for a particular Opportunity using custom filters.
 */
 
@isTest 
public with sharing class MTCH_FindGifts_TEST {
    
    /*******************************************************************************************************
    * @description test data for all tests
    */ 
    public static Account accMG;
    public static integer cItem = 10;
    public static list<Contact> listCon;
    public static list<Opportunity> listOpp;
    public static Opportunity oppMG;
        
    /*******************************************************************************************************
    * @description initialize test data for all tests
    * @param isMGClosedWon whether the Matching Gift should be created as Closed Won or Open.
    */ 
    public static void initTestData(boolean isMGClosedWon, double matchingGiftPercent) {
        
        // create a matching gift company
        accMG = new Account(name='matching gift company', Matching_Gift_Percent__c = matchingGiftPercent);
        insert accMG;
        
        // create some Contacts in Household Accounts
        listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(cItem);
        insert listCon;
        listCon = [select Id, Firstname,Lastname, AccountId from Contact];
        system.assertEquals(cItem, listCon.size());
        
        // create an Opportunity for each Contact
        listOpp = UTIL_UnitTestData_TEST.OppsForContactWithAccountList (listCon, null,             
            UTIL_UnitTestData_TEST.getClosedWonStage(), system.Today(), 100, null, null); 
        for (integer i = 0; i < cItem; i++) {
            // only make half of them matching gift potentials
            if (i >= cItem/2)
                break;
            Opportunity opp = listOpp[i];
            opp.Matching_Gift_Account__c = accMG.Id;
            opp.Matching_Gift_Status__c = 'Potential';
        }
        insert listOpp;
        
        // create the Matching Gift Opp
        String stage = UTIL_UnitTestData_TEST.getOpenStage();
        if (isMGClosedWon)
            stage = UTIL_UnitTestData_TEST.getClosedWonStage();
        oppMG = new Opportunity(Name='Matching Gift Opp', AccountId=accMG.Id, Amount=1000, StageName=stage, CloseDate=system.Today());  
        insert oppMG;   
    }

    /*******************************************************************************************************
    * @description create several matched gifts to the Matching Gift opp
    */ 
    static void createMatchesWithTestData() {
        list<OpportunityContactRole> listOCR = new list<OpportunityContactRole>();
        list<Partial_Soft_Credit__c> listPSC = new list<Partial_Soft_Credit__c>();
        
        listOpp[0].Matching_Gift__c = oppMG.Id;
        listOpp[0].Matching_Gift_Status__c = 'Received';
        listOpp[2].Matching_Gift__c = oppMG.Id;
        listOpp[2].Matching_Gift_Status__c = 'Received';
        update listOpp;
        
        string role = UTIL_CustomSettingsFacade.getHouseholdsSettings().Matched_Donor_Role__c;
        /* now that PSC trigger handlers creates OCRs if needed, no need to create them here.
        listOCR.add(new OpportunityContactRole(OpportunityId=oppMG.Id, ContactId=listCon[0].Id, Role=role));
        listOCR.add(new OpportunityContactRole(OpportunityId=oppMG.Id, ContactId=listCon[2].Id, Role=role));
        insert listOCR;
        */

        listPSC.add(new Partial_Soft_Credit__c(Opportunity__c=oppMG.Id, Contact__c=listCon[0].Id, /* Contact_Role_ID__c=listOCR[0].Id, */ Role_Name__c=role, Amount__c=100));
        listPSC.add(new Partial_Soft_Credit__c(Opportunity__c=oppMG.Id, Contact__c=listCon[2].Id, /* Contact_Role_ID__c=listOCR[1].Id, */ Role_Name__c=role, Amount__c=100));
        insert listPSC;
    }
    
    /*******************************************************************************************************
    * @description match some existing Opps to the Matching Gift
    * verify OCR and PSC created
    */ 
    static testmethod void testCreateMatches() {
        initTestData(true, 100);
        Test.setCurrentPage(Page.MTCH_FindGifts);
        MTCH_FindGifts_CTRL ctrl = new MTCH_FindGifts_CTRL(new ApexPages.StandardController(oppMG));
        system.assertEquals(cItem/2, ctrl.potentialGifts.size());
        system.assertEquals(cItem/2, ctrl.selection.size());
        system.assertEquals(false, ctrl.selection.get(listOpp[0].Id));
        system.assertEquals(false, ctrl.selection.get(listOpp[2].Id));
        system.assertEquals(0, ctrl.oppTotalSelected.Amount);
        ctrl.selection.put(listOpp[0].Id, true);    
        ctrl.selection.put(listOpp[2].Id, true);
        system.assertEquals(200, ctrl.oppTotalSelected.Amount);
        system.assertEquals(0, [select count() from Partial_Soft_Credit__c]);
        test.startTest();
        system.assertNotEquals(null, ctrl.saveAndClose());
        test.stopTest();
        system.assertEquals(2, [select count() from Partial_Soft_Credit__c]);
        system.assertEquals(2, [select count() from OpportunityContactRole where OpportunityId = :oppMG.Id]);
        system.assertEquals(2, [select count() from Opportunity where Matching_Gift_Status__c = 'Received']);
        system.assertEquals(2, [select count() from Opportunity where Matching_Gift__c = :oppMG.Id]);
    }

    /*******************************************************************************************************
    * @description match some existing Opps to the Matching Gift with a Matching Gift Percent < 100
    * verify OCR and PSC created
    */ 
    static testmethod void testCreateMatchesPercent() {
        initTestData(true, 50);
        Test.setCurrentPage(Page.MTCH_FindGifts);
        MTCH_FindGifts_CTRL ctrl = new MTCH_FindGifts_CTRL(new ApexPages.StandardController(oppMG));
        system.assertEquals(cItem/2, ctrl.potentialGifts.size());
        system.assertEquals(cItem/2, ctrl.selection.size());
        system.assertEquals(false, ctrl.selection.get(listOpp[0].Id));
        system.assertEquals(false, ctrl.selection.get(listOpp[2].Id));
        system.assertEquals(0, ctrl.oppTotalSelected.Amount);
        ctrl.selection.put(listOpp[0].Id, true);    
        ctrl.selection.put(listOpp[2].Id, true);
        system.assertEquals(100, ctrl.oppTotalSelected.Amount);
        system.assertEquals(0, [select count() from Partial_Soft_Credit__c]);
        test.startTest();
        system.assertNotEquals(null, ctrl.saveAndClose());
        test.stopTest();
        system.assertEquals(2, [select count() from Partial_Soft_Credit__c]);
        system.assertEquals(2, [select count() from OpportunityContactRole where OpportunityId = :oppMG.Id]);
        system.assertEquals(2, [select count() from Opportunity where Matching_Gift_Status__c = 'Received']);
        system.assertEquals(2, [select count() from Opportunity where Matching_Gift__c = :oppMG.Id]);
    }

    /*******************************************************************************************************
    * @description unmatch some existing Opps to the Matching Gift
    * verify OCR and PSC deleted
    */ 
    static testmethod void testUnmatchMatches() {
        initTestData(true, 100);
        createMatchesWithTestData();
        Test.setCurrentPage(Page.MTCH_FindGifts);
        MTCH_FindGifts_CTRL ctrl = new MTCH_FindGifts_CTRL(new ApexPages.StandardController(oppMG));
        system.assertEquals(cItem/2, ctrl.potentialGifts.size());
        system.assertEquals(cItem/2, ctrl.selection.size());
        system.assertEquals(true, ctrl.selection.get(listOpp[0].Id));
        system.assertEquals(true, ctrl.selection.get(listOpp[2].Id));
        system.assertEquals(200, ctrl.oppTotalSelected.Amount);
        ctrl.selection.put(listOpp[0].Id, false);    
        ctrl.selection.put(listOpp[2].Id, false);
        system.assertEquals(0, ctrl.oppTotalSelected.Amount);
        system.assertEquals(2, [select count() from Partial_Soft_Credit__c]);
        test.startTest();
        system.assertNotEquals(null, ctrl.saveAndClose());
        test.stopTest();
        system.assertEquals(0, [select count() from Partial_Soft_Credit__c]);
        system.assertEquals(0, [select count() from OpportunityContactRole where OpportunityId = :oppMG.Id]);
        system.assertEquals(0, [select count() from Opportunity where Matching_Gift_Status__c = 'Received']);
        system.assertEquals(0, [select count() from Opportunity where Matching_Gift__c = :oppMG.Id]);
    }

    /*******************************************************************************************************
    * @description match some Searched for existing Opps to the Matching Gift
    * verify OCR and PSC created
    */ 
    static testmethod void testCreateSearchedMatches() {
        initTestData(true, 100);
        Test.setCurrentPage(Page.MTCH_FindGifts);
        MTCH_FindGifts_CTRL ctrl = new MTCH_FindGifts_CTRL(new ApexPages.StandardController(oppMG));
        system.assertEquals(cItem/2, ctrl.potentialGifts.size());
        system.assertEquals(cItem/2, ctrl.selection.size());

        // search for more opps
        ctrl.showSearchSection();
        ctrl.searchFieldsWrapper.First_Soft_Credit_Amount__c = 100;
        ctrl.searchMore();
        system.assertEquals(cItem, ctrl.potentialGifts.size());
        system.assertEquals(cItem, ctrl.selection.size());

        // select from the searched opps
        system.assertEquals(false, ctrl.selection.get(listOpp[5].Id));
        system.assertEquals(false, ctrl.selection.get(listOpp[6].Id));
        system.assertEquals(0, ctrl.oppTotalSelected.Amount);
        ctrl.selection.put(listOpp[5].Id, true);    
        ctrl.selection.put(listOpp[6].Id, true);
        system.assertEquals(200, ctrl.oppTotalSelected.Amount);
        system.assertEquals(0, [select count() from Partial_Soft_Credit__c]);
        test.startTest();
        system.assertNotEquals(null, ctrl.saveAndClose());
        test.stopTest();
        system.assertEquals(2, [select count() from Partial_Soft_Credit__c]);
        system.assertEquals(2, [select count() from OpportunityContactRole where OpportunityId = :oppMG.Id]);
        system.assertEquals(2, [select count() from Opportunity where Matching_Gift_Status__c = 'Received']);
        system.assertEquals(2, [select count() from Opportunity where Matching_Gift__c = :oppMG.Id]);
        
        // does nothing, but hey, let's get that code coverage up!
        system.assertEquals(null, ctrl.refresh());
    }
}