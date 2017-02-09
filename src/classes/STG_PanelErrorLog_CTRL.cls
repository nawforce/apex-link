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
* @group Settings
* @description The controller for the Error Log panel 
*/
public with sharing class STG_PanelErrorLog_CTRL extends STG_Panel implements UTIL_iSoqlListViewConsumer  { 
	
    /*********************************************************************************************************
    * @description Holds the SoqlListView control used in the panel  
    */
    private UTIL_SoqlListView_CTRL ctrlSoqlListView;
	
    /*********************************************************************************************************
    * @description Sets the SoqlListView controller for the containing class
    * @param compController The SoqlListView controller
    * @return void  
    */
    public void setComponentController(UTIL_SoqlListView_CTRL compController){
    	if (ctrlSoqlListView == null) {
	        ctrlSoqlListView = compController;
	        ctrlSoqlListView.sortItemField = 'Datetime__c';
	        ctrlSoqlListView.SortItemAsc = false;
	        ctrlSoqlListView.showCheckboxes = false;
    	}
    }

    /*********************************************************************************************************
    * @description Returns the Soql string the SoqlListView should use to query records.  
    * @return string
    */
    public string getStrSoql() {    
        string strSoql = 'select Id, Context_Type__c, Datetime__c, Error_Type__c, Full_Message__c, ' +
                    'Object_Type__c, Record_URL__c, Stack_Trace__c, CreatedBy.Name from Error__c ';
        if (ctrlSoqlListView.strAlphaFilter != null && ctrlSoqlListView.sortItemField != 'Datetime__c' && ctrlSoqlListView.strAlphaFilter != '%') {
            strSoql += ' Where ' + ctrlSoqlListView.sortItemField + ' like \'' + ctrlSoqlListView.strAlphaFilter + '%\'';
        } 
        // SoqlListView adds the order by clause.
                                      
        return strSoql;         
    }


    /*********************************************************************************************************
    * @description Returns the Fields to display in the SoqlListView  
    * @return list<string>
    */
    public list<string> getstrFields()
        { 
    	   return new list<string> { 'Datetime__c', 'Error_Type__c', 'Full_Message__c', 
                    'Object_Type__c', 'Record_URL__c', 'Stack_Trace__c' };  // can't include  ', CreatedBy.Name'  unfortunately!
        } 
    
    /*********************************************************************************************************
    * @description Returns the primary field to initially sort by in the SoqlListView  
    */
    public string getstrNameField(){ return 'Datetime__c';  }
    
    /*********************************************************************************************************
    * @description Returns the name of the object the SoqlListView is binding to  
    */
    public string getstrObjectType() { return 'Error__c';  }

    /*********************************************************************************************************
    * @description Action Method for creating a new item (not used in this panel)
    * @return null  
    */
    public PageReference NewItem(){ return null;  }
    
    /*********************************************************************************************************
    * @description Action Method called when the Edit link for an item is chosen (not used in this panel)  
    * @return URL to the item
    */
    public PageReference EditItem(ID idItem) {
        return new PageReference('/' + idItem + '/e?retURL=' + ApexPages.currentPage().getURL());
    }   
	
    /*********************************************************************************************************
    * @description Returns this class, who is the consumer of the SoqlListView
    * @return UTIL_iSoqlListViewConsumer  
    */
	public UTIL_iSoqlListViewConsumer getslvConsumer() { return this; }
    
    /*********************************************************************************************************
    * @description Returns the string Id of the Error Log panel. 
    */
    public override string idPanel() { return 'idPanelErrorLog'; }

    /*********************************************************************************************************
    * @description Action Method to delete all errors in the error log
    * @return null 
    */
    public PageReference clearErrorLog() {
    	delete [select Id from Error__c LIMIT 10000];
    	//listError = null;
    	ctrlSoqlListView.setCon = null;
    	return null;
    }
  
}