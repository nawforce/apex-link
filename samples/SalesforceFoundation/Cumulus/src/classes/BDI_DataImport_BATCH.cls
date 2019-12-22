/*
    Copyright (c) 2014 Salesforce.org
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
* @description Batch class for the Batch Data Importer.
*
* Batch process looks for all DataImport records that need to be processed
*
*/
public class BDI_DataImport_BATCH implements Database.Batchable<sObject> { 

    private String strSoql;
    
    // constructor, which sets up our initial soql
    public BDI_DataImport_BATCH() {

        strSoql = 'SELECT ';
        string strComma = '';
        for (string strF : listStrDataImportFields) {
            strSoql += strComma + strF;
            strComma = ', ';
        }
        strSoql += ' FROM ' + UTIL_Namespace.StrTokenNSPrefix('DataImport__c');
        strSoql += ' WHERE Status__c <> \'' + label.bdiImported + '\' ';   
        // this ensures consistency for our test code, but also should
        // help users figure out import problems by importing in a consistent order.
        strSoql += ' ORDER BY Name ';                   
    }
    
    public Database.QueryLocator start(Database.BatchableContext bc) {
        return Database.getQueryLocator(strSoql);
    }
    
    // maps to keep track of the objects we will create.
    // all are indexed by the object's matching key or the di.Id
    private map<string, Contact> mapDIKeyToContact = new map<string, Contact>();
    private map<string, Account> mapDIKeyToAccount = new map<string, Account>();
    private map<Id, Address__c> mapDIIdToAddr = new map<Id, Address__c>();
    private map<Id, Opportunity> mapDIIdToOpp = new map<Id, Opportunity>();
    private map<Id, npe01__OppPayment__c> mapDIIdPmt = new map<Id, npe01__OppPayment__c>();
    private map<string, Id> mapStrNameToCampaignId = new map<string, Id>();

    // holds the list of DataImport records we are currently processing
    private list<DataImport__c> listDI;
    
    private Data_Import_Settings__c diSettings {
        get {
            if (diSettings == null) {
                diSettings = UTIL_CustomSettingsFacade.getDataImportSettings();
            }
            return diSettings;
        }
        private set;
    }    

    private boolean isFirstnameInContactMatchRules {
    	get {
    		string str = diSettings.Contact_Matching_Rule__c;
    		if (str == null || str == '')
                return false;
    		return str.contains('Firstname');
    	}
    }

    private boolean isLastnameInContactMatchRules {
        get {
            string str = diSettings.Contact_Matching_Rule__c;
            if (str == null || str == '')
                return false;
            return str.contains('Lastname');
        }
    }

    private boolean isEmailInContactMatchRules {
        get {
            string str = diSettings.Contact_Matching_Rule__c;
            if (str == null || str == '')
                return false;
            return str.contains('Email');
        }
    }

    private boolean isPhoneInContactMatchRules {
        get {
            string str = diSettings.Contact_Matching_Rule__c;
            if (str == null || str == '')
                return false;
            return str.contains('Phone');
        }
    }

    private boolean isCustomIdInContactMatchRules {
        get {
            string str = diSettings.Contact_Custom_Unique_ID__c;
            if (str == null || str == '')
                return false;
            return true;
        }
    }

    private boolean isCustomIdInContactDatatypeString {
        get {
            if (isCustomIdInContactDatatypeString == null) {
                isCustomIdInContactDatatypeString = false;
                if (isCustomIdInContactMatchRules) {
                    Schema.Displaytype dt = UTIL_Describe.getFieldDisplaytype('Contact', diSettings.Contact_Custom_Unique_ID__c);
                    if (dt == Schema.Displaytype.String || 
                        dt == Schema.Displaytype.TextArea || 
                        dt == Schema.Displaytype.Id ||
                        dt == Schema.Displaytype.URL ||
                        dt == Schema.Displaytype.Reference)
                        isCustomIdInContactDatatypeString = true;
                }
            }
            return isCustomIdInContactDatatypeString;
        }
        set;
    }

    private boolean isCustomIdInAccountMatchRules {
        get {
            string str = diSettings.Account_Custom_Unique_ID__c;
            if (str == null || str == '')
                return false;
            return true;
        }
    }
    
    private boolean isCustomIdInAccountDatatypeString {
        get {
            if (isCustomIdInAccountDatatypeString == null) {
                isCustomIdInAccountDatatypeString = false;
                if (isCustomIdInAccountMatchRules) {
                    Schema.Displaytype dt = UTIL_Describe.getFieldDisplaytype('Account', diSettings.Account_Custom_Unique_ID__c);
                    if (dt == Schema.Displaytype.String || 
                        dt == Schema.Displaytype.TextArea || 
                        dt == Schema.Displaytype.Id ||
                        dt == Schema.Displaytype.URL ||
                        dt == Schema.Displaytype.Reference)
                        isCustomIdInAccountDatatypeString = true;
                }
            }
            return isCustomIdInAccountDatatypeString;
        }
        set;
    }

    private string strNull(Object obj) {
        return strNull(string.valueOf(obj));
    }

    private string strNull(string str) {
        if (str == null)
            return '';
        else
            return str;
    }

    // the real work gets done here!
    // given a batch of di records, process them.
    public void execute(Database.BatchableContext bc, List<DataImport__c> listDI) {

	    SavePoint sp = Database.setSavepoint();
	    
	    try {
            this.listDI = listDI;
            
            // first set a clean state for each DI
            for (DataImport__c di: listDI) {
                di.Status__c = null;
                di.ImportedDate__c = null;
                di.ApexJobId__c = null;
                di.FailureInformation__c = null;
            }

            importContactsAndHouseholds();
            
            importHouseholdAccountFields();
            
            importAccounts();
            
            importAddresses();
            
            importCampaigns();

            importOpportunities();

            importCampaignMembers();

            // update final status for each DI
            for (DataImport__c di: listDI) {
                if (di.Status__c == null)
                    di.Status__c = label.bdiImported;
                di.ImportedDate__c = system.now();
                di.ApexJobId__c = bc.getJobId();
                
                // because a contact might be first imported in C1 location, but then moved to another 
                // household since it later shows up in C2, location, we can't be assured that the
                // contact's original household account is the same (and valid). Thus we must fixup
                // all Household Account Id lookups in our di records.
                if (di.Status__c == label.bdiImported) {
                    Contact c1 = ContactFromDi(di, 1);
                    if (c1 != null)
                        di.HouseholdAccountImported__c = c1.AccountId;
                } else {
                    di.HouseholdAccountImported__c = null;                    
                }
                
                // our matching marks non-matches with created, assuming they will get created
                // but if there were errors that stopped that creation, let's clear the status
                if (di.Contact1Imported__c == null && di.Contact1ImportStatus__c == label.bdiCreated)
                    di.Contact1ImportStatus__c = null;
                if (di.Contact2Imported__c == null && di.Contact2ImportStatus__c == label.bdiCreated)
                    di.Contact2ImportStatus__c = null;
                if (di.Account1Imported__c == null && di.Account1ImportStatus__c == label.bdiCreated)
                    di.Account1ImportStatus__c = null;
                if (di.Account2Imported__c == null && di.Account2ImportStatus__c == label.bdiCreated)
                    di.Account2ImportStatus__c = null;
            }
            
            //UTIL_Debug.debug('****DJH final update to listDI: ' + listDI);
            update listDI;
	
	    } catch (Exception e) {
	        //UTIL_Debug.debug('****DJH Exception ' + e);
	        
            // we should only hit this exception if a truly unexpected runtime
            // error occurs.  not even database updates/inserts should get here,
            // since we now handle all database updates/inserts using the flavor
            // of the api that allows us to specify AllOrNothing=false, and we mark
            // the failed update/inserts on the di record with the problem.
            // So go ahead and rollback the entire transaction on these totally unexpected errors.
	        Database.rollback(sp);
	        
	        // log the unexpected error
	        ERR_Handler.processError(e, ERR_Handler_API.Context.BDI);
	        
            // update all the di's with errors with the error state.
            // those di's without specific errors will get the exception's error
            list<DataImport__c> listDIError = new list<DataImport__c>();
            for (DataImport__c di : listDI) {
            		listDIError.add(new DataImport__c(
            		  Id = di.Id, 
            		  Status__c = label.bdiFailed, 
            		  FailureInformation__c = di.Status__c == label.bdiFailed ? di.FailureInformation__c : e.getMessage(),
            		  ApexJobId__c = bc.getJobId()
            		  ));
            }
            
            update listDIError;
            
            // unfortunately, we can't let the system think the batch failed, or
            // then it would rollback our setting of failure state in the DI records.
            // so we just return, and rely on our Data Import ui to notify the user.
	    }
    }

    // utility to copy the specific error into the provide di.
    private void LogBDIError(DataImport__c di, string strError, string strStatusField) {
    	//system.debug('****DJH: LogBDIError di: ' + di + ' strError: ' + strError + ' field: ' + strStatusField);
    	di.Status__c = label.bdiFailed;
        di.FailureInformation__c = strError;
        if (strStatusField != null)
            di.put(strStatusField, strError.left(255));
    }
            
    // required method for Batchable.  Nothing we need to do though!
    public void finish(Database.BatchableContext bc) {}

    // utility to return all fields from the Data Import object
    private static list<string> listStrDataImportFields { 
        get {
            if (listStrDataImportFields == null) {
                Map<String, Schema.SObjectField> mapS = Schema.SObjectType.DataImport__c.fields.getMap();
                listStrDataImportFields = new list<string>();
                listStrDataImportFields.addAll(mapS.keySet());                         
            }
            return listStrDataImportFields;
        }
        set;
    }    
    
    // our exception object for Field Level Security errors.
    private class flsException extends Exception {}
    
    // flag to allow us to test out field level security exception
    public static boolean failFieldLevelSecurity = false;
    
