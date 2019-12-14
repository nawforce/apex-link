/*
    Copyright (c) 2009, Salesforce.org
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
* @date 2011 (2.x)
* @description Controller for the NPSP contact merge page.
* @group ContactMerge
* @group-content ../../ApexDocContent/ContactMerge.htm
*/
public with sharing class CON_ContactMerge_CTRL {
    
    private static final String MASTER_KEY = '$MASTER$';
         
    /*******************************************************************************************************
    * @description Search text entered by user.
    */
    public String searchText { get { return searchText; } set { searchText = value; } }
    
    /*******************************************************************************************************
    * @description List of contactWrapper objects for display in search results pane.
    */
    public List<ContactWrapper> searchResults {get; private set;}
    
    /*******************************************************************************************************
    * @description List of contactWrapper objects for display in search results pane.
    */
    private Map<String, Contact> selectedRecords;
    
    /*******************************************************************************************************
    * @description Number of selected records.
    */
    public Integer selectedRecordsCount {get; private set;} {this.selectedRecordsCount = 0;}
    
    /*******************************************************************************************************
    * @description Max number of Contacts returned by a query.
    */
    private final Integer SOSL_LIMIT = 100;
    
    /*******************************************************************************************************
    * @description The contact merge step user is in. (The process has multiple steps.)
    */
    public Integer step {get; private set;}
    
    /*******************************************************************************************************
    * @description Wraps a contact together with a checkbox, to allow contact selection.
    */
    public class contactWrapper {
        /*******************************************************************************************************
        * @description The contact.
        */
        public Contact con {get; set;}
        /*******************************************************************************************************
        * @description The checkbox.
        */
        public Boolean selected {get; set;}
        
        /*******************************************************************************************************
        * @description Constructor that initializes instance properties.
        * @c The contact to wrap.
        */ 
        public contactWrapper(Contact c) {
            con = c;
            selected = false;
        }
    }
    
    /*******************************************************************************************************
    * @description The rows to merge.
    */
    public List<FieldRow> fieldRows {get; private set;}
    
    /*******************************************************************************************************
    * @description The struct to save all the information belonging to each contact field, including values for 
    * all the contacts to merge.
    */
    public class FieldRow {
        /*******************************************************************************************************
        * @description Stores the field Label.
        */
        public String fieldLabel {get; private set;}
        /*******************************************************************************************************
        * @description Stores the field api name.
        */
        public String fieldName {get; private set;} 
        /*******************************************************************************************************
        * @description Property to tell whether UI must should a radio to select the field value.
        */
        public boolean showRadio {get; private set;}
        /*******************************************************************************************************
        * @description  List of values for each contact record.
        */
        public List<Cell> values {get; private set;}
        /*******************************************************************************************************
        * @description Selected record.
        */
        public String selectedValue {get; set;}
        /*******************************************************************************************************
        * @description The class style.
        */
        public String styleClass {get; private set;}
        
        /*******************************************************************************************************
        * @description Constructor that initializes the values property.
        */
        public FieldRow() {
            this.values = new List<Cell>();
        }
        
        /*******************************************************************************************************
        * @description Constructor that initializes the values property.
        * @param fieldName The name of the field.
        * @param fieldLabel The field label.
        * @param showRadio Wether a radio button should be displayed for the row.
        * @param styleClass The style class.
        */
        public FieldRow(String fieldName, String fieldLabel, boolean showRadio, String styleClass) {
            this();
            this.fieldName = fieldName;
            this.fieldLabel = fieldLabel;
            this.showRadio = showRadio;
            this.styleClass = styleClass;
        }
    }
    
    /*******************************************************************************************************
    * @description The cell for a corresponding field row in the UI table.
    */
    public class Cell {
        /*******************************************************************************************************
        * @description Id of the record to which this value belongs.
        */
        public String objId {get; private set;} 
        /*******************************************************************************************************
        * @description The actual value.
        */
        public String value {get; private set;}
        
        /*******************************************************************************************************
        * @description Constructor that sets instance properties.
        * @param objectId The ID of the object whose field represented by the cell.
        * @param value The value of the field represented by the cell.
        */
        public Cell(String objectId, String value) {
            this.objId = objectId;
            this.value = value;
        }
    }
    
    /*******************************************************************************************************
    * @description Constructor for StandardSetController to allow invocation from list views.
    * @param controller The default list controller for contact. It allows us to do pagination on the page.
    */
    public CON_ContactMerge_CTRL(ApexPages.StandardSetController controller){
        searchText='';
        searchResults = new List<contactWrapper>();
        selectedRecords = new Map<String, Contact>();
        step = 1;
        fieldRows = new List<FieldRow>();
        
        set<Id> mergeIds = new set<Id>();
        
        //if any selected records were passed the standard set controller show the merge page with those records
        for(Contact c : (list<Contact>)controller.getSelected()) {
            mergeIds.add(c.Id);
        }
        
        if(!mergeIds.isEmpty()) {
            loadMergeCandidates(mergeIds);
        }
        
        //otherwise, check for a search parameter for the SOSL query and use that
        else if(ApexPages.CurrentPage().getParameters().containsKey('srch')) {
            searchText = ApexPages.CurrentPage().getParameters().get('srch');
            search();
        }
        //otherwise, check for a mergeIds parameter, which should contain a comma separated list of Ids to merge
        else if(ApexPages.CurrentPage().getParameters().containsKey('mergeIds') && ApexPages.CurrentPage().getParameters().get('mergeIds') != '') {
            try {
                //attempt to deserialise the comma separated Ids into a list and then a set
                mergeIds = new set<Id>((list<Id>)ApexPages.CurrentPage().getParameters().get('mergeIds').split(','));
                //if we got any ids, use to try and enter the merge selected records page
                if (mergeIds != null) {
                    loadMergeCandidates(mergeIds);
                }
            }
            catch(StringException e){
                ApexPages.addMessages(e);
            }
        }
    }
    
    /*******************************************************************************************************
    * @description Selects all the values of a certain record. Used by the "Select All" command link on the page.
    * @return void
    */
    public void selectDefaultRecord() {
        String recordId = Apexpages.currentPage().getParameters().get('recordId');
        //UTIL_Debug.debug('Selected Record: ' + recordId);
        if (recordId != null && selectedRecords.keySet().contains(recordId)) {
            for (FieldRow row : fieldRows) {
                if (row.showRadio) {
                    row.selectedValue = recordId; 
                }
            }
        }
    }
    
    /*******************************************************************************************************
    * @description Shows the next step of the wizard where user can see the diff of the records before merge.
    */
    public void nextStep() {
        
        set<Id> mergeIds = new set<Id>(); 
        for (ContactWrapper c : searchResults) {
            if (c.selected) {
                mergeIds.add(c.con.Id);
            }
        }
        loadMergeCandidates(mergeIds);
    }
    
    /*******************************************************************************************************
    * @description Shows the next step of the wizard where user can see the diff of the records before merge.
    */
    private void loadMergeCandidates(set<Id> mergeCandidates) {
        
        // Check we have atleast 2 and not more than 3 records selected for merge. If not throw an error. 
        if (mergeCandidates.size() <=1) {
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.Error, Label.npe01.Contact_Merge_Error_Too_Few_Contacts));
            return;
        }
        
        if (mergeCandidates.size() >3 ) {
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.Error, Label.npe01.Contact_Merge_Error_Too_Many_Contacts));
            return;
        }
        
        selectedRecordsCount = mergeCandidates.size();
        
        Map<String, Schema.SObjectField> contactFields = Schema.SObjectType.Contact.fields.getMap();
        Map<String, Schema.DescribeFieldResult> standardFieldMap = new Map<String, Schema.DescribeFieldResult>();
        Map<String, Schema.DescribeFieldResult> customFieldMap = new Map<String, Schema.DescribeFieldResult>();
        
        // Construct the query string
        String query = 'Select id, name, ';
        for (String fieldName : contactFields.keySet()) {
            Schema.SobjectField f = contactFields.get(fieldName);
            Schema.DescribeFieldResult fResult = f.getDescribe();
            
            // Ignore fields that have an identical API name as an npsp prefixed field
            // This is due to a bug in dynamic SOQL, quich causes querying for two real fields, 
            // e.g. npsp__foo__c and foo__c, to throw a duplicate field selected error.
            // Only select the NPSP namespaced field and ignore the same field without namespace
            if (!String.isBlank(UTIL_Namespace.getNamespace()) && !fieldName.startsWith(UTIL_Namespace.getNamespace()+'__') && 
                contactFields.containsKey(UTIL_Namespace.StrTokenNSPrefix(fieldName))) {
                
                continue;
            }

            // Only include the fields which are updateable and accessible
            if (fResult.isUpdateable() && fResult.isAccessible() && !isNPSPHiddenField(fieldname)) {
                // If the field is type lookup, select the parent name  
                string relName = fResult.getRelationshipName();
                if (string.isBlank(relName)) {
                    query += (fieldName + ',');
                } else {
                    //find the Name field for the related object
                    string nameField = getRelatedObjNameField(fResult);

                    if (nameField!=null) {
                        query += (relName + '.' + nameField + ',');
                    }
                }
                // Collect the standard and custom fields separately for sorting
                if(fResult.isCustom()) {
                    customFieldMap.put(fieldName, fResult);
                } else {
                    standardFieldMap.put(fieldName, fResult);
                }
            }
        }
        // Adding some non-updateable system fields which we need to add to the record diff table.
        query +=  'createdby.name, createddate, LastModifiedBy.name, LastModifiedDate';
        // Finally completing the query by appending the table name and the filter clause
        query += ' from Contact where id IN :mergeCandidates';
        
        //UTIL_Debug.debug('The contact query is: ' + query);
        
        List<Contact> contacts;
        try {
            contacts = Database.query(query); // Query the records
            // Ensure we got back the same number of records as expected. In case any record got deleted/moved since last search.
            if (contacts == null || contacts.size() != mergeCandidates.size()) {
                ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.Error, Label.npe01.Contact_Merge_Error_Query_Failed + ' ' + Label.npe01.Contact_Merge_Error_please_retry));
                return;
            }
        } catch (Exception e) {
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.Error, Label.npe01.Contact_Merge_Error_Contact_not_found + ' Error: ' + e));
            return;
        }
        
        // Update the list of searched records with only the ones selected
        searchResults.clear();
        for (Contact c : contacts) {
            this.selectedRecords.put(c.id, c);    
        }
        
        // Sort the standard fields list by name before creating the list of FieldRow for merge UI
        List<String> standardFieldList = new List<String>();
        standardFieldList.addAll(standardFieldMap.keySet());
        standardFieldList.sort();
        
        // Sort the custom fields list by name before creating the list of FieldRow for merge UI
        List<String> customFieldList = new List<String>();
        customFieldList.addAll(customFieldMap.keySet());
        customFieldList.sort();
        
        // Add the first row of type header with contact names as titles
        FieldRow temp = new FieldRow('', '', false, 'header');
        for (Sobject c: contacts) {
            Contact con = (Contact) c;
            temp.values.add(new Cell(c.id, con.name));  
        }
        fieldRows.add(temp);
        
        // Add second row to select the master record. Select the one which is last modified
        temp = new FieldRow(MASTER_KEY, 'Master Record', true, null);
        {
            DateTime lastModifiedDate;
            for (Sobject c: contacts) {
                temp.values.add(new Cell(c.id, null));
                if (lastModifiedDate == null || 
                    (lastModifiedDate != null && (DateTime)c.get('lastModifiedDate') > lastModifiedDate)) {
                    temp.selectedValue = c.id;
                    lastModifiedDate = (DateTime)c.get('lastModifiedDate');
                }   
            }
        }
        fieldRows.add(temp);
        
        // Add a dummy row to add a 'Standard Fields' header before the list of standard fields
        fieldRows.add(new FieldRow('Standard Fields', 'Standard Fields', false, 'separator'));
        
        // Add all standard fields to the list of FieldRow in sorted manner
        for (String fieldName: standardFieldList) {
            addFieldComparisonRows(fieldName, standardFieldMap.get(fieldName), contacts);
        }
        
        // Add a dummy row to add a 'Custom Fields' header before the list of custom fields
        fieldRows.add(new FieldRow('Custom Fields', 'Custom Fields', false, 'separator'));
        
        // Add all custom fields to the list of FieldRow in sorted manner
        for (String fieldName: customFieldList) {
            addFieldComparisonRows(fieldName, customFieldMap.get(fieldName), contacts);
        }
        
        // Add a dummy row to add a 'System Fields' header before the list of system fields
        fieldRows.add(new FieldRow('System Fields', 'System Fields', false, 'separator'));
        
        // Add created by and last modified by system fields to the list of FieldRow
        FieldRow createdByRow = new FieldRow('CreatedById', 'Created By', false, null), 
            lastModifiedByRow = new FieldRow('LastModifiedById', 'Last Modified By', false, null);
        for (Sobject c: contacts) {
            SObject createdBy = c.getSObject('createdby');
            SObject modifiedBy = c.getSObject('lastModifiedBy');
            createdByRow.values.add(new Cell(c.id, (createdBy != null ? createdBy.get('name') : '') + ' ' + c.get('createdDate')));
            lastModifiedByRow.values.add(new Cell(c.id, (modifiedBy != null ? modifiedBy.get('name') : '') + ' ' + c.get('LastModifiedDate'))); 
        }
        fieldRows.add(createdByRow);
        fieldRows.add(lastModifiedByRow);
        
        // If everything looks good go to "choose winning contact" step
        this.step = 3;
        
    }

    /*******************************************************************************************************
    * @description Returns a referenced object's name field.
    * @param controller The default list controller for contact. It allows us to do pagination on the page.
    * @return string The name of the name field, or null if we couldn't find one.
    */
    private string getRelatedObjNameField(Schema.DescribeFieldResult fieldDesc) {
        string nameField = 'Name';
        Map<String, Schema.SObjectField> mapRelatedObjectFields = fieldDesc.getReferenceTo()[0].getDescribe().fields.getMap();

        //if Name isn't the correct name field, find it
        if (!mapRelatedObjectFields.containsKey(nameField)) {
            nameField = null;
            for (String relatedFieldName : mapRelatedObjectFields.keySet()) {
                if (mapRelatedObjectFields.get(relatedFieldName).getDescribe().isNameField())
                    return relatedFieldName;
            }
        }
        return nameField;
    }
    
    /*******************************************************************************************************
    * @description Adds field information and field values to the list of field rows.
    */
    private void addFieldComparisonRows(String fieldName, Schema.DescribeFieldResult fieldDesc, List<SObject> objs) {
        // Create a new FieldRow item
        FieldRow row = new FieldRow();
        row.fieldName = fieldName;
        
        // For standrd lookup type fields, use the relationship name as the field label
        if (!fieldDesc.isCustom() && fieldDesc.getRelationshipName() != null) {
            row.fieldLabel = fieldDesc.getRelationshipName();
        } else {
            row.fieldLabel = fieldDesc.getLabel();
        }
        
        row.values = new List<Cell>();
        
        boolean isDifferent = false; // flag to indicate whether atleast one pair of field values is different across all contacts
        boolean isNull = true; // flag to indicate whether all the field values are null
        Integer idx = 0;
        
        List<String> values = new List<String>();
        DateTime lastModifiedDate = null;
        String prev;
        
        // Iterate over all contacts to find the field values and add them to row values
        for (SObject c : objs) {
            // For lookup fields set the name as the values
            if (fieldDesc.getRelationshipName() != null) {
                Sobject obj = c.getSObject(fieldDesc.getRelationshipName());
                if (obj != null) {
                    values.add(String.valueOf(obj.get(getRelatedObjNameField(fieldDesc))));
                } else {
                    values.add(null);
                }   
            } else {
                values.add(String.valueOf(c.get(fieldName)));
            }
            
            isNull &= (c.get(fieldName) == null); // Check if the value is null
            
            if (idx == 0) {
                prev = String.valueOf(c.get(fieldName));
            }
            if (idx > 0 && !isNull) {
                // Check if atleast one of the values is different. If yes then update the isDifferent flag
                String current = String.valueOf(c.get(fieldName));
                if ((prev != null && !prev.equals(current)) || 
                    (current != null && !current.equals(prev))) {
                    isDifferent = true;
                }
                prev = current;
            }
            
            // Select the default value for the field. A non-null value on the latest modified record 
            if (c.get(fieldName) != null && (lastModifiedDate == null || 
                (lastModifiedDate != null && (DateTime)c.get('lastModifiedDate') > lastModifiedDate))) {
                row.selectedValue = c.id;
                lastModifiedDate = (DateTime)c.get('lastModifiedDate');
            }
            
            idx++;
        }

        // If atleast one record has a non-null field value, then add it to the list of fieldRows.
        if (!isNull) {
            for (Integer i=0; i < values.size(); i++) {
                String val = values[i];
                if (val != null && val.length() > 255) {
                    val = val.substring(0, 251) + ' ...';
                }
                row.values.add(new Cell(objs[i].id, val));
            }
            // Show the select radio only if the field values are different. 
            // Dont let user choose the account as you dont want users to assign a differnt account in One to One case.
            row.showRadio = (isDifferent && !fieldName.equalsIgnoreCase('accountId') && !isNPSPReadOnly(fieldName));
            fieldRows.add(row);
        }
    }
    
    /*******************************************************************************************************
    * @description returns whether the field should be treated as an NPSP read only field, typically one
    * of our rollup fields that get automatically populated by rollups, and users should not direclty modify.
    * @return boolean true if should be treated as readonly, false if not.
    */
    private boolean isNPSPReadOnly(string strField) {
        // we have to maintain our own set of readonly fields, because 
        // RLLP_OppRollup_UTIL.ContactFields doesn't include them all!
        list<string> listStrField = new list<string>{
            'npo02__TotalOppAmount__c', 
            'npo02__AverageAmount__c', 
            'npo02__SmallestAmount__c', 
            'npo02__LargestAmount__c', 
            'npo02__FirstCloseDate__c', 
            'npo02__LastCloseDate__c', 
            'npo02__NumberOfClosedOpps__c', 
            'npo02__OppAmountThisYear__c', 
            'npo02__OppsClosedThisYear__c', 
            'npo02__OppAmountLastYear__c', 
            'npo02__OppsClosedLastYear__c',
            'npo02__OppsClosed2YearsAgo__c', 
            'npo02__OppAmount2YearsAgo__c', 
            'npo02__OppsClosedLastNDays__c', 
            'npo02__OppAmountLastNDays__c',
            'npo02__TotalMembershipOppAmount__c', 
            'npo02__NumberOfMembershipOpps__c', 
            'npo02__LastMembershipDate__c', 
            'npo02__LastMembershipAmount__c',                
            'npo02__LastMembershipLevel__c', 
            'npo02__LastMembershipOrigin__c',                
            'npo02__MembershipJoinDate__c', 
            'npo02__MembershipEndDate__c',                
            'npo02__LastOppAmount__c', 
            'npo02__Best_Gift_Year__c', 
            'npo02__Best_Gift_Year_Total__c',
            'Soft_Credit_Last_N_Days__c',
            'npo02__Soft_Credit_Last_Year__c',
            'npo02__Soft_Credit_This_Year__c',
            'npo02__Soft_Credit_Total__c',
            'npo02__Soft_Credit_Two_Years_Ago__c',
            'First_Soft_Credit_Amount__c',
            'First_Soft_Credit_Date__c',
            'Largest_Soft_Credit_Amount__c',
            'Largest_Soft_Credit_Date__c',
            'Last_Soft_Credit_Amount__c',
            'Last_Soft_Credit_Date__c',
            'Number_of_Soft_Credits__c',
            'Number_of_Soft_Credits_Last_N_Days__c',
            'Number_of_Soft_Credits_Last_Year__c',
            'Number_of_Soft_Credits_This_Year__c',
            'Number_of_Soft_Credits_Two_Years_Ago__c'
        };

        for (string strF : listStrField) {
           if (strField.equalsIgnoreCase(strF))
               return true;
        }
        return false;
    }
    
    
    /*******************************************************************************************************
    * @description returns whether the field should be treated as an NPSP hidden field, typically one
    * of our deprecated or system fields
    * @return boolean true if should be treated as hidden, false if not.
    */
    private boolean isNPSPHiddenField(string strField) {
        list<string> listStrField = new list<string>{
            'npe01__SystemAccountProcessor__c',
            'npo02__SystemHouseholdProcessor__c',
            'npe01__SystemIsIndividual__c',
            'Current_Address__c'
        };

        for (string strF : listStrField) {
           if (strField.equalsIgnoreCase(strF))
               return true;
        }
        return false;
    }
    
    /*******************************************************************************************************
    * @description Searches for contacts.
    * @return PageReference The page that it redirects to. Same page user is in.
    */
    public PageReference search() {
        Try {           
            if(searchText != null && searchText.length()>0){
                step = 2;
                this.searchResults = wrapSOSLResults(mySOSL());
            }
        } catch (exception ex) {
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.Error, ex.getMessage()));         
        }
        return null;
    }
    
    /*******************************************************************************************************
    * @description Runs SOSL query to find contacts according to the search text entered by the user.
    * @return PageReference The page that it redirects to. Same page user is in.
    */
    public List<List<SObject>> mySOSL() {
        searchResults.clear();    
        
        //build the SOSL query and execute - NOTE: * wildcard will only have effect at the middle or end of the search term
        String searchquery = 'FIND \'' + String.escapeSingleQuotes(searchText) + 
            '*\' IN ALL FIELDS RETURNING Contact(';
            
        string strComma = '';
        for (string strField : listStrContactField) {
            searchquery += strComma + strField;
            strComma = ',';
        }
        
        searchquery += ' ORDER BY LastName LIMIT ' + SOSL_LIMIT + ')';
        //UTIL_Debug.debug('Search Query: ' + searchquery);
        
        return search.query(searchquery);
        //List<List<SObject>> searchList = new List<List<SObject>>();
    }
    
    /*******************************************************************************************************
    * @description The list of contact field names that are used in search and displayed in Found Contacts.
    */
    private list<string> listStrContactField {
        get {
            if (listStrContactField == null) {
                listStrContactField = UTIL_Describe.listStrFromFieldSet('Contact', UTIL_Namespace.StrTokenNSPrefix('ContactMergeFoundFS'));
                set<string> setStr = new set<string>(listStrContactField);
                // now add additional fields we know we need
                setStr.add('Id');
                setStr.add('Name');
                listStrContactField.clear();
                listStrContactField.addAll(setStr);
            }
            return listStrContactField;
        }
        set;
    }
    
    /*******************************************************************************************************
    * @description Wraps the SOSL results.
    * @param mySearchList The list of SObjects to wrap. It contains a list per Sobject type.
    * @return List<ContactWrapper> Those records from the passed parameter that are of type Contact are returned
    * in this list, inside wrappers.
    */
    public List<ContactWrapper> wrapSOSLResults(List<List<SObject>> mySearchList){
        
        List<ContactWrapper> res = new List<ContactWrapper>();
        //loop through the contacts putting them in an array of wrappers
        if(mySearchList.size()>0){
            for (List<SObject> returnedObjects : mySearchList) {
                //UTIL_Debug.debug('List size' + returnedObjects.size());
                for (SObject returnedObject : returnedObjects){
                    //if the returned object is a contact, add it to the Contact list
                    if(returnedObject.getSObjectType() == Contact.sObjectType){
                        contactWrapper thisResult = new contactWrapper((Contact) returnedObject);   
                        //UTIL_Debug.debug('Contact Id: ' + returnedObject.id);
                        //UTIL_Debug.debug('Contact: ' + returnedObject);         
                        res.add(thisResult);   
                    }
                }
            }
        }
        //UTIL_Debug.debug('Search Results Map Size: ' + res.size());
        return res;       
    }
    
    /*******************************************************************************************************
    * @description Merges the winner and losers.
    * @return PageReference The page that it redirects to. Same page user is in.
    */
    public PageReference mergeContacts() {
        SObject master;
        // Find the master record based the selected value of the Master FieldRow
        for (FieldRow row : fieldRows) {
            if (row.showRadio && row.fieldName.equals(MASTER_KEY)) {
                master = new Contact(id = row.selectedValue);             
                break;
            }
        }
        
        if (master != null) {
            // Update the field values of master record based on the selected value for each field.
            for (FieldRow row : fieldRows) {
                if (row.showRadio && !row.fieldName.equals(MASTER_KEY) && row.selectedValue != master.id) {
                    SObject selectedRecord = this.selectedRecords.get(row.selectedValue);
                    
                    //UTIL_Debug.debug('Assigning field: ' + row.fieldName);
                    
                    // Sobject.put is not happy when sobject.get returns null. It throws an exception System.SObjectException: Illegal assignment from Object to String.
                    // But instead when you pass a null directly to put, it works fine. And hence, this if statement.
                    if (selectedRecord.get(row.fieldName) == null) {
                        master.put(row.fieldName, null);    
                    } else {
                        Object val = selectedRecord.get(row.fieldName);
                        //UTIL_Debug.debug('Setting value: ' + val);
                        master.put(row.fieldName, val);
                        //UTIL_Debug.debug('Set value: ' + master.get(row.fieldName));
                    }
                }
            }
            
            // Group all the loosing records separately.
            List<Contact> losers = new List<Contact>();
            
            for (Contact c : this.selectedRecords.values()) {
                if (c.id != master.id) {
                    losers.add(c);
                }   
            }
        
            // before proceeding further first lock the records for change
            List<Contact> allContacts = new List<Contact>();
            allContacts.add((Contact)master);
            allContacts.addAll(losers);
            List<Contact> lock = [Select id from Contact where id IN :allContacts for update];
            
            if (lock == null || lock.size() != allContacts.size()) {
                ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.Error, label.npe01.Contact_Merge_Error_Lock_failed  + ' ' + label.npe01.Contact_Merge_Error_please_retry) );
                return null;
            }
            
            System.SavePoint sp = Database.setSavepoint();
            try {
                Database.DMLOptions dml = new Database.DMLOptions();
                dml.DuplicateRuleHeader.AllowSave = true;
                Database.SaveResult sr = Database.update(master, dml);
                if (!sr.isSuccess()) {
                    string strErr = label.npe01.Contact_Merge_Error_Merge_Failed + ' Error: ';
                    for (Database.Error err : sr.getErrors()) {
                        strErr += err.getMessage() + '\n';
                    }
                    Database.rollback(sp);
                    ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.Error, strErr));
                    return null;
                }
                
                CON_ContactMerge.mergeContacts((Contact)master, losers);
                // Update the master with the selected values
                return new PageReference('/' + master.id);
            } catch (Exception e) {
                Database.rollback(sp);
                ERR_Handler.processError(e, ERR_Handler_API.Context.CON);
                ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.Error, label.npe01.Contact_Merge_Error_Merge_Failed + ' Error: ' + e));
            }
        } else {
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.Error, label.npe01.Contact_Merge_Error_No_Master));
        }
        
        return null;
    }
}