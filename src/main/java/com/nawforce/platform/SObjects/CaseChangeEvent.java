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
public class CaseChangeEvent extends SObject {
	public static SObjectType$<CaseChangeEvent> SObjectType;
	public static SObjectFields$<CaseChangeEvent> Fields;

	public Id AccountId;
	public Account Account;
	public Id AssetId;
	public Asset Asset;
	public String CaseNumber;
	public Object ChangeEventHeader;
	public Datetime ClosedDate;
	public Id ContactId;
	public Contact Contact;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String CurrencyIsoCode;
	public String Description;
	public Id Id;
	public Boolean IsClosed;
	public Boolean IsEscalated;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public String Origin;
	public Id OwnerId;
	public User Owner;
	public Id ParentId;
	public Case Parent;
	public String Priority;
	public String Reason;
	public String ReplayId;
	public String Status;
	public String Subject;
	public String SuppliedCompany;
	public String SuppliedEmail;
	public String SuppliedName;
	public String SuppliedPhone;
	public String Type;

	public CaseChangeEvent clone$() {throw new java.lang.UnsupportedOperationException();}
	public CaseChangeEvent clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public CaseChangeEvent clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public CaseChangeEvent clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public CaseChangeEvent clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
