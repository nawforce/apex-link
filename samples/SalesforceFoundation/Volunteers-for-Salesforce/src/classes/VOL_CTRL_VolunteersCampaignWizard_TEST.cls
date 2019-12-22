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
public with sharing class VOL_CTRL_VolunteersCampaignWizard_TEST {

	/*******************************************************************************************************
	* TEST METHODS
	********************************************************************************************************/
    static testmethod void CodeCoverageTests() {
        PageReference pageRef=Page.VolunteersCampaignWizard; 
        Test.setCurrentPage(pageRef); 
        
        // scenario 1: create a campaign with no cloning or samples.
        VOL_CTRL_VolunteersCampaignWizard ctrl = new VOL_CTRL_VolunteersCampaignWizard();
        ctrl.cmpVols.Name = 'My Test Volunteer Campaign';
        ctrl.cmpVols.StartDate = System.today();
        ctrl.cmpVols.IsActive = true;
        ctrl.campaignIdClone = null;
        ctrl.cSampleJobs = 0;
        ctrl.cSampleShifts = 0;
        Integer cVolCampaigns = ctrl.listSOVolunteersCampaigns.size();
        System.Assert(ctrl.Save() != null);
        VOL_SharedCode volSharedCode = new VOL_SharedCode();        
        System.AssertEquals(cVolCampaigns + 1, volSharedCode.listSOCampaignsWithJobs.size());

        // scenario 2: create a campaign with sample jobs and shifts.
        ctrl = new VOL_CTRL_VolunteersCampaignWizard();
        ctrl.cmpVols.Name = 'My Test Volunteer Campaign 2';
        ctrl.cmpVols.StartDate = System.today().addYears(-1);
        ctrl.cmpVols.IsActive = true;
        ctrl.campaignIdClone = null;
        ctrl.cSampleJobs = 2;
        ctrl.cSampleShifts = 3;
        System.Assert(ctrl.Save() != null);
        System.AssertEquals(cVolCampaigns + 2, volSharedCode.listSOCampaignsWithJobs.size());
        Campaign cmp2 = [select Id, Volunteer_Jobs__c, Volunteer_Shifts__c, StartDate from Campaign where Name = 'My Test Volunteer Campaign 2'];
        system.assertEquals(System.today().addYears(-1), cmp2.StartDate);
        System.AssertEquals(ctrl.cSampleJobs, cmp2.Volunteer_Jobs__c);
        System.AssertEquals(ctrl.cSampleShifts * ctrl.cSampleJobs, cmp2.Volunteer_Shifts__c);        
        list<Volunteer_Shift__c> listShift = [select Id, Name, Volunteer_Job__r.Campaign__c, Start_Date_Time__c 
        	from Volunteer_Shift__c where Volunteer_Job__r.Campaign__c = :cmp2.Id];
        system.assertEquals(2 * 3, listShift.size()); 
        system.assertEquals(cmp2.StartDate, listShift[0].Start_Date_Time__c.date());
        
        // now add some Hours to this campaign for our next test.
        Contact c1 = new Contact(Lastname='My Volunteer Contact');
        insert c1;
        Volunteer_Hours__c hours = new Volunteer_Hours__c(
        	Contact__c = c1.Id,
        	Volunteer_Job__c = listShift[0].Volunteer_Job__c,
        	Volunteer_Shift__c = listShift[0].Id,
        	Start_Date__c = listShift[0].Start_Date_Time__c.Date(),
        	Planned_Start_Date_Time__c = listShift[0].Start_Date_Time__c,
        	Status__c = 'Confirmed'        	
        	);
        insert hours;
        
        // scenario 3: create a campaign with a cloned set of Shifts and Jobs and Volunteer Hours
        ctrl = new VOL_CTRL_VolunteersCampaignWizard();
        ctrl.cmpVols.Name = 'My Test Volunteer Campaign 3';
        ctrl.cmpVols.StartDate = system.today();
        ctrl.cmpVols.IsActive = true;
        ctrl.campaignIdClone = cmp2.Id;
        ctrl.ChangeCampaign();
        ctrl.cmpShiftDates.EndDate = system.today().addmonths(1);
        ctrl.fCopyVolunteers = true;
        ctrl.hoursStatus.status__c = 'Prospect';
        System.Assert(ctrl.Save() != null);
        System.AssertEquals(cVolCampaigns + 3, volSharedCode.listSOCampaignsWithJobs.size());
        Campaign cmp3 = [select Id, Volunteer_Jobs__c, Volunteer_Shifts__c from Campaign where Name = 'My Test Volunteer Campaign 3'];
        System.AssertEquals(cmp2.Volunteer_Jobs__c, cmp3.Volunteer_Jobs__c);
        System.AssertEquals(cmp2.Volunteer_Shifts__c, cmp3.Volunteer_Shifts__c);
        Volunteer_Hours__c hours3 = [select Id, Start_Date__c, Volunteer_Job__c, Volunteer_Shift__c, Planned_Start_Date_Time__c,
        	Status__c, Contact__c from Volunteer_Hours__c where Contact__c = :c1.id and Volunteer_Job__r.Campaign__c = :cmp3.id];
        system.assertEquals('Prospect', hours3.Status__c);
        system.assertEquals(system.today().addmonths(1), hours3.Start_Date__c);
        system.assertEquals(system.today().addmonths(1), hours3.Planned_Start_Date_Time__c.Date());
        
        
        // scenario 4: test validation
        ctrl = new VOL_CTRL_VolunteersCampaignWizard();
        ctrl.cmpVols.Name = 'My Test Volunteer Campaign 4';
        ctrl.cmpVols.StartDate = null;
        ctrl.campaignIdClone = cmp2.Id;
        ctrl.ChangeCampaign();
        ctrl.cSampleJobs = 2;
        ctrl.cSampleShifts = 3;
        System.Assert(ctrl.Save() == null);
        ctrl.campaignIdClone = null;
        ctrl.cSampleJobs = 0;
        System.Assert(ctrl.Save() == null);
        
    }   
}