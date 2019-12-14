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
* @description Controller for the address verification settings page.
*/
public with sharing class STG_PanelADDRVerification_CTRL extends STG_Panel {

    /*******************************************************************************************************
    * @description The panel ID.
    */
    public override String idPanel() { return 'idPanelAddressVerification'; }
    
    /*******************************************************************************************************
    * @description The address verification settings.
    */
    public Addr_Verification_Settings__c addrVerifSettings { public get; public set; }
    
    /*******************************************************************************************************
    * @description The error message if the address verification settings cannot be saved.
    */
    public String apiSettingsErrorMessage { get; private set; }
    /*******************************************************************************************************
    * @description The success message if the address verification settings can be saved.
    */
    public String apiSettingsSaveMessage { get; private set; }

    /*******************************************************************************************************
    * @description The address (as a string) used to test if the address verification settings are valid.
    */
    public String apiTestEntry { get; set; }
    /*******************************************************************************************************
    * @description The result from testing the address verification settings.
    */
    public String apiTestResult { get; set; }

    /*******************************************************************************************************
    * @description Flag that determines if previously verified objects will be verified again.
    */
    public Boolean skipPreviouslyVerifiedObjects { get; set; } { skipPreviouslyVerifiedObjects = true; } 
    /*******************************************************************************************************
    * @description Flag that shows if batch address verification is running.
    */
    public boolean isRunningBatch { get; set; }
    /*******************************************************************************************************
    * @description Message that provides information about the batch address verification process.
    */
    public String batchVerifyMessage { get; set; }

    String[] validatorExclussions = new String[]{'ADDR_IValidator', 'ADDR_SmartyStreets_Gateway', 'ADDR_Validator', 'ADDR_Validator_REST'};
    
    /*******************************************************************************************************
    * @description The help text for the settings.
    */
    public String settingsHelpText { public get; public set; }    
    /*******************************************************************************************************
    * @description The help text for the batch.
    */
    public String validatorBatchHelpText { public get; public set; }
    
    /*******************************************************************************************************
    * @description Flag that provides information on whether batch address verification is enabled.
    */
    public Boolean batchVerifEnabled { public get; set; }
    
    /*******************************************************************************************************
    * @description Map that contains help text for each address verification service.
    */
    public Map<String, String> servicesHelpTextMap { public get; set; }
    /*******************************************************************************************************
    * @description Map that contains the URL endpoint for each address verification service.
    */
    public Map<String, String> servicesURLMap  { public get; set; }
    /*******************************************************************************************************
    * @description The name of the selected address verification service.
    */
    public string serviceName { public get; set; }
    
    /*******************************************************************************************************
    * @description The class that implements the selected address verification service.
    */
    public String validatorSelection;     
    /*******************************************************************************************************
    * @description Provides the name of the selected address verification class.
    * @return String The name of the class that implements the selected address verification service.
    */
    public String getValidatorSelection() { return validatorSelection; } 
    
    /*******************************************************************************************************
    * @description Sets the appropriate properties according to the selected address verification. 
    * @param validatorSelection The name of the selected address verification service. 
    * @return void
    */ 
    public void setValidatorSelection(String validatorSelection) {
        addrVerifSettings.Class__c = validatorSelection;
        clearDynamicFields();
        if(!String.isBlank(validatorSelection)) {
            Type classType = Type.forName(validatorSelection);
            if(classType != null) { 
                Object classInstance = classType.newInstance();
                if(classInstance instanceof ADDR_IValidator) {
                  this.validatorSelection = validatorSelection;
                  setDynamicFields((ADDR_IValidator)classInstance);
                }
            }
        } else serviceName = '';
    }
    
    /*******************************************************************************************************
    * @description Sets the value of those fields that change according to the selected address verification service. 
    */
    public void initDynamicFields() {
        addrVerifSettings = UTIL_CustomSettingsFacade.getAddressVerificationSettings();
        String validator = addrVerifSettings.Class__c;
        clearDynamicFields();
        if(validator != null) {           
            Type classType = Type.forName(validator);
            if(classType != null) { 
                Object classInstance = classType.newInstance();
                if(classInstance instanceof ADDR_IValidator) {
                   setDynamicFields((ADDR_IValidator)classInstance);
                   setValidatorSelection(addrVerifSettings.Class__c);
                }
            }
        }
    }
    
    private void clearDynamicFields() {
        validatorSelection = '';
        batchVerifEnabled = false;
        validatorBatchHelpText = '';
    }
    
    private void setDynamicFields(ADDR_IValidator validator) {
        batchVerifEnabled = validator.supportsBatch();
        validatorBatchHelpText = validator.getBatchHelpText();  
        serviceName = validator.getServiceName();
    }
    
    /*******************************************************************************************************
    * @description The address validator service options to provide on the drop-down list. 
    */
    public List<SelectOption> validators { public get; set; }
    
    /*******************************************************************************************************
    * @description Controller that initializes the class properties.
    */
    public STG_PanelADDRVerification_CTRL() {
        isRunningBatch = false;    
        servicesHelpTextMap = new Map<String, String>();
        servicesURLMap = new Map<String, String>();
    
        validators = new List<SelectOption>();
        validators.add(new SelectOption('', ''));
        //We have to use these strings because the underscode is treated like a special character in a LIKE 
        //clause, and if we try to escape it with a backslash it gets double-escaped automatically.
        String addrLikeClause = 'ADDR%';
        String testLikeClause = '%TEST';
        String ctrlLikeClause = '%CTRL';
        String tdtmLikeClause = '%TDTM';
        String batchLikeClause = '%Batch';
        String schedLikeClause = '%SCHED'; 
        ApexClass[] classes = [select Name, NamespacePrefix from ApexClass where Name like :addrLikeClause 
                  and (not Name like :testLikeClause) and (not Name like :ctrlLikeClause) 
                  and (not Name like :tdtmLikeClause) and (not Name like :batchLikeClause) 
                  and (not Name like :schedLikeClause) and (not Name IN :validatorExclussions)];
        UTIL_Debug.debug('****Validators: ' + JSON.serializePretty(classes));
        for(ApexClass c : classes) {
            Type classType = Type.forName(c.NamespacePrefix, c.Name);
            if(classType != null) {
                try {
                    Object classInstance = classType.newInstance();
                    if(classInstance instanceof ADDR_IValidator) {
                        validators.add(new SelectOption(c.Name, ((ADDR_IValidator)classInstance).getServiceName()));
                        servicesURLMap.put(((ADDR_IValidator)classInstance).getServiceName(), ((ADDR_IValidator)classInstance).getDefaultURL());
                    }
                } catch(exception ex) {
                    // we don't want to display an error, because we blindly pickup classes from any package that have ADDR in their title!
                }
            }
        }
    }

    /*******************************************************************************************************
    * @description Saves the address verification settings.
    * @return PageReference The page to redirect to. None, in this case.
    */
    public override PageReference saveSettings() {

        //if the user has blanked out the implementing class, disable verification and save settings
        if (string.isBlank(addrVerifSettings.Class__c) && addrVerifSettings.Enable_Automatic_Verification__c == true) {
            apiSettingsErrorMessage = Label.Settings_not_Saved + ': ' + Label.stgAddrMustChooseClass;
            return null;
        }

        try {
            apiTestResult = null;
                        
            encodeTokenIfNotEncoded(addrVerifSettings);
                        
            Boolean validationError = false;
            apiSettingsErrorMessage = '';
            if(addrVerifSettings.Class__c == 'ADDR_SmartyStreets_Validator' && String.isBlank(addrVerifSettings.Auth_ID__c)) {
                apiSettingsErrorMessage = Label.Addr_Id_Error;
                apiSettingsSaveMessage = null;
                validationError = true;
            }
            
            if(addrVerifSettings.Class__c == 'ADDR_SmartyStreets_Validator' && String.isBlank(addrVerifSettings.Auth_Token__c)) {
                String validationMessage = Label.Addr_Token_Error;
                apiSettingsErrorMessage = apiSettingsErrorMessage == null ? validationMessage : apiSettingsErrorMessage + validationMessage; 
                apiSettingsSaveMessage = null;
                validationError = true;
            }
            
            if(!validationError) {
                if (!string.isBlank(addrVerifSettings.Class__c)) {
                    ADDR_IValidator validator = (ADDR_IValidator)Type.forName(addrVerifSettings.Class__c).newInstance();
                    addrVerifSettings.Address_Verification_Endpoint__c = validator.getDefaultURL();
                    
                    if(addrVerifSettings.Address_Verification_Endpoint__c.contains('http:')) {
                       addrVerifSettings.Address_Verification_Endpoint__c = addrVerifSettings.Address_Verification_Endpoint__c.replaceFirst('http:', 'https:');
                    } else if(!addrVerifSettings.Address_Verification_Endpoint__c.contains('http')) {
                       addrVerifSettings.Address_Verification_Endpoint__c = 'https://' + addrVerifSettings.Address_Verification_Endpoint__c;
                    }
                }
                
                if(!Test.isRunningTest())
                    upsert addrVerifSettings;
                
                apiSettingsSaveMessage = Label.Saved + '\n';
                apiSettingsErrorMessage = null;
                setValidatorSelection(addrVerifSettings.Class__c);
                return super.saveSettings();
            }
        } catch (Exception e) {
            apiSettingsErrorMessage = Label.Settings_not_Saved + ': ' + e.getMessage();
        }
        return null;
    }
    
    /*******************************************************************************************************
    * @description Cancels the current modification of class properties.
    * @return PageReference The page to redirect to.
    */
    public override PageReference cancelEdit() {
        apiSettingsErrorMessage = null;
        apiSettingsSaveMessage = null;
        apiTestEntry = null;
        apiTestResult = null;
        initDynamicFields();
        return super.cancelEdit();
    }

    /*******************************************************************************************************
    * @description Tests the selected address verification service with the settings provided.
    * @return The page to redirect to. None, in this case.
    */
    public PageReference testApi() {
        apiTestResult = strVerifyAddressVerificationService(addrVerifSettings, apiTestEntry);
        return null;
    }
    
    /*******************************************************************************************************
    * @description Inspects the current address settings. Used by Health Check.
    * @param ctrl The health check controller.
    * @return void
    */    
    public static void verifyAddressVerificationService(STG_PanelHealthCheck_CTRL ctrl) {
        Addr_Verification_Settings__c avs = UTIL_CustomSettingsFacade.getAddressVerificationSettings();
        if (avs.Class__c == 'ADDR_SmartyStreets_Validator') {
            string strSetting = Schema.Sobjecttype.Addr_Verification_Settings__c.getLabel(); 
            string strResult = strVerifyAddressVerificationService(avs, '98005');
            string strStatus = strResult.contains('is valid') ? STG_PanelHealthCheck_CTRL.statusSuccess : STG_PanelHealthCheck_CTRL.statusError;
            string strSolution = strStatus == STG_PanelHealthCheck_CTRL.statusSuccess ? strResult : 
                string.format(system.Label.healthSolutionEditSetting, new string[]{strSetting, Label.stgNavHouseholds, Label.stgNavAddressVerification});
            if (strStatus == STG_PanelHealthCheck_CTRL.statusSuccess) strResult = null;
            ctrl.createDR(strSetting, strStatus, strResult, strSolution);
        } 
    }
    
    // verify the current address settings - used both by the UI panel, as well as Health Check.    
    private static string strVerifyAddressVerificationService(Addr_Verification_Settings__c addrVerifSettings, string strZip) {
        string strResult;
        Addr_Verification_Settings__c avs = UTIL_CustomSettingsFacade.getAddressVerificationSettings();
        if (addrVerifSettings.Class__c == 'ADDR_SmartyStreets_Validator') {
            try {
                encodeTokenIfNotEncoded(addrVerifSettings);
                ADDR_SmartyStreets_Gateway la = new ADDR_SmartyStreets_Gateway(addrVerifSettings);
                ADDR_SmartyStreets_Gateway.ZipResponse sz = la.verifyZip(strZip);
                strResult = Label.Addr_Valid_Key_Pair; //no exception thrown when deserializing it
            } catch (Exception e) {
                if(!(avs.Class__c == 'ADDR_SmartyStreets_Validator') || String.isBlank(addrVerifSettings.Auth_ID__c) 
                || String.isBlank(addrVerifSettings.Auth_Token__c)) {
                    strResult = Label.Addr_Verify_Settings;
                } else if(e.getMessage().startsWith('no protocol')) {
                    strResult = Label.Addr_Verify_Endpoint;
                } else if(e.getMessage().startsWith('Unauthorized endpoint')) {
                    strResult = Label.Addr_Unauthorized_Endpoint;
                } else {
                    strResult = e.getMessage();
                }
            }
        }
        return strResult;
    }
    
    private static void encodeTokenIfNotEncoded(Addr_Verification_Settings__c addrVerifSettings) {
        //SmartyStreets' unencoded token is always 88 chars long
        if(addrVerifSettings.Class__c == 'ADDR_SmartyStreets_Validator' && !String.isBlank(addrVerifSettings.Auth_Token__c) 
        && addrVerifSettings.Auth_Token__c.length() == 88)
            addrVerifSettings.Auth_Token__c = EncodingUtil.urlEncode(addrVerifSettings.Auth_Token__c, 'UTF-8');
    }

    /*******************************************************************************************************
    * @description Verify all non-verified address records, if the selected services supports it.
    * @return PageReference The page to redirect to. None, in this case.  
    */
    public PageReference batchVerify() {
        String classToRunName = String.valueOf(addrVerifSettings.get('Class__c'));
        if(!String.isBlank(classToRunName)) {
            Type classType = Type.forName(classToRunName);
            if(classType != null) {     
                Object classInstance = classType.newInstance();
                if(classInstance instanceof ADDR_IValidator) {
                    ADDR_IValidator validator = (ADDR_IValidator)classInstance;
                    if(validator.supportsBatch()) {
                        ADDR_Validator_Batch b = new ADDR_Validator_Batch(skipPreviouslyVerifiedObjects);
                        Database.executeBatch(b); 
                        isRunningBatch = true;
                        batchVerifyMessage = Label.Addr_Verifying;  
                    } else {
                        batchVerifyMessage = Label.Addr_No_Batch;   
                    }
                }
            } else {
                batchVerifyMessage = Label.Addr_No_Batch;
            }
        } else {
            batchVerifyMessage = Label.Addr_No_Batch;
        }
        return null;
    }
}