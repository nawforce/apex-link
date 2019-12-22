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

public with sharing class VOL_CTRL_SendBulkEmail {

	public string strSubject { get; set; }
	public string strMessage { get; set; }
	public Volunteer_Hours__c hourStatus { get; set; }
	public ID templateId { get; set; }
	public integer numberOfContacts { get; set; }
	public ID fromEmailId { get; set; }
	public string strJobName { get; set; }
	public boolean fLogActivity { get; set; }
	public boolean fEmailContactsOnlyOnce { get; set; }

	public ID folderId { get; set; }
	public ID campaignId { get; set; }
	public ID shiftId { get; set; }
	public ID jobId { get; set; }
	
	// constructor
	public VOL_CTRL_SendBulkEmail() { 
		
		// figure out what object we were invoked from
		campaignId = ApexPages.currentPage().getParameters().get('campaignId');
		jobId = ApexPages.currentPage().getParameters().get('jobId');
		shiftId = ApexPages.currentPage().getParameters().get('shiftId');
		fEmailContactsOnlyOnce = false;
		
		if (shiftId != null) {
			Volunteer_Shift__c shift = [select Name, Volunteer_Job__r.Name from Volunteer_Shift__c where Id = :shiftId];
			strJobName = shift.Volunteer_Job__r.Name + ' - ' + shift.Name;	
			templateObject = 'Shift';		
		} else if (jobId != null) {
			Volunteer_Job__c job = [select Name from Volunteer_Job__c where Id = :jobId];
			strJobName = job.Name;
			templateObject = 'Job';		
		} else if (campaignId != null) {
			Campaign cmp = [select Name from Campaign where Id = :campaignId];
			strJobName = cmp.Name;
			templateObject = 'Campaign';					
			// in the Campaign case, default to Unique Contacts
			fEmailContactsOnlyOnce = true;
		}
		hourStatus = new Volunteer_Hours__c();
		numberOfContacts = 0;
		fLogActivity = true;
		
		// get the folderId for our Volunteer email templates
		list<Folder> listf = [select Id from Folder where DeveloperName='Volunteers_Email_Templates'];
		if (listf.size() > 0) folderId = listf[0].Id;		
	}
		
    // the list of Email Templates
    public list<SelectOption> getlistSOTemplates() {
        list<SelectOption> listSO = new list<SelectOption>();
        listSO.add(new SelectOption('', ''));
        for (EmailTemplate et : [select Id, Name, Subject, Body from EmailTemplate 
        		where isActive=true and FolderId=:folderId order by name limit 999]) {
            listSO.add(new SelectOption(et.id, et.name));
        }       
        return listSO;
    }	
	
    // the list of Org Wide Email Addresses
    public list<SelectOption> getlistSOFromEmails() { 
        list<SelectOption> listSO = new list<SelectOption>();
		User thisUser = [Select Name, Email from User where id = :UserInfo.getUserId()];
        listSO.add(new SelectOption('', thisUser.Name + ' <' + thisUser.Email + '>'));
		for (OrgWideEmailAddress owa : [select id, Address, DisplayName from OrgWideEmailAddress]) {
            listSO.add(new SelectOption(owa.id, owa.DisplayName + ' <' + owa.Address + '>'));
        }       
        return listSO;
    }
	
	public string templateObject { get; set; }
	
	// action method to load up the email template the user has chosen
	public PageReference LoadEmailTemplate() {
		if (templateId != null) {
        	EmailTemplate et = [select Id, Name, Subject, HtmlValue, Body from EmailTemplate where Id = :templateId];
        	strSubject = et.Subject;
        	strMessage = (et.HtmlValue != null) ? et.HtmlValue : et.Body;
		} else {
			strSubject = null;
			strMessage = null;		
		}
		return null;
	}

    // action method called when status picklist has changed.  requery for number of contacts to email.
    public PageReference StatusChanged() {
    	list<Volunteer_Hours__c> listHr;
    	
    	if (shiftId != null) {
    		listHr = [select contact__c from Volunteer_Hours__c where Status__c = :hourStatus.Status__c and Volunteer_Shift__c = :shiftId];    		
    	} else if (jobId != null){
    		listHr = [select contact__c from Volunteer_Hours__c where Status__c = :hourStatus.Status__c and Volunteer_Job__c = :jobId];
    	} else if (campaignId != null) {
    		// Salesforce failed to match our campaignId against the formula field which is text, so use full reference.
    		listHr = [select contact__c from Volunteer_Hours__c where Status__c = :hourStatus.Status__c and Volunteer_Job__r.Campaign__c = :campaignId ];
    	}

		if (fEmailContactsOnlyOnce) {    	
    		set<ID> setContactId = new set<ID>();
    		for (Volunteer_Hours__c hr : listHr) {
    			setContactId.add(hr.contact__c);
    		}
    		NumberOfContacts = setContactId.size();
		} else {
			NumberOfContacts = (listHR != null ? listHr.size() : 0);
		}    		
    	return null;
    }

	// action method to send the email
	public PageReference SendMail() {
	
		// do our validation
		if (numberOfContacts == 0) {		
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, Label.labelMassEmailErrorNoContacts)); 
            return null; 
		}
		
		if (templateId == null) {
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, Label.labelMassEmailErrorNoTemplate)); 
            return null; 			
		}
		
		try {
			list<Messaging.SingleEmailMessage> listEmail = new list<Messaging.SingleEmailMessage>();
			
			
			// cases:
			// specific shift
			// specific job with or without shifts (or mixture)
			// specific campaign, with or without jobs, with or without shifts
			
			string strSoql = 'select Contact__c, Volunteer_Shift__c, Volunteer_Job__c, Volunteer_Job__r.Campaign__c from Volunteer_Hours__c ' + 
		    				' where Status__c = \'' + hourStatus.Status__c + '\' and ' + 
		    					' Contact__r.Email != null ';			
			
			if (shiftId != null) {
				strSoql += ' and Volunteer_Shift__c = :shiftId '; 
			} else if (jobId != null) {
				strSoql += ' and Volunteer_Job__c = :jobId '; 
			} else if (campaignId != null) {
				strSoql += ' and Volunteer_Job__r.Campaign__c = :campaignId '; // don't use formula field which is text!		
			}	
			strSoql += ' order by Start_Date__c ';		
			
			// to keep track of unique contacts
			set<ID> setContactId = new set<ID>();
			
			for (Volunteer_Hours__c hr : database.query(strSoql)) {
				if (!fEmailContactsOnlyOnce || setContactId.add(hr.Contact__c)) {
			    	Messaging.SingleEmailMessage mail = new Messaging.SingleEmailMessage();
					mail.setTargetObjectId(hr.Contact__c);
					
					// the rules for which object (and thus template) to support:
					// if we aren't doing each contact once, then use the shift if we got it, otherwise use the job.
					// if we are doing contacts only once, then always use the job, which seems more useful than the campaign.
					//mail.setWhatId(fEmailContactsOnlyOnce ? hr.Volunteer_Job__c : 
					//	(hr.Volunteer_Shift__c != null ? hr.Volunteer_Shift__c : hr.Volunteer_Job__c));
						
					ID whatId = null;
					if (templateObject == 'Campaign') {
						whatId = hr.Volunteer_Job__r.Campaign__c;
					} else if (templateObject == 'Job') {
						whatId = hr.Volunteer_Job__c;
					} else if (templateObject == 'Shift') {
						whatId = (hr.Volunteer_Shift__c != null ? hr.Volunteer_Shift__c : hr.Volunteer_Job__c);
					} else if (templateObject == 'Hours') {
						whatId = hr.Id;
					}
					mail.setWhatId(whatId);
					
					
					mail.setSaveAsActivity(fLogActivity);
					if (fromEmailId != null) {
						mail.setOrgWideEmailAddressId(fromEmailId);
					}
					mail.setTemplateID(templateId);	
					listEmail.add(mail);
				}					
	    	}								
						
			// send the email and return
			if (listEmail.size() > 0) {
				list<Messaging.SendEmailResult> listSER;
				listSER = Messaging.sendEmail(listEmail, false);
				
				// report problems
				integer cSuccess = 0;
				for (Messaging.SendEmailResult ser : listSER) {
					if (ser.isSuccess()) {
						cSuccess++;
					} else {
						list<Messaging.SendEmailError> listSEE = ser.getErrors();
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
							// special case logging Activity failing
							if (see.getStatusCode() == System.StatusCode.FIELD_INTEGRITY_EXCEPTION && fLogActivity)
								strDetails += ' ' + Label.labelMassEmailVolunteersActivityLoggingError; 

							ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, see.getStatusCode() + ': ' + see.getMessage() + strDetails));
						}
					}
				}
				if (cSuccess > 0) 
					ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.INFO, Label.labelMassEmailVolunteersSuccessCount + ' ' + cSuccess));						
				if (listSER.size() > 0)  
				    return null;  // stay on the page
			}
			return Cancel(); // this will take us back to our calling page.
						
		} catch (exception ex) {
            ApexPages.addMessages(ex);   
            return null;			
		} 
	}
	
    // action method that user wants to close this page
    public PageReference Cancel() {
        ID id;
        if (shiftId != null)
            id = shiftId;
        else if (jobId != null)
            id = jobId;
        else
            id = campaignId;
        PageReference p = new PageReference('/' + id);
        p.setRedirect(true);
        return p;
    }
    
}