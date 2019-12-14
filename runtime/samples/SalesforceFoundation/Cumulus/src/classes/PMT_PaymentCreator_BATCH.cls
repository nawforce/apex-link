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
* @description Batch class creates payments for Opportunities that are missing them.
 */
public class PMT_PaymentCreator_BATCH implements Database.Batchable<sObject> {
    
    /** @description The query for the batch process to run on.*/
    private String query;
    
    private set<Id> setOppRecordTypeIdNoPayment = PMT_PaymentCreator.setOppRecordTypeIdNoPayment;
    private set<String> setOppTypeNoPayment = PMT_PaymentCreator.setOppTypeNoPayment;

    /** @description The batch process constructor; creates opportunity query for opportunities that might need payments.*/
    public PMT_PaymentCreator_BATCH() {
        set<string> queryFields = new set<string>{'Type','Amount','CloseDate','npe01__Payments_Made__c','npe01__Number_of_Payments__c','npe01__Do_Not_Automatically_Create_Payment__c','IsClosed','IsWon'};

        //add record type and multicurrency fields if they exist
        if (UTIL_Describe.isValidField('Opportunity','RecordTypeId'))
            queryFields.add('RecordTypeID');
        if (UTIL_Describe.isValidField('Opportunity','CurrencyIsoCode'))
            queryFields.add('CurrencyIsoCode');

        //add payment mapping fields
        for (npe01__Payment_Field_Mapping_Settings__c pfms : PMT_PaymentCreator.paymentMappings.values()) {
            queryFields.add(pfms.npe01__Opportunity_Field__c);
        }

        //build query
        query = 'SELECT Id';
        for (string field : queryFields)
            query += ', ' + field;

        query += ' FROM Opportunity WHERE npe01__Number_of_Payments__c = 0 AND Amount > 0 AND npe01__Do_Not_Automatically_Create_Payment__c = false';
        query += ' AND (IsWon=TRUE OR IsClosed=FALSE) AND Type NOT IN :setOppTypeNoPayment';

        //filter record type id only if the field exists
        if (UTIL_Describe.isValidField('Opportunity','RecordTypeId'))
            query +=' AND RecordTypeID NOT IN :setOppRecordTypeIdNoPayment ';
    }

    /** @description Batch process start method.*/
    public Database.QueryLocator start(Database.BatchableContext BC) {
        return Database.getQueryLocator(query);
    }

    /*********************************************************************************************************
    * @description Batch process execute method. Names and updates all opportunities in the current batch.
    */
    public void execute(Database.BatchableContext BC, List<sObject> scope) {
        map<Id, Opportunity> mapOppIdOpportunity = new map<id, Opportunity>((list<Opportunity>)scope);
        
        TDTM_Runnable.DmlWrapper dmlWrapper = new TDTM_Runnable.dmlWrapper();
        //send to payment creator as if these opportunities were just inserted in order to create payments
        PMT_PaymentCreator pc = new PMT_PaymentCreator(mapOppIdOpportunity, null, TDTM_Runnable.Action.AfterInsert, dmlWrapper); 

        //insert payments
        TDTM_TriggerHandler.processDML(dmlWrapper);
    }
    
    /** @description Batch process finish method, does nothing.*/
    public void finish(Database.BatchableContext BC) {}
}