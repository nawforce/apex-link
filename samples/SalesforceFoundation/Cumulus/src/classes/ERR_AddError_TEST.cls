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
* @description The  tests in this class confirm that error messages are associated with the right records. 
* We cannot directly test the Helper class since there is no "getError" method, but we indirectly test 
* it here since we are following the same design.
* @group ErrorHandling
*/
@isTest
public with sharing class ERR_AddError_TEST {
    /*******************************************************************************************************
    * @description test (lack of) error handling on a database dml operation, verify success and failure results
    */
    public testmethod static void databaseDml() {
    	Error_Settings__c errorSettings = UTIL_CustomSettingsFacade.getErrorSettings();
    	// Delete the error settings if they are from the database
    	if (errorSettings.id != null) {
    	    delete errorSettings;
    	}
    	
        Account acc1 = new Account(Name='testAcc1');
        Account acc2 = new Account(Name='testAcc2');
        Account acc3 = new Account(Name='testAcc3');
        Account acc4 = new Account(Name='testAcc4');     
        insert new Account[] {acc1, acc2, acc3, acc4};
        
        Contact contact1 = new Contact(FirstName = 'test1', LastName = 'testerson', AccountId = acc1.Id, Title = 'VP');
        Contact contact2 = new Contact(FirstName = 'test2', LastName = 'testerson', AccountId = acc2.Id, Title = 'VP');
        Contact contact3 = new Contact(FirstName = 'test3', LastName = 'testerson', AccountId = acc3.Id, Title = 'VP');
        Contact contact4 = new Contact(FirstName = 'test3', LastName = 'testerson', AccountId = acc4.Id, Title = 'VP');
        Contact[] contacts = new Contact[] {contact1, contact2, contact3, contact4};
        
        delete new Account[] {acc2, acc4};
        
        List<Database.Saveresult> results = Database.insert(contacts, false);
        System.assertEquals(4, results.size());
        /* The failure of any of the contacts in the trigger, due to having a
         * null Account lookup, will cause an unhandled exception and the
         * entire trigger will be rolled back.  Every record will be marked as
         * an error with the message corresponding to the unhandled exception.
         */
        System.assertEquals(false, results[0].isSuccess());
        System.assertEquals(false, results[1].isSuccess());
        System.assertEquals(false, results[2].isSuccess());
        System.assertEquals(false, results[3].isSuccess());
    }
}