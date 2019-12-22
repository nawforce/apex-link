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
* @group API
* @group-content ../../ApexDocContent/API.htm
* @description API class that exposes the Custom Settings configuration.
*/
global class UTIL_CustomSettings_API {

    /*******************************************************************************************************
    * @description Returns the default Contacts and Orgs settings. 
    * @return npe01__Contacts_And_Orgs_Settings__c Custom settings record for Contacts and Orgs configuration. 
    * At the org level, if no user level settings are defined. The ID field should be checked to determine if 
    * the returned record already exists or doesn't exist in the database.   
    */
    global static npe01__Contacts_And_Orgs_Settings__c getContactsSettings() {
        return UTIL_CustomSettingsFacade.getContactsSettings();
    }
    
    /*******************************************************************************************************
    * @description Returns the default Households settings. 
    * @return npo02__Households_Settings__c Custom settings record for Households configuration. At the org level, 
    * if no user level settings are defined. The ID field should be checked to determine if the returned record 
    * already exists or doesn't exist in the database.
    */
    global static npo02__Households_Settings__c getHouseholdsSettings() {
        return UTIL_CustomSettingsFacade.getHouseholdsSettings();
    }
    
    /*******************************************************************************************************
    * @description Returns the default Recurring Donations settings. 
    * @return npe03__Recurring_Donations_Settings__c Custom settings record for Recurring Donations configuration. 
    * At the org level, if no user level settings are defined. The ID field should be checked to determine if the 
    * returned record already exists or doesn't exist in the database.
    */
    global static npe03__Recurring_Donations_Settings__c getRecurringDonationsSettings() {
        return UTIL_CustomSettingsFacade.getRecurringDonationsSettings();
    }
    
    /*******************************************************************************************************
    * @description Returns the default Relationships settings. 
    * @return npe4__Relationship_Settings__c Custom settings record for Relationships configuration. At the org level, 
    * if no user level settings are defined. The ID field should be checked to determine if the returned record already
    * exists or doesn't exist in the database.
    */
    global static npe4__Relationship_Settings__c getRelationshipSettings() {
        return UTIL_CustomSettingsFacade.getRelationshipSettings();
    }
    
    /*******************************************************************************************************
    * @description Returns the default Affiliations settings. 
    * @return npe5__Affiliations_Settings__c Custom settings record for Affiliations configuration. At the org level, 
    * if no user level settings are defined. The ID field should be checked to determine if the returned record already
    * exists or doesn't exist in the database.
    */
    global static npe5__Affiliations_Settings__c getAffiliationsSettings() {
        return UTIL_CustomSettingsFacade.getAffiliationsSettings();
    }
    
    /*******************************************************************************************************
    * @description Returns the default Error Handling settings. 
    * @return Error_Settings__c Custom settings record for Error Handling configuration. At the org level, if no user 
    * level settings are defined. The ID field should be checked to determine if the returned record already
    * exists or doesn't exist in the database.
    */
    global static Error_Settings__c getErrorSettings() {
        return UTIL_CustomSettingsFacade.getErrorSettings();
    }
    
    /*******************************************************************************************************
    * @description Returns the default Batch Data Entry settings. 
    * @return Batch_Data_Entry_Settings__c Custom settings record for Batch Data Entry configuration. At the org level, 
    * if no user level settings are defined. The ID field should be checked to determine if the returned record already
    * exists or doesn't exist in the database.
    */
    global static Batch_Data_Entry_Settings__c getBDESettings() {
        return UTIL_CustomSettingsFacade.getBDESettings();
    }
    
    /*******************************************************************************************************
    * @description Returns the default Address Verification settings. 
    * @return Addr_Verification_Settings__c Custom settings record for Address Verification configuration. At the org level, 
    * if no user level settings are defined. The ID field should be checked to determine if the returned record already
    * exists or doesn't exist in the database.
    *
    *
    * NOTE: this method should not be exposed because these settings are protected, and are not therefore 
          accessible by managed or unmanaged code outside the package.
    
    global static Addr_Verification_Settings__c getAddressVerificationSettings() {
        return UTIL_CustomSettingsFacade.getAddressVerificationSettings();
    }*/
    
    /*******************************************************************************************************
    * @description Returns the default Household Naming settings. 
    * @return Household_Naming_Settings__c Custom settings record for Household Naming configuration. At the org level, if 
    * no user level settings are defined. The ID field should be checked to determine if the returned record already
    * exists or doesn't exist in the database.
    */
    global static Household_Naming_Settings__c getHouseholdNamingSettings() {
        return UTIL_CustomSettingsFacade.getHouseholdNamingSettings();
    }
    
    /*******************************************************************************************************
    * @description Returns the default Allocations settings. 
    * @return Allocations_Settings__c Custom settings record for Allocations configuration. At the org level, if no user level
    * settings are defined. The ID field should be checked to determine if the returned record already exists or doesn't exist 
    * in the database.
    */
    global static Allocations_Settings__c getAllocationsSettings() {
        return UTIL_CustomSettingsFacade.getAllocationsSettings();
    }
    
    /*******************************************************************************************************
    * @description Returns the default Data Import settings. 
    * @return Data_Import_Settings__c Custom settings record for Data Import configuration. At the org level, if no user level
    * settings are defined. The ID field should be checked to determine if the returned record already exists or doesn't exist 
    * in the database.
    */
    global static Data_Import_Settings__c getDataImportSettings() {
    	return UTIL_CustomSettingsFacade.getDataImportSettings();
    }
}