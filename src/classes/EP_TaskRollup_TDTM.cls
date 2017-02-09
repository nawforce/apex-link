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
* @description Trigger Handler on Tasks 
*/
public class EP_TaskRollup_TDTM extends TDTM_Runnable {
    // newOldTaskMap - track changes after workflow rules run
    private static Map<Id, Task> mapNewOldTask = new Map<Id, Task>();
    
    /*******************************************************************************************************
    * @description Trigger Handler on Tasks related to Engagement Plans to rollup total tasks and total 
    * completed task to the parent Engagement Plan record
    * @param listNew the list of Tasks from trigger new. 
    * @param listOld the list of Tasks from trigger old. 
    * @param triggerAction which trigger event (BeforeInsert, AfterInsert, etc.). 
    * @param objResult the describe for Tasks 
    * @return dmlWrapper.  
    ********************************************************************************************************/
    public override DmlWrapper run(List<SObject> listNew, List<SObject> listOld, 
        TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {
        DmlWrapper dmlWrapper = new DmlWrapper();
        
        Set<Id> epIdsToUpdate = new Set<Id>();
        
        // AFTER INSERT | DELETE | UNDELETE
        if (triggerAction == TDTM_Runnable.Action.AfterInsert ||
            triggerAction == TDTM_Runnable.Action.AfterDelete ||
            triggerAction == TDTM_Runnable.Action.AfterUndelete) {
            // When list new is unavailable i.e. after delete use the old list
            List<SObject> listToCheck = listNew != null ? listNew : listOld;

            for (SObject newSO : listToCheck) {
                Task newTask = (Task)newSo;
                if (newTask.Engagement_Plan__c != null) {
                    epIdsToUpdate.add(newTask.Engagement_Plan__c);
                    mapNewOldTask.put(newTask.Id, newTask);
                }
            }
            if (epIdsToUpdate.size() > 0) {
                updateTaskCountOnEP(epIdsToUpdate, dmlWrapper);
            } 
        }

        // AFTER UPDATE
        if (triggerAction == TDTM_Runnable.Action.AfterUpdate) {
            // Convert the old list from trigger to a map
            Map<Id, Task> mapOld = new Map<Id, Task>((List<Task>)listOld);

            for (SObject newSO : listNew) {
                Task newTask = (Task)newSo;
                Task oldTask;
                if (mapNewOldTask.containsKey(newTask.Id)) {
                    oldTask = mapNewOldTask.get(newTask.Id);
                } else {
                    oldTask = mapOld.get(newTask.Id);
                    mapNewOldTask.put(newTask.Id, newTask);
                }
                if (newTask.Engagement_Plan__c != oldTask.Engagement_Plan__c
                    || (newTask.Engagement_Plan__c != null
                        && newTask.IsClosed != oldTask.IsClosed)) {
                    if (newTask.Engagement_Plan__c == null) {
                        epIdsToUpdate.add(oldTask.Engagement_Plan__c);
                    } else {
                        epIdsToUpdate.add(newTask.Engagement_Plan__c);        
                    }
                }
            }
            if (epIdsToUpdate.size() > 0) {
                updateTaskCountOnEP(epIdsToUpdate, dmlWrapper);
            }
        }

        return dmlWrapper;
    }

    /*******************************************************************************************************
    * @description Update passed in Engagement Plans total tasks and total completed task
    * @param epIdsToUpdate the set of EP Ids that require updates. 
    * @param dmlWrapper to hold the ep objects to be updated
    * @return void.  
    ********************************************************************************************************/
    private static void updateTaskCountOnEP(Set<Id> epIdsToUpdate, DmlWrapper dmlWrapper) {
        // map of engagement plan records to be updated
        Map<Id, Engagement_Plan__c> mapIdsToEP = new Map<Id, Engagement_Plan__c>();

        for (AggregateResult aggRes : [SELECT Engagement_Plan__c, IsClosed, COUNT(Id) cnt
                FROM Task WHERE Engagement_Plan__c IN :epIdsToUpdate GROUP BY ROLLUP(Engagement_Plan__c, IsClosed)]) {
            Id epId = (Id)aggRes.get(UTIL_Namespace.StrTokenNSPrefix('Engagement_Plan__c'));
            Boolean taskComp = (Boolean)aggRes.get('IsClosed');
            Decimal taskCount = (Decimal)aggRes.get('cnt');
            if (epId != null) {
                if (!mapIdsToEP.containsKey(epId)) {
                    mapIdsToEP.put(
                        epId,
                        new Engagement_Plan__c(Id = epId, Completed_Tasks__c = 0, Total_Tasks__c = 0));
                }
                if (taskComp != null && taskComp) {
                    mapIdsToEP.get(epId).Completed_Tasks__c = taskCount;
                } else if (taskComp == null) {
                    mapIdsToEP.get(epId).Total_Tasks__c = taskCount;
                }
            }
        }
        dmlWrapper.objectsToUpdate.addAll((List<SObject>)mapIdsToEP.values());
    }
}