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

import com.nawforce.platform.Internal.SObjectFields$;
import com.nawforce.platform.Internal.SObjectType$;
import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.String;
import com.nawforce.platform.System.*;

// To see Quote Enable on org via QuoteSettings
@SuppressWarnings("unused")
public class Quote extends SObject {
	public static SObjectType$<Quote> SObjectType;
	public static SObjectFields$<Quote> Fields;

	public Id AccountId;
	public Account Account;
	public Address AdditionalAddress;
	public String AdditionalCity;
	public String AdditionalCountry;
	public String AdditionalCountryCode;
	public String AdditionalGeocodeAccuracy;
	public Decimal AdditionalLatitude;
	public Decimal AdditionalLongitude;
	public String AdditionalName;
	public String AdditionalPostalCode;
	public String AdditionalState;
	public String AdditionalStateCode;
	public String AdditionalStreet;
	public Address BillingAddress;
	public String BillingCity;
	public String BillingCountry;
	public String BillingCountryCode;
	public String BillingGeocodeAccuracy;
	public Decimal BillingLatitude;
	public Decimal BillingLongitude;
	public String BillingName;
	public String BillingPostalCode;
	public String BillingState;
	public String BillingStateCode;
	public String BillingStreet;
	public Boolean CanCreateQuoteLineItems;
	public Id ContactId;
	public Contact Contact;
	public Id ContractId;
	public Contract Contract;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String CurrencyIsoCode;
	public String Description;
	public Decimal Discount;
	public String Email;
	public Date ExpirationDate;
	public String Fax;
	public Decimal GrandTotal;
	public Id Id;
	public Boolean IsDeleted;
	public Boolean IsSyncing;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public Datetime LastReferencedDate;
	public Datetime LastViewedDate;
	public Integer LineItemCount;
	public String Name;
	public Id OpportunityId;
	public Opportunity Opportunity;
	public Id OwnerId;
	public Group Owner;
	public String Phone;
	public Id Pricebook2Id;
	public Pricebook2 Pricebook2;
	public String QuoteNumber;
	public Address QuoteToAddress;
	public String QuoteToCity;
	public String QuoteToCountry;
	public String QuoteToCountryCode;
	public String QuoteToGeocodeAccuracy;
	public Decimal QuoteToLatitude;
	public Decimal QuoteToLongitude;
	public String QuoteToName;
	public String QuoteToPostalCode;
	public String QuoteToState;
	public String QuoteToStateCode;
	public String QuoteToStreet;
	public Address ShippingAddress;
	public String ShippingCity;
	public String ShippingCountry;
	public String ShippingCountryCode;
	public String ShippingGeocodeAccuracy;
	public Decimal ShippingHandling;
	public Decimal ShippingLatitude;
	public Decimal ShippingLongitude;
	public String ShippingName;
	public String ShippingPostalCode;
	public String ShippingState;
	public String ShippingStateCode;
	public String ShippingStreet;
	public String Status;
	public Decimal Subtotal;
	public Datetime SystemModstamp;
	public Decimal Tax;
	public Decimal TotalPrice;

	public ActivityHistory[] ActivityHistories;
	public AttachedContentDocument[] AttachedContentDocuments;
	public Attachment[] Attachments;
	public CombinedAttachment[] CombinedAttachments;
	public ContentDocumentLink[] ContentDocumentLinks;
	public EmailMessage[] Emails;
	public Event[] Events;
	public EntitySubscription[] FeedSubscriptionsForEntity;
	public QuoteFeed[] Feeds;
	public Note[] Notes;
	public NoteAndAttachment[] NotesAndAttachments;
	public OpenActivity[] OpenActivities;
	public ProcessInstance[] ProcessInstances;
	public ProcessInstanceHistory[] ProcessSteps;
	public QuoteDocument[] QuoteDocuments;
	public QuoteLineItem[] QuoteLineItems;
	public RecordActionHistory[] RecordActionHistories;
	public RecordAction[] RecordActions;
	public Task[] Tasks;

	public Quote clone$() {throw new java.lang.UnsupportedOperationException();}
	public Quote clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public Quote clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public Quote clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public Quote clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
