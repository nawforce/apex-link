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
public class REL_Relationships_TDTM extends TDTM_Runnable {

    public override DmlWrapper run(List<SObject> newlist, List<SObject> oldlist, 
    TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {
    	
    	List<npe4__Relationship__c> newListCasted = (newList == null ? new List<npe4__Relationship__c>(): (List<npe4__Relationship__c>)newList);
        List<npe4__Relationship__c> oldListCasted = (oldList == null ? new List<npe4__Relationship__c>(): (List<npe4__Relationship__c>)oldList);
    	
		return runForRelationships(newListCasted, oldListCasted, triggerAction);
    }
    
    /// <name> REL_Relationships </name>
    /// <summary> Overloads the REL_Relationships object constructor to handle Relationship processing </summary>
    /// <param name="Relationships"> Relationship objects that are being triggered </param>
    /// <param name="oldRelationships"> Relationships object values before trigger event </param>
    /// <param name="ta"> Trigger action that is occuring </param>
    private DmlWrapper runForRelationships(List<npe4__Relationship__c> newlist, List<npe4__Relationship__c> oldlist, 
    TDTM_Runnable.Action triggerAction) {
    	    
    	DmlWrapper mainWrapper = new DmlWrapper();
    	
        // Relationship list for termination updates
        List<npe4__Relationship__c> origrelationships = new List<npe4__Relationship__c>();
        List<npe4__Relationship__c> relationshipsToCreate = new List<npe4__Relationship__c>();
        List<npe4__Relationship__c> relationshipsToUpdate = new List<npe4__Relationship__c>();
        List<npe4__Relationship__c> relationshipsToDelete = new List<npe4__Relationship__c>();
        List<npe4__Relationship__c> reciprocalsToUpdate = new List<npe4__Relationship__c>();
        
        if (triggerAction == TDTM_Runnable.Action.afterUpdate) {
            REL_ProcessControl.hasRun = true;
        }
        
        Integer i = 0;          // processing counter            
        for(npe4__Relationship__c r : newlist) {
            // AFTER INSERT
            if (triggerAction == TDTM_Runnable.Action.afterInsert) {
                if (r.npe4__ReciprocalRelationship__c == null) {
                    relationshipsToCreate.add(r);
                }
                if (r.npe4__ReciprocalRelationship__c != null) {
                    reciprocalsToUpdate.add(r);
                }
            }
            
            // AFTER UPDATE
            if (triggerAction == TDTM_Runnable.Action.afterUpdate) {   
                if (r.npe4__ReciprocalRelationship__c == oldlist[i].npe4__ReciprocalRelationship__c 
                && r.npe4__RelatedContact__c != null) {
                    relationshipsToUpdate.add(r);
                    origRelationships.add(oldlist[i]);
                }
                if (r.npe4__RelatedContact__c == null) {
                    relationshipsToDelete.add(r);
                }
            }

            i++;
        }

        // AFTER DELETE
        if (triggerAction == TDTM_Runnable.Action.AfterDelete) {
            Map<Id, npe4__Relationship__c> deletedRelMap = new Map<Id, npe4__Relationship__c>(oldlist);
            for (npe4__Relationship__c rel : oldList) {
                
                //check that the recirocal relationship isn't already in the deletion trigger set before adding for deletion
                if (rel.npe4__ReciprocalRelationship__c != null && !deletedRelMap.containsKey(rel.npe4__ReciprocalRelationship__c)) {
                    relationshipsToDelete.add(new npe4__Relationship__c(Id=rel.npe4__ReciprocalRelationship__c));
                }
            }
        }

        if (relationshipsToCreate.size() > 0 ) {
        	DmlWrapper wrapper = createRelationship(relationshipsToCreate);
            mainWrapper.objectsToInsert.addAll(wrapper.objectsToInsert);
            mainWrapper.objectsToUpdate.addAll(wrapper.objectsToUpdate);
        }
        if (relationshipsToUpdate.size() > 0 ) {
            DmlWrapper wrapper = updateRelationship(relationshipsToUpdate, origRelationships);
            mainWrapper.objectsToUpdate.addAll(wrapper.objectsToUpdate);
        }
        if (reciprocalsToUpdate.size() > 0 ) {
            DmlWrapper wrapper = reciprocate(reciprocalsToUpdate);
            mainWrapper.objectsToUpdate.addAll(wrapper.objectsToUpdate);
            
        }
        if (relationshipsToDelete.size() > 0 ) {
            mainWrapper.objectsToDelete.addAll((List<SObject>)relationshipsToDelete);
        }

        if (triggerAction == TDTM_Runnable.Action.afterUpdate) {
            REL_ProcessControl.hasRun = false;
        }
        return mainWrapper;
    }

    /// <name> reciprocate </name>
    /// <summary>  </summary>
    /// <param name="Relationships">  </param>
    public static DmlWrapper reciprocate(npe4__Relationship__c[] Relationships) {
    	DmlWrapper dmlWrapper = new DmlWrapper();
        List<SObject> relationshipsToUpdate = new List<SObject>();
        for(npe4__Relationship__c r : Relationships) {
        	UTIL_Debug.debug('****Creating reciprocal relatioship');
            npe4__Relationship__c copy = new npe4__Relationship__c(Id = r.npe4__ReciprocalRelationship__c, 
                                                                    npe4__ReciprocalRelationship__c = r.Id);
            relationshipsToUpdate.add(copy);
        }
        dmlWrapper.objectsToUpdate.addAll(relationshipsToUpdate);
        return dmlWrapper;
    }
    
    /// <name> createRelationship </name>
    /// <summary> Inserts a new reciprocal Relationship After a relationship is Inserted</summary>
    /// <param name="Relationships">  </param>
    public static DmlWrapper createRelationship(npe4__Relationship__c[] Relationships) {
    	
    	DmlWrapper dmlWrapper = new DmlWrapper();
    	
        List<SObject> relationshipsToCreate = new List<SObject>();
                
        for(npe4__Relationship__c r : Relationships) {
        	UTIL_Debug.debug('****Creating relationship in createRelationship method');
            npe4__Relationship__c copy = new npe4__Relationship__c();              
            copy.npe4__Contact__c = r.npe4__RelatedContact__c;
            copy.npe4__RelatedContact__c = r.npe4__Contact__c;
            copy.npe4__Status__c = r.npe4__Status__c;
            copy.npe4__ReciprocalRelationship__c = r.Id;
            copy.npe4__Description__c = r.npe4__Description__c;
            copy.npe4__SYSTEM_SystemCreated__c = true;
            relationshipsToCreate.add(copy);
        }
        
        //evaluate and add the type
        addType(Relationships, relationshipstoCreate);      
        dmlWrapper.objectsToInsert.addAll(relationshipsToCreate);
        //inspectSaveResults(sr, relationshipsToCreate);
        
        return dmlWrapper;
    }

    /// <name> updateRelationship </name>
    /// <summary> Updates the Status and/or Type of a relationship After its reciprocal has been updated </summary>
    /// <param name="Relationships">  </param>
    /// <param name="oldRelationships">  </param>
    public static DmlWrapper updateRelationship(npe4__Relationship__c[] Relationships, 
    npe4__Relationship__c[] oldRelationships){
    	
    	DmlWrapper dmlWrapper = new DmlWrapper();
    	
        List<npe4__Relationship__c> originalRelationships = new List<npe4__Relationship__c>();
        List<SObject> relationshipsTypesToUpdate = new List<SObject>();
        List<SObject> reciprocalsToUpdate = new List<SObject>();
        Integer counter = 0;                                            // processing counter       

        for(npe4__Relationship__c r : Relationships) {
        	
            //SWA 2009-05-13 removed a duplicate check for change to npe4__Status__c from this OR statement
            if ((r.npe4__Status__c != oldRelationships[counter].npe4__Status__c 
            || r.npe4__Type__c != oldRelationships[counter].npe4__Type__c 
            || r.npe4__Description__c != oldRelationships[counter].npe4__Description__c) 
            && r.npe4__ReciprocalRelationship__c != null) {
            	UTIL_Debug.debug('****Creating relationship in updateRelationship method');
                npe4__Relationship__c copy = new npe4__Relationship__c(Id=r.npe4__ReciprocalRelationship__c);
                
                //SWA 2009-05-13 - only attempt to modify the Contact field if the Related Contact  has changed
                if(oldRelationships[counter].npe4__RelatedContact__c!=r.npe4__RelatedContact__c) {
                    copy.npe4__Contact__c = r.npe4__RelatedContact__c;
                }
                copy.npe4__Status__c = r.npe4__Status__c;
                copy.npe4__Description__c = r.npe4__Description__c;
                
                //only recalculate type on reciprocal if the original type has changed
                if (r.npe4__Type__c != oldRelationships[counter].npe4__Type__c) {
                    relationshipsTypesToUpdate.add(copy);
                    originalRelationships.add(r);
                } else {
                    reciprocalsToUpdate.add(copy);
                }

            }
            counter++;
        }
        //add type info as needed and update
        if (!originalRelationships.isEmpty() && !relationshipsTypesToUpdate.isEmpty()) {
            addType(originalRelationships, relationshipsTypesToUpdate);
            dmlWrapper.objectsToUpdate.addAll(relationshipsTypesToUpdate);
        }
        //update reciprocals without type change
        if (!reciprocalsToUpdate.isEmpty()){
            dmlWrapper.objectsToUpdate.addAll(reciprocalsToUpdate);
        }
        return dmlWrapper;
    }
    
    public static void addType(list<npe4__Relationship__c> rList, List<SObject> rCopy) {
        
        npe4__Relationship_Settings__c rs = UTIL_CustomSettingsFacade.getRelationshipSettings();
        
        //used for tracking place in array
        integer counter = 0;            
            
        if (rs.npe4__Reciprocal_Method__c == 'List Setting'){            
            map<string, npe4__Relationship_Lookup__c> rlMap = UTIL_ListCustomSettingsFacade.getMapRelationshipLookupSettings().clone();
        
            //remove inactive values
            for (string s : rlMap.keySet()){
                npe4__Relationship_Lookup__c thisRl = rlMap.get(s);
                if (thisRl.npe4__Active__c != true){
                    rlMap.remove(s);
                }
            }
        
            list<id> rIDs = new list<id>();
                
            for (npe4__Relationship__c r : rList){
            //    rIDs.add(r.npe4__RelatedContact__c);
                rIDs.add(r.npe4__Contact__c);
            }            
            
            String query_string = 'select id, Salutation';            
            String gender_field = '';
            
            if (rs.npe4__Gender_Field__c != null){
                gender_field = rs.npe4__Gender_Field__c;
                query_string += ', ' + gender_field;                
            }
            
            query_string += ' from Contact where Id IN :rIDs';                         
            
            map<id, Contact> cMap = new map<id,Contact>((List<Contact>)database.query(query_string));
            
            for(npe4__Relationship__c r : rList){
                
                npe4__Relationship__c copy = (npe4__Relationship__c)rCopy[counter];

                Contact relatedContact = cMap.get(r.npe4__Contact__c);
            
                if (rlMap.containsKey(r.npe4__Type__c)){
                    List<String> malelist = system.label.npe4.Male.split(',');                                                                                                        
                    List<String> femalelist = system.label.npe4.Female.split(',');
                    
                    Set<String> maleset = new Set<String>();
                    Set<String> femaleset = new Set<String>();
                    maleset.addAll(malelist);
                    femaleset.addAll(femalelist);                    
                                        
                    //was a gender field defined? 
                    if (gender_field != null && gender_field != '' && relatedContact.get(gender_field) != null){                        
                        
                        String contact_gender = (String)relatedContact.get(gender_field);                                               
                        String match_type = '';                        
                        
                        //try male
                        for (String s : malelist){
                            if (contact_gender.toLowerCase() == s.toLowerCase().trim()){
                                match_type = 'male';
                                break;
                            }
                        }
                        //if we haven't found it, try female
                        if(match_type == ''){
                            for (String s : femalelist){
                                if (contact_gender.toLowerCase() == s.toLowerCase().trim()){
                                    match_type = 'female';
                                    break;
                                }
                            }
                        }     
                        
                        if (match_type == 'male' && rlMap.get(r.npe4__Type__c).npe4__Male__c != null)
                            copy.npe4__Type__c = rlMap.get(r.npe4__Type__c).npe4__Male__c;
                        else if (match_type == 'female' && rlMap.get(r.npe4__Type__c).npe4__Female__c != null)                            
                            copy.npe4__Type__c = rlMap.get(r.npe4__Type__c).npe4__Female__c;
                        else
                            copy.npe4__Type__c = rlMap.get(r.npe4__Type__c).npe4__Neutral__c;
                    }
                    else if ((relatedContact.Salutation == 'Mr.' || maleset.contains(relatedContact.Salutation))  
                    && rlMap.get(r.npe4__Type__c).npe4__Male__c != null){
                        copy.npe4__Type__c = rlMap.get(r.npe4__Type__c).npe4__Male__c;
                    }
                    else if (((relatedContact.Salutation == 'Ms.' || relatedContact.Salutation == 'Mrs.') 
                    || femaleset.contains(relatedContact.Salutation)) && rlMap.get(r.npe4__Type__c).npe4__Female__c 
                    != null){
                        copy.npe4__Type__c = rlMap.get(r.npe4__Type__c).npe4__Female__c;
                    }
                    
                    //can't match up gender, bad field or otherwise
                    else{                       
                        if (copy.npe4__Type__c==null)
                            copy.npe4__Type__c = rlMap.get(r.npe4__Type__c).npe4__Neutral__c;
                    }          
                }                            
                //no matching custom list setting, use provided type
                else{
                    copy.npe4__Type__c = r.npe4__Type__c;
                }                
                counter++;
            }            
        }
        
        else if(rs.npe4__Reciprocal_Method__c == 'Value Inversion'){
            for (npe4__Relationship__c r : rList){                           
                //instead lets split the type
                List<string> splitrelationship = r.npe4__Type__c.split(system.label.npe4.Relationship_Split);
                String newstring = '';
                if (splitrelationship.size() > 1)
                    newstring = splitrelationship[1] + system.label.npe4.Relationship_Split + splitrelationship[0];
                else
                    newstring = r.npe4__Type__c;
            
                npe4__Relationship__c castedObject = (npe4__Relationship__c)rCopy[counter];
                castedObject.npe4__Type__c = newstring;
                counter++;
            }
        }
        else{
            for (npe4__Relationship__c r : rList){
            	npe4__Relationship__c castedObject = (npe4__Relationship__c)rCopy[counter];
                castedObject.npe4__Type__c = r.npe4__Type__c;
                counter++; 
            }   
        }
    }
    
    /*******************************************************************************************************
    * @description Remove all duplicate Relationships, for the list of Contacts.  
    * Called by handleContactMergeFixupsFuture() after a Contact Merge has occurred.
    * @param listCon The list of Contacts 
    * @return null
    ********************************************************************************************************/
    public static void cleanupContactRelationships(list<Contact> listCon) {

        // use dmlWrapper to handle all of our database operations
        TDTM_Runnable.DmlWrapper dmlWrapper = new TDTM_Runnable.DmlWrapper();    
        
        // for each Contact, get its list of Relationships in oldest to newest order (of LastModifiedDate)
        map<Id, list<npe4__Relationship__c>> mapConIdListRel = new map<Id, list<npe4__Relationship__c>>();
        
        // first we get all Relationships involved
        list<npe4__Relationship__c> listRel = [select Id, npe4__Contact__c, npe4__RelatedContact__c,
            npe4__Status__c, npe4__Type__c, npe4__Description__c
            from npe4__Relationship__c 
            where npe4__Contact__c in :listCon
            order by npe4__Contact__c, LastModifiedDate asc];

        // now put each contact's relationships on their own list            
        for (npe4__Relationship__c rel : listRel) {
            list<npe4__Relationship__c> listConRel = mapConIdListRel.get(rel.npe4__Contact__c);
            if (listConRel == null) {
                listConRel = new list<npe4__Relationship__c>();
                mapConIdListRel.put(rel.npe4__Contact__c, listConRel);
            }
            listConRel.add(rel);
        }
        
        // now we go through each contact's list of relationships to find duplicates
        for (Contact con : listCon) {
        
            // skip this contact if they have relationships to deal with 
            if (!mapConIdListRel.containsKey(con.Id))
               continue;

            // create a map of relKeys to relationship's to find our duplicates
            map<string, list<npe4__Relationship__c>> mapRelKeyRel = new map<string, list<npe4__Relationship__c>>(); 
            for (npe4__Relationship__c rel : mapConIdListRel.get(con.Id)) {
                string relKey = getRelKey(rel);
                listRel = mapRelKeyRel.get(relKey);
                if (listRel == null) {
                    listRel = new list<npe4__Relationship__c>();
                    mapRelKeyRel.put(relKey, listRel);
                }
                listRel.add(rel);                
            }
            
            // now we have the lists of duplicate relationships, remove the duplicates.            
            for (string relKey : mapRelKeyRel.keySet()) {
                listRel = mapRelKeyRel.get(relKey);
                
                // keep the oldest and delete the others
                for (integer i = 1; i < listRel.size(); i++) {
                    dmlWrapper.objectsToDelete.add(listRel[i]);
                }
            }
        }
        
        // perform our updates & deletes for dealing with the duplicate relationships
        TDTM_TriggerHandler.processDml(dmlWrapper); 
    }

    /*******************************************************************************************************
    * @description Returns a unique string key that represents the data of a Relationship, which can be used
    * to detect duplicate Relationships. 
    * @param rel The Relationship 
    * @return String The relKey
    ********************************************************************************************************/
    private static string getRelKey(npe4__Relationship__c rel) {
        if (rel == null)
            return null;
        string relKey = 
            rel.npe4__Contact__c + ':' + 
            rel.npe4__RelatedContact__c + ':' +
            rel.npe4__Type__c + ':' +
            rel.npe4__Status__c + ':' +
            rel.npe4__Description__c;
        return relKey;
    }
}