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
* @description Validator to provide address geocoding/verification through the Google Geocoding API
 */
@isTest
public with sharing class ADDR_GoogleGeoAPI_Validator_TEST {
    public static Addr_Verification_Settings__c getDefaultSettings() {
        // use the household account model
        UTIL_CustomSettingsFacade.getContactsSettings();

        // create the basic api settings
        Addr_Verification_Settings__c settings = UTIL_CustomSettingsFacade.getAddressVerificationSettings();
        settings.Using_SmartyStreets__c = false;
        settings.Auth_ID__c = 'hey';
        settings.Auth_Token__c = 'there';
        settings.Address_Verification_Endpoint__c = 'maps.googleapis.com/maps/api/geocode/json?';
        settings.Zipcode_Verification_Endpoint__c = 'maps.googleapis.com/maps/api/geocode/json?';
        settings.Class__c = 'ADDR_GoogleGeoAPI_Validator';
        settings.Enable_Automatic_Verification__c = true;
        settings.Reject_Ambiguous_Addresses__c = true;
        
        return settings;
    }

    static testMethod void testSingleAddress() {

        Addr_Verification_Settings__c settings = ADDR_GoogleGeoAPI_Validator_TEST.getDefaultSettings();
        Test.setMock(HttpCalloutMock.class, new ADDR_MockGoogleGeoAPIResponse_TEST());

        Address__c a = new Address__c(MailingState__c = 'CA',  MailingCity__c = 'Cupertino', MailingStreet__c = 'single-address');

        Test.StartTest();
        ADDR_GoogleGeoAPI_Validator testValidator = new ADDR_GoogleGeoAPI_Validator();
        List<Address__c> returnedAddress = testValidator.verifyRecords(new List<Address__c>{a}, settings);
        Test.StopTest();

        System.assertEquals(1, returnedAddress.size());
        System.assertEquals('1600 Amphitheatre Pkwy', returnedAddress[0].MailingStreet__c);
        System.assertEquals('CA', returnedAddress[0].MailingState__c);
        System.assertEquals('Mountain View', returnedAddress[0].MailingCity__c);
        System.assertEquals('94043', returnedAddress[0].MailingPostalCode__c);
        System.assertEquals(true, returnedAddress[0].Verified__c);
    }

    static testMethod void testSingleAddressSubPremise() {

        Addr_Verification_Settings__c settings = ADDR_GoogleGeoAPI_Validator_TEST.getDefaultSettings();
        Test.setMock(HttpCalloutMock.class, new ADDR_MockGoogleGeoAPIResponse_TEST());

        Address__c a = new Address__c(MailingState__c = 'CA',  MailingCity__c = 'Cupertino', MailingCountry__c = 'single-address', MailingStreet__c = '1600 Amphitheatre pkwy, #8');

        Test.StartTest();
        ADDR_GoogleGeoAPI_Validator testValidator = new ADDR_GoogleGeoAPI_Validator();
        List<Address__c> returnedAddress = testValidator.verifyRecords(new List<Address__c>{a}, settings);
        Test.StopTest();

        System.assertEquals(1, returnedAddress.size());
        System.assertEquals('1600 Amphitheatre Pkwy, #8', returnedAddress[0].MailingStreet__c);
        System.assertEquals('CA', returnedAddress[0].MailingState__c);
        System.assertEquals('Mountain View', returnedAddress[0].MailingCity__c);
        System.assertEquals('94043', returnedAddress[0].MailingPostalCode__c);
        System.assertEquals(true, returnedAddress[0].Verified__c);
    }
    
    static testMethod void testSingleAddressLongZipcode() {

        Addr_Verification_Settings__c settings = ADDR_GoogleGeoAPI_Validator_TEST.getDefaultSettings();
        Test.setMock(HttpCalloutMock.class, new ADDR_MockGoogleGeoAPIResponse_TEST());

        Address__c a = new Address__c(MailingState__c = 'CA',  MailingCity__c = 'Cupertino', MailingStreet__c = 'single-address', 
                                    MailingPostalCode__c='94043-9475');

        Test.StartTest();
        ADDR_GoogleGeoAPI_Validator testValidator = new ADDR_GoogleGeoAPI_Validator();
        List<Address__c> returnedAddress = testValidator.verifyRecords(new List<Address__c>{a}, settings);
        Test.StopTest();

        System.assertEquals(1, returnedAddress.size());
        System.assertEquals('1600 Amphitheatre Pkwy', returnedAddress[0].MailingStreet__c);
        System.assertEquals('CA', returnedAddress[0].MailingState__c);
        System.assertEquals('Mountain View', returnedAddress[0].MailingCity__c);
        System.assertEquals('94043-9475', returnedAddress[0].MailingPostalCode__c);
        System.assertEquals(true, returnedAddress[0].Verified__c);
    }

    static testMethod void testAmbiguousAddress() {
        Addr_Verification_Settings__c settings = ADDR_GoogleGeoAPI_Validator_TEST.getDefaultSettings();
        Test.setMock(HttpCalloutMock.class, new ADDR_MockGoogleGeoAPIResponse_TEST());

        Address__c a = new Address__c(MailingState__c = 'CA',  MailingCity__c = 'Cupertino', MailingStreet__c = 'ambiguous-address');

        Test.StartTest();
        ADDR_GoogleGeoAPI_Validator testValidator = new ADDR_GoogleGeoAPI_Validator();
        List<Address__c> returnedAddress = testValidator.verifyRecords(new List<Address__c>{a}, settings);
        Test.StopTest();

        System.assertEquals(1, returnedAddress.size());
        System.assertEquals(false, returnedAddress[0].Verified__c);
        System.assertEquals(true, returnedAddress[0].Ambiguous__c);
    }

    static testMethod void testOverQueryLimitAddress() {
        Addr_Verification_Settings__c settings = ADDR_GoogleGeoAPI_Validator_TEST.getDefaultSettings();
        Test.setMock(HttpCalloutMock.class, new ADDR_MockGoogleGeoAPIResponse_TEST());

        Address__c a = new Address__c(MailingState__c = 'CA',  MailingCity__c = 'Cupertino', MailingStreet__c = 'over-query-limit');

        Test.StartTest();
        ADDR_GoogleGeoAPI_Validator testValidator = new ADDR_GoogleGeoAPI_Validator();
        List<Address__c> returnedAddress = testValidator.verifyRecords(new List<Address__c>{a}, settings);
        Test.StopTest();
        
        System.assertEquals(1, returnedAddress.size());
        System.assertEquals('OVER_QUERY_LIMIT: indicates that you are over your quota.', returnedAddress[0].API_Response__c);
        System.assertEquals(false, returnedAddress[0].Verified__c);
    }
    
        static testMethod void testRequestDeniedAddress() {
        Addr_Verification_Settings__c settings = ADDR_GoogleGeoAPI_Validator_TEST.getDefaultSettings();
        Test.setMock(HttpCalloutMock.class, new ADDR_MockGoogleGeoAPIResponse_TEST());

        Address__c a = new Address__c(MailingState__c = 'CA',  MailingCity__c = 'Cupertino', MailingStreet__c = 'request-denied');

        Test.StartTest();
        ADDR_GoogleGeoAPI_Validator testValidator = new ADDR_GoogleGeoAPI_Validator();
        List<Address__c> returnedAddress = testValidator.verifyRecords(new List<Address__c>{a}, settings);
        Test.StopTest();

        System.assertEquals(1, returnedAddress.size());
        System.assertEquals('REQUEST_DENIED: indicates that your request was denied.', returnedAddress[0].API_Response__c);
    }

    static testMethod void testUnknownErrorAddress() {
        Addr_Verification_Settings__c settings = ADDR_GoogleGeoAPI_Validator_TEST.getDefaultSettings();
        Test.setMock(HttpCalloutMock.class, new ADDR_MockGoogleGeoAPIResponse_TEST());

        Address__c a = new Address__c(MailingState__c = 'CA',  MailingCity__c = 'Cupertino', MailingStreet__c = 'unknown-error');

        Test.StartTest();
        ADDR_GoogleGeoAPI_Validator testValidator = new ADDR_GoogleGeoAPI_Validator();
        List<Address__c> returnedAddress = testValidator.verifyRecords(new List<Address__c>{a}, settings);
        Test.StopTest();

        System.assertEquals(1, returnedAddress.size());
        System.assertEquals('UNKNOWN_ERROR: indicates that the request could not be processed due to a server error. The request may succeed if you try again.', returnedAddress[0].API_Response__c);
    }

    static testMethod void testInvalidRequestAddress() {
        Addr_Verification_Settings__c settings = ADDR_GoogleGeoAPI_Validator_TEST.getDefaultSettings();
        Test.setMock(HttpCalloutMock.class, new ADDR_MockGoogleGeoAPIResponse_TEST());

        Address__c a = new Address__c(MailingState__c = 'CA',  MailingCity__c = 'Cupertino', MailingStreet__c = 'invalid-request');

        Test.StartTest();
        ADDR_GoogleGeoAPI_Validator testValidator = new ADDR_GoogleGeoAPI_Validator();
        List<Address__c> returnedAddress = testValidator.verifyRecords(new List<Address__c>{a}, settings);
        Test.StopTest();

        System.assertEquals(1, returnedAddress.size());
        System.assertEquals('INVALID_REQUEST: generally indicates that the query (address, components or latlng) is missing.', returnedAddress[0].API_Response__c);
    }

    static testMethod void testZeroResultsAddress() {
        Addr_Verification_Settings__c settings = ADDR_GoogleGeoAPI_Validator_TEST.getDefaultSettings();
        Test.setMock(HttpCalloutMock.class, new ADDR_MockGoogleGeoAPIResponse_TEST());

        Address__c a = new Address__c(MailingState__c = 'CA',  MailingCity__c = 'Cupertino', MailingStreet__c = 'zero-results');

        Test.StartTest();
        ADDR_GoogleGeoAPI_Validator testValidator = new ADDR_GoogleGeoAPI_Validator();
        List<Address__c> returnedAddress = testValidator.verifyRecords(new List<Address__c>{a}, settings);
        Test.StopTest();

        System.assertEquals(1, returnedAddress.size());
        System.assertEquals('ZERO_RESULTS: indicates that the geocode was successful but returned no results. This may occur if the geocoder was passed a non-existent address.', returnedAddress[0].API_Response__c);
    }
    static testMethod void testBadResponse() {
        Addr_Verification_Settings__c settings = ADDR_GoogleGeoAPI_Validator_TEST.getDefaultSettings();
        Test.setMock(HttpCalloutMock.class, new ADDR_MockGoogleGeoAPIResponse_TEST());

        Address__c a = new Address__c(MailingState__c = 'CA',  MailingCity__c = 'Cupertino', MailingStreet__c = '401');

        Test.StartTest();
        ADDR_GoogleGeoAPI_Validator testValidator = new ADDR_GoogleGeoAPI_Validator();
        List<Address__c> returnedAddress = testValidator.verifyRecords(new List<Address__c>{a}, settings);
        Test.StopTest();

        System.assertEquals(1, returnedAddress.size());
        System.assertEquals('401: Not authenticated.', returnedAddress[0].API_Response__c);
    }
    static testMethod void testNullAuthToken() {
        // create settings w/o a token
        Addr_Verification_Settings__c settings = UTIL_CustomSettingsFacade.getAddressVerificationSettings();
        settings.Address_Verification_Endpoint__c = 'maps.googleapis.com/maps/api/geocode/json?';
        settings.Class__c = 'ADDR_GoogleGeoAPI_Validator';
        settings.Enable_Automatic_Verification__c = true;
        

        Address__c a = new Address__c(MailingState__c = 'CA',  MailingCity__c = 'Cupertino');

        Test.StartTest();
        ADDR_GoogleGeoAPI_Validator testValidator = new ADDR_GoogleGeoAPI_Validator();
        List<Address__c> returnedAddress = testValidator.verifyRecords(new List<Address__c>{a}, settings);
        Test.StopTest();

        System.assertEquals(1, returnedAddress.size());
        System.assertEquals('MISSING AUTHID OR ENDPOINT.  Please see the Address Verification menu in the NPSP Settings tab for more information', returnedAddress[0].API_Response__c);
    }
    static testMethod void testInterfaceStrings(){
        ADDR_GoogleGeoAPI_Validator testValidator = new ADDR_GoogleGeoAPI_Validator();
        system.assertEquals('The Google Geocoding API', testValidator.getServiceName());
        system.assertEquals('https://maps.googleapis.com/maps/api/geocode/json?', testValidator.getDefaultURL());
    }
}