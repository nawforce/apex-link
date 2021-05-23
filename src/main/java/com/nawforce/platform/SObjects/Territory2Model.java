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
public class Territory2Model extends SObject {
	public static SObjectType$<Territory2Model> SObjectType;
	public static SObjectFields$<Territory2Model> Fields;

	public Datetime ActivatedDate;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String CurrencyIsoCode;
	public Datetime DeactivatedDate;
	public String Description;
	public String DeveloperName;
	public Id Id;
	public Boolean IsCloneSource;
	public Boolean IsDeleted;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public Datetime LastOppTerrAssignEndDate;
	public Datetime LastRunRulesEndDate;
	public String Name;
	public String State;
	public Datetime SystemModstamp;

	public AttachedContentDocument[] AttachedContentDocuments;
	public CombinedAttachment[] CombinedAttachments;
	public ContentDocumentLink[] ContentDocumentLinks;
	public EntitySubscription[] FeedSubscriptionsForEntity;
	public Territory2ModelFeed[] Feeds;
	public Territory2ModelHistory[] Histories;
	public ObjectTerritory2AssignmentRule[] ObjectTerritory2AssignmentRules;
	public Territory2[] Territory2s;

	public Territory2Model clone$() {throw new java.lang.UnsupportedOperationException();}
	public Territory2Model clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public Territory2Model clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public Territory2Model clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public Territory2Model clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
