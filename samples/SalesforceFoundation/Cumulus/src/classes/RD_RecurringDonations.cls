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
* @date 2012 (2.0)
* @group Recurring Donations
* @group-content ../../ApexDocContent/RecurringDonations.htm
*
* @description The main class for controlling the behavior of Recurring Donations, both open ended and standard.
*
* The logic of this class is quite complicated, so here is a summary of the call chain for various scenarios.
*
* Insert a new RD (open or non open):
*   RD_RecurringDonations_TDTM(AfterInsert)
*       insertOppsOnRecurringDonationInsert
*           Database.insert(opps)
*           updateRecurringDonationOnOppChange
*
* Update a non open-ended RD:
*   RD_RecurringDonations_TDTM(AfterUpdate)
*       handleRecurringDonationUpdate
*           delete non-closed Opps
*           insertOppsOnRecurringDonationInsert
*
* Update a open-ended RD to closed:
*   RD_RecurringDonations_TDTM(AfterUpdate)
*       handleRecurringDonationUpdate
*           runRecurringDonationClosedActions
*
* Update an open-ended RD with changes that require opp updating:
*   RD_RecurringDonations_TDTM(AfterUpdate)
*       handleRecurringDonationUpdate
*           updateExistingOpps
*               updateRecurringDonationOnOppChange
*
* Update an open-ended RD to different schedule or contact (need to recreate opps):
*   RD_RecurringDonations_TDTM(AfterUpdate)
*       handleRecurringDonationUpdate
*           delete non-closed Opps
*           insertOppsOnRecurringDonationInsert
*
* Refresh Opportunities button on non open-ended:
*   evaluateRecurringDonationsForNewOppInsert 
*       delete non-closed Opps
*       insertOppsOnRecurringDonationInsert
*
* Refresh Opportunities button or Nightly Batch on open-ended:
*   evaluateRecurringDonationsForNewOppInsert
*       database.insert(opps) (for additional opps) 
*       insertOppsOnRecurringDonationInsert (if no opps exist)
*       updateRecurringDonationOnOppChange
*
* Update an Opportunity that belongs to a RD:
*   RD_RecurringDonations_Opp_TDTM(AfterUpdate)
*       updateRecurringDonationOnOppChange
*
*/
public class RD_RecurringDonations {
    
    /*******************************************************************************************************
    * @description Enum to specify behavior of what happens to open opportunities when their RD is closed.
    */ 
    public enum RecurringDonationCloseOptions {Delete_Open_Opportunities, Mark_Opportunities_Closed_Lost, No_Action}

    /*******************************************************************************************************
    * @description holds the current custom field mappings used when creating opps for the RD.
    */ 
    public static map<string, npe03__Custom_Field_Mapping__c> customFieldMappingSettings = UTIL_ListCustomSettingsFacade.getMapCustomFieldMappings();
    
    /*******************************************************************************************************
    * @description holds the RD's CurrencyIsoCode field that is enabled in multicurrency organizations.
    */ 
    private static SObjectField RDCurrField;

    /*******************************************************************************************************
    * @description holds the RD's CurrencyIsoCode field that is enabled in multicurrency organizations.
    */ 
    public static SObjectfield RDCurrencyField{get{
        if (RDCurrField == null){
            RDCurrField = Schema.sObjectType.npe03__Recurring_Donation__c.fields.getMap().get('CurrencyIsoCode');
        }   
        return RDCurrField;
    }set;}    

    /*******************************************************************************************************
    * @description holds the Opp's CurrencyIsoCode field that is enabled in multicurrency organizations.
    */ 
    private static Sobjectfield OppCurrField;

    /*******************************************************************************************************
    * @description holds the Opp's CurrencyIsoCode field that is enabled in multicurrency organizations.
    */ 
    public static SObjectField OppCurrencyField{get{
        if (OppCurrField == null)
            OppCurrField = Schema.sObjectType.Opportunity.fields.getMap().get('CurrencyIsoCode');
        return OppCurrField;
    }set;}       

    /*******************************************************************************************************
    * @description is MultiCurrency enabled in this org
    */ 
    private static boolean isMc;

    /*******************************************************************************************************
    * @description is MultiCurrency enabled in this org
    */ 
    public static boolean isMultiCurrency{get{
        if (isMc == null){          
            if (Schema.sObjectType.Opportunity.fields.getMap().get('CurrencyIsoCode') != null)
                isMc = true;
            else
                isMc = false;           
        }           
        return isMc;                
    }set;}
    
    /*******************************************************************************************************
    * @description Future method that updates the specified RD's by querying their Opps so it can update the RD's 
    * Next Payment Date, Last Payment Date, Paid Amount, Number of Paid Installments.
    * @param RDids The set of rd Id's to update
    * @return void
    */ 
    @future
    public static void updateRecurringDonationOnOppChangeFuture(set<id> RDids){
        updateRecurringDonationOnOppChange(RDids, null);
    }
    
