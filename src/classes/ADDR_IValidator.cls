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
* @description Interface that classes that provide address validation/normalization
* should implement.
*/
public Interface ADDR_IValidator {

    /*******************************************************************************************************
    * @description Provides the called with the service name.
    * @return String The name of the address validation service.
    */
    String getServiceName();
    
    /*******************************************************************************************************
    * @description Provides the caller with the default endpoint URL.
    * @return String The URL to send requests to.
    */
    String getDefaultURL();
        
    /*******************************************************************************************************
    * @description Tells the caller if the class supports batch address validation.
    * @return Boolean True is the class supports batch address validation. False otherwise.
    */
    Boolean supportsBatch();
    
    /*******************************************************************************************************
    * @description Provides the caller with help text regarding batch support.
    * @return String The help text.
    */
    String getBatchHelpText();
    
    /*******************************************************************************************************
    * @description Verifies a list of Addresses records.
    * @param addresses List of Address records to verify.
    * @param settings The Address Verification Settings to use. 
    * @return List<Address__c> List of verified address records. 
    */
    List<Address__c> verifyRecords(list<Address__c> addresses, Addr_Verification_Settings__c settings);
}