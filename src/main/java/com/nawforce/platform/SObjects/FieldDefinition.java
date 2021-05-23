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
public class FieldDefinition extends SObject {
	public static SObjectType$<FieldDefinition> SObjectType;
	public static SObjectFields$<FieldDefinition> Fields;

	public Id BusinessOwnerId;
	public Group BusinessOwner;
	public String BusinessStatus;
	public String ControllingFieldDefinitionId;
	public String DataType;
	public String Description;
	public String DeveloperName;
	public String DurableId;
	public String EntityDefinitionId;
	public EntityDefinition EntityDefinition;
	public String ExtraTypeInfo;
	public Id Id;
	public Boolean IsAiPredictionField;
	public Boolean IsApiFilterable;
	public Boolean IsApiGroupable;
	public Boolean IsApiSortable;
	public Boolean IsCalculated;
	public Boolean IsCompactLayoutable;
	public Boolean IsCompound;
	public Boolean IsFieldHistoryTracked;
	public Boolean IsHighScaleNumber;
	public Boolean IsHtmlFormatted;
	public Boolean IsIndexed;
	public Boolean IsListFilterable;
	public Boolean IsListSortable;
	public Boolean IsListVisible;
	public Boolean IsNameField;
	public Boolean IsNillable;
	public Boolean IsPolymorphicForeignKey;
	public Boolean IsSearchPrefilterable;
	public Boolean IsWorkflowFilterable;
	public String Label;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public Integer Length;
	public String MasterLabel;
	public String NamespacePrefix;
	public Integer Precision;
	public String PublisherId;
	public String QualifiedApiName;
	public String ReferenceTargetField;
	public Object ReferenceTo;
	public String RelationshipName;
	public String RunningUserFieldAccessId;
	public Integer Scale;
	public String SecurityClassification;
	public String ServiceDataTypeId;
	public String ValueTypeId;

	public FieldDefinition clone$() {throw new java.lang.UnsupportedOperationException();}
	public FieldDefinition clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public FieldDefinition clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public FieldDefinition clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public FieldDefinition clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
