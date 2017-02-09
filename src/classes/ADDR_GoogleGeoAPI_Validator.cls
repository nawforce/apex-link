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
public with sharing class ADDR_GoogleGeoAPI_Validator implements ADDR_IValidator{

    private String AUTH_TOKEN;
    private String END_POINT;
    private Double TIMEOUT;
    private List<Address__c> checked_addresses;

    private Addr_Verification_Settings__c settings;
    
    /*******************************************************************************************************
    * @description Provides the called with the service name.
    * @return String The name of the address validation service.
    */
    public String getServiceName() {
        return 'The Google Geocoding API';
    }
    
    /*******************************************************************************************************
    * @description Provides the caller with the default endpoint URL.
    * @return String The URL to send requests to.
    */
    public String getDefaultURL() {
        return 'https://maps.googleapis.com/maps/api/geocode/json?';
    }
    
    /*******************************************************************************************************
    * @description Tells the caller if the class supports batch address validation.
    * @return Boolean True is the class supports batch address validation. False otherwise.
    */
    public Boolean supportsBatch() {
        return false; 
    }
    
    /*******************************************************************************************************
    * @description Provides the caller with help text regarding batch support.
    * @return String The help text.
    */
    public String getBatchHelpText() { 
        return Label.Addr_Verification_Batch_Not_Supported; 
    }

    /*******************************************************************************************************
    * @description Verifies a list of Addresses records.
    * @param addresses List of Address records to verify.
    * @param settings The Address Verification Settings to use. 
    * @return List<Address__c> List of verified address records. 
    */
    public List<Address__c> verifyRecords(List<Address__c> addresses, Addr_Verification_Settings__c settings) {

        this.checked_addresses = new List<Address__c>();
        this.AUTH_TOKEN = settings.Auth_Token__c;
        this.TIMEOUT = settings.Timeout__c;

        this.settings = settings;
        
        if (settings.Address_Verification_Endpoint__c != null)      
            this.END_POINT = settings.Address_Verification_Endpoint__c;        
        else       
            this.END_POINT = getDefaultURL();

        if (!addresses.isEmpty()){
            for (Address__c a : addresses){
                checked_addresses.add(verifyAddress(a));
            }
        }
        return checked_addresses;
    }

    private Address__c verifyAddress(Address__c a){

        String address_request_string = '';
        String mailingStreet = '';
        String subpremise = '';
        
        if (!String.isBlank(a.MailingStreet__c))
            mailingStreet += a.MailingStreet__c.trim();
        if (!String.isBlank(a.MailingStreet2__c))
            mailingStreet += ' ' + a.MailingStreet2__c.trim();

        //if our address has a comma, split it off and append it after verification
        if (mailingStreet.contains(',')) {
            list<string> split = mailingStreet.split(',',2);
            address_request_string += 'search_address=' + split[0].replace(' ', '+') + '+';
            subpremise = split[1];
        } else {
            address_request_string += 'search_address=' + mailingStreet.replace(' ', '+') + '+';
        }

        if (a.MailingCity__c != null)
            address_request_string += a.MailingCity__c.replace(' ', '+') + '+';
        if (a.MailingState__c != null)
            address_request_string += a.MailingState__c.replace(' ', '+') + '+';
        if (a.MailingPostalCode__c != null)
            address_request_string += a.MailingPostalCode__c.replace(' ', '+') + '+';
        if (a.MailingCountry__c != null)
            address_request_string += a.MailingCountry__c.replace(' ', '+');

        HttpResponse response = new HttpResponse();

        if(AUTH_TOKEN != null && END_POINT != null) {
            //copy address before verification takes place, in case any information is lost
            if (!String.isBlank(a.Formula_MailingAddress__c))
                a.Pre_Verification_Address__c = a.Formula_MailingAddress__c.replace('<br>','\r\n');

            Http h = new Http();
            HttpRequest req = new HttpRequest();
            req.setMethod('GET');
            req.setHeader('Accept','application/json');
            req.setEndpoint(END_POINT + 'address=' + address_request_string + '&key=' + AUTH_TOKEN);
            try{
                UTIL_Debug.debug('****Request body: \n' + req);
                response = h.send(req);
                UTIL_Debug.debug('****Response body: \n' + response.getBody());
            } catch (Exception e){
                a.Verified__c = false;
                a.API_Response__c = 'REQUEST FAILED.  MESSAGE: ' + e.getMessage();
                ERR_Handler.processError(e, ERR_Handler_API.Context.ADDR);
                a.Verification_Status__c = e.getMessage();
            }

            if (response.getStatusCode() == 200){
                ADDR_GoogleGeoAPI_Validator.GoogleGeoResponse googresp = new ADDR_GoogleGeoAPI_Validator.GoogleGeoResponse();
                try{
                    googresp = (ADDR_GoogleGeoAPI_Validator.GoogleGeoResponse)JSON.deserialize(response.getBody(), ADDR_GoogleGeoAPI_Validator.GoogleGeoResponse.class);
                }catch (Exception e){
                    a.Verified__c = false;
                    a.API_Response__c = 'BAD JSON PARSE.  MESSAGE: ' + e.getMessage();
                    ERR_Handler.processError(e, ERR_Handler_API.Context.ADDR);
                    a.Verification_Status__c = e.getMessage();
                }

                String verified_street = '';
                String verified_premise = '';
                if (googresp.status == 'OK' && googresp.results != null && googresp.results.size() == 1 && 
                    !(settings.Reject_Ambiguous_Addresses__c && googresp.results[0].partial_match == true)){

                    for (ADDR_GoogleGeoAPI_Validator.Address_components add : googresp.results[0].address_components){
                        Set<String> type_set = new Set<String>();
                        type_set.addAll(add.types);

                        if(type_set.contains('postal_code')){
                            String verified5digs = add.long_name;
                            String originalFull = a.MailingPostalCode__c;
                            String original5digs = '';
                            if(originalFull != null && originalFull.length() > 5) {
                                 original5digs = originalFull.substring(0, 5);
                            }
                            //Google only returns 5-digit zipcodes. We don't want to override the original, if the
                            //first 5 match, and the original has the additional 4.
                            if(!(verified5digs == original5digs)) {
                                a.MailingPostalCode__c = add.long_name;
                            }
                        } else if (type_set.contains('country')){
                            a.MailingCountry__c = add.short_name;
                        } else if (type_set.contains('administrative_area_level_1')){
                            a.MailingState__c = add.short_name;
                        } else if (type_set.contains('administrative_area_level_2')){
                            a.County_Name__c = add.long_name;
                        } else if (type_set.contains('administrative_area_level_3')){
                            a.Administrative_Area__c = add.long_name;
                        } else if (type_set.contains('street_number')){
                            verified_street = add.long_name + ' ' + verified_street;
                        } else if (type_set.contains('route')){
                            verified_street += add.long_name;
                        } else if (type_set.contains('intersection')){
                            verified_street += add.long_name;
                        } else if (type_set.contains('premise')){
                            verified_premise += add.long_name;
                        } else if (type_set.contains('subpremise')){
                            verified_premise += add.long_name;
                        } else if (type_set.contains('locality')){
                            a.MailingCity__c = add.long_name;
                        }
                    }
                    a.MailingStreet__c = verified_street;
                    if(!String.isBlank(verified_premise)) {
                         a.MailingStreet__c = a.MailingStreet__c + ' ' + verified_premise;
                    }
                    if (!String.isBlank(subpremise))
                        a.MailingStreet__c += ','+subpremise;
                    ADDR_Addresses_TDTM.handleMultilineStreet(a);
                    a.Geolocation__Latitude__s = googresp.results[0].geometry.location.lat;
                    a.Geolocation__Longitude__s = googresp.results[0].geometry.location.lng;
                    a.Verified__c = true;
                    a.API_Response__c = trimResponse(response);
                    a.Verification_Status__c = Label.Addr_Verified;
                } else if ((googresp.status == 'OK' && googresp.results.size() > 1) || (googresp.results.size() == 1 && settings.Reject_Ambiguous_Addresses__c && googresp.results[0].partial_match == true)){
                    a.Ambiguous__c = true;
                    a.Verified__c = false;
                    a.API_Response__c = trimResponse(response);
                    a.Verification_Status__c = 'Ambiguous';
                } else if (googresp.status == 'OVER_QUERY_LIMIT'){
                    a.Verified__c = false;
                    a.API_Response__c = 'OVER_QUERY_LIMIT: indicates that you are over your quota.';
                    a.Verification_Status__c = a.API_Response__c;
                } else if (googresp.status == 'REQUEST_DENIED'){
                    a.Verified__c = false;
                    a.API_Response__c = 'REQUEST_DENIED: indicates that your request was denied.';
                    a.Verification_Status__c = a.API_Response__c;
                } else if (googresp.status == 'UNKNOWN_ERROR'){
                    a.Verified__c = false;
                    a.API_Response__c = 'UNKNOWN_ERROR: indicates that the request could not be processed due to a server error. The request may succeed if you try again.';
                    a.Verification_Status__c = a.API_Response__c;
                } else if (googresp.status == 'INVALID_REQUEST'){
                    a.Verified__c = false;
                    a.API_Response__c = 'INVALID_REQUEST: generally indicates that the query (address, components or latlng) is missing.';
                    a.Verification_Status__c = a.API_Response__c;
                } else if (googresp.status == 'ZERO_RESULTS'){
                    a.Verified__c = false;
                    a.API_Response__c = 'ZERO_RESULTS: indicates that the geocode was successful but returned no results. This may occur if the geocoder was passed a non-existent address.';
                    a.Verification_Status__c = a.API_Response__c;
                }
                else{
                    a.Verified__c = false;
                    //Truncating body response to avoid STRING_TOO_LONG error.
                    a.API_Response__c = 'STATUS: ' + googresp.status + ' BODY: '+ trimResponse(response);
                    a.Verification_Status__c = a.API_Response__c;
                }
            }
            else{
                a.Verified__c = false;
                a.API_Response__c = response.getStatusCode() + ': ' + response.getStatus();
                a.Verification_Status__c = a.API_Response__c;
            }
        }
        else{
            a.Verified__c = false;
            a.API_Response__c = 'MISSING AUTHID OR ENDPOINT.  Please see the Address Verification menu in the NPSP Settings tab for more information';
        }
        return a;
    }
    
    private static String trimResponse(HttpResponse response) {
        //Truncating body response to avoid STRING_TOO_LONG error.
        String responseBody = response.getBody();
        if(responseBody.length() > 32768) {
            responseBody = responseBody.substring(0, 32768);
        }
        return responseBody;
    }

    /*******************************************************************************************************
    * @description Apex model of the Google JSON response.
    */
    public class GoogleGeoResponse {
        public List<ADDR_GoogleGeoAPI_Validator.Results> results;
        public String status;
    }
    
    /*******************************************************************************************************
    * @description Apex model of the results element of the Google JSON response.
    */
    public class Results {
        public List<ADDR_GoogleGeoAPI_Validator.Address_components> address_components;
        public String formatted_address;
        public Geometry geometry;
        public boolean partial_match;
        public List<String> types;
    }
    
    /*******************************************************************************************************
    * @description Apex model of the address_components element of the Google JSON response.
    */
    public class Address_components {
        public String long_name;
        public String short_name;
        public List<String> types;
    }
    
    /*******************************************************************************************************
    * @description Apex model of the geometry element of the Google JSON response.
    */
    public class Geometry {
        public Location location;
        public String location_type;
        public Viewport viewport;
    }
    
    /*******************************************************************************************************
    * @description Apex model of the location element of the Google JSON response.
    */
    public class Location {
        public Double lat;
        public Double lng;
    }
    
    /*******************************************************************************************************
    * @description Apex model of the viewport element of the Google JSON response.
    */
    public class Viewport {
        public Location northeast;
        public Location southwest;
    }
    
    /*******************************************************************************************************
    * @description Parses a JSON strings and returns a GoogleGeoResponse Apex object.
    * @param String The JSON string.
    * @return GoogleGeoResponse The Apex object the JSON string has been transformed into.
    */
    public static GoogleGeoResponse parse(String json) {
        return (GoogleGeoResponse) System.JSON.deserialize(json, GoogleGeoResponse.class);
    }
}