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
* @description Controller for the Contacts settings page.
*/
 public with sharing class STG_PanelContacts_CTRL extends STG_Panel {
    
    /*********************************************************************************************************
    * @description Returns the string Id of the Contacts panel. 
    */
    public override string idPanel() { return 'idPanelCon'; }

    /*********************************************************************************************************
    * @description Action Method to save the current settings
    * @return null 
    */
    public override PageReference saveSettings() {
        
        // avoid reusing the same recordtype in Account models
        string idHH = STG_Panel.stgService.stgCon.npe01__HH_Account_RecordTypeID__c;
        string idOne = STG_Panel.stgService.stgCon.npe01__One_to_One_RecordTypeID__c;
        if (idHH != null && idOne != null && idHH == idOne) {
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, label.healthDetailsDuplicateAcctRT));
            return null;
        }

        return super.saveSettings();
    }

    
}