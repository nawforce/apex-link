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
* @description Controller for the User Defined Rollups panel.
*/
public with sharing class STG_PanelOppRollups_CTRL extends STG_Panel {

    /*********************************************************************************************************
    * @description Returns the string Id of the User Defined Rollups panel. 
    */
    public override string idPanel() { return 'idPanelOppRollup'; }
    
    /*********************************************************************************************************
    * @description Verifies that if Use Fiscal Years is turned on, Custom Fiscal Years are not in place - called by HealthCheck 
    * @param ctrl The Health Check panel controller
    * @return void
    */
    public static void verifyFiscalYearRollups(STG_PanelHealthCheck_CTRL ctrl) {
        string strSetting = UTIL_Describe.getFieldLabel('npo02__Households_Settings__c', 'npo02__Use_Fiscal_Year_for_Rollups__c');    	
        if (STG_Panel.stgService.stgHH.npo02__Use_Fiscal_Year_for_Rollups__c == true) {
            String fyQuery = 'select id from FiscalYearSettings where IsStandardYear = false';            
            List<SObject> fySettings = new List<SObject>();
            fySettings = Database.query(fyQuery);
            integer hasCustomFYRecord = fySettings.size();
                
            //if org has customFY records, disable FY settings
            if (hasCustomFYRecord > 0) {
                ctrl.createDR(strSetting, STG_PanelHealthCheck_CTRL.statusError, label.healthDetailsInvalidFiscalYears, label.healthSolutionInvalidFiscalYears);
                return;
            }            
        }
        ctrl.createDR(strSetting, STG_PanelHealthCheck_CTRL.statusSuccess, null, label.healthLabelFiscalYearsValid);            
    }        

    /**
     * @description A user friendly rendering of record type ids by record type
     * name for Excluded Account Opp RecTypes
     */
    static public String strExcludedAccountOppRecTypesReadOnly {
        get {
            if (strExcludedAccountOppRecTypesReadOnly == null) {
                strExcludedAccountOppRecTypesReadOnly = STG_Panel.getRecordTypeNamesFromPicklistString(
                    Opportunity.sObjectType,
                    STG_Panel.stgService.stgHH.npo02__Excluded_Account_Opp_Rectypes__c
                );
            }
            return strExcludedAccountOppRecTypesReadOnly;
        }
        private set;
    }

    /**
     * @description A user friendly rendering of record type ids by record type
     * name for Excluded Contact Opp RecTypes
     */
    static public String strExcludedContactOppRecTypesReadOnly {
        get {
            if (strExcludedContactOppRecTypesReadOnly == null) {
                strExcludedContactOppRecTypesReadOnly = STG_Panel.getRecordTypeNamesFromPicklistString(
                    Opportunity.sObjectType,
                    STG_Panel.stgService.stgHH.npo02__Excluded_Contact_Opp_Rectypes__c
                );
            }
            return strExcludedContactOppRecTypesReadOnly;
        }
        private set;
    }

}