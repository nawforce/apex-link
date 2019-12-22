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
 * @author Tomas "Bobby" Hnizdil, Saleforce.org
 * @date 8/22/2013
 * @group Opportunity
 * @description class to update soft credit rollup fields on Contact with values from OpportunityContactRole and Partial_Soft_Credit__c records
 *
 * This code has been contributed by KELL Partners, and further modified and enhanced by Salesforce.org.
 */
public class RLLP_OppPartialSoftCreditRollup {
    
    /*******************************************************************************************************
    * @description calculates the Soft Credit statistics for the specified contacts.  Takes into account 
    * both OpportunityContactRoles and Partial_Soft_Credits
    * @param listCon The list of contacts to process
    * @return void
    */ 
    public static void rollupPartialSoftCredits(List<Contact> listCon) {
        // since called by RLLP_OppRollups, we can assume RLLP_OppRollup_UTIL is set up.
        // RLLP_OppRollup_UTIL oppRollupUtil = new RLLP_OppRollup_UTIL();
        
        Date currentYearStart;
        if (RLLP_OppRollup_UTIL.useFiscalYear) {
            currentYearStart = Date.newInstance(Date.Today().Year(), [SELECT FiscalYearStartMonth FROM Organization LIMIT 1].FiscalYearStartMonth, 1);
            if (currentYearStart > Date.Today()) 
                currentYearStart = currentYearStart.addYears(-1);
        } else {
            currentYearStart = Date.newInstance(Date.Today().Year(), 1, 1);
        }

        // need local variables for dynamic soql variable binding to the soql
        // strings created by our rollup utility class
        set<string> softCreditRoles = RLLP_OppRollup_UTIL.softCreditRoles;
        List<Contact> cset = listCon;
        set<string> typeset = RLLP_OppRollup_UTIL.oppTypesToExcludeCons;
        set<id> rectypeset = RLLP_OppRollup_UTIL.recordTypesToExcludeCons;
        string ocrQuery = RLLP_OppRollup_UTIL.buildSoftCreditOCRQuery();
        string pscQuery = RLLP_OppRollup_UTIL.buildSoftCreditPSCQuery();
        Set<Id> primaryOCRs = new Set<Id>();
        
        Map<Id, List<OpportunityContactRole>> opportunityContactRoles = new Map<Id, List<OpportunityContactRole>>();
        for (OpportunityContactRole ocr : database.query(ocrQuery)) {
            if (ocr.isPrimary) // keep track of primary OCR to exclude from SC rollup.
                primaryOCRs.add(ocr.Id);
            if (!opportunityContactRoles.containsKey(ocr.ContactId)) 
                opportunityContactRoles.put(ocr.ContactId, new List<OpportunityContactRole>());
            opportunityContactRoles.get(ocr.ContactId).add(ocr);
        }
        
        Map<Id, List<Partial_Soft_Credit__c>> partialSoftCredits = new Map<Id, List<Partial_Soft_Credit__c>>();
        for (Partial_Soft_Credit__c psr : database.query(pscQuery)) {
            if (!partialSoftCredits.containsKey(psr.Contact__c)) 
                partialSoftCredits.put(psr.Contact__c, new List<Partial_Soft_Credit__c>());
            partialSoftCredits.get(psr.Contact__c).add(psr);
        }
    
        List<Contact> toUpdate = new List<Contact>();
        for (Contact c : listCon) {
            Contact newC = new Contact(Id = c.Id,
                                       First_Soft_Credit_Amount__c = 0,
                                       First_Soft_Credit_Date__c = null,
                                       
                                       Largest_Soft_Credit_Amount__c = 0,
                                       Largest_Soft_Credit_Date__c = null,
                                       
                                       Last_Soft_Credit_Amount__c = 0,
                                       Last_Soft_Credit_Date__c = null,                                       
                                       
                                       npo02__Soft_Credit_Total__c = 0,
                                       Number_of_Soft_Credits__c = 0,

                                       npo02__Soft_Credit_This_Year__c = 0,
                                       Number_of_Soft_Credits_This_Year__c = 0,
                                       
                                       npo02__Soft_Credit_Last_Year__c = 0,
                                       Number_of_Soft_Credits_Last_Year__c = 0,
                                       
                                       npo02__Soft_Credit_Two_Years_Ago__c = 0,
                                       Number_of_Soft_Credits_Two_Years_Ago__c = 0,
                                       
                                       Soft_Credit_Last_N_Days__c = 0,
                                       Number_of_Soft_Credits_Last_N_Days__c = 0
                                       );
            
            if (RLLP_OppRollup_UTIL.isMultiCurrency()) {
                newC.put('CurrencyIsoCode', c.get('CurrencyIsoCode'));
            }
            
            Set<Id> ocrForPSCIds = new Set<Id>();
            if (partialSoftCredits.containsKey(c.Id)) {
                for (Partial_Soft_Credit__c psc : partialSoftCredits.get(c.Id)) {
                    if (!primaryOCRs.contains(psc.Contact_Role_ID__c))
                        newC = countPartialSoftCredits(newC, psc, currentYearStart);
                    ocrForPSCIds.add(psc.Contact_Role_ID__c);
                }
            }
            
            Set<Id> processedOpportunityIds = new Set<Id>();
            if (opportunityContactRoles.containsKey(c.Id)) {
                for (OpportunityContactRole ocr : opportunityContactRoles.get(c.Id)) {
                    if (ocrForPSCIds.contains(ocr.Id)) continue; // partial soft credit
                    if (processedOpportunityIds.contains(ocr.OpportunityId)) continue;
                    if (!primaryOCRs.contains(ocr.Id))
                        newC = countOpportunityContactRoles(newC, ocr, currentYearStart);
                    processedOpportunityIds.add(ocr.OpportunityId);
                }
            }
            
            if (newC.First_Soft_Credit_Amount__c != c.First_Soft_Credit_Amount__c
             || newC.First_Soft_Credit_Date__c != c.First_Soft_Credit_Date__c

             || newC.Largest_Soft_Credit_Amount__c != c.Largest_Soft_Credit_Amount__c
             || newC.Largest_Soft_Credit_Date__c != c.Largest_Soft_Credit_Date__c

             || newC.Last_Soft_Credit_Amount__c != c.Last_Soft_Credit_Amount__c
             || newC.Last_Soft_Credit_Date__c != c.Last_Soft_Credit_Date__c

             || newC.npo02__Soft_Credit_Total__c != c.npo02__Soft_Credit_Total__c
             || newC.Number_of_Soft_Credits__c != c.Number_of_Soft_Credits__c

             || newC.npo02__Soft_Credit_This_Year__c != c.npo02__Soft_Credit_This_Year__c
             || newC.Number_of_Soft_Credits_This_Year__c != c.Number_of_Soft_Credits_This_Year__c

             || newC.npo02__Soft_Credit_Last_Year__c != c.npo02__Soft_Credit_Last_Year__c
             || newC.Number_of_Soft_Credits_Last_Year__c != c.Number_of_Soft_Credits_Last_Year__c
             
             || newC.npo02__Soft_Credit_Two_Years_Ago__c != c.npo02__Soft_Credit_Two_Years_Ago__c
             || newC.Number_of_Soft_Credits_Two_Years_Ago__c != c.Number_of_Soft_Credits_Two_Years_Ago__c
             
             || newC.Soft_Credit_Last_N_Days__c != c.Soft_Credit_Last_N_Days__c
             || newC.Number_of_Soft_Credits_Last_N_Days__c != c.Number_of_Soft_Credits_Last_N_Days__c
             ) {
                toUpdate.add(newC);
            }
        }
        update toUpdate;
    }
    
