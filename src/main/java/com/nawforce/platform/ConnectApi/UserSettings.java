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

package com.nawforce.platform.ConnectApi;

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Double;
import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.String;
import com.nawforce.platform.System.*;

@SuppressWarnings("unused")
public class UserSettings {
	public Boolean approvalPosts;
	public Boolean canAccessPersonalStreams;
	public Boolean canFollow;
	public Boolean canModifyAllData;
	public Boolean canOwnGroups;
	public Boolean canViewAllData;
	public Boolean canViewAllGroups;
	public Boolean canViewAllUsers;
	public Boolean canViewCommunitySwitcher;
	public Boolean canViewFullUserProfile;
	public Boolean canViewPublicFiles;
	public String currencySymbol;
	public Boolean externalUser;
	public Integer fileSyncLimit;
	public Integer fileSyncStorageLimit;
	public Boolean folderSyncLimit;
	public Boolean hasAccessToInternalOrg;
	public Boolean hasChatter;
	public Boolean hasFieldServiceLocationTracking;
	public Boolean hasFieldServiceMobileAccess;
	public Boolean hasFileSync;
	public Boolean hasFileSyncManagedClientAutoUpdate;
	public Boolean hasRestDataApiAccess;
	public TimeZone timeZone;
	public String userDefaultCurrencyIsoCode;
	public String userId;
	public String userLocale;

	public UserSettings() {throw new java.lang.UnsupportedOperationException();}

	public Boolean equals$(Object obj) {throw new java.lang.UnsupportedOperationException();}
	public Double getBuildVersion() {throw new java.lang.UnsupportedOperationException();}
	public Integer hashCode$() {throw new java.lang.UnsupportedOperationException();}
	public String toString$() {throw new java.lang.UnsupportedOperationException();}
}
