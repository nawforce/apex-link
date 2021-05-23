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
public class OpportunityChangeEvent extends SObject {
	public static SObjectType$<OpportunityChangeEvent> SObjectType;
	public static SObjectFields$<OpportunityChangeEvent> Fields;

	public Id AccountId;
	public Account Account;
	public Decimal Amount;
	public Id CampaignId;
	public Campaign Campaign;
	public Object ChangeEventHeader;
	public Date CloseDate;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String CurrencyIsoCode;
	public String Description;
	public Decimal ExpectedRevenue;
	public String ForecastCategory;
	public String ForecastCategoryName;
	public Boolean HasOpportunityLineItem;
	public Id Id;
	public Boolean IsClosed;
	public Boolean IsExcludedFromTerritory2Filter;
	public Boolean IsPrivate;
	public Boolean IsSplit;
	public Boolean IsWon;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public String LeadSource;
	public String Name;
	public String NextStep;
	public Id OwnerId;
	public User Owner;
	public Id Pricebook2Id;
	public Pricebook2 Pricebook2;
	public Decimal Probability;
	public String ReplayId;
	public String StageName;
	public Id SyncedQuoteId;
	public Quote SyncedQuote;
	public Id Territory2Id;
	public Territory2 Territory2;
	public Decimal TotalOpportunityQuantity;
	public String Type;

	public OpportunityChangeEvent clone$() {throw new java.lang.UnsupportedOperationException();}
	public OpportunityChangeEvent clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public OpportunityChangeEvent clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public OpportunityChangeEvent clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public OpportunityChangeEvent clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
