/*
    Copyright (c) 2015 Salesforce.org
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
* @author Team Kick Ass; Veronica Waters, Evan Callahan, Derek Dsouza, Mike Fullmore
* @date 04/27/2011
* @group BatchDataEntry
* @group-content ../../ApexDocContent/BatchDataEntry.htm
* @description base class for the batch data entry, supports all the custom settings you might have.
*/
public with sharing class BDE_BatchDataEntry {
    /** @description Object for batch data entry. */
    private String objectName;
    /** @description Fields to include for batch data entry, based on entry field set. */
    private List<String> entryFieldList;
    /** @description Fields to list for batch data entry, based on list field set. */
    private List<String> listFieldList;
    /** @description Boolean flag, set to true. */
    private Boolean saveBatch;
    /** @description Set with any error messages that may occur during batch data entry. */
    private String statusMessage;
    /** @description The field that is a lookup to the batch object on the selected object. */
    private String batchLookupFieldName;
    /** @description Set of all entry and list fields combined. */
    private Set<String> fieldSet;
    /** @description Holds the result of the query string built from the set of fields for the selected object. */
    private String queryString;
    /** @description Success Status Message. */
    private final String SUCCESS = 'Success';
    /** @description Failure Status Message. */
    private final String FAIL = 'Failure: ';
    /** @description Custom Settings Message. */
    private final String CREATED_CUSTOM_SETTING = 'You did not have a default custom setting so we are passing one to you in memory';
    /** @description Default Opportunity Name. */
    private final String DEFAULT_NAME = 'opportunity';
    /** @description Default Entry Fields. */
    private final String DEFAULT_ENTRY_FS = 'AccountID,Name,StageName,Amount,CloseDate';
    /** @description Default List Fields. */
    private final String DEFAULT_LIST_FS = 'AccountID,Name,StageName,Amount,CloseDate';
    /** @description Default Save Batch Value. */
    private final Boolean DEFAULT_SAVE_BATCH = true;
    /** @description Returns objectName to the page. */
    public String getObjectName(){return this.objectName;}
    /** @description Returns entryFieldList to the page. */
    public List<String> getEntryFieldList(){return this.entryFieldList;}
    /** @description Returns listFieldList to the page. */
    public List<String> getListFieldList(){return this.listFieldList;}
    /** @description Returns saveBatch to the page. */
    public Boolean getSaveBatch(){return this.saveBatch;}
    /** @description Returns statusMessage to the page. */
    public String getStatusMessage(){return this.statusMessage;}
    /** @description Returns fail to the page. */
    public String getFailureMessage(){return this.FAIL;}
    /** @description Returns batchLookupFieldName to the page. */
    public String getBatchLookupFieldName() {return this.batchLookupFieldName;}
    /** @description Returns fieldSet to the page. */
    public Set<String> getFieldSet(){return this.fieldSet;}
    /** @description Returns queryString to the page. */
    public String getQueryString(){return this.queryString;}

    /*********************************************************************************************************
    * @description For valid objects that have a lookup field to the batch, populates the entryFieldList and
    * listFieldList with fields in the field set from the object and then runs buildFieldSet and 
    * buildQueryString methods.
    * @param pObjectName the object to use for batch data entry
    */
    public BDE_BatchDataEntry(String pObjectName){
        this.queryString = 'select Id';
        boolean isValidObject=false;

        try{
            if(pObjectName != null){
                try {
                    String s = UTIL_Describe.getObjectLabel(pObjectName);
                    isValidObject=true;
                } catch (exception e) {
                    isValidObject=false;
                    this.statusMessage = this.FAIL + 'You are attempting to perform batch entry on an object that does not exist: ' + pObjectName;
                }
            } else {
            	this.statusMessage = this.SUCCESS;
            }

            if(isValidObject) {
                this.objectName = pObjectName;
                this.saveBatch = true; // DJH: did not port this setting due to lack of use.

                if(this.saveBatch && !checkLookupExists()) {
                    this.statusMessage = this.FAIL + this.objectName + ' does not have a lookup field to the ' + String.valueOf(Batch__c.sObjectType) + ' object. If this is a standard object, you can go to Setup->Customize-><objectname>->Fields to create a lookup field to the Batch. For custom object, please go to Setup->Create->Objects-><objectname> and click \'New\' under the Fields section.';
                } else {
                    this.entryFieldList = UTIL_Describe.listStrFromFieldSet(pObjectName, UTIL_Namespace.StrTokenNSPrefix('BDE_Entry_FS'));
                    if(this.entryFieldList == null) //The original fieldsets for opportunity got deleted by mistake. New name required.
                        this.entryFieldList = UTIL_Describe.listStrFromFieldSet(pObjectName, UTIL_Namespace.StrTokenNSPrefix('BDE_Entry_FS_v2'));

                    this.listFieldList = UTIL_Describe.listStrFromFieldSet(pObjectName, UTIL_Namespace.StrTokenNSPrefix('BDE_List_FS'));
                    if(this.listFieldList == null) //The original fieldsets for opportunity got deleted by mistake. New name required.
                        this.listFieldList = UTIL_Describe.listStrFromFieldSet(pObjectName, UTIL_Namespace.StrTokenNSPrefix('BDE_List_FS_v2'));

                    buildFieldSet();
                    buildQueryString();
					this.statusMessage = this.SUCCESS;
                }
            }
        }catch(Exception e){
            this.statusMessage = this.FAIL + e.getMessage();
        }
    }
    /*********************************************************************************************************
    * @description Assigns the entry and list fields to the field set.
    * @return void
    */
    private void buildFieldSet(){
        this.fieldSet = new Set<String>();

        for(String str : this.entryFieldList){
            this.fieldSet.add(str);
        }

        for(String str : this.listFieldList){
            this.fieldSet.add(str);
        }
    }
    /*********************************************************************************************************
    * @description Builds the query string from fieldSet and objectName.
    * @return void
    */
    private void buildQueryString(){
        if(this.fieldSet != null){
            for(String str : this.fieldSet){
                this.queryString += ', ' + str;
            }
        this.queryString += ' from ' + this.objectName + ' ';
        }
    }
    /*********************************************************************************************************
    * @description Determines if the object has a lookup field to batch and populates batchLookupFieldName.
    * @return boolean set to true when a field is found.
    */
    private boolean checkLookupExists() {
        Map<String, Schema.DescribeFieldResult> allFieldsMap = UTIL_Describe.getAllFieldsDescribe(this.objectName);
        for(Schema.DescribeFieldResult fr: allFieldsMap.values()) {
            if(fr.getType() == Schema.Displaytype.Reference) {
                Schema.sObjectType parent = fr.getReferenceTo()[0];
                if(parent.getDescribe().getName()==String.valueOf(Batch__c.sObjectType)) {
                    batchLookupFieldName = fr.getName();
                    return true;
                }
            }
        }
        return false;
    }
    /*********************************************************************************************************
    * @description Builds a map of all objects that support Batch Data Entry.
    * @return Map<String, String> the map of the devName to the object label.
    */
    public static map<string, string> mapDevNameToLabelBDEObjects() {
    	//return new string[]{'Opportunity','Contact','Lead','Account'};
    	map<string, string> mapBDE = new map<string, string>();
    	Schema.Describesobjectresult dor = UTIL_Describe.getObjectDescribe(UTIL_Namespace.StrTokenNSPrefix('Batch__c'));
    	for (Schema.ChildRelationship cr : dor.getChildRelationships()) {
    		// first see if they have a batch__c field
    		Schema.SObjectField sfld = cr.getField();
    		if (sfld.getDescribe().getLocalName() == 'Batch__c') {
	    		Schema.DescribeSObjectResult dr = cr.getChildSObject().getDescribe();
	            mapBDE.put(dr.getName(), dr.getLabel());
    		}
    	}
    	return mapBDE;
    }
}