    /*******************************************************************************************************
    * @description Updates the specified RD's by querying their Opps so it can update the RD's 
    * Next Payment Date, Last Payment Date, Paid Amount, Number of Paid Installments.
    * @param RDids The set of rd Id's to update
    * @param dmlWrapper Object to hold the rd's to be updated
    * @return void
    */ 
    public static void updateRecurringDonationOnOppChange(set<id> RDids, TDTM_Runnable.DmlWrapper dmlWrapper){   
        //set of RDs to be updated         
        map<id, npe03__Recurring_Donation__c> updateMap = new map<id, npe03__Recurring_Donation__c>();
        
        //Get the closed label for opps
        string closedLabel = system.label.npe03.RecurringDonationClosedStatus;
        
        //before we query for any RD's, we must make sure that all previous changes to them have been committed!
        if (dmlWrapper != null)
            TDTM_TriggerHandler.processDML(dmlWrapper);
        
        string rdQuery = strQueryRDNoWhere();
        rdQuery += ' where npe03__Open_Ended_Status__c <> :closedLabel and id IN :RDids';

        //get all possible rd records that may need updating, exclude ones that are closed.  
        map<id, npe03__Recurring_Donation__c> rdMap = new map<id, npe03__Recurring_Donation__c>();
        for (sObject rd : database.query(rdQuery))
            rdMap.put((id)rd.get('id'), (npe03__Recurring_Donation__c)rd);

        //get the cumulative values to update the recurring donations
        //update the rc next payment date 
        //and the paid ammounts and paid installments 
        for (sobject obj : [select count(id) oppcount, MIN(CloseDate) MinDate, MAX(CloseDate) MaxDate, SUM(Amount) Total,
                                  isClosed, npe03__Recurring_Donation__c rdid, isWon
                                  from Opportunity where npe03__Recurring_Donation__r.npe03__Open_Ended_Status__c <> :closedLabel 
                                  and npe03__Recurring_Donation__c IN :rdMap.keySet()
                                  group by rollup(npe03__Recurring_Donation__c, isClosed, isWon)]) {
            id rdid = (id)obj.get('rdid'); 
            
            boolean isClosed = (boolean)obj.get('isClosed');
            boolean isWon = (boolean)obj.get('isWon');            
            
            if (rdid != null) {
                npe03__Recurring_Donation__c rd;        
                //if we already have values for this RD, get it from our rdid map, otherwise just grab
                //the one we're already working on 
                if (!updateMap.containsKey(rdid)) {
                    rd = rdMap.get(rdid);

                    //initialize fields to null that might not get set
                    rd.npe03__Next_Payment_Date__c = null;
                    rd.npe03__Last_Payment_Date__c = null;
                    rd.npe03__Paid_Amount__c = null;     
                    rd.npe03__Total_Paid_Installments__c = null;
                    updateMap.put(rdid, rd);                        
                } else {
                    rd = updateMap.get(rdid);
                }
                
                //if this is the summary row for this rd
                if (isClosed == null && isWon == null) {
                    rd.npe03__Installments__c = (integer)obj.get('oppcount'); 
                    updateMap.put(rdid, rd);                                       
                }
                //if this is the open set of opps
                else if (isClosed == false && isWon == null) {
                    rd.npe03__Next_Payment_Date__c = (date)obj.get('MinDate');
                    updateMap.put(rdid, rd);                                                       	
                }
                //if this is the closed set of opps    
                else if (isClosed == true && isWon == true) {
                    rd.npe03__Last_Payment_Date__c = (date)obj.get('MaxDate');

                    //convert rollup total to RD's currency
                    if (RLLP_OppRollup_UTIL.isMultiCurrency())
                        rd.npe03__Paid_Amount__c = RLLP_OppRollup_UTIL.ConvertFromCorporate((string)rd.get(RDCurrencyField), (decimal)obj.get('Total'));
                    else
                        rd.npe03__Paid_Amount__c = (decimal)obj.get('Total');

                    rd.npe03__Total_Paid_Installments__c = (integer)obj.get('oppcount');
                    updateMap.put(rdid, rd);                        
                }                   
            }
        }
        
        if (!updateMap.isEmpty()) {
        	if (dmlWrapper != null)
                dmlWrapper.objectsToUpdate.addAll((list<SObject>) updateMap.values());
        	else
                update updateMap.values();
        }
    } 
   
    /*******************************************************************************************************
    * @description Returns a list of RD's for the specified Id's, with all their fields queried.
    * @param setRDids The set of rd Id's to quer
    * @return list<npe03__Recurring_Donation__c>
    */ 
    public static list<npe03__Recurring_Donation__c> requeryListRD(set<Id> setRDId) {    
        string queryRCD = strQueryRDNoWhere();
        queryRCD += ' where Id in :setRDId';
        
        //execute query
        return Database.query(queryRCD);
    }
    
    /*******************************************************************************************************
    * @description Returns the soql string for RD's.  Includes fields from the custom field map and
    * currency iso fields.  Does not include the where clause since different callers have different
    * filtering needs.
    * @return string
    */ 
    public static string strQueryRDNoWhere() {
    	
    	// these must be lowercase so set.contains() will work
        set<string> existingFields = new set<string>{  'npe03__open_ended_status__c', 'npe03__next_payment_date__c', 'name', 'npe03__Paid_Amount__c',
                                                       'npe03__organization__c', 'npe03__contact__c', 'npe03__installment_amount__c',
                                                       'npe03__installments__c', 'npe03__amount__c', 'npe03__total__c', 'npe03__installment_period__c',
                                                       'npe03__date_established__c', 'npe03__donor_name__c', 'npe03__schedule_type__c', 
                                                       'npe03__recurring_donation_campaign__c', 'npe03__total_paid_installments__c', 'ownerid'};
        
        String queryRCD = 'select id';
        for (string s : existingFields){
            queryRCD += ', ' + s;               
        }
   
        //add any custom mapping to make sure we have the required fields
        for (string s : customFieldMappingSettings.keySet()){
            string RDFieldName = customFieldMappingSettings.get(s).npe03__Recurring_Donation_Field__c;             
            if (!existingFields.contains(RDFieldName.toLowerCase()) && s != 'id'){
               queryRCD = queryRCD + ',' + customFieldMappingSettings.get(s).npe03__Recurring_Donation_Field__c;
               existingFields.add(RDFieldName.toLowerCase());   
            }
        }       
   
        //if currencyiso field exists add it to query for use later
        if (isMultiCurrency)
            queryRCD = queryRCD + ',CurrencyIsoCode';
    
        queryRCD=queryRCD+' from npe03__Recurring_Donation__c';
        
        return (queryRCD);
    }   
     
    /*******************************************************************************************************
    * @description Future method that creates new Opportunities for the specified Recurring Donations.
    * @param recurringDonations The list of RD's being created
    * @return void
    */ 
    @future
    public static void insertOppsOnRecurringDonationInsertFuture(set<id> recids) {
        delete [select id from Opportunity where isClosed = false and npe03__Recurring_Donation__c IN :recids];

        list<npe03__Recurring_Donation__c> reclist = requeryListRD(recids);
        insertOppsOnRecurringDonationInsert(reclist);
    }
    
