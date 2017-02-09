/*
    Copyright (c) 2016, Salesforce.org
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
* @date 2016
* @group Levels
* @description test the batchable class for Level Assignments
*/

@isTest
private class LVL_LevelAssignBATCH_TEST {

    private static Level__c lvlTin { get; set; }
    private static Level__c lvlBronze { get; set; }
    private static Level__c lvlSilver { get; set; }
    private static Level__c lvlGold { get; set; }
    private static Level__c lvlPlatinum { get; set; }
    private static Level__c lvlAcctZinc { get; set; }

    /*********************************************************************************************************
    * @description creates Bronze, Silver, and Gold Level records for test code to use.
    */
    static void createTestLevels() {
        // create Levels
        lvlTin = new Level__c(
            Name = 'Tin',
            Target__c = 'Contact',
            Source_Field__c = 'npo02__TotalOppAmount__c', 
            Level_Field__c = 'npo02__LastMembershipLevel__c', 
            Previous_Level_Field__c = 'npo02__LastMembershipOrigin__c', 
            Active__c = true, 
            Minimum_Amount__c = null, 
            Maximum_Amount__c = 100);

        lvlBronze = new Level__c(
            Name = 'Bronze',
            Target__c = 'Contact',
            Source_Field__c = 'npo02__TotalOppAmount__c', 
            Level_Field__c = 'npo02__LastMembershipLevel__c', 
            Previous_Level_Field__c = 'npo02__LastMembershipOrigin__c', 
            Active__c = true, 
            Minimum_Amount__c = 100, 
            Maximum_Amount__c = 1000);

        lvlSilver = new Level__c(
            Name = 'Silver',
            Target__c = 'Contact',
            Source_Field__c = 'npo02__TotalOppAmount__c', 
            Level_Field__c = 'npo02__LastMembershipLevel__c', 
            Previous_Level_Field__c = 'npo02__LastMembershipOrigin__c', 
            Active__c = true, 
            Minimum_Amount__c = 1000, 
            Maximum_Amount__c = 5000);

        lvlGold = new Level__c(
            Name = 'Gold',
            Target__c = 'Contact',
            Source_Field__c = 'npo02__TotalOppAmount__c', 
            Level_Field__c = 'npo02__LastMembershipLevel__c', 
            Previous_Level_Field__c = 'npo02__LastMembershipOrigin__c', 
            Active__c = true, 
            Minimum_Amount__c = 5000, 
            Maximum_Amount__c = 90000); // consciously left 10k break for other tests to use.
        
        lvlPlatinum = new Level__c(
            Name = 'Platinum',
            Target__c = 'Contact',
            Source_Field__c = 'npo02__TotalOppAmount__c', 
            Level_Field__c = 'npo02__LastMembershipLevel__c', 
            Previous_Level_Field__c = 'npo02__LastMembershipOrigin__c', 
            Active__c = true, 
            Minimum_Amount__c = 100000, 
            Maximum_Amount__c = null);
        
        lvlAcctZinc = new Level__c(
            Name = 'Zinc',
            Target__c = 'Account',
            Source_Field__c = 'npo02__TotalOppAmount__c', 
            Level_Field__c = 'npo02__LastMembershipLevel__c', 
            Previous_Level_Field__c = 'npo02__LastMembershipOrigin__c', 
            Active__c = true, 
            Minimum_Amount__c = 100, 
            Maximum_Amount__c = 200);

        insert new Level__c[]{lvlTin, lvlBronze, lvlSilver, lvlGold, lvlPlatinum, lvlAcctZinc};        
    }

    /*********************************************************************************************************
    * @description create and add an Engagement Plan Template to the provided Level
    */
    static void createLevelEngagementPlan(Level__c lvl) {
        Engagement_Plan_Template__c template = new Engagement_Plan_Template__c(
            Name = 'Template',
            Skip_Weekends__c = false,
            Default_Assignee__c = EP_EngagementPlans_UTIL.BLANK_ASSIGNMENT_USER_CREATING
        );
        insert template;

        Engagement_Plan_Task__c epTask = new Engagement_Plan_Task__c(
            Name = 'Call',
            Engagement_Plan_Template__c = template.id,
            Assigned_To__c = UserInfo.getUserId(),
            Comments__c = 'This comment should be copied correctly to the Task.',
            Reminder__c = true,
            Send_Email__c = true,
            Days_After__c = 3,
            Type__c = 'Call',
            Priority__c = 'Medium'
        );
        insert epTask;

        Engagement_Plan_Task__c dependentEpTask = new Engagement_Plan_Task__c(
            Name = 'Email',
            Engagement_Plan_Template__c = template.id,
            Parent_Task__c = epTask.id,
            Comments__c = 'This comment should also be copied correctly to the Task.',
            Reminder__c = true,
            Send_Email__c = true,
            Days_After__c = 10,
            Type__c = 'Email',
            Priority__c = 'Low'
        );
        insert dependentEpTask;
        
        // now add the EPTemplate to the Level and update it.    
        lvl.Engagement_Plan_Template__c = template.Id;
        update lvl;
    }

