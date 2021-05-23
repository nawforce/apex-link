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
public class UriEventStream extends SObject {
	public static SObjectType$<UriEventStream> SObjectType;
	public static SObjectFields$<UriEventStream> Fields;
	public Datetime CreatedDate;
	public Datetime EventDate;
	public String EventIdentifier;
	public String LoginKey;
	public String Message;
	public String Name;
	public String Operation;
	public String OperationStatus;
	public String QueriedEntities;
	public String RecordId;
	public String RelatedEventIdentifier;
	public String ReplayId;
	public String SessionKey;
	public String SessionLevel;
	public String SourceIp;
	public Id UserId;
	public User User;
	public String UserType;
	public String Username;

	public UriEventStream clone$() {throw new java.lang.UnsupportedOperationException();}
	public UriEventStream clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public UriEventStream clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public UriEventStream clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public UriEventStream clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
