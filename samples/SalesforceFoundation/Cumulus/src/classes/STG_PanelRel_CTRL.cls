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
* @description The controller for the Relationships panel 
*/
public with sharing class STG_PanelRel_CTRL extends STG_Panel { 

    /*********************************************************************************************************
    * @description Returns the string Id of the Relationships panel. 
    */
    public override string idPanel() { return 'idPanelRel'; }
    
    /*********************************************************************************************************
    * @description The list of SelectOptions for the Gender field 
    */
    public list<SelectOption> listSOGenderFieldOptions {
    	get {
	        if (listSOGenderFieldOptions == null) {
	            listSOGenderFieldOptions = new list<SelectOption>();
	            //add a default option
	            listSOGenderFieldOptions.add(new SelectOption('', Label.stgLabelNone));
	                        
	            map<String, Schema.SObjectField> conFieldTokenList = Schema.SObjectType.Contact.fields.getMap(); 
	           
	            //only get the describe information for custom fields (not from NPSP)
	            for (string fieldName : conFieldTokenList.keyset()) {
	                if (fieldname.endsWith('__c') && !fieldname.startsWith('np')) {
	                    listSOGenderFieldOptions.add(new SelectOption(fieldName, UTIL_Describe.getFieldLabel('Contact', fieldName))); 
	                }               
	            }
	        }               
	        return listSOGenderFieldOptions;
        }
        private set;
    }
    
    /*********************************************************************************************************
    * @description The Gender field's label 
    */
    public string strGenderFieldLabel {
    	get {
    		return getFieldLabel('Contact', STG_Panel.stgService.stgRel.npe4__Gender_Field__c);
    	}
    }  
    
    /*********************************************************************************************************
    * @description Returns a field's label.  cover over describe call to handle fields that no longer exist.
    * @param strObj The object
    * @param strField The field
    * @return string
    */
    private string getFieldLabel(string strObj, string strField) {
        if (UTIL_Describe.isValidField(strObj, strField)) {
            return UTIL_Describe.getFieldLabel(strObj, strField);
        } else {
            return strField;
        }
    }
        
    
}