    /*******************************************************************************************************
    * @description Creates new Opportunities for the specified Recurring Donations. Called by the AfterInsert
    * Trigger Handler on RD's, and also when RD's are updated and we delete and recreate their Opps.
    * @param recurringDonations The list of RD's being created
    * @return void
    */ 
    public static void insertOppsOnRecurringDonationInsert(list<npe03__Recurring_Donation__c> recurringDonations) {
        //Lists used for final Insert
        list<Opportunity> opportunityInserts = new list<Opportunity>();
        
        npe03__Recurring_Donations_Settings__c rds = UTIL_CustomSettingsFacade.getRecurringDonationsSettings();
        
        //grab the contact id for role to take advantage of NPSP C&O if it exists
        Sobjectfield ConIDForRole = Schema.sObjectType.Opportunity.fields.getMap().get('npe01__Contact_Id_for_Role__c');
        set<id> updateRecurringDonationSet = new set<id>();            
        
        //Create a list of ContactIds used in these RecurringDonations
        list<Id> contactIds = new list<Id>();
        map<Id,Id> recConMap = new map<Id,Id>();
        for(npe03__Recurring_Donation__c r : recurringDonations){
            updateRecurringDonationSet.add(r.id);
            if (r.npe03__Contact__c != null){ 
                contactIds.add(r.npe03__Contact__c);
                recConMap.put(r.Id,r.npe03__Contact__c);
            }
        }

        //Create a Map of Contact Ids and Contact Records
        map<Id,Contact> contactMap;
        map<Id,Account> accountMap;
        if (contactIds.size() > 0){
            contactMap = new Map<Id,Contact>([Select Id, AccountId from Contact where Id in :contactIds and AccountId != null]);
        }
        
        //Loop through the Recurring Donations and create the appropriate number of Opportunities
        for(npe03__Recurring_Donation__c r : recurringDonations){
            //if we're not looking at an open-ended type donation, handle it the 'old' way        
            if (r.npe03__Open_Ended_Status__c != system.label.npe03.RecurringDonationOpenStatus && r.npe03__Open_Ended_Status__c != system.label.npe03.RecurringDonationClosedStatus){          
        
                Decimal installs = r.npe03__Installments__c;
                Integer installments = (installs == null ? 0 : installs.intValue());
                    
                date OppCloseDate;             
                if (r.npe03__Next_Payment_Date__c != null)                     
                    OppCloseDate = r.npe03__Next_Payment_Date__c;
                else{
                    OppCloseDate = r.npe03__Date_Established__c;                                                                        
                }                                             
                
                Integer j=0;
                decimal installmentAmount = r.npe03__Installment_Amount__c;

                decimal paidAmount = 0;
                if (r.npe03__Paid_Amount__c != null)
                   paidAmount = r.npe03__Paid_Amount__c; 
                
                // deal with already paid installments.
                integer paidInstallments = 0;
                if (r.npe03__Total_Paid_Installments__c != null) {
                	paidInstallments = integer.valueOf(r.npe03__Total_Paid_Installments__c);
                    j = paidInstallments;
                    if (r.npe03__Schedule_Type__c != Label.npe03.RecurringDonationMultiplyValue) {
                        if (installments - paidInstallments > 0)                    
                            installmentAmount = (r.npe03__Amount__c - paidAmount) / (installments - paidInstallments);
                    }
                }
                    
                for ( ;j<installments;j++ )
                {
                    Opportunity opp = new Opportunity();
                    opp.CloseDate = OppCloseDate;
                    opp.OwnerId = r.OwnerId;
                
                    if ( r.npe03__Organization__c != null )
                    {
                       opp.AccountId = r.npe03__Organization__c;
                    }
                    else
                    if ( contactMap.containsKey(r.npe03__Contact__c) )
                    {
                        Contact c = contactMap.get(r.npe03__Contact__c);
                        opp.AccountId = c.AccountId;
                        if (ConIDForRole != null){
                            opp.put('npe01__Contact_Id_For_Role__c', (string)c.id);
                        }
                    } 

                    // James Melville 05/03/2011 Added to support multi-currency sfdc.  
                    // we check currency iscode before entering the loop since it's a system check
                    if(isMultiCurrency){                        
                        //set the donation currency field to equal the recurring donation currency
                        opp.put(OppCurrencyField,r.get(RDCurrencyField));                        
                    }
                
                    //add the remainder to the last installment, otherwise use the amount
                    decimal totalAmount = r.npe03__Total__c;
                    // current formula field doesn't handle a missing schedule type, but let's treat it as divide by.
                    if (r.npe03__Schedule_Type__c == null)
                        totalAmount = r.npe03__Amount__c;
                    if (j + 1 == installments && installments * r.npe03__Installment_Amount__c != totalAmount) {                    	
                        opp.Amount = totalAmount - paidAmount - ((j - paidInstallments) * installmentAmount);
                    } else {
                        opp.Amount = installmentAmount;
                    }
                
                    /************ we now share the routine to calc the next opp date **********
                    if (r.npe03__Installment_Period__c == system.label.npe03.RecurringDonationInstallmentPeriodYearly)
                    {
                        opp.CloseDate = (r.npe03__Date_Established__c).addYears(j);
                    } else
                    if (r.npe03__Installment_Period__c == system.label.npe03.RecurringDonationInstallmentPeriodQuarterly)
                    {
                       opp.CloseDate = (r.npe03__Date_Established__c).addMonths(3*j);
                       opp.CloseDate = dtEndOfMonthFixup(opp.CloseDate);                       
                    } else
                    if (r.npe03__Installment_Period__c == system.label.npe03.RecurringDonationInstallmentPeriodMonthly)
                    {
                       opp.CloseDate = (r.npe03__Date_Established__c).addMonths(j);
                       opp.CloseDate = dtEndOfMonthFixup(opp.CloseDate);                       
                    } else
                    if (r.npe03__Installment_Period__c == system.label.npe03.RecurringDonationInstallmentPeriodWeekly)
                    {
                       opp.CloseDate = (r.npe03__Date_Established__c).addDays(7*j);
                    } else
                    {
                       opp.CloseDate = (r.npe03__Date_Established__c);
                    }
                    *********************************/
                                        
                    OppCloseDate = getNextDate(OppCloseDate, r);
                
                    String oName = '';
                    oName += r.npe03__Donor_Name__c; 
                    oName += ' ';
                    oName += system.label.npe03.RecurringDonationPrefix;
                    oName += ' (';
                    oName += j+1;
                    oName += ' of ';
                    oName += installments;
                    oName += ') ';
                    oName += opp.CloseDate.format();
                    opp.Name = oName;

                    opp.Recurring_Donation_Installment_Number__c = j+1;

                    opp.StageName = system.label.npe03.RecurringDonationStageName;
                    opp.npe03__Recurring_Donation__c = r.Id;
                    if (r.npe03__Recurring_Donation_Campaign__c != null && (rds.npe03__Add_Campaign_to_All_Opportunites__c || j < 1)){
                       opp.CampaignId = r.npe03__Recurring_Donation_Campaign__c;
                    }
                    if (rds.npe03__Record_Type__c != null){
                        opp.put('RecordTypeId', rds.npe03__Record_Type__c);
                        //opp.RecordTypeID = rds.npe03__Record_Type__c;
                    }

                    //evaluate any custom field mappings
                    for (string s : customFieldMappingSettings.keySet()){
                        npe03__Custom_Field_Mapping__c cfm = customFieldMappingSettings.get(s);
                        opp.put(cfm.npe03__Opportunity_Field__c, r.get(cfm.npe03__Recurring_Donation_Field__c));
                    }
                    
                    opportunityInserts.add(opp);
                    if ( opportunityInserts.size() == 100 )
                    {
                       //Database.SaveResult[] lsr = Database.insert(opportunityInserts, false);
                       //all or nothing so errors not lost
                       insert opportunityInserts;
                       opportunityInserts.clear();
                    }
                }
            }
            
            //this is an open-ended rd that needs opptys
            else{
                //if its not 'open', we're not doing anything to it
                if (r.npe03__Open_Ended_Status__c == system.label.npe03.RecurringDonationOpenStatus){
                    //get settings so we can figure out how many donations to create                    
                    date OppCloseDate;
                  
                    if (r.npe03__Next_Payment_Date__c != null)                     
                        OppCloseDate = r.npe03__Next_Payment_Date__c;
                    else{
                        OppCloseDate = r.npe03__Date_Established__c;                                                                        
                    }                                             
                    /*
                    if (r.npe03__Total_Paid_Installments__c > 0){
                            OppCloseDate = getNextDate(OppCloseDate, r);
                    } */   
                    //date incrementdate = OppCloseDate;
                    integer forecast_months = (integer)rds.npe03__Opportunity_Forecast_Months__c;
                    Date dtForecast =  system.today().toStartOfMonth().addMonths(forecast_months);         
                    
                    integer rdcounter = 1;
                    if (r.npe03__Total_Paid_Installments__c > 0)
                        rdcounter = (integer)r.npe03__Total_Paid_Installments__c + 1;
                            
                    //do{
                    while (OppCloseDate < dtForecast) {
                    	UTIL_Debug.debug('****DJH insert rdcounter: ' + rdcounter + ' OppCloseDate: ' + OppCloseDate + ' dtForecast: ' + dtForecast);
                        Opportunity o = new Opportunity();
                        o.CloseDate = OppCloseDate;
                        o.OwnerId = r.OwnerId;
                        
                                                                                                          
                        if(isMultiCurrency){
                            //set the donation currency field to equal the recurring donation currency
                            o.put(OppCurrencyField,r.get(RDCurrencyField));
                        }
                    
                        if (r.npe03__Organization__c != null){
                            o.AccountId = r.npe03__Organization__c;
                        }
                        else if (contactMap.containsKey(r.npe03__Contact__c)){
                            Contact c = contactMap.get(r.npe03__Contact__c);
                            o.AccountId = c.AccountId;
                            if (ConIDForRole != null){
                                o.put('npe01__Contact_Id_For_Role__c', (string)c.id);
                            }
                        }       
                                       
                        o.Amount = r.npe03__Amount__c;
                    
                        OppCloseDate = getNextDate(OppCloseDate, r);
                    
                        String oName = '';
                        oName += r.npe03__Donor_Name__c; 
                        oName += ' ';
                        oName += system.label.npe03.RecurringDonationPrefix;
                        oName += ' (';
                        oName += string.valueOf(rdcounter);
                        oName += ') ';
                        oName += o.CloseDate.format();
                        o.Name = oName;

                        o.Recurring_Donation_Installment_Number__c = rdcounter;


                        if (rds.npe03__Record_Type__c != null){
                            o.put('RecordTypeId', rds.npe03__Record_Type__c);
                            //o.RecordTypeID = rds.npe03__Record_Type__c;
                        }
                        o.StageName = system.label.npe03.RecurringDonationStageName;
                        o.npe03__Recurring_Donation__c = r.Id;
                        if (r.npe03__Recurring_Donation_Campaign__c != null && (rds.npe03__Add_Campaign_to_All_Opportunites__c || rdcounter == 1)){
                            o.CampaignId = r.npe03__Recurring_Donation_Campaign__c;
                        }

                        //evaluate any custom field mappings
                        for (string s : customFieldMappingSettings.keySet()){
                            npe03__Custom_Field_Mapping__c cfm = customFieldMappingSettings.get(s);
                            o.put(cfm.npe03__Opportunity_Field__c, r.get(cfm.npe03__Recurring_Donation_Field__c)); 
                        }
                        opportunityInserts.add(o);    
                        rdcounter++;                                                                                       
                    }//while (OppCloseDate < dtForecast);                                                            
                }
            }                 
        }
        
        if( opportunityInserts.size() > 0 )
        {   
            set<id> RDID = new set<id>();           
            for (Opportunity o : opportunityInserts){
                RDID.add(o.npe03__Recurring_Donation__c);
            }
            
            Database.SaveResult[] lsr;
            list<Database.SaveResult> badSRs = new list<Database.SaveResult>();
            list<Opportunity> badOpps = new list<Opportunity>();
            set<id> createdOppIds = new set<id>();  
            
            //we're probably coming from the UI if we're only updating one RD, so lets 
            //let the error bubble up to the top
            if (RDID.size() == 1)
                lsr = Database.insert(opportunityInserts, true);
            //otherwise, this is a bulk operation so fail to the error handler 
            else
                lsr = Database.insert(opportunityInserts, false);        
                    
            integer i = 0;                          
            for (Database.SaveResult s : lsr){
                if (s.isSuccess() == true){
                    //get all the new OppIds into a list
                    createdOppIds.add(s.getId());
                }    
                //handle errors on insert   
                else{
                    badSRs.add(s);
                    badOpps.add(opportunityInserts[i]);            
                }                     
                i++;
            }
                    
            if (ConIDForRole == null){ 
                if (createdOppIds.size() > 1 && !system.isFuture() && !system.isBatch() && !RD_ProcessControl.batchButton)
                    oppContactRolesFuture(createdOppIds); 
                else
                    oppContactRoles(createdOppIds);                    
            }
            
            if (!badSRs.isEmpty()) {
                ERR_Handler.Errors errors = ERR_Handler.getErrors(badSRs, badOpps);
                ERR_Handler.processErrors(errors.errorRecords, ERR_Handler_API.Context.RD.name());
            }            
            
            if (updateRecurringDonationSet.size() > 1 && !system.isBatch() && !system.isFuture() && !RD_ProcessControl.batchButton)
                updateRecurringDonationOnOppChangeFuture(updateRecurringDonationSet); 
            else
                updateRecurringDonationOnOppChange(updateRecurringDonationSet, null);
        }
    }
    
