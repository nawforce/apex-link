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
* @description Class that generates mock HTTP responses for Google Geo API 
*/
@isTest
public with sharing class ADDR_MockGoogleGeoAPIResponse_TEST implements HttpCalloutMock {

    public HTTPResponse respond(HTTPRequest req) {
        String singleAddressResponse = '{'+
        '   \"results\" : ['+
        '      {'+
        '         \"address_components\" : ['+
        '            {'+
        '               \"long_name\" : \"1600\",'+
        '               \"short_name\" : \"1600\",'+
        '               \"types\" : [ \"street_number\" ]'+
        '            },'+
        '            {'+
        '               \"long_name\" : \"Amphitheatre Pkwy\",'+
        '               \"short_name\" : \"Amphitheatre Pkwy\",'+
        '               \"types\" : [ \"route\" ]'+
        '            },'+
        '            {'+
        '               \"long_name\" : \"Mountain View\",'+
        '               \"short_name\" : \"Mountain View\",'+
        '               \"types\" : [ \"locality\", \"political\" ]'+
        '            },'+
        '            {'+
        '               \"long_name\" : \"Santa Clara\",'+
        '               \"short_name\" : \"Santa Clara\",'+
        '               \"types\" : [ \"administrative_area_level_2\", \"political\" ]'+
        '            },'+
        '            {'+
        '               \"long_name\" : \"Whatever\",'+
        '               \"short_name\" : \"Whatevs\",'+
        '               \"types\" : [ \"administrative_area_level_3\", \"political\" ]'+
        '            },'+
        '            {'+
        '               \"long_name\" : \"California\",'+
        '               \"short_name\" : \"CA\",'+
        '               \"types\" : [ \"administrative_area_level_1\", \"political\" ]'+
        '            },'+
        '            {'+
        '               \"long_name\" : \"United States\",'+
        '               \"short_name\" : \"US\",'+
        '               \"types\" : [ \"country\", \"political\" ]'+
        '            },'+
        '            {'+
        '               \"long_name\" : \"94043\",'+
        '               \"short_name\" : \"94043\",'+
        '               \"types\" : [ \"postal_code\" ]'+
        '            }'+
        '         ],'+
        '         \"formatted_address\" : \"1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA\",'+
        '         \"geometry\" : {'+
        '            \"location\" : {'+
        '               \"lat\" : 37.42291810,'+
        '               \"lng\" : -122.08542120'+
        '            },'+
        '            \"location_type\" : \"ROOFTOP\",'+
        '            \"viewport\" : {'+
        '               \"northeast\" : {'+
        '                  \"lat\" : 37.42426708029149,'+
        '                  \"lng\" : -122.0840722197085'+
        '               },'+
        '               \"southwest\" : {'+
        '                  \"lat\" : 37.42156911970850,'+
        '                  \"lng\" : -122.0867701802915'+
        '               }'+
        '            }'+
        '         },'+
        '         \"partial_match\" : false,'+
        '         \"types\" : [ \"street_address\" ]'+
        '      }'+
        '   ],'+
        '   \"status\" : \"OK\"'+
        '}';

        String ambiguousAddressResponse = '{'+
        '   \"results\" : ['+
        '      {'+
        '         \"address_components\" : ['+
        '            {'+
        '               \"long_name\" : \"1600\",'+
        '               \"short_name\" : \"1600\",'+
        '               \"types\" : [ \"street_number\" ]'+
        '            },'+
        '            {'+
        '               \"long_name\" : \"Amphitheatre Pkwy\",'+
        '               \"short_name\" : \"Amphitheatre Pkwy\",'+
        '               \"types\" : [ \"route\" ]'+
        '            },'+
        '            {'+
        '               \"long_name\" : \"Mountain View\",'+
        '               \"short_name\" : \"Mountain View\",'+
        '               \"types\" : [ \"locality\", \"political\" ]'+
        '            },'+
        '            {'+
        '               \"long_name\" : \"Broad & Pattison\",'+
        '               \"short_name\" : \"Broad & Pattison\",'+
        '               \"types\" : [ \"intersection\"]'+
        '            },'+
        '            {'+
        '               \"long_name\" : \"Santa Clara\",'+
        '               \"short_name\" : \"Santa Clara\",'+
        '               \"types\" : [ \"administrative_area_level_2\", \"political\" ]'+
        '            },'+
        '            {'+
        '               \"long_name\" : \"Whatever\",'+
        '               \"short_name\" : \"Whatevs\",'+
        '               \"types\" : [ \"administrative_area_level_3\", \"political\" ]'+
        '            },'+
        '            {'+
        '               \"long_name\" : \"California\",'+
        '               \"short_name\" : \"CA\",'+
        '               \"types\" : [ \"administrative_area_level_1\", \"political\" ]'+
        '            },'+
        '            {'+
        '               \"long_name\" : \"United States\",'+
        '               \"short_name\" : \"US\",'+
        '               \"types\" : [ \"country\", \"political\" ]'+
        '            },'+
        '            {'+
        '               \"long_name\" : \"94043\",'+
        '               \"short_name\" : \"94043\",'+
        '               \"types\" : [ \"postal_code\" ]'+
        '            }'+
        '         ],'+
        '         \"formatted_address\" : \"1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA\",'+
        '         \"geometry\" : {'+
        '            \"location\" : {'+
        '               \"lat\" : 37.42291810,'+
        '               \"lng\" : -122.08542120'+
        '            },'+
        '            \"location_type\" : \"ROOFTOP\",'+
        '            \"viewport\" : {'+
        '               \"northeast\" : {'+
        '                  \"lat\" : 37.42426708029149,'+
        '                  \"lng\" : -122.0840722197085'+
        '               },'+
        '               \"southwest\" : {'+
        '                  \"lat\" : 37.42156911970850,'+
        '                  \"lng\" : -122.0867701802915'+
        '               }'+
        '            }'+
        '         },'+
        '         \"types\" : [ \"street_address\" ]'+
        '      },'+
        '      {'+
        '         \"address_components\" : ['+
        '            {'+
        '               \"long_name\" : \"1601\",'+
        '               \"short_name\" : \"1601\",'+
        '               \"types\" : [ \"street_number\" ]'+
        '            },'+
        '            {'+
        '               \"long_name\" : \"Amphitheater Pkwy\",'+
        '               \"short_name\" : \"Amphitheater Pkwy\",'+
        '               \"types\" : [ \"route\" ]'+
        '            },'+
        '            {'+
        '               \"long_name\" : \"Mountain View\",'+
        '               \"short_name\" : \"Mountain View\",'+
        '               \"types\" : [ \"locality\", \"political\" ]'+
        '            },'+
        '            {'+
        '               \"long_name\" : \"Broad & Pattison\",'+
        '               \"short_name\" : \"Broad & Pattison\",'+
        '               \"types\" : [ \"intersection\"]'+
        '            },'+
        '            {'+
        '               \"long_name\" : \"Santa Clara\",'+
        '               \"short_name\" : \"Santa Clara\",'+
        '               \"types\" : [ \"administrative_area_level_2\", \"political\" ]'+
        '            },'+
        '            {'+
        '               \"long_name\" : \"Whatever\",'+
        '               \"short_name\" : \"Whatevs\",'+
        '               \"types\" : [ \"administrative_area_level_3\", \"political\" ]'+
        '            },'+
        '            {'+
        '               \"long_name\" : \"California\",'+
        '               \"short_name\" : \"CA\",'+
        '               \"types\" : [ \"administrative_area_level_1\", \"political\" ]'+
        '            },'+
        '            {'+
        '               \"long_name\" : \"United States\",'+
        '               \"short_name\" : \"US\",'+
        '               \"types\" : [ \"country\", \"political\" ]'+
        '            },'+
        '            {'+
        '               \"long_name\" : \"94043\",'+
        '               \"short_name\" : \"94043\",'+
        '               \"types\" : [ \"postal_code\" ]'+
        '            }'+
        '         ],'+
        '         \"formatted_address\" : \"1601 Amphitheater Pkwy, Mountain View, CA 94043, USA\",'+
        '         \"geometry\" : {'+
        '            \"location\" : {'+
        '               \"lat\" : 37.42291810,'+
        '               \"lng\" : -122.08542120'+
        '            },'+
        '            \"location_type\" : \"ROOFTOP\",'+
        '            \"viewport\" : {'+
        '               \"northeast\" : {'+
        '                  \"lat\" : 37.42426708029149,'+
        '                  \"lng\" : -122.0840722197085'+
        '               },'+
        '               \"southwest\" : {'+
        '                  \"lat\" : 37.42156911970850,'+
        '                  \"lng\" : -122.0867701802915'+
        '               }'+
        '            }'+
        '         },'+
        '         \"partial_match\" : true,'+
        '         \"types\" : [ \"street_address\" ]'+
        '      }'+
        '   ],'+
        '   \"status\" : \"OK\"'+
        '}';

        String invalidRequestResponse = '{\"results\" : [],\"status\" : \"INVALID_REQUEST\"}';
        String overQueryLimitResponse = '{\"results\" : [],\"status\" : \"OVER_QUERY_LIMIT\"}';
        String requestDeniedResponse = '{\"results\" : [],\"status\" : \"REQUEST_DENIED\"}';
        String unknownErrorResponse = '{\"results\" : [],\"status\" : \"UNKNOWN_ERROR\"}';
        String zeroResultsResponse = '{\"results\" : [],\"status\" : \"ZERO_RESULTS\"}';

        HttpResponse res = new HttpResponse();
        res.setHeader('Content-Type', 'application/json');
        if (req.getEndpoint().contains('401')) {
            res.setStatusCode(401);
            res.setStatus('Not authenticated.');
        } else {
            res.setStatusCode(200);
            res.setStatus('OK');

            if(req.getEndPoint().contains('single-address')) {
                res.setBody(singleAddressResponse);
            } else if(req.getEndPoint().contains('ambiguous-address')) {
                res.setBody(ambiguousAddressResponse);
            } else if(req.getEndPoint().contains('invalid-request')) {
                res.setBody(invalidRequestResponse);
            } else if(req.getEndPoint().contains('over-query-limit')) {
                res.setBody(overQueryLimitResponse);
            } else if(req.getEndPoint().contains('request-denied')) {
                res.setBody(requestDeniedResponse);
            } else if(req.getEndPoint().contains('unknown-error')) {
                res.setBody(unknownErrorResponse);
            } else if(req.getEndPoint().contains('zero-results')) {
                res.setBody(zeroResultsResponse);
            }
        }
        return res;
    }
}