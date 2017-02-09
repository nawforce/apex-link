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
* @group-content ../../ApexDocContent/Settings.htm
* @description Controller for the base settings panel class.
*/
public with sharing virtual class STG_Panel {
	
    /*******************************************************************************************************
    * @description Class constructor
    */ 
	public STG_Panel() {
		//UTIL_Debug.debug('***** STG_Panel constructor');
		isEditMode = false;
	}
	
    /*******************************************************************************************************
    * @description the id of the panel, which we assume all derived classes will override.
    */ 
	public virtual string idPanel() { return null; }
	
    /*******************************************************************************************************
    * @description is the panel in Edit Mode
    */ 
	public boolean isEditMode { get; set; }
	
    /*******************************************************************************************************
    * @description is the panel in Read Only Mode
    */ 
	public boolean isReadOnlyMode {
		get {
			return !isEditMode;
		}
	}
    
    /*******************************************************************************************************
    * @description the shared Settings Service object panels should use.
    */ 
    static public STG_SettingsService stgService {
        get {
            if (stgService == null) { 
            	stgService = STG_SettingsService.stgService;
                //UTIL_Debug.debug('***** getting STG_SettingsService.stgService: ' + stgService);
            }
            return stgService;
        }
        private set;
    }

    /*******************************************************************************************************
    * @description Action Method to save all settings changes in the Settings Service.  Derived classes can override, if
    * they need to do any custom saving first.
    * @return void
    */ 
    public virtual PageReference saveSettings() { 
        STG_SettingsManager_CTRL.idPanelCurrent = idPanel();
        Savepoint sp = Database.setSavepoint();
        try {
	        stgService.saveAll();
	        isEditMode = false;
        } catch(Exception e) {
            Database.rollback(sp);
            ERR_Handler.processError(e, ERR_Handler_API.Context.STTG);
            ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.ERROR, e.getMessage()));
        }
        return null;
        
    }

    /*******************************************************************************************************
    * @description Action Method to put the current page into Edit mode.
    * @return null
    */ 
    public virtual PageReference editSettings() { 
        STG_SettingsManager_CTRL.idPanelCurrent = idPanel();
        isEditMode = true;
        return null;
    }

    /*******************************************************************************************************
    * @description Action Method to cancel the current page's Edit mode.
    * @return null
    */ 
    public virtual PageReference cancelEdit() {
        STG_SettingsManager_CTRL.idPanelCurrent = idPanel();
        isEditMode = false;
        return null;
    }
       
    /*******************************************************************************************************
    * @description list of SelectOptions for the Account Model.
    */ 
    static public list<SelectOption> listSOAccountModels {
        get {
            if (listSOAccountModels == null) {
                listSOAccountModels = new list<SelectOption>();
                listSOAccountModels.add(new SelectOption(CAO_Constants.HH_ACCOUNT_PROCESSOR, CAO_Constants.HH_ACCOUNT_PROCESSOR));
                listSOAccountModels.add(new SelectOption(CAO_Constants.ONE_TO_ONE_PROCESSOR, CAO_Constants.ONE_TO_ONE_PROCESSOR));
                listSOAccountModels.add(new SelectOption(CAO_Constants.BUCKET_PROCESSOR, CAO_Constants.BUCKET_PROCESSOR));
            }
            return listSOAccountModels;
        }
        private set;
    }

    /*******************************************************************************************************
    * @description list of SelectOptions for Opportunity Contact Roles
    */ 
    static public list<SelectOption> listSOContactRoles {
        get {    
            if (listSOContactRoles == null) {
                listSOContactRoles = new list<SelectOption>();
                listSOContactRoles.add(new SelectOption('', Label.stgLabelNone));
                Schema.DescribeFieldResult F = Schema.sObjectType.OpportunityContactRole.fields.Role; 
                List<Schema.PicklistEntry> P = F.getPicklistValues();
                for (Schema.PicklistEntry pe : P) {
                    listSOContactRoles.add(new SelectOption(pe.getLabel(), pe.getLabel()));
                }
            }
            return listSOContactRoles;
        }
        private set;
    }

    /*******************************************************************************************************
    * @description list of SelectOptions for Household Object Creation Rules
    */ 
    static public list<SelectOption> listSOHHRules {
        get {
            if (listSOHHRules == null) {
                listSOHHRules = new list<SelectOption>();
                listSOHHRules.add(new SelectOption(HH_Households.ALL_PROCESSOR, HH_Households.ALL_PROCESSOR));
                listSOHHRules.add(new SelectOption(HH_Households.ALL_INDIVIDUALS_PROCESSOR, HH_Households.ALL_INDIVIDUALS_PROCESSOR));
                listSOHHRules.add(new SelectOption(HH_Households.NO_HOUSEHOLDS_PROCESSOR, HH_Households.NO_HOUSEHOLDS_PROCESSOR));
            }
            return listSOHHRules;
        }
        private set;
    }
    
    /*******************************************************************************************************
    * @description list of SelectOptions for Contact Record Types
    */ 
    static public list<SelectOption> listSOContactRecTypes {
        get {
            if (listSOContactRecTypes == null) {
                listSOContactRecTypes = new list<SelectOption>();
                listSOContactRecTypes.add(new SelectOption('', Label.stgLabelNone));
                listSOContactRecTypes.addAll(UTIL_RecordTypes.getStringRecordTypesForSelectList(Contact.sObjectType));            
            }       
            return listSOContactRecTypes;
        }
        private set;
    }    

    /*
    * @description list of SelectOptions for Contact Record Type Ids
    */
    static public List<SelectOption> listSOContactRecTypeIds {
        get {
            if (listSOContactRecTypeIds == null) {
                listSOContactRecTypeIds = new list<SelectOption>();
                listSOContactRecTypeIds.add(new SelectOption('', Label.stgLabelNone));
                listSOContactRecTypeIds.addAll(UTIL_RecordTypes.getRecordTypesForSelectList(Contact.sObjectType));            
            }
            return listSOContactRecTypeIds;
        }
        private set;
    }

    /*******************************************************************************************************
    * @description list of SelectOptions for Acount Record Type Ids
    */ 
    static public list<SelectOption> listSOAccountRecTypeIds {
        get {
            if (listSOAccountRecTypeIds == null) {
                listSOAccountRecTypeIds = new list<SelectOption>();
                listSOAccountRecTypeIds.add(new SelectOption('', Label.stgLabelNone));
                listSOAccountRecTypeIds.addAll(UTIL_RecordTypes.getRecordTypesForSelectList(Account.sObjectType));            
            }       
            return listSOAccountRecTypeIds;
        }
        private set;
    }    

    /*******************************************************************************************************
    * @description list of SelectOptions for Opportunity Record Types
    */ 
    static public list<SelectOption> listSOOppRecTypes {
        get {
            if (listSOOppRecTypes == null) {
                listSOOppRecTypes = new list<SelectOption>();
                listSOOppRecTypes.add(new SelectOption('', Label.stgLabelNone));
                listSOOppRecTypes.addAll(UTIL_RecordTypes.getStringRecordTypesForSelectList(Opportunity.sObjectType));            
            }       
            return listSOOppRecTypes;
        }
        private set;
    }    
        
    /*******************************************************************************************************
    * @description list of SelectOptions for Opportunity Record Type Ids
    */ 
    static public list<SelectOption> listSOOppRecTypesIds {
        get {
            if (listSOOppRecTypesIds == null) {
                listSOOppRecTypesIds = new list<SelectOption>();
                listSOOppRecTypesIds.add(new SelectOption('', Label.stgLabelNone));
                listSOOppRecTypesIds.addAll(UTIL_RecordTypes.getRecordTypesForSelectList(Opportunity.sObjectType));            
            }       
            return listSOOppRecTypesIds;
        }
        private set;
    }    

    /*******************************************************************************************************
    * @description list of SelectOptions for Opportunity Contact Roles
    */ 
    static public list<SelectOption> listSOOppContactRoles {
        get {
            if (listSOOppContactRoles == null) {
                listSOOppContactRoles = new list<SelectOption>();
                listSOOppContactRoles.add(new SelectOption('', Label.stgLabelNone));
                Schema.DescribeFieldResult F = Schema.sObjectType.OpportunityContactRole.fields.Role; 
                List<Schema.PicklistEntry> P = F.getPicklistValues();
                for(Schema.PicklistEntry pe : P) {
                    listSOOppContactRoles.add(new SelectOption(pe.getLabel(), pe.getLabel()));
                }
            }
            return listSOOppContactRoles;
        }
        private set;
    }
    
    /*******************************************************************************************************
    * @description list of SelectOptions for Opportunity Types
    */ 
    static public list<SelectOption> listSOOppTypes {
    	get {
	        if (listSOOppTypes == null) {
	            listSOOppTypes = new list<SelectOption>();
	            listSOOppTypes.add(new SelectOption('', Label.stgLabelNone));
	            Schema.DescribeFieldResult F = Schema.sObjectType.Opportunity.fields.Type; 
	            list<Schema.PicklistEntry> P = F.getPicklistValues();
	        
	            for (Schema.PicklistEntry plistentry: P) {
	                listSOOppTypes.add(new SelectOption(plistentry.getLabel(),plistentry.getLabel()));
	            }
	        }
	        return listSOOppTypes;
    	}
    	private set;
    }

    /*******************************************************************************************************
    * @description list of SelectOptions for Relationships Types
    */ 
     static public list<SelectOption> listSORelTypes {
        get {    
            if (listSORelTypes == null) {
                listSORelTypes = new list<SelectOption>();
                listSORelTypes.add(new SelectOption('', Label.stgLabelNone));
                Schema.DescribeFieldResult F = Schema.sObjectType.npe4__Relationship__c.fields.npe4__Type__c; 
                List<Schema.PicklistEntry> P = F.getPicklistValues();
                for (Schema.PicklistEntry pe : P) {
                    listSORelTypes.add(new SelectOption(pe.getLabel(), pe.getLabel()));
                }
            }
            return listSORelTypes;
        }
        private set;
    }

    /*******************************************************************************************************
    * @description list of SelectOptions for Campaign Types
    */ 
     static public list<SelectOption> listSOCampaignTypes {
        get {    
            if (listSOCampaignTypes == null) {
                listSOCampaignTypes = new list<SelectOption>();
                listSOCampaignTypes.add(new SelectOption('', Label.stgLabelNone));
                Schema.DescribeFieldResult F = Schema.sObjectType.Campaign.fields.Type; 
                List<Schema.PicklistEntry> P = F.getPicklistValues();
                for (Schema.PicklistEntry pe : P) {
                    listSOCampaignTypes.add(new SelectOption(pe.getLabel(), pe.getLabel()));
                }
            }
            return listSOCampaignTypes;
        }
        private set;
    }

    /*******************************************************************************************************
    * @description list of SelectOptions for Mailing Report Ids (mail is in the report name)
    */ 
    static public list<SelectOption> listSOMailingReportIds {
        get {
            if (listSOMailingReportIds == null) {
                listSOMailingReportIds = new list<SelectOption>();
                listSOMailingReportIds.add(new SelectOption('', Label.stgLabelNone));
                for (Report rpt : [select Id, Name from Report where Name like '%mail%']) {
                    listSOMailingReportIds.add(new SelectOption(rpt.Id, rpt.Name));
                }
            }
            return listSOMailingReportIds;
        }
        private set;
    }
       
    /*******************************************************************************************************
    * @description the Household Account Record Type Name
    */ 
    static public string strHHAccountRecordTypeLabel {
    	get {
    		if (strHHAccountRecordTypeLabel == null) {
    			strHHAccountRecordTypeLabel = UTIL_RecordTypes.GetRecordTypeName(Account.sObjectType, stgService.stgCon.npe01__HH_Account_RecordTypeID__c);
    		}
    		return strHHAccountRecordTypeLabel;
    	}
    	private set;
    }
       
    /*******************************************************************************************************
    * @description the One-to-One Account Record Type Name
    */ 
    static public string strOneToOneRecordTypeLabel {
        get {
            if (strOneToOneRecordTypeLabel == null) {
                strOneToOneRecordTypeLabel = UTIL_RecordTypes.GetRecordTypeName(Account.sObjectType, stgService.stgCon.npe01__One_to_One_RecordTypeID__c);
            }
            return strOneToOneRecordTypeLabel;
        }
        private set;
    }     

    /**
     * @description Given an sObjectType and a list of semicolon ';' separated
     * ids corresponding to record types, return a comma ',' separated string
     * that contain the names of those record types.  If the given idValues
     * string is blank, the given blankValue will be returned.
     *
     * @param Schema.sObjectType The object type to which the record types apply
     * @param idValues A semicolon separated string of ids
     * @param blankValue The string returned when the given idValues string is blank
     * @return String
     */
    public static String getRecordTypeNamesFromPicklistString(Schema.sObjectType objectType, String idValues, String blankValue) {
        if (idValues == null) {
            return blankValue;
        }

        Map<Id, Schema.RecordTypeInfo> recordTypes = objectType.getDescribe().getRecordTypeInfosById();
        List<String> recordTypeIdStrings = idValues.split(';', 0);
        List<String> recordTypeNames = new List<String>();

        for (String recordTypeIdString : recordTypeIdStrings) {
            Id recordTypeId;

            if (recordTypeIdString instanceof Id) {
                recordTypeId = (Id) recordTypeIdString;
            } else {
                continue;
            }

            if (recordTypes.containsKey(recordTypeId)) {
                recordTypeNames.add(recordTypes.get(recordTypeId).getName());
            }
        }

        if (recordTypeNames.isEmpty()) {
            return blankValue;
        } else {
            return String.join(recordTypeNames, ', ');
        }
    }

    /**
     * @description Given an sObjectType and a list of semicolon ';' separated
     * ids corresponding to record types, return a comma ',' separated string
     * that contain the names of those record types.
     *
     * @param Schema.sObjectType The object type to which the record types apply
     * @param idValues A semicolon separated string of ids
     * @return String
     */
    public static String getRecordTypeNamesFromPicklistString(Schema.sObjectType objectType, String idValues) {
        return getRecordTypeNamesFromPicklistString(objectType, idValues, Label.stgLabelNone);
    }
}