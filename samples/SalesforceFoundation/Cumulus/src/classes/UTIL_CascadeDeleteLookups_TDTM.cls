/*
    Copyright (c) 2017, Salesforce.org
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
 * @date 2017
 * @group Util
 * @description Extend this to allow cascade delete and undelete of child objects through lookup relationships.
 * 
 * The only differences in functionality with standard cascade deletion is that child records show up separately from
 * their parent in the recycle bin, and all triggers run on cascade deleted objects.
 *
 * To enable cascade delete for a new object type: extend this class, create a static list of Cascades, override the
 * getCascades() method to return that static list, and create a Trigger Handler record for the new class.
 *
 * NOTE: Since standard cascade deletes don't run triggers on deleted children, records in the "cascades" map can also
 * be created for children further down the chain, e.g. grandchildren, great-grandchildren, etc. The relationship to 
 * the deleted parent object can be provided to allow cascade functionality for those parent objects.
 */
//TBD making this class global so managed package users can add lookup cascade deletions
public abstract class UTIL_CascadeDeleteLookups_TDTM extends TDTM_Runnable {

    /*******************************************************************************************************
    * @description Contains information for performing a cascade deletion.
    * All that is needed to perform cascade deletion is the object name and the relationship to the parent
    * object firing the deletion.
    * To define conditions on child records to prevent the cascade deletion from completing, add any fields
    * needed to queryFields, then override the addDeletionErrors
    ********************************************************************************************************/
    public class Cascade {
        public final String objectName;
        public final String parentRelationship;
        //any additional fields needed to identify error conditions in child records
        public List<String> queryFields;

        //allows running deletion validation without actually attempting to delete the records
        public boolean validateOnly;

        //parent objects of trigger set that are firing the deletion
        public Map<Id, SObject> parents;
        //queried child object stash for deletion
        public Map<Id, SObject> children;
        //child to parent relationship
        public Map<Id, Id> childToParent;

        //child records preventing deletion grouped by type per parent record
        public Map<Id, Map<String, List<Id>>> errors;
        
        //constructors
        public Cascade(String objName, String parentRel) {
            parents = new Map<Id, SObject>();
            children = new Map<Id, SObject>();
            childToParent = new Map<Id, Id>();

            validateOnly = false;

            errors = new Map<Id, Map<String, List<Id>>>();

            objectName = objName;
            parentRelationship = parentRel;
            queryFields = new List<String>{UTIL_Describe.getNameField(objName)};
        }
        public Cascade(String objName, String parentRel, List<String> qFields) {
            this(objName, parentRel);
            queryFields.addAll(qFields);
        }
    }

    /*******************************************************************************************************
    * @description Override this method to pass a static list of Cascades to this super class. The list must
    * be static to maintain context between before and after delete triggers.
    * @return List<Cascade> The cascade deletions to perform for this trigger.
    ********************************************************************************************************/
    public virtual List<Cascade> getCascades() {
        return new List<Cascade>();
    }

    public static Boolean disableCascade = false;