    // utility to return a map of fields from the DataImport to fields for the given 'object',
    // from the list of DataImport Fields, which have the form DIObject.developerfieldname in the field's help text property. 
    private static map<string, string> mapFieldsForDIObject(string strObj, string strBaseObj, list<string> listStrDIFields) {
        map<string, string> mapDIFieldToObjField = new map<string, string>();
        for (string strF : listStrDIFields) {
            DescribeFieldResult dfr = UTIL_Describe.getFieldDescribe(UTIL_Namespace.StrTokenNSPrefix('DataImport__c'), strF);
            string strHelpText = dfr.getInlineHelpText();
            if (strHelpText != null && strHelpText != '') {
	        	list<string> strParts = strHelpText.split('\\.', 2);
	        	if (strParts.size() == 2 && strParts[0] == strObj && strParts[1] != '') {
	        		string strField = strParts[1];
	        		//if we aren't in the npsp namespace, remove those tokens from help text
                    //also removes tokens from actual detected namespace, if we happen to be
                    //in a non-npsp namespace
                    if (UTIL_Namespace.getNamespace() != 'npsp') {
                        string strFieldOld = strField;
                        strField = UTIL_Namespace.StrTokenRemoveNSPrefix(strField);
                        
                        //if we have a detected namespace, and removed a namespace from the current field
                        //add the current namespace back in, allowing this code to run in any namespace
                        if (strField != strFieldOld && UTIL_Namespace.getNamespace() != '') {
                            strField = UTIL_Namespace.StrAllNSPrefix(strField);
                        }
                    }
                    
                    // make sure they have permissions to modify the target field
                    // we can't do this in all tests, because many profiles won't have FLS set for all our new fields!
                    // also they may have enabled "Set Audit Fields upon Record Creation", which doesn't seem to change the
                    // describe results!
                    if (strField != 'CreatedDate' && 
                        strField != 'LastModifiedDate' &&
                        (!Test.isRunningTest() || failFieldLevelSecurity)) {
                        dfr = UTIL_Describe.getFieldDescribe(strBaseObj, strField);
                        if (failFieldLevelSecurity)
                            dfr = null;
                        if (dfr == null || !dfr.isAccessible() || !dfr.isUpdateable())
                            throw (new flsException(string.format(label.flsError, new string[]{strBaseObj + '.' + strField})));
                    }
                                            
                    mapDIFieldToObjField.put(strF, strField);
                }
            } 
        }
        return mapDIFieldToObjField;
    }

    /*******************************************************************************************************
    * @description Copies non-null DI field values to the destination object, given the provided mapping.
    * also explicitly ignores boolean false's, since we can't differentiate null from false.
    * @param di The DataImport record
    * @param strDIField The DI field to copy from
    * @param objDst The object to copy to
    * @param mapDIFieldToDstField The field map from a DI field to the destination field
    * @return boolean True if copied, False is not copied.
    */
    private static boolean isCopiedDIField(DataImport__c di, String strDIField, SObject objDst, map<string, string> mapDIFieldToDstField) {
        
        Object val = di.get(strDIField);

        // explicitly avoid updating unchecked booleans (since we can't tell the difference between null and false)
        if (val != null && val != false) {
            objDst.put(mapDIFieldToDstField.get(strDIField), val);
            return true;
        } else {
            return false;
        }
    }

    //********************************************* CONTACTS & HOUSEHOLDS ************************************************
    // method to handle importing or updating Contact1 and Contact2,
    // creating their Household if none exists.
    private void importContactsAndHouseholds() {        

        // first, validate any required fields on contacts
        verifyContactData();
        
        // try to match our existing contacts
        matchExistingContacts();            

        // handle multiple di's referring to the same existing contact
        set<string> setDIKeyUpsert = new set<string>();
        list<Contact> listConUpsert = new list<Contact>();
        list<DataImport__c> listDIUpsert = new list<DataImport__c>();
                    
        // create/update our c1's
        map<string, string> mapDIFieldToC1Field = mapFieldsForDIObject('Contact1', 'Contact', listStrDataImportFields);
        for (DataImport__c di : listDI) {
            // skip di's that already have an error
            if (di.Status__c == label.bdiFailed)
               continue;
            Contact con = ContactFromDi(di, 1);
            if (con == null)
                continue;
            
            boolean isModified = false;                 

            // potentially set c1's household if provided
            if (di.HouseholdAccountImported__c != null && di.HouseholdAccountImported__c != con.AccountId) {
                con.AccountId = di.HouseholdAccountImported__c;
                isModified = true;
            }

            // copy over all fields with data
            for (string strDIField : mapDIFieldToC1Field.keySet()) {
                if (isCopiedDIField(di, strDIField, con, mapDIFieldToC1Field)) {
                    isModified = true;
                }
            }
                        
            if (isModified) {
                boolean isAlreadyAdded = false;
                // add all dikeys for the contact to make sure we don't try to upsert the contact twice
                for (string dikey : listDiKeyContact(con)) {
                    if (!setDIKeyUpsert.add(diKey))
                       isAlreadyAdded = true; 
                }
                if (!isAlreadyAdded) {
                    listConUpsert.add(con);
                    listDIUpsert.add(di);
                }
            }
            
            // testing error handling
            if (Test.isRunningTest() && con.LastName != null && con.LastName.startsWith('FailTest'))
                con.LastName = null;
        }
        //system.debug('****DJH about to upsert c1s: ' + listConUpsert);
        list<Database.UpsertResult> listUR = database.upsert(listConUpsert, false);
        for (integer i = 0; i < listUR.size(); i++) {
            Database.UpsertResult ur = listUR[i];
            if (!ur.isSuccess()) {
                LogBDIError(listDIUpsert[i], ur.getErrors()[0].getMessage(), 'Contact1ImportStatus__c');
            }
        }
        
        // update each DI with its new contact1 ID
        // also get all our C1 Id's in a set for further use
        set<Id> setC1Id = new set<Id>();
        for (DataImport__c di : listDI) {
            if (di.Contact1Imported__c == null) {
                Contact con = ContactFromDi(di, 1);
                if (con != null) 
                    di.Contact1Imported__c = con.Id;
            }
            setC1Id.add(di.Contact1Imported__c);
        }
            
        // get the Household Accounts for C1's
        map<Id, Id> mapC1IdToHHId = new map<Id, Id>();
        for (Contact con : [select Id, AccountId from Contact where Id in :setC1Id]) {
            mapC1IdToHHId.put(con.Id, con.AccountId);
        }
        // store it back in C1 and the DI for later use
        for (DataImport__c di : listDI) {
            if (di.Contact1Imported__c != null) {
                Id HHId = mapC1IdToHHId.get(di.Contact1Imported__c);
                if (HHId != null) {
                    Contact con = ContactFromDi(di, 1);
                    con.AccountId = HHId;
                    di.HouseholdAccountImported__c = HHId;
                }
            }
        }

        // now create our c2's
        map<string, string> mapDIFieldToC2Field = mapFieldsForDIObject('Contact2', 'Contact', listStrDataImportFields);
        setDIKeyUpsert.clear();
        listConUpsert.clear();
        listDIUpsert.clear();
        for (DataImport__c di : listDI) {
            // skip di's that already have an error
            if (di.Status__c == label.bdiFailed)
               continue;
            Contact con = ContactFromDi(di, 2);
            if (con == null)
                continue;

            boolean isModified = false;                 

            // set c2's household to match c1
            if (con.AccountId != mapC1IdToHHId.get(di.Contact1Imported__c)) {
                con.AccountId = mapC1IdToHHId.get(di.Contact1Imported__c);
                isModified = true;
            }
            
            // copy over all fields with data
            for (string strDIField : mapDIFieldToC2Field.keySet()) {
                if (isCopiedDIField(di, strDIField, con, mapDIFieldToC2Field)) {
                    isModified = true;
                }
            }
            if (isModified) {
                boolean isAlreadyAdded = false;
                // add all dikeys for the contact to make sure we don't try to upsert the contact twice
                for (string dikey : listDiKeyContact(con)) {
                    if (!setDIKeyUpsert.add(diKey))
                       isAlreadyAdded = true; 
                }
                if (!isAlreadyAdded) {
                    listConUpsert.add(con);
                    listDIUpsert.add(di);
                }
            }
            // testing error handling
            if (Test.isRunningTest() && con.LastName != null && con.LastName.startsWith('FailTest'))
                con.LastName = null;
        }
        //system.debug('****DJH about to upsert c2s: ' + listConUpsert);
        //system.debug('****DJH listDIUpsert: ' + listDIUpsert);
        //system.debug('****DJH setDIKeyUpsert: ' + setDIKeyUpsert);
        listUR = database.upsert(listConUpsert, false);
        for (integer i = 0; i < listUR.size(); i++) {
            Database.UpsertResult ur = listUR[i];
            if (!ur.isSuccess()) {
                LogBDIError(listDIUpsert[i], ur.getErrors()[0].getMessage(), 'Contact2ImportStatus__c');
            }
        }
            
        // update each DI with its new contact2 ID
        for (DataImport__c di: listDI) {
            if (di.Contact2Imported__c == null) {
                Contact con = ContactFromDi(di, 2);
                if (con != null) 
                    di.Contact2Imported__c = con.Id;
            }
        }
    }

