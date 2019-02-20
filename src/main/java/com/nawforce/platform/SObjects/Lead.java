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

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.String;
import com.nawforce.platform.System.*;


@SuppressWarnings("unused")
public class Lead extends SObject {
	public com.nawforce.platform.System.Address Address;
	public Decimal AnnualRevenue;
	public String City;
	public String Company;
	public String CompanyDunsNumber;
	public Id ConvertedAccountId;
	public Account ConvertedAccount;
	public Id ConvertedContactId;
	public Contact ConvertedContact;
	public Date ConvertedDate;
	public Id ConvertedOpportunityId;
	public Opportunity ConvertedOpportunity;
	public String Country;
	public String CurrencyIsoCode;
	public Id DandbCompanyId;
	public DandBCompany DandbCompany;
	public String Description;
	public String Email;
	public Datetime EmailBouncedDate;
	public String EmailBouncedReason;
	public String Fax;
	public String FirstName;
	public String GeocodeAccuracy;
	public String Industry;
	public com.nawforce.platform.System.Boolean IsConverted;
	public Boolean IsUnreadByOwner;
	public String Jigsaw;
	public String JigsawContactId;
	public Date LastActivityDate;
	public String LastName;
	public Datetime LastReferencedDate;
	public Datetime LastViewedDate;
	public Decimal Latitude;
	public String LeadSource;
	public Decimal Longitude;
	public Id MasterRecordId;
	public Lead MasterRecord;
	public String MobilePhone;
	public String Name;
	public Integer NumberOfEmployees;
	public Id OwnerId;
	public Group Owner;
	public String Phone;
	public String PhotoUrl;
	public String PostalCode;
	public String Rating;
	public String Salutation;
	public String State;
	public String Status;
	public String Street;
	public String Title;
	public String Website;

	public AcceptedEventRelation AcceptedEventRelations;
	public ActivityHistory ActivityHistories;
	public AttachedContentDocument AttachedContentDocuments;
	public Attachment Attachments;
	public CampaignMember CampaignMembers;
	public CombinedAttachment CombinedAttachments;
	public ContentDocumentLink ContentDocumentLinks;
	public DeclinedEventRelation DeclinedEventRelations;
	public DuplicateRecordItem DuplicateRecordItems;
	public EmailMessageRelation EmailMessageRelations;
	public EmailStatus EmailStatuses;
	public EventRelation EventRelations;
	public Event Events;
	public EntitySubscription FeedSubscriptionsForEntity;
	public LeadFeed Feeds;
	public LeadHistory Histories;
	public ListEmailIndividualRecipient ListEmailIndividualRecipients;
	public Note Notes;
	public NoteAndAttachment NotesAndAttachments;
	public OpenActivity OpenActivities;
	public OutgoingEmailRelation OutgoingEmailRelations;
	public UserEmailPreferredPerson PersonRecord;
	public ProcessInstance ProcessInstances;
	public ProcessInstanceHistory ProcessSteps;
	public RecordActionHistory RecordActionHistories;
	public RecordAction RecordActions;
	public CollaborationGroupRecord RecordAssociatedGroups;
	public LeadShare Shares;
	public Task Tasks;
	public TopicAssignment TopicAssignments;
	public UndecidedEventRelation UndecidedEventRelations;
}
