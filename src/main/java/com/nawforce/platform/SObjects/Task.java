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
public class Task extends SObject {
	public static SObjectType$<Task> SObjectType;
	public static SObjectFields$<Task> Fields;

	public String Type;
	public Id AccountId;
	public Account Account;
	public Date ActivityDate;
	public String CallDisposition;
	public Integer CallDurationInSeconds;
	public String CallObject;
	public String CallType;
	public Datetime CompletedDateTime;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String CurrencyIsoCode;
	public String Description;
	public Id Id;
	public Boolean IsArchived;
	public Boolean IsClosed;
	public Boolean IsDeleted;
	public Boolean IsHighPriority;
	public Boolean IsRecurrence;
	public Boolean IsReminderSet;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public Id OwnerId;
	public User Owner;
	public String Priority;
	public Id RecurrenceActivityId;
	public Task RecurrenceActivity;
	public Integer RecurrenceDayOfMonth;
	public Integer RecurrenceDayOfWeekMask;
	public Date RecurrenceEndDateOnly;
	public String RecurrenceInstance;
	public Integer RecurrenceInterval;
	public String RecurrenceMonthOfYear;
	public String RecurrenceRegeneratedType;
	public Date RecurrenceStartDateOnly;
	public String RecurrenceTimeZoneSidKey;
	public String RecurrenceType;
	public Datetime ReminderDateTime;
	public String Status;
	public String Subject;
	public Datetime SystemModstamp;
	public String TaskSubtype;
	public Id WhatId;
	public Account What;
	public Id WhoId;
	public Contact Who;

	public AttachedContentDocument[] AttachedContentDocuments;
	public Attachment[] Attachments;
	public CombinedAttachment[] CombinedAttachments;
	public ContentDocumentLink[] ContentDocumentLinks;
	public EntitySubscription[] FeedSubscriptionsForEntity;
	public TaskFeed[] Feeds;
	public RecordActionHistory[] RecordActionHistories;
	public RecordAction[] RecordActions;
	public Task[] RecurringTasks;
	public TopicAssignment[] TopicAssignments;

	public Task clone$() {throw new java.lang.UnsupportedOperationException();}
	public Task clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public Task clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public Task clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public Task clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