    /*******************************************************************************************************
    * @description Imports any custom fields specified for Household Accounts
    * @return void
    */
    private void importHouseholdAccountFields() {
        
        // get our mapping of household custom fields
        map<string, string> mapDIFieldToHHField = mapFieldsForDIObject('Household', 'Account', listStrDataImportFields);
        
        // bail out if no custom fields
        if (mapDIFieldToHHField.size() == 0)
            return;
        
        // holds the Households that need updating
        list<Account> listAccHHUpdate = new list<Account>();
        set<Id> setHHIdUpdate = new set<Id>();
        list<DataImport__c> listDIUpdate = new list<DataImport__c>();
        
        for (DataImport__c di : listDI) {
            // skip di's that already have an error
            if (di.Status__c == label.bdiFailed)
               continue;
            if (di.HouseholdAccountImported__c == null)
                continue;
            
            boolean isModified = false;
            Account accHH = new Account(Id=di.HouseholdAccountImported__c);                
            for (string strDIField : mapDIFieldToHHField.keySet()) {
                if (isCopiedDIField(di, strDIField, accHH, mapDIFieldToHHField)) {
                    isModified = true;
                }
            }
                        
            if (isModified && setHHIdUpdate.add(accHH.Id)) {
                listAccHHUpdate.add(accHH);
                listDIUpdate.add(di);
            }
            
        }
        
        //UTIL_Debug.debug('****DJH about to upsert household Accounts: ' + listAccHHUpdate);
        list<Database.UpsertResult> listUR = database.upsert(listAccHHUpdate, false);
        for (integer i = 0; i < listUR.size(); i++) {
            Database.UpsertResult ur = listUR[i];
            if (!ur.isSuccess()) {
                LogBDIError(listDIUpdate[i], ur.getErrors()[0].getMessage(), null);
            }
        }        
    }
    
    // for every di with C1 or C2 data, make sure they have a lastname
    private void verifyContactData() {    
        for (DataImport__c di: listDI) {
            if (isContactSpecified(di, 'Contact1') && di.Contact1_Lastname__c == null && di.Contact1Imported__c == null) {
                LogBDIError(di, label.bdiErrorInvalidLastname, 'Contact1ImportStatus__c');
                continue;                
            }
            if (isContactSpecified(di, 'Contact2') && di.Contact2_Lastname__c == null && di.Contact2Imported__c == null) {
                di.Contact2_Lastname__c = di.Contact1_Lastname__c;
                if (di.Contact2_Lastname__c == null && di.Contact1Imported__c == null)
	                LogBDIError(di, label.bdiErrorInvalidLastname, 'Contact2ImportStatus__c');
            }
        }
    }
    
    // utility to see if any of the dikey fields for the Contact are specified
    private boolean isContactSpecified(DataImport__c di, string strCx) {
        if (di.get(strCx + 'Imported__c') != null)
            return true;
        if (di.get(strCx + '_Firstname__c') != null)
            return true;
        if (di.get(strCx + '_Lastname__c') != null)
            return true;
        if (di.get(strCx + '_Personal_Email__c') != null)
            return true;
        if (di.get(strCx + '_Work_Email__c') != null)
            return true;
        if (di.get(strCx + '_Alternate_Email__c') != null)
            return true;
        if (di.get(strCx + '_Home_Phone__c') != null)
            return true;
        if (di.get(strCx + '_Mobile_Phone__c') != null)
            return true;
        if (di.get(strCx + '_Other_Phone__c') != null)
            return true;
        if (di.get(strCx + '_Work_Phone__c') != null)
            return true;

        // no dikey data about the contact
        return false;
    }
    
    /*******************************************************************************************************
    * @description returns the field name of the Contact CustomID field for contact1 or contact2 in the
    * Data Import object.  Note that if tests are running, it assumes the field is a standard field on 
    * Contact and thus has no trailing __c, but needs to have the __c for accessing in the Data Import object.
    * @param strCx  which contact to reference (contact1 or contact2)
    * @return string
    */ 
    private string strDIContactCustomIDField(string strCx) {
        return strCx + '_' + diSettings.Contact_Custom_Unique_ID__c + (test.isRunningTest() ? '__c' : '');
    }
    
    // looks for existing contacts that match C1 or C2, and if found,
    // updates their Id's in the Di's.  if not found, creates a new
    // contact object for that di.
    private void matchExistingContacts() {
    
        set<string> setFname = new set<string>();
        set<string> setLname = new set<string>();
        set<string> setEmail = new set<string>();
        set<string> setPhone = new set<string>();
        set<string> setCustomIDString = new set<string>();
        set<double> setCustomIDNumber = new set<double>();
        set<Id> setSFId = new set<Id>();
        set<Id> setHHId = new set<Id>();
                
        for (DataImport__c di : listDI) {
            // skip di's that already have an error
        	if (di.Status__c == label.bdiFailed)
        	   continue;
            // c1
            if (di.Contact1Imported__c != null)
                setSFId.add(di.Contact1Imported__c);
            if (di.Contact1_Firstname__c != null)
                setFname.add(di.Contact1_Firstname__c);
            if (di.Contact1_Lastname__c != null)
                setLname.add(di.Contact1_Lastname__c);
            if (di.Contact1_Personal_Email__c != null)
                setEmail.add(di.Contact1_Personal_Email__c);            
            if (di.Contact1_Work_Email__c != null)
                setEmail.add(di.Contact1_Work_Email__c);            
            if (di.Contact1_Alternate_Email__c != null)
                setEmail.add(di.Contact1_Alternate_Email__c);
            if (di.Contact1_Home_Phone__c != null)
                setPhone.add(di.Contact1_Home_Phone__c);
            if (di.Contact1_Mobile_Phone__c != null)
                setPhone.add(di.Contact1_Mobile_Phone__c);
            if (di.Contact1_Other_Phone__c != null)
                setPhone.add(di.Contact1_Other_Phone__c);
            if (di.Contact1_Work_Phone__c != null)
                setPhone.add(di.Contact1_Work_Phone__c);
            if (isCustomIDInContactMatchRules) { 
                object val = di.get(strDIContactCustomIDField('Contact1'));
                if (val != null) {
                    if (isCustomIdInContactDatatypeString)
                        setCustomIDString.add(string.valueOf(val));
                    else
                        setCustomIDNumber.add(double.valueOf(val));
                }
            }
                
            // c2
            if (di.Contact2Imported__c != null)
                setSFId.add(di.Contact2Imported__c);
            if (di.Contact2_Firstname__c != null)
                setFname.add(di.Contact2_Firstname__c);
            if (di.Contact2_Lastname__c != null)
                setLname.add(di.Contact2_Lastname__c);
            if (di.Contact2_Personal_Email__c != null)
                setEmail.add(di.Contact2_Personal_Email__c);
            if (di.Contact2_Work_Email__c != null)
                setEmail.add(di.Contact1_Work_Email__c);
            if (di.Contact2_Alternate_Email__c != null)
                setEmail.add(di.Contact1_Alternate_Email__c);
            if (di.Contact2_Home_Phone__c != null)
                setPhone.add(di.Contact2_Home_Phone__c);
            if (di.Contact2_Mobile_Phone__c != null)
                setPhone.add(di.Contact2_Mobile_Phone__c);
            if (di.Contact2_Other_Phone__c != null)
                setPhone.add(di.Contact2_Other_Phone__c);
            if (di.Contact2_Work_Phone__c != null)
                setPhone.add(di.Contact2_Work_Phone__c);
            if (isCustomIDInContactMatchRules) { 
                object val = di.get(strDIContactCustomIDField('Contact2'));
                if (val != null) {
                    if (isCustomIdInContactDatatypeString)
                        setCustomIDString.add(string.valueOf(val));
                    else
                        setCustomIDNumber.add(double.valueOf(val));
                }
            }
        }
        
        string strSoql = 'Select Id, Firstname, Lastname, AccountId, Account.npe01__SYSTEM_AccountType__c, ';
        if (isCustomIDInContactMatchRules)
            strSoql += diSettings.Contact_Custom_Unique_ID__c + ', ';        
        strSoql += ' Email, npe01__AlternateEmail__c, npe01__HomeEmail__c, npe01__WorkEmail__c, ';
        strSoql += ' Phone, HomePhone, MobilePhone, OtherPhone, npo02__Formula_HouseholdPhone__c, npe01__WorkPhone__c ';
        strSoql += ' from Contact where ';
        if (setSFId.size() > 0) {
            strSoql += ' (Id in :setSFId) or ';
        }
        string strAnd = '(';
        if (isFirstnameInContactMatchRules) {
            strSoql += strAnd + ' Firstname in :setFName ';
            strAnd = ' and ';
        }
        if (isLastnameInContactMatchRules) {
            strSoql += strAnd + ' Lastname in :setLName ';
            strAnd = ' and ';
        }
        if (isEmailInContactMatchRules) {
            strSoql += strAnd + '(Email in :setEmail or npe01__AlternateEmail__c in :setEmail or npe01__HomeEmail__c in :setEmail or npe01__WorkEmail__c in :setEmail)';
            strAnd = ' and ';
        }
        if (isPhoneInContactMatchRules) {
            strSoql += strAnd + '(' + 
                'Phone in :setPhone or ' +  
                'HomePhone in :setPhone or ' +  
                'MobilePhone in :setPhone or ' + 
                'OtherPhone in :setPhone or ' + 
                'npo02__Formula_HouseholdPhone__c in :setPhone or ' + 
                'npe01__WorkPhone__c in :setPhone )';
            strAnd = ' and ';
        }
        if (isCustomIDInContactMatchRules && isCustomIdInContactDatatypeString) {
            strSoql += ') or (' + diSettings.Contact_Custom_Unique_ID__c + ' in :setCustomIDString'; 
        } else if (isCustomIDInContactMatchRules && !isCustomIdInContactDatatypeString) {
            strSoql += ') or (' + diSettings.Contact_Custom_Unique_ID__c + ' in :setCustomIDNumber'; 
        }
        strSoql += ')';
        
        //system.debug('****DJH about to soql contacts: ' + strSoql);
        list<Contact> listCon = database.query(strSoql);
        //system.debug('****DJH potential existing contacts: ' + listCon);
        
        // put all potential contacts into our map
        for (Contact con : listCon) {
            for (string diKey : listDiKeyContact(con))
                mapDIKeyToContact.put(diKey, con);
        }
        //system.debug('****DJH after adding potential contacts mapDIKeyToContact: ' + mapDIKeyToContact);
        
        // first we process c1's
        for (DataImport__c di : listDI) {
            // skip di's that already have an error
            if (di.Status__c == label.bdiFailed)
               continue;
            Contact con = ContactFromDi(di, 1);
            list<string> listDiKey = listDIKeyC1(di);
            //system.debug('****DJH listDiKey: ' + listDiKey);
            if (con != null) {
                if (con.Id != null && con.AccountId != null && con.Account.npe01__SYSTEM_AccountType__c != CAO_Constants.HH_ACCOUNT_TYPE) {
                    LogBDIError(di, label.bdiErrorNonHHAccountContact, 'Contact1ImportStatus__c');
                    continue;
                }
                di.Contact1Imported__c = con.id;
                di.Contact1ImportStatus__c = label.bdiMatched;
                // remember all the existing household accounts, so we can query for and add "looser" keys for c2
                if (con.AccountId != null) {
                    setHHId.add(con.AccountId);
                    if (di.HouseholdAccountImported__c == null)
                        di.HouseholdAccountImported__c = con.AccountId;
                }
            } else if (listDiKey.size() > 0) {
                con = new Contact();
                di.Contact1ImportStatus__c = label.bdiCreated;
                di.Contact1_Preferred_Email__c = strPreferredEmail(di, 1);
                di.Contact1_Preferred_Phone__c = strPreferredPhone(di, 1);
            }
            // make sure all dikeys for that contact are in the map
            for (string diKey : listDiKey) 
                mapDIKeyToContact.put(diKey, con);
            //system.debug('****DJH after add di keys to mapDIKeyToContact: ' + mapDIKeyToContact);
        }
                    
        // before we process c2's, we want to add some "looser" keys
        // for any c2's to match against existing household members just by firstname
        list<Contact> listHHContacts = [select Id, Firstname, Lastname, AccountId, Account.npe01__SYSTEM_AccountType__c 
            from Contact where AccountId in :setHHId];
        for (Contact con : listHHContacts) {
            mapDIKeyToContact.put(con.Firstname + '|' + con.AccountId, con); 
        } 
        
        // now process c2's
        for (DataImport__c di : listDI) {
            // skip di's that already have an error
            if (di.Status__c == label.bdiFailed)
               continue;
            Contact con = ContactFromDi(di, 2);
            list<string> listDiKey = listDiKeyC2(di);
            if (con != null) {
                if (con.Id != null && con.AccountId != null && con.Account.npe01__SYSTEM_AccountType__c != CAO_Constants.HH_ACCOUNT_TYPE) {
                    LogBDIError(di, label.bdiErrorNonHHAccountContact, 'Contact2ImportStatus__c');
                    continue;
                }
                di.Contact2Imported__c = con.id;
                di.Contact2ImportStatus__c = label.bdiMatched;
            } else if (listDiKey.size() > 0) {
                con = new Contact();
                di.Contact2ImportStatus__c = label.bdiCreated;
                di.Contact2_Preferred_Email__c = strPreferredEmail(di, 2);
                di.Contact2_Preferred_Phone__c = strPreferredPhone(di, 2);
                // if no lastname specified, we need to get it from c1 (which may only be implicit via Contact1_Imported)
                if (di.Contact2_Lastname__c == null) {
                    Contact con1 = ContactFromDi(di, 1);
                    di.Contact2_Lastname__c = con1.Lastname;
                }
            }
            // make sure all dikeys for that contact are in the map
            for (string diKey : listDiKey) 
                mapDIKeyToContact.put(diKey, con);
        }
        //system.debug('****DJH at end of MatchExistingCOntacts, mapDIKeyToContact: ' + mapDIKeyToContact);
    }

