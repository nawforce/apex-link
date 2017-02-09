/*
    Copyright (c) 2009, Salesforce.org
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
* @author Nick Bailey 
* @date 2011 (2.1)
* @group Payments
* @description Controls the creation of payment objects from Opportunity trigger
*/
public class PMT_PaymentCreator {

    /*******************************************************************************************************
    * @description map of Opportunity to Payment field mappings, validated to only contain mappings that
    * are of compatible types.
    */ 
    public static map<string, npe01__Payment_Field_Mapping_Settings__c> paymentMappings {
        get {
            if (paymentMappings == null) {
                //load custom field mapping settings - if they exist        
                map<string, npe01__Payment_Field_Mapping_Settings__c> paymentMappingsAll = UTIL_ListCustomSettingsFacade.getMapPaymentFieldMappingSettings().clone();
                paymentMappings = UTIL_ListCustomSettingsFacade.getMapPaymentFieldMappingSettings().clone();
            
                for (string s : paymentMappingsAll.keySet()){
                    npe01__Payment_Field_Mapping_Settings__c pfms = paymentMappingsAll.get(s); 
                
                    SobjectField opportunityField;
                    SobjectField paymentField;
                    boolean isValid = true; 
                                
                    //check Opp fields
                    opportunityField = Schema.sObjectType.Opportunity.fields.getMap().get(pfms.npe01__Opportunity_Field__c);
                              
                    //check Payment fields            
                    paymentField = Schema.sObjectType.npe01__OppPayment__c.fields.getMap().get(pfms.npe01__Payment_Field__c);
                        
                    if (paymentField != null && opportunityField != null) {
                        Schema.DisplayType payF = paymentField.getDescribe().getType();
                        Schema.DisplayType oppF = opportunityField.getDescribe().getType();
                        isValid = UTIL_Describe.canDisplaytypesCopy(oppF, payF);
                    } else {
                        isValid = false;
                    }
    
                    if (isValid == false) paymentMappings.keyset().remove(s);                         
                }
                
            }
            return paymentMappings;
        }
        set;
    }
    
    /*******************************************************************************************************
    * @description set of Opportunity Recordtype Id's to not create Payments for.
    */ 
    public static set<id> setOppRecordTypeIdNoPayment {
        get {
            if (setOppRecordTypeIdNoPayment == null) {
                string strProp = UTIL_CustomSettingsFacade.getContactsSettings().Opp_RecTypes_Excluded_for_Payments__c;
                if (strProp != null) {
                    setOppRecordTypeIdNoPayment = new Set<Id>((List<Id>)strProp.split(';'));
                } else {
                    setOppRecordTypeIdNoPayment = new set<Id>();
                }
            }
            return setOppRecordTypeIdNoPayment;
        }
        set;
    }

    /*******************************************************************************************************
    * @description set of Opportunity Type values to not create Payments for.
    */ 
    public static set<string> setOppTypeNoPayment {
        get {
            if (setOppTypeNoPayment == null) {
                string strProp = UTIL_CustomSettingsFacade.getContactsSettings().Opp_Types_Excluded_for_Payments__c;
                if (strProp != null) {
                    setOppTypeNoPayment = new set<string>(strProp.split(';'));    
                } else {
                    setOppTypeNoPayment = new set<string>();
                }
            }
            return setOppTypeNoPayment;
        }
        set;
    }

    /*******************************************************************************************************
    * @description specifies whether Payments should be excluded for the Opportunity
    * @param opp the Opportunity to test.
    * @return boolean true if Payments excluded for the Opportunity
    */ 
    private static boolean isNoPaymentForOpp(Opportunity opp) {
        if (opp.npe01__Do_Not_Automatically_Create_Payment__c)
            return true;
        if (setOppTypeNoPayment.contains(opp.Type))
            return true;
        if (setOppRecordTypeIdNoPayment.contains((id)opp.get('RecordTypeId')))
            return true;
        return false;
    }    
                
