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
* @description Trigger Handler on Partial Soft Credit for PSC management 
*/
public class PSC_PartialSoftCredit_TDTM extends TDTM_Runnable {

    /** @Description Allows PSC_Opportunity_TDTM to modify PSC currency while blocking user requests to change currency.*/
    public static boolean blockCurrencyChange = true;

    /*******************************************************************************************************
    * @description Trigger Handler on Partial Soft Credit that handles PSC sychronization with OCR's.
    * The scenarios to support are:
    *   Before Insert - match an existing OCR to the PSC, or create a new OCR if not found.
    *   Before Update - if RoleName changes, update its associated OCR's role.
    *   Note that we don't need to worry about Contact__c or Opportunity__c changing on the PSC, because
    *   those master-detail fields are not reparentable.
    *
    * @param listNew the list of PSCs from trigger new. 
    * @param listOld the list of PSCs from trigger old. 
    * @param triggerAction which trigger event (BeforeInsert, AfterInsert, etc.). 
    * @param objResult the describe for PSC 
    * @return dmlWrapper.  
    ********************************************************************************************************/
    public override DmlWrapper run(List<SObject> listNew, List<SObject> listOld, 
        TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {

        // BEFORE INSERT
        if (triggerAction == TDTM_Runnable.Action.BeforeInsert) {
            matchOrCreateOCRs(listNew);
            return null;                
        }
        // BEFORE UPDATE
        else if (triggerAction == TDTM_Runnable.Action.BeforeUpdate) {
            DMLWrapper dmlWrapper = new DmlWrapper();
            doRoleUpdates(listNew, listOld, dmlWrapper);
            return dmlWrapper;
        }
         
        return null;
    }


    /*******************************************************************************************************
    * @description For the PSCs find their matching OCRs, or create them if not found, and store their Id
    * back in the PSCs.
    * @param listPSC the list of PSCs from trigger new. 
    * @return void.  
    ********************************************************************************************************/
    private void matchOrCreateOCRs(list<Partial_Soft_Credit__c> listPSC) {
    
        // collect all our Opps and Contacts referenced by the PSC's
        set<Id> setOppId = new set<Id>();
        set<Id> setConId = new set<Id>();
        for (Partial_Soft_Credit__c psc : listPSC) { 
            setOppId.add(psc.Opportunity__c);
            setConId.add(psc.Contact__c);
        }

        //copy opportunity currency if multicurrency is enabled
        if (userInfo.isMultiCurrencyOrganization()) {
            string oppQuery = 'SELECT Id, CurrencyIsoCode FROM Opportunity WHERE Id IN :setOppId';
            map<id, Opportunity> mapOppIdOpp = new map<id, Opportunity>((list<Opportunity>) database.query(oppQuery));
            
            for (Partial_Soft_Credit__c psc : listPSC)
                psc.put('CurrencyIsoCode', mapOppIdOpp.get(psc.Opportunity__c).get('CurrencyIsoCode'));
        }

        // create a map of all OCR's for the Opps in question
        // our map will use conId-oppId as its key
        map<String, OpportunityContactRole> mapConIdOppIdToOCR = new map<String, OpportunityContactRole>();
        for (OpportunityContactRole ocr : [select Id, ContactId, OpportunityId, Role, IsPrimary from OpportunityContactRole 
                where ContactId in :setConId and OpportunityId in :setOppId]) {
            mapConIdOppIdToOCR.put(ocr.ContactId + '-' + ocr.OpportunityId, ocr);        
        }
            
        // now go thru all our new PSC's, see if we find an existing OCR to use, otherwise create a new OCR
        list<OpportunityContactRole> listOCRNew = new list<OpportunityContactRole>();
        for (Partial_Soft_Credit__c psc : listPSC) {
            OpportunityContactRole ocr = mapConIdOppIdToOCR.get(psc.Contact__c + '-' + psc.Opportunity__c);
            if (ocr == null) {
                ocr = new OpportunityContactRole(OpportunityId=psc.Opportunity__c, ContactId=psc.Contact__c, Role=psc.Role_Name__c);
                listOCRNew.add(ocr);
            }
        } 
        
        // now we must create the new OCR's so we can then store their Id back in the PSC's
        insert listOCRNew;
        
        // add these new OCR's to our OCR map
        for (OpportunityContactRole ocr : listOCRNew) {
            mapConIdOppIdToOCR.put(ocr.ContactId + '-' + ocr.OpportunityId, ocr);        
        }
         
        // now set the matching OCR in each PSC
        for (Partial_Soft_Credit__c psc : listPSC) {
            OpportunityContactRole ocr = mapConIdOppIdToOCR.get(psc.Contact__c + '-' + psc.Opportunity__c);
            if (ocr != null) {
                psc.Contact_Role_ID__c = ocr.Id;
            }
        }
        
        // we explicitly do not update the PSC's, since we know this is called from Before triggers!     
    }

    /*******************************************************************************************************
    * @description For the PSCs allow any Role changes, and propagate to their matching OCR if it previously
    * had the same Role. 
    * @param listPSCNew the list of PSCs from trigger new. 
    * @param listPSCOld the list of PSCs from trigger old.
    * @param dmlWrapper to hold any OCR changes 
    * @return void.  
    ********************************************************************************************************/
    private void doRoleUpdates(list<Partial_Soft_Credit__c> listPSCNew, list<Partial_Soft_Credit__c> listPSCOld, DmlWrapper dmlWrapper) {
        map<Id, integer> mapOCRIdToPSCIndex = new map<Id, integer>();
        
        for (integer i = 0; i < listPSCNew.size(); i++) {
            Partial_Soft_Credit__c pscNew = listPSCNew[i];
            Partial_Soft_Credit__c pscOld = listPSCOld[i];
            
            // track RoleName changes to potentially update the associated OCR
            if (pscNew.Role_Name__c != pscOld.Role_Name__c && pscNew.Contact_Role_ID__c != null) {
                mapOCRIdToPSCIndex.put(pscNew.Contact_Role_ID__c, i);
            }    
        
            // don't allow manually changing the PSC currency: it must be changed through the parent opportunity
            if (userInfo.isMultiCurrencyOrganization() && pscNew.get('CurrencyIsoCode') != pscOld.get('CurrencyIsoCode') && blockCurrencyChange) {
                pscNew.addError(Label.pscManageSoftCreditsCantChangeCurrency);
            }
        }
        
        // now update RoleNames if any changed.
        if (mapOCRIdToPSCIndex.size() > 0) {
            set<Id> setOCRId = mapOCRIdToPSCIndex.keySet();
            list<OpportunityContactRole> listOCR = [select Id, Role from OpportunityContactRole where Id in :setOCRId];
            for (OpportunityContactRole ocr : listOCR) {
                integer i = mapOCRIdTOPSCIndex.get(ocr.Id);
                if (i != null) {
                    if (ocr.Role == listPscOld[i].Role_Name__c) {
                        ocr.Role = listPscNew[i].Role_Name__c;
                        dmlWrapper.objectsToUpdate.add(ocr);
                    }
                }
            }
        }
    }
}