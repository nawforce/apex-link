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
public class ContentVersion extends SObject {
	public static SObjectType$<ContentVersion> SObjectType;
	public static SObjectFields$<ContentVersion> Fields;

	public String Checksum;
	public Id ContentBodyId;
	public ContentBody ContentBody;
	public Id ContentDocumentId;
	public ContentDocument ContentDocument;
	public String ContentLocation;
	public Id ContentModifiedById;
	public User ContentModifiedBy;
	public Datetime ContentModifiedDate;
	public Integer ContentSize;
	public String ContentUrl;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String CurrencyIsoCode;
	public String Description;
	public Id ExternalDataSourceId;
	public ExternalDataSource ExternalDataSource;
	public String ExternalDocumentInfo1;
	public String ExternalDocumentInfo2;
	public Integer FeaturedContentBoost;
	public Date FeaturedContentDate;
	public String FileExtension;
	public String FileType;
	public Id FirstPublishLocationId;
	public Account FirstPublishLocation;
	public Id Id;
	public Boolean IsAssetEnabled;
	public Boolean IsDeleted;
	public Boolean IsLatest;
	public Boolean IsMajorVersion;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public Integer NegativeRatingCount;
	public String Origin;
	public Id OwnerId;
	public User Owner;
	public String PathOnClient;
	public Integer PositiveRatingCount;
	public String PublishStatus;
	public Integer RatingCount;
	public String ReasonForChange;
	public String SharingOption;
	public String SharingPrivacy;
	public Datetime SystemModstamp;
	public String TagCsv;
	public String TextPreview;
	public String Title;
	public Blob VersionData;
	public String VersionNumber;

	public ContentVersionHistory[] Histories;

	public ContentVersion clone$() {throw new java.lang.UnsupportedOperationException();}
	public ContentVersion clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public ContentVersion clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public ContentVersion clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public ContentVersion clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
