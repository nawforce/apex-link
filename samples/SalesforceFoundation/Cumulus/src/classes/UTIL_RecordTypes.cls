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
* @date 2016
* @group Utilities
*
* @description Methods for obtaining information about record types
*/
public class UTIL_RecordTypes {
    /**
     * @description Get the ids of the record types for the given record type names
     *
     * @param objectType The type of the object
     * @param recordTypeNames The names of the record types
     * @return Set<Id>
     */
    public static Set<Id> getRecordTypeIdSet(sObjectType objectType, Set<String> recordTypeNames) {
        Map<String, Schema.RecordTypeInfo> recordTypeInfos = objectType.getDescribe().getRecordTypeInfosByName();

        Set<Id> recordTypeIds = new Set<Id>();

        for (String recordTypeName : recordTypeNames) {
            if (recordTypeInfos.containsKey(recordTypeName)) {
                recordTypeIds.add(
                    recordTypeInfos.get(recordTypeName).getRecordTypeId()
                );
            }
        }

        return recordTypeIds;
    }

    /**
     * @description Return the record type info for a record type with the
     * given name for the given object.  This will return an instance
     * conforming to the UTIL_RecordTypes.RecordTypeInfo interface, not an
     * instance of Schema.RecordTypeInfo.  In the case where the record type
     * does not exist, an UnavailableRecordTypeInfo "null object" will be
     * returned.
     *
     * @param objectType The type of the object
     * @param The name of the record type
     * @return UTIL_RecordTypes.RecordTypeInfo
     */
    private static RecordTypeInfo getRecordTypeInfo(sObjectType objectType, String recordTypeName) {
        Map<String, Schema.RecordTypeInfo> recordTypeInfos = objectType.getDescribe().getRecordTypeInfosByName();
        if (recordTypeInfos.containsKey(recordTypeName)) {
            return new RecordTypeInfoWrapper(recordTypeInfos.get(recordTypeName));
        } else {
            return new UnavailableRecordTypeInfo();
        }
    }


    /**
     * @description Get the id of the given record type name for the given
     * object type, or null if the record type is not available or does not
     * exist.
     *
     * @param objectType The type of the object
     * @param recordTypeName The name of the record type
     * @return Id
     */
    public static Id getRecordTypeId(sObjectType objectType, String recordTypeName) {
        return getRecordTypeInfo(objectType, recordTypeName).getRecordTypeId();
    }


    /**
     * @description Get the name of the record type for the given record type
     * id and object type, or null if the record type is not available or does
     * not exist.
     *
     * @param objectType The type of the object
     * @param recordTypeId The id of the record type
     * @return String
     */
    public static String getRecordTypeName(sObjectType objectType, Id recordTypeId) {
        Map<Id, Schema.RecordTypeInfo> rtiById = objectType.getDescribe().getRecordTypeInfosById();
        if (rtiById.containsKey(recordTypeId)) {
            Schema.RecordTypeInfo rti = rtiById.get(recordTypeId);
            return rti.getName();
        } else {
            return null;
        }
    }

    /**
     * @description Is the given record type name active for the current user?
     *
     * @param objectType The type of the object
     * @param recordTypeName The name of the record type
     * @return Boolean
     */
    public static Boolean isRecordTypeActive(sObjectType objectType, String recordTypeName) {
        return getRecordTypeInfo(objectType, recordTypeName).isAvailable();
    }

    /**
     * @description Is the given record type name the default record type for
     * the current user?
     *
     * @param objectType The type of the object
     * @param recordTypeName The name of the record type
     * @return Boolean
     */
    public static Boolean isRecordTypeDefault(sObjectType objectType, String recordTypeName) {
        return getRecordTypeInfo(objectType, recordTypeName).isDefaultRecordTypeMapping();
    }

    /**
     * @description Return a record type info appropriate for using in tests,
     * that belongs to the given object type.  This will return an instance
     * conforming to the UTIL_RecordTypes.RecordTypeInfo interface, not an
     * instance of Schema.RecordTypeInfo.  In the case where the record type
     * does not exist, an UnavailableRecordTypeInfo "null object" will be
     * returned.
     *
     * @param objectType The type of the object
     * @return UTIL_RecordTypes.RecordTypeInfo
     */
    private static RecordTypeInfo getRecordTypeInfoForTests(sObjectType objectType) {
        List<Schema.RecordTypeInfo> recordTypeInfos = objectType.getDescribe().getRecordTypeInfos();
        for (Schema.RecordTypeInfo rti : recordTypeInfos) {
            if (!rti.isMaster() && rti.isAvailable()) {
                return new RecordTypeInfoWrapper(rti);
            }
        }
        return new UnavailableRecordTypeInfo();
    }

    /**
     * @description Return the name of a record type that can be used for
     * tests, if one exists in the org.  Otherwise, return a blank string.
     *
     * @param objectType The type of the object
     * @return String
     */
    public static String getRecordTypeNameForGiftsTests(sObjectType objectType) {
        return getRecordTypeInfoForTests(objectType).getName();
    }

    /**
     * @description Return the id of a record type that can be used for tests,
     * if one exists in the org.  Otherwise, return null.
     *
     * @param objectType The type of the object
     * @return Id
     */
    public static Id getRecordTypeIdForGiftsTests(sObjectType objectType) {
        return getRecordTypeInfoForTests(objectType).getRecordTypeId();
    }

