/*  
    Copyright (c) 2011, Salesforce.org
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
* @group Rollups
* @group-content ../../ApexDocContent/Rollups.htm
* @description Main logic class for rollups of opportunities to the Accounts, Contacts, and Households.
*/
public without sharing class RLLP_OppRollup {
    
    /** @description Constructor gets settings and sets up variables. */
    public RLLP_OppRollup(){
        // setup households settings
        RLLP_OppRollup_UTIL.setupRollupVars(UTIL_CustomSettingsFacade.getHouseholdsSettings());
        // setup user rollups
        RLLP_OppRollup_UTIL.setupUserRollupVars();
    }
    
    /*******************************************************************************************************
    * @description Opportunity rollups from triggers: determines which opportunities have changed and pass
    * the contact, account, and household IDs to rollup methods.
    * @param newOpps The map of Id to Opportunity from trigger new.
    * @param listOld The map of Id to Opportunity from trigger old. 
    * @return void This method does updates directly and returns nothing.
    ********************************************************************************************************/
    public void rollupForOppTrigger( map<id, Opportunity> newOpps, map<id, Opportunity> oldOpps ) {
        //opp ids for contact/hh querying
        set<id> contactRequerySet = new set<id>();        
        //opp ids for Account querying
        set<id> accountRequerySet = new set<id>();        
        
            //delete or insert            
            if (newOpps == null || oldOpps == null){
                for (id oid : (newOpps == null ? oldOpps.keySet() : newOpps.keySet())){
                    Opportunity o = (newOpps == null ? oldOpps.get(oid) : newOpps.get(oid));
                    if (RLLP_OppRollup_UTIL.oppForRollup(o)){
                        if (RLLP_OppRollup_UTIL.needsAccountRollup(o)){
                            accountRequerySet.add(o.AccountId);
                        }                       
                        if (RLLP_OppRollup_UTIL.needsContactRollup(o)){
                            contactRequerySet.add(oid);
                        }
                    }    
                }
            }            
            //update
            else{
                for (id oid : newOpps.keySet()){
                   Opportunity newo = newOpps.get(oid);
                   Opportunity oldo = oldOpps.get(oid);
                   //if the new one is valid and there are differences between the two opps
                   if (RLLP_OppRollup_UTIL.oppForRollup(newo, oldo)){
                        if (RLLP_OppRollup_UTIL.needsAccountRollup(newo)){
                            accountRequerySet.add(newo.AccountId);
                        }                       
                        if (RLLP_OppRollup_UTIL.needsContactRollup(newo)){
                            contactRequerySet.add(oid);
                        }
                   }    
                }
            }
            
        if (!accountRequerySet.isEmpty()){
            RLLP_OppRollup_UTIL oppRollupUtil = new RLLP_OppRollup_UTIL();
            map<id, Account> amap = new map<id, account>((list<Account>)Database.query(oppRollupUtil.buildAccountQuery() + 
                                                                        ' where id in :accountRequerySet'));            
            //if we only need to rollup a single Account, do sync
            if (amap.size() == 1 || system.isFuture() || system.isBatch()){
                rollupAccounts(amap);                               
            }   
            //do it @future
            else if (Limits.getAsyncCalls() < Limits.getLimitAsyncCalls()){
                rollupAccountsFuture(amap.keySet());
            }                       
        }
        
        //contacts needs to be done async to make sure the OCR is in place
        if (!contactRequerySet.isEmpty()){
        	if (system.isFuture() || system.isBatch())
                RLLP_OppRollup.rollupContactsandHouseholdsForTrigger(contactRequerySet);                        
            else        	
                RLLP_OppRollup.rollupContactsandHouseholdsForTriggerFuture(contactRequerySet);                        
        }            
    }       
    
    /*******************************************************************************************************
    * @description Future wrapper method for updating contacts and households from a set of opportunity IDs.
    * @param OppIds A set of opportunity ids.
    * @return void This method does updates directly and returns nothing.
    ********************************************************************************************************/
    @future 
    public static void rollupContactsandHouseholdsForTriggerFuture(set<id> OppIds) {
        rollupContactsandHouseholdsForTrigger(OppIds);
    }

    /*******************************************************************************************************
    * @description Updating contacts and households rollups from a set of opportunity IDs.
    * @param OppIds A set of opportunity ids.
    * @return void This method does updates directly and returns nothing.
    ********************************************************************************************************/
    public static void rollupContactsandHouseholdsForTrigger(set<id> OppIds){
        //we need this to make sure there's a proper OCR when Contact Opps are inserted
        
        map<id, Contact> cmap = new map<id, Contact>();
        map<id, npo02__Household__c> hhmap = new map<id, npo02__Household__c>();
        
        // use the contact roles to find the contacts and households
        RLLP_OppRollup_UTIL oppRollupUtil = new RLLP_OppRollup_UTIL();
        for (OpportunityContactRole r : Database.query(oppRollupUtil.buildOpportunityContactRoleQuery() +
                                            ' where Opportunity.Id IN :OppIds and isPrimary = true ALL ROWS ')){   
                    
            cmap.put(r.ContactId, r.Contact);
                
            if (r.Contact.npo02__Household__c != null){
                hhmap.put(r.Contact.npo02__Household__c, r.Contact.npo02__Household__r);
            }
        }       
            
        if (cmap.size() > 0){              
            RLLP_OppRollup oppR = new RLLP_OppRollup();
            oppR.rollupContacts(cmap);   
            if (hhmap.size() > 0){
                oppR.rollupHouseholds(hhmap);    
            }        
        }           
    }
    
    /*******************************************************************************************************
    * @description Future wrapper method for updating account rollups from a set of account IDs.
    * @param acctIds A set of account ids.
    * @return void This method does updates directly and returns nothing.
    ********************************************************************************************************/
    @future 
    public static void rollupAccountsFuture( set<id> acctIds ) {
        if (acctIds != null && !acctIds.isEmpty()) {
            RLLP_OppRollup_UTIL oppRollupUtil = new RLLP_OppRollup_UTIL();
            map<id, Account> amap = new map<id, account>((list<Account>)Database.query(oppRollupUtil.buildAccountQuery() + 
                                                           ' where id IN :acctIds')); 
            RLLP_OppRollup rg = new RLLP_OppRollup();
            rg.rollupAccounts(amap);
        }
    }

    /*******************************************************************************************************
    * @description Future wrapper method for updating contact rollups from a set of contact IDs.
    * @param acctIds A set of contact ids.
    * @return void This method does updates directly and returns nothing.
    ********************************************************************************************************/
    @future
    public static void rollupContactsFuture (set<id> conIDs){
        if (conIds != null && !conIDs.isEmpty()){
            RLLP_OppRollup_UTIL oppRollupUtil = new RLLP_OppRollup_UTIL();
            map<id, Contact> cmap = new map<id, Contact>((list<Contact>)Database.query(oppRollupUtil.buildContactQuery() +
                                                          ' where id IN :conIds'));
            RLLP_OppRollup rg = new RLLP_OppRollup();
            rg.rollupContacts(cmap);
        }
    }

    /*******************************************************************************************************
    * @description Future wrapper method for updating household rollups from a set of household IDs.
    * @param acctIds A set of household ids.
    * @return void This method does updates directly and returns nothing.
    ********************************************************************************************************/
    @future
    public static void rollupHouseholdsFuture (set<id> hhIDs){
        if (hhIds != null && !hhIds.isEmpty()){
            RLLP_OppRollup_UTIL oppRollupUtil = new RLLP_OppRollup_UTIL();
            map<id, npo02__Household__c> hhmap = new map<id, npo02__Household__c>((list<npo02__Household__c>)Database.query(oppRollupUtil.buildHouseholdQuery() +
                                                                            ' where id IN :hhIDs'));
            RLLP_OppRollup rg = new RLLP_OppRollup();
            rg.rollupHouseholds(hhmap);
        }
    }

    /*******************************************************************************************************
    * @description Runs opportunity rollups for accounts.
    * @param amap A map of account IDs to accounts.
    * @return void This method does updates directly and returns nothing.
    ********************************************************************************************************/
    public void rollupAccounts(map<id, Account> amap) {

        //arg used by multi currency for rollup field currencies
        map<id,Opportunity> opptyCurrencies;
        
        //create mappings for UDRs if they exist
        Map<String, Object> UDRFieldMap = new Map<String, Object>();
        
        if (RLLP_OppRollup_UTIL.urfsMap.size() > 0){
            Map<String, Schema.SObjectField> M = Schema.SObjectType.Account.fields.getMap();                    
                for (string s : RLLP_OppRollup_UTIL.urfsMap.keyset()){
                    if (Account.getSObjectType().getDescribe().getName() == RLLP_OppRollup_UTIL.urfsMap.get(s).npo02__Object_Name__c){
                        npo02__User_Rollup_Field_Settings__c urfs = RLLP_OppRollup_UTIL.urfsMap.get(s);
                        if (M.get(urfs.npo02__Target_Field__c) != null){
                            if (M.get(urfs.npo02__Target_Field__c).getDescribe().getSOAPType() == Schema.Soaptype.Integer || M.get(urfs.npo02__Target_Field__c).getDescribe().getSOAPType() == Schema.Soaptype.Double){
                                UDRFieldMap.put(urfs.npo02__Target_Field__c, 0);
                            }      
                        else{
                            UDRFieldMap.put(urfs.npo02__Target_Field__c, null);                              
                        }                                  
                    }
                }
            }
        }        
        
        // copy the accounts to a map of zerod out versions
        map<id, Account> accountsToUpdate = new map<id, Account>();                
        for (id aid : amap.keyset()){
            
            Account a = new Account(id = aid, npo02__TotalOppAmount__c = 0, npo02__AverageAmount__c = 0, 
                npo02__SmallestAmount__c = 0, npo02__LargestAmount__c = 0, npo02__FirstCloseDate__c = null,
                npo02__LastCloseDate__c = null, npo02__NumberOfClosedOpps__c = 0, npo02__OppAmountThisYear__c = 0, 
                npo02__OppsClosedThisYear__c = 0, npo02__OppAmountLastYear__c = 0, npo02__OppsClosedLastYear__c = 0,
                npo02__OppsClosed2YearsAgo__c = 0, npo02__OppAmount2YearsAgo__c = 0,
                npo02__OppsClosedLastNDays__c = 0, npo02__OppAmountLastNDays__c = 0, 
                npo02__TotalMembershipOppAmount__c = 0, npo02__NumberOfMembershipOpps__c = 0,               
                npo02__LastMembershipDate__c = null, npo02__LastMembershipAmount__c = 0,                
                npo02__LastMembershipLevel__c = null, npo02__LastMembershipOrigin__c = null,                
                npo02__MembershipJoinDate__c = null, npo02__MembershipEndDate__c = null,
                npo02__LastOppAmount__c = null,
                npo02__Best_Gift_Year__c = null, npo02__Best_Gift_Year_Total__c = 0 );
            
                //add zeroed out UDRs
                for (String s : UDRFieldMap.keySet()){
                    a.put(s, UDRFieldMap.get(s));
                }            
            
            accountsToUpdate.put(aid, a);
            
            // map currency across if multicurrency
            if(RLLP_OppRollup_UTIL.isMultiCurrency())
                accountsToUpdate.get(aid).put(RLLP_OppRollup_UTIL.mcFieldValues.get('Account'),amap.get(aid).get(RLLP_OppRollup_UTIL.mcFieldValues.get('Account')));
        }
        
        //need local vars b/c dynamic soql is stupid
        set<id> aset = amap.keySet();
        set<string> typeset = RLLP_OppRollup_UTIL.oppTypesToExcludeAccts;
        set<id> rectypeset = RLLP_OppRollup_UTIL.recordTypesToExcludeAccts;

        RLLP_OppRollup_UTIL oppRollupUtil = new RLLP_OppRollup_UTIL();
        list<sobject> objectList = Database.query(oppRollupUtil.buildAccountRollupQuery());

        // invokes code to get the currency of npo02__CombinedRollupFieldset__c oppty
        opptyCurrencies = (RLLP_OppRollup_UTIL.isMultiCurrency() ? RLLP_OppRollup_UTIL.rcfFindCurrency(objectList) : null);
        
        for (sobject r : objectList){            
            id aid = (id)(r.get('AccountId'));
            if (aid != null){
                updateRollupFromResult((sobject)(accountsToUpdate.get(aid)), r, opptyCurrencies);
                
                //calculate best gift year
                decimal yrAmt = (decimal)r.get('TotalOppAmount');
                integer yr = (integer)r.get('CalendarYr');
                Account a = accountsToUpdate.get(aid);
                if ((yrAmt > a.npo02__Best_Gift_Year_Total__c || a.npo02__Best_Gift_Year_Total__c==null ) && yr !=null){
                    a.npo02__Best_Gift_Year_Total__c = yrAmt;
                    a.npo02__Best_Gift_Year__c = string.valueOf(yr);
                }          
            }   
        }       
        
        objectList.clear();
        objectList = database.query(oppRollupUtil.buildAccountNDayQuery());
        
        for (sobject r : objectList){
            id aid = (id)(r.get('AccountId'));
            if (aid != null)
                updateRollupFromResultLastNDays((sobject)(accountsToUpdate.get(aid)), r);
        }

        if (!RLLP_OppRollup_UTIL.mbrRecordTypes.isEmpty()) {
            set<id> memberset = RLLP_OppRollup_UTIL.mbrRecordTypes; 
            objectList.clear();
            objectList = database.query(oppRollupUtil.buildAccountMembershipQuery());        
            
            opptyCurrencies = (RLLP_OppRollup_UTIL.isMultiCurrency() ? RLLP_OppRollup_UTIL.rcfFindCurrency(objectList) : null);
   
            for (sobject r : objectList){                
                id aid = (id)(r.get('AccountId'));
                if (aid != null)
                    updateRollupFromResultMembership((sobject)(accountsToUpdate.get(aid)), r, opptyCurrencies);
            }
        }

        // remove any records that have not changed
        for (id aid : amap.keySet()) {
            account a1 = amap.get(aid); 
            account a2 = accountsToUpdate.get(aid);
            
            if (a1.npo02__TotalOppAmount__c == a2.npo02__TotalOppAmount__c &&
                    a1.npo02__OppsClosedThisYear__c == a2.npo02__OppsClosedThisYear__c &&
                    a1.npo02__OppsClosedLastYear__c == a2.npo02__OppsClosedLastYear__c &&
                    a1.npo02__OppsClosedLastNDays__c == a2.npo02__OppsClosedLastNDays__c &&
                    a1.npo02__OppsClosed2YearsAgo__c == a2.npo02__OppsClosed2YearsAgo__c &&
                    a1.npo02__OppAmountLastYear__c == a2.npo02__OppAmountLastYear__c &&
                    a1.npo02__OppAmount2YearsAgo__c == a2.npo02__OppAmount2YearsAgo__c &&
                    a1.npo02__OppAmountThisYear__c == a2.npo02__OppAmountThisYear__c &&
                    a1.npo02__OppAmountLastNDays__c == a2.npo02__OppAmountLastNDays__c &&
                    a1.npo02__FirstCloseDate__c == a2.npo02__FirstCloseDate__c &&
                    a1.npo02__LastCloseDate__c == a2.npo02__LastCloseDate__c && 
                    a1.npo02__TotalMembershipOppAmount__c == a2.npo02__TotalMembershipOppAmount__c &&
                    a1.npo02__LastMembershipLevel__c == a2.npo02__LastMembershipLevel__c &&
                    a1.npo02__LastMembershipDate__c == a2.npo02__LastMembershipDate__c &&
                    a1.npo02__MembershipJoinDate__c == a2.npo02__MembershipJoinDate__c &&
                    a1.npo02__MembershipEndDate__c == a2.npo02__MembershipEndDate__c &&
                    a1.npo02__Best_Gift_Year__c == a2.npo02__Best_Gift_Year__c &&
                    a1.npo02__Best_Gift_Year_Total__c == a2.npo02__Best_Gift_Year_Total__c &&
                    a1.npo02__AverageAmount__c == a2.npo02__AverageAmount__c &&
                    a1.npo02__LargestAmount__c == a2.npo02__LargestAmount__c &&
                    a1.npo02__SmallestAmount__c == a2.npo02__SmallestAmount__c
                    ){ 
                         
                if (UDRFieldMap.size() > 0){
                    boolean hasUDRUpdate = false;
                    for (String s : UDRFieldMap.keySet()){
                        if (a1.get(s) != a2.get(s)){
                            hasUDRUpdate = true;
                        }                                       
                    }                    
                    if (!hasUDRUpdate){
                        accountsToUpdate.remove(aid);
                    }
                }        
                else{                    
                    accountsToUpdate.remove(aid);
                }
            }
        }         

        update accountsToUpdate.values();     
    }    
    
    /*******************************************************************************************************
    * @description Runs opportunity rollups for contacts.
    * @param cmap A map of contact IDs to contacts.
    * @return void This method does updates directly and returns nothing.
    ********************************************************************************************************/
    public void rollupContacts(map<id, Contact> cmap){
        //arg used by multi currency for rollup field currencies
        map<Id,Opportunity> opptyCurrencies;
        
        //create mappings for UDRs if they exist
        Map<String, Object> UDRFieldMap = new Map<String, Object>();
        
        if (RLLP_OppRollup_UTIL.urfsMap.size() > 0){
            Map<String, Schema.SObjectField> M = Schema.SObjectType.Contact.fields.getMap();                    
                for (string s : RLLP_OppRollup_UTIL.urfsMap.keyset()){
                    if (Contact.getSObjectType().getDescribe().getName() == RLLP_OppRollup_UTIL.urfsMap.get(s).npo02__Object_Name__c){
                        npo02__User_Rollup_Field_Settings__c urfs = RLLP_OppRollup_UTIL.urfsMap.get(s);
                        if (M.get(urfs.npo02__Target_Field__c) != null){
                            if (M.get(urfs.npo02__Target_Field__c).getDescribe().getSOAPType() == Schema.Soaptype.Integer || M.get(urfs.npo02__Target_Field__c).getDescribe().getSOAPType() == Schema.Soaptype.Double){
                                UDRFieldMap.put(urfs.npo02__Target_Field__c, 0);
                            }      
                        else{
                            UDRFieldMap.put(urfs.npo02__Target_Field__c, null);                              
                        }                                  
                    }
                }
            }
        }        
        
        // copy the contacts to a map of zeroed out versions
        map<id, Contact> contactsToUpdate = new map<id, Contact>();
        
        for (id cid : cmap.keySet()){           
            Contact c = new Contact(id = cid, npo02__TotalOppAmount__c = 0, npo02__AverageAmount__c = 0, 
                npo02__SmallestAmount__c = 0, npo02__LargestAmount__c = 0, npo02__FirstCloseDate__c = null, 
                npo02__LastCloseDate__c = null, npo02__NumberOfClosedOpps__c = 0, npo02__OppAmountThisYear__c = 0, 
                npo02__OppsClosedThisYear__c = 0, npo02__OppAmountLastYear__c = 0, npo02__OppsClosedLastYear__c = 0,
                npo02__OppsClosed2YearsAgo__c = 0, npo02__OppAmount2YearsAgo__c = 0, 
                npo02__OppsClosedLastNDays__c = 0, npo02__OppAmountLastNDays__c = 0,
                npo02__TotalMembershipOppAmount__c = 0, npo02__NumberOfMembershipOpps__c = 0,               
                npo02__LastMembershipDate__c = null, npo02__LastMembershipAmount__c = 0,                
                npo02__LastMembershipLevel__c = null, npo02__LastMembershipOrigin__c = null,                
                npo02__MembershipJoinDate__c = null, npo02__MembershipEndDate__c = null,                
                npo02__LastOppAmount__c = null, 
                npo02__Best_Gift_Year__c = null, npo02__Best_Gift_Year_Total__c = 0);
            
           //add zeroed out UDRs
            for (String s : UDRFieldMap.keySet()){
                c.put(s, UDRFieldMap.get(s));
            }      
            
            contactsToUpdate.put(cid, c);
            // map currency across if multicurrency
            if(RLLP_OppRollup_UTIL.isMultiCurrency())
                contactsToUpdate.get(cid).put(RLLP_OppRollup_UTIL.mcFieldValues.get('Contact'),cmap.get(cid).get(RLLP_OppRollup_UTIL.mcFieldValues.get('Contact')));            
        }
    
        list<sobject> objectList = new list<sobject>();   
        set<id> cset = cmap.keySet();
        set<string> typeset = RLLP_OppRollup_UTIL.oppTypesToExcludeCons;
        set<id> rectypeset = RLLP_OppRollup_UTIL.recordTypesToExcludeCons;
        
        RLLP_OppRollup_UTIL oppRollupUtil = new RLLP_OppRollup_UTIL();
        objectList = database.query(oppRollupUtil.buildContactRollupQuery());
            
        // invokes code to get the currency of npo02__CombinedRollupFieldset__c oppty
        opptyCurrencies = (RLLP_OppRollup_UTIL.isMultiCurrency() ? RLLP_OppRollup_UTIL.rcfFindCurrency(objectList) : null);
            
        for (sobject r : objectList) {
            id cid = (id)(r.get('ContactId'));
            if (cid != null){
                updateRollupFromResult((sobject)(contactsToUpdate.get(cid)), r, opptyCurrencies);
                
                //calculate best gift year
                decimal yrAmt = (decimal)r.get('TotalOppAmount');
                integer yr = (integer)r.get('CalendarYr');
                
                Contact c = contactsToUpdate.get(cid);

                if(RLLP_OppRollup_UTIL.isMultiCurrency())
                    yrAmt = RLLP_OppRollup_UTIL.ConvertFromCorporate((string)c.get('CurrencyIsoCode'),yrAmt);

                if ((yrAmt > c.npo02__Best_Gift_Year_Total__c || c.npo02__Best_Gift_Year_Total__c==null ) && yr !=null){
                    c.npo02__Best_Gift_Year_Total__c = yrAmt;
                    c.npo02__Best_Gift_Year__c = string.valueOf(yr);
                }
            }
        }

        objectList.clear();
        objectList = database.query(oppRollupUtil.buildContactNDayQuery());
        
        for (sobject r : objectList){
            id cid = (id)(r.get('ContactId'));
            if (cid != null)
                updateRollupFromResultLastNDays((sobject)(contactsToUpdate.get(cid)), r);
        }

        if (!RLLP_OppRollup_UTIL.mbrRecordTypes.isEmpty()){
            set<id> memberset = RLLP_OppRollup_UTIL.mbrRecordTypes;
            objectList.clear();
            objectList = database.query(oppRollupUtil.buildContactMembershipQuery());        
            
            opptyCurrencies = (RLLP_OppRollup_UTIL.isMultiCurrency() ? RLLP_OppRollup_UTIL.rcfFindCurrency(objectList) : null);
   
            for (sobject r : objectList){                
                id cid = (id)(r.get('ContactId'));
                if (cid != null)
                    updateRollupFromResultMembership((sobject)(contactsToUpdate.get(cid)), r, opptyCurrencies);
            }
        }        
        
        for (id cid : cmap.keySet()) {
            contact c1 = cmap.get(cid);
            contact c2 = contactsToUpdate.get(cid);
            
            if (c1.npo02__TotalOppAmount__c == c2.npo02__TotalOppAmount__c &&
                    c1.npo02__OppsClosedThisYear__c == c2.npo02__OppsClosedThisYear__c &&
                    c1.npo02__OppsClosedLastYear__c == c2.npo02__OppsClosedLastYear__c &&
                    c1.npo02__OppsClosedLastNDays__c == c2.npo02__OppsClosedLastNDays__c &&
                    c1.npo02__OppsClosed2YearsAgo__c == c2.npo02__OppsClosed2YearsAgo__c &&
                    c1.npo02__OppAmountLastYear__c == c2.npo02__OppAmountLastYear__c &&
                    c1.npo02__OppAmount2YearsAgo__c == c2.npo02__OppAmount2YearsAgo__c &&
                    c1.npo02__OppAmountThisYear__c == c2.npo02__OppAmountThisYear__c &&
                    c1.npo02__OppAmountLastNDays__c == c2.npo02__OppAmountLastNDays__c &&
                    c1.npo02__FirstCloseDate__c == c2.npo02__FirstCloseDate__c &&
                    c1.npo02__LastCloseDate__c == c2.npo02__LastCloseDate__c && 
                    c1.npo02__TotalMembershipOppAmount__c == c2.npo02__TotalMembershipOppAmount__c &&
                    c1.npo02__LastMembershipLevel__c == c2.npo02__LastMembershipLevel__c &&
                    c1.npo02__LastMembershipDate__c == c2.npo02__LastMembershipDate__c &&
                    c1.npo02__MembershipJoinDate__c == c2.npo02__MembershipJoinDate__c &&
                    c1.npo02__MembershipEndDate__c == c2.npo02__MembershipEndDate__c &&
                    c1.npo02__Best_Gift_Year__c == c2.npo02__Best_Gift_Year__c &&
                    c1.npo02__Best_Gift_Year_Total__c == c2.npo02__Best_Gift_Year_Total__c &&
                    c1.npo02__AverageAmount__c == c2.npo02__AverageAmount__c &&
                    c1.npo02__LargestAmount__c == c2.npo02__LargestAmount__c &&
                    c1.npo02__SmallestAmount__c == c2.npo02__SmallestAmount__c
                    ){
                    
                if (UDRFieldMap.size() > 0){
                    boolean hasUDRUpdate = false;
                    for (String s : UDRFieldMap.keySet()){
                        if (c1.get(s) != c2.get(s)){
                            hasUDRUpdate = true;
                        }                                       
                    }                    
                    if (!hasUDRUpdate){
                        contactsToUpdate.remove(cid);
                    }
                }                    
                else{                    
                    contactsToUpdate.remove(cid);
                }
            }                
        }

        update contactsToUpdate.values();
    }    
    
    /*******************************************************************************************************
    * @description Runs opportunity rollups for households.
    * @param hhmap A map of contact IDs to households.
    * @return void This method does updates directly and returns nothing.
    ********************************************************************************************************/
    public void rollupHouseholds(map<id, npo02__Household__c> hhmap){        
        //arg used by multi currency for rollup field currencies
        map<Id,Opportunity> opptyCurrencies;        
        
        //create mappings for UDRs if they exist
        Map<String, Object> UDRFieldMap = new Map<String, Object>();
        
        if (RLLP_OppRollup_UTIL.urfsMap.size() > 0){
            Map<String, Schema.SObjectField> M = Schema.SObjectType.npo02__Household__c.fields.getMap();                    
                for (string s : RLLP_OppRollup_UTIL.urfsMap.keyset()){
                    if (npo02__Household__c.getSObjectType().getDescribe().getName() == RLLP_OppRollup_UTIL.urfsMap.get(s).npo02__Object_Name__c){
                        npo02__User_Rollup_Field_Settings__c urfs = RLLP_OppRollup_UTIL.urfsMap.get(s);
                        if (M.get(urfs.npo02__Target_Field__c) != null){
                            if (M.get(urfs.npo02__Target_Field__c).getDescribe().getSOAPType() == Schema.Soaptype.Integer || M.get(urfs.npo02__Target_Field__c).getDescribe().getSOAPType() == Schema.Soaptype.Double){
                                UDRFieldMap.put(urfs.npo02__Target_Field__c, 0);
                            }      
                        else{
                            UDRFieldMap.put(urfs.npo02__Target_Field__c, null);                              
                        }                                  
                    }
                }
            }
        }    
        
        // copy the contacts and households to a map of zerod out versions
        map<id, npo02__Household__c> householdsToUpdate = new map<id, npo02__Household__c>();
        
        for (id hhid : hhmap.keySet()) {
            npo02__Household__c h = new npo02__Household__c(id = hhid, npo02__TotalOppAmount__c = 0, npo02__AverageAmount__c = 0, 
                npo02__SmallestAmount__c = 0, npo02__LargestAmount__c = 0, npo02__FirstCloseDate__c = null, 
                npo02__LastCloseDate__c = null, npo02__NumberOfClosedOpps__c = 0, npo02__OppAmountThisYear__c = 0, 
                npo02__OppsClosedThisYear__c = 0, npo02__OppAmountLastYear__c = 0, npo02__OppsClosedLastYear__c = 0,
                npo02__OppsClosed2YearsAgo__c = 0, npo02__OppAmount2YearsAgo__c = 0, 
                npo02__OppsClosedLastNDays__c = 0, npo02__OppAmountLastNDays__c = 0,
                npo02__TotalMembershipOppAmount__c = 0, npo02__NumberOfMembershipOpps__c = 0,               
                npo02__LastMembershipDate__c = null, npo02__LastMembershipAmount__c = 0,                
                npo02__LastMembershipLevel__c = null, npo02__LastMembershipOrigin__c = null,                
                npo02__MembershipJoinDate__c = null, npo02__MembershipEndDate__c = null,                
                npo02__LastOppAmount__c = null,
                npo02__Best_Gift_Year__c = null, npo02__Best_Gift_Year_Total__c = 0);            
            
            //add zeroed out UDRs
            for (String s : UDRFieldMap.keySet()){
                h.put(s, UDRFieldMap.get(s));
            }
            
            householdsToUpdate.put(hhid, h);            
            
            // map currency across if multicurrency
            if(RLLP_OppRollup_UTIL.isMultiCurrency())
                householdsToUpdate.get(hhid).put(RLLP_OppRollup_UTIL.mcFieldValues.get('npo02__Household__c'),hhmap.get(hhid).get(RLLP_OppRollup_UTIL.mcFieldValues.get('npo02__Household__c')));
        }
    
        list<sobject> objectList = new list<sobject>();   
        set<id> hhset = hhmap.keySet();
        set<string> typeset = RLLP_OppRollup_UTIL.oppTypesToExcludeCons;
        set<id> rectypeset = RLLP_OppRollup_UTIL.recordTypesToExcludeCons;
        
        RLLP_OppRollup_UTIL oppRollupUtil = new RLLP_OppRollup_UTIL();
        objectList = database.query(oppRollupUtil.buildHouseholdRollupQuery());
            
        // invokes code to get the currency of npo02__CombinedRollupFieldset__c oppty
        opptyCurrencies = (RLLP_OppRollup_UTIL.isMultiCurrency() ? RLLP_OppRollup_UTIL.rcfFindCurrency(objectList) : null);
            
        for (sobject r : objectList) {
            id hhid = (id)(r.get('hhid'));
            if (hhid != null){
                updateRollupFromResult((sobject)(householdsToUpdate.get(hhid)), r, opptyCurrencies);
            
                //calculate best gift year
                decimal yrAmt = (decimal)r.get('TotalOppAmount');
                integer yr = (integer)r.get('CalendarYr');
                npo02__Household__c hh = householdsToUpdate.get(hhid);
                if ((yrAmt > hh.npo02__Best_Gift_Year_Total__c || hh.npo02__Best_Gift_Year_Total__c==null ) && yr !=null){
                    hh.npo02__Best_Gift_Year_Total__c = yrAmt;
                    hh.npo02__Best_Gift_Year__c = string.valueOf(yr);
                }                
            }                
        }        
        
        objectList.clear();
        objectList = database.query(oppRollupUtil.buildHouseholdNDayQuery());
        
        for (sobject r : objectList){
            id hhid = (id)(r.get('hhid'));
            if (hhid != null)
                updateRollupFromResultLastNDays((sobject)(householdsToUpdate.get(hhid)), r);
        }

        if (!RLLP_OppRollup_UTIL.mbrRecordTypes.isEmpty()){
            set<id> memberset = RLLP_OppRollup_UTIL.mbrRecordTypes;
            objectList.clear();
            objectList = database.query(oppRollupUtil.buildHouseholdMembershipQuery());        
            
            opptyCurrencies = (RLLP_OppRollup_UTIL.isMultiCurrency() ? RLLP_OppRollup_UTIL.rcfFindCurrency(objectList) : null);
   
            for (sobject r : objectList){                
                id hhid = (id)(r.get('hhid'));
                if (hhid != null)
                    updateRollupFromResultMembership((sobject)(householdsToUpdate.get(hhid)), r, opptyCurrencies);
            }
        }        
        
        for (id hhid : hhmap.keySet()) {
            npo02__Household__c h1 = hhmap.get(hhid);
            npo02__Household__c h2 = householdsToUpdate.get(hhid);
            
            if (h1.npo02__TotalOppAmount__c == h2.npo02__TotalOppAmount__c &&
                    h1.npo02__OppsClosedThisYear__c == h2.npo02__OppsClosedThisYear__c &&
                    h1.npo02__OppsClosedLastYear__c == h2.npo02__OppsClosedLastYear__c &&
                    h1.npo02__OppsClosedLastNDays__c == h2.npo02__OppsClosedLastNDays__c &&
                    h1.npo02__OppsClosed2YearsAgo__c == h2.npo02__OppsClosed2YearsAgo__c &&
                    h1.npo02__OppAmountLastYear__c == h2.npo02__OppAmountLastYear__c &&
                    h1.npo02__OppAmount2YearsAgo__c == h2.npo02__OppAmount2YearsAgo__c &&
                    h1.npo02__OppAmountThisYear__c == h2.npo02__OppAmountThisYear__c &&
                    h1.npo02__OppAmountLastNDays__c == h2.npo02__OppAmountLastNDays__c &&
                    h1.npo02__FirstCloseDate__c == h2.npo02__FirstCloseDate__c &&
                    h1.npo02__LastCloseDate__c == h2.npo02__LastCloseDate__c && 
                    h1.npo02__TotalMembershipOppAmount__c == h2.npo02__TotalMembershipOppAmount__c &&
                    h1.npo02__LastMembershipLevel__c == h2.npo02__LastMembershipLevel__c &&
                    h1.npo02__LastMembershipDate__c == h2.npo02__LastMembershipDate__c &&
                    h1.npo02__MembershipJoinDate__c == h2.npo02__MembershipJoinDate__c &&
                    h1.npo02__MembershipEndDate__c == h2.npo02__MembershipEndDate__c &&
                    h1.npo02__Best_Gift_Year__c == h2.npo02__Best_Gift_Year__c &&
                    h1.npo02__Best_Gift_Year_Total__c == h2.npo02__Best_Gift_Year_Total__c &&
                    h1.npo02__AverageAmount__c == h2.npo02__AverageAmount__c &&
                    h1.npo02__LargestAmount__c == h2.npo02__LargestAmount__c &&
                    h1.npo02__SmallestAmount__c == h2.npo02__SmallestAmount__c
                    ){
                                                
            if (UDRFieldMap.size() > 0){
                    boolean hasUDRUpdate = false;
                    for (String s : UDRFieldMap.keySet()){
                        if (h1.get(s) != h2.get(s)){
                            hasUDRUpdate = true;
                        }                                       
                    }                    
                    if (!hasUDRUpdate){
                        householdsToUpdate.remove(hhid);
                    }
                }                    
                else{                    
                    householdsToUpdate.remove(hhid);
                }
            }                
        }
        
        update householdsToUpdate.values();
    }  
    
    /*******************************************************************************************************
    * @description Runs opportunity soft credit rollups for contacts.
    * @param list The list of contacts to rollup to.
    * @return void This method does updates directly and returns nothing.
    ********************************************************************************************************/
    public void rollupSoftCreditsWithPartialSupport(list<Contact> listCon) {
        RLLP_OppPartialSoftCreditRollup.rollupPartialSoftCredits(listCon);      
    }
    
    /*******************************************************************************************************
    * @description Runs opportunity soft credit rollups for contacts. 
    * NO LONGER USED SINCE DOES NOT SUPPORT PARTIAL SOFT CREDITS.  Can be removed once we are done testing.
    * @param cmap A map of contact IDs to contacts.
    * @return void This method does updates directly and returns nothing.
    public void rollupSoftCreditsOLDNoPartialSupport(map<id, Contact> cmap){      
        //arg used by multi currency for rollup field currencies
        map<Id,Opportunity> opptyCurrencies;
        // copy the contacts to a zeroed out version
        map<id, Contact> contactsToUpdate = new map<id, Contact>();        
        
        for (id cid : cmap.keySet()) {
            contactsToUpdate.put(cid, new Contact(id = cid,                 
                npo02__Soft_Credit_This_Year__c = 0, npo02__Soft_Credit_Last_Year__c = 0,                
                npo02__Soft_Credit_Two_Years_Ago__c = 0, npo02__Soft_Credit_Total__c = 0, Soft_Credit_Last_N_Days__c = 0
            ));
            // map currency across if multicurrency
            if(RLLP_OppRollup_UTIL.isMultiCurrency())
                contactsToUpdate.get(cid).put(RLLP_OppRollup_UTIL.mcFieldValues.get('Contact'),cmap.get(cid).get(RLLP_OppRollup_UTIL.mcFieldValues.get('Contact')));            
        }
    
        list<sobject> objectList = new list<sobject>();   
        set<id> cset = cmap.keySet();        
        set<string> softCreditRoles = RLLP_OppRollup_UTIL.softCreditRoles;
        set<string> typeset = RLLP_OppRollup_UTIL.oppTypesToExcludeCons;
        set<id> rectypeset = RLLP_OppRollup_UTIL.recordTypesToExcludeCons;
        
        RLLP_OppRollup_UTIL oppRollupUtil = new RLLP_OppRollup_UTIL();
        objectList = database.query(oppRollupUtil.buildSoftCreditQuery());
            
        // invokes code to get the currency of npo02__CombinedRollupFieldset__c oppty
        opptyCurrencies = (RLLP_OppRollup_UTIL.isMultiCurrency() ? RLLP_OppRollup_UTIL.rcfFindCurrency(objectList) : null);
            
        for (sobject r : objectList) {
            id cid = (id)(r.get('ContactId'));
            if (cid != null){
                updateSoftCreditFromResult((sobject)(contactsToUpdate.get(cid)), r, opptyCurrencies);
            }                
        }                

        objectList.clear();
        objectList = database.query(oppRollupUtil.buildContactNDaySoftCreditQuery());
        
        for (sobject r : objectList){
            id cid = (id)(r.get('ContactId'));
            if (cid != null)
                updateSoftCreditRollupFromResultLastNDays((sobject)(contactsToUpdate.get(cid)), r);
        }

        for (id cid : cmap.keySet()) {
            contact c1 = cmap.get(cid);
            contact c2 = contactsToUpdate.get(cid);
            
            if (c1.npo02__Soft_Credit_This_Year__c == c2.npo02__Soft_Credit_This_Year__c &&
                c1.npo02__Soft_Credit_Last_Year__c == c2.npo02__Soft_Credit_Last_Year__c &&
                c1.npo02__Soft_Credit_Two_Years_Ago__c == c2.npo02__Soft_Credit_Two_Years_Ago__c && 
                c1.Soft_Credit_Last_N_Days__c == c2.Soft_Credit_Last_N_Days__c &&
                c1.npo02__Soft_Credit_Total__c == c2.npo02__Soft_Credit_Total__c)
                contactsToUpdate.remove(cid);
        }
        
        update contactsToUpdate.values();  
    }
    ********************************************************************************************************/
       
    /*******************************************************************************************************
    * @description Runs all batch opportunity rollups for all records on all objects.
    * @return void This method does updates directly and returns nothing.
    ********************************************************************************************************/
    public void rollupAll(){
    	
    	//clear the error queue from any previous runs
        delete [select id from Error__c where Context_Type__c = 'RLLP'];

        rollupAllAccounts();
        rollupAllContacts(); 
        rollupAllHouseholds();        
        
        if (RLLP_OppRollup_UTIL.useSoftCredits()){ 
            rollupAllSoftCredits();
        }            
    }
    /*******************************************************************************************************
    * @description Runs batch opportunity rollups for all accounts.
    * @return void This method does updates directly and returns nothing.
    ********************************************************************************************************/
    public void rollupAllAccounts() {
        RLLP_OppAccRollup_BATCH batch = new RLLP_OppAccRollup_BATCH();
        id batchProcessId = database.executeBatch(batch, RLLP_OppRollup_UTIL.BATCH_SIZE);        
    }
    /*******************************************************************************************************
    * @description Runs batch opportunity rollups for all contacts.
    * @return void This method does updates directly and returns nothing.
    ********************************************************************************************************/
    public void rollupAllContacts() { 
        RLLP_OppContactRollup_BATCH batch = new RLLP_OppContactRollup_BATCH();
        id batchProcessId = database.executeBatch(batch, RLLP_OppRollup_UTIL.BATCH_SIZE);        
    }
    /*******************************************************************************************************
    * @description Runs batch opportunity rollups for all households.
    * @return void This method does updates directly and returns nothing.
    ********************************************************************************************************/
    public void rollupAllHouseholds(){
        RLLP_OppHouseholdRollup_BATCH batch = new RLLP_OppHouseholdRollup_BATCH();
        id batchProcessId = database.executeBatch(batch, RLLP_OppRollup_UTIL.BATCH_SIZE);
    }
    /*******************************************************************************************************
    * @description Runs batch opportunity soft credit rollups for all contacts.
    * @return void This method does updates directly and returns nothing.
    ********************************************************************************************************/
    public void rollupAllSoftCredits(){
        RLLP_OppSoftCreditRollup_BATCH batch = new RLLP_OppSoftCreditRollup_BATCH();
        id batchProcessId = database.executeBatch(batch, RLLP_OppRollup_UTIL.BATCH_SIZE);
    }
    
    /*******************************************************************************************************
    * @description Method writes rollup fields from AggregateResults to an object needing rollups.
    * @param obj The object to roll up to.
    * @param r The aggregate result object.
    * @param opptyCurrencies Holds opportunity currency codes for multicurrency orgs.
    * @return void This method does updates directly and returns nothing.
    ********************************************************************************************************/
    public static void updateRollupFromResult(sobject obj, sobject r, map<id, Opportunity> opptyCurrencies) {
       // get the fiscal year, total amount, and opp count for this result row     
        integer fy = (integer)(r.get('CalendarYr'));
        decimal amt = (decimal)(r.get('TotalOppAmount'));
        integer cnt = (integer)(r.get('NumberOfClosedOpps'));               
        
        // split the special field to get the last opp id and amount
        string[] rcf = ((string)(r.get('RollupFieldset'))).split(';\\|;',-4);
        decimal lastAmt = (rcf.size() > 1 && rcf[1] != '') ? decimal.valueOf(rcf[1]) : null;
        
        // calculate amounts into correct currency if needed
        if(RLLP_OppRollup_UTIL.isMultiCurrency()){
            amt = RLLP_OppRollup_UTIL.ConvertFromCorporate((string)obj.get('CurrencyIsoCode'),amt);
            if(lastAmt != null && rcf.size() > 4 && rcf[4] != '')
                lastAmt = RLLP_OppRollup_UTIL.ConvertCurrency((string)opptyCurrencies.get((Id)rcf[4]).get('CurrencyISOCode'),(string)obj.get('CurrencyIsoCode'),lastAmt);           
        }
        
        // check if this is an annual total or account total
        if (fy != null){

            // put the fiscal year total in the right fields
            integer thisYear;
            if (RLLP_OppRollup_UTIL.useFiscalYear)
                thisYear = RLLP_OppRollup_UTIL.getCurrentFY();
            else
                thisYear = system.today().year();
            
            if (fy == thisYear){
                obj.put('npo02__OppAmountThisYear__c', amt); 
                obj.put('npo02__OppsClosedThisYear__c', cnt); 
            } 
            else if (fy == (thisYear - 1)){
                obj.put('npo02__OppAmountLastYear__c', amt); 
                obj.put('npo02__OppsClosedLastYear__c', cnt); 
            }
            else if (fy == (thisYear - 2) ){
                obj.put('npo02__OppAmount2YearsAgo__c', amt); 
                obj.put('npo02__OppsClosed2YearsAgo__c', cnt); 
            } 
                
        } 
        else { 
            // fill in summary totals
            obj.put('npo02__TotalOppAmount__c', amt);
            obj.put('npo02__NumberOfClosedOpps__c', cnt);               
            obj.put('npo02__LastOppAmount__c', lastAmt);
            obj.put('npo02__FirstCloseDate__c', r.get('FirstCloseDate')); //date
            obj.put('npo02__LastCloseDate__c', r.get('LastCloseDate')); //date
            
            // calculate amounts into correct currency if needed
            if(RLLP_OppRollup_UTIL.isMultiCurrency()){
               obj.put('npo02__AverageAmount__c', RLLP_OppRollup_UTIL.ConvertFromCorporate((string)obj.get('CurrencyIsoCode'),(decimal)r.get('AverageAmount'))); 
               obj.put('npo02__SmallestAmount__c', RLLP_OppRollup_UTIL.ConvertFromCorporate((string)obj.get('CurrencyIsoCode'),(decimal)r.get('SmallestAmount'))); 
               obj.put('npo02__LargestAmount__c', RLLP_OppRollup_UTIL.ConvertFromCorporate((string)obj.get('CurrencyIsoCode'),(decimal)r.get('LargestAmount'))); 
            }
            else{
               obj.put('npo02__AverageAmount__c', (decimal)(r.get('AverageAmount'))); 
               obj.put('npo02__SmallestAmount__c', (decimal)(r.get('SmallestAmount'))); 
               obj.put('npo02__LargestAmount__c', (decimal)(r.get('LargestAmount'))); 
            }
        }
        
        //deal with user rollups
        if (RLLP_OppRollup_UTIL.urfsMap.size() > 0){
            for (string s : RLLP_OppRollup_UTIL.urfsMap.keyset()){
                if (obj.getSObjectType().getDescribe().getName() == RLLP_OppRollup_UTIL.urfsMap.get(s).npo02__Object_Name__c){
                    npo02__User_Rollup_Field_Settings__c urfs = RLLP_OppRollup_UTIL.urfsMap.get(s);
                    string x = urfs.Name;                   
                    if (r.get(x) != null)
                       obj.put(urfs.npo02__Target_Field__c, r.get(x));
                }
            }
        } 
    }

    /*******************************************************************************************************
    * @description Method writes Last N Days rollup fields from AggregateResults to an object needing rollups.
    * @param obj The object to roll up to.
    * @param r The aggregate result object.
    * @return void This method does updates directly and returns nothing.
    ********************************************************************************************************/
    public static void updateRollupFromResultLastNDays(sobject obj, sobject r){
           
        // get the fiscal year, total amount, and opp count for this result row     
        decimal amt = (decimal)(r.get('TotalOppAmount'));
        integer cnt = (integer)(r.get('NumberOfClosedOpps'));               
        
        // calculate amounts into correct currency if needed
        if(RLLP_OppRollup_UTIL.isMultiCurrency())
           amt = RLLP_OppRollup_UTIL.ConvertFromCorporate((string)obj.get('CurrencyIsoCode'),amt);
        
        obj.put('npo02__OppAmountLastNDays__c', amt);               
        obj.put('npo02__OppsClosedLastNDays__c', cnt);
    }

    /*******************************************************************************************************
    * @description Method writes membership rollup fields from AggregateResults to an object needing rollups.
    * @param obj The object to roll up to.
    * @param r The aggregate result object.
    * @param opptyCurrencies Holds opportunity currency codes for multicurrency orgs.
    * @return void This method does updates directly and returns nothing.
    ********************************************************************************************************/
    public static void updateRollupFromResultMembership(sobject obj, sobject r, map<id, Opportunity> opptyCurrencies){
    // used for single and batch rollups, this maps query results to the right fields
        
        // get the fiscal year, total amount, and opp count for this result row
        decimal amt = (decimal)(r.get('TotalOppAmount'));
        integer cnt = (integer)(r.get('NumberOfClosedOpps'));               

        // split the special field to get the last opp id and amount
        string[] rcf = ((string)(r.get('RollupFieldset'))).split(';\\|;',-4);
        decimal lastAmt = (rcf.size() > 1 && rcf[1] != null) ? decimal.valueOf(rcf[1]) : null;
        string lastMemberLevel = (rcf.size() > 2) ? rcf[2] : null;
        string lastMemberOrigin = (rcf.size() > 3) ? rcf[3] : null;
        // calculate amounts into correct currency if needed
        if(RLLP_OppRollup_UTIL.isMultiCurrency()){
           amt = RLLP_OppRollup_UTIL.ConvertFromCorporate((string)obj.get('CurrencyIsoCode'),amt);
           if(lastAmt != null && rcf.size() > 4 && rcf[4] != '')
               lastAmt = RLLP_OppRollup_UTIL.ConvertCurrency((string)opptyCurrencies.get((Id)rcf[4]).get('CurrencyISOCode'),(string)obj.get('CurrencyIsoCode'),lastAmt);           
        }

        // fill in summary totals
        obj.put('npo02__TotalMembershipOppAmount__c', amt);
        obj.put('npo02__NumberOfMembershipOpps__c', cnt);               
        obj.put('npo02__LastMembershipDate__c', (date)(r.get('LastCloseDate')));                
        obj.put('npo02__LastMembershipAmount__c', lastAmt);                 
        obj.put('npo02__LastMembershipLevel__c', lastMemberLevel);              
        obj.put('npo02__LastMembershipOrigin__c', lastMemberOrigin);                
        obj.put('npo02__MembershipJoinDate__c', (date)(r.get('FirstStartDate'))); 
        obj.put('npo02__MembershipEndDate__c', (date)(r.get('LastEndDate')));
    }   
    
    /*******************************************************************************************************
    * @description Method writes soft credit rollup fields from AggregateResults to an object needing rollups.
    * @param obj The object to roll up to.
    * @param r The aggregate result object.
    * @param opptyCurrencies Holds opportunity currency codes for multicurrency orgs.
    * @return void This method does updates directly and returns nothing.
    public static void updateSoftCreditFromResult(sobject obj, sobject r, map<id, Opportunity> opptyCurrencies){
         // get the fiscal year, total amount, and opp count for this result row     
        integer fy = (integer)(r.get('CalendarYr'));
        decimal amt = (decimal)(r.get('TotalOppAmount'));                       
        
        // calculate amounts into correct currency if needed
        if(RLLP_OppRollup_UTIL.isMultiCurrency())
            amt = RLLP_OppRollup_UTIL.ConvertFromCorporate((string)obj.get('CurrencyIsoCode'),amt);
        
        // check if this is an annual total or account total
        if (fy != null){

            // put the fiscal year total in the right fields
            integer thisYear;
            if (RLLP_OppRollup_UTIL.useFiscalYear)
                thisYear = RLLP_OppRollup_UTIL.getCurrentFY();
            else
                thisYear = system.today().year();
            
            if (fy == thisYear)
                obj.put('npo02__Soft_Credit_This_Year__c', amt); 
                 
            else if (fy == (thisYear - 1))
                obj.put('npo02__Soft_Credit_Last_Year__c', amt); 
                 
            else if (fy == (thisYear - 2))
                obj.put('npo02__Soft_Credit_Two_Years_Ago__c', amt);    
        }
        else { 
            // fill in summary totals
            obj.put('npo02__Soft_Credit_Total__c', amt);        
        }
    }
    ********************************************************************************************************/

     /*******************************************************************************************************
    * @description Method writes soft credit last N days rollup fields from AggregateResults to an object 
    * needing rollups.
    * @param obj The object to roll up to.
    * @param r The aggregate result object.
    * @return void This method does updates directly and returns nothing.
   public static void updateSoftCreditRollupFromResultLastNDays(sobject obj, sobject r){
           
        // get the total amount for this result row     
        decimal amt = (decimal)(r.get('TotalOppAmount'));
        
        // calculate amounts into correct currency if needed
        if(RLLP_OppRollup_UTIL.isMultiCurrency())
           amt = RLLP_OppRollup_UTIL.ConvertFromCorporate((string)obj.get('CurrencyIsoCode'),amt);
        
        obj.put('Soft_Credit_Last_N_Days__c', amt);               
    }
    ********************************************************************************************************/
}