    /*******************************************************************************************************
    * @description Future method that creates household OpportunityContactRoles for the specified Opps.
    * Since NPSP3, the RD code always leverages opp.npe01__Contact_Id_for_Role__c and this code isn't used.
    * @param oppIds The list of Opportunity Id's to create OCRS for
    * @return void
    */ 
    @future 
    public static void oppContactRolesFuture(set<id> oppIds){
       oppContactRoles(oppIds);
    }
    
    /*******************************************************************************************************
    * @description Creates household OpportunityContactRoles for the specified Opps.
    * Since NPSP3, the RD code always leverages opp.npe01__Contact_Id_for_Role__c and this code isn't used.
    * @param oppIds The list of Opportunity Id's to create OCRS for
    * @return void
    */ 
    public static void oppContactRoles( Set<Id> oppIds ){
        List<OpportunityContactRole> contactRoleInserts = new List<OpportunityContactRole>();
        map <String,Id> contactOppToContactRole = new Map <String,Id> ();
            
        Opportunity[] oppsFromRecurringDontaions = [select id, npe03__Recurring_Donation__c,npe03__Recurring_Donation__r.npe03__Contact__c from Opportunity where Id IN :oppIds AND npe03__Recurring_Donation__r.npe03__Contact__c!=null];
        
        if(oppsFromRecurringDontaions.size()>0){
            // query for OppConRoles where primary=true and OppID in arg set
            OpportunityContactRole[] conRoles = [select Id,OpportunityId, ContactId From OpportunityContactRole WHERE IsPrimary = true AND Opportunity.Id IN :oppIds ];
            String uniqueConRole = '';
            // now loop through the results and build the map
            for (OpportunityContactRole thisCR : conRoles) {
                uniqueConRole = string.valueOf(thisCR.OpportunityId) + string.valueOf(thisCR.ContactId);
                contactOppToContactRole.put(uniqueConRole , thisCR.Id );
            }
                
            for (Opportunity createdOpp : oppsFromRecurringDontaions) {
              
                OpportunityContactRole ocr = new OpportunityContactRole();
                ocr.OpportunityId = createdOpp.Id;
                ocr.Role = system.label.npe03.RecurringDonationContactRole;
                ocr.IsPrimary = true;
                
                uniqueConRole = string.valueOf(createdOpp.Id)+string.valueOf(createdOpp.npe03__Recurring_Donation__r.npe03__Contact__c);
                if(contactOppToContactRole.get(uniqueConRole)==null){
                    ocr.ContactId = createdOpp.npe03__Recurring_Donation__r.npe03__Contact__c;
                    contactRoleInserts.add(ocr);
                    if ( contactRoleInserts.size() == 100 )
                    {
                        //Database.SaveResult[] osr = Database.insert(contactRoleInserts, false);
                        // all or nothing so errors not lost
                        insert contactRoleInserts;
                        contactRoleInserts.clear();
                    }
                }
            }
        }
        if ( contactRoleInserts.size() > 0 )
        {
            //Database.SaveResult[] osr = Database.insert(contactRoleInserts, false);
            // all or nothing so errors not lost
            insert contactRoleInserts;
        }
    }
    