    // utility to return the Preferred Email if only one email is set in the di.
    private string strPreferredEmail(DataImport__c di, integer iCon) {
    	string strPreferred = null;
    	string strCx = UTIL_Namespace.StrTokenNSPrefix('Contact' + string.valueOf(iCon));
    	
        if (di.get(strCx + '_Preferred_Email__c') != null) {
            return string.valueOf(di.get(strCx + '_Preferred_Email__c'));
        }    
    	if (di.get(strCx + '_Personal_Email__c') != null) {
            strPreferred = 'Personal';
    	}
        if (di.get(strCx + '_Work_Email__c') != null) {
            if (strPreferred != null)
                return null;
            strPreferred = 'Work';
        }
        if (di.get(strCx + '_Alternate_Email__c') != null) {
            if (strPreferred != null)
                return null;
            strPreferred = 'Alternate';
        }
        return strPreferred;
    }
    
    // utility to return the Preferred Phone if only one phone is set in the di.
    private string strPreferredPhone(DataImport__c di, integer iCon) {
        string strPreferred = null;
        string strCx = UTIL_Namespace.StrTokenNSPrefix('Contact' + string.valueOf(iCon));
        
        if (di.get(strCx + '_Preferred_Phone__c') != null) {
            return string.valueOf(di.get(strCx + '_Preferred_Phone__c'));
        }    
        if (di.get(strCx + '_Home_Phone__c') != null) {
            strPreferred = 'Home';
        }
        if (di.get(strCx + '_Work_Phone__c') != null) {
            if (strPreferred != null)
                return null;
            strPreferred = 'Work';
        }
        if (di.get(strCx + '_Mobile_Phone__c') != null) {
            if (strPreferred != null)
                return null;
            strPreferred = 'Mobile';
        }
        if (di.get(strCx + '_Other_Phone__c') != null) {
            if (strPreferred != null)
                return null;
            strPreferred = 'Other';
        }
        return strPreferred;
    }
    
    /**********
    Key Matching Explained
    
    First, we create a key which is the Salesforce Id of the Contact.  
    Second, if Contact Unique Id is specified, we create a key with that unique id.
    Then we use a compound key of Contact Firstname, Lastname, Email, and Phone (with a | between each) to match against existing Contacts,
    and also those Contacts we are creating. We call this compound key a diKey. Our mapDIKeyToContact 
    is used to lookup a given Contact from a diKey. In order to support settings that tell us which fields
    to use in the match, a given Contact can have multiple keys. Eg., if the setting is Email Only, and
    we have an existing Contact of Fred Smith fred@smith.com, then we need to support matching the following
    diKeys:
        Fred|Smith|fred@smith.com 
        Fred||fred@smith.com 
        |Smith|fred@smith.com 
        ||fred@smith.com

    In this same example, if our DI record is John Doe john@doe.com, then its diKeys would be:
        John|Doe|john@doe.com
        John||john@doe.com
        |Doe|john@doe.com
        ||john@doe.com
        
    But if another DI record is just Bob Dole (no email), then we know it can't match an existing Contact
    since it has no email, but we still need to create a unique diKey for this record so that we can track
    its Contact, as well as map other DI records with the same name to the same contact.  Thus we'll create
    the diKey:
        Bob|Dole|
            
    So the rules can be explained as:
        Existing contacts only add the specific keys the rules can match against.
        Di's add the specific keys the rules can match against, and if none created, a single unique key
        
    ************/
    
    // utility to return the dikeys for a given contact.
    private list<string> listDiKeyContact(Contact con) {
        if (con == null)
            return null;
            
        list<string> listDiKey = new list<string>();
        
        // we need any Contact SFId key to always be checked first when matching
        if (con.Id != null) {
            listDiKey.add(con.Id);
        }

        // we need the Contact CustomId key to always be checked second when matching
        string strUniqueId = '';
        if (isCustomIDInContactMatchRules && (strUniqueId = strNull(con.get(diSettings.Contact_Custom_Unique_ID__c))) != '') {
            listDiKey.add(strUniqueId);
        }

        list<string> listFName = new list<string>();    
        list<string> listLName = new list<string>();    
        list<string> listEmail = new list<string>();
        list<string> listPhone = new list<string>();
        
        boolean addedToken = false;
        if (con.Firstname != null) {
            listFName.add(con.Firstname);
            addedToken = true;
        }
        if (!addedToken || !isFirstnameInContactMatchRules)
            listFName.add('');
        
        addedToken = false;
        if (con.Lastname != null) {
            listLName.add(con.Lastname);
            addedToken = true;
        }
        if (!addedToken || !isLastnameInContactMatchRules)
            listLName.add('');
            
        addedToken = false;
        if (con.Email != null) {
            listEmail.add(con.Email);
            addedToken = true;
        }
        if (con.npe01__HomeEmail__c != null) {
            listEmail.add(con.npe01__HomeEmail__c);
            addedToken = true;
        }
        if (con.npe01__WorkEmail__c != null) {
            listEmail.add(con.npe01__WorkEmail__c);
            addedToken = true;
        }
        if (con.npe01__AlternateEmail__c != null) {
            listEmail.add(con.npe01__AlternateEmail__c);
            addedToken = true;
        }
        if (!addedToken || !isEmailInContactMatchRules)
            listEmail.add('');

        addedToken = false;
        if (con.Phone != null) {
            listPhone.add(con.Phone);
            addedToken = true;
        }
        if (con.HomePhone != null) {
            listPhone.add(con.HomePhone);
            addedToken = true;
        }
        if (con.MobilePhone != null) {
            listPhone.add(con.MobilePhone);
            addedToken = true;
        }
        if (con.OtherPhone != null) {
            listPhone.add(con.OtherPhone);
            addedToken = true;
        }
        if (con.npo02__Formula_HouseholdPhone__c != null) {
            listPhone.add(con.OtherPhone);
            addedToken = true;
        }
        if (con.npe01__WorkPhone__c != null) {
            listPhone.add(con.npe01__WorkPhone__c);
            addedToken = true;
        }
        if (!addedToken || !isPhoneInContactMatchRules)
            listPhone.add('');

        set<string> setDiKey = new set<string>();
        addDiKeyPermutations(setDiKey, listFName, listLName, ListEmail, listPhone, strUniqueId);
        listDiKey.addAll(setDiKey);

        //system.debug('****DJH listDiKeyContact con: ' + con + ' listDIKey: ' + listDIKey);
        return listDiKey;
    }

