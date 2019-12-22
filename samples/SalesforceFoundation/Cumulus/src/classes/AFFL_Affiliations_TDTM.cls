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
* @date 2009
* @group Affiliations
* @group-content ../../ApexDocContent/Affiliations.htm
* @description  Trigger handler for Affiliations related operations on Contacts, Accounts, or Affiliations.
*/
public class AFFL_Affiliations_TDTM extends TDTM_Runnable {

    // static flags to prevent some recursive calls 
    public static boolean doNotRunForContacts = false;
    public static boolean hasRunForAffiliations = false;
    public static boolean hasRunForAccounts = false;

    /*******************************************************************************************************
    * @description Trigger Handler on Contact, Account, or Affiliation, dispatches to the correct method
    * based on object type.
    * @param listNew the list of contacts, accounts, or affiliations from trigger new. 
    * @param listOld the list of contacts, accounts, or affiliations from trigger old. 
    * @param triggerAction Which action triggered the current context. (Before or After; Insert, Update, 
    * Delete, or Undelete.)
    * @param objResult The field describe object for the current trigger context. 
    * @return dmlWrapper Objects that need to be inserted or updated after the processing of the trigger.
    ********************************************************************************************************/    
    public override DmlWrapper run(List<SObject> newlist, List<SObject> oldlist, 
        TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {
            
        // dispatch to the correct handler
        if (objResult.getsObjectType() == Contact.sObjectType && !doNotRunForContacts) {
            DmlWrapper wrapper = runContactTriggerHandler(newlist, oldlist, triggerAction, objResult);
            
            // we used to prevent any further recursive call with contacts and affiliations,
            // but it turns out in some scenarios, before the AfterInsert of a contact calls us,
            // another trigger handler may update the contact, causing the AfterUpdate to come thru.
            // if we set the flag to stop recursion, then we won't process the AfterInsert, where
            // we create the initial affiliation. thus we no longer set a recursion prevention flag,
            // and we only use doNotRunForContacts where we actually have a use case to prevent recursion.
            // as for the Affiliations and Account recursion flags, we have no current known issues with
            // their recursion prevention, so we left that as is.
            //
            // hasRunForContacts = true;
            
            return wrapper;
        } else if (objResult.getsObjectType() == npe5__Affiliation__c.sObjectType && !hasRunForAffiliations) {
            DmlWrapper wrapper = runAfflTriggerHandler(newlist, oldlist, triggerAction, objResult);
            hasRunForAffiliations = true;
            return wrapper;
        } else if (objResult.getsObjectType() == Account.sObjectType && !hasRunForAccounts) {
            DmlWrapper wrapper = runAccountTriggerHandler(newlist, oldlist, triggerAction, objResult);
            hasRunForAccounts = true;
            return wrapper;
        } else {
            return null;
        }
    }
                
    /*******************************************************************************************************
    * @description Affiliation trigger handler on Contact. Creates affiliations and/or updates existing 
    * affiliation primary checkbox and status field.
    * @param listNew The list of contacts from trigger new. 
    * @param listOld The list of contacts from trigger old. 
    * @param triggerAction Which action triggered the current context. (Before or After; Insert, Update, 
    * Delete, or Undelete.)
    * @param objResult The field describe object for Contact.
    * @return dmlWrapper Objects that need to be inserted or updated after the processing of the trigger.
    ********************************************************************************************************/
    private DmlWrapper runContactTriggerHandler(List<SObject> newlist, List<SObject> oldlist, 
        TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {
        DmlWrapper dmlWrapper = new DmlWrapper();
        
        //Passing null basically indicates that the existing setting (in memory) should be used
        npe5__Affiliations_Settings__c currentAffiliationsSettings = UTIL_CustomSettingsFacade.getAffiliationsSettings();
        
        // we have multiple scenarios to support
        // 1. create a new current affiliation, potentially mark primary
        // 2. set an existing affiliation to former
        // 3. set an existing affiliation to non-primary
        // 4. set an existing affiliation to primary
        
        map<string, AfflOps> mapStrComboKeyAfflOps = new map<string, AfflOps>();
        
        Integer i = 0;
        if (currentAffiliationsSettings.npe5__Automatic_Affiliation_Creation_Turned_On__c) {
            
            for (SObject so : newlist) {
                Contact contact = (Contact)so;

                // AFTER INSERT
                if (triggerAction == TDTM_Runnable.Action.AfterInsert) {

                    if (contact.AccountId != null) {
                        string strComboKey = strComboKeyFromContactIdAccountId(contact.Id, contact.AccountId);
                        AfflOps ao = mapStrComboKeyAfflOps.get(strComboKey);
                        if (ao == null) ao = new AfflOps(contact.Id, contact.AccountId);
                        ao.fCurrent = true;
                        ao.strRole = contact.Title;
                        mapStrComboKeyAfflOps.put(strComboKey, ao);
                    }
                    if (contact.Primary_Affiliation__c != null) {
                        string strComboKey = strComboKeyFromContactIdAccountId(contact.Id, contact.Primary_Affiliation__c);                     
                        AfflOps ao = mapStrComboKeyAfflOps.get(strComboKey);
                        if (ao == null) ao = new AfflOps(contact.Id, contact.Primary_Affiliation__c);
                        ao.fCurrent = true;
                        ao.fPrimary = true;
                        ao.strRole = contact.Title;
                        mapStrComboKeyAfflOps.put(strComboKey, ao);
                    }
                }

                // AFTER UPDATE
                if (triggerAction == TDTM_Runnable.Action.AfterUpdate) {                
                    Contact oldContact = (Contact) oldlist[i];
                    
                    if (contact.AccountId != oldContact.AccountId) {
                        if (contact.AccountId != null) {
                            string strComboKey = strComboKeyFromContactIdAccountId(contact.Id, contact.AccountId);
                            AfflOps ao = mapStrComboKeyAfflOps.get(strComboKey);
                            if (ao == null) ao = new AfflOps(contact.Id, contact.AccountId);
                            ao.fCurrent = true;
                            ao.strRole = contact.Title;
                            mapStrComboKeyAfflOps.put(strComboKey, ao);
                        }
                        if (oldContact.AccountId != null) {
                            string strComboKey = strComboKeyFromContactIdAccountId(oldContact.Id, oldContact.AccountId);
                            AfflOps ao = mapStrComboKeyAfflOps.get(strComboKey);
                            if (ao == null) ao = new AfflOps(oldContact.Id, oldContact.AccountId);
                            ao.fFormer = true;
                            mapStrComboKeyAfflOps.put(strComboKey, ao);
                        }
                    }
    
                    if (contact.Primary_Affiliation__c != oldContact.Primary_Affiliation__c) {
                        if (contact.Primary_Affiliation__c != null) {
                            string strComboKey = strComboKeyFromContactIdAccountId(contact.Id, contact.Primary_Affiliation__c);                     
                            AfflOps ao = mapStrComboKeyAfflOps.get(strComboKey);
                            if (ao == null) ao = new AfflOps(contact.Id, contact.Primary_Affiliation__c);
                            ao.fCurrent = true;
                            ao.fPrimary = true;
                            ao.strRole = contact.Title;
                            mapStrComboKeyAfflOps.put(strComboKey, ao);
                        }
                        if (oldContact.Primary_Affiliation__c != null) {
                            string strComboKey = strComboKeyFromContactIdAccountId(oldContact.Id, oldContact.Primary_Affiliation__c);                     
                            AfflOps ao = mapStrComboKeyAfflOps.get(strComboKey);
                            if (ao == null) ao = new AfflOps(oldContact.Id, oldContact.Primary_Affiliation__c);
                            ao.fFormer = true;
                            ao.fUnprimary = true;
                            mapStrComboKeyAfflOps.put(strComboKey, ao);
                        }
                    }
                }
    
                i++;
            }
        
            if (mapStrComboKeyAfflOps.size() > 0) {
                processAfflOps(mapStrComboKeyAfflOps, dmlWrapper);
            }

        }
        return dmlWrapper;
    }

    /*******************************************************************************************************
    * @description Affiliation trigger handler on Account. Creates affiliations and/or updates existing 
    * affiliation primary checkbox and status field.
    * @param listNew The list of Accounts from trigger new. 
    * @param listOld The list of Accounts from trigger old. 
    * @param triggerAction Which action triggered the current context. (Before or After; Insert, Update, 
    * Delete, or Undelete.)
    * @param objResult The field describe object for Account. 
    * @return dmlWrapper Objects that need to be inserted or updated after the processing of the trigger.
    ********************************************************************************************************/
    private DmlWrapper runAccountTriggerHandler(List<SObject> newlist, List<SObject> oldlist, 
        TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {
        DmlWrapper dmlWrapper = new DmlWrapper();

        //Passing null basically indicates that the existing setting (in memory) should be used
        npe5__Affiliations_Settings__c currentAffiliationsSettings = UTIL_CustomSettingsFacade.getAffiliationsSettings();
        
        // we have multiple scenarios to support
        // 1. insert: create new current affiliation (not primary)
        // 2. update: search for existing affiliations
        //      if one exists and isn't marked current, mark it as current
        //      otherwise create it
        
        map<string, AfflOps> mapStrComboKeyAfflOps = new map<string, AfflOps>();
        map<id, AfflOps> mapConIdToAfflOps = new map<id, AfflOps>();
        
        if (currentAffiliationsSettings.npe5__Automatic_Affiliation_Creation_Turned_On__c) {
            
            for (integer i=0; i<newlist.size(); i++) {
                Account account = (Account)newlist[i];
                //if this account is an organization, and we have a primary contact
                if (account.npe01__SYSTEMIsIndividual__c == false && account.npe01__One2OneContact__c != null) {
                    // AFTER INSERT
                    if (triggerAction == TDTM_Runnable.Action.AfterInsert) {
                        string strComboKey = strComboKeyFromContactIdAccountId(account.npe01__One2OneContact__c, account.id);
                        AfflOps ao = mapStrComboKeyAfflOps.get(strComboKey);
                        if (ao == null) ao = new AfflOps(account.npe01__One2OneContact__c, account.id);
                        ao.fCurrent = true;
                        mapConIdToAfflOps.put(account.npe01__One2OneContact__c, ao);
                        mapStrComboKeyAfflOps.put(strComboKey, ao);
                    }

                    // AFTER UPDATE
                    if (triggerAction == TDTM_Runnable.Action.AfterUpdate) {
                        Account oldAccount = (Account)oldlist[i];
                        
                        if (account.npe01__One2OneContact__c != oldAccount.npe01__One2OneContact__c) {
                            string strComboKey = strComboKeyFromContactIdAccountId(account.npe01__One2OneContact__c, account.id);
                            AfflOps ao = mapStrComboKeyAfflOps.get(strComboKey);
                            if (ao == null) ao = new AfflOps(account.npe01__One2OneContact__c, account.id);
                            ao.fCurrent = true;
                            mapConIdToAfflOps.put(account.npe01__One2OneContact__c, ao);
                            mapStrComboKeyAfflOps.put(strComboKey, ao);
                        }
                    }
                }
    
            }
        
            //we need an extra query here to get the correct role from the contact title
            if (!mapConIdToAfflOps.isEmpty()) {
                for (Contact con : [SELECT Id, Title FROM Contact WHERE Id IN :mapConIdToAfflOps.keySet()])
                    mapConIdToAfflOps.get(con.id).strRole = con.Title;
            }

            if (!mapStrComboKeyAfflOps.isEmpty()) {
                processAfflOps(mapStrComboKeyAfflOps, dmlWrapper);
            }

        }
        return dmlWrapper;
    }


    /*******************************************************************************************************
    * @description Wrapper class to track affiliations and which operations need to be performed on them.
    */ 
    private class AfflOps {

        // constructor
        AfflOps(Id contactId, Id accountId) {
            strComboKey = strComboKeyFromContactIdAccountId(contactId, accountId);
            fCurrent = false;
            fFormer = false;
            fPrimary = false;
            fUnprimary = false;
        }

        string strComboKey;
        boolean fCurrent;
        boolean fFormer;
        boolean fPrimary;
        boolean fUnprimary;
        string strRole;
        
        ID ContactId {
            get {
                if (strComboKey == null) return null;
                return strComboKey.left(strComboKey.indexOf('|'));
            }
        }
        
        ID AccountId {
            get {
                if (strComboKey == null) return null;
                return strComboKey.substring(strComboKey.indexOf('|') + 1);
            }
        }
        
    }
    
    /*******************************************************************************************************
    * @description Combines the ids of a contact and an account as a unique identifier for affiliations.
    * @param contactId The id of a contact.
    * @param accountId The id of an account.
    * @return string A string with the contact id, a pipe character |, and the account id.
    ********************************************************************************************************/
    private static string strComboKeyFromContactIdAccountId(Id contactId, Id accountId) {
        return contactId + '|' + accountId;
    }

    /*******************************************************************************************************
    * @description Using a map of unique affiliation identifiers and our AfflOps class, this method queries
    * existing affiliations, then processes AfflOps to create new or update existing affiliations.
    * @param mapStrComboKeyAfflOps A map of combination contact id and account id keys from the 
    * strComboKeyFromContactIdAccountId method to AfflOps objects.
    * @param dmlWrapper The dmlWrapper class for this transaction.
    * @return void This class updates the dmlWrapper parameter and returns nothing.
    ********************************************************************************************************/  
    private static void processAfflOps(map<string, AfflOps> mapStrComboKeyAfflOps, DmlWrapper dmlWrapper) {

        // the collections we want to insert/update in the db
        list<npe5__Affiliation__c> listAfflInsert = new list<npe5__Affiliation__c>();
        list<npe5__Affiliation__c> listAfflIdAfflUpdate = new list<npe5__Affiliation__c>();

        // get all the accounts & contacts
        set<Id> setContactId = new set<Id>();
        set<Id> setAccountId = new set<Id>();
        for (AfflOps ao : mapStrComboKeyAfflOps.values()) {
            setContactId.add(ao.ContactId);
            setAccountId.add(ao.AccountId);
        }
        
        Map<Id,Account> mapAccountIdAccount = new Map<Id,Account>([Select Id, npe01__SYSTEMIsIndividual__c from Account where Id in :setAccountId]);
        
        // get existing affiliations
        map<string, npe5__Affiliation__c> mapStrComboKeyAffl = new map<string, npe5__Affiliation__c>();

        for (npe5__Affiliation__c a : [Select Id, npe5__Contact__c, npe5__Organization__c from npe5__Affiliation__c 
            where npe5__EndDate__c = null and npe5__Contact__c in :setContactId and npe5__Organization__c in :setAccountId]) {
            mapStrComboKeyAffl.put(strComboKeyFromContactIdAccountId(a.npe5__Contact__c, a.npe5__Organization__c), a);
        }

        for (AfflOps ao : mapStrComboKeyAfflOps.values()) {
            Id accountId = ao.AccountId;
            Id contactId = ao.ContactId;
            
            if (ao.fCurrent) {
                //check to see if this account isn't an individual.
                Account acc =  mapAccountIdAccount.get(accountId);
                if (acc != null && acc.npe01__SYSTEMIsIndividual__c != true) {
                    npe5__Affiliation__c existingAffl = mapStrComboKeyAffl.get(ao.strComboKey);
                    if (existingAffl != null) {
                        existingAffl.npe5__EndDate__c = null;
                        existingAffl.npe5__Status__c = system.label.npe5.DefaultCurrentStatus;
                        if (ao.fPrimary) existingAffl.npe5__Primary__c = ao.fPrimary;
                        listAfflIdAfflUpdate.add(existingAffl);
                    } else {
                        npe5__Affiliation__c a = new npe5__Affiliation__c();
                        a.npe5__Contact__c = contactId;
                        a.npe5__Organization__c = accountId;
                        a.npe5__Status__c = system.label.npe5.DefaultCurrentStatus;
                        a.npe5__StartDate__c = System.Today();
                        a.npe5__Role__c = ao.strRole;
                        a.npe5__Primary__c = ao.fPrimary;
                        listAfflInsert.add(a);
                    }
                }           
            }
            
            if (!ao.fCurrent && ao.fPrimary) {
               // see if we have an existing current affiliation to use, otherwise create a new one
               npe5__Affiliation__c a = mapStrComboKeyAffl.get(ao.strComboKey);
               if (a != null) {
                   a.npe5__EndDate__c = null;
                   a.npe5__Status__c = system.label.npe5.DefaultCurrentStatus;
                   a.npe5__Primary__c = true;
                   listAfflIdAfflUpdate.add(a);
               } else {
                    a = new npe5__Affiliation__c();
                    a.npe5__Contact__c = contactId;
                    a.npe5__Organization__c = accountId;
                    a.npe5__Status__c = system.label.npe5.DefaultCurrentStatus;
                    a.npe5__StartDate__c = System.Today();
                    a.npe5__Role__c = ao.strRole;
                    a.npe5__Primary__c = true;
                    listAfflInsert.add(a);              
               }
            }
            
            if (ao.fFormer || ao.fUnprimary) {
               npe5__Affiliation__c a = mapStrComboKeyAffl.get(ao.strComboKey);
               
               // deal with the affiliation being deleted (if the org or contact was deleted)
               if (a == null) continue;
               
               if (ao.fFormer) {
                    //flag it as former ending today
                    a.npe5__Status__c = system.label.npe5.DefaultFormerStatus;
                    a.npe5__EndDate__c = System.Today();                
               }
               if (ao.fUnprimary) {
                    a.npe5__Primary__c = false;
               }  
               listAfflIdAfflUpdate.add(a);
            }
        }

        if (listAfflInsert.size() > 0) {
            //UTIL_Debug.debug('**** listAfflInsert: ' + JSON.serializePretty(listAfflInsert));
            dmlWrapper.objectsToInsert.addAll((list<SObject>) listAfflInsert);
        }
            
       if (listAfflIdAfflUpdate.size() > 0) {
            //UTIL_Debug.debug('**** listAfflIdAfflUpdate: ' + JSON.serializePretty(listAfflIdAfflUpdate));
            dmlWrapper.objectsToUpdate.addAll((list<SObject>) listAfflIdAfflUpdate);
       }
    }
    
    /*******************************************************************************************************
    * @description Affiliation trigger handler on Affiliations. Updates a contact's primary affiliation
    * field for a new primary affiliation and unchecks existing primary affiliations, removes contact's 
    * primary affiliation field for an affiliation that is no longer primary.
    * @param listNew The list of affiliations from trigger new. 
    * @param listOld The list of affiliations from trigger old. 
    * @param triggerAction Which action triggered the current context. (Before or After; Insert, Update, 
    * Delete, or Undelete.)
    * @param objResult The field describe object for Contact.
    * @return dmlWrapper Objects that need to be inserted or updated after the processing of the trigger.
    ********************************************************************************************************/
    private DmlWrapper runAfflTriggerHandler(List<SObject> newlist, List<SObject> oldlist, 
        TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {

        DmlWrapper dmlWrapper = new DmlWrapper();
        
        map<Id, Id> mapConIdAccIdPrimary = new map<Id, Id>();
        map<Id, Id> mapConIdAccIdUnprimary = new map<Id, Id>();
        
        //holds which primary affiliation is being used in case of multiple primary affiliations for a single contact
        map<Id, Id> mapConIdAfflIdPrimary = new map<Id, Id>();
        
        for (integer i = 0; i<newlist.size(); i++) {
            npe5__Affiliation__c affl = (npe5__Affiliation__c)newlist[i];

            // AFTER INSERT
            if (triggerAction == TDTM_Runnable.Action.AfterInsert) {
                if (affl.npe5__Primary__c && affl.npe5__Contact__c != null && affl.npe5__Organization__c != null) {
                    mapConIdAccIdPrimary.put(affl.npe5__Contact__c, affl.npe5__Organization__c);
                    mapConIdAfflIdPrimary.put(affl.npe5__Contact__c, affl.Id);
                }        
            }
            
            // AFTER UPDATE
            if (triggerAction == TDTM_Runnable.Action.AfterUpdate) {
                npe5__Affiliation__c afflOld = (npe5__Affiliation__c)oldlist[i];
                if (affl.npe5__Primary__c != afflOld.npe5__Primary__c) {
                    if (affl.npe5__Primary__c) {
                        mapConIdAccIdPrimary.put(affl.npe5__Contact__c, affl.npe5__Organization__c);
                        mapConIdAfflIdPrimary.put(affl.npe5__Contact__c, affl.Id);
                    } else {
                        mapConIdAccIdUnprimary.put(afflOld.npe5__Contact__c, afflOld.npe5__Organization__c);                    
                    }
                }
            }
        }

        // update contacts' primary affiliation field as appropriate
        set<id> setAllContactIds = new set<id>();
        setAllContactIds.addAll(mapConIdAccIdPrimary.keySet());
        setAllContactIds.addAll(mapConIdAccIdUnprimary.keySet());

        if (!setAllContactIds.isEmpty()) {
            list<Contact> conForUpdate = new list<Contact>();
            list<Contact> queryCon = [SELECT Id, Primary_Affiliation__c FROM Contact WHERE Id IN :setAllContactIds];
            
            for (Contact con : queryCon) {
                if (mapConIdAccIdPrimary.containsKey(con.id)) {
                    con.Primary_Affiliation__c = mapConIdAccIdPrimary.get(con.id);
                    conForUpdate.add(con);

                //only remove primary affiliation if primary was unchecked for this account and we haven't already replaced the primary affiliation
                } else if (mapConIdAccIdUnprimary.containsKey(con.id) && con.Primary_Affiliation__c == mapConIdAccIdUnprimary.get(con.id)) {
                    con.Primary_Affiliation__c = null;
                    conForUpdate.add(con);
                }
            }

            if (!conForUpdate.isEmpty()) {
                doNotRunForContacts = true;
                dmlWrapper.objectsToUpdate.addAll((List<SObject>)conForUpdate);
            }
        }

        if (!mapConIdAccIdPrimary.isEmpty()) {
            //uncheck any existing primary affiliations
            list<npe5__Affiliation__c> afflForUpdate = new list<npe5__Affiliation__c>();
            list<npe5__Affiliation__c> queryAffl = [SELECT Id, npe5__Contact__c, npe5__Organization__c FROM npe5__Affiliation__c WHERE npe5__Primary__c = TRUE AND npe5__Contact__c IN :mapConIdAccIdPrimary.keySet() AND ID NOT IN :mapConIdAfflIdPrimary.values()];
        
            for (npe5__Affiliation__c affl : queryAffl) {
                affl.npe5__Primary__c = false;
                afflForUpdate.add(affl);
            }

            if (!afflForUpdate.isEmpty()) {
                dmlWrapper.objectsToUpdate.addAll((List<SObject>)afflForUpdate);
            }
        }

        return dmlWrapper;
    }
    
    /*******************************************************************************************************
    * @description Remove all duplicate affiliations and make sure only one is primary, for the list of
    * Contacts.  Called by handleContactMergeFixupsFuture() after a Contact Merge has occurred.
    * @param listCon The list of Contacts 
    * @return null
    ********************************************************************************************************/
    public static void cleanupContactAffiliations(list<Contact> listCon) {
    
        // use dmlWrapper to handle all of our database operations
        TDTM_Runnable.DmlWrapper dmlWrapper = new TDTM_Runnable.DmlWrapper();    
        
        // for each Contact, get its list of Affiliations in oldest to newest order (of LastModifiedDate)
        map<Id, list<npe5__Affiliation__c>> mapConIdListAffl = new map<Id, list<npe5__Affiliation__c>>();
        
        // first we get all affiliations involved
        list<npe5__Affiliation__c> listAffl = [select Id, npe5__Contact__c, npe5__EndDate__c, npe5__Organization__c, 
            npe5__Primary__c, npe5__Role__c, npe5__StartDate__c, npe5__Status__c, npe5__Description__c
            from npe5__Affiliation__c 
            where npe5__Contact__c in :listCon
            order by npe5__Contact__c, npe5__Primary__c desc, LastModifiedDate asc];
        //UTIL_Debug.debug('****DJH listAffl: ' + listAffl);

        // now put each contact's affilations on their own list            
        for (npe5__Affiliation__c affl : listAffl) {
            list<npe5__Affiliation__c> listConAffl = mapConIdListAffl.get(affl.npe5__Contact__c);
            if (listConAffl == null) {
                listConAffl = new list<npe5__Affiliation__c>();
                mapConIdListAffl.put(affl.npe5__Contact__c, listConAffl);
            }
            listConAffl.add(affl);
        }
        
        // now we go through each contact's list of affiliations to find duplicates
        // and pick the one to be marked Primary  
        for (Contact con : listCon) {
        
	        // skip this contact if they have no affiliations to deal with 
	        if (!mapConIdListAffl.containsKey(con.Id))
	           continue;

            // to track the best Affilition to be Primary
            npe5__Affiliation__c afflPrimary;
            npe5__Affiliation__c afflPrimaryBackup;

            // create a map of afflKeys to affl's to find our duplicates
            map<string, list<npe5__Affiliation__c>> mapAfflKeyAffl = new map<string, list<npe5__Affiliation__c>>(); 
            for (npe5__Affiliation__c affl : mapConIdListAffl.get(con.Id)) {
                string afflKey = getAfflKey(affl);
                listAffl = mapAfflKeyAffl.get(afflKey);
                if (listAffl == null) {
                    listAffl = new list<npe5__Affiliation__c>();
                    mapAfflKeyAffl.put(afflKey, listAffl);
                }
                listAffl.add(affl);
                
                // primary checking
                if (affl.npe5__Organization__c == con.Primary_Affiliation__c && afflPrimary == null) {
                    if (affl.npe5__Primary__c)
                        afflPrimary = affl;
                    else
                        afflPrimaryBackup = affl;
                }
            }
            
            // if no Affiliation was correctly marked primary, take our backup
            if (afflPrimary == null)
                afflPrimary = afflPrimaryBackup;
            
            //UTIL_Debug.debug('****DJH mapAfflKeyAffl: ' + mapAfflKeyAffl);
            
            // now we have the lists of duplicate affiliations, remove the duplicates.            
            for (string afflKey : mapAfflKeyAffl.keySet()) {
                listAffl = mapAfflKeyAffl.get(afflKey);
                
                // first find the Affl to keep (Primary, and then oldest)
                npe5__Affiliation__c afflKeep;                     
                for (integer i = 0; i < listAffl.size(); i++) {
                    npe5__Affiliation__c affl = listAffl[i];
                    if (affl == afflPrimary) {                                                
                        affl.npe5__Primary__c = true;
                        afflKeep = affl;
                    } else {
                        affl.npe5__Primary__c = false;
	                    if (i == 0 & afflKeep == null) {
	                        afflKeep = affl;
	                    }
                    }
                }
                    
                // update the primary/oldest and delete the others
                for (integer i = 0; i < listAffl.size(); i++) {
                    npe5__Affiliation__c affl = listAffl[i];
                    if (affl == afflKeep) {
                        //UTIL_Debug.debug('****DJH update affl: ' + affl);
                        dmlWrapper.objectsToUpdate.add(affl);                        
                    } else {
                        //UTIL_Debug.debug('****DJH delete affl: ' + affl);
                        dmlWrapper.objectsToDelete.add(affl);
                    }
                }
            }
        }
        // perform our updates & deletes for dealing with the duplicate affiliations
        TDTM_TriggerHandler.processDml(dmlWrapper); 
    }

    /*******************************************************************************************************
    * @description Returns a unique string key that represents the data of an Affiliation, which can be used
    * to detect duplicate Affiliations.  Note that Primary is intentionally not included, since we will use
    * the Affliation specified in the Contact's Primary Affiliation field.
    * @param affl The Affiliation 
    * @return String The afflKey
    ********************************************************************************************************/
    private static string getAfflKey(npe5__Affiliation__c affl) {
        if (affl == null)
            return null;
        // do not include Primary
        string afflKey = 
            affl.npe5__Contact__c + ':' + 
            affl.npe5__Organization__c + ':' +
            affl.npe5__Role__c + ':' +
            affl.npe5__Status__c + ':' +
            affl.npe5__Description__c + ':' +
            string.valueOf(affl.npe5__StartDate__c) + ':' + 
            string.valueOf(affl.npe5__EndDate__c);
        return afflKey;
    }

    /*******************************************************************************************************
    * @description Create a non-duplicate Affilation, making it primary if specified. The intended caller
    * of this functionality is Lead Convert.
    * @param ld The Lead who was converted
    * @param conId The Contact ID of the contact the Lead was converted to
    * @param accId The AccountId to Affiliate with
    * @param isPrimary Whether the Affiliation should be the Contact's primary Affiliation
    * @return null
    ********************************************************************************************************/
    public static void createNonDupeAffiliation(Lead ld, ID conId, ID accId, boolean isPrimary) {
    
        // first we get all affiliations involved
        list<npe5__Affiliation__c> listAffl = [select Id, npe5__Contact__c, npe5__EndDate__c, npe5__Organization__c, 
            npe5__Primary__c, npe5__Role__c, npe5__StartDate__c, npe5__Status__c, npe5__Description__c
            from npe5__Affiliation__c 
            where npe5__Contact__c = :conId and npe5__Organization__c = :accId
            order by npe5__Primary__c desc, LastModifiedDate asc];
        
        npe5__Affiliation__c affl;
        if (listAffl.size() > 0) {
            affl = listAffl[0];
            if (affl.npe5__Primary__c != true && isPrimary) {
                affl.npe5__Primary__c = true;
                update affl;
            }
        } else {
            affl = new npe5__Affiliation__c();
            affl.npe5__Contact__c = conId;
            affl.npe5__Organization__c = accId;
            affl.npe5__Primary__c = isPrimary;
            affl.npe5__Role__c = ld.Title;
            insert affl;
        }
    }

}