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
* @description Controller for the Contact Roles panel.
*/
public with sharing class STG_PanelContactRoles_CTRL extends STG_Panel {
    
    /*********************************************************************************************************
    * @description Returns the string Id of the Contact Roles panel. 
    */
    public override string idPanel() { return 'idPanelConRoles'; }

    /*********************************************************************************************************
    * @description The panel's constructor 
    */
    public STG_PanelContactRoles_CTRL() {}

    /**
     * @description A user friendly rendering of record type ids by record type
     * name for Household OCR Excluded Record Types
     */
    static public String strHouseholdOCRExcludedRecTypesReadOnly {
        get {
            if (strHouseholdOCRExcludedRecTypesReadOnly == null) {
                strHouseholdOCRExcludedRecTypesReadOnly = STG_Panel.getRecordTypeNamesFromPicklistString(
                    Contact.sObjectType,
                    STG_Panel.stgService.stgHH.npo02__Household_OCR_Excluded_Recordtypes__c
                );
            }
            return strHouseholdOCRExcludedRecTypesReadOnly;
        }
        private set;
    }

    /*********************************************************************************************************
    * @description Save override that performs settings validation before saving.
    */
    public override PageReference saveSettings() {
        if (isValidOCRSettings()) {
            return super.saveSettings();
        } else {
            return null;
        }
    }

    /*********************************************************************************************************
    * @description Performs settings validation and adds pagemessages for each error.
    * @return Boolean whether the settings are valid or not.
    */
    public Boolean isValidOCRSettings() {
        Boolean isValid = true;
        List<String> invalidOCRSettings = getInvalidOCRSettings();
        
        //Blank out household member role if disabled
        if (!STG_Panel.stgService.stgHH.npo02__Household_Contact_Roles_On__c && 
            !String.isBlank(STG_Panel.stgService.stgHH.npo02__Household_Member_Contact_Role__c)) {
            STG_Panel.stgService.stgHH.npo02__Household_Member_Contact_Role__c = '';
        }

        //add error for blank Primary Contact Role value
        if (String.isBlank(STG_Panel.stgService.stgCon.npe01__Opportunity_Contact_Role_Default_role__c)) {
            String defaultRoleLabel = UTIL_Describe.getFieldLabel('npe01__Contacts_And_Orgs_Settings__c', 'npe01__Opportunity_Contact_Role_Default_role__c');
            ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, defaultRoleLabel + ' ' + Label.isARequiredField));
            isValid = false;
        }

        //for each invalid settings, add a message to the page
        if (!invalidOCRSettings.isEmpty()) {
            for (Integer i=0; i<invalidOCRSettings.size(); i+=2) {
                ApexPages.addMessage(
                    new ApexPages.message(
                        ApexPages.Severity.ERROR, 
                        String.format(Label.healthDetailsOCR, new List<String>{invalidOCRSettings[i], invalidOCRSettings[i+1]})
                    )
                );
            }
            isValid = false;
        }

        return isValid;
    }

    /*********************************************************************************************************
    * @description Returns the labels of settings that have invalid OCRs. 
    * Invalid OCR settings:
    * Soft Credit Contact Roles contains the Default Role
    * Default Role and HH Member Role are identical
    * Default Role and Matched Donor Role are identical
    * Default Role and Honoree Role are identical
    * Default Role and Notifaction Recipient Role are identical
    * HH Member Role and Matched Donor Role are identical
    * HH Member Role and Honoree Role are identical
    * HH Member Role and Notifaction Recipient Role are identical
    * @return List<String> Which fields have invalid settings.
    */
    public static List<String> getInvalidOCRSettings() {
        List<String> results = new List<String>();

        String defaultRole = STG_Panel.stgService.stgCon.npe01__Opportunity_Contact_Role_Default_role__c;
        String defaultRoleLabel = UTIL_Describe.getFieldLabel('npe01__Contacts_And_Orgs_Settings__c', 'npe01__Opportunity_Contact_Role_Default_role__c');
        String softCreditRoles = STG_Panel.stgService.stgHH.npo02__Soft_Credit_Roles__c;
        String softCreditRolesLabel = UTIL_Describe.getFieldLabel('npo02__Households_Settings__c', 'npo02__Soft_Credit_Roles__c');
        String hhMemberRole = STG_Panel.stgService.stgHH.npo02__Household_Member_Contact_Role__c;
        String hhMemberRoleLabel = UTIL_Describe.getFieldLabel('npo02__Households_Settings__c', 'npo02__Household_Member_Contact_Role__c');
        String matchedDonorRole = STG_Panel.stgService.stgHH.Matched_Donor_Role__c;
        String matchedDonorRoleLabel = UTIL_Describe.getFieldLabel('npo02__Households_Settings__c', UTIL_Namespace.StrTokenNSPrefix('Matched_Donor_Role__c'));
        String honoreeRole = STG_Panel.stgService.stgCon.Honoree_Opportunity_Contact_Role__c;
        String honoreeRoleLabel = UTIL_Describe.getFieldLabel('npe01__Contacts_And_Orgs_Settings__c', UTIL_Namespace.StrTokenNSPrefix('Honoree_Opportunity_Contact_Role__c'));
        String notificationRole = STG_Panel.stgService.stgCon.Notification_Recipient_Opp_Contact_Role__c;
        String notificationRoleLabel = UTIL_Describe.getFieldLabel('npe01__Contacts_And_Orgs_Settings__c', UTIL_Namespace.StrTokenNSPrefix('Notification_Recipient_Opp_Contact_Role__c'));

        //validation for npe01__Opportunity_Contact_Role_Default_role__c is done previously; don't check for identical values if this field is blank
        if (!String.isBlank(STG_Panel.stgService.stgCon.npe01__Opportunity_Contact_Role_Default_role__c)) {
            if (!String.isBlank(softCreditRoles)) {
                for (String softRole : softCreditRoles.split(';')) {
                    if (softRole.equals(defaultRole)) {
                        results.add(defaultRoleLabel);
                        results.add(softCreditRolesLabel);
                    }
                }
            }
            if (defaultRole == hhMemberRole) {
                results.add(defaultRoleLabel);
                results.add(hhMemberRoleLabel);
            }
            if (defaultRole == matchedDonorRole) {
                results.add(defaultRoleLabel);
                results.add(matchedDonorRoleLabel);
            }
            if (defaultRole == honoreeRole) {
                results.add(defaultRoleLabel);
                results.add(honoreeRoleLabel);
            }
            if (defaultRole == notificationRole) {
                results.add(defaultRoleLabel);
                results.add(notificationRoleLabel);
            }
        }
        
        //only check the rest if we have a non-blank household member role
        if (String.isBlank(hhMemberRole)) {
            return results;
        }
        if (hhMemberRole == matchedDonorRole) {
            results.add(hhMemberRoleLabel);
            results.add(matchedDonorRoleLabel);
        }
        if (hhMemberRole == honoreeRole) {
            results.add(hhMemberRoleLabel);
            results.add(honoreeRoleLabel);
        }
        if (hhMemberRole == notificationRole) {
            results.add(hhMemberRoleLabel);
            results.add(notificationRoleLabel);
        }
        return results;
    }
}