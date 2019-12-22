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
* @description Class that gets called when Address__c records change and
* begins the address verification/normalization.
* @async_support this trigger handler DOES NOT SUPPORT ASYNC!  the validation call is a future,
* and if we are already in a future to deal with the trigger handler, we can't call the future verification api!
*/
public class ADDR_Validator_TDTM extends TDTM_Runnable {
	
	/*******************************************************************************************************
    * @description Exception raised during the address validation process.
    */
	public class ADDR_Validator_Exception extends Exception { }
	
	/*******************************************************************************************************
    * @description Indicates whether the class has been run in the current context.
    */
	public static Boolean alreadyRun = false;
	
	/*******************************************************************************************************
    * @description Verifies new or modified address records, if verification enabled in the settings.
    * @param listNew The list of Addresses from trigger new. 
    * @param listOld The list of Addresses from trigger old. 
    * @param triggerAction The trigger event (BeforeInsert, AfterInsert, etc.) that caused the class to run. 
    * @param objResult The describe for Addresses.
    * @return dmlWrapper The wrapper with the records to perform DML on. 
    */
	public override DmlWrapper run(List<SObject> newList, List<SObject> oldList, 
    TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) { 
        
        if(!alreadyRun) {
	        Addr_Verification_Settings__c settings = UTIL_CustomSettingsFacade.getAddressVerificationSettings();
	        if ((triggerAction == TDTM_Runnable.Action.BeforeInsert || triggerAction == TDTM_Runnable.Action.BeforeUpdate) &&
	           settings.enable_automatic_verification__c) {        	
	        	for(SObject so : newList) {
	        		Address__c address = (Address__c)so;
	        		if(!system.isFuture() && !system.isBatch()) {
	        		    address.Verification_Status__c = Label.Addr_Pending_Verification;
	        		} else {
	        			address.Verification_Status__c = Label.Addr_Verification_Required;
	        		}	
        	    }
	        }
            if ((triggerAction == TDTM_Runnable.Action.AfterInsert || triggerAction == TDTM_Runnable.Action.AfterUpdate) &&
	           settings.enable_automatic_verification__c) {
                if(!system.isFuture() && !system.isBatch()) {
                    ADDR_Validator.verifyRecordsFuture(new Map<Id, SObject>(newList).keyset());
                    alreadyRun = true;
                } else if(system.isFuture()) {
                    UTIL_Debug.debug('****ADDR_Validator_TDTM called from future, addresses not verified.');
                    /*We don't want to log an error here. Instead, we'll just do a debug statement.
                	ERR_Handler.processError(new ADDR_Validator_Exception('ADDR_Validator_TDTM called from future'), 
                	                           ERR_Handler_API.Context.ADDR);*/
                } else if(system.isBatch()) {
                	UTIL_Debug.debug('****ADDR_Validator_TDTM called from batch, addresses not verified.');
                    /*We don't want to log an error here. Instead, we'll just do a debug statement.

                    ERR_Handler.processError(new ADDR_Validator_Exception('ADDR_Validator_TDTM called from batch'), 
                                               ERR_Handler_API.Context.ADDR);*/
                }
	        }
        }
        //we cannot return records to perform DML on because the address verification has to run
        //in a future for making external web service calls
        return null;
    }
}