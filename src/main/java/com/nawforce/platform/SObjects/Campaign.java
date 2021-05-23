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
public class Campaign extends SObject {
	public static SObjectType$<Campaign> SObjectType;
	public static SObjectFields$<Campaign> Fields;

	public Id RecordTypeId;
	public Decimal ActualCost;
	public Decimal AmountAllOpportunities;
	public Decimal AmountWonOpportunities;
	public Decimal BudgetedCost;
	public Id CampaignMemberRecordTypeId;
	public RecordType CampaignMemberRecordType;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String CurrencyIsoCode;
	public String Description;
	public Date EndDate;
	public Decimal ExpectedResponse;
	public Decimal ExpectedRevenue;
	public Id Id;
	public Boolean IsActive;
	public Boolean IsDeleted;
	public Date LastActivityDate;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public Datetime LastReferencedDate;
	public Datetime LastViewedDate;
	public String Name;
	public Integer NumberOfContacts;
	public Integer NumberOfConvertedLeads;
	public Integer NumberOfLeads;
	public Integer NumberOfOpportunities;
	public Integer NumberOfResponses;
	public Integer NumberOfWonOpportunities;
	public Decimal NumberSent;
	public Id OwnerId;
	public User Owner;
	public Id ParentId;
	public Campaign Parent;
	public Date StartDate;
	public String Status;
	public Datetime SystemModstamp;
	public String Type;

	public ActivityHistory[] ActivityHistories;
	public AttachedContentDocument[] AttachedContentDocuments;
	public Attachment[] Attachments;
	public CampaignMemberStatus[] CampaignMemberStatuses;
	public CampaignMember[] CampaignMembers;
	public Campaign[] ChildCampaigns;
	public CombinedAttachment[] CombinedAttachments;
	public ContentDocumentLink[] ContentDocumentLinks;
	public EmailMessage[] Emails;
	public Event[] Events;
	public EntitySubscription[] FeedSubscriptionsForEntity;
	public CampaignFeed[] Feeds;
	public CampaignHistory[] Histories;
	public ListEmailRecipientSource[] ListEmailRecipientSources;
	public ListEmail[] ListEmails;
	public OpenActivity[] OpenActivities;
	public Opportunity[] Opportunities;
	public ProcessInstance[] ProcessInstances;
	public ProcessInstanceHistory[] ProcessSteps;
	public RecordActionHistory[] RecordActionHistories;
	public RecordAction[] RecordActions;
	public CollaborationGroupRecord[] RecordAssociatedGroups;
	public CampaignShare[] Shares;
	public Task[] Tasks;
	public TopicAssignment[] TopicAssignments;

	public Campaign clone$() {throw new java.lang.UnsupportedOperationException();}
	public Campaign clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public Campaign clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public Campaign clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public Campaign clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
