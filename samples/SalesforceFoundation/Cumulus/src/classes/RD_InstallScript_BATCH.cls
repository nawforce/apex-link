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
* @group Recurring Donations
* @description Batch job runs after installation to populate Recurring_Donation_Installment_Number__c on opportunity.
*/
public without sharing class RD_InstallScript_BATCH implements Database.Batchable<sObject> {
    
    String query = 'SELECT Id FROM npe03__Recurring_Donation__c';
    
    public RD_InstallScript_BATCH() {}
    
    public Database.QueryLocator start(Database.BatchableContext BC) {
        return Database.getQueryLocator(query);
    }

    public void execute(Database.BatchableContext BC, List<sObject> scope) {
        map<id,list<Opportunity>> mapRdListOpp = new map<id,list<Opportunity>>();
        list<Opportunity> listOppsForUpdate = new list<Opportunity>();
        for (Opportunity opp : [SELECT Id, Recurring_Donation_Installment_Number__c, npe03__Recurring_Donation__c FROM Opportunity WHERE npe03__Recurring_Donation__c IN :scope ORDER BY CloseDate]) {
            if (mapRdListOpp.containsKey(opp.npe03__Recurring_Donation__c)) {
                mapRdListOpp.get(opp.npe03__Recurring_Donation__c).add(opp);
            } else {
                mapRdListOpp.put(opp.npe03__Recurring_Donation__c, new list<Opportunity>{opp});
            }
        }

        for (list<Opportunity> listOpp : mapRdListOpp.values()) {
            for (integer i=0;i<listOpp.size();i++) {
                if (listOpp[i].Recurring_Donation_Installment_Number__c != i+1) {
                    listOppsForUpdate.add(
                        new Opportunity(id=listOpp[i].id, Recurring_Donation_Installment_Number__c = i+1)
                    );
                }
            }
        }
        
        if (!listOppsForUpdate.isEmpty()) {
            TDTM_TriggerHandler.disableTDTM = true;
            database.update(listOppsForUpdate, false);
            TDTM_TriggerHandler.disableTDTM = false;
        }
    }
    
    public void finish(Database.BatchableContext BC) {}
}