/*
    Copyright (c) 2015, Salesforce.org
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
* @group Opportunity
* @group-content ../../ApexDocContent/Opportunity.htm
* @description Test class for Opportunity Naming.
*/
@isTest
private class OPP_OpportunityNaming_TEST
{
    /*********************************************************************************************************
    * @description If you only want to run a single test in this class, fill in its name here.
    * If you want to run all tests, then use '*'
    */
    private static string strTestOnly = '*';

    /*********************************************************************************************************
    * @description Returns an active record type id for the current profile.
    */
    private static boolean hasActiveRecType() {
        Schema.RecordTypeInfo giftRecTypeInfo = UTIL_RecordTypes.getRecordTypeInfoForGiftsTests(Opportunity.sObjectType);

        if (giftRecTypeInfo == null) {
            return false;
        } else {
            return giftRecTypeInfo.isAvailable();
        }
    }

    /*******************************************************************************************************
    * @description Creates an opportunity name setting and an opportunity, verifies name is calculated and
    * dates with a blank naming scheme use a default international format.
    * Modifies the value of a field and clicks the rename button, verifies rename happened correctly.
    ********************************************************************************************************/
    @isTest
    static void testButton() {
        if (strTestOnly != '*' && strTestOnly != 'testButton') return;

        Opportunity_Naming_Settings__c ons = new Opportunity_Naming_Settings__c(
            Name = 'foo',
            Opportunity_Name_Format__c = '{!Account.Name} {!Contact.Name} {!CloseDate} {!RecordType.Name}',
            Attribution__c = Label.oppNamingBoth
        );
        if (hasActiveRecType())
            ons.Opportunity_Record_Types__c = UTIL_RecordTypes.getrecordTypeIdForGiftsTests(Opportunity.sObjectType);
        insert ons;

        Account acc = new Account(Name='accname');
        insert acc;

        Contact con = new Contact(LastName='conname');
        insert con;

        Opportunity opp = new Opportunity(
            AccountId = acc.id,
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage(),
            Name='temp',
            Amount=8,
            CloseDate = Date.newInstance(2000, 1, 1),
            Primary_Contact__c = con.id
        );
        if (hasActiveRecType())
            opp.RecordTypeId = UTIL_RecordTypes.GetRecordTypeId(Opportunity.sObjectType, UTIL_RecordTypes.getrecordTypeNameForGiftsTests(Opportunity.sObjectType));
        insert opp;

        list<Opportunity> queryOpp = [SELECT Name FROM Opportunity WHERE Id = :opp.id];
        string oppName = 'accname conname 2000.01.01';
        if (hasActiveRecType())
            oppname += ' ' + UTIL_RecordTypes.getrecordTypeNameForGiftsTests(Opportunity.sObjectType);
        system.assertEquals(oppName, queryOpp[0].Name, 'The name should be calculated based on the setting.');

        acc.Name = 'newname';
        update acc;

        ApexPages.StandardController sc = new ApexPages.StandardController(opp);
        OPP_OpportunityNamingBTN_CTRL ctrl = new OPP_OpportunityNamingBTN_CTRL(sc);

        PageReference pageRef = Page.OPP_OpportunityNamingBTN;
        pageRef.getParameters().put('id', String.valueOf(opp.Id));
        Test.setCurrentPage(pageRef);

        ctrl.buttonClick();

        queryOpp = [SELECT Name FROM Opportunity WHERE Id = :opp.id];
        oppname = 'newname conname 2000.01.01';
        if (hasActiveRecType())
            oppname += ' ' + UTIL_RecordTypes.getrecordTypeNameForGiftsTests(Opportunity.sObjectType);
        system.assertEquals(oppName, queryOpp[0].Name, 'The name should be recalculated after clicking the button.');

    }

    /*******************************************************************************************************
    * @description Creates an opportunity name setting and an opportunity with a missing field and related
    * object. Verifies name is still calculated correctly with blanks instead of missing fields/objects.
    ********************************************************************************************************/
    @isTest
    static void testMissingData() {
        if (strTestOnly != '*' && strTestOnly != 'testMissingData') return;

        Opportunity_Naming_Settings__c ons = new Opportunity_Naming_Settings__c(
            Name = 'foo',
            Opportunity_Name_Format__c = '{!Account.Name} {!Account.npe01__One2OneContact__r.Primary_Affiliation__r.Name} {!Campaign.Name} {!Type} {!RecordType.Name}',
            Attribution__c = Label.oppNamingBoth
        );
        if (hasActiveRecType())
            ons.Opportunity_Record_Types__c = UTIL_RecordTypes.getrecordTypeIdForGiftsTests(Opportunity.sObjectType);
        insert ons;

        Account acc = new Account(Name='accname');
        insert acc;

        Contact con = new Contact(LastName='conname');
        insert con;

        Opportunity opp = new Opportunity(
            AccountId = acc.id,
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage(),
            Name='temp',
            Amount=8,
            CloseDate = Date.newInstance(2000, 1, 1),
            npe01__Contact_Id_for_Role__c = con.id
        );
        if (hasActiveRecType())
            opp.RecordTypeId = UTIL_RecordTypes.GetRecordTypeId(Opportunity.sObjectType, UTIL_RecordTypes.getrecordTypeNameForGiftsTests(Opportunity.sObjectType));
        insert opp;

        list<Opportunity> queryOpp = [SELECT Name FROM Opportunity WHERE Id = :opp.id];

        string oppname = 'accname';
        if (hasActiveRecType())
            oppname += ' ' + UTIL_RecordTypes.getrecordTypeNameForGiftsTests(Opportunity.sObjectType);
        system.assertEquals(oppname, queryOpp[0].Name, 'The name should be calculated based on the setting; missing fields should be excluded.');

    }

