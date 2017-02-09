/*
    Copyright (c) 2011, Salesforce.org
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
* @date 2011 (2.3)
* @group Households
* @description Controls generation of household names for lists of households
* NOTE: This class handles both household naming for the Household__c object
* as well as for HH Account objects.
*/
public without sharing class HH_HouseholdNaming {

    /*******************************************************************************************************
    * @description future method to update the household names for the specified households
    * @param hhids the list of household Id's (either Household__c or Account Id's)
    * @return void
    */
    @future
    public static void FutureUpdateNames(list<id> hhids){
        //set our process control to avoid recursive updating on household records
        HH_ProcessControl.inFutureContext = true;

        //create a new instance of the HH_HouseholdNaming class -
        //and pass in our param
        //this is b/c of the public visibility
        HH_HouseholdNaming hn = new HH_HouseholdNaming();

        hn.UpdateNames(hhids);

        HH_ProcessControl.inFutureContext = false;    
    }

    /*******************************************************************************************************
    * @description Sets Number_Of_Household_Members__c, and
    * it will be responsible for only updating names if the advanced household naming is true.
    * So any code that used to check for advanced household naming should no longer do so,
    * (unless it is only for a naming scenario that would possibly change number of household members.)
    * @param hhids the list of household Id's (either Household__c or Account Id's)
    * @return void
    */
    public void UpdateNames(list<id> hhids){

        //we need this turned on to prevent recursive triggering on household creation
        HH_ProcessControl.inFutureContext = true;

        //get all of the contacts and their associated households
        //return them in the order of household and then household naming order
        //we use dynamic soql so we can include all contact fields, since custom naming may refer to any field.
        string strSoql = strContactSelectStmtAllNamingFields;
        string strHHId = UTIL_Namespace.StrTokenNSPrefix('HHId__c');
        strSoql += ' WHERE (AccountId != null AND AccountId IN :hhids) OR (npo02__Household__c != null AND npo02__Household__c in :hhids) ' +
            ' ORDER BY ' + strHHId + ', npo02__Household_Naming_Order__c ASC NULLS LAST, Primary_Contact__c DESC, CreatedDate ';
        list<Contact> contactlist = Database.Query(strSoql);

        list<SObject> listHHObj = [select Id, Name, npo02__SYSTEM_CUSTOM_NAMING__c from npo02__Household__c where id IN : hhids];
        list<SObject> listHHAcct = [select Id, Name, npo02__SYSTEM_CUSTOM_NAMING__c from Account where id IN : hhids];

        // handle two types of households by using an SObject
        list<SObject> hhupdatelist = new list<SObject>();
        hhupdatelist.addAll(listHHObj);
        hhupdatelist.addAll(listHHAcct);

        map<id, list<Contact>> hhIDContactMap = new map<id, list<Contact>>();

        //sort contacts by household
        for (Contact c : contactlist){
            if(!hhIDContactMap.containskey(c.hhId__c)){
                hhIDContactMap.put(c.hhId__c, new list<Contact>{c});
            }
            else{
                list<Contact> clist = hhIDContactMap.get(c.hhId__c);
                clist.add(c);
            }
        }

        npo02__Households_Settings__c hs = UTIL_CustomSettingsFacade.getHouseholdsSettings();

        //loop through the households needing name changes, if necessary, make them.
        for (SObject h : hhupdatelist){
            ID hhId = string.valueOf(h.get('Id'));
            list<Contact> listCon = hhIDContactMap.get(hhId);

            if (hs.npo02__Advanced_Household_Naming__c == true) {
	            string customnameparam = '';
	            if (h.get('npo02__SYSTEM_CUSTOM_NAMING__c') != null){
	                customnameparam = string.valueOf(h.get('npo02__SYSTEM_CUSTOM_NAMING__c'));
	            }

	            if (!customnameparam.contains('Name'))
	                h.put('Name', getHHName(listCon));

	            if (!customnameparam.contains('Formal_Greeting__c'))
	                h.put('npo02__Formal_Greeting__c', getFormalName(listCon));

	            if (!customnameparam.contains('Informal_Greeting__c'))
	                h.put('npo02__Informal_Greeting__c', getInformalName(listCon));
            }

            // now we also store the number of members on the Household!
            h.put(UTIL_Namespace.StrTokenNSPrefix('Number_of_Household_Members__c'), listCon != null ? listCon.size() : 0);
        }

        if (!hhupdatelist.isEmpty()) {
        	//Database.SaveResult[] listSR = Database.update(hhupdatelist, false);
        	// all or nothing, so errors not lost
        	update hhupdatelist;
        }

        HH_ProcessControl.inFutureContext = false;
    }


    /*******************************************************************************************************
    * @description our cached copy of the Household Naming Settings
    */
    private static Household_Naming_Settings__c hns {
        get {
            if (hns == null) hns = UTIL_CustomSettingsFacade.getHouseholdNamingSettings();
            return hns;
        }
        set;
    }

    /*******************************************************************************************************
    * @description the class object that supports the HH_INaming interface
    */
    public static HH_INaming iNaming {
    	get {
    		if (iNaming == null) {
		        string strClass = hns.Implementing_Class__c;
		        if (strClass == null || strClass == '') strClass = 'HH_NameSpec';
		        Type classType = Type.forName(strClass);
		        if (classType != null) {
		            Object classInstance = classType.newInstance();
		            if (classInstance instanceof HH_INaming) {
		                iNaming = (HH_INaming)classInstance;
		            }
		        }
    		}
    		return iNaming;
    	}
    	set;
    }

    /*******************************************************************************************************
    * @description returns the Household Name for the list of Contacts
    * @param ContactList the list of Contacts in the same Household
    * @return string
    */
    public string getHHName(list<Contact> ContactList){
        if (ContactList == null)
            ContactList = new list<Contact>();
    	list<Contact> listCon = new list<Contact>();
    	for (Contact con : ContactList) {
            if (con.npo02__Naming_Exclusions__c == null || !con.npo02__Naming_Exclusions__c.contains('Name'))
                listCon.add(con);
    	}
        return iNaming.getHouseholdName(listCon);
    }

    /*******************************************************************************************************
    * @description returns the Informal Greeting for the list of Contacts
    * @param ContactList the list of Contacts in the same Household
    * @return string
    */
    public  string getInformalName(list<Contact> ContactList){
        if (ContactList == null)
            ContactList = new list<Contact>();
        list<Contact> listCon = new list<Contact>();
        for (Contact con : ContactList) {
            if (con.npo02__Naming_Exclusions__c == null || !con.npo02__Naming_Exclusions__c.contains('Informal_Greeting__c'))
                listCon.add(con);
        }
        return iNaming.getHouseholdInformalGreeting(listCon);
    }

    /*******************************************************************************************************
    * @description returns the Formal Greeting for the list of Contacts
    * @param ContactList the list of Contacts in the same Household
    * @return string
    */
    public  string getFormalName(list<Contact> ContactList){
        if (ContactList == null)
            ContactList = new list<Contact>();
        list<Contact> listCon = new list<Contact>();
        for (Contact con : ContactList) {
            if (con.npo02__Naming_Exclusions__c == null || !con.npo02__Naming_Exclusions__c.contains('Formal_Greeting__c'))
                listCon.add(con);
        }
        return iNaming.getHouseholdFormalGreeting(listCon);
    }

    /*******************************************************************************************************
    * @description executes the batch job to update all household names
    * @param isActivation whether this is being called when npo02__Advanced_Household_Naming__c is being turned on
    * @return void
    */
    public static void refreshAllHouseholdNaming(boolean isActivation) {
        // the household batch expects a list of Contact's with just Id, LastName, HHId__c available.
        string strSoql = 'SELECT Id, LastName, ' + UTIL_Namespace.StrTokenNSPrefix('HHId__c') + ' FROM Contact WHERE ' +
            UTIL_Namespace.StrTokenNSPrefix('HHId__c') + ' != NULL ';
        HH_HouseholdNaming_BATCH batch = new HH_HouseholdNaming_BATCH(strSoql, isActivation);
        integer batchSize = 200;
        id batchProcessId = database.executeBatch(batch, batchSize);
    }

    /*******************************************************************************************************
    * @description list of all Contact fields for using in soql's
    */
    private static list<string> listStrContactFields {
        get {
            if (listStrContactFields == null) {
                Map<String, Schema.SObjectField> mapS = Schema.SObjectType.Contact.fields.getMap().clone();
                // due to a Salesforce bug, we get these new fields if our caller is api 30 or greater,
                // but we will hit an error if we soql for them since we are api 28.
                // this workaround will no longer be needed when we move up to version 30 or greater.
                mapS.remove('mailingaddress');
                mapS.remove('otheraddress');
                listStrContactFields = new list<string>();
                listStrContactFields.addAll(mapS.keySet());
            }
            return listStrContactFields;
        }
        set;
    }

    /*******************************************************************************************************
    * @description the soql select string to use which includes any fields we use or are referred to in
    * any of the naming specifications.
    */
    public string strContactSelectStmtAllNamingFields {
        get {
	        if (strContactSelectStmtAllNamingFields == null) {
		        //we use dynamic soql so we can include all contact fields, since custom naming may refer to any field.
		        string strSoql = 'SELECT Id, HHId__c, npo02__Naming_Exclusions__c';
		        
		        set<string> setStrField = iNaming.setHouseholdNameFieldsOnContact();
		        for (string strF : setStrField) {
                    if (strF == 'Salutation') strF = 'toLabel(Salutation)';
                    strSoql += ', ' + strF;
		        }
		        strSoql += ' FROM Contact ';
		        strContactSelectStmtAllNamingFields = strSoql;
	        }
	        return strContactSelectStmtAllNamingFields;
        }
        private set;
    }

    /*******************************************************************************************************
    * @description pseudo-trigger handler for dealing with changes to the system name fields on the Household
    * in case they are changed and require recalculating the Household name and greetings.
    * @param listNew the list of Households/Accounts from trigger new. 
    * @param listOld the list of Households/Accounts from trigger old. 
    * @param triggerAction which trigger event (BeforeInsert, AfterInsert, etc.). 
    * @param objResult the describe for Households/Accounts 
    * @return void
    */
    public static void handleHHNameUpdates(List<SObject> newlist, List<SObject> oldlist,
        TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {

        list<SObject> listHHNew = newlist;
        list<SObject> listHHOld = oldlist;
        map<ID, SObject> mapIdHHOld = new map<ID, SObject>(listHHOld);

        npo02__Households_Settings__c hs = UTIL_CustomSettingsFacade.getHouseholdsSettings();

        //updates household records to indicate where/if user changes to the household record are happening
        //and marks them as such so they won't be updated
        //need to use a process control class to avoid recursion when async updates occur
        //in non async updates, this won't fire again, so we don't need to worry
        if (!HH_ProcessControl.inFutureContext &&
            (triggerAction == TDTM_Runnable.Action.BeforeUpdate || triggerAction == TDTM_Runnable.Action.AfterUpdate)) {

            // no need to do any of this name checking if we aren't using auto household naming.
            // even though UpdateNames() keeps Number of Household Members updated, there is no need
            // to call it from here which is just trying to detect manual hh name changes.
            if (hs != null && hs.npo02__Advanced_Household_Naming__c == true ) {
                // AfterUpdate data
                list<id> hhlist = new list<id>();

                for (SObject h : listHHNew) {
                    string customname = '';

                    if (h.get('npo02__SYSTEM_CUSTOM_NAMING__c') == null)
                        customname = ';';
                    else
                        customname = h.get('npo02__SYSTEM_CUSTOM_NAMING__c') + ';';

                    // BeforeUpdate data
                    list<string> customnamelist = new list<string>();
                    set<string> customnameset = new set<string>();
                    customnamelist = customname.split(';');
                    customnameset.addall(customnamelist);

                    if (triggerAction == TDTM_Runnable.Action.BeforeUpdate) {
                    	string hName = (string)h.get('Name');
                        if (hName == null || hName == '' || hName == system.Label.npo02.NameReplacementText) {
                            if (customnameset.contains('Name')) {
                                customname = customname.replace('Name;', ';');
                            }
                            h.put('Name', system.Label.npo02.NameReplacementText);
                        } else if (hName != mapIdHHOld.get(h.id).get('Name') && !customnameset.contains('Name')) {
                            customname += 'Name' + ';';
                        }

                        string informalGreeting = (string)h.get('npo02__Informal_Greeting__c');
                        if (informalGreeting == null || informalGreeting == '' || informalGreeting == system.Label.npo02.NameReplacementText) {
                            if (customnameset.contains('Informal_Greeting__c')) {
                                customname = customname.replace('Informal_Greeting__c;', ';');
                            }
                            h.put('npo02__Informal_Greeting__c', system.Label.npo02.NameReplacementText);
                        } else if (informalGreeting != mapIdHHOld.get(h.id).get('npo02__Informal_Greeting__c') && 
                            !customnameset.contains('Informal_Greeting__c')) {
                            customname += 'Informal_Greeting__c' + ';';
                        }

                        string formalGreeting = (string)h.get('npo02__Formal_Greeting__c');
                        if (formalGreeting == null || formalGreeting == '' || formalGreeting == system.Label.npo02.NameReplacementText) {
                            if (customnameset.contains('Formal_Greeting__c')) {
                                customname = customname.replace('Formal_Greeting__c;', ';');
                            }
                            h.put('npo02__Formal_Greeting__c', system.Label.npo02.NameReplacementText);
                        } else if (formalGreeting != mapIdHHOld.get(h.id).get('npo02__Formal_Greeting__c') &&
                            !customnameset.contains('Formal_Greeting__c')) {
                            customname += 'Formal_Greeting__c' + ';';
                        }
                            
                        h.put('npo02__SYSTEM_CUSTOM_NAMING__c', customname);

                    } else if (triggerAction == TDTM_Runnable.Action.AfterUpdate) {
                        if (h.get('Name') == system.Label.npo02.NameReplacementText)
                           hhlist.add(h.id);
                        else if (h.get('npo02__Informal_Greeting__c') == system.Label.npo02.NameReplacementText)
                           hhlist.add(h.id);
                        else if (h.get('npo02__Formal_Greeting__c') == system.Label.npo02.NameReplacementText)
                            hhlist.add(h.id);
                        else if (h.get('npo02__SYSTEM_CUSTOM_NAMING__c') != mapIdHHOld.get(h.id).get('npo02__SYSTEM_CUSTOM_NAMING__c'))
                            hhlist.add(h.id);
                        else if (h.getSObjectType() == Account.sObjectType && h.get('npe01__One2OneContact__c') != mapIdHHOld.get(h.id).get('npe01__One2OneContact__c'))
                            hhlist.add(h.id);
                    }
                }
                if (!hhlist.isEmpty()){
                    HH_HouseholdNaming hn = new HH_HouseholdNaming();
                    hn.UpdateNames(hhlist);
                }
            }
        }

    }

}