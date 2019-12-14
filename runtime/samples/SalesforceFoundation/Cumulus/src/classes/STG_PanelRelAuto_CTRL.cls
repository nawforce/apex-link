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
* @description The controller for the Autocreate Relationships panel 
*/
public with sharing class STG_PanelRelAuto_CTRL extends STG_Panel {
    
    /*********************************************************************************************************
    * @description Returns the string Id of the Autocreate Relationships panel. 
    */
    public override string idPanel() { return 'idPanelRelAuto'; }
    
    /*********************************************************************************************************
    * @description temporary ac used by the page for creating a new ac. 
    */
    public npe4__Relationship_Auto_Create__c acNew {
        get {
            if (acNew == null)
              acNew = new npe4__Relationship_Auto_Create__c();
            return acNew;
        }
        private set;
    }
    
    /*********************************************************************************************************
    * @description Action Method for going into edit mode (to create a new ac)
    * @return null
    */
    public PageReference newRelAutoCreate() {
        isEditMode = true;
        return null;
    }

    /*********************************************************************************************************
    * @description Action Method to save the new ac to the database.  Any errors are added as page messages
    * on the visualforce page. 
    * @return null
    */
    public PageReference createRelAutoCreate() {  

        // validate required fields
    	if (acNew.npe4__Object__c == null || acNew.npe4__Field__c == null || acNew.npe4__Relationship_Type__c == null) {
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, label.healthDetailsAutoRelFieldsMissing));
            return null;    		
    	} 
    	if (acNew.npe4__Object__c == 'CampaignMember' && acNew.npe4__Campaign_Types__c == null) {
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, label.healthDetailsAutoRelCampaignTypeMissing));
            return null;                		
    	}    

        Savepoint sp = Database.setSavepoint();
        try {
        	acNew.Name = 'AutoCreateRel-' + system.now();
            insert acNew;
            isEditMode = false;
            resetRelAutoSettings();
            acNew = null;
        } catch(Exception e) {
            Database.rollback(sp);
            ERR_Handler.processError(e, ERR_Handler_API.Context.STTG);
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, e.getMessage()));
        }
        return null;
    }

    /*********************************************************************************************************
    * @description Action Method to cancel out of creating a new ac. 
    * @return null
    */
    public PageReference cancelRelAutoCreate() {
        isEditMode = false;
        acNew = null;
        return null;
    }
    
    /*********************************************************************************************************
    * @description String to hold the Id of an existing ac to be deleted
    */
    public string acIdDelete { get; set; }

    /*********************************************************************************************************
    * @description Resets relationship auto create settings so that they are reloaded and the cached version isn't used.
    */
    public void resetRelAutoSettings() {
        UTIL_ListCustomSettingsFacade.mapRelationshipAutoCreateSettings = null;
        listRelAutoCreate = null;
    }

    /*********************************************************************************************************
    * @description Action Method for deleting an existing ac. 
    * @return null
    */
    public PageReference deleteAc() {
        Savepoint sp = Database.setSavepoint();
        try {
            STG_SettingsManager_CTRL.idPanelCurrent = idPanel();        
            if (acIdDelete != null) {
                npe4__Relationship_Auto_Create__c ac = new npe4__Relationship_Auto_Create__c(Id = acIdDelete);
                delete ac;
                // force reload.
                resetRelAutoSettings();
            }
        } catch(Exception e) {
            Database.rollback(sp);
            ERR_Handler.processError(e, ERR_Handler_API.Context.STTG);
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, e.getMessage()));
        }
        return null;
    }

    /*********************************************************************************************************
    * @description The list of Relationship Auto Create records  
    */
    public list<npe4__Relationship_Auto_Create__c> listRelAutoCreate {
        get {
            if (listRelAutoCreate == null) {
                listRelAutoCreate = new list<npe4__Relationship_Auto_Create__c>();
                listRelAutoCreate = UTIL_ListCustomSettingsFacade.getMapRelationshipAutoCreateSettings().values();
            } 
            return listRelAutoCreate;           
        }
        private set;     
    }

    /*********************************************************************************************************
    * @description The list of SelectOptions of Fields appropriate for the current object  
    */
    public List<SelectOption> listSOACfield {
    	get {
	        listSOACfield = new List<SelectOption>();
	        listSOACfield.add(new SelectOption('', '- none -'));
	        if (acNew.npe4__Object__c != null) {
	            map<String, Schema.SObjectField> fieldTokenMap = new Map<String, Schema.SobjectField>();
	            if (acNew.npe4__Object__c == 'Contact') {
	                fieldTokenMap = Schema.SObjectType.Contact.fields.getMap();         
	            }   
	            else if (acNew.npe4__Object__c == 'CampaignMember') {
	                fieldTokenMap = Schema.SObjectType.CampaignMember.fields.getMap();          
	            }   
	            for (String s : fieldTokenMap.keySet()) {
	                if (s.equalsIgnoreCase('ReportsToId') || (s.endsWith('__c') && !s.startsWith('npe01__') && !s.startsWith('npo02__') && !s.startsWith('npe03__'))){
	                    Schema.DescribeFieldResult F = fieldTokenMap.get(s).getDescribe();             
	                    List <Schema.sObjectType> sobjref = new List<Schema.sObjectType>(); 
	                    sobjref = F.getReferenceTo();
	                    for (Schema.sObjectType objT : sobjref) {
	                        if (objT == Contact.sObjectType) {
	                            listSOACfield.add(new SelectOption(F.getName(), F.getLabel()));
	                        }                   
	                    }               
	                }
	            }
	        }                                      
	        return listSOACfield;      
	    }
	    private set;
	}
 
    /*********************************************************************************************************
    * @description Checks that existing auto create rels are valid - called by Health Check 
    * @param ctrl The Health Check panel's controller
    * return void
    */
    public static void verifyAutoCreateRels(STG_PanelHealthCheck_CTRL ctrl) {
        
        map<string, npe4__Relationship_Auto_Create__c> mapAC = UTIL_ListCustomSettingsFacade.getMapRelationshipAutoCreateSettings();
        string strSetting = Schema.Sobjecttype.npe4__Relationship_Auto_Create__c.getLabel(); 
        boolean isValid = true;
        boolean isValidAll = true;
        
        for (string s : mapAC.keySet()){
            npe4__Relationship_Auto_Create__c ac = mapAC.get(s);
            SobjectField conField;
            isValid = true;
                        
            if (ac.npe4__Object__c == null || ac.npe4__Field__c == null || ac.npe4__Relationship_Type__c == null) {
               isValid = false;
               ctrl.createDR(strSetting, STG_PanelHealthCheck_CTRL.statusError, 
                   string.format(label.healthDetailsAutoRelFieldsMissing, new string[]{strSetting}), 
                   string.format(label.healthSolutionAutoRelFieldsMissing, new string[]{label.stgNavConnections, label.stgNavRelAutoCreate}));
        	
            }
            
            if (ac.npe4__Object__c == 'Contact'){
                conField = Schema.sObjectType.Contact.fields.getMap().get(ac.npe4__Field__c); 
                if (conField == null) { 
	               isValid = false;
	               ctrl.createDR(strSetting, STG_PanelHealthCheck_CTRL.statusError, 
	                   string.format(label.healthDetailsInvalidContactField, new string[]{ac.npe4__Field__c}), 
	                   string.format(label.healthSolutionAutoRelInvalidContactField, new string[]{label.stgNavConnections, label.stgNavRelAutoCreate}));
                }   
            }
            else if (ac.npe4__Object__c == 'CampaignMember'){
                conField = Schema.sObjectType.CampaignMember.fields.getMap().get(ac.npe4__Field__c); 
                if (conField == null) { 
                    isValid = false;
                    ctrl.createDR(strSetting, STG_PanelHealthCheck_CTRL.statusError, 
                        string.format(label.healthDetailsInvalidCMField, new string[]{ac.npe4__Field__c}), 
                        string.format(label.healthSolutionAutoRelInvalidCMField, new string[]{label.stgNavConnections, label.stgNavRelAutoCreate}));
                } else if (ac.npe4__Campaign_Types__c == null) { 
                    isValid = false;
                    ctrl.createDR(strSetting, STG_PanelHealthCheck_CTRL.statusError, 
                        label.healthDetailsAutoRelCampaignTypeMissing, 
                        string.format(label.healthSolutionAutoRelFieldsMissing, new string[]{label.stgNavConnections, label.stgNavRelAutoCreate}));
                } 
                  
            }
            
            //only if the other checks succeed, get the describe type
            if (isValid == true){
                //check Field Types
                Schema.Displaytype dt = UTIL_Describe.getFieldDisplaytype(ac.npe4__Object__c, ac.npe4__Field__c);
                if (dt != Schema.Displaytype.Reference) { 
                    isValid = false;
                    ctrl.createDR(strSetting, STG_PanelHealthCheck_CTRL.statusError, 
                        string.format(label.healthDetailsAutoRelInvalidLookupField, new string[]{ac.npe4__Field__c}), 
                        string.format(label.healthSolutionAutoRelInvalidLookupField, new string[]{label.stgNavConnections, label.stgNavRelAutoCreate}));
                }               
            }
            
            if (!isValid)
                isValidAll = false;            
        }

        if (isValidAll) {
            ctrl.createDR(strSetting, STG_PanelHealthCheck_CTRL.statusSuccess, null, label.healthLabelAutoRelValid);            
        }                           
    }           
}