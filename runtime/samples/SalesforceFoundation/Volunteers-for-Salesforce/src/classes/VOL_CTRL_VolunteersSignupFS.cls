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

global virtual with sharing class VOL_CTRL_VolunteersSignupFS {
     
    // constructor
    global VOL_CTRL_VolunteersSignupFS() {

		// initial values for potential parameters
    	strLanguage = 'en-us'; 
    	strFileUploadLabel = null;
    	attachment = null;

        // handle optional page parameters (must use string, not ID, to handle null)
        map<string, string> params = ApexPages.currentPage().getParameters();
        string p = params.get('Language');
        if (p != null && p != '') strLanguage = p;
        p = params.get('FileUploadLabel');
        if (p != null && p != '') {
        	strFileUploadLabel = p;
        	attachment = new Attachment();
        }

        // we now get the fields from the fieldset
        listStrFields = VOL_SharedCode.listStrFieldsFromContactFieldSet(Schema.SObjectType.Contact.fieldSets.VolunteersSignupFS);

        // output error page messages for any field that doesn't have visibility correctly set.
        VOL_SharedCode.testObjectFieldVisibility('Contact', listStrFields);
    } 
            
    global Contact contact { 
        get {
            if (contact == null) {
                contact = new Contact();
                // if state & country picklists enabled, we want to copy in any defaults from state/country Code to 
                // the state & country fields, since the picklists cannot be included in the field set.
                if (VOL_StateCountryPicklists.isStateCountryPicklistsEnabled) {
                    VOL_StateCountryPicklists.copyAddressStdSObj(contact, 'Mailing', contact, 'Mailing');
                    VOL_StateCountryPicklists.copyAddressStdSObj(contact, 'Other', contact, 'Other');
                }
            }
            return contact;
        }       
        set; 
    }

    global string strURLtoCSSFile { 
        get { 
            if (strURLtoCSSFile == null) {
                list<Document> listDocs = [SELECT Name, Id From Document WHERE Name = 'VolunteersSignupCSS.css' LIMIT 1 ];
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
    
    // global properties
    global string strLanguage { get; set; }
    global string strFileUploadLabel { get; set; }
    global String strSaveResult { get; set; }    
    global list<string> listStrFields { get; set; } 
    global Attachment attachment { get; set; }   
        
    global virtual PageReference Save() {  
    	try { 
    		// save or update the contact              
	        ID contactId = VOL_SharedCode.CreateOrUpdateContactFS(null, contact, contact.Volunteer_Organization__c, listStrFields, true);
			
			// upload any attachment
			if (contactId != null && attachment != null && attachment.body != null) {
				attachment.ParentId = contactId;
				attachment.Description = strFileUploadLabel + ' ' + system.label.labelFileUploadDescription;
				insert attachment;
				attachment = new Attachment();
			}
	        StrSaveResult = system.label.labelVolunteerSignupThankYou;
	        contact = null; // so fields get reset to null.
        } catch (DMLException ex) {
            //Added check for FIELD_CUSTOM_VALIDATION_EXCEPTION to return user friendly error.
            StrSaveResult = ex.getMessage(); 
            if (StrSaveResult.contains('FIELD_CUSTOM_VALIDATION_EXCEPTION')) {
                StrSaveResult = StrSaveResult.substringBetween('FIELD_CUSTOM_VALIDATION_EXCEPTION, ', ': ');
            }
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.FATAL, strSaveResult));
    	} catch (exception ex) {
    		strSaveResult = ex.getMessage(); 
    		ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.FATAL, strSaveResult)); 
    	}
        return null;
    }

	// this was a testmethod which can't be compiled in this class under api 31.  
	// but because it was marked global, it could not be completely removed.
    global static void CodeCoverageTests() {}

}