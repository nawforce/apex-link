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
 * @group Utilities
 * @description Utility script to convert record type settings from old format
 * (where record types were stored by display name) to new format (where record
 * types are stored by id).
 *
 * This will handle the conversion of the following custom settings:
 *  Allocations_Settings__c.Excluded_Opp_RecTypes__c (Opportunity, Hierarchy)
 *  npe01__Contacts_And_Orgs_Settings__c.Opp_RecTypes_Excluded_for_Payments__c (Opportunity, Hierarchy)
 *  npo02__Households_Settings__c.npo02__Household_Creation_Excluded_Recordtypes__c (Contact, Hierarchy)
 *  npo02__Households_Settings__c.npo02__Household_OCR_Excluded_Recordtypes__c (Contact, Hierarchy)
 *  npo02__Households_Settings__c.npo02__Membership_Record_Types__c (Opportunity, Hierarchy)
 *  npo02__Households_Settings__c.npo02__Excluded_Account_Opp_Rectypes__c (Opportunity, Hierarchy)
 *  npo02__Households_Settings__c.npo02__Excluded_Contact_Opp_Rectypes__c (Opportunity, Hierarchy)
 *  Opportunity_Naming_Settings__c.Opportunity_Record_Types__c (Opportunity, List)
 */
global virtual class UTIL_RecordTypeSettingsUpdate {
    @TestVisible
    /** @description Private constructor for singleton pattern */
    private UTIL_RecordTypeSettingsUpdate() {}

    @TestVisible
    /** @description Static instance for singleton pattern */
    private static UTIL_RecordTypeSettingsUpdate instance;

    /**
     * @description get singleton instance of this class
     * @return UTIL_RecordTypeSettingsUpdate
     */
    global static UTIL_RecordTypeSettingsUpdate getInstance() {
        if (null == instance) {
            instance = new UTIL_RecordTypeSettingsUpdate();
        }
        return instance;
    }

    /**
     * @description Update custom settings from old format to new format
     */
    global virtual void updateSettings() {
        Map<String, Id> opportunityNameMap = getNameToRecordTypeIdMap(Opportunity.sObjectType);
        Map<String, Id> contactNameMap = getNameToRecordTypeIdMap(Contact.sObjectType);

        RecordTypeSettings settings = new RecordTypeSettings();

        settings.allocationsSettings = [
            SELECT Excluded_Opp_RecTypes__c
            FROM Allocations_Settings__c
        ];

        settings.contactsAndOrgsSettings = [
            SELECT Opp_RecTypes_Excluded_for_Payments__c
            FROM npe01__Contacts_And_Orgs_Settings__c
        ];

        settings.householdSettings = [
            SELECT
                npo02__Household_Creation_Excluded_Recordtypes__c,
                npo02__Household_OCR_Excluded_Recordtypes__c,
                npo02__Membership_Record_Types__c,
                npo02__Excluded_Account_Opp_Rectypes__c,
                npo02__Excluded_Contact_Opp_Rectypes__c
            FROM npo02__Households_Settings__c
        ];

        settings.opportunityNamingSettings = [
            SELECT Opportunity_Record_Types__c
            FROM Opportunity_Naming_Settings__c
        ];

        settings = updateSettings(settings, opportunityNameMap, contactNameMap);

        if (settings.allocationsSettings != null && !settings.allocationsSettings.isEmpty()) {
            upsert settings.allocationsSettings;
        }

        if (settings.contactsAndOrgsSettings != null && !settings.contactsAndOrgsSettings.isEmpty()) {
            upsert settings.contactsAndOrgsSettings;
        }

        if (settings.householdSettings != null && !settings.householdSettings.isEmpty()) {
            upsert settings.householdSettings;
        }

        if (settings.opportunityNamingSettings != null && !settings.opportunityNamingSettings.isEmpty()) {
            upsert settings.opportunityNamingSettings;
        }
    }

    @TestVisible
    /**
     * @description Update given custom settings from old format to new format,
     * using the given String -> Id maps to determine record type id from
     * record type display name
     *
     * @param settings The custom settings to be updated
     * @param opportunityNameMap A mapping of Opportunity record type display names to record type ids
     * @param contactNameMap A mapping of Contact record type display names to record type ids
     * @return RecordTypeSettings The settings after they have been converted
     */
    private RecordTypeSettings updateSettings(RecordTypeSettings settings, Map<String, Id> opportunityNameMap, Map<String, Id> contactNameMap) {
        for (Allocations_Settings__c allocationsSettings : settings.allocationsSettings) {
            allocationsSettings.Excluded_Opp_RecTypes__c = getIdsFromNames(
                allocationsSettings.Excluded_Opp_RecTypes__c,
                opportunityNameMap
            );
        }

        for (npe01__Contacts_And_Orgs_Settings__c contactsAndOrgsSettings : settings.contactsAndOrgsSettings) {
                contactsAndOrgsSettings.Opp_RecTypes_Excluded_for_Payments__c = getIdsFromNames(
                    contactsAndOrgsSettings.Opp_RecTypes_Excluded_for_Payments__c,
                    opportunityNameMap
                );
            }

        for (npo02__Households_Settings__c householdSettings : settings.householdSettings) {
            householdSettings.npo02__Household_Creation_Excluded_Recordtypes__c = getIdsFromNames(
                householdSettings.npo02__Household_Creation_Excluded_Recordtypes__c,
                contactNameMap
            );

            householdSettings.npo02__Household_OCR_Excluded_Recordtypes__c = getIdsFromNames(
                householdSettings.npo02__Household_OCR_Excluded_Recordtypes__c,
                contactNameMap
            );

            householdSettings.npo02__Membership_Record_Types__c = getIdsFromNames(
                householdSettings.npo02__Membership_Record_Types__c,
                opportunityNameMap
            );

            householdSettings.npo02__Excluded_Account_Opp_Rectypes__c = getIdsFromNames(
                householdSettings.npo02__Excluded_Account_Opp_Rectypes__c,
                opportunityNameMap
            );

            householdSettings.npo02__Excluded_Contact_Opp_Rectypes__c = getIdsFromNames(
                householdSettings.npo02__Excluded_Contact_Opp_Rectypes__c,
                opportunityNameMap
            );
        }

        for (Opportunity_Naming_Settings__c opportunityNamingSettings : settings.opportunityNamingSettings) {
            opportunityNamingSettings.Opportunity_Record_Types__c = getIdsFromNames(
                opportunityNamingSettings.Opportunity_Record_Types__c,
                opportunityNameMap
            );
        }

        return settings;
    }

    /**
     * @description Build a mapping of record type name to record type id for
     * all of the record types defined for the given SObjectType.  Note that
     * the keys in this map will include the untranslated record type name, as
     * well as all translated versions of the record type name.  The map will
     * *not* contain a mapping for the 'Master' record type.
     *
     * @param sot The sObjectType to use for determining record types
     * @return Map<String, Id>
     */
    private virtual Map<String, Id> getNameToRecordTypeIdMap(Schema.SObjectType sot) {
        String sobjectTypeName = sot.getDescribe().getName();

        List<RecordType> recordTypes;

        try {
            recordTypes = Database.query(
                'SELECT Name, (SELECT Value FROM Localization) FROM RecordType WHERE SobjectType = :sobjectTypeName'
            );
        } catch (QueryException e) {
            recordTypes = Database.query(
                'SELECT Name FROM RecordType WHERE SobjectType = :sobjectTypeName'
            );
        }

        Map<String, Id> nameMap = new Map<String, Id>();

        for (RecordType rt : recordTypes) {
            nameMap.put(rt.Name, rt.Id);
            try {
                for (sObject rtl : rt.getSobjects('Localization')) {
                    // potentially overwrite existing entry due to ambiguity in naming-- fine
                    nameMap.put((String) rtl.get('Value'), rt.Id);
                }
            } catch (SObjectException e) {
                // no localizations available
            }
        }

        return nameMap;
    }

    @TestVisible
    /**
     * @description Given a semicolon ';' separated string of record type
     * names, and a mapping of record type names to record type ids, return a
     * new semicolon separated string of record type ids for all of the known
     * record types in the original string.  Unknown or blank record type names
     * will be discarded.
     *
     * @param nameString A semicolon separated string of record type names
     * @param nameMap A mapping of record type names to record type ids
     * @return String
     */
    private String getIdsFromNames(String nameString, Map<String, Id> nameMap) {
        if (nameString == null) {
            return null;
        }

        List<String> names = nameString.split(';', 0);

        Set<String> ids = new Set<String>();
        for (String name : names) {
            if (isId(name)) {
                ids.add(name);
            } else if (nameMap.containsKey(name)) {
                ids.add(String.valueOf(nameMap.get(name)));
            }
        }

        String idsString = String.join(new List<String>(ids), ';');

        return idsString;
    }

    /**
     * @description Determine if the given name is an id or not
     * @param name The string value to check
     * @return Boolean
     */
    private Boolean isId(String name) {
        try {
            Id.valueOf(name);
            return true;
        } catch (StringException e) {
            return false;
        }
    }

    @TestVisible
    /**
     * @description A storage class for wrapping multiple related custom
     * setting objects
     */
    private class RecordTypeSettings {
        public List<Allocations_Settings__c> allocationsSettings;
        public List<npe01__Contacts_And_Orgs_Settings__c> contactsAndOrgsSettings;
        public List<npo02__Households_Settings__c> householdSettings;
        public List<Opportunity_Naming_Settings__c> opportunityNamingSettings;
    }
}