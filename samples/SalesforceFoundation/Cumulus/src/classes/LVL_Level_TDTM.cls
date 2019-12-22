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
* @group Levels
* @group-content ../../ApexDocContent/Levels.htm
* @description Trigger handler on Level object, ensures Levels within the same ladder do not overlap.
*/
public class LVL_Level_TDTM extends TDTM_Runnable {

    /*******************************************************************************************************
    * @description Trigger Handler on Level, which validates Levels within the same ladder do not overlap.
    * @param listNew the list of Levels from trigger new. 
    * @param listOld the list of Levels from trigger old. 
    * @param triggerAction Which action triggered the current context. (Before or After; Insert, Update, 
    * Delete, or Undelete.)
    * @param objResult The field describe object for the current trigger context. 
    * @return dmlWrapper Objects that need to be inserted or updated after the processing of the trigger.
    ********************************************************************************************************/
    public override DmlWrapper run(List<SObject> newlist, List<SObject> oldlist, 
        TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {
            
            // only validate before triggers
            if (triggerAction == TDTM_Runnable.Action.BeforeInsert || triggerAction == TDTM_Runnable.Action.BeforeUpdate) {
                
                // ensure valid values for each level object
                verifyNoInvalidValues((List<Level__c>) newlist);
                
                // maintain a set of all the object id's from the trigger, to help with error reporting
                Set<Level__c> setTrigger = new Set<Level__c>();
                
                // get a set of all objects being referenced
                set<String> setStrObject = new set<String>();
                
                for (SObject sobj : newlist) {
                    setStrObject.add(((Level__c)sobj).Target__c);
                    setTrigger.add((Level__c)sobj);
                }
                
                // for each object, validate any updates to its ladders
                for (String strObj : setStrObject) {
                    
                    // get our map of Ladder Levels
                    map<String, list<Level__c>> mapLadderToLevels = LVL_LevelAssign_BATCH.mapLadderToLevelsForObject(strObj);
                    
                    // go thru our new/updated levels
                    for (integer i = 0; i < newlist.size(); i++) {
                        Level__c lvl = (Level__c)newlist[i];
                        
                        // only look at one object this iteration
                        if (lvl.Target__c != strObj)
                            continue;
                        
                        // add new levels to their ladder
                        if (lvl.Id == null) {
                            if (lvl.Active__c) {
                                list<Level__c> listLvl = getListLevel(mapLadderToLevels, lvl.Level_Field__c);
                                addLevel(lvl, listLvl);
                            }
                        }
                        
                        // deal with updates, by removing from old ladder and adding to new ladder
                        if (lvl.Id != null) {
                            
                            // remove from old ladder
                            Level__c lvlOld = (Level__c)oldlist[i];
                            list<Level__c> listLvlOld = getListLevel(mapLadderToLevels, lvlOld.Level_Field__c);
                            removeLevel(lvlOld, listLvlOld);
                            
                            // add to new ladder
                            if (lvl.Active__c) {
                                list<Level__c> listLvl = getListLevel(mapLadderToLevels, lvl.Level_Field__c);
                                addLevel(lvl, listLvl);
                            }
                        }
                    }
                    
                    
                    // now validate that each ladder has no overlaps, and no duplicate names
                    for (list<Level__c> listLvl : mapLadderToLevels.values()) {
                        verifyNoOverlapListLvl(listLvl, setTrigger);
                        verifyNoDuplicateNames(listLvl, setTrigger);
                    }
                }
            }
        return null;
    }
    
    /*******************************************************************************************************
    * @description get the list of Levels for the given ladder, creating the list if empty.
    * @param mapLadderToLevels The map to get the list from
    * @param strLadder The key into the map
    * @return list<Level__c> the List
    */
    private static list<Level__c> getListLevel(map<String, list<Level__c>> mapLadderToLevels, string strLadder) {
        list<Level__c> listLvl = mapLadderToLevels.get(strLadder);
        if (listLvl == null) {
            listLvl = new list<Level__c>();
            mapLadderToLevels.put(strLadder, listLvl);
        }
        return listLvl;
    }
    
    /*******************************************************************************************************
    * @description add the Level in the correct order for the list of Levels
    * @param lvl The Level to insert
    * @param listLvl The list to insert into
    */
    private void addLevel(Level__c lvl, list<Level__c> listLvl) {
        
        for (integer i = 0; i < listLvl.size(); i++) {
            if (lvl.Minimum_Amount__c == null || lvl.Minimum_Amount__c <= listLvl[i].Minimum_Amount__c) {
                listLvl.add(i, lvl);
                return;
            }
        }
        listLvl.add(lvl);
    }    

    /*******************************************************************************************************
    * @description remove the Level from the list of Levels
    * @param lvl The Level to remove
    * @param listLvl The list to remove from
    */
    private void removeLevel(Level__c lvl, list<Level__c> listLvl) {
        
        for (integer i = 0; i < listLvl.size(); i++) {
            if (lvl.Id == listLvl[i].Id) {
                listLvl.remove(i);
                return;
            }
        }
    }    

    /*******************************************************************************************************
    * @description verify no Levels overlap in the list of Levels.  tags each level with a problem using 
    * addError(), rather than throwing an exception on the first error.
    * @param listLvl The list to verify
    * @param setTrigger The objects that triggered this event
    */
    private void verifyNoOverlapListLvl(list<Level__c> listLvl, Set<Level__c> setTrigger) {
        
        for (integer i = 1; i < listLvl.size(); i++) {
            // test overlapping ranges between levels
            if ((listLvl[i].Minimum_Amount__c == null || listLvl[i-1].Minimum_Amount__c >= listLvl[i].Minimum_Amount__c) ||
                (listLvl[i-1].Maximum_Amount__c == null || listLvl[i-1].Maximum_Amount__c > listLvl[i].Minimum_Amount__c)) {
                integer ierr = setTrigger.contains(listLvl[i-1]) ? i-1 : i;
                listLvl[ierr].addError(String.format(Label.lvlErrorMinOverlap, new list<String>{listLvl[i-1].Name, listLvl[i].Name}));
            }
        }
                    
        for (integer i = 0; i < listLvl.size(); i++) {
            // test min/max overlap within a level
            if (listLvl[i].Minimum_Amount__c >= listLvl[i].Maximum_Amount__c) {
                listLvl[i].addError(String.format(Label.lvlErrorMinGTMax, new list<String>{listLvl[i].Name}));                
            }
            
        }
    }

    /*******************************************************************************************************
    * @description verify no Levels have the same name.  tags each level with a problem using 
    * addError(), rather than throwing an exception on the first error.
    * @param listLvl The list to verify
    * @param setTrigger The objects that triggered this event
    */
    private void verifyNoDuplicateNames(list<Level__c> listLvl, Set<Level__c> setTrigger) {
        
        // track how many times each name is used
        boolean hasDupe = false;
        Map<String, Integer> mapNameToCount = new Map<String, Integer>();
        for (Level__c lvl : listLvl) {
            integer c = mapNameToCount.get(lvl.Name);
            if (c == null) {
                c = 1;
            } else {
                c++;
                hasDupe = true;
            }
            mapNameToCount.put(lvl.Name, c);
        }

        // if dupe detected, find the one from the trigger that caused it
        if (hasDupe) {
            for (Level__c lvl : listLvl) {
                if (setTrigger.contains(lvl) && mapNameToCount.get(lvl.Name) > 1) {
                    lvl.addError(String.format(Label.lvlErrorDuplicateName, new list<String>{lvl.Name}));
                }                
            }
        }
    }

    /*******************************************************************************************************
    * @description verify no invalid data in each level record.  tags each level with a problem using 
    * addError(), rather than throwing an exception on the first error.
    * @param listLvl The list to verify
    */
    private void verifyNoInvalidValues(list<Level__c> listLvl) {
        
        for (integer i = 0; i < listLvl.size(); i++) {
            // ensure no duplicate lookups
            if (listLvl[i].Level_Field__c == listLvl[i].Previous_Level_Field__c) {
                listLvl[i].addError(String.format(Label.lvlErrorDuplicateLookups, new list<String>{listLvl[i].Target__c}));                
            }
            
            // ensure both min & max are not missing
            if (listLvl[i].Minimum_Amount__c == null && listLvl[i].Maximum_Amount__c == null) {
                listLvl[i].addError(String.format(Label.lvlErrorNoMinMax, new list<String>{listLvl[i].Name}));                
            }
        }
    }

}