    /*******************************************************************************************************
    * @description Future method that updates all the existing Opportunities for the specified Recurring Donations.
    * @param recIDs The set of RD id's to process
    * @param dmlWrapper Object to store the Opps that need updating
    * @return void
    */ 
    @future
    public static void updateExistingOppsFuture(set<id>recIDs){
       updateExistingOpps(recIDs, null);
    }
    
    /*******************************************************************************************************
    * @description Updates all the existing Opportunities for the specified Recurring Donations.
    * @param recIDs The set of RD id's to process
    * @param dmlWrapper Object to store the Opps that need updating
    * @return void
    */ 
    public static void updateExistingOpps(set<id> recIDs, TDTM_Runnable.DmlWrapper dmlWrapper){
        
        
        npe03__Recurring_Donations_Settings__c rds = UTIL_CustomSettingsFacade.getRecurringDonationsSettings();
        string closedLabel = system.label.npe03.RecurringDonationClosedStatus;

        string rdQuery = strQueryRDNoWhere();
                rdQuery+=' where npe03__Open_Ended_Status__c <> :closedLabel ';
                rdQuery+=' and id IN :recIDs ';
        List<npe03__Recurring_Donation__c> rdsToUpdate = database.query(rdQuery);       
        map<id, npe03__Recurring_Donation__c> rdMap = new map<id, npe03__Recurring_Donation__c>(rdsToUpdate);  
                           
        list<Opportunity> oppList = [select id, Amount, Name, CampaignId, AccountID, CloseDate, Recurring_Donation_Installment_Number__c, npe03__Recurring_Donation__c                                         
                                       from Opportunity 
                                       where npe03__Recurring_Donation__c IN :rdMap.keySet() 
                                       and isClosed = false
                                       order by CloseDate asc];

        //build a map of recurring donation ID and opps 
        map<id, list<Opportunity>> RDOppMap = new map<id, list<Opportunity>>(); 
        for (Opportunity o : opplist){
            if (RDOppMap.containsKey(o.npe03__Recurring_Donation__c)){
                list<Opportunity> opps = RDOppMap.get(o.npe03__Recurring_Donation__c);
                opps.add(o);
                RDOppMap.put(o.npe03__Recurring_Donation__c, opps);
            }
            else{
                RDOppMap.put(o.npe03__Recurring_Donation__c, new list<Opportunity>{o});
            }
        }       
        
        //opps for updating
        list<Opportunity> updateOppsList = new list<Opportunity>();
        
        //loop through existing opps for each RD and update according to values on the RD
        for (id RDid : RDOppMap.keySet()){
            
            date PaymentDate = RDMap.get(RDid).npe03__Next_Payment_Date__c;
            if (PaymentDate == null)
                PaymentDate = RDMap.get(RDid).npe03__Date_Established__c;
            
            for (Opportunity o : RDOppMap.get(RDid)){
                
                o.Amount = rdMap.get(RDid).npe03__Amount__c;
                if (rdMap.get(RDid).npe03__Recurring_Donation_Campaign__c != null && rds.npe03__Add_Campaign_to_All_Opportunites__c){ 
                    o.CampaignId = rdMap.get(RDid).npe03__Recurring_Donation_Campaign__c;
                }               
                
                //changed for beta 7 - dont' update with a null org unless we need to
                if (rdMap.get(RDid).npe03__Organization__c != null)
                    o.AccountID = rdMap.get(RDid).npe03__Organization__c;
                o.CloseDate = PaymentDate;                      
                
                //modify the name 
                String oName = '';
                oName += rdMap.get(RDid).npe03__Donor_Name__c; 
                oName += ' ';
                oName += system.label.npe03.RecurringDonationPrefix;
                oName += ' (' + o.Recurring_Donation_Installment_Number__c + ') ';
                oName += o.CloseDate.format();
                o.Name = oName;
                
                //now append appropriate amount to this date
                PaymentDate = getNextDate(PaymentDate, rdMap.get(RDid));

                //evaluate any custom field mappings
                for (string s : customFieldMappingSettings.keySet()){
                    npe03__Custom_Field_Mapping__c cfm = customFieldMappingSettings.get(s);
                    o.put(cfm.npe03__Opportunity_Field__c, rdMap.get(RDid).get(cfm.npe03__Recurring_Donation_Field__c));
                }
                updateOppsList.add(o);
            }
        }
        
        if (!updateOppsList.isEmpty()) {
            //refresh opportunity names before update. if no opportunity names are defined, keeps original name
            OPP_OpportunityNaming.refreshOppNames(updateOppsList);
            if (dmlWrapper != null)
                dmlWrapper.objectsToUpdate.addAll((list<SObject>) updateOppsList);
            else
                update updateOppsList;
        }
        
        
        if (system.isFuture() || RDOppMap.keySet().size() == 1 || system.isBatch() || RD_ProcessControl.batchButton){
            updateRecurringDonationOnOppChange(RDOppMap.keySet(), dmlWrapper);
        }          
        else{
            updateRecurringDonationOnOppChangeFuture(RDOppMap.keySet());            
        }
    }
         
