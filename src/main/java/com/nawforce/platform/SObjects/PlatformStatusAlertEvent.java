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
public class PlatformStatusAlertEvent extends SObject {
	public static SObjectType$<PlatformStatusAlertEvent> SObjectType;
	public static SObjectFields$<PlatformStatusAlertEvent> Fields;

	public String ApiErrorCode;
	public String ComponentName;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public Datetime EventDate;
	public String EventIdentifier;
	public String ExtendedErrorCode;
	public String RelatedEventIdentifier;
	public String ReplayId;
	public String RequestId;
	public String ServiceJobId;
	public String ServiceName;
	public String StatusType;
	public String SubComponentName;
	public String Subject;
	public Id UserId;
	public User User;
	public String Username;

	public PlatformStatusAlertEvent clone$() {throw new java.lang.UnsupportedOperationException();}
	public PlatformStatusAlertEvent clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public PlatformStatusAlertEvent clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public PlatformStatusAlertEvent clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public PlatformStatusAlertEvent clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
