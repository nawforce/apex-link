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
public class PromptVersion extends SObject {
	public static SObjectType$<PromptVersion> SObjectType;
	public static SObjectFields$<PromptVersion> Fields;
	public String ActionButtonLabel;
	public String ActionButtonLink;
	public String Body;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public Integer DelayDays;
	public String Description;
	public String DismissButtonLabel;
	public String DisplayPosition;
	public String DisplayType;
	public Date EndDate;
	public String Header;
	public Id Id;
	public String IndexWithIsPublished;
	public String IndexWithoutIsPublished;
	public Boolean IsDeleted;
	public Boolean IsPublished;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public String MasterLabel;
	public Id ParentId;
	public Prompt Parent;
	public Id PublishedByUserId;
	public User PublishedByUser;
	public Date PublishedDate;
	public Boolean ShouldDisplayActionButton;
	public Boolean ShouldIgnoreGlobalDelay;
	public Date StartDate;
	public Datetime SystemModstamp;
	public String TargetAppDeveloperName;
	public String TargetAppNamespacePrefix;
	public String TargetPageKey1;
	public String TargetPageKey2;
	public String TargetPageType;
	public Integer TimesToDisplay;
	public String Title;
	public String UserAccess;
	public String UserProfileAccess;
	public Integer VersionNumber;
	public String VideoLink;

	public PromptVersion clone$() {throw new java.lang.UnsupportedOperationException();}
	public PromptVersion clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public PromptVersion clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public PromptVersion clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public PromptVersion clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