    /*******************************************************************************************************
    * @description Inserts 100 opportunities related to 100 accounts, and one opportunity related to a
    * contact. Verifies naming hasn't happened as naming settings haven't been inserted. Insert settings,
    * run batch naming job. Verify both types of opportunities have been named correctly.
    ********************************************************************************************************/
    @isTest
    static void testBulk() {
        if (strTestOnly != '*' && strTestOnly != 'testBulk') return;

        list<Opportunity_Naming_Settings__c> onsList = new list<Opportunity_Naming_Settings__c>();
        onsList.add(new Opportunity_Naming_Settings__c(
            Name = 'foo',
            Opportunity_Record_Types__c = '',
            Opportunity_Name_Format__c = '{!Account.Name} {!Amount} {!Type} {!RecordType.Name}',
            Attribution__c = Label.oppNamingOrganizational
        ));
        onsList.add(new Opportunity_Naming_Settings__c(
            Name = 'foo2',
            Opportunity_Name_Format__c = '{!Contact.Name} {!Contact.HasOptedOutOfFax} {!Type} {!RecordType.Name}',
            Attribution__c = Label.oppNamingIndividual
        ));

        if (hasActiveRecType())
            onsList[1].Opportunity_Record_Types__c = UTIL_RecordTypes.getRecordTypeIdForGiftsTests(Opportunity.sObjectType);

        Contact con = new Contact(LastName='conname', HasOptedOutOfFax=true);
        insert con;

        list<Contact> queryCon = [SELECT Id, AccountId FROM Contact WHERE Id = :con.id];

        list<Account> listAcc = UTIL_UnitTestData_TEST.CreateMultipleTestAccounts(10, null);
        insert listAcc;

        list<Opportunity> listOpp = UTIL_UnitTestData_TEST.OppsForAccountListByRecTypeId(listAcc, null, UTIL_UnitTestData_TEST.getClosedWonStage(), Date.newInstance(2000, 1, 1), 8, null,null);

        opportunity opp = new Opportunity(
            AccountId = queryCon[0].accountId,
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage(),
            Name='Test',
            Amount=8.8,
            CloseDate = Date.newInstance(2000, 1, 1),
            npe01__Contact_Id_for_Role__c = con.id
        );
        if (hasActiveRecType())
            opp.RecordTypeId = UTIL_RecordTypes.getRecordTypeIdForGiftsTests(Opportunity.sObjectType);
        listOpp.add(opp);
        insert listOpp;

        //inserting settings after the fact so opps aren't named prematurely
        insert onsList;
        //blank out cached version so the inserted setting takes effect
        UTIL_ListCustomSettingsFacade.mapOppNamingSettings = null;

        //verify opps haven't been renamed yet
        list<Opportunity> queryOpp = [SELECT Name FROM Opportunity WHERE Id IN :listOpp];
        for (Opportunity thisopp : queryOpp) {
            system.assert(thisopp.name.contains('Test'));
        }

        test.startTest();
        STG_PanelOppNamingBatch_CTRL ctrl = new STG_PanelOppNamingBatch_CTRL();
        ctrl.runBatch();

        test.stopTest();

        queryOpp = [SELECT Name, RecordType.Name, Account.npe01__SYSTEMIsIndividual__c FROM Opportunity WHERE AccountId = :queryCon[0].accountId];

        string oppname = 'conname true';
        if (hasActiveRecType())
            oppname += ' ' + UTIL_RecordTypes.getrecordTypeNameForGiftsTests(Opportunity.sObjectType);

        system.assertEquals(oppname, queryOpp[0].Name, 'The name should be calculated based on the individual attributed setting.');

        queryOpp = [SELECT Name FROM Opportunity WHERE AccountId != :queryCon[0].accountId];

        for (Opportunity thisopp : queryOpp) {
            system.assert(!thisopp.name.contains('8.00'), 'Opportunities have been renamed by batch process.');
            system.assert(thisopp.name.contains('8'), 'Opportunities have been renamed by batch process.');
        }
    }

    /*******************************************************************************************************
    * @description Creates an invalid opportunity name setting. Inserts an opportunity. Verifies name left
    * unchanged and no exception is thrown.
    ********************************************************************************************************/
    @isTest
    static void testException() {
        if (strTestOnly != '*' && strTestOnly != 'testException') return;

        Opportunity_Naming_Settings__c ons = new Opportunity_Naming_Settings__c(
            Name = 'foo',
            Opportunity_Name_Format__c = '{!Account.Invalid_Field}',
            Attribution__c = Label.oppNamingBoth
        );
        insert ons;

        Account acc = new Account(Name='accname');
        insert acc;

        Contact con = new Contact(LastName='conname');
        insert con;

        Opportunity opp = new Opportunity(
            AccountId = acc.id,
            StageName = UTIL_UnitTestData_TEST.getClosedWonStage(),
            Name='temp',
            Amount=8,
            CloseDate = Date.newInstance(2000, 1, 1),
            npe01__Contact_Id_for_Role__c = con.id
        );

        Exception oppException;

        Test.startTest();
        try {
            insert opp;
        } catch (Exception ex) {
            oppException = ex;
        }
        Test.stopTest();

        system.assertEquals(null, oppException, 'No exception should be thrown.');
        list<Opportunity> queryOpp = [SELECT Name FROM Opportunity WHERE Id = :opp.id];
        system.assertEquals('temp', queryOpp[0].Name, 'The name should not be calculated, as it is an invalid format based on the setting.');
        List<Error__c> errors = [select Id from Error__c];
        System.assertEquals(1, errors.size(), 'An error record should be created.');

    }
}