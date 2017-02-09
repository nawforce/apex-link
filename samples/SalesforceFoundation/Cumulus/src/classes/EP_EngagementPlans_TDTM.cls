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
* @group Engagement Plans
* @group-content ../../ApexDocContent/EngagementPlans.htm
* @description Trigger handler for Engagement Plans object. On insert, validates that the Engagement Plan has one lookup
* to a parent object, then creates Tasks associated with that object based on the plan's template.
*/
public with sharing class EP_EngagementPlans_TDTM extends TDTM_Runnable {

    /*******************************************************************************************************
    * @description Trigger Handler on Engagement Plans; creates Tasks based on the associated 
    * template's Engagement Plan Tasks after insert, and prevents modifying lookups after update.
    * This method inserts Task objects directly instead of using DmlWrapper, as DML options are needed on
    * certain Tasks for sending emails.
    * @param listNew the list of Engagement Plans from trigger new. 
    * @param listOld the list of Engagement Plans from trigger old. 
    * @param triggerAction which trigger event (BeforeInsert, AfterInsert, etc.). 
    * @param objResult the describe for Engagement Plans 
    * @return dmlWrapper.  
    ********************************************************************************************************/
    public override DmlWrapper run(List<SObject> newlist, List<SObject> oldlist, 
        TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {
        
        //validate and adderror for poorly constructed engagement plans
        if (triggerAction == TDTM_Runnable.Action.BeforeInsert) {
            for (Integer i = 0; i < newlist.size(); i++) {
                Engagement_Plan__c newEP = (Engagement_Plan__c) newlist[i];
                String targetField = EP_EngagementPlans_UTIL.getTargetObjectField(newEP, true);
            }
        }

        //validate that the user doesn't add two lookups, or modify an existing lookup to a new value
        if (triggerAction == TDTM_Runnable.Action.BeforeUpdate) {
            for (Integer i = 0; i < newlist.size(); i++) {
                Engagement_Plan__c newEP = (Engagement_Plan__c) newlist[i];
                Engagement_Plan__c oldEP = (Engagement_Plan__c) oldlist[i];
                String newTargetField = EP_EngagementPlans_UTIL.getTargetObjectField(newEP, true);
                String oldTargetField = EP_EngagementPlans_UTIL.getTargetObjectField(oldEP, false);
                
                if (!String.isBlank(newTargetField) && !String.isBlank(oldTargetField)) {
                    Id newEpTarget = (Id) newEP.get(newTargetField);
                    Id oldEpTarget = (Id) oldEP.get(oldTargetField);
                    if (newEpTarget != oldEpTarget) {
                        newEP.addError(Label.engagementPlanCantEdit);
                    }
                }
            }
        }

        if (triggerAction == TDTM_Runnable.Action.AfterInsert) {
            List<Task> taskInsertEmail = new List<Task>();
            List<Task> taskInsertNoEmail = new List<Task>();

            List<Engagement_Plan__c> engagementPlans = (List<Engagement_Plan__c>) newlist;

            //EP_Task_UTIL class for Task creation and related functionality
            EP_Task_UTIL taskUtil = new EP_Task_UTIL(engagementPlans);
            // EP_EngagementPlans_UTIL class for calculating which lookup field Engagement Plans use and related operations.
            EP_EngagementPlans_UTIL epUtil = new EP_EngagementPlans_UTIL(engagementPlans);

            for (Engagement_Plan__c engagementPlan : engagementPlans) {
                Engagement_Plan_Template__c template = taskUtil.templateMap.get(engagementPlan.Engagement_Plan_Template__c);
                for (Engagement_Plan_Task__c epTask : template.Engagement_Plan_Tasks__r) {
                    Task newTask = taskUtil.createTask(epTask, engagementPlan);
                    
                    //set target object to WhoId or WhatId based on object type
                    taskUtil.assignTask(newTask, epUtil.targetObjectIdMap.get(engagementPlan.id));

                    //assign owner based on template setting
                    if (newTask.OwnerId == null) {
                        if (template.Default_Assignee__c == EP_EngagementPlans_UTIL.BLANK_ASSIGNMENT_USER_CREATING) {
                            newTask.OwnerId = UserInfo.getUserId();
                        } else {
                            //get the owner of the record this engagement plan is being applied to
                            newTask.OwnerId = epUtil.targetOwnerMap.get(engagementPlan.id);
                        }
                    }

                    //add to appropriate list based on the email setting.
                    if (epTask.Send_Email__c && epTask.Parent_Task__c == null) {
                        taskInsertEmail.add(newTask);
                    } else {
                        taskInsertNoEmail.add(newTask);
                    }
                }
            }

            Database.DMLOptions options = new Database.DMLOptions();
            options.OptAllOrNone = true;

            if (!taskInsertEmail.isEmpty()) {
                options.EmailHeader.triggerUserEmail = true;
                List<Database.SaveResult> results = Database.insert(taskInsertEmail, options);
            }

            if (!taskInsertNoEmail.isEmpty()) {
                options.EmailHeader.triggerUserEmail = false;
                List<Database.SaveResult> results = Database.insert(taskInsertNoEmail, options);
            }
        }

        return null;
    }
}