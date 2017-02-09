/*  
    Copyright (c) 2012, Salesforce.org
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
* @date 2012 (2.4) 
* @description set of utility methods and values used in opportunity rollups
*/
public without sharing class RLLP_OppRollup_UTIL {

    /*** Settings ***/

    /** @description Set to true if fiscal year should be used. */    
    public static boolean useFiscalYear = false;
    /** @description Set of Opportunity record types to exclue from Account Rollups. */
    public static set<id> recordTypesToExcludeAccts = new set<id>();
    /** @description Set of Opportunity record types to exclue from Contact Rollups. */
    public static set<id> recordTypesToExcludeCons = new set<id>();
    /** @description Set of Opportunity Membership record type Ids. */
    public static set<id> mbrRecordTypes = new set<id>(); 
    /** @description Set of Opportunity record type names to exclue from Account Rollups. */
    public static set<string> oppTypesToExcludeAccts = new set<string>();
    /** @description Set of Opportunity record type names to exclue from Contract Rollups. */
    public static set<string> oppTypesToExcludeCons = new set<string>();
    /** @description Set of Roles to use for Soft Credit Rollups. */
    public static set<string> softCreditRoles = new set<string>();
    /** @description Household Custom Settings. */
    public static npo02__Households_Settings__c hh_settings;
    /** @description Batch size defaults to 200 if not set in Settings. */
    public static integer BATCH_SIZE = 200;
    /** @description Set to true if record types are enabled for opportunities. */
    private static boolean recordTypesOnOpps;
    /** @description Set to true if soft credits are used. */
    private static boolean useSoftCredits;
    /** @description Set of Account Ids of Individual Accounts. */
    private static set<id> IndividualAccountIds;
    /** @description Current Fiscal Year. */
    private static integer fiscal_year;
    
    /*** Multicurrency ***/
    /** @description set when multicurrency check is called, holds the field string/sobject mapping. */
    public static Map<String, SObjectField> mcFieldValues = new Map<String, SObjectField>();
    /** @description Determines if multi-currency is enabled. */
    public static boolean multiCurrency;
    /** @description map of exchange rates. */
    static map<string, decimal> exchangeRates = new map<string,decimal>();
    /** @description Coproate Currency */
    public static string currCorporate; 
    
    /*** User Rollups ***/
    /** @description map of object and rollup field. */
    public static map<string, string> objectRollUpFieldMap;
    /** @description User Rollup Field Custom Settings. */
    public static map<string, npo02__User_Rollup_Field_Settings__c> urfsMap = new map<string, npo02__User_Rollup_Field_Settings__c>();
    
    /*** Query Strings ***/
    /** @description Holds the Account Query String. */
    private static string AccountQuery;
    /** @description Holds the Contact Query String. */
    private static string ContactQuery;
    /** @description Holds the Household Query String. */
    private static string HouseholdQuery;
    /** @description Holds the Opportunity Contact Role Query String. */
    private static string OpportunityContactRoleQuery;
    /** @description Holds the Household Rollup Query String. */
    private static string HouseholdRollupQuery;
    /** @description Holds the Contact Rollup Query String. */
    private static string ContactRollupQuery;
    /** @description Holds the Account Rollup Query String. */
    private static string AccountRollupQuery;
    /** @description Holds the Household NDay Rollup Query String. */
    private static string HouseholdNDayQuery;
    /** @description Holds the Contact NDay Rollup Query String. */
    private static string ContactNDayQuery;
    /** @description Holds the Account NDay Rollup Query String. */
    private static string AccountNDayQuery;
    /** @description Holds the Household Membership Rollup Query String. */
    private static string HouseholdMembershipQuery;
    /** @description Holds the Contact Membership Rollup Query String. */
    private static string ContactMembershipQuery;
    /** @description Holds the Account Membership Rollup Query String. */
    private static string AccountMembershipQuery;
    /** @description Holds the Soft Credit Opportunity Contact Role Rollup Query String. */
    private static string SoftCreditOCRQuery;
    /** @description Holds the Soft Credit Partial Soft Credit Rollup Query String. */
    private static string SoftCreditPSCQuery;
    //private static string ContactNDaySoftCreditQuery;
         
    /*** Field Lists ***/
    /** @description List of Account Fields to use in Account Queries. */
    private static set<string> AccountFields = new set<string>{'npo02__TotalOppAmount__c', 'npo02__OppsClosedThisYear__c', 'npo02__OppsClosedLastYear__c', 
                                                        'npo02__OppsClosedLastNDays__c', 'npo02__OppsClosed2YearsAgo__c', 'npo02__OppAmountLastYear__c', 
                                                        'npo02__OppAmount2YearsAgo__c', 'npo02__OppAmountThisYear__c', 'npo02__OppAmountLastNDays__c', 
                                                        'npo02__FirstCloseDate__c', 'npo02__LastCloseDate__c', 'npo02__TotalMembershipOppAmount__c', 
                                                        'npo02__LastMembershipLevel__c', 'npo02__LastMembershipDate__c', 'npo02__MembershipJoinDate__c', 
                                                        'npo02__MembershipEndDate__c', 'npo02__Best_Gift_Year__c', 'npo02__Best_Gift_Year_Total__c', 'npo02__LastMembershipOrigin__c',
                                                        'npo02__AverageAmount__c', 'npo02__LargestAmount__c', 'npo02__SmallestAmount__c', 'npo02__LastMembershipAmount__c'
                                                        };
    /** @description List of Opportunity Contact Role Fields to use in OCR Queries. */
    private static set<string> OpportunityContactRoleFields = new set<string>{'ContactId', 'Contact.npo02__household__c', 
                                                        'Contact.npo02__AverageAmount__c', 'Contact.npo02__household__r.npo02__AverageAmount__c', 
                                                        'Contact.npo02__Best_Gift_Year__c', 'Contact.npo02__household__r.npo02__Best_Gift_Year__c', 
                                                        'Contact.npo02__Best_Gift_Year_Total__c', 'Contact.npo02__household__r.npo02__Best_Gift_Year_Total__c', 
                                                        'Contact.npo02__FirstCloseDate__c', 'Contact.npo02__household__r.npo02__FirstCloseDate__c', 
                                                        'Contact.npo02__LargestAmount__c', 'Contact.npo02__household__r.npo02__LargestAmount__c', 
                                                        'Contact.npo02__LastCloseDate__c', 'Contact.npo02__household__r.npo02__LastCloseDate__c', 
                                                        'Contact.npo02__LastMembershipDate__c', 'Contact.npo02__household__r.npo02__LastMembershipDate__c', 
                                                        'Contact.npo02__LastMembershipLevel__c', 'Contact.npo02__Household__r.npo02__LastMembershipLevel__c', 
                                                        'Contact.npo02__LastOppAmount__c', 'Contact.npo02__Household__r.npo02__LastOppAmount__c', 
                                                        'Contact.npo02__MembershipEndDate__c', 'Contact.npo02__household__r.npo02__MembershipEndDate__c', 
                                                        'Contact.npo02__MembershipJoinDate__c', 'Contact.npo02__household__r.npo02__MembershipJoinDate__c', 
                                                        'Contact.npo02__NumberOfClosedOpps__c', 'Contact.npo02__household__r.npo02__NumberOfClosedOpps__c', 
                                                        'Contact.npo02__OppAmount2YearsAgo__c', 'Contact.npo02__household__r.npo02__OppAmount2YearsAgo__c', 
                                                        'Contact.npo02__OppAmountLastNDays__c', 'Contact.npo02__household__r.npo02__OppAmountLastNDays__c', 
                                                        'Contact.npo02__OppAmountLastYear__c', 'Contact.npo02__household__r.npo02__OppAmountLastYear__c', 
                                                        'Contact.npo02__OppAmountThisYear__c', 'Contact.npo02__household__r.npo02__OppAmountThisYear__c', 
                                                        'Contact.npo02__OppsClosedThisYear__c', 'Contact.npo02__household__r.npo02__OppsClosedThisYear__c', 
                                                        'Contact.npo02__OppsClosed2YearsAgo__c', 'Contact.npo02__household__r.npo02__OppsClosed2YearsAgo__c', 
                                                        'Contact.npo02__OppsClosedLastNDays__c', 'Contact.npo02__household__r.npo02__OppsClosedLastNDays__c', 
                                                        'Contact.npo02__OppsClosedLastYear__c', 'Contact.npo02__household__r.npo02__OppsClosedLastYear__c', 
                                                        'Contact.npo02__SmallestAmount__c', 'Contact.npo02__household__r.npo02__SmallestAmount__c', 
                                                        'Contact.npo02__TotalMembershipOppAmount__c', 'Contact.npo02__household__r.npo02__TotalMembershipOppAmount__c', 
                                                        'Contact.npo02__TotalOppAmount__c', 'Contact.npo02__household__r.npo02__TotalOppAmount__c'
                                                        }; 
    /** @description List of Contact Fields to use in Contact Queries. */   
    private static set<string> ContactFields = new set<string>{'npo02__TotalOppAmount__c', 'npo02__OppsClosedThisYear__c', 'npo02__OppsClosedLastYear__c', 
                                                        'npo02__OppsClosedLastNDays__c', 'npo02__OppsClosed2YearsAgo__c', 'npo02__OppAmountLastYear__c', 
                                                        'npo02__OppAmount2YearsAgo__c', 'npo02__OppAmountThisYear__c', 'npo02__OppAmountLastNDays__c', 
                                                        'npo02__FirstCloseDate__c', 'npo02__LastCloseDate__c', 'npo02__TotalMembershipOppAmount__c', 
                                                        'npo02__LastMembershipLevel__c', 'npo02__LastMembershipDate__c', 'npo02__MembershipJoinDate__c', 
                                                        'npo02__MembershipEndDate__c', 'npo02__Best_Gift_Year__c', 'npo02__Best_Gift_Year_Total__c', 
                                                        'npo02__AverageAmount__c', 'npo02__LargestAmount__c', 'npo02__SmallestAmount__c', 
                                                        'npo02__Soft_Credit_Two_Years_Ago__c', 'npo02__Soft_Credit_Total__c', 'Soft_Credit_Last_N_Days__c',
                                                        'npo02__Soft_Credit_Last_Year__c', 'npo02__Best_Gift_Year__c', 'npo02__Best_Gift_Year_Total__c',
                                                        'First_Soft_Credit_Amount__c', 'First_Soft_Credit_Date__c', 
                                                        'Largest_Soft_Credit_Amount__c', 'Largest_Soft_Credit_Date__c',
                                                        'Last_Soft_Credit_Amount__c', 'Last_Soft_Credit_Date__c',
                                                        'Number_of_Soft_Credits__c', 'Number_of_Soft_Credits_Last_N_Days__c',
                                                        'Number_of_Soft_Credits_Last_Year__c', 'Number_of_Soft_Credits_This_Year__c', 
                                                        'Number_of_Soft_Credits_Two_Years_Ago__c', 'Soft_Credit_Last_N_Days__c',                                                     
                                                        'npo02__Household__c', 'npo02__Soft_Credit_This_Year__c'
                                                        };
    /** @description List of Household Fields to use in Household Queries. */
    private static set<string> HouseholdFields = new set<string>{'npo02__TotalOppAmount__c', 'npo02__OppsClosedThisYear__c', 'npo02__OppsClosedLastYear__c', 
                                                        'npo02__OppsClosedLastNDays__c', 'npo02__OppsClosed2YearsAgo__c', 'npo02__OppAmountLastYear__c', 
                                                        'npo02__OppAmount2YearsAgo__c', 'npo02__OppAmountThisYear__c', 'npo02__OppAmountLastNDays__c', 
                                                        'npo02__FirstCloseDate__c', 'npo02__LastCloseDate__c', 'npo02__TotalMembershipOppAmount__c', 
                                                        'npo02__LastMembershipLevel__c', 'npo02__LastMembershipDate__c', 'npo02__MembershipJoinDate__c', 
                                                        'npo02__MembershipEndDate__c', 'npo02__Best_Gift_Year__c', 'npo02__Best_Gift_Year_Total__c', 
                                                        'npo02__AverageAmount__c', 'npo02__LargestAmount__c', 'npo02__SmallestAmount__c'
                                                        };

    /*********************************************************************************************************
    * @description Constructor runs setupRollupVars and setupUserRollupVars.
    */
	public RLLP_OppRollup_UTIL(){
	    // setup households settings
	    setupRollupVars(UTIL_CustomSettingsFacade.getHouseholdsSettings());
	    // setup user rollups
	    setupUserRollupVars();
	}
    /*********************************************************************************************************
    * @description Configures and sets up static variables for OppRollups settings based on custom settings.
    * @param household_settings custom household settings.
    */
    public static void setupRollupVars(npo02__Households_Settings__c household_settings){
        
        if (household_settings.npo02__Excluded_Contact_Opp_Rectypes__c != null) {
            recordTypesToExcludeCons = new Set<Id>((List<Id>)household_settings.npo02__Excluded_Contact_Opp_Rectypes__c.split(';'));
        }
        if (household_settings.npo02__Excluded_Account_Opp_Rectypes__c != null) {
            recordTypesToExcludeAccts = new Set<Id>((List<Id>)household_settings.npo02__Excluded_Account_Opp_Rectypes__c.split(';'));
        }
        if (household_settings.npo02__Excluded_Contact_Opp_Types__c != null) {
            oppTypesToExcludeCons = new set<string>(household_settings.npo02__Excluded_Contact_Opp_Types__c.split(';'));
        }
        if (household_settings.npo02__Excluded_Account_Opp_Types__c != null) {
            oppTypesToExcludeAccts = new set<string>(household_settings.npo02__Excluded_Account_Opp_Types__c.split(';'));
        }    
        if (household_settings.npo02__Membership_Record_Types__c != null) {
            mbrRecordTypes = new Set<Id>((List<Id>)household_settings.npo02__Membership_Record_Types__c.split(';'));
        }  
        if (household_settings.npo02__Soft_Credit_Roles__c != null){
            softCreditRoles = new set<string>(household_Settings.npo02__Soft_Credit_Roles__c.split(';'));
        }
        
        if (household_settings.npo02__Rollup_N_Day_Value__c == null){
            household_settings.npo02__Rollup_N_Day_Value__c = 365;
        }
        //check the settings, if they have the fiscal year set
        //we still need to query to make sure its not a custom fiscal year
        //or else we'll throw errors when calling Fiscal_Year in queries
        if (household_settings.npo02__Use_Fiscal_Year_for_Rollups__c == true){
            String fyQuery = 'select id from FiscalYearSettings  where IsStandardYear = false';            
            List<SObject> fySettings = new List<SObject>();
            fySettings = Database.query(fyQuery);
            integer hasCustomFYRecord = fySettings.size();
                
            //if org has customFY records, disable FY settings
            if (hasCustomFYRecord > 0 && !household_settings.npo02__Force_Fiscal_Year__c)
                useFiscalYear = false;
            else
                useFiscalYear = true;
        }        
        
        if (household_settings.npo02__Batch_Size__c != null)
            BATCH_SIZE = household_settings.npo02__Batch_Size__c.intValue();
        
        //set the static var
        hh_settings = household_settings;
    }
    /*********************************************************************************************************
    * @description Configures and sets up static variables for User Rollup Field Settings.
    */
    public static void setupUserRollupVars(){
        if (objectRollupFieldMap == null){
            urfsMap = UTIL_ListCustomSettingsFacade.getMapUserRollupFieldSettings().clone();
            objectRollupFieldMap = new map<string, string>();
        
            //build the object delineated query string for use later
            for (string s : urfsMap.keySet()){
               npo02__User_Rollup_Field_Settings__c urfs = urfsMap.get(s);
            
               if (objectRollUpFieldMap.containsKey(urfs.npo02__Object_Name__c)){
                  String qString = objectRollupFieldMap.get(urfs.npo02__Object_Name__c);
                  qString += urfs.npo02__Field_Action__c + '(Opportunity.' + urfs.npo02__Source_Field__c + ') ' + urfs.Name + ', ';
                  objectRollupFieldMap.put(urfs.npo02__Object_Name__c, qString);
               }
               else{
                  objectRollupFieldMap.put(urfs.npo02__Object_Name__c, urfs.npo02__Field_Action__c + '(Opportunity.' + urfs.npo02__Source_Field__c + ') ' + urfs.Name + ', ');
               }
               
               if (urfs.npo02__Object_Name__c == npo02__Household__c.getSObjectType().getDescribe().getName()){
                   RLLP_OppRollup_UTIL.HouseholdFields.add(urfs.npo02__Target_Field__c);              
                   RLLP_OppRollup_UTIL.OpportunityContactRoleFields.add('Contact.npo02__household__r.' + urfs.npo02__Target_Field__c);   
               }
               else if (urfs.npo02__Object_Name__c == Contact.getSObjectType().getDescribe().getName()){
                   RLLP_OppRollup_UTIL.ContactFields.add(urfs.npo02__Target_Field__c);            
                   RLLP_OppRollup_UTIL.OpportunityContactRoleFields.add('Contact.' + urfs.npo02__Target_Field__c);
               }
               else if (urfs.npo02__Object_Name__c == Account.getSObjectType().getDescribe().getName()){
                   RLLP_OppRollup_UTIL.AccountFields.add(urfs.npo02__Target_Field__c);             
               }               
            }
        }
    }
    /*********************************************************************************************************
    * @description Determines if record types are enabled on opportunities.
    * @return Boolean representing whether or not the record types are enabled.
    */
    public static boolean areRecordTypesOnOpps(){
        if (recordTypesOnOpps==null){
            String giftRt = UTIL_RecordTypes.getRecordTypeNameForGiftsTests(Opportunity.sObjectType);
            if (giftRt!=null&&giftRt!=''){
                recordTypesOnOpps = true;
            } 
            else{
                recordTypesOnOpps = false;  
            }
        }
        return recordTypesOnOpps;
    }
    /*********************************************************************************************************
    * @description Determines if Soft Credit Rollups are enabled.
    * @return Boolean representing whether or not the soft credits are enabled.
    */
    public static boolean useSoftCredits(){
        if (useSoftCredits == null){
            if(!softCreditRoles.isEmpty() && hh_settings.npo02__Enable_Soft_Credit_Rollups__c)
                useSoftCredits = true;
            else
                useSoftCredits = false;
        }       
        return useSoftCredits;
    }  
    /*********************************************************************************************************
    * @description Retrieves the Individual Account Ids when available.
    * @return Set<Id> Set of Individual Account Ids retrieved.
    */
    public static set<id> getIndividualAccountIds(){
        if (IndividualAccountIds == null){         
            IndividualAccountIds = new set<id>();                       
            if (CAO_Constants.getIndividualAccountId() != null)
                IndividualAccountIds.add(CAO_Constants.getIndividualAccountId());        
        }
        return IndividualAccountIds;        
    }    
    
    /****************************************/
    
    /*** Multicurrency Support Methods ***/
    
    /*********************************************************************************************************
    * @description Determines if multi-currency is enabled for the org.
    * @return Boolean representing whether or not multi-currency is enabled.
    */
    public static boolean isMultiCurrency(){                    
        if (multiCurrency==null){
            //create currency field handles for later dynamic dml
            mcFieldValues.put('Opportunity', Schema.sObjectType.Opportunity.fields.getMap().get('CurrencyIsoCode'));
            mcFieldValues.put('Account', Schema.sObjectType.Account.fields.getMap().get('CurrencyIsoCode'));
            mcFieldValues.put('Contact', Schema.sObjectType.Contact.fields.getMap().get('CurrencyIsoCode'));
            mcFieldValues.put('npo02__Household__c', Schema.sObjectType.npo02__Household__c.fields.getMap().get('CurrencyIsoCode'));

            String queryExchangeRates = 'select IsoCode,ConversionRate,IsCorporate from CurrencyType';
            SObjectType soCurrencyType = Schema.getGlobalDescribe().get('CurrencyType'); 

            //are we in a multi-currency org?
            if (mcFieldValues.get('Opportunity') != null && mcFieldValues.get('Account') != null && 
                mcFieldValues.get('npo02__Household__c') != null && mcFieldValues.get('Contact') != null && soCurrencyType != null){
                multiCurrency = true;
 
                map<string,SObjectField> ctFields = soCurrencyType.getDescribe().fields.getMap();
                SObjectField ctIsoCode = ctFields.get('IsoCode');
                SObjectField ctConversionRate = ctFields.get('ConversionRate');
                SObjectField ctCorporate = ctFields.get('IsCorporate');

                //iterate over all the currencies in the org (inc. inactive ones as they may have been used previously)
                //this allows us to put them in a map and use the ISO code as key and also set the corporate
                for(sObject ct: Database.query(queryExchangeRates)){
                    exchangeRates.put((string)ct.get(ctIsoCode),(decimal)ct.get(ctConversionRate));
                    if((boolean)ct.get(ctCorporate))currCorporate=(string)ct.get(ctIsoCode);
                }
            }
            else
                multiCurrency = false; 
        }        
        return multiCurrency;
    }
    /*********************************************************************************************************
    * @description Converts the amount from corporate currency to the currency specified by the ISO code.
    * @param ISO The ISO code.
    * @param amt The amount to convert.
    * @return Decimal The converted amount.
    */
    public static decimal convertFromCorporate(String ISO,Decimal amt){
        if(currCorporate == ISO || currCorporate == null) // no need to convert
            return amt;
        else //return value to 2DP
            return (amt * exchangeRates.get(ISO)).setScale(2,System.RoundingMode.UP);
    }
    /*********************************************************************************************************
    * @description Converts the amount from an ISO code to the currency of the specified ISO code.
    * @param fromISO The ISO code to convert from.
    * @param toISO The ISO code to convert to.
    * @param amt The amount to convert.
    * @return Decimal The converted amount.
    */
    public static decimal convertCurrency(String fromISO, String toISO, Decimal amt){
        if(fromISO == toISO) // no need to convert
            return amt;
        else //return value to 2DP
            return ((amt / exchangeRates.get(fromISO)) * exchangeRates.get(toISO)).setScale(2,System.RoundingMode.UP);
    }
    /*********************************************************************************************************
    * @description Retrieves the Opportunities CurrencyIsoCode related to the objects passed in
    * @param objectList The list of obects with rollup field sets to obtain the Id from.
    * @return Map<Id, Opportunity> The map of the Ids and the associated oppty.
    */
    public static map<Id,Opportunity> rcfFindCurrency(list<sobject> objectList){
        //new set to hold the opptys we need to query for
        set<Id> opptysForCurrency = new set<Id>();

        //iterate over objects provided to us
        for (sobject r : objectList){
            //pull Id from rollup field and add to set
            string[] rcf = ((string)(r.get('RollupFieldset'))).split(';\\|;',-4);
            if(rcf.size() > 1 && rcf[1] != '')
                opptysForCurrency.add((Id)rcf[4]);
        }
        
        if (!objectlist.isEmpty()){
            //query for oppty and currencycode for all Ids in set.
            string soqlStatement = 'SELECT Id,CurrencyIsoCode FROM Opportunity WHERE Id in :opptysForCurrency';
            map<Id,Opportunity> opptyCurrencies = new map<Id,Opportunity>((list<Opportunity>)Database.query(soqlStatement));
            return opptyCurrencies;
        }
        else
            return null; 
    }
    /****************************************/
    
    /*** Opportunity Evaluation Methods ***/
    
    /*********************************************************************************************************
    * @description evaluates a single Opportunity to see if it needs to be rolled up
    * @param o The opportunity to evaluate.
    * @return Boolean representing whether or not a rollup is needed.
    */  
    public static boolean oppForRollup(Opportunity o){
        boolean needsRollup = false;
        if (o.isWon && o.Amount != null)
           needsRollup = true;
        return needsRollup;
    }
    /*********************************************************************************************************
    * @description evaluates if an Opp will need a contact-based rollup
    * @param o The opportunity to evaluate.
    * @return Boolean representing whether or not a rollup is needed.
    */ 
    public static boolean needsContactRollup(Opportunity o){
        boolean needsRollup = false;
        //if its from an individual account or has a null account ID OR alwaysRollupToPrimary is set AND
        //its the membership rectype or the rectype isn't exclused AND
        //the opp type isn't excluded        
        if (((o.npe01__Is_Opp_From_Individual__c == 'true' || o.accountid == null) || hh_settings.npo02__Always_Rollup_to_Primary_Contact__c) &&
                (mbrRecordTypes.contains((id)o.get('recordTypeId')) || !recordTypesToExcludeCons.contains((id)o.get('recordTypeId'))) &&
                (!oppTypesToExcludeCons.contains(o.type))){
            needsRollup = true;
        }
        return needsRollup;
    }
    /*********************************************************************************************************
    * @description evaluates if an Opp belongs to a valid Account needing a rollup
    * @param o The opportunity to evaluate.
    * @return Boolean representing whether or not a rollup is needed.
    */ 
    public static boolean needsAccountRollup(Opportunity o){
        boolean needsRollup = false;    
        //if the account id is not null and its not the individual/bucket account AND
        //its the membership rectype or the rectype isn't exclused AND
        //the opp type isn't excluded
        if (o.AccountId != null && !getIndividualAccountIds().contains(o.AccountId) && 
        (mbrRecordTypes.contains((id)o.get('recordTypeId')) || !recordTypesToExcludeAccts.contains((id)o.get('recordTypeId'))) && 
        !oppTypesToExcludeAccts.contains(o.type)){
            needsRollup = true;                
        }                                    
        return needsRollup;
    }
    /*********************************************************************************************************
    * @description evaluates two opps for changes that would require a rollup
    * @param newOpp The opportunity after update to evaluate.
    * @param oldOPP The opportunity before update to evaluate.
    * @return Boolean representing whether or not a rollup is needed.
    */
    public static boolean oppForRollup (Opportunity newOpp, Opportunity oldOpp){
        boolean needsRollup = false;          
        //if the iswon stage has changed, or its iswon and the amount has changed
        //or the recordtype has changed, or the type has changed, or the account id has changed
        //or its won and any of the membership fields have changed
        if (newOpp.isWon != oldOpp.isWon ||
            (newOpp.isWon && (newOpp.Amount != oldOpp.Amount)) ||
            (id)newOpp.get('recordTypeId') != (id)oldOpp.get('recordTypeId') ||                                            
            newOpp.type != oldOpp.type ||
            newOpp.closeDate != oldOpp.closeDate ||
            newOpp.accountId != oldOpp.accountId ||
            (newOpp.isWon && (newOpp.npe01__Membership_Start_Date__c != oldOpp.npe01__Membership_Start_Date__c || 
                              newOpp.npe01__Membership_End_Date__c != oldOpp.npe01__Membership_End_Date__c ||                                            
                              newOpp.npe01__Member_Level__c != oldOpp.npe01__Member_Level__c))){
            needsRollup = true;                                      
        }        
        return needsRollup;
    }    
    
    /****************************************/
    
    /*** Query Building Methods ***/    
    
    /*********************************************************************************************************
    * @description Builds the query string for Account Rollups.
    * @return String The resulting Account Query.
    */
    public string buildAccountQuery(){
        if (AccountQuery == null){
            AccountQuery = 'select id';
            for (string s : AccountFields){
                AccountQuery += ', ' + s;
            }           
            //multicurrency?
            AccountQuery += (isMultiCurrency() ? ', CurrencyIsoCode ' : '');  
            AccountQuery += ' from Account ';
        }
        return AccountQuery;
    }
    /*********************************************************************************************************
    * @description Builds the query string for Opportunity Contact Roll Rollups.
    * @return String The resulting Opportunity Contact Role Query.
    */
    public string buildOpportunityContactRoleQuery(){
        if (OpportunityContactRoleQuery == null){
            OpportunityContactRoleQuery = 'select id';
            for (string s : OpportunityContactRoleFields){
                OpportunityContactRoleQuery += ', ' + s;
            }           
            OpportunityContactRoleQuery += (isMultiCurrency() ? ', Contact.CurrencyIsoCode, Contact.npo02__Household__r.CurrencyIsoCode ' : ''); 
            OpportunityContactRoleQuery += ' from OpportunityContactRole ';
        }
        return OpportunityContactRoleQuery;     
    }
    /*********************************************************************************************************
    * @description Builds the query string for Contact Rollups.
    * @return String The resulting Contact Query.
    */
    public string buildContactQuery(){    
        if (ContactQuery == null){
            ContactQuery = 'select id';
            for (string s : ContactFields){
                ContactQuery += ', ' + s;               
            }   
            ContactQuery += (isMultiCurrency() ? ', CurrencyIsoCode ' : '');  
            ContactQuery += ' from Contact ';           
        }    
        return ContactQuery;
    }
    /*********************************************************************************************************
    * @description Builds the query string for Household Rollups.
    * @return String The resulting Household Query.
    */
    public string buildHouseholdQuery(){
        if (HouseholdQuery == null){
            HouseholdQuery = 'select id';
            for (string s : HouseholdFields){
                HouseholdQuery += ', ' + s;               
            }   
            HouseholdQuery += (isMultiCurrency() ? ', CurrencyIsoCode ' : '');  
            HouseholdQuery += ' from npo02__Household__c ';           
        }
        return HouseholdQuery;    
    }
    /*********************************************************************************************************
    * @description Builds the query string for the Account Rollups.
    * @return String The resulting Account Rollup Query.
    */
    public string buildAccountRollupQuery(){
        if (AccountRollupQuery == null){
            if (useFiscalYear)
                AccountRollupQuery = 'SELECT AccountId, Fiscal_Year(CloseDate) CalendarYr, ';
            else
                AccountRollupQuery = 'SELECT AccountId, Calendar_Year(CloseDate) CalendarYr, ';        
            AccountRollupQuery += 'SUM(Amount) TotalOppAmount, AVG(Amount) AverageAmount, MIN(Amount) SmallestAmount, ';
            AccountRollupQuery += 'MAX(Amount) LargestAmount, MIN(CloseDate) FirstCloseDate, MAX(CloseDate) LastCloseDate, ';
            if (objectRollupFieldMap.containsKey('Account'))
                AccountRollupQuery += objectRollupFieldMap.get('Account');        
            AccountRollupQuery += ' COUNT_DISTINCT(Id) NumberOfClosedOpps, MAX(npo02__CombinedRollupFieldset__c) RollupFieldset ';
            AccountRollupQuery += 'FROM Opportunity ';
            AccountRollupQuery += 'WHERE isWon=true '; 
            AccountRollupQuery += 'AND Amount != null '; 
            if (!getIndividualAccountIds().isEmpty()){
                AccountRollupQuery += 'AND AccountID NOT IN (';
                for (id i : getIndividualAccountIds()){                 
                    AccountRollupQuery += '\'' + i + '\',';
                }
                //drop the trailing comma
                AccountRollupQuery = AccountRollupQuery.substring(0, AccountRollupQuery.length() - 1);
                AccountRollupQuery += ') ';
            }         
            if(areRecordTypesOnOpps()){
                AccountRollupQuery += 'AND RecordTypeId NOT IN :rectypeset ';
            }
            AccountRollupQuery += 'AND Type NOT IN :typeset ';
            AccountRollupQuery += 'AND AccountId IN :aset ';
            if (useFiscalYear){
                AccountRollupQuery += 'GROUP BY ROLLUP (AccountId, Fiscal_Year(CloseDate)) ';                
            }
            else{
                AccountRollupQuery += 'GROUP BY ROLLUP (AccountId, Calendar_Year(CloseDate)) ';                
            }
            AccountRollupQuery += ' HAVING AccountId != null ';
        }
        return AccountRollupQuery;
    }
    /*********************************************************************************************************
    * @description Builds the query string for the Account NDay Rollups.
    * @return String The resulting Account NDay Query.
    */
    public string buildAccountNDayQuery(){
        if (AccountNDayQuery == null){
            AccountNDayQuery = 'SELECT AccountId, ';
            AccountNDayQuery += 'SUM(Amount) TotalOppAmount, COUNT_DISTINCT(Id) NumberOfClosedOpps '; 
            AccountNDayQuery += 'FROM Opportunity  '; 
            AccountNDayQuery += 'WHERE isWon=true  '; 
            AccountNDayQuery += 'AND Amount != null '; 
            if (!getIndividualAccountIds().isEmpty()){
                AccountRollupQuery += 'AND AccountID NOT IN (';
                for (id i : getIndividualAccountIds()){                 
                    AccountRollupQuery += '\'' + i + '\',';
                }
                //drop the trailing comma
                AccountRollupQuery = AccountRollupQuery.substring(0, AccountRollupQuery.length() - 1);
                AccountRollupQuery += ') ';
            }   
            if(areRecordTypesOnOpps()){ 
                AccountNDayQuery += 'AND RecordTypeId NOT IN :rectypeset '; 
            }
            AccountNDayQuery += 'AND Type NOT IN :typeset '; 
            AccountNDayQuery += 'AND AccountId IN :aset '; 
            AccountNDayQuery += 'AND closeDate >= LAST_N_DAYS:' + hh_settings.npo02__Rollup_N_Day_Value__c.intValue() + ' '; 
            AccountNDayQuery += 'GROUP BY AccountId '; 
            AccountNDayQuery += 'HAVING AccountId != null ';
        }       
        return AccountNDayQuery;
    }
    /*********************************************************************************************************
    * @description Builds the query string for the Account Membership Rollups.
    * @return String The resulting Account Membership Query.
    */
    public string buildAccountMembershipQuery(){
        if (AccountMembershipQuery == null){
            AccountMembershipQuery = 'SELECT AccountId, ';
            AccountMembershipQuery += 'SUM(Amount) TotalOppAmount, '; 
            AccountMembershipQuery += 'MAX(CloseDate) LastCloseDate, ';
            AccountMembershipQuery += 'MIN(npe01__membership_start_date__c) FirstStartDate, MAX(npe01__membership_end_date__c) LastEndDate,  ';
            AccountMembershipQuery += 'COUNT_DISTINCT(Id) NumberOfClosedOpps, MAX(npo02__CombinedRollupFieldset__c) RollupFieldset  ';
            AccountMembershipQuery += 'FROM Opportunity ';
            AccountMembershipQuery += 'WHERE isWon=true  ';
            AccountMembershipQuery += 'AND Amount != null  ';         
            if (!getIndividualAccountIds().isEmpty()){
                AccountRollupQuery += 'AND AccountID NOT IN (';
                for (id i : getIndividualAccountIds()){                 
                    AccountRollupQuery += '\'' + i + '\',';
                }
                //drop the trailing comma
                AccountRollupQuery = AccountRollupQuery.substring(0, AccountRollupQuery.length()-1);
                AccountRollupQuery += ') ';
            }             
            if(areRecordTypesOnOpps()){
                AccountMembershipQuery += 'AND RecordTypeId IN :memberset ';
            }
            AccountMembershipQuery += 'AND Type NOT IN :typeset ';
            AccountMembershipQuery += 'AND AccountId IN :aset ';            
            if (useFiscalYear)
                AccountMembershipQuery += 'GROUP BY ROLLUP (AccountId, Fiscal_Year(closeDate)) ';
            else
                AccountMembershipQuery += 'GROUP BY ROLLUP (AccountId, Calendar_Year(closeDate)) ';
            AccountMembershipQuery += 'HAVING AccountId != null ';
        }
        return AccountMembershipQuery;
    }
    /*********************************************************************************************************
    * @description Builds the query string for the Contact Rollups.
    * @return String The resulting Contact Rollup Query.
    */
    public string buildContactRollupQuery(){
        if (ContactRollupQuery == null){
            if (useFiscalYear)
                ContactRollupQuery = 'SELECT ContactId, Fiscal_Year(Opportunity.CloseDate) CalendarYr, ';
            else
                ContactRollupQuery = 'SELECT ContactId, Calendar_Year(Opportunity.CloseDate) CalendarYr, ';            
            ContactRollupQuery += 'SUM(Opportunity.Amount) TotalOppAmount, ';                        
            ContactRollupQuery += 'AVG(Opportunity.Amount) AverageAmount, MIN(Opportunity.Amount) SmallestAmount, ';
            ContactRollupQuery += 'MAX(Opportunity.Amount) LargestAmount, MIN(Opportunity.CloseDate) FirstCloseDate, '; 
            ContactRollupQuery += 'MAX(Opportunity.CloseDate) LastCloseDate, COUNT_DISTINCT(Opportunity.Id) NumberOfClosedOpps, ';
            if (objectRollupFieldMap.containsKey('Contact'))
                ContactRollupQuery += objectRollupFieldMap.get('Contact');         
            ContactRollupQuery += 'MAX(Opportunity.npo02__CombinedRollupFieldset__c) RollupFieldset ';
            ContactRollupQuery += 'FROM OpportunityContactRole ';
            ContactRollupQuery += 'WHERE isPrimary=true AND Opportunity.isWon=true '; 
            ContactRollupQuery += 'AND Opportunity.Amount != null '; 
            if(areRecordTypesOnOpps()){
                ContactRollupQuery += 'AND Opportunity.RecordTypeId NOT IN :rectypeset ';
            }
            ContactRollupQuery += 'AND Opportunity.Type NOT IN :typeset '; 
            if (!hh_settings.npo02__Always_Rollup_to_Primary_Contact__c){
                 ContactRollupQuery += 'AND (Opportunity.AccountId = null OR Opportunity.Account.npe01__SYSTEMIsIndividual__c = true) ';
            }                    
            ContactRollupQuery += 'AND ContactId IN :cset ';            
            if (useFiscalYear)
                ContactRollupQuery += 'GROUP BY ROLLUP(ContactId, Fiscal_Year(Opportunity.CloseDate)) ';
            else
                ContactRollupQuery += 'GROUP BY ROLLUP(ContactId, Calendar_Year(Opportunity.CloseDate)) ';              
        }        
        return ContactRollupQuery;
    }
    /*********************************************************************************************************
    * @description Builds the query string for the Contact N Day Rollups.
    * @return String The resulting Contact NDay Query.
    */
    public string buildContactNDayQuery(){
        if (ContactNDayQuery == null){
            ContactNDayQuery = 'SELECT ContactId, ';
            ContactNDayQuery += 'SUM(Opportunity.Amount) TotalOppAmount, COUNT_DISTINCT(Opportunity.Id) NumberOfClosedOpps ';
            ContactNDayQuery += 'FROM OpportunityContactRole  ';
            ContactNDayQuery += 'WHERE isPrimary=true AND opportunity.isWon=true  ';
            ContactNDayQuery += 'AND Opportunity.Amount != null '; 
            if(areRecordTypesOnOpps()){
                ContactNDayQuery += 'AND Opportunity.RecordTypeId NOT IN :rectypeset ';
            }
            ContactNDayQuery += 'AND Opportunity.Type NOT IN :typeset ';
            if (!hh_settings.npo02__Always_Rollup_to_Primary_Contact__c){
                ContactNDayQuery += 'AND (Opportunity.AccountId = null OR Opportunity.Account.npe01__SYSTEMIsIndividual__c = true) ';
            }  
            ContactNDayQuery += 'AND ContactId IN :cset ';
            ContactNDayQuery += 'AND Opportunity.CloseDate >= LAST_N_DAYS:' + hh_settings.npo02__Rollup_N_Day_Value__c.intValue() + ' ';
            ContactNDayQuery += 'GROUP BY ROLLUP(ContactId) ';
        }       
        return ContactNDayQuery;
    }
    /*********************************************************************************************************
    * @description Builds the query string for Contact Membership Rollups.
    * @return String The resulting Account Membership Query.
    */
    public string buildContactMembershipQuery(){ 
        if (ContactMembershipQuery == null){
            ContactMembershipQuery = 'SELECT ContactId, ';
            ContactMembershipQuery += 'SUM(Opportunity.Amount) TotalOppAmount,  ';
            ContactMembershipQuery += 'MAX(Opportunity.CloseDate) LastCloseDate,  ';
            ContactMembershipQuery += 'MIN(Opportunity.npe01__Membership_start_date__c) FirstStartDate, '; 
            ContactMembershipQuery += 'MAX(Opportunity.npe01__Membership_End_Date__c) LastEndDate,  ';                
            ContactMembershipQuery += 'COUNT_DISTINCT(Opportunity.Id) NumberOfClosedOpps, ';
            ContactMembershipQuery += 'MAX(Opportunity.npo02__CombinedRollupFieldset__c) RollupFieldset ';
            ContactMembershipQuery += 'FROM OpportunityContactRole  ';
            ContactMembershipQuery += 'WHERE isPrimary=true AND Opportunity.isWon=true '; 
            ContactMembershipQuery += 'AND Opportunity.Amount != null '; 
            if(areRecordTypesOnOpps()){
                ContactMembershipQuery += 'AND Opportunity.RecordTypeId IN :memberset ';
            }
            ContactMembershipQuery += 'AND Opportunity.Type NOT IN :typeset ';
            if (!hh_settings.npo02__Always_Rollup_to_Primary_Contact__c){
                ContactMembershipQuery += 'AND (Opportunity.AccountId = null OR Opportunity.Account.npe01__SYSTEMIsIndividual__c = true) ';
            }
            ContactMembershipQuery += 'AND Contactid IN :cset ';
            ContactMembershipQuery += 'GROUP BY ROLLUP(ContactId) ';
        }   
        return ContactMembershipQuery;      
    }
    /*********************************************************************************************************
    * @description Builds the query string for the Household Rollups.
    * @return String The resulting Household Rollup Query.
    */
    public string buildHouseholdRollupQuery(){
        if (HouseholdRollupQuery == null){
            if (useFiscalYear)
                HouseholdRollupQuery = 'SELECT Contact.npo02__Household__c hhid, Fiscal_Year(Opportunity.CloseDate) CalendarYr, ';
            else
                HouseholdRollupQuery = 'SELECT Contact.npo02__Household__c hhid, Calendar_Year(Opportunity.CloseDate) CalendarYr, ';            
            HouseholdRollupQuery += 'SUM(Opportunity.Amount) TotalOppAmount, ';
            HouseholdRollupQuery += 'AVG(Opportunity.Amount) AverageAmount, MIN(Opportunity.Amount) SmallestAmount, ';
            HouseholdRollupQuery += 'MAX(Opportunity.Amount) LargestAmount, MIN(Opportunity.CloseDate) FirstCloseDate, '; 
            HouseholdRollupQuery += 'MAX(Opportunity.CloseDate) LastCloseDate, COUNT_DISTINCT(Opportunity.Id) NumberOfClosedOpps, ';
            if (objectRollupFieldMap.containsKey('npo02__Household__c'))
                HouseholdRollupQuery += objectRollupFieldMap.get('npo02__Household__c');         
            HouseholdRollupQuery += 'MAX(Opportunity.npo02__CombinedRollupFieldset__c) RollupFieldset ';
            HouseholdRollupQuery += 'FROM OpportunityContactRole ';
            HouseholdRollupQuery += 'WHERE isPrimary=true AND Opportunity.isWon=true '; 
            HouseholdRollupQuery += 'AND Opportunity.Amount != null '; 
            if(areRecordTypesOnOpps()){
                HouseholdRollupQuery += 'AND Opportunity.RecordTypeId NOT IN :rectypeset ';
            }
            HouseholdRollupQuery += 'AND Opportunity.Type NOT IN :typeset '; 
            if (!hh_settings.npo02__Always_Rollup_to_Primary_Contact__c){
                 HouseholdRollupQuery += 'AND (Opportunity.AccountId = null OR Opportunity.Account.npe01__SYSTEMIsIndividual__c = true) ';
            }                    
            HouseholdRollupQuery += 'AND Contact.npo02__Household__c IN :hhset ';
            if (useFiscalYear)
                HouseholdRollupQuery += 'GROUP BY ROLLUP(Contact.npo02__Household__c, Fiscal_Year(Opportunity.CloseDate)) ';
            else
                HouseholdRollupQuery += 'GROUP BY ROLLUP(Contact.npo02__Household__c, Calendar_Year(Opportunity.CloseDate)) ';              
        }
        return HouseholdRollupQuery;        
    }
    /*********************************************************************************************************
    * @description Builds the query string for the Household NDay Rollups.
    * @return String The resulting Household NDay Query.
    */
    public string buildHouseholdNDayQuery(){
        if (HouseholdNDayQuery == null){
            HouseholdNDayQuery = 'SELECT Contact.npo02__Household__c hhid, ';
            HouseholdNDayQuery += 'SUM(Opportunity.Amount) TotalOppAmount, COUNT_DISTINCT(Opportunity.Id) NumberOfClosedOpps ';
            HouseholdNDayQuery += 'FROM OpportunityContactRole  ';
            HouseholdNDayQuery += 'WHERE isPrimary=true AND opportunity.isWon=true  ';
            HouseholdNDayQuery += 'AND Opportunity.Amount != null '; 
            if(areRecordTypesOnOpps()){
                HouseholdNDayQuery += 'AND Opportunity.RecordTypeId NOT IN :rectypeset ';
            }
            HouseholdNDayQuery += 'AND Opportunity.Type NOT IN :typeset ';
            if (!hh_settings.npo02__Always_Rollup_to_Primary_Contact__c){
                HouseholdNDayQuery += 'AND (Opportunity.AccountId = null OR Opportunity.Account.npe01__SYSTEMIsIndividual__c = true) ';
            }  
            HouseholdNDayQuery += 'AND Contact.npo02__Household__c IN :hhset ';
            HouseholdNDayQuery += 'AND Opportunity.CloseDate >= LAST_N_DAYS:' + hh_settings.npo02__Rollup_N_Day_Value__c.intValue() + ' ';
            HouseholdNDayQuery += 'GROUP BY ROLLUP(Contact.npo02__Household__c) ';          
        }
        return HouseholdNDayQuery;      
    }
    /*********************************************************************************************************
    * @description Builds the query string ]for the Household Membership Rollups.
    * @return String The resulting Household NDay Query.
    */
    public string buildHouseholdMembershipQuery(){
        if (HouseholdMembershipQuery == null){
            HouseholdMembershipQuery = 'SELECT Contact.npo02__Household__c hhid, ';
            HouseholdMembershipQuery += 'SUM(Opportunity.Amount) TotalOppAmount,  ';
            HouseholdMembershipQuery += 'MAX(Opportunity.CloseDate) LastCloseDate,  ';
            HouseholdMembershipQuery += 'MIN(Opportunity.npe01__Membership_start_date__c) FirstStartDate, '; 
            HouseholdMembershipQuery += 'MAX(Opportunity.npe01__Membership_End_Date__c) LastEndDate,  ';                
            HouseholdMembershipQuery += 'COUNT_DISTINCT(Opportunity.Id) NumberOfClosedOpps, ';
            HouseholdMembershipQuery += 'MAX(Opportunity.npo02__CombinedRollupFieldset__c) RollupFieldset ';
            HouseholdMembershipQuery += 'FROM OpportunityContactRole  ';
            HouseholdMembershipQuery += 'WHERE isPrimary=true AND Opportunity.isWon=true '; 
            HouseholdMembershipQuery += 'AND Opportunity.Amount != null '; 
            if(areRecordTypesOnOpps()){
                HouseholdMembershipQuery += 'AND Opportunity.RecordTypeId IN :memberset ';
            }
            HouseholdMembershipQuery += 'AND Opportunity.Type NOT IN :typeset ';
            if (!hh_settings.npo02__Always_Rollup_to_Primary_Contact__c){
                HouseholdMembershipQuery += 'AND (Opportunity.AccountId = null OR Opportunity.Account.npe01__SYSTEMIsIndividual__c = true) ';
            }
            HouseholdMembershipQuery += 'AND Contact.npo02__Household__c IN :hhset ';
            HouseholdMembershipQuery += 'GROUP BY ROLLUP(Contact.npo02__Household__c) ';
        }
        return HouseholdMembershipQuery;
    }
    
    /*******************************************************************************************************
    * @description Builds the query string for Soft Credit rollups based on OCR's.
    * Assumes caller will have local variables for cset, softCreditRoles, rectypeset, and typeset.
    * @return String The soql query
    */
    public static string buildSoftCreditOCRQuery() {
        if (SoftCreditOCRQuery == null) {
            SoftCreditOCRQuery = 'SELECT Id, ContactId, isPrimary, Opportunity.CloseDate, Opportunity.Amount, OpportunityId ';
            if (isMultiCurrency()) {
                SoftCreditOCRQuery += ', Opportunity.CurrencyIsoCode ';
            }
            SoftCreditOCRQuery += 'FROM OpportunityContactRole ';
            SoftCreditOCRQuery += 'WHERE ContactId IN :cset AND Opportunity.isWon=true AND Role IN :softCreditRoles ';
            if (areRecordTypesOnOpps()) {
                SoftCreditOCRQuery += 'AND Opportunity.RecordTypeId NOT IN :rectypeset ';
            }
            SoftCreditOCRQuery += 'AND Opportunity.Type NOT IN :typeset ';
            
        }
        return SoftCreditOCRQuery;
    } 
    
    /*******************************************************************************************************
    * @description Builds the query string for Soft Credit rollups based on PSC's.
    * Assumes caller will have local variables for cset, softCreditRoles, rectypeset, and typeset.
    * @return String The soql query
    */
    public static string buildSoftCreditPSCQuery() {
        if (SoftCreditPSCQuery == null) {
            SoftCreditPSCQuery = 'SELECT Id, Contact__c, Opportunity__r.CloseDate, Amount__c, Contact_Role_ID__c ';
            if (isMultiCurrency()) {
                SoftCreditPSCQuery += ', CurrencyIsoCode ';
            }
            SoftCreditPSCQuery += 'FROM Partial_Soft_Credit__c ';
            SoftCreditPSCQuery += 'WHERE Contact__c IN :cset AND Opportunity__r.isWon=true AND Role_Name__c IN :softCreditRoles ';
            if (areRecordTypesOnOpps()) {
                SoftCreditPSCQuery += 'AND Opportunity__r.RecordTypeId NOT IN :rectypeset ';
            }
            SoftCreditPSCQuery += 'AND Opportunity__r.Type NOT IN :typeset ';
            
        }
        return SoftCreditPSCQuery;
    } 

    /**************************************************************************
    * Soft Credit Query rollups no longer use an aggregate query
    public string buildSoftCreditQuery(){
        if (SoftCreditQuery == null){           
            if (useFiscalYear)
                SoftCreditQuery = 'SELECT ContactId, Fiscal_Year(Opportunity.CloseDate) CalendarYr, ';
            else
                SoftCreditQuery = 'SELECT ContactId, Calendar_Year(Opportunity.CloseDate) CalendarYr, ';            
            SoftCreditQuery += 'SUM(Opportunity.Amount) TotalOppAmount, ';
            SoftCreditQuery += 'MAX(Opportunity.npo02__CombinedRollupFieldset__c) RollupFieldset ';
            SoftCreditQuery += 'FROM OpportunityContactRole ';
            SoftCreditQuery += 'WHERE Opportunity.isWon=true '; 
            SoftCreditQuery += 'AND Opportunity.Amount != null ';   
            SoftCreditQuery += 'AND Role IN :softCreditRoles ';                                         
            if (areRecordTypesOnOpps()){
                SoftCreditQuery += 'AND Opportunity.RecordTypeId NOT IN :rectypeset ';
            }
            SoftCreditQuery += 'AND Opportunity.Type NOT IN :typeset ';
            // soft credits should always roll up to contacts with those OCR's regardless of the tpe of account. 
            //if (!hh_settings.npo02__Always_Rollup_to_Primary_Contact__c){
            //    SoftCreditQuery += 'AND (Opportunity.AccountId = null OR Opportunity.Account.npe01__SYSTEMIsIndividual__c = true) ';
            //}
            
            SoftCreditQuery += 'AND ContactId IN :cset ';            
            if (useFiscalYear)
                SoftCreditQuery += 'GROUP BY ROLLUP(ContactId, Fiscal_Year(Opportunity.CloseDate)) ';
            else
                SoftCreditQuery += 'GROUP BY ROLLUP(ContactId, Calendar_Year(Opportunity.CloseDate)) ';
        }           
        return SoftCreditQuery;  
    }
    
    public string buildContactNDaySoftCreditQuery(){
        if (ContactNDaySoftCreditQuery == null){
            ContactNDaySoftCreditQuery = 'SELECT ContactId, ';
            ContactNDaySoftCreditQuery += 'SUM(Opportunity.Amount) TotalOppAmount ';
            ContactNDaySoftCreditQuery += 'FROM OpportunityContactRole  ';
            ContactNDaySoftCreditQuery += 'WHERE opportunity.isWon=true  ';
            ContactNDaySoftCreditQuery += 'AND Opportunity.Amount != null '; 
            ContactNDaySoftCreditQuery += 'AND Role IN :softCreditRoles ';        
            if (areRecordTypesOnOpps()){
                ContactNDaySoftCreditQuery += 'AND Opportunity.RecordTypeId NOT IN :rectypeset ';
            }
            ContactNDaySoftCreditQuery += 'AND Opportunity.Type NOT IN :typeset ';
            if (!hh_settings.npo02__Always_Rollup_to_Primary_Contact__c){
                ContactNDaySoftCreditQuery += 'AND (Opportunity.AccountId = null OR Opportunity.Account.npe01__SYSTEMIsIndividual__c = true) ';
            }
            ContactNDaySoftCreditQuery += 'AND ContactId IN :cset ';
            ContactNDaySoftCreditQuery += 'AND Opportunity.CloseDate >= LAST_N_DAYS:' + hh_settings.npo02__Rollup_N_Day_Value__c.intValue() + ' ';
            ContactNDaySoftCreditQuery += 'GROUP BY ROLLUP(ContactId) ';
        }       
        return ContactNDaySoftCreditQuery;
    }
    **************************************************************************/

    /****************************************/

    /*** Utility Methods ***/

    /*********************************************************************************************************
    * @description Determines the fiscal year for the current date according to the current org settings.
    * @return Integer The resulting fiscal year.
    */
    public static Integer getCurrentFY() {
        if (fiscal_year == null) {
            Organization orgInfo = [
                SELECT
                    FiscalYearStartMonth,
                    UsesStartDateAsFiscalYearName
                FROM Organization
                WHERE Id = :UserInfo.getOrganizationId()
            ];

            fiscal_year = getFiscalYearForDate(
                Date.today(),
                orgInfo.FiscalYearStartMonth,
                orgInfo.UsesStartDateAsFiscalYearName
            );
        }

        return fiscal_year;
    }

    /*********************************************************************************************************
    * @description Determine the fiscal year for the given date, fiscal year start month, and whether the fiscal year name is based on the start month or end month
    * @param targetDate The date for which the fiscal year will be determined
    * @param fiscalYearStartMonth The month, as an Integer (Jan=1), that the fiscal year starts (see Fiscal Year org settings)
    * @param usesStartDateAsFiscalYearName If true, then the fiscal year is determined by the year of the starting month of the fiscal year, otherwise the fiscal year is determined by the end month in the fiscal year (see Fiscal Year org settings)
    * @return Integer The Fiscal Year for the Date which will depend on whether to use Start or End date
    */
    public static Integer getFiscalYearForDate(Date targetDate, Integer fiscalYearStartMonth, Boolean usesStartDateAsFiscalYearName) {
        Date fiscalYearStartDate = Date.newInstance(
            targetDate.year(),
            fiscalYearStartMonth,
            1
        );

        Date fiscalYearEndDate = fiscalYearStartDate.addYears(1).addDays(-1);

        if (targetDate < fiscalYearStartDate) {
            fiscalYearStartDate = fiscalYearStartDate.addYears(-1);
            fiscalYearEndDate = fiscalYearEndDate.addYears(-1);
        }

        if (usesStartDateAsFiscalYearName) {
            return fiscalYearStartDate.year();
        } else {
            return fiscalYearEndDate.year();
        }
    }
}