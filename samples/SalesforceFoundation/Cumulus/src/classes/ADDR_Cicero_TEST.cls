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
* @group Address Management
* @description test class that provides address validation/normalization services
* through the Cicero API.
 */

@isTest
public with sharing class ADDR_Cicero_TEST {
	
    /*********************************************************************************************************
    * @description if you only want to run one test in this class, fill in its name here.
    * if you want to run all tests, then use '*'
    */
    private static string strTestOnly = '*';
    
    /*********************************************************************************************************
    * @description a fake http response from Cicero for test code
    */
    private static string strResponseTest = 
		'{'+
		'"response" : {'+
		'"messages" : [ ],'+
		'"results" : {'+
		'"candidates" : [ {'+
		'"x" : -122.162791,'+
		'"y" : 47.63411,'+
		'"match_addr" : "2623 134th Ave NE, Bellevue, Washington, 98005",'+
		'"locator_type" : "PointAddress",'+
		'"score" : 100,'+
		'"districts" : [ {'+
		'"valid_to" : null,'+
		'"last_update_date" : "2012-03-18 07:46:32",'+
		'"district_type" : "NATIONAL_EXEC",'+
		'"subtype" : "NATION",'+
		'"id" : 19,'+
		'"country" : "US",'+
		'"city" : "",'+
		'"label" : "United States",'+
		'"district_id" : "UNITED STATES",'+
		'"state" : "",'+
		'"data" : { },'+
		'"valid_from" : "0001-01-01 04:56:02",'+
		'"sk" : 19'+
		'}, {'+
		'"valid_to" : null,'+
		'"last_update_date" : "2012-03-18 07:46:32",'+
		'"district_type" : "NATIONAL_UPPER",'+
		'"subtype" : "UPPER",'+
		'"id" : 74,'+
		'"country" : "US",'+
		'"city" : "",'+
		'"label" : "Washington",'+
		'"district_id" : "WA",'+
		'"state" : "WA",'+
		'"data" : { },'+
		'"valid_from" : "1977-05-18 00:00:00",'+
		'"sk" : 74'+
		'}, {'+
		'"valid_to" : null,'+
		'"last_update_date" : "2012-03-18 07:46:32",'+
		'"district_type" : "STATE_EXEC",'+
		'"subtype" : "STATE",'+
		'"id" : 1009,'+
		'"country" : "US",'+
		'"city" : "",'+
		'"label" : "Washington",'+
		'"district_id" : "WA",'+
		'"state" : "WA",'+
		'"data" : { },'+
		'"valid_from" : "0001-01-01 04:56:02",'+
		'"sk" : 1009'+
		'}, {'+
		'"valid_to" : null,'+
		'"last_update_date" : "2012-03-18 07:46:31",'+
		'"district_type" : "LOCAL",'+
		'"subtype" : "COUNTY",'+
		'"id" : 2908,'+
		'"country" : "US",'+
		'"city" : "King",'+
		'"label" : "",'+
		'"district_id" : "6",'+
		'"state" : "WA",'+
		'"data" : { },'+
		'"valid_from" : "0001-01-01 04:56:02",'+
		'"sk" : 2908'+
		'}, {'+
		'"valid_to" : null,'+
		'"last_update_date" : "2012-03-18 07:46:31",'+
		'"district_type" : "LOCAL",'+
		'"subtype" : "COUNTY",'+
		'"id" : 2916,'+
		'"country" : "US",'+
		'"city" : "King",'+
		'"label" : "",'+
		'"district_id" : "AT LARGE",'+
		'"state" : "WA",'+
		'"data" : { },'+
		'"valid_from" : "0001-01-01 04:56:02",'+
		'"sk" : 2916'+
		'}, {'+
		'"valid_to" : "2023-01-09 00:00:00",'+
		'"last_update_date" : "2012-11-20 00:00:00",'+
		'"district_type" : "STATE_UPPER",'+
		'"subtype" : "UPPER",'+
		'"id" : 756663,'+
		'"country" : "US",'+
		'"city" : "",'+
		'"label" : "Washington State Senate district 48",'+
		'"district_id" : "48",'+
		'"state" : "WA",'+
		'"data" : { },'+
		'"valid_from" : "2013-01-14 00:00:00",'+
		'"sk" : 756663'+
		'}, {'+
		'"valid_to" : "2023-01-09 00:00:00",'+
		'"last_update_date" : "2012-11-20 00:00:00",'+
		'"district_type" : "STATE_LOWER",'+
		'"subtype" : "LOWER",'+
		'"id" : 756712,'+
		'"country" : "US",'+
		'"city" : "",'+
		'"label" : "Washington House of Representatives district 48",'+
		'"district_id" : "48",'+
		'"state" : "WA",'+
		'"data" : { },'+
		'"valid_from" : "2013-01-14 00:00:00",'+
		'"sk" : 756712'+
		'}, {'+
		'"valid_to" : "2023-01-03 00:00:00",'+
		'"last_update_date" : "2012-11-30 00:00:00",'+
		'"district_type" : "NATIONAL_LOWER",'+
		'"subtype" : "LOWER",'+
		'"id" : 757403,'+
		'"country" : "US",'+
		'"city" : "",'+
		'"label" : "Washington\'s 9th congressional district",'+
		'"district_id" : "9",'+
		'"state" : "WA",'+
		'"data" : { },'+
		'"valid_from" : "2013-01-03 00:00:00",'+
		'"sk" : 757403'+
		'} ],'+
		'"geoservice" : "EsriWGS",'+
		'"wkid" : 4326,'+
		'"count" : {'+
		'"from" : 0,'+
		'"total" : 8,'+
		'"to" : 7'+
		'},'+
		'"locator" : "rooftop"'+
		'} ]'+
		'},'+
		'"errors" : [ ]'+
		'}'+
		'}';
    
    /*********************************************************************************************************
    * @description returns the HTTP Response from the HTTP Request for Cicero.
    * @param req the HTTP Request object
    * *returns HTTPResponse
    */
    public static HTTPResponse respond(HTTPRequest req) {        
        // Create a fake response
        HttpResponse res = new HttpResponse();
        res.setHeader('Content-Type', 'application/json');
        res.setBody(strResponseTest);
        res.setStatusCode(200);
        return res;
    }

    /*********************************************************************************************************
    * @description Tests validating a single address.
    */
	private static testmethod void singleValidAddr() {
        if (strTestOnly != '*' && strTestOnly != 'NewContactsNoAccounts') return;

        list<Address__c> listAddr = new list<Address__c>();
        Address__c addr = new Address__c();
        addr.MailingStreet__c = '2623 134 ave ne, #2';
        addr.MailingCity__c = 'bellevue';
        addr.MailingState__c = 'wa';
        addr.MailingPostalCode__c = '98005';
        listAddr.add(addr);
        
        ADDR_Cicero_Validator cicero = new ADDR_Cicero_Validator();
        Addr_Verification_Settings__c settings = new Addr_Verification_Settings__c();
        settings.Auth_Token__c = 'foo';  // we need a fake token or we won't validate
        settings.Address_Verification_Endpoint__c = 'bar';
        List<Address__c> listAddrVerified = cicero.verifyRecords(listAddr, settings);
        system.assertEquals(1, listAddrVerified.size());
        //system.assertNotEquals(null, listAddrVerified[0].Pre_Verification_Address__c);
        system.assertEquals(true, listAddrVerified[0].Verified__c);
        system.assertEquals('2623 134th Ave NE, #2', listAddrVerified[0].MailingStreet__c);
        system.assertEquals('Bellevue', listAddrVerified[0].MailingCity__c);
        system.assertEquals('Washington', listAddrVerified[0].MailingState__c);
        system.assertEquals('48', listAddrVerified[0].State_Lower_District__c);
        system.assertEquals('48', listAddrVerified[0].State_Upper_District__c);

	}

}