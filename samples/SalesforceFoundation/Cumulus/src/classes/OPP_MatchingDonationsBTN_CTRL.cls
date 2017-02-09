/*
    Copyright (c) 2011, Salesforce.org
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
* @author James Melville
* @date 2011 (2.1)
* @group Opportunity
* @description Controller for matching donation button for opportunity
*/
public with sharing class OPP_MatchingDonationsBTN_CTRL {

    private final ApexPages.StandardController opty;

    //constructor takes StandardController
    public OPP_MatchingDonationsBTN_CTRL(ApexPages.StandardController optyControl){
        opty = optyControl;
    }

    // Code we will invoke on page load.
    public PageReference MatchingDonationPage()
    {
        Savepoint sp = Database.setSavepoint();

        try {
            //get the Id of the current record. we will query for the current record in case any fields we expect are not exposed
            Id opptyId = opty.getId();

            //multi-currency support - get the schema map to see if we have the CurrencyIsoCode field
            SObjectField OppCurrencyField = Schema.sObjectType.Opportunity.fields.getMap().get('CurrencyIsoCode');

            //query for the current donation to create the matching one
            Opportunity oppty = Database.query('select id, name,RecordTypeId,accountId,Account.Name,Amount,closedate' + 
            (OppCurrencyField != null ? ',CurrencyIsoCode' : '') + 
            ' from Opportunity where id = :opptyId');

            //start preparing our oppty new record url to redirect to
            PageReference pageRef = new PageReference('/006/e');

            //get the URL parameters map to add to it
            Map<string,string> params = pageRef.getParameters();

            //set record type based on source donation (label.npe01.= "$Source"), or other type
            if(system.label.npe01.Matching_Donation_Record_Type == '$Source'){
                params.put('RecordType', (Id)oppty.get('RecordTypeId'));
            }
            else{
                List <RecordType> rts = [select Id from RecordType where SobjectType = 'Opportunity' and Name = :system.label.npe01.Matching_Donation_Record_Type limit 1];
                if (rts.size() > 0)
                    params.put('RecordType', rts[0].Id);
            }

            //find primary contact
            List<OpportunityContactRole> ocr = [select id, ContactId from OpportunityContactRole where IsPrimary = true and OpportunityId = :oppty.Id limit 1];
            if ( ocr.size() > 0 ) 
                params.put('conid',ocr[0].ContactId);
                
            //and the rest of the params
            params.put('retURL','/'+opptyId);
            params.put('cancelURL','/'+opptyId);

            //put in important fields
            params.put('accid',oppty.AccountId);
            params.put('opp3',string.format(system.label.npe01.Matching_Donation_Record_Suffix, new string[] {oppty.Name,oppty.Account.Name,oppty.CloseDate.format(),system.today().format()}));
            params.put('opp7',oppty.Amount.format());
            params.put('opp9',oppty.CloseDate.format());

            //copy currency accross if mutli currency org
            if(OppCurrencyField != null)
                params.put('opp16',String.ValueOf(oppty.get(OppCurrencyField)));

            pageRef.setRedirect(true);

            return pageRef;
        } catch(Exception e) {
            Database.rollback(sp);
            ERR_Handler.processError(e, ERR_Handler_API.Context.OPP);
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, e.getMessage()));
            return null;
        }
    }
}