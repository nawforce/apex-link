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
/*
 * @author Tadeas Brzak
 * @date 8/16/2013
 * @group Opportunity
 * @description Controller for FindGifts VF page allowing users to find matched gifts for a particular Opportunity using custom filters.
 * This code has been contributed by KELL Partners.
 */
public with sharing class MTCH_FindGifts_CTRL {
    
    /* @description the Opportunity of the Matching Gift */
    public Opportunity opp {get; set;}
    
    /* @description the matching gift percentage the account for the matching gift provides */
    public double matchingGiftPercent {get; private set;}
    
    /* @description the list of potential opportunities in the top section of the page */
    public List<Opportunity> potentialGifts {get; private set;}

    /* @description the list of potential opportunities in the bottom search section of the page */
    public List<Opportunity> potentialGifts2 {get; private set;}

    /* @description a map that specifies which opps are checked on to be included in the match */
    public Map<Id, Boolean> selection {get; set;}

    /* @description where the search section is displayed or not */
    public Boolean showSearchSection {get; set;}

    /* @description a dummy contact object to use for binding the search parameter fields to */
    public Contact searchFieldsWrapper {get; set;}

    /* @description a set of closed won Opp stages */
    private Set<String> closedWonStages;
    
    /*******************************************************************************************************
    * @description constructor for the page
    * @param controller the StandardController for the page
    * @return nothing
    */ 
    public MTCH_FindGifts_CTRL(ApexPages.StandardController controller){
        opp = (Opportunity) controller.getRecord();
        init();
    }
    
    /*******************************************************************************************************
    * @description Initializes the page by querying for all potential opportunities that could be matched
    * with the Opportunity of the page.
    * @return void
    */ 
    private void init(){
        Id oppId = opp.Id;
        String oppQuery = 'SELECT Id, Name, AccountId, Account.Name, Amount, StageName, Account.Matching_Gift_Percent__c';

        if (userInfo.isMultiCurrencyOrganization()) {
            oppQuery += ', CurrencyIsoCode';
        }

        oppQuery +=  ' FROM Opportunity WHERE Id = :oppId';
        opp = Database.query(oppQuery);
        matchingGiftPercent = opp.Account.Matching_Gift_Percent__c == null ? 100 : opp.Account.Matching_Gift_Percent__c;
        
        // if the opp Amount is null, treat as $0 and still load the page.
        if (opp.Amount == null) 
            opp.Amount = 0;

        closedWonStages = new Set<String>();
        for (OpportunityStage s : [SELECT MasterLabel FROM OpportunityStage WHERE IsClosed = true AND IsWon = true]){
            closedWonStages.add(s.MasterLabel);
        }
        
        potentialGifts = [SELECT Id, Name, AccountId, Account.Name, Primary_Contact__c, Primary_Contact__r.Name, Amount, CloseDate, 
                                Matching_Gift__c, Matching_Gift_Account__c, Matching_Gift_Account__r.Matching_Gift_Percent__c,
                                (SELECT Id, ContactId FROM OpportunityContactRoles WHERE IsPrimary = true AND IsDeleted = false)
                        FROM Opportunity 
                        WHERE ((Matching_Gift__c = null AND StageName IN :closedWonStages AND Account.Name = :opp.Account.Name AND (Matching_Gift_Status__c = 'Potential' or Matching_Gift_Status__c = 'Submitted')) 
                        OR (Matching_Gift__c = null AND StageName IN :closedWonStages AND Matching_Gift_Account__r.Name = :opp.Account.Name AND (Matching_Gift_Status__c = 'Potential' or Matching_Gift_Status__c = 'Submitted')) 
                        OR Matching_Gift__c = :opp.Id)
                        AND Id != :opp.Id 
                        ORDER BY Name];
        selection = new Map<Id, Boolean>();
        for (Opportunity o : potentialGifts){
            if (o.Matching_Gift__c == opp.Id){
                selection.put(o.Id, true);
            } else {
                selection.put(o.Id, false);
            }
        }
        
        searchFieldsWrapper = new Contact();
    }
    
    /*******************************************************************************************************
    * @description the number of Opps displayed
    */ 
    public integer numberOfOpps {
        get {
            return potentialGifts == null ? 0 : potentialGifts.size();
        }
    }
    
    /*******************************************************************************************************
    * @description temporary Opp to hold the current Total Selected Amount and currency
    */
    public Opportunity oppTotalSelected {
        get {
            if (oppTotalSelected == null) {
                oppTotalSelected = new Opportunity();
                if (userInfo.isMultiCurrencyOrganization()) 
                    oppTotalSelected.put('CurrencyIsoCode', opp.get('CurrencyIsoCode'));
            }
            oppTotalSelected.Amount = getTotalSelected();
            return oppTotalSelected;
        }
        private set;
    }
    
    /*******************************************************************************************************
    * @description temporary Opp to hold the current Unaccounted Amount and currency
    */
    public Opportunity oppTotalUnaccounted {
        get {
            if (oppTotalUnaccounted == null) {
                oppTotalUnaccounted = new Opportunity();
                if (userInfo.isMultiCurrencyOrganization()) 
                    oppTotalUnaccounted.put('CurrencyIsoCode', opp.get('CurrencyIsoCode'));
            }
            oppTotalUnaccounted.Amount = opp.Amount - oppTotalSelected.Amount;
            return oppTotalUnaccounted;
        }
        private set;
    }
    
    /*******************************************************************************************************
    * @description adds up the amount of all selected opps matched or to be matched, based on matching percent.
    * @return Double the total amount
    */ 
    private Double getTotalSelected(){
        Double totalSelected = 0;
        
        for (Opportunity o : potentialGifts){
            if (selection.get(o.Id) && o.Amount != null){
                totalSelected += (matchingGiftPercent/100 * o.Amount);
            }
        }
        return totalSelected;
    }
    
    /*******************************************************************************************************
    * @description Saves changes made in the page by creating OCR's and Partial Soft Credits.
    * @return True if success, false if failure
    */ 
    private Boolean saveChanges(){
        /* this commented out since partial allocations are done in a separate page, so we have to allow > the opp total.
        if (getTotalSelected() > opp.Amount) {
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.INFO, 'Total selected amount cannot exceed the matching gift amount.'));
            return false;
        }*/
        
        List<OpportunityContactRole> roles = new List<OpportunityContactRole>();
        List<Partial_Soft_Credit__c> softCredits = new List<Partial_Soft_Credit__c>();
        List<OpportunityContactRole> listOCRDelete = new List<OpportunityContactRole>();
        List<Partial_Soft_Credit__c> listPSCDelete = new List<Partial_Soft_Credit__c>();
        
        // get our current psc's on the matching gift
        list<Partial_Soft_Credit__c> listPSCExisting = [select Id, Contact__c, Opportunity__c, Role_Name__c, Contact_Role_ID__c 
            from Partial_Soft_Credit__c where Opportunity__c = :opp.Id];
        
        for (Opportunity o : potentialGifts){
            if (selection.get(o.Id) && o.Matching_Gift__c == null){
                if (closedWonStages.contains(opp.StageName))
                    o.Matching_Gift_Status__c = 'Received'; 
                o.Matching_Gift__c = opp.Id;
                if (o.Matching_Gift_Account__c == null)
                    o.Matching_Gift_Account__c = opp.AccountId;
                // if matching gift has Primary contact role, create partial soft credit
                if (!o.OpportunityContactRoles.isEmpty()){
                    string role = UTIL_CustomSettingsFacade.getHouseholdsSettings().Matched_Donor_Role__c;
                    softCredits.add(new Partial_Soft_Credit__c(
                        Amount__c = o.Amount * matchingGiftPercent/100,
                        Contact__c = o.OpportunityContactRoles[0].ContactId,
                        Role_Name__c = role,
                        Opportunity__c = opp.Id
                    ));
                    // now that PSC trigger handlers creates OCRs if needed, no need to create them here. 
                }
            }
            if (!selection.get(o.Id) && o.Matching_Gift__c != null){
                o.Matching_Gift_Status__c = 'Potential';
                o.Matching_Gift__c = null;
                // remove existing partial soft credit...? How to identify them?
                // how about if there is only 1 psc on the opp for this unmatched opp's primary contact, remove it.
                if (listPSCExisting != null && !o.OpportunityContactRoles.isEmpty()) {
                    Partial_Soft_Credit__c pscDel;
                    integer cPsc = 0;
                    for (Partial_Soft_Credit__c psc : listPSCExisting) {
                        if (psc.Contact__c == o.OpportunityContactRoles[0].ContactId) {
                            cPsc++;
                            pscDel = psc;
                        }
                    }
                    if (pscDel != null && cPsc == 1) {
                        listPSCDelete.add(pscDel);
                        if (pscDel.Contact_Role_ID__c != null)
                            listOCRDelete.add(new OpportunityContactRole(Id=pscDel.Contact_Role_ID__c));
                    }
                }
            }
        }
        try {
            update potentialGifts;
            if (softCredits.size() > 0)
                insert softCredits;
            if (listPSCDelete.size() > 0)
               delete listPSCDelete;
            if (listOCRDelete.size() > 0)
               delete listOCRDelete;
            return true;
        } catch (Exception ex) {
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, ex.getMessage()));
            return false;
        }
    }
    
    /*******************************************************************************************************
    * @description ActionMethod to Save and Close the page.
    * @return PageReference url of opp if save success, otherwise null if failure so errors displayed on page.
    */ 
    public PageReference saveAndClose(){
        if (saveChanges()) {
            return new PageReference('/'+opp.Id);
        }
        init();
        return null;
    }
    
    /*******************************************************************************************************
    * @description empty ActionMethod that causes page to get fully refreshed
    * @return null, so page is rerendered
    */ 
    public PageReference refresh() {
        return null;
    }
    
    /*******************************************************************************************************
    * @description tells the page to show the search section
    * @return void
    */ 
    public void showSearchSection() {
        showSearchSection = true;
    }
    
    /*******************************************************************************************************
    * @description tells the page to query based on the search fields, and display the results
    * @return null
    */ 
    public PageReference searchMore() {    
        
        if (searchFieldsWrapper.AccountId == null &&
            searchFieldsWrapper.ReportsToId == null &&
            searchFieldsWrapper.First_Soft_Credit_Amount__c == null &&
            searchFieldsWrapper.First_Soft_Credit_Date__c == null &&
            searchFieldsWrapper.Last_Soft_Credit_Date__c == null) {
                ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.WARNING, Label.mtchSearchCriteriaEmpty));
                return null;
        }
                
        String query = 'SELECT Id, Name, AccountId, Account.Name, Primary_Contact__c, Primary_Contact__r.Name, Amount, CloseDate, Matching_Gift__c ,'+
                            '  (SELECT Id, ContactId FROM OpportunityContactRoles WHERE IsPrimary = true AND IsDeleted = false) ' +
                            'FROM Opportunity ' +
                            'WHERE StageName IN :closedWonStages ' +
                            'AND Matching_Gift__c = null ' +
                            (searchFieldsWrapper.AccountId != null ? 'AND AccountId = \'' + searchFieldsWrapper.AccountId + '\'' : '') +
                            (searchFieldsWrapper.ReportsToId != null ? 'AND Primary_Contact__c = \'' + searchFieldsWrapper.ReportsToId + '\'' : '') +
                            (searchFieldsWrapper.First_Soft_Credit_Amount__c != null ? 'AND Amount = ' + searchFieldsWrapper.First_Soft_Credit_Amount__c + ' ' : '') +
                            (searchFieldsWrapper.First_Soft_Credit_Date__c != null ? 'AND CloseDate >= ' + String.valueOf(searchFieldsWrapper.First_Soft_Credit_Date__c).substring(0, 10) + ' ' : '') +
                            (searchFieldsWrapper.Last_Soft_Credit_Date__c != null ? 'AND CloseDate <= ' + String.valueOf(searchFieldsWrapper.Last_Soft_Credit_Date__c).substring(0, 10) + ' ' : '') +
                            'AND Id NOT IN :potentialGifts ' +
                            'AND Id != \'' + opp.Id + '\' ' +
                            'ORDER BY Name';
        list<Opportunity> potentialGifts2 = Database.query(query);
        for (Opportunity o : potentialGifts2){
            if (o.Matching_Gift__c == opp.Id){
                selection.put(o.Id, true);
            } else {
                selection.put(o.Id, false);
            }
        }
        
        // now rather than displaying in a seperate list, let's just add them to our first list!
        potentialGifts.addAll(potentialGifts2);
        
        return null;
    }
}