    /*******************************************************************************************************
    * @description updates the Soft Credit statistics for the specified partial soft credit
    * @param c The Contact
    * @param psc The Partial Soft Credit
    * @param currentYearStart The date that current year starts (to take into account fiscal years)
    * @return Contact The updated Contact record
    */ 
    private static Contact countPartialSoftCredits(Contact c, Partial_Soft_Credit__c psc, Date currentYearStart) {
        decimal amount = psc.Amount__c != null ? psc.Amount__c : 0;
        string pscCurrencyIsoCode = RLLP_OppRollup_UTIL.isMultiCurrency() ? (string)psc.get('CurrencyIsoCode') : '';
        return countSoftCreditsSharedLogic(c, amount, pscCurrencyIsoCode, psc.Opportunity__r.CloseDate, currentYearStart);
    }

    /*******************************************************************************************************
    * @description updates the Soft Credit statistics for the specified OpportunityContactRole
    * @param c The Contact
    * @param ocr The OpportunityContactRole
    * @param currentYearStart The date that current year starts (to take into account fiscal years)
    * @return Contact The updated Contact record
    */ 
    private static Contact countOpportunityContactRoles(Contact c, OpportunityContactRole ocr, Date currentYearStart) {
        decimal amount = ocr.Opportunity.Amount != null ? ocr.Opportunity.Amount : 0;
        string oppCurrencyIsoCode = RLLP_OppRollup_UTIL.isMultiCurrency() ? (string)ocr.Opportunity.get('CurrencyIsoCode') : '';
        return countSoftCreditsSharedLogic(c, amount, oppCurrencyIsoCode, ocr.Opportunity.CloseDate, currentYearStart);
    }       

