/*
    Copyright (c) 2015, Salesforce.org
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
* @group-content ../../ApexDocContent/Opportunity.htm
* @description Batch class creates names all Opportunities per the naming spec.
 */
public class OPP_OpportunityNaming_BATCH implements Database.Batchable<sObject>, Schedulable {
    
    /** @description The query for the batch process to run on.*/
    String query;
    
    /** @description The batch process constructor; creates opportunity query for all opportunities.*/
    public OPP_OpportunityNaming_BATCH() {
        query = 'SELECT Id, Name FROM Opportunity';
    }
    
    /** @description The batch process constructor; creates opportunity query for all opportunities with custom WHERE clause .*/
    public OPP_OpportunityNaming_BATCH(string customWhere) {
        query = 'SELECT Id, Name FROM Opportunity ' + customWhere;
    }

    /** @description Batch process start method.*/
    public Database.QueryLocator start(Database.BatchableContext BC) {
        return Database.getQueryLocator(query);
    }


    /** @description Schedulable execute method.*/
    public void execute(SchedulableContext context) {
        Database.executeBatch(new OPP_OpportunityNaming_BATCH(), 200);
    }

    /*********************************************************************************************************
    * @description Batch process execute method. Names and updates all opportunities in the current batch.
    */
    public void execute(Database.BatchableContext BC, List<sObject> scope) {
        //save old opp names to see if we need an update
        list<opportunity> oppsForUpdate = new list<Opportunity>();
        map<id,string> oppNames = new map<id,string>();
        for (Opportunity opp : (list<Opportunity>)scope)
            oppNames.put(opp.id, opp.Name);

        //refresh names
        OPP_OpportunityNaming.refreshOppNames((list<Opportunity>)scope);

        //find which names have been updated, add to list
        for (Opportunity opp : (list<Opportunity>)scope) {
            if (opp.Name != oppNames.get(opp.id))
                oppsForUpdate.add(opp);
        }

        if (!oppsForUpdate.isEmpty()) {
            database.update(oppsForUpdate, false);
        }
    }
    
    /** @description Batch process finish method, does nothing.*/
    public void finish(Database.BatchableContext BC) {}
    
}