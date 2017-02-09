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
* @date 2015
* @group Contacts
* @group-content ../../ApexDocContent/Contacts.htm
* @description Test class for Contact delete override.
*/
@isTest
private class CON_DeleteContactOverride_TEST {
    /*********************************************************************************************************
    * @description if you only want to run one test in this class, fill in its name here.
    * if you want to run all tests, then use '*'
    */
    private static string strTestOnly = '*';

    /*******************************************************************************************************
    * @description Deletes a contact not alone in their household, verifies household is not deleted.
    */
    @isTest static void deleteWithOtherMembers() {
        if (strTestOnly != '*' && strTestOnly != 'deleteWithOtherMembers') return;
        
        Contact con = new Contact(LastName='foo');
        insert con;

        list<Contact> queryCon = [SELECT Id, AccountId, Account.npe01__SYSTEMIsIndividual__c FROM Contact WHERE Id = :con.id];
        system.assertNotEquals(null, queryCon[0].AccountId, 'The contact should have an auto-created household.');
        system.assert(queryCon[0].Account.npe01__SYSTEMIsIndividual__c, 'The household should have the system flag set.');

        Contact con2 = new Contact(LastName='foo', FirstName='Mr.', AccountId=querycon[0].AccountId);
        insert con2;

        test.startTest();
        CON_DeleteContactOverride_CTRL ctrl = new CON_DeleteContactOverride_CTRL(new ApexPages.StandardController(con));
        ctrl.processDelete();
        test.stopTest();

        list<account> queryAcc = [SELECT Id, isDeleted FROM Account WHERE id = :querycon[0].AccountId ALL ROWS];
        
        system.assertEquals(1,queryAcc.size(),'There should be one account.');
        system.assertEquals(false, queryacc[0].isDeleted,'Deleted flag should not be set.');

        queryCon = [SELECT Id, isDeleted FROM Contact WHERE id = :con.id ALL ROWS];

        system.assertEquals(1,queryCon.size(),'There should be one deleted contact.');
        system.assert(queryCon[0].isDeleted,'Deleted flag should be set.');

    }

    /*******************************************************************************************************
    * @description Deletes contact in the 1:1 account model. Verifies that contact is marked is in the 
    * recycle bin and not hard deleted.
    */
    @isTest static void deleteOneToOne() {
        if (strTestOnly != '*' && strTestOnly != 'deleteOneToOne') return;
        
        npe01__Contacts_and_Orgs_Settings__c contactSettingsForTests = UTIL_CustomSettingsFacade.getContactsSettingsForTests(
            new npe01__Contacts_and_Orgs_Settings__c (
                npe01__Account_Processor__c = CAO_Constants.ONE_TO_ONE_PROCESSOR
            )
        );
        Contact con = new Contact(LastName='foo');
        insert con;
        
        list<Contact> queryCon = [SELECT Id, AccountId, Account.npe01__SYSTEMIsIndividual__c FROM Contact WHERE Id = :con.id];
        system.assertNotEquals(null, queryCon[0].AccountId, 'The contact should have an auto-created household.');
        system.assert(queryCon[0].Account.npe01__SYSTEMIsIndividual__c, 'The household should have the system flag set.');

        test.startTest();
        CON_DeleteContactOverride_CTRL ctrl = new CON_DeleteContactOverride_CTRL(new ApexPages.StandardController(con));
        ctrl.processDelete();
        ctrl.deleteAccount();
        test.stopTest();

        list<account> queryAcc = [SELECT Id, isDeleted FROM Account WHERE id = :querycon[0].AccountId ALL ROWS];
        
        system.assertEquals(1,queryAcc.size(),'There should be one deleted account.');
        system.assert(queryacc[0].isDeleted,'Deleted flag should be set.');

        queryCon = [SELECT Id, isDeleted FROM Contact WHERE id = :con.id ALL ROWS];

        system.assertEquals(1,queryCon.size(),'There should be one deleted contact.');
        system.assert(queryCon[0].isDeleted,'Deleted flag should be set.');
    }
    
    /*******************************************************************************************************
    * @description Deletes contact in the household account model that is alone in their household
    * account. Verifies that contact is in the recycle bin and not hard deleted.
    */
    @isTest static void deleteAloneInHousehold() {
        if (strTestOnly != '*' && strTestOnly != 'deleteAloneInHousehold') return;

        Contact con = new Contact(LastName='foo');
        insert con;
        
        list<Contact> queryCon = [SELECT Id, AccountId, Account.npe01__SYSTEMIsIndividual__c FROM Contact WHERE Id = :con.id];
        system.assertNotEquals(null, queryCon[0].AccountId, 'The contact should have an auto-created household.');
        system.assert(queryCon[0].Account.npe01__SYSTEMIsIndividual__c, 'The household should have the system flag set.');

        test.startTest();
        CON_DeleteContactOverride_CTRL ctrl = new CON_DeleteContactOverride_CTRL(new ApexPages.StandardController(con));
        ctrl.processDelete();
        ctrl.deleteAccount();
        test.stopTest();

        list<account> queryAcc = [SELECT Id, isDeleted FROM Account WHERE id = :querycon[0].AccountId ALL ROWS];
        
        system.assertEquals(1,queryAcc.size(),'There should be one deleted account.');
        system.assert(queryacc[0].isDeleted,'Deleted flag should be set.');

        queryCon = [SELECT Id, isDeleted FROM Contact WHERE id = :con.id ALL ROWS];

        system.assertEquals(1,queryCon.size(),'There should be one deleted contact.');
        system.assert(queryCon[0].isDeleted,'Deleted flag should be set.');

    }
}