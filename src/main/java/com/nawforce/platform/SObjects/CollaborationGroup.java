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
public class CollaborationGroup extends SObject {
	public static SObjectType$<CollaborationGroup> SObjectType;
	public static SObjectFields$<CollaborationGroup> Fields;

	public Id AnnouncementId;
	public Announcement Announcement;
	public String BannerPhotoUrl;
	public Boolean CanHaveGuests;
	public String CollaborationType;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String Description;
	public String FullPhotoUrl;
	public Boolean HasPrivateFieldsAccess;
	public Id Id;
	public String InformationBody;
	public String InformationTitle;
	public Boolean IsActivityGroup;
	public Boolean IsArchived;
	public Boolean IsAutoArchiveDisabled;
	public Boolean IsBroadcast;
	public Datetime LastFeedModifiedDate;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public Datetime LastReferencedDate;
	public Datetime LastViewedDate;
	public String MediumPhotoUrl;
	public Integer MemberCount;
	public String Name;
	public Id OwnerId;
	public User Owner;
	public String SmallPhotoUrl;
	public Datetime SystemModstamp;

	public AttachedContentDocument[] AttachedContentDocuments;
	public CollaborationGroupRecord[] CollaborationGroupRecords;
	public CombinedAttachment[] CombinedAttachments;
	public ContentDocumentLink[] ContentDocumentLinks;
	public EntitySubscription[] FeedSubscriptionsForEntity;
	public CollaborationGroupFeed[] Feeds;
	public CollaborationGroupMemberRequest[] GroupMemberRequests;
	public CollaborationGroupMember[] GroupMembers;
	public RecordActionHistory[] RecordActionHistories;
	public RecordAction[] RecordActions;

	public CollaborationGroup clone$() {throw new java.lang.UnsupportedOperationException();}
	public CollaborationGroup clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public CollaborationGroup clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public CollaborationGroup clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public CollaborationGroup clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
