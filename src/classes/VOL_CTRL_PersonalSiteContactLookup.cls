/*
    Copyright (c) 2016, Salesforce.org
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

global with sharing class VOL_CTRL_PersonalSiteContactLookup {

    // constructor
    global VOL_CTRL_PersonalSiteContactLookup() {

		// initial values for potential parameters
    	strLanguage = 'en-us'; 
    	strResult = null;

        // handle optional page parameters (must use string, not ID, to handle null)
        map<string, string> params = ApexPages.currentPage().getParameters();
        string p = params.get('Language');
        if (p != null && p != '') strLanguage = p;
    }	
	
	// returns the correct URL for the CSS override file
    global string strURLtoCSSFile { 
        get { 
            if (strURLtoCSSFile == null) {
                list<Document> listDocs = [SELECT Name, Id From Document WHERE Name = 'VolunteersPersonalSiteCSS.css' LIMIT 1 ];
                if (listDocs.size() > 0) {
                    Document doc = listDocs[0];
                    string imageid = doc.id;
                    imageid = imageid.substring(0,15);
                    strURLToCSSFile = '/servlet/servlet.FileDownload?file=' + imageid;
                }
            }
            return strURLtoCSSFile;
        }  
        
        set;
    }
    
    global Contact contact {
        get {
            if (contact == null) contact = new Contact();
            return contact;
        }       
        set; 
    }

	global string strResult { get; set; }
    global string strLanguage { get; set; }
	
	// the action method called from the page to lookup the contact, and to send them email if found.
	global PageReference LookupContact() {
		
		list<Contact> listCon = VOL_SharedCode.LookupContact(contact, null);
		if (listCon == null || listCon.size() == 0) {
			strResult = System.Label.labelContactLookupNotFound;
		} else {
			SendEmailToContact(listCon[0]);
		}				
		return null;
	}
	
	// get the Id of the Email Template to use
	public ID emailTemplateId {
		get {
			if (emailTemplateId == null) {
				list<EmailTemplate> listT = [select Id from EmailTemplate where Name='Volunteers Personal Site Contact Lookup' limit 1];
				if (listT.size() > 0) emailTemplateId = listT[0].Id;
			}
			return emailTemplateId;
		}
		set;
	}
	
	// get the Id of the Org-wide Email Address to use
	public ID orgWideEmailId {
		get {
			if (orgWideEmailId == null) {
				string strOwa = VOL_SharedCode.VolunteersSettings.Personal_Site_Org_Wide_Email_Name__c;
				if (strOwa != null) {
					list<OrgWideEmailAddress> listOwa = [select id from OrgWideEmailAddress	where DisplayName = :strOwa];
					if (listOwa.size() > 0) orgWideEmailId = listOwa[0].Id;
				}
			}
			return orgWideEmailId;
		}
		set;
	}
		
	// send the email to the passed in contact
	public void SendEmailToContact(Contact con) {
		strResult = null;			
    	Messaging.SingleEmailMessage mail = new Messaging.SingleEmailMessage();
		mail.setTargetObjectId(con.Id);
		mail.setSaveAsActivity(true);
		mail.setTemplateID(emailTemplateId);	
		if (orgWideEmailId != null) {
			mail.setOrgWideEmailAddressId(orgWideEmailId);
		}		
		list<Messaging.SendEmailResult> listSER;
		listSER = Messaging.sendEmail(new Messaging.Email[] { mail }, false);
		if (listSER[0].isSuccess()) {
			strResult = System.Label.labelContactLookupSuccess;
		} else {
			list<Messaging.SendEmailError> listSEE = listSER[0].getErrors();
			for (Messaging.SendEmailError see : listSEE) {
				ID objId = see.getTargetObjectId();
				if (objId == null) {
					// see if we can parse it out of the error string.
					string strErr = see.getMessage();
					integer ich = strErr.lastIndexOf(' for id : ');
					if (ich > = 0) {
						objId = strErr.substring(ich + 10);
					}
				}
				list<Contact> listCon = [select Name, Email from Contact where Id =: objId];
				string strDetails = '';
				if (listCon.size() > 0) 
					strDetails = ' ' + listCon[0].Name + ' (' + listCon[0].Email + ') '; 
				strResult += see.getStatusCode() + ': ' + see.getMessage() + strDetails;
			}
		}			
	}					

}