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
* @description The controller for the Reciprocal Relationships panel 
*/
public with sharing class STG_PanelRelReciprocal_CTRL extends STG_Panel {
    
    /*********************************************************************************************************
    * @description Returns the string Id of the Reciprocal Relationships panel. 
    */
    public override string idPanel() { return 'idPanelRelReciprocal'; }
    
    /*********************************************************************************************************
    * @description temporary rl used by the page for creating a new rl.
    */
    public npe4__Relationship_Lookup__c rlNew {
        get {
            if (rlNew == null)
              rlNew = new npe4__Relationship_Lookup__c();
            return rlNew;
        }
        private set;
    }
    
    /*********************************************************************************************************
    * @description Action Method for going into edit mode (to create a new rl)
    * @return null
    */
    public PageReference newRelLookup() {
        isEditMode = true;
        return null;
    }

    /*********************************************************************************************************
    * @description Action Method to save the new rl to the database.  Any errors are added as page messages.
    * @return null
    */
    public PageReference createRelLookup() {        
        Savepoint sp = Database.setSavepoint();
        try {
            insert rlNew;
            isEditMode = false;
            resetRelLookupSettings();
            rlNew = null;
        } catch(Exception e) {
            Database.rollback(sp);
            ERR_Handler.processError(e, ERR_Handler_API.Context.STTG);
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, e.getMessage()));
        }
        return null;
    }

    /*********************************************************************************************************
    * @description Action Method to cancel out of creating a new rl.
    * @return null
    */
    public PageReference cancelRelLookup() {
        isEditMode = false;
        rlNew = null;
        return null;
    }

    /*********************************************************************************************************
    * @description Resets relationship lookup settings so that they are reloaded and the cached version isn't used.
    */
    public void resetRelLookupSettings() {
        UTIL_ListCustomSettingsFacade.mapRelationshipLookupSettings = null;
        listRelLookup = null;
    }
    
    /*********************************************************************************************************
    * @description String to hold the Id of an existing rl to be deleted. 
    */
    public string rlIdDelete { get; set; }

    /*********************************************************************************************************
    * @description Action Method for deleting an existing rl.
    * @return null
    */
    public PageReference deleteRl() {
        Savepoint sp = Database.setSavepoint();
        try {
            STG_SettingsManager_CTRL.idPanelCurrent = idPanel();        
            if (rlIdDelete != null) {
                npe4__Relationship_Lookup__c rl = new npe4__Relationship_Lookup__c(Id = rlIdDelete);
                delete rl;
                // force reload.
                resetRelLookupSettings();
            }
        } catch(Exception e) {
            Database.rollback(sp);
            ERR_Handler.processError(e, ERR_Handler_API.Context.STTG);
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, e.getMessage()));
        }
        return null;
    }

    /*********************************************************************************************************
    * @description The list of Relationship Lookup records
    */
    public list<npe4__Relationship_Lookup__c> listRelLookup {
        get {
            if (listRelLookup == null) {
                
                map<string, npe4__Relationship_Lookup__c> mapRL = UTIL_ListCustomSettingsFacade.getMapRelationshipLookupSettings();
                
	            // Sort them by name
	            list<string> rlNames = new list<string>();
	            rlNames.addAll(mapRL.keySet());
	            rlNames.sort(); 
	            
	            listRelLookup = new list<npe4__Relationship_Lookup__c>();
	            for (string str : rlNames) {
	                listRelLookup.add(mapRL.get(str));
	            }     
            } 
            return listRelLookup;           
        }
        private set;     
    }

    /*********************************************************************************************************
    * @description Checks that existing Reciprocal Relationships are valid - called by Health Check
    * @param ctrl The Health Check panel's controller
    * @return void
    */
    public static void verifyRelLookups(STG_PanelHealthCheck_CTRL ctrl) {
        
        map<string, npe4__Relationship_Lookup__c> mapRL = UTIL_ListCustomSettingsFacade.getMapRelationshipLookupSettings();
        //string strSetting = Schema.Sobjecttype.npe4__Relationship_Lookup__c.getLabel(); 
        boolean isValid = true;
        boolean isValidAll = true;
        
        for (string s : mapRL.keySet()) {
            npe4__Relationship_Lookup__c rl = mapRL.get(s);
            
            if (rl.npe4__Active__c && rl.npe4__Male__c == null && rl.npe4__Female__c == null && rl.npe4__Neutral__c == null) {
                isValid = false;
                ctrl.createDR(Label.stgNavRelReciprocal, STG_PanelHealthCheck_CTRL.statusError, 
                    string.format(label.healthDetailsRelReciprocalInvalid, new string[]{rl.Name}), 
                    string.format(label.healthSolutionRelReciprocalInvalid, new string[]{label.stgNavConnections, Label.stgNavRelReciprocal}));    
            }               
            
            if (!isValid)
                isValidAll = false;            
        }

        if (isValidAll) {
            ctrl.createDR(Label.stgNavRelReciprocal, STG_PanelHealthCheck_CTRL.statusSuccess, null, label.healthLabelRelReciprocalValid);            
        }                           
    }           
    
}