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
* @description Class that asynchronously starts the address verification calls
* and processes the results.
*/
public with sharing class ADDR_Validator {

    /** @description Gets verification settings.*/
    private static Addr_Verification_Settings__c settings = UTIL_CustomSettingsFacade.getAddressVerificationSettings();

    /** @description Controller for verify address button, does nothing.*/
    public ADDR_Validator(ApexPages.StandardController standardController) {}

    /** @description Action method for verify address button, verifies address.*/
    public PageReference verifyAddressButton() {
        id addrId = ApexPages.currentPage().getParameters().get('id');
        if (addrId != null) {
            set<id> setAddrId = new Set<Id>{addrId};
            verifyRecords(setAddrId);
        }
        return null;
    }

    /*******************************************************************************************************
    * @description Verifies a set of address records in a future.
    * @param recordIds A set containing the IDs of the address records to verify.
    * @return void
    */
    @future(callout=true)
    public static void verifyRecordsFuture(set<id> recordIds) {
        verifyRecords(recordIds);
    }
    
    /*******************************************************************************************************
    * @description Verifies a set of address records.
    * @param recordIds A set containing the IDs of the address records to verify.
    * @return void
    */
    public static void verifyRecords(set<id> recordIds) {
    	UTIL_Debug.debug(LoggingLevel.WARN, '****Retrieving data records');
        //query for all the indicated verification records
        list<Address__c> avList = [ select Id, Name, CreatedDate, LastModifiedDate, 
            MailingStreet__c, MailingStreet2__c, Formula_MailingStreetAddress__c, MailingCity__c, MailingCountry__c, MailingState__c, MailingPostalCode__c, 
            Verified__c, API_Response__c, Geolocation__Latitude__s, Geolocation__Longitude__s, Formula_MailingAddress__c,
            Congressional_District__c, County_Name__c from Address__c where id in : recordIds];

        verifyAddressRecords(avList);
    }
    
    private class ValidationException extends Exception {}
    
    /*******************************************************************************************************
    * @description Verifies a list of address records.
    * We separate this in its own method from the query so that we can call it also from the batch process.
    * @param avList The list of address records to verify.
    * @return void
    */
    public static void verifyAddressRecords(List<Address__c> avList) {
    	if (!avList.isEmpty()) {
            UTIL_Debug.debug(LoggingLevel.WARN, '****Verifying records');
            if(settings != null) {
                String classToRunName = String.valueOf(settings.get('Class__c'));
                if(classToRunName != null) {
	                Type classType = Type.forName(classToRunName);
	                if(classType != null) {     
	                    Object classInstance = classType.newInstance();
	                    //the objects we create dynamically need to implement the ADDR_IValidator interface
	                    if(classInstance instanceof ADDR_IValidator) {
	                        ADDR_IValidator validator = (ADDR_IValidator)classInstance;
	                        //setting the flag to avoid running ADDR_Validator_TDTM again after the verification
	                        ADDR_Validator_TDTM.alreadyRun = true;
	                        //updating the addresses with the verified version
	                        update validator.verifyRecords(avList, settings);
	                    } else {
	                        ERR_Handler.processError(new ValidationException(Label.Addr_Verification_Wrong_Interface), ERR_Handler_API.Context.ADDR);
	                    }
	                } else {
	                    ERR_Handler.processError(new ValidationException(Label.Addr_Verification_Wrong_Class), ERR_Handler_API.Context.ADDR);
	                }
                }
            } else {
                ERR_Handler.processError(new ValidationException(Label.Addr_Verification_Undefined_Class), ERR_Handler_API.Context.ADDR);
            }
        }
    }
}