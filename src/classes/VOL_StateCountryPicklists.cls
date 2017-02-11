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

public with sharing class VOL_StateCountryPicklists {
     

    /*******************************************************************************************************
    * @description Utility to copy Address fields between Contacts and Accounts (or vice-versa).
    * Handles instances where State and Country picklists are enabled.  Does not copy a source field if it
    * is null.
    * @param sobjSrc the source Contact or Account
    * @param strFieldPrefixSrc the address fields to copy from, ie., Mailing, Other, Shipping, Billing
    * @param sobjDst the destination Contact or Account
    * @param strFieldPrefixDst the address fields to copy to, ie., Mailing, Other, Shipping, Billing
    * @return void
    */ 
    public static void copyAddressStdSObj(sobject sobjSrc, string strFieldPrefixSrc, sobject sobjDst, string strFieldPrefixDst) {
    	string strVal;
    	if ((strVal = (string)sobjSrc.get(strFieldPrefixSrc + 'Street')) != null)
	        sobjDst.put(strFieldPrefixDst + 'Street', strVal);
    	if ((strVal = (string)sobjSrc.get(strFieldPrefixSrc + 'City')) != null)
	        sobjDst.put(strFieldPrefixDst + 'City', strVal);
    	if ((strVal = (string)sobjSrc.get(strFieldPrefixSrc + 'PostalCode')) != null)
	        sobjDst.put(strFieldPrefixDst + 'PostalCode', strVal);
    	
    	/****
    	string strMsg;	
		strMsg = 'before copy Country: ' + sobjSrc.get(strFieldPrefixSrc + 'Country') + ' State: ' + sobjSrc.get(strFieldPrefixSrc + 'State');	
		if (isStateCountryPicklistsEnabled)	    		
			strMsg += ' CountryCode: ' + sobjSrc.get(strFieldPrefixSrc + 'CountryCode') + ' StateCode: ' + sobjSrc.get(strFieldPrefixSrc + 'StateCode');   		
        ****/

        if (!isStateCountryPicklistsEnabled) {
	    	if ((strVal = (string)sobjSrc.get(strFieldPrefixSrc + 'State')) != null)
		        sobjDst.put(strFieldPrefixDst + 'State', strVal);
	    	if ((strVal = (string)sobjSrc.get(strFieldPrefixSrc + 'Country')) != null)
		        sobjDst.put(strFieldPrefixDst + 'Country', strVal);
        } else {
        	// try to deal gracefully with Country
        	string strCountry = (string)sobjSrc.get(strFieldPrefixSrc + 'Country');
        	if (strCountry == null)
        		strCountry = 'United States';
            if (validCountriesByLabel.containsKey(strCountry)) {
                sobjDst.put(strFieldPrefixDst + 'Country', strCountry);
                sobjDst.put(strFieldPrefixDst + 'CountryCode', validCountriesByLabel.get(strCountry));
            } else if (validCountriesByCode.containsKey(strCountry.toUpperCase())) {
                sobjDst.put(strFieldPrefixDst + 'CountryCode', strCountry.toUpperCase());
                sobjDst.put(strFieldPrefixDst + 'Country', validCountriesByCode.get(strCountry.toUpperCase()));
            } else {
                // allow the invalid country to be placed in the country field, so Salesforce will generate the error.
                sobjDst.put(strFieldPrefixDst + 'Country', strCountry);
            }

			// try to deal gracefully with State            
        	string strState = (string)sobjSrc.get(strFieldPrefixSrc + 'State');        	
            if (strState != null) {
                // best to make sure first letter is capitalized.
                strState = strState.capitalize();
                if (validStatesByLabel.containsKey(strState)) {
                    sobjDst.put(strFieldPrefixDst + 'State', strState);
                    sobjDst.put(strFieldPrefixDst + 'StateCode', validStatesByLabel.get(strState));
                } else {
                	// too expensive for us to create the map of CountryCode|StateCode to StateLabel
                	// so we will just try to save any state that isn't a label as a code.
                	sobjDst.put(strFieldPrefixDst + 'StateCode', strState.toUpperCase());
                }
            }
        }
        
        /****        
		strMsg += ' after copy Country: ' + sobjDst.get(strFieldPrefixDst + 'Country') + ' State: ' + sobjDst.get(strFieldPrefixDst + 'State');	
		if (isStateCountryPicklistsEnabled)	    		
			strMsg += ' CountryCode: ' + sobjDst.get(strFieldPrefixDst + 'CountryCode') + ' StateCode: ' + sobjDst.get(strFieldPrefixDst + 'StateCode');   		
    	ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.FATAL, strMsg));
    	****/		    		
    }

    /*******************************************************************************************************
    * @description specifies whether state and country picklists are enabled in this org.
    * @return true if enabled
    */ 
    public static Boolean isStateCountryPicklistsEnabled {
        get {
        	if (isStateCountryPicklistsEnabled == null)
                isStateCountryPicklistsEnabled = initValidStateCountryCodes();
            return isStateCountryPicklistsEnabled;
        }
        private set;
    }

    // The maps to convert the State/Country Code to a State/Country Label and Vice-Versa
    private static Map<String, String> validCountriesByLabel = new Map<String, String>();
    private static Map<String, String> validStatesByLabel = new Map<String, String>();
    private static Map<String, String> validCountriesByCode = new Map<String, String>();
    
    /*******************************************************************************************************
    * @description utility to determine if the "State and Country Picklist" field feature is enabled in Salesforce
    * @return true if enabled; Fills 4 sets<> with a list of value codes and labels for each field
    */ 
    private static Boolean initValidStateCountryCodes() {

        // If State & Country Picklists are enabled in the org, build maps of the
        // valid Labels and Codes for each field to use for validation
        map<String, Schema.SObjectField> acctFields = Account.getSobjectType().getDescribe().fields.getMap();
        boolean isCodesEnabled = acctFields.containsKey('BillingCountryCode');
        if (isCodesEnabled) {
            list<Schema.Picklistentry> countryPLValues = acctFields.get('BillingCountryCode').getDescribe().getPicklistValues();
            for (Schema.Picklistentry p : countryPLValues) {
                if (p.isActive()) { 
                    validCountriesByLabel.put(p.getLabel(), p.getValue()); 
                    validCountriesByCode.put(p.getValue(), p.getLabel());
                }
            }
            list<Schema.Picklistentry> statePLValues = acctFields.get('BillingStateCode').getDescribe().getPicklistValues();
            for (Schema.Picklistentry p : statePLValues) {
                if (p.isActive()) { 
                    validStatesByLabel.put(p.getLabel(), p.getValue());
                    // we can't have a map of valid StateCodes, because they are not unique.
                    // ideally we would have a map of CountryCode + StateCode -> StateLabel
                    // but Salesforce provides us no way of figuring out picklist dependencies efficiently. 
                }
            }
        }
        return isCodesEnabled;
    }

}