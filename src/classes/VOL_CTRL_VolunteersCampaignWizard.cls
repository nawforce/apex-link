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
* @date 11/17/2011
* @description Page Controller class for the Volunteers Wizard visualforce page.  
********************************************************************************************************/
public with sharing class VOL_CTRL_VolunteersCampaignWizard {

	// the new campaign we will create
    public Campaign cmpVols { 
        get {
            if (cmpVols == null) cmpVols = new Campaign(RecordTypeId=VOL_SharedCode.recordtypeIdVolunteersCampaign);
            return cmpVols;
        }
        
        set; 
    }
    
    // a temporary campaign to use for *locale* date fields on the visualforce page.
    public Campaign cmpShiftDates {
    	get {
    		if (cmpShiftDates == null) cmpShiftDates = new Campaign();
    		return cmpShiftDates;
    	}
    	set;
    }

    // a temporary Volunteer Hours to use for status field on the visualforce page.
    public Volunteer_Hours__c hoursStatus {
    	get {
    		if (hoursStatus == null) hoursStatus = new Volunteer_Hours__c();
    		return hoursStatus;
    	}
    	set;
    }
	
    public ID campaignIdClone { get; set; }    
    public integer cSampleJobs { get; set; }
    public integer cSampleShifts { get; set; } 
    public boolean fCopyVolunteers { get; set; }

    private VOL_SharedCode volSharedCode;

    // the list of Campaigns to put in the clone existing campaign dropdown
    public list<SelectOption> listSOVolunteersCampaigns { get; set; }
    
    // constructor
    public VOL_CTRL_VolunteersCampaignWizard() {
        volSharedCode = new VOL_SharedCode();
        listSOVolunteersCampaigns = volSharedCode.listSOCampaignsWithJobs;  
        fCopyVolunteers = false;      
    }
    
    
	/*******************************************************************************************************
	* @description Action Method to Save changes in the Volunteers Wizard.  This will cause it to create
	* a new campaign, and either populate it with sample jobs and shifts, or clone jobs, shifts, and 
	* volunteers from an existing Volunteers campaign.
	* @return PageReference null
	********************************************************************************************************/
    public PageReference Save() {

        // validation
        if (campaignIdClone != null && (cSampleJobs > 0 || cSampleShifts > 0)) {
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, Label.labelVolunteersWizardErrorExistingOrSample)); 
            return null; 
        }
        
        // more validation
        if (campaignIdClone == null && cSampleShifts > 0 && cSampleJobs == 0) {
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, Label.labelVolunteersWizardErrorNoJobsWithShifts)); 
            return null; 
        }
                
        try {
            insert cmpVols;
            
            if (campaignIdClone != null) {
                CloneExistingJobsAndShifts();
            } else if (cSampleJobs > 0) {
                CreateSampleJobsAndShifts();
            } 
                    
            // go to the campaign's detail page
            PageReference p = new PageReference('/' + cmpVols.Id);
            p.setRedirect(true);
            return p;
            
        } catch (Exception e) {
            ApexPages.addMessages(e);
            return null;
        }           
    }
    
	/*******************************************************************************************************
	* @description method to create sample jobs and shifts on the new Volunteers campaign.
	********************************************************************************************************/
    private void CreateSampleJobsAndShifts() {
        list<Volunteer_Job__c> listJobs = new list<Volunteer_Job__c>();
        list<Volunteer_Shift__c> listShifts = new list<Volunteer_Shift__c>();
        
        // create the sample jobs
        for (Integer iJob = 0; iJob < cSampleJobs; iJob++) {
            Volunteer_Job__c job = new Volunteer_Job__c();
            job.Campaign__c = cmpVols.Id;
            job.Name = Label.labelVolunteersWizardSampleJobName + (iJob + 1);
            listJobs.add(job);
        }   
        insert listJobs;
        
        // create the sample shifts
        for (Integer iJob = 0; iJob < cSampleJobs; iJob++) {
            for (Integer iShift = 0; iShift < cSampleShifts; iShift++) {
                Volunteer_Shift__c shift = new Volunteer_Shift__c();
                shift.Volunteer_Job__c = listJobs[iJob].Id;
                if (cmpVols.StartDate == null) cmpVols.StartDate = system.today();
                shift.Start_Date_Time__c = DateTime.newInstance(cmpVols.StartDate.year(), cmpVols.StartDate.month(), cmpVols.StartDate.day(), iShift+8, 0, 0); 
                shift.Duration__c = 1;
                shift.Description__c = Label.labelVolunteersWizardSampleShiftName + (iShift + 1);
                listShifts.add(shift);
            }                   
        }           
        insert listShifts;  
    }
    
	/*******************************************************************************************************
	* @description method to clone existing jobs, shifts, and optionally Volunteer Hours onto 
	* the new Volunteers campaign.
	********************************************************************************************************/
    private void CloneExistingJobsAndShifts() {
        list<Volunteer_Job__c> listJobs = new list<Volunteer_Job__c>();
        list<Volunteer_Shift__c> listShifts = new list<Volunteer_Shift__c>();
        list<Volunteer_Hours__c> listHours = new list<Volunteer_Hours__c>();
        list<Volunteer_Job__c> listJobsExisting = new list<Volunteer_Job__c>();
        list<Volunteer_Shift__c> listShiftsExisting = new list<Volunteer_Shift__c>();
        list<Volunteer_Hours__c> listHoursExisting = new list<Volunteer_Hours__c>();
        
		// initialize the list of all fields on Job.
		Map<String, Schema.SObjectField> mapS = Schema.SObjectType.Volunteer_Job__c.fields.getMap();
		list<string> listStrFieldsJob = new list<string>();
		listStrFieldsJob.addAll(mapS.keySet());       
		
		// find the existing jobs
		// we need to use dynamic soql to pick up all custom fields we don't know about
        string strSoql = 'select ';
        string strComma = '';
        for (string strF : listStrFieldsJob) {
        	strSoql += strComma + strF;
        	strComma = ', ';
        }
        strSoql += ' from Volunteer_Job__c where Campaign__c = :campaignIdClone order by CreatedDate';    
        listJobsExisting = Database.Query(strSoql); 

		// initialize the list of all fields on Shift.
		mapS = Schema.SObjectType.Volunteer_Shift__c.fields.getMap();
		list<string> listStrFieldsShift = new list<string>();
		listStrFieldsShift.addAll(mapS.keySet());         
        
        // find the existing shifts for those jobs
		// we need to use dynamic soql to pick up all custom fields we don't know about
        strSoql = 'select ';
        strComma = '';
        for (string strF : listStrFieldsShift) {
        	strSoql += strComma + strF;
        	strComma = ', ';
        }
        strSoql += ' from Volunteer_Shift__c where Volunteer_Job__c in :listJobsExisting order by Start_Date_Time__c';  
        listShiftsExisting = Database.Query(strSoql); 
        
        // clone the jobs               
        for (Volunteer_Job__c job : listJobsExisting) {
            Volunteer_Job__c jobNew = new Volunteer_Job__c();

            //  now copy over all the non-null fields 
            for (string strF : listStrFieldsJob) {
            	if (strF != 'Id' && job.get(strF) != null) {
            		try {
            			jobNew.put(strF, job.get(strF));
            		} catch (exception ex) {
						// catch errors from readonly fields, and continue.            			            			
            		}
            	}
            }
            jobNew.Campaign__c = cmpVols.Id;
            listJobs.add(jobNew);
        }
        insert listJobs;
        
        // create a map of existing JobId to new JobId
        map<ID, ID> mapJobId = new map<ID, ID>();
        for (Integer i = 0; i < listJobsExisting.size(); i++) {
            mapJobId.put(listJobsExisting[i].Id, listJobs[i].Id);
        }
        
        // figure out our base date to use.
        Date dt = cmpShiftDates.EndDate;
        if (dt == null) dt = cmpVols.StartDate;
        if (dt == null) dt = System.Now().date();
        
        // figure out date offset
        integer cDays;        
        if (cmpShiftDates.StartDate == null) {
        	cDays = -1; // don't offset, use fixed dt.        	        	
        } else {
        	cDays = cmpShiftDates.StartDate.daysBetween(dt);
        }
                
        // clone the shifts
        for (Volunteer_Shift__c shift : listShiftsExisting) {
            Volunteer_Shift__c shiftNew = new Volunteer_Shift__c();

            //  now copy over all the non-null fields 
            for (string strF : listStrFieldsShift) {
            	if (strF != 'Id' && shift.get(strF) != null) {
            		try {
            			shiftNew.put(strF, shift.get(strF));
            		} catch (exception ex) {
						// catch errors from readonly fields, and continue.            			
            		}
            	}
            }

            shiftNew.Volunteer_Job__c = mapJobId.get(shift.Volunteer_Job__c);
            
            // just use the time, not the date, from the existing shift.
            Time tm = shift.Start_Date_Time__c.time();
            
            if (cDays > -1) {
            	dt = shift.Start_Date_Time__c.addDays(cDays).date();	
            }
            shiftNew.Start_Date_Time__c = DateTime.newInstance(dt, tm);
            
            // clear the volunteer count
            shiftNew.Total_Volunteers__c = 0;
            
            listShifts.add(shiftNew);
        }
        insert listShifts;

		if (fCopyVolunteers) {
			// initialize the list of all fields on Hours.
			mapS = Schema.SObjectType.Volunteer_Hours__c.fields.getMap();
			list<string> listStrFieldsHours = new list<string>();
			listStrFieldsHours.addAll(mapS.keySet());       

	        // find the existing hours for those jobs and shifts
			// we need to use dynamic soql to pick up all custom fields we don't know about
	        strSoql = 'select ';
	        strComma = '';
	        for (string strF : listStrFieldsHours) {
	        	strSoql += strComma + strF;
	        	strComma = ', ';
	        }
	        strSoql += ' from Volunteer_Hours__c where Volunteer_Job__c in :listJobsExisting ';
	        strSoql += ' or Volunteer_Shift__c in :listShiftsExisting ';
	        listHoursExisting = Database.Query(strSoql); 

	        // create a map of existing ShiftId to new ShiftId
	        map<ID, ID> mapShiftId = new map<ID, ID>();
	        for (Integer i = 0; i < listShiftsExisting.size(); i++) {
	            mapShiftId.put(listShiftsExisting[i].Id, listShifts[i].Id);
	        }
	        
	        // create a map of new ShiftId to new Shift (so we can get dates)
	        map<ID, Volunteer_Shift__c> mapIdToShift = new map<ID, Volunteer_Shift__c>();
	        for (Volunteer_Shift__c shift : listShifts) {
	        	mapIdToShift.put(shift.Id, shift);
	        }

	        // clone the hours
	        for (Volunteer_Hours__c hours : listHoursExisting) {
	            Volunteer_Hours__c hoursNew = new Volunteer_Hours__c();
	
	            //  now copy over all the non-null fields 
	            for (string strF : listStrFieldsHours) {
	            	if (strF != 'Id' && hours.get(strF) != null) {
	            		try {
	            			hoursNew.put(strF, hours.get(strF));
	            		} catch (exception ex) {
							// catch errors from readonly fields, and continue.            			
	            		}
	            	}
	            }
	
	            hoursNew.Volunteer_Job__c = mapJobId.get(hours.Volunteer_Job__c);
	            hoursNew.Volunteer_Shift__c = mapShiftId.get(hours.Volunteer_Shift__c);
	            
	            // override hours status
	            if (hoursStatus.Status__c != null) {
	            	hoursNew.Status__c = hoursStatus.Status__c;
	            }
	            
	            // override start date and end date
	            if (hoursNew.Volunteer_Shift__c != null) {
	            	Date dtNew = mapIdToShift.get(hoursNew.Volunteer_Shift__c).Start_Date_Time__c.date();
					hoursNew.Start_Date__c = dtNew;
					hoursNew.End_Date__c = dtNew;	            	
		            // fixup Planned_Start_Date_Time__c
		            if (hours.Planned_Start_Date_Time__c != null) {
		            	Time tm = hours.Planned_Start_Date_Time__c.time();
		            	hoursNew.Planned_Start_Date_Time__c = DateTime.newInstance(dtNew, tm);
		            }
	            } else {
	            	hoursNew.Start_Date__c = cmpVols.StartDate;
	            	hoursNew.End_Date__c = null;
	            }
	            	                        
	            listHours.add(hoursNew);
	        }
	        insert listHours;
		}
    }
    
	/*******************************************************************************************************
	* @description action method called whenever the existing campaign dropdown changes.  Queries the 
	* campaign to find its earliest job shift.
	********************************************************************************************************/
    public PageReference ChangeCampaign() {
    	cSampleJobs = 0;
    	cSampleShifts = 0;
    	
    	list<Volunteer_Job__c> listJobs = [select Id, First_Shift__c from Volunteer_Job__c 
			where Campaign__c = :campaignIdClone
			order by First_Shift__c];

		cmpShiftDates.StartDate = null;

		// find the earliest shift (if there is one)
		for (Volunteer_Job__c job : listJobs) {
			if (job.First_Shift__c != null) {
				// will give date in locale time zone of current SF user.
				cmpShiftDates.StartDate = job.First_Shift__c.date(); 
				return null;				
			}
		}
    	return null;
    }
        
}