    /*******************************************************************************************************
    * @description Trigger AfterUpdate Handler for updates on existing Recurring Donations.  Key scenarios include:
    * - closing an open ended rd, which should close all remaining opps on the rd.
    * - opening a closed rd, which should create new opps on the rd.
    * - any other modification to the rd, which should update the rd's opps.
    * @param recurringDonations The list of updated RD's to process
    * @param oldRecurringDonations A map from an RD's Id to its RD before the update 
    * @param dmlWrapper Object to store the Opps that need updating
    * @return void
    */ 
    public static void handleRecurringDonationUpdate(list<npe03__Recurring_Donation__c> recurringDonations, map<id, npe03__Recurring_Donation__c> oldRecurringDonations, TDTM_Runnable.DmlWrapper dmlWrapper){     
        //opening a closed recurring donation
        set<id> reevaluateOpps = new set<id>();
        list<npe03__Recurring_Donation__c> reinsertOpps = new list<npe03__Recurring_Donation__c>(); 
        // closed an open recurring donation
        list<npe03__Recurring_Donation__c> newlycloseddonations = new list<npe03__Recurring_Donation__c>();
         
        string openlabel = system.label.npe03.RecurringDonationOpenStatus;
        string closedlabel = system.label.npe03.RecurringDonationClosedStatus;        
        
        for (npe03__Recurring_Donation__c r : recurringDonations){
            npe03__Recurring_Donation__c oldRD = oldRecurringDonations.get(r.id);

            //Check for custom field changes and set flag for update 
            boolean cfmFieldChanged=false;
            for (string s : customFieldMappingSettings.keySet()){
                npe03__Custom_Field_Mapping__c cfm = customFieldMappingSettings.get(s);
               if(oldRD.get(cfm.npe03__Recurring_Donation_Field__c)!=r.get(cfm.npe03__Recurring_Donation_Field__c)){
                    cfmFieldChanged=true;
               }
            }  
            //if we're closing an open ended donation, modify the existing opps as required by the custom setting
            if (r.npe03__Open_Ended_Status__c == closedlabel && oldRD.npe03__Open_Ended_Status__c != r.npe03__Open_Ended_Status__c){
                newlycloseddonations.add(r);        
            }            
            
            //if there's any other change that would require a reevaluation of existing Opps
            else if (r.npe03__Open_Ended_Status__c != closedlabel){
            	// for non open-ended rd's, just always re-insert
            	if (r.npe03__Open_Ended_Status__c != openlabel) {
            		reinsertopps.add(r);
            	}
                else if (r.npe03__Amount__c != oldRD.npe03__Amount__c ||
                    r.npe03__Recurring_Donation_Campaign__c != oldRD.npe03__Recurring_Donation_Campaign__c ||
                    r.npe03__Organization__c != oldRD.npe03__Organization__c ||
                    r.npe03__Next_Payment_Date__c != oldRD.npe03__Next_Payment_Date__c ||
                    cfmFieldChanged==true
                    ){             
                    reevaluateopps.add(r.id);
                }
                else if (r.npe03__Installment_Period__c != oldRD.npe03__Installment_Period__c || r.npe03__Contact__c != oldRD.npe03__Contact__c){
                    reinsertopps.add(r);
                }                                        
            }
        }      
        
        if (!newlycloseddonations.isEmpty()){
            runRecurringDonationClosedActions(newlycloseddonations, dmlWrapper);
        }
        if (!reevaluateopps.isEmpty()){
            if (reevaluateopps.size() == 1 || system.isFuture() || system.isBatch())
                updateExistingOpps(reevaluateopps, dmlWrapper);
            else
                updateExistingOppsFuture(reevaluateopps);
        }
        //needs a total reinsert, not an update
        if (!reinsertopps.isEmpty()){
            //clear existing open opps and recreate
            delete [select id from Opportunity where npe03__Recurring_Donation__c IN :reinsertopps and isClosed = false];
            if (reinsertopps.size() == 1 || system.isFuture() || system.isBatch()){
                insertOppsOnRecurringDonationInsert(reinsertopps);                  
            }
            else{
                set<id> rdset = new set<id>();
                for (npe03__Recurring_Donation__c r : reinsertopps)
                    rdset.add(r.id);
                insertOppsOnRecurringDonationInsertFuture(rdset);   
            }
        }
    }
    
