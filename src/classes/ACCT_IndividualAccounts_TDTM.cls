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
* @date 2011 (1.x)
* @group Accounts
* @description Implements one of three account models, 1x1 or bucket or HH - controls syncing within the model 
*/
public class ACCT_IndividualAccounts_TDTM extends TDTM_Runnable {

    /*******************************************************************************************************
    * @description the current Contact Settings object
    */ 
    private static npe01__Contacts_and_Orgs_Settings__c ContactsSettings;
    
    /*******************************************************************************************************
    * @description a set of languages that require different naming conventions
    */ 
    private static Set<String> EasternOrderLanguages = new Set<String>{
        'ja','ko','vi','hu','ro','zh_CN','zh_TW'
    };
              
    /*******************************************************************************************************
    * @description the current user's Locale/Language
    */ 
    private static String OrgLanguage { get {
        if (OrgLanguage == null)
            OrgLanguage = UserInfo.getLocale();// [Select LanguageLocaleKey From Organization].LanguageLocaleKey;
        return OrgLanguage;             
    } set; }       
            
    /*******************************************************************************************************
    * @description Trigger Handler on Contact that handles account management.  Depending on the Account
    * Model, creates a Household Account, One-to-One Account, or uses the Individual Bucket Account.
    * @param listNew the list of Contacts from trigger new. 
    * @param listOld the list of Contacts from trigger old. 
    * @param triggerAction which trigger event (BeforeInsert, AfterInsert, etc.). 
    * @param objResult the describe for Contacts 
    * @return dmlWrapper.  
    ********************************************************************************************************/
    public override DmlWrapper run(List<SObject> newlist, List<SObject> oldlist, 
        TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {
        
        list<Contact> contacts = newlist;
        list<Contact> oldContacts = oldlist;
        
        DmlWrapper dmlWrapper = new DmlWrapper();
               
        ContactsSettings = UTIL_CustomSettingsFacade.getContactsSettings();
            
            List<Contact> listContactNeedAccount = new List<Contact>();
            List<Contact> listContactNeedAccountUpdate = new List<Contact>();
            List<Contact> listContactAccountDelete = new List<Contact>();
            List<Contact> listContactNeedBucketAccount = new List<Contact>();
            Map<Id,Id> mapAccountIdContactId = new Map<Id,Id>();
            list<Id> listAccountIdHHToUpdate = new list<Id>();
            list<Id> listAccountIdHHToRename = new list<Id>();
            Map<Id,Account> mapAccountIdAccount = null;
            map<Id, Contact> mapContactIdContactOwnerChange = new map<Id, Contact>();
            map<Id, Id> mapContactIdAccountIdOldMoveOpps = new map<Id, Id>();
            map<Id, Id> mapContactIdAccountIdNewMoveOpps = new map<Id, Id>();
            Set<String> hhNamingFields = HH_HouseholdNaming.iNaming.setHouseholdNameFieldsOnContact();

            
            if (triggerAction == TDTM_Runnable.Action.BeforeInsert || triggerAction == TDTM_Runnable.Action.BeforeUpdate) {                    
                // need to query account fields we need to look at
                Set<Id> setAccountId = new Set<Id>();
                for (Contact c : contacts) 
                    setAccountId.add(c.AccountId);
                mapAccountIdAccount = new Map<Id,Account>([select Id, npe01__One2OneContact__c, npe01__SYSTEM_AccountType__c from Account where Id IN :setAccountId]);
            } else if (triggerAction == TDTM_Runnable.Action.AfterInsert || triggerAction == TDTM_Runnable.Action.AfterUpdate || triggerAction == TDTM_Runnable.Action.AfterUndelete) {
               // requery to get correct Account values (and all the other fields we will look at)
               string strSoql = 'select id,npe01__Private__c,AccountId,Account.npe01__SYSTEMIsIndividual__c,' +
                    'Account.npe01__SYSTEM_AccountType__c,Account.npe01__One2OneContact__c,npe01__Organization_Type__c,Account.Name,' +
                    'firstname, lastname, OwnerId, Salutation, npo02__Naming_Exclusions__c, npo02__Household_Naming_Order__c,' +
                    'MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, MailingLatitude, MailingLongitude, ' + 
                    'OtherStreet, OtherCity, OtherState, OtherPostalCode, OtherCountry, OtherLatitude, OtherLongitude, '; 
                if (ADDR_Addresses_TDTM.isStateCountryPicklistsEnabled)
                    strSoql += 'MailingCountryCode, MailingStateCode, OtherCountryCode, OtherStateCode, ';
                
                if (hhNamingFields.size() > 0) {
                    for (String hhNameField : hhNamingFields) {
                        if (!strSoql.containsIgnoreCase(hhNameField))
                            strSoql += hhNameField + ',';
                    }
                }

                strSoql += 'Phone, Fax from Contact where Id IN :contacts';
                contacts = database.query(strSoql);
            }        
                        
            // AFTER DELETE
            if (triggerAction == TDTM_Runnable.Action.AfterDelete) {
                for (Contact c : oldContacts)
                    listContactAccountDelete.add(c);                     
            } else {               
                Integer i = 0; // used to index into oldContacts when needed                    
                for (Contact c : contacts) {
                    
                    // BEFORE INSERT
                    if (triggerAction == TDTM_Runnable.Action.BeforeInsert) {
                        // always clear this deprecated field to avoid confusing users.
                        c.npe01__SystemAccountProcessor__c = null;
                        
                        /* HH Account should be created if:
                            1. The account isn't set by the user
                            2. The npe01__Private__c checkbox isn't set by the user
                            3. The Account Model is set to Household Account
                        */
                        if (CAO_Constants.isHHAccountModel() && c.AccountId == null && c.npe01__Private__c != true) {
                            listContactNeedAccount.add(c);
                        } 
                                             
                        /*one-to-one account should be created if:
                            1. The account isn't set by the user
                            2. The npe01__Private__c checkbox isn't set by the user
                            3. The Account Model is set to OneToOne
                        */ 
                        else if (CAO_Constants.isOneToOne() && c.npe01__Private__c != true && 
                            (c.AccountId == null || (mapAccountIdAccount.containsKey(c.AccountId) && mapAccountIdAccount.get(c.AccountId).npe01__SYSTEM_AccountType__c == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE)) ) {
                            
                            // Avoid creating a new contact with the 1:1 account of another contact  
                            if (c.AccountId != null)
                                c.AccountId = null;
                           
                            listContactNeedAccount.add(c);
                        }                    
                        /*contact should be connected to the bucket account if:
                            1. The account isn't set by the user
                            2. The npe01__Private__c checkbox isn't set by the user
                            3. The Account Model is set to the individual bucket model
                        */
                        else if (CAO_Constants.isBucketModel() && c.AccountId == null && c.npe01__Private__c != true) {        
                            listContactNeedBucketAccount.add(c);
                        }                                                
                    }
        
                    // AFTER INSERT and AFTER UNDELETE
                    if (triggerAction == TDTM_Runnable.Action.AfterInsert || triggerAction == TDTM_Runnable.Action.AfterUndelete) {
                        /*grab the Accounts that need to have the newly assigned Contact Id to them if:
                            1. If the contact is connected to an Account
                            2. The Contact is flagged as an individual
                            3. we are in the one-to-one or HH Account model
                        */
                        if (c.AccountId != null && c.npe01__Private__c != true && 
                            c.Account.npe01__SYSTEM_AccountType__c != CAO_Constants.BUCKET_ORGANIZATION_TYPE && 
                            c.Account.npe01__SYSTEMIsIndividual__c &&
                            c.Account.npe01__One2OneContact__c == null) { 
                            // contacts are connected to Accounts, make the connection in the other direction
                            mapAccountIdContactId.put(c.AccountId, c.Id);
                        }
                        // there is no after insert processing for the bucket account model.

                        // HHAccount renaming needs to be called to set various HH naming fields like greeting.
                        if (c.AccountId != null && c.Account.npe01__SYSTEM_AccountType__c == CAO_Constants.HH_ACCOUNT_TYPE)
                           listAccountIdHHToRename.add(c.AccountId);    
                    }
        
                    // BEFORE UPDATE
                    if (triggerAction == TDTM_Runnable.Action.BeforeUpdate) {
                        // always clear this deprecated field to avoid confusing users.
                        c.npe01__SystemAccountProcessor__c = null;

                        /* HH account should be created if:
                            1. The account has been blanked out by the user
                            2. The npe01__Private__c checkbox isn't set by the user
                            3. The Account Model is set to Household Account
                        */
                        if (CAO_Constants.isHHAccountModel() && c.AccountId == null && c.npe01__Private__c != true) {
                            listContactNeedAccount.add(c);
                        }          
                        /*one-to-one account should be created if:
                            1. The account has been blanked out by the user
                            2. The npe01__Private__c checkbox isn't set by the user
                            3. The Account Model is set to One-to-One
                        */
                        else if (CAO_Constants.isOneToOne() && c.npe01__Private__c != true && 
                            (c.AccountId == null || (mapAccountIdAccount.containsKey(c.AccountId) && mapAccountIdAccount.get(c.AccountId).npe01__SYSTEM_AccountType__c == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE)) ) {
                            
                            // Avoid updating a contact with the 1:1 account of another contact  
                            if (c.AccountId != null && mapAccountIdAccount.get(c.AccountId).npe01__One2OneContact__c != c.Id &&
                                mapAccountIdAccount.get(c.AccountId).npe01__One2OneContact__c != null &&
                                // only do this fix if this update is currently happening.
                                // this avoids an account merge from being undone by a later update to the contact
                                c.AccountId != oldContacts[i].AccountId) {  
                                c.AccountId = null;
                            }
                            
                            if (c.AccountId == null)
                                listContactNeedAccount.add(c);
                        }          
                        /*contact should be connected to the bucket account if:
                            1. The account has been blanked out by the user
                            2. The npe01__Private__c checkbox isn't set by the user
                            3. The Account Model is set to individual bucket
                        */ 
                        else if (CAO_Constants.isBucketModel() && c.AccountId == null && c.npe01__Private__c != true) {        
                            listContactNeedBucketAccount.add(c);
                        }
                        // for contacts marked private after they have been created, break the relationship from their account (any model)
                        if (c.AccountId != null && c.npe01__Private__c == true) {
                            c.AccountId = null;
                        } 
                    }
        
                    // AFTER UPDATE
                    if (triggerAction == TDTM_Runnable.Action.AfterUpdate) {
                        /*if the contact is in the one-to-one model, changes to fields on the Contact
                        will require data changes on the Account to keep some fields in sync
                        */
                        if (c.npe01__Organization_Type__c == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE && needsOne2OneAccountUpdate(c, oldContacts[i])) {
                            listContactNeedAccountUpdate.add(c);                                
                        }
                        
                        /************* NO MORE, since we now support Address__c objects on HH Accounts, 
                                        we don't want contact address updates to push to the account directly.
                        // handle address changes for HH Accounts
                        if (c.npe01__Organization_Type__c == CAO_Constants.HH_ACCOUNT_TYPE && needsHHAccountAddressUpdate(c, oldContacts[i])) {
                            listContactNeedAccountUpdate.add(c);                               
                        }
                        *************/
                        
                        // if the account (any type) has changed
                        if (c.AccountId != oldContacts[i].AccountId) {
                            
                            // need to update any HH Accounts, both rollups and naming
                            if (c.npe01__Organization_Type__c == CAO_Constants.HH_ACCOUNT_TYPE) {
                                listAccountIdHHToUpdate.add(c.AccountId);
                                mapContactIdAccountIdNewMoveOpps.put(c.Id, c.AccountId);
                            }
                            
                            // old account should be checked for deletion (if it wasn't private)
                            if (c.npe01__Private__c != true && oldContacts[i].npe01__Private__c != true)
                                listContactAccountDelete.add(oldContacts[i]);
                            
                            // old HH Accounts that don't get deleted still need to have name and rollups redone
                            if (oldContacts[i].npe01__Organization_Type__c == CAO_Constants.HH_ACCOUNT_TYPE) {    
                                listAccountIdHHToUpdate.add(oldContacts[i].AccountId);
                                mapContactIdAccountIdOldMoveOpps.put(c.Id, oldContacts[i].AccountId);
                            }
                                                                                 
                           // HH Account didn't change, but it still might need renaming
                        } else if (c.npe01__Organization_Type__c == CAO_Constants.HH_ACCOUNT_TYPE && 
                                needsHHAccountRename(hhNamingFields, c, oldContacts[i])) {
                            listAccountIdHHToRename.add(c.AccountId);    
                        }  
                        
                        // check for ownership changes
                        if (c.OwnerId != oldContacts[i].OwnerId) {
                            mapContactIdContactOwnerChange.put(c.Id, c);
                        }                     
                    }
                    i += 1;
                }               
            }    

            // now done going thru all contacts, do our DML logic based on our lists
            
            if (listContactNeedAccount.size() > 0) {
                //add the newly created or updated Contacts that need a new individual account
                insertContactAccount(listContactNeedAccount, dmlWrapper);
            }
            if (listContactNeedBucketAccount.size() > 0) {
                //add the newly created or updated Contact to the bucket account
                attachToBucketAccount(listContactNeedBucketAccount);
            }
            if (listContactNeedAccountUpdate.size() > 0) {
                //contacts in the one-to-one model that are changing for syncing with account
                updateContactAccount(listContactNeedAccountUpdate, dmlWrapper);
            }
            if (mapAccountIdContactId.size() > 0) {
                //update Accounts that have newly created Contacts connected to them
                updateAccountPrimaryContact(mapAccountIdContactId, dmlWrapper);
            }
            if (mapContactIdAccountIdNewMoveOpps.size() > 0) {
                moveOppsToContactAccount(mapContactIdAccountIdNewMoveOpps, mapContactIdAccountIdOldMoveOpps, null);
            }
            if (listAccountIdHHToUpdate.size() > 0) {
                updateHHAccount(listAccountIdHHToUpdate, dmlWrapper);
            }
            if (listAccountIdHHToRename.size() > 0) {
                renameHHAccounts(listAccountIdHHToRename);    
            }
            if (mapContactIdContactOwnerChange.size() > 0) {
                updateOwners(mapContactIdContactOwnerChange, dmlWrapper);
            }
            if (listContactAccountDelete.size() > 0) {
                // check all old accounts to delete is they are left hanging around
                deleteContactAccountsIfEmpty(listContactAccountDelete, dmlWrapper);
            }
            
    return dmlWrapper;
    }

    /*******************************************************************************************************
    * @description Updates Accounts with the primary Individual Contact Id. Used for 1:1 and HH Accounts.
    * @param mapAccountIdContactId a map of AccountId's to ContactId's which specifies which accounts need updating.
    * @param dmlWrapper to hold the Accounts that need updating.
    * @return void
    */ 
    private void updateAccountPrimaryContact(Map<Id, Id> mapAccountIdContactId, DmlWrapper dmlWrapper) {
        List<Account> listAccount = new List<Account>(); 
        for (Id i : mapAccountIdContactId.keySet()) {
            Account a = new Account(Id = i, npe01__One2OneContact__c = mapAccountIdContactId.get(i));
            listAccount.add(a);
        }
        if (listAccount.size() > 0) {
            dmlWrapper.objectsToUpdate.addAll((List<SObject>)listAccount);
            //Database.SaveResult[] lsr = Database.update(listAccount, false);
        }      
    }

    /*******************************************************************************************************
    * @description Updates an HH Account's naming, primary contact, and rollups
    * @param listAccountId the list of Account (Id's) that need updating.
    * @param dmlWrapper to hold the Accounts that need updating.
    * @return void
    */ 
    private void updateHHAccount(list<Id> listAccountId, DmlWrapper dmlWrapper) {
        resetPrimaryContactForAccount(listAccountId, dmlWrapper);        
        renameHHAccounts(listAccountId); 
        rollupAccounts(listAccountId);                  
    }
    
    /*******************************************************************************************************
    * @description sets the Primary Account for each Account in the list of Accounts
    * @param listAccountId the list of Account (Id's) that need updating.
    * @param dmlWrapper to hold the Accounts that need updating.
    * @return void
    */ 
    private void resetPrimaryContactForAccount(list<Id> listAccountId, DmlWrapper dmlWrapper) {
    
        // for each account, we need a set of their contacts.
        map<Id, set<Id>> mapAccountIdSetContacts = new map<Id, set<Id>>();
        
        list<Contact> listCon = [select Id, AccountId, Account.npe01__One2OneContact__c from Contact where AccountId in :listAccountId];
        for (Contact con : listCon) {
            set<Id> setContactId = mapAccountIdSetContacts.get(con.AccountId);
            if (setContactId == null) { 
                setContactId = new set<Id>();
                mapAccountIdSetContacts.put(con.AccountId, setContactId);
            }
            setContactId.add(con.Id);
        }
        
        // now go thru each account and update their primary contact if the current one isn't valid
        list<Account> listAcc = [select Id, npe01__One2OneContact__c from Account where Id in :listAccountId];
        list<Account> listAccToUpdate = new list<Account>();
        for (Account acc : listAcc) {
            set<Id> setContactId = mapAccountIdSetContacts.get(acc.Id);
            if (setContactId != null && !setContactId.contains(acc.npe01__One2OneContact__c)) {
                acc.npe01__One2OneContact__c = firstIdFromSet(setContactId);
                listAccToUpdate.add(acc);
            }    
        }
        
        // now do the update
        dmlWrapper.objectsToUpdate.addAll((List<SObject>)listAccToUpdate);
        //update listAccToUpdate;
    }
        
    /*******************************************************************************************************
    * @description returns the first Id from the specified set
    * @param setId a set of Id's
    * @return Id.  null if the set is empty.
    */ 
    private Id firstIdFromSet(set<Id> setId) {
        for (ID id : setId) {
            return Id;
        }
        return null;
    }

    /*******************************************************************************************************
    * @description for the list of AccountId's, recalc their rollups 
    * @param listAccountId the list of Account (Id's) that need updating.
    * @return void
    */ 
    public static void rollupAccounts(list<Id> listAccountId) {
        if (listAccountId.size() == 1 || system.isFuture() || system.isBatch()) {
            RLLP_OppRollup_UTIL oppRollupUtil = new RLLP_OppRollup_UTIL(); 
            map<id, Account> amap = new map<id, account>((list<Account>)Database.query(oppRollupUtil.buildAccountQuery() + 
                                                           ' where id IN :listAccountId')); 
            RLLP_OppRollup rg = new RLLP_OppRollup();
            rg.rollupAccounts(amap);                    
        } else {
            RLLP_OppRollup.rollupAccountsFuture(new set<Id>(listAccountId));
        } 
    }
    
    /*******************************************************************************************************
    * @description for the list of HH AccountId's, update their Household Names 
    * @param listAccountId the list of Account (Id's) that need updating.
    * @return void
    */ 
    public static void renameHHAccounts(list<Id> listAccountId) {
        if (listAccountId.size() == 1 || system.isFuture() || system.isBatch()) {
            HH_HouseholdNaming hhName = new HH_HouseholdNaming();
            hhName.UpdateNames(listAccountId);
        } else {
            HH_HouseholdNaming.FutureUpdateNames(listAccountId);
        }
    }

    /*******************************************************************************************************
    * @description Updates Contacts to relate to a single Individual Account 
    * @param contacts the list of Contacts that need updating.
    * @return void
    */ 
    private void attachToBucketAccount(Contact[] contacts)
    {
        //grab the first Account that is named Individual
        List<Account> individuals = [Select Id from Account where name = :CAO_Constants.BUCKET_ACCOUNT_NAME ORDER BY CreatedDate Desc Limit 1];
        
        Id individualAccountId = CAO_Constants.GetIndividualAccountId();
        
        if (individuals.size() > 0)
        {
            for(Contact c : contacts)
            {
                //connect all Contacts to the bucket Account
                c.AccountId = individualAccountId;
            }
        } else
        {
            //if there is no bucket Account, the bucket model won't function unless we create one
            Account individual = new Account();
            individual.Name = CAO_Constants.BUCKET_ACCOUNT_NAME;
            //flag the account as an individual account
            individual.npe01__SYSTEMISINDIVIDUAL__c = true;
            //individual.Type = CAO_Constants.BUCKET_ORGANIZATION_TYPE;
            individual.npe01__SYSTEM_AccountType__c = CAO_Constants.BUCKET_ORGANIZATION_TYPE;
            insert individual;

            for(Contact c : contacts)
            {
                //connect Contact to bucket Account
                c.AccountId = individual.Id;
            }
        }
    }

    /*******************************************************************************************************
    * @description utility to return the recordtypeId for a new account, given the account model. 
    * @return Id. if none is explicitly specified, returns null.
    */ 
    public static ID rtIdForNewAccount() {
        return rtIdForAccountModel(CAO_Constants.isHHAccountModel());
    }

    /*******************************************************************************************************
    * @description utility to return the recordtypeId for a new account, given the account model. 
    * @param isHHAccountModel whether we are using the Household Account Model
    * @return Id. recordtype Id to use, or null if none is specified for the account model.
    */ 
    public static ID rtIdForAccountModel(boolean isHHAccountModel) {
        ContactsSettings = UTIL_CustomSettingsFacade.getContactsSettings();
        Schema.Recordtypeinfo rtById = null;
        // check if recordtypes are enabled, and if it matches the existing setting
        // we go thru describe to validate that it is a valid recordType ID.
        ID rtId = isHHAccountModel ? ContactsSettings.npe01__HH_Account_RecordTypeID__c : ContactsSettings.npe01__One_to_One_RecordTypeID__c;
        if (rtId != null) {
            Schema.DescribeSObjectResult R = Account.SObjectType.getDescribe();
            Map<Id,Schema.RecordTypeInfo> rtMapById = r.getRecordTypeInfosById();
            rtById = rtMapById.get(rtId);
            return (rtById == null ? null : rtById.getRecordTypeID());            
        }
        return null;
    }

    /*******************************************************************************************************
    * @description Inserts a new 1:1 or HH Account for an Individual Contact
    * @param contacts the list of Contacts that need updating.
    * @param dmlWrapper to hold the Contacts that need updating.
    * @return void
    */ 
    private void insertContactAccount(Contact[] contacts, DmlWrapper dmlWrapper)
    {
        List<Id> contactIds = new List<Id>();
        Map<Id,Account> mapContactIdAccount = new Map<Id,Account>();  
        list<Contact> listContactNewAcct = new list<Contact>();     
        
        for (Contact c : contacts) {
            //track which contacts are being inserted vs. updated
            if (c.Id != null) {
                contactIds.add(c.Id);
            }
        }
        // get all the Accounts that are connected to the existing Contacts
        for (Account acc : [Select Id, npe01__One2OneContact__c, npe01__SYSTEM_AccountType__c from Account where npe01__One2OneContact__c in :contactIds]) {
            mapContactIdAccount.put(acc.npe01__One2OneContact__c, acc);
        }
        
        List<Account> accountInserts = new List<Account>();
        ID rtIdAcccount = rtIdForNewAccount();
        
        for (Contact c : contacts) {
            // if we found an Account already connected to this Contact, connect the Contact to that Account if it
            // is the correct type for the current account processor
            Account acc = mapContactIdAccount.get(c.Id);
            if (acc != null && 
                ((CAO_Constants.isHHAccountModel() && acc.npe01__SYSTEM_AccountType__c == CAO_Constants.HH_ACCOUNT_TYPE) ||
                (CAO_Constants.isOneToOne() && acc.npe01__SYSTEM_AccountType__c == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE)) ) {
                // if a user has blanked out the Account for a Contact, this will put it right back
                c.AccountId = acc.Id;                
            } else {
                // construct the individul account for the Contact
                Account a = new Account();
                
                // construct the Account name
                a.Name = CAO_Constants.isHHAccountModel() ? strNameHHAccountForContact(c) : strNameOne2OneAccountForContact(c);
                
                //connect the Account to the Contact for 1:1 and HHAccount
                if (c.Id != null) {
                    a.npe01__One2OneContact__c = c.Id;
                }
                a.Phone = c.Phone;
                a.Fax = c.Fax;
                // only set account's billing address if address management is off.
                // otherwise, we'll let ADDR_Contact_TDTM push the new address into the account.
                if (!CAO_Constants.isHHAccountModel() || UTIL_CustomSettingsFacade.getContactsSettings().Household_Account_Addresses_Disabled__c)
                    ADDR_Addresses_TDTM.copyAddressStdSObj(c, 'Mailing', a, 'Billing');
                ADDR_Addresses_TDTM.copyAddressStdSObj(c, 'Other', a, 'Shipping');

                a.npe01__SYSTEM_AccountType__c = CAO_Constants.isHHAccountModel() ? CAO_Constants.HH_ACCOUNT_TYPE : CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE;
                if (rtIdAcccount != null) {                
                    a.put('RecordTypeID', rtIdAcccount);
                }                
                a.Type = CAO_Constants.isHHAccountModel() ? CAO_Constants.HH_TYPE : '';
                a.npe01__SYSTEMISINDIVIDUAL__c = true;
                accountInserts.add(a);
                listContactNewAcct.add(c);
            }
        }
        
        if (accountInserts.size() > 0) {
            // with current TDTM design, we can't use dmlWrapper
            // because we need to get the new AccountID's into the Contacts.

            // identify the specific records that had the problem, and don't fail the whole batch
            List<Database.SaveResult> insertResults = Database.insert(accountInserts, false);
            for (integer i = 0; i < insertResults.size(); i++) {
                Database.SaveResult sr = insertResults[i];
                if (!sr.isSuccess()) {
                    for (Database.Error err : sr.getErrors()) {
                        accountInserts[i].addError(err.getMessage());
                        Contact con = listContactNewAcct[i];
                        con.addError(string.format(label.conFailedAccountCreate, new string[]{con.Firstname, con.Lastname, err.getMessage()}));
                    }
                }
            }
                                    
            // now update each contact's accountId
            Integer i = 0;
            for (Contact c : listContactNewAcct) {
                c.AccountId = accountInserts[i].Id;
                i++;
            }
        }
    }

    /*******************************************************************************************************
    * @description Updates an Individual Account for an Individual Contact
    * @param contacts the list of Contacts that need updating.
    * @param dmlWrapper to hold the Contacts that need updating.
    * @return void
    */ 
    private void updateContactAccount(Contact[] contacts, DmlWrapper dmlWrapper)
    {
        Map<Id,Contact> accConMap = new Map<Id,Contact>();
        for (Contact c : contacts) {
            accConMap.put(c.AccountId, c);
        }
        //get the corresponding Accounts
        // DJH: note that we explicitly do NOT get the account name.  This is to avoid the changes to the account from
        // this routine which get added to dmlWrapper, don't end up overwriting the name changes that are done syncronously
        // for HH accounts (which don't use dmlWrapper).
        List<Account> accts = [Select a.npe01__SYSTEM_AccountType__c, a.ShippingStreet, a.ShippingState, a.ShippingPostalCode, a.ShippingCountry, 
            a.ShippingCity, a.BillingStreet, a.BillingState, a.BillingPostalCode, a.BillingCountry, a.BillingCity
            From Account a where a.Id IN :accConMap.keyset()];
        List<Account> listAccount = new List<Account>();
        
        for (Account a : accts) {         
            Contact c = accConMap.get(a.Id);
            //if any address data has changed on the contact, push it all to the Account
            {
                // Update Account fields
                ADDR_Addresses_TDTM.copyAddressStdSObj(c, 'Mailing', a, 'Billing');
                ADDR_Addresses_TDTM.copyAddressStdSObj(c, 'Other', a, 'Shipping');

                if (a.npe01__SYSTEM_AccountType__c == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE)               
                    a.Name = strNameOne2OneAccountForContact(c);
                // HH Account naming updates handled seperately to deal with asyncronous naming

                listAccount.add(a);
            }           
        }   

        if (listAccount.size() > 0) {
            dmlWrapper.objectsToUpdate.addAll((list<SObject>)listAccount);
            //Database.SaveResult[] lsr = Database.update(listAccount, false);
        }
    }
    
    /*******************************************************************************************************
    * @description Deletes the accounts for contacts that have been deleted if they are empty (no ops, no hh members)
    * @param contacts the list of Contacts that need to be considered
    * @param dmlWrapper to hold the Accounts that need updating.
    * @return void
    */ 
    public void deleteContactAccountsIfEmpty(Contact[] contacts, DmlWrapper dmlWrapper) {
        List<Id> accountIds = new List<Id>();
        List<Account> accountsOnContacts = new List<Account>();
        List<SObject> accountsForDeletion = new List<SObject>();
        List<Id> listAccountIdUpdate = new list<Id>();
        
        if (contacts.size() > 0) {
            for (Contact thisContact : contacts) {
                accountIds.add(thisContact.accountId);
            }

            accountsOnContacts = [Select Id, Type, npe01__SYSTEM_AccountType__c, (Select Id from Opportunities limit 1), (select Id from Contacts limit 1) from Account where Id IN :accountIds];
            
            for (Account thisAccount : accountsOnContacts) {
                //if the legacy or current type field show it's not a one-to-one or HH Account record, don't delete the account
                if (thisAccount.Type == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE || 
                    thisAccount.npe01__SYSTEM_AccountType__c == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE ||
                    thisAccount.npe01__SYSTEM_AccountType__c == CAO_Constants.HH_ACCOUNT_TYPE) {
                    
                    // if there are no Opportunities for this Account, add it for deletion
                    if (thisAccount.Opportunities.size() == 0 && thisAccount.Contacts.size() == 0) {
                        accountsForDeletion.add(thisAccount);
                    } else if (thisAccount.npe01__SYSTEM_AccountType__c == CAO_Constants.HH_ACCOUNT_TYPE) {
                        listAccountIdUpdate.add(thisAccount.Id);
                    }
                }   
            }        
        }
        // delete the accounts
        if (accountsForDeletion.size() > 0) {
            /*This is a bad strategy as it leaves the deleted contact unrecoverable from the recycle bin.
            **Leaving an orphaned account is less of an issue than unrecoverable data, and that is solved
            **by the contact delete button override.
            */

            //dmlWrapper.objectsToDelete.addAll(accountsForDeletion);
        }
        // update any HH accounts that just lost a contact
        if (listAccountIdUpdate.size() > 0)
            updateHHAccount(listAccountIdUpdate, dmlWrapper);        
    }

    /*******************************************************************************************************
    * @description contact's owner has changed.  consider updating related account owner.
    * @param mapContactIdContactOwnerChange a map of Contact Id to Contact, for the contacts to consider
    * @param dmlWrapper to hold the Contacts that need updating.
    * @return void
    */ 
    private void updateOwners(map<Id, Contact> mapContactIdContactOwnerChange, DmlWrapper dmlWrapper) {        
        if (mapContactIdContactOwnerChange.size() > 0) {
            list<Account> listAccToUpdate = new list<Account>();
            set<Id> setAccId = new set<Id>();
            for (Contact con : mapContactIdContactOwnerChange.values()) {
                setAccId.add(con.AccountId);
            }
                       
            list<Account> listAcc = [Select Id, npe01__SYSTEM_AccountType__c, npe01__One2OneContact__c, OwnerId, (select Id from Contacts limit 2) from Account 
                where Id IN :setAccId];
            
            for (Account acc : listAcc) {
                // only consider 1:1 and HH Accounts
                if (acc.npe01__SYSTEM_AccountType__c == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE || acc.npe01__SYSTEM_AccountType__c == CAO_Constants.HH_ACCOUNT_TYPE) {                    
                    // if there is only 1 contact associated with this account
                    if (acc.Contacts.size() == 1 && acc.npe01__One2OneContact__c != null) {
                        Contact c = mapContactIdContactOwnerChange.get(acc.npe01__One2OneContact__c);
                        Id ownerIdContact = (c != null ? c.OwnerId : null);
                        if (acc.OwnerId != ownerIdContact) {
                            acc.OwnerId = ownerIdContact;
                            listAccToUpdate.add(acc);
                        }
                    }
                }   
            }
            // add all accounts needing updating to our list
            dmlWrapper.objectsToUpdate.addAll((List<SObject>)listAccToUpdate);                        
        }
    }
    
    /*******************************************************************************************************
    * @description Moves all opps for the Primary OCR provided contacts to their new account.  Note that it
    * is the caller's responsibility to decide whether moving opps is appropriate for the involved accounts.
    * @param mapContactIdAccountIdNew a map of Contact Id to Account Id, for the Accounts to move opps to
    * @param mapContactIdAccountIdOld a map of Contact Id to Account Id, for the Accounts to move opps from
    * @param dmlWrapper to hold the Opps that need updating.  If null, will commit immediately.
    * @return void
    */ 
    public static void moveOppsToContactAccount(map<Id,Id> mapContactIdAccountIdNew, map<Id,Id> mapContactIdAccountIdOld, DmlWrapper dmlWrapper) {
        set<Id> setConId = mapContactIdAccountIdNew.keySet();
        list<Id> listAccIdOld = mapContactIdAccountIdOld.values();
        list<Opportunity> listOpp = new list<Opportunity>();
        set<Id> setOppId = new set<Id>();
        
        //system.debug('****DJH: moveOpps setConIdNew: ' + setConId);
        //system.debug('****DJH: moveOpps listAccIdOld: ' + listAccIdOld);
    
        // query changed to also include Role = default role, because Contact Merge scenarios were clearing isPrimary (not by our code)!
        npe01__Contacts_and_Orgs_Settings__c cs = UTIL_CustomSettingsFacade.getContactsSettings();
        
        list<OpportunityContactRole> listOCR = [select ContactId, OpportunityId, Opportunity.isClosed, Opportunity.AccountId, IsPrimary, Role 
            from OpportunityContactRole where IsPrimary = true and Opportunity.AccountId in :listAccIdOld and ContactId in :setConId];
        //system.debug('****DJH: moveOpps listOCR: ' + listOCR);
            
        for (OpportunityContactRole ocr : listOCR) {
            if (mapContactIdAccountIdOld.get(ocr.ContactId) == ocr.Opportunity.AccountId) {
                if (setOppId.add(ocr.OpportunityId))
                    listOpp.add(new Opportunity(Id = ocr.OpportunityId, AccountId = mapContactIdAccountIdNew.get(ocr.ContactId)));
            }
        }
        //system.debug('****DJH: moveOpps listOpp: ' + listOpp);
        
        if (listOpp.size() > 0) {
            // since our rollup code isn't using dmlWrapper, some callers want us to commit now
            if (dmlWrapper == null)
                update listOpp;
            else
                dmlWrapper.objectsToUpdate.addAll((list<SObject>)listOpp);
        }
    }

    /*******************************************************************************************************
    * @description returns whether changes in the contact require an update to the 1:1 account 
    * @param conNew the new version of the Contact
    * @param conOld the old version of the Contact
    * @return boolean
    */ 
    private boolean needsOne2OneAccountUpdate(Contact conNew, Contact conOld) {      
        if(conNew == null && conOld != null)
            return true;
        if(conNew != null && conOld == null)
            return true;
        if(conNew.FirstName == null) conNew.FirstName = '';
        if(conNew.LastName == null) conNew.LastName = '';
        
        return (conNew.AccountId != conOld.AccountId ||
                !conNew.FirstName.equals(conOld.FirstName) ||
                !conNew.LastName.equals(conOld.LastName) ||
                conNew.MailingStreet != conOld.MailingStreet ||
                conNew.MailingCity != conOld.MailingCity ||
                conNew.MailingState != conOld.MailingState ||
                conNew.MailingPostalCode != conOld.MailingPostalCode ||
                conNew.MailingCountry != conOld.MailingCountry ||
                conNew.OtherStreet != conOld.OtherStreet ||
                conNew.OtherCity != conOld.OtherCity ||
                conNew.OtherState != conOld.OtherState ||
                conNew.OtherPostalCode != conOld.OtherPostalCode ||
                conNew.OtherCountry != conOld.OtherCountry ||
                conNew.Phone != conOld.Phone ||
                conNew.Fax != conOld.Fax);
    }
 
    /*******************************************************************************************************
    * @description returns whether changes in the contact require a rename of a HH Account
    * @param hhNamingFields the set of fields used in the namespec
    * @param conNew the new version of the Contact
    * @param conOld the old version of the Contact
    * @return boolean
    */
    private boolean needsHHAccountRename(Set<String> hhNamingFields, Contact conNew, Contact conOld) {
        if (conNew == null && conOld != null)
            return true;
        if (conNew != null && conOld == null)
            return true;
        if (conNew.FirstName == null) conNew.FirstName = '';
        if (conNew.LastName == null) conNew.LastName = '';
        
        // check all household naming fields for changes
        for(String fieldName : hhNamingFields) {
            string strName;
            string strNameOld;
            
            //separate cross object references, i.e. account.name
            list<string> splitField = (fieldName.split('\\.',0));
            if (splitField.size() > 1) {
                SObject sobj = conNew.getSobject(splitField[0]);
                if (sobj != null) {
                    strName = sobj.Id;
                }
                sobj = conOld.getSobject(splitField[0]);
                if (sobj != null) {
                    strNameOld = sobj.Id;
                }
            } else {
                strName = String.valueOf(conNew.get(fieldName));
                strNameOld = String.valueOf(conOld.get(fieldName));
            }

            // make sure to use case sensitive comparison
            if (strName == null)
                strName = '';
            if (strNameOld == null)
                strNameOld = '';
            if (!strName.equals(strNameOld)) {
                return true;
            }
        }
        if (conNew.npo02__Household_Naming_Order__c != conOld.npo02__Household_Naming_Order__c ||
            conNew.npo02__Naming_Exclusions__c != conOld.npo02__Naming_Exclusions__c) {
            return true;
        }
        return false;
    }
    
    /*******************************************************************************************************
    * @description returns whether changes in the contact require an address change in the HH Account 
    * @param conNew the new version of the Contact
    * @param conOld the old version of the Contact
    * @return boolean
    */ 
    private boolean needsHHAccountAddressUpdate(Contact conNew, Contact conOld) {              
       return (conNew.MailingStreet != conOld.MailingStreet ||
                conNew.MailingCity != conOld.MailingCity ||
                conNew.MailingState != conOld.MailingState ||
                conNew.MailingPostalCode != conOld.MailingPostalCode ||
                conNew.MailingCountry != conOld.MailingCountry ||
                conNew.OtherStreet != conOld.OtherStreet ||
                conNew.OtherCity != conOld.OtherCity ||
                conNew.OtherState != conOld.OtherState ||
                conNew.OtherPostalCode != conOld.OtherPostalCode ||
                conNew.OtherCountry != conOld.OtherCountry);
    }

    /*******************************************************************************************************
    * @description returns the default name for a new HH Account. Custom household naming will update this 
    * account later if enabled, but we still need one if not enabled.
    * @param con the Contact
    * @return string. the Household account name
    */ 
    public static String strNameHHAccountForContact(Contact con) {
        return con.LastName + ' ' + system.label.npo02.DefaultHouseholdName;
    }

    /*******************************************************************************************************
    * @description returns the default name for a new 1:1 Account 
    * @param con the Contact
    * @return string. the 1:1 account name
    */ 
    public static String strNameOne2OneAccountForContact(Contact con) {
        String str = '';
        
        if (con.FirstName != null) {
            str += con.FirstName;
            str += ' ' ;
        }                
        if (EasternOrderLanguages.contains(OrgLanguage))
            str = con.LastName + ' ' + str;
        else
            str += con.LastName;
        
        return str;
    }

}