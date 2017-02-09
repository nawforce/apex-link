/*
    Copyright (c) 2014 Salesforce.org
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
* @description Controller for the Health Check wizard
*/
public without sharing class STG_PanelHealthCheck_CTRL extends STG_Panel {

    /*********************************************************************************************************
    * @description The panel's constructor 
    */
    public STG_PanelHealthCheck_CTRL() {
        isRunningDetector = false;
        listDR = new list<DetectResult>();
    }
    
    /*********************************************************************************************************
    * @description Returns the string Id of the Health Check panel. 
    */
    public override string idPanel() { return 'idPanelHealthCheck'; }
    
    /*********************************************************************************************************
    * @description Whether Health Check is currently running
    */
    public boolean isRunningDetector { get; set; }

    /*********************************************************************************************************
    * @description Whether Health Check has results to report  
    */
    public boolean hasResults { 
        get {
            return (listDR != null && listDR.size() > 0);
        }
    }
    
    /*********************************************************************************************************
    * @description Whether all tests passed Health Check  
    */
    public boolean isAllTestsPassed { 
        get {
            if (!hasResults)
                return false;
            for (DetectResult dr : listDR) {
                if (dr.strStatus == statusError)
                    return false;
            }
            return true;
        }
    }
    
    /*********************************************************************************************************
    * @description How long did Health Check take to run
    */
    public string strTime { get; set; }
    
    /*********************************************************************************************************
    * @description The current time  
    */
    public string strNow {
        get {
            return datetime.now().format();  // this will put it in the time zone of the current user.
        }
    }
    
    /*********************************************************************************************************
    * @description Action Method to run Health Check
    * @return null  
    */
    public PageReference runDetector() { 
        isRunningDetector = true;
        listDR.clear();
        runAllDetections();
        return null;
    }
    
    /*********************************************************************************************************
    * @description The list of DetectResults from Health Check  
    */
    public list<DetectResult> listDR { get; private set; }
    
    /*********************************************************************************************************
    * @description Used to report back the results of each Health Check test
    */
    public class DetectResult {
        public string strName { get; set; }
        public string strStatus { get; set; }
        public string strDetails { get; set; }
        public string strSolution { get; set; }
    }
    
    /*******************************************************************************************************
    * @description status value for Success
    */ 
    public static final String statusSuccess = 'Success';

    /*******************************************************************************************************
    * @description status value for Warning
    */ 
    public static final String statusWarning = 'Warning';

    /*******************************************************************************************************
    * @description status value for Error
    */ 
    public static final String statusError = 'Error';

    /*********************************************************************************************************
    * @description Creates and Stores a DetectResult
    * @param strName The test name
    * @param strStatus The status ('Success', 'Error', 'Warning')
    * @param strDetails The error details
    * @param strSolution The suggested solution to the problem
    * @return void  
    */
    public void createDR(string strName, string strStatus, string strDetails, string strSolution) {
        DetectResult dr = new DetectResult();
        dr.strName = strName;
        dr.strStatus = strStatus;
        dr.strDetails = strDetails;
        dr.strSolution = strSolution;
        if (strStatus == statusSuccess && listDR.size() > 0)
            listDR.add(0, dr);
        else
            listDR.add(dr);
    }    

    /*********************************************************************************************************
    * @description calls all of the specific detection routines
    */
    private void runAllDetections() {
        
        DateTime dtStart = system.now();
        
        verifyRecordTypes(); 
        verifyAccountModel(); 
        verifyAccountData();
        verifyAccountData2();
        verifyContactData();
        verifyNoOrphanedHouseholds();
        verifyNoOrphanedOne2OneAccounts();
        verifyReports(); 
        verifyFields(); 
        STG_PanelADDRVerification_CTRL.verifyAddressVerificationService(this);
        STG_PanelPaymentMapping_CTRL.verifyPaymentMappings(this);
        STG_PanelUserRollup_CTRL.verifyUserRollups(this);
        STG_PanelRDCustomFieldMapping_CTRL.verifyRDMappings(this);
        STG_PanelRDCustomInstallment_CTRL.verifyRDSaving(this);
        STG_PanelRelAuto_CTRL.verifyAutoCreateRels(this);
        STG_PanelRelReciprocal_CTRL.verifyRelLookups(this);
        STG_PanelOppRollups_CTRL.verifyFiscalYearRollups(this);
        STG_PanelOpps_CTRL.verifyOpportunityStageForPaymentAutoCloseIsActiveClosedWon(this);
        verifyScheduledJobs();
        ERR_Notifier.verifyErrorSettings(this);
        verifyOppContactRoles();
        verifyOppContactRolesSettings();
        verifyTriggerHandlers();
        verifyNoMissingOppPayments();
        verifyNoDuplicateContactRoles();
        verifyLevels();
        
        DateTime dtEnd = system.now();
        double msec = dtEnd.getTime() - dtStart.getTime();
        strTime = string.valueOf(double.valueOf(msec / 1000));
    }
     
    /*********************************************************************************************************
    * @description Verifies settings are consistent with the chosen Account Model 
    */
    private void verifyAccountModel() {
        string strAccModel = UTIL_Describe.getFieldLabel('npe01__Contacts_And_Orgs_Settings__c', 'npe01__Account_Processor__c');
        
        if (CAO_Constants.isHHAccountModel()) {
            if (STG_Panel.stgService.stgHH.npo02__Household_Rules__c != HH_Households.NO_HOUSEHOLDS_PROCESSOR) {
                string strHHRules = UTIL_Describe.getFieldLabel('npo02__Households_Settings__c', 'npo02__Household_Rules__c');
                createDR(strHHRules, statusError, label.healthDetailsHHAccountModel, 
                    string.format(label.healthSolutionEditSetting, new string[]{strHHRules, Label.stgNavPeople, Label.stgNavHouseholds} ));
                return;                 
            }           
        } else if (CAO_Constants.isOneToOne()) {
            // nothing to check         
        } else if (CAO_Constants.isBucketModel()) {
            // nothing to check         
        } else {
            createDR(strAccModel, statusError, label.healthDetailsAccountModel, 
                string.format(label.healthSolutionEditSetting, new string[]{strAccModel, Label.stgNavPeople, Label.stgNavAccountModel} ));
            return;         
        }
        createDR(strAccModel, statusSuccess, null, STG_Panel.stgService.stgCon.npe01__Account_Processor__c);
    }
    
    /*********************************************************************************************************
    * @description Verifies Account data is consistent with the chosen Account Model 
    */
    private void verifyAccountData() { 
        integer cBucket = 0;
        integer cOne2One = 0;
        integer cHH = 0;
        boolean isMixedAccs = false;
        
        try {       
            cBucket = [select count() from Account where npe01__SYSTEM_AccountType__c = :CAO_Constants.BUCKET_ORGANIZATION_TYPE limit 9999];
            cOne2One = [select count() from Account where npe01__SYSTEM_AccountType__c = :CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE limit 9999];
            cHH = [select count() from Account where npe01__SYSTEM_AccountType__c = :CAO_Constants.HH_ACCOUNT_TYPE limit 9999];
            if (cBucket > 0 && !CAO_Constants.isBucketModel())
                isMixedAccs = true;     
            if (cOne2One > 0 && !CAO_Constants.isOneToOne())
                isMixedAccs = true;     
            if (cHH > 0 && !CAO_Constants.isHHAccountModel())
                isMixedAccs = true;     
            
            string strInfo = '';
            if (isMixedAccs) {
                strInfo = string.format(Label.healthDetailsAccountProcessor, new string[]{UTIL_CustomSettingsFacade.getContactsSettings().npe01__Account_Processor__c});
            }
            strInfo += '<BR/>' + CAO_Constants.BUCKET_ORGANIZATION_TYPE + ': ' + cBucket + (cBucket == 9999 ? '+' : '');
            strInfo += '<BR/>' + CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE + ': ' + cOne2One + (cOne2One == 9999 ? '+' : '');
            strInfo += '<BR/>' + CAO_Constants.HH_ACCOUNT_TYPE + ': ' + cHH + (cHH == 9999 ? '+' : '');        
            
            createDR(Label.healthLabelAccountModelData, isMixedAccs ? statusError : statusSuccess,  isMixedAccs ? strInfo : null, 
               isMixedAccs ? label.healthSolutionAccountModel : strInfo);        
        } catch (exception ex) {
            createDR(Label.healthLabelAccountModelData, statusError,  ex.getMessage(), null);                   
        }    
    }

    /*********************************************************************************************************
    * @description Verifies Account system fields match account record types
    */
    private void verifyAccountData2() { 
        integer cOne2One = 0;
        integer cHH = 0;
        string strError = '';

        try {                        
            ID rectype = STG_Panel.stgService.stgCon.npe01__HH_Account_RecordTypeID__c;
            if (rectype != null) {
                cHH = [select count() from Account where RecordTypeId = :rectype and
                    (npe01__SYSTEM_AccountType__c != :CAO_Constants.HH_ACCOUNT_TYPE or npe01__SYSTEMISINDIVIDUAL__c = false)
                    limit 9999]; 
                if (cHH > 0) {
                    strError += string.format(Label.healthDetailsAccountRTIssue, new string[]{string.valueOf(cHH), CAO_Constants.HH_ACCOUNT_TYPE});                
                }
            }
            rectype = STG_Panel.stgService.stgCon.npe01__One_to_One_RecordTypeID__c;
            if (rectype != null) {
                cOne2One = [select count() from Account where RecordTypeId = :rectype and
                    (npe01__SYSTEM_AccountType__c != :CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE or npe01__SYSTEMISINDIVIDUAL__c = false)
                    limit 9999];
                if (cOne2One > 0) {
                    if (cHH > 0) strError += '<BR/>';
                    strError += string.format(Label.healthDetailsAccountRTIssue, new string[]{string.valueOf(cOne2One), CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE});                
                }
            }
            
            if (cHH > 0 || cOne2One > 0) {
                createDR(Label.healthLabelAccountModelData, statusError, strError, Label.healthSolutionAccountRTIssue);
            } else {          
                createDR(Label.healthLabelAccountModelData, statusSuccess, null, Label.healthLabelAccountRTIssueValid);
            }            
        } catch (exception ex) {
            createDR(Label.healthLabelAccountModelData, statusError,  ex.getMessage(), null);                   
        }    
    }

    /*********************************************************************************************************
    * @description Verifies that all Contacts have an account  
    */
    private void verifyContactData() {
        try {
            integer cContact = [select Count() from Contact where AccountId = null limit 9999];   
            if (cContact > 0) {
                createDR(Label.healthLabelContactData, statusError, 
                   string.format(Label.healthDetailsContactData, new string[]{string.valueOf(cContact)}), label.healthSolutionContactData);                 
            } else {
                createDR(Label.healthLabelContactData, statusSuccess, null, null); 
            }  
        } catch (exception ex) {
            createDR(Label.healthLabelContactData, statusError,  ex.getMessage(), null);                   
        }    
    }
    
    /*********************************************************************************************************
    * @description Verifies there are no Households Accounts and Objects with no Contacts 
    */
    private void verifyNoOrphanedHouseholds() {
        try {
            integer cHHObj = [select Count() from npo02__Household__c 
                where Number_of_Household_Members__c = 0 limit 9999];
                //where Id not in (select npo02__Household__c from Contact where npo02__Household__c <> null) limit 9999];        
            integer cHHAcc = [select Count() from Account 
                where npe01__SYSTEM_AccountType__c = :CAO_Constants.HH_ACCOUNT_TYPE and 
                    Number_of_Household_Members__c = 0 limit 9999];
                    //Id not in (select AccountId from Contact where AccountId <> null and Account.npe01__SYSTEM_AccountType__c = :CAO_Constants.HH_ACCOUNT_TYPE) limit 9999];        
            if (cHHObj > 0) {
                createDR(Label.healthLabelHHObjData, statusError, 
                    string.format(Label.healthDetailsHHObNoContacts, new string[]{string.valueOf(cHHObj)}),
                    Label.healthSolutionHHObjNoContacts);                 
            } 
            if (cHHAcc > 0) {
                createDR(Label.healthLabelHHAccData, statusError, 
                    string.format(Label.healthDetailsHHActNoContacts, new string[]{string.valueOf(cHHAcc)}),
                    Label.healthSolutionHHAccNoContacts);                 
            }         
            if (cHHObj == 0 && cHHAcc == 0) {
                createDR(Label.healthLabelHHData, statusSuccess, null, null); 
            }  
        } catch (exception ex) {
            createDR(Label.healthLabelHHAccData, statusError,  ex.getMessage(), null);                   
        }    
    }

    /*********************************************************************************************************
    * @description Verifies there are no 1:1 Accounts with no Contacts 
    */
    private void verifyNoOrphanedOne2OneAccounts() {
        try {
            integer cAcc = [select Count() from Account 
                where Account.npe01__SYSTEM_AccountType__c = :CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE and Account.npe01__One2OneContact__c = null limit 9999];
                    //Id not in (select AccountId from Contact where AccountId <> null and Account.npe01__SYSTEM_AccountType__c = :CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE) limit 9999];        
            if (cAcc > 0) {
                createDR(Label.healthLabelAccountData, statusError, 
                    string.format(Label.healthDetailsAccOne2OneNoContacts, new string[]{string.valueOf(cAcc)}),
                    Label.healthSolutionAccOne2OneNoContacts);                 
            } else {
                createDR(Label.healthLabelAccountData, statusSuccess, null, null); 
            }  
        } catch (exception ex) {
            createDR(Label.healthLabelAccountData, statusError,  ex.getMessage(), null);                   
        }    
    }

    /*********************************************************************************************************
    * @description Verifies all reports exist that settings use 
    */
    private void verifyReports() {
        string rptId = null;
        
        // Household Mailing List Report
        rptId = STG_Panel.stgService.stgHH.npo02__Household_Mailing_List_ID__c;
        string strField = UTIL_Describe.getFieldLabel('npo02__Households_Settings__c', 'npo02__Household_Mailing_List_ID__c');
        if (rptId != null && rptId != '') { 
            list<Report> listRpt = [select Id from Report where Id = :rptId];
            if (listRpt.size() == 0) {
                createDR(strField, statusError, 
                    string.format(Label.healthDetailsBadReportId, new string[]{rptId}),
                    string.format(label.healthSolutionEditSetting, new string[]{strField, Label.stgNavPeople, Label.stgNavHouseholds}));        
                return;
            }
        } else {
            list<Report> listRpt = [select id from Report where DeveloperName = 'NPSP_Campaign_Household_Mailing_List'];
            if (listRpt.size() == 0) {
                createDR(strField, statusError, Label.healthDetailsMailingListReportMissing, 
                   string.format(Label.healthSolutionMailingListReportMissing, new string[]{Label.stgNavPeople, Label.stgNavHouseholds}));
               return;
            }
        }
        createDR(strField, statusSuccess, null, rptId);

    }
    
    /*********************************************************************************************************
    * @description Checks all fields that are used in settings 
    */
    private void verifyFields() {
    
        // check Relationship Gender field on Contact
        string strSetting = UTIL_Describe.getFieldLabel('npe4__Relationship_Settings__c', 'npe4__Gender_Field__c');
        string strField = STG_Panel.stgService.stgRel.npe4__Gender_Field__c;
        if (strField != null && !UTIL_Describe.isValidField('Contact', strField)) {
            createDR(strSetting, statusError, 
                string.format(Label.healthDetailsGenderField, new string[]{strField}),
                string.format(label.healthSolutionEditSetting, new string[]{strSetting, Label.stgNavRelationships, Label.stgNavRelationships}));        
        } else {
            createDR(strSetting, statusSuccess, null, strField == null ? label.healthLabelNone : strField);
        }        
    }
    
    /*********************************************************************************************************
    * @description Checks all settings that contain recordType ID's to make sure they are valid. 
    */
    private void verifyRecordTypes() {
    
        checkRecordTypeId(Account.sObjectType, STG_Panel.stgService.stgCon.npe01__HH_Account_RecordTypeID__c, 
            'npe01__Contacts_And_Orgs_Settings__c', 'npe01__HH_Account_RecordTypeID__c', Label.stgNavPeople, Label.stgNavAccountModel);
        checkRecordTypeId(Account.sObjectType, STG_Panel.stgService.stgCon.npe01__One_to_One_RecordTypeID__c, 
            'npe01__Contacts_And_Orgs_Settings__c', 'npe01__One_to_One_RecordTypeID__c', Label.stgNavPeople, Label.stgNavAccountModel);        
        checkRecordTypeId(Opportunity.sObjectType, STG_Panel.stgService.stgRD.npe03__Record_Type__c, 
            'npe03__Recurring_Donations_Settings__c', 'npe03__Record_Type__c', Label.stgNavRecurringDonations, Label.stgNavRecurringDonations);       
        checkRecordTypeMulti(Contact.sObjectType, STG_Panel.stgService.stgHH.npo02__Household_Creation_Excluded_Recordtypes__c, true,
            'npo02__Households_Settings__c', 'npo02__Household_Creation_Excluded_Recordtypes__c', Label.stgNavPeople, Label.stgNavHouseholds);
        checkRecordTypeMulti(Contact.sObjectType, STG_Panel.stgService.stgHH.npo02__Household_OCR_Excluded_Recordtypes__c, true,
            'npo02__Households_Settings__c', 'npo02__Household_OCR_Excluded_Recordtypes__c', Label.stgNavDonations, Label.stgNavContactRoles);            
        checkRecordTypeMulti(Opportunity.sObjectType, STG_Panel.stgService.stgHH.npo02__Excluded_Account_Opp_Rectypes__c, true,
            'npo02__Households_Settings__c', 'npo02__Excluded_Account_Opp_Rectypes__c', Label.stgNavDonations, Label.stgNavDonorStatistics);
        checkRecordTypeMulti(Opportunity.sObjectType, STG_Panel.stgService.stgHH.npo02__Excluded_Contact_Opp_Rectypes__c, true,
            'npo02__Households_Settings__c', 'npo02__Excluded_Contact_Opp_Rectypes__c', Label.stgNavDonations, Label.stgNavDonorStatistics);
        checkRecordTypeMulti(Opportunity.sObjectType, STG_Panel.stgService.stgHH.npo02__Membership_Record_Types__c, true,
            'npo02__Households_Settings__c', 'npo02__Membership_Record_Types__c', Label.stgNavDonations, Label.stgNavMembership);   
            
        // avoid reusing the same recordtype in Account models
        string idHH = STG_Panel.stgService.stgCon.npe01__HH_Account_RecordTypeID__c;
        string idOne = STG_Panel.stgService.stgCon.npe01__One_to_One_RecordTypeID__c;
        if (idHH != null && idOne != null && idHH == idOne) {
            string strField = UTIL_Describe.getFieldLabel('npe01__Contacts_And_Orgs_Settings__c', 'npe01__One_to_One_RecordTypeID__c');            
            createDR(strField, statusError, label.healthDetailsDuplicateAcctRT, 
                string.format(label.healthSolutionEditSetting, new string[]{strField, Label.stgNavPeople, Label.stgNavAccountModel}));
        }         
    }

    /*********************************************************************************************************
    * @description Checks a setting that contains multiple recordType Id's in a semi-colon delimeted string
    * @param objectType The Object to check recordtypes against
    * @param strValues A semicolon separated string of values to check
    * @param isIds Whether the values are Ids or Names
    * @param strSettingObj The Settings object
    * @param strSettingField The Settings field
    * @param strTabLevel The parent tab the setting is on
    * @param strTabname The tab the setting is on
    * @return void
    */
    private void checkRecordTypeMulti(sObjectType objectType, string strValues, boolean isIds, string strSettingObj, string strSettingField, string strTabLevel, string strTabname) {
        if (strValues != null) {
            list<string> listStr = strValues.split(';',0);
            for (string strRT : listStr) {
                if (isIds)
                    checkRecordTypeId(objectType, strRT, strSettingObj, strSettingField, strTabLevel, strTabname);
                else
                    checkRecordTypeName(objectType, strRT, strSettingObj, strSettingField, strTabLevel, strTabname);              
            }
        } else {
            string strField = UTIL_Describe.getFieldLabel(strSettingObj, strSettingField);
            createDR(strField, statusSuccess, null, Label.healthLabelNone);                          
        }
    }

    /*********************************************************************************************************
    * @description Checks whether the recordType Id is valid, and logs an appropriate DetectResult
    * @param objectType The Object to check recordtypes against
    * @param rtId A recordtype Id
    * @param strSettingObj The Settings object
    * @param strSettingField The Settings field
    * @param strTabLevel The parent tab the setting is on
    * @param strTabname The tab the setting is on
    * @return void
    */
    private void checkRecordTypeId(sObjectType objectType, Id rtId, string strSettingObj, string strSettingField, string strTabLevel, string strTabname) {
        string strField = UTIL_Describe.getFieldLabel(strSettingObj, strSettingField);
        string strRT = UTIL_RecordTypes.GetRecordTypeName(objectType, rtId);
        if (rtId != null && strRT == null) {
            createDR(strField, statusError, 
                string.format(label.healthDetailsInvalidRecordtypeId, new string[]{rtId}),        
                string.format(label.healthSolutionEditSetting, new string[]{strField, strTabLevel, strTabname}));
        } else {
            if (rtId == null) strRT = Label.healthLabelNone;
            createDR(strField, statusSuccess, null, strRT);               
        }           
    }
    
    /*********************************************************************************************************
    * @description Checks whether the recordType Name is valid, and logs an appropriate DetectResult
    * @param objectType The Object to check recordtypes against
    * @param strRTName A recordtype name
    * @param strSettingObj The Settings object
    * @param strSettingField The Settings field
    * @param strTabLevel The parent tab the setting is on
    * @param strTabname The tab the setting is on
    * @return void
    */
    private void checkRecordTypeName(sObjectType objectType, string strRTName, string strSettingObj, string strSettingField, string strTabLevel, string strTabname) {
        string strField = UTIL_Describe.getFieldLabel(strSettingObj, strSettingField);
        if (strRTName != null && UTIL_RecordTypes.GetRecordTypeId(objectType, strRTName) == null) {
            createDR(strField, statusError, 
                string.format(label.healthDetailsInvalidRecordtypeName, new string[]{strRTName}),        
                string.format(label.healthSolutionEditSetting, new string[]{strField, strTabLevel, strTabname}));        
        } else {
            if (strRTName == null) strRTName = Label.healthLabelNone;
            createDR(strField, statusSuccess, null, strRTName);               
        }           
    }

    /*********************************************************************************************************
    * @description Verifies that our Master Scheduler jobs are actually scheduled, and that all of the entries are valid
    */
    private void verifyScheduledJobs() {
        try {        
            // make sure all default jobs are scheduduled
            List<CronTrigger> jobs = [select Id, CronJobDetail.Id from CronTrigger 
               where CronJobDetail.Name IN :UTIL_MasterSchedulableHelper.defaultScheduledJobs.keySet() and CronJobDetail.JobType = '7'];        
            if (jobs.size() != UTIL_MasterSchedulableHelper.defaultScheduledJobs.size()) {
                createDR(
                    Label.healthLabelScheduler, 
                    statusError, 
                    Label.healthDetailsNothingScheduled, 
                    label.healthSolutionNothingScheduled
                );
                return;            
            }
            createDR(Label.healthLabelScheduler, statusSuccess, null, label.healthLabelSchedulerValid);
        } catch (exception ex) {
            createDR(Label.healthLabelScheduler, statusError,  ex.getMessage(), null);                   
        }    
    }
    
    /*********************************************************************************************************
    * @description Verifies that no Opportunities have multiple primary contact roles, which would throw off opp rollups.
    */
    private void verifyOppContactRoles() {
        try {
            // detect duplicate primary ocr's
            // we understand this will fail if there are over 50k OCR's in they system,
            // but it is better than nothing, since for many orgs that will be fine.
            integer cOpp = 0;
            list<AggregateResult> listAR = [select count(Id), OpportunityId from OpportunityContactRole 
                 where IsPrimary=true group by OpportunityId having count(Id) > 1 limit 50000];
            cOpp = listAR.size();

            if (cOpp > 0) {
                createDR(label.healthLabelOCRCheck, statusError, 
                  string.format(label.healthDetailsOCRDupPrimary, new string[]{string.valueOf(cOpp)}), label.healthSolutionOCRDupPrimary);      
            } else {
                createDR(label.healthLabelOCRCheck, statusSuccess, null, label.healthDetailsOCRDupPrimaryValid);
            }    
        } catch (exception ex) {
            createDR(Label.healthLabelOCRCheck, statusError, ex.getMessage(), label.healthSolutionOCRException);                   
        }    
    }
    
    /*********************************************************************************************************
    * @description Verifies that no OCR settings are conflicting.
    */
    public void verifyOppContactRolesSettings() {
        List<String> invalidOCRSettings = STG_PanelContactRoles_CTRL.getInvalidOCRSettings();
        if (invalidOCRSettings.isEmpty()) {
            createDR(Label.healthLabelOCRSettings, statusSuccess, null, Label.healthDetailsOCRSettingsValid);
        } else {
            String invalidOCRErrors = '';
            for (Integer i=0; i<invalidOCRSettings.size(); i+=2) {
                invalidOCRErrors += String.format(Label.healthDetailsOCR, new List<String>{invalidOCRSettings[i], invalidOCRSettings[i+1]}) + '<br/>';
            }
            createDR(Label.healthLabelOCRSettings, statusError, invalidOCRErrors, Label.healthDetailsOCRSettingsResolution);
        }
    }

    /*********************************************************************************************************
    * @description Verifies that the default trigger handlers exist in the Trigger_Handler object, and warns
    * about any NPSP triggers marked as Async.
    */
    private void verifyTriggerHandlers() {
        try {
            List<Trigger_Handler__c> listHandlersDefault = TDTM_DefaultConfig.getDefaultRecords();
            List<Trigger_Handler__c> listHandlersCurrent = [select Asynchronous__c, Class__c, Load_Order__c, Object__c, Active__c, 
                    Trigger_Action__c, User_Managed__c from Trigger_Handler__c];
            string strInfo = '<ul class="clsDRDetailsUL">';
            
            for (Trigger_Handler__c defaultHandler : listHandlersDefault) {
                Boolean alreadyExists = false;
                integer cExactMatch = 0;
                for (Trigger_Handler__c existingHandler : listHandlersCurrent) {
                    if (defaultHandler.Class__c == existingHandler.Class__c && defaultHandler.Object__c == existingHandler.Object__c) {
                        alreadyExists = true;
    
                        // Determine if order, action, active, async has changed
                        if (defaultHandler.Load_Order__c != existingHandler.Load_Order__c || 
                            defaultHandler.Trigger_Action__c != existingHandler.Trigger_Action__c ||
                            defaultHandler.Active__c != existingHandler.Active__c ||
                            defaultHandler.Asynchronous__c != existingHandler.Asynchronous__c) {
                            
                            strInfo += '<li>';
                            strInfo += string.format(label.healthDetailsTriggerHandlerMismatch, new string[]{defaultHandler.Class__c, defaultHandler.Object__c});
                            strInfo += '</li>';
                        } else {
                            cExactMatch++;
                        }
                    }
                }
                if (!alreadyExists) {
                    strInfo += '<li>';
                    strInfo += string.format(label.healthDetailsTriggerHandlerMissing, new string[]{defaultHandler.Class__c, defaultHandler.Object__c});
                    strInfo += '</li>';
                } else if (cExactMatch > 1) {
                    strInfo += '<li>';
                    strInfo += string.format(label.healthDetailsTriggerHandlerDuplicate, new string[]{defaultHandler.Class__c, defaultHandler.Object__c});
                    strInfo += '</li>';
                }
            }    
            if (strInfo != '<ul class="clsDRDetailsUL">') { 
                strInfo += '</ul>';
                createDR(Label.healthLabelTriggerHandlerCheck, statusWarning, strInfo, label.healthSolutionTriggerHandlerMismatch);
            } else
                createDR(Label.healthLabelTriggerHandlerCheck, statusSuccess, null, label.healthLabelTriggerHandlersValid);
                                
        } catch (exception ex) {
            createDR(Label.healthLabelTriggerHandlerCheck, statusError, ex.getMessage(), null);                   
        }    
    }
    
    /*********************************************************************************************************
    * @description Verifies that there are no Opportunities that are missing payment records
    */
    public void verifyNoMissingOppPayments() {
        if (!UTIL_CustomSettingsFacade.getContactsSettings().npe01__Payments_Enabled__c)
            return;
        try {
            set<string> setOppTypeNoPayment = PMT_PaymentCreator.setOppTypeNoPayment;
            set<id> setOppRecordTypeIdNoPayment = PMT_PaymentCreator.setOppRecordTypeIdNoPayment;
            string soql = 'select count() from Opportunity where npe01__Number_of_Payments__c = 0 and Amount > 0 ' +
                ' and (isWon = true or isClosed = false) ' +
                ' and npe01__Do_Not_Automatically_Create_Payment__c = false ' +
                ' and  Type not in :setOppTypeNoPayment ';

            SObjectField OppRecordTypeId = Schema.sObjectType.Opportunity.fields.getMap().get('RecordTypeId');
            if (OppRecordTypeId != null)
                soql += ' and RecordTypeId not in :setOppRecordTypeIdNoPayment ';

            soql += 'LIMIT 1001';

            integer cMissing = database.countQuery(soql);
            if (cMissing > 0) {
                String sMissing;

                if (cMissing > 1000) {
                    sMissing = '1000+';
                } else {
                    sMissing = String.valueOf(cMissing);
                }

                createDR(Label.healthLabelOppPayments, statusWarning, string.format(Label.healthDetailsMissingOppPayments, new string[]{sMissing}), label.healthSolutionMissingOppPayments);
            } else {
                createDR(Label.healthLabelOppPayments, statusSuccess, null, label.healthLabelNoMissingOppPayments);
            }            
        } catch (exception ex) {
            createDR(Label.healthLabelOppPayments, statusError, ex.getMessage(), null);                   
        }    
        
    }

    /**
     * @description Detect if any opportunities exists where the same contact
     * is associated with that opportunity multiple times through multiple
     * opportunity contact role records.
     */
     public void verifyNoDuplicateContactRoles() {
        try {
            Set<String> softCreditRoles = RLLP_OppRollup_UTIL.softCreditRoles;

            List<AggregateResult> results = [
                SELECT
                    COUNT(Id),
                    OpportunityId,
                    ContactId
                FROM OpportunityContactRole
                WHERE Opportunity.IsWon = true
                AND Role IN :softCreditRoles
                GROUP BY OpportunityId, ContactId
                HAVING Count(Id) > 1
                LIMIT 1
            ];

            List<String> quotedSoftCreditRoles = new List<String>();

            for (String role : softCreditRoles) {
                quotedSoftCreditRoles.add('\'' + String.escapeSingleQuotes(role) + '\'');
            }

            if (results.size() > 0) {
                createDR(
                    Label.healthLabelDuplicateContactRoles,
                    statusWarning,
                    Label.healthDetailsDuplicateContactRoles,
                    String.format(
                        Label.healthSolutionDuplicateContactRoles,
                        new List<String>{
                            String.join(quotedSoftCreditRoles, ', ')
                        }
                    )
                );
            } else {
                createDR(
                    Label.healthLabelDuplicateContactRoles,
                    statusSuccess,
                    null,
                    label.healthLabelNoDuplicateContactRoles
                );
            }
        } catch (Exception e) {
            createDR(
                Label.healthLabelDuplicateContactRoles,
                statusError,
                e.getMessage(),
                null
            );
        }
     }

    /*********************************************************************************************************
    * @description Verifies that all Level records refer to existing fields of the correct type
    */
    public void verifyLevels() {
        try {
            // get all our levels for this object type
            list<Level__c> listAllLvls = [select Id, Name, Target__c, Source_Field__c, Level_Field__c, 
                Previous_Level_Field__c, Active__c, Minimum_Amount__c, Maximum_Amount__c
                from Level__c
                order by Level_Field__c asc, Minimum_Amount__c asc];
            
            integer cErrors = 0;
            for (Level__c lvl : listAllLvls) {
                for (integer i = 0; i < 3; i++) {
                    String strField;
                    if (i == 0)
                        strField = lvl.Source_Field__c;
                    else if (i == 1)
                        strField = lvl.Level_Field__c;
                    else if (i == 2)
                        strField = lvl.Previous_Level_Field__c;
                        
                    // ensure the field exists
                    if (strField != null && !UTIL_Describe.isValidField(lvl.Target__c, strField)) {
                        cErrors++; 
                        createDR(Label.healthLabelVerifyLevels, statusError, 
                            string.format(Label.healthDetailsInvalidLevelField, new string[]{lvl.Name, strField, lvl.Target__c}),
                            string.format(label.healthSolutionInvalidLevelField, new string[]{lvl.Name}));
                    } 
                    // ensure the datatype is valid
                    else if (strField != null) {
                        Schema.Displaytype dt = UTIL_Describe.getFieldDisplayType(lvl.Target__c, strField);
                        if (i == 0 && !UTIL_Describe.canDisplaytypesCopy(dt, Schema.DisplayType.Double)) {
                            cErrors++; 
                            createDR(Label.healthLabelVerifyLevels, statusError, 
                                string.format(Label.healthDetailsInvalidFieldNumeric, new string[]{lvl.Name, strField, lvl.Target__c}),
                                string.format(label.healthSolutionInvalidLevelField, new string[]{lvl.Name}));
                        }
                        if (i > 0 && !UTIL_Describe.canDisplaytypesCopy(dt, Schema.DisplayType.Reference)) {
                            cErrors++; 
                            createDR(Label.healthLabelVerifyLevels, statusError, 
                                string.format(Label.healthDetailsInvalidLevelFieldReference, new string[]{lvl.Name, strField, lvl.Target__c}),
                                string.format(label.healthSolutionInvalidLevelField, new string[]{lvl.Name}));
                        }
                    }       
                }
            }

            if (cErrors == 0) {
                createDR(Label.healthLabelVerifyLevels, statusSuccess, null, label.healthLabelVerifyLevelsSuccess);
            }
            
        } catch (exception ex) {
            createDR(Label.healthLabelVerifyLevels, statusError, ex.getMessage(), null);                   
        }    
    }
}