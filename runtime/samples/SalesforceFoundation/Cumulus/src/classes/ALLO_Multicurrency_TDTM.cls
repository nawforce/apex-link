/*
    Copyright (c) 2014, Salesforce.org
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
* @date 2014
* @group Allocations
* @group-content ../../ApexDocContent/Allocations.htm
* @description Class for multicurrency triggers and allocations rollup multicurrency related code.
*/
public class ALLO_Multicurrency_TDTM extends TDTM_Runnable {

    /** @description Boolean holds whether this org has multicurrency enabled. */
    public static boolean multiCurrency;

    /** @description Map holds exchange rates to avoid multiple queries. */
    public static map<string, decimal> exchangeRates = new map<string,decimal>();
    
    /** @description This organization's default corporate currency. */
    public static string currCorporate;

    /** @description DmlWrapper to hold object changes. */
    public DmlWrapper wrapper = new DmlWrapper();

    /*******************************************************************************************************
    * @description Trigger handler on Campaign and Recurring Donation for updating child allocation currency
    * when the parent currency is changed.
    * @param listNew The list of campaigns or recurring donations from trigger new. 
    * @param listOld The list of campaigns or recurring donations from trigger old. 
    * @param triggerAction Which action triggered the current context. (Before or After; Insert, Update, 
    * Delete, or Undelete.)
    * @param objResult The field describe object for the current trigger context. 
    * @return dmlWrapper Objects that need to be inserted or updated after the processing of the trigger.
    ********************************************************************************************************/
    public override DmlWrapper run(List<SObject> newlist, List<SObject> oldlist, 
        TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {

        //if this is a multicurrency org, update child objects 
        if (isMultiCurrency())
            wrapper.objectsToUpdate.addAll((list<sObject>)updateAllocationCurrencyFromParent(newlist,oldlist));

        return wrapper;
    }

    /*******************************************************************************************************
    * @description For any given list of allocation parent objects, (Opportunity, Campaign, or Recurring 
    * Donation,) queries for child allocations and updates currency to match parent.
    * @param listNew The list of opportunities, campaigns, or recurring donations from trigger new. 
    * @param listOld The list of opportunities, campaigns, or recurring donations from trigger old. 
    * @return list<Allocation__c> Allocations with modified currency that need updating via DML.
    ********************************************************************************************************/
    public static list<Allocation__c> updateAllocationCurrencyFromParent(list<sObject> newlist, list<sObject> oldlist) {
        list<Allocation__c> results = new list<Allocation__c>();
        //map holds object id to currency iso code
        map<id,string> mapIdToISO = new map<id,string>();

        //if the parent currency has changed, add to map
        for (integer i=0;i<newlist.size();i++) {
            if (newlist[i].get('CurrencyIsoCode')!=oldlist[i].get('CurrencyIsoCode')) {
                mapIdToISO.put((id)newlist[i].get('id'),(string)newlist[i].get('CurrencyIsoCode'));
            }
        }

        //can't put mapIdToISO.keySet() in dynamic soql query, so creating new set to hold the IDs
        set<id> parentIds = mapIdToISO.keySet();

        string alloQuery = 'SELECT Id, Amount__c, Opportunity__c, Campaign__c, Recurring_Donation__c, CurrencyIsoCode FROM Allocation__c '+
            'WHERE Opportunity__c IN :parentIds OR Campaign__c IN :parentIds OR Recurring_Donation__c IN :parentIds';
        
        //query all allocations associated with the parents, update their ISO code and their amount if needed 
        for (Allocation__c allo : database.query(alloQuery)) {
            string parentCurrency = mapIdToISO.get(ALLO_Allocations_TDTM.getParentId(allo));
            
            /*Not updating allocation amounts, as Salesforce only updates the currency and does not convert the amount
            if (allo.Amount__c!=null)
                allo.Amount__c = convertCurrency(parentCurrency, (string)allo.get('CurrencyIsoCode'), allo.Amount__c);
            */
            allo.put('CurrencyIsoCode', parentCurrency);
            results.add(allo);
        }

        return results;
    }

    /*******************************************************************************************************
    * @description For a list of allocations, set the currency based on the parent object.
    * @param listAllo The list of allocations. 
    * @return void This method modifies the list of allocations directly and returns nothing.
    ********************************************************************************************************/
    public static void updateAllocationCurrency(list<Allocation__c> listAllo) {
        map<id,string> mapIdToISO = new map<id,string>();

        //collect parent Ids
        for (Allocation__c allo : listAllo) {
            mapIdToISO.put(ALLO_Allocations_TDTM.getParentId(allo),null);
        }

        //get iso codes of parents
        mapIdToISO = getISOFromIdSet(mapIdToISO.keySet());

        //update allocation ISO code to match parent
        for (Allocation__c allo : listAllo) {
            allo.put('CurrencyIsoCode',mapIdToISO.get(ALLO_Allocations_TDTM.getParentId(allo)));
        }
    }

    /*******************************************************************************************************
    * @description For any set of ids of any sObject, gets the sObject's currency code.
    * @param setId A set of ids of any sObjects.
    * @return map<id,string> A map from the id of the object to the object's currency code string.
    ********************************************************************************************************/
    public static map<id,string> getISOFromIdSet (set<id> setId) {
        map<id,string> results = new map<id,string>();
        set<string> parentTypes = new set<string>();

        //get parent object names for query
        for (id thisId : setId)
            parentTypes.add(thisId.getSObjectType().getDescribe().getName());

        //query for ISO codes, fill out map
        for (string parent : parentTypes) {
            string query = 'SELECT Id, CurrencyIsoCode FROM ' + parent + ' WHERE Id IN :setId';
            for (sObject sobj : database.query(query))
                results.put((id)sobj.get('id'),(string)sobj.get('CurrencyIsoCode'));
        }

        return results;
    }

    /*******************************************************************************************************
    * @description Detects if this organization has multicurrency enabled, and populates class variables if 
    * so.
    * @return boolean Whether this org has multicurrency enabled.
    ********************************************************************************************************/
    public static boolean isMultiCurrency() {
        if (multiCurrency==null) {

            String queryExchangeRates = 'select IsoCode, ConversionRate, IsCorporate from CurrencyType';
            SObjectType soCurrencyType = Schema.getGlobalDescribe().get('CurrencyType'); 

            //are we in a multi-currency org?
            if (userInfo.isMultiCurrencyOrganization() && soCurrencyType != null) {
                multiCurrency = true;

                //iterate over all the currencies in the org (inc. inactive ones as they may have been used previously)
                //this allows us to put them in a map and use the ISO code as key and also set the corporate
                for(sObject ct: Database.query(queryExchangeRates)){
                    exchangeRates.put((string)ct.get('IsoCode'),(decimal)ct.get('ConversionRate'));
                    if ((boolean)ct.get('IsCorporate'))
                        currCorporate=(string)ct.get('IsoCode');
                }
            }
            else
                multiCurrency = false; 
        }        
        return multiCurrency;
    }

    /*******************************************************************************************************
    * @description Converts an amount from the corporate currency to a given currency.
    * @param ISO A string of the currency's iso code that we wish to convert the amount to.
    * @param amt A decimal of the amount to convert.
    * @return decimal The converted amount.
    ********************************************************************************************************/
        //do a conversion from amount in corporate currency to the currency specified by the ISO code
    public static decimal convertFromCorporate(String ISO,Decimal amt) {
        if(currCorporate == ISO || currCorporate == null) // no need to convert
            return amt;
        else //return value to 2DP
            return (amt * exchangeRates.get(ISO)).setScale(2,System.RoundingMode.UP) ;
    }


    /*******************************************************************************************************
    * @description Converts an amount from any given currency to any other currency.
    * @param fromISO A string of the currency's iso code of the amount we wish to convert.
    * @param toISO A string of the currency's iso code that we wish to convert the amount to.
    * @param amt A decimal of the amount to convert.
    * @return decimal The converted amount.
    ********************************************************************************************************/
   public static decimal convertCurrency(String fromISO, String toISO, Decimal amt) {
        if(fromISO == toISO) // no need to convert
            return amt;
        else //return value to 2DP
            return ((amt / exchangeRates.get(fromISO)) * exchangeRates.get(toISO)).setScale(2,System.RoundingMode.UP) ;
    }

}