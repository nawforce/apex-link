/*
    Copyright (c) 2015 Salesforce.org
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
* @date 2015
* @group Opportunity
* @description Visualforce controller for the Send Acknowledgment buttons (single and list view) on Opportunities.  
*/
public with sharing class OPP_SendAcknowledgmentBTN_CTRL {

    /*******************************************************************************************************
    * @description the Set Controller that specifies the selected opportunities
    */
    private ApexPages.StandardSetController setCtrl { get; set; }

    /*******************************************************************************************************
    * @description the id to return the user to or first 3 for the list
    */
    public String recordId { get; set; }

    /*******************************************************************************************************
    * @description set to true when user should be returned to previous page via javascript
    */
    public Boolean redirect { get; set; }

    /*******************************************************************************************************
    * @description the list of Opportunities to be acknowledged
    */
    private list<Opportunity> listOpp { 
        get {
            if (listOpp == null)
                listOpp = new list<Opportunity>(); 
            return listOpp;
        }
        set; 
    }

    /*******************************************************************************************************
    * @description the list of Opportunities that were not acknowledged
    */
    public list<Opportunity> listOppNotSent { 
        get {
            if (listOppNotSent == null)
                listOppNotSent = new list<Opportunity>(); 
            return listOppNotSent;
        }
        set; 
    }

    /*******************************************************************************************************
    * @description the count of Opportunities that were acknowledged
    */
    public integer cSent { get; set; }

    /*******************************************************************************************************
    * @description the count of Opportunities that were NOT acknowledged
    */
    public integer cNotSent { get; set; }

    /*******************************************************************************************************
    * @description Page constructor that takes the set controller
    * @param controller The set controller
    * @return void
    */
    public OPP_SendAcknowledgmentBTN_CTRL(ApexPages.StandardSetController controller) {
        recordId = '006';
        setCtrl = controller;
        listOpp = setCtrl.getSelected();
        
        // detect if we are being called in the singular scenario
        if (listOpp.size() == 0) {
            ID oppId = ApexPages.currentPage().getParameters().get('oppId');
            if (oppId != null) {
                Opportunity opp = [select Id from Opportunity where Id = :oppId];
                listOpp.add(opp);
                recordId = oppId;
            }
        }
    }
    
    /*******************************************************************************************************
    * @description Action method called when the user clicks on the Send Acknowledgment button.
    * @return null to the calling page
    */
    public PageReference SendAcknowledgment() {
        // wanted to give an error if they were going to overflow their email limit,
        // but one can only see the Apex email limits (1k/day), 
        // not the workflow email limits (1k/day per user license).
        // integer cSent = system.limits.getEmailInvocations();
        // integer cLimit = system.limits.getLimitEmailInvocations();
        
        // wanted to give an error if there were no workflow rules activated on Opportunity
        // that looked at the Acknowledgment Status field.  But unfortunately one cannot 
        // use Apex to view workflows (they aren't supported in describe calls).  the 
        // workarounds are to use the metadata API, but we aren't going to invest in that for this
        // one little feature.
        
        if (listOpp.size() == 0) {
            cNotSent = 0;
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, label.sendAcknowledgmentNoneSelected));
            return null;        
        }

        for (Opportunity opp : listOpp) {
            opp.Acknowledgment_Status__c = label.sendAcknowledgmentFireStatus;
        }
                
        try {
            update listOpp;
            
            // detect how many were sent via the workflow, by seeing if their status changed.
            listOppNotSent = [select Id, Name from Opportunity where 
                id in :listOpp and Acknowledgment_Status__c = :label.sendAcknowledgmentFireStatus];
            
            cNotSent = listOppNotSent.size();
            cSent = listOpp.size() - cNotSent;

            // reporting how many sent
            if (cSent > 0) {
                ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.INFO, 
                    string.format(label.sendAcknowledgmentNumberSent, 
                        new list<string>{string.valueOf(cSent)})));
            }                

            // reporting how many skipped
            if (cNotSent > 0) {
                ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.WARNING, 
                    string.format(label.sendAcknowledgmentNumberNoEmail, 
                        new string[] {string.valueOf(cNotSent)})));                
            }
            
            // update NotSent's status
            for (Opportunity opp : listOppNotSent) {
                opp.Acknowledgment_Status__c = label.sendAcknowledgmentFailedStatus;
            }
            update listOppNotSent;
            
            return null;
        } catch (Exception ex) {
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, ex.getMessage()));
            return null;
        }

        return null;
    }

    /*******************************************************************************************************
    * @description Action method called to close and return.
    * @return null to the calling page
    */
    public PageReference close() {
        redirect = true;
        return null;
    }
}