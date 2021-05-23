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
public class UserProvisioningRequest extends SObject {
	public static SObjectType$<UserProvisioningRequest> SObjectType;
	public static SObjectFields$<UserProvisioningRequest> Fields;

	public String AppName;
	public String ApprovalStatus;
	public Id ConnectedAppId;
	public ConnectedApplication ConnectedApp;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String ExternalUserId;
	public Id Id;
	public Boolean IsDeleted;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public Id ManagerId;
	public User Manager;
	public String Name;
	public String Operation;
	public Id OwnerId;
	public Group Owner;
	public Id ParentId;
	public UserProvisioningRequest Parent;
	public Integer RetryCount;
	public Id SalesforceUserId;
	public User SalesforceUser;
	public Datetime ScheduleDate;
	public String State;
	public Datetime SystemModstamp;
	public Id UserProvAccountId;
	public UserProvAccount UserProvAccount;
	public Id UserProvConfigId;
	public UserProvisioningConfig UserProvConfig;

	public ProcessInstance[] ProcessInstances;
	public ProcessInstanceHistory[] ProcessSteps;

	public UserProvisioningRequest clone$() {throw new java.lang.UnsupportedOperationException();}
	public UserProvisioningRequest clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public UserProvisioningRequest clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public UserProvisioningRequest clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public UserProvisioningRequest clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
