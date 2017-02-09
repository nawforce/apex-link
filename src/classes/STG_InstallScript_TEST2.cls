/*
    Copyright (c) 2014, Salesforce.org
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
* @date 2014
* @group Settings
* @description Test class for the Install Script for the NPSP package. 
*/@isTest
public with sharing class STG_InstallScript_TEST2 {

    //If the handlers in our default TDTM config are different from what it's in the org (either action or load order),
    //and the user did not flag them as user managed, we need to update them
    public static testmethod void handlersChanged() {
      //Handlers that exist in the org
        List<Trigger_Handler__c> oldHandlers = new List<Trigger_Handler__c>();
        oldHandlers.add(new Trigger_Handler__c(Active__c = true, Asynchronous__c = false, 
              Class__c = 'AFFL_Affiliations_TDTM', Load_Order__c = 1, Object__c = 'Contact', 
              Trigger_Action__c = 'AfterInsert'));
        oldHandlers.add(new Trigger_Handler__c(Active__c = false, Asynchronous__c = false, 
              Class__c = 'REL_Relationships_Cm_TDTM', Load_Order__c = 1, Object__c = 'CampaignMember', 
              Trigger_Action__c = 'AfterInsert;AfterUpdate'));
        oldHandlers.add(new Trigger_Handler__c(Active__c = true, Asynchronous__c = false, 
              Class__c = 'REL_Relationships_Con_TDTM', Load_Order__c = 1, Object__c = 'Contact', 
              Trigger_Action__c = 'AfterInsert;AfterDelete'));
      insert oldHandlers;
      
      //Updated handlers
      List<Trigger_Handler__c> updatedHandlers = new List<Trigger_Handler__c>();
        updatedHandlers.add(new Trigger_Handler__c(Active__c = true, Asynchronous__c = false, 
              Class__c = 'AFFL_Affiliations_TDTM', Load_Order__c = 1, Object__c = 'Contact', 
              Trigger_Action__c = 'AfterInsert;AfterUpdate'));
        updatedHandlers.add(new Trigger_Handler__c(Active__c = true, Asynchronous__c = false, 
              Class__c = 'REL_Relationships_Cm_TDTM', Load_Order__c = 0, Object__c = 'CampaignMember', 
              Trigger_Action__c = 'AfterInsert;AfterUpdate'));
        updatedHandlers.add(new Trigger_Handler__c(Active__c = true, Asynchronous__c = false, 
              Class__c = 'REL_Relationships_Con_TDTM', Load_Order__c = 2, Object__c = 'Contact', 
              Trigger_Action__c = 'AfterInsert;AfterUpdate;AfterDelete'));
      
      Test.startTest();
      STG_InstallScript.insertTdtmDefaults(updatedHandlers);
      Test.stopTest();
      
      //Verify handlers have been updated
      List<Trigger_Handler__c> newHandlers = [select Load_Order__c, Class__c, Trigger_Action__c from Trigger_Handler__c order by Load_Order__c];
        System.assertEquals(0, newHandlers[0].Load_Order__c);   
        System.assertEquals('REL_Relationships_Cm_TDTM', newHandlers[0].Class__c);   
        System.assertEquals('AfterInsert;AfterUpdate', newHandlers[0].Trigger_Action__c);
        System.assertEquals(1, newHandlers[1].Load_Order__c);   
        System.assertEquals('AFFL_Affiliations_TDTM', newHandlers[1].Class__c);   
        System.assertEquals('AfterInsert;AfterUpdate', newHandlers[1].Trigger_Action__c);  
        System.assertEquals(2, newHandlers[2].Load_Order__c);   
        System.assertEquals('REL_Relationships_Con_TDTM', newHandlers[2].Class__c);   
        System.assertEquals('AfterInsert;AfterUpdate;AfterDelete', newHandlers[2].Trigger_Action__c);    
    }
    
    //If the handlers in our default TDTM config are different from what it's in the org (either action or load order),
    //and the user flagged them as user managed, we do not need to update them
    public static testmethod void handlersChanged_UserManaged() {
        //Handlers that exist in the org
        List<Trigger_Handler__c> oldHandlers = new List<Trigger_Handler__c>();
        //Should not be modified 
        oldHandlers.add(new Trigger_Handler__c(Active__c = true, Asynchronous__c = false, 
              Class__c = 'AFFL_Affiliations_TDTM', Load_Order__c = 1, Object__c = 'Contact', 
              Trigger_Action__c = 'AfterInsert', User_Managed__c = true));
        //Should be modified
        oldHandlers.add(new Trigger_Handler__c(Active__c = false, Asynchronous__c = false, 
              Class__c = 'REL_Relationships_Cm_TDTM', Load_Order__c = 1, Object__c = 'CampaignMember', 
              Trigger_Action__c = 'AfterInsert;AfterUpdate'));
        //Should not be modified
        oldHandlers.add(new Trigger_Handler__c(Active__c = true, Asynchronous__c = false, 
              Class__c = 'REL_Relationships_Con_TDTM', Load_Order__c = 2, Object__c = 'Contact', 
              Trigger_Action__c = 'AfterInsert;AfterDelete', User_Managed__c = true));
        insert oldHandlers;
        
        //Updated handlers
        List<Trigger_Handler__c> updatedHandlers = new List<Trigger_Handler__c>();
        updatedHandlers.add(new Trigger_Handler__c(Active__c = true, Asynchronous__c = false, 
              Class__c = 'AFFL_Affiliations_TDTM', Load_Order__c = 1, Object__c = 'Contact', 
              Trigger_Action__c = 'AfterInsert;AfterUpdate'));
        updatedHandlers.add(new Trigger_Handler__c(Active__c = true, Asynchronous__c = false, 
              Class__c = 'REL_Relationships_Cm_TDTM', Load_Order__c = 0, Object__c = 'CampaignMember', 
              Trigger_Action__c = 'AfterInsert;AfterUpdate'));
        updatedHandlers.add(new Trigger_Handler__c(Active__c = true, Asynchronous__c = false, 
              Class__c = 'REL_Relationships_Con_TDTM', Load_Order__c = 1, Object__c = 'Contact', 
              Trigger_Action__c = 'AfterInsert;AfterUpdate;AfterDelete'));
        
        Test.startTest();
        STG_InstallScript.insertTdtmDefaults(updatedHandlers);
        Test.stopTest();
        
        //Verify handlers marked as "User Managed" were not modified
        List<Trigger_Handler__c> newHandlers = [select Load_Order__c, Class__c, Trigger_Action__c from Trigger_Handler__c order by Load_Order__c];
        //Should have been modified
        System.assertEquals('REL_Relationships_Cm_TDTM', newHandlers[0].Class__c);   
        System.assertEquals(0, newHandlers[0].Load_Order__c); 
        System.assertEquals('AfterInsert;AfterUpdate', newHandlers[0].Trigger_Action__c);
        //Should not have been modified  
        System.assertEquals('AFFL_Affiliations_TDTM', newHandlers[1].Class__c);   
        System.assertEquals(1, newHandlers[1].Load_Order__c);
        System.assertEquals('AfterInsert', newHandlers[1].Trigger_Action__c);  
        //Should not have been modified
        System.assertEquals('REL_Relationships_Con_TDTM', newHandlers[2].Class__c); 
        System.assertEquals(2, newHandlers[2].Load_Order__c);     
        System.assertEquals('AfterInsert;AfterDelete', newHandlers[2].Trigger_Action__c);     
    }

    public static testmethod void schedulersChanged() {
        List<Schedulable__c> schedForInsert = new List<Schedulable__c>();
        schedForInsert.add(new Schedulable__c(Name = 'Recurring Donation Updates', Class_Name__c = 'RD_RecurringDonations_BATCH', 
                    Active__c = true, Frequency__c = 'Daily'));
        schedForInsert.add(new Schedulable__c(Name = 'Opportunity Account Rollups', Class_Name__c = 'RLLP_OppAccRollup_BATCH', 
                Active__c = true, Frequency__c = 'Daily'));
        schedForInsert.add(new Schedulable__c(Name = 'Opportunity Contact Rollups', Class_Name__c = 'RLLP_OppContactRollup_BATCH', 
                Active__c = true, Frequency__c = 'Daily'));
        schedForInsert.add(new Schedulable__c(Name = 'Opportunity Household Rollups', Class_Name__c = 'RLLP_OppHouseholdRollup_BATCH', 
                Active__c = false, Frequency__c = 'Daily'));
        schedForInsert.add(new Schedulable__c(Name = 'Opportunity Soft Credit Rollups', Class_Name__c = 'RLLP_OppSoftCreditRollup_BATCH', 
                Active__c = false, Frequency__c = 'Daily'));
        schedForInsert.add(new Schedulable__c(Name = 'Seasonal Address Updates', Class_Name__c = 'ADDR_SEASONAL_SCHED',
                Active__c = false, Frequency__c = 'Daily'));
        schedForInsert.add(new Schedulable__c(Name = 'GAU Allocations Rollups', Class_Name__c = 'ALLO_Rollup_SCHED',
                Active__c = false, Frequency__c = 'Daily'));

        insert schedForInsert;

        //abort already scheduled jobs so test can re-sechedule them
        for(CronTrigger ct : [SELECT Id FROM CronTrigger WHERE CronJobDetail.JobType = '7']) {
            System.abortJob(ct.Id);
        }

        Test.startTest();
        UTIL_MasterSchedulableHelper.scheduleJobsFromOldObject();
        Test.stopTest();

        Set<String> chronJobNames = new Set<String>();
        for(CronTrigger ct : [SELECT CronJobDetail.Name FROM CronTrigger WHERE CronJobDetail.JobType = '7']) {
            chronJobNames.add(ct.CronJobDetail.Name);
        }

        System.assert(chronJobNames.contains('NPSP 00 - Error Processing'));
        System.assert(chronJobNames.contains('NPSP 01 - Opportunity Account Rollups'));
        System.assert(chronJobNames.contains('NPSP 02 - Opportunity Contact Rollups'));
        System.assert(chronJobNames.contains('NPSP 06 - Recurring Donation Updates'));

        System.assert(!chronJobNames.contains('NPSP 03 - Opportunity Household Rollups'));
        System.assert(!chronJobNames.contains('NPSP 04 - Opportunity Soft Credit Rollups'));
        System.assert(!chronJobNames.contains('NPSP 05 - GAU Allocation Rollups'));

    }
}