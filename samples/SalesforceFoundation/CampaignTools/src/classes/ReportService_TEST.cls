/*
    Copyright (c) 2015 Salesforce.org
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
@isTest
private with sharing class ReportService_TEST {
    @isTest(SeeAllData=True)
    public static void testGetIdColumnsReturnsIdColumns() {
        Map<String, String> expectedColumns = new Map<String, String>{
            'CONTACT_ID' => 'Contact ID'
        };

        Report testReport = [
            SELECT Id
            FROM Report
            WHERE DeveloperName = 'NPSP_Test_Contact_Report'
        ];

        ReportService service = new ReportService();

        Map<String, String> actualColumns = service.getIdColumns(testReport.Id);

        System.assertEquals(expectedColumns, actualColumns);
    }

    @isTest(SeeAllData=True)
    public static void testReportIterator() {
        Map<Id, Contact> testContacts = new Map<Id, Contact>([
            SELECT Id
            FROM Contact
            WHERE Department = 'NPSP Test Contact'
            LIMIT 2501
        ]);

        if (testContacts.size() != 2500) {
            // We do not have the test data that we need
            // in this org, so we are not going to run
            // this test
            return;
        }

        Set<Id> expectedIds = testContacts.keySet();

        Report testReport = [
            SELECT Id
            FROM Report
            WHERE DeveloperName = 'NPSP_Test_Contact_Report'
        ];

        String columnName = 'CONTACT_ID';

        Test.startTest();

        Iterable<Id> reportRowIdIterable = new ReportService.ReportRowIdIterableIterator(
            testReport.Id,
            'CONTACT_ID'
        );

        Iterator<Id> reportRowIdIterator = reportRowIdIterable.iterator();

        Set<Id> actualIds = new Set<Id>();

        while(reportRowIdIterator.hasNext()) {
            actualIds.add(reportRowIdIterator.next());
        }

        Test.stopTest();

        System.assertEquals(expectedIds, actualIds);
    }
}