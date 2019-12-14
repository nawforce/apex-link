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
/**
* @author Salesforce.org
* @date 2016
* @description Tests for VOL_BATCH_Recurrence.
*/
@isTest
public class VOL_BATCH_Recurrence_TEST {
	
	public static void createRecurringJob() {
		// set up intial test data
        Account acc = new Account(name='Individual Test Account');
        insert acc;
        Contact con = new Contact(Lastname='Testy', AccountId=acc.Id);
        insert con;
        Campaign cmp = new Campaign(recordtypeid=VOL_SharedCode.recordtypeIdVolunteersCampaign, 
        	name='Test Campaign', IsActive=true);
        insert cmp;
        Volunteer_Job__c job = new Volunteer_Job__c(name='Job1', campaign__c=cmp.Id);
        insert job;
        Job_Recurrence_Schedule__c recurSched = new Job_Recurrence_Schedule__c(
        	Days_of_Week__c = 'Sunday',
        	Desired_Number_of_Volunteers__c = 2,
        	Schedule_Start_Date_Time__c = System.now(),
        	Duration__c = 5.00,
        	Volunteer_Job__c = job.id,
        	Weekly_Occurrence__c = 'Every'
    	);
    	insert recurSched;
	}

	@isTest private static void testSingleBatch() {
	    
	    // set future recurrence to low value for initial JRS insert
	    VOL_SharedCode.VolunteersSettings.Recurring_Job_Future_Months__c = 1;
		createRecurringJob();
        List<Volunteer_Shift__c> shifts = [SELECT Id FROM Volunteer_Shift__c];
        integer cShifts = shifts.size();
        system.assert(cShifts >= 0 && cShifts <= 5);
		
        // now increase our future recurrence limit to a larger size and run the batch
        VOL_SharedCode.VolunteersSettings.Recurring_Job_Future_Months__c = 2;
		VOL_BATCH_Recurrence batchRecSched = new VOL_BATCH_Recurrence();
		Test.startTest();
		database.executeBatch(batchRecSched);
		Test.stopTest();
		shifts = [SELECT Id FROM Volunteer_Shift__c];
		integer cShiftsNew = shifts.size() - cShifts;
		system.assert(cShiftsNew >=3 && cShiftsNew <= 5);
	}

}