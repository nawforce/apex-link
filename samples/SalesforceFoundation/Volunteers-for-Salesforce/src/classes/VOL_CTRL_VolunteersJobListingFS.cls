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

global virtual with sharing class VOL_CTRL_VolunteersJobListingFS {

    // page parameters that can get passed into the page to control its behavior.
    global ID campaignIdFilter { get; set; }
    global ID jobIdFilter { get; set; }
    global ID shiftIdFilter { get; set; }
    global boolean fCalendar { get; set; }
    global boolean fPersonalSite { get; set; }
    global boolean ShowJobName { get; set; }
    global boolean ShowDescription { get; set; }
    global boolean ShowLocationAddress { get; set; }
    global boolean ShowLocationMap { get; set; }
    global boolean ShowLocationInfo { get; set; }
    global boolean ShowSkills { get; set; }
    global boolean ShowShifts { get; set; }
    global boolean ShowCampaignHierarchy { get; set; }
    global boolean ShowNumberAvailable { get; set; }
    global string strLanguage { get; set; }
    global string strDateFormat { get; set; }
    global string strTimeFormat { get; set; }
    global list<string> listStrFields { get; set; } 
    global string strSaveResult { get; set; }
    global string strFileUploadLabel { get; set; }
    global Attachment attachment { get; set; }   
    global string strParams { get; set; }
    global integer nMonthsToShow { get; set; }
    global integer nDaysToShow { get; set; }
    global date dtMonthFilter { get; set; }
    global boolean jobAllShiftsInThePast { get; set; }
    
    
    // contact record to bind the Contact SignUp form to.
    global Contact contact { 
        get {
            if (contact == null) {
                contact = new Contact();
                
                // when used within the Personal Site, we should use the appropriate Contact
                Cookie cId = ApexPages.currentPage().getCookies().get('contactIdPersonalSite');
                if (cId != null) contactIdSignUp = cId.getValue();
                if (contactIdSignUp != null && contactIdSignUp != '') {
                    VOL_SharedCodeAPI25.LoadAndCopyObject(contactIdSignUp, contact, listStrFields);                 
                } else {
                    // if state & country picklists enabled, we want to copy in any defaults from state/country Code to 
                    // the state & country fields, since the picklists cannot be included in the field set.
                    if (VOL_StateCountryPicklists.isStateCountryPicklistsEnabled) {
                        VOL_StateCountryPicklists.copyAddressStdSObj(contact, 'Mailing', contact, 'Mailing');
                        VOL_StateCountryPicklists.copyAddressStdSObj(contact, 'Other', contact, 'Other');
                    }
                }
            }
            return contact;
        }
        set; 
    }
      
    // volunteer hours record, to bind the field set to.
    global Volunteer_Hours__c vhours {
        get {
            if (vhours == null) vhours = new Volunteer_Hours__c(Number_of_Volunteers__c = 1);
            return vhours;      
        }
        set;
    }
    
    // parameters set by the client before calling VolunteerShiftSignUp()   
    global ID jobIdSignUp { get; set; }
    global string shiftIdSignUp { get; set; }
    global string strJobDisplayName { get; set; }
    global string strShiftDisplayName { get; set; }
    
    // return parameters set by VolunteerShiftSignUp()
    // NOTE: while these are actually ID's, we need to make the type string so the client can clear them with null!
    global string contactIdSignUp { get; set; }
    global string volunteerHoursIdSignUp { get; set; }
    
    // the instance specific url to the css that can be modified by the user.
    global string strURLtoCSSFile { 
        get {
            if (fPersonalSite) return null; // just use whatever CSS the Site Template includes.
             
            if (strURLtoCSSFile == null) {
                list<Document> listDocs = [SELECT Name, Id From Document WHERE Name = 'VolunteersJobListingCSS.css' LIMIT 1 ];
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
    
    global string strGoogleMapAPIKey {
    	get {
    		return VOL_SharedCode.VolunteersSettings.Google_Maps_API_Key__c;
    	}
    }  
    
    // constructor
    global VOL_CTRL_VolunteersJobListingFS() {
                
        // set default's for page parameters
        campaignIdFilter = null;
        jobIdFilter = null;
        shiftIdFilter = null;
        fCalendar = false;
        ShowJobName = true;
        ShowDescription = true;
        ShowLocationAddress = true;
        ShowLocationMap = true;
        ShowLocationInfo = true;
        ShowSkills = false;
        ShowShifts = true;
        ShowCampaignHierarchy = false;
		ShowNumberAvailable = false;
        strLanguage = 'en-us'; 
        strDateFormat = 'EEEE M/d/yyyy';
        strTimeFormat = 'h:mm tt';
        nMonthsToShow = 3;
        nDaysToShow = 0;  
        dtMonthFilter = system.today(); // default to current month.
                
        // handle optional parameters (must use string, not ID, to handle null)
        map<string, string> params = ApexPages.currentPage().getParameters();
        string p = params.get('campaignId');
        if (p != null && p != '') campaignIdFilter = p;
        p = params.get('jobId');
        if (p != null && p != '') jobIdFilter = p;
        p = params.get('volunteerShiftId');  // this is the param the JobCalendar page has always been passing.
        if (p != null && p != '') shiftIdFilter = p;
        p = params.get('Calendar');
        if (p == '1') fCalendar = true;
        p = params.get('ShowJobName');
        if (p == '0') ShowJobName = false;
        p = params.get('ShowDescription');
        if (p == '0') ShowDescription = false;
        p = params.get('ShowLocationAddress');
        if (p == '0') ShowLocationAddress = false;
        p = params.get('ShowLocationMap');
        if (p == '0') ShowLocationMap = false;
        p = params.get('ShowLocationInfo');
        if (p == '0') ShowLocationInfo = false;
        p = params.get('ShowSkills');
        if (p == '1') ShowSkills = true;
        p = params.get('ShowShifts');
        if ( p == '0') ShowShifts = false;
        p = params.get('ShowCampaignHierarchy');
        if ( p == '1') ShowCampaignHierarchy = true;
        p = params.get('ShowNumberAvailable');
        if ( p == '1') ShowNumberAvailable = true;
        p = params.get('Language');
        if (p != null && p != '') strLanguage = p;
        p = params.get('DateFormat');
        if (p != null && p != '') strDateFormat = p;
        p = params.get('TimeFormat');
        if (p != null && p != '') strTimeFormat = p;
        p = params.get('nMonthsToShow');
        if (p != null && p != '') nMonthsToShow = integer.valueOf(p);
        p = params.get('nDaysToShow');
        if (p != null && p != '') nDaysToShow = integer.valueOf(p);
        p = params.get('dtMonthFilter');
        if (p != null && p != '') dtMonthFilter = date.valueOf(p);
        p = params.get('FileUploadLabel');
        if (p != null && p != '') {
            strFileUploadLabel = p;
            attachment = new Attachment();
        }

        // we can derive whether we are within the Personal Site or not, so no need for parameter.
        fPersonalSite = ApexPages.currentPage().getUrl().contains('PersonalSite');
        
        // finally, keep all parameters for passing back to JobCalendar
        strParams = ApexPages.currentPage().getUrl();
        integer iparams = strParams.indexOf('?');
        if (iparams >= 0){
            strParams = strParams.substring(iparams+1);
        } else {
            strParams = '';
        }       
                
        // we now get the fields from the fieldset
        listStrFields = VOL_SharedCode.listStrFieldsFromContactFieldSet(Schema.SObjectType.Contact.fieldSets.VolunteersJobListingFS);
        
        // output error page messages for any field that doesn't have visibility correctly set.
        VOL_SharedCode.testObjectFieldVisibility('Contact', listStrFields);
        VOL_SharedCode.testObjectFieldVisibility(VOL_SharedCode.StrTokenNSPrefix('Volunteer_Job__c'), 
        	new list<string>{'Name','Description__c','Campaign__c','Location_Information__c','Number_of_Shifts__c','Skills_Needed__c',
        					'Volunteer_Website_Time_Zone__c','Location_Street__c','First_Shift__c'});
        VOL_SharedCode.testObjectFieldVisibility(VOL_SharedCode.StrTokenNSPrefix('Volunteer_Shift__c'), 
        	new list<string>{'Start_Date_Time__c','Duration__c','Number_of_Volunteers_Still_Needed__c','Description__c','System_Note__c'});
       	VOL_SharedCode.testObjectFieldVisibility(VOL_SharedCode.StrTokenNSPrefix('Volunteer_Hours__c'), 
       		VOL_SharedCode.listStrFieldsFromFieldSet(Schema.SObjectType.Volunteer_Hours__c.FieldSets.VolunteersJobListingFS));
        
        contactIdSignUp = null;
        volunteerHoursIdSignUp = null;        
    }

    // The list of Volunteer Jobs.
    global list<Volunteer_Job__c> listVolunteerJobs { 
        get {
            if (listVolunteerJobs == null) {
                        
                listVolunteerJobs = new list<Volunteer_Job__c>();
                Date dateNow = dtMonthFilter;
                if (nDaysToShow == 0) {
                	dateNow = dateNow.toStartOfMonth();
                	if (dateNow < system.Today()) dateNow = system.Today();
                }
                DateTime dtNow = dateNow;
                
                // NOTE: the following GMT date mappings are to help improve
                // our filtering for the correct shifts.  Unfortunately, the 
                // best way of doing this would be based on the specific timezone 
                // specified for each Campaign or Job, but there is no way to do that
                // within a single query.  So we will live with optimizing our filtering
                // based off the Site's Guest User timezone.
                TimeZone tz = UserInfo.getTimeZone();
                
                // map dtNow to GMT based on the Site User, to avoid getting
                // days that are from the last day of the previous month locally,
                // but are in this month GMT.
                dtNow = VOL_SharedCode.dtGmtFromDtTimeZone(dtNow, tz);

                // map dtLast to GMT based on the Site User, to include
                // days that are from the last day of this month locally,
                // but are in the next month GMT.
                DateTime dtLast;
                if (nDaysToShow > 0) 
                	dtLast = VOL_SharedCode.dtGmtFromDtTimeZone(dateNow.addDays(nDaysToShow), tz);
                else
                	dtLast = VOL_SharedCode.dtGmtFromDtTimeZone(dateNow.addMonths(nMonthsToShow).toStartOfMonth(), tz);                
                
                if (shiftIdFilter != null && nMonthsToShow == 0) {
                    listVolunteerJobs = [select Id, Name, Campaign__c, Campaign__r.IsActive, Campaign__r.Name, Campaign__r.StartDate, Campaign__r.Volunteer_Website_Time_Zone__c,
                        Description__c, Location_Information__c, Number_of_Shifts__c, Skills_Needed__c, Volunteer_Website_Time_Zone__c,
                        Location_Street__c, Location_City__c, Location__c, Location_Zip_Postal_Code__c, External_Signup_Url__c, First_Shift__c,
                        (Select Id, Start_Date_Time__c, Duration__c, Number_of_Volunteers_Still_Needed__c, Total_Volunteers__c,
                            Description__c, System_Note__c From Volunteer_Job_Slots__r 
                            where Id = :shiftIdFilter) 
                        from Volunteer_Job__c where Id = :jobIdFilter  
                        order by First_Shift__c, Campaign__r.StartDate, Campaign__r.Name, Name];
                } else if (jobIdFilter != null) {
                    listVolunteerJobs = [select Id, Name, Campaign__c, Campaign__r.IsActive, Campaign__r.Name, Campaign__r.StartDate, Campaign__r.Volunteer_Website_Time_Zone__c,
                        Description__c, Location_Information__c, Number_of_Shifts__c, Skills_Needed__c, Volunteer_Website_Time_Zone__c,
                        Location_Street__c, Location_City__c, Location__c, Location_Zip_Postal_Code__c, External_Signup_Url__c, First_Shift__c,
                        (Select Id, Start_Date_Time__c, Duration__c, Number_of_Volunteers_Still_Needed__c, Total_Volunteers__c,
                            Description__c, System_Note__c From Volunteer_Job_Slots__r 
                            where Start_Date_Time__c >= :dtNow and Start_Date_Time__c < :dtLast
                            order by Start_Date_Time__c) 
                        from Volunteer_Job__c where Id = :jobIdFilter  
                        order by First_Shift__c, Campaign__r.StartDate, Campaign__r.Name, Name];
                } else if (campaignIdFilter != null) {
                    List<Id> listCampaignIds = new List<Id> {campaignIdFilter};
                    if (ShowCampaignHierarchy) {
                        listCampaignIds = VOL_SharedCode.listIdsCampaignsInHierarchy(campaignIdFilter);    
                    }
                    listVolunteerJobs = [select Id, Name, Campaign__c, Campaign__r.IsActive, Campaign__r.Name, Campaign__r.StartDate, Campaign__r.Volunteer_Website_Time_Zone__c, 
                        Description__c, Location_Information__c, Number_of_Shifts__c, Skills_Needed__c, Volunteer_Website_Time_Zone__c,
                        Location_Street__c, Location_City__c, Location__c, Location_Zip_Postal_Code__c, External_Signup_Url__c, First_Shift__c,
                        (Select Id, Start_Date_Time__c, Duration__c, Number_of_Volunteers_Still_Needed__c, Total_Volunteers__c,
                            Description__c, System_Note__c From Volunteer_Job_Slots__r 
                            where Start_Date_Time__c >= :dtNow and Start_Date_Time__c < :dtLast
                            order by Start_Date_Time__c) 
                        from Volunteer_Job__c where Campaign__c IN :listCampaignIds and Display_on_Website__c = true 
                        order by First_Shift__c, Campaign__r.StartDate, Campaign__r.Name, Name];
                } else {
                    listVolunteerJobs = [select Id, Name, Campaign__c, Campaign__r.IsActive, Campaign__r.Name, Campaign__r.StartDate, Campaign__r.Volunteer_Website_Time_Zone__c,
                        Description__c, Location_Information__c, Number_of_Shifts__c, Skills_Needed__c, Volunteer_Website_Time_Zone__c,
                        Location_Street__c, Location_City__c, Location__c, Location_Zip_Postal_Code__c, External_Signup_Url__c, First_Shift__c,
                        (Select Id, Start_Date_Time__c, Duration__c, Number_of_Volunteers_Still_Needed__c, Total_Volunteers__c,
                            Description__c, System_Note__c From Volunteer_Job_Slots__r 
                            where Start_Date_Time__c >= :dtNow and Start_Date_Time__c < :dtLast
                            order by Start_Date_Time__c LIMIT 100) 
                        from Volunteer_Job__c where Campaign__r.IsActive = true and Display_on_Website__c = true 
                        order by First_Shift__c, Campaign__r.StartDate, Campaign__r.Name, Name LIMIT 999];
                }
                
                // now remove any jobs whose shifts are all past date.
                // unless the user links directly to the job
                if (shiftIdFilter == null && jobIdFilter == null) {
                	for (integer i = listVolunteerJobs.size() - 1; i >= 0; i--) {
	                    Volunteer_Job__c job = listVolunteerJobs[i];
	                    if (job.Number_of_Shifts__c > 0 && job.Volunteer_Job_Slots__r.size() == 0) {
	                        listVolunteerJobs.remove(i);                    
	                    }
	                }
                }
                
                // if one event was selected; check if the event was in the past
                jobAllShiftsInThePast = false;
                if (listVolunteerJobs.size() == 1) {
                	Volunteer_Job__c job = listVolunteerJobs[0];
                	jobAllShiftsInThePast = 0 < job.Number_of_Shifts__c;
                	for (Volunteer_Shift__c shift : job.Volunteer_Job_Slots__r) {
                		if (date.today() <= shift.Start_Date_Time__c) {
                			jobAllShiftsInThePast = false;
                			break;
                		}
                	}
                }
              
            }
            
            VOL_SharedCode.dateTimeFixup(listVolunteerJobs, strDateFormat, strTimeFormat);
            return sortVolunteerJobs(listVolunteerJobs);
        }
        
        set;
    }
        
    global virtual list<Volunteer_Job__c> sortVolunteerJobs (list<Volunteer_Job__c> jobsList) {
        list<Volunteer_Job__c> resultList = new list<Volunteer_Job__c>();
        DateTime faroutFuture = System.Now().addYears(100);
        
        //create a map of earliest shift datetime to job list
        map<DateTime, list<Volunteer_Job__c>> jobMap = new map<DateTime, list<Volunteer_Job__c>>();
        
        for (Volunteer_Job__c vj : jobsList) {
            DateTime nextShift = faroutFuture;
            
            //if the job has no shifts, set it in the past so it appears first on the page, otherwise get the first shift
            if (vj.Volunteer_Job_Slots__r.size() == 0) {
                nextShift = faroutFuture.addYears(-200);
            } else if (vj.Volunteer_Job_Slots__r.size() > 0) {
                for (Volunteer_Shift__c vs : vj.Volunteer_Job_Slots__r) {
                    if (vs.Start_Date_Time__c < nextshift) {
                        nextShift = vs.Start_Date_Time__c;
                    }
                }
            }
            
            //if our map doesn't contain this shift, create a new list, otherwise add to the list
            if (jobMap.get(nextShift) == null) {
                jobMap.put(nextShift, new List<Volunteer_Job__c>());
            }
            jobMap.get(nextShift).add(vj);
        }
        
        //use SF's standard sort method on the datetimes
        list<DateTime> keys = new list<DateTime>(jobMap.keySet());
        keys.sort();
        
        //rebuild list in order
        for (DateTime key:keys) {
            resultList.addAll(jobMap.get(key));
        }
        return resultlist;
    }
    
    global virtual boolean getVolunteersHasJobs() {
        return (listVolunteerJobs.size() > 0);
    }
    
    // action method to allow the javascript to clear this variable.
    // used when the confirmation dialog is closed.
    global virtual PageReference ClearVolunteerHoursIdSignUp() {
        volunteerHoursIdSignUp = null;
        return null;
    }
    
    // action method to cancel the signup dialog,
    // clearing any cached state.
    global virtual PageReference CancelSignUp() {
        volunteerHoursIdSignUp = null;
        strSaveResult = null;
        return null;
    }
    // just before we insert the new Volunteer Hours record,
    // give clients a chance to set any additional information they want.
    global virtual void VolunteerHoursBeforeInsert(Volunteer_Hours__c vh) {
        
    }   
    
    private class MyException extends Exception {}
    
    global virtual PageReference VolunteerShiftSignUp() {
        try {               
            // clear return variables in case we fail.
            contactIdSignUp = null;
            volunteerHoursIdSignUp = null;
            strSaveResult = null;
            Date dtStart = system.today();
            decimal duration = null; 
            
            // do any validation we weren't able to do directly on the client.
            // also find the shift's start date.
            if (shiftIdSignUp != null && shiftIdSignUp != '') {
                // make sure we don't go over the number of volunteers still needed on the shift.
                list<Volunteer_Shift__c> listShift = [select Number_of_Volunteers_Still_Needed__c, Start_Date_Time__c, Duration__c from Volunteer_Shift__c where Id = :shiftIdSignUp];
                if (listShift != null) {
                    if (vhours.Number_of_Volunteers__c > listShift[0].Number_of_Volunteers_Still_Needed__c) {
                        string strError = String.Format(system.label.labelVolunteersJobListingErrorTooManyVols, 
                            new string[] { string.valueOf(listShift[0].Number_of_Volunteers_Still_Needed__c) }); 
                        throw (new MyException(strError));                  
                    }
                    dtStart = listShift[0].Start_Date_Time__c.date();
                    duration = listShift[0].Duration__c;
                }
            }
                        
            // when used within the Personal Site, we should use the appropriate Contact
            Cookie cId = ApexPages.currentPage().getCookies().get('contactIdPersonalSite');
            ID contactIdPersonalSite = null;
            if (cId != null) contactIdPersonalSite = cId.getValue();

            // save or update the contact              
            contactIdSignUp = VOL_SharedCode.CreateOrUpdateContactFS(contactIdPersonalSite, contact, contact.Volunteer_Organization__c, listStrFields, true);

            // upload any attachment
            if (contactIdSignUp != null && attachment != null && attachment.body != null) {
                attachment.ParentId = contactIdSignUp;
                attachment.Description = strFileUploadLabel + ' ' + system.label.labelFileUploadDescription;
                insert attachment;
                attachment = new Attachment();
            }
                        
            // then Update or Create hours if it was successful.
            if (contactIdSignUp != null) {              
                // if signing up for a specific shift, see if they already have an Hours record.
                list<Volunteer_Hours__c> listVHours = new list<Volunteer_Hours__c>();
                if (shiftIdSignUp != null && shiftIdSignUp != '') {
                    string strSoql = 'select ';
                    string strComma = '';
                    for (string strF : listStrFieldsVHours) {
                        strSoql += strComma + strF;
                        strComma = ', ';
                    }
                    strSoql += ' from ' + VOL_SharedCode.StrTokenNSPrefix('Volunteer_Hours__c');
                    strSoql += ' where ' + VOL_SharedCode.StrTokenNSPrefix('Contact__c') + ' = :contactIdSignUp ';
                    strSoql += ' and ' + VOL_SharedCode.StrTokenNSPrefix('Volunteer_Job__c') + ' = :jobIdSignUp ';
                    strSoql += ' and ' + VOL_SharedCode.StrTokenNSPrefix('Volunteer_Shift__c') + ' = :shiftIdSignUp ';             
                    strSoql += ' limit 1';  
                    listVHours = Database.Query(strSoql);               
                }
                
                // if existing Hours found, use the first one.
                if (listVHours.size() > 0) {      
                    //  now copy over all the non-null fields from the form's Hours to the existing Hours.
                    for (string strF : listStrFieldsVHours) {
                        if (vhours.get(strF) != null) {
                            try {
                                listVHours[0].put(strF, vhours.get(strF));
                            } catch(exception ex) { 
                                // ignore and continue;
                            }
                        }
                    }
                    // update the  status if not already Confirmed or Completed
                    if (listVHours[0].Status__c != 'Confirmed' && listVHours[0].Status__c != 'Completed')
                    	listVHours[0].Status__c = 'Web Sign Up';
                    	
                    VolunteerHoursBeforeInsert(listVHours[0]);
                    update listVHours[0];
                    volunteerHoursIdSignUp = listVHours[0].Id;
                } else {    
                    vhours.Contact__c = contactIdSignUp;
                    vhours.Volunteer_Job__c = jobIdSignUp;
                    if (shiftIdSignUp != null && shiftIdSignUp != '') { vhours.Volunteer_Shift__c = shiftIdSignUp; }
                    vhours.Status__c = 'Web Sign Up';
                    vhours.Start_Date__c = dtStart;
                    vhours.Hours_Worked__c = duration;
                    VolunteerHoursBeforeInsert(vhours);
                    insert vhours;
                    volunteerHoursIdSignUp = vhours.Id;
                }
                
                // force recreate new hours record to use for next submit.
                vhours = null;
            }
        } catch (DMLException ex) {
            //Added check for FIELD_CUSTOM_VALIDATION_EXCEPTION to return user friendly error.
            strSaveResult = ex.getMessage(); 
            if (strSaveResult.contains('FIELD_CUSTOM_VALIDATION_EXCEPTION')) {
                strSaveResult = strSaveResult.substringBetween('FIELD_CUSTOM_VALIDATION_EXCEPTION, ', ': ');
            }
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.FATAL, strSaveResult));
        } catch (exception ex) {
            strSaveResult = ex.getMessage(); 
            //StrSaveResult += ' stack: ' + ex.getStackTraceString();
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.FATAL, strSaveResult));         
        }
        return null; 
    }

    private list<string> listStrFieldsVHours {
        get {
            if (listStrFieldsVHours == null) {
                // initialize the list of all fields on Volunteer Hours.
                Map<String, Schema.SObjectField> mapS = Schema.SObjectType.Volunteer_Hours__c.fields.getMap();
                listStrFieldsVHours = new list<string>();
                listStrFieldsVHours.addAll(mapS.keySet());                      
            }
            return listStrFieldsVHours;
        }
        set;
    }

	// this was a testmethod which can't be compiled in this class under api 31.  
	// but because it was marked global, it could not be completely removed.
    global static void UnitTest1() {}
    
}