    /*********************************************************************************************************
    * @description creates a list of Contacts with various donation totals for test code to use.
    */
    private static list<Contact> listCon { get; set; }
    private static DateTime dtCon6Modified { get; set;  }
    static void createTestContacts() {
        // create & insert contact
        listCon = UTIL_UnitTestData_TEST.CreateMultipleTestContacts(8);
        listCon[0].npo02__TotalOppAmount__c = null;
        listCon[1].npo02__TotalOppAmount__c = 10;
        listCon[2].npo02__TotalOppAmount__c = 100;
        
        listCon[3].npo02__TotalOppAmount__c = 999;
        listCon[3].npo02__LastMembershipLevel__c = lvlGold.Id;

        listCon[4].npo02__TotalOppAmount__c = 1000;
        listCon[4].npo02__LastMembershipLevel__c = lvlBronze.Id;
        
        listCon[5].npo02__TotalOppAmount__c = 5000;

        listCon[6].npo02__TotalOppAmount__c = 500;
        listCon[6].npo02__LastMembershipLevel__c = lvlBronze.Id;
        listCon[6].npo02__LastMembershipOrigin__c = lvlBronze.Id;
        
        listCon[7].npo02__TotalOppAmount__c = 100000;
        insert listCon;
        
        // we need to see what the LastModifiedDate is after the insert completes, in case any
        // workflow rules in the org may have ran during the insert and thus LastModifiedDate <> CreatedDate.
        // dtCon6Modified will be used in one of our asserts that this contact wasn't modified by our level batch.
         Contact c6 = [select LastModifiedDate from Contact where Id = :listCon[6].Id];
         dtCon6Modified = c6.LastModifiedDate;
    }
        
    /*********************************************************************************************************
    * @description creates a list of Accounts with various donation totals for test code to use.
    */
    private static list<Account> listAcc { get; set; }
    static void createTestAccounts() {
        // create & insert contact
        listAcc = UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(2, null);
        listAcc[0].npo02__TotalOppAmount__c = 10;
        listAcc[1].npo02__TotalOppAmount__c = 150;
        insert listAcc;
    }

    /*********************************************************************************************************
    * @description test our batch process for assigning Levels to Contacts
    */
    static testMethod void testContactLevels() {
        // create Levels
        createTestLevels();
        
        // add an Engagement Plan Template to Silver
        createLevelEngagementPlan(lvlSilver);
        
        // create test Contacts
        createTestContacts();
        
        string strSoql = 'SELECT Id, Name, npo02__TotalOppAmount__c, npo02__LastMembershipLevel__c, npo02__LastMembershipOrigin__c, CreatedDate, LastModifiedDate FROM Contact';
        LVL_LevelAssign_BATCH batch = new LVL_LevelAssign_BATCH(strSoql, 'Contact');
        
        Test.startTest();
        Database.executeBatch(batch);
        Test.stopTest();
        
        verifyContactLevels();
    }
    
    /*********************************************************************************************************
    * @description tests our Level Assigning when invokved through our Schedulable class.
    */
    static testMethod void testLevelAssignScheduling() {
        // create Levels
        createtestLevels();

        // add an Engagement Plan Template to Silver
        createLevelEngagementPlan(lvlSilver);
        
        // create test Contacts
        createTestContacts();

        LVL_LevelAssign_SCHED sched = new LVL_LevelAssign_SCHED();
        Test.startTest();
        sched.runBatch();
        Test.stopTest();
        
        verifyContactLevels();
    }

