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
public class ProcessInstance extends SObject {
	public static SObjectType$<ProcessInstance> SObjectType;
	public static SObjectFields$<ProcessInstance> Fields;

	public Datetime CompletedDate;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public Decimal ElapsedTimeInDays;
	public Decimal ElapsedTimeInHours;
	public Decimal ElapsedTimeInMinutes;
	public Id Id;
	public Boolean IsDeleted;
	public Id LastActorId;
	public User LastActor;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public Id ProcessDefinitionId;
	public ProcessDefinition ProcessDefinition;
	public String Status;
	public Id SubmittedById;
	public User SubmittedBy;
	public Datetime SystemModstamp;
	public Id TargetObjectId;
	public Account TargetObject;

	public ProcessInstanceNode[] Nodes;
	public ProcessInstanceStep[] Steps;
	public ProcessInstanceHistory[] StepsAndWorkitems;
	public ProcessInstanceWorkitem[] Workitems;

	public ProcessInstance clone$() {throw new java.lang.UnsupportedOperationException();}
	public ProcessInstance clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public ProcessInstance clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public ProcessInstance clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public ProcessInstance clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
