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
/*******************************************************************************************************
* @author David Habib
* @date 7/1/2011
* @description Page Controller class for the Job Calendar visualforce page.  Provides Javascript remoting
* method for getting Job Shift data. 
********************************************************************************************************/
global with sharing class VOL_CTRL_JobCalendar {

    // constructor
    global VOL_CTRL_JobCalendar() {
        namespacePrefix = VOL_SharedCode.getNamespace();
        // handle optional parameters (must use string, not ID, to handle null)
        
        // if Campaign passed in, set current state
        map<string, string> params = ApexPages.currentPage().getParameters();
        string p = params.get('campaignId');
        if (p != null && p != '') {
        	list<Campaign> listCampaign = [select Id, StartDate from Campaign where Id = :p];
        	if (listCampaign.size() > 0) {
        		initialDate = Date.valueOf(listCampaign[0].StartDate);
        		if (initialDate < Date.Today()) 
        			initialDate = Date.Today();
        		// because this is a GMT time, we should add some padding, so we'll stick
        		// with the same day, even if the locale time zone is up to 12 hours before GMT.
        		if (initialDate != null) initialDate = initialDate.addHours(12);  
        		campaignId = p;        		
        	}
        }
        
        // if Job passed in, set current state
        p = params.get('volunteerJobId');
        if (p != null && p != '') {
        	list<Volunteer_Job__c> listJob = [select Id, First_Shift__c, Campaign__c
        		from Volunteer_Job__c where Id = :p];
        	if (listJob.size() > 0) {
        		initialDate = Date.valueOf(listJob[0].First_Shift__c);
        		if (initialDate < Date.Today()) 
        			initialDate = Date.Today();
        		volunteerJobId = p;
        		// let caller control whether to filter by campaign or not.
        		//campaignId = listJob[0].Campaign__c;        		
        	}
        }
        	
		// if shift passed in, set current state
        p = params.get('volunteerShiftId');
        if (p != null && p != '') {
        	list<Volunteer_Shift__c> listShift = [select Id, Start_Date_Time__c, 
        		Volunteer_Job__c, Volunteer_Job__r.Campaign__c 
        		from Volunteer_Shift__c where Id = :p];
        	if (listShift.size() > 0) {
        		initialDate = Date.valueOf(listShift[0].Start_Date_Time__c);
        		// let caller control whether to filter by campaign & job or not.
        		//volunteerJobId = listShift[0].Volunteer_Job__c;
        		//campaignId = listShift[0].Volunteer_Job__r.Campaign__c;
        	}
        }

    	fPrint = false; 
        p = params.get('Print');
        if (p == '1') fPrint = true;

    	// we can derive whether we are within the Personal Site or not, so no need for parameter.
    	fPersonalSite = ApexPages.currentPage().getUrl().contains('PersonalSite');

		// we can derive whether we are within Sites or not, so no need for parameter.
    	fWeb = false; 
        fWeb = (Site.getName() != null);

    	strLanguage = fWeb ? 'en-us' : null;  // don't set language if viewing in SF.  Let SF decide. 
        p = params.get('Language');
        if (p != null && p != '') strLanguage = p;
        
        p = params.get('initialDate');
		if (p != null && p != '') initialDate = Date.valueOf(p);
		
		strCalendarView = 'month';
        p = params.get('CalendarView');
        if (p != null && p != '') strCalendarView = p;

		fShowCampaignHierarchy = false;
        p = params.get('ShowCampaignHierarchy');
        if (p == '1') fShowCampaignHierarchy = true;

		fShowNumberAvailable = false;
        p = params.get('ShowNumberAvailable');
        if (p == '1') fShowNumberAvailable = true;

		// finally, keep all parameters for passing to VolunteersJobListingFS
		// (but remove the params that the page will explicitly add to the url)
		params.remove('Calendar');
		params.remove('volunteerShiftId');
		params.remove('jobId');
		params.remove('dtMonthFilter');
		strParams = '';
		string strAmp = '';
		for (string str : params.keySet()) {
			strParams += strAmp + str + '=' + params.get(str);
			strAmp = '&';
		}
    }
    
    global string strURLtoCSSFile { 
        get { 
        	if (fPersonalSite) return null;	// just use whatever CSS the Site Template includes.
        	
        	// only specify the css file if in the web page scenario.
            if (strURLtoCSSFile == null && fWeb) {
                list<Document> listDocs = [SELECT Name, Id From Document WHERE Name = 'JobCalendarCSS.css' LIMIT 1 ];
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

    private VOL_SharedCode volSharedCode;
    
    // global properties
    global string strLanguage { get; set; }
    global boolean fPrint { get; set; }
    global boolean fWeb { get; set; }
    global string strCalendarView { get; set; }
    global string strParams { get; set; }
    global boolean fPersonalSite { get; set; }
    global boolean fShowCampaignHierarchy { get; set; }
    global boolean fShowNumberAvailable { get; set; }
    /** @description what is the namespace (null if unmanaged, or GW_Volunteers if managed) */
    global string namespacePrefix { get; set; }
   	 
    // holds the currently selected Campaign in the Campaign dropdown
    global ID campaignId { get; set; }

    // the list of active Volunteer Campaigns 
    global list<SelectOption> listSOCampaigns {
        get {
            list<SelectOption> listSO = new list<SelectOption>();
            listSO.add(new SelectOption('', system.label.labelChoiceAllActiveCampaigns));
            for (Campaign c : [select Name, Id, StartDate from Campaign 
            	where RecordTypeId = :VOL_SharedCode.recordtypeIdVolunteersCampaign 
            	and IsActive = true order by Name asc limit 999]) {
                listSO.add(new SelectOption(c.id, c.name));
            }       
            return listSO;
        }
        set;
    }
    
    // the user has changed the Campaign dropdown
    global virtual PageReference ChangeCampaign() {
        // clear out all state that is specific to the campaign
        listSOVolunteerJobs = null;
        volunteerJobId = null;
        ChangeVolunteerJob();
        return null;
    }

    // holds the currently select Job in the Job dropdown
    global ID volunteerJobId { get; set; }
    
    // hold the initial date for the calendar
    global DateTime initialDate { get; set; }
    
    // the list of Volunteer Jobs for the specified Campaign
    global list<SelectOption> listSOVolunteerJobs {
    	get {
	        list<SelectOption> listSO = new list<SelectOption>();
	        listSO.add(new SelectOption('', system.label.labelChoiceAllJobs));
	        if (campaignId == null) {
		        for (Volunteer_Job__c vj : [select Name, Id from Volunteer_Job__c 
		        	where Campaign__r.IsActive = true order by name limit 999]) {
		            listSO.add(new SelectOption(vj.id, vj.name));
		        }        	
	        } else {
		        for (Volunteer_Job__c vj : [select Name, Id from Volunteer_Job__c 
		        	where Campaign__c = :campaignId order by name limit 999]) {
		            listSO.add(new SelectOption(vj.id, vj.name));
		        }
	        }       
	        return listSO;
	    } 
	    
	    set;
    }    

    // the user has changed the Volunteer Job dropdown
    global virtual PageReference ChangeVolunteerJob() {
        // clear out all state that is specific to the Job
        return null;
    }
    
    
	/*******************************************************************************************************
	* @description Javascript Remoting method to return a list of Shifts, optionally filtered by strFilter.
	* @param strStartDateTime filter for Logs >= startDate
	* @param strEndDateTime filter for Logs <= endDate
	* @return list<Volunteer_Shift__c>, which will be turned into a Javascript collection.
	********************************************************************************************************/
    @RemoteAction global static list<Volunteer_Shift__c> getListShifts(string strCampaignId, string strJobId, 
    	string strStartDateTime, string strEndDateTime) {
    	return getListShiftsWeb2(strCampaignId, strJobId, strStartDateTime, strEndDateTime, false, false);		
    }
    
	/*******************************************************************************************************
	* @description Javascript Remoting method to return a list of Shifts, optionally filtered by strFilter.
	* @param strStartDateTime filter for Logs >= startDate
	* @param strEndDateTime filter for Logs <= endDate
	* @param fWeb whether to filter out job's who are not display on web.
	* @return list<Volunteer_Shift__c>, which will be turned into a Javascript collection.
	********************************************************************************************************/
    @RemoteAction global static list<Volunteer_Shift__c> getListShiftsWeb(string strCampaignId, string strJobId, 
    	string strStartDateTime, string strEndDateTime, boolean fWeb) {    
    	return getListShiftsWeb2(strCampaignId, strJobId, strStartDateTime, strEndDateTime, fWeb, false);		
	}
    
	/*******************************************************************************************************
	* @description Javascript Remoting method to return a list of Shifts, optionally filtered by strFilter.
	* @param strStartDateTime filter for Logs >= startDate
	* @param strEndDateTime filter for Logs <= endDate
	* @param fWeb whether to filter out job's who are not display on web.
	* @param fShowCampaignHierarchy whether do to include descendant campaigns of strCampaignId
	* @return list<Volunteer_Shift__c>, which will be turned into a Javascript collection.
	********************************************************************************************************/
    @RemoteAction global static list<Volunteer_Shift__c> getListShiftsWeb2(string strCampaignId, string strJobId, 
    	string strStartDateTime, string strEndDateTime, boolean fWeb, boolean fShowCampaignHierarchy) {

    	DateTime dtStart = datetime.valueOf(strStartDateTime);
    	DateTime dtEnd = datetime.valueOf(strEndDateTime);
    	boolean fAllCampaign = (strCampaignId == '*');
    	boolean fAllJob = (strJobId == '*');
    	list<Volunteer_Shift__c> listShifts;
    	    	
    	if (!fAllJob) {
			listShifts = [select Id, Name, Volunteer_Job__c, Volunteer_Job__r.Name, Volunteer_Job__r.Volunteer_Website_Time_Zone__c, Volunteer_Job__r.Campaign__r.Volunteer_Website_Time_Zone__c,
				Volunteer_Job__r.Campaign__c, Start_Date_Time__c, Duration__c, 
				Total_Volunteers__c, Number_of_Volunteers_Still_Needed__c, Description__c
				from Volunteer_Shift__c
				where Start_Date_Time__c >= :dtStart and Start_Date_Time__c <= :dtEnd
					and Volunteer_Job__c = :strJobId
					and (Volunteer_Job__r.Display_On_Website__c = true or Volunteer_Job__r.Display_On_Website__c = :fWeb)
				order by Start_Date_Time__c asc];    		
    	} else if (fAllCampaign && fAllJob) {
			listShifts = [select Id, Name, Volunteer_Job__c, Volunteer_Job__r.Name, Volunteer_Job__r.Volunteer_Website_Time_Zone__c,Volunteer_Job__r.Campaign__r.Volunteer_Website_Time_Zone__c,
				Volunteer_Job__r.Campaign__c, Start_Date_Time__c, Duration__c,
				Total_Volunteers__c, Number_of_Volunteers_Still_Needed__c, Description__c
				from Volunteer_Shift__c
				where Start_Date_Time__c >= :dtStart and Start_Date_Time__c <= :dtEnd
					and Volunteer_Job__r.Campaign__r.IsActive = true
					and (Volunteer_Job__r.Display_On_Website__c = true or Volunteer_Job__r.Display_On_Website__c = :fWeb)
				order by Start_Date_Time__c asc];    		
    	} else if (!fAllCampaign && fAllJob) {
            List<Id> listCampaignIds = new List<Id> {strCampaignId};
            if (fShowCampaignHierarchy) {
                listCampaignIds = VOL_SharedCode.listIdsCampaignsInHierarchy(strCampaignId);    
            }
			listShifts = [select Id, Name, Volunteer_Job__c, Volunteer_Job__r.Name, Volunteer_Job__r.Volunteer_Website_Time_Zone__c,Volunteer_Job__r.Campaign__r.Volunteer_Website_Time_Zone__c,
				Volunteer_Job__r.Campaign__c, Start_Date_Time__c, Duration__c,
				Total_Volunteers__c, Number_of_Volunteers_Still_Needed__c, Description__c
				from Volunteer_Shift__c
				where Start_Date_Time__c >= :dtStart and Start_Date_Time__c <= :dtEnd
					and Volunteer_Job__r.Campaign__c = :listCampaignIds
					and (Volunteer_Job__r.Display_On_Website__c = true or Volunteer_Job__r.Display_On_Website__c = :fWeb)
				order by Start_Date_Time__c asc];    		
    	}
    	
    	
    	// to avoid FullCalendar from displaying shifts based on the user's machine time zone,
    	// we translate it to the desired time zone, then say it is GMT.
    	// get default time zone for site guest user
    	User u = [Select TimeZoneSidKey From User where id =: Userinfo.getUserId()];
    	    	
    	for (Volunteer_Shift__c shift : listShifts) {
			string strTimeZone = shift.Volunteer_Job__r.Volunteer_Website_Time_Zone__c;
			if (strTimeZone == null) strTimeZone = shift.Volunteer_Job__r.Campaign__r.Volunteer_Website_Time_Zone__c;
			if (strTimeZone == null) strTimeZone = u.TimeZoneSidKey;
    		shift.Start_Date_Time__c = DateTime.valueOfGmt(shift.Start_Date_Time__c.format('yyyy-MM-dd HH:mm:ss', strTimeZone));
    	}
    			
		return listShifts;
    } 		
    
	// this was a testmethod which can't be compiled in this class under api 31.  
	// but because it was marked global, it could not be completely removed.
    global static void TestJobCalendar() { }
}