    /*********************************************************************************************************
    * @description verify contact levels correctly set
    */
    static void verifyContactLevels() {
        string strSoql = 'SELECT Id, Name, npo02__TotalOppAmount__c, npo02__LastMembershipLevel__c, npo02__LastMembershipOrigin__c, CreatedDate, LastModifiedDate FROM Contact';
        listCon = database.query(strSoql + ' order by FirstName');
        System.assertEquals(lvlTin.Id, listCon[0].npo02__LastMembershipLevel__c);
        System.assertEquals(null, listCon[0].npo02__LastMembershipOrigin__c);
        
        System.assertEquals(lvlTin.Id, listCon[1].npo02__LastMembershipLevel__c);
        System.assertEquals(null, listCon[1].npo02__LastMembershipOrigin__c);
        
        System.assertEquals(lvlBronze.Id, listCon[2].npo02__LastMembershipLevel__c);
        System.assertEquals(null, listCon[2].npo02__LastMembershipOrigin__c);
        
        System.assertEquals(lvlBronze.Id, listCon[3].npo02__LastMembershipLevel__c);
        System.assertEquals(lvlGold.Id, listCon[3].npo02__LastMembershipOrigin__c);
        
        System.assertEquals(lvlSilver.Id, listCon[4].npo02__LastMembershipLevel__c);
        System.assertEquals(lvlBronze.Id, listCon[4].npo02__LastMembershipOrigin__c);
        
        // check Silver's Engagement Plan got created
        list<Engagement_Plan__c> listEP = [select Id, Contact__c, Engagement_Plan_Template__c from Engagement_Plan__c]; 
        system.assertEquals(1, listEP.size());
        system.assertEquals(listCon[4].Id, listEP[0].Contact__c);
        system.assertEquals(lvlSilver.Engagement_Plan_Template__c, listEP[0].Engagement_Plan_Template__c);
        
        System.assertEquals(lvlGold.Id, listCon[5].npo02__LastMembershipLevel__c);
        System.assertEquals(null, listCon[5].npo02__LastMembershipOrigin__c);
        
        // contact6 should not be touched since it was already Bronze level.
        System.assertEquals(lvlBronze.Id, listCon[6].npo02__LastMembershipLevel__c);
        System.assertEquals(lvlBronze.Id, listCon[6].npo02__LastMembershipOrigin__c);
        System.assertEquals(dtCon6Modified, listCon[6].LastModifiedDate);
        
        System.assertEquals(lvlPlatinum.Id, listCon[7].npo02__LastMembershipLevel__c);
        System.assertEquals(null, listCon[7].npo02__LastMembershipOrigin__c);
    }
    
    /*********************************************************************************************************
    * @description test our batch process for assigning Levels to Accounts
    */
    static testMethod void testAccountLevels() {
        // create Levels
        createTestLevels();
        
        // add an Engagement Plan Template to Zinc
        createLevelEngagementPlan(lvlAcctZinc);

        // create test Accounts
        createTestAccounts();
        
        string strSoql = 'SELECT Id, Name, npo02__TotalOppAmount__c, npo02__LastMembershipLevel__c, ' +
            'npo02__LastMembershipOrigin__c, CreatedDate, LastModifiedDate FROM Account where npe01__SYSTEMIsIndividual__c = false ';
        LVL_LevelAssign_BATCH batch = new LVL_LevelAssign_BATCH(strSoql, 'Account');
        
        Test.startTest();
        Database.executeBatch(batch);
        Test.stopTest();
        
        listAcc = database.query(strSoql + ' order by Name');
        System.assertEquals(null, listAcc[0].npo02__LastMembershipLevel__c);
        System.assertEquals(null, listAcc[0].npo02__LastMembershipOrigin__c);
        
        System.assertEquals(lvlAcctZinc.Id, listAcc[1].npo02__LastMembershipLevel__c);
        System.assertEquals(null, listAcc[1].npo02__LastMembershipOrigin__c);

        // check Zinc's Engagement Plan got created
        list<Engagement_Plan__c> listEP = [select Id, Account__c, Engagement_Plan_Template__c from Engagement_Plan__c]; 
        system.assertEquals(1, listEP.size());
        system.assertEquals(listAcc[1].Id, listEP[0].Account__c);
        system.assertEquals(lvlAcctZinc.Engagement_Plan_Template__c, listEP[0].Engagement_Plan_Template__c);
    }
    
    /*********************************************************************************************************
    * @description tests our Level Validation to detect overlaps with existing levels
    */
    static testMethod void testLevelValidationExistingMin() {
        // create Levels
        createtestLevels();
        
        Level__c lvl = new Level__c(
            Name = 'Silver Overlap',
            Target__c = 'Contact',
            Source_Field__c = 'npo02__TotalOppAmount__c', 
            Level_Field__c = 'npo02__LastMembershipLevel__c', 
            Previous_Level_Field__c = 'npo02__LastMembershipOrigin__c', 
            Active__c = true, 
            Minimum_Amount__c = 1000, 
            Maximum_Amount__c = 4999);
            
        Test.startTest();
        try {
            insert lvl;
            system.assert(false, 'exception should have occurred');
        } catch(Exception ex) {
            system.assert(ex.getMessage().contains(lvl.Name));
        }
        
        // make sure null minimum also detected
        lvl.Minimum_Amount__c = null;
        try {
            insert lvl;
            system.assert(false, 'exception should have occurred');
        } catch(Exception ex) {
            system.assert(ex.getMessage().contains(lvl.Name));
        }
        Test.stopTest();
        
    }

