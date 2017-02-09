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
* @description Class that provides address validation/normalization services
* throught the SmartyStreets API.
 */
public with sharing class ADDR_SmartyStreets_Validator implements ADDR_IValidator {

    private Addr_Verification_Settings__c settings;
    
    /*******************************************************************************************************
    * @description Provides the called with the service name.
    * @return String The name of the address validation service.
    */
    public String getServiceName() {
    	return 'SmartyStreets';
    }
    
    /*******************************************************************************************************
    * @description Provides the caller with the default endpoint URL.
    * @return String The URL to send requests to.
    */
    public String getDefaultURL() {
        return 'https://api.smartystreets.com/street-address';	
    }
    
    /*******************************************************************************************************
    * @description Provides the caller with a zipcode validation endpoint URL.
    * @return String The URL to send requests to for zicode validation.
    */
    public String getDefaultZipcodeURL() {
        return 'https://us-zipcode.api.smartystreets.com/lookup';  
    }

    /*******************************************************************************************************
    * @description Tells the caller if the class supports batch address validation.
    * @return Boolean True is the class supports batch address validation. False otherwise.
    */
    public Boolean supportsBatch() {
        return true;	
    }
    
    /*******************************************************************************************************
    * @description Provides the caller with help text regarding batch support.
    * @return String The help text.
    */
    public String getBatchHelpText() {
    	return Label.Addr_Verification_Batch_SmartyStreets_Message;
    }
    
    /*******************************************************************************************************
    * @description Verifies a list of Addresses records.
    * @param addresses List of Address records to verify.
    * @param settings The Address Verification Settings to use. 
    * @return List<Address__c> List of verified address records. 
    */
    public List<Address__c> verifyRecords(List<Address__c> avList, Addr_Verification_Settings__c settings) {
    	this.settings = settings;
        
        List<Address__c> shortList = new List<Address__c>();
        List<Address__c> shortListZipOnly = new List<Address__c>();
        List<Address__c> addressesToUpdate = new List<Address__c>();
        
        //It splits the records up into those we are sending for address and those for zip only - max of 100 at a time for each.
        for (Address__c av : avList) {
            //has address   
            if (av.MailingStreet__c != null) {
                shortList.add(av);
                if (shortList.size() == 100) {
                    addressesToUpdate.addAll(verifyRecords100(shortList));
                    shortList = new List<Address__c>(); 
                }
            // has no address, but can be verified and geocoded by the zipcode api
            } else if (av.MailingStreet__c == null && (av.MailingPostalCode__c != null || 
                (av.MailingCity__c != null && av.MailingState__c != null))) {
                shortListZipOnly.add(av);
                if (shortListZipOnly.size() == 100) {
                    addressesToUpdate.addAll(verifyZipRecords100(shortListZipOnly));
                    shortListZipOnly = new List<Address__c>();
                }
            }
        }
        
        if (!shortListZipOnly.isEmpty())
            addressesToUpdate.addAll(verifyZipRecords100(shortListZipOnly));
        
        if (!shortList.isEmpty())
            addressesToUpdate.addAll(verifyRecords100(shortList));
        
        return addressesToUpdate;        
    }

    private List<Address__c> verifyRecords100(List<Address__c> avList) {
    	UTIL_Debug.debug(LoggingLevel.WARN, '****Verifying ' + avList.size() + ' address records');
        integer recs = 0;
        List<ADDR_SmartyStreets_Gateway.AddressRequest> srList = new List<ADDR_SmartyStreets_Gateway.AddressRequest>();
        
        Boolean includesUSAddress = false;
        for (Address__c av : avList) {
            ADDR_SmartyStreets_Gateway.AddressRequest sr = new ADDR_SmartyStreets_Gateway.AddressRequest();
            sr.street = av.MailingStreet__c;
            sr.street2 = av.MailingStreet2__c;
            sr.city = av.MailingCity__c;
            sr.state = av.MailingState__c;
            sr.zipcode = av.MailingPostalCode__c;

            // request a few candidates to determine if the address is unique
            sr.candidates = (settings.Reject_Ambiguous_Addresses__c) ? 3 : 1;   
            srList.add(sr);
            if(ADDR_Validator_Batch.isUS(av))
                includesUSAddress = true;
        }

        //Don't even make the address verification request to SmartyStreets if no US address is included, to save in API calls
        if(includesUSAddress) {
	        try {
	            ADDR_SmartyStreets_Gateway la = new ADDR_SmartyStreets_Gateway(settings);
	            List<ADDR_SmartyStreets_Gateway.AddressResponse> responses = la.verifyAddresses(srList);
	            UTIL_Debug.debug('****Gateway responses: ' + JSON.serializePretty(responses));
	            if (responses != null) {
	                List<ADDR_SmartyStreets_Gateway.AddressResponse> responseGroup;
	                for (ADDR_SmartyStreets_Gateway.AddressResponse sr : responses) {         
	                    Address__c av = avList[ sr.input_index ];              
	                    if (sr.candidate_index == 0) {
	                        responseGroup = new List<ADDR_SmartyStreets_Gateway.AddressResponse>{ sr };
	                        UTIL_Debug.debug('****Flagging address as verified. Address ID: ' + av.Id);
	                        
                            if (!String.isBlank(av.Formula_MailingAddress__c))
                                av.Pre_Verification_Address__c = av.Formula_MailingAddress__c.replace('<br>','\r\n');

                            av.Verified__c = true;
	                        av.MailingStreet__c = sr.delivery_line_1;
	                        av.MailingStreet2__c = sr.delivery_line_2;
	                        av.MailingCity__c = sr.components.city_name;
	                        av.MailingState__c = sr.components.state_abbreviation;
	                        av.MailingPostalCode__c = (sr.components.zipcode == null) ? null : (sr.components.zipcode +
	                            ((sr.components.plus4_code != null && sr.components.plus4_code != '') ? ('-' + sr.components.plus4_code) : ''));
	                        av.MailingCountry__c = 'US';
	                        av.Geolocation__Latitude__s = sr.metadata.latitude;
	                        av.Geolocation__Longitude__s = sr.metadata.longitude;
	                        av.County_Name__c = sr.metadata.county_name;
	                        av.Congressional_District__c = sr.metadata.congressional_district;
	                    } else {
	                        // more than one candidate means should mark as ambiguous
	                        UTIL_Debug.debug('****Flagging address as NOT verified. Flagging it as ambiguous. Address ID: ' + av.Id);
	                        av.Verified__c = false; 
	                        av.Ambiguous__c = true;
	                        responseGroup.add(sr);
	                    }
	                    String response = JSON.serializePretty(responseGroup);
	                    av.API_Response__c = trimResponse(response);
	                    av.Verification_Status__c = Label.Addr_Verified;
	                }
	            }
	            for (Address__c av : avList) {             
	                if (av.API_Response__c == null && !String.isBlank(settings.Auth_ID__c) && 
	                !String.isBlank(settings.Auth_Token__c) && (settings.Class__c == 'ADDR_SmartyStreets_Validator')) {   
	                    av.API_Response__c = Label.Address_Not_Found;
	                    av.Verification_Status__c = Label.Address_Not_Found;
	                }
	            }
	        } catch (Exception ex) {
	            for (Address__c av : avList) {
	                UTIL_Debug.debug('****Flagging address as NOT verified Address ID: ' + av.Id 
	                               + ' - Exception in verifyRecords100: ' + ex.getMessage() 
	                               + '\n' + ex.getStackTraceString());
	                av.Verified__c = false;
	                av.API_Response__c = ex.getMessage();
	                av.Verification_Status__c = ex.getMessage();
	                ERR_Handler.processError(ex, ERR_Handler_API.Context.ADDR);
	            }
	        }
        }
        return avList;
    }

    private List<Address__c> verifyZipRecords100(List<Address__c> avList) {
    	UTIL_Debug.debug(LoggingLevel.WARN, '****Verifying ' + avList + ' zip records');
        integer recs = 0;
        List<ADDR_SmartyStreets_Gateway.ZipRequest> zrList = new List<ADDR_SmartyStreets_Gateway.ZipRequest>();
        
        Boolean includesUSAddress = false;
        for (Address__c av : avList) {
            zrList.add(new ADDR_SmartyStreets_Gateway.ZipRequest(
                av.MailingCity__c,
                av.MailingState__c,
                av.MailingPostalCode__c
           ));
           if(ADDR_Validator_Batch.isUS(av))
                includesUSAddress = true;
        }   
        
        //Don't even make the address verification request to SmartyStreets if no US address is included, to save in API calls
        if(includesUSAddress) {
	        try {
	            ADDR_SmartyStreets_Gateway la = new ADDR_SmartyStreets_Gateway(settings);
	            List<ADDR_SmartyStreets_Gateway.ZipResponse> responses = la.verifyZips(zrList);
	            UTIL_Debug.debug('****Gateway responses: ' + JSON.serializePretty(responses));
	            if (responses != null && !responses.isEmpty()) {
	                if(responses.size() == avList.size()) {
	                    for (integer i = 0; i < responses.size(); i++) {
	                        ADDR_SmartyStreets_Gateway.ZipResponse sr = responses[i];
	                        Address__c av = avList[i];             
	    
	                        // look for an error
	                        if (sr.status != null && sr.reason != null) {
	                            UTIL_Debug.debug('****Error found. Reason: ' + sr.reason);
	                            av.API_Response__c = sr.reason;
	                        // if there is any response, serialize it and fill in location with first location value
	                        } else if (sr.zipcodes != null) {
	                            UTIL_Debug.debug('****No error. Populating raw response field.');
	                            String response = JSON.serializePretty(sr);
	                            av.API_Response__c = trimResponse(response);
	                            av.Verification_Status__c = Label.Addr_Verified;
	                            
	                            // if there is at least one city/state, fill in city state
	                            if (sr.city_states != null && sr.city_states.size() > 0) {
	                                UTIL_Debug.debug('****Populating city and estate.');
	                                av.Verified__c = true;
	                                av.MailingCity__c = sr.city_states[0].city;
	                                av.MailingState__c = sr.city_states[0].state_abbreviation;
	                                av.MailingCountry__c = 'US';
	                            }
	    
	                            // if there is exactly one zip, fill in zip
	                            if (sr.zipcodes.size() == 1)
	                                av.MailingPostalCode__c = sr.zipcodes[0].zipcode;
	                        }
	                    }
	                } else {
	                    UTIL_Debug.debug('****Responses did not match number of verifications.');
	                    for(Address__c av : avList) {
	                    	av.Verification_Status__c = Label.Zip_Not_Found;
	                    }
	                }
	            }
	            for (Address__c av : avList) {             
	                if (av.API_Response__c == null) {   
	                    av.API_Response__c = Label.Zip_Not_Found;
	                    av.Verification_Status__c = Label.Zip_Not_Found;
	                }
	            }
	        } catch (Exception ex) {
	            for (Address__c av : avList) {
	                UTIL_Debug.debug('****Flagging address as NOT verified Address ID: ' + av.Id 
	                            + ' - Exception in verifyZipRecords100: ' + ex.getMessage()
	                            + '\n' + ex.getStackTraceString());
	                av.Verified__c = false;
	                av.API_Response__c = ex.getMessage();
	                av.Verification_Status__c = Label.Zip_Not_Found;
	                ERR_Handler.processError(ex, ERR_Handler_API.Context.ADDR);
	            }
	            ERR_Handler.processError(ex, ERR_Handler_API.Context.ADDR);
	        }
        }
        return avList;
    }
    
    private static String trimResponse(String response) {
        //Truncating body response to avoid STRING_TOO_LONG error.
        if(response.length() > 32768) {
            response = response.substring(0, 32768);
        }
        return response;
    }
}