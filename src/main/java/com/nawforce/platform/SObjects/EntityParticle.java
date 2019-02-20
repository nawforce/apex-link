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

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.SObject;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class EntityParticle extends SObject {
	public Integer ByteLength;
	public String DataType;
	public String DefaultValueFormula;
	public String DeveloperName;
	public Integer Digits;
	public String DurableId;
	public String EntityDefinitionId;
	public String ExtraTypeInfo;
	public String FieldDefinitionId;
	public String InlineHelpText;
	public Boolean IsApiFilterable;
	public Boolean IsApiGroupable;
	public Boolean IsApiSortable;
	public Boolean IsAutonumber;
	public Boolean IsCalculated;
	public Boolean IsCaseSensitive;
	public Boolean IsCompactLayoutable;
	public Boolean IsComponent;
	public Boolean IsCompound;
	public Boolean IsCreatable;
	public Boolean IsDefaultedOnCreate;
	public Boolean IsDependentPicklist;
	public Boolean IsDeprecatedAndHidden;
	public Boolean IsDisplayLocationInDecimal;
	public Boolean IsEncrypted;
	public Boolean IsFieldHistoryTracked;
	public Boolean IsHighScaleNumber;
	public Boolean IsHtmlFormatted;
	public Boolean IsIdLookup;
	public Boolean IsLayoutable;
	public Boolean IsListVisible;
	public Boolean IsNameField;
	public Boolean IsNamePointing;
	public Boolean IsNillable;
	public Boolean IsPermissionable;
	public Boolean IsUnique;
	public Boolean IsUpdatable;
	public Boolean IsWorkflowFilterable;
	public Boolean IsWriteRequiresMasterRead;
	public String Label;
	public Integer Length;
	public String Mask;
	public String MaskType;
	public String MasterLabel;
	public String Name;
	public String NamespacePrefix;
	public Integer Precision;
	public String QualifiedApiName;
	public String ReferenceTargetField;
	public Object ReferenceTo;
	public String RelationshipName;
	public Integer RelationshipOrder;
	public Integer Scale;
	public String ServiceDataTypeId;
	public String ValueTypeId;

	public PicklistValueInfo PicklistValues;
}