    /*******************************************************************************************************
    * @description Constructor that gets called by the PMT_Payment_TDTM trigger handler.
    * @param newOppsMap the map of Opps from trigger new. 
    * @param oldOppsMap the map of Opps from trigger old. 
    * @param ta which trigger event (BeforeInsert, AfterInsert, etc.). 
    * @param dmlWrapper to hold any payments that need to be inserted, updated, or deleted.  
    ********************************************************************************************************/
    public PMT_PaymentCreator(Map<id, Opportunity> newOppsMap, Map<id, Opportunity> oldOppsMap, TDTM_Runnable.Action ta, 
        TDTM_Runnable.DMLWrapper dmlWrapper) {
        
        npe01__Contacts_And_Orgs_Settings__c ContactSettings = UTIL_CustomSettingsFacade.getContactsSettings();
                    
        if (ContactSettings != null && ContactSettings.npe01__Payments_Enabled__c == true){
            list<Opportunity> newOpps = newOppsMap.values();
            list<npe01__OppPayment__c> paymentsToInsert = new list<npe01__OppPayment__c>();
            list<npe01__OppPayment__c> paymentsToUpdate = new list<npe01__OppPayment__c>();
            list<npe01__OppPayment__c> paymentsToDelete = new list<npe01__OppPayment__c>();
            list<Opportunity> updatedOpps = new list<Opportunity>();
            
            // James Melville Added to support multi-currency sfdc.  */
            // CurrencyIsoCode doesn't exist in non-multi-currency orgs
            SObjectField OppCurrencyField = Schema.sObjectType.Opportunity.fields.getMap().get('CurrencyIsoCode');
            SObjectField PaymentCurrencyField = Schema.sObjectType.npe01__OppPayment__c.fields.getMap().get('CurrencyIsoCode');    
            SObjectField OppRecordTypeId = Schema.sObjectType.Opportunity.fields.getMap().get('RecordTypeId');

            if (TDTM_Runnable.Action.BeforeUpdate == ta) {
                if (!String.isEmpty(ContactSettings.Payments_Auto_Close_Stage_Name__c)) {
                    String closedStageName = ContactSettings.Payments_Auto_Close_Stage_Name__c;
                    for (Opportunity opp : newOppsMap.values()) {
                        closeOpportunityIfAllPaymentsReceived(
                            oldOppsMap.get(opp.Id),
                            opp,
                            closedStageName
                        );
                    }
                }
            }

            //SOQL for Updated Opps and Related Payments.  Queries for known fields and field in custom Payment Field Mappings.
            if (ta == TDTM_Runnable.Action.AfterUpdate){
                String soqlStatement = '';
                soqlStatement += 'select id, CloseDate, Amount, isClosed, isWon, npe01__Do_Not_Automatically_Create_Payment__c, Type, ';
                if (OppRecordTypeId != null)
                    soqlStatement += 'RecordTypeId, ';
                for (string s : paymentMappings.keyset()) {
                    npe01__Payment_Field_Mapping_Settings__c pfms = paymentMappings.get(s);
                    if (!soqlStatement.contains(pfms.npe01__Opportunity_Field__c)) {               
                        soqlStatement += pfms.npe01__Opportunity_Field__c + ', ';
                    }   
                }
                if (OppCurrencyField!=null)
                    soqlStatement += 'CurrencyIsoCode, ';
                soqlStatement += 'npe01__payments_made__c, ';
                string soqlSub = '(SELECT id, npe01__Paid__c, npe01__Payment_Amount__c, npe01__Payment_Date__c, npe01__Written_Off__c, ';
                for (string s : paymentMappings.keyset()) {
                    npe01__Payment_Field_Mapping_Settings__c pfms = paymentMappings.get(s);
                    if (!soqlSub.contains(pfms.npe01__Payment_Field__c)) {               
                        soqlSub += pfms.npe01__Payment_Field__c + ', ';   
                    }
                }
                soqlStatement += soqlSub;
                soqlStatement += 'npe01__scheduled_date__c ';
                soqlStatement += 'from Opportunity.npe01__OppPayment__r WHERE npe01__Opportunity__c IN :newOpps) from Opportunity WHERE id in :newOpps';
                updatedOpps = Database.query(soqlStatement);
            }
    
            //IF Trigger is Insert, Create a new Payment
            if( ta == TDTM_Runnable.Action.AfterInsert){
                for (Opportunity thisOpp : newOpps){
                    
                    if (isNoPaymentForOpp(thisOpp))
                        continue;
                        
                    // if opp is closed won with no payments scheduled or made, add an automatic payment
                        if ( /*(thisOpp.IsClosed && thisOpp.IsWon) && */
                                        thisOpp.amount > 0 && thisOpp.npe01__payments_made__c == 0 && thisOpp.npe01__Number_of_Payments__c == 0) {
                                npe01__oppPayment__c op = new npe01__oppPayment__c(
                                    npe01__opportunity__c = thisOpp.id
                                );
                                
                                //in multicurrency SFDC set the payment currency field to equal to the opp currency
                                if(PaymentCurrencyField != null && OppCurrencyField != null)
                                    op.put(PaymentCurrencyField, thisOpp.get(OppCurrencyField));
                                
                                op.npe01__payment_amount__c = thisOpp.amount;
                            
                            if (paymentMappings.size() > 0){
                                for (string s : paymentMappings.keyset()){
                                        npe01__Payment_Field_Mapping_Settings__c pfms = paymentMappings.get(s);
                                        string x = pfms.npe01__Opportunity_Field__c;
                                        if (thisOpp.get(x) != null)
                                           op.put(pfms.npe01__Payment_Field__c, thisOpp.get(x));
                                }
                            }
                            
                            if (thisOpp.IsClosed && thisOpp.IsWon){
                                op.npe01__paid__c = true;
                                op.npe01__payment_date__c = thisOpp.closeDate;
                            }
                            
                            if (!thisOpp.IsClosed){
                                op.npe01__paid__c = false;
                                op.npe01__scheduled_date__c = thisOpp.closeDate;
                            }
                            
                            paymentsToInsert.add(op);
                        }
                        /*
                        else if (!thisOpp.IsClosed && 
                                        thisOpp.amount > 0 && thisOpp.npe01__payments_made__c == 0 && thisOpp.npe01__Number_of_Payments__c == 0 && !thisOpp.npe01__Do_Not_Automatically_Create_Payment__c) {
                            paymentsToInsert.add(new npe01__oppPayment__c(
                                npe01__opportunity__c = thisOpp.id,
                                npe01__scheduled_date__c = thisOpp.closeDate,
                                npe01__payment_amount__c = thisOpp.amount,
                                npe01__paid__c = false
                            ));
                        }
                        */
                }
                // put in the payment for closed opps       
                if (!paymentsToInsert.isEmpty()) 
                    dmlWrapper.objectsToInsert.addAll((list<SObject>) paymentsToInsert);
            }
            
            
            //IF Trigger is Update, keep synced Payments in Sync or delete if Opp is lost
            if( ta == TDTM_Runnable.Action.AfterUpdate ){
                list<Opportunity> oldOpps = oldOppsMap.values();
                for (Opportunity thisOpp : updatedOpps){
                    Opportunity oldOpp = oldOppsMap.get(thisOpp.id);
                    
                    boolean mappingsUpdated = false;
                    
                    //check to see if any of the custom mapped fields have been updated
                    for (string s : paymentMappings.keyset()){
                        if(thisOpp.get(paymentMappings.get(s).npe01__Opportunity_Field__c) != oldOpp.get(paymentMappings.get(s).npe01__Opportunity_Field__c)){
                            mappingsUpdated = true;
                        }
                    }
                
                     
                    if (thisOpp.npe01__OppPayment__r.size() > 0){
                         // if opp is updated, update the automatic payment
                        if ( ((thisOpp.Amount != oldOpp.Amount) || (thisOpp.CloseDate != oldOpp.CloseDate) || mappingsUpdated == true) &&
                                (thisOpp.npe01__OppPayment__r[0].npe01__Scheduled_Date__c == oldOpp.CloseDate || thisOpp.npe01__OppPayment__r[0].npe01__Scheduled_Date__c == thisOpp.CloseDate) &&
                                (thisOpp.npe01__OppPayment__r[0].npe01__Payment_Amount__c == oldOpp.Amount || thisOpp.npe01__OppPayment__r[0].npe01__Payment_Amount__c == thisOpp.Amount) &&
                                thisOpp.npe01__OppPayment__r.size() == 1 &&
                                thisOpp.npe01__OppPayment__r[0].npe01__paid__c == False &&
                                !thisOpp.isClosed && thisOpp.amount > 0 &&
                                thisOpp.npe01__payments_made__c == 0) {
                            thisOpp.npe01__OppPayment__r[0].npe01__scheduled_date__c = thisOpp.closeDate;
                            thisOpp.npe01__OppPayment__r[0].npe01__payment_amount__c = thisOpp.amount;
                            
                            
                            if (paymentMappings.size() > 0){
                                for (string s : paymentMappings.keyset()){
                                        npe01__Payment_Field_Mapping_Settings__c pfms = paymentMappings.get(s);
                                        string x = pfms.npe01__Opportunity_Field__c;
                                        if (thisOpp.get(x) != null)
                                           thisOpp.npe01__OppPayment__r[0].put(pfms.npe01__Payment_Field__c, thisOpp.get(x));
                                }
                            }
                         
                            paymentsToUpdate.add(thisOpp.npe01__OppPayment__r[0]);
                        }
                        else if ( ((thisOpp.Amount != oldOpp.Amount) || (thisOpp.CloseDate != oldOpp.CloseDate) || (thisOpp.isWon != oldOpp.IsWon)) &&
                                (thisOpp.npe01__OppPayment__r[0].npe01__scheduled_Date__c == oldOpp.CloseDate || thisOpp.npe01__OppPayment__r[0].npe01__scheduled_Date__c == thisOpp.CloseDate || thisOpp.npe01__OppPayment__r[0].npe01__Payment_Date__c == oldOpp.CloseDate || thisOpp.npe01__OppPayment__r[0].npe01__Payment_Date__c == thisOpp.CloseDate) &&
                                (thisOpp.npe01__OppPayment__r[0].npe01__Payment_Amount__c == oldOpp.Amount || thisOpp.npe01__OppPayment__r[0].npe01__Payment_Amount__c == thisOpp.Amount) &&
                                thisOpp.npe01__OppPayment__r.size() == 1 &&
                                thisOpp.npe01__OppPayment__r[0].npe01__paid__c == False &&
                                thisOpp.isClosed && thisOpp.isWon && thisOpp.amount > 0 &&
                                thisOpp.npe01__payments_made__c == 0) {
                            thisOpp.npe01__OppPayment__r[0].npe01__payment_date__c = thisOpp.closeDate;
                            thisOpp.npe01__OppPayment__r[0].npe01__payment_amount__c = thisOpp.amount;
                            thisOpp.npe01__OppPayment__r[0].npe01__paid__c = true;
                            paymentsToUpdate.add(thisOpp.npe01__OppPayment__r[0]);
                      
                        }
                        else if ( thisOpp.isClosed && !thisOpp.isWon) {
                            
                            for(npe01__OppPayment__c thisPayment : thisOpp.npe01__OppPayment__r){
                                if(thisPayment.npe01__Paid__c == False && thisPayment.npe01__Written_Off__c == False){
                                    paymentsToDelete.add(thisPayment);
                                }
                            }
                        }
                    }
                    
                    // we may need to add payments if the open opp amount has been changed from $0
                    else if (thisOpp.Amount != 0 && thisOpp.Amount != null && (oldOpp.Amount == 0 || oldOpp.Amount == null ) 
                        && !thisOpp.isClosed && !isNoPaymentForOpp(thisOpp)){
                        npe01__oppPayment__c op = new npe01__oppPayment__c(
                            npe01__opportunity__c = thisOpp.id,
                            npe01__Paid__c = false,
                            npe01__Scheduled_Date__c = thisOpp.CloseDate,
                            npe01__Payment_Amount__c = thisOpp.Amount
                        );                                
                                
                        //in multicurrency SFDC set the payment currency field to equal to the opp currency
                        if(PaymentCurrencyField != null && OppCurrencyField != null)
                            op.put(PaymentCurrencyField, thisOpp.get(OppCurrencyField));
                            
                        if (paymentMappings.size() > 0){
                            for (string s : paymentMappings.keyset()){
                                npe01__Payment_Field_Mapping_Settings__c pfms = paymentMappings.get(s);
                                string x = pfms.npe01__Opportunity_Field__c;
                                if (thisOpp.get(x) != null)
                                    op.put(pfms.npe01__Payment_Field__c, thisOpp.get(x));
                            }
                        }   
                        paymentsToInsert.add(op);                                              
                    }   
                }
                // update or delete payments     
                if (!paymentsToUpdate.isEmpty())
                    dmlWrapper.objectsToUpdate.addAll((list<SObject>)paymentsToUpdate);
                if (!paymentsToDelete.isEmpty())
                    dmlWrapper.objectsToDelete.addAll((list<SObject>)paymentsToDelete);
                if (!paymentsToInsert.isEmpty())
                    dmlWrapper.objectsToInsert.addAll((list<SObject>)paymentsToInsert);
            }
        }
    }

