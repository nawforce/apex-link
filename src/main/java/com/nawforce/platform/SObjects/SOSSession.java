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
public class SOSSession extends SObject {
	public static SObjectType$<SOSSession> SObjectType;
	public static SObjectFields$<SOSSession> Fields;

	public String AppVersion;
	public Id CaseId;
	public Case Case;
	public Id ContactId;
	public Contact Contact;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String CurrencyIsoCode;
	public Id DeploymentId;
	public SOSDeployment Deployment;
	public Datetime EndTime;
	public Id Id;
	public String IpAddress;
	public Boolean IsDeleted;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public Datetime LastReferencedDate;
	public Datetime LastViewedDate;
	public String Name;
	public Blob OpentokSession;
	public Id OwnerId;
	public Group Owner;
	public Integer SessionDuration;
	public String SessionRecordingUrl;
	public String SessionToken;
	public String SosVersion;
	public Datetime StartTime;
	public String SystemInfo;
	public Datetime SystemModstamp;
	public Integer WaitDuration;

	public ActivityHistory[] ActivityHistories;
	public AttachedContentDocument[] AttachedContentDocuments;
	public CombinedAttachment[] CombinedAttachments;
	public ContentDocumentLink[] ContentDocumentLinks;
	public EmailMessage[] Emails;
	public Event[] Events;
	public EntitySubscription[] FeedSubscriptionsForEntity;
	public SOSSessionFeed[] Feeds;
	public SOSSessionHistory[] Histories;
	public OpenActivity[] OpenActivities;
	public SOSSessionActivity[] SOSSessionActivities;
	public Task[] Tasks;

	public SOSSession clone$() {throw new java.lang.UnsupportedOperationException();}
	public SOSSession clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public SOSSession clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public SOSSession clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public SOSSession clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
