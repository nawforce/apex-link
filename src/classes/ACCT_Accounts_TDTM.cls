/*
    Copyright (c) 2014 Salesforce.org
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
*
* @group Accounts
* @group-content ../../ApexDocContent/Accounts.htm
*
* @description Trigger Handler on Accounts that handles ensuring the correct system flags are set on
* our special accounts (Household, One-to-One), and also detects changes on Household Account that requires
* name updating.
*/
public class ACCT_Accounts_TDTM extends TDTM_Runnable {
        
    
    /*******************************************************************************************************
    * @description Trigger Handler on Accounts that handles ensuring the correct system flags are set on
    * our special accounts (Household, One-to-One), and also detects changes on Household Account that requires
    * name updating.
    * @param listNew the list of Accounts from trigger new. 
    * @param listOld the list of Accounts from trigger old. 
    * @param triggerAction which trigger event (BeforeInsert, AfterInsert, etc.). 
    * @param objResult the describe for Accounts 
    * @return dmlWrapper.  always null.
    ********************************************************************************************************/
    public override DmlWrapper run(List<SObject> listNew, List<SObject> listOld, 
        TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {
        
        ID rtIdAccountHH;
        ID rtIdAccountOne2One;
        if (triggerAction == TDTM_Runnable.Action.BeforeInsert || triggerAction == TDTM_Runnable.Action.BeforeUpdate) {        
            rtIdAccountHH = ACCT_IndividualAccounts_TDTM.rtIdForAccountModel(true);
            rtIdAccountOne2One = ACCT_IndividualAccounts_TDTM.rtIdForAccountModel(false);
        }
        list<Account> listHHNew = new list<Account>();
        list<Account> listHHOld = new list<Account>();
        
        integer i = 0;
        for (SObject so : listNew) {
            Account acc = (Account)so;
            
            if (triggerAction == TDTM_Runnable.Action.BeforeInsert || triggerAction == TDTM_Runnable.Action.BeforeUpdate) {
                if (acc.RecordTypeId == rtIdAccountHH && acc.RecordTypeId != null) {
                   if (acc.npe01__SYSTEM_AccountType__c == null || acc.npe01__SYSTEMISINDIVIDUAL__c != true) {
                        acc.npe01__SYSTEM_AccountType__c = CAO_Constants.HH_ACCOUNT_TYPE;
                        acc.Type = CAO_Constants.HH_TYPE;
                        acc.npe01__SYSTEMISINDIVIDUAL__c = true; 
                    }
                } else if (acc.RecordTypeId == rtIdAccountOne2One && acc.RecordTypeId != null) {
                   if (acc.npe01__SYSTEM_AccountType__c == null || acc.npe01__SYSTEMISINDIVIDUAL__c != true) {
                        acc.npe01__SYSTEM_AccountType__c = CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE;
                        acc.npe01__SYSTEMISINDIVIDUAL__c = true; 
                    }
                } else if (!(CAO_Constants.isBucketModel() && acc.Name == CAO_Constants.BUCKET_ACCOUNT_NAME)) {
                    if (triggerAction == TDTM_Runnable.Action.BeforeUpdate && acc.RecordTypeId != listOld[i].get('RecordTypeId')) {
                        acc.npe01__SYSTEMISINDIVIDUAL__c = false;
                        acc.npe01__SYSTEM_AccountType__c = null;
                    }
                }
            }
            
            if (triggerAction == TDTM_Runnable.Action.BeforeUpdate || triggerAction == TDTM_Runnable.Action.AfterUpdate) {
               if (acc.npe01__SYSTEM_AccountType__c == CAO_Constants.HH_ACCOUNT_TYPE) {
                   listHHNew.add(acc);
                   listHHOld.add((Account)listOld[i]);
               }
            }
        i++;
        }
        
        // deal with any name changes for Household Accounts
        if (listHHNew.size() > 0)
            HH_HouseholdNaming.handleHHNameUpdates(listHHNew, listHHOld, triggerAction, objResult);

        return null;
    }
}