    // utility to create all the diKey permutations given firstname, lastname, and emails.
    private void addDiKeyPermutations(set<string> setDiKey, list<string> listFname, list<string> listLName, 
        list<string> listEmail, list<string> listPhone, string strUniqueId) {
        for (string strFName : listFName) {
            for (string strLName : listLName) {
                for (string strEmail : listEmail) {
                    for (string strPhone : listPhone) {
                        setDiKey.add(strFName + '|' + strLName + '|' + strEmail + '|' + strPhone + '|' + strUniqueId);
                    }
                }
            }
        }
        setDiKey.remove('||||');
    }

    // utility to return the dikeys for contact1 in the given di.
    private list<string> listDiKeyC1(DataImport__c di) {
    	return listDiKeyCx(di, 'Contact1');
    }

    // utility to return the dikeys for contact2 in the given di.
    private list<string> listDiKeyC2(DataImport__c di) {
    	return listDIKeyCx(di, 'Contact2');
    }

    // utility to return the dikeys for the given contact in the given di.
    private list<string> listDiKeyCx(DataImport__c di, string strCx) {
        list<string> listDiKey = new list<string>();

        // we need the Contact Lookup key to always be checked first when matching
        string strUniqueId = ''; 
        if ((strUniqueId = strNull(di.get(strCx + 'Imported__c'))) != '') {
            listDiKey.add(strUniqueId);
            strUniqueId = '';
        }

        // we need the Contact CustomId key to always be checked second when matching
        if (isCustomIDInContactMatchRules && (strUniqueId = strNull(di.get(strDIContactCustomIDField(strCx)))) != '') {
            listDiKey.add(strUniqueId);
        }
                
        list<string> listFName = new list<string>();    
        list<string> listLName = new list<string>();    
        list<string> listEmail = new list<string>();
        list<string> listPhone = new list<string>();
        boolean addedToken = false;
        
        if (di.get(strCx + '_Firstname__c') != null) {
            listFName.add(string.valueOf(di.get(strCx + '_Firstname__c')));
            addedToken = true;
        }
        if (!isFirstnameInContactMatchRules || !addedToken)
            listFName.add('');

        addedToken = false;
        if (di.get(strCx + '_Lastname__c') != null) {
            listLName.add(string.valueOf(di.get(strCx + '_Lastname__c')));
            addedToken = true;
        }
        if (!isLastnameInContactMatchRules || !addedToken)
            listLName.add('');

        addedToken = false;
        if (di.get(strCx + '_Personal_Email__c') != null) {
            listEmail.add(string.valueOf(di.get(strCx + '_Personal_Email__c')));
            addedToken = true;
        }
        if (di.get(strCx + '_Work_Email__c') != null) {
            listEmail.add(string.valueOf(di.get(strCx + '_Work_Email__c')));
            addedToken = true;
        }
        if (di.get(strCx + '_Alternate_Email__c') != null) {
            listEmail.add(string.valueOf(di.get(strCx + '_Alternate_Email__c')));
            addedToken = true;
        }
        if (!isEmailInContactMatchRules || !addedToken)
            listEmail.add('');
            
        addedToken = false;
        if (di.get(strCx + '_Home_Phone__c') != null) {
            listPhone.add(string.valueOf(di.get(strCx + '_Home_Phone__c')));
            addedToken = true;
        }
        if (di.get(strCx + '_Mobile_Phone__c') != null) {
            listPhone.add(string.valueOf(di.get(strCx + '_Mobile_Phone__c')));
            addedToken = true;
        }
        if (di.get(strCx + '_Other_Phone__c') != null) {
            listPhone.add(string.valueOf(di.get(strCx + '_Other_Phone__c')));
            addedToken = true;
        }
        if (di.get(strCx + '_Work_Phone__c') != null) {
            listPhone.add(string.valueOf(di.get(strCx + '_Work_Phone__c')));
            addedToken = true;
        }
        if (!isPhoneInContactMatchRules || !addedToken)
            listPhone.add('');

        set<string> setDiKey = new set<string>();
        addDiKeyPermutations(setDiKey, listFName, listLName, ListEmail, listPhone, strUniqueId);
        listDiKey.addAll(setDiKey);
        
        // if we didn't create any dikey's for this di due to it not matching all constraints,
        // we have to still add a single key that this di can use to find the new contact to be created.
        if (listDiKey.size() == 0) {
	        string diKey =  strNull(di.get(strCx + '_Firstname__c')) + '|' + strNull(di.get(strCx + '_Lastname__c')) + '|';
	        
	        // email
	        if (di.get(strCx + '_Personal_Email__c') != null)
	           diKey += string.valueOf(di.get(strCx + '_Personal_Email__c'));
	        else if (di.get(strCx + '_Work_Email__c') != null)
	            diKey += string.valueOf(di.get(strCx + '_Work_Email__c'));
	        else if (di.get(strCx + '_Alternate_Email__c') != null)
	            diKey += string.valueOf(di.get(strCx + '_Alternate_Email__c'));
	        diKey += '|';
	            
	        // phone
            if (di.get(strCx + '_Home_Phone__c') != null) 
                diKey += string.valueOf(di.get(strCx + '_Home_Phone__c'));
            else if (di.get(strCx + '_Mobile_Phone__c') != null) 
                diKey += string.valueOf(di.get(strCx + '_Mobile_Phone__c'));
            else if (di.get(strCx + '_Other_Phone__c') != null) 
                diKey += string.valueOf(di.get(strCx + '_Other_Phone__c'));
            else if (di.get(strCx + '_Work_Phone__c') != null) 
                diKey += string.valueOf(di.get(strCx + '_Work_Phone__c'));
                
	        diKey += '|' + strUniqueId;
	        
	        if (diKey != '||||')
	           listDiKey.add(diKey);
        }
        
        // for c2's that we know their household, we want to add a "looser" key to match by as well.
        if (strCx == 'Contact2' && di.HouseholdAccountImported__c != null && di.get('Contact2_Firstname__c') != null) {
            listDiKey.add(di.get('Contact2_Firstname__c') + '|' + di.HouseholdAccountImported__c);
        }
        //system.debug('****DJH: listDiKeyCx returns: ' + listDiKey);               
        return listDiKey;
    }
    
    // utility that finds a contact in our map, by matching against all the contact dikeys
    private Contact ContactFromDi(DataImport__c di, integer iCon) {
        for (string diKey : listDiKeyCx(di, iCon == 1 ? 'Contact1' : 'Contact2')) {
            Contact con = mapDIKeyToContact.get(diKey);
            if (con != null)
                return con;
        }
        return null;
    }
    
    //********************************************* ACCOUNTS ************************************************
    // method to handle importing or updating Account1 and Account2,
    // and setting the contacts' Primary Affiliation.
    private void importAccounts() {        

        // first, try to match our existing Accounts
        matchExistingAccounts();            
            
        // handle multiple di's referring to the same existing account
        set<string> setDIKeyUpsert = new set<string>();
        list<Account> listAccUpsert = new list<Account>();
        list<DataImport__c> listDIUpsert = new list<DataImport__c>();

        // create/update our A1's
        map<string, string> mapDIFieldToA1Field = mapFieldsForDIObject('Account1', 'Account', listStrDataImportFields);
        for (DataImport__c di : listDI) {
            // skip di's that already have an error
            if (di.Status__c == label.bdiFailed)
               continue;
               
            Account acc = AccountFromDi(di, 1);
            if (acc == null)
                continue;

            boolean isModified = false;             
            for (string strDIField : mapDIFieldToA1Field.keySet()) {
                if (isCopiedDIField(di, strDIField, acc, mapDIFieldToA1Field)) {
                    isModified = true;
                }
            }
            if (isModified) {
            	boolean isAlreadyAdded = false;
            	for (string diKey : listDiKeyA1(di)) {
	                if (!setDIKeyUpsert.add(diKey))
	                   isAlreadyAdded = true;
            	}
            	if (!isAlreadyAdded) {
                    listAccUpsert.add(acc);
                    listDIUpsert.add(di);
            	}
            }
            // testing error handling
            if (Test.isRunningTest() && acc.Name != null && acc.Name.startsWith('FailTest'))
                acc.Name = null;            
        }
        //upsert listAccUpsert;
        list<Database.UpsertResult> listUR = database.upsert(listAccUpsert, false);
        for (integer i = 0; i < listUR.size(); i++) {
            Database.UpsertResult ur = listUR[i];
            if (!ur.isSuccess()) {
                LogBDIError(listDIUpsert[i], ur.getErrors()[0].getMessage(), 'Account1ImportStatus__c');
            }
        }
            
        // update each DI with its new Account1 ID
        // and set contact's primary affiliations
        map<Id, Contact> mapConIdToConUpdate = new map<Id, Contact>();
        for (DataImport__c di: listDI) {
            if (di.Account1Imported__c == null) {
            	Account acc = AccountFromDi(di, 1);
            	if (acc != null) 
	                di.Account1Imported__c = acc.Id;
            }
            // set c1's primary affilation
            if (di.Account1Imported__c != null) {
            	Contact c1 = ContactFromDi(di, 1);
                if (c1 != null) {
	                c1.Primary_Affiliation__c = di.Account1Imported__c;
	                if (mapConIdToConUpdate.get(c1.Id) == null)
	                   mapConIdToConUpdate.put(c1.Id, c1);
                }
            }            
        }
            
        // create/update our A2's
        map<string, string> mapDIFieldToA2Field = mapFieldsForDIObject('Account2', 'Account', listStrDataImportFields);
        listAccUpsert.clear();
        listDIUpsert.clear();
        setDIKeyUpsert.clear();
        for (DataImport__c di : listDI) {
            // skip di's that already have an error
            if (di.Status__c == label.bdiFailed)
               continue;
            
            Account acc = AccountFromDi(di, 2);
            if (acc == null)
                continue;

            boolean isModified = false;             
            for (string strDIField : mapDIFieldToA2Field.keySet()) {
                if (isCopiedDIField(di, strDIField, acc, mapDIFieldToA2Field)) {
                    isModified = true;
                }
            }
            if (isModified) {
                boolean isAlreadyAdded = false;
                for (string diKey : listDiKeyA2(di)) {
                    if (!setDIKeyUpsert.add(diKey))
                       isAlreadyAdded = true;
                }
                if (!isAlreadyAdded) { 
                    listAccUpsert.add(acc);
                    listDIUpsert.add(di);
                }
            }
            // testing error handling
            if (Test.isRunningTest() && acc.Name != null && acc.Name.startsWith('FailTest'))
                acc.Name = null;
        }
        //upsert listAccUpsert;
        listUR = database.upsert(listAccUpsert, false);
        for (integer i = 0; i < listUR.size(); i++) {
            Database.UpsertResult ur = listUR[i];
            if (!ur.isSuccess()) {
                LogBDIError(listDIUpsert[i], ur.getErrors()[0].getMessage(), 'Account2ImportStatus__c');
            }
        }

        // update each DI with its new Account2 ID
        // and set contact's primary affiliations
        for (DataImport__c di: listDI) {
            if (di.Account2Imported__c == null) {
            	Account acc = AccountFromDi(di, 2);
            	if (acc != null) 
                    di.Account2Imported__c = acc.Id;
            }
            // set c2's primary affilation
            if (di.Account2Imported__c != null) {
                Contact c2 = ContactFromDi(di, 2);
                if (c2 != null) {
	                c2.Primary_Affiliation__c = di.Account2Imported__c;
	                if (mapConIdToConUpdate.get(c2.Id) == null)
	                   mapConIdToConUpdate.put(c2.Id, c2);
                }
            }            
        }
        
        // now update the Contacts to create their Affiliations
	    // unfortunately, we must manually clear these static flags since we are doing 
	    // a bunch of stuff in the same context.
	    AFFL_Affiliations_TDTM.doNotRunForContacts = false;
	    AFFL_Affiliations_TDTM.hasRunForAffiliations = false;       
        update mapConIdToConUpdate.values();
    }

