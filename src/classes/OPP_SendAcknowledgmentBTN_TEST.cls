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
* @description Test class for the Send Acknowledgment buttons (single and list view) on Opportunities.  
*/
@isTest
public with sharing class OPP_SendAcknowledgmentBTN_TEST {

    /*********************************************************************************************************
    * @description Tests the controller for the Send Acknowledgment Button
    */
    static testmethod void testBtnCTRL() {
        Account testaccount = new Account(name='Test Company');
        insert testaccount;
        
        Contact testcontact = new Contact(AccountID = testaccount.Id,FirstName = 'Test',LastName = 'Company',Email = 'Test.Company@example.com');
        insert testcontact;
        
        Opportunity testoppty = new Opportunity(AccountId = testaccount.Id, Primary_Contact__c = testcontact.Id,
            closedate = system.today(),amount = 100,name='Test Company Donation',StageName = 'Closed Won',
            Acknowledgment_Status__c = 'To be Acknowledged');
        insert testoppty;
        
        OpportunityContactRole testocr = new OpportunityContactRole (OpportunityId = testoppty.Id, ContactId = testcontact.Id, IsPrimary = true);
        insert testocr;
        
        list<Opportunity> listOpp = new list<Opportunity>{testoppty};
        ApexPages.StandardSetController sc = new ApexPages.StandardSetController(listOpp);
        sc.setSelected(listOpp);
        OPP_SendAcknowledgmentBTN_CTRL btnCtrl = new OPP_SendAcknowledgmentBTN_CTRL(sc);
        
        test.startTest();
        btnCtrl.SendAcknowledgment();
        test.stopTest();
        
        listOpp = [select Id, name, Acknowledgment_Status__c, Acknowledgment_Date__c from Opportunity];
        system.assertEquals(1, listOpp.size());
        // we don't know if the workflow is active or not.
        system.assert(listOpp[0].Acknowledgment_Status__c == label.sendAcknowledgmentFailedStatus ||
            listOpp[0].Acknowledgment_Status__c == label.sendAcknowledgmentSucceeded);            
        system.assertNotEquals(null, btnCtrl.listOppNotSent);
        system.assertNotEquals(btnCtrl.cSent, btnCtrl.cNotSent);
        
            
    }

}