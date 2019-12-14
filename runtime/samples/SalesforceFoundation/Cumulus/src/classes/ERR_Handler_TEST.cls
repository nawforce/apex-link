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
* @description test class for error handling
* @group ErrorHandling
*/
@isTest
public class ERR_Handler_TEST {

    // if you only want to run one test in this class, fill in its name here.
    // if you want to run all tests, then use '*'
    private static string strTestOnly = '*';
    
    private static Id chatterGroupId;
    /*******************************************************************************************************
    * @description test setup of error notifications via chatter
    */
    private static void setupWithChatterNotifications() {
    	// all these tests are about testing our error handling, so we can't let the assert interfere
    	TDTM_TriggerHandler.suppressDebugAssertAfterErrorLogging = true;        
    	
    	UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c(
            npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR));
    	UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(new npo02__Households_Settings__c (
            npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR, npo02__Enable_Opp_Rollup_Triggers__c = true));
    	
    	if(Schema.SObjectType.User.isFeedEnabled()) {
	    	SObject chatterGroup = (SObject)System.Type.forName('CollaborationGroup').newInstance();
	    	chatterGroup.put('Name', 'ChatterTestGroup');
	    	chatterGroup.put('CollaborationType', 'Private');
	    	insert chatterGroup;
	    	chatterGroupId = chatterGroup.Id;
	    	UTIL_CustomSettingsFacade.getErrorSettingsForTests(new Error_Settings__c(Error_Notifications_On__c = true, 
	    	  Error_Notifications_To__c = chatterGroup.Id, Store_Errors_On__c = true));
    	} else {
    		UTIL_CustomSettingsFacade.getErrorSettingsForTests(new Error_Settings__c(Error_Notifications_On__c = true, 
    		  Error_Notifications_To__c = null, Store_Errors_On__c = true));
    	}
    }
    /*******************************************************************************************************
    * @description test setup of error notifications via email
    */
    private static void setupWithEmailNotifications() {
        // all these tests are about testing our error handling, so we can't let the assert interfere
        TDTM_TriggerHandler.suppressDebugAssertAfterErrorLogging = true;        

        UTIL_CustomSettingsFacade.getContactsSettingsForTests(new npe01__Contacts_and_Orgs_Settings__c(
            npe01__Account_Processor__c = CAO_Constants.HH_ACCOUNT_PROCESSOR));
        UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(new npo02__Households_Settings__c (
            npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR, npo02__Enable_Opp_Rollup_Triggers__c = true));
        
        User user = UTIL_UnitTestData_TEST.CreateNewUserForTests(System.now().getTime() + '@testerson.com');
        UTIL_CustomSettingsFacade.getErrorSettingsForTests(new Error_Settings__c(Error_Notifications_On__c = true, 
            Error_Notifications_To__c = user.Id, Store_Errors_On__c = true));
    }

    /**
     * @description Trigger error notification processing by calling
     * ERR_Handler.processError() and verify that the error was posted to
     * Chatter.
     */
    public testmethod static void errorsStoredInDbTdtmCustomConfigPostToChatter() {
    	if (strTestOnly != '*' && strTestOnly != 'errorsStoredInDbTdtmCustomConfigPostToChatter') return;
    	
    	setupWithChatterNotifications();
    	
        StringException testException = new StringException('Test Exception');
        ERR_Handler.processError(testException, ERR_Handler_API.Context.TDTM);

        List<Error__c> errors = [select Id, Full_Message__c, Stack_Trace__c, Posted_in_Chatter__c from Error__c];
        System.assertEquals(1, errors.size());
        UTIL_Debug.debug('****Errors: ' + JSON.serializePretty(errors));
        
        //Verify error was posted to chatter group
        if(Schema.SObjectType.User.isFeedEnabled()) {
	        List<SObject> chatterPosts = Database.query('select Title from FeedItem where ParentId = :chatterGroupId');
	        System.assertEquals(1, chatterPosts.size());
	        System.assertEquals('Salesforce Error', chatterPosts[0].get('Title'));
	        for(Error__c error : errors) {
	        	System.assertEquals(true, error.Posted_in_Chatter__c);
	        }
        }
    }
    
    /**
     * @description Trigger error notification processing by calling
     * ERR_Handler.processError() and verify that the error was emailed
     */
    public testmethod static void errorsStoredInDbTdtmCustomConfigEmailErrors() {
        if (strTestOnly != '*' && strTestOnly != 'errorsStoredInDbTdtmCustomConfigEmailErrors') return;
        
        setupWithEmailNotifications();

        Test.startTest();

        StringException testException = new StringException('Test Exception');
        ERR_Handler.processError(testException, ERR_Handler_API.Context.TDTM);

        Test.stopTest();

        List<Error__c> errors = [select Id, Full_Message__c, Stack_Trace__c, Email_Sent__c from Error__c];
        System.assertEquals(1, errors.size());
        
        //Verify error was emailed
        for(Error__c error : errors) {
            System.assertEquals(true, error.Email_Sent__c);
        }
    }

    /*******************************************************************************************************
    * @description This is the same test called testErrorRecordCreation in the REL_Relationships_TEST class,
    * but using simple insert instead of Database.insert
    */
    static testMethod void errorStoredInDatabaseFromInsert() {
        if (strTestOnly != '*' && strTestOnly != 'errorStoredInDatabaseFromInsert') return;
        
        setupWithChatterNotifications();
        
        Contact contact1 = new Contact(FirstName = 'test', LastName = 'testerson1', Title = 'VP1');
        insert contact1;
                
        npe4__Relationship__c[] relationships = new npe4__Relationship__c[]{
            new npe4__Relationship__c(npe4__contact__c = contact1.id, npe4__Type__c='TestLookupType') };
        
        try {
            insert relationships;
        } catch (exception ex) {
            ERR_Handler.processError(ex, ERR_Handler_API.Context.REL);
        }
        //assert an error record was created - Required fields are missing: [npe4__Contact__c]
        //npe4__Contact__c is missing from the reciprocal relationship that gets automatically created.
        system.assertEquals(1, [select count() from Error__c]);
        
        //update relationship - this will create the missing npe4__Contact__c field
        relationships[0].npe4__RelatedContact__c = contact1.id;
        Database.SaveResult[] updateResults = Database.update(relationships, false);
        system.assertNotEquals(null, relationships[0].npe4__Contact__c);
        
        //get errors
        ERR_Handler.Errors errors = ERR_Handler.getErrors(updateResults, (List<SObject>)relationships);
        //we still get an error, because of the Related_Contact_Do_Not_Change validation rule on Relationship
        system.assertEquals(true, errors.errorsExist);
        system.assertEquals(1, errors.errorRecords.size());

        //assert no additional error record was created on update
        system.assertEquals(1, [select count() from Error__c]);
    }

    /*******************************************************************************************************
    * @description Testing that DML changes are properly rolled back in a controller that adheres to our
    * error-handling design, and that the error is properly stored.
    */
    static testMethod void controllerWithManualRollback() {
        if (strTestOnly != '*' && strTestOnly != 'controllerWithManualRollback') return;
        
        setupWithChatterNotifications();
        
        ERR_Handler_CTRL_TEST controller = new ERR_Handler_CTRL_TEST();
        
        Test.startTest();
        controller.withManualRollback();
        Test.stopTest();
        
        //assert contact record creation was rolled back
        system.assertEquals(0, [select count() from Contact]);
        
        //assert relationship record was not stored
        system.assertEquals(0, [select count() from npe4__Relationship__c]);
         
        //assert an error record was created - Required fields are missing: [npe4__Contact__c]
        system.assertEquals(1, [select count() from Error__c]);    
    }
    /*******************************************************************************************************
    * @description Test that an inactive user won't receive email as the primary error user 
    */
    static testMethod void testInactiveUserEmailBehavior() {

        if (strTestOnly != '*' && strTestOnly != 'testInactiveUserEmailBehavior') return;

        String un = System.now().getTime() + '@testerson.com';
        User u = UTIL_UnitTestData_TEST.createNewInactiveUserForTests(un);

        UTIL_CustomSettingsFacade.getErrorSettingsForTests(new Error_Settings__c(Error_Notifications_On__c = true, 
            Error_Notifications_To__c = u.Id, Store_Errors_On__c = true));
 
        Test.startTest();

        StringException testException = new StringException('Test Exception');
        ERR_Handler.processError(testException, ERR_Handler_API.Context.TDTM);

        Test.stopTest();

        List<Error__c> errors = [select Id, Full_Message__c, Stack_Trace__c, Email_Sent__c from Error__c];
        System.assertEquals(1, errors.size());

        //Verify error was NOT emailed
        for(Error__c error : errors) {
            System.assertEquals(false, error.Email_Sent__c);
        }
    }
}