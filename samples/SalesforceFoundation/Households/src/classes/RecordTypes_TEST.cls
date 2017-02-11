/**
* @author Evan Callahan
* @date 2010 
* @description Provides recordtype wrapper for easy access in other codeblocks 
*/

////cumulusci-managed@deprecated
@isTest
private class RecordTypes_TEST {

    static testmethod void test() {
    	RecordTypes.GetRecordTypeId(null, null);
    	RecordTypes.getRecordTypeNameForGiftsTests(null);
    	RecordTypes.getRecordTypeNameForMembershipTests(null);
    	RecordTypes.GetRecordTypeName(null, null);
    	RecordTypes.GetRecordTypeIdSet(null, null);
    	RecordTypes.GetRecordTypeIdMap(null);
    	RecordTypes.GetOppNamingSuffix(null);
    	RecordTypes.getRecordTypesForSelectList(null);
    	RecordTypes.getStringRecordTypesForSelectList(null);
    }
}