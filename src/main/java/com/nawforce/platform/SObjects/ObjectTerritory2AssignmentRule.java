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
public class ObjectTerritory2AssignmentRule extends SObject {
	public static SObjectType$<ObjectTerritory2AssignmentRule> SObjectType;
	public static SObjectFields$<ObjectTerritory2AssignmentRule> Fields;

	public String BooleanFilter;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String DeveloperName;
	public Id Id;
	public Boolean IsActive;
	public Boolean IsDeleted;
	public String Language;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public String MasterLabel;
	public String ObjectType;
	public Datetime SystemModstamp;
	public Id Territory2ModelId;
	public Territory2Model Territory2Model;

	public ObjectTerritory2AssignmentRuleItem[] ObjectTerritory2AssignmentRuleItems;
	public RuleTerritory2Association[] RuleTerritory2Associations;

	public ObjectTerritory2AssignmentRule clone$() {throw new java.lang.UnsupportedOperationException();}
	public ObjectTerritory2AssignmentRule clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public ObjectTerritory2AssignmentRule clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public ObjectTerritory2AssignmentRule clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public ObjectTerritory2AssignmentRule clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
