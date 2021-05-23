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
public class ContentHubItem extends SObject {
	public static SObjectType$<ContentHubItem> SObjectType;
	public static SObjectFields$<ContentHubItem> Fields;

	public Id ContentHubRepositoryId;
	public ContentHubRepository ContentHubRepository;
	public Datetime ContentModifiedDate;
	public Integer ContentSize;
	public Datetime CreatedDate;
	public String Description;
	public String ExternalContentUrl;
	public String ExternalDocumentUrl;
	public String ExternalId;
	public String FileExtension;
	public String FileType;
	public Id Id;
	public Boolean IsFolder;
	public Datetime LastModifiedDate;
	public String MimeType;
	public String Name;
	public String Owner;
	public String ParentId;
	public String Title;
	public String UpdatedBy;

	public ContentHubItem clone$() {throw new java.lang.UnsupportedOperationException();}
	public ContentHubItem clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public ContentHubItem clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public ContentHubItem clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public ContentHubItem clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