    /**
     * @description Close an opportunity if all payments have been received.
     * This will update the StageName of newOpp, in place, if "all payments
     * received" conditions are satisfied.
     * @param oldOpp The opportunity before being updated
     * @param newOpp The opportunity after being updated
     * @param closedStageName The name of an opportunity stage that corresponds to Closed/Won to transition to if all payments are determined to be received
     * @return void
     */
    private void closeOpportunityIfAllPaymentsReceived(Opportunity oldOpp, Opportunity newOpp, String closedStageName) {
        Boolean amountUpdated = (oldOpp.Amount != newOpp.Amount);
        Boolean paymentAmountReceivedUpdated = (oldOpp.npe01__Payments_Made__c != newOpp.npe01__Payments_Made__c);
        Boolean amountPositive = (newOpp.Amount > 0);
        Boolean isNotAndWasNotClosed = (!oldOpp.IsClosed && !newOpp.IsClosed);
        Boolean amountEqualPaymentReceived = (newOpp.Amount == newOpp.npe01__Payments_Made__c);
        Boolean stageNameSpecified = !String.isEmpty(closedStageName);

        if (
            (amountUpdated || paymentAmountReceivedUpdated)
            && amountPositive
            && isNotAndWasNotClosed
            && amountEqualPaymentReceived
            && stageNameSpecified
        ) {
            newOpp.StageName = closedStageName;
        }
    }
}