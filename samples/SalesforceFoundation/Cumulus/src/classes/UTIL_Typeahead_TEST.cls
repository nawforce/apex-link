/*
    Copyright (c) 2011, Salesforce.org
    All rights reserved.
    
    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are met:
    
    * Redistributions of source code must retain the above copyright
      notice, this List of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this List of conditions and the following disclaimer in the
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
* @group Utilities
* @description Test class for the Force.com Typeahead component
*/
@isTest
public with sharing class UTIL_Typeahead_TEST {

    private static testMethod void testQuery() {
	    list<Account> aa = new list<Account>{
	      new Account(Name='Test Account'),
	      new Account(Name='Another Account', BillingCity='Test City'),
	      new Account(Name='Third Account')
	    };
	    insert aa;
	    
	      // crazy, but you have to fake this
	      list<Id> fixedSearchResults= new list<Id>{aa[0].id, aa[1].id};
	      Test.setFixedSearchResults(fixedSearchResults);
	
	    Test.startTest();
	    list<sObject> accts = UTIL_Typeahead.searchRecords( 'test', 'Account', null, null, null, null, null );
	    Test.stopTest();
	
	    system.assertEquals(2, accts.size());
	    system.assertEquals('Another Account', accts[0].get('Name'));
	    system.assertEquals('Test Account', accts[1].get('Name'));
    }
}