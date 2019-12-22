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
* @date 11/1/2012
* @description Main class for managing Volunteer Recurrence Schedules (VRS). 
********************************************************************************************************/
public with sharing class VOL_VRS {


    //******************************************************************************************************
	// for the specified VRS's, delete all hours that are not completed hours.
	// called from the After Delete VRS trigger.
	public static void DeleteListVRS(list<Volunteer_Recurrence_Schedule__c> listVRS) {
		DeleteListVRS(listVRS, false);
	}
		
    //******************************************************************************************************
	// for the specified VRS's, delete all hours that are not completed hours.
	// called from the After Delete VRS trigger and when we are processing a modified VRS.
	private static void DeleteListVRS(list<Volunteer_Recurrence_Schedule__c> listVRS, boolean fPreserveCanceled) {

		// get a set of the VRS ID's for querying
		set<ID> setVRSId = new set<ID>();
		for (Volunteer_Recurrence_Schedule__c vrs : listVRS) {
			setVRSId.add(vrs.Id);
		}
		
		// get all hours associated with these VRS's that we should delete
		list<Volunteer_Hours__c> listHours = new list<Volunteer_Hours__c>();
		if (fPreserveCanceled) {
			listHours = [select Id from Volunteer_Hours__c  
				where (Status__c <> 'Completed' and Status__c <> 'Canceled' and
					Volunteer_Recurrence_Schedule__c in : setVRSId)];
		} else {
			listHours = [select Id from Volunteer_Hours__c  
				where (Status__c <> 'Completed' and
					Volunteer_Recurrence_Schedule__c in : setVRSId)];			
		}
				
		if (listHours.size() > 0) {
			delete listHours;
			
			// if PreserveCanceled, then we know we are updating the VRS, and so there is no need
			// to keep the deleted Hours in the recycle bin, since we are going to recreate the hours.
			if (fPreserveCanceled)
				Database.emptyRecycleBin(listHours);
		}
	}

   	//******************************************************************************************************
	// given a list of Volunteer recurring schedules, does all the work to delete any hours that
	// no longer match, and creates new hours into the future.
	// called from both the VRS trigger (when the user modifies a specific VRS),
	// as well as from the JRS processing, so new shifts get their hours assigned.
	// @param listVRS the list of VRS's to process
	// @param listVRSOld If called from the AfterUpdate trigger, the trigger.old list
	// @param fReviewAllShifts Specifies whether called from the trigger on JRS's, in
    // which case we should review all shifts under the Jobs, or from the scheduled batch,
    // in which case we only need to be looking to add additional shifts in the future.
	public static void ProcessListVRS(
	   list<Volunteer_Recurrence_Schedule__c> listVRS, 
	   list<Volunteer_Recurrence_Schedule__c> listVRSOld, 
	   boolean fReviewAllShifts) {
		
		// get a set of the VRS ID's for querying
		// also the Job ID's they are associated with
		// also the contact ID's they are associated with
		set<ID> setVRSId = new set<ID>();
		set<ID> setJobId = new set<ID>();
		set<ID> setContactId = new set<ID>();
		for (Volunteer_Recurrence_Schedule__c vrs : listVRS) {
			setVRSId.add(vrs.Id);
			setJobId.add(vrs.Volunteer_Job__c);
			setContactId.add(vrs.Contact__c);
		}
		
		// if the VRS was updated, then deal with potent changes to the VRS's job or contact 
		if (listVRSOld != null) {
	        for (Volunteer_Recurrence_Schedule__c vrs : listVRSOld) {
	            setVRSId.add(vrs.Id);
	            setJobId.add(vrs.Volunteer_Job__c);
	            setContactId.add(vrs.Contact__c);
	        }
		}
							
		// get all shifts of the jobs these vrs's are associated with
		list<Volunteer_Shift__c> listShift = new list<Volunteer_Shift__c>();
		if (fReviewAllShifts) {
			listShift = [select Id, Duration__c, Start_Date_Time__c, Desired_Number_of_Volunteers__c, 
				Volunteer_Job__c from Volunteer_Shift__c
				where Volunteer_Job__c in :setJobId];
		} else {
            listShift = [select Id, Duration__c, Start_Date_Time__c, Desired_Number_of_Volunteers__c, 
                Volunteer_Job__c from Volunteer_Shift__c
                where Volunteer_Job__c in :setJobId and Start_Date_Time__c >= TODAY];
		}
		
		// construct a map of Job to its associated list of VRS's
		map<ID, list<Volunteer_Recurrence_Schedule__c>> mapJobIdListVRS = new map<ID, list<Volunteer_Recurrence_Schedule__c>>();
		
		// put the VRS's on each job's list
		for (Volunteer_Recurrence_Schedule__c vrs : listVRS) {			
			list<Volunteer_Recurrence_Schedule__c> listVRSforJob = mapJobIdListVRS.get(vrs.Volunteer_Job__c);
			if (listVRSforJob == null) listVRSforJob = new list<Volunteer_Recurrence_Schedule__c>();
			listVRSforJob.add(vrs);
			mapJobIdListVRS.put(vrs.Volunteer_Job__c, listVRSforJob);				
		}
	
		// in order to avoid creating hours that already exist (completed or canceled),
		// create a set of shiftId|contactId tuples, which allows us to quickly see if we
		// already have an hours record for the given shift and contact.
		list<Volunteer_Hours__c> listHoursExisting = new list<Volunteer_Hours__c>();
		if (fReviewAllShifts) { 
			listHoursExisting = [select Id, Volunteer_Job__c, Volunteer_Recurrence_Schedule__c,
				Volunteer_Shift__c, Status__c, Contact__c from
				Volunteer_Hours__c where Volunteer_Job__c in :setJobId and Contact__c in :setContactId];
		} else {
            listHoursExisting = [select Id, Volunteer_Job__c, Volunteer_Recurrence_Schedule__c,
                Volunteer_Shift__c, Status__c, Contact__c from
                Volunteer_Hours__c where Volunteer_Job__c in :setJobId and Contact__c in :setContactId and Shift_Start_Date_Time__c >= TODAY];
		}
		map<string, Volunteer_Hours__c> mapShiftIdContactIdToHours = new map<string, Volunteer_Hours__c>();
		for (Volunteer_Hours__c hr : listHoursExisting) {
			mapShiftIdContactIdToHours.put(hr.Volunteer_Shift__c + '|' + hr.Contact__c, hr);
		}

		// create hours for these vrs's for each shift 				
		list<Volunteer_Hours__c> listHoursNew = new list<Volunteer_Hours__c>();
        list<Volunteer_Hours__c> listHoursUpdate = new list<Volunteer_Hours__c>();
		for (Volunteer_Shift__c shift : listShift) {
			list<Volunteer_Recurrence_Schedule__c> listVRSforJob = mapJobIdListVRS.get(shift.Volunteer_Job__c);
			AssignVRSHours(shift, listVRSforJob, mapShiftIdContactIdToHours, listHoursNew, listHoursUpdate);
		}
		if (listHoursNew.size() > 0) {
			insert listHoursNew;
		}
			
        if (listHoursUpdate.size() > 0) {
            update listHoursUpdate;
        }

		// mapShiftIdContactIdToHours now contains only the Hours that did not match the VRS's, 
		// so we can delete them if they aren't marked Completed or Canceled, and they used to be associated with these VRS's.
		list<Volunteer_Hours__c> listHRDelete = new list<Volunteer_Hours__c>();
		for (Volunteer_Hours__c hr : mapShiftIdContactIdToHours.values()) {
		    if (hr.Status__c != 'Completed' && hr.Status__c != 'Canceled' && setVRSId.contains(hr.Volunteer_Recurrence_Schedule__c)) {
		        listHRDelete.add(hr);
		    }
		}
        if (listHRDelete.size() > 0) {
            delete listHRDelete;
        }
	}
	
    //******************************************************************************************************
	// @description for the specified shift, create VolunteerHours records for all volunteers
	// who have a recurring schedule which should include this shift.
	// @param shift The shift to assign Hours to
	// @param listVRS The list of VRS's to consider for assigning hours to the shift
	// @param mapShiftIdContactIdToHours A map of shiftId/ContactId keys to existing Hours.  As Hours are
	// matched, they are removed from this map.  So upon return, this map contains those Hours that no longer
	// match their VRS (so they can be considered for deletion).
	// @param listHoursNew The returned set of Hours to create (leaving creation to the caller)
    // @param listHoursUpdate The returned set of Hours to update (leaving update to the caller)
	private static void AssignVRSHours(
		Volunteer_Shift__c shift, 
		list<Volunteer_Recurrence_Schedule__c> listVRS, 
		map<string, Volunteer_Hours__c> mapShiftIdContactIdToHours,
		list<Volunteer_Hours__c> listHoursNew,
        list<Volunteer_Hours__c> listHoursUpdate
		) {
			
		if (listVRS == null) return; 

		// Strategy:
		// for the given shift, go through the list of VRS's
		// and create hours for the ones that match.
		
		// we decided not to limit the number of volunteers assigned
		//integer cDesiredVols = 1000;
		//if (shift.Desired_Number_of_Volunteers__c != null) 
		//	cDesiredVols = integer.valueOf(shift.Desired_Number_of_Volunteers__c);
		
		Date dtShift = shift.Start_Date_Time__c.Date();
		DateTime dtShiftEnd = shift.Start_Date_Time__c.addMinutes(integer.valueOf(shift.Duration__c * 60));
		integer nday = VOL_JRS.nDayOfWeek(dtShift);
		Set<Volunteer_Hours__c> setHoursOld = new set<Volunteer_Hours__c>();
        Set<Volunteer_Hours__c> setHoursUpdate = new set<Volunteer_Hours__c>();
        
        /*
        system.debug('****DJH: shift: ' + shift);
        system.debug('****DJH: dtShift: ' + dtShift);
        system.debug('****DJH: dtShiftEnd: ' + dtShiftEnd);
        system.debug('****DJH: nday: ' + nday);
		*/
		
		for (Volunteer_Recurrence_Schedule__c vrs : listVRS) {
		
			// exit if we've filled all the slots.
			//if (cDesiredVols <= 0) 
				//break;
	
			// for this jrs, what week should we treat this shift as
			integer nweek = VOL_JRS.nWeekOfDate(dtFirstOccurrenceInWeek(vrs, dtShift));
            //system.debug('****DJH: nweek: ' + nweek);
		
			list<boolean> listWhichDays = WhichDaysVRS(vrs);
			list<boolean> listWhichWeeks = WhichWeeksVRS(vrs);
            //system.debug('****DJH: listWhichDays: ' + listWhichDays);
            //system.debug('****DJH: listWhichWeeks: ' + listWhichWeeks);
			
			if (vrs.Weekly_Occurrence__c == null)
				continue;
				
			if ((listWhichWeeks[nweek] || 
					vrs.Weekly_Occurrence__c.contains('Every') ||
					(vrs.Weekly_Occurrence__c.contains('Alternate') && alternateWeekVRS(vrs, dtShift))) && 
				listWhichDays[nday] &&
				vrs.Schedule_Start_Date_Time__c != null &&
				dtShift >= vrs.Schedule_Start_Date_Time__c.Date() &&
				(vrs.Schedule_End_Date__c == null || vrs.Schedule_End_Date__c >= dtShift)) {
				    
                //system.debug('****DJH: vrs candidate for shift: vrs '+ vrs + ' shift ' + shift);

				// we need to deal with the person already having hours on this shift
				// that might be completed, or canceled, or web signup.  
				// avoid creating another confirmed record.
				// note that if we match, we remove the key from the map, so we know it was matched.
				Volunteer_Hours__c hrExisting = mapShiftIdContactIdToHours.get(shift.Id + '|' + vrs.Contact__c);
				if (hrExisting != null) {
					// if shift/hour in the future, we update a subset of fields
					if (dtShift >= System.today()) {
                        hrExisting.Hours_Worked__c = vrs.Duration__c;
                        if (vrs.Number_of_Volunteers__c != null && vrs.Number_of_Volunteers__c >= 1)
                            hrExisting.Number_of_Volunteers__c = vrs.Number_of_Volunteers__c;
                        if (vrs.Volunteer_Hours_Status__c != null) 
                            hrExisting.Status__c = vrs.Volunteer_Hours_Status__c;
                        hrExisting.Planned_Start_Date_Time__c = datetime.newInstance(shift.Start_Date_Time__c.date(), vrs.Schedule_Start_Date_Time__c.time());
                        if (vrs.Comments__c != null)
                            hrExisting.Comments__c = vrs.Comments__c;
                        hrExisting.Volunteer_Recurrence_Schedule__c = vrs.Id; // in case it wasn't already claimed by this VRS   
                        setHoursUpdate.add(hrExisting);
					} else {
					    // shift is in the past.  we won't update it, but track it so before we return we can remove it.
					    setHoursOld.add(hrExisting);
					}
					continue;
				}
									
				// only take volunteers whose time fits in the shift time (to handle multiple shifts per day)
				Time tmVRS = vrs.Schedule_Start_Date_Time__c.Time();				
				DateTime dtVRSStart =  datetime.newInstance(dtShift, tmVRS);
				if (dtVRSStart < shift.Start_Date_Time__c  || dtVRSStart >= dtShiftEnd) {
				    /*
					system.debug('****DJH: vrs startdatetime no fit!');
					system.debug('****DJH: tmVRS: ' + tmVRS);
					system.debug('****DJH: dtVRSStart: ' + dtVRSStart);
                    system.debug('****DJH: shift.Start_Date_Time__c: ' + shift.Start_Date_Time__c);
                    system.debug('****DJH: dtShiftEnd: ' + dtShiftEnd);
					*/
					continue;					
				}	
					
				Volunteer_Hours__c hr = new Volunteer_Hours__c();
				hr.System_Note__c = label.labelVRSHoursCreatedSystemNote + ' ' + vrs.Name + '.';
				hr.Contact__c = vrs.Contact__c;
				hr.Hours_Worked__c = vrs.Duration__c;
				hr.Number_of_Volunteers__c = 1;
				if (vrs.Number_of_Volunteers__c != null && vrs.Number_of_Volunteers__c > 1)
					hr.Number_of_Volunteers__c = vrs.Number_of_Volunteers__c; 
				hr.Start_Date__c = dtShift;
				hr.End_Date__c = dtShift;
				hr.Status__c = (vrs.Volunteer_Hours_Status__c == null ? 'Confirmed' : vrs.Volunteer_Hours_Status__c);
				hr.Volunteer_Job__c = shift.Volunteer_Job__c;
				hr.Volunteer_Shift__c = shift.Id;	
				hr.Volunteer_Recurrence_Schedule__c = vrs.Id;	
				hr.Planned_Start_Date_Time__c = datetime.newInstance(shift.Start_Date_Time__c.date(), vrs.Schedule_Start_Date_Time__c.time());
				hr.Comments__c = vrs.Comments__c;
				listHoursNew.add(hr);
				
                // add the new hours to our map, so it won't get duplicated by two VRS's
                mapShiftIdContactIdToHours.put(hr.Volunteer_Shift__c + '|' + hr.Contact__c, hr);                    
				
				//cDesiredVols--;
			} else {
			    //system.debug('****DJH: vrs no match shift: vrs '+ vrs + ' shift ' + shift);
			}			 		
		}
		
        // we remove any matched or new hours from the map, so the caller knows who wasn't matched.
        listHoursUpdate.addAll(setHoursUpdate);
        for (Volunteer_Hours__c hr : setHoursUpdate)		
            mapShiftIdContactIdToHours.remove(hr.Volunteer_Shift__c + '|' + hr.Contact__c);                    
        for (Volunteer_Hours__c hr : listHoursNew)       
            mapShiftIdContactIdToHours.remove(hr.Volunteer_Shift__c + '|' + hr.Contact__c);                    
        for (Volunteer_Hours__c hr : setHoursOld)       
            mapShiftIdContactIdToHours.remove(hr.Volunteer_Shift__c + '|' + hr.Contact__c);                    

        // we let the caller commit the new/updated hours to the db.
	}

    //******************************************************************************************************
	// returns whether the nweek of the specified date is an alternate week for this schedule
	public static boolean alternateWeekVRS(Volunteer_Recurrence_Schedule__c vrs, Date dt) {
		if (vrs.Schedule_Start_Date_Time__c == null) return false;
		Date dtStart = vrs.Schedule_Start_Date_Time__c.Date().toStartOfWeek();
		Date dtEnd = dt.toStartOfWeek();
		integer cdays = dtStart.daysBetween(dtEnd);
		integer nweeks = (cdays + 1) / 7;
		return math.mod(nweeks, 2) == 0 ? true : false;		
	}		
		
    //******************************************************************************************************
	// returns an array of booleans for which days are on the schedule.
	// note that you should index by nDay (ie, Mon = index 2).
	private static list<boolean> WhichDaysVRS (Volunteer_Recurrence_Schedule__c vrs) {
		list<boolean> listWhichDays = new boolean[] { false, false, false, false, false, false, false, false };
		boolean isSun = VOL_JRS.isSundayFirstOfWeek();
		
		if (vrs.Days_of_Week__c != null) {
			listWhichDays[isSun ? 1 : 7] = vrs.Days_of_Week__c.contains('Sunday');
			listWhichDays[isSun ? 2 : 1] = vrs.Days_of_Week__c.contains('Monday');
			listWhichDays[isSun ? 3 : 2] = vrs.Days_of_Week__c.contains('Tuesday');
			listWhichDays[isSun ? 4 : 3] = vrs.Days_of_Week__c.contains('Wednesday');
			listWhichDays[isSun ? 5 : 4] = vrs.Days_of_Week__c.contains('Thursday');
			listWhichDays[isSun ? 6 : 5] = vrs.Days_of_Week__c.contains('Friday');
			listWhichDays[isSun ? 7 : 6] = vrs.Days_of_Week__c.contains('Saturday');
		}
		return listWhichDays; 
	}

    //******************************************************************************************************
	// returns an array of booleans for which weeks are on the schedule.
	// note that you should index by nWeek (ie, first = index 1).
	private static list<boolean> WhichWeeksVRS(Volunteer_Recurrence_Schedule__c vrs) {
		list<boolean> listWhichWeeks = new boolean[] { false, false, false, false, false, false };
		if (vrs.Weekly_Occurrence__c != null) {
			listWhichWeeks[1] = vrs.Weekly_Occurrence__c.contains('1st');
			listWhichWeeks[2] = vrs.Weekly_Occurrence__c.contains('2nd');
			listWhichWeeks[3] = vrs.Weekly_Occurrence__c.contains('3rd');
			listWhichWeeks[4] = vrs.Weekly_Occurrence__c.contains('4th');
			listWhichWeeks[5] = vrs.Weekly_Occurrence__c.contains('5th');
		}
		return listWhichWeeks;
	}

    //******************************************************************************************************
	// given the current date, return the first date in that week that should be scheduled		
	private static Date dtFirstOccurrenceInWeek(Volunteer_Recurrence_Schedule__c vrs, Date dt) {
		list<boolean> listWhichDays = WhichDaysVRS(vrs);
		integer nday = VOL_JRS.nDayOfWeek(dt);
		integer n;
		for (n = 1; n < nday && n <= 7; n++) { 
			if (listWhichDays[n])
				break;
		}		
		return dt.addDays(n - nday);
	} 	
		
	
}