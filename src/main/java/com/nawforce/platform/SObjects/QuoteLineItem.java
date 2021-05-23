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
public class QuoteLineItem extends SObject {
	public static SObjectType$<QuoteLineItem> SObjectType;
	public static SObjectFields$<QuoteLineItem> Fields;

	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String CurrencyIsoCode;
	public String Description;
	public Decimal Discount;
	public Boolean HasQuantitySchedule;
	public Boolean HasRevenueSchedule;
	public Boolean HasSchedule;
	public Id Id;
	public Boolean IsDeleted;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public Datetime LastReferencedDate;
	public Datetime LastViewedDate;
	public String LineNumber;
	public Decimal ListPrice;
	public Id OpportunityLineItemId;
	public OpportunityLineItem OpportunityLineItem;
	public Id PricebookEntryId;
	public PricebookEntry PricebookEntry;
	public Id Product2Id;
	public Product2 Product2;
	public Decimal Quantity;
	public Id QuoteId;
	public Quote Quote;
	public Date ServiceDate;
	public Integer SortOrder;
	public Decimal Subtotal;
	public Datetime SystemModstamp;
	public Decimal TotalPrice;
	public Decimal UnitPrice;

	public RecordActionHistory[] RecordActionHistories;
	public RecordAction[] RecordActions;

	public QuoteLineItem clone$() {throw new java.lang.UnsupportedOperationException();}
	public QuoteLineItem clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public QuoteLineItem clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public QuoteLineItem clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public QuoteLineItem clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
