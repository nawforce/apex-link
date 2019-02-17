/*
 [The "BSD licence"]
 Copyright (c) 2019 Kevin Jones
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
    derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package io.github.nawforce.platform.SObjects;

import io.github.nawforce.platform.System.Integer;
import io.github.nawforce.platform.System.String;
import io.github.nawforce.platform.System.*;

@SuppressWarnings("unused")
public class AccountChangeEvent extends SObject {
	public String AccountNumber;
	public String AccountSource;
	public Decimal AnnualRevenue;
	public Address BillingAddress;
	public String BillingCity;
	public String BillingCountry;
	public String BillingGeocodeAccuracy;
	public Decimal BillingLatitude;
	public Decimal BillingLongitude;
	public String BillingPostalCode;
	public String BillingState;
	public String BillingStreet;
	public Object ChangeEventHeader;
	public String CurrencyIsoCode;
	public Id DandbCompanyId;
	public DandBCompany DandbCompany;
	public String Description;
	public String DunsNumber;
	public String Fax;
	public String FirstName;
	public String Industry;
	public String Jigsaw;
	public String JigsawCompanyId;
	public String LastName;
	public String NaicsCode;
	public String NaicsDesc;
	public String Name;
	public Integer NumberOfEmployees;
	public Id OwnerId;
	public User Owner;
	public String Ownership;
	public Id ParentId;
	public Account Parent;
	public String Phone;
	public String Rating;
	public String ReplayId;
	public String Salutation;
	public Address ShippingAddress;
	public String ShippingCity;
	public String ShippingCountry;
	public String ShippingGeocodeAccuracy;
	public Decimal ShippingLatitude;
	public Decimal ShippingLongitude;
	public String ShippingPostalCode;
	public String ShippingState;
	public String ShippingStreet;
	public String Sic;
	public String SicDesc;
	public String Site;
	public String TickerSymbol;
	public String Tradestyle;
	public String Type;
	public String Website;
	public String YearStarted;
}
