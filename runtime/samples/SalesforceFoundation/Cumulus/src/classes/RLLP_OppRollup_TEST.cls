/*
    Copyright (c) 2009, Salesforce.org
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
* @date 2011
* @group Rollups
* @group-content ../../ApexDocContent/Rollups.htm
* @description Test class for Opportunity Rollup functionality
*/
@isTest
public with sharing class RLLP_OppRollup_TEST {

    /*********************************************************************************************************
    * @description If you only want to run a single test in this class, fill in its name here.
    * If you want to run all tests, then use '*'
    */
    private static string strTestOnly = '*';
    
    /** @description Today's date. */
    static Date datToday = System.Today();
    /** @description The date of January 1st, last year. */
    static Date dat1YearAgo = Date.newInstance( datToday.year()-1,1,1);
    /** @description The date of January 1st, two years ago. */
    static Date dat2YearAgo = Date.newInstance( datToday.year()-2,1,1);
    /** @description The date of January 1st, four years ago */
    static Date dat4YearAgo = Date.newInstance( datToday.year()-4,1,1);
    /** @description Default gift record type for tests. */
    static String giftRecordTypeIdForTests = UTIL_RecordTypes.getRecordTypeIdForGiftsTests(Opportunity.sObjectType);
    /** @description Default membership record type for tests. */
    static String membershipRecordTypeIdForTests = UTIL_RecordTypes.getRecordTypeIdForMembershipTests(Opportunity.sObjectType);
    /** @description List of test contacts. */
    static list<Contact> listConTest;
    /** @description List of test opportunities. */
    static list<Opportunity> listOppTest;

    /*******************************************************************************************************
    * @description Helper utility to create test contacts and test opportunities
    * @param numContacts The number of test contacts to create.
    * @param strProcessor The account processor type for this test.
    * @return boolean Returns false if there is no a membership record type name for tests.
    ********************************************************************************************************/    
    static boolean setupTestData(integer numContacts, string strProcessor) {      
        
        if (membershipRecordTypeIdForTests == null)
            return false;
            
        // reinitialize our lists
        listConTest = new list<Contact>();
        listOppTest = new list<Opportunity>();
            
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (
            npe01__Account_Processor__c = strProcessor,
            npe01__Enable_Opportunity_Contact_Role_Trigger__c = true,
            npe01__Opportunity_Contact_Role_Default_role__c = 'Donor'
        ));
                
        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
            new npo02__Households_Settings__c (
                npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR,
                npo02__Always_Rollup_to_Primary_Contact__c = false,
                npo02__Enable_Opp_Rollup_Triggers__c = false, // DJH: not needed since calling opp rollups directly. 
                npo02__Excluded_Account_Opp_Rectypes__c = null,
                npo02__Excluded_Account_Opp_Types__c = null,
                npo02__Excluded_Contact_Opp_Rectypes__c = null,
                npo02__Excluded_Contact_Opp_Types__c = null,
                npo02__Membership_Record_Types__c = membershipRecordTypeIdForTests
            ));
                
        // create & insert contact(s)
        listConTest = UTIL_UnitTestData_TEST.CreateMultipleTestContacts (numContacts) ;
        insert listConTest;
            
        // create a new gift for this yr
        Opportunity[] testGift1 = UTIL_UnitTestData_TEST.OppsForContactListByRecTypeId ( listConTest, null, UTIL_UnitTestData_TEST.getClosedWonStage(), 
            datToday, 100 , householdSettingsForTests.npo02__Membership_Record_Types__c,null);
        listOppTest.addAll(testGift1);
        //create a 2nd gift for last yr
        Opportunity[] testGift2 = UTIL_UnitTestData_TEST.OppsForContactListByRecTypeId ( listConTest, null, UTIL_UnitTestData_TEST.getClosedWonStage(), 
            dat1YearAgo, 60, householdSettingsForTests.npo02__Membership_Record_Types__c,null);
        listOppTest.addAll(testGift2);
        insert listOppTest;
            
        // now roll up manually
        RLLP_OppRollup rg = new RLLP_OppRollup();
        rg.rollupContacts(new map<id, Contact>(listConTest));

        //now test that the contact has received the proper stats from the trigger
        RLLP_OppRollup_UTIL oppRollupUtil = new RLLP_OppRollup_UTIL();
        string strSoql = oppRollupUtil.buildContactQuery();
        strSoql = strSoql.replace('select id,', 'select id, Firstname, LastName,');
        listConTest = database.query(strSoql);
            
        System.AssertEquals ( 160 , listConTest[0].npo02__TotalOppAmount__c );
        System.AssertEquals ( 100 , listConTest[0].npo02__OppAmountThisYear__c );
        System.AssertEquals ( 60 , listConTest[0].npo02__OppAmountLastYear__c );
        System.AssertEquals ( 0 , listConTest[0].npo02__OppAmount2YearsAgo__c );
        return true;
    }

    /*********************************************************************************************************
    * @description Runs testGivingRollupProcessor test method for the One to One account processor.
    */
    static testMethod void testGivingRollupOne2One(){
    	if (strTestOnly != '*' && strTestOnly != 'testGivingRollupOne2One') return;
        testGivingRollupProcessor(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    /*********************************************************************************************************
    * @description Runs testGivingRollupProcessor test method for the household account processor.
    */
    static testMethod void testGivingRollupHHAccount(){
        if (strTestOnly != '*' && strTestOnly != 'testGivingRollupHHAccount') return;
        testGivingRollupProcessor(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }
    /*********************************************************************************************************
    * @description Creates a contact, inserts an opportunity, and verifies rollup fields from opportunity 
    * trigger for a given account processor.
    * @param strProcessor The account processor type.
    */
    static void testGivingRollupProcessor(string strProcessor){

        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (
            npe01__Account_Processor__c = strProcessor,
            npe01__Enable_Opportunity_Contact_Role_Trigger__c = true,
            npe01__Opportunity_Contact_Role_Default_role__c = 'Donor'
        ));

        npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
            new npo02__Households_Settings__c (
            npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR, 
            npo02__Always_Rollup_to_Primary_Contact__c = false,
            npo02__Enable_Opp_Rollup_Triggers__c = true,
            npo02__Excluded_Account_Opp_Rectypes__c = null,
            npo02__Excluded_Account_Opp_Types__c = null,
            npo02__Excluded_Contact_Opp_Rectypes__c = null,
            npo02__Excluded_Contact_Opp_Types__c = null,
            npo02__Membership_Record_Types__c = null
        )); 
            
        Date datClose = System.Today();

        // create & insert contact(s)
        Contact c = new Contact( 
            FirstName = CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
            LastName = CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
            npe01__Private__c=false,
            npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
            npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
            npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
            npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS
        );
        insert c;

        Id rt_id = giftRecordTypeIdForTests;

        //create new opp            
        Opportunity o = new Opportunity(
                    Name = 'Test Opp 45',
                    CloseDate = datClose,
                    StageName = UTIL_UnitTestData_TEST.getClosedWonStage(),
                    npe01__Contact_Id_for_Role__c = c.id,
                    Amount = 100
                    );

        if (rt_id != null) {
            o.put('RecordTypeId', rt_id);
        }

        //insert the opp(s)
        Test.StartTest();
        insert o;
        Test.StopTest();
        
        system.assertEquals('true', [select npe01__Is_Opp_from_Individual__c from Opportunity where id = :o.id].npe01__Is_Opp_from_Individual__c);
        system.AssertEquals(1, [select count() from OpportunityContactRole where ContactId = :c.id and isPrimary = true]);
        system.AssertEquals(1, [select count() from Account where npe01__One2OneContact__c = :c.id and npe01__SYSTEMIsIndividual__c = true]);
                  
        //now test that a contact has received the proper member stats from the trigger
        Contact UpdatedCon = [SELECT id, npo02__OppAmountLastNDays__c, npo02__OppAmountThisYear__c, npo02__TotalMembershipOppAmount__c,
                                    npo02__OppAmount2YearsAgo__c, npo02__OppAmountLastYear__c, npo02__household__c,
                                    npo02__Best_Gift_Year__c, npo02__Best_Gift_Year_Total__c, npo02__Household__r.npo02__Best_Gift_Year__c, npo02__Household__r.npo02__Best_Gift_Year_Total__c,  
                                    npo02__household__r.npo02__TotalOppAmount__c, npo02__TotalOppAmount__c, npo02__LastCloseDate__c 
                                    FROM Contact WHERE Id = :c.id];
    
        system.AssertEquals(100, UpdatedCon.npo02__TotalOppAmount__c);
        system.AssertEquals(100, UpdatedCon.npo02__Household__r.npo02__TotalOppAmount__c);
        system.AssertEquals(100, UpdatedCon.npo02__OppAmountThisYear__c);
        system.AssertEquals(0, UpdatedCon.npo02__OppAmountLastYear__c);
        system.AssertEquals(100, UpdatedCon.npo02__Best_Gift_Year_Total__c);
        system.AssertEquals(string.valueof(datClose.year()), UpdatedCon.npo02__Best_Gift_Year__c);
        system.AssertEquals(100, UpdatedCon.npo02__household__r.npo02__Best_Gift_Year_Total__c);
        system.AssertEquals(string.valueof(datClose.year()), UpdatedCon.npo02__household__r.npo02__Best_Gift_Year__c);
    }
    
    /*********************************************************************************************************
    * @description Runs testGivingRollupIndividualProcessor test method for the bucket account processor.
    */
    static testMethod void testGivingRollupIndividualBucket(){
    	if (strTestOnly != '*' && strTestOnly != 'testGivingRollupIndividualBucket') return;
        testGivingRollupIndividualProcessor(CAO_Constants.BUCKET_PROCESSOR);
    }
    /*********************************************************************************************************
    * @description Runs testGivingRollupIndividualProcessor test method for the household account processor.
    */
    static testMethod void testGivingRollupIndividualHHAccount(){
    	if (strTestOnly != '*' && strTestOnly != 'testGivingRollupIndividualHHAccount') return;
        testGivingRollupIndividualProcessor(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }
    /*********************************************************************************************************
    * @description Creates a contact, inserts an opportunity, and verifies rollup fields from opportunity 
    * trigger for a given account processor, then runs rollups manually and verifies nothing has changed.
    * @param strProcessor The account processor type.
    */
    static void testGivingRollupIndividualProcessor(string strProcessor) {

            npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Account_Processor__c = strProcessor,
                npe01__Enable_Opportunity_Contact_Role_Trigger__c = true,
                npe01__Opportunity_Contact_Role_Default_role__c = 'Donor'
            ));

            npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
                new npo02__Households_Settings__c (
                    npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR,
                    npo02__Always_Rollup_to_Primary_Contact__c = false,
                    npo02__Enable_Opp_Rollup_Triggers__c = true,
                    npo02__Excluded_Account_Opp_Rectypes__c = null,
                    npo02__Excluded_Account_Opp_Types__c = null,
                    npo02__Excluded_Contact_Opp_Rectypes__c = null,
                    npo02__Excluded_Contact_Opp_Types__c = null,
                    npo02__Membership_Record_Types__c = null
                ));

            Date datClose = System.Today();

            // create & insert contact(s)
            Contact[] TestCons = new contact[]{ new contact(
                FirstName= CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
                LastName= CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
                npe01__Private__c=false,
                npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
                npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
                npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
                npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS
            ) };

            //since we're running rollup code on a manually created contact, we have to manually set currency field in multicurrency
            if (RLLP_OppRollup_UTIL.isMultiCurrency()) {
                TestCons[0].put(RLLP_OppRollup_UTIL.mcFieldValues.get('Contact'), RLLP_OppRollup_UTIL.currCorporate);
            }

            insert TestCons;

            // create new opps
            Opportunity[] newOpps = UTIL_UnitTestData_TEST.OppsForContactListByRecTypeId ( TestCons, null, UTIL_UnitTestData_TEST.getClosedWonStage(), datClose, 100 , giftRecordTypeIdForTests ,null);

            // insert the opp(s)
            Test.StartTest();
            insert newOpps;
            Test.StopTest();

            //now test that a contact has received the proper member stats from the trigger
            id FirstConId = TestCons[0].id;
            Contact UpdatedCon = [SELECT id, account.npo02__TotalOppAmount__c, npo02__OppAmountThisYear__c, npo02__OppAmountLastYear__c, npo02__household__c, npo02__household__r.npo02__TotalOppAmount__c, npo02__TotalOppAmount__c, npo02__household__r.npo02__MembershipEndDate__c, npo02__MembershipJoinDate__c FROM Contact WHERE Id = :FirstConId];

            System.AssertEquals ( 100 , UpdatedCon.npo02__TotalOppAmount__c );
            System.AssertEquals ( 100 , UpdatedCon.npo02__household__r.npo02__TotalOppAmount__c );
            System.AssertEquals ( 100 , UpdatedCon.npo02__OppAmountThisYear__c);
            System.AssertEquals ( 0 , UpdatedCon.npo02__OppAmountLastYear__c);

            // now roll up manually
            RLLP_OppRollup rg = new RLLP_OppRollup();
            rg.rollupContacts(new map<id, Contact>(new list<Contact>{TestCons[0]}));

            RLLP_OppRollup_UTIL oppRollupUtil = new RLLP_OppRollup_UTIL();
            npo02__household__c[] hhs = Database.query(oppRollupUtil.buildHouseholdQuery() + ' where id =\''+UpdatedCon.npo02__household__r.id+'\' LIMIT 1');

            rg = new RLLP_OppRollup();
            rg.rollupHouseholds(new map<id, npo02__Household__c>(hhs));

            //make sure the values are still right
            UpdatedCon = [SELECT id, account.npo02__TotalOppAmount__c, 
                                npo02__OppAmountThisYear__c, npo02__OppAmountLastYear__c, npo02__household__c, 
                                npo02__household__r.npo02__TotalOppAmount__c, npo02__TotalOppAmount__c FROM Contact WHERE Id = :FirstConId];

            System.AssertEquals (100 , UpdatedCon.npo02__TotalOppAmount__c);
            System.AssertEquals (100 , UpdatedCon.npo02__household__r.npo02__TotalOppAmount__c);      
            System.AssertEquals (100 , UpdatedCon.npo02__OppAmountThisYear__c);
            System.AssertEquals (0 , UpdatedCon.npo02__OppAmountLastYear__c);
    }

    /*********************************************************************************************************
    * @description Runs testGivingRollupExcludedRTProcessor test method for the one to one account processor.
    */
    static testMethod void testGivingRollupExcludedRTOne2One(){
    	if (strTestOnly != '*' && strTestOnly != 'testGivingRollupExcludedRTOne2One') return;
        testGivingRollupExcludedRTProcessor(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    /*********************************************************************************************************
    * @description Runs testGivingRollupExcludedRTProcessor test method for the household account processor.
    */
    static testMethod void testGivingRollupExcludedRTHHAccount(){
    	if (strTestOnly != '*' && strTestOnly != 'testGivingRollupExcludedRTHHAccount') return;
        testGivingRollupExcludedRTProcessor(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }
    /*********************************************************************************************************
    * @description Creates a contact, inserts an opportunity with an excluded record type, and verifies rollup
    * fields from opportunity trigger are blank, then runs rollups manually and verifies nothing has changed.
    * @param strProcessor The account processor type.
    */
    static void testGivingRollupExcludedRTProcessor (string strProcessor) {

        if(giftRecordTypeIdForTests != null){
            npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Account_Processor__c = strProcessor,
                npe01__Enable_Opportunity_Contact_Role_Trigger__c = true,
                npe01__Opportunity_Contact_Role_Default_role__c = 'Donor'
            ));

            npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
                new npo02__Households_Settings__c (
                    npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR,
                    npo02__Always_Rollup_to_Primary_Contact__c = false,
                    npo02__Enable_Opp_Rollup_Triggers__c = true,
                    npo02__Excluded_Account_Opp_Rectypes__c = giftRecordTypeIdForTests,
                    npo02__Excluded_Account_Opp_Types__c = null,
                    npo02__Excluded_Contact_Opp_Rectypes__c = giftRecordTypeIdForTests,
                    npo02__Excluded_Contact_Opp_Types__c = null,
                    npo02__Membership_Record_Types__c = null
                ));

            Date datClose = System.Today();

            // create & insert contact(s)
            Contact[] TestCons = new contact[]{ new contact(
                FirstName= CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
                LastName= CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
                npe01__Private__c=false,
                npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
                npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
                npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
                npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS
            ) };

            //since we're running rollup code on a manually created contact, we have to manually set currency field in multicurrency
            if (RLLP_OppRollup_UTIL.isMultiCurrency()) {
                TestCons[0].put(RLLP_OppRollup_UTIL.mcFieldValues.get('Contact'), RLLP_OppRollup_UTIL.currCorporate);
            }
 
            insert TestCons;

            // create new opps
            Opportunity[] newOpps = UTIL_UnitTestData_TEST.OppsForContactListByRecTypeId ( TestCons, null, UTIL_UnitTestData_TEST.getClosedWonStage(), datClose, 100 , giftRecordTypeIdForTests ,null);

            // insert the opp(s)
            Test.StartTest();
            insert newOpps;
            Test.StopTest();

            RLLP_OppRollup_UTIL oppRollupUtil = new RLLP_OppRollup_UTIL();

            //now test that a contact has received the proper member stats from the trigger
            id FirstConId = TestCons[0].id;
            Contact UpdatedCon = [SELECT id, Account.npo02__TotalOppAmount__c, npo02__OppAmountThisYear__c, npo02__OppAmountLastYear__c, npo02__Household__c, npo02__Household__r.npo02__TotalOppAmount__c, npo02__TotalOppAmount__c FROM Contact WHERE Id = :FirstConId];

            System.AssertEquals ( 0 , UpdatedCon.npo02__TotalOppAmount__c );
            System.AssertEquals ( 0 , UpdatedCon.npo02__household__r.npo02__TotalOppAmount__c );        
            System.AssertEquals ( 0 , UpdatedCon.npo02__OppAmountThisYear__c);

            // now roll up manually
            RLLP_OppRollup rg = new RLLP_OppRollup();
            rg.rollupContacts(new map<id, Contact>(new list<Contact>{TestCons[0]}));

            npo02__household__c[] hhs = Database.query(oppRollupUtil.buildHouseholdQuery() + ' where id =\''+UpdatedCon.npo02__household__r.id+'\' LIMIT 1');

            rg = new RLLP_OppRollup();
            rg.rollupHouseholds(new map<id, npo02__Household__c>(hhs));

            //make sure the values are still right
            UpdatedCon = [SELECT id, Account.npo02__TotalOppAmount__c, npo02__OppAmountThisYear__c, npo02__OppAmountLastYear__c, npo02__Household__c, npo02__Household__r.npo02__TotalOppAmount__c, npo02__TotalOppAmount__c FROM Contact WHERE Id = :FirstConId];

            System.AssertEquals (0, UpdatedCon.npo02__TotalOppAmount__c );
            System.AssertEquals (0, UpdatedCon.npo02__household__r.npo02__TotalOppAmount__c );        
            System.AssertEquals (0, UpdatedCon.npo02__OppAmountThisYear__c);
            System.AssertEquals (0, UpdatedCon.npo02__OppAmountLastYear__c);
        }
    }

    /*********************************************************************************************************
    * @description Runs testGivingRollupAlwaysPrimaryProcessor test method for the one to one account processor.
    */
    static testMethod void testGivingRollupAlwaysPrimaryOne2One(){
    	if (strTestOnly != '*' && strTestOnly != 'testGivingRollupAlwaysPrimaryOne2One') return;
        testGivingRollupAlwaysPrimaryProcessor(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    /*********************************************************************************************************
    * @description Runs testGivingRollupAlwaysPrimaryProcessor test method for the household account processor.
    */
    static testMethod void testGivingRollupAlwaysPrimaryHHAccount(){
        if (strTestOnly != '*' && strTestOnly != 'testGivingRollupAlwaysPrimaryHHAccount') return;
        testGivingRollupAlwaysPrimaryProcessor(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }
    /*********************************************************************************************************
    * @description Sets Always Rollup to Primary Contact setting, creates a contact, inserts an opportunity 
    * attributed to a organizatio account, and verifies rollups go to the primary contact instead of the account.
    * @param strProcessor The account processor type.
    */
   static void testGivingRollupAlwaysPrimaryProcessor (string strProcessor) {

            npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Account_Processor__c = strProcessor,
                npe01__Enable_Opportunity_Contact_Role_Trigger__c = true,
                npe01__Opportunity_Contact_Role_Default_role__c = 'Donor'
            ));

            npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
                new npo02__Households_Settings__c (
                    npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR,
                    npo02__Always_Rollup_to_Primary_Contact__c = true,
                    npo02__Enable_Opp_Rollup_Triggers__c = true,
                    npo02__Excluded_Account_Opp_Rectypes__c = null,
                    npo02__Excluded_Account_Opp_Types__c = null,
                    npo02__Excluded_Contact_Opp_Rectypes__c = null,
                    npo02__Excluded_Contact_Opp_Types__c = null,
                    npo02__Membership_Record_Types__c = null
                ));

            Date datClose = System.Today();
            // create account
            account testacct = new account(name='testacct');
            insert testacct;
            // create & insert contact(s)
            Contact[] TestCons = new contact[]{ new contact(
                FirstName= CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
                LastName= CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
                npe01__Private__c=false,
                AccountId = testacct.id,
                npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
                npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
                npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
                npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS
            ) };

            //since we're running rollup code on a manually created contact, we have to manually set currency field in multicurrency
            if (RLLP_OppRollup_UTIL.isMultiCurrency()) {
                TestCons[0].put(RLLP_OppRollup_UTIL.mcFieldValues.get('Contact'), RLLP_OppRollup_UTIL.currCorporate);
            }
 
            insert TestCons;

            // create new opps
            Opportunity[] newOpps = UTIL_UnitTestData_TEST.OppsForContactListByRecTypeId ( TestCons, null, UTIL_UnitTestData_TEST.getClosedWonStage(), datClose, 100 , giftRecordTypeIdForTests ,null);

            // insert the opp(s)
            Test.StartTest();
            insert newOpps;
            Test.StopTest();
            
            //now test that a contact has received the proper member stats from the trigger
            id FirstConId = TestCons[0].id;
            Contact UpdatedCon = [SELECT id, account.npo02__TotalOppAmount__c, npo02__OppAmountThisYear__c, npo02__OppAmountLastYear__c, npo02__household__c, npo02__household__r.npo02__TotalOppAmount__c, npo02__TotalOppAmount__c FROM Contact WHERE Id = :FirstConId];

            System.AssertEquals ( 100 , UpdatedCon.npo02__TotalOppAmount__c );
            System.AssertEquals ( 100 , UpdatedCon.npo02__household__r.npo02__TotalOppAmount__c );      
            System.AssertEquals ( 100 , UpdatedCon.npo02__OppAmountThisYear__c);
            System.AssertEquals ( 0 , UpdatedCon.npo02__OppAmountLastYear__c);

            // now roll up manually
            RLLP_OppRollup rg = new RLLP_OppRollup();
            rg.rollupContacts(new map<id, Contact>(new list<Contact>{TestCons[0]}));

            RLLP_OppRollup_UTIL oppRollupUtil = new RLLP_OppRollup_UTIL();
            npo02__household__c[] hhs = Database.query(oppRollupUtil.buildHouseholdQuery() + ' where id =\''+UpdatedCon.npo02__household__r.id+'\' LIMIT 1');

            rg = new RLLP_OppRollup();
            rg.rollupHouseholds(new map<id, npo02__Household__c>(hhs));

            //make sure the values are still right
            UpdatedCon = [SELECT id, account.npo02__TotalOppAmount__c, npo02__OppAmountThisYear__c, npo02__OppAmountLastYear__c, npo02__household__c, npo02__household__r.npo02__TotalOppAmount__c, npo02__TotalOppAmount__c FROM Contact WHERE Id = :FirstConId];

            System.AssertEquals ( 100 , UpdatedCon.npo02__TotalOppAmount__c );
            System.AssertEquals ( 100 , UpdatedCon.npo02__household__r.npo02__TotalOppAmount__c );      
            System.AssertEquals ( 100 , UpdatedCon.npo02__OppAmountThisYear__c);
            System.AssertEquals ( 0 , UpdatedCon.npo02__OppAmountLastYear__c);
        
    }
    
    /*********************************************************************************************************
    * @description
    */
    static testMethod void testMemberRollupOne2One(){
        if (strTestOnly != '*' && strTestOnly != 'testMemberRollupOne2One') return;
        testMemberRollupProcessor(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    /*********************************************************************************************************
    * @description
    */
    static testMethod void testMemberRollupHHAccount(){
    	if (strTestOnly != '*' && strTestOnly != 'testMemberRollupHHAccount') return;
        testMemberRollupProcessor(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }    
    /*********************************************************************************************************
    * @description
    */
    static void testMemberRollupProcessor (string strProcessor) {

        if(membershipRecordTypeIdForTests != null){
            npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Account_Processor__c = strProcessor,
                npe01__Enable_Opportunity_Contact_Role_Trigger__c = true,
                npe01__Opportunity_Contact_Role_Default_role__c = 'Donor'
            ));

            npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
                new npo02__Households_Settings__c (
                    npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR,
                    npo02__Always_Rollup_to_Primary_Contact__c = false,
                    npo02__Enable_Opp_Rollup_Triggers__c = true,
                    npo02__Excluded_Account_Opp_Rectypes__c = null,
                    npo02__Excluded_Account_Opp_Types__c = null,
                    npo02__Excluded_Contact_Opp_Rectypes__c = null,
                    npo02__Excluded_Contact_Opp_Types__c = null,
                    npo02__Membership_Record_Types__c = membershipRecordTypeIdForTests
                ));

            Date datClose = System.Today();

            // create & insert contact(s)
            Contact[] TestCons = new contact[]{ new contact(
                FirstName= CAO_Constants.CONTACT_FIRSTNAME_FOR_TESTS,
                LastName= CAO_Constants.CONTACT_LASTNAME_FOR_TESTS,
                npe01__Private__c=false,
                npe01__WorkEmail__c = CAO_Constants.CONTACT_EMAIL_FOR_TESTS, 
                npe01__Preferred_Email__c = CAO_Constants.CONTACT_PREFERRED_EMAIL_FOR_TESTS,
                npe01__WorkPhone__c = CAO_Constants.CONTACT_PHONE_FOR_TESTS,
                npe01__PreferredPhone__c = CAO_Constants.CONTACT_PREFERRED_PHONE_FOR_TESTS
            ) };
            insert TestCons;

            // create new opps
            Opportunity[] newOpps = UTIL_UnitTestData_TEST.OppsForContactListByRecTypeId (
                TestCons,
                null,
                UTIL_UnitTestData_TEST.getClosedWonStage(),
                datClose,
                100,
                householdSettingsForTests.npo02__Membership_Record_Types__c,
                null
            );

            // insert the opp(s)
            Test.StartTest();
            newOpps[0].npe01__Membership_Origin__c = 'Renewal';
            insert newOpps;
            Test.StopTest();

            //now test that a contact has received the proper member stats from the trigger
            id FirstConId = TestCons[0].id;
            Contact UpdatedCon = [SELECT id, npo02__TotalMembershipOppAmount__c,npo02__LastMembershipOrigin__c,
                npo02__LastMembershipAmount__c, npo02__LastMembershipDate__c 
                from contact where id =: firstconid];
            System.AssertEquals (100 , updatedcon.npo02__TotalMembershipOppAmount__c );
            System.AssertEquals (100 , updatedcon.npo02__LastMembershipAmount__c );
            System.AssertEquals ('Renewal' , updatedcon.npo02__LastMembershipOrigin__c );
            System.AssertEquals (system.today() , updatedcon.npo02__LastMembershipDate__c );
        }
    }

    /*********************************************************************************************************
    * @description
    */
    static testMethod void testGivingRollupAcctOne2One(){
        if (strTestOnly != '*' && strTestOnly != 'testGivingRollupAcctOne2One') return;
        testGivingRollupAcct(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    /*********************************************************************************************************
    * @description
    */
    static testMethod void testGivingRollupAcctHHAccount(){
        if (strTestOnly != '*' && strTestOnly != 'testGivingRollupAcctHHAccount') return;
        testGivingRollupAcct(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }
    /*********************************************************************************************************
    * @description
    */
    static void testGivingRollupAcct (string strProcessor) {
 
            npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Account_Processor__c = strProcessor,
                npe01__Enable_Opportunity_Contact_Role_Trigger__c = true,
                npe01__Opportunity_Contact_Role_Default_role__c = 'Donor'
            ));
            
            npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
                new npo02__Households_Settings__c (
                    npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR,
                    npo02__Always_Rollup_to_Primary_Contact__c = false,
                    npo02__Enable_Opp_Rollup_Triggers__c = true,
                    npo02__Excluded_Account_Opp_Rectypes__c = null,
                    npo02__Excluded_Account_Opp_Types__c = null,
                    npo02__Excluded_Contact_Opp_Rectypes__c = null,
                    npo02__Excluded_Contact_Opp_Types__c = null,
                    npo02__Membership_Record_Types__c = null
                ));

            Date datClose = System.Today();

            // create account
            account testacct = new account(name='testacct');
            insert testacct;
            opportunity newOpp =
                 new opportunity (
                    name = 'testopp',
                    accountid = testacct.id, 
                    stagename=UTIL_UnitTestData_TEST.getClosedWonStage(),
                    closedate=datClose, amount=33333
                 );
            if(RLLP_OppRollup_UTIL.areRecordTypesOnOpps()){
                newOpp.put('RecordTypeId', giftRecordTypeIdForTests);
            }
            // insert the opp(s)
            Test.StartTest();
            insert newOpp;
            Test.StopTest();
            
            // test whether the trigger worked
            RLLP_OppRollup_UTIL oppRollupUtil = new RLLP_OppRollup_UTIL();
            Account updatedAcct = Database.query(oppRollupUtil.buildAccountQuery() + ' where id =\''+testacct.id+'\'');
                                    
            System.AssertEquals ( 33333 , updatedAcct.npo02__TotalOppAmount__c );

            // now roll up manually
            RLLP_OppRollup rg = new RLLP_OppRollup();
            rg.rollupAccounts(new map<id, Account>(new list<Account>{updatedAcct}));

            updatedAcct = [select id, npo02__TotalOppAmount__c from account where id =: testacct.id];
            System.AssertEquals ( 33333 , updatedAcct.npo02__TotalOppAmount__c );    
    }

    static testMethod void testGivingRollupAcctMembershipOne2One(){
        if (strTestOnly != '*' && strTestOnly != 'testGivingRollupAcctMembershipOne2One') return;
        testGivingRollupAcctMembership(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    static testMethod void testGivingRollupAcctMembershipHHAccount(){
        if (strTestOnly != '*' && strTestOnly != 'testGivingRollupAcctMembershipHHAccount') return;
        testGivingRollupAcctMembership(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }
    static void testGivingRollupAcctMembership (string strProcessor) {

        if(membershipRecordTypeIdForTests != null){
            npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Account_Processor__c = strProcessor,
                npe01__Enable_Opportunity_Contact_Role_Trigger__c = true,
                npe01__Opportunity_Contact_Role_Default_role__c = 'Donor'
            ));

            npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
                new npo02__Households_Settings__c (
                    npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR,
                    npo02__Always_Rollup_to_Primary_Contact__c = false,
                    npo02__Enable_Opp_Rollup_Triggers__c = true,
                    npo02__Excluded_Account_Opp_Rectypes__c = null,
                    npo02__Excluded_Account_Opp_Types__c = null,
                    npo02__Excluded_Contact_Opp_Rectypes__c = null,
                    npo02__Excluded_Contact_Opp_Types__c = null,
                    npo02__Membership_Record_Types__c = membershipRecordTypeIdForTests
                ));

            Date datClose = System.Today();

            // create account
            account testacct = new account(name='testacct');
            insert testacct;
            opportunity newOpp =
                 new opportunity (name = 'testopp', accountid = testacct.id, 
                                    npe01__member_level__c = 'Gold', npe01__membership_origin__c = 'New',
                                    stagename=UTIL_UnitTestData_TEST.getClosedWonStage(), closedate=datClose, amount=33333);
            newOpp.put('RecordTypeId', householdSettingsForTests.npo02__Membership_Record_Types__c);
            // insert the opp(s)
            Test.StartTest();
            insert newOpp;
            Test.StopTest();

            // test whether the trigger worked      
            RLLP_OppRollup_UTIL oppRollupUtil = new RLLP_OppRollup_UTIL();
            Account updatedAcct = Database.query(oppRollupUtil.buildAccountQuery() + ' where id =\''+testacct.id+'\'');

            System.AssertEquals ( 33333 , updatedAcct.npo02__TotalMembershipOppAmount__c );
            System.AssertEquals ( system.today() , updatedAcct.npo02__LastMembershipDate__c );
            System.AssertEquals ( 33333 , updatedAcct.npo02__LastMembershipAmount__c );
            System.AssertEquals ( 'Gold' , updatedAcct.npo02__LastMembershipLevel__c );
            System.AssertEquals ( 'New' , updatedAcct.npo02__LastMembershipOrigin__c );

            // now roll up manually
            RLLP_OppRollup rg = new RLLP_OppRollup();
            rg.rollupAccounts(new map<id, Account>(new list<Account>{updatedAcct}));

            updatedAcct = [select id, npo02__TotalMembershipOppAmount__c from account where id =: testacct.id];
            System.AssertEquals ( 33333 , updatedAcct.npo02__TotalMembershipOppAmount__c );
        }
    }

    static testMethod void testGivingRollupBatchOne2One(){
        if (strTestOnly != '*' && strTestOnly != 'testGivingRollupBatchOne2One') return;
        testGivingRollupBatch(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    static testMethod void testGivingRollupBatchHHAccount(){
        if (strTestOnly != '*' && strTestOnly != 'testGivingRollupBatchHHAccount') return;
        testGivingRollupBatch(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }
    static void testGivingRollupBatch (string strProcessor) {

            npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Account_Processor__c = strProcessor,
                npe01__Enable_Opportunity_Contact_Role_Trigger__c = true,
                npe01__Opportunity_Contact_Role_Default_role__c = 'Donor'
            ));

            npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
                new npo02__Households_Settings__c (
                    npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR,
                    npo02__Always_Rollup_to_Primary_Contact__c = false,
                    npo02__Enable_Opp_Rollup_Triggers__c = true,
                    npo02__Excluded_Account_Opp_Rectypes__c = null,
                    npo02__Excluded_Account_Opp_Types__c = null,
                    npo02__Excluded_Contact_Opp_Rectypes__c = null,
                    npo02__Excluded_Contact_Opp_Types__c = null,
                    npo02__Membership_Record_Types__c = null
                ));

            Date datClose = System.Today();

            // create & insert contact(s)
            Contact[] TestCons = UTIL_UnitTestData_TEST.CreateMultipleTestContacts ( 50 ) ;
            insert TestCons;

            // create new opps
            Opportunity[] newOpps = UTIL_UnitTestData_TEST.OppsForContactListByRecTypeId ( TestCons, null, UTIL_UnitTestData_TEST.getClosedWonStage(), datClose, 1000 , giftRecordTypeIdForTests ,null);

            account testacct = new account(name='testacct');
            insert testacct;

            // test the batch rollup method
            Test.StartTest();
            RLLP_OppRollup rg = new RLLP_OppRollup();
            rg.rollupAll();
            Test.StopTest();
    }

    static testMethod void OneContactMultipleOppsOne2One_1(){
        if (strTestOnly != '*' && strTestOnly != 'OneContactMultipleOppsOne2One_1') return;
        OneContactMultipleOpps_1(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    static testMethod void OneContactMultipleOppsHHAccount_1(){
        if (strTestOnly != '*' && strTestOnly != 'OneContactMultipleOppsHHAccount_1') return;
        OneContactMultipleOpps_1(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }
    public static void OneContactMultipleOpps_1(string strProcessor) {

        integer numCon = 1;
        if (!setupTestData(numCon, strProcessor))
            return;

        System.AssertEquals ( 160 , listConTest[0].npo02__TotalOppAmount__c );
        System.AssertEquals ( 100 , listConTest[0].npo02__OppAmountThisYear__c );
        System.AssertEquals ( 60 , listConTest[0].npo02__OppAmountLastYear__c );

        // now chg the amts for both opps (cheapskate!)
        listOppTest[0].Amount = 50;
        listOppTest[numCon].Amount = 25;
        update listOppTest;
        
        // now roll up manually
        RLLP_OppRollup rg = new RLLP_OppRollup();        
        rg.rollupContacts(new map<id, Contact>(listConTest));

        ID ThisConId = listConTest[0].id;
        Contact UpdatedCon = [SELECT Id, npo02__TotalOppAmount__c, npo02__OppAmountThisYear__c, npo02__OppAmountLastYear__c  FROM Contact WHERE Id = :ThisConId];        
        System.AssertEquals ( 75 , UpdatedCon.npo02__TotalOppAmount__c );
        System.AssertEquals ( 50 , UpdatedCon.npo02__OppAmountThisYear__c );
        System.AssertEquals ( 25 , UpdatedCon.npo02__OppAmountLastYear__c );
    }

    static testMethod void OneContactMultipleOppsOne2One_2(){
        if (strTestOnly != '*' && strTestOnly != 'OneContactMultipleOppsOne2One_2') return;
        OneContactMultipleOpps_2(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    static testMethod void OneContactMultipleOppsHHAccount_2(){
        if (strTestOnly != '*' && strTestOnly != 'OneContactMultipleOppsHHAccount_2') return;
        OneContactMultipleOpps_2(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }    
    public static void OneContactMultipleOpps_2(string strProcessor) {

        integer numCon = 1;
        if (!setupTestData(numCon, strProcessor))
            return;

        System.AssertEquals ( 160 , listConTest[0].npo02__TotalOppAmount__c );
        System.AssertEquals ( 100 , listConTest[0].npo02__OppAmountThisYear__c );
        System.AssertEquals ( 60 , listConTest[0].npo02__OppAmountLastYear__c );

        // now create a gift from 2 yrs ago
        Opportunity[] testGift3 = UTIL_UnitTestData_TEST.OppsForContactListByRecTypeId ( listConTest, null, UTIL_UnitTestData_TEST.getClosedWonStage(), dat2YearAgo, 10 , giftRecordTypeIdForTests,null);  
        insert testGift3;

        // now roll up manually
        RLLP_OppRollup rg = new RLLP_OppRollup();
        rg.rollupContacts(new map<id, Contact>(new list<Contact>{listConTest[0]}));

        ID ThisConId = listConTest[0].id;
        Contact UpdatedCon = [SELECT Id, npo02__TotalOppAmount__c, npo02__OppAmountThisYear__c, npo02__OppAmountLastYear__c, npo02__OppAmount2YearsAgo__c  FROM Contact WHERE Id = :ThisConId];        

        System.AssertEquals ( 170 , UpdatedCon.npo02__TotalOppAmount__c );
        System.AssertEquals ( 100 , UpdatedCon.npo02__OppAmountThisYear__c );
        System.AssertEquals ( 60 , UpdatedCon.npo02__OppAmountLastYear__c );
        System.AssertEquals ( 10 , UpdatedCon.npo02__OppAmount2YearsAgo__c ); 
    }

    static testMethod void OneContactMultipleOppsOne2One_3(){
        if (strTestOnly != '*' && strTestOnly != 'OneContactMultipleOppsOne2One_3') return;
        OneContactMultipleOpps_3(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    static testMethod void OneContactMultipleOppsHHAccount_3(){
    	if (strTestOnly != '*' && strTestOnly != 'OneContactMultipleOppsHHAccount_3') return;
        OneContactMultipleOpps_3(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }    
    public static void OneContactMultipleOpps_3(string strProcessor) {

        integer numCon = 1;
        if (!setupTestData(numCon, strProcessor))
            return;

        System.AssertEquals ( 160 , listConTest[0].npo02__TotalOppAmount__c );
        System.AssertEquals ( 100 , listConTest[0].npo02__OppAmountThisYear__c );
        System.AssertEquals ( 60 , listConTest[0].npo02__OppAmountLastYear__c );

        // add another from this year (to test adding)
        Opportunity[] testGift4 = UTIL_UnitTestData_TEST.OppsForContactListByRecTypeId ( listConTest, null, UTIL_UnitTestData_TEST.getClosedWonStage(), datToday, 25 , giftRecordTypeIdForTests,null);
        insert testGift4;
        Opportunity[] testGift5 = UTIL_UnitTestData_TEST.OppsForContactListByRecTypeId ( listConTest, null, UTIL_UnitTestData_TEST.getClosedWonStage(), datToday, 25 , giftRecordTypeIdForTests,null);
        insert testGift5;

        // now roll up manually
        RLLP_OppRollup rg = new RLLP_OppRollup();
        rg.rollupContacts(new map<id, Contact>(new list<Contact>{listConTest[0]}));

        ID ThisConId = listConTest[0].id;
        Contact UpdatedCon = [SELECT Id, npo02__TotalOppAmount__c, npo02__OppAmountThisYear__c, npo02__OppAmountLastYear__c, npo02__OppAmount2YearsAgo__c  FROM Contact WHERE Id = :ThisConId];        
        System.AssertEquals ( 210 , UpdatedCon.npo02__TotalOppAmount__c );
        System.AssertEquals ( 150 , UpdatedCon.npo02__OppAmountThisYear__c );        
        System.AssertEquals ( 60 , UpdatedCon.npo02__OppAmountLastYear__c );
        System.AssertEquals ( 0 , UpdatedCon.npo02__OppAmount2YearsAgo__c );
    }

    static testMethod void OneContactMultipleOppsOne2One_4(){
    	if (strTestOnly != '*' && strTestOnly != 'OneContactMultipleOppsOne2One_4') return;
        OneContactMultipleOpps_4(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    static testMethod void OneContactMultipleOppsHHAccount_4(){
        if (strTestOnly != '*' && strTestOnly != 'OneContactMultipleOppsHHAccount_4') return;
        OneContactMultipleOpps_4(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }    
    public static void OneContactMultipleOpps_4(string strProcessor) {

        integer numCon = 1;
        if (!setupTestData(numCon, strProcessor))
            return;

        System.AssertEquals ( 160 , listConTest[0].npo02__TotalOppAmount__c );
        System.AssertEquals ( 100 , listConTest[0].npo02__OppAmountThisYear__c );
        System.AssertEquals ( 60 , listConTest[0].npo02__OppAmountLastYear__c );

        // TBD add a gift from longer ago
        Opportunity[] testGift5 = UTIL_UnitTestData_TEST.OppsForContactListByRecTypeId ( listConTest, null, UTIL_UnitTestData_TEST.getClosedWonStage(), dat4YearAgo, 200 , giftRecordTypeIdForTests,null);
        insert testGift5;

        // now roll up manually
        RLLP_OppRollup rg = new RLLP_OppRollup();
        rg.rollupContacts(new map<id, Contact>(new list<Contact>{listConTest[0]}));

        // totals should not have changed, except lifetime & best yr
        ID ThisConId = listConTest[0].id;
        Contact UpdatedCon = [SELECT Id, npo02__TotalOppAmount__c, npo02__OppAmountThisYear__c, npo02__OppAmountLastYear__c, npo02__OppAmount2YearsAgo__c  FROM Contact WHERE Id = :ThisConId];        

        System.AssertEquals ( 360 , UpdatedCon.npo02__TotalOppAmount__c );
        System.AssertEquals ( 100 , UpdatedCon.npo02__OppAmountThisYear__c );
        System.AssertEquals ( 60 , UpdatedCon.npo02__OppAmountLastYear__c );
        System.AssertEquals ( 0 , UpdatedCon.npo02__OppAmount2YearsAgo__c );
    }

    static testMethod void OneContactMultipleOppsOne2One_5(){
        if (strTestOnly != '*' && strTestOnly != 'OneContactMultipleOppsOne2One_5') return;
        OneContactMultipleOpps_5(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    static testMethod void OneContactMultipleOppsHHAccount_5(){
        if (strTestOnly != '*' && strTestOnly != 'OneContactMultipleOppsHHAccount_5') return;
        OneContactMultipleOpps_5(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }
    public static void OneContactMultipleOpps_5(string strProcessor) {

        integer numCon = 1;
        if (!setupTestData(numCon, strProcessor))
            return;

        System.AssertEquals ( 160 , listConTest[0].npo02__TotalOppAmount__c );
        System.AssertEquals ( 100 , listConTest[0].npo02__OppAmountThisYear__c );
        System.AssertEquals ( 60 , listConTest[0].npo02__OppAmountLastYear__c );

        // TBD add non-won gift
        Opportunity[] testGift6 = UTIL_UnitTestData_TEST.OppsForContactListByRecTypeId ( listConTest, null, UTIL_UnitTestData_TEST.getOpenStage(), dat4YearAgo, 35 , giftRecordTypeIdForTests,null);
        insert testGift6;

        // now roll up manually
        RLLP_OppRollup rg = new RLLP_OppRollup();
        rg.rollupContacts(new map<id, Contact>(new list<Contact>{listConTest[0]}));

        // totals should not have changed at all
        ID ThisConId = listConTest[0].id;
        Contact UpdatedCon = [SELECT Id, npo02__TotalOppAmount__c, npo02__OppAmountThisYear__c, npo02__OppAmountLastYear__c, npo02__OppAmount2YearsAgo__c  FROM Contact WHERE Id = :ThisConId];        
        System.AssertEquals ( 160 , UpdatedCon.npo02__TotalOppAmount__c );
        System.AssertEquals ( 100 , UpdatedCon.npo02__OppAmountThisYear__c );
        System.AssertEquals ( 60 , UpdatedCon.npo02__OppAmountLastYear__c );
        System.AssertEquals ( 0 , UpdatedCon.npo02__OppAmount2YearsAgo__c );
    }

    static testMethod void OneContactMultipleOppsOne2One_6(){
        if (strTestOnly != '*' && strTestOnly != 'OneContactMultipleOppsOne2One_6') return;
        OneContactMultipleOpps_6(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    static testMethod void OneContactMultipleOppsHHAccount_6(){
        if (strTestOnly != '*' && strTestOnly != 'OneContactMultipleOppsHHAccount_6') return;
        OneContactMultipleOpps_6(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }    
    public static void OneContactMultipleOpps_6(string strProcessor) {

        integer numCon = 1;
        if (!setupTestData(numCon, strProcessor))
            return;

        System.AssertEquals ( 160 , listConTest[0].npo02__TotalOppAmount__c );
        System.AssertEquals ( 100 , listConTest[0].npo02__OppAmountThisYear__c );
        System.AssertEquals ( 60 , listConTest[0].npo02__OppAmountLastYear__c );

        // now delete the 1st gift, totals should decrease
        decimal dblAmt = listOppTest[0].Amount;
        delete listOppTest[0];

        // now roll up manually
        RLLP_OppRollup rg = new RLLP_OppRollup();
        rg.rollupContacts(new map<id, Contact>(new list<Contact>{listConTest[0]}));

        ID ThisConId = listConTest[0].id;
        Contact UpdatedCon = [SELECT Id, npo02__TotalOppAmount__c, npo02__OppAmountThisYear__c, npo02__OppAmountLastYear__c, npo02__OppAmount2YearsAgo__c  FROM Contact WHERE Id = :ThisConId];        

        System.AssertEquals ( 160-dblAmt , UpdatedCon.npo02__TotalOppAmount__c );
        System.AssertEquals ( 100-dblAmt , UpdatedCon.npo02__OppAmountThisYear__c );        
        System.AssertEquals ( 60 , UpdatedCon.npo02__OppAmountLastYear__c );
        System.AssertEquals ( 0 , UpdatedCon.npo02__OppAmount2YearsAgo__c );        
    }

    static testMethod void OneContactOneInkindOne2One(){
        if (strTestOnly != '*' && strTestOnly != 'OneContactOneInkindOne2One') return;
        OneContactOneInkind(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    static testMethod void OneContactOneInkindHHAccount(){
        if (strTestOnly != '*' && strTestOnly != 'OneContactOneInkindHHAccount') return;
        OneContactOneInkind(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }
    static void OneContactOneInkind(string strProcessor) {

            npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Account_Processor__c = strProcessor,
                npe01__Enable_Opportunity_Contact_Role_Trigger__c = true,
                npe01__Opportunity_Contact_Role_Default_role__c = 'Donor'
            ));

            npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
                new npo02__Households_Settings__c (
                    npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR,
                    npo02__Always_Rollup_to_Primary_Contact__c = false,
                    npo02__Enable_Opp_Rollup_Triggers__c = true,
                    npo02__Excluded_Account_Opp_Rectypes__c = null,
                    npo02__Excluded_Account_Opp_Types__c = 'In Kind',
                    npo02__Excluded_Contact_Opp_Rectypes__c = null,
                    npo02__Excluded_Contact_Opp_Types__c = 'In Kind',
                    npo02__Membership_Record_Types__c = null
                ));

            integer howMany = 1;

            // create & insert contact(s)
            Contact[] TestCons = UTIL_UnitTestData_TEST.CreateMultipleTestContacts ( howMany ) ;
            insert TestCons;

            // create a new gift for this yr
            Opportunity[] testGift1 = UTIL_UnitTestData_TEST.OppsForContactListByRecTypeId ( TestCons, null, UTIL_UnitTestData_TEST.getClosedWonStage(), datToday, 100 , giftRecordTypeIdForTests,'In Kind');
            Test.StartTest();
            insert testGift1 ;
            Test.StopTest();

            id ThisConId = TestCons[0].id;
            contact UpdatedCon = [SELECT Id,  npo02__TotalOppAmount__c, npo02__OppAmountThisYear__c, npo02__OppAmountLastYear__c, npo02__OppAmount2YearsAgo__c FROM Contact WHERE Id = :ThisConId];

            System.Assert(!(UpdatedCon.npo02__TotalOppAmount__c>0));
    }

    static testMethod void testGivingRollupBulkOne2One(){
        if (strTestOnly != '*' && strTestOnly != 'testGivingRollupBulkOne2One') return;
        testGivingRollupBulk(CAO_Constants.ONE_TO_ONE_PROCESSOR);
    }
    static testMethod void testGivingRollupBulkHHAccount(){
        if (strTestOnly != '*' && strTestOnly != 'testGivingRollupBulkHHAccount') return;
        testGivingRollupBulk(CAO_Constants.HH_ACCOUNT_PROCESSOR);
    }    
    static void testGivingRollupBulk (string strProcessor) {
    
            npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Account_Processor__c = strProcessor,
                npe01__Enable_Opportunity_Contact_Role_Trigger__c = true,
                npe01__Opportunity_Contact_Role_Default_role__c = 'Donor'
            ));

            npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
                new npo02__Households_Settings__c (
                    npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR,
                    npo02__Always_Rollup_to_Primary_Contact__c = false,
                    npo02__Enable_Opp_Rollup_Triggers__c = true,
                    npo02__Excluded_Account_Opp_Rectypes__c = null,
                    npo02__Excluded_Account_Opp_Types__c = null,
                    npo02__Excluded_Contact_Opp_Rectypes__c = null,
                    npo02__Excluded_Contact_Opp_Types__c = null,
                    npo02__Membership_Record_Types__c = null
                ));

            // for a single contact w/ no previous mbrships, add a new membership
            // and test mbr stats are created
            integer howMany = 50;
            Date datClose = System.Today();

            // create & insert contact(s)
            Contact[] TestCons = UTIL_UnitTestData_TEST.CreateMultipleTestContacts ( howMany ) ;
            insert TestCons;

            // create new opps
            Opportunity[] newOpps1 = UTIL_UnitTestData_TEST.OppsForContactListByRecTypeId ( TestCons, null, UTIL_UnitTestData_TEST.getClosedWonStage(), datClose, 100 , giftRecordTypeIdForTests,null);
            Opportunity[] newOpps2 = UTIL_UnitTestData_TEST.OppsForContactListByRecTypeId ( TestCons, null, UTIL_UnitTestData_TEST.getClosedWonStage(), datClose.addYears(-1), 50 , giftRecordTypeIdForTests,null);

            // insert the opp(s)
            Test.StartTest();
            insert newOpps1;

            insert newOpps2;

            Test.StopTest();

            id FirstConId = TestCons[10].id;
            Contact UpdatedCon = [SELECT Id,  npo02__TotalOppAmount__c, npo02__OppAmountThisYear__c, npo02__OppAmountLastYear__c, npo02__OppAmount2YearsAgo__c FROM Contact WHERE Id = :FirstConId];

            System.AssertEquals ( 150 , UpdatedCon.npo02__TotalOppAmount__c );
            System.AssertEquals ( 100 , UpdatedCon.npo02__OppAmountThisYear__c );       
            System.AssertEquals ( 50 , UpdatedCon.npo02__OppAmountLastYear__c );
            System.AssertEquals ( 0 , UpdatedCon.npo02__OppAmount2YearsAgo__c );
    }

}