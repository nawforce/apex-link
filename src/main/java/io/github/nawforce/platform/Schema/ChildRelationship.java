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
package io.github.nawforce.platform.Schema;

import io.github.nawforce.platform.System.Boolean;
import io.github.nawforce.platform.System.List;
import io.github.nawforce.platform.System.String;

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
