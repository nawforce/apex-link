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
public class Opportunity extends SObject {
	public static SObjectType$<Opportunity> SObjectType;
	public static SObjectFields$<Opportunity> Fields;

	public Id RecordTypeId;
	public Id AccountId;
	public Account Account;
	public Decimal Amount;
	public Id CampaignId;
	public Campaign Campaign;
	public Date CloseDate;
	public Id ContractId;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String CurrencyIsoCode;
	public String Description;
	public Decimal ExpectedRevenue;
	public String Fiscal;
	public Integer FiscalQuarter;
	public Integer FiscalYear;
	public String ForecastCategory;
	public String ForecastCategoryName;
	public Boolean HasOpenActivity;
	public Boolean HasOpportunityLineItem;
	public Boolean HasOverdueTask;
	public Id Id;
	public Boolean IsClosed;
	public Boolean IsDeleted;
	public Boolean IsExcludedFromTerritory2Filter;
	public Boolean IsPrivate;
	public Boolean IsSplit;
	public Boolean IsWon;
	public Date LastActivityDate;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public Datetime LastReferencedDate;
	public Datetime LastViewedDate;
	public String LeadSource;
	public String Name;
	public String NextStep;
	public Id OwnerId;
	public User Owner;
	public Id Pricebook2Id;
	public Pricebook2 Pricebook2;
	public Decimal Probability;
	public String StageName;
	public Id SyncedQuoteId;
	public Quote SyncedQuote;
	public Datetime SystemModstamp;
	public Id Territory2Id;
	public Territory2 Territory2;
	public Decimal TotalOpportunityQuantity;
	public String Type;

	public AccountPartner[] AccountPartners;
	public ActivityHistory[] ActivityHistories;
	public AttachedContentDocument[] AttachedContentDocuments;
	public Attachment[] Attachments;
	public CombinedAttachment[] CombinedAttachments;
	public ContactRequest[] ContactRequests;
	public ContentDocumentLink[] ContentDocumentLinks;
	public EmailMessage[] Emails;
	public Event[] Events;
	public EntitySubscription[] FeedSubscriptionsForEntity;
	public OpportunityFeed[] Feeds;
	public OpportunityFieldHistory[] Histories;
	public Note[] Notes;
	public NoteAndAttachment[] NotesAndAttachments;
	public OpenActivity[] OpenActivities;
	public OpportunityCompetitor[] OpportunityCompetitors;
	public OpportunityContactRole[] OpportunityContactRoles;
	public OpportunityHistory[] OpportunityHistories;
	public OpportunityLineItem[] OpportunityLineItems;
	public OpportunityPartner[] OpportunityPartnersFrom;
	public OpportunitySplit[] OpportunitySplits;
	public OpportunityTeamMember[] OpportunityTeamMembers;
	public Partner[] Partners;
	public ProcessInstance[] ProcessInstances;
	public ProcessInstanceHistory[] ProcessSteps;
	public Quote[] Quotes;
	public RecordActionHistory[] RecordActionHistories;
	public RecordAction[] RecordActions;
	public CollaborationGroupRecord[] RecordAssociatedGroups;
	public OpportunityShare[] Shares;
	public Task[] Tasks;
	public TopicAssignment[] TopicAssignments;

	public Opportunity clone$() {throw new java.lang.UnsupportedOperationException();}
	public Opportunity clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public Opportunity clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public Opportunity clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public Opportunity clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
