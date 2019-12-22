/*
    Copyright (c) 2013, Salesforce.org
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
* @group TDTM
* @group-content ../../ApexDocContent/TDTM.htm
* @description Class called from each of the triggers (only one per object) that follow the TDTM design. It's in 
* charge of figuring out which of the classes that do the actual work need to be called, and calling them.
*/
public class TDTM_TriggerHandler {
    
    private static Boolean defaultRecordsInserted = false;
    
    public static boolean suppressDebugAssertAfterErrorLogging = false;

    public static boolean disableTDTM = false;
    
    /*******************************************************************************************************
    * @description Method to be called from each of the triggers (only one per object) that follow the TDTM 
    * design. Figures out which classes need to be called, and calls them.
    * @param isBefore Are we in a before trigger context.
    * @param isAfter Are we in an after trigger context.
    * @param isInsert Did the trigger run in response to an insert operation.
    * @param isUpdate Did the trigger run in response to an update operation.
    * @param isDelete Did the trigger run in response to a delete operation.
    * @param isUnDelete Did the trigger run in response to an undelete operation.
    * @param newList The records that were passed to the trigger as trigger.new.
    * @param oldList The records that were passed to the trigger as trigger.old.
    * @param describeObj The type of SObject the class runs for.
    * @param dao The class that is going to retrieve all the TDTM records.
    * @return void
    */
    public void run(Boolean isBefore, Boolean isAfter, Boolean isInsert, Boolean isUpdate,
    Boolean isDelete, Boolean isUnDelete, List<Sobject> newList, List<Sobject> oldList, 
    Schema.DescribeSobjectResult describeObj, TDTM_iTableDataGateway dao) {
    	if (disableTDTM) {
           UTIL_Debug.debug(LoggingLevel.WARN, '****TDTM Disabled: no triggers will run for this transaction.');
           return;
        }

        if(newList != null) {
           UTIL_Debug.debug(LoggingLevel.WARN, '****Trigger.new size: ' + newList.size());
        }

        TDTM_Runnable.Action thisAction = TDTM_TriggerActionHelper.determineTriggerAction(isBefore,
                                                      isAfter, isInsert, isUpdate, isDelete, isUnDelete);
        UTIL_Debug.debugWithInfo('****ACTION: ' + thisAction + ' on ' + describeObj.getLabel());

        TDTM_Runnable.DmlWrapper dmlWrapper = new TDTM_Runnable.DmlWrapper();

        // If there are no records, insert the defaults.
        if(!defaultRecordsInserted && dao.isEmpty()) {
            List<Trigger_Handler__c> defaultConfig = TDTM_DefaultConfig.getDefaultRecords();
            insert defaultConfig;
            defaultRecordsInserted = true;
        }

        // Get the classes to run. Note that we are using the local object name, so it works for objects
        // from Cumulus both in the package and unmanaged dev orgs.
        List<SObject> classesToCall = dao.getclassesToCallForObject(describeObj.getLocalName(), thisAction);
        UTIL_Debug.debug(LoggingLevel.WARN, '****Classes To Call for ' + describeObj.getLocalName() + ': ' + JSON.serializePretty(classesToCall));

        if(classesToCall != null && classesToCall.size() > 0) {
            //Run the relevant classes
            for (SObject classToCall : classesToCall) {
                TDTM_Runnable.DmlWrapper dmlWrapperLocal = new TDTM_Runnable.DmlWrapper();
                
                try {
                    dmlWrapperLocal = runClass(classToCall, newList, OldList, thisAction, describeObj);
                } catch (Exception ex) {
                    //use the correct list
                    List<SObject> populatedList = newList == null || newList.isEmpty() ? oldList : newList;
                    //if we're only doing DML on one object and the caught exception is handled,
                    //trap the error and add the message to the page.
                    if (populatedList.size() == 1 && ERR_ExceptionHandler.isHandledException(ex)) {
                        ERR_ExceptionHandler.beautifyExceptionMessage(ex);
                        populatedList[0].addError(ex);
                        return;
                    } else {
                        throw ex;
                    }
                }

                if(dmlWrapperLocal != null) {
                    if(dmlWrapperLocal.objectsToInsert != null && !dmlWrapperLocal.objectsToInsert.isEmpty())
                        dmlWrapper.objectsToInsert.addAll(dmlWrapperLocal.objectsToInsert);
                    if(dmlWrapperLocal.objectsToUpdate != null && !dmlWrapperLocal.objectsToUpdate.isEmpty())
                        dmlWrapper.objectsToUpdate.addAll(dmlWrapperLocal.objectsToUpdate);
                    if(dmlWrapperLocal.objectsToDelete != null && !dmlWrapperLocal.objectsToDelete.isEmpty())
                        dmlWrapper.objectsToDelete.addAll(dmlWrapperLocal.objectsToDelete);
                    if(dmlWrapperLocal.objectsToUndelete != null && !dmlWrapperLocal.objectsToUndelete.isEmpty())
                        dmlWrapper.objectsToUndelete.addAll(dmlWrapperLocal.objectsToUndelete);
                }
                UTIL_Debug.debugWithInfo('****Finished executing: ' + classToCall.get('Class__c'));
            }

            
            //Process the result
            if(dmlWrapper != null) {
                try {
                    processDML(dmlWrapper);
                } catch (Exception ex) {
                    //use the correct list
                    List<SObject> populatedList = newList == null || newList.isEmpty() ? oldList : newList;
                    //if we're only doing DML on one object and the caught exception is handled,
                    //trap the error and add the message to the page.
                    if (populatedList.size() == 1 && ERR_ExceptionHandler.isHandledException(ex)) {
                        ERR_ExceptionHandler.beautifyExceptionMessage(ex);
                        populatedList[0].addError(ex);
                        return;
                    } else {
                        throw ex;
                    }
                }
            }
        }
    }

