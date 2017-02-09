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
* @date 2011 (2.3)
* @group Households
* @description Class provides a batchable wrapper for Household naming functionality. Called on initial activation of
* automatic household naming, or to refresh all household naming (when the user changes their custom format).
* works on both Household Objects and Household Accounts.
*/
public class HH_HouseholdNaming_BATCH implements Database.Batchable<SObject> {
// batch apex class to name households in existing orgs
    
    /*******************************************************************************************************
    * @description soql query for computing which Contacts to run the batch on.
    */ 
    public String query {get; set;}

    /*******************************************************************************************************
    * @description specifies whether automatic Household Naming is being turned on for the first time.
    */ 
    public boolean isActivation {get; set;} 
    
    /*******************************************************************************************************
    * @description constructor for the class. the household batch expects a list of Contact's with just Id, 
    * LastName, HHId__c available.
    * @param q the soql query of Contacts
    * @param isA whether automatic Household Naming is being turned on for the first time.
    */
    public HH_HouseholdNaming_BATCH(string q, boolean isA) {          
        isActivation = isA;     
        query = q; 
    }
    
    /*******************************************************************************************************
    * @description default old constructor to skip activation sequence. the household batch expects a list of Contact's with just Id, 
    * LastName, HHId__c available.
    * @param q the soql query of Contacts
    */
    public HH_HouseholdNaming_BATCH(string q) {
        query = q;
        isActivation = false;       
    }
        
    /*******************************************************************************************************
    * @description start Method for the Database.Batchable interface
    * @param bc the BatchableContext
    * @return database.Querylocator  
    ********************************************************************************************************/
    public database.Querylocator start(Database.BatchableContext bc) {
        return Database.getQueryLocator(query);      
    }
    
    /*******************************************************************************************************
    * @description execute Method for the Database.Batchable interface. Includes seperate paths for initial activations.
    * @param bc the BatchableContext
    * @param result list of Contact's with just Id, LastName, HHId__c available.
    * @return void  
    ********************************************************************************************************/
    public void execute(Database.BatchableContext bc, Sobject[] result) {
        HH_ProcessControl.inFutureContext = true;     
        
        //first, if this is coming from the VF settings interface, and the user just enabled
        //automatic household naming for the first time, we need to markup
        //all of the households that have existing names.        
        if (isActivation) {
        	
	        set<ID> setHHId = new set<ID>();
	        for (Contact con : (list<Contact>)result) {
	            setHHId.add(con.HHId__c);
	        }
	        list<SObject> listHHObj = [select Id, Name, npo02__SYSTEM_CUSTOM_NAMING__c, npo02__Formal_Greeting__c, npo02__Informal_Greeting__c 
	           from npo02__Household__c where id IN : setHHId];
	        list<SObject> listHHAcct = [select Id, Name, npo02__SYSTEM_CUSTOM_NAMING__c, npo02__Formal_Greeting__c, npo02__Informal_Greeting__c 
	           from Account where id IN : setHHId];
	
	        // handle two types of households by using an SObject    
	        list<SObject> listHH = new list<SObject>();
	        listHH.addAll(listHHObj);
	        listHH.addAll(listHHAcct);     
           
            //sort our contacts objects into a map of 
            //household id and list of contacts
            map<id, list<Contact>> hhIDContactMap = new map<id, list<Contact>>();
            //sort contacts by household
            for (sobject s : result) {               
                Contact c = (Contact)s; 
                if (!hhIDContactMap.containskey(c.HHId__c))
                    hhidContactMap.put(c.HHId__c, new list<Contact>{c});
                else{
                    list<Contact> clist = hhidContactMap.get(c.HHId__c);
                    clist.add(c);
                }
            }
        
            list<SObject> updatelist = new list<SObject>();
        
            for (SObject h : listHH) {
                boolean needsupdate = false;                
                string strSystemCustomNaming = string.valueOf(h.get('npo02__SYSTEM_CUSTOM_NAMING__c'));
                
                if (strSystemCustomNaming != null)
                    strSystemCustomNaming += ';';                                         
                else
                    strSystemCustomNaming = '';                                      
                
                if (h.get('npo02__Formal_Greeting__c') != null && !strSystemCustomNaming.contains('Formal_Greeting__c')) {
                    strSystemCustomNaming += 'Formal_Greeting__c' + ';';
                    needsupdate = true;                     
                }
                if (h.get('npo02__Informal_Greeting__c') != null && !strSystemCustomNaming.contains('Informal_Greeting__c')) {
                    strSystemCustomNaming += 'Informal_Greeting__c' + ';';
                    needsupdate = true;                 
                }    

                //pile up a list of all the lastnames in the household
                set<string> hhlastnames = new set<string>();
                ID HHId = string.valueOf(h.get('Id'));
                for (Contact c : hhidcontactmap.get(HHId))
                    hhlastnames.add(c.LastName);    
                
                boolean hasnamematch = false;
                for (string s : hhlastnames) {
                    if (h.get('Name') == s + ' ' + system.label.npo02.DefaultHouseholdName)
                       hasnamematch = true;                 
                }
                
                //if we never found a matching default name its been customized, so
                //add them to the update list and mark the custom naming field accordingly
                if (!hasnamematch && !strSystemCustomNaming.contains('Name')) {
                    strSystemCustomNaming += 'Name' + ';';
                    needsupdate = true;                 
                }
                
                if (needsupdate) {
                    h.put('npo02__SYSTEM_CUSTOM_NAMING__c', strSystemCustomNaming);
                    updatelist.add(h);
                }                
            }
        
            if (updatelist.size() > 0)
                //Database.Saveresult[] sr = database.update(updatelist, false);
                // all or nothing so errors not lost.
                update updatelist;
        }
        
        //No error handling necessary, because the only place this is called from is the widget that displays the batch
        //process status in the UI.

        //now run the regular process for completing the batch run                
        HH_HouseholdNaming hn = new HH_HouseholdNaming();
        set<id> hhids = new set<id>();
        for (sobject s : result) {
            Contact c = (Contact)s;        
            hhids.add(c.HHId__c);
        }
        list<id> hhidlist = new list<id>();
        hhidlist.addall(hhids);
        
        hn.UpdateNames(hhidlist);
    }
    
    /*******************************************************************************************************
    * @description finish Method for the Database.Batchable interface
    * @param bc the BatchableContext
    * @return void  
    ********************************************************************************************************/
    public void finish(Database.BatchableContext bc) {
    }
    
}