    /*********************************************************************************************************
    * @description tests our Level Validation to detect overlaps with existing levels
    */
    static testMethod void testLevelValidationExistingMax() {
        // create Levels
        createtestLevels();
        
        Level__c lvl = new Level__c(
            Name = 'Bronze Overlap',
            Target__c = 'Contact',
            Source_Field__c = 'npo02__TotalOppAmount__c', 
            Level_Field__c = 'npo02__LastMembershipLevel__c', 
            Previous_Level_Field__c = 'npo02__LastMembershipOrigin__c', 
            Active__c = true, 
            Minimum_Amount__c = 10, 
            Maximum_Amount__c = 100);
            
        Test.startTest();
        try {
            insert lvl;
            system.assert(false, 'exception should have occurred');
        } catch(Exception ex) {
            system.assert(ex.getMessage().contains(lvl.Name));
        }

        // make sure null maximum also detected
        lvl.Maximum_Amount__c = null;
        try {
            insert lvl;
            system.assert(false, 'exception should have occurred');
        } catch(Exception ex) {
            system.assert(ex.getMessage().contains(lvl.Name));
        }
        Test.stopTest();
        
    }

    /*********************************************************************************************************
    * @description tests our Level Validation to detect overlaps with new levels
    */
    static testMethod void testLevelValidationNewMin() {
        
        Level__c lvl1 = new Level__c(
            Name = 'new',
            Target__c = 'Contact',
            Source_Field__c = 'npo02__TotalOppAmount__c', 
            Level_Field__c = 'npo02__LastMembershipLevel__c', 
            Previous_Level_Field__c = 'npo02__LastMembershipOrigin__c', 
            Active__c = true, 
            Minimum_Amount__c = 10, 
            Maximum_Amount__c = 100);
            
        Level__c lvl2 = new Level__c(
            Name = 'new2',
            Target__c = 'Contact',
            Source_Field__c = 'npo02__TotalOppAmount__c', 
            Level_Field__c = 'npo02__LastMembershipLevel__c', 
            Previous_Level_Field__c = 'npo02__LastMembershipOrigin__c', 
            Active__c = true, 
            Minimum_Amount__c = 101, 
            Maximum_Amount__c = 200);

        Level__c lvl3 = new Level__c(
            Name = 'new3',
            Target__c = 'Contact',
            Source_Field__c = 'npo02__TotalOppAmount__c', 
            Level_Field__c = 'npo02__LastMembershipLevel__c', 
            Previous_Level_Field__c = 'npo02__LastMembershipOrigin__c', 
            Active__c = true, 
            Minimum_Amount__c = 50, 
            Maximum_Amount__c = 75);

        try {
            Test.startTest();
            insert new Level__c[]{lvl1, lvl2, lvl3};
            Test.stopTest();
            system.assert(false, 'exception should have occurred');
        } catch(Exception ex) {
            system.assert(ex.getMessage().contains(lvl3.Name));
        }
    }

    /*********************************************************************************************************
    * @description tests our Level Validation to detect overlaps with new levels
    */
    static testMethod void testLevelValidationNewMax() {
        
        Level__c lvl1 = new Level__c(
            Name = 'new',
            Target__c = 'Contact',
            Source_Field__c = 'npo02__TotalOppAmount__c', 
            Level_Field__c = 'npo02__LastMembershipLevel__c', 
            Previous_Level_Field__c = 'npo02__LastMembershipOrigin__c', 
            Active__c = true, 
            Minimum_Amount__c = 10, 
            Maximum_Amount__c = 100);
            
        Level__c lvl2 = new Level__c(
            Name = 'new2',
            Target__c = 'Contact',
            Source_Field__c = 'npo02__TotalOppAmount__c', 
            Level_Field__c = 'npo02__LastMembershipLevel__c', 
            Previous_Level_Field__c = 'npo02__LastMembershipOrigin__c', 
            Active__c = true, 
            Minimum_Amount__c = 101, 
            Maximum_Amount__c = 200);

        Level__c lvl3 = new Level__c(
            Name = 'new3',
            Target__c = 'Contact',
            Source_Field__c = 'npo02__TotalOppAmount__c', 
            Level_Field__c = 'npo02__LastMembershipLevel__c', 
            Previous_Level_Field__c = 'npo02__LastMembershipOrigin__c', 
            Active__c = true, 
            Minimum_Amount__c = 5, 
            Maximum_Amount__c = 75);

        try {
            Test.startTest();
            insert new Level__c[]{lvl1, lvl2, lvl3};
            Test.stopTest();
            system.assert(false, 'exception should have occurred');
        } catch(Exception ex) {
            system.assert(ex.getMessage().contains(lvl3.Name));
        }
    }

