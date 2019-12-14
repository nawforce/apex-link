/*
    Copyright (c) 2015, Salesforce.org
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
* @date 2011 (1.x)
* @group Opportunity
* @group-content ../../ApexDocContent/Opportunity.htm
* @description Provides opportunity and contact role support for all models  
*/
public without sharing class OPP_OpportunityContactRoles_TDTM extends TDTM_Runnable {

    /** @description DmlWrapper holds dml transactions for processing classes. */
    private DmlWrapper dmlWrapper = new DmlWrapper();

    /*******************************************************************************************************
    * @description Trigger Handler on Opportunity that handles Contact Role support.
    * @param listNew the list of Opportunities from trigger new. 
    * @param listOld the list of Opportunities from trigger old. 
    * @param triggerAction which trigger event (BeforeInsert, AfterInsert, etc.). 
    * @param objResult the describe for Opportunities 
    * @return dmlWrapper Always null. DML can't be deferred in this case as too many of our other opp related
    * triggers depend on Opportunity Contact Roles existing.
    ********************************************************************************************************/
    public override DmlWrapper run(List<SObject> newlist, List<SObject> oldlist, 
    TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {
            
        if (triggerAction == TDTM_Runnable.Action.BeforeInsert) {
            setOpportunityAccountField((list<Opportunity>) newlist);
            updateHonoreeNotificationFields((list<Opportunity>) newlist, null);

        } else if (triggerAction == TDTM_Runnable.Action.BeforeUpdate) {
            updateHonoreeNotificationFields((list<Opportunity>) newlist, (list<Opportunity>) oldlist);

        } else if (triggerAction == TDTM_Runnable.Action.AfterInsert) {
            upsertContactRolesAfterInsert((list<Opportunity>) newlist);
            try {
                updateOpportunityNames((list<Opportunity>) newList);            
            } catch (Exception ex) {
                ERR_Handler.processError(ex, ERR_Handler_API.Context.OPP);
            }
        } else if (triggerAction == TDTM_Runnable.Action.AfterUpdate) {
            upsertContactRolesAfterUpdate((list<Opportunity>) newlist, (list<Opportunity>) oldlist);
        }

        // we can't defer the dml.  too many of our other opp related triggers depend on the ocr's being saved.
        // disable TDTM so we don't waste resources running through opp triggers a second time while fixing Primary Contact lookup
        TDTM_TriggerHandler.disableTDTM = true;
        TDTM_TriggerHandler.processDML(dmlWrapper);
        TDTM_TriggerHandler.disableTDTM = false;

        return new DmlWrapper();
    }

    /*******************************************************************************************************
    * @description Fills 1:1 and HH accounts for Opportunities where the Contact Id is supplied. Fills the
    * contact Id with the account's primary contact if the account is supplied but not the contact.
    * @param oppList A list of opportunities in the current transaction.
    *******************************************************************************************************/
    private static void setOpportunityAccountField(List<Opportunity> oppList) {
        
        Map<Id,Account> contactsAndOneToOneAccounts = new Map<Id,Account>();
        Set<String> primaryContactIds = new Set<String>();
        Set<Id> oppAccounts = new Set<Id>();
        
        for (Opportunity o : oppList) {

            //keep new and old contact fields in sync
            if (o.npe01__Contact_Id_for_Role__c == null && o.Primary_Contact__c != null)
                o.npe01__Contact_Id_for_Role__c = o.Primary_Contact__c;

            if (o.npe01__Contact_Id_for_Role__c != null && o.Primary_Contact__c == null) {
                //if the npe01__Contact_Id_for_Role__c isn't a valid ID, null it out
                try {
                    o.Primary_Contact__c = o.npe01__Contact_Id_for_Role__c;
                } catch (Exception e) {
                    o.addError(Label.npe01.Opportunity_Contact_Role_Error_Bad_Contact_Id);
                }
            }
            
            if (o.AccountId != null && o.Primary_Contact__c == null)
                oppAccounts.add(o.AccountId);
            
            if (o.AccountId == null && o.Primary_Contact__c != null)
                primaryContactIds.add(o.Primary_Contact__c);                
        }
        
        Map<Id,Account> primaryAccounts = new Map<Id,Account>([select id, npe01__One2OneContact__c, npe01__SYSTEM_AccountType__c from Account where id IN :oppAccounts]);
        List<Contact> primaryContacts = [select AccountId,Account.Id,Account.npe01__SYSTEM_AccountType__c from Contact where Id IN :primaryContactIds];

        for (Contact thisContact : primaryContacts)
            if (thisContact.AccountId!=null)
                contactsAndOneToOneAccounts.put(thisContact.Id,thisContact.Account);

        //loop through opps again and then put the right accountid on the opp
        for (Opportunity o : oppList) {

            //add the contact id from the Account. We're guessing this is for the primary contact,
            //in the after insert trigger we'll get the actual value from the contact role
            if (o.AccountId != null && o.Primary_Contact__c == null) {
                Account acc = primaryAccounts.get(o.AccountId);
                if (acc.npe01__SYSTEM_AccountType__c == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE || 
                    acc.npe01__SYSTEM_AccountType__c == CAO_Constants.HH_ACCOUNT_TYPE) {
                    o.Primary_Contact__c = acc.npe01__One2OneContact__c;
                    o.npe01__Contact_Id_for_role__c = acc.npe01__One2OneContact__c;
                }
            }
            
            //add the account ID from the Contact
            if (o.AccountId == null && o.Primary_Contact__c != null) { //using the contact id, pull the account from the map
                
                if (contactsAndOneToOneAccounts.containsKey(o.Primary_Contact__c)) {
                    Account accountForContact = contactsAndOneToOneAccounts.get(o.Primary_Contact__c);
                    //for one-to-one accounts and HH Accounts, set the account id
                    if (accountForContact.npe01__SYSTEM_AccountType__c == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE || 
                        accountForContact.npe01__SYSTEM_AccountType__c == CAO_Constants.HH_ACCOUNT_TYPE) {
                        o.AccountId = accountForContact.Id;
                    }
                }            
            }
        }       
    }

    /*******************************************************************************************************
    * @description Detects changes in the opportunity Honoree Contact, and Notification Recipient Contact 
    * fields and updates Honoree Name and Notification Recipient Name fields.
    * @param listOpps List of opportunities from trigger.new
    * @param listOldOpps List of opportunities from trigger.old
    ********************************************************************************************************/
    private static void updateHonoreeNotificationFields(list<Opportunity> listOpps, list<Opportunity> listOldOpps) {
        map<id,Contact> mapConIdContact = new map<id,Contact>();
        list<String> listFields = new list<string>{'Honoree_Contact__c','Notification_Recipient_Contact__c'};

        for (integer i=0; i<listOpps.size(); i++) {
            Opportunity newOpp = listOpps[i];
            Opportunity oldOpp = listOldOpps!=null && !listOldOpps.IsEmpty() ? listOldOpps[i] : new Opportunity();
            for (string field : listFields) {
                if (newOpp.get(field) != oldOpp.get(field)) {
                    if (newOpp.get(field)!=null)
                        mapConIdContact.put((id)newOpp.get(field), null);
                    if (oldOpp.get(field)!=null)
                        mapConIdContact.put((id)oldOpp.get(field), null);
                }
            }
        }

        if (!mapConIdContact.isEmpty()) {
            mapConIdContact = new map<id,Contact>([SELECT Id, Name FROM Contact WHERE Id IN :mapConIdContact.keySet()]);
            for (integer i=0; i<listOpps.size(); i++) {
                Opportunity newOpp = listOpps[i];
                Opportunity oldOpp = listOldOpps!=null && !listOldOpps.IsEmpty() ? listOldOpps[i] : new Opportunity();

                for (string field : listFields) {
                    if (newOpp.get(field) != oldOpp.get(field)) {
                        //get the text name field
                        string nameField = field.replace('Contact','Name');
                        
                        string oldName;
                        if (oldOpp.get(field)!=null)
                            oldName = mapConIdContact.get((id)oldOpp.get(field)).Name;
                        //only overwrite the name field if it's blank, or if we've switched contacts and it contained the old name
                        if (newOpp.get(nameField) == null || newOpp.get(nameField) == oldName) {
                            id newContactId = (id)newOpp.get(field);
                            if (newContactId != null) {
                                newOpp.put(nameField, mapConIdContact.get(newContactId).Name);
                            } else {
                                newOpp.put(nameField, '');
                            }
                        }
                    }
                }
            }            
        }
    }

    /*******************************************************************************************************
    * @description Creates Contact Role records when an opp is inserted and there is no primary CR. Updates
    * existing primary CRs with the correct role value if it is null. Updates existing opps' Primary Contact
    * based on existing CRs.
    * @param opportunties List of opportunities meeting trigger criteria
    ********************************************************************************************************/
    private void upsertContactRolesAfterInsert(list<Opportunity> listOpp) {
        
        Map<Id, OpportunityContactRole> mapOppIdPrimaryOCR = new map<Id, OpportunityContactRole>();

        //primary contact OCRs need to be inserted for opportunity naming code that runs after this
        list<OpportunityContactRole> listPrimaryOCRForInsert = new list<OpportunityContactRole>();

        //Get existing primary contact roles for the trigger opps. 
        for (OpportunityContactRole ocr : [SELECT OpportunityId, ContactId, Role, IsPrimary FROM OpportunityContactRole WHERE IsPrimary = true and OpportunityId IN :listOpp]) {
            mapOppIdPrimaryOCR.put(ocr.OpportunityId, ocr);
        }

        for (Opportunity opp : listOpp) {
            //cloned opportunity is necessary in order to modify the Primary Contact lookup and update the record in the trigger set
            Opportunity clonedOpp = opp.clone(true,true,false,true);

            //process opportunities that already have a primary OCR
            if (mapOppIdPrimaryOCR.containsKey(opp.id)) {
                OpportunityContactRole ocr = mapOppIdPrimaryOCR.get(opp.id);

                //populate blank role
                if (string.isBlank(ocr.Role)) {
                    ocr.Role = getRole('Primary_Contact__c');
                    dmlWrapper.objectsToUpdate.add(ocr);
                }

                //if our primary contact lookup doesn't match the primary OCR, we'll need to fix the opportunity
                if (opp.Primary_Contact__c != ocr.ContactId) {
                    //create new opportunity record to allow DML on current trigger set
                    clonedOpp.Primary_Contact__c = ocr.ContactId;
                    clonedOpp.npe01__Contact_Id_for_Role__c = ocr.ContactId;
                    dmlWrapper.objectsToUpdate.add(clonedOpp);
                }
            //we don't have a primary contact role for this record, create one
            } else {
                if(opp.Primary_Contact__c != null) {
                    listPrimaryOCRForInsert.add(getOCR(opp,'Primary_Contact__c'));
                //if primary contact is null, still try using the contact id for role field
                //this is done for integrations that populate the npe01__Contact_Id_for_Role__c
                //after our beforeinsert trigger copied it to the primary contact field
                } else if (opp.npe01__Contact_Id_for_Role__c != null) {
                    try {
                        clonedOpp.Primary_Contact__c = opp.npe01__Contact_Id_for_Role__c;
                        listPrimaryOCRForInsert.add(getOCR(clonedOpp,'Primary_Contact__c'));
                    } catch (Exception ex) {
                        opp.addError(Label.npe01.Opportunity_Contact_Role_Error_Bad_Contact_Id);
                    }
                }
            }
            //using cloned opp to check if these OCRs need to be managed, as it relies on the Primary Contact field being populated
            if (needsManageOCR(clonedOpp, new Opportunity(), 'Honoree_Contact__c')) {
                dmlWrapper.objectsToInsert.add(getOCR(clonedOpp,'Honoree_Contact__c'));
            }

            if (needsManageOCR(clonedOpp, new Opportunity(), 'Notification_Recipient_Contact__c')) {
                dmlWrapper.objectsToInsert.add(getOCR(clonedOpp, 'Notification_Recipient_Contact__c'));
            }
        }

        if (!listPrimaryOCRForInsert.isEmpty())
            insert listPrimaryOCRForInsert;
    }

    /*******************************************************************************************************
    * @description Runs opportunities through opportunity naming.
    * @param listOpps List of opportunities from trigger.new
    ********************************************************************************************************/
    private void updateOpportunityNames(list<Opportunity> listOpps) {
        //name opportunities
        map<id,Opportunity> mapOppNaming = new map<id,Opportunity>(OPP_OpportunityNaming.getOppNamesAfterInsert(listOpps));
        
        //update existing dmlWrapper objects with opp name to avoid errors updating the same opportunity twice
        for (sObject dmlObj : dmlWrapper.objectsToUpdate) {
            id objId = (id) dmlObj.get('id');
            if (mapOppNaming.containsKey(objId)) {
                dmlObj.put('Name',mapOppNaming.get(objId).Name);
                mapOppNaming.remove(objId);
            }
        }
        
        for (Opportunity opp : listOpps) {
            //add renamed opportunities that weren't updated as part of the contact roles fixing
            if (mapOppNaming.containsKey(opp.Id) && mapOppNaming.get(opp.id).Name != opp.Name) {
                dmlWrapper.objectsToUpdate.add((sObject) mapOppNaming.get(opp.id));
            }
        }
    }

    /*******************************************************************************************************
    * @description Detects changes in the opportunity Primary Contact, Honoree Contact, and Notification 
    * Recipient Contact fields and creates, updates or deletes the associated opportunity contact roles.
    * @param listOpps List of opportunities from trigger.new
    * @param listOldOpps List of opportunities from trigger.old
    ********************************************************************************************************/
    private void upsertContactRolesAfterUpdate(list<Opportunity> listOpps, list<Opportunity> listOldOpps) { 
        map<id, map<id,OpportunityContactRole>> mapOppIdMapConIdOCR = new map<id, map<id,OpportunityContactRole>>();

        //find changed opportunities and instantiate a map entry to hold OCRs
        for (integer i=0; i<listOpps.size(); i++) {
            if (listOpps[i].Primary_Contact__c != listOldOpps[i].Primary_Contact__c ||
                needsManageOCR(listOpps[i], listOldOpps[i], 'Honoree_Contact__c') || 
                needsManageOCR(listOpps[i], listOldOpps[i], 'Notification_Recipient_Contact__c')) {

                mapOppIdMapConIdOCR.put(listOpps[i].id, new map<id,OpportunityContactRole>());
            }
        }

        //query for OCRs, hand off to processing methods
        if (!mapOppIdMapConIdOCR.isEmpty()) {
            for (OpportunityContactRole ocr : [SELECT OpportunityId, ContactId, isPrimary, Role FROM OpportunityContactRole 
                                                    WHERE OpportunityId IN :mapOppIdMapConIdOCR.keyset()]) {
                mapOppIdMapConIdOCR.get(ocr.OpportunityId).put(ocr.ContactId, ocr);
            }

            for (integer i=0; i<listOpps.size(); i++) {
                Opportunity newOpp = listOpps[i];
                Opportunity oldOpp = listOldOpps[i];
                //first, manage primary contact role if the primary contact has changed
                if (needsManageOCR(newOpp, oldOpp, 'Primary_Contact__c')) {
                    managePrimaryOCR(newOpp, oldOpp, mapOppIdMapConIdOCR.get(newOpp.id));
                }
                //then manage honoree, if it has changed and it's not the same as the primary contact
                if (needsManageOCR(newOpp, oldOpp, 'Honoree_Contact__c')) {
                    manageOtherOCR(newOpp, oldOpp, mapOppIdMapConIdOCR.get(newOpp.id), 'Honoree_Contact__c');
                }
                //finally, manage the notification, if it has changed and it's not the same as either contact already dealt with
                if (needsManageOCR(newOpp, oldOpp, 'Notification_Recipient_Contact__c')) {
                    manageOtherOCR(newOpp, oldOpp, mapOppIdMapConIdOCR.get(newOpp.id), 'Notification_Recipient_Contact__c');
                }
            }
        }
    }

     /*******************************************************************************************************
    * @description Manages create and update of the primary Opportunity Contact Role.
    * @param opp The current opportunity from trigger.new
    * @param oldOpp The old opportunity from trigger.old
    * @param mapConIdOCR A map of contact id to Opportunity Contact Role records for this opportunity.
    * @return void This method adds records to dmlWrapper and returns nothing.
    ********************************************************************************************************/
    private void managePrimaryOCR(Opportunity opp, Opportunity oldOpp, map<id,OpportunityContactRole> mapConIdOCR) {

        id idNewPrimaryCon = opp.Primary_Contact__c;
        id idOldCon = oldOpp.Primary_Contact__c;

        //if we have a primary OCR, separate it from the list
        OpportunityContactRole primaryOCR;
        if (mapConIdOCR!=null) for (OpportunityContactRole ocr : mapConIdOCR.values()) {
            if (ocr.isPrimary) {
                primaryOCR = ocr;
                mapConIdOCR.remove(ocr.ContactId);
            }
        }
        //primary contact is blanked out 
        if (idNewPrimaryCon == null) {
            //if we have an existing primary OCR, delete it
            if (primaryOCR != null)
                dmlWrapper.objectsToDelete.add(primaryOCR);

        //primary contact went from blank to a new contact
        } else if (idNewPrimaryCon != null) {
            //create or update the existing primary OCR
            if (primaryOCR == null) {
                dmlWrapper.objectsToInsert.add(getOCR(opp,'Primary_Contact__c'));
            } else {
                if (primaryOCR.ContactId != idNewPrimaryCon || string.isBlank(primaryOCR.Role)) {
                    primaryOCR.ContactId = idNewPrimaryCon;
                    if (string.isBlank(primaryOCR.Role))
                        primaryOCR.Role = getRole('Primary_Contact__c');
                    
                    dmlWrapper.objectsToUpdate.add((SObject)primaryOCR);
                }
            }

            //if we have an existing hh member OCR for the primary contact
            //and the old contact doesn't have an OCR, update the hh member OCR with the old contact
            //otherwise, delete it
            if (mapConIdOCR!=null && mapConIdOCR.containsKey(idNewPrimaryCon)) {
                OpportunityContactRole newConOCR = mapConIdOCR.get(idNewPrimaryCon);
                string hhMemberRole = UTIL_CustomSettingsFacade.getHouseholdsSettings().npo02__Household_Member_Contact_Role__c;
                
                if (newConOCR.Role == hhMemberRole && idOldCon != null && !mapConIdOCR.containsKey(idOldCon)) {
                    newConOCR.ContactId=idOldCon;
                    dmlWrapper.objectsToUpdate.add((SObject)newConOCR);
                
                //no old contact to switch with, just delete the new contact's old OCR
                } else {
                    dmlWrapper.objectsToDelete.add((SObject)newConOCR);
                }
            }
        }
    }

    /*******************************************************************************************************
    * @description Manages create and update of the Opportunity Contact Roles for the Honoree and Notification
    * Recipient contact lookup fields.
    * @param opp The current opportunity from trigger.new
    * @param oldOpp The old opportunity from trigger.old
    * @param mapConIdOCR A map of contact id to Opportunity Contact Role records for this opportunity.
    * @param fieldName The API name of the field to verify
    * @return void This method adds records to dmlWrapper and returns nothing.
    ********************************************************************************************************/
    private void manageOtherOCR(Opportunity opp, Opportunity oldOpp, map<id,OpportunityContactRole> mapConIdOCR, string fieldName) {
        id idNewCon = (id)opp.get(fieldName);
        id idOldCon = (id)oldOpp.get(fieldName);
        OpportunityContactRole ocrNewCon = mapConIdOCR.get(idNewCon);
        OpportunityContactRole ocrOldCon = mapConIdOCR.get(idOldCon);

        if (idNewCon == null) {
            //if the lookup field was blanked out, and an OCR matches the previous contact with the correct role, delete it
            if (idOldCon!=null && ocrOldCon!=null) {
                if (ocrOldCon.Role == getRole(fieldName)) {
                    dmlWrapper.objectsToDelete.add((SObject)ocrOldCon);
                }
            }
        //the lookup has a new contact
        } else if (idNewCon != null) {
            //net new contact, i.e. no old contact; create a contact role if we don't have one for this contact yet
            if (idOldCon == null && ocrNewCon == null) {
                dmlWrapper.objectsToInsert.add(getOCR(opp,fieldName));
            }
            //contact lookup changed from one contact to another
            if (idOldCon != null) {
                //an OCR for the old contact exists, and has the role for this field
                if (ocrOldCon != null && ocrOldCon.Role == getRole(fieldName)) {
                    //our new contact doesn't yet have an OCR, so update the old one to the new contact
                    if (ocrNewCon == null) {
                        ocrOldCon.ContactId = idNewCon;
                        dmlWrapper.objectsToUpdate.add(ocrOldCon);
                    //our new contact already has an OCR, delete the old one
                    } else {
                        dmlWrapper.objectsToDelete.add(ocrOldCon);
                    }
                //new contact doesn't have an OCR yet, create one with the appropriate role
                } else if (ocrNewCon == null) {
                    dmlWrapper.objectsToInsert.add(getOCR(opp,fieldName));
                }
            }
        }
    }

     /*******************************************************************************************************
    * @description For a given opportunity contact lookup field, returns the role value associated with that
    * field as defined in settings.
    * @param fieldName The name of the opportunity field.
    * @return string The role value associated with the field.
    ********************************************************************************************************/
    private string getRole(string fieldName) {
        string result;
        if (fieldName == 'Primary_Contact__c')
            result = UTIL_CustomSettingsFacade.getContactsSettings().npe01__Opportunity_Contact_Role_Default_role__c;
        else if (fieldName == 'Honoree_Contact__c')
            result = UTIL_CustomSettingsFacade.getContactsSettings().Honoree_Opportunity_Contact_Role__c;
        else if (fieldName == 'Notification_Recipient_Contact__c')
            result = UTIL_CustomSettingsFacade.getContactsSettings().Notification_Recipient_Opp_Contact_Role__c;
        return result;
    }

    /*******************************************************************************************************
    * @description Returns a new OpportunityContactRole record, based on an opportunity and the API name of 
    * the contact lookup field.
    * @param opp The opportunity for which to create an OCR.
    * @param fieldName The name of the opportunity field.
    * @return OpportunityContactRole The newly created OCR.
    ********************************************************************************************************/
    private OpportunityContactRole getOCR(Opportunity opp, string fieldName) {
        OpportunityContactRole ocr = new OpportunityContactRole();
        ocr.OpportunityId = opp.id;
        ocr.ContactId = (id)opp.get(fieldname);
        ocr.Role = getRole(fieldName);
        if (fieldName == 'Primary_Contact__c')
            ocr.isPrimary = true;
        return ocr;
    }

    /*******************************************************************************************************
    * @description Verifies if an opportunity contact lookup needs autoamtic management of the Opportunity 
    * Contact Role associated with that field. For automatic management, the lookup field's associated role custom
    * setting needs a value, the value in the lookup field needs to be new or have changed, and the contact
    * needs to be different from a contact that's already managed.
    * @param opp The current opportunity from trigger.new
    * @param oldOpp The old opportunity from trigger.old
    * @param fieldName The API name of the field to verify
    * @return boolean Whether this opportunity and field needs OCR handling.
    ********************************************************************************************************/
    private boolean needsManageOCR(Opportunity opp, Opportunity oldOpp, string fieldName) {
        //no role for this field
        if (string.isBlank(getRole(fieldName)))
            return false;
        //field value hasn't changed
        if (opp.get(fieldName) == oldOpp.get(fieldName))
            return false;
        //the honoree contact is the same as the primary contact, thus is already managed
        if (fieldName == 'Honoree_Contact__c' && opp.Honoree_Contact__c == opp.Primary_Contact__c)
            return false;
        //the notification recipient contact is the same as the primary contact, thus the OCR is already managed
        if (fieldName == 'Notification_Recipient_Contact__c' && opp.Notification_Recipient_Contact__c == opp.Primary_Contact__c)
            return false;
        //the notification recipient contact is the same as the honoree contact, thus the OCR is already managed
        if (fieldName == 'Notification_Recipient_Contact__c' && opp.Notification_Recipient_Contact__c == opp.Honoree_Contact__c)
            return false;

        return true;
    }
}