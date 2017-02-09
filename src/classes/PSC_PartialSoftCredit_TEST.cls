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
* @description tests for the Partial Soft Credits trigger handler
*/

@isTest
public with sharing class PSC_PartialSoftCredit_TEST {

    /*******************************************************************************************************
    * @description test data for Partial Soft Credit tests
    */ 
    static integer cContactTest = 10;
    static integer cOppTest = 10;
    static integer amountTest = 100;
    static list<Contact> listCon;
    static list<Opportunity> listOpp;
    static Account accTest;
    
    /*******************************************************************************************************
    * @description create test data for Partial Soft Credit tests. This includes Household
    * Contacts, Opportunities, OCRs, and Partial Soft Credits.
    * @param cContact The number of Contacts to create
    * @param cOpp The number of Opportunities to create
    * @param amount The amount to make each Opportunity.  Partial soft credits will be made for amount/2.
    * @return void
    */ 
    private static void createTestData(integer cContact, integer cOpp, integer amount, boolean addOCRs) {
        
        // create an org account to own the Opps.
        accTest = new Account(name='Test Org Account');
        
        // create some Contacts in Household Accounts
        listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(cContact);
        insert listCon;
        listCon = [select Id, Firstname,Lastname, AccountId from Contact];
        system.assertEquals(cContact, listCon.size());
        
        // create cOpp Opportunities
        listOpp = new list<Opportunity>();
        integer iCon = 0;
        for (integer i = 0; i < cOpp; i++) {
            Opportunity opp = new Opportunity(
                AccountId=accTest.Id,
                name='Test Opp' + i,
                StageName=UTIL_UnitTestData_TEST.getClosedWonStage(), 
                CloseDate=system.Today(), 
                Amount=amount
                );
            if (addOCRs) {
                if (iCon >= cContact)
                    iCon = 0;
                opp.Primary_Contact__c = listCon[iCon].Id;
                iCon++;
            }
            listOpp.add(opp);
        } 
        insert listOpp;
        
        // check OCR's automatically created
        list<OpportunityContactRole> listOCR = [select Id, ContactId, OpportunityId, Role, IsPrimary from OpportunityContactRole];
        system.assertEquals(addOCRs ? cOpp : 0, listOCR.size());

    }

    /*******************************************************************************************************
    * @description create multiple new Partial Soft Credits, and verify new OCRs are created for them.
    */
    static testmethod void createPSCsNewOCRs() {
        
        createTestData(cContactTest, cOppTest, amountTest, false);
        
        // make sure no OCR's exist
        list<OpportunityContactRole> listOCR = [select Id, ContactId, OpportunityId, Role, IsPrimary from OpportunityContactRole];
        system.assertEquals(0, listOCR.size());        
        
        // create our Partial Soft Credits
        list<Partial_Soft_Credit__c> listPSC = new list<Partial_Soft_Credit__c>();
        integer iOpp = 0;
        for (integer i = 0; i < cContactTest; i++) {
            if (iOpp >= cOppTest)
                iOpp = 0;
            
            listPSC.add(new Partial_Soft_Credit__c(
                Contact__c = listCon[i].Id,
                Opportunity__c = listOpp[iOpp].Id,
                Role_Name__c = 'Soft Credit',
                Amount__c = amountTest/2                
                ));

            iOpp++;
        }
        insert listPSC;
        
        // make sure new OCRs created
        set<ID> setOCRId = new set<ID>();
        for (OpportunityContactRole ocr : [select Id, Role from OpportunityContactRole]) {
            system.assertEquals('Soft Credit', ocr.Role);
            setOCRId.add(ocr.Id);
        }
        system.assertEquals(cOppTest, setOCRId.size());
        
        // make sure OCR id's stored in PSCs
        listPSC = [select Id, Contact_Role_ID__c from Partial_Soft_Credit__c];
        system.assertEquals(cOppTest, listPSC.size());
        for (Partial_Soft_Credit__c psc : listPSC) {
            system.assertEquals(true, setOCRId.contains(psc.Contact_Role_ID__c));
        }        
    }      

    /*******************************************************************************************************
    * @description create multiple new Partial Soft Credits, and verify existing OCRs are used for them.
    */
    static testmethod void createPSCsExistingOCRs() {
        
        createTestData(cContactTest, cOppTest, amountTest, true);
        
        // make sure OCR's exist
        list<OpportunityContactRole> listOCR = [select Id, ContactId, OpportunityId, Role, IsPrimary from OpportunityContactRole];
        system.assertEquals(cOppTest, listOCR.size());        
        
        // create our Partial Soft Credits
        list<Partial_Soft_Credit__c> listPSC = new list<Partial_Soft_Credit__c>();
        integer iOpp = 0;
        for (integer i = 0; i < cContactTest; i++) {
            if (iOpp >= cOppTest)
                iOpp = 0;
            
            listPSC.add(new Partial_Soft_Credit__c(
                Contact__c = listCon[i].Id,
                Opportunity__c = listOpp[iOpp].Id,
                Role_Name__c = 'Soft Credit',
                Amount__c = amountTest/2                
                ));

            iOpp++;
        }
        insert listPSC;
        
        // make sure no new OCRs created
        set<ID> setOCRId = new set<ID>();
        for (OpportunityContactRole ocr : [select Id, Role from OpportunityContactRole]) {
            system.assertNotEquals('Soft Credit', ocr.Role);
            setOCRId.add(ocr.Id);
        }
        system.assertEquals(cOppTest, setOCRId.size());
                
        // make sure OCR id's stored in PSCs
        listPSC = [select Id, Contact_Role_ID__c from Partial_Soft_Credit__c];
        system.assertEquals(cOppTest, listPSC.size());
        for (Partial_Soft_Credit__c psc : listPSC) {
            system.assertEquals(true, setOCRId.contains(psc.Contact_Role_ID__c));
        }        
    }      

    /*******************************************************************************************************
    * @description create multiple new Partial Soft Credits, and verify new & existing OCRs are used for them.
    */
    static testmethod void createPSCsSomeExistingOCRs() {
        
        createTestData(cContactTest, cOppTest, amountTest, true);
        
        // make sure OCR's exist
        list<OpportunityContactRole> listOCR = [select Id, ContactId, OpportunityId, Role, IsPrimary from OpportunityContactRole];
        system.assertEquals(cOppTest, listOCR.size());  
        
        // now delete 1/2 our OCRs
        for (integer i = 0; i < listOCR.size(); i++) {
            if (system.math.mod(i, 2) == 0)
                listOCR.remove(i);
        }
        system.assert(listOCR.size() < cOppTest);      
        delete listOCR;
        
        // create our Partial Soft Credits
        list<Partial_Soft_Credit__c> listPSC = new list<Partial_Soft_Credit__c>();
        integer iOpp = 0;
        for (integer i = 0; i < cContactTest; i++) {
            if (iOpp >= cOppTest)
                iOpp = 0;
            
            listPSC.add(new Partial_Soft_Credit__c(
                Contact__c = listCon[i].Id,
                Opportunity__c = listOpp[iOpp].Id,
                Role_Name__c = 'Soft Credit',
                Amount__c = amountTest/2                
                ));

            iOpp++;
        }
        insert listPSC;
        
        // make we get correct number of OCRs
        set<ID> setOCRId = new set<ID>();
        for (OpportunityContactRole ocr : [select Id, Role from OpportunityContactRole]) {
            setOCRId.add(ocr.Id);
        }
        system.assertEquals(cOppTest, setOCRId.size());
                
        // make sure OCR id's stored in PSCs
        listPSC = [select Id, Contact_Role_ID__c from Partial_Soft_Credit__c];
        system.assertEquals(cOppTest, listPSC.size());
        for (Partial_Soft_Credit__c psc : listPSC) {
            system.assertEquals(true, setOCRId.contains(psc.Contact_Role_ID__c));
        }        
    }      

    /*******************************************************************************************************
    * @description update existing PSC's Contact and Opportunity fields.  verify update fails with error.
    */
    static testmethod void updatePSCsInvalidFields() {
        
        createTestData(cContactTest, cOppTest, amountTest, true);
        
        // make sure OCR's exist
        list<OpportunityContactRole> listOCR = [select Id, ContactId, OpportunityId, Role, IsPrimary from OpportunityContactRole];
        system.assertEquals(cOppTest, listOCR.size());        
        
        // create our Partial Soft Credits
        list<Partial_Soft_Credit__c> listPSC = new list<Partial_Soft_Credit__c>();
        integer iOpp = 0;
        for (integer i = 0; i < cContactTest; i++) {
            if (iOpp >= cOppTest)
                iOpp = 0;
            
            listPSC.add(new Partial_Soft_Credit__c(
                Contact__c = listCon[i].Id,
                Opportunity__c = listOpp[iOpp].Id,
                Role_Name__c = 'Soft Credit',
                Amount__c = amountTest/2                
                ));

            iOpp++;
        }
        insert listPSC;
        
        // try to update illegal fields and make sure error thrown.
        try {
            listPSC[0].Contact__c = listCon[cContactTest-1].Id;
            listPSC[1].Opportunity__c = listOpp[0].Id;
            update listPSC;
            system.assert(false,'update should have thrown an exception!');
        } catch (Exception ex) {
            //reparentable master field is not set on these fields, so the system generates the error.
            //not sure I trust the message text to not change depending on locale or other core changes,
            //so the assert is commented out.
            //system.assert(ex.getMessage().contains('Field is not writeable'));            
        }
    }      

    /*******************************************************************************************************
    * @description update existing PSC's Role fields.  verify OCR's that had same Role get updated but 
    * others do not.
    */
    static testmethod void updatePSCsRoleName() {
        
         createTestData(cContactTest, cOppTest, amountTest, true);
        
        // make sure OCR's exist
        list<OpportunityContactRole> listOCR = [select Id, ContactId, OpportunityId, Role, IsPrimary from OpportunityContactRole];
        system.assertEquals(cOppTest, listOCR.size());  
        
        // now delete 1/2 our OCRs
        for (integer i = 0; i < listOCR.size(); i++) {
            if (system.math.mod(i, 2) == 0)
                listOCR.remove(i);
        }
        system.assert(listOCR.size() < cOppTest);      
        delete listOCR;
        
        // create our Partial Soft Credits
        list<Partial_Soft_Credit__c> listPSC = new list<Partial_Soft_Credit__c>();
        integer iOpp = 0;
        for (integer i = 0; i < cContactTest; i++) {
            if (iOpp >= cOppTest)
                iOpp = 0;
            
            listPSC.add(new Partial_Soft_Credit__c(
                Contact__c = listCon[i].Id,
                Opportunity__c = listOpp[iOpp].Id,
                Role_Name__c = 'Soft Credit',
                Amount__c = amountTest/2                
                ));

            iOpp++;
        }
        insert listPSC;
        
        // make we get correct number of OCRs
        set<ID> setOCRId = new set<ID>();
        for (OpportunityContactRole ocr : [select Id, Role from OpportunityContactRole]) {
            setOCRId.add(ocr.Id);
        }
        system.assertEquals(cOppTest, setOCRId.size());
                
        // make sure OCR id's stored in PSCs
        listPSC = [select Id, Contact_Role_ID__c from Partial_Soft_Credit__c];
        system.assertEquals(cOppTest, listPSC.size());
        for (Partial_Soft_Credit__c psc : listPSC) {
            system.assertEquals(true, setOCRId.contains(psc.Contact_Role_ID__c));
        }
        
        // now update all PSC Role Names
        for (Partial_Soft_Credit__c psc : listPSC) {
            psc.Role_Name__c = 'Changed';
        }
        update listPSC;
        
        // verify only OCR's that had a matching Role were changed
        integer cChanged = 0;
        for (OpportunityContactRole ocr : [select Id, Role from OpportunityContactRole]) {
            system.assertNotEquals('Soft Credit', ocr.Role);
            if (ocr.Role == 'Changed')
                cChanged++;
        }
        system.assert(cChanged > 0 && cChanged < listPSC.size());
    }      

}