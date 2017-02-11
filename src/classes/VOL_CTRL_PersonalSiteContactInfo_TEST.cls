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
public with sharing class VOL_CTRL_PersonalSiteContactInfo_TEST {

    //******************************************************************************************************
    // Test Code
    public static testmethod void TestPage() {
    	// run our tests as the Site Guest User
    	// documentation says The runAs method doesn't enforce user permissions or field-level permissions, only record sharing.
    	// However, the test would fail if I set up the test data as system, and then only did the page controller testing
    	// as guest, because under the Site's Guest User Profile, it didn't see any completed volunteer hours.  This is
    	// inconsistent with the real behavior we see with this page that correctly allows the guest profile to view and edit
    	// information previously created by a different user.
    	// anyways, I figured doing the whole test under the Sites Guest User Profile was better than not trying at all!
    	list<User> listU = [Select Username, UserType, Name, IsActive, Id From User where IsActive = true and UserType = 'Guest' and 
    		Name like '%Site Guest User%'];
    	if (listU.size() > 0) {
	    	system.debug('Running test as Sites Guest User: ' + listU[0]);
	    	system.runAs(listU[0]) {
	    		testPSC();
	    	}
    	} else {
    		system.debug('Running test as system.');
    		testPSC();
    	}
    }
    	
    private static void testPSC() {
		// create test data
        Campaign cmp = new Campaign(recordtypeid=VOL_SharedCode.recordtypeIdVolunteersCampaign, 
        	name='Volunteer Personal Site Test Campaign', IsActive=true);
        insert cmp;
        Volunteer_Job__c job = new Volunteer_Job__c(name='Job1', campaign__c=cmp.Id);
        insert job;
		Contact contact = new Contact(firstname='test', lastname='test');
		insert contact;
		
		Job_Recurrence_Schedule__c jrs = new Job_Recurrence_Schedule__c(
			Volunteer_Job__c = job.Id,
			Days_of_Week__c = 'Monday;Friday',
			Duration__c = 1.5,
			Schedule_Start_Date_Time__c = datetime.now(),
			Weekly_Occurrence__c = '1st');
		insert jrs;
		system.assertNotEquals(null, jrs.Id);
    	
		Volunteer_Recurrence_Schedule__c vrs = new Volunteer_Recurrence_Schedule__c(
			Contact__c = contact.Id, 
			Volunteer_Job__c = job.Id,
			Days_of_Week__c = 'Monday;Friday',
			Duration__c = 1.5,
			Schedule_Start_Date_Time__c = datetime.now(),
			Weekly_Occurrence__c = '1st');
		insert vrs;
		system.assertNotEquals(null, vrs.Id);
    	// setup page
        PageReference pageRef = Page.PersonalSiteContactInfo;
        pageRef.getParameters().put('contactId', contact.Id);     
        system.assertNotEquals(null, contact.Id);   
        Test.setCurrentPage(pageRef); 

        //instantiate the controller 
        VOL_CTRL_PersonalSiteContactInfo ctrl = new VOL_CTRL_PersonalSiteContactInfo();
        System.AssertNotEquals(null, ctrl.listStrHoursFields);
        System.AssertNotEquals(null, ctrl.contactEdit);
        System.AssertNotEquals(null, ctrl.contactReadOnly);
        System.AssertNotEquals(null, ctrl.contactId);
        System.AssertNotEquals(null, ctrl.listCompletedVolunteerHours);
        System.AssertEquals(0, ctrl.listCompletedVolunteerHours.size());
        System.AssertNotEquals(null, ctrl.listUpcomingVolunteerHours);
        System.AssertNotEquals(0, ctrl.listUpcomingVolunteerHours.size());
        ctrl.showMoreRowsCompleted();
        ctrl.showMoreRowsUpcoming();
        
        // mark some hours completed
        list<Volunteer_Hours__c> listHours = [select Id, Status__c from Volunteer_Hours__c where Contact__c = :contact.Id];
        system.assertNotEquals(null, listHours);
        system.assert(listHours.size() > 2);
        listHours[0].Status__c = 'Completed';
        listHours[1].Status__c = 'Completed';
        update listHours;
        
        system.assertNotEquals(null, ctrl.getChartData());
        system.assertEquals('', ctrl.strRank); // null because we don't have enough other volunteers in test data.
        
        system.assertEquals(null, ctrl.Edit());
        system.assertEquals(null, ctrl.Cancel());
        
        ctrl.contactEdit.LastName = 'TestIsNowTesty';
        ctrl.save();
        Contact con = [select Id, LastName from Contact where Id = :ctrl.contactId];
        system.assertEquals('TestIsNowTesty', con.LastName);
        
        // CancelShift
        ctrl.hoursId = listHours[0].Id;
        ctrl.cancelShift();
        listHours = [select Id, Status__c from Volunteer_Hours__c where Contact__c = :contact.Id and Status__c = 'Canceled'];
        system.assertEquals(1, listHours.size());

		// hit constructors of PersonalSite wrappers on our other pages to ensure code coverage
        VOL_CTRL_PersonalSiteJobCalendar ctrlJC = new VOL_CTRL_PersonalSiteJobCalendar();
        system.assertNotEquals(null, ctrlJC);
        system.assertEquals('en-us', ctrlJC.strLanguage);
        VOL_CTRL_PersonalSiteJobListing ctrlJL = new VOL_CTRL_PersonalSiteJobListing();
        system.assertNotEquals(null, ctrlJL);
        system.assertEquals('en-us', ctrlJL.strLanguage);
        VOL_CTRL_PersonalSiteReportHours ctrlRH = new VOL_CTRL_PersonalSiteReportHours();
        system.assertNotEquals(null, ctrlRH);
        system.assertEquals('en-us', ctrlRH.strLanguage);
		        
    }
	
}