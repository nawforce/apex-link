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
import com.nawforce.platform.System.String;
import com.nawforce.platform.System.*;

@SuppressWarnings("unused")
public class DescribeSObjectResult {
	public Boolean Accessible;
	public List<ChildRelationship> ChildRelationships;
	public Boolean Createable;
	public Boolean Custom;
	public Boolean CustomSetting;
	public Boolean Deletable;
	public Boolean DeprecatedAndHidden;
	public Boolean FeedEnabled;
	public SObjectTypeFields Fields;
	public SObjectTypeFieldSets FieldSets;
	public Boolean HasSubtypes;
	public Boolean IsSubtype;
	public String KeyPrefix;
	public String Label;
	public String LabelPlural;
	public String LocalName;
	public Boolean Mergeable;
	public Boolean MruEnabled;
	public String Name;
	public Boolean Queryable;
	public List<RecordTypeInfo> RecordTypeInfos;
	public Map<String,RecordTypeInfo> RecordTypeInfosByDeveloperName;
	public Map<Id,RecordTypeInfo> RecordTypeInfosById;
	public Map<String,RecordTypeInfo> RecordTypeInfosByName;
	public Boolean Searchable;
	public SObjectType SObjectType;
	public Boolean Undeletable;
	public Boolean Updateable;

	public List<ChildRelationship> getChildRelationships() {throw new java.lang.UnsupportedOperationException();}
	public SObjectTypeFieldSets getFieldSets() {throw new java.lang.UnsupportedOperationException();}
	public SObjectTypeFields getFields() {throw new java.lang.UnsupportedOperationException();}
	public Boolean getHasSubtypes() {throw new java.lang.UnsupportedOperationException();}
	public Boolean getIsSubtype() {throw new java.lang.UnsupportedOperationException();}
	public String getKeyPrefix() {throw new java.lang.UnsupportedOperationException();}
	public String getLabel() {throw new java.lang.UnsupportedOperationException();}
	public String getLabelPlural() {throw new java.lang.UnsupportedOperationException();}
	public String getLocalName() {throw new java.lang.UnsupportedOperationException();}
	public String getName() {throw new java.lang.UnsupportedOperationException();}
	public List<RecordTypeInfo> getRecordTypeInfos() {throw new java.lang.UnsupportedOperationException();}
	public Map<String,RecordTypeInfo> getRecordTypeInfosByDeveloperName() {throw new java.lang.UnsupportedOperationException();}
	public Map<Id,RecordTypeInfo> getRecordTypeInfosById() {throw new java.lang.UnsupportedOperationException();}
	public Map<String,RecordTypeInfo> getRecordTypeInfosByName() {throw new java.lang.UnsupportedOperationException();}
	public SObjectType getSObjectType() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isAccessible() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isCreateable() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isCustom() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isCustomSetting() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isDeletable() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isDeprecatedAndHidden() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isFeedEnabled() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isMergeable() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isMruEnabled() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isQueryable() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isSearchable() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isUndeletable() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isUpdateable() {throw new java.lang.UnsupportedOperationException();}
}
