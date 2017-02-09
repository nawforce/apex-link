/*
    Copyright (c) 2016 Salesforce.org
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
* @description Trigger Handler on Engagement Plan Tasks 
*/
public class EP_EngagementPlanTaskValidation_TDTM extends TDTM_Runnable {
    
    /*******************************************************************************************************
    * @description Trigger Handler on Engagement Plan Tasks that prevents recursion on dependent tasks and
    * ensures that dependent and parent tasks belong to the same template.
    * @param listNew the list of Engagement Plan Tasks from trigger new. 
    * @param listOld the list of Engagement Plan Tasks from trigger old. 
    * @param triggerAction which trigger event (BeforeInsert, AfterInsert, etc.). 
    * @param objResult the describe for Engagement Plan Tasks 
    * @return null.  
    ********************************************************************************************************/
    public override DmlWrapper run(List<SObject> listNew, List<SObject> listOld, 
        TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {
        // A list of the tasks that require validation.
        List<Engagement_Plan_Task__c> listToValidateDependencies = new List<Engagement_Plan_Task__c>();
        // AFTER INSERT
        if (triggerAction == TDTM_Runnable.Action.AfterInsert) {
            for (SObject newSO : listNew) {
                Engagement_Plan_Task__c newEPTask = (Engagement_Plan_Task__c)newSO;
                if (newEPTask.Parent_Task__c != null) {
                    listToValidateDependencies.add(newEPTask);
                }
            }
            if (listToValidateDependencies.size() > 0) {
                validateDependencies(listToValidateDependencies);
            }
        }

        // AFTER UPDATE
        if (triggerAction == TDTM_Runnable.Action.AfterUpdate) {
            // List of Engagement Plan Tasks where the template has changed
            List<Engagement_Plan_Task__c> listToValidateChildDependencies = new List<Engagement_Plan_Task__c>();
            // Convert list of old tasks to a map of old tasks.
            Map<Id, Engagement_Plan_Task__c> mapOld = new Map<Id, Engagement_Plan_Task__c>((List<Engagement_Plan_Task__c>)listOld);

            for (SObject newSO : listNew) {
                Engagement_Plan_Task__c newEPTask = (Engagement_Plan_Task__c)newSO;
                Engagement_Plan_Task__c oldEPTask = mapOld.get(newEPTask.Id);

                if (newEPTask.Parent_Task__c != null
                    && (newEPTask.Parent_Task__c != oldEPTask.Parent_Task__c
                        || newEPTask.Engagement_Plan_Template__c != oldEPTask.Engagement_Plan_Template__c)) {
                        listToValidateDependencies.add(newEPTask);
                } 
                if (newEPTask.Parent_Task__c == null && newEPTask.Engagement_Plan_Template__c != oldEPTask.Engagement_Plan_Template__c) {
                    listToValidateChildDependencies.add(newEPTask);
                }
            }
            if (listToValidateDependencies.size() > 0) {
                validateDependencies(listToValidateDependencies);
            }
            if (listToValidateChildDependencies.size() > 0) {
                validateChildDependencies(listToValidateChildDependencies);
            }
        }

        return null;
    }

    /*******************************************************************************************************
    * @description For the Tasks that have dependencies and had the dependency changed,
    * prevent redundant dependencies and mismatched templates
    * @param listToValidate the list of EP Tasks that require dependency validation. 
    * @return void.  
    ********************************************************************************************************/
    private static void validateDependencies(List<Engagement_Plan_Task__c> listToValidate) {
        // A set of the Engagement Plan Template Ids related to the tasks.
        Set<Id> templateIds = new Set<Id>();        

        for (Engagement_Plan_Task__c newEPTask : listToValidate) {
            templateIds.add(newEPTask.Engagement_Plan_Template__c);
        }
        // A map of related Engagement Plan Template Ids and corresponding Template objects with their Tasks
        Map<Id, Engagement_Plan_Template__c> mapIdToTemplateWithTasks = new Map<Id, Engagement_Plan_Template__c>(
            [SELECT Id,
                (SELECT Id, Parent_Task__c, Parent_Task__r.Engagement_Plan_Template__c, Engagement_Plan_Template__c
                FROM Engagement_Plan_Tasks__r)
            FROM Engagement_Plan_Template__c WHERE Id IN :templateIds]);
        
        // Loop through the lists of tasks check for dependency recursion and ensure template matches the dependent tasks
        for (Engagement_Plan_Task__c newEPTask : listToValidate) {
            Map<Id, Engagement_Plan_Task__c> mapExistingEPTasks =
                new Map<Id, Engagement_Plan_Task__c>(
                    mapIdToTemplateWithTasks.get(newEPTask.Engagement_Plan_Template__c).Engagement_Plan_Tasks__r);
            if (newEPTask.Parent_Task__c != null) {
                // If the dependent task does not belong to the same template add an error
                if (!mapExistingEPTasks.containsKey(newEPTask.Parent_Task__c)) {
                    newEPTask.addError(Label.EPTaskTemplate);
                } else {
                    // Traverse through the dependency chain to verify the dependency only occurs once in the chain
                    Set<Id> taskIds = new Set<Id>{newEPTask.Id};
                    Engagement_Plan_Task__c dependentTask = mapExistingEPTasks.get(newEPTask.Parent_Task__c);
                    Integer count = 0;
                    while (dependentTask.Parent_Task__c != null
                            && mapExistingEPTasks.containsKey(dependentTask.Parent_Task__c)
                            && count < EP_EngagementPlans_UTIL.MAX_ITERATIONS) {
                        if (taskIds.contains(dependentTask.Parent_Task__c)) {
                            newEPTask.addError(Label.EPTaskDependency);
                            break;
                        }
                        taskIds.add(dependentTask.Parent_Task__c);
                        dependentTask = mapExistingEPTasks.get(dependentTask.Parent_Task__c);
                        count++;
                    }
                }
            }
        }
    }
    /*******************************************************************************************************
    * @description For the Tasks that are updated and have the EP Template changed,
    * query for any child tasks to verify the templates are the same
    * @param listToValidate the list of EP Tasks that require child dependency validation. 
    * @return void.  
    ********************************************************************************************************/
    private static void validateChildDependencies(List<Engagement_Plan_Task__c> listToValidate) {
        // Engagement Plan Task mapped to tasks that are dependent on it to check for template mismatch
        Map<Id, Engagement_Plan_Task__c> mapIdToTaskWithChildren = new Map<Id, Engagement_Plan_Task__c>(
            [SELECT Id, 
                (SELECT Id, Parent_Task__c, Engagement_Plan_Template__c
                FROM Engagement_Plan_Tasks__r)
                FROM Engagement_Plan_Task__c WHERE Id IN :listToValidate]);
        // loop through the tasks where the Engagement Plan Template has chained and check for dependent tasks
        for (Engagement_Plan_Task__c newEPTask : listToValidate) {
            // If one or more tasks are dependent on this task then verify they are from the same template
            if (mapIdToTaskWithChildren.get(newEPTask.Id).Engagement_Plan_Tasks__r.size() > 0) {
                for (Engagement_Plan_Task__c childTask : mapIdToTaskWithChildren.get(newEPTask.Id).Engagement_Plan_Tasks__r) {
                    if (childTask.Engagement_Plan_Template__c != newEPTask.Engagement_Plan_Template__c) {
                        newEPTask.addError(Label.EPTaskTemplate);
                    }
                }
            }
        }
    }
}