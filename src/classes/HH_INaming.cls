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
* @group Households
* @description Interface for classes that will provide Household Naming functionality.
*/
public Interface HH_INaming {
    
    /*******************************************************************************************************
    * @description Get the Household name for the list of Contacts from a single Household.
    * Contacts who have set their npo02__Naming_Exclusions__c field to be excluded from the name, are not included in the list of Contacts.
    * If the list of Contacts is empty, returns the appropriate 'anonymous' name.  
    * @param listCon the list of Contacts in the Household
    * @return the Household name
    */ 
    string getHouseholdName(list<Contact> listCon);

    /*******************************************************************************************************
    * @description Get the Household Formal Greeting for the list of Contacts from a single Household.
    * Contacts who have set their npo02__Naming_Exclusions__c field to be excluded from the greeting, are not included in the list of Contacts.
    * If the list of Contacts is empty, returns the appropriate 'anonymous' greeting.  
    * @param listCon the list of Contacts in the Household
    * @return the Household Formal Greeting
    */ 
    string getHouseholdFormalGreeting(list<Contact> listCon);

    /*******************************************************************************************************
    * @description Get the Household Informal Greeting for the list of Contacts from a single Household.
    * Contacts who have set their npo02__Naming_Exclusions__c field to be excluded from the greeting, are not included in the list of Contacts.
    * If the list of Contacts is empty, returns the appropriate 'anonymous' greeting.  
    * @param listCon the list of Contacts in the Household
    * @return the Household Informal Greeting
    */ 
    string getHouseholdInformalGreeting(list<Contact> listCon);
    
    /*******************************************************************************************************
    * @description For the Settings page, get the formatted name or greeting for the list of Contacts.
    * @param hns an in-memory Household Naming Settings object
	* @param strField is one of: Household_Name_Format__c, Formal_Greeting_Format__c, or Informal_Greeting_Format__c.
    * @param listCon the list of Contacts in the Household
    * @return the appropriate Household name or greeting
    */ 
    string getExampleName(Household_Naming_Settings__c hns, string strField, list<Contact> listCon);
    
    /*******************************************************************************************************
    * @description To allow the NPSP code to know which fields to query for on Contact, before handing them off to the naming routines.
    * @return set of strings of all fields referenced from Contact.
    */
    set<string> setHouseholdNameFieldsOnContact();
}