    /*******************************************************************************************************
    * @description Handles changes to the specified RD's to update/recreate their Opportunities.  
    * Called from the Refresh Opportunities button on both Open and Non-Open RD's, 
    * as well as the Batch process for extending Open Ended RD's (no Non-Open).
    * @param recurringDonationsToEvaluate The list of updated RD's to process
    * @return integer The number of records that failed to be processed.
    */ 
    public static Integer evaluateRecurringDonationsForNewOppInsert(list<npe03__Recurring_Donation__c> recurringDonationsToEvaluate){
    
        string openlabel = system.label.npe03.RecurringDonationOpenStatus;
        string closedlabel = system.label.npe03.RecurringDonationClosedStatus;
    
        // non open-ended rd's are handled strictly by the insert rd code.
        list<npe03__Recurring_Donation__c> listRDNonOpen = new list<npe03__Recurring_Donation__c>();
        list<npe03__Recurring_Donation__c> listRDOpen = new list<npe03__Recurring_Donation__c>();
        
        for (Integer i=0; i<recurringDonationsToEvaluate.size(); i++) {
            npe03__Recurring_Donation__c rd = recurringDonationsToEvaluate[i];
            
            //don't try to evaluate orphaned recurring donations
            if (rd.npe03__Contact__c == null && rd.npe03__Organization__c == null) {
                recurringDonationsToEvaluate.remove(i);
                //decrement counter after removing value to avoid going over the end of the array
                i--;
                continue;
            }

            if (rd.npe03__Open_Ended_Status__c != openlabel && rd.npe03__Open_Ended_Status__c != closedlabel) {
                listRDNonOpen.add(rd);
            } else {
                listRDOpen.add(rd);
            }
        }

        if (listRDNonOpen.size() > 0) {
            //clear existing open opps and recreate
            delete [select id from Opportunity where npe03__Recurring_Donation__c IN :listRDNonOpen and isClosed = false];
            insertOppsOnRecurringDonationInsert(listRDNonOpen);
            recurringDonationsToEvaluate = listRDOpen;
        }
       
        //continue on to evaluate open ended rd's.
        
        npe03__Recurring_Donations_Settings__c rds = UTIL_CustomSettingsFacade.getRecurringDonationsSettings();
    
        list<Opportunity> oppInsertList = new list<Opportunity>();
        
        //get the total number of forecast months
        integer forecast_months = (integer)rds.npe03__Opportunity_Forecast_Months__c;
        
        //grab the contact id for role to take advantage of NPSP C&O if it exists
        Sobjectfield ConIDForRole = Schema.sObjectType.Opportunity.fields.getMap().get('npe01__Contact_Id_for_Role__c');
                
        //get the existing donations
        list<sobject> sobjs = [select count(id) oppCount, MAX(CloseDate) MaxDate,
                                  npe03__Recurring_Donation__c rdid
                                  from Opportunity where npe03__Recurring_Donation__r.npe03__Open_Ended_Status__c <> :closedLabel 
                                  and npe03__Recurring_Donation__c IN :recurringDonationsToEvaluate                                  
                                  group by rollup(npe03__Recurring_Donation__c)]; 
        
        map<id, date> lastOppMap = new map<id, date>();
        map<id, integer> oppCountMap = new map<id, integer>();
        set<id> contactIDs = new set<id>();
                
        for (npe03__Recurring_Donation__c rd : recurringDonationsToEvaluate){
            if (rd.npe03__Contact__c != null ){
                contactIds.add(rd.npe03__Contact__c);
            }
        }            
        //Create a Map of Contact Ids and Contact Records
        map<id,Contact> contactMap;        
        if (contactIds.size() > 0){
            contactMap = new Map<Id,Contact>([Select Id, AccountId from Contact where Id in :contactIds and AccountId != null]);
        }        
 
        for (sobject obj : sobjs){
            id rdid = (id)obj.get('rdid'); 
            if ((date)obj.get('MaxDate') != null){
                lastOppMap.put(rdid, (date)obj.get('MaxDate'));
            }
                                  
            oppCountMap.put(rdid, (integer)obj.get('oppCount'));                 
        }
        
        //if the RD has no opps, we'll need to bail back to the insert methods
        list<npe03__Recurring_Donation__c> reRollRDs = new list<npe03__Recurring_Donation__c>();
        for (npe03__Recurring_Donation__c rd : recurringDonationsToEvaluate){
            
            //only proceed if there are some opps, otherwise we need to start over from scratch 
            if (oppCountMap.containsKey(rd.id)){
                integer oppCounter = oppCountMap.get(rd.id) + 1;          
                
                Date dtForecast =  system.today().toStartOfMonth().addMonths(forecast_months);         
                           
                if (dtForecast > lastOppMap.get(rd.id)){
                    date oppclosedate = lastOppMap.get(rd.id);
                    oppclosedate = getNextDate(oppclosedate, rd);
                    //do{
                    while(oppclosedate < dtForecast) {
                        UTIL_Debug.debug('****DJH evaluate oppCounter: ' + oppCounter + ' OppCloseDate: ' + OppCloseDate + ' dtForecast: ' + dtForecast);
                        Opportunity o = new Opportunity();
                        o.OwnerId = rd.OwnerId;

                        //multicurrency?
                        if(isMultiCurrency)
                            o.put(OppCurrencyField,rd.get(RDCurrencyField));
                    
                        //account or contact?
                        if (rd.npe03__Organization__c != null){
                           o.AccountId = rd.npe03__Organization__c;
                        }
                        else if (contactMap.containsKey(rd.npe03__Contact__c)){
                            Contact c = contactMap.get(rd.npe03__Contact__c);
                            o.AccountId = c.AccountId;
                            if (ConIDForRole != null){
                                o.put('npe01__Contact_Id_For_Role__c', (string)c.id);
                            }
                        }                    
                    
                        o.CloseDate = oppclosedate;                                                                                        
                        string oName = rd.npe03__Donor_Name__c + ' ' + system.label.npe03.RecurringDonationPrefix +
                        ' (' + string.valueOf(oppCounter) + ') ' + o.CloseDate.format();
                    
                        o.Name = oName;
                        o.Amount = rd.npe03__Amount__c;
                        o.StageName = system.label.npe03.RecurringDonationStageName;                    
                        o.npe03__Recurring_Donation__c = rd.Id;                    
                        o.Recurring_Donation_Installment_Number__c = oppCounter;

                        //evaluate any custom field mappings
                        for (string s : customFieldMappingSettings.keySet()){
                            npe03__Custom_Field_Mapping__c cfm = customFieldMappingSettings.get(s);
                            o.put(cfm.npe03__Opportunity_Field__c, rd.get(cfm.npe03__Recurring_Donation_Field__c));
                        }
                    
                        if (rd.npe03__Recurring_Donation_Campaign__c != null && (rds.npe03__Add_Campaign_to_All_Opportunites__c || oppCounter == 1)){
                            o.CampaignId = rd.npe03__Recurring_Donation_Campaign__c;
                        }
                        
	                    if (rds.npe03__Record_Type__c != null){
	                        o.put('RecordTypeId', rds.npe03__Record_Type__c);
	                    }
                                            
                        oppCounter++;
                        oppInsertList.add(o);                   
                        oppclosedate = getNextDate(oppclosedate, rd);
                    }//while(oppclosedate < dtForecast);
                }
            }
            else{
                reRollRDs.add(rd);
            }
        }
        if (!reRollRDs.isEmpty())
            insertOppsOnRecurringDonationInsert(reRollRDs);
       
        set<id> RDID = new set<id>(); 
        list<Database.SaveResult> badSRs = new list<Database.SaveResult>(); 
        if(!oppInsertList.isEmpty())
        {          
            list<Opportunity> badOpps = new list<Opportunity>();
            set<id> createdOppIds = new set<id>();   
            
            Database.SaveResult[] lsr = Database.insert(oppInsertList, false);        
                    
            integer i = 0;                          
            for (Database.SaveResult s : lsr){
                if (s.isSuccess() == true){
                    //get all the new OppIds into a list
                    createdOppIds.add(s.getId());
                    RDID.add(oppInsertList[i].npe03__Recurring_Donation__c);                    
                }    
                //handle errors on insert   
                else{
                    badSRs.add(s);
                    badOpps.add(oppInsertList[i]);            
                }                     
                i++;
            }
                    
            if (ConIDForRole == null /* || Test.isRunningTest() */){
                if (createdOppIds.size() > 1 && !system.isFuture() && !system.isBatch() && !RD_ProcessControl.batchButton)
                    oppContactRolesFuture(createdOppIds); 
                else 
                    oppContactRoles(createdOppIds);                    
            }
            
            if (!badSRs.isEmpty()){
                ERR_Handler.Errors errors = ERR_Handler.getErrors(badSRs, badOpps);
                ERR_Handler.processErrors(errors.errorRecords, ERR_Handler_API.Context.RD.name());
            }
        }
        
        if (system.isFuture() || system.isBatch() || (recurringDonationsToEvaluate.size() == 1) || RD_ProcessControl.batchButton){
            updateRecurringDonationOnOppChange(RDID, null);
        }
        else{
            updateRecurringDonationOnOppChangeFuture(RDID); 
        }   
        return badSRs.size();
    }
  