    /*********************************************************************************************************
    * @description tests our Level Validation to detect duplicate names with existing levels
    */
    static testMethod void testLevelValidationExistingNames() {
        // create Levels
        createtestLevels();
        
        Level__c lvl = new Level__c(
            Name = 'Gold',
            Target__c = 'Contact',
            Source_Field__c = 'npo02__TotalOppAmount__c', 
            Level_Field__c = 'npo02__LastMembershipLevel__c', 
            Previous_Level_Field__c = 'npo02__LastMembershipOrigin__c', 
            Active__c = true, 
            Minimum_Amount__c = 90000, 
            Maximum_Amount__c = 90001);
            
        Test.startTest();
        try {
            insert lvl;
            system.assert(false, 'exception should have occurred');
        } catch(Exception ex) {
            system.assert(ex.getMessage().contains(lvl.Name));
        }
        
        // make sure rename also detected
        lvlSilver.Name = 'Gold';
        try {
            insert lvl;
            system.assert(false, 'exception should have occurred');
        } catch(Exception ex) {
            system.assert(ex.getMessage().contains(lvl.Name));
        }
        Test.stopTest();        
    }
    
    /*********************************************************************************************************
    * @description tests our Level Validation to detect duplicate names with new levels
    */
    static testMethod void testLevelValidationNewNames() {
        
        Level__c lvl1 = new Level__c(
            Name = 'new',
            Target__c = 'Contact',
            Source_Field__c = 'npo02__TotalOppAmount__c', 
            Level_Field__c = 'npo02__LastMembershipLevel__c', 
            Previous_Level_Field__c = 'npo02__LastMembershipOrigin__c', 
            Active__c = true, 
            Minimum_Amount__c = 10, 
            Maximum_Amount__c = 100);
            
        Level__c lvl2 = new Level__c(
            Name = 'new',
            Target__c = 'Contact',
            Source_Field__c = 'npo02__TotalOppAmount__c', 
            Level_Field__c = 'npo02__LastMembershipLevel__c', 
            Previous_Level_Field__c = 'npo02__LastMembershipOrigin__c', 
            Active__c = true, 
            Minimum_Amount__c = 101, 
            Maximum_Amount__c = 200);

        try {
            Test.startTest();
            insert new Level__c[]{lvl1, lvl2};
            Test.stopTest();
            system.assert(false, 'exception should have occurred');
        } catch(Exception ex) {
            system.assert(ex.getMessage().contains(lvl1.Name));
        }
    }
    
    /*********************************************************************************************************
    * @description tests our Level Validation to detect min/max overlap within a new level
    */
    static testMethod void testLevelValidationMinMaxOverlap() {
        
        Level__c lvl1 = new Level__c(
            Name = 'new',
            Target__c = 'Contact',
            Source_Field__c = 'npo02__TotalOppAmount__c', 
            Level_Field__c = 'npo02__LastMembershipLevel__c', 
            Previous_Level_Field__c = 'npo02__LastMembershipOrigin__c', 
            Active__c = true, 
            Minimum_Amount__c = 101, 
            Maximum_Amount__c = 100);

        try {
            Test.startTest();
            insert lvl1;
            Test.stopTest();
            system.assert(false, 'exception should have occurred');
        } catch(Exception ex) {
            system.assert(ex.getMessage().contains(lvl1.Name));
        }
    }
    
    /*********************************************************************************************************
    * @description tests our Level Validation to detect no min & max specified
    */
    static testMethod void testLevelValidationMinMaxMustExist() {
        
        Level__c lvl1 = new Level__c(
            Name = 'new',
            Target__c = 'Contact',
            Source_Field__c = 'npo02__TotalOppAmount__c', 
            Level_Field__c = 'npo02__LastMembershipLevel__c', 
            Previous_Level_Field__c = 'npo02__LastMembershipOrigin__c', 
            Active__c = true);

        try {
            Test.startTest();
            insert lvl1;
            Test.stopTest();
            system.assert(false, 'exception should have occurred');
        } catch(Exception ex) {
            system.assert(ex.getMessage().contains(lvl1.Name));
        }
    }

}