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
* @description Tests for UTIL_RecordTypeSettingsUpdate
*/
@isTest
private class UTIL_RecordTypeSettingsUpdate_TEST {
    @isTest
    private static void testNullSettingsRemainNull() {
        // if a setting is null, it should remain null after updating
        Test.startTest();

        UTIL_RecordTypeSettingsUpdate rtsu = new UTIL_RecordTypeSettingsUpdate();
        String ids = rtsu.getIdsFromNames(
            null,
            new Map<String, Id>()
        );

        Test.stopTest();

        System.assertEquals(null, ids);
    }

    @isTest
    public static void testBlankSettingsRemainBlank() {
        // if a setting is blank, it should remain blank after updating
        Test.startTest();

        UTIL_RecordTypeSettingsUpdate rtsu = new UTIL_RecordTypeSettingsUpdate();
        String ids = rtsu.getIdsFromNames(
            '',
            new Map<String, Id>()
        );

        Test.stopTest();

        System.assertEquals('', ids);
    }

    @isTest
    public static void testEmptyValuesAreDiscarded() {
        // After detokenizing, empty values should be discarded
        Test.startTest();

        UTIL_RecordTypeSettingsUpdate rtsu = new UTIL_RecordTypeSettingsUpdate();
        String ids = rtsu.getIdsFromNames(
            ';;   ;;   ; ',
            new Map<String, Id>()
        );

        Test.stopTest();

        System.assertEquals('', ids);
    }

    @isTest
    public static void testUnknownRecordTypeNamesAreDiscarded() {
        // Unknown record type names should be discarded
        Test.startTest();

        UTIL_RecordTypeSettingsUpdate rtsu = new UTIL_RecordTypeSettingsUpdate();
        String ids = rtsu.getIdsFromNames(
            'UnknownRecordTypeName',
            new Map<String, Id>()
        );

        Test.stopTest();

        System.assertEquals('', ids);
    }

    @isTest
    public static void testIdsRemainIds() {
        // If the setting is an id, it should remain an id after updating
        Test.startTest();

        UTIL_RecordTypeSettingsUpdate rtsu = new UTIL_RecordTypeSettingsUpdate();
        String ids = rtsu.getIdsFromNames(
            '012410000000RXlAAM;01241000000N0kX',
            new Map<String, Id>()
        );

        Test.stopTest();

        System.assertEquals('012410000000RXlAAM;01241000000N0kX', ids);
    }

    @isTest
    public static void testKnownRecordTypeNamesAreConverted() {
        // Unknown record type names should be discarded
        Id knownRecordTypeId = '012000000000001';

        Test.startTest();

        UTIL_RecordTypeSettingsUpdate rtsu = new UTIL_RecordTypeSettingsUpdate();
        String ids = rtsu.getIdsFromNames(
            'KnownRecordTypeName',
            new Map<String, Id>{
                'KnownRecordTypeName' => knownRecordTypeId
            }
        );

        Test.stopTest();

        System.assertEquals(knownRecordTypeId, ids);
    }

    @isTest
    public static void testKitchenSink() {
        // Combine multiple conditions in one test

        Id knownRecordTypeId1 = '012000000000001';
        Id knownRecordTypeId2 = '012000000000002';
        Test.startTest();

        UTIL_RecordTypeSettingsUpdate rtsu = new UTIL_RecordTypeSettingsUpdate();
        String ids = rtsu.getIdsFromNames(
            ';KnownRecordTypeName1; ;UnknownRecordTypeName;KnownRecordTypeName2;012410000000RXlAAM;01241000000N0kX;   ;',
            new Map<String, Id>{
                'KnownRecordTypeName1' => knownRecordTypeId1,
                'KnownRecordTypeName2' => knownRecordTypeId2
            }
        );

        Test.stopTest();

        String expectedRecordTypeIds = String.join(
            new List<String>{knownRecordTypeId1, knownRecordTypeId2, '012410000000RXlAAM', '01241000000N0kX'},
            ';'
        );

        System.assertEquals(expectedRecordTypeIds, ids);
    }

    /**
     * @description A stub class to provide known record type name to record
     * type id mappings
     */
    private class NameMappingStub extends UTIL_RecordTypeSettingsUpdate {
        public Map<String, Id> opportunityNameMap;
        public Map<String, Id> contactNameMap;
        private Map<String, Id> getNameToRecordTypeIdMap(Schema.SObjectType sot) {
            if (sot == Contact.sObjectType) {
                return contactNameMap;
            } else if (sot == Opportunity.sObjectType) {
                return opportunityNameMap;
            }
            return null;
        }
    }

