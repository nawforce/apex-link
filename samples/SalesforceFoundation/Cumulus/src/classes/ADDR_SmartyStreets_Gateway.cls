/*
    Copyright (c) 2014, Salesforce.org
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright
      notice, this List of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this List of conditions and the following disclaimer in the
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
* @description Class that talks to the SmartyStreets address verification API.
 */
public with sharing class ADDR_SmartyStreets_Gateway {

    private Addr_Verification_Settings__c settings;
    private String auth;

    /*******************************************************************************************************
    * @description Exception raised during the SmartyStreets address verification process.
    */
    public class LiveAddressException extends Exception {}

    /*******************************************************************************************************
    * @description Constructor that initializes the instance properties.
    * @param settings The address verification settings.
    */
    public ADDR_SmartyStreets_Gateway(Addr_Verification_Settings__c settings) {
        this.settings = settings;

        if (!String.isBlank(settings.auth_id__c) && !String.isBlank(settings.auth_token__c))
            auth = '?auth-id=' + settings.auth_id__c + '&auth-token=' + settings.auth_token__c;
    }

    /*******************************************************************************************************
    * @description Verifies a single address from a string representation.
    * @param String A string containing the address to verify.
    * @return AddressResponse An Apex representation of the verified address.
    */
    public AddressResponse verifyAddress(String address) {
        return verifyAddress(new AddressRequest(address));
    }

    /*******************************************************************************************************
    * @description Verifies a single address from an AddressRequest object.
    * @param AddressRequest The address to verify in an Apex representation.
    * @return AddressResponse An Apex representation of the verified address.
    */
    public AddressResponse verifyAddress(AddressRequest address) {
        List<AddressResponse> sr = verifyAddresses(new List<AddressRequest>{address});
        return ((sr == null) ? null : sr[0]);
    }

    /*******************************************************************************************************
    * @description Verifies multiple addresses from a list of strings.
    * @param addresses A list containing the addresses to verify, one per element in the list.
    * @return List<AddressResponse> A list containing the verified addresses, as AddressResponse objects.
    */
    public List<AddressResponse> verifyAddresses(List<String> addresses) {
        List<AddressRequest> requests = new List<AddressRequest>();
        for (String addr : addresses)
            requests.add(new AddressRequest(addr));
        return verifyAddresses(requests);
    }

    /*******************************************************************************************************
    * @description Verifies multiple addresses from a list of AddressRequest objects.
    * @param addresses A list containing the addresses to verify, one per element in the list.
    * @return List<AddressResponse> A list containing the verified addresses, as AddressResponse objects.
    */
    public List<AddressResponse> verifyAddresses(List<AddressRequest> addresses) {

        if (addresses == null || addresses.isEmpty())
            return null;
        else if (addresses.size() > 100) //This is a smartyStreets limit
            throw new LiveAddressException(Label.Address_Verification_Limit);

        String endpoint;
        if (settings.Address_Verification_Endpoint__c != null) {
            endpoint = settings.Address_Verification_Endpoint__c;
        } else {
        	ADDR_SmartyStreets_Validator validator = new ADDR_SmartyStreets_Validator();
        	endpoint = validator.getDefaultURL();
        }

        // send the request
        HttpResponse res = sendRequest(addresses, endpoint);

        List<AddressResponse> sr;
        if (res.getStatusCode() == 200)
            sr = (List<AddressResponse>)(JSON.deserialize(res.getBody(), List<AddressResponse>.class));
        else if(res.getStatusCode() == 400)
            throw new LiveAddressException('Bad input. Required fields missing from input or are malformed.');
        else if(res.getStatusCode() == 401)
            throw new LiveAddressException('Unauthorized. Authentication failure; invalid credentials.');
        else if(res.getStatusCode() == 402)
            throw new LiveAddressException('Payment required. No active subscription found.');
        else if(res.getStatusCode() == 500)
            throw new LiveAddressException('Internal server error. General service failure; retry request.');


        if (sr == null || sr.isEmpty()) return null;
        else return sr;
    }

    /*******************************************************************************************************
    * @description Verifies a single zipcode from a string representation.
    * @param String A string containing the zipcode to verify.
    * @return ZipResponse An Apex representation of the verified zipcode.
    */
    public ZipResponse verifyZip(String zip) {
        return verifyZip(null, null, zip);
    }
    /*******************************************************************************************************
    * @description Verifies a city and state combination.
    * @param city The city to verify.
    * @param state The state to verify.
    * @return ZipResponse An Apex representation of the verified city and state combination.
    */
    public ZipResponse verifyZip(String city, String state) {
        return verifyZip(city, state, null);
    }
    /*******************************************************************************************************
    * @description Verifies a zipcode, city and state combination.
    * @param String The zipcode to verify.
    * @param city The city to verify.
    * @param state The state to verify.
    * @return ZipResponse An Apex representation of the verified zipcode, city and state combination.
    */
    public ZipResponse verifyZip(String city, String state, String zip) {
        list<ZipResponse> sz = verifyZips(new list<ZipRequest>{ new ZipRequest(city, state, zip) });
        return ((sz == null) ? null : sz[0]);
    }

    /*******************************************************************************************************
    * @description Verifies multiple zipcode-city-state combinations from a list of ZipRequest objects.
    * @param addresses A list containing the zipcode-city-state combinations to verify.
    * @return List<ZipResponse> A list containing the verified addresses, as ZipResponse objects.
    */
    public list<ZipResponse> verifyZips(list<ZipRequest> zreqs) {

        if (zreqs == null || zreqs.isEmpty())
            return null;
        else if (zreqs.size() > 100)
            throw new LiveAddressException(Label.Zipcode_Verification_Limit);

        //Send the request. Sice SmartyStreets is the only service that provides a different endpoint
        //only for zipcode requests, we have removed it from the UI, and are using it directly here.
        ADDR_SmartyStreets_Validator validator = new ADDR_SmartyStreets_Validator();
        HttpResponse res = sendRequest(zreqs, validator.getDefaultZipcodeURL());

        list<ZipResponse> sz;
        if (res.getStatusCode() == 200)
            sz = (list<ZipResponse>)(JSON.deserialize(res.getBody(), list<ZipResponse>.class));

        if (sz == null || sz.isEmpty()) return null;
        else return sz;
    }

    private HTTPResponse sendRequest(List<Object> payload, String baseURL) {
    	HttpResponse response = new HttpResponse();
    	if(auth != null && (settings.Class__c == 'ADDR_SmartyStreets_Validator')) {
	    	// prepare the request
	        Http h = new Http();
	    	String body = JSON.serialize(payload);
	        HttpRequest req = new HttpRequest();
	        req.setMethod('POST');
	        req.setHeader('Content-Type','application/json');
	        req.setHeader('Accept','application/json');
	        req.setEndpoint(baseURL + auth);
	        req.setBody(body);
          //addresses #2038 by doubling default timeout to 10 seconds
	        req.setTimeout((settings.timeout__c == null) ? 10000 : (settings.timeout__c * 1000).intValue());
	        //Just to test the batch address validator and avoid getting a "Please commit or rollback before
	        //calling out" error. I believe the issue is that the test is performing DML (to set things up)
	        //in the same transaction (because everything before "stopTest" is one transaction, and everything
	        //after another)
	        if(Test.isRunningTest() && System.isBatch()) {
	            ADDR_MockHttpRespGenerator_TEST mockResponseGenerator = new ADDR_MockHttpRespGenerator_TEST();
	            if(req.getBody().contains('single-address'))
	               req.setBody('single-address');
	            else if(req.getBody().contains('multiple-address'))
	               req.setBody('multiple-address');
	            else if(req.getBody().contains('invalid-address'))
	               req.setBody('invalid-address');
	            else if(req.getBody().contains('ambiguous-address'))
	               req.setBody('ambiguous-address');
	            else if(req.getBody().contains('single-zip'))
	               req.setBody('single-zip');
	            else if(req.getBody().contains('invalid-zip'))
	               req.setBody('invalid-zip');
	            response = mockResponseGenerator.respond(req);
	        } else {
	        	// execute the request
	        	response = h.send(req);
	        }
    	} else {
    		response.setBody('No credentials provided or SmartyStreets not selected. Please check your settings.');
    	}
    	return response;
    }

    /*******************************************************************************************************
    * @description Apex representation of the address to send in the verification request.
    */
    public class AddressRequest {

        public String street;           // REQUIRED - Can be full address, or just first line
        public String street2;
        public String secondary;        // If used, usually contains apartment or suite number.
        public String city;
        public String state;
        public String urbanization;     // Only used with Puerto Rican addresses.
        public String zipcode;
        public String addressee;        // The recipient's name or FirmName. Can also be the company.
        public Integer candidates = 1;  // The maximum number of valid addresses returned when the
                                        //   input address is ambiguous (max = 10, default = 1).

        /*******************************************************************************************************
        * @description Empty constructor.
        */
        public AddressRequest() { }

        /*******************************************************************************************************
        * @description Constructor that initializes the address property.
        */
        public AddressRequest(String address) {
            street = address;
        }
    }

    /*******************************************************************************************************
    * @description Apex representation of the address returned from verification.
    */
    public class AddressResponse {

        public Integer input_index;      // The order in which this address was submitted
        public Integer candidate_index;  // Index when there are multiple addresses returned
        public String addressee;         // Company name or intended recipient
        public String delivery_line_1;  // Verified street address
        public String delivery_line_2;
        public String last_line;            // City, state, and ZIP code combined
        public String delivery_point_barcode;
        public Components components;
        public Metadata metadata;
        public Analysis analysis;

        /*******************************************************************************************************
        * @description Returns the address in a string format.
        * @return String The address as a string.
        */
        public String getFullAddress() {
            return (((addressee != null) ? (addressee + '\n') : '') + ((delivery_line_1 != null) ? (delivery_line_1 + '\n') : '') +
                ((delivery_line_2 != null) ? (delivery_line_2 + '\n') : '') + ((last_line != null) ? (last_line) : '')).trim();
        }
    }

    /*******************************************************************************************************
    * @description Apex representation of the components element returned in the verification response.
    */
    public class Components {
        public String primary_number;   // The house, PO box, or building number
        public String street_name;
        public String street_predirection;
        public String street_postdirection;
        public String street_suffix;
        public String secondary_number;
        public String secondary_designator;
        public String pmb_designator;
        public String pmb_number;
        public String city_name;
        public String state_abbreviation;
        public String zipcode;
        public String plus4_code;
        public String delivery_point;
        public String delivery_point_check_digit;
    }

    /*******************************************************************************************************
    * @description Apex representation of the metadata element returned in the verification response.
    */
    public class Metadata {
        public String record_type;
        public String county_fips;
        public String county_name;
        public String carrier_route;
        public String building_default_indicator;
        public String congressional_district;
        public String rdi;
        public Double latitude;
        public Double longitude;
        public String precision;
    }

    /*******************************************************************************************************
    * @description Apex representation of the metadata analysis returned in the verification response.
    */
    public class Analysis {
        public String dpv_match_code;
        public String dpv_footnotes;
        public String dpv_cmra;
        public String dpv_vacant;
        public String active;
        public String ews_match;
        public String footnotes;
        public String lacslink_code;
        public String lacslink_indicator;
    }

    /*******************************************************************************************************
    * @description Apex representation of the zipcode-city-state combination to send in the verification request.
    */
    public class ZipRequest {
        public String city;
        public String state;
        public String zipcode;

        /*******************************************************************************************************
        * @description Empty constructor.
        */
        public ZipRequest() {}

        /*******************************************************************************************************
        * @description Constructor that initializes the class properties.
        */
        public ZipRequest(String c, String s, String z) {
            city = c;
            state = s;
            zipcode = z;
        }
    }

    /*******************************************************************************************************
    * @description Apex representation of the zipcode-city-state combination returned from verification.
    */
    public class ZipResponse {
        public String status;
        public String reason;
        public list<CityState> city_states;
        public list<ZipLoc> zipcodes;
    }

    /*******************************************************************************************************
    * @description Apex representation of the city_states element returned in the verification response.
    */
    public class CityState {
        public String city;
        public String state_abbreviation;
        public String state;
    }

    /*******************************************************************************************************
    * @description Apex representation of the zipcodes element returned in the verification response.
    */
    public class ZipLoc {
        public String zipcode;
        public Double latitude;
        public Double longitude;
    }
}