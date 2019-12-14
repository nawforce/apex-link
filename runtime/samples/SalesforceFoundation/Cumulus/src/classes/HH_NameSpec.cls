/*
    Copyright (c) 2014, Salesforce.org
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
* @group Households
* @description HH_NameSpec class that implements template based naming for Households.
*/
public with sharing class HH_NameSpec implements HH_INaming {

    /*******************************************************************************************************
    * @description Get the Household name for the list of Contacts from a single Household.
    * Contacts who have set their npo02__Naming_Exclusions__c field to be excluded from the name, are not included in the list of Contacts.
    * If the list of Contacts is empty, returns the appropriate 'anonymous' name.  
    * @param listCon the list of Contacts in the Household
    * @return the Household name
    */ 
    public string getHouseholdName(list<Contact> listCon) { 
        NameSpec ns = new NameSpec(hns, 'Household_Name_Format__c');
        return strNameFromNameSpec(ns, listCon);            	
    }

    /*******************************************************************************************************
    * @description Get the Household Formal Greeting for the list of Contacts from a single Household.
    * Contacts who have set their npo02__Naming_Exclusions__c field to be excluded from the greeting, are not included in the list of Contacts.
    * If the list of Contacts is empty, returns the appropriate 'anonymous' greeting.  
    * @param listCon the list of Contacts in the Household
    * @return the Household Formal Greeting
    */ 
    public string getHouseholdFormalGreeting(list<Contact> listCon) { 
        NameSpec ns = new NameSpec(hns, 'Formal_Greeting_Format__c');
        return strNameFromNameSpec(ns, listCon);                
    }

    /*******************************************************************************************************
    * @description Get the Household Informal Greeting for the list of Contacts from a single Household.
    * Contacts who have set their npo02__Naming_Exclusions__c field to be excluded from the greeting, are not included in the list of Contacts.
    * If the list of Contacts is empty, returns the appropriate 'anonymous' greeting.  
    * @param listCon the list of Contacts in the Household
    * @return the Household Informal Greeting
    */ 
    public string getHouseholdInformalGreeting(list<Contact> listCon) { 
        NameSpec ns = new NameSpec(hns, 'Informal_Greeting_Format__c');
        return strNameFromNameSpec(ns, listCon);                
    }
    
    /*******************************************************************************************************
    * @description For the Settings page, get the formatted name or greeting for the list of Contacts.
    * @param hns an in-memory Household Naming Settings object
    * @param strField is one of: Household_Name_Format__c, Formal_Greeting_Format__c, or Informal_Greeting_Format__c.
    * @param listCon the list of Contacts in the Household
    * @return the appropriate Household name or greeting
    */ 
    public string getExampleName(Household_Naming_Settings__c hnsMem, string strField, list<Contact> listCon) {
        NameSpec ns = new NameSpec(hnsMem, strField);
        return strNameFromNameSpec(ns, listCon);        
    }
    
    /*******************************************************************************************************
    * @description To allow the NPSP code to know which fields to query for on Contact, before handing them off to the naming routines.
    * @return set of strings of all fields referenced from Contact.
    */
    public set<string> setHouseholdNameFieldsOnContact() {
        set<string> setStrField = new set<string>(); 
        NameSpec ns;
        ns = new NameSpec(hns, 'Household_Name_Format__c');
        setStrField.addAll(ns.setStrFieldsInNameSpec());
        ns = new NameSpec(hns, 'Formal_Greeting_Format__c');
        setStrField.addAll(ns.setStrFieldsInNameSpec());
        ns = new NameSpec(hns, 'Informal_Greeting_Format__c');
        setStrField.addAll(ns.setStrFieldsInNameSpec());
        return setStrField;
    }
    
    /*******************************************************************************************************
    * @description our cached copy of the Household Naming Settings
    */
    private Household_Naming_Settings__c hns {
    	get {
    		if (hns == null) hns = UTIL_CustomSettingsFacade.getHouseholdNamingSettings();
    		return hns;
    	}
    	set;
    }

    private static string chLToken = '{!';
    private static string chRToken = '}';
        
    /*******************************************************************************************************
    * @description 
    * Class that supports the parsing of a format string from the Household Naming Settings object.
    *
    * The NameSpec looks like:
    *      prefix {lastname} {{firstnamespec}} suffix
    * Where firstname spec is a combo of fields and literals like:
    *      {salutation} {firstname} ({mailingcity})
    *
    */
    private class NameSpec {
        private string strPrefix { get; set; }
        private string strSuffix { get; set; }
        private string strFirstNameSpec { get; set; }
        private string strFullNameSpec { get; set; }
        private string strAnd { get; set; }
        private string strOverrun { get; set; }
        private integer cMaxUseOverrun { get; set; }
        private string strHNSField { get; set; }
        
        /*******************************************************************************************************
        * @description NameSpec Constructor
        * @param hns An instance of Household Naming Settings to use.
        * @param strField The namespec field in Settings to use.
        */
        private NameSpec(Household_Naming_Settings__c hns, string strField) {
            strHNSField = strField; 
            string strNameSpec = string.valueOf(hns.get(strField));
            strAnd = (hns.Name_Connector__c == null ? ' ' : ' ' + hns.Name_Connector__c + ' ');
            strOverrun = hns.Name_Overrun__c;
            cMaxUseOverrun = integer.valueOf(hns.Contact_Overrun_Count__c);
            parseNameSpec(strNameSpec);
        }
        
        /*******************************************************************************************************
        * @description Given the namespec string, parses out its constituent parts, and sets them in the class.
        * @param strNameSpec The namespec
        */
        private void parseNameSpec(string strNameSpec) {
            strPrefix = '';
            strSuffix = '';
            strFirstNameSpec = '';
            strFullNameSpec = '';
            
            if (strNameSpec == null)
                return;
            
            // get optional Prefix
            integer ich = strNameSpec.indexOf(chLToken);   
            if (ich > 0) {
                // don't include any tokens immediately next to the '{' in the prefix
                //while (ich > 0 && strNameSpec.substring(ich, ich+1) != ' ') ich--;
                strPrefix = strNameSpec.left(ich);
                strNameSpec = strNameSpec.substring(ich);
            }
               
            // get optional Suffix
            ich = strNameSpec.lastIndexOf(chRToken);
            if (ich > 0) {
                // don't include any tokens immediately after the '}' in the suffix
                while (ich < strNameSpec.length()-1 && strNameSpec.substring(ich+1, ich+2) != ' ') ich++;
                strSuffix = strNameSpec.substring(ich+1);
                strNameSpec = strNameSpec.left(ich+1);
            }
            
            // extract FirstNameSpec
            string str = strFNameSpecFromStrNameSpec(strNameSpec);

            // replace the firstnamespec with a token in our fullnamespec
            ich = str.indexOf(chLToken);
            integer ichR = str.lastIndexOf(chRToken);
            if (ich >= 0 && ichR > 0) {
                strFullNameSpec = strNameSpec.replace(str, 'FirstNameSpec');
                strFirstNameSpec = str.substring(ich + chLToken.length(), ichR);
            } else {
                strFullNameSpec = strNameSpec;
            }
        }
        
        /*******************************************************************************************************
        * @description Returns a set of all field names in all parts of the namespec
        * @return string
        */
        public set<string> setStrFieldsInNameSpec() {
            set<string> setStrField = new set<string>();
            if (strFirstNameSpec != null)
                setStrField.addAll(setStrFieldFromStrFSpec(strFirstNameSpec));
            if (strFullNameSpec != null)
                setStrField.addAll(setStrFieldFromStrFSpec(strFullNameSpec));
            setStrField.add('LastName');    
            return setStrField;
        }
    }
    
    
    /*******************************************************************************************************
    * @description Given a namespec and a list of Contacts (assumed from a single household),
    * return the formated string for that household.  
    * this can be used for any of the household strings (name, formal greeting, informal greeting).
    * @param ns The namespec class
    * @param listCon the list of Contacts in the Household
    * return string
    */
    private static string strNameFromNameSpec(NameSpec ns, list<Contact> listCon) {
        string strName = '';
        integer cCon = listCon.size();          
        integer i = 0;
        boolean isNoLastnames = (ns.strFullNameSpec.indexOf(chLToken + 'LastName' + chRToken) == -1);
        
        if (cCon == 0) {
        	if (ns.strHNSField == 'Household_Name_Format__c')
        	   strName = label.npo02.HouseholdAnonymousName;
            else 
        	   strName = label.npo02.HouseholdAnonymousGreeting;
        }
        
        while (i < cCon && i < ns.cMaxUseOverrun) {
            
            string strLname = listCon[i].LastName;
            string strFname = strConFspec(listCon[i], ns.strFirstNameSpec);
            
            // get the set of additional fname's for this lname
            list<String> listFname = new list<String>();
            integer j = i + 1;
            while (j < cCon && (isNoLastnames || listCon[j].LastName == strLname)) {
                String strF = strConFspec(listCon[j], ns.strFirstNameSpec);
                if (strF != '') {
                    listFName.add(strF);
                }
                j++;
            }

            // now put those additional fname's together
            for (integer k = 0; k < listFName.size(); k++) {
                // if we've hit the limit, then bail out
                if (i+k+1 == ns.cMaxUseOverrun) {
                    // we need j to hold maxOverrun
                    j = ns.cMaxUseOverrun;
                    break;
                }

                // we want to use And if this is the last person in this lastname, or the last one we will include
                if (k == listFName.size()-1 || (!isNoLastnames && (i+k+2) == ns.cMaxUseOverrun)) {
                    strFname += ns.strAnd + listFName[k];
                } else {
                    strFName += ', ' + listFname[k];
                }
            }
            // create lname part using first contact with that lastname
            string strCon = strConFSpec(listCon[i], ns.strFullNameSpec);
            strCon = strCon.replace('FirstNameSpec', strFname);
            if (strFname == '')
                strCon = strCon.trim();
            
            // add it to the whole name
            boolean willAddOverrun = (j >= ns.cMaxUseOverrun && cCon > ns.cMaxUseOverrun && ns.strFirstNameSpec != '' && ns.strOverrun != null);
             
            // use and for last set as long as we won't be adding overrun too
            if (i > 0 && j == cCon && !willAddOverrun) {
                strName += ns.strAnd;                
            } else if (i > 0) {
                strName += ', ';
            }
            strName += strCon;

            if (willAddOverrun) {
                strName += ns.strAnd + ns.strOverrun;
            }

            // continue with the next person we haven't included
            i = j;
        }
        
        return ns.strPrefix + strName + ns.strSuffix;
    }
    
    /*******************************************************************************************************
    * @description Given a single contact and a namespec string, returns the formatted string filling
    * in fields in the namespec from the contact.
    * @param con The Contact
    * @param strFspec The namespec string
    * return string
    */
    private static string strConFspec(Contact con, string strFspec) {
        string str = strFspec;
        
        // First, instantiate a new Pattern object looking for {...} without any nested {'s.
        Pattern MyPattern = Pattern.compile('\\{![^\\{!]*\\}');       
        // Then instantiate a new Matcher object 
        Matcher MyMatcher = MyPattern.matcher(strFspec);
        
        while (MyMatcher.find()) {
            // get the fieldname without the {}'s
            string strField = strFspec.substring(MyMatcher.start() + chLToken.length(), MyMatcher.end()-chRToken.length());
            
            //separate cross object references, i.e. account.name
            list<string> splitField = (strField.split('\\.',0));
            
            //remove the field name itself to only include parent object references
            string fieldName = splitField[splitField.size()-1].trim();
            splitField.remove(splitField.size()-1);

            //use the correct sObject
            sObject thisObj;
            if (!splitField.isEmpty()) {
                thisObj = con.getsObject(splitField[0]);
                splitField.remove(0);
            } else {
                thisObj = con;
            }

            //traverse parent relationships until the last one
            for (string parentObj : splitField) {
                if (thisObj != null) {
                    thisObj = thisObj.getsObject(parentObj);
                }
            }

            string val;
            if (thisObj != null) {
                val = string.valueOf(thisObj.get(fieldName));
            }
            
            // add back the {}'s for string substitution
            strField = chLToken + strField + chRToken;
            if (val == null) {   
                // eat potential trailing space
                str = str.replace(strField + ' ', '');
                // eat potential leading space
                str = str.replace(' ' + strField, '');
            }
            str = str.replace(strField, val == null ? '' : val);
        }
        //system.debug('**** strConFspec con: ' + con + ' strFspec: ' + strFspec + ' returns: ' + str);
        return str;     
    }

    /*******************************************************************************************************
    * @description Given a namespec string, returns a set of contact fieldnames
    * specified in that string.
    * @param strFspec The namespec string
    * return set<string>
    */
    private static set<string> setStrFieldFromStrFSpec(string strFSpec) {
        set<string> setStrField = new set<string>();
                
        // First, instantiate a new Pattern object looking for {...} without any nested {'s.
        Pattern MyPattern = Pattern.compile('\\{![^\\{!]*\\}');       
        // Then instantiate a new Matcher object 
        Matcher MyMatcher = MyPattern.matcher(strFSpec);
        
        while (MyMatcher.find()) {
            // get the fieldname without the {}'s
            string strField = strFSpec.substring(MyMatcher.start() + chLToken.length(), MyMatcher.end()-chRToken.length());
            setStrField.add(strField.trim());
        }
        return setStrField;     
    }

    /*******************************************************************************************************
    * @description Given a namespec string, returns the firstnamespec which is demarcated by an outer {! }
    * specified in that string.
    * @param strNameSpec The namespec string, ie. {!lastname} {! {!firstname} foo {!bar}} Family
    * return string The firstnamespec string, ie. {! {!firstname} foo {!bar}}
    */
    private static string strFNameSpecFromStrNameSpec(string strNameSpec) {

        // First, instantiate a new Pattern object looking for {! {! xxxx } }
        Pattern MyPattern = Pattern.compile('\\{![^\\}]*\\{!.*\\}[^\\{!]*\\}');       
        // Then instantiate a new Matcher object 
        Matcher MyMatcher = MyPattern.matcher(strNameSpec);
        
        if (MyMatcher.find()) {
            return strNameSpec.substring(MyMatcher.start(), MyMatcher.end());
        } else {
            return '';
        }     
    }
}