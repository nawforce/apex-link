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
* @description Utilities for working with currencies
*/
public with sharing class UTIL_Currency implements UTIL_Currency.Interface_x {

    public interface Interface_x {
        Boolean isMultiCurrencyOrganization();
        String getDefaultCurrency();
        String getDefaultCurrencySymbol();
        String getCurrencySymbol(Id recordId);
        String getCurrencyIsoCodeQuery(Id recordId);
        String queryCurrencyIsoCode(Id recordId);
    }

    /** @description Store instance for Singleton pattern */
    @TestVisible
    private static Interface_x instance;

    /**
     * @description Get the singleton instance of the UTIL_Currency class
     * @return UTIL_Currency.Interface_x
     */
    public static Interface_x getInstance() {
        if (instance == null) {
            instance = new UTIL_Currency();
        }
        return instance;
    }

    /** @description Private constructor for Singleton pattern */
    @TestVisible
    private UTIL_Currency() {}

    /**
     * @description Wrapper for UserInfo.isMultiCurrencyOrganization()
     */
    public Boolean isMultiCurrencyOrganization() {
        return UserInfo.isMultiCurrencyOrganization();
    }

    /**
     * @description Wrapper for UserInfo.getDefaultCurrency()
     */
    public String getDefaultCurrency() {
        return UserInfo.getDefaultCurrency();
    }

    /**
     * @description For single currency orgs, return the symbol of the org's
     * currency or the org's currency ISO code if no symbol is known for that
     * currency
     *
     * @return String
     */
    public String getDefaultCurrencySymbol() {
        String orgCurrency = UTIL_Currency.getInstance().getDefaultCurrency();
        if (currencySymbols.containsKey(orgCurrency)) {
            return currencySymbols.get(orgCurrency);
        } else {
            return orgCurrency;
        }
    }

    /**
     * @description Return the currency symbol appropriate for the given record
     * id.
     * If the org is multi currency enabled, it will return the
     * currency iso code from the given record.  If the org is not multi
     * currency enabled, it will return the symbol for the currency of the org,
     * or the currency iso code if no symbol is known.
     *
     * @param recordId The id of the record to determine currency symbol from
     * @return String
     */
    public String getCurrencySymbol(Id recordId) {
        Interface_x currencyUtil = UTIL_Currency.getInstance();
        if (currencyUtil.isMultiCurrencyOrganization() && recordId != null) {
            return currencyUtil.queryCurrencyIsoCode(recordId);
        } else {
            return currencyUtil.getDefaultCurrencySymbol();
        }
    }

    /**
     * @description Build a SOQL string to query CurrencyIsoCode from the
     * object identified by the given record id
     *
     * @param recordId The id of the record that should be queried
     * @return String
     */
    public String getCurrencyIsoCodeQuery(Id recordId) {
        String objectName = recordId.getSobjectType().getDescribe().getName();
        return String.format(
            'SELECT CurrencyIsoCode FROM {0} WHERE Id = \'\'{1}\'\' LIMIT 1',
            new List<String>{objectName, recordId}
        );
    }

    /**
     * @description Query the database for the CurrencyIsoCode of the object
     * identified by the given record id
     *
     * @param recordId The id of the record that should be queried
     * @return String
     */
    public String queryCurrencyIsoCode(Id recordId) {
        String isoCodeQuery = UTIL_Currency.getInstance().getCurrencyIsoCodeQuery(recordId);
        sObject objectWithCurrency = Database.query(isoCodeQuery);
        return (String) objectWithCurrency.get('CurrencyIsoCode');
    }

    /**
     * @description Mapping of common currency ISO codes to currency symbols
     */
    private static Map<String, String> currencySymbols = new Map<String, String>{
        'USD' => '$',
        'CNY' => '¥',
        'JPY' => '¥',
        'EUR' => '€',
        'GBP' => '£',
        'BRL' => 'R$',
        'INR' => '₹',
        'RUB' => '₽',
        'CAD' => '$',
        'AUD' => '$',
        'KRW' => '₩',
        'MXN' => '$',
        'IDR' => 'Rp',
        'TRY' => '₺',
        'SAR' => '﷼'
    };
}