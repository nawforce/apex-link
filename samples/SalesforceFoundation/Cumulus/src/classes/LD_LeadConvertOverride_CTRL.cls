/*
    Copyright (c) 2010, Salesforce.org
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
* @author Jason Venable & Evan Callahan
* @date 2010 (2.x)
* @group Lead Conversion
* @group-content ../../ApexDocContent/LeadConversion.htm
* @description Controller for lead conversion of all contacts
*/
public with sharing class LD_LeadConvertOverride_CTRL {
// Controller for lead conversion of all contacts

// Written by jason venable and evan callahan
// at the NPSP Sprint 2010

    /*******************************************************************************************************
    * @description Sets the Lead to be converted
    */
    public lead l { get; set; }
    /*******************************************************************************************************
    * @description Provides a dummy Contact to write to - avoids ownership issues when setting owner
    */
    public Contact dummyCon { get; set; }
    /*******************************************************************************************************
    * @description Sends email to the owner on conversion if selected
    */
    public boolean sendEmailToOwner { get; set; }
    
    /*******************************************************************************************************
    * @description Contains the Id or 'NEW' for the Account to use.
    */
    public string strAccountId { get; set; }
    
    /*******************************************************************************************************
    * @description Contains the Id or 'NEW' for the Primary Affiliation to use.
    */
    public string strAfflAccountId { get; set; }
    
    /*******************************************************************************************************
    * @description Determines the account that the created Opportunity will be related to. 
    * 'CON_ACC' -> Contact.AccountId
    * 'AFF_ACC' -> Contact.Primary_Affiliation__c  
    */
    public string strOppAccChoice { get; set; }
    
    /*******************************************************************************************************
    * @description Contains the value of the converted-into Contact
    */
    public string strConId { get; set; }

    /*******************************************************************************************************
    * @description Constructs the value of the Opportunity name if created
    */
    public string oppName { get; set; }
    /*******************************************************************************************************
    * @description Determines if an Opportunity is not created
    */
    public boolean doNotCreateOpp { get; set; }

    /*******************************************************************************************************
    * @description List of possible matching Contacts
    */
    public list<selectOption> conList;

    /*******************************************************************************************************
    * @description Selected Contact
    */
    public Contact selectedCon;

    /*******************************************************************************************************
    * @description List of possible Opportunity Account
    */
    public list<selectOption> oppAccChoices;

    /*******************************************************************************************************
    * @description Settings for the main Contacts & Organizations pacakge (npe01)
    */
    private npe01__Contacts_And_Orgs_Settings__c ContactsSettings;

    /*******************************************************************************************************
    * @description Select options for the possible Lead Statuses
    */
    public list<selectOption> lcStatuses {
      get {
        if (lcStatuses == null) {
          lcStatuses = new list<selectOption>();
          for (LeadStatus ls : [Select Id, Masterlabel from LeadStatus where IsConverted=true] ) {
            lcStatuses.add( new SelectOption(ls.masterlabel, ls.masterlabel));
          }
        }
        return lcStatuses;
      }
      private set;
    }

    /*******************************************************************************************************
    * @description Checks the value of the 'Company' field for values associated with 1x1 or Individual Accounts
    */
    public boolean isSpecialAccount {
        get {
            if (l == null)
                return false;
            return (
                l.company == null ||
                l.company == '' ||
                l.company.toLowerCase() == 'self' ||
                l.company.toLowerCase() == 'individual' ||
                l.company.toLowerCase() == l.name.toLowerCase()
            );
        }
    }

    /*******************************************************************************************************
    * @description Provides the options for select list of possible Contact matches
    */
    public list<selectOption> getConList() {
      if (conList == null) {
        conList = new list<selectOption>();

        conList.add( new SelectOption('', label.stgLabelNone) );
        conList.add( new SelectOption('NEW_CONTACT', label.leadConvertCreateNewContact + ' ' + l.name) );

        for (contact c : conMap.values()) {
          conList.add( new SelectOption(c.id, label.leadConvertMergeExistingContact + ' ' + c.name) );
        }
      }
      return conList;
    }
    
    public List<SelectOption> getOppAccChoices() {
        if(oppAccChoices == null) {
            oppAccChoices = new List<SelectOption>();
            
            oppAccChoices.add(new SelectOption('CON_ACC', label.leadConvertContactAccount));
            oppAccChoices.add(new SelectOption('AFF_ACC', label.leadConvertAffiliatedAccount));
        }
        return oppAccChoices;
    }

    /*******************************************************************************************************
    * @description Display the selection list for matching Contacts
    */
    private map<id, contact> conMap {
        get {
            if (conMap == null) {
                String lLastName = l.LastName;
                String lFirstName = l.FirstName;
                String lEmail = l.Email;

                Set<String> fieldSet = new Set<String>{'Id', 'Name', 'FirstName', 'LastName'};
                String queryStr = 'SELECT ';
                for (FieldSetMember cfs : conFieldSet)
                   fieldSet.add(cfs.getFieldPath());
                queryStr += String.join(new List<String>(fieldSet), ',');
                queryStr += ' FROM Contact WHERE (LastName = :lLastName AND FirstName = :lFirstName) OR (Email = :lEmail AND Email != null) LIMIT 50';

                // find contact matches
                conMap = new map<id, contact>((List<Contact>)Database.query(queryStr));
            }
            return conMap;
        }
        set;
    }

    /*******************************************************************************************************
    * @description holds the list of FieldSetMembers from the appropriate field set.
    * @return List<Schema.FieldSetMember>
    */
    public List<Schema.FieldSetMember> conFieldSet {
        get {
            if (conFieldSet == null) {
                conFieldSet = Schema.SObjectType.Contact.FieldSets.ContactMergeFoundFS.getFields();
            }
            return conFieldSet;
        }
        private set;
    }

    /*******************************************************************************************************
    * @description Display the selected Contact
    */
    public Contact getSelectedCon() {
        if (strConId!='NEW_CONTACT' & strConId!=null && conMap.containsKey(strConId)) {
            selectedCon = conMap.get(strConId);
        } else {
            selectedCon = null;
        }
        return selectedCon;
    }

    /*******************************************************************************************************
    * @description Provides the options for select list of possible Account to use for the Contact
    */
    public list<selectOption> listSOAccounts {
        get {
            if (listSOAccounts == null) {
                listSOAccounts = new list<selectOption>();
                string strAcc;
                if (CAO_CONSTANTS.isHHAccountModel())
                    strAcc = label.leadConvertAutocreateHHAccount;
                else if (CAO_CONSTANTS.isOneToOne())
                    strAcc = label.leadConvertAutocreateOne2OneAccount;
                else if (CAO_CONSTANTS.isBucketModel())
                    strAcc = label.leadConvertAttachBucketAccount;
                if (strAcc != null)
                    listSOAccounts.add(new SelectOption('AUTO', strAcc));
                    
                if (!isSpecialAccount) {
                    // find Account matches
                    for (Account acc : mapIdAccountMatches.values())
                        listSOAccounts.add(new SelectOption(acc.Id, Label.leadConvertAttachExistingAccount + ' ' + acc.Name));
                    // new Account
                    listSOAccounts.add(new SelectOption('NEW', Label.leadConvertCreateAccount + ' ' + l.Company));
                }
            }
        return listSOAccounts;
        }
      
        set;
    }
    
    /*******************************************************************************************************
    * @description Provides the options for select list of possible Accounts to use for the Affiliation
    */
    public list<selectOption> listSOAfflAccounts {
        get {
            if (listSOAfflAccounts == null) {
                listSOAfflAccounts = new list<selectOption>();
                listSOAfflAccounts.add(new SelectOption('', label.stgLabelNone));
                if (!isSpecialAccount) {
                    // find Account matches
                    for (Account acc : mapIdAccountMatches.values())
                        listSOAfflAccounts.add(new SelectOption(acc.Id, Label.leadConvertAffiliateExistingAccount + ' ' + acc.Name));
                    // new Account
                    listSOAfflAccounts.add(new SelectOption('NEW', Label.leadConvertAffiliateNewAccount + ' ' + l.Company));
                }
            }
        return listSOAfflAccounts;
        }
      
        set;
    }

    /*******************************************************************************************************
    * @description map of all Accounts that match the name of Lead.Company
    */
    public map<Id, Account> mapIdAccountMatches {
        get {
            if (mapIdAccountMatches == null) {
                mapIdAccountMatches = new map<Id, Account>([select Id, Name, BillingCity from Account where name = :l.company limit 50]);
            }
            return mapIdAccountMatches;
        }
        set;
    }

    /*******************************************************************************************************
    * @description Constructs the page and default values for the Lead, dummy Contact and Opportunity
    * @param controller Standard page controller for the page
    * @param result Results to be processed from the query
    */
    public LD_LeadConvertOverride_CTRL(ApexPages.StandardController controller) {
        ContactsSettings = UTIL_CustomSettingsFacade.getContactsSettings();

        // get the lead
        l = [select id, name, firstname, lastname, company, email, title, ownerId, status,
             		CompanyStreet__c, CompanyCity__c, CompanyState__c, CompanyPostalCode__c, CompanyCountry__c
             from lead
             where id = : controller.getId()];

        // dummy contact allows owner selection - use contact as always accessible if user has lead conversion rights
        dummyCon = new Contact(
            OwnerId = ((((string)(l.ownerid)).startsWith('005')) ? l.ownerid : userInfo.getUserId())
        );

        // set a default opp name
        oppName = l.name;
        doNotCreateOpp = !Schema.SObjectType.Opportunity.isCreateable() || !ContactsSettings.npe01__Default_Opp_on_Convert__c;
        sendEmailToOwner = false;
        
        // set reasonable defaults for contact and account.
        strAccountId = 'AUTO';
        if (conMap.size() == 0)
            strConId = 'NEW_CONTACT';
        if (!isSpecialAccount)
            strAfflAccountId = listSOAfflAccounts[1].getValue(); // either the first existing or the new account if none.

        // warn user if default record type is the same as the HH Account Record Type
        Id recTypeId = UTIL_CustomSettingsFacade.getContactsSettings().npe01__HH_Account_RecordTypeID__c;
        if (recTypeId != null) {
            sObjectType objType = Account.sObjectType;
            String recTypeName = UTIL_RecordTypes.getRecordTypeName(objType, recTypeId);
            if (UTIL_RecordTypes.isRecordTypeDefault(objType, recTypeName)) {
                apexPages.addMessage(new ApexPages.Message(ApexPages.Severity.Warning,
                        Label.leadConvertRecordTypeWarning));
            }
        }
    }
    
    /*******************************************************************************************************
    * @description action method called when the page is created, which decides whether to defer to the
    * standard Salesforce Lead Convert page (when the Account is a company) vs. using the NPSP version (when
    * the Account is self/invididual/name to imply an 1:1/household/bucket account).
    * @return PageReference to either standard or null (this custom lead convert page).
    */
    public pageReference pickLeadConvertPage() {
        PageReference pageRef = null;
        
        if (!isSpecialAccount) {
            pageRef = new PageReference('/lead/leadconvert.jsp?retURL=%2F' + l.Id + '&id=' + l.Id + '&nooverride=1');
            pageRef.setRedirect(true);
        }
        return pageRef;
    }               

    /*******************************************************************************************************
    * @description Converts the actual Lead.  All conversion logic is contained in this method
    * @return PageReference
    */
    public pageReference convertLeadNow() {
    	Savepoint sp = Database.setSavepoint();
    	try {
	        if (strConId == null) {
	            apexPages.addMessage(new ApexPages.Message(ApexPages.Severity.Error,
	                label.leadConvertContactSelection));
	            return null;
	        }

	        // set up the conversion
	        Database.LeadConvert lc = new database.LeadConvert();
	        lc.setLeadId(l.Id);
	        if (!doNotCreateOpp && Schema.SObjectType.Opportunity.isCreateable()) lc.setOpportunityName(oppName);
	        lc.setDoNotCreateOpportunity(!Schema.SObjectType.Opportunity.isCreateable() || doNotCreateOpp);
	        lc.setConvertedStatus(l.Status);
	        lc.setOwnerId(dummyCon.OwnerId);
	        lc.setSendNotificationEmail(sendEmailToOwner);
	        
	        boolean isIndividualAccount = isSpecialAccount || strAccountId == 'AUTO';

	        // is this a merge to existing?
	        if (strConId != 'NEW_CONTACT') {
	            // get this contact's account
	            lc.setContactId(strConId);
	            lc.setAccountId(conMap.get(strConId).accountId);
	        } else if (strAccountId != 'NEW' && strAccountId != 'AUTO' && strAccountId != null && strAccountId != '') {
	           lc.setAccountId(strAccountId);
	        } else if (CAO_Constants.isBucketModel() && isIndividualAccount) {
                lc.setAccountID(CAO_Constants.getIndividualAccountID());
	        }

	        // avoid auto-affiliation creation
            AFFL_Affiliations_TDTM.doNotRunForContacts = true;
            AFFL_Affiliations_TDTM.hasRunForAccounts = true;
            
            // temporarily disable address management
            boolean hhAcctAddrDisabled = UTIL_CustomSettingsFacade.getContactsSettings().Household_Account_Addresses_Disabled__c;
            boolean orgAcctAddrEnabled = UTIL_CustomSettingsFacade.getContactsSettings().Organizational_Account_Addresses_Enabled__c;
            UTIL_CustomSettingsFacade.getContactsSettings().Household_Account_Addresses_Disabled__c = true;
            UTIL_CustomSettingsFacade.getContactsSettings().Organizational_Account_Addresses_Enabled__c = false;

	        // convert the lead
	        Database.LeadConvertResult lcr = Database.convertLead(lc);

            // restore address management
            UTIL_CustomSettingsFacade.getContactsSettings().Household_Account_Addresses_Disabled__c = hhAcctAddrDisabled;
            UTIL_CustomSettingsFacade.getContactsSettings().Organizational_Account_Addresses_Enabled__c = orgAcctAddrEnabled;

            // fix the account that lead conversion created
            if (strConId == 'NEW_CONTACT' && isIndividualAccount) {
            	string strSoql = 'select id, firstname, lastname,';
            	if (Schema.sObjectType.Contact.fields.getMap().get('RecordTypeId') != null)
            	   strSoql += ' recordtypeId,';
            	strSoql += ' npo02__Household__c, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry, ';
                if (ADDR_Addresses_TDTM.isStateCountryPicklistsEnabled)
                    strSoql += ' MailingStateCode, MailingCountryCode, ';
            	strSoql += ' MailingLatitude, MailingLongitude, HomePhone, Email';
                strSoql += ' from contact where id= \'' + lcr.getContactId() + '\'';
            	Contact con = database.query(strSoql);
                Account myAccount = new Account(Id=lcr.getAccountId());
                npo02__Households_Settings__c hs = UTIL_CustomSettingsFacade.getHouseholdsSettings();

                // update the account since it is non-standard
                if (CAO_Constants.isOneToOne()) {
                    myAccount.Name = ACCT_IndividualAccounts_TDTM.strNameOne2OneAccountForContact(con);
                    myAccount.npe01__SYSTEM_AccountType__c = CAO_Constants.ONE_TO_ONE_ORGANIZATION_TYPE;
                } else if (CAO_Constants.isHHAccountModel()) {
                    // if custom household naming is on, we need to leave the account name untouched,
                    // so on update, it will get its correct name.
                    if (!hs.npo02__Advanced_Household_Naming__c)
                        myAccount.Name = ACCT_IndividualAccounts_TDTM.strNameHHAccountForContact(con);
                    else
                        myAccount.Name = system.Label.npo02.NameReplacementText;
                    myAccount.npe01__SYSTEM_AccountType__c = CAO_Constants.HH_ACCOUNT_TYPE;
                }
                
                if (!CAO_Constants.isBucketModel()) {
                    // change its recordtype if specified in settings
                    ID rtIdAcccount = ACCT_IndividualAccounts_TDTM.rtIdForNewAccount();
                    if (rtIdAcccount != null) 
                        myAccount.put('RecordTypeID', rtIdAcccount);
                    myAccount.npe01__SYSTEMISINDIVIDUAL__c = true;
                    myAccount.npe01__One2OneContact__c = con.Id;
                    update myAccount;
                    }

	            // see if we need to create a household object for this contact (1:1 & bucket only)
	            if (!CAO_Constants.isHHAccountModel() && con.npo02__Household__c == null) {
	                if (hs.npo02__Household_Rules__c == HH_Households.ALL_PROCESSOR || 
	                   hs.npo02__Household_Rules__c == HH_Households.ALL_INDIVIDUALS_PROCESSOR) {
	                    HH_Households_TDTM.insertHousehold(new list<Contact>{con}, true, null);
	                    update con;
	                }
	            }
	            
	            // now create the address object if appropriate
	            if (CAO_Constants.isHHAccountModel() && !hhAcctAddrDisabled) {
	               Address__c addr = new Address__c();
		            addr.Household_Account__c = myAccount.Id;
		            addr.Default_Address__c = true;
		            addr.Latest_Start_Date__c = system.today();
		            addr.Latest_End_Date__c = null;
		            ADDR_Addresses_TDTM.copyAddressStdSObjAddr(con, 'Mailing', addr, null);
		            insert addr;         
	            }
            }

            //clean up the extra OCR Issue
            // if we create an opp, its a 1x1 (or HH), 
            // before 6/2016 this fix was only applied if merging into an existing contact,
            // but we've run into multiple orgs where they can reproduce the bug when creating new contacts.
            // npsp devs have not been able to reproduce it, but feel this is a safe fix for all.
            if (!lc.isDoNotCreateOpportunity() && !CAO_Constants.isBucketModel()){

                //get the new OCRs
                list<OpportunityContactRole> ocrList = [select id, isPrimary, Role from OpportunityContactRole where 
                    isPrimary = true and
                    OpportunityID = :lcr.getOpportunityId() and ContactID = :lcr.getContactId()];

                //if there are more than one delete the extraneous ones
                if (ocrList != null && ocrList.size() > 1){
                    list<OpportunityContactRole> dlist = new list<OpportunityContactRole>();
                    for (integer i = 1; i < ocrList.size(); i++){
                            dlist.add(ocrList[i]);
                    }
                    delete dlist;

                    //on delete of one ocr, the other 'isPrimary' is set to false
                    //so we need to issue an udpate to correct it.
                    //also make sure it has the correct role
                    OpportunityContactRole ocr = ocrList[0];
                    ocr.IsPrimary = true;
                    ocr.Role = ContactsSettings.npe01__Opportunity_Contact_Role_Default_role__c; 
                    update ocr;
                }

	        }
	        
            // if we created a new company account, affiliate to it.
            if (strAccountId == 'NEW')
                strAfflAccountId = lcr.getAccountId();
            
            Address__c tempAddr = new Address__c(
                MailingStreet__c = l.CompanyStreet__c,
                MailingCity__c = l.CompanyCity__c,
                MailingState__c = l.CompanyState__c,
                MailingPostalCode__c = l.CompanyPostalCode__c,
                MailingCountry__c = l.CompanyCountry__c
            );

            // if we created a new company acct and the Lead had a company address, set the new acct's address
            if (strAccountId == 'NEW' && !String.isBlank(l.CompanyStreet__c)) {
            	Account acc = new Account(
                    Id=lcr.getAccountId()
                );
                //copy address from temporary address__c record, handles state and country picklists if enabled
                ADDR_Addresses_TDTM.copyAddressAddrSObj(tempAddr,acc,'Billing',null);
                update acc;
            }   
            // handle the optional Affiliation to a new or existing Account.
            ID afflAccId = null;
            if (strAfflAccountId == 'NEW') {
                Account acc = new Account(
                    name = l.Company
                );
                //copy address from temporary address__c record, handles state and country picklists if enabled
                ADDR_Addresses_TDTM.copyAddressAddrSObj(tempAddr,acc,'Billing',null);
                insert acc;
                afflAccId = acc.Id;
            } else if (strAfflAccountId != null && strAfflAccountId != '') {
            	afflAccId = strAfflAccountId;
            }
            if (afflAccId != null) {
                AFFL_Affiliations_TDTM.doNotRunForContacts = false;
                AFFL_Affiliations_TDTM.hasRunForAccounts = false;
                AFFL_Affiliations_TDTM.createNonDupeAffiliation(l, lcr.getContactId(), afflAccId, true); 
            }
            
            //// Opportunity Cleanup 
            if(!lc.isDoNotCreateOpportunity()) {
            	Opportunity convertedOpp = new Opportunity(Id = lcr.getOpportunityId());
            
            	// Set the (a/o 3.45) Opportunity Primary Contact to the converted contact
            	convertedOpp.Primary_Contact__c = lcr.getContactId();
            
            	// set the Opportunity Account to the Primary Affiliation based on strOppAccChoice
            	if(strOppAccChoice == 'AFF_ACC') {
                	convertedOpp.AccountId = afflAccId;
            	}
                String oppName = convertedOpp.Name;

            	update convertedOpp;

                // refresh opportunity names after update. if no opportunity names are defined, keeps original name
                OPP_OpportunityNaming.refreshOppNames(new List<Opportunity>{convertedOpp});
                // update Opportunity again if refreshed name is different
                if (convertedOpp.Name != oppName)
                    update convertedOpp;
            }
            
	        // redirect
            return new PageReference('/' + lcr.getContactId());
    	} catch (exception e) {
            Database.rollback(sp);
            ERR_Handler.processError(e, ERR_Handler_API.Context.LD);
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, e.getMessage()));
            return null;
        }
    }
}