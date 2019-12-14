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

trigger VOL_Contact_MaintainHours on Contact (before delete, after delete, after undelete) {

	// Hours are true children of Contacts, but Salesforce doesn't
	// have the cascading delete fire events on the child objects.
	// So we have to call the trigger ourselves.
	if (trigger.isDelete && trigger.isBefore) {
		set<ID> setContactId = new set<ID>();
		for (SObject obj : trigger.old) {
			setContactId.add(obj.Id);
		}
		list<Volunteer_Hours__c> listHours = new list<Volunteer_Hours__c>();
		listHours = [select Id, Status__c, Volunteer_Shift__c, Volunteer_Job__c, Number_Of_Volunteers__c from Volunteer_Hours__c where Contact__c in :setContactId];
		
		VOL_SharedCode.VolunteerHoursTrigger(listHours, null, false);		
	}

	// in the case of Merge, we've removed the hours from the old shift during the Before Delete.
	// now in the After Delete, we are able to see who the winning contacts are.
	// unfortunately, we can't tell which are the Hours that got moved to the winning contact,
	// so we have to recalc the Shifts rollups for all its hours.
	if (trigger.isDelete && trigger.isAfter) {
		set<ID> setContactId = new set<ID>();
		for (Contact obj : trigger.old) {
			setContactId.add(obj.MasterRecordId);
		}
		list<Volunteer_Hours__c> listHours = new list<Volunteer_Hours__c>();
		listHours = [select Id, Status__c, Volunteer_Shift__c, Volunteer_Job__c, Number_Of_Volunteers__c from Volunteer_Hours__c where Contact__c in :setContactId];
		
		// get all the Hours for the affected Shifts
		set<ID> setShiftId = new set<ID>();
		for (Volunteer_Hours__c hr : listHours) {
			if (hr.Volunteer_Shift__c != null)
				setShiftId.add(hr.Volunteer_Shift__c);
		}
		if (setShiftId.size() > 0) {
			listHours = [select Id, Status__c, Volunteer_Shift__c, Volunteer_Job__c, Number_Of_Volunteers__c from Volunteer_Hours__c where Volunteer_Shift__c in :setShiftId];
			
			VOL_SharedCode.VolunteerHoursTrigger(null, listHours, true);
		}		
	}

	// similar issue with undeletes of contacts.  
	// Salesforce won't fire an undelete trigger on the hours.
	// So we have to call the trigger ourselves.
	if (trigger.isUndelete) {
		set<ID> setContactId = new set<ID>();
		for (SObject obj : trigger.new) {
			setContactId.add(obj.Id);
		}
		list<Volunteer_Hours__c> listHours = new list<Volunteer_Hours__c>();
		listHours = [select Id, Status__c, Volunteer_Shift__c, Volunteer_Job__c, Number_Of_Volunteers__c from Volunteer_Hours__c where Contact__c in :setContactId];
		
		VOL_SharedCode.VolunteerHoursTrigger(null, listHours, false);		
	}


}