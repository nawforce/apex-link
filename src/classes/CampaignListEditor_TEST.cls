/*
    Copyright (c) 2016 Salesforce.org
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the Salesforce.com Foundation nor the names of
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
@isTest
private with sharing class CampaignListEditor_TEST {
	
	/*********************************************************************************************************
    * @description Tests Autocomplete_CTRL with results
    */
    public static testMethod void testAutocompleteControllerWithResults() { 
        
        Campaign testCampaign = new Campaign(Name ='Test Campaign');
        insert testCampaign;

        Test.startTest();
        
        List<Autocomplete_CTRL.ProviderResult> listPR = Autocomplete_CTRL.getSuggestions('Campaign', 'Test');

        Test.stopTest();

        System.assertEquals(1, listPR.size(), 'There should be one campaign that matches the search term.');
    }

    /*********************************************************************************************************
    * @description Tests Autocomplete_CTRL without results
    */
    public static testMethod void testAutocompleteControllerWithoutResults() { 
        
        Test.startTest();
        
        List<Autocomplete_CTRL.ProviderResult> listPR = Autocomplete_CTRL.getSuggestions('Campaign', 'Test');

        Test.stopTest();

        System.assertEquals(0, listPR.size(), 'When no matching results are found, an empty list should be returned.');
    }

    /*********************************************************************************************************
    * @description Tests Autocomplete_CTRL with bad object Name
    */
    public static testMethod void testAutocompleteControllerWithBadObjectName() { 
        
        Test.startTest();
        
        List<Autocomplete_CTRL.ProviderResult> listPR = Autocomplete_CTRL.getSuggestions('BadObjectName', 'Test');

        Test.stopTest();

        System.assertEquals(0, listPR.size(), 'When a bad object is passed in, an empty list should be returned.');
    }

    /*********************************************************************************************************
    * @description Tests the namespace for the page
    */
    public static testMethod void testGetNamespace() { 
        String currentClassName = CampaignListEditor_TEST.class.getName();
    	String currentNS = currentClassName.contains('.') ? currentClassName.substringBefore('.') : '';
    	String nsPrefix = CampaignListEditor_CTRL.getNamespace();

        System.assertEquals(currentNS, nsPrefix, 'The namespace should match the test class namespace.');
    }
    /*********************************************************************************************************
    * @description Tests loading the page with permissions
    */
	@isTest
    private static void testPageLoadWithPerms() {
        Campaign testCampaign = new Campaign(Name='Test Campaign');
        insert testCampaign;

        Test.startTest();

        ApexPages.StandardController sc = new ApexPages.StandardController(testCampaign);
        CampaignListEditor_CTRL ctrl = new CampaignListEditor_CTRL(sc);
        Boolean hasPerms = CampaignListEditor_CTRL.getHasPerms();
        List<ApexPages.Message> pageMessages = ApexPages.getMessages();

        Test.stopTest();

        System.assertEquals(0, pageMessages.size(), 'There should not be any errors on the page.');
        System.assert(hasPerms, 'Apex Test should default to have all permissions.');
        System.assert(hasPerms, 'A permission exception should not occur, user should have permissions.');
    }
    /*********************************************************************************************************
    * @description Tests loading the page without permissions
    */
    @isTest
    private static void testPageLoadWithoutPerms() {

        Campaign testCampaign = new Campaign(Name='Test Campaign');
        insert testCampaign;


        Test.startTest();

        ApexPages.StandardController sc = new ApexPages.StandardController(testCampaign);
        CampaignListEditor_CTRL ctrl = new CampaignListEditor_CTRL(sc);
        CampaignTools_UTIL.throwException = true;
        Boolean hasPerms = CampaignListEditor_CTRL.getHasPerms();

        Test.stopTest();

        System.assert(!hasPerms, 'When permission exception is caught, user should not have permissions.');
    }
}