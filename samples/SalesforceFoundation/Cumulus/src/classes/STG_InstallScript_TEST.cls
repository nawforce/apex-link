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
* @group Settings
* @description Test Class for the Install Script for the NPSP package. 
*/
@isTest
public with sharing class STG_InstallScript_TEST {

    // if you only want to run one test in this class, fill in its name here.
    // if you want to run all tests, then use '*'
    private static string strTestOnly = '*';
    
    /** NPSP to TDTM test - to verify no exception is thrown if the custom settings don't exist **/
    public testmethod static void mappingsTest_runScriptNoCustomSettings() {
        if (strTestOnly != '*' && strTestOnly != 'mappingsTest_runScriptNoCustomSettings') return;
               
        //Don't create NPSP custom settings
        
        //Run the install script
        Test.testInstall(new STG_InstallScript(), null);
        
        //Verify all settings created
        verifyAllSettingsCreated();
    }
    
    /** Tests that missing trigger handlers get created **/
    public static testmethod void missingSettingsCreated() {
        if (strTestOnly != '*' && strTestOnly != 'missingSettingsCreated') return;
        
        //Create some of the default settings
        createSomeDefaultSettings();
        
        //Run the install script               
        Test.testInstall(new STG_InstallScript(), null);
        
        //Verify all settings created
        verifyAllSettingsCreated();
    }

    /** Tests that deleted class trigger handlers get deleted **/
    public static testmethod void deletedHandlers() {
        if (strTestOnly != '*' && strTestOnly != 'deletedHandlers') return;
        
        //Create some of the default settings
        createSomeDefaultSettings();

        // HH Object contact trigger support
        insert new Trigger_Handler__c(Active__c = true, Asynchronous__c = false, 
              Class__c = 'OPP_OpportunityNaming_TDTM', Load_Order__c = 0, Object__c = 'Opportunity', 
              Trigger_Action__c = 'AfterInsert;AfterUpdate;AfterDelete');        

        //Run the install script               
        Test.testInstall(new STG_InstallScript(), null);
        
        //Verify all settings created
        verifyAllSettingsCreated();
    }
    
    public testmethod static void push() {
        if (strTestOnly != '*' && strTestOnly != 'push') return;
        
        //Create some of the default settings
        createSomeDefaultSettings();
        
        //Run the install script               
        Test.testInstall(new STG_InstallScript(), new Version(1,0), true);
        
        //Verify all settings created
        verifyAllSettingsCreated();
    }
    
    public testmethod static void upgrade() {
        if (strTestOnly != '*' && strTestOnly != 'upgrade') return;
        
        //Create some of the default settings
        createSomeDefaultSettings();
        
        //Run the install script               
        Test.testInstall(new STG_InstallScript(), new Version(1,0), false);
        
        //Verify all settings created
        verifyAllSettingsCreated();
    }
    
    /** NPSP to TDTM test - If it's the first time we install Cumulus and there was no custom DISABLE flag enabled **/
    public testmethod static void mappingsTest_runScriptNoCustomConfigOnInstall() {
        if (strTestOnly != '*' && strTestOnly != 'mappingsTest_runScriptNoCustomConfigOnInstall') return;
         
        //Create NPSP custom settings with all disable flags off
        setAllNpspFlags(false);
               
        Test.testInstall(new STG_InstallScript(), null);
        
        List<Trigger_Handler__c> afterScriptConfig = [select Active__c, Asynchronous__c, Class__c, Load_Order__c, 
            Object__c, Trigger_Action__c from Trigger_Handler__c];
        
        //Using the API class just to give it test coverage.      
        List<Trigger_Handler__c> defaultConfig = TDTM_Config_API.getDefaultRecords();
        
        assertTriggerHandlersEqual(afterScriptConfig, defaultConfig);
    }
    
    private static void assertTriggerHandlersEqual(List<Trigger_Handler__c> afterScriptConfig, List<Trigger_Handler__c> defaultConfig) {
        afterScriptConfig.sort();
        defaultConfig.sort();
        
        System.assertEquals(defaultConfig.size(), afterScriptConfig.size(), 'Number of created trigger handlers should match the number of handlers in TDTM_DefaultConfig.');
        for(Integer i = 0; i < defaultConfig.size(); i++) {
            System.assertEquals(afterScriptConfig[i].Class__c, defaultConfig[i].Class__c);
            System.assertEquals(afterScriptConfig[i].Load_Order__c, defaultConfig[i].Load_Order__c);
            System.assertEquals(afterScriptConfig[i].Object__c, defaultConfig[i].Object__c);
            System.assertEquals(afterScriptConfig[i].Active__c, defaultConfig[i].Active__c);
            System.assertEquals(afterScriptConfig[i].Asynchronous__c, defaultConfig[i].Asynchronous__c);
            //split and sort trigger actions so the test doesn't fail for different orders
            List<String> afterTriggerAction = afterScriptConfig[i].Trigger_Action__c.split(';');
            List<String> defaultTriggerAction = defaultConfig[i].Trigger_Action__c.split(';');
            afterTriggerAction.sort();
            defaultTriggerAction.sort();
            System.assertEquals(afterTriggerAction, defaultTriggerAction);
        }
    }
    
    /** NPSP to TDTM test - If it's the first time we install Cumulus and all the custom DISABLE flag were enabled **/
    public testmethod static void mappingsTest_runScriptCustomConfigOnInstallAllFlags() {
        if (strTestOnly != '*' && strTestOnly != 'mappingsTest_runScriptCustomConfigOnInstallAllFlags') return;
        
        //Create NPSP custom settings with all disable flags on
        setAllNpspFlags(true);

        Test.testInstall(new STG_InstallScript(), null);
        
        //Get the TDTM classes that map to the NPSP flags
        List<String> tdtmClasses = TDTM_DefaultConfig.getNpspToCumulusMap().values();
        //Remove empty values
        List<String> tdtmClassesWithValues = new List<String>();
        for(String tdtmClass : tdtmClasses) {
            if(!String.isBlank(tdtmClass))
               tdtmClassesWithValues.add(tdtmClass);
        }
        
        //Verify that all the TDTM classes have been deactivated (since all the NPSP disable flags were on)
        List<Trigger_Handler__c> afterScriptConfig = [select Active__c, Class__c from Trigger_Handler__c where Class__c IN :tdtmClasses];     
        //The -1 is because there are two NPSP flags (npe01__DISABLE_OpportunityAccount_trigger__c and 
        //npe01__DISABLE_OpportunityContactRoles_trigger__c) that point to the same TDTM class (OPP_OpportunityContactRoles_TDTM)
        System.assertEquals(tdtmClassesWithValues.size() - 1, afterScriptConfig.size());     
        for(Trigger_Handler__c handler : afterScriptConfig) {
            UTIL_Debug.debug('****Is class ' + handler.Class__c + ' still active: ' + handler.Active__c);
            System.assertEquals(false, handler.Active__c);
        }
        
        //Assert old flags have been properly disabled
        assertNpspFlagsDeactivated();
    }
    
     /** NPSP to TDTM test - If it's the first time we install Cumulus and some the custom DISABLE flag were enabled **/
    public testmethod static void mappingsTest_runScriptCustomConfigOnInstallSomeFlags() {
        if (strTestOnly != '*' && strTestOnly != 'mappingsTest_runScriptCustomConfigOnInstallSomeFlags') return;
        
        //Create NPSP custom settings with some disable flags on
        List<SObject> settingsToUpdate = new List<SObject>();
        
        //Modify settings only in-memory for the test, so it can be run in parallel
        npe01__Contacts_And_Orgs_Settings__c npe01Settings = UTIL_CustomSettingsFacade.getOrgContactsSettings();
        npe01Settings.npe01__DISABLE_IndividualAccounts_trigger__c = true;
        npe01Settings.npe01__DISABLE_OpportunityAccount_trigger__c = true;
        
        npo02__Households_Settings__c npo02Settings = UTIL_CustomSettingsFacade.getOrgHouseholdsSettings();
        npo02Settings.npo02__DISABLE_Household_Opportunity_trigger__c = true;

        Test.testInstall(new STG_InstallScript(), null);
        
        //Verify that the right TDTM classes have been deactivated
        List<String> mappedTdtmClasses = new String[] {'ACCT_IndividualAccounts_TDTM', 'OPP_OpportunityContactRoles_TDTM', 
                                                               'HH_OppContactRoles_TDTM'};
        List<Trigger_Handler__c> afterScriptConfig = [select Active__c, Class__c from Trigger_Handler__c where Class__c IN :mappedTdtmClasses];
        for(Trigger_Handler__c handler : afterScriptConfig) {
            UTIL_Debug.debug('****Is class ' + handler.Class__c + ' still active: ' + handler.Active__c);
            System.assertEquals(false, handler.Active__c);
        }
        
        //Assert old flags have been properly disabled
        assertNpspFlagsDeactivated();
    }
    
    private static void setAllNpspFlags(Boolean flagValue) {
        npe01__Contacts_And_Orgs_Settings__c npe01Settings = UTIL_CustomSettingsFacade.getOrgContactsSettings();
        npe01Settings.npe01__DISABLE_IndividualAccounts_trigger__c = flagValue;
        npe01Settings.npe01__DISABLE_OpportunityAccount_trigger__c = flagValue;
        npe01Settings.npe01__DISABLE_OpportunityContactRoles_trigger__c = flagValue;
        npe01Settings.npe01__DISABLE_OpportunityPayments_trigger__c = flagValue;
        
        npo02__Households_Settings__c npo02Settings = UTIL_CustomSettingsFacade.getOrgHouseholdsSettings();
        npo02Settings.npo02__DISABLE_Household_Opportunity_trigger__c = flagValue;
        npo02Settings.npo02__DISABLE_HouseholdAfter_trigger__c = flagValue;
        npo02Settings.npo02__DISABLE_HouseholdBefore_trigger__c = flagValue;
        npo02Settings.npo02__DISABLE_Households_trigger__c = flagValue;
        npo02Settings.npo02__DISABLE_OpportunityRollup_trigger__c = flagValue;
        
        npe03__Recurring_Donations_Settings__c npe03Settings = UTIL_CustomSettingsFacade.getOrgRecurringDonationsSettings();
        npe03Settings.npe03__DISABLE_Donations_trigger__c = flagValue;
        npe03Settings.npe03__DISABLE_RecurringDonations_trigger__c = flagValue;
        
        npe4__Relationship_Settings__c npe04Settings = UTIL_CustomSettingsFacade.getOrgRelationshipSettings();
        npe04Settings.npe4__DISABLE_RelationshipCM_trigger__c = flagValue;
        npe04Settings.npe4__DISABLE_RelationshipContacts_trigger__c = flagValue;
        npe04Settings.npe4__DISABLE_Relationships_trigger__c = flagValue;
    }
    
    private static void assertNpspFlagsDeactivated() {
        npe01__Contacts_And_Orgs_Settings__c npe01Settings = UTIL_CustomSettingsFacade.getOrgContactsSettings();
        System.assertEquals(false, npe01Settings.npe01__DISABLE_IndividualAccounts_trigger__c);
        System.assertEquals(false, npe01Settings.npe01__DISABLE_OpportunityAccount_trigger__c);
        System.assertEquals(false, npe01Settings.npe01__DISABLE_OpportunityContactRoles_trigger__c);
        System.assertEquals(false, npe01Settings.npe01__DISABLE_OpportunityPayments_trigger__c);
        
        npo02__Households_Settings__c npo02Settings = UTIL_CustomSettingsFacade.getOrgHouseholdsSettings();
        System.assertEquals(false, npo02Settings.npo02__DISABLE_Household_Opportunity_trigger__c);
        System.assertEquals(false, npo02Settings.npo02__DISABLE_HouseholdAfter_trigger__c);
        System.assertEquals(false, npo02Settings.npo02__DISABLE_HouseholdBefore_trigger__c);
        System.assertEquals(false, npo02Settings.npo02__DISABLE_Households_trigger__c);
        System.assertEquals(false, npo02Settings.npo02__DISABLE_OpportunityRollup_trigger__c);
        
        npe03__Recurring_Donations_Settings__c npe03Settings = UTIL_CustomSettingsFacade.getOrgRecurringDonationsSettings();
        System.assertEquals(false, npe03Settings.npe03__DISABLE_Donations_trigger__c);
        System.assertEquals(false, npe03Settings.npe03__DISABLE_RecurringDonations_trigger__c);
        
        npe4__Relationship_Settings__c npe04Settings = UTIL_CustomSettingsFacade.getOrgRelationshipSettings();
        System.assertEquals(false, npe04Settings.npe4__DISABLE_RelationshipCM_trigger__c);
        System.assertEquals(false, npe04Settings.npe4__DISABLE_RelationshipContacts_trigger__c);
        System.assertEquals(false, npe04Settings.npe4__DISABLE_Relationships_trigger__c);
    }
    
    public static testmethod void defaultSettingsCreated() {
        if (strTestOnly != '*' && strTestOnly != 'defaultSettingsCreated') return;
        
        //Clear all custom settings
        deleteAllCustomSettings();
                       
        Test.testInstall(new STG_InstallScript(), null);
        
        npe01__Contacts_And_Orgs_Settings__c npe01Settings = UTIL_CustomSettingsFacade.getOrgContactsSettings();
        npo02__Households_Settings__c npo02Settings = UTIL_CustomSettingsFacade.getOrgHouseholdsSettings();
        npe03__Recurring_Donations_Settings__c npe03Settings = UTIL_CustomSettingsFacade.getOrgRecurringDonationsSettings();
        npe4__Relationship_Settings__c npe04Settings = UTIL_CustomSettingsFacade.getOrgRelationshipSettings();
        npe5__Affiliations_Settings__c npe05Settings = UTIL_CustomSettingsFacade.getOrgAffiliationsSettings();
        Error_Settings__c errorSettings = UTIL_CustomSettingsFacade.getOrgErrorSettings();
        Addr_Verification_Settings__c addrVerificationSetgs = UTIL_CustomSettingsFacade.getOrgAddressVerificationSettings();
        Batch_Data_Entry_Settings__c batchDataEntrySetgs = UTIL_CustomSettingsFacade.getOrgBDESettings();
        Household_Naming_Settings__c householdNamingSettings = UTIL_CustomSettingsFacade.getOrgHouseholdNamingSettings();
        Allocations_Settings__c allocationsSettings = UTIL_CustomSettingsFacade.getOrgAllocationsSettings();
        Data_Import_Settings__c dataImportSettings = UTIL_CustomSettingsFacade.getOrgDataImportSettings();

        //If it's a new install we want to set the Household model by default
        System.assertEquals(CAO_Constants.HH_ACCOUNT_PROCESSOR, npe01Settings.npe01__Account_Processor__c);
        System.assertEquals(false, npe01Settings.npe01__Enable_Opportunity_Contact_Role_Trigger__c);
        System.assertEquals(true, npe01Settings.npe01__Payments_Enabled__c);     
        Recordtypeinfo householdAccount = Schema.SObjectType.Account.getRecordTypeInfosByName().get('Household Account');
        Id householdAccountId = (householdAccount == null ? null : householdAccount.getRecordTypeId());
        System.assertEquals(householdAccountId, npe01Settings.npe01__HH_Account_RecordTypeID__c);
        
        System.assertEquals(HH_Households.NO_HOUSEHOLDS_PROCESSOR, npo02Settings.npo02__Household_Rules__c);
        System.assertEquals(label.npo02.Household_Member_Contact_Role, npo02Settings.npo02__Household_Member_Contact_Role__c);
        System.assertEquals(false, npo02Settings.npo02__Always_Rollup_to_Primary_Contact__c);
        System.assertEquals(true, npo02Settings.npo02__Enable_Opp_Rollup_Triggers__c);
        System.assertEquals(null, npo02Settings.npo02__Excluded_Account_Opp_Rectypes__c);
        System.assertEquals(null, npo02Settings.npo02__Excluded_Account_Opp_Types__c);
        System.assertEquals(null, npo02Settings.npo02__Excluded_Contact_Opp_Rectypes__c);
        System.assertEquals(null, npo02Settings.npo02__Excluded_Contact_Opp_Types__c);
        System.assertEquals(null, npo02Settings.npo02__Membership_Record_Types__c);
        System.assertEquals(365, npo02Settings.npo02__Rollup_N_Day_Value__c);
        System.assertEquals(30, npo02Settings.npo02__Membership_Grace_Period__c);
        System.assertEquals(true, npo02Settings.npo02__Advanced_Household_Naming__c);
        System.assertEquals(false, npo02Settings.npo02__Async_Household_Naming__c);
        System.assertEquals(UserInfo.getOrganizationId(), npo02Settings.Setupownerid);
        System.assertEquals(25, npo02Settings.npo02__Schedule_Job_Limit__c);
        System.assertEquals(null, npo02Settings.npo02__Household_OCR_Excluded_Recordtypes__c);
        System.assertEquals(null, npo02Settings.npo02__Household_Creation_Excluded_Recordtypes__c);
        System.assertEquals(HH_Households.NO_HOUSEHOLDS_PROCESSOR, npo02Settings.npo02__Household_Rules__c);
        System.assertEquals(true, npo02Settings.npo02__Enable_Soft_Credit_Rollups__c);
        System.assertEquals('Matched Donor;Soft Credit;Household Member', npo02Settings.npo02__Soft_Credit_Roles__c);
        System.assertEquals(true, npo02Settings.npo02__Household_Contact_Roles_On__c);
        
        System.assertEquals(RD_RecurringDonations.RecurringDonationCloseOptions.Mark_Opportunities_Closed_Lost.name(), npe03Settings.npe03__Open_Opportunity_Behavior__c);
        System.assertEquals(true, npe03Settings.npe03__Add_Campaign_to_All_Opportunites__c);
        System.assertEquals(true, npe03Settings.npe03__Enable_Update_Check__c);
        System.assertEquals(50, npe03Settings.npe03__Maximum_Donations__c);
        System.assertEquals(12, npe03Settings.npe03__Opportunity_Forecast_Months__c);
        System.assertEquals(90, npe03Settings.npe03__Update_Check_Interval__c);
        System.assertEquals([select id from RecordType where sobjecttype = 'Opportunity' limit 1].id, npe03Settings.npe03__Record_Type__c);
        
        System.assertEquals('List Setting', npe04Settings.npe4__Reciprocal_Method__c);
        
        System.assertEquals(true, npe05Settings.npe5__Automatic_Affiliation_Creation_Turned_On__c);
        
        System.assertEquals(true, errorSettings.Store_Errors_On__c);
        System.assertEquals(true, errorSettings.Error_Notifications_On__c);
        System.assertEquals(ERR_Notifier.NotificationOptions.sysAdmins, errorSettings.Error_Notifications_To__c);

        System.assertEquals(false, addrVerificationSetgs.Enable_Automatic_Verification__c);
        System.assertEquals(false, addrVerificationSetgs.Reject_Ambiguous_Addresses__c);
        
        System.assertEquals(true, batchDataEntrySetgs.Allow_Blank_Opportunity_Names__c);
        System.assertEquals(true, batchDataEntrySetgs.Opportunity_Naming__c);

        System.assertEquals('HH_NameSpec',householdNamingSettings.Implementing_Class__c);
        System.assertEquals(365,allocationsSettings.Rollup_N_Day_Value__c);
        System.assertEquals(50,dataImportSettings.Batch_Size__c);
    }

    public static void deleteAllCustomSettings() {
        npe01__Contacts_And_Orgs_Settings__c npe01Settings = npe01__Contacts_And_Orgs_Settings__c.getInstance();
        npo02__Households_Settings__c npo02Settings = npo02__Households_Settings__c.getInstance();
        npe03__Recurring_Donations_Settings__c npe03Settings = npe03__Recurring_Donations_Settings__c.getInstance();
        npe4__Relationship_Settings__c npe04Settings = npe4__Relationship_Settings__c.getInstance();
        npe5__Affiliations_Settings__c npe05Settings = npe5__Affiliations_Settings__c.getInstance();
        Error_Settings__c errorSettings = Error_Settings__c.getInstance(); 
        if(npe01Settings.Id != null) delete npe01Settings;               
        if(npo02Settings.Id != null) delete npo02Settings;
        if(npe03Settings.Id != null) delete npe03Settings;
        if(npe04Settings.Id != null) delete npe04Settings;
        if(npe05Settings.Id != null) delete npe05Settings;
        if(errorSettings.Id != null) delete errorSettings;
    }
    
    public static void createSomeDefaultSettings() {
        List<Trigger_Handler__c> handlers = new List<Trigger_Handler__c>();
        
        //Relationships on npe4__Relationship__c
        handlers.add(new Trigger_Handler__c(Active__c = true, Asynchronous__c = false, 
              Class__c = 'REL_Relationships_TDTM', Load_Order__c = 1, Object__c = 'npe4__Relationship__c', 
              Trigger_Action__c = 'BeforeInsert;BeforeUpdate;AfterInsert;AfterUpdate;AfterDelete'));
                                         
        //Account Management on Contact
        handlers.add(new Trigger_Handler__c(Active__c = true, Asynchronous__c = false, 
              Class__c = 'ACCT_IndividualAccounts_TDTM', Load_Order__c = 1, Object__c = 'Contact', 
              Trigger_Action__c = 'BeforeInsert;BeforeUpdate;AfterInsert;AfterUpdate;AfterDelete'));

        // OpportunityContactRole support
        handlers.add(new Trigger_Handler__c(Active__c = true, Asynchronous__c = false, 
              Class__c = 'HH_OppContactRoles_TDTM', Load_Order__c = 1, Object__c = 'Opportunity', 
              Trigger_Action__c = 'AfterInsert'));

        // HH Object support
        handlers.add(new Trigger_Handler__c(Active__c = true, Asynchronous__c = false, 
              Class__c = 'HH_HHObject_TDTM', Load_Order__c = 1, Object__c = 'npo02__Household__c', 
              Trigger_Action__c = 'BeforeUpdate;AfterUpdate'));

        // HH Object contact trigger support
        handlers.add(new Trigger_Handler__c(Active__c = true, Asynchronous__c = false, 
              Class__c = 'HH_Households_TDTM', Load_Order__c = 0, Object__c = 'Contact', 
              Trigger_Action__c = 'AfterInsert;AfterUpdate;AfterDelete'));
        
        insert handlers;
    }
    
    public static void verifyAllSettingsCreated() {
        List<Trigger_Handler__c> afterScriptConfig = [select Active__c, Asynchronous__c, Class__c, Load_Order__c, 
            Object__c, Trigger_Action__c from Trigger_Handler__c];
        
        List<Trigger_Handler__c> defaultConfig = TDTM_DefaultConfig.getDefaultRecords();
        
        assertTriggerHandlersEqual(afterScriptConfig, defaultConfig);
    }
}