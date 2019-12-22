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
public with sharing class VOL_CTRL_VolunteersJobListingFS_TEST {

    //==================== TEST METHOD(s) ======================================
    public static testmethod void UnitTest1() {

        // set up intial test data
        Account acc = new Account(name='Test Individual Bucket Account');
        insert acc;
        Contact con = new Contact(Lastname='Testy', Firstname='Test', Email='test@testy.com', AccountId=acc.Id);
        insert con;
        Campaign cmp = new Campaign(recordtypeid=VOL_SharedCode.recordtypeIdVolunteersCampaign, name='Test Job Campaign', IsActive=true);
        insert cmp;
        Volunteer_Job__c job = new Volunteer_Job__c(name='Job1', campaign__c=cmp.Id, Display_on_Website__c=true);
        insert job;
        Volunteer_Shift__c shift = new Volunteer_Shift__c(Volunteer_Job__c=job.Id, Duration__c=2, Start_Date_Time__c=System.now());
        insert shift;        
        
        // specify our settings        
        System.Assert(VOL_SharedCode.VolunteersSettings != null);
        Volunteers_Settings__c settings = new Volunteers_Settings__c();
        settings.Signup_Bucket_Account_On_Create__c = 'Test Individual Bucket Account';
        VOL_SharedCode.getVolunteersSettingsForTests(settings);
        
        PageReference pageRef = Page.VolunteersJobListingFS;
        pageRef.getParameters().put('ShowJobName', '1');
        pageRef.getParameters().put('ShowDescription', '1');
        pageRef.getParameters().put('ShowLocationAddress', '1');
        pageRef.getParameters().put('ShowLocationInfo', '1');
        pageRef.getParameters().put('ShowShifts', '1');
        pageRef.getParameters().put('ShowSkills', '1');
        Test.setCurrentPage(pageRef); 

        //instantiate the controller 
        VOL_CTRL_VolunteersJobListingFS ctrl = new VOL_CTRL_VolunteersJobListingFS();
        System.AssertEquals(null, ctrl.campaignIdFilter);
        System.AssertEquals(null, ctrl.jobIdFilter);
        System.AssertEquals(true, ctrl.ShowJobName);
        System.AssertEquals(true, ctrl.ShowDescription);
        System.AssertEquals(true, ctrl.ShowLocationAddress);
        System.AssertEquals(true, ctrl.ShowLocationInfo);
        System.AssertEquals(true, ctrl.ShowShifts);
        System.AssertEquals(true, ctrl.ShowSkills);
        System.AssertEquals(null, ctrl.contact.Id);
        System.AssertEquals(null, ctrl.jobIdSignUp);
        System.AssertEquals(null, ctrl.shiftIdSignUp);
        System.AssertEquals(1, ctrl.vhours.Number_of_Volunteers__c);
        System.AssertEquals(null, ctrl.strJobDisplayName);
        System.AssertEquals(null, ctrl.strShiftDisplayName);
        System.AssertEquals(null, ctrl.contactIdSignUp);
        System.AssertEquals(null, ctrl.volunteerHoursIdSignUp);
        System.Assert(ctrl.strURLtoCSSFile == ctrl.strURLtoCSSFile);
        System.Assert(ctrl.listVolunteerJobs.size() > 0);
        
        integer cJobs = ctrl.listVolunteerJobs.size();
        Volunteer_Job__c job2 = new Volunteer_Job__c(name='Job2', campaign__c=cmp.Id, Display_on_Website__c=true);
        insert job2;
        ctrl.listVolunteerJobs = null;
        System.AssertEquals(cJobs + 1, ctrl.listVolunteerJobs.size());

        ctrl.ClearVolunteerHoursIdSignUp();
        
        // sign someone up
        System.AssertEquals(null, ctrl.contact.Id);
        System.AssertEquals(null, ctrl.volunteerHoursIdSignUp);
        ctrl.contact.FirstName = 'Test';
        ctrl.contact.LastName = 'Testy';
        ctrl.contact.Email = 'test@testy.com';
		ctrl.contact.MailingState='wa';        
        ctrl.jobIdSignUp = job.id;
        ctrl.shiftIdSignUp = shift.id;     
        ctrl.VolunteerShiftSignUp();
        System.AssertEquals(con.Id, ctrl.contactIdSignUp);
        System.Assert(ctrl.volunteerHoursIdSignUp != null);
        Volunteer_Hours__c vh = [select Volunteer_Job__c, Volunteer_Shift__c, Hours_Worked__c, Contact__c from Volunteer_Hours__c 
        	where Volunteer_Job__c = :job.Id];
        system.assertEquals(shift.Duration__c, vh.Hours_Worked__c);
        
        // pass cmp.Id as filter, verify only 2 jobs.
        // pass Show params with 0, verify values.
        pageRef = Page.VolunteersJobListingFS;
        pageRef.getParameters().put('ShowJobName', '0');
        pageRef.getParameters().put('ShowDescription', '0');
        pageRef.getParameters().put('ShowLocationAddress', '0');
        pageRef.getParameters().put('ShowLocationInfo', '0');
        pageRef.getParameters().put('ShowShifts', '0');
        pageRef.getParameters().put('ShowSkills', '0');
        pageRef.getParameters().put('campaignId', cmp.Id);
        Test.setCurrentPage(pageRef); 

        //instantiate the controller 
        ctrl = new VOL_CTRL_VolunteersJobListingFS();
        System.AssertEquals(2, ctrl.listVolunteerJobs.size());
        System.AssertEquals(cmp.Id, ctrl.campaignIdFilter);
        
        // create hours for new person, verify contact created
        ctrl.ClearVolunteerHoursIdSignUp();
        ctrl.contact.FirstName = 'New person';
        ctrl.contact.LastName = 'New person for my test';
        ctrl.contact.Email = 'newperson@volunteersjoblisting.com';
        ctrl.contact.MailingState = 'or';
        ctrl.jobIdSignUp = job.id;
        ctrl.shiftIdSignUp = shift.id;
        ctrl.VolunteerShiftSignUp();
        System.Assert(ctrl.contactIdSignUp != null);
        System.Assert(ctrl.volunteerHoursIdSignUp != null);
        
        // test shift reuse
        ID hoursId = ctrl.volunteerHoursIdSignUp;
        System.AssertEquals(1, ctrl.vhours.Number_Of_Volunteers__c);
        ctrl.vhours.Number_Of_Volunteers__c = 2;        
        ctrl.ClearVolunteerHoursIdSignUp();
        ctrl.VolunteerShiftSignUp();
        System.Assert(ctrl.contactIdSignUp != null);
        System.Assert(ctrl.volunteerHoursIdSignUp != null);
        System.AssertEquals(hoursId, ctrl.volunteerHoursIdSignUp);
        vh = [select Number_Of_Volunteers__c from Volunteer_Hours__c where Id = :hoursId];
        System.AssertEquals(2, vh.Number_Of_Volunteers__c);
    }

    public static testmethod void testCampaignHierarchies() {

		//create our test data
		map<string, Campaign> mapCmp = VOL_SharedCode_TEST.mapCampaignTestHierarchy();
		ID cmpIdGrandparent = mapCmp.get('Grandparent').Id;
		system.assertNotEquals(null, cmpIdGrandparent);
    
		// verify w/ no hierarchy
        PageReference pageRef = Page.VolunteersJobListingFS;
        pageRef.getParameters().put('campaignId', cmpIdGrandparent);
        Test.setCurrentPage(pageRef); 
        VOL_CTRL_VolunteersJobListingFS ctrl = new VOL_CTRL_VolunteersJobListingFS();
		system.assertEquals(1, ctrl.listVolunteerJobs.size()); 

		// verify w/ hierarchy
        PageReference pageRef2 = Page.VolunteersJobListingFS;
        pageRef2.getParameters().put('campaignId', cmpIdGrandparent);
        pageRef2.getParameters().put('ShowCampaignHierarchy', '1');
        Test.setCurrentPage(pageRef2); 
        VOL_CTRL_VolunteersJobListingFS ctrl2 = new VOL_CTRL_VolunteersJobListingFS();
		system.assertEquals(mapCmp.size(), ctrl2.listVolunteerJobs.size()); 
    }

}