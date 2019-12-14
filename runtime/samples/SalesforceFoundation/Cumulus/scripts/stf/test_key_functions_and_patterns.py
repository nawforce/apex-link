import unittest
import json
from key_functions import KeyParser

with open('key_patterns.json', 'r') as key_patterns_file:
    key_patterns = json.load(key_patterns_file)

class TestKeyTransforms(unittest.TestCase):
    def setUp(self):
        self.parser = KeyParser(key_patterns)

    def test_ButtonOrLink1(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'ButtonOrLink.Account.npsp__Manage_Household'
            ),
            ('npsp', 'ButtonOrLink.Account.Manage_Household')
        )

    def test_ButtonOrLink2(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'ButtonOrLink.npe01__OppPayment__c.npe01__Schedule_Payments'
            ),
            ('npe01', 'ButtonOrLink.OppPayment__c.Schedule_Payments')
        )

    def test_ButtonOrLink3(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'ButtonOrLink.npsp__Address__c.npsp__Verify_Address'
            ),
            ('npsp', 'ButtonOrLink.Address__c.Verify_Address')
        )

    def test_CrtLayoutSection1(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'CrtLayoutSection.npo02__Households_and_Donations_1'
            ),
            ('npo02', 'CrtLayoutSection.Households_and_Donations_1')
        )

    def test_CrtLayoutSection2(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'CrtLayoutSection.npsp__Contacts_Only_1'
            ),
            ('npsp', 'CrtLayoutSection.Contacts_Only_1')
        )

    def test_CustomField1(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'CustomField.Account.npo02__AverageAmount.FieldLabel'
            ),
            ('npo02', 'CustomField.Account.AverageAmount.FieldLabel')
        )

    def test_CustomField2(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'CustomField.Account.npsp__Batch.FieldLabel'
            ),
            ('npsp', 'CustomField.Account.Batch.FieldLabel')
        )

    def test_CustomField3(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'CustomField.Account.npsp__Batch.HelpText'
            ),
            ('npsp', 'CustomField.Account.Batch.HelpText')
        )

    def test_CustomField4(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'CustomField.Account.npsp__Batch.RelatedListLabel'
            ),
            ('npsp', 'CustomField.Account.Batch.RelatedListLabel')
        )

    def test_CustomField5(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'CustomField.npe01__Contacts_And_Orgs_Settings__c.npe01__Default_Opp_on_Convert.FieldLabel'
            ),
            ('npe01', 'CustomField.Contacts_And_Orgs_Settings__c.Default_Opp_on_Convert.FieldLabel')
        )

    def test_CustomField6(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'CustomField.npe01__Contacts_And_Orgs_Settings__c.npsp__Automatic_Campaign_Member_Management.FieldLabel'
            ),
            ('npsp', 'CustomField.npe01__Contacts_And_Orgs_Settings__c.Automatic_Campaign_Member_Management.FieldLabel')
        )

    def test_CustomLabel1(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'CustomLabel.npo02__Add'
            ),
            ('npo02', 'CustomLabel.Add')
        )

    def test_CustomLabel2(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'CustomLabel.npsp__Addr_Id_Error'
            ),
            ('npsp', 'CustomLabel.Addr_Id_Error')
        )

    def test_CustomReportType1(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'CustomReportType.Account.Description'
            ),
            (None, 'CustomReportType.Account.Description')
        )

    def test_CustomReportType2(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'CustomReportType.npo02__Households_and_Donations.Description'
            ),
            ('npo02', 'CustomReportType.Households_and_Donations.Description')
        )

    def test_CustomReportType3(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'CustomReportType.npo02__Households_and_Donations.Name'
            ),
            ('npo02', 'CustomReportType.Households_and_Donations.Name')
        )

    def test_CustomReportType4(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'CustomReportType.npsp__Contacts_Only.Description'
            ),
            ('npsp', 'CustomReportType.Contacts_Only.Description')
        )

    def test_CustomReportType5(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'CustomReportType.npsp__Contacts_Only.Name'
            ),
            ('npsp', 'CustomReportType.Contacts_Only.Name')
        )

    def test_LayoutSection1(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'LayoutSection.Account.npsp__Household Layout.Address_1'
            ),
            ('npsp', 'LayoutSection.Account.Household Layout.Address_1')
        )

    def test_LayoutSection2(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'LayoutSection.Campaign.npsp__NPSP Campaign Layout.Custom Links_4'
            ),
            ('npsp', 'LayoutSection.Campaign.NPSP Campaign Layout.Custom Links_4')
        )

    def test_LayoutSection3(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'LayoutSection.UserProvAccount.User Provisioning Account Layout.System Information_1'
            ),
            (None, 'LayoutSection.UserProvAccount.User Provisioning Account Layout.System Information_1')
        )

    def test_LayoutSection4(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'LayoutSection.npe01__OppPayment__c.npe01__Payment Layout.Custom Links_2'
            ),
            ('npe01', 'LayoutSection.OppPayment__c.Payment Layout.Custom Links_2')
        )

    def test_LayoutSection5(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'LayoutSection.npsp__Address__c.npsp__Address Layout.Custom Links_3'
            ),
            ('npsp', 'LayoutSection.Address__c.Address Layout.Custom Links_3')
        )

    def test_LookupFilter1(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'LookupFilter.npsp__Address__c.Contact.npsp__npsp__Current_Address__c.InfoMessage'
            ),
            ('npsp', 'LookupFilter.Address__c.Contact.Current_Address__c.InfoMessage')
        )

    def test_LookupFilter2(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'LookupFilter.npsp__General_Accounting_Unit__c.npsp__Allocation__c.npsp__npsp__General_Accounting_Unit__c.ErrorMessage'
            ),
            ('npsp', 'LookupFilter.General_Accounting_Unit__c.Allocation__c.General_Accounting_Unit__c.ErrorMessage')
        )

    def test_LookupFilter3(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'LookupFilter.npsp__General_Accounting_Unit__c.npsp__Allocation__c.npsp__npsp__General_Accounting_Unit__c.InfoMessage'
            ),
            ('npsp', 'LookupFilter.General_Accounting_Unit__c.Allocation__c.General_Accounting_Unit__c.InfoMessage')
        )

    def test_PicklistValue1(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'PicklistValue.Account.npsp__Funding_Focus.Animal Welfare'
            ),
            ('npsp', 'PicklistValue.Account.Funding_Focus.Animal Welfare')
        )

    def test_PicklistValue2(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'PicklistValue.Contact.npe01__PreferredPhone.Home'
            ),
            ('npe01', 'PicklistValue.Contact.PreferredPhone.Home')
        )

    def test_PicklistValue3(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'PicklistValue.Standard.contactRole.Decision Maker'
            ),
            (None, 'PicklistValue.Standard.contactRole.Decision Maker')
        )

    def test_PicklistValue4(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'PicklistValue.npe01__OppPayment__c.npe01__Payment_Method.Cash'
            ),
            ('npe01', 'PicklistValue.OppPayment__c.Payment_Method.Cash')
        )

    def test_PicklistValue5(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'PicklistValue.npe01__OppPayment__c.npsp__Payment_Acknowledgment_Status.Acknowledged'
            ),
            ('npsp', 'PicklistValue.npe01__OppPayment__c.Payment_Acknowledgment_Status.Acknowledged')
        )

    def test_QuickAction1(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'QuickAction.Account.npsp__New_Address'
            ),
            ('npsp', 'QuickAction.Account.New_Address')
        )

    def test_QuickAction2(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'QuickAction.New_Household_Account'
            ),
            (None, 'QuickAction.New_Household_Account')
        )

    def test_QuickAction3(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'QuickAction.New_Organization'
            ),
            (None, 'QuickAction.New_Organization')
        )

    def test_QuickAction4(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'QuickAction.npe01__OppPayment__c.npsp__Quick_Update'
            ),
            ('npsp', 'QuickAction.npe01__OppPayment__c.Quick_Update')
        )

    def test_QuickAction5(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'QuickAction.npe03__Recurring_Donation__c.npe03__New_Opportunity'
            ),
            ('npe03', 'QuickAction.Recurring_Donation__c.New_Opportunity')
        )

    def test_QuickAction6(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'QuickAction.npsp__Address__c.npsp__Update_Address'
            ),
            ('npsp', 'QuickAction.Address__c.Update_Address')
        )

    def test_QuickAction7(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'QuickAction.npsp__New_Opportunity'
            ),
            ('npsp', 'QuickAction.New_Opportunity')
        )

    def test_RecordType1(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'RecordType.Account.HH_Account'
            ),
            (None, 'RecordType.Account.HH_Account')
        )

    def test_RecordType2(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'RecordType.Account.Organization'
            ),
            (None, 'RecordType.Account.Organization')
        )

    def test_RecordType3(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'RecordType.Metric.Completion'
            ),
            (None, 'RecordType.Metric.Completion')
        )

    def test_RecordType4(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'RecordType.Metric.Progress'
            ),
            (None, 'RecordType.Metric.Progress')
        )

    def test_Scontrol1(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'Scontrol.npe01__AccountViewOverride'
            ),
            ('npe01', 'Scontrol.AccountViewOverride')
        )

    def test_Scontrol2(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'Scontrol.npo02__HouseholdTransactionHistory'
            ),
            ('npo02', 'Scontrol.HouseholdTransactionHistory')
        )

    def test_ValidationFormula1(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'ValidationFormula.npe4__Relationship__c.npe4__Related_Contact_Do_Not_Change'
            ),
            ('npe4', 'ValidationFormula.Relationship__c.Related_Contact_Do_Not_Change')
        )

    def test_WebTab1(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'WebTab.npe5__Affiliations_Settings'
            ),
            ('npe5', 'WebTab.Affiliations_Settings')
        )

    def test_WebTab2(self):
        self.assertEquals(
            self.parser.rewrite_key(
                'WebTab.npsp__Batch_Data_Entry'
            ),
            ('npsp', 'WebTab.Batch_Data_Entry')
        )

if __name__ == '__main__':
    unittest.main()
