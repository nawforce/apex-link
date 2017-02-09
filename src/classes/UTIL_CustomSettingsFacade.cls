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
* @group Utilities
* @description Helper class that retrieves custom settings and sets defaults.
* Needs to be "without sharing" so that the Install Script can run it.
*/
public without sharing class UTIL_CustomSettingsFacade {
    
    //storing user-level custom settings in-memory to avoid trying to isert/update them 
    //twice and thus trigger the "duplicate OwnerId" error
    static npe01__Contacts_And_Orgs_Settings__c contactsSettings;
    static npo02__Households_Settings__c householdsSettings;
    static npe03__Recurring_Donations_Settings__c recurringDonationsSettings;
    static npe4__Relationship_Settings__c relationshipsSettings;
    static npe5__Affiliations_Settings__c affiliationsSettings;
    static Error_Settings__c errorSettings;
    static Batch_Data_Entry_Settings__c bdeSettings;
    static Addr_Verification_Settings__c addressVerificationSettings;
    static Household_Naming_Settings__c householdNamingSettings;
    static Allocations_Settings__c allocationsSettings;
    static Data_Import_Settings__c dataImportSettings;
    
    //storing org-level custom settings in-memory to avoid slowing down the settings page
    static npe01__Contacts_And_Orgs_Settings__c orgContactsSettings;
    static npo02__Households_Settings__c orgHouseholdsSettings;
    static npe03__Recurring_Donations_Settings__c orgRecurringDonationsSettings;
    static npe4__Relationship_Settings__c orgRelationshipsSettings;
    static npe5__Affiliations_Settings__c orgAffiliationsSettings;
    static Error_Settings__c orgErrorSettings;
    static Batch_Data_Entry_Settings__c orgBdeSettings;
    static Addr_Verification_Settings__c orgAddressVerificationSettings;
    static Household_Naming_Settings__c orgHouseholdNamingSettings;
    static Allocations_Settings__c orgAllocationsSettings;
    static Data_Import_Settings__c orgDataImportSettings;

    /*******************************************************************************************************
    * @description Returns the default Contacts and Orgs settings. 
    * @return npe01__Contacts_And_Orgs_Settings__c Custom settings record for Contacts and Orgs configuration. 
    * At the org level, if no user level settings are defined. The ID field should be checked to determine if 
    * the returned record already exists or doesn't exist in the database.      
    */
    public static npe01__Contacts_And_Orgs_Settings__c getContactsSettings() {
        if(Test.isRunningTest() && contactsSettings == null) {
        	contactsSettings = new npe01__Contacts_And_Orgs_Settings__c();
        	configContactsSettings(contactsSettings);
        } else if (contactsSettings == null) {
            contactsSettings = npe01__Contacts_And_Orgs_Settings__c.getInstance();
            if(contactsSettings.Id == null)
                contactsSettings = getOrgContactsSettings();
        }    
        return contactsSettings;
    }
    
    /*******************************************************************************************************
    * @description Returns the default Households settings. 
    * @return npo02__Households_Settings__c Custom settings record for Households configuration. At the org level, 
    * if no user level settings are defined. The ID field should be checked to determine if the returned record 
    * already exists or doesn't exist in the database.
    */
    public static npo02__Households_Settings__c getHouseholdsSettings() {
        if(Test.isRunningTest() && householdsSettings == null) {
            householdsSettings = new npo02__Households_Settings__c();
            configHouseholdSettings(householdsSettings);
        } else if (householdsSettings == null) {
            householdsSettings = npo02__Households_Settings__c.getInstance();
            if(householdsSettings.Id == null)
                householdsSettings = getOrgHouseholdsSettings();
        }  
        return householdsSettings;
    }
    
    /*******************************************************************************************************
    * @description Returns the default Recurring Donations settings. 
    * @return npe03__Recurring_Donations_Settings__c Custom settings record for Recurring Donations configuration. 
    * At the org level, if no user level settings are defined. The ID field should be checked to determine if the 
    * returned record already exists or doesn't exist in the database.
    */
    public static npe03__Recurring_Donations_Settings__c getRecurringDonationsSettings() {
        if(Test.isRunningTest() && recurringDonationsSettings == null) {
        	recurringDonationsSettings = new npe03__Recurring_Donations_Settings__c();
        	configRecurringDonationsSettings(recurringDonationsSettings);
        } else if (recurringDonationsSettings == null) {
            recurringDonationsSettings = npe03__Recurring_Donations_Settings__c.getInstance();
            if(recurringDonationsSettings.Id == null)
                recurringDonationsSettings = getOrgRecurringDonationsSettings();
            else if (recurringDonationsSettings.npe03__Open_Opportunity_Behavior__c == null) {
                configRecurringDonationsSettings(recurringDonationsSettings);
                if(!Test.isRunningTest())
                    upsert recurringDonationsSettings;
            }
        }
        return recurringDonationsSettings;
    }
    
    /*******************************************************************************************************
    * @description Returns the default Relationships settings. 
    * @return npe4__Relationship_Settings__c Custom settings record for Relationships configuration. At the org level, 
    * if no user level settings are defined. The ID field should be checked to determine if the returned record already
    * exists or doesn't exist in the database.
    */
    public static npe4__Relationship_Settings__c getRelationshipSettings() {
        if(Test.isRunningTest() && relationshipsSettings == null) {
            relationshipsSettings = new npe4__Relationship_Settings__c();
            configRelationshipsSettings(relationshipsSettings);
        } else if (relationshipsSettings == null) {
            relationshipsSettings = npe4__Relationship_Settings__c.getInstance();
            if(relationshipsSettings.Id == null)
                relationshipsSettings = getOrgRelationshipSettings();
        }
        return relationshipsSettings;
    }
    
    /*******************************************************************************************************
    * @description Returns the default Affiliations settings. 
    * @return npe5__Affiliations_Settings__c Custom settings record for Affiliations configuration. At the org level, 
    * if no user level settings are defined. The ID field should be checked to determine if the returned record already
    * exists or doesn't exist in the database.
    */
    public static npe5__Affiliations_Settings__c getAffiliationsSettings() {
        if(Test.isRunningTest() && affiliationsSettings == null) {
            affiliationsSettings = new npe5__Affiliations_Settings__c();
            configAffiliationsSettings(affiliationsSettings);
        } else if(affiliationsSettings == null) {      
            affiliationsSettings = npe5__Affiliations_Settings__c.getInstance();
            if(affiliationsSettings.Id == null) 
                affiliationsSettings = getOrgAffiliationsSettings();
        }
        return affiliationsSettings;
    }
    
    /*******************************************************************************************************
    * @description Returns the default Error Handling settings. 
    * @return Error_Settings__c Custom settings record for Error Handling configuration. At the org level, if no user 
    * level settings are defined. The ID field should be checked to determine if the returned record already
    * exists or doesn't exist in the database.
    */
    public static Error_Settings__c getErrorSettings() {
        if(Test.isRunningTest() && errorSettings == null) {
            errorSettings = new Error_Settings__c();
            configErrorSettings(errorSettings, null);
        } else if(errorSettings == null) {
            errorSettings = Error_Settings__c.getInstance();
            if(errorSettings.Id == null)
                errorSettings = getOrgErrorSettings();
        }    
        return errorSettings;
    }
    
    /*******************************************************************************************************
    * @description Returns the default Batch Data Entry settings. 
    * @return Batch_Data_Entry_Settings__c Custom settings record for Batch Data Entry configuration. At the org level, 
    * if no user level settings are defined. The ID field should be checked to determine if the returned record already
    * exists or doesn't exist in the database.
    */
    public static Batch_Data_Entry_Settings__c getBDESettings() {
        if(Test.isRunningTest() && bdeSettings == null) {
            bdeSettings = new Batch_Data_Entry_Settings__c();
            configBDESettings(bdeSettings);
        } else if(bdeSettings == null) {
            bdeSettings = Batch_Data_Entry_Settings__c.getInstance();
            if(bdeSettings.Id == null)
                bdeSettings = getOrgBDESettings();
        }
        return bdeSettings;
    }
    
    /*******************************************************************************************************
    * @description Returns the default Address Verification settings. 
    * @return Addr_Verification_Settings__c Custom settings record for Address Verification configuration. At the org level, 
    * if no user level settings are defined. The ID field should be checked to determine if the returned record already
    * exists or doesn't exist in the database.
    */
    public static Addr_Verification_Settings__c getAddressVerificationSettings() {        
        if(Test.isRunningTest() && addressVerificationSettings == null) {
            addressVerificationSettings = new Addr_Verification_Settings__c();
            configAddressVerificationSettings(addressVerificationSettings);
        } else if(addressVerificationSettings == null) {
            addressVerificationSettings = Addr_Verification_Settings__c.getInstance();
            if(addressVerificationSettings.Id == null)
                addressVerificationSettings = getOrgAddressVerificationSettings();
        }
        return addressVerificationSettings;
    }
    
    /*******************************************************************************************************
    * @description Returns the default Household Naming settings. 
    * @return Household_Naming_Settings__c Custom settings record for Household Naming configuration. At the org level, if 
    * no user level settings are defined. The ID field should be checked to determine if the returned record already
    * exists or doesn't exist in the database.
    */
    public static Household_Naming_Settings__c getHouseholdNamingSettings() {        
        if(Test.isRunningTest() && householdNamingSettings == null) {
            householdNamingSettings = new Household_Naming_Settings__c();
            configHouseholdNamingSettings(householdNamingSettings);
        } else if (householdNamingSettings == null) {
            householdNamingSettings = Household_Naming_Settings__c.getInstance();
            if(householdNamingSettings.Id == null) 
                householdNamingSettings = getOrgHouseholdNamingSettings();
        }
        return householdNamingSettings;
    }
    
    /*******************************************************************************************************
    * @description Returns the default Allocations settings. 
    * @return Allocations_Settings__c Custom settings record for Allocations configuration. At the org level, if no user level
    * settings are defined. The ID field should be checked to determine if the returned record already exists or doesn't exist 
    * in the database.
    */
    public static Allocations_Settings__c getAllocationsSettings() {
        if(Test.isRunningTest() && allocationsSettings == null) {
            allocationsSettings = new Allocations_Settings__c();
            configAllocationsSettings(allocationsSettings);
        } else if (allocationsSettings == null) {
            allocationsSettings = Allocations_Settings__c.getInstance();
            if (allocationsSettings.Id == null) {
                allocationsSettings = getOrgAllocationsSettings();
            }
        }
        return allocationsSettings;
    }
    
    /*******************************************************************************************************
    * @description Returns the default Data Import settings. 
    * @return Data_Import_Settings__c Custom settings record for Data Import configuration. At the org level, if no user level
    * settings are defined. The ID field should be checked to determine if the returned record already exists or doesn't exist 
    * in the database.
    */
    public static Data_Import_Settings__c getDataImportSettings() {        
        if(Test.isRunningTest() && dataImportSettings == null) {
            dataImportSettings = new Data_Import_Settings__c();
            configDataImportSettings(dataImportSettings);
        } else if (dataImportSettings == null) {
            dataImportSettings = Data_Import_Settings__c.getInstance();
            if (dataImportSettings.Id == null) 
                dataImportSettings = getOrgDataImportSettings();
        }
        return dataImportSettings;
    }

    /*******************************************************************************************************
    * @description Returns the org-level Contacts and Orgs settings. Default org-level settings will be created 
    * if none exist. Meant to be called only from settings page and install script.
    * @return npe01__Contacts_And_Orgs_Settings__c Org-level settings for Contacts and Orgs.
    */
    public static npe01__Contacts_And_Orgs_Settings__c getOrgContactsSettings() {
        if(orgContactsSettings == null) {
            orgContactsSettings = npe01__Contacts_And_Orgs_Settings__c.getOrgDefaults();
            if(orgContactsSettings.Id == null) {
                configContactsSettings(orgContactsSettings);
                orgContactsSettings.Setupownerid = UserInfo.getOrganizationId();
                if(!Test.isRunningTest())
                    upsert orgContactsSettings;
            }
        }
        return orgContactsSettings;
    }
    
    /*******************************************************************************************************
    * @description Returns the org-level Households settings. Default org-level settings will be created 
    * if none exist. Meant to be called only from settings page and install script.
    * @return npo02__Households_Settings__c Org-level settings for Households.
    */
    public static npo02__Households_Settings__c getOrgHouseholdsSettings() {
        if(orgHouseholdsSettings == null) {
            orgHouseholdsSettings = npo02__Households_Settings__c.getOrgDefaults();
            if(orgHouseholdsSettings.Id == null) {
                configHouseholdSettings(orgHouseholdsSettings);
                orgHouseholdsSettings.Setupownerid = UserInfo.getOrganizationId();
                if(!Test.isRunningTest())
                    upsert orgHouseholdsSettings;
            }
        }
        return orgHouseholdsSettings;
    }
    
    /*******************************************************************************************************
    * @description Returns the org-level Recurring Donation settings. Default org-level settings will be created 
    * if none exist. Meant to be called only from settings page and install script.
    * @return npe03__Recurring_Donations_Settings__c Org-level settings for Recurring Donations.
    */
    public static npe03__Recurring_Donations_Settings__c getOrgRecurringDonationsSettings() {
        if(orgRecurringDonationsSettings == null) {
            orgRecurringDonationsSettings = npe03__Recurring_Donations_Settings__c.getOrgDefaults();
            if(orgRecurringDonationsSettings.Id == null || orgRecurringDonationsSettings.npe03__Open_Opportunity_Behavior__c == null) {
                configRecurringDonationsSettings(orgRecurringDonationsSettings);
                if(!Test.isRunningTest())
                    upsert orgRecurringDonationsSettings;
            }
        }
        return orgRecurringDonationsSettings;
    }
    
    /*******************************************************************************************************
    * @description Returns the org-level Relationship settings. Default org-level settings will be created 
    * if none exist. Meant to be called only from settings page and install script.
    * @return npe4__Relationship_Settings__c Org-level settings for Relationships.
    */
    public static npe4__Relationship_Settings__c getOrgRelationshipSettings() {
        if(orgRelationshipsSettings == null) { 
            orgRelationshipsSettings = npe4__Relationship_Settings__c.getOrgDefaults();
            if(orgRelationshipsSettings.Id == null) {
                configRelationshipsSettings(orgRelationshipsSettings);
                if(!Test.isRunningTest())
                    upsert orgRelationshipsSettings;
            }
        }
        return orgRelationshipsSettings;
    }
    
    /*******************************************************************************************************
    * @description Returns the org-level Affiliation settings. Default org-level settings will be created 
    * if none exist. Meant to be called only from settings page and install script.
    * @return npe5__Affiliations_Settings__c Org-level settings for Affiliations.
    */
    public static npe5__Affiliations_Settings__c getOrgAffiliationsSettings() {
        if(orgAffiliationsSettings == null) {
            orgAffiliationsSettings = npe5__Affiliations_Settings__c.getOrgDefaults();
            if(orgAffiliationsSettings.Id == null) {
                configAffiliationsSettings(orgAffiliationsSettings);
                if(!Test.isRunningTest())
                    upsert orgAffiliationsSettings;
            }
        }
        return orgAffiliationsSettings;
    }
    
    /*******************************************************************************************************
    * @description Returns the org-level Error settings. Default org-level settings will be created 
    * if none exist. Meant to be called only from settings page and install script.
    * @return Error_Settings__c Org-level settings for Errors.
    */
    public static Error_Settings__c getOrgErrorSettings() {
        if(orgErrorSettings == null) {
            orgErrorSettings = Error_Settings__c.getOrgDefaults();
            if(orgErrorSettings.Id == null) {
                configErrorSettings(orgErrorSettings, null);
                if(!Test.isRunningTest())
                    upsert orgErrorSettings;
            }
        }
        return orgErrorSettings;
    }
    
    /*******************************************************************************************************
    * @description Returns the org-level Batch Data Entry settings. Default org-level settings will be created 
    * if none exist. Meant to be called only from settings page and install script.
    * @return Batch_Data_Entry_Settings__c Org-level settings for Batch Data Entry.
    */
    public static Batch_Data_Entry_Settings__c getOrgBDESettings() {
        if(orgBDESettings == null) {
            orgBDESettings = Batch_Data_Entry_Settings__c.getOrgDefaults();
            if(orgBDESettings.Id == null) {
                configBDESettings(orgBDESettings);
                if(!Test.isRunningTest())
                    upsert orgBDESettings;
            }
        }
        return orgBDESettings;
    }
    
    /*******************************************************************************************************
    * @description Returns the org-level Address Verification settings. Default org-level settings will be created 
    * if none exist. Meant to be called only from settings page and install script.
    * @return Addr_Verification_Settings__c Org-level settings for Address Verification.
    */
    public static Addr_Verification_Settings__c getOrgAddressVerificationSettings() {
        if(orgAddressVerificationSettings == null) {
            orgAddressVerificationSettings = Addr_Verification_Settings__c.getOrgDefaults();
            if(orgAddressVerificationSettings.Id == null) {
                configAddressVerificationSettings(orgAddressVerificationSettings);
                if(!Test.isRunningTest())
                    upsert orgAddressVerificationSettings;
            }
        }
        return orgAddressVerificationSettings;
    }
    
    /*******************************************************************************************************
    * @description Returns the org-level Household Naming settings. Default org-level settings will be created 
    * if none exist. Meant to be called only from settings page and install script.
    * @return Household_Naming_Settings__c Org-level settings for Household Naming.
    */
    public static Household_Naming_Settings__c getOrgHouseholdNamingSettings() {
        if(orgHouseholdNamingSettings == null) {
            orgHouseholdNamingSettings = Household_Naming_Settings__c.getOrgDefaults();
            if(orgHouseholdNamingSettings.Id == null) {
                configHouseholdNamingSettings(orgHouseholdNamingSettings);
                if(!Test.isRunningTest())
                    upsert orgHouseholdNamingSettings;
            }
        }
        return orgHouseholdNamingSettings;
    }
    
    /*******************************************************************************************************
    * @description Returns the org-level Allocation settings. Default org-level settings will be created 
    * if none exist. Meant to be called only from settings page and install script.
    * @return Allocations_Settings__c Org-level settings for Allocations.
    */
    public static Allocations_Settings__c getOrgAllocationsSettings() {
        if (orgAllocationsSettings==null) {
            orgAllocationsSettings = Allocations_Settings__c.getOrgDefaults();
            if (orgAllocationsSettings.Id == null) {
                configAllocationsSettings(orgAllocationsSettings);
                if(!Test.isRunningTest())
                    upsert orgAllocationsSettings;
            }
        }
        return orgAllocationsSettings;
    }
    
    /*******************************************************************************************************
    * @description Returns the org-level Data Import settings. Default org-level settings will be created 
    * if none exist. Meant to be called only from settings page and install script.
    * @return Data_Import_Settings__c Org-level settings for Data Import.
    */
    public static Data_Import_Settings__c getOrgDataImportSettings() {
        if (orgDataImportSettings == null) {
            orgDataImportSettings = Data_Import_Settings__c.getOrgDefaults();
            if (orgDataImportSettings.Id == null) {
                configDataImportSettings(orgDataImportSettings);
                if(!Test.isRunningTest()) {
                    upsert orgDataImportSettings;
                }
            }
        }
        return orgDataImportSettings;
    }

    /*******************************************************************************************************
     * Private methods that will configure the default org-level custom settings.
     **/
    private static void configContactsSettings(npe01__Contacts_And_Orgs_Settings__c cs) {
        //this setting does nothing, but needs to be extracted from the tests before removing
        cs.npe01__Enable_Opportunity_Contact_Role_Trigger__c = false;
        cs.npe01__Payments_Enabled__c  = true;
        cs.npe01__Opportunity_Contact_Role_Default_role__c = 'Donor';
        cs.npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR;
        cs.Opp_RecTypes_Excluded_for_Payments__c = null;
        cs.Opp_Types_Excluded_for_Payments__c = null;
        Recordtypeinfo householdAccount = Schema.SObjectType.Account.getRecordTypeInfosByName().get(CAO_Constants.HH_ACCOUNT_PROCESSOR);
        if(householdAccount != null)
            cs.npe01__HH_Account_RecordTypeID__c = householdAccount.getRecordTypeId();
    }

    private static void configHouseholdSettings(npo02__Households_Settings__c hs) {
        String oldProcessor = '';
        //modified to check again the Id instead of the object
        //get the model they used to be in 
        Schema.DescribeFieldResult F = Schema.sObjectType.Contact.fields.npo02__SystemHouseholdProcessor__c; 
        List<Schema.PicklistEntry> P = F.getPicklistValues();
        
        for(Schema.PicklistEntry pe : P){
            if (pe.isDefaultValue()){
                if(pe.getValue() == HH_Households.ALL_PROCESSOR){
                    oldProcessor = HH_Households.ALL_PROCESSOR;
                } else if(pe.getValue() == HH_Households.ALL_INDIVIDUALS_PROCESSOR){
                    oldProcessor = HH_Households.ALL_INDIVIDUALS_PROCESSOR;
                } else if(pe.getValue() == HH_Households.NO_HOUSEHOLDS_PROCESSOR){
                    oldProcessor = HH_Households.NO_HOUSEHOLDS_PROCESSOR;
                }
                break;
            }
        }
        //set them up with their old setting in the new settings object
        if(oldProcessor == HH_Households.ALL_PROCESSOR){
            hs.npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR;
        } else if(oldProcessor == HH_Households.ALL_INDIVIDUALS_PROCESSOR){
            hs.npo02__Household_Rules__c = HH_Households.ALL_INDIVIDUALS_PROCESSOR;
        } else if(oldProcessor == HH_Households.NO_HOUSEHOLDS_PROCESSOR){
            hs.npo02__Household_Rules__c = HH_Households.NO_HOUSEHOLDS_PROCESSOR;
        } else {
            hs.npo02__Household_Rules__c = HH_Households.NO_HOUSEHOLDS_PROCESSOR;
        }
 
        hs.npo02__Household_Member_Contact_Role__c = label.npo02.Household_Member_Contact_Role;
        hs.npo02__Always_Rollup_to_Primary_Contact__c = false;
        hs.npo02__Enable_Opp_Rollup_Triggers__c = true;
        hs.npo02__Excluded_Account_Opp_Rectypes__c = null;
        hs.npo02__Excluded_Account_Opp_Types__c = null;
        hs.npo02__Excluded_Contact_Opp_Rectypes__c = null;
        hs.npo02__Excluded_Contact_Opp_Types__c = null;
        hs.npo02__Membership_Record_Types__c = null;
        hs.npo02__Rollup_N_Day_Value__c = 365;
        hs.npo02__Membership_Grace_Period__c = 30;
        hs.npo02__Advanced_Household_Naming__c = true;
        hs.npo02__Async_Household_Naming__c = false;
        hs.npo02__Schedule_Job_Limit__c = 25;
        hs.npo02__Household_OCR_Excluded_Recordtypes__c = null;
        hs.npo02__Household_Creation_Excluded_Recordtypes__c = null;
        hs.npo02__Enable_Soft_Credit_Rollups__c = true;
        hs.npo02__Soft_Credit_Roles__c = 'Matched Donor;Soft Credit;Household Member';
        hs.Matched_Donor_Role__c = 'Matched Donor';
        hs.npo02__Household_Contact_Roles_On__c = true;
                
        // new HH Account model overrides having HH objects
        if (contactsSettings != null && contactsSettings.npe01__Account_Processor__c == CAO_Constants.HH_ACCOUNT_PROCESSOR)
            hs.npo02__Household_Rules__c = HH_Households.NO_HOUSEHOLDS_PROCESSOR;
    }
    
    private static void configRecurringDonationsSettings(npe03__Recurring_Donations_Settings__c rds) {
        //if the save behavior is null, then we'll need to upsert new settings, otherwise, we have valid settings as its 
        //the only field w/o a default defined
        if (rds.npe03__Open_Opportunity_Behavior__c == null) {
            rds.npe03__Add_Campaign_to_All_Opportunites__c = true;
            rds.npe03__Enable_Update_Check__c = true;
            //rds.npe03__Error_Email_Notifications__c = 'All Sys Admins'; *This field has been moved to the ERR_Settings__c custom setting
            rds.npe03__Maximum_Donations__c = 50;
            rds.npe03__Opportunity_Forecast_Months__c = 12;
            rds.npe03__Update_Check_Interval__c = 90;
            rds.npe03__Open_Opportunity_Behavior__c = RD_RecurringDonations.RecurringDonationCloseOptions.Mark_Opportunities_Closed_Lost.name();
            List<RecordType> oppRecordTypes = [select id from RecordType where sobjecttype = 'Opportunity'];
            if(oppRecordTypes.size() > 0)
                rds.npe03__Record_Type__c = oppRecordTypes[0].id;
        }   
    }

    private static void configRelationshipsSettings(npe4__Relationship_Settings__c rs) {
        rs.npe4__Reciprocal_Method__c = 'List Setting';
    }
    
    private static void configAffiliationsSettings(npe5__Affiliations_Settings__c afs) {
        afs.npe5__Automatic_Affiliation_Creation_Turned_On__c = true;
    }

    private static void configErrorSettings(Error_Settings__c es, String notificationsTo) {   
        es.Store_Errors_On__c = true;
        es.Error_Notifications_On__c = true;
        es.Error_Notifications_To__c = ERR_Notifier.NotificationOptions.sysAdmins;
        es.Disable_Error_Handling__c = false;
        es.Enable_Debug__c = false;
    }
    
    private static void configBDESettings(Batch_Data_Entry_Settings__c bds) {
        bds.Allow_Blank_Opportunity_Names__c = true;
        bds.Opportunity_Naming__c = true;
    }

    private static void configAddressVerificationSettings(Addr_Verification_Settings__c avs) {
        avs.Enable_Automatic_Verification__c = false;
        avs.Reject_Ambiguous_Addresses__c = false;
    }
    
    private static void configHouseholdNamingSettings(Household_Naming_Settings__c hns) {
        hns.Household_Name_Format__c = '{!LastName} ' + label.npo02.DefaultHouseholdName;
        hns.Formal_Greeting_Format__c = '{!{!Salutation} {!FirstName}} {!LastName}';
        hns.Informal_Greeting_Format__c = '{!{!FirstName}}';
        hns.Name_Connector__c = label.npo02.HouseholdNameConnector;
        hns.Name_Overrun__c = label.npo02.HouseholdNameOverrun;
        hns.Contact_Overrun_Count__c = 9;
        hns.Implementing_Class__c = 'HH_NameSpec';
    }
    
    public static void configAllocationsSettings(Allocations_Settings__c allosettings) {
        //disable allocations by default
        allosettings.Default_Allocations_Enabled__c = false;
        allosettings.Default__c = null;
        allosettings.Excluded_Opp_RecTypes__c = null;
        allosettings.Excluded_Opp_Types__c = null;
        allosettings.Rollup_N_Day_Value__c = 365;
        allosettings.Use_Fiscal_Year_for_Rollups__c = false;
    }
    
    private static void configDataImportSettings(Data_Import_Settings__c dis) {
        dis.Batch_Size__c = 50;
        dis.Contact_Matching_Rule__c = 'Firstname,Lastname,Email';
    }

    /*******************************************************************************************************
    * @description Creates instance of settings to use in tests. It does not insert it, but all other methods will see these settings
    * as the configured settings.
    * @parameter mySettings Settings instance with the values to set.
    * @return npe01__Contacts_And_Orgs_Settings__c The configured settings.
    **/
    public static npe01__Contacts_And_Orgs_Settings__c getContactsSettingsForTests(npe01__Contacts_And_Orgs_Settings__c mySettings) {
        if(contactsSettings == null)
            contactsSettings = new npe01__Contacts_And_Orgs_Settings__c();
        
        //create our own based on what's passed in from the test
        contactsSettings.npe01__One_to_One_RecordTypeID__c = mySettings.npe01__One_to_One_RecordTypeID__c;
        contactsSettings.npe01__HH_Account_RecordTypeID__c = mySettings.npe01__HH_Account_RecordTypeID__c;
        contactsSettings.npe01__Account_Processor__c = mySettings.npe01__Account_Processor__c;
        contactsSettings.npe01__Enable_Opportunity_Contact_Role_Trigger__c = mySettings.npe01__Enable_Opportunity_Contact_Role_Trigger__c;
        contactsSettings.npe01__Opportunity_Contact_Role_Default_role__c = mySettings.npe01__Opportunity_Contact_Role_Default_role__c;
        contactsSettings.npe01__Disable_Account_Model_Trigger__c = mySettings.npe01__Disable_Account_Model_Trigger__c;
        contactsSettings.npe01__Payments_Enabled__c = mySettings.npe01__Payments_Enabled__c;
        contactsSettings.Opp_RecTypes_Excluded_for_Payments__c = mySettings.Opp_RecTypes_Excluded_for_Payments__c;
        contactsSettings.Opp_Types_Excluded_for_Payments__c = mySettings.Opp_Types_Excluded_for_Payments__c;
        contactsSettings.Automatic_Campaign_Member_Management__c = mySettings.Automatic_Campaign_Member_Management__c;
        contactsSettings.Campaign_Member_Responded_Status__c = mySettings.Campaign_Member_Responded_Status__c;
        contactsSettings.Campaign_Member_Non_Responded_Status__c = mySettings.Campaign_Member_Non_Responded_Status__c;
        contactsSettings.Organizational_Account_Addresses_Enabled__c = mySettings.Organizational_Account_Addresses_Enabled__c;
        contactsSettings.Simple_Address_Change_Treated_as_Update__c = mySettings.Simple_Address_Change_Treated_as_Update__c;
        contactsSettings.Household_Account_Addresses_Disabled__c = mySettings.Household_Account_Addresses_Disabled__c;
        contactsSettings.Payments_Auto_Close_Stage_Name__c = mySettings.Payments_Auto_Close_Stage_Name__c;
        contactsSettings.Honoree_Opportunity_Contact_Role__c = mySettings.Honoree_Opportunity_Contact_Role__c;
        contactsSettings.Notification_Recipient_Opp_Contact_Role__c = mySettings.Notification_Recipient_Opp_Contact_Role__c;
        orgContactsSettings = contactsSettings;
        return contactsSettings;
    }
    
    /*******************************************************************************************************
    * @description Creates instance of settings to use in tests. It does not insert it, but all other methods will see these settings
    * as the configured settings.
    * @parameter mySettings Settings instance with the values to set.
    * @return npo02__Households_Settings__c The configured settings.
    **/
    public static npo02__Households_Settings__c getHouseholdsSettingsForTests(npo02__Households_Settings__c mySettings) {
        if(householdsSettings == null)
            householdsSettings = new npo02__Households_Settings__c();
            
        //create our own based on what's passed in from the test
        householdsSettings.npo02__Household_Rules__c = mySettings.npo02__Household_Rules__c;
        householdsSettings.npo02__Household_Contact_Roles_On__c = mySettings.npo02__Household_Contact_Roles_On__c;
        householdsSettings.npo02__Household_Member_Contact_Role__c = mySettings.npo02__Household_Member_Contact_Role__c;
        householdsSettings.npo02__Always_Rollup_to_Primary_Contact__c = mySettings.npo02__Always_Rollup_to_Primary_Contact__c;
        householdsSettings.npo02__Enable_Opp_Rollup_Triggers__c = mySettings.npo02__Enable_Opp_Rollup_Triggers__c;
        householdsSettings.npo02__Excluded_Account_Opp_Rectypes__c = mySettings.npo02__Excluded_Account_Opp_Rectypes__c;
        householdsSettings.npo02__Excluded_Account_Opp_Types__c = mySettings.npo02__Excluded_Account_Opp_Types__c;
        householdsSettings.npo02__Excluded_Contact_Opp_Rectypes__c = mySettings.npo02__Excluded_Contact_Opp_Rectypes__c;
        householdsSettings.npo02__Excluded_Contact_Opp_Types__c = mySettings.npo02__Excluded_Contact_Opp_Types__c;
        householdsSettings.npo02__Membership_Grace_Period__c = mySettings.npo02__Membership_Grace_Period__c;
        householdsSettings.npo02__Rollup_N_Day_Value__c = mySettings.npo02__Rollup_N_Day_Value__c;
        householdsSettings.npo02__Membership_Record_Types__c = mySettings.npo02__Membership_Record_Types__c;
        householdsSettings.npo02__Advanced_Household_Naming__c = mySettings.npo02__Advanced_Household_Naming__c;
        householdsSettings.npo02__Enable_Soft_Credit_Rollups__c = mySettings.npo02__Enable_Soft_Credit_Rollups__c;
        householdsSettings.npo02__Soft_Credit_Roles__c = mySettings.npo02__Soft_Credit_Roles__c;
        householdsSettings.Matched_Donor_Role__c = mySettings.Matched_Donor_Role__c;
        householdsSettings.npo02__Household_OCR_Excluded_Recordtypes__c = mySettings.npo02__Household_OCR_Excluded_Recordtypes__c; 
        householdsSettings.npo02__Household_Creation_Excluded_Recordtypes__c = mySettings.npo02__Household_Creation_Excluded_Recordtypes__c;
        householdsSettings.npo02__Household_Mailing_List_ID__c = mySettings.npo02__Household_Mailing_List_ID__c;
        householdsSettings.npo02__Schedule_Job_Limit__c = 25;
        orgHouseholdsSettings = householdsSettings;
 
        // if the caller explicitly is testing Household objects, make sure we have HH Accounts turned off.
        if (householdsSettings != null && householdsSettings.npo02__Household_Rules__c != HH_Households.NO_HOUSEHOLDS_PROCESSOR) {
            if (contactsSettings != null && contactsSettings.npe01__Account_Processor__c == CAO_Constants.HH_ACCOUNT_PROCESSOR) {
                contactsSettings.npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR;
            }   
        }
        return householdsSettings;
    }
    
    /*******************************************************************************************************
    * @description Creates instance of settings to use in tests. It does not insert it, but all other methods will see these settings
    * as the configured settings.
    * @parameter mySettings Settings instance with the values to set.
    * @return npe4__Relationship_Settings__c The configured settings.
    **/
    public static npe4__Relationship_Settings__c getRelationshipsSettingsForTests(npe4__Relationship_Settings__c mySettings) {
        if(relationshipsSettings == null)
            relationshipsSettings = new npe4__Relationship_Settings__c();
        
        relationshipsSettings.npe4__Reciprocal_Method__c = mySettings.npe4__Reciprocal_Method__c;
        relationshipsSettings.npe4__Gender_Field__c = mySettings.npe4__Gender_Field__c;
        orgRelationshipsSettings = relationshipsSettings;
        return relationshipsSettings;
    }
    
    /*******************************************************************************************************
    * @description Creates instance of settings to use in tests. It does not insert it, but all other methods will see these settings
    * as the configured settings.
    * @parameter mySettings Settings instance with the values to set.
    * @return npe5__Affiliations_Settings__c The configured settings.
    **/
    public static npe5__Affiliations_Settings__c getAffiliationsSettingsForTests(npe5__Affiliations_Settings__c mySettings) {
        if(affiliationsSettings == null)
           affiliationsSettings = new npe5__Affiliations_Settings__c();
           
        affiliationsSettings.npe5__Automatic_Affiliation_Creation_Turned_On__c = mySettings.npe5__Automatic_Affiliation_Creation_Turned_On__c;   
        orgAffiliationsSettings = affiliationsSettings;
        return affiliationsSettings;
    }
    
    /*******************************************************************************************************
    * @description Creates instance of settings to use in tests. It does not insert it, but all other methods will see these settings
    * as the configured settings.
    * @parameter mySettings Settings instance with the values to set.
    * @return Error_Settings__c The configured settings.
    **/
    public static Error_Settings__c getErrorSettingsForTests(Error_Settings__c mySettings) {
        if(errorSettings == null)
           errorSettings = new Error_Settings__c();
           
        errorSettings.Error_Notifications_On__c = mySettings.Error_Notifications_On__c;   
        errorSettings.Error_Notifications_To__c = mySettings.Error_Notifications_To__c;
        errorSettings.Store_Errors_On__c = mySettings.Store_Errors_On__c;
        orgErrorSettings = errorSettings;
        return errorSettings;  
    }

    /*******************************************************************************************************
    * @description Creates instance of settings to use in tests. It does not insert it, but all other methods will see these settings
    * as the configured settings.
    * @parameter mySettings Settings instance with the values to set.
    * @return Allocations_Settings__c The configured settings.
    **/
    public static Allocations_Settings__c getAllocationsSettingsForTests(Allocations_Settings__c mySettings) {
        if (allocationsSettings == null)
            allocationsSettings = new Allocations_Settings__c();

        allocationsSettings.Default_Allocations_Enabled__c = mySettings.Default_Allocations_Enabled__c;
        allocationsSettings.Default__c = mySettings.Default__c;
        allocationsSettings.Excluded_Opp_RecTypes__c = mySettings.Excluded_Opp_RecTypes__c;
        allocationsSettings.Excluded_Opp_Types__c = mySettings.Excluded_Opp_Types__c;
        allocationsSettings.Rollup_N_Day_Value__c = mySettings.Rollup_N_Day_Value__c;
        allocationsSettings.Use_Fiscal_Year_for_Rollups__c = mySettings.Use_Fiscal_Year_for_Rollups__c;
        orgAllocationsSettings = allocationsSettings;
        return allocationsSettings;
    }
    
    /*******************************************************************************************************
    * @description Creates instance of settings to use in tests. It does not insert it, but all other methods will see these settings
    * as the configured settings.
    * @parameter mySettings Settings instance with the values to set.
    * @return npe03__Recurring_Donations_Settings__c The configured settings.
    **/        
    public static npe03__Recurring_Donations_Settings__c getRecurringDonationsSettingsForTest(npe03__Recurring_Donations_Settings__c mySettings) {
        if (recurringDonationsSettings == null)
            recurringDonationsSettings = new npe03__Recurring_Donations_Settings__c();
            
        recurringDonationsSettings.npe03__Opportunity_Forecast_Months__c = mySettings.npe03__Opportunity_Forecast_Months__c;
        recurringDonationsSettings.npe03__Maximum_Donations__c = mySettings.npe03__Maximum_Donations__c;
        recurringDonationsSettings.npe03__Open_Opportunity_Behavior__c = mySettings.npe03__Open_Opportunity_Behavior__c;
        recurringDonationsSettings.npe03__Add_Campaign_to_All_Opportunites__c = mySettings.npe03__Add_Campaign_to_All_Opportunites__c;
        recurringDonationsSettings.npe03__Error_Email_Notifications__c = mySettings.npe03__Error_Email_Notifications__c;  
        orgRecurringDonationsSettings = recurringDonationsSettings;
        return recurringDonationsSettings;
    }
    
    /*******************************************************************************************************
    * @description Creates instance of settings to use in tests. It does not insert it, but all other methods will see these settings
    * as the configured settings.
    * @parameter mySettings Settings instance with the values to set.
    * @return Household_Naming_Settings__c The configured settings.
    **/
    public static Household_Naming_Settings__c getHouseholdNamingSettingsForTests(Household_Naming_Settings__c mySettings) {
      if(householdNamingSettings == null)
        householdNamingSettings = new Household_Naming_Settings__c();

      householdNamingSettings.Household_Name_Format__c = mySettings.Household_Name_Format__c;
      householdNamingSettings.Formal_Greeting_Format__c = mySettings.Formal_Greeting_Format__c;
      householdNamingSettings.Informal_Greeting_Format__c = mySettings.Informal_Greeting_Format__c;
      householdNamingSettings.Name_Connector__c = mySettings.Name_Connector__c;
      householdNamingSettings.Name_Overrun__c = mySettings.Name_Overrun__c;
      householdNamingSettings.Contact_Overrun_Count__c = mySettings.Contact_Overrun_Count__c;
      householdNamingSettings.Implementing_Class__c = mySettings.Implementing_Class__c;

      orgHouseholdNamingSettings = householdNamingSettings;
      return householdNamingSettings;
    }

}