    /**
     * @description Return RecordTypeInfo of a record type that can be used for
     * tests, if one exists in the org.  Otherwise, return null.
     *
     * @param objectType The type of the object
     * @return Schema.RecordTypeInfo
     */
    public static Schema.RecordTypeInfo getRecordTypeInfoForGiftsTests(sObjectType objectType) {
        return getRecordTypeInfoForTests(objectType).getRecordTypeInfo();
    }

    /**
     * @description Return the name of a record type that can be used for
     * tests, if one exists in the org.  Otherwise, return a blank string.
     *
     * @param objectType The type of the object
     * @return String
     */
    public static String getRecordTypeNameForMembershipTests(sObjectType objectType) {
        return getRecordTypeInfoForTests(objectType).getName();
    }

    /**
     * @description Return the id of a record type that can be used for tests,
     * if one exists in the org.  Otherwise, return null.
     *
     * @param objectType The type of the object
     * @return Id
     */
    public static Id getRecordTypeIdForMembershipTests(sObjectType objectType) {
        return getRecordTypeInfoForTests(objectType).getRecordTypeId();
    }

    /**
     * @description Return a list of SelectOptions where the label is the
     * record type name and the value is the record type id of a record type
     * that belongs to the given object type.  The 'master' record type is
     * omitted from this list of options.
     *
     * @param objectType The type of the object
     * @return List<SelectOption>
     */
    public static List<SelectOption> getRecordTypesForSelectList(sObjectType objectType) {
        Map<String, Schema.RecordTypeInfo> recordTypeInfos = objectType.getDescribe().getRecordTypeInfosByName();
        List<SelectOption> options = new List<SelectOption>();
        for (String recordTypeName : recordTypeInfos.keySet()) {
            Schema.RecordTypeInfo rti = recordTypeInfos.get(recordTypeName);
            options.add(new SelectOption(rti.getRecordTypeId(), recordTypeName));
        }
        return options;
    }

    /**
     * @description Return a list of SelectOptions where both the value and the
     * label are the record type name of a record type that belongs to the
     * given object type.  The 'master' record type is omitted from this list
     * of options.
     *
     * @param objectType The type of the object
     * @return List<SelectOption>
     */
    public static List<SelectOption> getStringRecordTypesForSelectList(sObjectType objectType) {
        Map<String, Schema.RecordTypeInfo> recordTypeInfos = objectType.getDescribe().getRecordTypeInfosByName();
        List<SelectOption> options = new List<SelectOption>();
        for (String recordTypeName : recordTypeInfos.keySet()) {
            Schema.RecordTypeInfo rti = recordTypeInfos.get(recordTypeName);
            options.add(new SelectOption(recordTypeName, recordTypeName));
        }
        return options;
    }

    /**
     * @description This interface replicates all of the methods available from
     * the Schema.RecordTypeInfo class, intended to be used as a transparent
     * wrapper around the Schema.RecordTypeInfo class.  Additionally, a method
     * is available to access the wrapped Schema.RecordTypeInfo instance.
     */
    private interface RecordTypeInfo {
        Schema.RecordTypeInfo getRecordTypeInfo();
        String getName();
        Id getRecordTypeId();
        Boolean isAvailable();
        Boolean isDefaultRecordTypeMapping();
        Boolean isMaster();
    }

    /**
     * @description This class replicates all of the methods available from
     * the Schema.RecordTypeInfo class, intended to be used as a transparent
     * wrapper around the Schema.RecordTypeInfo class.  Additionally, a method
     * is available to access the wrapped Schema.RecordTypeInfo instance.
     */
    private class RecordTypeInfoWrapper implements RecordTypeInfo {
        private Schema.RecordTypeInfo rti;
        public RecordTypeInfoWrapper(Schema.RecordTypeInfo rti) {
            this.rti = rti;
        }
        public Schema.RecordTypeInfo getRecordTypeInfo() {
            return this.rti;
        }
        public String getName() {
            return rti.getName();
        }
        public Id getRecordTypeId() {
            return rti.getRecordTypeId();
        }
        public Boolean isAvailable() {
            return rti.isAvailable();
        }
        public Boolean isDefaultRecordTypeMapping() {
            return rti.isDefaultRecordTypeMapping();
        }
        public Boolean isMaster() {
            return rti.isMaster();
        }
    }

    /**
     * @description This class replicates all of the methods available from the
     * Schema.RecordTypeInfo class, except it is intended to be used in places
     * where an actual Schema.RecordTypeInfo instance is not available.  The
     * methods of this class will return the appropriate null, blank string, or
     * negative boolean values.  This class is intended to reduce the need to
     * perform null checks within this utility class, as per the Null Object
     * pattern.
     */
    private class UnavailableRecordTypeInfo implements RecordTypeInfo {
        public Schema.RecordTypeInfo getRecordTypeInfo() {
            return null;
        }
        public String getName() {
            return '';
        }
        public Id getRecordTypeId() {
            return null;
        }
        public Boolean isAvailable() {
            return false;
        }
        public Boolean isDefaultRecordTypeMapping() {
            return false;
        }
        public Boolean isMaster() {
            return false;
        }
    }
}