    /*******************************************************************************************************
    * @description Performs cascade delete for lookup fields. Stashes records, deletes records, or undeletes
    * records depending on trigger action.
    * @param listNew The list of sobjects from trigger new.
    * @param listOld The list of sobjects from trigger old.
    * @param triggerAction Which action triggered the current context. (Before or After; Insert, Update, 
    * Delete, or Undelete.)
    * @param objResult The field describe object for the current trigger set.
    * @return dmlWrapper Objects that need to be inserted or updated after the processing of the trigger.
    ********************************************************************************************************/
    public override DmlWrapper run(List<SObject> newlist, List<SObject> oldlist, 
        TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {

        if (disableCascade) {
            return null;
        }

        if (triggerAction == TDTM_Runnable.Action.BeforeDelete) {
            doStash(oldlist);
            addDeletionErrors();
            processDeletionErrors(objResult.getSObjectType().getDescribe().getName());
        }

        if (triggerAction == TDTM_Runnable.Action.AfterDelete) {
            //if this trigger context is the result of a merge, exit trigger without performing deletion

            try {
                String masterRecordId = (String)oldlist[0].get('MasterRecordId');
                if (!String.isBlank(masterRecordId)) {
                    return null;
                }
            } catch (Exception ex) {}

            disableCascade = true;
            doDelete();
        }

        if (triggerAction == TDTM_Runnable.Action.AfterUndelete) {
            disableCascade = true;
            doUndelete(newlist);
        }


        return null;
    }

    /*******************************************************************************************************
    * @description Stashes child objects in the before delete event, for cascade deletion in the after delete
    * event.
    * @param deletedParents The list of sobjects of deleted parent objects.
    * @return void This method stashes child objects for deletions in the static variable and returns nothing.
    ********************************************************************************************************/
    private void doStash(List<SObject> deletedParents) {
        for (Cascade cc : getCascades()) {
            cc.parents = new Map<Id,SObject>(deletedParents);

            String childQuery = getQuery(cc);
            cc.children = new Map<Id, SObject>(Database.query(childQuery));

            //relate child ids to parent ids
            for (SObject child : cc.children.values()) {
                Id parent = getParentId(child, cc.parentRelationship);
                cc.childToParent.put(child.id, parent);
            }
        }
    }

    /*******************************************************************************************************
    * @description Override this method to do custom error processing of child records that would prevent
    * a cascade deletion from completing.
    * @return void When overriden, this method should add errors to Cascades using the putError method below.
    ********************************************************************************************************/
    public virtual void addDeletionErrors() {}

    /*******************************************************************************************************
    * @description Facilitates adding deletion errors to a Cascade class; this method should be used in
    * overrides of the addDeletionErrors method.
    * @param cc The cascade to add the error to.
    * @param child The record with the error.
    * @param String The string that describes the error.
    * @return void This method adds to the Cascade's errors and returns nothing.
    ********************************************************************************************************/
    public void putError(Cascade cc, Id child, String error) {
        Id parent = cc.childToParent.get(child);

        //instantiate the map if this is the first error for this parent
        if (!cc.errors.containsKey(parent)) {
            cc.errors.put(parent, new Map<String, List<String>>());
        }

        Map<String,List<Id>> errorMap = cc.errors.get(parent);

        if (!errorMap.containsKey(error)) {
            errorMap.put(error, new List<Id>{child});
        } else {
            errorMap.get(error).add(child);
        }
    }

    /*******************************************************************************************************
    * @description Processes all errors stored in Cascades, creating links to the records with errors and
    * adding the errors to the trigger set records.
    * @param parentObjectName The name of the object firing the delete.
    * @return void This method adds errors to records in the trigger set and returns nothing.
    ********************************************************************************************************/
    private void processDeletionErrors(String parentObjectName){
        String parentNameField = UTIL_Describe.getNameField(parentObjectName);
        for (Cascade cc : getCascades()) {
            for (Id parentId : cc.errors.keySet()) {
                SObject parent = cc.parents.get(parentId);

                String parentName = (String)parent.get(parentNameField);

                //special case Contacts as contact.name is null in the trigger set
                if (String.isBlank(parentName) && parentObjectName == Contact.SObjectType.getDescribe().getName()) {
                    //lastname is required, first name is optional.
                    parentName = (String)parent.get('LastName');
                    if (parent.get('FirstName') != null) {
                        parentName = parent.get('FirstName') + ' ' + parentName;
                    }
                }

                String error = String.format(Label.CascadeDeletionError, new List<String>{parentName}) + '<br/><br/>';
                for (String errType : cc.errors.get(parentId).keySet()) {
                    error += ' ' + errType + ': ' + getErrorLinks(cc,cc.errors.get(parentId).get(errType));
                }
                //don't escape error message with HTML links
                parent.addError(error, false);
            }
        }
    }

    /*******************************************************************************************************
    * @description Creates a list of the child records preventing deletion, with html links to those records
    * with the record's name.
    * @param cc The Cascade these records belong to.
    * @param records The child records preventing deletion.
    * @return String An HTML string with links to the child records.
    ********************************************************************************************************/
    private String getErrorLinks(Cascade cc, List<Id> records) {
        List<String> results = new List<String>{'<ul>'};
        String nameField = (String)UTIL_Describe.getNameField(records[0].getSobjectType().getDescribe().getName());
        for (Id recId : records) {
            String childName = (String)cc.children.get(recId).get(nameField);
            String htmlLink = '<li><a href="#" onclick="window.open(\'/'+recId+'\', \'\');return false;" target="_blank">' + childName + '</a></li>';
            results.add(htmlLink);
        }
        results.add('</ul>');
        return String.join(results,'');
    }

    /*******************************************************************************************************
    * @description Performs cascade delete an object type, allowing for partial success and recording errors.
    * @return void This method deletes records inline and returns nothing.
    ********************************************************************************************************/
    private void doDelete() {
        List<SObject> forDelete = new List<SObject>();
        for (Cascade cc : getCascades()) {
            if (!cc.validateOnly) {
                forDelete.addAll(cc.children.values());
            }
            cc.children.clear();
        }
        if (!forDelete.isEmpty()) {
            List<Database.DeleteResult> delResults = Database.delete(forDelete, false);
            
            ERR_Handler.Errors errorResult = ERR_Handler.getErrors(delResults, forDelete);
            if (errorResult.errorsExist) {
                ERR_Handler.processErrors(errorResult.errorRecords, 'TDTM');
            }
        }
    }

    /*******************************************************************************************************
    * @description Performs cascade undelete for lookup fields.
    * @param deletedParents The list of sobjects of undeleted parent objects.
    * @return void This method undeletes records inline and returns nothing.
    ********************************************************************************************************/
    private void doUndelete(List<SObject> deletedParents) {
        for (Cascade cc : getCascades()) {
            String childQuery = getQuery(cc) + ' AND IsDeleted = true ALL ROWS';

            List<SObject> forUndelete = Database.query(childQuery);
            if (!forUndelete.isEmpty()) {
                List<Database.UndeleteResult> undelResults = Database.undelete(forUndelete, false);
                ERR_Handler.Errors errorResult = ERR_Handler.getErrors(undelResults, forUndelete);
                if (errorResult.errorsExist) {
                    ERR_Handler.processErrors(errorResult.errorRecords, 'TDTM');
                }
            }
        }
    }

    /*******************************************************************************************************
    * @description Creates query for objects to delete or undelete.
    * @param childLookup The combo key containing the to query and the relationship to the parent object.
    * @return String The query.
    ********************************************************************************************************/
    private String getQuery(Cascade cc) {
        String childQuery = 'SELECT ID, ' + cc.parentRelationship;
        if (!cc.queryFields.isEmpty()) {
            childQuery += ', ' + String.join(cc.queryFields, ',');
        }
        childQuery += ' FROM ' + cc.objectName + ' WHERE ' +  cc.parentRelationship + ' IN :deletedParents';

        return childQuery;
    }

    /*******************************************************************************************************
    * @description Returns a child sobject's parent's Id, following cross object references.
    * @param obj The child SObject.
    * @param relationship The relationship to the parent object.
    * @return Id The parent Id
    ********************************************************************************************************/
    private Id getParentId(SObject obj, String relationship) {

        SObject thisObj = obj;
        //separate cross object references, i.e. account.name
        list<string> splitField = (relationship.split('\\.',0));
        
        //remove the field name itself to only include parent object references
        string fieldName = splitField[splitField.size()-1];
        splitField.remove(splitField.size()-1);

        //traverse parent relationships until the last one
        for (string parentObj : splitField) {
            if (thisObj!=null) {
                thisObj = thisObj.getsObject(parentObj);
            }
        }

        return (Id)thisObj.get(fieldName);
    }
}