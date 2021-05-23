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
public class CampaignMember extends SObject {
	public static SObjectType$<CampaignMember> SObjectType;
	public static SObjectFields$<CampaignMember> Fields;

	public Id CampaignId;
	public Campaign Campaign;
	public String City;
	public String CompanyOrAccount;
	public Id ContactId;
	public Contact Contact;
	public String Country;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String CurrencyIsoCode;
	public String Description;
	public Boolean DoNotCall;
	public String Email;
	public String Fax;
	public String FirstName;
	public Date FirstRespondedDate;
	public Boolean HasOptedOutOfEmail;
	public Boolean HasOptedOutOfFax;
	public Boolean HasResponded;
	public Id Id;
	public Boolean IsDeleted;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public String LastName;
	public Id LeadId;
	public Lead Lead;
	public Id LeadOrContactId;
	public Contact LeadOrContact;
	public Id LeadOrContactOwnerId;
	public Group LeadOrContactOwner;
	public String LeadSource;
	public String MobilePhone;
	public String Name;
	public String Phone;
	public String PostalCode;
	public String Salutation;
	public String State;
	public String Status;
	public String Street;
	public String Title;
	public String Type;

	public ListEmailIndividualRecipient[] ListEmailIndividualRecipients;
	public RecordActionHistory[] RecordActionHistories;
	public RecordAction[] RecordActions;

	public CampaignMember clone$() {throw new java.lang.UnsupportedOperationException();}
	public CampaignMember clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public CampaignMember clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public CampaignMember clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public CampaignMember clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
