/* 
    Copyright (c) 2012, Salesforce.org
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
* @date 2012 (2.0)
* @group Settings
* @description The controller for the Recurring Donations Custom Field Mapping panel 
*/
public with sharing class STG_PanelRDCustomFieldMapping_CTRL extends STG_Panel {
    
    /*********************************************************************************************************
    * @description Returns the string Id of the RD Field Mapping panel. 
    */
    public override string idPanel() { return 'idPanelRDFieldMap'; }
    
    /*********************************************************************************************************
    * @description temporary fm used by the page for creating a new fm.
    */
    public npe03__Custom_Field_Mapping__c fmNew {
        get {
            if (fmNew == null)
              fmNew = new npe03__Custom_Field_Mapping__c();
            return fmNew;
        }
        private set;
    }
    
    /*********************************************************************************************************
    * @description Action Method for going into Edit mode (to create a new fm) 
    * @return null
    */
    public PageReference newCustomFieldMapping() {
        isEditMode = true;
        return null;
    }

    /*********************************************************************************************************
    * @description Action Method to save the new fm to the database 
    * @return null
    */
    public PageReference createCustomFieldMapping() {  
    	
    	// validate first
    	if (!isValidMapping(fmNew.npe03__Recurring_Donation_Field__c, fmNew.npe03__Opportunity_Field__c)) {
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, system.label.npe03.RecurringDonationBadFieldMapping));
            return null;    		
    	}      
    	
        Savepoint sp = Database.setSavepoint();
        try {
        	fmNew.Name = 'FieldMapping-' + system.now();
            insert fmNew;
            isEditMode = false;
            resetFieldMappingSettings();
            fmNew = null;
        } catch(Exception e) {
            Database.rollback(sp);
            ERR_Handler.processError(e, ERR_Handler_API.Context.STTG);
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, e.getMessage()));
        }
        return null;
    }

    /*********************************************************************************************************
    * @description Returns whether the recurring donation and opp fields are compatible
    * @param RDField The Recurring Donation field
    * @param OppField The Opportunity field
    * @return boolean
    */
    private boolean isValidMapping(string RDField, string OppField){
        boolean isValid = false;
        
        Schema.Sobjectfield schemaRDField = schema.SObjectType.npe03__Recurring_Donation__c.fields.getMap().get(RDField);
        Schema.Sobjectfield schemaOppField = schema.SobjectType.Opportunity.fields.getMap().get(OppField);
        
        if (schemaRDField != null && schemaOppField != null) {
            Schema.DescribeFieldResult RDF = schemaRDField.getDescribe();
            Schema.DescribeFieldResult ODF = schemaOppField.getDescribe();                              
            if  (RDF.isAccessible() && ODF.isAccessible() && !ODF.isDeprecatedAndHidden() && ODF.isUpdateable() &&
                UTIL_Describe.canDisplaytypesCopy(RDF.getType(), ODF.getType()) ){
                isValid = true;                           
            }
        }
        return isValid;
    }

    /*********************************************************************************************************
    * @description Action Method to cancel out of creating a new fm.
    * @return null
    */
    public PageReference cancelCustomFieldMapping() {
        isEditMode = false;
        fmNew = null;
        return null;
    }
    
    /*********************************************************************************************************
    * @description Holds the Id to a field mapping to be deleted
    */
    public string fmIdDelete { get; set; }

    /*********************************************************************************************************
    * @description Action Method for deleting an existing field mapping
    * @return null
    */
    public PageReference deleteFm() {
        Savepoint sp = Database.setSavepoint(); 
        try {
            STG_SettingsManager_CTRL.idPanelCurrent = idPanel();        
            if (fmIdDelete != null) {
                npe03__Custom_Field_Mapping__c fm = new npe03__Custom_Field_Mapping__c(Id = fmIdDelete);
                delete fm;
                // force reload.
                resetFieldMappingSettings();
            }
        } catch(Exception e) {
            Database.rollback(sp);
            ERR_Handler.processError(e, ERR_Handler_API.Context.RD);
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, e.getMessage()));
        }
        return null;
    }

    /*********************************************************************************************************
    * @description Resets RD custom field mappings so that they are reloaded and the cached version isn't used.
    */
    public void resetFieldMappingSettings() {
        UTIL_ListCustomSettingsFacade.mapCustomFieldMappings = null;
        STG_Panel.stgService.listRDCustomFieldMapping = null;
        listRDCustomFieldMapping = null;
    }

    /*********************************************************************************************************
    * @description List of RD Custom Field Mappings, with developer names replaced with labels
    */
    public list<npe03__Custom_Field_Mapping__c> listRDCustomFieldMapping {
        get {
            if (listRDCustomFieldMapping == null) {
                listRDCustomFieldMapping = new list<npe03__Custom_Field_Mapping__c>();
                for (npe03__Custom_Field_Mapping__c fm : STG_Panel.stgService.listRDCustomFieldMapping) {
                    string strOppField = getFieldLabel('Opportunity', fm.npe03__Opportunity_Field__c);
                    string strRDField = getFieldLabel('npe03__Recurring_Donation__c', fm.npe03__Recurring_Donation_Field__c);
                    npe03__Custom_Field_Mapping__c fm2 = new npe03__Custom_Field_Mapping__c();
                    fm2.Id = fm.Id;
                    fm2.npe03__Opportunity_Field__c = strOppField;
                    fm2.npe03__Recurring_Donation_Field__c = strRDField;
                    listRDCustomFieldMapping.add(fm2);                                      
                }                
            } 
            return listRDCustomFieldMapping;           
        }
        private set;     
    }
    
    /*********************************************************************************************************
    * @description Returns the fields label.  This is a cover over describe call to handle fields that no longer exist.
    * @param strObj The Object
    * @param strField The Field
    * @return string
    */
    private string getFieldLabel(string strObj, string strField) {
        if (UTIL_Describe.isValidField(strObj, strField)) {
            return UTIL_Describe.getFieldLabel(strObj, strField);
        } else {
            return strField;
        }
    }

    /*********************************************************************************************************
    * @description Set of invalid fields for opp mappings
    */
    static set<string> setInvalidOppFields = new set<string>{'accountid', 'amount', 'campaignid', 'closedate', 'createdbyid', 
                                                   'createddate', 'fiscal', 'fiscalquarter', 'fiscalyear', 'forecastcategory', 
                                                   'stagename', 'npe03__recurring_donation__c', 'forecastcategoryname',
                                                   'forecastname', 'hasopportunitylineitem', 'id', 'isclosed', 'isdeleted', 'isprivate', 
                                                   'iswon', 'lastactivitydate', 'lastmodifiedbyid', 'lastmodifieddate', 'recordtypeid', 
                                                   'systemmodstamp', 'totalopportunityquantity'};        

    /*********************************************************************************************************
    * @description The SelectOption list of Opportunity Fields available for a new mapping
    */
    static public list<SelectOption> listSOOppFields {
        get {
            if (listSOOppFields == null) {
                listSOOppFields = new list<SelectOption>();
                //listSOOppFields.add(new SelectOption('', Label.stgLabelNone));
            
                // filter out opp fields already used
	            for (npe03__Custom_Field_Mapping__c fm : STG_Panel.stgService.listRDCustomFieldMapping) {   
	                setInvalidOppFields.add(fm.npe03__Opportunity_Field__c);   
	            }                 

                map<String, Schema.SObjectField> oppFieldTokenList = Schema.SObjectType.Opportunity.fields.getMap(); 
                for (string fieldName : oppFieldTokenList.keyset()) {                	
                	if (!setInvalidOppFields.contains(fieldName)) {                	
	                    Schema.DescribeFieldResult f = UTIL_Describe.getFieldDescribe('Opportunity', fieldname);
	                    if (f.isAccessible() && !f.isDeprecatedAndHidden() && f.isUpdateable() )
	                       listSOOppFields.add(new SelectOption(f.getName(), f.getLabel()));
                	}
                }
                listSOOppFields.sort();
            }
            return listSOOppFields;
        }
        private set;
    }

    /*********************************************************************************************************
    * @description The SelectOption list of Recurring Donation fields available for a new mapping 
    */
    static public list<SelectOption> listSORDFields {
        get {
            if (listSORDFields == null) {
                listSORDFields = new list<SelectOption>();
                //listSORDFields.add(new SelectOption('', Label.stgLabelNone));

                map<String, Schema.SObjectField> oppFieldTokenList = Schema.SObjectType.npe03__Recurring_Donation__c.fields.getMap(); 
                for (string fieldName : oppFieldTokenList.keyset()) {
                    Schema.DescribeFieldResult f = UTIL_Describe.getFieldDescribe('npe03__Recurring_Donation__c', fieldname);                         
                    listSORDFields.add(new SelectOption(f.getName(), f.getLabel()));
                }
                listSORDFields.sort();
            }
            return listSORDFields;
        }
        private set;
    }

    /*********************************************************************************************************
    * @description Checks that existing RD field mappings are valid - called by the Health Check
    * @param ctrl The Health Check panel controller
    * @return void
    */
    public static void verifyRDMappings(STG_PanelHealthCheck_CTRL ctrl) {
        
        map<string, npe03__Custom_Field_Mapping__c> mapRDCustomField = UTIL_ListCustomSettingsFacade.getMapCustomFieldMappings();
        string strSetting = Schema.Sobjecttype.npe03__Custom_Field_Mapping__c.getLabel(); 
        boolean isValid = true;
        
        for (string s : mapRDCustomField.keySet()){
            npe03__Custom_Field_Mapping__c cfm = mapRDCustomField.get(s);
            SobjectField RDField;
            SobjectField opportunityField;
            isValid = true;
            
            //check source field
            opportunityField = Schema.sObjectType.Opportunity.fields.getMap().get(cfm.npe03__Opportunity_Field__c);
            if (opportunityField == null) {
                isValid = false;
                ctrl.createDR(strSetting, STG_PanelHealthCheck_CTRL.statusError, 
                    string.format(label.healthDetailsInvalidOppField, new string[]{cfm.npe03__Opportunity_Field__c}), 
                    string.format(label.healthSolutionRDBadOppField, new string[]{Label.stgNavDonations, label.stgNavRDFieldMap}));
            }               
                      
            //check target fields            
            RDField = Schema.sObjectType.npe03__Recurring_Donation__c.fields.getMap().get(cfm.npe03__Recurring_Donation_Field__c);
            if (RDField == null) {
                isValid = false;
                ctrl.createDR(strSetting, STG_PanelHealthCheck_CTRL.statusError, 
                    string.format(label.healthDetailsBadRDField, new string[]{cfm.npe03__Recurring_Donation_Field__c}), 
                    string.format(label.healthSolutionBadRDField, new string[]{Label.stgNavDonations, label.stgNavRDFieldMap}));
            }               
            
            //only if the other checks succeed, get the describe type
            if (isValid == true){
                //check Field Types
                Schema.Displaytype dtRD = UTIL_Describe.getFieldDisplaytype('npe03__Recurring_Donation__c', cfm.npe03__Recurring_Donation_Field__c);
                Schema.Displaytype dtOpp = UTIL_Describe.getFieldDisplaytype('Opportunity', cfm.npe03__Opportunity_Field__c);
                if (!UTIL_Describe.canDisplaytypesCopy(dtRD, dtOpp)) { 
                    isValid = false;
                    ctrl.createDR(strSetting, STG_PanelHealthCheck_CTRL.statusError, 
                        string.format(label.healthDetailsRDFieldsBadDatatypes, new string[]{cfm.npe03__Recurring_Donation_Field__c, cfm.npe03__Opportunity_Field__c}), 
                        string.format(label.healthSolutionRDFieldsBadDatatypes, new string[]{Label.stgNavDonations, label.stgNavRDFieldMap}));
                }               
            }            
        }

        if (isValid) {
            ctrl.createDR(strSetting, STG_PanelHealthCheck_CTRL.statusSuccess, null, label.healthLabelRDValid);            
        }                           
    }           

}