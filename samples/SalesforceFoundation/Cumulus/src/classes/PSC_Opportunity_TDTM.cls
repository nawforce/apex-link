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
* @description Trigger Handler on Opportunity for multi currency management 
*/
public with sharing class PSC_Opportunity_TDTM extends TDTM_Runnable {

    /*******************************************************************************************************
    * @description Trigger Handler on Opportunity that handles updating PSC currency when opportunity 
    * currency changes.
    * @param listNew the list of opportunity from trigger new. 
    * @param listOld the list of opportunity from trigger old. 
    * @param triggerAction which trigger event (BeforeInsert, AfterInsert, etc.). 
    * @param objResult the describe for opportunity
    * @return dmlWrapper.  
    ********************************************************************************************************/
    public override DmlWrapper run(List<SObject> listNew, List<SObject> listOld, 
        TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {

        if (triggerAction == TDTM_Runnable.Action.AfterUpdate && userInfo.isMultiCurrencyOrganization()) {
            DMLWrapper dmlWrapper = new DmlWrapper();
            
            dmlWrapper.objectsToUpdate.addAll((list<sObject>)updatePartialSoftCreditCurrencyFromParent(listNew, listOld));

            //set flag to allow changing PSC currency
            PSC_PartialSoftCredit_TDTM.blockCurrencyChange = false;
            return dmlWrapper;
        }
        return null;
    }

    /*******************************************************************************************************
    * @description For any given list of opportunities, makes child PSCs match the opportunity's currency.
    * @param listNew The list of opportunities from trigger new. 
    * @param listOld The list of opportunities from trigger old. 
    * @return list<Partial_Soft_Credit__c> Partial soft credits with modified currency that need updating via DML.
    ********************************************************************************************************/
    public static list<Partial_Soft_Credit__c> updatePartialSoftCreditCurrencyFromParent(list<sObject> newlist, list<sObject> oldlist) {
        list<Partial_Soft_Credit__c> results = new list<Partial_Soft_Credit__c>();
        //map holds object id to currency iso code
        map<id,string> mapOppIdToISO = new map<id,string>();

        //if the parent currency has changed, add to map
        for (integer i=0;i<newlist.size();i++) {
            if (newlist[i].get('CurrencyIsoCode')!=oldlist[i].get('CurrencyIsoCode')) {
                mapOppIdToISO.put((id)newlist[i].get('id'),(string)newlist[i].get('CurrencyIsoCode'));
            }
        }

        //can't put mapOppIdToISO.keySet() in dynamic soql query, so creating new set to hold the IDs
        set<id> oppIds = mapOppIdToISO.keySet();

        //query all PSCs associated with these opps, update their ISO code
        for (Partial_Soft_Credit__c psc : database.query('SELECT Id, Opportunity__c, CurrencyIsoCode FROM Partial_Soft_Credit__c WHERE Opportunity__c IN :oppIds')) {
            string oppCurrency = mapOppIdToISO.get(psc.Opportunity__c);

            psc.put('CurrencyIsoCode', oppCurrency);
            results.add(psc);
        }

        return results;
    }
}