/*
    Copyright (c) 2014, Salesforce.org
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
* @group Allocations
* @group-content ../../ApexDocContent/Allocations.htm
* @description Allocations Rollups to General Accounting Units
*/
public without sharing class ALLO_Rollup_SCHED extends UTIL_AbstractRollup_BATCH implements Schedulable {

    /*******************************************************************************************************
    * @description Schedulable execute method executes this batch job.
    ********************************************************************************************************/
    public void execute(SchedulableContext context) {
        try {
            ALLO_Rollup_SCHED rollup = new ALLO_Rollup_SCHED(RLLP_OppRollup_UTIL.BATCH_SIZE);
            rollup.runRollups();
        } catch(Exception e) {
            ERR_Handler.processError(e, ERR_Handler_API.Context.ALLO);
        }
    }

    /*******************************************************************************************************
    * @description Blank constructor, runs allocations rollup on all records in a batch of 200.
    ********************************************************************************************************/
    public ALLO_Rollup_SCHED () {
        super (200);
    }

    /*******************************************************************************************************
    * @description Constructor that allows specifiying a batch size with which to rollup all GAU Allocations.
    * @param batchSz The batch size.
    ********************************************************************************************************/
    public ALLO_Rollup_SCHED (integer batchSz) {
        super (batchSz);
    }
    
    /*******************************************************************************************************
    * @description Constructor takes an id of a general accounting unit and rolls up GAU Allocations to it.
    * This method is used for in the rollup button on General Accounting Unit.
    * @param parentRecId The id of the general accounting unit to roll up to.
    ********************************************************************************************************/
    public ALLO_Rollup_SCHED (id parentRecId) {
        super (parentRecId);
    }

    /*******************************************************************************************************
    * @description Constructor takes a set of ids of general accounting units and rolls up GAU Allocations 
    * to them. With this method, we could easily implement trigger based rollups on GAU Allocations, 
    * although they weren't implemented in this case due to high opportunity DML overhead.
    * @param parentRecId The set of ids of the general accounting units to roll up to.
    ********************************************************************************************************/
    public ALLO_Rollup_SCHED (set<id> parentRecIds) {
        super (parentRecIds);
    }
    
    /*******************************************************************************************************
    * @description This method tells the super class, UTIL_AbstractRollup_BATCH, which object this class is
    * rolling up to. In this case, we are rolling up to General Accounting Unit.
    * @return string The name of the object to roll up to.
    ********************************************************************************************************/
    public override string setParentObjName() {
        return UTIL_Namespace.StrTokenNSPrefix('General_Accounting_Unit__c');
    }

    /*******************************************************************************************************
    * @description This method defines rollup specs for the super class. These specs contain information
    * necessary for the rollup code.
    * @return list<RollupSpec> The RollupSpecs for this class.
    ********************************************************************************************************/
    public virtual override list<RollupSpec> setRollupSpecs() {

        //get allocations settings
        Allocations_Settings__c alloSettings = UTIL_CustomSettingsFacade.getAllocationsSettings();
        if (alloSettings.Rollup_N_Day_Value__c==null) alloSettings.Rollup_N_Day_Value__c = 365;
        string strFiscalOrCalendarYear = alloSettings.Use_Fiscal_Year_for_Rollups__c ? 'Fiscal_Year' : 'Calendar_Year';
        string strExcludeOppRecTypesQuery = '';
        string strExcludeOppTypesQuery = '';

        //build string of opp rec type names for query
        if (alloSettings.Excluded_Opp_RecTypes__c != null) {
            strExcludeOppRecTypesQuery = 'AND Opportunity__r.RecordTypeId NOT IN ' + convertToQueryListFormat(alloSettings.Excluded_Opp_Rectypes__c);
        }

        //make  opp types
        if (alloSettings.Excluded_Opp_Types__c != null) {
            strExcludeOppTypesQuery = 'AND Opportunity__r.Type NOT IN ' + convertToQueryListFormat(alloSettings.Excluded_Opp_Types__c);
        }

        list<RollupSpec> mySpecs = new list<RollupSpec>();
        
        //total all time / this year / last year / two years ago
        RollupSpec rs = new RollupSpec(UTIL_Namespace.StrTokenNSPrefix('Allocation__c'), UTIL_Namespace.StrTokenNSPrefix('General_Accounting_Unit__c'));
        rs.filter = 'Opportunity__r.IsWon = TRUE AND Opportunity__r.IsClosed = TRUE ';
        //add built out exclusions to query
        rs.filter += strExcludeOppRecTypesQuery + strExcludeOppTypesQuery;
        rs.aggregateToParentFieldnameMap = new map<string, string> {
            'SUM(Amount__c)' => UTIL_Namespace.StrTokenNSPrefix('Total_Allocations__c'),
            'COUNT_DISTINCT(ID)' => UTIL_Namespace.StrTokenNSPrefix('Total_Number_of_Allocations__c'),
            'MIN(Amount__c)' => UTIL_Namespace.StrTokenNSPrefix('Smallest_Allocation__c'),
            'MAX(Amount__c)' => UTIL_Namespace.StrTokenNSPrefix('Largest_Allocation__c'),
            'AVG(Amount__c)' => UTIL_Namespace.StrTokenNSPrefix('Average_Allocation__c'),
            'MIN(Opportunity__r.CloseDate)' => UTIL_Namespace.StrTokenNSPrefix('First_Allocation_Date__c'),
            'MAX(Opportunity__r.CloseDate)' => UTIL_Namespace.StrTokenNSPrefix('Last_Allocation_Date__c')
        };
        
        rs.subgroupExpression = strFiscalOrCalendarYear+'(Opportunity__r.CloseDate)';
        rs.subgroupValueToParentFieldnameMap = new map<string, map<object, string>> {
            'SUM(Amount__c)' => new map<object, string> { 
                System.Today().Year() => UTIL_Namespace.StrTokenNSPrefix('Total_Allocations_This_Year__c'), 
                System.Today().AddYears(-1).Year() => UTIL_Namespace.StrTokenNSPrefix('Total_Allocations_Last_Year__c'),
                System.Today().AddYears(-2).Year() => UTIL_Namespace.StrTokenNSPrefix('Total_Allocations_Two_Years_Ago__c')
            },
            'COUNT_DISTINCT(ID)' => new map<object, string> { 
                System.Today().Year() => UTIL_Namespace.StrTokenNSPrefix('Number_of_Allocations_This_Year__c'), 
                System.Today().AddYears(-1).Year() => UTIL_Namespace.StrTokenNSPrefix('Number_of_Allocations_Last_Year__c'),
                System.Today().AddYears(-2).Year() => UTIL_Namespace.StrTokenNSPrefix('Number_of_Allocations_Two_Years_Ago__c')
            }
        };

        if (ALLO_Multicurrency_TDTM.isMultiCurrency())
            rs.additionalQueryFields = new set<string>{'CurrencyIsoCode'};

        mySpecs.add(rs);

        //last 365 days
        RollupSpec rs2 = new RollupSpec(UTIL_Namespace.StrTokenNSPrefix('Allocation__c'), UTIL_Namespace.StrTokenNSPrefix('General_Accounting_Unit__c'));
        rs2.filter = 'Opportunity__r.IsWon = TRUE AND Opportunity__r.IsClosed = TRUE AND Opportunity__r.CloseDate = LAST_N_DAYS:' + alloSettings.Rollup_N_Day_Value__c.intValue() + ' ';
        rs2.filter += strExcludeOppRecTypesQuery + strExcludeOppTypesQuery;
        rs2.aggregateToParentFieldnameMap = new map<string, string> {
            'SUM(Amount__c)' => UTIL_Namespace.StrTokenNSPrefix('Total_Allocations_Last_N_Days__c'),
            'COUNT_DISTINCT(ID)' => UTIL_Namespace.StrTokenNSPrefix('Number_of_Allocations_Last_N_Days__c')
        };

        if (ALLO_Multicurrency_TDTM.isMultiCurrency())
            rs2.additionalQueryFields = new set<string>{'CurrencyIsoCode'};

        mySpecs.add(rs2);
        
        return mySpecs;
    }

    /*******************************************************************************************************
    * @description Converts a semicolon delimited string, e.g. "str1;str2;str3", into a SOQL friendly list 
    * format, e.g. "('str1','str2','str3')".
    * @param str The semicolon delimited string to convert.
    * @return string The converted string in SOQL friendly list format.
    ********************************************************************************************************/
    public static string convertToQueryListFormat(string str) {
        string results = '(\'';

        for (string s : new set<string>(str.split(';'))) {
            results += s + '\',\'';
        }
        //remove trailing quote and comma, close parenthesis
        results = results.subString(0,results.length()-2);
        results += ') ';

        return results;
    }

    /*******************************************************************************************************
    * @description This method allows processing results after rollup calculations are complete. In this 
    * case, for multicurrency enabled organizations, we're converting the rolled up currency fields from the 
    * organization's default currency to the currency of the general accounting unit.
    * @param objMap A map of general accounting unit ids to general accounting units.
    * @return void This method modifies the objMap parameter if needed, and returns nothing.
    ********************************************************************************************************/
    public override void customFinalProcessing (map<id, sobject> objMap) {
        if (ALLO_Multicurrency_TDTM.isMultiCurrency()) {
            for (sObject obj : objMap.values()) {
                obj.put('Total_Allocations__c', ALLO_Multicurrency_TDTM.ConvertFromCorporate((string)obj.get('CurrencyIsoCode'),(decimal)obj.get('Total_Allocations__c')));
                obj.put('Total_Allocations_This_Year__c', ALLO_Multicurrency_TDTM.ConvertFromCorporate((string)obj.get('CurrencyIsoCode'),(decimal)obj.get('Total_Allocations_This_Year__c')));
                obj.put('Total_Allocations_Last_Year__c', ALLO_Multicurrency_TDTM.ConvertFromCorporate((string)obj.get('CurrencyIsoCode'),(decimal)obj.get('Total_Allocations_Last_Year__c')));
                obj.put('Total_Allocations_Two_Years_Ago__c', ALLO_Multicurrency_TDTM.ConvertFromCorporate((string)obj.get('CurrencyIsoCode'),(decimal)obj.get('Total_Allocations_Two_Years_Ago__c')));
                obj.put('Smallest_Allocation__c', ALLO_Multicurrency_TDTM.ConvertFromCorporate((string)obj.get('CurrencyIsoCode'),(decimal)obj.get('Smallest_Allocation__c')));
                obj.put('Largest_Allocation__c', ALLO_Multicurrency_TDTM.ConvertFromCorporate((string)obj.get('CurrencyIsoCode'),(decimal)obj.get('Largest_Allocation__c')));
                obj.put('Average_Allocation__c', ALLO_Multicurrency_TDTM.ConvertFromCorporate((string)obj.get('CurrencyIsoCode'),(decimal)obj.get('Average_Allocation__c')));
                obj.put('Total_Allocations_Last_N_Days__c', ALLO_Multicurrency_TDTM.ConvertFromCorporate((string)obj.get('CurrencyIsoCode'),(decimal)obj.get('Total_Allocations_Last_N_Days__c')));
            }
        }
    }
}