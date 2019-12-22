/*
    Copyright (c) 2016, Salesforce.org
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
* @date 2016
* @group Utilities
* @description Helper class that retrieves custom list settings and sets defaults.
*/
public with sharing class UTIL_ListCustomSettingsFacade {

    //list settings
    public static Map<String,npe03__Custom_Field_Mapping__c> mapCustomFieldMappings;
    public static Map<String,npe03__Custom_Installment_Settings__c> mapCustomInstallmentSettings;
    public static Map<String,Opportunity_Naming_Settings__c> mapOppNamingSettings;
    public static Map<String,npe01__Payment_Field_Mapping_Settings__c> mapPaymentFieldMappingSettings;
    public static Map<String,npe4__Relationship_Auto_Create__c> mapRelationshipAutoCreateSettings;
    public static Map<String,npe4__Relationship_Lookup__c> mapRelationshipLookupSettings;
    public static Map<String,npo02__User_Rollup_Field_Settings__c> mapUserRollupFieldSettings;

    /*******************************************************************************************************
    * @description Retrieves the existing Custom Field Mapping list setting, for mapping recurring donations
    * fields to opportunities.
    * @return Map<String,npe03__Custom_Field_Mapping__c> Map of existing custom field mapping settings.
    **/
    public static Map<String,npe03__Custom_Field_Mapping__c> getMapCustomFieldMappings() {
        if(mapCustomFieldMappings == null) {
            mapCustomFieldMappings = npe03__Custom_Field_Mapping__c.getAll();
        }
        return mapCustomFieldMappings;
    }

    /*******************************************************************************************************
    * @description Retrieves the existing custom installments list setting, for defining custom recurring
    * donations installments.
    * @return Map<String,npe03__Custom_Installment_Settings__c> Map of existing custom installment settings.
    **/
    public static Map<String,npe03__Custom_Installment_Settings__c> getMapCustomInstallmentSettings() {
        if(mapCustomInstallmentSettings == null) {
            mapCustomInstallmentSettings = npe03__Custom_Installment_Settings__c.getAll();
        }
        return mapCustomInstallmentSettings;
    }

    /*******************************************************************************************************
    * @description Retrieves the existing opportunity naming list setting, for defining custom opportunity
    * naming.
    * @return Map<String,Opportunity_Naming_Settings__c> Map of existing custom opportunity naming settings.
    **/
    public static Map<String,Opportunity_Naming_Settings__c> getMapOppNamingSettings() {
        if(mapOppNamingSettings == null) {
            mapOppNamingSettings = Opportunity_Naming_Settings__c.getAll();
        }
        return mapOppNamingSettings;
    }

    /*******************************************************************************************************
    * @description Retrieves the existing payment field mapping list setting, for mapping opportunity fields
    * to payments.
    * @return Map<String,npe01__Payment_Field_Mapping_Settings__c> Map of existing payment field mapping settings.
    **/
    public static Map<String,npe01__Payment_Field_Mapping_Settings__c> getMapPaymentFieldMappingSettings() {
        if(mapPaymentFieldMappingSettings == null) {
            mapPaymentFieldMappingSettings = npe01__Payment_Field_Mapping_Settings__c.getAll();
        }
        return mapPaymentFieldMappingSettings;
    }

    /*******************************************************************************************************
    * @description Retrieves the existing relationship auto create setting, for automatically generating
    * relationships based on custom lookup fields
    * @return Map<String,npe4__Relationship_Auto_Create__c> Map of existing relationship auto create settings.
    **/
    public static Map<String,npe4__Relationship_Auto_Create__c> getMapRelationshipAutoCreateSettings() {
        if(mapRelationshipAutoCreateSettings == null) {
            mapRelationshipAutoCreateSettings = npe4__Relationship_Auto_Create__c.getAll();
        }
        return mapRelationshipAutoCreateSettings;
    }

    /*******************************************************************************************************
    * @description Retrieves the existing relationship lookup list setting, for providing a table of 
    * gendered and gender-neutral reciprocal relationships for various relationship types.
    * @return Map<String,npe4__Relationship_Lookup__c> Map of existing relationship lookup settings.
    **/
    public static Map<String,npe4__Relationship_Lookup__c> getMapRelationshipLookupSettings() {
        if(mapRelationshipLookupSettings == null) {
            mapRelationshipLookupSettings = npe4__Relationship_Lookup__c.getAll();
        }
        return mapRelationshipLookupSettings;
    }

    /*******************************************************************************************************
    * @description Retrieves the existing user rollup field list setting, creating user defined rollups from
    * opportunity to contact, account and household.
    * @return Map<String,npo02__User_Rollup_Field_Settings__c> Map of existing relationship lookup settings.
    **/
    public static Map<String,npo02__User_Rollup_Field_Settings__c> getMapUserRollupFieldSettings() {
        if(mapUserRollupFieldSettings == null) {
            mapUserRollupFieldSettings = npo02__User_Rollup_Field_Settings__c.getAll();
        }
        return mapUserRollupFieldSettings;
    }
}