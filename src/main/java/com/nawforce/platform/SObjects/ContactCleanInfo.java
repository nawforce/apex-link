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
import com.nawforce.platform.System.String;
import com.nawforce.platform.System.*;


@SuppressWarnings("unused")
public class ContactCleanInfo extends SObject {
	public static SObjectType$<ContactCleanInfo> SObjectType;
	public static SObjectFields$<ContactCleanInfo> Fields;
	public Address Address;
	public String City;
	public Boolean CleanedByJob;
	public Boolean CleanedByUser;
	public Id ContactId;
	public Contact Contact;
	public String ContactStatusDataDotCom;
	public String Country;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String DataDotComId;
	public String Email;
	public String FirstName;
	public String GeocodeAccuracy;
	public Id Id;
	public Boolean IsDeleted;
	public Boolean IsDifferentCity;
	public Boolean IsDifferentCountry;
	public Boolean IsDifferentCountryCode;
	public Boolean IsDifferentEmail;
	public Boolean IsDifferentFirstName;
	public Boolean IsDifferentLastName;
	public Boolean IsDifferentPhone;
	public Boolean IsDifferentPostalCode;
	public Boolean IsDifferentState;
	public Boolean IsDifferentStateCode;
	public Boolean IsDifferentStreet;
	public Boolean IsDifferentTitle;
	public Boolean IsFlaggedWrongAddress;
	public Boolean IsFlaggedWrongEmail;
	public Boolean IsFlaggedWrongName;
	public Boolean IsFlaggedWrongPhone;
	public Boolean IsFlaggedWrongTitle;
	public Boolean IsInactive;
	public Boolean IsReviewedAddress;
	public Boolean IsReviewedEmail;
	public Boolean IsReviewedName;
	public Boolean IsReviewedPhone;
	public Boolean IsReviewedTitle;
	public Datetime LastMatchedDate;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public String LastName;
	public Id LastStatusChangedById;
	public User LastStatusChangedBy;
	public Datetime LastStatusChangedDate;
	public Decimal Latitude;
	public Decimal Longitude;
	public String Name;
	public String Phone;
	public String PostalCode;
	public String State;
	public String Street;
	public Datetime SystemModstamp;
	public String Title;

	public ContactCleanInfo clone$() {throw new java.lang.UnsupportedOperationException();}
	public ContactCleanInfo clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public ContactCleanInfo clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public ContactCleanInfo clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public ContactCleanInfo clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
