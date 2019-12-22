/*
    Copyright (c) 2012, Salesforce.org
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
* @description Batch job runs after installation to populate Primary_Contact__c on opportunity.
*/
public class OPP_PrimaryContact_BATCH implements Database.Batchable<sObject> {
    
    /** @description The query for the batch process to run on.*/
    private String query;
    
    /** @description The batch process constructor; creates opportunity contact role query for all primary OCRs.*/
    public OPP_PrimaryContact_BATCH() {
        query = 'SELECT OpportunityId, Opportunity.Primary_Contact__c, ContactId FROM OpportunityContactRole WHERE IsPrimary=TRUE ORDER BY OpportunityId, LastModifiedDate DESC';
    }
    
    /** @description Batch process start method.*/
    public Database.QueryLocator start(Database.BatchableContext BC) {
        return Database.getQueryLocator(query);
    }

    /*********************************************************************************************************
    * @description Batch process execute method. Update existing opportunities' Primary Contact field based on
    * primary Opportunity Contact Role.
    */
    public void execute(Database.BatchableContext BC, List<sObject> scope) {
        
        //creates an opportunity object
        //if there are duplicate primary contact roles, take the oldest modified one, so if the
        //duplicate is the result of a merge, the winner of the merge keeps the primary contact role
        map<id,Opportunity> mapOppsForUpdate = new map<id,Opportunity>();
        for (OpportunityContactRole ocr : (list<OpportunityContactRole>)scope) {
            mapOppsForUpdate.put(ocr.OpportunityId, new Opportunity(id=ocr.OpportunityId, Primary_Contact__c=ocr.ContactId));
        }

        //verify we have an actual update to the primary contact field, otherwise remove the record
        for (OpportunityContactRole ocr : (list<OpportunityContactRole>)scope) {
            if (mapOppsForUpdate.get(ocr.OpportunityId).Primary_Contact__c == ocr.Opportunity.Primary_Contact__c)
                mapOppsForUpdate.remove(ocr.OpportunityId);
        }

        if (!mapOppsForUpdate.isEmpty()) {
            TDTM_TriggerHandler.disableTDTM = true;
            database.update(mapOppsForUpdate.values(), false);
            TDTM_TriggerHandler.disableTDTM = false;
        }
    }
    
    /** @description Batch process finish method, does nothing.*/
    public void finish(Database.BatchableContext BC) {}
}