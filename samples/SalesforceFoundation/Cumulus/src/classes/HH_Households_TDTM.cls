/*
    Copyright (c) 2009, Salesforce.org
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
* @date 2011 (2.0)
* @group Households
* @group-content ../../ApexDocContent/Households.htm
* @description Trigger Handler on Contact to deal with managing Households (Objects), and naming exclusions
* on contact.
*/
public without sharing class HH_Households_TDTM extends TDTM_Runnable {
          	
    /*******************************************************************************************************
    * @description Trigger Handler on Contacts that handles managing Households (Objects) and naming exclusions
    * on contact.
    * @param listNew List of Contacts from trigger new. 
    * @param listOld List of Contacts from trigger old. 
    * @param triggerAction Which trigger event (BeforeInsert, AfterInsert, etc.). 
    * @param objResult DescribeSObjectResult for Contacts 
    * @return dmlWrapper The objects to insert/update/delete
    ********************************************************************************************************/
    public override DmlWrapper run(List<SObject> newlist, List<SObject> oldlist, 
        TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {
        
        list<Contact> contacts = newlist;
        list<Contact> oldContacts = oldlist;
        DmlWrapper dmlWrapper = new DmlWrapper();

        //if this is before insert or update, sync the household name exclustions
        if (triggerAction == TDTM_Runnable.Action.BeforeInsert || triggerAction == TDTM_Runnable.Action.BeforeUpdate) {
            for (integer i=0; i<contacts.size();i++) {
                Contact con = contacts[i];
                
                //before insert case: 
                //first, copy multiselect exclusions to checkboxes, then recreate multiselect values from checboxes
                //this ensures all values from either source are kept
                if (triggerAction == TDTM_Runnable.Action.BeforeInsert) {
                    if (con.npo02__Naming_Exclusions__c != null)
                        copyNamingExclusionsFromMSP(con);
                    
                    if (con.Exclude_from_Household_Name__c || con.Exclude_from_Household_Informal_Greeting__c || con.Exclude_from_Household_Formal_Greeting__c)
                        copyNamingExclusionsFromCheckboxes(con);

                //before update case:
                //first, copy values from checkboxes to the multiselect picklist if checkboxes have changed
                //then, copy from multiselect to checkboxes if multiselect has changed
                //if both have changed, prefers checkboxes
                } else {
                    Contact oldCon = oldContacts[i];
                    if (con.Exclude_from_Household_Name__c != oldCon.Exclude_from_Household_Name__c || 
                        con.Exclude_from_Household_Informal_Greeting__c != oldCon.Exclude_from_Household_Informal_Greeting__c ||
                        con.Exclude_from_Household_Formal_Greeting__c != oldCon.Exclude_from_Household_Formal_Greeting__c) {

                        copyNamingExclusionsFromCheckboxes(con);
                    } else if (con.npo02__Naming_Exclusions__c != oldCon.npo02__Naming_Exclusions__c) {
                        copyNamingExclusionsFromMSP(con);
                    }
                }
            }
        } else {
            
            // this code assumed newlist was filled in for AfterDelete, rather than oldlist
            // rather than change the code to require two seperate contact loops, just
            // continue the strategy.
            if (triggerAction == TDTM_Runnable.Action.AfterDelete) {
            	contacts = oldlist;
            	oldContacts = null;
            }
            
            map<Id, Contact> newcmap = new map<Id, Contact>(contacts);
            map<Id, Contact> oldcmap;
            if (oldContacts != null) oldcmap = new map<Id, Contact>(oldContacts);
            
                	   
            npo02__Households_Settings__c currentHouseholdsSettings = UTIL_CustomSettingsFacade.getHouseholdsSettings();
            List<Contact> householdInserts = new List<Contact>();
            List<Contact> contactEvaluations = new List<Contact>();
            List<Contact> householdDeletes = new List<Contact>();
            list<id> householdnameupdates = new list<id>();        
            list<id> oldHouseholds = new list<id>();
            map<id, integer> oldHouseholdSize = new map<id, integer>();
            set<Id> setHHIdToRollup = new set<Id>();
            
            Set<Id> mergedWinningContact = new Set<Id>();
                    
            //typically this is handled through the contact, but for old households
            //being updated, we need to get the list here
            list<SObject> blankHHDeletes = new list<SObject>();
         
            //if its an update, we need to get some data about their
            //old Household for processing
            if (triggerAction==TDTM_Runnable.Action.AfterUpdate){
                
                for (Contact c : Contacts){
                    
                    if (oldcmap.get(c.id).npo02__household__c != null){
                        oldHouseholds.add(oldcmap.get(c.id).npo02__household__c);
                        //UTIL_Debug.debug('ID ADDED================' + oldcmap.get(c.id).npo02__household__c);
                    }
                    
                }            
                
                //create a map from the old household id and its size
                for (AggregateResult a : [select npo02__Household__c oldhh, count(id) membercount from Contact where npo02__Household__c IN :oldHouseholds group by npo02__Household__c]){
                    //UTIL_Debug.debug('AR RESULTS=====ID: ' + (id)a.get('oldhh') + ' MEMBERCOUNT: ' + (integer)a.get('membercount'));
                    oldHouseholdSize.put((id)a.get('oldhh'), (integer)a.get('membercount'));
                }
                
                //an agg query doesn't pick empty HHs, so if we have an ID, and no
                //HH in the ag, we know we have an empty hh
                for (id hhid : oldHouseholds){
                    if (!oldHouseholdSize.containskey(hhid))
                       oldHouseholdSize.put(hhid, 0);               
                }            
            }
         
            Set<String> hhNamingFields = null;  // defer creating until needed.
            Integer i = 0;
            for(Contact c : contacts) {
                //AFTER INSERT - NOTE: naming has to be handled inside othe household inserts, 
                //since we don't have the contact's household ids until after the insert
                //we'll check the trigger action again there to make sure it doesn't run twice
                if ( triggerAction==TDTM_Runnable.Action.AfterInsert )
                {
                    //If the user has chosen for all Contacts to have households, add them all to the list
                    //for Household creation
                    if (c.npo02__household__c == null && currentHouseholdsSettings.npo02__Household_Rules__c == HH_Households.ALL_PROCESSOR)
                        householdInserts.add(c);
                    //If the user has chosen for only Contacts connected to Individual Accounts to have Households
                    //add them all to a list for evaluation
                    else if (c.npo02__household__c == null && currentHouseholdsSettings.npo02__Household_Rules__c == HH_Households.ALL_INDIVIDUALS_PROCESSOR)
                        contactEvaluations.add(c);
                    else if  (c.npo02__household__c != null)
                        householdnameupdates.add(c.npo02__household__c);
                }

                //AFTER UPDATE
                if ( triggerAction==TDTM_Runnable.Action.AfterUpdate )
                {            
                    //if we're doing an update, pile up all of the households into a list
                    //and send it to our naming code for action
                    //first contact's names to their old names, and see if anything changed
                    //salutation
                    if (c.npo02__household__c == null && currentHouseholdsSettings.npo02__Household_Rules__c == HH_Households.ALL_PROCESSOR)
                        householdInserts.add(c);
                    else if (c.npo02__household__c == null && currentHouseholdsSettings.npo02__Household_Rules__c == HH_Households.ALL_INDIVIDUALS_PROCESSOR)
                        contactEvaluations.add(c);
                    
                    if (c.npo02__household__c != null) {
                        if (hhNamingFields == null)
                            hhNamingFields = HH_HouseholdNaming.iNaming.setHouseholdNameFieldsOnContact();
                            
                        for (String fieldName : hhNamingFields) {
                            // make sure to use case sensitive comparison
                            string strName = String.valueOf(newcmap.get(c.id).get(fieldName));
                            if (strName == null)
                                strName = '';
                            string strNameOld = String.valueOf(oldcmap.get(c.id).get(fieldName));
                            if (strNameOld == null)
                                strNameOld = '';
                            if (!strName.equals(strNameOld)) {
                                householdnameupdates.add(c.npo02__household__c);
                                break;
                            }
                        }
                    }
                                        
                    //if they've changed households, we need to query the old
                    //household to see if anybody is left, if so, send it for renaming
                    //if not, delete it.
                    if (newcmap.get(c.id).npo02__household__c != oldcmap.get(c.id).npo02__household__c){
                        householdnameupdates.add(c.npo02__household__c);
                        setHHIdToRollup.add(c.npo02__household__c);
                        if (oldHouseholdSize.get(oldcmap.get(c.id).npo02__household__c) > 0){
                            householdnameupdates.add(oldcmap.get(c.id).npo02__household__c);
                            setHHIdToRollup.add(oldcmap.get(c.id).npo02__household__c);
                        }
                        else if(oldHouseholdSize.get(oldcmap.get(c.id).npo02__household__c) == 0){
                            blankHHDeletes.add(new npo02__Household__c(Id = oldcmap.get(c.id).npo02__household__c));
                        }       
                    }                    
                    else if (newcmap.get(c.id).npo02__Naming_Exclusions__c != oldcmap.get(c.id).npo02__Naming_Exclusions__c)
                        householdnameupdates.add(c.npo02__household__c);
                }
                
                if ( triggerAction==TDTM_Runnable.Action.AfterDelete ){
                    if (c.npo02__household__c != null )
                        householdDeletes.add(c);
                
                    //check if this Contact is the loser in a merge
                    if (c.MasterRecordId != null)
                        mergedWinningContact.add(c.MasterRecordId);
                }
                i += 1;
            }
            
            if (mergedWinningContact.size() > 0) {
            	// this fires off opp rollups, which we aren't going to move to dmlWrapper!
                contactMergeUpdate(mergedWinningContact);        
            }
            
            if(!blankHHDeletes.isEmpty()) {
                //Database.delete(blankHHDeletes);
                dmlWrapper.objectsToDelete.addAll(blankHHDeletes);
            }
            
            if (householdInserts.size() > 0 && triggerAction==TDTM_Runnable.Action.AfterInsert)        
                insertHousehold(householdInserts, true, dmlWrapper);
            else if (householdInserts.size() > 0 && triggerAction==TDTM_Runnable.Action.AfterUpdate)
                insertHousehold(householdInserts, false, dmlWrapper);
            
            if (contactEvaluations.size() > 0 && triggerAction==TDTM_Runnable.Action.AfterInsert)        
                evaluateContacts(contactEvaluations, true, dmlWrapper);
            else if (contactEvaluations.size() > 0 && triggerAction==TDTM_Runnable.Action.AfterUpdate)
                evaluateContacts(contactEvaluations, false, dmlWrapper);
            
            if (householdDeletes.size() > 0)        
                deleteHousehold(householdDeletes, dmlWrapper);
            
            if (householdnameupdates.size() > 0){
                              
                if (currentHouseholdsSettings.npo02__Async_Household_Naming__c == true && !system.isFuture() && !system.isBatch())
                    HH_HouseholdNaming.FutureUpdateNames(householdnameupdates);
                else{
                    HH_HouseholdNaming hn = new HH_HouseholdNaming(); 
                    hn.UpdateNames(householdnameupdates);
                }                   
            }
            
            if (setHHIdToRollup.size() > 0) {
                if (system.isFuture() || system.isBatch() || setHHIdToRollup.size() <= 2) {
                    RLLP_OppRollup_UTIL oppRollupUtil = new RLLP_OppRollup_UTIL();
                    map<id, npo02__Household__c> hhmap = new map<id, npo02__Household__c>((list<npo02__Household__c>)Database.query(oppRollupUtil.buildHouseholdQuery() +
                                                                                    ' where id IN :setHHIdToRollup'));
                    RLLP_OppRollup rg = new RLLP_OppRollup();
                    rg.rollupHouseholds(hhmap);
                } else {
                    RLLP_OppRollup.rollupHouseholdsFuture(setHHIdToRollup);
                }                                                                         
            }
        }
        return dmlWrapper;
    }
    
    /*******************************************************************************************************
    * @description Copies the values from the naming exclusions multiselect picklist to the individual
    * checkbox fields.
    * @param con The Contact 
    * @return void
    ********************************************************************************************************/
    public static void copyNamingExclusionsFromMSP(Contact con) {
        string strExclusions = con.npo02__Naming_Exclusions__c;
        if (strExclusions == null)
            strExclusions = '';
        con.Exclude_from_Household_Name__c = strExclusions.contains('Household__c.Name');
        con.Exclude_from_Household_Formal_Greeting__c = strExclusions.contains('Household__c.Formal_Greeting__c');
        con.Exclude_from_Household_Informal_Greeting__c = strExclusions.contains('Household__c.Informal_Greeting__c');
    }

    /*******************************************************************************************************
    * @description Copies the values from the naming exclusions checkboxes to the multiselect picklist.
    * @param con The Contact 
    * @return void
    ********************************************************************************************************/
    public static void copyNamingExclusionsFromCheckboxes(Contact con) {
        string mspValue = '';
        
        if (con.Exclude_from_Household_Name__c)
            mspValue += 'Household__c.Name;';
        if (con.Exclude_from_Household_Formal_Greeting__c)
            mspValue += 'Household__c.Formal_Greeting__c;';
        if (con.Exclude_from_Household_Informal_Greeting__c)
            mspValue += 'Household__c.Informal_Greeting__c;';
        
        if (mspValue.endsWith(';'))
            mspValue = mspValue.substring(0,mspValue.length()-1);

        con.npo02__Naming_Exclusions__c = mspValue;
    }

    /*******************************************************************************************************
    * @description kicks off Opportunity Rollups after contacts have been merged.
    * @param winningContacts Set of Contact Id's 
    * @return void
    ********************************************************************************************************/
    public static void contactMergeUpdate(Set<Id> winningContacts){     
        Set<Id> oppIDs = new Set<Id>();
        List<OpportunityContactRole> ocrlist = new List<OpportunityContactRole>();
        ocrlist = [select OpportunityId from OpportunityContactRole where ContactId IN :winningContacts and isPrimary = true];      
        
        for (OpportunityContactRole ocr : ocrlist)
            oppIds.add(ocr.OpportunityId);
                
        if(!oppIDs.isEmpty()) {
            if (system.isFuture() || system.isBatch())
                RLLP_OppRollup.rollupContactsandHouseholdsForTrigger(oppIds);                        
            else            
                RLLP_OppRollup.rollupContactsandHouseholdsForTriggerFuture(oppIds);                        
        }            
    }
    
    /*******************************************************************************************************
    * @description Creates a new Household Object for each contact, unless excluded by recordType
    * @param contacts List of Contacts 
    * @param isInsertTrigger Whether called from the afterInsert trigger (vs. afterUpdate trigger) on Contacts
    * @param dmlWrapper Not used. Commits to the database so Id's can be updated in the Contact.    
    * @return void
    ********************************************************************************************************/
    public static void insertHousehold(Contact[] contacts, boolean isInsertTrigger, DmlWrapper dmlWrapper)
    {
        List<npo02__household__c> householdInserts = new List<npo02__household__c>();
        //reload to evaluate insert criteria
        npo02__Households_Settings__c currentHouseholdsSettings = UTIL_CustomSettingsFacade.getHouseholdsSettings();
                
        set<id> conRecordTypesToExclude = new set<id>();
        if (currentHouseholdsSettings.npo02__Household_Creation_Excluded_Recordtypes__c != null) {
            conRecordTypesToExclude = new Set<Id>((List<Id>)currentHouseholdsSettings.npo02__Household_Creation_Excluded_Recordtypes__c.split(';'));
        }
        
        boolean recordTypesOnCons = false;
        if(Schema.sObjectType.Contact.fields.getMap().get('RecordTypeId') != null)
            recordTypesOnCons = true;
        
        for(Contact c : contacts)
        {
            if (!recordTypesOnCons || !conRecordTypesToExclude.contains((id)c.get('RecordTypeId'))){            
                npo02__household__c h = new npo02__household__c(); 
                String hName = c.LastName;
                hName += ' ' + system.label.npo02.DefaultHouseholdName;
                h.Name = hName;
                //sync the primary address block
                h.npo02__MailingStreet__c = c.MailingStreet;
                h.npo02__MailingCity__c = c.MailingCity;
                h.npo02__MailingState__c = c.MailingState;
                h.npo02__MailingPostalCode__c  = c.MailingPostalCode;
                h.npo02__MailingCountry__c = c.MailingCountry;
                h.npo02__HouseholdPhone__c = c.HomePhone;
                h.npo02__HouseholdEmail__c = c.Email;
                householdInserts.add(h);
            }                
        }
        List<Contact> contactUpdates = new List<Contact>();
        
        if (householdInserts.size() > 0)
        {            
            // ideally, we'd do this all with dmlWrapper.  But since we
            // need to update the contact's hhId, we can't defer creating the HH's.
            
            Database.SaveResult[] lsr = Database.insert(householdInserts, false);
                       
            Integer i = 0;
            for (Contact c : contacts)
            {
                if (!recordTypesOnCons || !conRecordTypesToExclude.contains((id)c.get('RecordTypeId'))){
                    if (lsr[i].isSuccess() == true)
                    {
                        //write the new Household Ids to the Contacts
                        Contact clone = c.Clone(true,false);
                        clone.npo02__household__c = lsr[i].getId();
                        contactUpdates.add(clone);
                    }
                    else{
                        Database.Error err = lsr[i].getErrors()[0];                 
                        c.addError(err.getMessage());
                    }
                    i += 1;
                }
            }
            
            if (contactUpdates.size() > 0) {
                // using dmlWrapper here caused too many errors with other 
                // trigger handlers on contact, so do the update directly.
                //dmlWrapper.objectsToUpdate.addAll(contactUpdates);
                //Database.SaveResult[] dbsr = Database.update(contactUpdates, false);
                // all or nothing so errors not lost.
                update contactUpdates;
            }
        
            if (isInsertTrigger){
                list<id> hhidsfornaming = new list<id>();
                
                for (Database.SaveResult sr : lsr){
                    if (sr.isSuccess())
                        hhidsfornaming.add(sr.getID());
                }
                
                //send it all out for (re)naming
                //secret setting for async processing... ooooooooh....
                
                if (!system.isFuture() && !system.isBatch() && currentHouseholdsSettings.npo02__Async_Household_Naming__c == true)
                    HH_HouseholdNaming.FutureUpdateNames(hhidsfornaming);
                else{
                    HH_HouseholdNaming hn = new HH_HouseholdNaming();
                    hn.UpdateNames(hhidsfornaming);
                }
                    
            }
        }
    }
    
    /*******************************************************************************************************
    * @description Determines if a Contact Should have a household object created 
    * @param contacts List of Contacts 
    * @param isAfterTrigger Whether called from the afterInsert trigger (vs. afterUpdate trigger) on Contacts
    * @param dmlWrapper Not used. Commits are immediate.    
    * @return void
    ********************************************************************************************************/
    public static void evaluateContacts(Contact[] contacts, boolean isAfterInsert, DmlWrapper dmlWrapper) 
    {
        List<Contact> householdInserts = new List<Contact>();

        List<Id> accountIds = new List<Id>();
        for(Contact c : contacts)
        {
            if (c.accountId != null)
            {
                accountIds.add(c.AccountId); 
            }
        }
        //get all the Accounts so we can test to see if they are individuals
        Map<Id,Account> contactAccountMap = new Map<Id,Account>([Select Id, npe01__SYSTEMIsIndividual__c, Name from Account where Id in :accountIds]);
        for(Contact c : contacts)
        {
            if (c.AccountId != null){
                Account acc = contactAccountMap.get(c.AccountId);
                //check the system field on Account to see if the account is an individual
                if (acc.npe01__SYSTEMIsIndividual__c == true)
                {
                    householdInserts.add(c);
                }
            } else if (c.AccountId == null){
                householdInserts.add(c);
            }
        }
        if ( householdInserts.size() > 0)
        {
            //hand any Contacts that need Households over to the insert method
            insertHousehold(householdInserts, isAfterInsert, dmlWrapper);
        }
    }

    /*******************************************************************************************************
    * @description Deletes a Household object when no Contacts are related to it. Called by the AfterDelete
    * trigger handler on Contact.  Also detects if other Contacts remain in the Household, and thus the 
    * Household needs its names updated.
    * @param contacts List of Contacts being deleted
    * @param dmlWrapper On return, holds the Households to delete.    
    * @return void
    ********************************************************************************************************/
    public static void deleteHousehold(Contact[] contacts, DmlWrapper dmlWrapper){
            
        list<SObject> householdDeletes = new list<SObject>();
        map<Id,Integer> householdCountMap = new map<Id,Integer>();
        list<id> householdrenames = new list<id>();
        //reload to evaluate delete criteria
        npo02__Households_Settings__c currentHouseholdsSettings = UTIL_CustomSettingsFacade.getHouseholdsSettings();

        //Generate a list of Household Ids
        for(Contact c : contacts){
            if (c.npo02__household__c != null)
                householdCountMap.put(c.npo02__household__c,0);
        }

        //Loop through all Contacts at one of the Household Ids
        for (Contact c : [Select Id, npo02__Household__c from Contact where npo02__Household__c in :householdCountMap.keyset()])
        {
            Integer lastCount = householdCountMap.get(c.npo02__household__c);
            lastCount += 1;
            householdCountMap.put(c.npo02__household__c,lastCount);
        }

        //Loop through all the contacts to determine if they can be deleted
        for (Contact c : contacts){
            //If there are no other Contacts on the Account
            if (householdCountMap.get(c.npo02__household__c) < 1 )
                householdDeletes.add(new npo02__Household__c(Id = c.npo02__household__c));
            //otherwise, we need to update the household
            else
                householdrenames.add(c.npo02__household__c);        
        }

        if (householdDeletes.size() > 0) {
            dmlWrapper.objectsToDelete.addAll(householdDeletes);
        }           
        
        if (householdrenames.size() > 0){
            if (!system.isFuture() && !system.isBatch() && currentHouseholdsSettings.npo02__Async_Household_Naming__c == true)
                HH_HouseholdNaming.FutureUpdateNames(householdrenames);
            else{
                HH_HouseholdNaming hn = new HH_HouseholdNaming();
                hn.UpdateNames(householdrenames);
            }
        }
    }

}