    @isTest
    private static void testUpdateSettings() {
        // we insert some settings with fake record type names and then test to
        // see if UTIL_RecordTypeSettingsUpdate correctly reads those settings,
        // converts the fake names to fake ids, and then rewrites the settings.
        // To control for a known set of record types, we will stub the
        // getNameToRecordTypeIdMap() method to return a set of test mappings.

        Allocations_Settings__c allocationsSettings = new Allocations_Settings__c(
            Excluded_Opp_RecTypes__c = 'FakeOpportunityRecTypeName1;FakeOpportunityRecTypeName2'
        );

        npe01__Contacts_And_Orgs_Settings__c contactsAndOrgsSettings = new npe01__Contacts_And_Orgs_Settings__c(
            Opp_RecTypes_Excluded_for_Payments__c = 'FakeOpportunityRecTypeName1;FakeOpportunityRecTypeName2'
        );

        npo02__Households_Settings__c householdsSettings = new npo02__Households_Settings__c(
            npo02__Household_Creation_Excluded_Recordtypes__c = 'FakeContactRecTypeName1;FakeContactRecTypeName2',
            npo02__Household_OCR_Excluded_Recordtypes__c = 'FakeContactRecTypeName1;FakeContactRecTypeName2',
            npo02__Membership_Record_Types__c = 'FakeOpportunityRecTypeName1;FakeOpportunityRecTypeName2',
            npo02__Excluded_Account_Opp_Rectypes__c = 'FakeOpportunityRecTypeName1;FakeOpportunityRecTypeName2',
            npo02__Excluded_Contact_Opp_Rectypes__c = 'FakeOpportunityRecTypeName1;FakeOpportunityRecTypeName2'
        );
        Opportunity_Naming_Settings__c opportunityNamingSettings = new Opportunity_Naming_Settings__c(
            Name = 'ons1',
            Opportunity_Record_Types__c = 'FakeOpportunityRecTypeName1;FakeOpportunityRecTypeName2'
        );

        insert allocationsSettings;
        insert contactsAndOrgsSettings;
        insert householdsSettings;
        insert opportunityNamingSettings;

        Id opportunityRecTypeId1 = '012000000000001';
        Id opportunityRecTypeId2 = '012000000000002';
        Id contactRecTypeId1 = '012000000000003';
        Id contactRecTypeId2 = '012000000000004';

        NameMappingStub stub = new NameMappingStub();
        stub.opportunityNameMap = new Map<String, Id>{
            'FakeOpportunityRecTypeName1' => opportunityRecTypeId1,
            'FakeOpportunityRecTypeName2' => opportunityRecTypeId2
        };
        stub.contactNameMap = new Map<String, Id>{
            'FakeContactRecTypeName1' => contactRecTypeId1,
            'FakeContactRecTypeName2' => contactRecTypeId2
        };

        String expectedOpportunityIdString = String.join(new List<String>{opportunityRecTypeId1, opportunityRecTypeId2}, ';');
        String expectedContactIdString = String.join(new List<String>{contactRecTypeId1, contactRecTypeId2}, ';');

        Test.startTest();

        stub.updateSettings();

        Test.stopTest();

        Allocations_Settings__c allocationsSettingsActual = [
            SELECT Excluded_Opp_RecTypes__c
            FROM Allocations_Settings__c
            WHERE Id = :allocationsSettings.Id
        ];

        npe01__Contacts_And_Orgs_Settings__c contactsAndOrgsSettingsActual = [
            SELECT Opp_RecTypes_Excluded_for_Payments__c
            FROM npe01__Contacts_And_Orgs_Settings__c
            WHERE Id = :contactsAndOrgsSettings.Id
        ];

        npo02__Households_Settings__c householdsSettingsActual = [
            SELECT
                npo02__Household_Creation_Excluded_Recordtypes__c,
                npo02__Household_OCR_Excluded_Recordtypes__c,
                npo02__Membership_Record_Types__c,
                npo02__Excluded_Account_Opp_Rectypes__c,
                npo02__Excluded_Contact_Opp_Rectypes__c
            FROM npo02__Households_Settings__c
            WHERE Id = :householdsSettings.Id
        ];

        Opportunity_Naming_Settings__c opportunityNamingSettingsActual = [
            SELECT Opportunity_Record_Types__c
            FROM Opportunity_Naming_Settings__c
            WHERE Id = :opportunityNamingSettings.Id
        ];

        System.assertEquals(
            expectedOpportunityIdString,
            allocationsSettingsActual.Excluded_Opp_RecTypes__c
        );

        System.assertEquals(
            expectedOpportunityIdString,
            contactsAndOrgsSettingsActual.Opp_RecTypes_Excluded_for_Payments__c
        );

        System.assertEquals(
            expectedContactIdString,
            householdsSettingsActual.npo02__Household_Creation_Excluded_Recordtypes__c
        );

        System.assertEquals(
            expectedContactIdString,
            householdsSettingsActual.npo02__Household_OCR_Excluded_Recordtypes__c
        );

        System.assertEquals(
            expectedOpportunityIdString,
            householdsSettingsActual.npo02__Membership_Record_Types__c
        );

        System.assertEquals(
            expectedOpportunityIdString,
            householdsSettingsActual.npo02__Excluded_Account_Opp_Rectypes__c
        );

        System.assertEquals(
            expectedOpportunityIdString,
            householdsSettingsActual.npo02__Excluded_Contact_Opp_Rectypes__c
        );

        System.assertEquals(
            expectedOpportunityIdString,
            opportunityNamingSettingsActual.Opportunity_Record_Types__c
        );
    }

