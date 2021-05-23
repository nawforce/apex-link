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
public class DatacloudCompany extends SObject {
	public static SObjectType$<DatacloudCompany> SObjectType;
	public static SObjectFields$<DatacloudCompany> Fields;
	public Integer ActiveContacts;
	public Decimal AnnualRevenue;
	public String City;
	public String CompanyId;
	public String Country;
	public String CountryCode;
	public String Description;
	public String DunsNumber;
	public Decimal EmployeeQuantityGrowthRate;
	public String ExternalId;
	public String Fax;
	public Integer FortuneRank;
	public String FullAddress;
	public Id Id;
	public String IncludedInSnP500;
	public String Industry;
	public Boolean IsInCrm;
	public Boolean IsInactive;
	public Boolean IsOwned;
	public String NaicsCode;
	public String NaicsDesc;
	public String Name;
	public Integer NumberOfEmployees;
	public String Ownership;
	public String Phone;
	public Integer PremisesMeasure;
	public String PremisesMeasureReliability;
	public String PremisesMeasureUnit;
	public Integer PriorYearEmployees;
	public Decimal PriorYearRevenue;
	public Decimal SalesTurnoverGrowthRate;
	public String Sic;
	public String SicCodeDesc;
	public String SicDesc;
	public String Site;
	public String State;
	public String StateCode;
	public String Street;
	public String TickerSymbol;
	public String TradeStyle;
	public Datetime UpdatedDate;
	public String Website;
	public String YearStarted;
	public String Zip;

	public DatacloudCompany clone$() {throw new java.lang.UnsupportedOperationException();}
	public DatacloudCompany clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public DatacloudCompany clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public DatacloudCompany clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public DatacloudCompany clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
