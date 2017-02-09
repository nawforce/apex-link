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
 * @description test class for the MTCH_Opportunity_TDTM trigger handler
 */
 
@isTest 
public with sharing class MTCH_Opportunity_TEST {

    /*******************************************************************************************************
    * @description make a Matching Gift ClosedWon, and make sure its matched gifts are updated
    */ 
    static testmethod void testCreateMatches() {
        MTCH_FindGifts_TEST.initTestData(false, 100);
        Opportunity oppMG = MTCH_FindGifts_TEST.oppMG;
        system.assertNotEquals(null, oppMG);
        
        oppMG = [select Id, isClosed, isWon, Name, Amount from Opportunity where Id = :oppMG.Id];
        system.assertEquals(false, oppMG.isClosed);
        system.assertEquals(false, oppMG.isWon);
        
        list<Opportunity> listOpp = [select Id, Matching_Gift_Account__c, Matching_Gift_Status__c 
            from Opportunity where Id <> :oppMG.Id];
        system.assertEquals(MTCH_FindGifts_TEST.cItem, listOpp.size());
        
        for (Opportunity opp : listOpp) {
            system.assertNotEquals('Received', opp.Matching_Gift_Status__c);
            opp.Matching_Gift__c = oppMG.Id;
        }
        update listOpp;
            
        Test.startTest();
        oppMG.StageName = UTIL_UnitTestData_TEST.getClosedWonStage();
        update oppMG;
        Test.stopTest();    

        listOpp = [select Id, Matching_Gift_Account__c, Matching_Gift_Status__c 
            from Opportunity where Matching_Gift__c = :oppMG.Id];
        
        for (Opportunity opp : listOpp)
            system.assertEquals('Received', opp.Matching_Gift_Status__c);
    }

}