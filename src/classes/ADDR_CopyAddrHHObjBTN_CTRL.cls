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
* @group Address Management
* @description The controller for the VF page invoked by using the address copy button. 
*   This is a simple utility for transferring a contact address to a household    
*   or pushing a household address out to its member contacts 
*   NOTE: This is only supported for Household__c objects.
*   NOTE: For Household Accounts, the new Address__c object and its associated features provide this functionality. 
*/
public with sharing class ADDR_CopyAddrHHObjBTN_CTRL {
    
    /*******************************************************************************************************
    * @description the source object to copy address fields from
    */
    private sobject obj;

    /*******************************************************************************************************
    * @description the result message (success or errors)
    */
    public string resulttext{get; set;}

    /*******************************************************************************************************
    * @description the URL to return to after running.
    */
    public string returnurl{get; set;}  

    /*******************************************************************************************************
    * @description the type of source object (Contact, npo02__Household__c)
    */
    public string strSrcObject { get; set; }
    
    /*******************************************************************************************************
    * @description whether to redirect on success, or stay on the page for errors.
    */
    public boolean redirectPage { get; set; }
    
    /*******************************************************************************************************
    * @description Constructor for the class
    */
    public ADDR_CopyAddrHHObjBTN_CTRL(){
            
        obj = null;
        strSrcObject = null;
        returnurl = null;
        resulttext = null;
        redirectPage = true;
        String id = ApexPages.currentPage().getParameters().get('id');
        if (id != null && id != '') {        	
        	if (UTIL_Describe.isObjectIdThisType(id, 'Contact')) {
        		strSrcObject = 'Contact';
                obj = [select id from Contact where id = :id];
        	} else if (UTIL_Describe.isObjectIdThisType(id, 'npo02__Household__c')) {
        		strSrcObject = 'npo02__Household__c';
                obj = [select id from npo02__Household__c where id = :id];
        	}        	
            if (obj != null) {            	
                PageReference pageRef = new PageReference('/' + obj.id);
                returnurl = pageref.getUrl();
            }
        }
    }
    
    /*******************************************************************************************************
    * @description Action method called when the user clicks on the copy button.
    * @return PageReference - null
    */
    public PageReference buttonClick(){
        
        if (strSrcObject == 'Contact'){
            Contact c = (Contact)obj;
            npo02__Household__c h;
            
            //there must be a more efficient way to do this,
            //but for now, this will work
            c = [select Id, npo02__Household__c, npo02__Household__r.npo02__MailingStreet__c, npo02__Household__r.npo02__MailingCity__c, 
                npo02__Household__r.npo02__MailingState__c, npo02__Household__r.npo02__MailingPostalCode__c, 
                npo02__Household__r.npo02__MailingCountry__c,             
                MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry From Contact where id = :c.id]; 
            
            if (c.npo02__Household__c != null){
                
                //get a reference to the HH object for updating
                h = new npo02__Household__c(id = c.npo02__Household__r.id);
                
                Note n;
                
                //if there's a street already listed on the household
                //save the old address as a note on the household object                
                if (c.npo02__Household__r.npo02__MailingStreet__c != null){
                    string notebody = '';
                    notebody += c.npo02__Household__r.npo02__MailingStreet__c + '  ';
                    notebody += c.npo02__Household__r.npo02__MailingCity__c + '  '; 
                    notebody += c.npo02__Household__r.npo02__MailingState__c + '  ';
                    notebody += c.npo02__Household__r.npo02__MailingPostalCode__c + '  ';
                    notebody += c.npo02__Household__r.npo02__MailingCountry__c;
                    string strTitle = 'ADDRESS UPDATE FROM CONTACT: ' + system.today() + ' BY: ' + UserInfo.getName();
                    strTitle = strTitle.left(80);
                    n = new Note(Title = strTitle, ParentId = h.id, Body = notebody);
                }
                
                h.npo02__MailingStreet__c = c.MailingStreet;
                h.npo02__MailingState__c = c.MailingState;
                h.npo02__MailingPostalCode__c = c.MailingPostalCode;
                h.npo02__MailingCity__c = c.MailingCity;
                h.npo02__MailingCountry__c = c.MailingCountry; 
            
                Database.Saveresult sr = Database.update(h, false);
                
                if (sr.isSuccess()){
                    resulttext = system.label.npo02.AddressCopySuccess;
                    if (n != null) insert n;
                    redirectPage = true;                    
                }
                else{
                    Database.Error[] ers= sr.getErrors();
                    resulttext = system.label.npo02.AddressCopyFailure + ': ' + ers[0].getStatusCode();
                    redirectPage = false;
                    showMsg(resulttext, ApexPages.Severity.ERROR);
                }            
            }
            else{
                resulttext = system.label.npo02.AddressCopyFailure + ': ' + system.label.addrCopyConAddBtnHHObjOnly;
                redirectPage = false;
                showMsg(resulttext, ApexPages.Severity.ERROR);
            }
        }
        
        //its coming from a household record, so push address to other members
        else if (strSrcObject ==  'npo02__Household__c'){
            
            npo02__Household__c house = (npo02__Household__c)obj;
            list<Contact> constoupdate = new list<Contact>();
                        
            //we want these seperate in case a contact update fails - since we don't 
            //want to add a note for those
            map<id, Note> oldaddressnotes = new map<id, Note>();
            list<Note> notestoinsert = new list<Note>();
            house = [select npo02__MailingStreet__c, npo02__MailingState__c, npo02__MailingPostalCode__c, npo02__MailingCity__c, npo02__MailingCountry__c from npo02__Household__c where id = :house.id];
            constoupdate = [select id, MailingStreet, MailingCity, MailingState, MailingPostalCode, MailingCountry from Contact where npo02__Household__c = :house.id];
        
            for(Contact con : constoupdate){
                
                Note n;
                if (con.MailingStreet != null){
                    string notebody = '';
                    notebody += con.MailingStreet + '  ';
                    notebody += con.MailingCity + '  '; 
                    notebody += con.MailingState + '  ';
                    notebody += con.MailingPostalCode + '  ';
                    notebody += con.MailingCountry;
                    string strTitle = 'ADDRESS UPDATE FROM HOUSEHOLD: ' + system.today() + ' BY: ' + UserInfo.getName();
                    strTitle = strTitle.left(80);
                    n = new Note(Title = strTitle, ParentId = con.id, Body = notebody);
                    notestoinsert.add(n);
                }   
                
                con.MailingStreet = house.npo02__MailingStreet__c;
                con.MailingState = house.npo02__MailingState__c;
                con.MailingPostalCode = house.npo02__MailingPostalCode__c;
                con.MailingCity = house.npo02__MailingCity__c;
                con.MailingCountry = house.npo02__MailingCountry__c;
            }
    
            try{
                update constoupdate;   
                insert notestoinsert;
                redirectPage = true;            
            }
            
            catch(Exception e){
                resulttext = system.label.npo02.AddressCopyFailure + ': ' + e.getMessage();
                redirectPage = false;
                showMsg(resulttext, ApexPages.Severity.ERROR);              
            }   
        }
        
        //unknown object
        else{
            resulttext = system.label.npo02.AddressCopyUnknownObject;
            redirectPage = false;
            showMsg(resulttext, ApexPages.Severity.ERROR);
        }
        
        return null;
    }
    
    /*******************************************************************************************************
    * @description adds a Message to the Page
    * @return void
    */
    private void showMsg(String arg, ApexPages.Severity severity){
        ApexPages.Message myMsg = new ApexPages.Message(severity,arg);
        ApexPages.addMessage(myMsg);
    }    

}