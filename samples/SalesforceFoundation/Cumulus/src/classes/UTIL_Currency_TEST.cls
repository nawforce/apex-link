/*
    Copyright (c) 2016, Salesforce.org
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
* @group Util
* @description Tests for currency utility class
*/
@isTest
public class UTIL_Currency_TEST {

    /**
     * @description UTIL_Currency.isMultiCurrencyOrganization() should return
     * the same value as UserInfo.isMultiCurrencyOrganization()
     */
    @isTest
    private static void testIsMultiCurrencyOrganizationWrapsUserInfoMethod() {
        UTIL_Currency actual = new UTIL_Currency();
        System.assertEquals(
            UserInfo.isMultiCurrencyOrganization(),
            actual.isMultiCurrencyOrganization()
        );
    }

    /**
     * @description UTIL_Currency.getDefaultCurrency() should return the same
     * value as UserInfo.getDefaultCurrency()
     */
    @isTest
    private static void testGetDefaultCurrencyWrapsUserInfoMethod() {
        UTIL_Currency actual = new UTIL_Currency();
        System.assertEquals(
            UserInfo.getDefaultCurrency(),
            actual.getDefaultCurrency()
        );
    }

    /**
     * @description UTIL_Currency implements the Singleton pattern.  If the
     * class has already been instantiated, it should be returned by
     * getInstance().
     */
    @isTest
    private static void testSingletonReturnsExistingInstance() {
        UtilCurrencyMock existingInstance = new UtilCurrencyMock();
        UTIL_Currency.instance = existingInstance;

        System.assertEquals(
            existingInstance,
            UTIL_Currency.getInstance()
        );
    }

    /**
     * @description UTIL_Currency implements the Singleton pattern.  If the
     * class has not already been instantiated, then UTIL_Currency should be
     * instantiated when getInstance() is called.
     */
    @isTest
    private static void testSingletonReturnsNewInstanceIfNoExisting() {
        System.assertEquals(null, UTIL_Currency.instance);
        System.assert(UTIL_Currency.getInstance() instanceof UTIL_Currency);
    }

    /**
     * @description If the org's default currency iso code maps to a known
     * currency symbol, then getDefaultCurrencySymbol() should return that
     * symbol (and not the ISO code)
     */
    @isTest
    private static void testGetDefaultCurrencySymbolReturnsKnownSymbol() {
        UTIL_Currency actual = new UTIL_Currency();
        UtilCurrencyMock mock = new UtilCurrencyMock();
        mock.getDefaultCurrencyReturn = 'EUR';
        UTIL_Currency.instance = mock;

        System.assertEquals(
            '€',
            actual.getDefaultCurrencySymbol()
        );
    }

    /**
     * @description If the org's default currency iso code does not have a
     * known currency symbol, then getDefaultCurrencySymbol() should return the
     * currency ISO code.
     */
    @isTest
    private static void testGetDefaultCurrencySymbolReturnsIsoCodeIfUnknownSymbol() {
        UTIL_Currency actual = new UTIL_Currency();
        UtilCurrencyMock mock = new UtilCurrencyMock();
        mock.getDefaultCurrencyReturn = 'FOO';
        UTIL_Currency.instance = mock;

        System.assertEquals(
            'FOO',
            actual.getDefaultCurrencySymbol()
        );
    }

    /**
     * @description If the organization is multi-currency enabled, then calling
     * getCurrencySymbol() should query for the given record id and return the
     * currency iso code of that record.
     */
    @isTest
    private static void testGetCurrencySymbolQueriesForCodeIfMultiCurrency() {
        Id recordId = Account.sObjectType.getDescribe().getKeyPrefix() + '000000000001AAA';
        UTIL_Currency actual = new UTIL_Currency();
        UtilCurrencyMock mock = new UtilCurrencyMock();
        mock.isMultiCurrencyOrganizationReturn = true;
        mock.queryCurrencyIsoCodeReturn = 'FOO';
        UTIL_Currency.instance = mock;

        System.assertEquals(
            'FOO',
            actual.getCurrencySymbol(recordId)
        );

        System.assertEquals(
            recordId,
            mock.queryCurrencyIsoCodeRecordId
        );
    }

    /**
     * @description If the organization is single currency, then calling
     * getCurrencySymbol() should return the currency symbol corresponding to
     * the org's default currency
     */
    @isTest
    private static void testGetCurrencySymbolReturnsDefaultCurrencySymbolIfSingleCurrency() {
        Id recordId = Account.sObjectType.getDescribe().getKeyPrefix() + '000000000001AAA';
        UTIL_Currency actual = new UTIL_Currency();
        UtilCurrencyMock mock = new UtilCurrencyMock();
        mock.isMultiCurrencyOrganizationReturn = false;
        mock.getDefaultCurrencySymbolReturn = '$';
        UTIL_Currency.instance = mock;

        System.assertEquals(
            '$',
            actual.getCurrencySymbol(recordId)
        );
    }

    /**
     * @description Calling getCurrencyIsoCodeQuery() should properly build a
     * query that retrieves the CurrencyIsoCode of the given record.
     */
    @isTest
    private static void testGetCurrencyIsoCodeQueryBuildsCorrectQuery() {
        Id recordId = Account.sObjectType.getDescribe().getKeyPrefix() + '000000000001AAA';

        System.assertEquals(
            'SELECT CurrencyIsoCode FROM Account WHERE Id = \'001000000000001AAA\' LIMIT 1',
            UTIL_Currency.getInstance().getCurrencyIsoCodeQuery(recordId)
        );
    }

    /**
     * @description A mock implementation of UTIL_Currency.Interface_x that can
     * be used to provide pre-set return values from methods and store
     * parameters passed to methods.
     */
    public class UtilCurrencyMock implements UTIL_Currency.Interface_x {
        public Boolean isMultiCurrencyOrganizationReturn;
        public String getDefaultCurrencyReturn;
        public String getDefaultCurrencySymbolReturn;
        public Id getCurrencySymbolRecordId;
        public String getCurrencySymbolReturn;
        public Id getCurrencyIsoCodeQueryRecordId;
        public String getCurrencyIsoCodeQueryReturn;
        public Id queryCurrencyIsoCodeRecordId;
        public String queryCurrencyIsoCodeReturn;
        public Boolean isMultiCurrencyOrganization() {
            return isMultiCurrencyOrganizationReturn;
        }
        public String getDefaultCurrency() {
            return getDefaultCurrencyReturn;
        }
        public String getDefaultCurrencySymbol() {
            return getDefaultCurrencySymbolReturn;
        }
        public String getCurrencySymbol(Id recordId) {
            getCurrencySymbolRecordId = recordId;
            return getCurrencySymbolReturn;
        }
        public String getCurrencyIsoCodeQuery(Id recordId) {
            getCurrencyIsoCodeQueryRecordId = recordId;
            return getCurrencyIsoCodeQueryReturn;
        }
        public String queryCurrencyIsoCode(Id recordId) {
            queryCurrencyIsoCodeRecordId = recordId;
            return queryCurrencyIsoCodeReturn;
        }
    }
}