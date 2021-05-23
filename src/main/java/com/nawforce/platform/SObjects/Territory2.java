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
public class Territory2 extends SObject {
	public static SObjectType$<Territory2> SObjectType;
	public static SObjectFields$<Territory2> Fields;

	public String AccountAccessLevel;
	public String CaseAccessLevel;
	public String ContactAccessLevel;
	public String CurrencyIsoCode;
	public String Description;
	public String DeveloperName;
	public Id ForecastUserId;
	public User ForecastUser;
	public Id Id;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public String Name;
	public String OpportunityAccessLevel;
	public Id ParentTerritory2Id;
	public Territory2 ParentTerritory2;
	public Datetime SystemModstamp;
	public Id Territory2ModelId;
	public Territory2Model Territory2Model;
	public Id Territory2TypeId;
	public Territory2Type Territory2Type;

	public AccountUserTerritory2View[] AccountUserTerritory2Views;
	public ObjectTerritory2Association[] ObjectTerritory2Associations;
	public RuleTerritory2Association[] RuleTerritory2Associations;
	public UserTerritory2Association[] UserTerritory2Associations;

	public Territory2 clone$() {throw new java.lang.UnsupportedOperationException();}
	public Territory2 clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public Territory2 clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public Territory2 clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public Territory2 clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
