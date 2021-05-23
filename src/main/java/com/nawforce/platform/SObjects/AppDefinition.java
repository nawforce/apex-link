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
public class AppDefinition extends SObject {
	public static SObjectType$<AppDefinition> SObjectType;
	public static SObjectFields$<AppDefinition> Fields;

	public String Description;
	public String DeveloperName;
	public String DurableId;
	public String HeaderColor;
	public Id Id;
	public Boolean IsLargeFormFactorSupported;
	public Boolean IsMediumFormFactorSupported;
	public Boolean IsNavAutoTempTabsDisabled;
	public Boolean IsNavPersonalizationDisabled;
	public Boolean IsOverrideOrgTheme;
	public Boolean IsSmallFormFactorSupported;
	public String Label;
	public String LogoUrl;
	public String MasterLabel;
	public String NamespacePrefix;
	public String NavType;
	public String UiType;
	public String UtilityBar;

	public AppTabMember[] AppTabs;

	public AppDefinition clone$() {throw new java.lang.UnsupportedOperationException();}
	public AppDefinition clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public AppDefinition clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public AppDefinition clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public AppDefinition clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
