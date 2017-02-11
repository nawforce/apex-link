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
public with sharing class VOL_CTRL_VolunteersReportHours_TEST {

    //==================== TEST METHOD(s) ======================================
    public static testmethod void CodeCoverageTests() {

        // create test bucket account
        Account acc = new Account(Name='Test Individual Bucket Account');
        insert acc;

        Campaign cmp = new Campaign(recordtypeid=VOL_SharedCode.recordtypeIdVolunteersCampaign, name='Test Job Campaign', IsActive=true);
        insert cmp;
        Volunteer_Job__c job = new Volunteer_Job__c(name='Job1', campaign__c=cmp.Id, Display_on_Website__c=true);
        insert job;
        Volunteer_Shift__c shift = new Volunteer_Shift__c(Volunteer_Job__c=job.Id, Duration__c=1, Start_Date_Time__c=System.now());
        insert shift;        
        
        // exercise some initial settings code
        System.Assert(VOL_SharedCode.VolunteersSettings != null);
        
        // specify our settings        
        Volunteers_Settings__c settings = new Volunteers_Settings__c();
        settings.Signup_Bucket_Account_On_Create__c = 'Test Individual Bucket Account';
        VOL_SharedCode.getVolunteersSettingsForTests(settings);
        
        PageReference pageRef=Page.VolunteersReportHours; 
        Test.setCurrentPage(pageRef); 

        //instantiate the controller 
        VOL_CTRL_VolunteersReportHours ctrl = new VOL_CTRL_VolunteersReportHours();

        // scenario 1: creating a new contact to the bucket account.
        Contact contact = ctrl.contact;
        System.Assert(contact != null);
        contact.FirstName = 'TestFirstName';
        contact.LastName = 'TestLastName';
        contact.Email = 'foovolunteer@bar.com';
        ctrl.volunteerJobId = job.Id;
        ctrl.vhours.Start_Date__c = System.Today();
        ctrl.vhours.End_Date__c = System.Today();
        ctrl.vhours.Hours_Worked__c = 1;
        ctrl.Save();
        System.AssertEquals(system.label.labelVolunteerReportHoursThankYou, ctrl.strSaveResult);
                
        // make sure it got created correctly
        contact = [select Id, AccountId from Contact where Email='foovolunteer@bar.com' and Firstname='TestFirstName' limit 1];
        System.Assert(contact.Id != null);
        System.AssertEquals(VOL_SharedCode.SettingsBucketAccountId, contact.AccountId);        
        Volunteer_Hours__c vh = [select Id, Status__c from Volunteer_Hours__c where contact__c = :contact.Id];
        system.assertEquals('Completed', vh.Status__c);
        
        // scenario 2: update to an existing contact
        contact = ctrl.contact;
        System.Assert(contact != null);
        contact.FirstName = 'TestFirstName';
        contact.LastName = 'TestLastName';
        contact.Email = 'foovolunteer@bar.com';
        ctrl.volunteerJobId = job.Id;
        ctrl.volunteerShiftId = shift.Id;
        ctrl.ChangeVolunteerShift();
        ctrl.Save();
        System.AssertEquals(system.label.labelVolunteerReportHoursThankYou, ctrl.strSaveResult);
                
        // make sure the existing contact's volunteer status field was updated          
        contact = [select Id, Volunteer_Status__c, Phone from Contact where Email='foovolunteer@bar.com' and Firstname='TestFirstName' limit 1];
        System.AssertEquals('Active', contact.Volunteer_Status__c);
        Volunteer_Hours__c vh2 = [select Id, Status__c, Hours_Worked__c from Volunteer_Hours__c where Volunteer_Shift__c = :shift.Id];
        system.assertEquals('Completed', vh2.Status__c);
        system.assertEquals(1, vh2.Hours_Worked__c);
        
        // scenario 3: update existing Hours record
        ctrl.volunteerJobId = job.Id;
        ctrl.volunteerShiftId = shift.Id;
        ctrl.ChangeVolunteerShift();
		ctrl.vhours.Hours_Worked__c = 2;
        ctrl.Save();
        System.AssertEquals(system.label.labelVolunteerReportHoursThankYou, ctrl.strSaveResult);
        vh2 = [select Id, Status__c, Hours_Worked__c from Volunteer_Hours__c where Volunteer_Shift__c = :shift.Id];
        system.assertEquals('Completed', vh2.Status__c);
        system.assertEquals(2, vh2.Hours_Worked__c);
        
                
        // anything else
        System.Assert(ctrl.strURLtoCSSFile == ctrl.strURLtoCSSFile);
        System.Assert(ctrl.listSOVolunteerJobs != null);
        ctrl.volunteerJobId = job.Id;
        ctrl.ChangeVolunteerJob();
        System.Assert(ctrl.listSOVolunteerShifts != null);
        system.assertEquals(true, ctrl.hasShifts);
        
               
    }       

}