    /*******************************************************************************************************
    * @description returns the field name of the Account CustomID field for account1 or account2 in the
    * Data Import object.  Note that if tests are running, it assumes the field is a standard field on 
    * Account and thus has no trailing __c, but needs to have the __c for accessing in the Data Import object.
    * @param strAx  which account to reference (account1 or account2)
    * @return string
    */ 
    private string strDIAccountCustomIDField(string strAx) {
        return strAx + '_' + diSettings.Account_Custom_Unique_ID__c + (test.isRunningTest() ? '__c' : '');
    }
    
    // utility to return the dikeys for a given account.
    private list<string> listDiKeyAccount(Account acc) {
        if (acc == null)
            return null;

        list<string> listDiKey = new list<string>();
        
        // we need any Account SFId key to always be checked first when matching
        if (acc.Id != null) {
            listDiKey.add(acc.Id);
        }

        // we need the Account CustomId key to always be checked second when matching
        string strUniqueId = ''; 
        if (isCustomIDInAccountMatchRules && (strUniqueId = strNull(acc.get(diSettings.Account_Custom_Unique_ID__c))) != '') {
            listDiKey.add(strUniqueId);
        }

        if (acc.Name != null)
            listDiKey.add(acc.Name + '|' + strUniqueId);        
        return listDiKey;
    }

    // utility to return the dikeys for account1 in the given di.
    private list<string> listDiKeyA1(DataImport__c di) {
        return listDiKeyAx(di, 'Account1');
    }
    private list<string> listDiKeyA2(DataImport__c di) {
        return listDIKeyAx(di, 'Account2');
    }
    private list<string> listDiKeyAx(DataImport__c di, string strAx) {
        list<string> listDiKey = new list<string>();

        // we need the Account Lookup key to always be checked first when matching
        string strUniqueId = ''; 
        if ((strUniqueId = strNull(di.get(strAx + 'Imported__c'))) != '') {
            listDiKey.add(strUniqueId);
            strUniqueId = '';
        }

        // we need the Account CustomId key to always be checked second when matching
        if (isCustomIDInAccountMatchRules && (strUniqueId = strNull(di.get(strDIAccountCustomIDField(strAx)))) != '') {
            listDiKey.add(strUniqueId);
        }

        // now create any other dikeys
        string strName;
        if ((strName = string.valueOf(di.get(strAx + '_Name__c'))) != null)
            listDiKey.add(strName + '|' + strUniqueId); 
        return listDiKey;        
    }
    
    // utility that finds an account in our map, by matching against all the account dikeys
    private Account AccountFromDi(DataImport__c di, integer iAcc) {
        for (string diKey : listDiKeyAx(di, iAcc == 1 ? 'Account1' : 'Account2')) {
            Account acc = mapDIKeyToAccount.get(diKey);
            if (acc != null)
                return acc;
        }
        return null;
    }

    // looks for existing Accounts that match A1 or A2, and if found,
    // updates their Id's in the Di's. if not found, creates a new
    // account object for that di.
    private void matchExistingAccounts() {
    
        set<string> setName = new set<string>();
        set<string> setCustomIDString = new set<string>();
        set<double> setCustomIDNumber = new set<double>();
        set<Id> setSFId = new set<Id>();
        
        for (DataImport__c di : listDI) {
            // skip di's that already have an error
            if (di.Status__c == label.bdiFailed)
               continue;

            // a1
            if (di.Account1Imported__c != null)
                setSFId.add(di.Account1Imported__c);
            if (di.Account1_Name__c != null)
                setName.add(di.Account1_Name__c);
            if (isCustomIDInAccountMatchRules) { 
                object val = di.get(strDIAccountCustomIDField('Account1'));
                if (val != null) {
	                if (isCustomIdInAccountDatatypeString)
	                    setCustomIDString.add(string.valueOf(val));
	                else
	                    setCustomIDNumber.add(double.valueOf(val));
                }
            }
            // a2
            if (di.Account2Imported__c != null)
                setSFId.add(di.Account2Imported__c);
            if (di.Account2_Name__c != null)
                setName.add(di.Account2_Name__c);
            if (isCustomIDInAccountMatchRules) { 
                object val = di.get(strDIAccountCustomIDField('Account2'));
                if (val != null) {
                    if (isCustomIdInAccountDatatypeString)
                        setCustomIDString.add(string.valueOf(val));
                    else
                        setCustomIDNumber.add(double.valueOf(val));
                }
            }
        }
        
        
        string strSoql = 'Select Id, ';
        if (isCustomIDInAccountMatchRules)
            strSoql += diSettings.Account_Custom_Unique_ID__c + ', ';                
        strSoql += 'Name from Account where ';
        if (setSFId.size() > 0) {
            strSoql += ' (Id in :setSFId) or ';
        }
        strSoql += 'Name in :setName';
        if (isCustomIDInAccountMatchRules && isCustomIdInAccountDatatypeString) 
            strSoql += ' or ' + diSettings.Account_Custom_Unique_ID__c + ' in :setCustomIDString';     
        else if (isCustomIDInAccountMatchRules && !isCustomIdInAccountDatatypeString) 
            strSoql += ' or ' + diSettings.Account_Custom_Unique_ID__c + ' in :setCustomIDNumber';     
        list<Account> listAcc = database.query(strSoql);
        
        // put all Accounts into our map
        for (Account acc : listAcc) {
            for (string diKey : listDiKeyAccount(acc))
                mapDIKeyToAccount.put(dikey, acc);
        }        
        
        for (DataImport__c di : listDI) {
            // skip di's that already have an error
            if (di.Status__c == label.bdiFailed)
               continue;

            Account acc = AccountFromDi(di, 1);
            list<string> listDiKey = listDiKeyA1(di);
            if (acc != null) {
                di.Account1Imported__c = acc.id;
                di.Account1ImportStatus__c = label.bdiMatched;
            } else if (listDiKey.size() > 0) {
                acc = new Account();
                di.Account1ImportStatus__c = label.bdiCreated;                
            }
            // make sure all dikeys for that account are in the map
            for (string diKey : listDiKey) 
                mapDIKeyToAccount.put(diKey, acc);

            acc = AccountFromDi(di, 2);
            listDiKey = listDiKeyA2(di);
            if (acc != null) {
                di.Account2Imported__c = acc.id;
                di.Account2ImportStatus__c = label.bdiMatched;
            } else if (listDiKey.size() > 0) {
                acc = new Account();
                di.Account2ImportStatus__c = label.bdiCreated;                
            }
            // make sure all dikeys for that account are in the map
            for (string diKey : listDiKey) 
                mapDIKeyToAccount.put(diKey, acc);
        }
    }

