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
* @date 2015
* @group Contacts
* @group-content ../../ApexDocContent/Contacts.htm
* @description Overrides the contact Delete button.
* Avoids leaving orphaned accounts or contacts unrecoverable from the recycle bin by providing an interstitial confirmation dialog
* for deleting the account instead of the contact if they are alone in their household or 1-1 account.
* Also cascade deletes a Contact's Opportunities, and provides validation if any are closed/won or attributed to an organization.
*/
public with sharing class CON_DeleteContactOverride_CTRL {

    /** @description The contact record the user is attempting to delete. */
    private Contact queryContact;
    /** @description The id of the contact's account. */
    private String accountId;
    /** @description The return URL. */
    private String retURL;
    /** @description for LEx and SF1 set navigation instructions for JS - navigate back if only del contact and not from contact */ 
    public String sforceNav {get; private set;}
    /** @description Boolean that is set to true if user theme is LEx or SF1 */ 
    private Boolean sforceUser;
    /** @description Formatted string prompting the user to delete the account instead. */
    public String helpDeleteAccountInstead {get;set;}
    /** @description Flag to tell the page which operation we're doing. */
    public Boolean shouldDeleteContactAlone {get;set;}
    /** @description Boolean for disabling the account deletion button if there are errors preventing deletion. */
    public Boolean hasPageMessages {
        get {
            return ApexPages.hasMessages();
        }
        set;
    }

    /*******************************************************************************************************
    * @description Standard controller constructor. Queries for contact, verifies whether the contact or its
    * account should be deleted.
    */ 
    public CON_DeleteContactOverride_CTRL(ApexPages.StandardController stdController) {
        sforceUser = UserInfo.getUiThemeDisplayed().contains('Theme4');
        // In LEx and SF1 the returl param name differs from classic
        String param = sforceUser ? 'vfRetURLInSFX' : 'returl';
        retURL = ApexPages.currentPage().getParameters().get(param);

        //if we don't have a return URL, go back to Contacts home.
        if (String.isBlank(retURL)) {
            retURL = '/003/o';
        }

        queryContact = [SELECT Id, Name, AccountId, Account.Name, Account.npe01__SYSTEMIsIndividual__c FROM Contact WHERE Id = :stdController.getId()];

        helpDeleteAccountInstead = String.format(Label.ConfirmDeleteAccount, new List<String>{queryContact.Name, queryContact.Account.Name});

        shouldDeleteContactAlone = true;

        if (queryContact.AccountId != null && queryContact.Account.npe01__SYSTEMIsIndividual__c) {
            accountId = queryContact.AccountId;
            list<AggregateResult> contactsInHousehold = [SELECT COUNT(id) ct FROM Contact WHERE AccountId = :accountId GROUP BY AccountId HAVING COUNT(id) = 1];

            //This contact is alone in a system account, delete the system account and allow the cascading
            //delete to remove the contact
            if (contactsInHousehold.size() == 1 && contactsInHousehold[0].get('ct') == 1) {
                shouldDeleteContactAlone = false;
            }
        }
    }

    /*******************************************************************************************************
    * @description Action method in Contact Delete Button override, handles deleting a contact or prompting
    * user to delete its account if it is a system account (1:1, individual, or household) and is the last 
    * contact in the account.
    * @return pageReference Redirects to the Contacts tab if the contact has been deleted, otherwise stays
    * on the page and displays error messages.
    */ 
    public PageReference processDelete() {
        try {
            return deleteContact();
        } catch (Exception ex){
            if (!hasPageMessages)
                ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, ex.getMessage()));
            return null;
        }
    }

    /*******************************************************************************************************
    * @description Deletes a contact, after adding cascade deletion records for related opportunities and 
    * recurring donations. Validates opportunities and adds error messages where appropriate.
    * @return pageReference Redirects to the Contacts tab if the contact has been deleted, otherwise stays
    * on the page and displays error messages.
    */ 
    public PageReference deleteContact() {
        if (shouldDeleteContactAlone) {
            //add cascades with validation that we can't include all the time
            //this is because contact merge runs before delete triggers with no way to differentiate from standard delete
            List<UTIL_CascadeDeleteLookups_TDTM.Cascade> contactCascades = new List<UTIL_CascadeDeleteLookups_TDTM.Cascade>{
                new UTIL_CascadeDeleteLookups_TDTM.Cascade('Case', 'ContactId'),
                new UTIL_CascadeDeleteLookups_TDTM.Cascade('Opportunity','Primary_Contact__c', new List<String>{'IsWon','IsClosed','AccountId'}),
                new UTIL_CascadeDeleteLookups_TDTM.Cascade('npe03__Recurring_Donation__c', 'npe03__Contact__c')
            };
            //validate that there are no cases associated with this contact, but don't try to cascade delete them if validation passes
            contactCascades[0].validateOnly = true;
            CON_CascadeDeleteLookups_TDTM.cascades.addAll(contactCascades);
            delete queryContact;

            if (sforceUser) {
                sforceNav = returl.contains(queryContact.Id) ? 'home' : 'back';
                return null;
            } else {
                return getRedirect();
            }
        }
        return null;
    }

    /*******************************************************************************************************
    * @description Deletes contact's account once the user has accepted to do so. Adds cascade deletion 
    * records for validating that there are no related closed/won opportunities or cases associated with this
    * Account. Although the platform handles this validation, the error message returned in this custom page
    * wasn't friendly; the cascade deletion framework displays helpful errors with links to the offending records.
    * @return pageReference Redirects to the Contacts tab if the account has been deleted, otherwise stays
    * on the page and displays error messages.
    */ 
    public PageReference deleteAccount() {
        Account accForDelete = new Account(id=accountId);
        
        //add cascade to make prettier error message on contact delete page,
        //but don't want to attempt under normal account circumstances as these are handled by the platform 
        List<UTIL_CascadeDeleteLookups_TDTM.Cascade> validationOnlyCascades = new List<UTIL_CascadeDeleteLookups_TDTM.Cascade>{
            new UTIL_CascadeDeleteLookups_TDTM.Cascade('Opportunity', 'AccountId', new List<String>{'IsWon','IsClosed'}),
            new UTIL_CascadeDeleteLookups_TDTM.Cascade('Case', 'AccountId')
        };
        //validate that there are no cases or closed/won opps associated with this account, but don't try to cascade delete them if validation passes
        validationOnlyCascades[0].validateOnly = true;
        validationOnlyCascades[1].validateOnly = true;

        ACCT_CascadeDeleteLookups_TDTM.cascades.addAll(validationOnlyCascades);

        try {
            delete accForDelete;
        } catch (Exception ex){
            if (!hasPageMessages)
                ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, ex.getMessage()));
            return null;
        }

        //if we were returning to the account we just deleted, go back to contacts home
        if (retURL.contains(accountId.substring(0,15))) {
            retURL = '/003/o';
        }

        if (sforceUser) {
            sforceNav = 'home';
            return null;
        } else {
            return getRedirect();
        }
    }

    /*******************************************************************************************************
    * @description Returns a redirect enabled PageReference for the return URL
    * @return pageReference The PageReference with redirect enabled.
    */ 
    private PageReference getRedirect() {
        PageReference redirect = new PageReference(retURL);
        redirect.setRedirect(true);
        return redirect;
    }
}