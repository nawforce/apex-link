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
private with sharing class CampaignListBuilder_TEST {
    public static testMethod void testGetReportIdColumnsReturnsIdColumns() {
        Id reportId = CampaignList_TEST.getNextId(Report.sObjectType);

        Map<String, String> expectedColumns = new Map<String, String>{
            'CONTACT_ID' => 'Contact ID'
        };

        ReportServiceWithResultsStub service = new ReportServiceWithResultsStub();
        service.idColumns = expectedColumns;
        CampaignList.setReportService(service);

        Test.startTest();

        Map<String, String> actualColumns = CampaignListBuilder_CTRL.getReportIdColumns(reportId);

        Test.stopTest();

        System.assertEquals(expectedColumns, actualColumns);
        System.assertEquals(reportId, service.reportId);
    }

    public static testMethod void testGetReportIdInvalidThrowsNoColumnsException() {
        ReportServiceWithExceptionStub service = new ReportServiceWithExceptionStub();
        CampaignList.setReportService(service);

        Boolean exceptionThrown = false;

        try {
            CampaignListBuilder_CTRL.getReportIdColumns(null);
        } catch (AuraHandledException ex) {
            exceptionThrown = true;
        }

        System.assert(exceptionThrown);
    }

    public static testMethod void testCheckPerms() {
        Boolean exceptionThrown;

        try {
            CampaignListBuilder_CTRL.checkPerms();
            exceptionThrown = false;
        } catch (AuraHandledException ex) {
            exceptionThrown = true;
        }

        System.assert(!exceptionThrown, 'An exception should not be thrown and the user should have permission.');
    }


    public static testMethod void testCheckWithoutPerms() {
        Boolean exceptionThrown;

        try {
            CampaignTools_UTIL.throwException = true;
            CampaignListBuilder_CTRL.checkPerms();
            exceptionThrown = false;
        } catch (AuraHandledException ex) {
            exceptionThrown = true;
        }

        System.assert(exceptionThrown, 'An exception should be thrown and the user should not have permission.');
    }

    private class ReportServiceWithResultsStub implements CampaignList.ReportService {
        public Id reportId;
        public Map<String, String> idColumns;
        public Map<String, String> getIdColumns(Id reportId) {
            this.reportId = reportId;
            return idColumns;
        }
    }

    private class ReportServiceWithExceptionStub implements CampaignList.ReportService {
        public Map<String, String> getIdColumns(Id reportId) {
            throw new DummyException();
        }
    }

    private class DummyException extends Exception {}
}