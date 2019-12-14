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
 * @author salesforce.org
 * @date 8/2016
 * @group Levels
 * @description Controller for the Edit Level override.
 */
public with sharing class LVL_LevelEdit_CTRL {

    /** @description holds the Level currently being edited by the page */
    public Level__c lvl {get; set;}
    
    /*******************************************************************************************************
    * @description constructor for the page
    * @param controller the StandardController for the page
    * @return nothing
    */ 
    public LVL_LevelEdit_CTRL(ApexPages.StandardController controller) {
        List<String> fieldSetList = UTIL_Describe.listStrFromFieldSet(UTIL_Namespace.StrTokenNSPrefix('Level__c'),
                UTIL_Namespace.StrTokenNSPrefix('LevelEdit'));
        fieldSetList.add(UTIL_Namespace.StrTokenNSPrefix('Active__c'));
        fieldSetList.add(UTIL_Namespace.StrTokenNSPrefix('Engagement_Plan_Template__c'));
        if (!Test.isRunningTest()) controller.addFields(fieldSetList);
        lvl = (Level__c)controller.getRecord();
        
        //if clone param is set, this is the clone button override
        Map<String,String> params = ApexPages.currentPage().getParameters();
        if (params.containsKey('clone') && params.get('clone') == '1') {
            lvl.Id = null;
            lvl.Name += ' - ' + Label.lblCloned;
        }
    }
    
    /*********************************************************************************************************
    * @description action method called when the Target field has changed
    */
    public PageReference targetChanged() {
        listSOSourceFields = null;
        listSOLevelFields = null;
        return null;
    }

    /*********************************************************************************************************
    * @description The SelectOption list of Source Fields available on the Target object.
    */
    public list<SelectOption> listSOSourceFields {
        get {
            if (listSOSourceFields == null) {
                listSOSourceFields = new list<SelectOption>();
            
                if (lvl.Target__c != null) {
                    listSOSourceFields.add(new SelectOption('', Label.stgLabelNone));
                    Map<String, Schema.DescribeFieldResult> mapDFR = UTIL_Describe.getAllFieldsDescribe(lvl.Target__c);
                    for (Schema.DescribeFieldResult f : mapDFR.values()) {                   
                        if (f.isAccessible() && UTIL_Describe.canDisplaytypesCopy(f.getType(), Schema.Displaytype.Double))
                           listSOSourceFields.add(new SelectOption(f.getName(), f.getLabel()));
                    }
                    sortListSOByLabel(listSOSourceFields);
                }
            }
            return listSOSourceFields;
        }
        private set;
    }

    /*********************************************************************************************************
    * @description The SelectOption list of Level Lookup Fields available on the Target object.
    */
    public list<SelectOption> listSOLevelFields {
        get {
            if (listSOLevelFields == null) {
                listSOLevelFields = new list<SelectOption>();
            
                if (lvl.Target__c != null) {
                    listSOLevelFields.add(new SelectOption('', Label.stgLabelNone));
                    Map<String, Schema.DescribeFieldResult> mapDFR = UTIL_Describe.getAllFieldsDescribe(lvl.Target__c);
                    Schema.sObjectType soTypeLevel = UTIL_Describe.getObjectDescribe(UTIL_Namespace.StrTokenNSPrefix('Level__c')).getSObjectType(); 
                    
                    for (Schema.DescribeFieldResult f : mapDFR.values()) {                   
                        if (f.isAccessible() && f.getType() == Schema.Displaytype.Reference) {
                            List <Schema.sObjectType> listSO = f.getReferenceTo();
                            if (listSO.size() == 1 && listSO[0] == soTypeLevel) {
                                listSOLevelFields.add(new SelectOption(f.getName(), f.getLabel()));
                            }
                        }
                    }
                    sortListSOByLabel(listSOLevelFields);
                }
            }
            return listSOLevelFields;
        }
        private set;
    }

    /*********************************************************************************************************
    * @description sort the list of SelectOptions by labels
    */
    private static void sortListSOByLabel(List<SelectOption> listSO) {
        
        Map<String, SelectOption> mapSO = new Map<String, SelectOption>();

        // Suffix to avoid duplicate values like same labels or values are in inbound list 
        Integer suffix = 1;
        for (SelectOption so : listSO) {
            mapSO.put(so.getLabel() + suffix++, so);   
        }
        
        List<String> sortKeys = new List<String>();
        sortKeys.addAll(mapSO.keySet());
        sortKeys.sort();
        // clear the original collection to rebuilt it
        listSO.clear();
        
        for (String key : sortKeys) {
            listSO.add(mapSO.get(key));
        }
    }
    
    /*********************************************************************************************************
    * @description actionMethod to Save the current Level and create a new one, which is assumed to be the
    * next in the ladder.
    */
    public PageReference saveAndNew() {
        try {
            upsert lvl;
            // now fixup lvl to be the new next level
            lvl.Id = null;
            lvl.Name = null;
            lvl.Minimum_Amount__c = lvl.Maximum_Amount__c;
            lvl.Maximum_Amount__c = null;
            lvl.Engagement_Plan_Template__c = null;
        } catch (Exception ex) {
            ApexPages.addMessages(ex);            
        }
        return null;
    }

    /*********************************************************************************************************
    * @description actionMethod to Save the current Level.  we didn't just let the standardController handle
    * this, in case this is done after a saveAndNew, in which case we are actually editing a different Level
    * than the standardController originally started with.
    */
    public PageReference save() {
        try {
            upsert lvl;
            return new PageReference('/' + lvl.Id);
        } catch (Exception ex) {
            ApexPages.addMessages(ex);            
            return null;
        }
    }
}