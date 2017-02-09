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
* @group API
* @group-content ../../ApexDocContent/API.htm
* @description API class that exposes data import functionality.
*/
global with sharing class BDI_DataImport_API {

     private class BDIException extends Exception {}
     
    /*******************************************************************************************************
    * @description Imports all DataImport records that have not been previously imported. Their status is changed
                    to imported, once the process fishishes.
    * @return ID The ID of the batch job that performs the data import.
    */
    global static ID importData() {
        Data_Import_Settings__c diSettings = UTIL_CustomSettingsFacade.getDataImportSettings();
        Boolean batchRunning = false;
        Id apexJobId;
        try {
            // ensure we are in the household account model!
            if (!CAO_Constants.isHHAccountModel())
                throw(new BDIException(Label.bdiHouseholdModelRequired));
            
            // validate settings
            if (diSettings.Contact_Custom_Unique_ID__c != null) {
                // make sure c1 and c2 have the appropriately named field
                if (!UTIL_Describe.isValidField(UTIL_Namespace.StrTokenNSPrefix('DataImport__c'), 'Contact1_' + diSettings.Contact_Custom_Unique_ID__c) ||
                   !UTIL_Describe.isValidField(UTIL_Namespace.StrTokenNSPrefix('DataImport__c'), 'Contact2_' + diSettings.Contact_Custom_Unique_ID__c)) {
                    throw(new BDIException(string.format(Label.bdiContactCustomIdError, 
                        new string[]{diSettings.Contact_Custom_Unique_ID__c, 
                            'Contact1_' + diSettings.Contact_Custom_Unique_ID__c,
                            'Contact2_' + diSettings.Contact_Custom_Unique_ID__c})));
                }
            }
            
            if (diSettings.Account_Custom_Unique_ID__c != null) {
                // make sure a1 and a2 have the appropriately named field
                if (!UTIL_Describe.isValidField(UTIL_Namespace.StrTokenNSPrefix('DataImport__c'), 'Account1_' + diSettings.Account_Custom_Unique_ID__c) ||
                   !UTIL_Describe.isValidField(UTIL_Namespace.StrTokenNSPrefix('DataImport__c'), 'Account2_' + diSettings.Account_Custom_Unique_ID__c)) {
                    throw(new BDIException(string.format(Label.bdiAccountCustomIdError, 
                        new string[]{diSettings.Account_Custom_Unique_ID__c, 
                            'Account1_' + diSettings.Account_Custom_Unique_ID__c,
                            'Account2_' + diSettings.Account_Custom_Unique_ID__c})));
                }
            }
            
            
            // save any settings changes.
            try {
                upsert diSettings;
            } catch (Exception ex) {
                // this is a complete hack to work around the bug
                // https://github.com/SalesforceFoundation/Cumulus/issues/1094
                // for reasons I cannot fathom, we get an error trying to upsert the
                // settings object even though the UTIL_CustomSettingsFacade has already
                // done an upsert on it.  This failure only occurs if there is no org defined
                // custom setting instance.  The workaround is to catch the upsert failing, and
                // just clear the Id and do an insert.
                diSettings.Id = null;
                insert diSettings;
            }
            
            batchRunning = true;
            BDI_DataImport_BATCH bdi = new BDI_DataImport_BATCH();
            apexJobId = Database.executeBatch(bdi, integer.valueOf(diSettings.Batch_Size__c));
            return apexJobId;

        } catch (Exception ex) {
            if (ApexPages.currentPage() != null) {
                ApexPages.addMessage(new ApexPages.Message(
                    ApexPages.Severity.ERROR,
                    ex.getMessage()
                ));
            } else {
                // if we are not in a context where we can notify the user of
                // this exception, rethrow it
                throw ex;
            }
            return null;
        }
    }
}