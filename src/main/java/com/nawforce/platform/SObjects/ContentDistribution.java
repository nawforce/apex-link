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
public class ContentDistribution extends SObject {
	public static SObjectType$<ContentDistribution> SObjectType;
	public static SObjectFields$<ContentDistribution> Fields;

	public Id ContentDocumentId;
	public ContentDocument ContentDocument;
	public String ContentDownloadUrl;
	public Id ContentVersionId;
	public ContentVersion ContentVersion;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String DistributionPublicUrl;
	public Datetime ExpiryDate;
	public Datetime FirstViewDate;
	public Id Id;
	public Boolean IsDeleted;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public Datetime LastViewDate;
	public String Name;
	public Id OwnerId;
	public User Owner;
	public String Password;
	public String PdfDownloadUrl;
	public Boolean PreferencesAllowOriginalDownload;
	public Boolean PreferencesAllowPDFDownload;
	public Boolean PreferencesAllowViewInBrowser;
	public Boolean PreferencesExpires;
	public Boolean PreferencesLinkLatestVersion;
	public Boolean PreferencesNotifyOnVisit;
	public Boolean PreferencesNotifyRndtnComplete;
	public Boolean PreferencesPasswordRequired;
	public Id RelatedRecordId;
	public Account RelatedRecord;
	public Datetime SystemModstamp;
	public Integer ViewCount;

	public ContentDistributionView[] ContentDistributionViews;

	public ContentDistribution clone$() {throw new java.lang.UnsupportedOperationException();}
	public ContentDistribution clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public ContentDistribution clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public ContentDistribution clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public ContentDistribution clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
