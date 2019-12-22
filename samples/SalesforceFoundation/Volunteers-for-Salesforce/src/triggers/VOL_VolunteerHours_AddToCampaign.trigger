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

trigger VOL_VolunteerHours_AddToCampaign on Volunteer_Hours__c (after insert, after update) {

	// TASK1: Adding to Campaigns
	// given a list of hours, we need to keep track of all the contacts for a given campaign.
	// with that info, we'll need to see if they have CampaignMember records.
	// if they don't, we'll create a CampaignMember.
	// if they do, we might need to update the CampaignMember.
	set<Id> setContactId = new set<Id>();
	set<Id> setCampaignId = new set<Id>();
	
	// TASK2: Setting Contact's Volunteer Status 
	// if the volunteer hours are being marked confirmed or completed, mark them active.
	set<Id> setContactIdMarkActive = new set<Id>();	
	
	// the lists of Campaign Members to add and update
	list<CampaignMember> listCMToAdd = new list<CampaignMember>();
	list<CampaignMember> listCMToUpdate = new list<CampaignMember>();
	set<Id> setCMIdToUpdate = new set<Id>();
	
	// first we go through the new/updated hours, and find the volunteers and their campaigns
	if (trigger.new != null) {
		for (Volunteer_Hours__c hr : trigger.new) {
			setContactId.add(hr.Contact__c);
			setCampaignId.add(hr.Volunteer_Campaign__c);
		}						
	}
	
	// also need to look for CampaignMember record thats were already there in the update case
	if (trigger.old != null) {
		for (Volunteer_Hours__c hr : trigger.old) {
			setContactId.add(hr.Contact__c);
			setCampaignId.add(hr.Volunteer_Campaign__c);
		}						
	}
	
	// now find all the potential CampaignMembers.  Unfortunately, we can't do a soql
	// that will give us the exact match, so this really is the list of potential CM's.
	// this map's keys will be a concatenation of contactID & campaignID 
	map<string,CampaignMember> mapComboIdToCM = new map<string,CampaignMember>();
	for (CampaignMember cm : [Select Id, ContactId, CampaignId, Status, HasResponded From CampaignMember 
			WHERE ContactId IN :setContactId AND CampaignId IN :setCampaignId]) {
		string comboId = cm.ContactID + '|' + cm.CampaignId;
		mapComboIdToCM.put(comboId, cm);
	}
	
	// now go through our list of hours again, and look for their CampaignMembers
	if (trigger.new != null) {
		for (Volunteer_Hours__c hr : trigger.new) {
			// CAUTION!! Since the Volunteer_Campaign__c calculated field is text, need to cast it to ID to get full 18 chars!
			string comboId = hr.Contact__c + '|' + (ID)hr.Volunteer_Campaign__c;
			
			// do they already have a CampaignMember
			CampaignMember cm = mapComboIdToCM.get(comboId);
			if (cm == null) {
				cm = new CampaignMember(CampaignId = hr.Volunteer_Campaign__c, ContactId = hr.Contact__c, Status=hr.Status__c);
				listCMToAdd.add(cm);
				mapComboIdToCM.put(comboId, cm);	// add it to the map to avoid creating duplicate CM's
			} else {
				cm.Status = hr.Status__c; // NOTE: last hour record wins the CM status.  Too hard to define precedence rules and heuristics.
				// use the set to only put cm's on the list once!
				if (setCMIdToUpdate.add(cm.id)) listCMToUpdate.add(cm);
				mapComboIdToCM.put(comboId, cm);	// add it to the map to avoid creating duplicate CM's
			}

			// keep track of the active volunteers
			if (hr.Status__c == 'Confirmed' || hr.Status__c == 'Completed') {
				setContactIdMarkActive.add(hr.Contact__c);
			}

		}
		
		insert listCMToAdd;
		update listCMToUpdate;
		
		// now take care of setting the active volunteers
		list<Contact> listContactMarkActive = new list<Contact>();
		for (Contact con : [select Id, Volunteer_Status__c from Contact where Id in :setContactIdMarkActive]) {
			if (con.Volunteer_Status__c != 'Active') {
				con.Volunteer_Status__c = 'Active';				
				listContactMarkActive.add(con);
			}
		}
		update listContactMarkActive;
	}

}