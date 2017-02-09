/*
    Copyright (c) 2011, Salesforce.org
    All rights reserved.
    
    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are met:
    
    * Redistributions of source code must retain the above copyright
      notice, this List of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this List of conditions and the following disclaimer in the
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
* @author Evan Callahan
* @date 2010 
* @group Utilities
* @description Tests for recordtype wrapper for easy access to record type information.
*/

@isTest
private class UTIL_RecordTypes_TEST {

    // TEST
    static testmethod void testRecTypes() {
        // try bogus values
        Id bogus = UTIL_RecordTypes.GetRecordTypeId(Opportunity.sObjectType, 'Bogus');
        bogus = UTIL_RecordTypes.GetRecordTypeId(Contact.sObjectType, 'Bogus');
        bogus = UTIL_RecordTypes_API.GetRecordTypeId('Campaign', 'Bogus');
        
        // try all the functions
        set<Id> oppRtSet = UTIL_RecordTypes_API.GetRecordTypeIdSet('Opportunity', (new set<string>{'Bogus', 'Master', 'Grant'}));
        List<SelectOption> rtOptions = UTIL_RecordTypes.getRecordTypesForSelectList(Opportunity.sObjectType);
        
        // check against queried rectypes
        list<recordtype> ort = [select id, name from recordtype where isactive=true and sobjecttype='Opportunity' limit 1];
        if (!ort.isEmpty()) {
            Id rtId = UTIL_RecordTypes.GetRecordTypeId(Opportunity.sObjectType, ort[0].name);
            system.assertEquals(ort[0].id, rtId); 
            String rtName = UTIL_RecordTypes_API.GetRecordTypeName('Opportunity', ort[0].id);
            system.assertEquals(ort[0].name, rtName); 

            // the sets above should contain something too
            system.assert(oppRtSet.size() > 0);
            system.assert(rtOptions.size() > 0);            
        }       
    }
    
    static testMethod void TestRecordTypes() {

        ID idGift = UTIL_RecordTypes.GetRecordTypeId(Opportunity.sObjectType, UTIL_RecordTypes.getRecordTypeNameForGiftsTests(Opportunity.sObjectType));
        
        Set<String> setStr = new Set<String>{UTIL_RecordTypes.getRecordTypeNameForGiftsTests(Opportunity.sObjectType)};
        Set<Id> setId = UTIL_RecordTypes.GetRecordTypeIdSet(Opportunity.sObjectType, setStr);
        if(idGift!=null){
            system.Assert(setId.contains(idGift));
        
            system.AssertEquals(UTIL_RecordTypes.getRecordTypeNameForGiftsTests(Opportunity.sObjectType), UTIL_RecordTypes.GetRecordTypeName(Opportunity.sObjectType, idGift));        
                
            List<SelectOption> listSO = UTIL_RecordTypes.getRecordTypesForSelectList(Opportunity.sObjectType);
            system.Assert(listSO.IsEmpty() == false);       
        }
    }       

}