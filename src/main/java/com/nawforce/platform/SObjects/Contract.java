/*
 [The "BSD licence"]
 Copyright (c) 2019 Kevin Jones
 All rights reserved.

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

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.nawforce.platform.SObjects;

import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.String;
import com.nawforce.platform.System.*;

@SuppressWarnings("unused")
public class Contract extends SObject {
	public Id AccountId;
	public Account Account;
	public Id ActivatedById;
	public User ActivatedBy;
	public Datetime ActivatedDate;
	public Address BillingAddress;
	public com.nawforce.platform.System.String BillingCity;
	public com.nawforce.platform.System.String BillingCountry;
	public com.nawforce.platform.System.String BillingGeocodeAccuracy;
	public Decimal BillingLatitude;
	public Decimal BillingLongitude;
	public com.nawforce.platform.System.String BillingPostalCode;
	public com.nawforce.platform.System.String BillingState;
	public com.nawforce.platform.System.String BillingStreet;
	public Date CompanySignedDate;
	public Id CompanySignedId;
	public User CompanySigned;
	public com.nawforce.platform.System.String ContractNumber;
	public Integer ContractTerm;
	public com.nawforce.platform.System.String CurrencyIsoCode;
	public Date CustomerSignedDate;
	public Id CustomerSignedId;
	public Contact CustomerSigned;
	public com.nawforce.platform.System.String CustomerSignedTitle;
	public com.nawforce.platform.System.String Description;
	public Date EndDate;
	public Date LastActivityDate;
	public Datetime LastApprovedDate;
	public Datetime LastReferencedDate;
	public Datetime LastViewedDate;
	public com.nawforce.platform.System.String OwnerExpirationNotice;
	public Id OwnerId;
	public User Owner;
	public Id Pricebook2Id;
	public Pricebook2 Pricebook2;
	public com.nawforce.platform.System.String SpecialTerms;
	public Date StartDate;
	public com.nawforce.platform.System.String Status;
	public String StatusCode;

	public ActivityHistory ActivityHistories;
	public Approval Approvals;
	public AttachedContentDocument AttachedContentDocuments;
	public Attachment Attachments;
	public CombinedAttachment CombinedAttachments;
	public ContentDocumentLink ContentDocumentLinks;
	public ContractContactRole ContractContactRoles;
	public EmailMessage Emails;
	public Event Events;
	public EntitySubscription FeedSubscriptionsForEntity;
	public ContractFeed Feeds;
	public ContractHistory Histories;
	public Note Notes;
	public NoteAndAttachment NotesAndAttachments;
	public OpenActivity OpenActivities;
	public Order Orders;
	public ProcessInstance ProcessInstances;
	public ProcessInstanceHistory ProcessSteps;
	public RecordActionHistory RecordActionHistories;
	public RecordAction RecordActions;
	public CollaborationGroupRecord RecordAssociatedGroups;
	public Task Tasks;
	public TopicAssignment TopicAssignments;
}
