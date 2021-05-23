/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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
 */

package com.nawforce.platform.SObjects;

import com.nawforce.platform.Internal.SObjectFields$;
import com.nawforce.platform.Internal.SObjectType$;
import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.String;
import com.nawforce.platform.System.*;


@SuppressWarnings("unused")
public class AccountChangeEvent extends SObject {
	public static SObjectType$<AccountChangeEvent> SObjectType;
	public static SObjectFields$<AccountChangeEvent> Fields;

	public String AccountNumber;
	public String AccountSource;
	public Decimal AnnualRevenue;
	public Address BillingAddress;
	public String BillingCity;
	public String BillingCountry;
	public String BillingCountryCode;
	public String BillingGeocodeAccuracy;
	public Decimal BillingLatitude;
	public Decimal BillingLongitude;
	public String BillingPostalCode;
	public String BillingState;
	public String BillingStateCode;
	public String BillingStreet;
	public Object ChangeEventHeader;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String CurrencyIsoCode;
	public Id DandbCompanyId;
	public DandBCompany DandbCompany;
	public String Description;
	public String DunsNumber;
	public String Fax;
	public String FirstName;
	public Id Id;
	public String Industry;
	public String Jigsaw;
	public String JigsawCompanyId;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
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
	public String ShippingCountryCode;
	public String ShippingGeocodeAccuracy;
	public Decimal ShippingLatitude;
	public Decimal ShippingLongitude;
	public String ShippingPostalCode;
	public String ShippingState;
	public String ShippingStateCode;
	public String ShippingStreet;
	public String Sic;
	public String SicDesc;
	public String Site;
	public String TickerSymbol;
	public String Tradestyle;
	public String Type;
	public String Website;
	public String YearStarted;

	public AccountChangeEvent clone$() {throw new java.lang.UnsupportedOperationException();}
	public AccountChangeEvent clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public AccountChangeEvent clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public AccountChangeEvent clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public AccountChangeEvent clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
