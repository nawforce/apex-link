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
    * Neither the name of the Salesforce.org nor the names of
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
*
* @group CampaignTools
*
* @description Apex extension for the Auto Complete Component.
*
*/
public with sharing class Autocomplete_CTRL {
    @AuraEnabled
    public static List<ProviderResult> getSuggestions(String sObjectType, String term) {
        List<ProviderResult> providerResults = new List<ProviderResult>();
        try {
            Map<String, Schema.SObjectType> gd = Schema.getGlobalDescribe();
            Boolean objectAccessible = false;
            Boolean nameFieldAccessible = false;
            if (gd.containsKey(sObjectType) && gd.get(sObjectType).getDescribe().fields.getMap().containsKey('Name')) {
                Map<String, SObjectField> fields = gd.get(sObjectType).getDescribe().fields.getMap();
                DescribeFieldResult fieldDescribe = fields.get('Name').getDescribe();
                objectAccessible = gd.get(sObjectType).getDescribe().isAccessible();
                nameFieldAccessible = fields.get('Name').getDescribe().isAccessible();
            }

            if (!objectAccessible || !nameFieldAccessible) {
                return new List<ProviderResult>();
            }

            // '%', '_', and '\' all are special characters in LIKE syntax.
            // We escape them here by prepending those characters with a backslash
            String preparedTerm = '%' + term.replaceAll('([%_\\\\])', '\\\\$0') + '%';
            String soql = 'SELECT Name FROM {0} WHERE Name LIKE :preparedTerm ORDER BY Name LIMIT 10';
            String query = String.format(soql, new List<String>{sObjectType});
            List<SObject> results = Database.query(query);

            for (SObject so : results) {
                ProviderResult result = new ProviderResult();
                result.value = so.Id;
                result.label = (String) so.get('Name');
                providerResults.add(result);
            }

            return providerResults;
        } catch (Exception e) {
            throw new AuraHandledException(e.getMessage());
        }
    }
    /*******************************************************************************************************
    * @description helper class that holds the display string and sobject to return to the
    * autocomplete lightning component
    */
    public class ProviderResult {
        @AuraEnabled
        public String label;

        @AuraEnabled
        public Id value;
    }
}