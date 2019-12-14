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

@isTest
public with sharing class VOL_CTRL_NewAndEditVRS_TEST {

    //******************************************************************************************************
    // Test Methods
    public static testmethod void TestNewAndEditVRS() {

		// create test data
        Campaign cmp = new Campaign(recordtypeid=VOL_SharedCode.recordtypeIdVolunteersCampaign, 
        	name='Job Test Campaign', IsActive=true);
        insert cmp;
        Volunteer_Job__c job = new Volunteer_Job__c(name='Job1', campaign__c=cmp.Id);
        insert job;
		Contact contact = new Contact(firstname='test', lastname='test');
		insert contact;
        Job_Recurrence_Schedule__c jrs = new Job_Recurrence_Schedule__c(Volunteer_Job__c = job.Id);
		jrs.Days_of_Week__c = 'Monday;Wednesday;Friday';
		jrs.Duration__c = 1;
		integer year = system.today().year();
		jrs.Schedule_Start_Date_Time__c = datetime.newInstance(year, 1, 1, 3, 30, 0);
		jrs.Schedule_End_Date__c = date.newInstance(year, 12, 31);
		jrs.Weekly_Occurrence__c = 'Every';
		jrs.Desired_Number_of_Volunteers__c = 5;
		insert jrs;
		
		// scenario 1: creating a new VRS
		Volunteer_Recurrence_Schedule__c vrs = new Volunteer_Recurrence_Schedule__c();
		ApexPages.StandardController stdCtrl = new ApexPages.StandardController(vrs);		
		VOL_CTRL_NewAndEditVRS ctrl = new VOL_CTRL_NewAndEditVRS(stdCtrl);
		ctrl.vrs.Contact__c = contact.Id; 
		ctrl.vrs.Volunteer_Job__c = job.Id;
		ctrl.vrs.Days_of_Week__c = 'Monday;Friday';
		ctrl.vrs.Duration__c = 1.5;
		ctrl.vrs.Schedule_Start_Date_Time__c = datetime.newInstance(year, 2, 1, 3, 30, 0);
		ctrl.vrs.Schedule_End_Date__c = date.newInstance(year, 6, 3);
		ctrl.vrs.Weekly_Occurrence__c = '1st';
		stdCtrl.Save();
		
		// verify it got saved
		list<Volunteer_Recurrence_Schedule__c> listVRS = [select Id from Volunteer_Recurrence_Schedule__c where Volunteer_Job__c = :job.Id];
		system.assertEquals(1, listVRS.size());
		
		// scenario 2: updating an existing VRS
		vrs = [select Id, Days_of_Week__c from Volunteer_Recurrence_Schedule__c where Id = :listVRS[0].Id];
		ApexPages.StandardController stdCtrl2 = new ApexPages.StandardController(vrs);		
		VOL_CTRL_NewAndEditVRS ctrl2 = new VOL_CTRL_NewAndEditVRS(stdCtrl2);
		system.assertEquals('Monday;Friday', ctrl2.vrs.Days_of_Week__c);
		ctrl2.vrs.Days_of_Week__c = 'Saturday';
		stdCtrl2.Save();
		vrs = [select Id, Days_of_Week__c from Volunteer_Recurrence_Schedule__c where Id = :listVRS[0].Id];
		system.assertEquals('Saturday', ctrl2.vrs.Days_of_Week__c);
		
		// scneario 3: copying from an existing JRS
		ctrl.vrs.Volunteer_Job__c = job.Id;
		system.assert(ctrl.refreshListSOJRS() == null);
		system.assertEquals(2, ctrl.listSOJRS.size());
		ctrl.jrsId = jrs.Id;
		system.assert(ctrl.copyScheduleFromJRS() == null);
		system.assertEquals('Monday;Wednesday;Friday', ctrl.vrs.Days_of_Week__c);
						
    }
	
}