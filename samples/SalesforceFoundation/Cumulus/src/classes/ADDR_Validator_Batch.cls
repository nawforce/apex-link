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
* @description Batch class for SmartyStreets address verification. It looks for all populated addresses and updates them if the setting is turned on.
*/
public class ADDR_Validator_Batch implements Database.Batchable<sObject>, Database.AllowsCallouts {

    private String query;
    
    /*******************************************************************************************************
    * @description Constructor that initializes the class.
    * @param skipPreviouslyVerified If true, the batch skips over objects that already have any verification records.
    */
    public ADDR_Validator_Batch(Boolean skipPreviouslyVerified) {
    	//turn smartystreets verification on, so the batch verification works even if the checkbox wasn't checked by the user
    	Addr_Verification_Settings__c avs = UTIL_CustomSettingsFacade.getAddressVerificationSettings();
        
        //query for all US addresses
        query = 'select id, name, MailingStreet__c, MailingStreet2__c, Formula_MailingStreetAddress__c, Formula_MailingAddress__c, MailingCity__c, MailingState__c, MailingPostalCode__c, MailingCountry__c, API_Response__c ' + 
            ' from Address__c where ' +
            '(MailingCountry__c = null or MailingCountry__c in (\'US\', \'USA\', \'United States\', \'United States of America\')) ' +
            'and (MailingStreet__c != null or MailingPostalCode__c != null or (MailingCity__c != null and MailingState__c != null))';
         
        if (skipPreviouslyVerified)
            query += ' and Verified__c != true';
    }
    
    /*******************************************************************************************************
    * @description Collects the records to be passed to the execute method in batches.
    * @param bc The context for the batch. Provided by the platform.
    * @return Database.QueryLocator The record set processed in chunks for each iteration of the batch.
    */
    public Database.QueryLocator start(Database.BatchableContext bc) {
        return Database.getQueryLocator(query);
    }

    /*******************************************************************************************************
    * @description Processes the batch of records in the scope.
    * @param bc The context for the batch. Provided by the platform.
    * @param scope The batch records to be process.
    * @return void
    */
    public void execute(Database.BatchableContext bc, List<Address__c> scope) {
        list<Address__c> addressesToVerify = new list<Address__c>();
        for (Address__c a : scope)
            if (isUS(a)) addressesToVerify.add(a); 
        
        //verify and update this batch of addresses
        if (!addressesToVerify.isEmpty())
            ADDR_Validator.verifyAddressRecords(addressesToVerify);
    }
    
    /*******************************************************************************************************
    * @description Method to be called after all batches are processed. Required by the interface.
    * @return void
    */
    public void finish(Database.BatchableContext bc) { }
    
    /*******************************************************************************************************
    * @description Determines if an address is US-based. 
    *
    *  Also called from ADDR_SmartyStreets_Validator. Since it's an auxiliary method we could move it to a separate 
    * class, but we'll keep it here for now because it's the only auxiliary one we have for address validation.
    *
    * @param c The address to inspect.
    * @return Boolean True if the address is US-based. False otherwise.
    */
    public static Boolean isUS(Address__c c) {
        return ((c.MailingStreet__c != null || c.MailingPostalCode__c != null || (c.MailingCity__c != null && c.MailingState__c != null))
            && (c.MailingCountry__c == null || c.MailingCountry__c == 'US' || c.MailingCountry__c == 'USA' || 
                        c.MailingCountry__c == 'United States' || c.MailingCountry__c == 'United States of America'));
    }
}