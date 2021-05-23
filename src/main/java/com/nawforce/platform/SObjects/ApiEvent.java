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
public class ApiEvent extends SObject {
	public static SObjectType$<ApiEvent> SObjectType;
	public static SObjectFields$<ApiEvent> Fields;
	public String AdditionalInfo;
	public String ApiType;
	public Decimal ApiVersion;
	public String Application;
	public String Client;
	public Id ConnectedAppId;
	public ConnectedApplication ConnectedApp;
	public Datetime CreatedDate;
	public Integer ElapsedTime;
	public Decimal EvaluationTime;
	public Datetime EventDate;
	public String EventIdentifier;
	public Id Id;
	public Id LoginHistoryId;
	public LoginHistory LoginHistory;
	public String LoginKey;
	public String Operation;
	public String Platform;
	public Id PolicyId;
	public TransactionSecurityPolicy Policy;
	public String PolicyOutcome;
	public String QueriedEntities;
	public String Query;
	public String Records;
	public String RelatedEventIdentifier;
	public Decimal RowsProcessed;
	public String SessionKey;
	public String SessionLevel;
	public String SourceIp;
	public String UserAgent;
	public Id UserId;
	public User User;
	public String Username;

	public ApiEvent clone$() {throw new java.lang.UnsupportedOperationException();}
	public ApiEvent clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public ApiEvent clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public ApiEvent clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public ApiEvent clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
