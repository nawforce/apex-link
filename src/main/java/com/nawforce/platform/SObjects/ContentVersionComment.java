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
public class ContentVersionComment extends SObject {
	public static SObjectType$<ContentVersionComment> SObjectType;
	public static SObjectFields$<ContentVersionComment> Fields;

	public Id ContentDocumentId;
	public ContentDocument ContentDocument;
	public Id ContentVersionId;
	public ContentVersion ContentVersion;
	public Datetime CreatedDate;
	public Id Id;
	public String UserComment;

	public ContentVersionComment clone$() {throw new java.lang.UnsupportedOperationException();}
	public ContentVersionComment clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public ContentVersionComment clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public ContentVersionComment clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public ContentVersionComment clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