    //********************************************* ADDRESSES ************************************************    
    // method to create the default Household Address for each household.
    // must ensure the address doesn't already exist for the case of updating existing contacts and accounts.
    private void importAddresses() {

        // if address mgmt is turned off, then we want to save Home address fields directly on the contacts and HH account
        if (UTIL_CustomSettingsFacade.getContactsSettings().Household_Account_Addresses_Disabled__c) {
            importAddressesAsAddressFields();
            return;
        }
    	
    	// only create Home Address if it doesn't already exist on the Household.
    	// so we need to get all the households, and then get all their addresses.
        set<Id> setHHId = new set<Id>();
        for (DataImport__c di : listDI) {
            // skip di's that already have an error
            if (di.Status__c == label.bdiFailed)
               continue;
            Contact con = ContactFromDi(di, 1);
            if (con != null && con.AccountId != null)
                setHHId.add(con.AccountId);
        }
        list<Address__c> listAddrExisting = [select Id, Household_Account__c, MailingStreet__c, MailingCity__c, MailingState__c,
            MailingPostalCode__c from Address__c where Household_Account__c in :setHHId];
        map<String, Id> mapHHAddrKeyToId = new map<String, Id>();
        for (Address__c addr : listAddrExisting) {
            mapHHAddrKeyToId.put(strKeyOfAddr(addr), addr.Id);
        }        
        
        map<string, string> mapDIFieldToAddrField = mapFieldsForDIObject('Address', UTIL_Namespace.StrTokenNSPrefix('Address__c'), listStrDataImportFields);
        list<Address__c> listAddrInsert = new list<Address__c>();
        list<DataImport__c> listDIInsert = new list<DataImport__c>();
        map<String, Address__c> mapHHAddrKeyToAddrNew = new map<String, Address__c>();
        for (DataImport__c di : listDI) {
            // skip di's that already have an error
            if (di.Status__c == label.bdiFailed)
               continue;
 
            Contact con = ContactFromDi(di, 1);
            Address__c addr = new Address__c();
            addr.Address_Type__c = 'Home';
            addr.Default_Address__c = true;
            addr.Household_Account__c = (con != null ? con.AccountId : null);
            boolean isEmpty = true;
            for (string strDIField : mapDIFieldToAddrField.keySet()) {
                if (isCopiedDIField(di, strDIField, addr, mapDIFieldToAddrField)) {
                    isEmpty = false;
                }
            }
            if (!isEmpty) {
	            // only create Home Address if we have a household
	            if (con == null || con.AccountId == null) {
	               di.HomeAddressImportStatus__c = label.bdiIgnored;
	               continue;
	            }
            	
            	// check to see if we already have this address in this hh.
            	ID idAddr = mapHHAddrKeyToId.get(strKeyOfAddr(addr));
            	if (idAddr != null) {
            	   di.HomeAddressImported__c = idAddr;
            	   di.HomeAddressImportStatus__c = label.bdiMatched;
            	} else {
            	    // see if we are already creating this new address
            	    Address__c addrNew = mapHHAddrKeyToAddrNew.get(strKeyOfAddr(addr));
            	    if (addrNew != null) {
            	        addr = addrNew;
            	    } else {
                        mapHHAddrKeyToAddrNew.put(strKeyOfAddr(addr), addr);            	        
                        listAddrInsert.add(addr);
                        listDIInsert.add(di);
            	    }
                    mapDIIdToAddr.put(di.Id, addr);
            	}
            }
            if (Test.isRunningTest() && addr.MailingCity__c != null && addr.MailingCity__c.startsWith('FailTest')) {
                addr.Household_Account__c = di.Id;  // a bogus id for this field
            }            
        }
        // because we have inserted contacts and accounts in the same context,
        // the address trigger flag will have been set to true.  we need to
        // reset it back to false, or else the newly inserted addresses won't
        // propogate to the hh account and contacts.
        ADDR_Addresses_TDTM.hasRunAddrTrigger = false;        

        // two prong strategy! if any address object fails to save, and we allow
        // salesforce to do the retry with the other records (ie, calling insert with AllOrNothing=false),
        // our hasRunAddrTrigger won't get cleared for the retries, and address propagation will not occur.
        // solution: first try to save all with AllOrNothing=true.  Then if we hit any failures, do one 
        // address record at a time, so we can reset hasRunAddTrigger before each insert.
        try  {
            insert listAddrInsert;        
        } catch (exception ex) {
            for (integer i = 0; i < listAddrInsert.size(); i++) {
                ADDR_Addresses_TDTM.hasRunAddrTrigger = false;        
                Database.SaveResult sr = database.insert(listAddrInsert[i], false);
                if (!sr.isSuccess()) {
                    LogBDIError(listDIInsert[i], sr.getErrors()[0].getMessage(), 'HomeAddressImportStatus__c');
                }
            }        
        }

        // update each DI with its new home address
        for (DataImport__c di: listDI) {
            // skip di's that already have an error
            if (di.Status__c == label.bdiFailed)
               continue;
            if (di.HomeAddressImported__c == null) {
                Address__c addr = mapDIIdToAddr.get(di.Id);
                if (addr != null && addr.Id != null) {
                    di.HomeAddressImported__c = addr.Id;
                    di.HomeAddressImportStatus__c = label.bdiCreated;
                }
            }
        }
    }

    // returns the unique address key for the given address    
    private static string strKeyOfAddr(Address__c addr) {
        return addr.Household_Account__c + addr.MailingStreet__c + addr.MailingCity__c + addr.MailingState__c + addr.MailingPostalCode__c;
    }

    /*******************************************************************************************************
    * @description update the contacts and household account with the Home Address information
    * @return void
    ********************************************************************************************************/
    private void importAddressesAsAddressFields() {

        // track the contacts and hh accounts to update
        set<Id> setIdUpdate = new set<Id>();
        list<Contact> listCon1Update = new list<Contact>();
        list<Contact> listCon2Update = new list<Contact>();
        list<Account> listAccUpdate = new list<Account>();
        list<DataImport__c> listCon1Di = new list<DataImport__c>();
        list<DataImport__c> listCon2Di = new list<DataImport__c>();
        list<DataImport__c> listAccDi = new list<DataImport__c>();
        
        // first get our Household Accounts
        set<Id> setHHId = new set<Id>();
        for (DataImport__c di : listDI) {
            // skip di's that already have an error
            if (di.Status__c == label.bdiFailed)
               continue;
            if (di.HouseholdAccountImported__c != null)
                setHHId.add(di.HouseholdAccountImported__c);
        }
        map<Id, Account> mapHHIdToAcc = new map<Id, Account>([select Id, BillingStreet, BillingCity, 
            BillingState, BillingPostalCode, BillingCountry from Account where Id in :setHHId]);

        // now update home address on the contacts and hh account
        for (DataImport__c di : listDI) {
            // skip di's that already have an error
            if (di.Status__c == label.bdiFailed)
               continue;
            Contact con1 = ContactFromDi(di, 1);
            Contact con2 = ContactFromDi(di, 2);
            Account accHH = mapHHIdToAcc.get(di.HouseholdAccountImported__c);
            
            if (di.Home_Street__c != null ||
                di.Home_City__c != null ||
                di.Home_State_Province__c != null ||
                di.Home_Zip_Postal_Code__c != null ||
                di.Home_Country__c != null) {
                
                // use an Address object, so we can leverage the copy methods that support state & country picklists.
                Address__c addr = new Address__c();
                addr.MailingStreet__c = di.Home_Street__c;
                addr.MailingCity__c = di.Home_City__c;
                addr.MailingState__c = di.Home_State_Province__c;
                addr.MailingPostalCode__c = di.Home_Zip_Postal_Code__c;
                addr.MailingCountry__c = di.Home_Country__c;
                addr.Address_Type__c = 'Home';
                
                if (con1 != null) {
                    ADDR_Addresses_TDTM.copyAddressAddrSObj(addr, con1, 'Mailing', 'npe01__Primary_Address_Type__c');
                    if (setIdUpdate.add(con1.Id)) {
                        listCon1Update.add(con1);
                        listCon1Di.add(di);
                    }
                }            
                if (con2 != null) {
                    ADDR_Addresses_TDTM.copyAddressAddrSObj(addr, con2, 'Mailing', 'npe01__Primary_Address_Type__c');
                    if (setIdUpdate.add(con2.Id)) {
                        listCon2Update.add(con2);
                        listCon2Di.add(di);
                    }
                }
                if (accHH != null) {
                    ADDR_Addresses_TDTM.copyAddressAddrSObj(addr, accHH, 'Billing', null);
                    if (setIdUpdate.add(accHH.Id)) {
                        listAccUpdate.add(accHH);
                        listAccDi.add(di);
                    }
                }
            }
        }
                
        // now update the objects
        list<Database.UpsertResult> listUR = database.upsert(listCon1Update, false);
        for (integer i = 0; i < listUR.size(); i++) {
            Database.UpsertResult ur = listUR[i];
            if (!ur.isSuccess()) {
                LogBDIError(listCon1Di[i], ur.getErrors()[0].getMessage(), 'Contact1ImportStatus__c');
            }
        }
        listUR = database.upsert(listCon2Update, false);
        for (integer i = 0; i < listUR.size(); i++) {
            Database.UpsertResult ur = listUR[i];
            if (!ur.isSuccess()) {
                LogBDIError(listCon2Di[i], ur.getErrors()[0].getMessage(), 'Contact2ImportStatus__c');
            }
        }
        listUR = database.upsert(listAccUpdate, false);
        for (integer i = 0; i < listUR.size(); i++) {
            Database.UpsertResult ur = listUR[i];
            if (!ur.isSuccess()) {
                LogBDIError(listAccDi[i], ur.getErrors()[0].getMessage(), 'HomeAddressImportStatus__c');
            }
        }
        
    }

