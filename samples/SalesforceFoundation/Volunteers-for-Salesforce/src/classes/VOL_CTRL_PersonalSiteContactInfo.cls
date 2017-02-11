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

global with sharing class VOL_CTRL_PersonalSiteContactInfo {
	
	global VOL_CTRL_PersonalSiteContactInfo() {

		// set default property values
		cRowsCompleted = 10;
		cRowsUpcoming = 10;
    	strDateFormat = 'E M/d/yy';
    	strChartDateFormat = 'MMM yyyy';
    	strTimeFormat = 'h:mm tt';
    	strLanguage = 'en-us'; 
    	isEditing = false;

        map<string, string> params = ApexPages.currentPage().getParameters();
        string p;
        p = params.get('Language');
        if (p != null && p != '') strLanguage = p;
        p = params.get('DateFormat');
        if (p != null && p != '') strDateFormat = p;
        p = params.get('ChartDateFormat');
        if (p != null && p != '') strChartDateFormat = p;
        p = params.get('TimeFormat');
        if (p != null && p != '') strTimeFormat = p;
        
        p = params.get('contactId');
        if (p != null && p != '') {
        	try {
        		contactId = p;
        	} catch (Exception ex) {
        		contactId = null;
        	}
        	
        	// now get new email parameter for extra validation
        	// note that the validation routine only uses it if the setting for this extra validation is set
        	if (!VOL_SharedCode.isValidContactIdAndEmail(contactId, params.get('Email')))
        		contactId = null;        	
        	
        	// cache the contactId for all pages to use.
			Cookie cId = new cookie('contactIdPersonalSite', contactId, null, -1, false);
        	ApexPages.currentPage().setCookies(new Cookie[] {cId});
        } else {
	        // check for cached Id
	        Cookie cId = ApexPages.currentPage().getCookies().get('contactIdPersonalSite');
	        if (cId != null) contactId = cId.getValue();        
        }
        
		// if we still don't know who we are editing, need to bail out.
        if (contactId == null) {    	
    		ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.FATAL,'Please pass in a valid contactId and Email with the URL.')); 	
        	return;
        }

		/**********
        // let's see if we can find any matching Contacts.
        // we need to use dynamic soql, since we allow the user to modify the FieldSet of fields to edit.
        string strSoql = 'select ';
        string strComma = '';
        for (string strF : listStrContactFields) {
        	strSoql += strComma + strF;
        	strComma = ', ';
        }
        strSoql += ' from Contact where Id=:contactId';
        strSoql += ' limit 1';      
        listCon = Database.Query(strSoql); 
        
        if (listCon.size() > 0) {        	
        	Contact con = listCon[0];
        	contactId = con.Id;
            //  now copy over all the non-null fields from the form's contact to the existing contact.
            for (string strF : listStrContactFields) {
            	if (con.get(strF) != null) {
            		try {
            			contactEdit.put(strF, con.get(strF));
            		} catch(exception ex) {
            			
            		}
            	}
            }
            // also maintain a readonly version of the contact to display rollup summary fields.
            contactReadOnly = con;
        }
        ********/
        list<String> listStrFieldsPanel1FS = VOL_SharedCode.listStrFieldsFromFieldSet(Schema.SObjectType.Contact.FieldSets.PersonalSiteContactInfoPanel1FS);
        list<String> listStrFieldsPanel2FS = VOL_SharedCode.listStrFieldsFromFieldSet(Schema.SObjectType.Contact.FieldSets.PersonalSiteContactInfoPanel2FS);
        list<String> listStrFieldsExtraFS = VOL_SharedCode.listStrFieldsFromFieldSet(Schema.SObjectType.Contact.FieldSets.PersonalSiteContactInfoExtraFS);

        Set<String> setStrFields = new Set<String>();
        setStrFields.addAll(listStrFieldsPanel1FS);
        setStrFields.addAll(listStrFieldsPanel2FS);
        setStrFields.addAll(listStrFieldsExtraFS);
        contactReadOnly = (Contact)VOL_SharedCodeAPI25.LoadAndCopyObject(contactId, contactEdit, new List<String>(setStrFields));                 
        
        
        // output error page messages for any field that doesn't have visibility correctly set.
       	VOL_SharedCode.testObjectFieldVisibility('Contact', listStrFieldsPanel1FS); 
       	VOL_SharedCode.testObjectFieldVisibility('Contact', listStrFieldsPanel2FS);
       	VOL_SharedCode.testObjectFieldVisibility('Contact', listStrFieldsExtraFS);
       	VOL_SharedCode.testObjectFieldVisibility(VOL_SharedCode.StrTokenNSPrefix('Volunteer_Hours__c'), 
       		VOL_SharedCode.listStrFieldsFromFieldSet(Schema.SObjectType.Volunteer_Hours__c.FieldSets.PersonalSiteContactInfoUpcomingShifts));
       	VOL_SharedCode.testObjectFieldVisibility(VOL_SharedCode.StrTokenNSPrefix('Volunteer_Hours__c'), 
       		VOL_SharedCode.listStrFieldsFromFieldSet(Schema.SObjectType.Volunteer_Hours__c.FieldSets.PersonalSiteContactInfoHistory));
        
	}

    global Contact contactEdit { 
        get {
            if (contactEdit == null) contactEdit = new Contact();
            return contactEdit;
        }       
        set; 
    }
    
    global Contact contactReadOnly { 
        get {
            //if (contactReadOnly == null) contactReadOnly = new Contact();
            return contactReadOnly;
        }       
        set; 
    }

    global ID contactId { get; set; }
    global boolean isEditing { get; set; }
    global ID hoursId { get; set; }	// to hold the ID of the hours record an action is being performed on.
    //public list<Contact> listCon { get; set; }	// to hold the loaded contact record
    global integer cRowsCompleted { get; set; }
    global integer cRowsUpcoming { get; set; }
    global string strDateFormat { get; set; }
    global string strChartDateFormat { get; set; }
 	global string strTimeFormat { get; set; }
 	global string strLanguage { get; set; }
 	
    public list<string> listStrContactFields { 
    	get {
    		if (listStrContactFields == null) {
		        list<string> listStrFields1 = VOL_SharedCode.listStrFieldsFromContactFieldSet(Schema.SObjectType.Contact.fieldSets.PersonalSiteContactInfoPanel1FS);
		        list<string> listStrFields2 = VOL_SharedCode.listStrFieldsFromContactFieldSet(Schema.SObjectType.Contact.fieldSets.PersonalSiteContactInfoPanel2FS);
				// because we used to get all Contact fields, we need a way for people extending the page to tell us additional fields to query for.
		        list<string> listStrFields3 = VOL_SharedCode.listStrFieldsFromContactFieldSet(Schema.SObjectType.Contact.fieldSets.PersonalSiteContactInfoExtraFS);

		        set<string> setStrFields = new set<string>();
		        setStrFields.addAll(listStrFields1);
		        setStrFields.addAll(listStrFields2);
		        setStrFields.addAll(listStrFields3);
		        listStrContactFields = new list<string>();
	        	listStrContactFields.addAll(setStrFields);
    		}
    		return listStrContactFields;
    	}
    	set;
    }
        
    public list<string> listStrHoursFields { 
    	get {
    		if (listStrHoursFields == null) {
				Map<String, Schema.SObjectField> mapS = Schema.SObjectType.Volunteer_Hours__c.fields.getMap();
				listStrHoursFields = new list<string>();
				listStrHoursFields.addAll(mapS.keySet());    		    			
    		}
    		return listStrHoursFields;
    	}
    	set;
    }
       
    // The list of Completed Volunteer Hours.
    global list<Volunteer_Hours__c> listCompletedVolunteerHours {
        get {
            if (listCompletedVolunteerHours == null) {
		        string strSoql = 'select Volunteer_Job__r.Name, Volunteer_Job__r.Volunteer_Website_Time_Zone__c, ' +
		        	' Volunteer_Job__r.Campaign__r.Volunteer_Website_Time_Zone__c, Volunteer_Shift__r.Duration__c, ';
		        string strComma = '';
		        for (string strF : listStrHoursFields) {
		        	strSoql += strComma + strF;
		        	strComma = ', ';
		        }
		        strSoql += ' from Volunteer_Hours__c where Contact__c = :contactId ';
		        strSoql += ' and Status__c = \'Completed\' ';
		        strSoql += ' order by Start_Date__c DESC ';
		        strSoql += ' limit ' + cRowsCompleted;
		        listCompletedVolunteerHours = Database.Query(strSoql); 
		        // store friendly datetime string in system field for display only
		        dateTimeFixup(listCompletedVolunteerHours);
            }
            return listCompletedVolunteerHours;
        }        
        set;
    }
    
    // does this Volunteer have any completed hours?
    global boolean hasCompletedHours {
    	get {
    		return listCompletedVolunteerHours.size() > 0;
    	}
    }
    
    // The list of Upcoming Volunteer Hours.
    global list<Volunteer_Hours__c> listUpcomingVolunteerHours {
        get {
            if (listUpcomingVolunteerHours == null) {
		        string strSoql = 'select Volunteer_Job__r.Name, Volunteer_Job__r.Volunteer_Website_Time_Zone__c, ' +
		        	' Volunteer_Job__r.Campaign__r.Volunteer_Website_Time_Zone__c, Volunteer_Shift__r.Duration__c, ';
		        string strComma = '';
		        for (string strF : listStrHoursFields) {
		        	strSoql += strComma + strF;
		        	strComma = ', ';
		        }
		        strSoql += ' from Volunteer_Hours__c where Contact__c = :contactId ';
		        strSoql += ' and Status__c <> \'Canceled\' and Status__c <>  \'Completed\' ';
		        Date dtToday = system.today();
		        strSoql += ' and Shift_Start_Date_Time__c >= :dtToday ';
		        strSoql += ' order by Shift_Start_Date_Time__c ASC ';        
		        strSoql += ' limit ' + cRowsUpcoming;
		        listUpcomingVolunteerHours = Database.Query(strSoql); 		        
		        // store friendly datetime string in system field for display only
		        dateTimeFixup(listUpcomingVolunteerHours);
            }
            return listUpcomingVolunteerHours;
        }        
        set;
    }  
    
    // routine to go through all the hours, and create the display string
    // for the shifts start time - end date & time, using the appropriate
    // time zone that might be specified on the Job, Campaign, or Site Guest User.
    // Note that it stores the formatted Time in the Hours' System_Note__c field (in memory only).
    // Note that it stores the formatted Date in the Hours' Comment field (in memory only).
    private void dateTimeFixup(list<Volunteer_Hours__c> listHours) {
    	
    	// get default time zone for site guest user
    	User u = [Select TimeZoneSidKey From User where id =: Userinfo.getUserId()];
    	
    	// javascript formatting used 'tt' for am/pm, whereas apex formatting uses 'a'.
    	string strFormat = strDateFormat + ' ' + strTimeFormat.replace('tt','a');
    	string strFormatEndTime = strTimeFormat.replace('tt','a');
    	
    	for (Volunteer_Hours__c hr : listHours) {
			string strTimeZone = hr.Volunteer_Job__r.Volunteer_Website_Time_Zone__c;
			if (strTimeZone == null) strTimeZone = hr.Volunteer_Job__r.Campaign__r.Volunteer_Website_Time_Zone__c;
			if (strTimeZone == null) strTimeZone = u.TimeZoneSidKey;
			
			DateTime dtStart = hr.Planned_Start_Date_Time__c == null ? hr.Shift_Start_Date_Time__c : hr.Planned_Start_Date_Time__c;
			if (dtStart == null) {
				// if we don't have a start time, then format by GMT, to avoid timezones moving the date!
				dtStart = hr.Start_Date__c;
				hr.Comments__c = dtStart.formatGmt(strDateFormat);
			} else {
				double duration = hr.Hours_Worked__c == null ? hr.Volunteer_Shift__r.Duration__c : hr.Hours_Worked__c;
				DateTime dtEnd = dtStart.addMinutes(integer.valueOf(duration * 60));
				string strStart = dtStart.format(strFormat, strTimeZone);
				
				// see if start and end are on the same day
				if (dtStart.format('d', strTimeZone) == dtEnd.format('d', strTimeZone)) {
					hr.System_Note__c =  dtStart.format(strFormatEndTime, strTimeZone) + ' - ' + dtEnd.format(strFormatEndTime, strTimeZone);	
				} else {
					hr.System_Note__c =  dtStart.format(strFormatEndTime, strTimeZone) + ' - ' + dtEnd.format(strFormat, strTimeZone);	    				
				} 
				
				// also save user formated Start Date in Comments field
				hr.Comments__c = dtStart.format(strDateFormat, strTimeZone);
			}  			
    	}
    }
        
	global PageReference edit() {
		isEditing = true;    
		return null;
	}

	global PageReference save() {  
        if (contactReadOnly != null) {        	
        	//Contact con = listCon[0];       	
            //  now copy over all the non-null fields from the form's contact to the existing contact.
            for (string strF : listStrContactFields) {
            	if (contactEdit.get(strF) != null) {
            		try {
            			contactReadOnly.put(strF, contactEdit.get(strF));
            		} catch(exception ex) {
            			
            		}
            	}
            }            
            // save the changes
            update contactReadOnly;
        }
		isEditing = false;
		return null;
	}
	
	global PageReference cancel() {
		isEditing = false;
		return null;
	}
	   
	global PageReference cancelShift() {
		if (hoursId != null) {           
			Volunteer_Hours__c hr = [select Id, Status__c, Hours_Worked__c from Volunteer_Hours__c where Id = :hoursId];
			hr.Status__c = 'Canceled';
			hr.Hours_Worked__c = 0;
			update hr;
			hoursId = null; 
			listUpcomingVolunteerHours = null; // to force it to be refreshed.
		}
		return null;
	}
	
	global PageReference showMoreRowsCompleted() {
		cRowsCompleted += 10;
		listCompletedVolunteerHours = null;
		return null;
	}

	global PageReference showMoreRowsUpcoming() {
		cRowsUpcoming += 10;
		listUpcomingVolunteerHours = null;
		return null;
	}
	
	// global method to get the list of ChartData items to run the visualforce chart off of.
	global list<ChartData> getChartData() {
		integer cMonths = 12;
		Date dtStart = date.today().addMonths(-cMonths + 1).toStartOfMonth();
		
		list<AggregateResult> listAG = [select CALENDAR_YEAR(Start_Date__c) theYear, CALENDAR_MONTH(Start_Date__c) theMonth, SUM(Hours_Worked__c) sumHours 
		    from Volunteer_Hours__c 
		    where Contact__c = :contactId and Status__c = 'Completed' and Start_Date__c >= :dtStart
      		group by CALENDAR_YEAR(Start_Date__c), CALENDAR_MONTH(Start_Date__c) 
      		order by CALENDAR_YEAR(Start_Date__c), CALENDAR_MONTH(Start_Date__c) ];
      	
      	list<ChartData> listCD = new list<ChartData>();
      	
      	Date dtNext = dtStart;
      	Time timeT = Time.newInstance(1, 0, 0, 0);
      	for (AggregateResult ag : listAG) {
      		Date dt = date.newInstance(integer.valueOf(ag.get('theYear')), integer.valueOf(ag.get('theMonth')), 1);
      		
      		// handle months with no data
      		while (dtNext < dt) {
	      		listCD.add(new ChartData(datetime.newInstance(dtNext,timeT).format(strChartDateFormat), 0));  	
	      		dtNext = dtNext.addMonths(1);  		
      		}
      		
      		listCD.add(new ChartData(datetime.newInstance(dt,timeT).format(strChartDateFormat), integer.valueOf(ag.get('sumHours'))));
      		dtNext = dt.addMonths(1);
      	}
      	return listCD;
	}
	
	// our wrapper class to hold chart data for each of the last N months
	global class ChartData {		
		public string strDateLabel { get; set; }
		public integer sumHours { get; set; }		
		public ChartData (string strDateLabel, integer sumHours) {
			this.strDateLabel = strDateLabel;
			this.sumHours = sumHours;			
		}
	}
	
	global string strRank {
		get {
			if (strRank == null) {
				try {
					strRank = strRankLast12Months();
				} catch(exception ex) {
					strRank = strRankLifetime();
				}
			}
			return strRank;
		}
		private set;
	}

	private string strRankLast12Months() {
		integer cMonths = 12;
		Date dtStart = date.today().addMonths(-cMonths + 1).toStartOfMonth();
      	integer iVol = 0;
      	integer iCurrent = 0;

		for (list<AggregateResult> listAG : [select Contact__c cId, SUM(Hours_Worked__c) sumHours 
		    from Volunteer_Hours__c 
		    where Status__c = 'Completed' and Start_Date__c >= :dtStart
      		group by Contact__c 
      		having SUM(Hours_Worked__c) > 0 
      		order by SUM(Hours_Worked__c) desc ]) {
      	
		      	for (AggregateResult ag : listAG) {
		      		if (ag.get('cId') == contactId) {
		      			iCurrent = iVol;
		      		}
		      		iVol++;
		      	}
      	}
      	if (iVol > 2) {
      		integer irank = integer.valueOf(100 * (decimal.valueOf(iCurrent)/decimal.valueOf(iVol - 1)));
      		if (irank == 0) irank = 1;
      		return string.format(label.labelContactInfoRankText12Months, new string[]{irank + '%'});
      	} else {
      		return '';
      	}			
	}
	
	private string strRankLifetime() {
      	integer iVol = 0;
      	integer iCurrent = 0;
		for (list<Contact> listCon : [select Id, Volunteer_Hours__c
			from Contact where Volunteer_Hours__c > 0 order by Volunteer_Hours__c desc]) {
				
		      	for (Contact con : listCon) {
		      		if (con.Id == contactId) {
		      			iCurrent = iVol;
		      		}
		      		iVol++;
		      	}
		}
      	if (iVol > 2) {
      		integer irank = integer.valueOf(100 * (decimal.valueOf(iCurrent)/decimal.valueOf(iVol - 1)));
      		if (irank == 0) irank = 1;
      		return string.format(label.labelContactInfoRankTextLifetime, new string[]{irank + '%'});
      	} else {
      		return '';	
      	}			
	}	
}