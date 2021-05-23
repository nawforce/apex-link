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
public class ContactRequest extends SObject {
	public static SObjectType$<ContactRequest> SObjectType;
	public static SObjectFields$<ContactRequest> Fields;

	public com.nawforce.platform.System.Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String CurrencyIsoCode;
	public Id Id;
	public Boolean IsDeleted;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public Datetime LastReferencedDate;
	public Datetime LastViewedDate;
	public String Name;
	public Id OwnerId;
	public Group Owner;
	public String PreferredChannel;
	public String PreferredPhone;
	public String RequestDescription;
	public String RequestReason;
	public String Status;
	public Datetime SystemModstamp;
	public Id WhatId;
	public Account What;
	public Id WhoId;
	public Contact Who;

	public ActivityHistory[] ActivityHistories;
	public EmailMessage[] Emails;
	public Event[] Events;
	public OpenActivity[] OpenActivities;
	public RecordActionHistory[] RecordActionHistories;
	public RecordAction[] RecordActions;
	public Task[] Tasks;

	public ContactRequest clone$() {throw new java.lang.UnsupportedOperationException();}
	public ContactRequest clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public ContactRequest clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public ContactRequest clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public ContactRequest clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
