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
package com.nawforce.platform.Schema;

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.List;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class DescribeFieldResult {
	public Boolean Accessible;
	public Boolean Aggregatable;
	public Boolean AiPredictionField;
	public Boolean AutoNumber;
	public Integer ByteLength;
	public Boolean Calculated;
	public String CalculatedFormula;
	public Boolean CascadeDelete;
	public Boolean CaseSensitive;
	public String CompoundFieldName;
	public SObjectField Controller;
	public Boolean Createable;
	public Boolean Custom;
	public Boolean DefaultedOnCreate;
	public Object DefaultValue;
	public String DefaultValueFormula;
	public Boolean DependentPicklist;
	public Boolean DeprecatedAndHidden;
	public Integer Digits;
	public Boolean DisplayLocationInDecimal;
	public Boolean Encrypted;
	public Boolean ExternalId;
	public Boolean Filterable;
	public FilteredLookupInfo FilteredLookupInfo;
	public Boolean FormulaTreatNullNumberAsZero;
	public Boolean Groupable;
	public Boolean HighScaleNumber;
	public Boolean HtmlFormatted;
	public Boolean IdLookup;
	public String InlineHelpText;
	public String Label;
	public Integer Length;
	public String LocalName;
	public String Mask;
	public String MaskType;
	public String Name;
	public Boolean NameField;
	public Boolean NamePointing;
	public Boolean Nillable;
	public Boolean Permissionable;
	public List<PicklistEntry> PicklistValues;
	public Integer Precision;
	public Boolean QueryByDistance;
	public String ReferenceTargetField;
	public List<SObjectType> ReferenceTo;
	public String RelationshipName;
	public Integer RelationshipOrder;
	public Boolean RestrictedDelete;
	public Boolean RestrictedPicklist;
	public Integer Scale;
	public Boolean SearchPrefilterable;
	public SoapType SoapType;
	public SObjectField SobjectField;
	public Boolean Sortable;
	public DisplayType Type;
	public Boolean Unique;
	public Boolean Updateable;
	public Boolean WriteRequiresMasterRead;

	public Integer getByteLength() {throw new java.lang.UnsupportedOperationException();}
	public String getCalculatedFormula() {throw new java.lang.UnsupportedOperationException();}
	public String getCompoundFieldName() {throw new java.lang.UnsupportedOperationException();}
	public SObjectField getController() {throw new java.lang.UnsupportedOperationException();}
	public Object getDefaultValue() {throw new java.lang.UnsupportedOperationException();}
	public String getDefaultValueFormula() {throw new java.lang.UnsupportedOperationException();}
	public Integer getDigits() {throw new java.lang.UnsupportedOperationException();}
	public FilteredLookupInfo getFilteredLookupInfo() {throw new java.lang.UnsupportedOperationException();}
	public String getInlineHelpText() {throw new java.lang.UnsupportedOperationException();}
	public String getLabel() {throw new java.lang.UnsupportedOperationException();}
	public Integer getLength() {throw new java.lang.UnsupportedOperationException();}
	public String getLocalName() {throw new java.lang.UnsupportedOperationException();}
	public String getMask() {throw new java.lang.UnsupportedOperationException();}
	public String getMaskType() {throw new java.lang.UnsupportedOperationException();}
	public String getName() {throw new java.lang.UnsupportedOperationException();}
	public List<PicklistEntry> getPicklistValues() {throw new java.lang.UnsupportedOperationException();}
	public Integer getPrecision() {throw new java.lang.UnsupportedOperationException();}
	public String getReferenceTargetField() {throw new java.lang.UnsupportedOperationException();}
	public List<SObjectType> getReferenceTo() {throw new java.lang.UnsupportedOperationException();}
	public String getRelationshipName() {throw new java.lang.UnsupportedOperationException();}
	public Integer getRelationshipOrder() {throw new java.lang.UnsupportedOperationException();}
	public Integer getScale() {throw new java.lang.UnsupportedOperationException();}
	public SoapType getSoapType() {throw new java.lang.UnsupportedOperationException();}
	public SObjectField getSobjectField() {throw new java.lang.UnsupportedOperationException();}
	public DisplayType getType() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isAccessible() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isAggregatable() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isAiPredictionField() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isAutoNumber() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isCalculated() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isCascadeDelete() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isCaseSensitive() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isCreateable() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isCustom() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isDefaultedOnCreate() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isDependentPicklist() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isDeprecatedAndHidden() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isDisplayLocationInDecimal() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isEncrypted() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isExternalId() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isFilterable() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isFormulaTreatNullNumberAsZero() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isGroupable() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isHighScaleNumber() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isHtmlFormatted() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isIdLookup() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isNameField() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isNamePointing() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isNillable() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isPermissionable() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isQueryByDistance() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isRestrictedDelete() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isRestrictedPicklist() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isSearchPrefilterable() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isSortable() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isUnique() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isUpdateable() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isWriteRequiresMasterRead() {throw new java.lang.UnsupportedOperationException();}
}
