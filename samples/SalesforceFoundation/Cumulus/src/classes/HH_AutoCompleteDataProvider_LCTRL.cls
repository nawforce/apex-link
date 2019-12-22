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
* @group Households
* @group-content ../../ApexDocContent/Households.htm
* @description Lightning Component Server Controller for the Manage Household UI page's HH_AutoCompleteDateProvider LC.
*/
public with sharing class HH_AutoCompleteDataProvider_LCTRL {

    /*******************************************************************************************************
    * @description returns a list of matching contacts given the queryValue
    * @param queryValue The name to search for
    * @param listCon The list of Contacts to filter out
    * @return List<ProviderResult> the list of matching contacts
    */
    @AuraEnabled
    public static List<ProviderResult> queryObjects(String queryValue, list<Contact> listCon) {
        try {
            if (listCon == null)
                listCon = new list<Contact>();
            
            // '%', '_', and '\' all are special characters in LIKE syntax.
            // We escape them here by prepending those characters with a backslash
            String preparedQueryValue = '%' + queryValue.replaceAll('([%_\\\\])', '\\\\$0') + '%';
            
            // use helper to get all contact fields to support custom household naming and greetings.
            // note that our helper does FLS, so we don't have to do it here.
            String strSoql = HH_Container_LCTRL.getContactSelectQueryWithFls();
            strSoql += ' WHERE Name LIKE :preparedQueryValue AND Id not in :listCon LIMIT 100';
    
            List<SObject> results = Database.query(strSoql);
    
            List<ProviderResult> providerResults = new List<ProviderResult>();
    
            for (SObject so : results) {
                ProviderResult result = new ProviderResult();
                result.value = so;
                result.displayValue = (String) so.get('Name');
                providerResults.add(result);
            }
    
            return providerResults;
        } catch (Exception e) {
            throw new AuraHandledException(e.getMessage());
        }
    }

    /*******************************************************************************************************
    * @description helper class that holds the display string and Contact sobject to return to the 
    * autocomplete lightning component
    */
    public class ProviderResult {
        @AuraEnabled
        public String displayValue;

        @AuraEnabled
        public SObject value;
    }
}