    //********************************************* OPPORTUNITIES ************************************************
    // method to create an new Opportunity for the specified Contact or Account
    private void importOpportunities() {
    
        // get the fields we will auto-copy
        map<string, string> mapDIFieldToOppField = mapFieldsForDIObject('Opportunity', 'Opportunity', listStrDataImportFields);
        list<Opportunity> listOppInsert = new list<Opportunity>();
        list<DataImport__c> listDIInsert = new list<DataImport__c>();
        
        for (DataImport__c di : listDI) {
            // skip di's that already have an error
            if (di.Status__c == label.bdiFailed)
               continue;
            integer cPropSet = 0;
            Opportunity opp = new Opportunity();
            string strOppName;
            
            // ignore this row if we've already imported an opp 
            if (di.DonationImported__c != null)
                continue;

            if (di.Donation_Donor__c == 'Contact1' || (di.Donation_Donor__c == null && di.Donation_Amount__c != null)) {
                Contact con = ContactFromDi(di, 1);
                if (con == null) {
                    LogBDIError(di, label.bdiErrorInvalidDonor, 'DonationImportStatus__c');
                    continue;
                }                
                opp.npe01__Contact_Id_for_Role__c = con.Id;
                opp.Primary_Contact__c = con.Id;
                opp.Primary_Contact_Campaign_Member_Status__c = di.Campaign_Member_Status__c;
                opp.AccountId = con.AccountId;
                strOppName = con.Firstname + ' ' + con.Lastname;
            } else if (di.Donation_Donor__c == 'Account1') {
                Account acc = AccountFromDi(di, 1);
                if (acc == null) {
                    LogBDIError(di, label.bdiErrorInvalidDonor, 'DonationImportStatus__c');
                    continue;
                }
                opp.accountId = acc.Id;    
                strOppName = acc.Name;            
            } else if (di.Donation_Donor__c != null) {
                LogBDIError(di, label.bdiErrorInvalidDonor, 'DonationImportStatus__c');
                continue;
            }
                
            if (di.Donation_Record_Type_Name__c != null) {
                Id idRt = UTIL_RecordTypes.GetRecordTypeId(Opportunity.sObjectType, di.Donation_Record_Type_Name__c);
                if (idRt == null) {
                    LogBDIError(di, label.bdiErrorInvalidOppRTName, 'DonationImportStatus__c');
                    continue;
                }
                opp.RecordTypeId = idRt;
                strOppName += ' ' + di.Donation_Record_Type_Name__c;
            } else {
                strOppName += ' ' + label.bdiDonation;
            }
            
            if (di.Donation_Campaign_Name__c != null) {
                Id idCmp = mapStrNameToCampaignId.get(di.Donation_Campaign_Name__c);
                if (idCmp == null) {
                    LogBDIError(di, label.bdiErrorInvalidCampaignName, 'DonationImportStatus__c');
                    continue;
                }
                opp.CampaignId = idCmp;
            }
            
            if (di.Donation_Date__c == null) {
                di.Donation_Date__c = system.today();
                cPropSet--;
            }
            
            if (di.Donation_Name__c == null) {
                di.Donation_Name__c = strOppName + ' ' + di.Donation_Date__c.format();
                cPropSet--;
            } 
                
            if (di.Donation_Stage__c == null && defaultClosedWonStage != '') {
                di.Donation_Stage__c = defaultClosedWonStage;
                cPropSet--;
            }

            for (string strDIField : mapDIFieldToOppField.keySet()) {
                if (isCopiedDIField(di, strDIField, opp, mapDIFieldToOppField)) {
                    cPropSet++;
                }
            }
            // unfortunately, if there are checkbox fields that we copied, we can't tell the difference
            // between null or false, so looking at cPropSet isn't good enough!  let's also look for an account specified.
            if (cPropSet > 0 && opp.AccountId != null) {
                mapDIIdToOpp.put(di.Id, opp);
                listOppInsert.add(opp);
                listDIInsert.add(di);  
            } else {
            	// clear out fields we autoset since we didn't actually create an opp.
            	di.Donation_Date__c = null;
            	di.Donation_Name__c = null;
            	di.Donation_Stage__c = null;
            }
            if (Test.isRunningTest() && opp.Name != null && opp.Name.startsWith('FailTest'))
                opp.Name = null;
        }
        //insert mapDIIdToOpp.values();
        list<Database.SaveResult> listSR = database.insert(listOppInsert, false);
        for (integer i = 0; i < listSR.size(); i++) {
            Database.SaveResult sr = listSR[i];
            if (!sr.isSuccess()) {
                LogBDIError(listDIInsert[i], sr.getErrors()[0].getMessage(), 'DonationImportStatus__c');
            }
        }
        
        // update each DI with its new opp
        for (DataImport__c di: listDI) {
            if (di.DonationImported__c == null) {
                Opportunity opp = mapDIIdToOpp.get(di.Id);
                if (opp != null && opp.Id != null) {
                    di.DonationImported__c = opp.Id;
                    di.DonationImportStatus__c = label.bdiCreated;
                }
            }
        }  
        
        // now deal with any payment child records
        updatePayments();      
    }
    
    // finds a valid Close Won Opportunity Stage
    private static string defaultClosedWonStage {
        get {	
            if (defaultClosedWonStage == null){
                List<OpportunityStage> listStg = [select masterlabel from opportunitystage where isActive = true and iswon = true];
                if (listStg.size() > 0) {
                    defaultClosedWonStage = listStg[0].masterlabel;
                } else {
                    defaultClosedWonStage = '';
                }
            }
            return defaultClosedWonStage;
        }
        set;
    }
    
    // method to update any fields on payment objects created for the Data Import opportunities.
    private void updatePayments() {

        // need to find payment records, to add any additional info.
        map<Id, npe01__OppPayment__c> mapOppIdToPayment = new map<Id, npe01__OppPayment__c>();
        for (npe01__OppPayment__c pmt : [select Id, npe01__Opportunity__c from npe01__OppPayment__c where npe01__Opportunity__c in :mapDIIdToOpp.values()]) {
            mapOppIdToPayment.put(pmt.npe01__Opportunity__c, pmt);
        }

        // get the payment fields we will auto-copy
        map<string, string> mapDIFieldToPmtField = mapFieldsForDIObject('Payment', 'npe01__OppPayment__c', listStrDataImportFields);
        list<npe01__OppPayment__c> listPmtUpsert = new list<npe01__OppPayment__c>();
        list<DataImport__c> listDIUpsert = new list<DataImport__c>();

        // update payments
        for (DataImport__c di: listDI) {
            // skip di's that already have an error
            if (di.Status__c == label.bdiFailed)
               continue;        	
            if (di.DonationImported__c != null) {
                npe01__OppPayment__c pmt = mapOppIdToPayment.get(di.DonationImported__c);
                if (pmt != null) {
                    integer cPropSet = 0;
                    for (string strDIField : mapDIFieldToPmtField.keySet()) {
                        if (isCopiedDIField(di, strDIField, pmt, mapDIFieldToPmtField)) {
                            cPropSet++;
                        }
                    }                    
                    if (cPropSet > 0) {
                        mapDIIdPmt.put(di.Id, pmt);
                        listPmtUpsert.add(pmt);
                        listDIUpsert.add(di);
                    }
	            }
	        }
        }
        
        //upsert mapDIIdPmt.values();
        list<Database.UpsertResult> listUR = database.upsert(listPmtUpsert, false);
        for (integer i = 0; i < listUR.size(); i++) {
            Database.UpsertResult ur = listUR[i];
            if (!ur.isSuccess()) {
                LogBDIError(listDIUpsert[i], ur.getErrors()[0].getMessage(), null);
            }
        }
        
    }    

    //********************************************* CAMPAIGNS ************************************************
    // utility to match to existing campaigns, and create new campaigns, as needed.
    private void importCampaigns() {

        // need to find all the Campaigns that we might need
        set<string> setStrCampaign = new set<string>();
        for (DataImport__c di : listDI) {
            // skip di's that already have an error
            if (di.Status__c == label.bdiFailed)
               continue;
            if (di.Donation_Campaign_Name__c != null)
                setStrCampaign.add(di.Donation_Campaign_Name__c);
        }

        // add the existing campaigns to our map
        for (Campaign cmp : [select Id, Name from Campaign where Name in :setStrCampaign]) {
            mapStrNameToCampaignId.put(cmp.Name, cmp.Id);
        }
        
        // create any new campaigns
        list<Campaign> listCmpNew = new list<Campaign>();
        list<DataImport__c> listDINew = new list<DataImport__c>();
        setStrCampaign.clear();
        for (DataImport__c di : listDI) {
            // skip di's that already have an error
            if (di.Status__c == label.bdiFailed)
               continue;
            string strName = di.Donation_Campaign_Name__c;
            Campaign cmp;
            if (strName != null && setStrCampaign.add(strName)) {
	            if (mapStrNameToCampaignId.get(strName) == null) {
	            	cmp = new Campaign(Name=strName, IsActive=true);
	            	listCmpNew.add(cmp);
	            	listDINew.add(di);
	            }
            }
            if (Test.isRunningTest() && strName != null && strName.startsWith('FailTest'))
                cmp.Name = null;
        }
        //insert listCmpNew;
        list<Database.UpsertResult> listUR = database.upsert(listCmpNew, false);
        for (integer i = 0; i < listUR.size(); i++) {
            Database.UpsertResult ur = listUR[i];
            if (!ur.isSuccess()) {
                LogBDIError(listDINew[i], ur.getErrors()[0].getMessage(), 'DonationImportStatus__c');
            }
        }
        
        // add the new campaigns to our map
        for (Campaign cmp : listCmpNew) {
            mapStrNameToCampaignId.put(cmp.Name, cmp.Id);
        }
            
    }

    //********************************************* CAMPAIGN MEMBERS ************************************************
    // method to create an new CAMPAIGN MEMBERS for imported Contacts and Campaigns
    private void importCampaignMembers() {
        list<string> newCmContactIds = new list<string>();
        list<string> listCampaignIds = new list<string>();
        list<string> listCmStatues = new list<string>();
        // loop over the dis contacts and find existing cms or new cms to create (if they have a campaign)
        for (DataImport__c di : listDI) {
            // skip that already have an error
            if (di.Status__c == label.bdiFailed)
                continue;
            // only create CMs here for rows that don't have an opportunity
            if (di.Donation_Campaign_Name__c != null && di.DonationImported__c == null) {
                // assume errors would have already been thrown
                Id idCmp = mapStrNameToCampaignId.get(di.Donation_Campaign_Name__c);
                string diStatus = String.isBlank(di.Campaign_Member_Status__c) ? Label.campaignMemberStatusDefault : di.Campaign_Member_Status__c;

                if (di.Contact1Imported__c != null) {
                    newCmContactIds.add(di.Contact1Imported__c);
                    listCampaignIds.add(idCmp); // current campaign for row
                    listCmStatues.add(diStatus);
                }
                if (di.Contact2Imported__c != null) {
                    newCmContactIds.add(di.Contact2Imported__c);
                    listCampaignIds.add(idCmp); // current campaign for row
                    listCmStatues.add(diStatus);
                }
            }
        }

        // return DML wrapper for new CMs
        TDTM_Runnable.DMLWrapper newCmWrapper = CON_AddToCampaign.CreateCampaignMembers(newCmContactIds,listCampaignIds,listCmStatues);

        // process dmls for CMs
        TDTM_TriggerHandler.processDML(newCmWrapper);
    }
}