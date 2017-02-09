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
* @description Implements relationships behavior 
*/
public class REL_Relationships_Con_TDTM extends TDTM_Runnable {
    

    /*******************************************************************************************************
    * @description Trigger Handler on Contact that handles relationship autocreation and gender change.
    * @param listNew the list of Contacts from trigger new. 
    * @param listOld the list of Contacts from trigger old. 
    * @param triggerAction which trigger event (BeforeInsert, AfterInsert, etc.). 
    * @param objResult the describe for Contacts 
    * @return dmlWrapper Any relationships that need DML.
    ********************************************************************************************************/
    public override DmlWrapper run(List<SObject> newList, List<SObject> oldList, 
    TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {

        //Casting them to contacts, since this class should be run only for contacts
        List<Contact> newListCasted = (newList == null ? new List<Contact>(): (List<Contact>)newList);
        List<Contact> oldListCasted = (oldList == null ? new List<Contact>(): (List<Contact>)oldList);
        Map<Id, Contact> oldMap = new Map<Id, Contact>(oldListCasted);
                    
        DmlWrapper dmlWrapper = new DmlWrapper();
        
        List<Id> ContactIds = new List<Id>();
        List<Id> ReciprocalIds = new List<Id>();
        List<String> Types = new List<String>();        
        
        string genderField = UTIL_CustomSettingsFacade.getRelationshipSettings().npe4__Gender_Field__c;
        Set<Id> changedGender = new set<Id>();

        if(newlist != null && newlist.size() > 0) {
            for (Contact c : newListCasted){
                for(String s : REL_Utils.getAutoCreateMap().keySet()){
                    npe4__Relationship_Auto_Create__c rac = REL_Utils.getAutoCreateMap().get(s);
                    if (rac.npe4__Object__c == 'Contact'){
                        Id relatedContactId;
                        try{
                            //get the id from the field                        
                            relatedContactId = (Id)c.get(rac.npe4__Field__c);
                        }catch(Exception e){
                            c.addError(system.Label.npe4.AutoCreateFieldError);
                        }    
                        
                        if(triggerAction == TDTM_Runnable.Action.afterInsert && relatedContactId != null) {                        
                            ContactIds.add(c.Id);
                            ReciprocalIds.add(relatedContactId);
                            Types.add(rac.npe4__Relationship_Type__c);
                        }
                        else if (triggerAction == TDTM_Runnable.Action.afterUpdate && relatedContactId != null 
                        && relatedContactId != oldMap.get(c.id).get(rac.npe4__Field__c)){
                            ContactIds.add(c.Id);
                            ReciprocalIds.add(relatedContactId);
                            Types.add(rac.npe4__Relationship_Type__c);
                        }
                    }               
                }

                //handle updates to the gender field if we have one
                if (triggerAction == TDTM_Runnable.Action.afterUpdate && genderField!=null) {
                    if (c.get(genderField) != oldMap.get(c.id).get(genderField)) {
                        changedGender.add(c.id);
                    }
                }
            }
        }
        
        if(triggerAction == TDTM_Runnable.Action.AfterDelete) {
            DmlWrapper w = deleteEmptyRelationships(oldMap);
            dmlWrapper.objectsToDelete.addAll(w.objectsToDelete);
        }
        
        if (!ContactIds.isEmpty()){
            DmlWrapper w = createNewRelationships(ContactIds, ReciprocalIds, Types);
            dmlWrapper.objectsToInsert.addAll(w.objectsToInsert);
        }

        if (!changedGender.isEmpty()) {
            //query for a map of all relationships where where contact or related contact has changed gender
            map<id,npe4__Relationship__c> mapRelationship = new map<Id,npe4__Relationship__c>(
                [SELECT Id, npe4__Contact__c, npe4__RelatedContact__c, npe4__ReciprocalRelationship__c, npe4__Type__c 
                FROM npe4__Relationship__c WHERE npe4__Contact__c IN :changedGender OR npe4__RelatedContact__c IN :changedGender]
            );
            //make a list of "Related Contact" lookup relationships in our set, these we want to update
            list<npe4__Relationship__c> listRelsForUpdate = new list<npe4__Relationship__c>();
            //make a list of mirror relationships for our first list, we need these for the addType method
            list<npe4__Relationship__c> listMirrorRels = new list<npe4__Relationship__c>();

            //get the relationships that the gender change affects
            for (npe4__Relationship__c rel : mapRelationship.values()) {
                //add the changed gender's relationship and reciprocal
                if (changedGender.contains(rel.npe4__RelatedContact__c)) {
                    listRelsForUpdate.add(rel);
                    listMirrorRels.add(mapRelationship.get(rel.npe4__ReciprocalRelationship__c));
                }
            }

            //update type info
            REL_Relationships_TDTM.addType(listMirrorRels, listRelsForUpdate);
            dmlWrapper.objectsToUpdate.addAll((list<sObject>)listRelsForUpdate);
        }

        return dmlWrapper;
    }
    
    /*******************************************************************************************************
    * @description Deletes Relationships when a Contact is deleted
    * @param contacts the Map of Contacts from trigger oldMap. 
    * @return dmlWrapper Relationships to delete.
    ********************************************************************************************************/
    public static DmlWrapper deleteEmptyRelationships(Map<Id, Contact> contacts) {
        DmlWrapper dmlWrapper = new DmlWrapper();
        List<SObject> relationshipsToDelete = new List<SObject>();

        // The Contacts primary Relationships have already been cascade deleted in Contact After Delete context
        // Using ALL ROWS returns them in the query used to gather the mirror Relationships to be deleted 
        for (npe4__Relationship__c r : [Select Id, npe4__ReciprocalRelationship__c from npe4__Relationship__c 
            where npe4__Contact__c in :contacts.keySet() ALL ROWS]){
            if (r.npe4__ReciprocalRelationship__c!=null) {
                relationshipsToDelete.add(new npe4__Relationship__c(Id = r.npe4__ReciprocalRelationship__c));
            }
        }
        if ( relationshipsToDelete.size() > 0 ) {
            dmlWrapper.objectsToDelete.addAll(relationshipsToDelete);
        }
        return dmlWrapper;
    }
    

    public static DmlWrapper createNewRelationships(List<Id> ContactIds, List<Id> RelatedContactIds, List<String> Types) {
        
        DmlWrapper dmlWrapper = new DmlWrapper();
        Integer i = 0;
        List<SObject> relationshipsForInsert = new List<SObject>();
        
        //used to hold existing relationships for the group of contacts and check for dupes
        List<npe4__Relationship__c> existingRelationships = new List<npe4__Relationship__c>();
        Map<Id, List<npe4__Relationship__c>> relationshipsByContact = new Map<Id, List<npe4__Relationship__c>>();
        
        if (!UTIL_CustomSettingsFacade.getRelationshipSettings().npe4__Allow_AutoCreated_Duplicates__c) {
            
            existingRelationships = [select Id, npe4__Contact__c, npe4__RelatedContact__c, npe4__Type__c 
            from npe4__Relationship__c where npe4__Contact__c IN :ContactIds limit 2000];
            
            //sort by npe4__Contact__c so we can pull relationships based on the inserted Contact
            for(npe4__Relationship__c r : existingRelationships){
                if(relationshipsByContact.containsKey(r.npe4__Contact__c)){
                    List<npe4__Relationship__c> rList = relationshipsByContact.get(r.npe4__Contact__c);
                    rList.add(r);
                    relationshipsByContact.put(r.npe4__Contact__c, rList);
                }
                else{
                    relationshipsByContact.put(r.npe4__Contact__c, new List<npe4__Relationship__c>{r});
                }               
            }        
        }        
            
        for (Id conId : ContactIds){
            boolean doNotCreate = false;
            
            //prevent duplicates from being created if they exist and setting is enabled
            if (relationshipsByContact.containsKey(conId)){
                for (npe4__Relationship__c r : relationshipsByContact.get(conId)){
                    if (r.npe4__Type__c == Types[i] && r.npe4__RelatedContact__c == RelatedContactIds[i]){
                        doNotCreate = true;                     
                        break;
                    }                   
                }    
            }
            
            if (!doNotCreate){
                UTIL_Debug.debug('****Creating relationship in createNewRelationships method');
                npe4__Relationship__c r = new npe4__Relationship__c(
                    npe4__Contact__c = conId, 
                    npe4__RelatedContact__c = RelatedContactIds[i],
                    npe4__Type__c = Types[i],           
                    npe4__Status__c = 'Current'                 
                );
                relationshipsForInsert.add(r);
            }
            i++;
        }       
        
        if (!relationshipsForInsert.isEmpty()){
            dmlWrapper.objectsToInsert.addAll(relationshipsForInsert);
        }
        
        return dmlWrapper;
    }
}