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
public class AuthProvider extends SObject {
	public static SObjectType$<AuthProvider> SObjectType;
	public static SObjectFields$<AuthProvider> Fields;

	public String AuthorizeUrl;
	public String ConsumerKey;
	public String ConsumerSecret;
	public Datetime CreatedDate;
	public String CustomMetadataTypeRecord;
	public String DefaultScopes;
	public String DeveloperName;
	public String ErrorUrl;
	public Id ExecutionUserId;
	public User ExecutionUser;
	public String FriendlyName;
	public String IconUrl;
	public Id Id;
	public String IdTokenIssuer;
	public String LinkKickoffUrl;
	public String LogoutUrl;
	public String OauthKickoffUrl;
	public Boolean OptionsIncludeOrgIdInId;
	public Boolean OptionsSendAccessTokenInHeader;
	public Boolean OptionsSendClientCredentialsInHeader;
	public Id PluginId;
	public ApexClass Plugin;
	public String ProviderType;
	public Id RegistrationHandlerId;
	public ApexClass RegistrationHandler;
	public String SsoKickoffUrl;
	public String TokenUrl;
	public String UserInfoUrl;

	public AuthProvider clone$() {throw new java.lang.UnsupportedOperationException();}
	public AuthProvider clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public AuthProvider clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public AuthProvider clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public AuthProvider clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
