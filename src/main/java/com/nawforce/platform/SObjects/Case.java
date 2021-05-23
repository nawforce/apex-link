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
public class Case extends SObject {
	public static SObjectType$<Case> SObjectType;
	public static SObjectFields$<Case> Fields;

	public Id RecordTypeId;
	public Id AccountId;
	public Account Account;
	public Id AssetId;
	public Asset Asset;
	public String CaseNumber;
	public Datetime ClosedDate;
	public String Comments;
	public String ContactEmail;
	public String ContactFax;
	public Id ContactId;
	public Contact Contact;
	public String ContactMobile;
	public String ContactPhone;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String CurrencyIsoCode;
	public String Description;
	public Id Id;
	public Boolean IsClosed;
	public Boolean IsDeleted;
	public Boolean IsEscalated;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public Datetime LastReferencedDate;
	public Datetime LastViewedDate;
	public String Origin;
	public Id OwnerId;
	public Group Owner;
	public Id ParentId;
	public Case Parent;
	public String Priority;
	public String Reason;
	public String Status;
	public String Subject;
	public String SuppliedCompany;
	public String SuppliedEmail;
	public String SuppliedName;
	public String SuppliedPhone;
	public Datetime SystemModstamp;
	public String Type;

	public ActivityHistory[] ActivityHistories;
	public AttachedContentDocument[] AttachedContentDocuments;
	public Attachment[] Attachments;
	public CaseComment[] CaseComments;
	public CaseContactRole[] CaseContactRoles;
	public CaseSolution[] CaseSolutions;
	public Case[] Cases;
	public CombinedAttachment[] CombinedAttachments;
	public ContactRequest[] ContactRequests;
	public ContentDocumentLink[] ContentDocumentLinks;
	public EmailMessage[] EmailMessages;
	public EmailMessage[] Emails;
	public Event[] Events;
	public EntitySubscription[] FeedSubscriptionsForEntity;
	public CaseFeed[] Feeds;
	public CaseHistory[] Histories;
	public OpenActivity[] OpenActivities;
	public ProcessInstance[] ProcessInstances;
	public ProcessInstanceHistory[] ProcessSteps;
	public RecordActionHistory[] RecordActionHistories;
	public RecordAction[] RecordActions;
	public CollaborationGroupRecord[] RecordAssociatedGroups;
	public SOSSession[] SOSSessions;
	public CaseShare[] Shares;
	public Task[] Tasks;
	public CaseTeamMember[] TeamMembers;
	public CaseTeamTemplateRecord[] TeamTemplateRecords;
	public TopicAssignment[] TopicAssignments;

	public Case clone$() {throw new java.lang.UnsupportedOperationException();}
	public Case clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public Case clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public Case clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public Case clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