    /*******************************************************************************************************
    * @description When closing an RD, either deletes or closes all of its currently open Opps. 
    * @param closedRDs The list of RD's to process
    * @param dmlWrapper Object to hold the Opps that need deleting or updating
    * @return void
    */ 
    private static void runRecurringDonationClosedActions(list<npe03__Recurring_Donation__c> closedRDs, TDTM_Runnable.DmlWrapper dmlWrapper) {
        // determine the update type - 
        npe03__Recurring_Donations_Settings__c rds = UTIL_CustomSettingsFacade.getRecurringDonationsSettings();
        //UTIL_Debug.debug('IN THE PRIVATE METHOD');
        if (!closedRDs.isEmpty() && rds.npe03__Open_Opportunity_Behavior__c != RecurringDonationCloseOptions.No_Action.name()) {
            map<Id, Opportunity> mapIdOpp = new map<Id, Opportunity>([select Id, StageName, npe03__Recurring_Donation__c 
                                    from Opportunity where npe03__Recurring_Donation__c IN :closedRDs and isClosed = false]);
         
            if (rds.npe03__Open_Opportunity_Behavior__c == RecurringDonationCloseOptions.Delete_Open_Opportunities.name()){
                //UTIL_Debug.debug('IN THE PRIVATE METHOD3');
                if (dmlWrapper != null) {
                    List<SObject> oppsToDelete = new List<SObject>();
                    for(Id oppToDeleteId : mapIdOpp.keySet()) {
                    	oppsToDelete.add(new Opportunity(Id = oppToDeleteId));
                    }
                    dmlWrapper.objectsToDelete.addAll(oppsToDelete);
                } else {
                    database.delete(mapIdOpp.values());
                }
            }              
            else if (rds.npe03__Open_Opportunity_Behavior__c == RecurringDonationCloseOptions.Mark_Opportunities_Closed_Lost.name()){
                for (Opportunity o : mapIdOpp.values()){
                    o.StageName = system.label.npe03.RecurringDonationClosedLostOpportunityStage;
                }            
                if (dmlWrapper != null)
                    dmlWrapper.objectsToUpdate.addAll((list<SObject>) mapIdOpp.values()); 
                else
                    update mapIdOpp.values();
            }
        }
    }

    /*******************************************************************************************************
    * @description Returns the last day of month for the provided date, assuming is set for
    * the 28th or later.  This is a way to avoid the last day of month getting pushed to the
    * 30th, and then the 28th.  Note that if Date Established is >= 28, we assume it instead.
    * @param dt The date to analyze
    * @return date Either the passed in date, or the last day of the month if the provided date
    * was >= the 28th.
    */ 
    private static date dtEndOfMonthFixup(Date dt, npe03__Recurring_Donation__c rd) {
        if (dt.day() >= 28) {
            // only push to end of month if we are pretty sure they weren't trying specifically for the 28th-30th.
            if (rd.npe03__Date_Established__c != null && rd.npe03__Date_Established__c.day() >= 28 &&
                rd.npe03__Date_Established__c.day() <= date.daysInMonth(dt.year(), dt.Month()))
                dt = date.newInstance(dt.year(), dt.month(), rd.npe03__Date_Established__c.day());
            else
                dt = date.newInstance(dt.year(), dt.month(), date.daysInMonth(dt.year(), dt.Month()));
        } 
        return dt;   	
    }

    /*******************************************************************************************************
    * @description Returns the next date in the sequence based on the RD schedule type 
    * @param CalcDate The date to start from
    * @param rd The Recurring Donation.
    * @return date 
    */ 
    private static date getNextDate(date CalcDate, npe03__Recurring_Donation__c rd) {
        npe03__Recurring_Donations_Settings__c rds = UTIL_CustomSettingsFacade.getRecurringDonationsSettings();
        string InstallmentType = rd.npe03__Installment_Period__c;
        
        if (InstallmentType == system.label.npe03.RecurringDonationInstallmentPeriodYearly){
            CalcDate = CalcDate.addYears(1);
        } 
        else if (InstallmentType == system.label.npe03.RecurringDonationInstallmentPeriodQuarterly){
            CalcDate = CalcDate.addMonths(3);
            CalcDate = dtEndOfMonthFixup(CalcDate, rd);
        } 
        else if (InstallmentType == system.label.npe03.RecurringDonationInstallmentPeriodMonthly){
            CalcDate = CalcDate.addMonths(1);
            CalcDate = dtEndOfMonthFixup(CalcDate, rd);
        } 
        else if (InstallmentType == system.label.npe03.RecurringDonationInstallmentPeriodWeekly){
            CalcDate = CalcDate.addDays(7);
        }
      /*  else if (InstallmentType == system.label.npe03.RecurringDonationInstallmentPeriodCustom){ 
            CalcDate = CalcDate.addDays((integer)rds.Custom_Days__c);
        }*/
        else if (InstallmentType == system.label.npe03.RecurringDonationInstallmentPeriod1stand15th){
            //increment it by one day until we hit either the 1st or 15th            
            do{
                CalcDate = CalcDate.addDays(1);
            }while (CalcDate.day() != 15 && CalcDate.day() != 1);
        }        
        else{
            map<string, npe03__Custom_Installment_Settings__c> cisMap = UTIL_ListCustomSettingsFacade.getMapCustomInstallmentSettings();
            //handle custom installment types           
            if (cisMap.containsKey(InstallmentType)){
                npe03__Custom_Installment_Settings__c c = cisMap.get(InstallmentType);
                
                if (c.npe03__Increment__c == 'Days'){
                    CalcDate = CalcDate.addDays(integer.valueOf(c.npe03__Value__c));
                }
                else if (c.npe03__Increment__c == 'Weeks'){
                    CalcDate = CalcDate.addDays(integer.valueOf(c.npe03__Value__c * 7));
                }
                else if (c.npe03__Increment__c == 'Months'){
                    CalcDate = CalcDate.addMonths(integer.valueOf(c.npe03__Value__c));
                    CalcDate = dtEndOfMonthFixup(CalcDate, rd);                    
                }
                else if (c.npe03__Increment__c == 'Years'){
                    CalcDate = CalcDate.addYears(integer.valueOf(c.npe03__Value__c));
                }           
            }
            //if its not a valid value, set it to the max last payment date plus the 
            //the open ended value so we only create one opp max                           
            else{   
                CalcDate = CalcDate.addMonths((integer)rds.npe03__Opportunity_Forecast_Months__c);
                CalcDate = dtEndOfMonthFixup(CalcDate, rd);            
            }                
        }
        return CalcDate;
    }
}