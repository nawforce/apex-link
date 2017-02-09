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
* @description Tests specific to Opportunity Allocations rollup to General Accounting Units. 
*/

@isTest
private with sharing class ALLO_Rollup_TEST {

    /*********************************************************************************************************
    * @description If you only want to run a single test in this class, fill in its name here.
    * If you want to run all tests, then use '*'
    */
    private static string strTestOnly = '*';

    /*******************************************************************************************************
    * @description Setup of allocations settings.
    * @param alloset An instance of Allocations_Settings__c custom setting. Calling this method with a null
    * parameter uses default settings.
    * @return void Modifies settings, returns nothing.
    ********************************************************************************************************/
    private static void setupSettings (Allocations_Settings__c alloset) {
        //set running user to avoid dml issue            
        System.runAs(new User(id = UserInfo.getUserId())){
            if (alloset!=null)
                UTIL_CustomSettingsFacade.getAllocationsSettingsForTests(alloset);
            else 
                UTIL_CustomSettingsFacade.getAllocationsSettingsForTests(new Allocations_Settings__c());

        }
    }

    /** @description Tests rollup button on general accounting units. */
    static testMethod void rollupBTN() {
        if (strTestOnly != '*' && strTestOnly != 'rollupBTN') return;
        setupSettings(null);

        Account acc = new Account(Name='foo');
        insert acc;
        
        list<Opportunity> oppsForInsert = new list<Opportunity>();
        oppsForInsert.add(new Opportunity(Name='foo', Amount = 8, AccountID=acc.id, CloseDate=system.today(), StageName=UTIL_UnitTestData_TEST.getClosedWonStage()));
        oppsForInsert.add(new Opportunity(Name='foo', Amount = 8, AccountID=acc.id, CloseDate=system.today().addYears(-2), StageName=UTIL_UnitTestData_TEST.getClosedWonStage()));
        insert oppsForInsert;
        
        General_Accounting_Unit__c gau = new General_Accounting_Unit__c(Name='foo');
        insert gau;

        list<Allocation__c> alloForInsert = new list<Allocation__c>();
        alloForInsert.add(new Allocation__c(Opportunity__c=oppsForInsert[0].id, Amount__c = 8, General_Accounting_Unit__c = gau.id));
        alloForInsert.add(new Allocation__c(Opportunity__c=oppsForInsert[1].id, Amount__c = 8, General_Accounting_Unit__c = gau.id));
        insert alloForInsert;

        PageReference pageRef = Page.ALLO_RollupBTN;
    	Test.setCurrentPage(pageRef);

    	ALLO_RollupBTN_CTRL ctrl = new ALLO_RollupBTN_CTRL(new ApexPages.StandardController(gau));

    	ctrl.buttonClick();

    	list<General_Accounting_Unit__c> queryGAU = [SELECT Id, First_Allocation_Date__c, Largest_Allocation__c, Last_Allocation_Date__c, Number_of_Allocations_Last_N_Days__c, Number_of_Allocations_Last_Year__c, Number_of_Allocations_This_Year__c, 
    												Number_of_Allocations_Two_Years_Ago__c, Smallest_Allocation__c, Total_Allocations__c, Total_Allocations_Last_N_Days__c, Total_Allocations_Last_Year__c, Total_Allocations_This_Year__c, 
    												Total_Allocations_Two_Years_Ago__c, Total_Number_of_Allocations__c FROM General_Accounting_Unit__c WHERE Id = :gau.id];
    	system.assertEquals(16, queryGAU[0].Total_Allocations__c, 'Total allocations should equal 16.');
    	system.assertEquals(system.today(), queryGAU[0].Last_Allocation_Date__c, 'Last allocation should be today.');
    	system.assertEquals(system.today().addYears(-2), queryGAU[0].First_Allocation_Date__c, 'First allocation should be two years ago.');
    	system.assertEquals(8, queryGAU[0].Total_Allocations_This_Year__c, 'Total allocations this year should equal 8.');
    	system.assertEquals(0, queryGAU[0].Total_Allocations_Last_Year__c, 'Total allocations this year should equal 0.');
    	system.assertEquals(8, queryGAU[0].Total_Allocations_Two_Years_Ago__c, 'Total allocations two years ago should equal 8.');
    	system.assertEquals(8, queryGAU[0].Total_Allocations_Last_N_Days__c, 'Total allocations last 365 days should equal 8.');

	}
}