    private TDTM_Runnable.DmlWrapper runClass(SObject classToRunRecord, List<Sobject> newList, List<Sobject> oldList, 
    TDTM_Runnable.Action thisAction, Schema.DescribeSobjectResult describeObj) {
    	        
        if(classToRunRecord != null) {
	        String classToRunName = String.valueOf(classToRunRecord.get('Class__c'));
	        Type classType = Type.forName(classToRunName);
	        
	        if(classType != null) {   	
	        	Object classInstance = classType.newInstance();
	        	
	        	//The objects we create dynamically need to implement the TDTM_Runnable interface
	        	if(classInstance instanceof TDTM_Runnable) {
			        TDTM_Runnable classToRun = (TDTM_Runnable)classInstance;
			        Boolean async = Boolean.valueOf(classToRunRecord.get('Asynchronous__c'));
			        boolean isBefore = (thisAction == TDTM_Runnable.Action.BeforeInsert || 
			             thisAction == TDTM_Runnable.Action.BeforeUpdate || thisAction == TDTM_Runnable.Action.BeforeDelete); 
			        
			        //A class can only run asynchronously (calling a future) if we have the ids of the
                    //records we want to affect. This means we can only run it for after in insert. 
                    //Also,a future cannot be called from a future or batch method.
			        if(async && !isBefore && !System.isFuture() && !System.isBatch()) {
			        	UTIL_Debug.debug(LoggingLevel.WARN, '****Calling asynchronously: ' + classToRunName);
			        	runAsync(classToRun, classToRunName, newList, oldList, thisAction, describeObj);
			        } else {
			        	UTIL_Debug.debugWithInfo('****Calling synchronously: ' + classToRunName);
                        return classToRun.run(newlist, oldlist, thisAction, describeObj);
			        }
	        	} else {
	        		UTIL_Debug.debug(LoggingLevel.WARN, '****The class does not implement the required interface.');
	        	}
	        } else {
	        	UTIL_Debug.debug(LoggingLevel.WARN, '****ClassType was null.');
	        }
        } else {
        	UTIL_Debug.debug(LoggingLevel.WARN, '****ClassToCall was null');
        }
        return null;
    }
    
    private void runAsync(TDTM_Runnable classToRun, String classToRunName, List<Sobject> newList, 
    List<Sobject> oldList, TDTM_Runnable.Action thisAction, Schema.DescribeSobjectResult describeObj) {
       set<Id> setNewId;
       if(newlist != null) {
           Map<Id,Sobject> nm = new Map<Id,Sobject>(newlist);
           setNewId = new set<Id>(nm.keySet());
       }
       
       set<Id> setOldId;
       if(oldlist != null) {
           Map<Id,Sobject> om = new Map<Id,SObject>(oldlist);
           setOldId = new set<Id>(om.keySet());
       }
       
       classToRun.runFutureNonStatic(setNewId, setOldId, thisAction.name(), describeObj.getName(), classToRunName);
    }
    
    /*******************************************************************************************************
    * @description Performs pending DML operations, on the records stored in the DMLWrapper that it gets as parameter.
    * We want this method to be static so that it can be called from the future method in TDTM_Runnable.
    * @param dmlWrapper The class that contains the records that need to have DML performed on.
    * @return void
    */
    public static void processDML(TDTM_Runnable.DmlWrapper dmlWrapper) {
        processDMLAllOrNothing(dmlWrapper, true);
    }

    /*******************************************************************************************************
    * @description Performs pending DML operations, on the records stored in the DMLWrapper that it gets as parameter.
    * We want this method to be static so that it can be called from the future method in TDTM_Runnable.
    * @param dmlWrapper The class that contains the records that need to have DML performed on.
    * @param allOrNothing whether all objects must save successfully.  if true, and there is an error, an exception
    * will be thrown.  If false, then inspect the error collection to see what failed
    * @return Errors Object with list of Error objects
    */
    public static ERR_Handler.Errors processDMLAllOrNothing(TDTM_Runnable.DmlWrapper dmlWrapper, boolean allOrNothing) {
        if (dmlWrapper == null)
            return null;
                    
        dmlWrapper.groupByType(); //sort thyself!
        
        /**We keep the Database methods with the opt_allOrNone flag set to false to be able to alert the client 
        process of all the errors that occurred in the context **/
        List<Database.SaveResult> insertResults = Database.insert(dmlWrapper.objectsToInsert, allOrNothing);        
        List<Database.SaveResult> updateResults = Database.update(dmlWrapper.objectsToUpdate, allOrNothing);        
        List<Database.DeleteResult> deleteResults = Database.delete(dmlWrapper.objectsToDelete, allOrNothing);        
        List<Database.UndeleteResult> undeleteResults = Database.undelete(dmlWrapper.objectsToUndelete, allOrNothing); //Not yet using this one, afaik, but no reason not to support it.
        
        //@TODO: We cannot support upsert because you cannot do upsert on SObject. You have to specify the actual type.        
        //Also, should we support Database.merge?
        
        //Process the results. 
        ERR_Handler.Errors errors = ERR_Handler.getErrors(insertResults, updateResults, deleteResults, undeleteResults, 
            dmlWrapper.objectsToInsert, dmlWrapper.objectsToUpdate, dmlWrapper.objectsToDelete, 
            dmlWrapper.objectsToUndelete);
                    
        return errors;
    }
}