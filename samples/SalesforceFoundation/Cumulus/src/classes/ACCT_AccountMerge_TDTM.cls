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
* @group Accounts
*
* @description Trigger Handler on Accounts that handles detecting when an Account Merge 
* has completed, in order to fixup an assortment of affected information.
*/
public class ACCT_AccountMerge_TDTM extends TDTM_Runnable {
        
    
    /*******************************************************************************************************
    * @description Trigger Handler on Accounts that handles detecting when an Account Merge 
    * has completed, in order to fixup an assortment of affected information.
    * @param listNew the list of Accounts from trigger new. 
    * @param listOld the list of Accounts from trigger old. 
    * @param triggerAction which trigger event (BeforeInsert, AfterInsert, etc.). 
    * @param objResult the describe for Accounts 
    * @return dmlWrapper.  always null.
    ********************************************************************************************************/
    public override DmlWrapper run(List<SObject> listNew, List<SObject> listOld, 
        TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {
        
        if (triggerAction == TDTM_Runnable.Action.AfterDelete) {
            set<ID> setMergeWinner = new set<ID>();
        
            for (SObject so : listOld) {
                Account acc = (Account)so;
                if (acc != null && acc.MasterRecordId != null)
                    setMergeWinner.add(acc.MasterRecordId);
            }
            
            // deal with any fixups that need to occur due to Account Merges
            if (setMergeWinner.size() > 0) {
                if(System.isFuture() || System.isBatch()) {
                    handleAccountMergeFixups(new List<Id>(setMergeWinner));
                }
                else {
                    handleAccountMergeFixupsFuture(new List<Id>(setMergeWinner));
                }
            }
        }        
        
        return null; 
    }

    /*******************************************************************************************************
    * @description After an Account Merge, call this Future routine to perform assorted fixups that need to
    * occur which include:
    *   - update the Account name and greetings (for Household Accounts)
    *   - recalculate Household Soft Credit OCRs (for Household Accounts)
    *   - recalculate Opportunity rollups (for Household and One2One Accounts)
    *   - fixup Addresses (for Household and Org Accounts)
    *
    * @param listAccountId the list of Accounts that were the winner in Account Merge 
    * @return null
    ********************************************************************************************************/
    @Future    
    private static void handleAccountMergeFixupsFuture(List<Id> listAccountId) {
            handleAccountMergeFixups(listAccountId);
    }

    private static void handleAccountMergeFixups(List<Id> listAccountId) {
        // figure out which of type of accounts we are dealing with
        list<ID> listHHId = new list<ID>();
        list<ID> listOne2OneId = new list<ID>();
        list<ID> listOrgId = new list<ID>();
        list<ID> listTemp = new list<ID>();            
        
        for (Account acc : [select Id, npe01__SYSTEM_AccountType__c from Account where Id in :listAccountId]) {
            if (acc.npe01__SYSTEM_AccountType__c == CAO_Constants.HH_ACCOUNT_TYPE)
                listHHId.add(acc.Id);
            else if (acc.npe01__SYSTEM_AccountType__c == CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE)
                listOne2OneId.add(acc.Id);
            else if (acc.npe01__SYSTEM_AccountType__c != CAO_Constants.BUCKET_ORGANIZATION_TYPE)
                listOrgId.add(acc.Id);            
        }
        
        if (listHHId.size() > 0) 
            ACCT_IndividualAccounts_TDTM.renameHHAccounts(listHHId);

        listTemp.addAll(listHHId);
        listTemp.addAll(listOne2OneId);
        if (listTemp.size() > 0) { 
            HH_OppContactRoles_TDTM.recreateHouseholdSoftCredits(listTemp);
            ACCT_IndividualAccounts_TDTM.rollupAccounts(listTemp);
        }
        
        listTemp.clear();
        listTemp.addAll(listHHId);
        listTemp.addAll(listOrgId);
        if (listTemp.size() > 0)
            ADDR_Addresses_TDTM.cleanupAccountAddresses(listTemp);
        
            
    }
    
}