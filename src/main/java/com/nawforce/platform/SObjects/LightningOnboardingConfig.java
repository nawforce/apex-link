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
public class LightningOnboardingConfig extends SObject {
	public static SObjectType$<LightningOnboardingConfig> SObjectType;
	public static SObjectFields$<LightningOnboardingConfig> Fields;
	public Id CollaborationGroupId;
	public CollaborationGroup CollaborationGroup;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String CustomQuestion;
	public String DeveloperName;
	public Integer FeedbackFormDaysFrequency;
	public Id Id;
	public Boolean IsCustom;
	public Boolean IsDeleted;
	public String Language;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public String MasterLabel;
	public Integer PromptDelayTime;
	public Boolean SendFeedbackToSalesforce;
	public Datetime SystemModstamp;

	public LightningOnboardingConfig clone$() {throw new java.lang.UnsupportedOperationException();}
	public LightningOnboardingConfig clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public LightningOnboardingConfig clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public LightningOnboardingConfig clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public LightningOnboardingConfig clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
