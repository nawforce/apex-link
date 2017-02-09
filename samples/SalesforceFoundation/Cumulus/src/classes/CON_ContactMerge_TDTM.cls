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
*
* @group ContactMerge
*
* @description Trigger Handler on Contact that handles detecting when a Contact Merge 
* has completed, in order to fixup an assortment of affected information.
*/
public class CON_ContactMerge_TDTM extends TDTM_Runnable {
        
    
    /*******************************************************************************************************
    * @description Trigger Handler on Contact that handles detecting when an Contact Merge 
    * has completed, in order to fixup an assortment of affected information.
    * @param listNew the list of Contacts from trigger new. 
    * @param listOld the list of Contacts from trigger old. 
    * @param triggerAction which trigger event (BeforeInsert, AfterInsert, etc.). 
    * @param objResult the describe for Contacts 
    * @return dmlWrapper.  always null.
    ********************************************************************************************************/
    public override DmlWrapper run(List<SObject> listNew, List<SObject> listOld, 
        TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {
        
        TDTM_Runnable.DmlWrapper dmlWrapper = new TDTM_Runnable.DmlWrapper();    

        if (triggerAction == TDTM_Runnable.Action.AfterDelete) {
            list<string> listMergeKey = new list<string>();
        
            for (SObject so : listOld) {
                Contact con = (Contact)so;
                if (con != null && con.MasterRecordId != null)
                    listMergeKey.add(mergeKey(con.Id, con.AccountId, con.MasterRecordId));
            }
            
            if (listMergeKey.size() > 0)
                handleContactMergeFixupsFuture(listMergeKey);
        }        
        
        return dmlWrapper; 
    }
    
    /*******************************************************************************************************
    * @description returns a unique key for a tracking a losing Contact, their account, and the winning Contact.
    * @param contactIdLoser The losing Contact's Id
    * @param accountIdLoser The losing Contact's Account Id
    * @param contactIdWinner The winning Contact's Id
    * @return string
    ********************************************************************************************************/
    private static string mergeKey(string contactIdLoser, string accountIdLoser, string contactIdWinner) {
        return (contactIdLoser + ':' + accountIdLoser + ':' + contactIdWinner);
    }
    
    /*******************************************************************************************************
    * @description returns the object Id at the specified index of a mergeKey
    * @param mergeKey The mergeKey to extract an Id from
    * @param index The index of which Id to return.  values should be 0 - contactIdLoser, 1 - accountIdLower, 
    * 2 - contactIdWinner
    * @return Id
    ********************************************************************************************************/
    private static string idFromMergeKey(string mergeKey, integer index) {
        if (mergeKey == null || index < 0 || index > 2)
            return null;
        list<string> listId = mergeKey.split(':');
        string strId = listId[index];
        if (strId == 'null') strId = null;
        return strId;
    }

    /*******************************************************************************************************
    * @description After a Contact Merge, call this Future routine to perform assorted fixups that need to
    * occur which include:
    *   - update the Account name and greetings 
    *   - move Opps to the Winner's Account 
    *   - recalculate Household Soft Credit OCRs 
    *   - recalculate Opportunity Rollups
    *
    * @param listMergeKey The list of mergeKeys that provide contactIdLoser, accountIdLoser, and contactIdWinner
    * @return null
    ********************************************************************************************************/
    @Future    
    private static void handleContactMergeFixupsFuture(list<string> listMergeKey) {
    
        list<string> listContactIdLoser = new list<string>();
        list<string> listAccountIdLoser = new list<string>();
        list<string> listContactIdWinner = new list<string>();

        // extract out our Contact and Account Id's from the mergeKeys
        for (string mergeKey : listMergeKey) {
            listContactIdLoser.add(idFromMergeKey(mergeKey, 0));
            listAccountIdLoser.add(idFromMergeKey(mergeKey, 1));
            listContactIdWinner.add(idFromMergeKey(mergeKey, 2));
        }
    
        // get the winning Contacts (with the fields we need)
        RLLP_OppRollup_UTIL oppRollupUtil = new RLLP_OppRollup_UTIL(); 
        string strSoql = oppRollupUtil.buildContactQuery();
        strSoql = strSoql.replace('from Contact', '');
        strSoql += ', AccountId, Account.npe01__SYSTEM_AccountType__c, Primary_Affiliation__c from Contact where id IN :listContactIdWinner';
        list<Contact> listContactWinner = Database.query(strSoql);

        // create maps of contactId's to AccountId's that some of our routines need.
        map<Id,Id> mapContactIdAccountIdWinner = new map<Id,Id>();
        map<Id,Id> mapContactIdAccountIdLoser = new map<Id,Id>();
        list<Id> listAccIdHH= new list<Id>();
        for (Contact con : listContactWinner) {
        	if (con.Account.npe01__SYSTEM_AccountType__c == CAO_Constants.HH_ACCOUNT_TYPE)
        	   listAccIdHH.add(con.AccountId);
            if (con.Account.npe01__SYSTEM_AccountType__c == CAO_Constants.HH_ACCOUNT_TYPE ||
                con.Account.npe01__SYSTEM_AccountType__c == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE ||
                con.Account.npe01__SYSTEM_AccountType__c == CAO_Constants.BUCKET_ORGANIZATION_TYPE) {
                mapContactIdAccountIdWinner.put(con.Id, con.AccountId);        
            }
        }
        
        // we only want to move opps from non-org accounts, so we need to query for their type.
        map<Id, Account> mapAccountIdAccountLoser = new map<Id, Account>([select Id, npe01__SYSTEM_AccountType__c 
            from Account where Id in :listAccountIdLoser]);
        for (integer i = 0; i < listContactIdLoser.size(); i++) {
        	if (listAccountIdLoser[i] != null) {
                Account accLoser = mapAccountIdAccountLoser.get(listAccountIdLoser[i]);
                if (accLoser == null)
                    continue;
                if (accLoser.npe01__SYSTEM_AccountType__c == CAO_Constants.HH_ACCOUNT_TYPE)
                    listAccIdHH.add(accLoser.Id); 
                if (accLoser.npe01__SYSTEM_AccountType__c == CAO_Constants.HH_ACCOUNT_TYPE || 
                    accLoser.npe01__SYSTEM_AccountType__c == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE ||
                    accLoser.npe01__SYSTEM_AccountType__c == CAO_Constants.BUCKET_ORGANIZATION_TYPE) {
                    mapContactIdAccountIdLoser.put(listContactIdWinner[i], listAccountIdLoser[i]);
                }
        	}
        }
    
        // create a list of both Winning and Losing Accounts
        list<Id> listAccId = new list<Id>();
        listAccId.addAll(mapContactIdAccountIdLoser.values());
        listAccId.addAll(mapContactIdAccountIdWinner.values());

        // if we have any winning Contacts in Household or 1:1 Accounts...
        if (listAccId.size() > 0) {    
            // refresh account naming
            ACCT_IndividualAccounts_TDTM.renameHHAccounts(listAccIdHH);
            
            // move any Contact Opps to winning Accounts
            ACCT_IndividualAccounts_TDTM.moveOppsToContactAccount(mapContactIdAccountIdWinner, mapContactIdAccountIdLoser, null);
            
            // recreate Soft Credits 
            HH_OppContactRoles_TDTM.recreateHouseholdSoftCredits(listAccId);

            // recalc Account Rollups
            ACCT_IndividualAccounts_TDTM.rollupAccounts(listAccId);            
        }
        
        // recalc Contact Rollups
        RLLP_OppRollup opprollup = new RLLP_OppRollup();
        opprollup.rollupContacts(new map<Id, Contact>(listContactWinner));
        
        // fixup duplicate Affiliations & Primary Affiliation
        AFFL_Affiliations_TDTM.cleanupContactAffiliations(listContactWinner);
        
        // remove duplicate Relationships
        REL_Relationships_TDTM.cleanupContactRelationships(listContactWinner);
                
    }
    
}