    private static void testUpdateSettingsHandlesNoSettings() {
        // no settings in database, verify updateSettings() properly runs
        UTIL_RecordTypeSettingsUpdate.getInstance().updateSettings();
    }

    /**
     * @description Stub class to verify how many times updateSettings() method
     * is called
     */
    private class UpdateSettingsRunCount extends UTIL_RecordTypeSettingsUpdate {
        public Integer runCount = 0;
        public override void updateSettings() {
            runCount++;
        }
    }

    @isTest
    public static void testUpdateRunsOnUpgradeFrom377() {
        // when upgrading from v3.78, the updateSettings() method should be
        // called exactly once
        UpdateSettingsRunCount stub = new UpdateSettingsRunCount();
        UTIL_RecordTypeSettingsUpdate.instance = stub;

        Test.startTest();

        Test.testInstall(new STG_InstallScript(), new Version(3, 78));

        Test.stopTest();

        System.assertEquals(1, stub.runCount);
    }

    @isTest
    public static void testUpdateRunsOnUpgradeFrom354() {
        // when upgrading from v3.54, the updateSettings() method should be
        // called exactly once (this is to verify that if upgrading from a
        // version prior to 3.79 the update will run)
        UpdateSettingsRunCount stub = new UpdateSettingsRunCount();
        UTIL_RecordTypeSettingsUpdate.instance = stub;

        Test.startTest();

        Test.testInstall(new STG_InstallScript(), new Version(3, 54));

        Test.stopTest();

        System.assertEquals(1, stub.runCount);
    }

    @isTest
    public static void testUpdateRunsOnNewInstall() {
        // when new install, updateSettings() should be called, since this
        // will handle cases where users are updating from NPSP 2.0, and
        // otherwise be a noop.
        UpdateSettingsRunCount stub = new UpdateSettingsRunCount();
        UTIL_RecordTypeSettingsUpdate.instance = stub;

        Test.startTest();

        Test.testInstall(new STG_InstallScript(), null);

        Test.stopTest();

        System.assertEquals(1, stub.runCount);
    }

    @isTest
    public static void testUpdateDoesNotRunOnUpgradeFrom379() {
        // when upgrading from version 3.79 or later, update should not run
        UpdateSettingsRunCount stub = new UpdateSettingsRunCount();
        UTIL_RecordTypeSettingsUpdate.instance = stub;

        Test.startTest();

        Test.testInstall(new STG_InstallScript(), new Version(3, 79));

        Test.stopTest();

        System.assertEquals(0, stub.runCount);
    }

    @isTest
    public static void testUpdateDoesNotRunOnUpgradeFrom399() {
        // when upgrading from version 3.79 or later, update should not run
        // when new install, updateSettings() should not be called
        UpdateSettingsRunCount stub = new UpdateSettingsRunCount();
        UTIL_RecordTypeSettingsUpdate.instance = stub;

        Test.startTest();

        Test.testInstall(new STG_InstallScript(), new Version(3, 99));

        Test.stopTest();

        System.assertEquals(0, stub.runCount);
    }
}