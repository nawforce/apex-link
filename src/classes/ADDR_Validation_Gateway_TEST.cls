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
* @description Class that tests ADDR_SmartyStreets_Gateway class.
*/
@isTest
public with sharing class ADDR_Validation_Gateway_TEST {
	
	// if you only want to run one test in this class, fill in its name here.
    // if you want to run all tests, then use '*'
    private static string strTestOnly = '*';

    private static Addr_Verification_Settings__c settings;
    
    public static ADDR_SmartyStreets_Gateway createDefaultSettings() {
        // use the household account model
        UTIL_CustomSettingsFacade.getContactsSettings();

        // create the basic api authentication settings
        settings = UTIL_CustomSettingsFacade.getAddressVerificationSettings();
        settings.Auth_ID__c = 'hey';
        settings.Auth_Token__c = 'there';
        settings.Class__c = 'ADDR_SmartyStreets_Validator';
        settings.Enable_Automatic_Verification__c = true;
        settings.Reject_Ambiguous_Addresses__c = true;
        
        Test.setMock(HttpCalloutMock.class, new ADDR_MockHttpRespGenerator_TEST());
        return new ADDR_SmartyStreets_Gateway(settings);
        
    }
    
    public static testMethod void testOneAddress() {
    	if (strTestOnly != '*' && strTestOnly != 'testOneAddress') return;
    	ADDR_SmartyStreets_Gateway gateway = createDefaultSettings();
        
        Test.startTest();
        ADDR_SmartyStreets_Gateway.AddressResponse sr = gateway.verifyAddress('single-address');
        Test.stopTest();

        system.assert(sr != null);
        system.assertEquals(sr.delivery_line_1, '1 Infinite Loop');
        system.assertEquals(37.33118, sr.metadata.latitude);
        UTIL_Debug.debug(logginglevel.warn, sr.getFullAddress());
    }

    public static testMethod void testMultiAddress() {
    	if (strTestOnly != '*' && strTestOnly != 'testMultiAddress') return;
    	ADDR_SmartyStreets_Gateway gateway = createDefaultSettings();
    	
        Test.startTest();
        list<ADDR_SmartyStreets_Gateway.AddressResponse> srs = gateway.verifyAddresses(
            new list<String>{'One Infinite Loop Cupertino multiple-address, CA', 'Two Infinite Loop Cupertino, CA'} );
        Test.stopTest();
        
        system.assertEquals('1 Infinite Loop', srs[0].delivery_line_1);
        system.assertEquals(37.33118, srs[0].metadata.latitude);
        system.assertEquals('1 Infinite Loop', srs[1].delivery_line_1);
    }

    public static testMethod void testZip() {
    	if (strTestOnly != '*' && strTestOnly != 'testZip') return;
    	ADDR_SmartyStreets_Gateway gateway = createDefaultSettings();

        Test.startTest();
        ADDR_SmartyStreets_Gateway.ZipResponse sz = gateway.verifyZip('single-zip');
        Test.stopTest();

        system.assert(sz != null);
        system.assertEquals(3, sz.city_states.size());
        system.assertEquals(1, sz.zipcodes.size());
        system.assertEquals(47.957763, sz.zipcodes[0].latitude);
    }

    public static testMethod void testBadInput() {
        if (strTestOnly != '*' && strTestOnly != 'testBadInput') return;
        ADDR_SmartyStreets_Gateway gateway = createDefaultSettings();        
        
        Test.startTest();
        ADDR_SmartyStreets_Gateway.AddressResponse sr;
        try {
            sr = gateway.verifyAddress('400');
        } catch (Exception e) {
            System.debug('****' + e.getStackTraceString());
            system.assertEquals('Bad input. Required fields missing from input or are malformed.', e.getMessage());
        }
        Test.stopTest();
        system.assertEquals(null, sr);
    }
    
    public static testMethod void testBadAuth() {
    	if (strTestOnly != '*' && strTestOnly != 'testBadAuth') return;
    	ADDR_SmartyStreets_Gateway gateway = createDefaultSettings();        
        
        Test.startTest();
        ADDR_SmartyStreets_Gateway.AddressResponse sr;
        try {
            sr = gateway.verifyAddress('401');
        } catch (Exception e) {
        	UTIL_Debug.debug('****' + e.getStackTraceString());
            system.assertEquals('Unauthorized. Authentication failure; invalid credentials.', e.getMessage());
        }
        Test.stopTest();
        system.assertEquals(null, sr);
    }
    
    public static testMethod void testNoSubscription() {
        if (strTestOnly != '*' && strTestOnly != 'testNoSubscription') return;
        ADDR_SmartyStreets_Gateway gateway = createDefaultSettings();
                
        Test.startTest();
        ADDR_SmartyStreets_Gateway.AddressResponse sr;
        try {
            sr = gateway.verifyAddress('402');
        } catch (Exception e) {
            System.debug('****' + e.getStackTraceString());
            system.assertEquals('Payment required. No active subscription found.', e.getMessage());
        }
        Test.stopTest();
        system.assertEquals(null, sr);
    }
    
    public static testMethod void testServerError() {
        if (strTestOnly != '*' && strTestOnly != 'testServerError') return;
        ADDR_SmartyStreets_Gateway gateway = createDefaultSettings();
                
        Test.startTest();
        ADDR_SmartyStreets_Gateway.AddressResponse sr;
        try {
            sr = gateway.verifyAddress('500');
        } catch (Exception e) {
            System.debug('****' + e.getStackTraceString());
            system.assertEquals('Internal server error. General service failure; retry request.', e.getMessage());
        }
        Test.stopTest();
        system.assertEquals(null, sr);
    }
}