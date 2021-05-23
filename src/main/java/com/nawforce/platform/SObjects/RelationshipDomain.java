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
import com.nawforce.platform.System.Id;
import com.nawforce.platform.System.SObject;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class RelationshipDomain extends SObject {
	public static SObjectType$<RelationshipDomain> SObjectType;
	public static SObjectFields$<RelationshipDomain> Fields;

	public String ChildSobjectId;
	public String DurableId;
	public String FieldId;
	public Id Id;
	public Boolean IsCascadeDelete;
	public Boolean IsDeprecatedAndHidden;
	public Boolean IsRestrictedDelete;
	public Object JunctionIdListNames;
	public String ParentSobjectId;
	public String RelationshipInfoId;
	public String RelationshipName;

	public RelationshipDomain clone$() {throw new java.lang.UnsupportedOperationException();}
	public RelationshipDomain clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public RelationshipDomain clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public RelationshipDomain clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public RelationshipDomain clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
