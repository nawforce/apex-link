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
public class AccountCleanInfo extends SObject {
	public static SObjectType$<AccountCleanInfo> SObjectType;
	public static SObjectFields$<AccountCleanInfo> Fields;
	public Id AccountId;
	public Account Account;
	public String AccountSite;
	public Address Address;
	public Decimal AnnualRevenue;
	public String City;
	public Boolean CleanedByJob;
	public Boolean CleanedByUser;
	public String CompanyName;
	public String CompanyStatusDataDotCom;
	public String Country;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String DandBCompanyDunsNumber;
	public String DataDotComId;
	public String Description;
	public String DunsNumber;
	public Integer DunsRightMatchConfidence;
	public String DunsRightMatchGrade;
	public String Fax;
	public String GeocodeAccuracy;
	public Id Id;
	public String Industry;
	public Boolean IsDeleted;
	public Boolean IsDifferentAccountSite;
	public Boolean IsDifferentAnnualRevenue;
	public Boolean IsDifferentCity;
	public Boolean IsDifferentCompanyName;
	public Boolean IsDifferentCountry;
	public Boolean IsDifferentCountryCode;
	public Boolean IsDifferentDandBCompanyDunsNumber;
	public Boolean IsDifferentDescription;
	public Boolean IsDifferentDunsNumber;
	public Boolean IsDifferentFax;
	public Boolean IsDifferentIndustry;
	public Boolean IsDifferentNaicsCode;
	public Boolean IsDifferentNaicsDescription;
	public Boolean IsDifferentNumberOfEmployees;
	public Boolean IsDifferentOwnership;
	public Boolean IsDifferentPhone;
	public Boolean IsDifferentPostalCode;
	public Boolean IsDifferentSic;
	public Boolean IsDifferentSicDescription;
	public Boolean IsDifferentState;
	public Boolean IsDifferentStateCode;
	public Boolean IsDifferentStreet;
	public Boolean IsDifferentTickerSymbol;
	public Boolean IsDifferentTradestyle;
	public Boolean IsDifferentWebsite;
	public Boolean IsDifferentYearStarted;
	public Boolean IsFlaggedWrongAccountSite;
	public Boolean IsFlaggedWrongAddress;
	public Boolean IsFlaggedWrongAnnualRevenue;
	public Boolean IsFlaggedWrongCompanyName;
	public Boolean IsFlaggedWrongDescription;
	public Boolean IsFlaggedWrongDunsNumber;
	public Boolean IsFlaggedWrongFax;
	public Boolean IsFlaggedWrongIndustry;
	public Boolean IsFlaggedWrongNaicsCode;
	public Boolean IsFlaggedWrongNaicsDescription;
	public Boolean IsFlaggedWrongNumberOfEmployees;
	public Boolean IsFlaggedWrongOwnership;
	public Boolean IsFlaggedWrongPhone;
	public Boolean IsFlaggedWrongSic;
	public Boolean IsFlaggedWrongSicDescription;
	public Boolean IsFlaggedWrongTickerSymbol;
	public Boolean IsFlaggedWrongTradestyle;
	public Boolean IsFlaggedWrongWebsite;
	public Boolean IsFlaggedWrongYearStarted;
	public Boolean IsInactive;
	public Boolean IsReviewedAccountSite;
	public Boolean IsReviewedAddress;
	public Boolean IsReviewedAnnualRevenue;
	public Boolean IsReviewedCompanyName;
	public Boolean IsReviewedDandBCompanyDunsNumber;
	public Boolean IsReviewedDescription;
	public Boolean IsReviewedDunsNumber;
	public Boolean IsReviewedFax;
	public Boolean IsReviewedIndustry;
	public Boolean IsReviewedNaicsCode;
	public Boolean IsReviewedNaicsDescription;
	public Boolean IsReviewedNumberOfEmployees;
	public Boolean IsReviewedOwnership;
	public Boolean IsReviewedPhone;
	public Boolean IsReviewedSic;
	public Boolean IsReviewedSicDescription;
	public Boolean IsReviewedTickerSymbol;
	public Boolean IsReviewedTradestyle;
	public Boolean IsReviewedWebsite;
	public Boolean IsReviewedYearStarted;
	public Datetime LastMatchedDate;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public Id LastStatusChangedById;
	public User LastStatusChangedBy;
	public Datetime LastStatusChangedDate;
	public Decimal Latitude;
	public Decimal Longitude;
	public String NaicsCode;
	public String NaicsDescription;
	public String Name;
	public Integer NumberOfEmployees;
	public String Ownership;
	public String Phone;
	public String PostalCode;
	public String Sic;
	public String SicDescription;
	public String State;
	public String Street;
	public Datetime SystemModstamp;
	public String TickerSymbol;
	public String Tradestyle;
	public String Website;
	public String YearStarted;

	public AccountCleanInfo clone$() {throw new java.lang.UnsupportedOperationException();}
	public AccountCleanInfo clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public AccountCleanInfo clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public AccountCleanInfo clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public AccountCleanInfo clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
