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

package com.nawforce.platform.Schema;

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.List;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class ChildRelationship {
	public Boolean CascadeDelete;
	public SObjectType ChildSObject;
	public Boolean DeprecatedAndHidden;
	public SObjectField Field;
	public List<String> JunctionIdListNames;
	public List<SObjectType> JunctionReferenceTo;
	public String RelationshipName;
	public Boolean RestrictedDelete;

	public SObjectType getChildSObject() {throw new java.lang.UnsupportedOperationException();}
	public SObjectField getField() {throw new java.lang.UnsupportedOperationException();}
	public List<String> getJunctionIdListNames() {throw new java.lang.UnsupportedOperationException();}
	public List<SObjectType> getJunctionReferenceTo() {throw new java.lang.UnsupportedOperationException();}
	public String getRelationshipName() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isCascadeDelete() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isDeprecatedAndHidden() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isRestrictedDelete() {throw new java.lang.UnsupportedOperationException();}
}