    /*******************************************************************************************************
    * @description updates the Soft Credit statistics for the specified credit information
    * @param c The Contact
    * @param amount The Amount to credit
    * @param string currencyIsoCode The currency code of the amount to credit, if multicurrency is enabled.
    * @param oppDate The CloseDate of the Opp
    * @param currentYearStart The date that current year starts (to take into account fiscal years)
    * @return Contact The updated Contact record
    */ 
    private static Contact countSoftCreditsSharedLogic(Contact c, decimal amount, string currencyIsoCode, Date oppDate, Date currentYearStart) {
        // calculate amounts into correct currency if needed
        if (RLLP_OppRollup_UTIL.isMultiCurrency())
           amount = RLLP_OppRollup_UTIL.convertCurrency(currencyIsoCode, (string)c.get('CurrencyIsoCode'), amount);
                
        if (oppDate != null) {
            
            // first soft credit
            if (c.First_Soft_Credit_Date__c == null || c.First_Soft_Credit_Date__c > oppDate) {
                c.First_Soft_Credit_Date__c = oppDate;
                c.First_Soft_Credit_Amount__c = amount;
            }
            
            // last soft credit
            if (c.Last_Soft_Credit_Date__c == null || c.Last_Soft_Credit_Date__c < oppDate) {
                c.Last_Soft_Credit_Date__c = oppDate;
                c.Last_Soft_Credit_Amount__c = amount;
            }
            
            //largest soft credit
            if (c.Largest_Soft_Credit_Amount__c < amount) {
                c.Largest_Soft_Credit_Date__c = oppDate;
                c.Largest_Soft_Credit_Amount__c = amount;
            }

            // last2YearSoftCredit
            if (oppDate >= currentYearStart.addYears(-2) && oppDate < currentYearStart.addYears(-1)) { 
                c.Number_of_Soft_Credits_Two_Years_Ago__c++;
                c.npo02__Soft_Credit_Two_Years_Ago__c += amount;                
            }
            
            // lastYearSoftCredit
            else if (oppDate >= currentYearStart.addYears(-1) && oppDate < currentYearStart) { 
                c.Number_of_Soft_Credits_Last_Year__c++;
                c.npo02__Soft_Credit_Last_Year__c += amount;                
            }
            
            // current year Soft Credit
            else if (oppDate >= currentYearStart && oppDate < currentYearStart.addYears(+1)) {
                c.Number_of_Soft_Credits_This_Year__c++;
                c.npo02__Soft_Credit_This_Year__c += amount;
            }
            
            // N day Soft Credit
            integer nday = UTIL_CustomSettingsFacade.getHouseholdsSettings().npo02__Rollup_N_Day_Value__c.intValue();
            if (oppDate.daysBetween(Date.Today()) <= nday) {
                c.Number_of_Soft_Credits_Last_N_Days__c++;
                c.Soft_Credit_Last_N_Days__c += amount;                
            }
        }
        
        // total soft credits
        c.Number_of_Soft_Credits__c++;
        c.npo02__Soft_Credit_Total__c += amount;
        return c;
    }
}