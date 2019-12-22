/*
    Copyright (c) 2016 Salesforce.org
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the Salesforce.com Foundation nor the names of
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
 * @group CampaignTools
 * @description A utility class that checks the CRUD and FLS required for all SOQL
 * and DML operations. The checks are done immediately and will throw an error when
 * any permission is missing that is required to fully utilize Campaign Tools.
 */
public with sharing class CampaignTools_UTIL {

    /** @description our exception object for Field Level & Object Security errors. */
    public class PermsException extends Exception {}
    /** @description our exceptions when a bogus object or field is provided. */
    public class SchemaDescribeException extends Exception {}
    /** @description String helper property for getNamespace() method. */
    private static string plainNamespace;
    /** @description Map to hold global describe */
    private static Map<String, Schema.SObjectType> gd;
    /** @description Map to hold object describes */
    private static Map<String, Schema.DescribeSObjectResult> objectDescribes = new Map<String, Schema.DescribeSObjectResult>();
    /** @description Map to hold field token describes */
    private static Map<String, Map<String, Schema.SObjectField>> fieldTokens = new Map<String,Map<String, Schema.SObjectField>>();
    /** @description Map to hold field describes */
    private static Map<String, Map<String, Schema.DescribeFieldResult>> fieldDescribes = new Map<String,Map<String, Schema.DescribeFieldResult>>();
    /** @description Set of segment fields that require update access for editing campaign lists and batch processing */
    private static Set<String> segmentUpdateableFields = new Set<String> {
        'Parent_Segment__c',
        'Operation__c',
        'Source_ID__c',
        'Exclude_Source__c',
        'Report_Column_Name__c',
        'Root_Segment__c',
        'StatusIds__c'
    };
    /** @description Set of campaign fields that require update access for editing campaign lists */
    private static Set<String> campListUpdateFields = new Set<String> {
        'Campaign_List__c',
        'Campaign_List_Last_Updated__c'
    };
    /** @description Set of campaign fields that require update access for batch processing */
    private static Set<String> campBatchUpdateFields = new Set<String> {
        'Campaign_List_Mutex__c',
        'Campaign_List_Update_Status__c',
        'Campaign_List__c'
    };
    /** @description Set of campaign fields that require update access for batch processing */
    private static Set<String> campBatchReadFields = new Set<String> {
        'Campaign_List_Refresh_Automatically__c'
    };
    /** @description Set of campaign member fields that require create access for batch processing */
    private static Set<String> campMemberBatchCreateFields = new Set<String> {
        'CampaignId',
        'ContactId',
        'LeadId'
    };
    /** @description Set of campaign member fields that require update access for batch processing */
    private static Set<String> campMemberBatchUpdateFields = new Set<String> {
        'Source_Names__c'
    };
    /** @description Set of member fields that require update access for batch processing */
    private static Set<String> memberBatchUpdateFields = new Set<String> {
        'Contact__c',
        'Lead__c',
        'Root_Segment__c',
        'Sources__c',
        'Source_Names__c'
    };

    @TestVisible
    private static Boolean throwException = false;

    /*******************************************************************************************************
    * @description Static method to check permissions required for editing the campaign list
    * when permissions are not available an error will be thrown
    ********************************************************************************************************/
    public static void checkCampaignListPermission() {
        checkUpdateAccess('Campaign', campListUpdateFields);

        checkObjectCreateAccess('Segment__c');
        checkObjectDeleteAccess('Segment__c');
        checkUpdateAccess('Segment__c', segmentUpdateableFields);
    }

    /*******************************************************************************************************
    * @description Static method to check permissions required for processing the campaign refresh
    * when permissions are not available an error will be thrown
    ********************************************************************************************************/
    public static void checkCampaignBatchPermission() {
        //Requires same level of permissions as campaign list editing plus additional requirements
        checkCampaignListPermission();
        checkUpdateAccess('Campaign', campBatchUpdateFields);
        checkReadAccess('Campaign', campBatchReadFields);

        checkObjectDeleteAccess('CampaignMember');
        checkUpdateAccess('CampaignMember', campmemberBatchUpdateFields);
        checkCreateAccess('CampaignMember', campMemberBatchCreateFields);

        checkObjectCreateAccess('Member__c');
        checkObjectDeleteAccess('Member__c');
        checkUpdateAccess('Member__c', memberBatchUpdateFields);
    }

    /*******************************************************************************************************
    * @description Finds the namespace for the current context.
    * @return String The current namespace as a string, or a blank string if we're not in a namespaced context.
    ********************************************************************************************************/
    @TestVisible
    private static String getNamespace() {       
        if (null == plainNamespace) {
            String currentClassName = CampaignTools_UTIL.class.getName();
            plainNamespace = currentClassName.contains('.') ? currentClassName.substringBefore('.') : '';
        }
        return plainNamespace;   
    }

    /*******************************************************************************************************
    * @description Fills the describe maps for the object
    * @param objectName the name of the object to describe
    ********************************************************************************************************/
    private static void fillMapsForObject(String objectName) {
        // get the object map the first time
        if (null == gd) gd = Schema.getGlobalDescribe();

        // get the object description
        if (gd.containsKey(objectName)) {

            if (!objectDescribes.containsKey(objectName))
                objectDescribes.put(objectName, gd.get(objectName).getDescribe());
        } else {
            throw new SchemaDescribeException('Invalid object name \'' + objectName + '\'');
        }
    }

    /*******************************************************************************************************
    * @description Fills the describe maps for the object's fields
    * @param objectName the name of the object to describe
    * @param fieldName the name of the field to describe
    ********************************************************************************************************/
    private static void fillFieldMapsForObject(String objectName, String fieldName) {
        // get the object map the first time
        fillMapsForObject(objectName);

        // get the field description
        if (!fieldTokens.containsKey(objectName)) {
            fieldTokens.put(objectName, objectDescribes.get(objectName).fields.getMap());
            fieldDescribes.put(objectName, new Map<String, Schema.DescribeFieldResult>());
        }
        if (!fieldDescribes.get(objectName).containsKey(fieldName)) {
            if (fieldTokens.get(objectName).containsKey(fieldName)) {
                Schema.DescribeFieldResult dfr = fieldTokens.get(objectName).get(fieldName).getDescribe();
                fieldDescribes.get(objectName).put(fieldName, dfr);
            } else {
                throw new SchemaDescribeException('Invalid field name \'' + fieldName + '\'');
            }
        }
    }

    /*******************************************************************************************************
    * @description Returns object describe data.
    * @param objectName the name of the object to lookup
    * @return Schema.DescribeSObjectResult of the object
    ********************************************************************************************************/
    private static Schema.DescribeSObjectResult getObjectDescribe(String objectName) {
        // make sure we have this object's schema mapped
        if (!objectDescribes.containsKey(objectName))
            fillMapsForObject(objectName);

        return objectDescribes.get(objectName);
    }

    /*******************************************************************************************************
    * @description Returns field describe data
    * @param objectName the name of the object to look up
    * @param fieldName the name of the field to look up
    * @return Schema.DescribeFieldResult the describe field result for the given field
    ********************************************************************************************************/
    private static Schema.DescribeFieldResult getFieldDescribe(String objectName, String fieldName) {
        // make sure we have this field's schema mapped
        if (!fieldDescribes.containsKey(objectName) || !fieldDescribes.get(objectName).containsKey(fieldName))
            fillFieldMapsForObject(objectName, fieldName);

        Schema.DescribeFieldResult dfr = fieldDescribes.get(objectName).get(fieldName);
        return dfr;
    }

    /*******************************************************************************************************
    * @description Static method adds the current namespace to a string if we're in a managed context, 
    * with double underscore notation. If we're not in a managed context, returns the string itself.
    * @param str Token name.
    * @return String with namespace prefix and double underscore if in a managed package.
    ********************************************************************************************************/
    public static String StrTokenNSPrefix(String str) {
        if (getNamespace() == '') return str;
        return getNamespace() + '__' + str;
    }

    /*******************************************************************************************************
    * @description Static method checks if running user field read access for a set of fields
    * @param objectName the name of the object the field belongs to
    * @param fieldNames the set of field names to check read access
    ********************************************************************************************************/
    public static void checkReadAccess(String objectName, Set<String> fieldNames) {
        for (String fieldName : fieldNames) {
        	checkFieldReadAccess(objectName, fieldName);
        }
    }

    /*******************************************************************************************************
    * @description Static method checks if running user has object read access and throws error
    * if the object is not accessible
    * @param objectName the name of the object to check read access
    ********************************************************************************************************/
    public static void checkObjectReadAccess(String objectName) {
        objectName = objectName.contains('__c') ? StrTokenNSPrefix(objectName) : objectName;
        if (!getObjectDescribe(objectName).isAccessible() || throwException) {
        	throw new PermsException(String.format(
                Label.PermissionReadException, 
                new String[]{objectName}
            ));
        }
    }

    /*******************************************************************************************************
    * @description Static method checks if running user has field read access and throws error
    * if the field is not accessible
    * @param objectName the name of the object the field belongs to
    * @param fieldNames the set of field names to check read access
    ********************************************************************************************************/
    public static void checkFieldReadAccess(String objectName, String fieldName) {
        fieldName = fieldName.contains('__c') ? StrTokenNSPrefix(fieldName) : fieldName;
        objectName = objectName.contains('__c') ? StrTokenNSPrefix(objectName) : objectName;
        if (!getFieldDescribe(objectName, fieldName).isAccessible() || throwException) {
        	throw new PermsException(String.format(
                Label.PermissionReadException, 
                new String[]{objectName + '.' + fieldName}
            ));
        }
    }

    /*******************************************************************************************************
    * @description Static method checks if running user has field create access for a set of fields
    * @param objectName the name of the object the field belongs to
    * @param fieldNames the set of field names to check create access
    ********************************************************************************************************/
    public static void checkCreateAccess(String objectName, Set<String> fieldNames) {
        for (String fieldName : fieldNames) {
            checkFieldCreateAccess(objectName, fieldName);
        }
    }

    /*******************************************************************************************************
    * @description Static method checks if running user has object create access and throws error
    * if the object is not createable
    * @param objectName the name of the object to check create access
    ********************************************************************************************************/
    public static void checkObjectCreateAccess(String objectName) {
        objectName = objectName.contains('__c') ? StrTokenNSPrefix(objectName) : objectName;
        if (!getObjectDescribe(objectName).isCreateable() || throwException) {
        	throw new PermsException(String.format(
                Label.PermissionCreateException, 
                new String[]{objectName}
        	));
        }
    }

    /*******************************************************************************************************
    * @description Static method checks if running user has field create access and throws error
    * if the field is not creatable
    * @param objectName the name of the object the field belongs to
    * @param fieldNames the set of field names to check create access
    ********************************************************************************************************/
    public static void checkFieldCreateAccess(String objectName, String fieldName) {
        fieldName = fieldName.contains('__c') ? StrTokenNSPrefix(fieldName) : fieldName;
        objectName = objectName.contains('__c') ? StrTokenNSPrefix(objectName) : objectName;
        if (!getFieldDescribe(objectName, fieldName).isCreateable() || throwException) {
            throw new PermsException(String.format(
                Label.PermissionUpdateException, 
                new String[]{objectName + '.' + fieldName}
            ));
        }
    }

    /*******************************************************************************************************
    * @description Static method checks if running user has field update access for a set of fields
    * @param objectName the name of the object the field belongs to
    * @param fieldNames the set of field names to check update access
    ********************************************************************************************************/
    public static void checkUpdateAccess(String objectName, Set<String> fieldNames) {
        for (String fieldName : fieldNames) {
        	checkFieldUpdateAccess(objectName, fieldName);
        }
    }


    /*******************************************************************************************************
    * @description Static method checks if running user has field update access and throws error
    * if the field is not updateable
    * @param objectName the name of the object the field belongs to
    * @param fieldNames the set of field names to check update access
    ********************************************************************************************************/
    public static void checkFieldUpdateAccess(String objectName, String fieldName) {
        fieldName = fieldName.contains('__c') ? StrTokenNSPrefix(fieldName) : fieldName;
        objectName = objectName.contains('__c') ? StrTokenNSPrefix(objectName) : objectName;
        if (!getFieldDescribe(objectName, fieldName).isUpdateable() || throwException) {
        	throw new PermsException(String.format(
                Label.PermissionUpdateException, 
                new String[]{objectName + '.' + fieldName}
            ));
        }
    }

    /*******************************************************************************************************
    * @description Static method checks if running user has object delete access and throws error
    * if the object is not deleteable
    * @param fieldNames the set of field names to check delete access
    ********************************************************************************************************/
    public static void checkObjectDeleteAccess(String objectName) {
        objectName = objectName.contains('__c') ? StrTokenNSPrefix(objectName) : objectName;
        if (!getObjectDescribe(objectName).isDeletable() || throwException) {
        	throw new PermsException(String.format(
                Label.PermissionDeleteException, 
                new String[]{objectName}
        	));
        }
    }
}