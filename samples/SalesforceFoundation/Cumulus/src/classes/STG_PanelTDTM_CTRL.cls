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
* @description The controller for the TDTM Trigger Handler panel 
*/
public with sharing class STG_PanelTDTM_CTRL extends STG_Panel {

    /*******************************************************************************************************
    * @description Class constructor sets pagemessage
    */
    public STG_PanelTDTM_CTRL() {}

    /*******************************************************************************************************
    * @description The panel ID.
    */
    public override string idPanel() { return 'idPanelTDTM'; }
    
    /*******************************************************************************************************
    * @description Temporary trigger handler used by the page for creating a new handler.
    */
    public Trigger_Handler__c thNew {
        get {
            if (thNew == null)
              thNew = new Trigger_Handler__c();
            return thNew;
        }
        private set;
    }
    
    /*******************************************************************************************************
    * @description The ID of the trigger handler to delete.
    */
    public string thIdDelete { get; set; }
    
    /*******************************************************************************************************
    * @description Existing trigger handlers.
    */
    public list<Trigger_Handler__c> listTH {
        get {
            if (listTH == null) {
                listTH = new list<Trigger_Handler__c>();
                listTH = [select Id, Active__c, Asynchronous__c, Class__c, Load_Order__c, Object__c, Trigger_Action__c, 
                            User_Managed__c from Trigger_Handler__c order by Object__c, Load_Order__c];
            }
            for (Trigger_Handler__c th : listTH) {
                th.Trigger_Action__c = th.Trigger_Action__c.replaceAll('[;]','; ');
            } 
            return listTH;           
        }
        private set;     
    }

    /*******************************************************************************************************
    * @description The trigger actions available for the trigger handlers.
    */
    public list<SelectOption> listSOTriggerAction {
        get {    
            if (listSOTriggerAction == null) {
                listSOTriggerAction = new list<SelectOption>();
                listSOTriggerAction.add(new SelectOption('','- none -'));
                Schema.DescribeFieldResult F = Schema.sObjectType.Trigger_Handler__c.fields.Trigger_Action__c; 
                List<Schema.PicklistEntry> P = F.getPicklistValues();
                for (Schema.PicklistEntry pe : P) {
                    listSOTriggerAction.add(new SelectOption(pe.getLabel(), pe.getLabel()));
                }
            }
            return listSOTriggerAction;
        }
        private set;
    } 

    /*******************************************************************************************************
    * @description Changes the page from view to edit mode (to create a new trigger handler).
    * @return PageReference The page that it redirects to. Same page user was in, in this case.
    */
    public PageReference addPageMessages() {
        // Display standard warning message
        ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.Warning, Label.stgHelpTriggerHandlers));

        // Display message if any async classes
        boolean hasAsync = false;
        List<Trigger_Handler__c> listTHDefault = TDTM_DefaultConfig.getDefaultRecords();
        set<string> setClasses = new set<string>();
        for (Trigger_Handler__c th : listTHDefault)
            setClasses.add(th.Class__c);
        
        for (Trigger_Handler__c th : listTH) {
            if (setClasses.contains(th.Class__c) && th.Asynchronous__c) {
                hasAsync = true;
                break;
            }
        }
        if (hasAsync) {
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.Warning, Label.stgHelpTriggerHandlersAsync));
        }
        return null;
    }

    
    /*******************************************************************************************************
    * @description Changes the page from view to edit mode (to create a new trigger handler).
    * @return PageReference The page that it redirects to. Same page user was in, in this case.
    */
    public PageReference newTh() {
        isEditMode = true;
        return null;
    }

    /*******************************************************************************************************
    * @description Saves the new trigger handler to the database.
    * @return PageReference The page that it redirects to. Same page user was in, in this case.
    */
    public PageReference createTh() {  

        // validate required fields
        if (thNew.Class__c == null || thNew.Object__c == null || thNew.Trigger_Action__c == null || thNew.Load_Order__c == null) {
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, 'Trigger Handler requires a Class, Object, Trigger Action, and Load Order to save.'));
            return null;            
        }      

        Savepoint sp = Database.setSavepoint();
        try {
            thNew.User_Managed__c = true;
            insert thNew;
            isEditMode = false;
            listTH = null;
            thNew = null;
        } catch(Exception e) {
            Database.rollback(sp);
            ERR_Handler.processError(e, ERR_Handler_API.Context.STTG);
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, e.getMessage()));
        }
        return null;
    }

    /*******************************************************************************************************
    * @description Cancels the creation of a new trigger handler.
    * @return PageReference The page that it redirects to. Same page user was in, in this case.
    */
    public PageReference cancelTh() {
        isEditMode = false;
        thNew = null;
        return null;
    }
    
    /*******************************************************************************************************
    * @description Deletes the trigger handler whose ID is stored in the thIdDelete property.
    * @return PageReference The page that it redirects to. Same page user was in, in this case.
    */
    public PageReference deleteTh() {
        Savepoint sp = Database.setSavepoint();
        try {
            STG_SettingsManager_CTRL.idPanelCurrent = idPanel();        
            if (thIdDelete != null) {
                Trigger_Handler__c th = new Trigger_Handler__c(Id = thIdDelete);
                delete th;
                // force reload.
                listTH = null;
            }
        } catch(Exception e) {
            Database.rollback(sp);
            ERR_Handler.processError(e, ERR_Handler_API.Context.STTG);
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, e.getMessage()));
        }
        return null;
    } 
}