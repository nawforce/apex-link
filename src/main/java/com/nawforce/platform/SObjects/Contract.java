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
public class Contract extends SObject {
	public static SObjectType$<Contract> SObjectType;
	public static SObjectFields$<Contract> Fields;

	public Id AccountId;
	public Account Account;
	public Id ActivatedById;
	public User ActivatedBy;
	public Datetime ActivatedDate;
	public Address BillingAddress;
	public String BillingCity;
	public String BillingCountry;
	public String BillingCountryCode;
	public String BillingGeocodeAccuracy;
	public Decimal BillingLatitude;
	public Decimal BillingLongitude;
	public String BillingPostalCode;
	public String BillingState;
	public String BillingStateCode;
	public String BillingStreet;
	public Date CompanySignedDate;
	public Id CompanySignedId;
	public User CompanySigned;
	public String ContractNumber;
	public Integer ContractTerm;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String CurrencyIsoCode;
	public Date CustomerSignedDate;
	public Id CustomerSignedId;
	public Contact CustomerSigned;
	public String CustomerSignedTitle;
	public String Description;
	public Date EndDate;
	public Id Id;
	public Boolean IsDeleted;
	public Date LastActivityDate;
	public Datetime LastApprovedDate;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public Datetime LastReferencedDate;
	public Datetime LastViewedDate;
	public String OwnerExpirationNotice;
	public Id OwnerId;
	public User Owner;
	public Id Pricebook2Id;
	public Pricebook2 Pricebook2;
	public String SpecialTerms;
	public Date StartDate;
	public String Status;
	public String StatusCode;
	public Datetime SystemModstamp;

	public ActivityHistory[] ActivityHistories;
	public Approval[] Approvals;
	public AttachedContentDocument[] AttachedContentDocuments;
	public Attachment[] Attachments;
	public CombinedAttachment[] CombinedAttachments;
	public ContentDocumentLink[] ContentDocumentLinks;
	public ContractContactRole[] ContractContactRoles;
	public EmailMessage[] Emails;
	public Event[] Events;
	public EntitySubscription[] FeedSubscriptionsForEntity;
	public ContractFeed[] Feeds;
	public ContractHistory[] Histories;
	public Note[] Notes;
	public NoteAndAttachment[] NotesAndAttachments;
	public OpenActivity[] OpenActivities;
	public Order[] Orders;
	public ProcessInstance[] ProcessInstances;
	public ProcessInstanceHistory[] ProcessSteps;
	public Quote[] Quotes;
	public RecordActionHistory[] RecordActionHistories;
	public RecordAction[] RecordActions;
	public CollaborationGroupRecord[] RecordAssociatedGroups;
	public Task[] Tasks;
	public TopicAssignment[] TopicAssignments;

	public Contract clone$() {throw new java.lang.UnsupportedOperationException();}
	public Contract clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public Contract clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public Contract clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public Contract clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
