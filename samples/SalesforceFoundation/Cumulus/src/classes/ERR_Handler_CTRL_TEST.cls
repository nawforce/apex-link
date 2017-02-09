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
* @description test class for error handler controller
* @group ErrorHandling
*/
public with sharing class ERR_Handler_CTRL_TEST {
    /*******************************************************************************************************
    * @description test error handling with a save point
    */
    public pageReference withManualRollback() {
        Savepoint sp = Database.setSavepoint();
        try {       
            Contact contact1 = new Contact(FirstName = 'test', LastName = 'testerson1', Title = 'VP1');
            insert contact1;
            
            //Deleting the contact to force an exception. In the method above an exception is not thrown
            //because the error is caught by TDTM_TriggerHandler.
            delete contact1;
            
            npe4__Relationship__c[] relationships = new npe4__Relationship__c[]{
                new npe4__Relationship__c(npe4__contact__c = contact1.id, npe4__Type__c='TestLookupType') };
            
            insert relationships;
        
        } catch(Exception e) {
        	UTIL_Debug.debug('****Exception caught: ' + e.getMessage());
            Database.rollback(sp);
            //Using the API class just to give it test coverage.
            ERR_Handler_API.processError(e, ERR_Handler_API.Context.TDTM.name());
        }
        return null;
    }
}