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
public class LeadChangeEvent extends SObject {
	public static SObjectType$<LeadChangeEvent> SObjectType;
	public static SObjectFields$<LeadChangeEvent> Fields;


	public com.nawforce.platform.System.Address Address;
	public Decimal AnnualRevenue;
	public Object ChangeEventHeader;
	public String City;
	public String Company;
	public String CompanyDunsNumber;
	public Id ConvertedAccountId;
	public Account ConvertedAccount;
	public Id ConvertedContactId;
	public Contact ConvertedContact;
	public Date ConvertedDate;
	public Id ConvertedOpportunityId;
	public Opportunity ConvertedOpportunity;
	public String Country;
	public String CountryCode;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String CurrencyIsoCode;
	public Id DandbCompanyId;
	public DandBCompany DandbCompany;
	public String Description;
	public String Email;
	public Datetime EmailBouncedDate;
	public String EmailBouncedReason;
	public String Fax;
	public String FirstName;
	public String GeocodeAccuracy;
	public Id Id;
	public Id IndividualId;
	public Individual Individual;
	public String Industry;
	public Boolean IsConverted;
	public Boolean IsUnreadByOwner;
	public String Jigsaw;
	public String JigsawContactId;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public String LastName;
	public Decimal Latitude;
	public String LeadSource;
	public Decimal Longitude;
	public String MobilePhone;
	public String Name;
	public Integer NumberOfEmployees;
	public Id OwnerId;
	public User Owner;
	public String Phone;
	public String PostalCode;
	public String Rating;
	public String ReplayId;
	public String Salutation;
	public String State;
	public String StateCode;
	public String Status;
	public String Street;
	public String Title;
	public String Website;

	public LeadChangeEvent clone$() {throw new java.lang.UnsupportedOperationException();}
	public LeadChangeEvent clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public LeadChangeEvent clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public LeadChangeEvent clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public LeadChangeEvent clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
