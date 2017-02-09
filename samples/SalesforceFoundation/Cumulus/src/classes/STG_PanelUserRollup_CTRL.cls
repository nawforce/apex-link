/*
    Copyright (c) 2011, Salesforce.org
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
* @date 2011 (2.3)
* @group Settings
* @description Controller class for the User Rollup Settings panel  
*/
public with sharing class STG_PanelUserRollup_CTRL extends STG_Panel { 

    /*********************************************************************************************************
    * @description Returns the string Id of the User Rollup panel. 
    */
    public override string idPanel() { return 'idPanelUserRollup'; }
    
    /*********************************************************************************************************
    * @description Map of Fields to Displaytype 
    */
    map<string, Schema.Displaytype> mapFieldTypes = new map<string, Schema.Displaytype>();
        
    /*********************************************************************************************************
    * @description The list of SelectOptions for the source object's fields 
    */
    public list<SelectOption> sourcefields = new list<SelectOption>();

    /*********************************************************************************************************
    * @description The list of SelectOptions of valid operators 
    */
    public list<SelectOption> validoperators = new list<SelectOption>();

    /*********************************************************************************************************
    * @description The list of SelectOptions for the target object's fields 
    */
    public list<SelectOption> targetfields = new list<SelectOption>();    

    /*********************************************************************************************************
    * @description The list of SelectOptions of targe objects 
    */
    public list<SelectOption> targetobjects = new list<SelectOption>{
        new SelectOption('Account', 'Account'),
        new SelectOption('Contact', 'Contact'),
        new SelectOption('npo02__Household__c', 'npo02__Household__c')    
    }; 
    
    /*********************************************************************************************************
    * @description The list of UDR's 
    */
    public list<UDR> udrList {
    	get {
    		if (udrList == null) {
		        list<npo02__User_Rollup_Field_Settings__c> thisList = UTIL_ListCustomSettingsFacade.getMapUserRollupFieldSettings().values(); 
		        udrList = new list<UDR>();
		        for (npo02__User_Rollup_Field_Settings__c udr : thisList) {
		            udrList.add(new UDR(udr, this));
		        }
		        udrlistsize = udrList.size();
    		}
            return udrList;
    	}
    	set;
    }
    
    /*********************************************************************************************************
    * @description The number of UDR's 
    */
    public integer udrlistsize{get;set;}

    /*********************************************************************************************************
    * @description The current UDR being edited 
    */
    public UDR activeUDR{get; set;}

    /*********************************************************************************************************
    * @description Whether the currently edited URD is valid 
    */
    public boolean isValidRollup{get; set;}
    
    /*********************************************************************************************************
    * @description The panel's constructor 
    */
    public STG_PanelUserRollup_CTRL(){        
        isValidRollup = false;
        isEditMode = false;        
    }    
    
    /*********************************************************************************************************
    * @description Gets the panel ready for edit mode of a new UDR 
    */
    public void startBuild(){    
        activeUDR = new UDR(this);
        isEditMode = true;
    }
        
    /*********************************************************************************************************
    * @description Cancels edit mode and reset all instance variables
    */
    public void cancel(){       
        this.sourcefields.clear();
        this.mapFieldTypes.clear();
        this.validoperators.clear();
        this.targetfields.clear();     
        this.isEditMode = false;    
        this.activeUDR = null;
    }    
    
    /*********************************************************************************************************
    * @description The list of SelectOptions of target objects 
    */
    public list<SelectOption> getTargetObjects(){
        return this.targetobjects;
    }
    
    /*********************************************************************************************************
    * @description The list of SelectOptions of valid operators based on the current DisplayTypes 
    */
    public list<SelectOption> getValidOperators(){
        validoperators.clear();        
        if (mapFieldTypes.containsKey(activeUDR.SourceField)){
           validoperators.add(new SelectOption('MIN', 'MIN'));
           validoperators.add(new SelectOption('MAX', 'MAX'));  
           
            if ((mapFieldTypes.get(activeUDR.SourceField) != Schema.Displaytype.Date) && (mapFieldTypes.get(activeUDR.SourceField) != Schema.Displaytype.Datetime)){  
                validoperators.add(new SelectOption('AVG', 'AVG'));
                validoperators.add(new SelectOption('SUM', 'SUM'));                
            }
        }    
        return validoperators; 
    }
    
    /*********************************************************************************************************
    * @description The list of SelectOptions of fields from the Target object 
    */
    public list<SelectOption> getTargetFields() { 
        
        if (targetfields.isEmpty()) {
	        set<string> knownfields;    
	        map<string, Schema.Sobjectfield> targetTokenMap = new map<string, Schema.Sobjectfield>();        
	        //need to load current settings to not allow the user to point to the same target field
	        //twice
	        map<string, npo02__User_Rollup_Field_Settings__c> currentURFSMap = UTIL_ListCustomSettingsFacade.getMapUserRollupFieldSettings();
	        set<string> currentTargetFieldSet = new set<string>();
	        for (string s : currentURFSMap.keySet()){
	            if (currentURFSMap.get(s).npo02__Object_Name__c == activeUDR.TargetObject && currentURFSMap.get(s).npo02__Target_Field__c != activeUDR.TargetField) {
	               string fieldname = (currentURFSMap.get(s).npo02__Target_Field__c).toLowerCase();
	               currentTargetFieldSet.add(fieldname);
	            }
	        }  
	         
	        if (activeUDR.TargetObject == 'Contact') {
	            targetTokenMap = Schema.SObjectType.Contact.fields.getMap().clone();
	        }
	        else if (activeUDR.TargetObject == 'Account') {
	            targetTokenMap = Schema.SobjectType.Account.fields.getMap().clone();
	        }
	        else if (activeUDR.TargetObject == 'npo02__Household__c') {
	            targetTokenMap = Schema.SobjectType.npo02__household__c.fields.getMap().clone();
	        }
	                
	        // remove any field already used in a UDR
	        for (string s : currentTargetFieldSet){
	            s = s.toLowerCase();
	            targetTokenMap.remove(s);
	        }
	        
	        // get datatype of current Source field
	        Schema.Displaytype dtSrc = mapFieldTypes.get(activeUDR.SourceField);
	        
	        // valid target fields must:
	        // - be a custom field
	        // - not from any NPSP package
	        // - not be a formula field
	        // - support the source field datatype         
	        if (targettokenMap.size() > 0) {        
	            for (string s : targetTokenMap.keyset()) {            
	                if (s.contains('__c') && 
	                    !s.startswith(UTIL_Namespace.getNamespace()+'__') && 
	                    !s.startswith('npe01__') && 
	                    !s.startswith('npo02__') && 
	                    !s.startswith('npe03__') && 
	                    !s.startswith('npe4__') &&
	                    !s.startswith('npe5__')) {
	                    
	                    // check datatype                                             
	                    Schema.DescribeFieldResult f = UTIL_Describe.getFieldDescribe(activeUDR.TargetObject, s);
	                    
	                    // disallow formula fields and autonums
	                    if (f.isCalculated() || f.isAutoNumber())
	                        continue;
	                        
	                    Schema.Displaytype dtTgt = f.getType();
	                    if (UTIL_Describe.canDisplaytypesCopy(dtSrc, dtTgt)) {
	                        targetfields.add(new SelectOption(f.getName(), f.getLabel()));
	                    }    
	                }
	            }
	        }
	        
	        if (targetfields.size() < 1){            
	            targetfields.clear();
	            targetfields.add(new SelectOption('', 'No Valid Field Available'));                            
	        }
	        targetfields.sort();
        }
        return targetfields;    
    }
    
    /*********************************************************************************************************
    * @description The list of SelectOptions of valid source fields from the Opportunity object 
    */
    public list<SelectOption> getSourceFields(){
        
        if (sourcefields.isEmpty()) {                       

	        // sets of strings are case sensitive, so use lower case in our set for the contains() against knownopptyFields.
	        set<string> knownOpptyFields = new set<string>{'amount', 'closedate', 'expectedrevenue', 
	            'probability', 'totalopportunityquantity', 'npe01__membership_start_date__c', 'npe01__membership_end_date__c'};
        
            map<String, Schema.SObjectField> oppFieldTokenList = Schema.SObjectType.Opportunity.fields.getMap(); 
            
            //loop through the map, only get the describe information for fields we don't
            //already recognize, including our own and non-custom fields, we'll add those in a moment
            for(string fieldName : oppFieldTokenList.keyset()){
                if (fieldname.contains('__c') || knownopptyFields.contains(fieldName)){
                    Schema.DescribeFieldResult f = UTIL_Describe.getFieldDescribe('Opportunity', fieldname);                         
                    
                    Schema.Displaytype dt = f.getType();
                    if ((dt == Schema.Displaytype.Currency) ||
                        (dt == Schema.Displaytype.Date) ||
                        (dt == Schema.Displaytype.Datetime) ||
                        (dt == Schema.Displaytype.Double) ||
                        (dt == Schema.Displaytype.Integer) ||
                        (dt == Schema.Displaytype.Percent)){                                
                        
                        sourcefields.add(new SelectOption(f.getName(), f.getLabel()));
                        mapFieldTypes.put(f.getName(), dt);                    
                    }
                }
           }                      
           sourceFields.sort();               
        }         
        return sourceFields;
    }
        
    /*********************************************************************************************************
    * @description Clears out the current UDR's related data after a change to the Source field
    * @return void
    */
    public void clearForSourceField(){
        activeUDR.Operation = null;     
        activeUDR.TargetObject = null;
        activeUDR.TargetField = null;  
        targetfields.clear();
             
    }    

    /*********************************************************************************************************
    * @description Clears out the current UDR's related data after a change to the Operator (nothing to do!)
    * @return void
    */
    public void clearForOperation(){
        // the targets are dependent on the source datatype, not the aggregating op.
        //activeUDR.TargetObject = null;
        //activeUDR.TargetField = null;
        
    }

    /*********************************************************************************************************
    * @description Clears out the current UDR's related data after a change to the Target object 
    * @return void
    */
    public void clearForTargetObject(){
        activeUDR.TargetField = null;       
        targetfields.clear();
    }    
    
    /*********************************************************************************************************
    * @description Checks that existing user defined rollups are valid - called by Health Check
    * @param ctrl The Health Check panel's controller
    * @return void
    */
    public static void verifyUserRollups(STG_PanelHealthCheck_CTRL ctrl) {
        boolean isValid = true;
        boolean isValidAll = true;
        
        map<string, npo02__User_Rollup_Field_Settings__c> userRollups = UTIL_ListCustomSettingsFacade.getMapUserRollupFieldSettings(); 
        string strSetting = Schema.Sobjecttype.npo02__User_Rollup_Field_Settings__c.getLabel(); 
    
        for (string s : userRollups.keySet()) {
            npo02__User_Rollup_Field_Settings__c urfs = userRollups.get(s);
            SobjectField targetField;
            SobjectField sourceField;
            isValid = true;
                        
            //check source field
            sourceField = Schema.sObjectType.Opportunity.fields.getMap().get(urfs.npo02__Source_Field__c);
            if (sourceField == null) {
                isValid = false;
                ctrl.createDR(strSetting, STG_PanelHealthCheck_CTRL.statusError, 
                    string.format(label.healthDetailsInvalidOppField, new string[]{urfs.npo02__Source_Field__c}), 
                    string.format(label.healthSolutionUDFOppField, new string[]{label.stgNavDonations, label.stgNavUserDefinedRollups}));
            }               
                      
            //check target fields  
            boolean isValidTarget = true;          
            if (urfs.npo02__Object_Name__c == 'Contact'){
                targetField = Schema.sObjectType.Contact.fields.getMap().get(urfs.npo02__Target_Field__c); 
                if (targetField == null) isValidTarget = false;
            }
            else if (urfs.npo02__Object_Name__c == 'Account'){
                targetField = Schema.sObjectType.Account.fields.getMap().get(urfs.npo02__Target_Field__c); 
                if (targetField == null) isValidTarget = false;               
            }
            else if (urfs.npo02__Object_Name__c == 'npo02__Household__c' || urfs.npo02__Object_Name__c == 'npo02__Household__c'){
                targetField = Schema.sObjectType.npo02__household__c.fields.getMap().get(urfs.npo02__Target_Field__c); 
                if (targetField == null) isValidTarget = false;
            }
            else isValidTarget = false;
            
            if (!isValidTarget) {
            	isValid = false;
                ctrl.createDR(strSetting, STG_PanelHealthCheck_CTRL.statusError, 
                    string.format(label.healthDetailsInvalidObjectField, new string[]{urfs.npo02__Object_Name__c, urfs.npo02__Target_Field__c}), 
                    string.format(label.healthSolutionInvalidUDFObjectField, new string[]{label.stgNavDonations, label.stgNavUserDefinedRollups}));
            }

            //only if the other checks succeed, check displaytypes
            if (isValid) {
                //check Field Types
                Schema.Displaytype dtSrc = UTIL_Describe.getFieldDisplaytype('Opportunity', urfs.npo02__Source_Field__c);
                Schema.Displaytype dtTgt = UTIL_Describe.getFieldDisplaytype(urfs.npo02__Object_Name__c, urfs.npo02__Target_Field__c);
                if (!UTIL_Describe.canDisplaytypesCopy(dtSrc, dtTgt)) { 
                    isValid = false;
                    ctrl.createDR(strSetting, STG_PanelHealthCheck_CTRL.statusError, 
                        string.format(label.healthDetailsUDFBadDatatypes, new string[]{urfs.npo02__Source_Field__c, urfs.npo02__Object_Name__c, urfs.npo02__Target_Field__c}), 
                        string.format(label.healthSolutionUDFBadDatatypes, new string[]{label.stgNavDonations, label.stgNavUserDefinedRollups}));
                }               
            }
            
            if (!isValid)
                isValidAll = false;            
            
        }    
        if (isValidAll) {
            ctrl.createDR(strSetting, STG_PanelHealthCheck_CTRL.statusSuccess, null, label.healthLabelUDFValid);                    	
        }
    }           
        
    /*********************************************************************************************************
    * @description Adds a page message for an error to the visualforce page
    * @return void
    */
    private void showMsg(String arg, ApexPages.Severity severity){
        ApexPages.Message myMsg = new ApexPages.Message(severity,arg);
        ApexPages.addMessage(myMsg);
    }    
    
    /*********************************************************************************************************
    * @description Class the represents a User Defined Rollup
    */
    public class UDR{
        
	    /*********************************************************************************************************
	    * @description The User Rollup Field Settings object this class holds
	    */
        public npo02__User_Rollup_Field_Settings__c myUDR{get;set;}

        /*********************************************************************************************************
        * @description The source object field
        */
        public String SourceField{get;set;}

        /*********************************************************************************************************
        * @description The target object field
        */
        public String TargetField{get;set;}

        /*********************************************************************************************************
        * @description The target object
        */
        public String TargetObject{get;set;}

        /*********************************************************************************************************
        * @description The operator
        */
        public String Operation{get;set;}

        /*********************************************************************************************************
        * @description Whether this UDR is valid
        */
        public Boolean isValid{set;}

        /*********************************************************************************************************
        * @description The page controller for this UDR
        */
        STG_PanelUserRollup_CTRL mydad;
        
        /*********************************************************************************************************
        * @description The Source field's label
        */
        public string SourceFieldLabel {
        	get {
        		return UTIL_Describe.getFieldLabelSafe('Opportunity', SourceField);
        	}
        }
        
        /*********************************************************************************************************
        * @description The Target field's label
        */
        public string TargetFieldLabel {
            get {
                return UTIL_Describe.getFieldLabelSafe(TargetObject, TargetField);
            }
        }
        
        /*********************************************************************************************************
        * @description constructor
        */
        public UDR(STG_PanelUserRollup_CTRL parentclass){
            mydad = parentclass;  
            myUDR = new npo02__User_Rollup_Field_Settings__c();        
        }

        /*********************************************************************************************************
        * @description constructor
        */
        public UDR(npo02__User_Rollup_Field_Settings__c thisUDR, STG_PanelUserRollup_CTRL parentclass){
            this.SourceField = thisUDR.npo02__Source_Field__c;
            this.TargetField = thisUDR.npo02__Target_Field__c;
            this.TargetOBject = thisUDR.npo02__Object_Name__c;
            this.Operation = thisUDR.npo02__Field_Action__c;
            myUDR = thisUDR;        
            mydad = parentclass;    
        }       

        /*********************************************************************************************************
        * @description constructor
        */
        public UDR(String SourceField, String TargetField, String TargetObject, String Operation, STG_PanelUserRollup_CTRL parentclass){
            this.SourceField = SourceField;
            this.TargetField = TargetField;
            this.TargetObject = TargetObject;
            this.Operation = Operation;
            myUDR = new npo02__User_Rollup_Field_Settings__c();
            mydad = parentclass;
        }
        
        /*********************************************************************************************************
        * @description Saves the UDR class's data into the npo02__User_Rollup_Field_Settings__c object and saves
        * the setting in the database.  Any errors are reported as page messages.
        * @return void
        */
        public void save(){
            
            if (myUDR.id == null){
            	// the udr's name needs to be a valid field alias when we construct the rollup query.
                string strName = 'CR_' + system.Now();
                strName = strName.replace(' ','');
                strName = strName.replace(':',''); 
                strName = strName.replace('-',''); 
                myUDR.Name = strName;
                myUDR.npo02__Object_Name__c = TargetObject;
                myUDR.npo02__Target_Field__c = TargetField;
                myUDR.npo02__Field_Action__c = Operation;
                myUDR.npo02__Source_Field__c = SourceField;                
            }
            
            try{
            	upsert myUDR;                        
                mydad.udrList = null;                
                mydad.activeUDR = null;
                mydad.isEditMode = false;
            }
        
            catch(Exception e){
            	ERR_Handler.processError(e, ERR_Handler_API.Context.STTG);            	
                mydad.showMsg('An unhandled exception has occurred. Please try again.',ApexPages.Severity.FATAL);
                mydad.showMsg('Exception type' + e, ApexPages.Severity.INFO);          
            }           
        }       

        /*********************************************************************************************************
        * @description Initializes the page with this UDR for editing
        * @return void
        */
        public void loadMe(){
            mydad.activeUDR = this;
            mydad.isEditMode = true;
        }

        /*********************************************************************************************************
        * @description Deletes the UDR from the database, and ends Edit mode
        * @return void
        */
        public void deleteMe(){         
            if (myUDR.id != null)
              delete myUDR;
            mydad.udrList = null;                
            mydad.activeUDR = null;
            mydad.isEditMode = false;
        }       
        
        /*********************************************************************************************************
        * @description Returns whether this UDR is valid
        * @return boolean
        */
        public boolean getisValid(){
            if (SourceField != null && TargetField != null && TargetObject != null && Operation != null && TargetField != '')
                return true;
            else
                return false;           
        }       
    }
    
}