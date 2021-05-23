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
public class AuthorizationForm extends SObject {
	public static SObjectType$<AuthorizationForm> SObjectType;
	public static SObjectFields$<AuthorizationForm> Fields;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public Id DefaultAuthFormTextId;
	public AuthorizationFormText DefaultAuthFormText;
	public Date EffectiveFromDate;
	public Date EffectiveToDate;
	public Id Id;
	public Boolean IsDeleted;
	public Boolean IsSignatureRequired;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public Datetime LastReferencedDate;
	public Datetime LastViewedDate;
	public String Name;
	public Id OwnerId;
	public Group Owner;
	public String RevisionNumber;
	public Datetime SystemModstamp;

	public AuthorizationFormText[] AuthorizationForms;
	public AuthorizationFormHistory[] Histories;
	public ProcessInstance[] ProcessInstances;
	public ProcessInstanceHistory[] ProcessSteps;
	public AuthorizationFormShare[] Shares;

	public AuthorizationForm clone$() {throw new java.lang.UnsupportedOperationException();}
	public AuthorizationForm clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public AuthorizationForm clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public AuthorizationForm clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public AuthorizationForm clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
