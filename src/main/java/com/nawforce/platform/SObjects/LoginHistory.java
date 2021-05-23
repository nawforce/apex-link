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
public class LoginHistory extends SObject {
	public static SObjectType$<LoginHistory> SObjectType;
	public static SObjectFields$<LoginHistory> Fields;

	public String ApiType;
	public String ApiVersion;
	public String Application;
	public Id AuthenticationServiceId;
	public AuthProvider AuthenticationService;
	public String Browser;
	public String CipherSuite;
	public String ClientVersion;
	public String CountryIso;
	public Id Id;
	public Id LoginGeoId;
	public LoginGeo LoginGeo;
	public Datetime LoginTime;
	public String LoginType;
	public String LoginUrl;
	public Boolean OptionsIsGet;
	public Boolean OptionsIsPost;
	public String Platform;
	public String SourceIp;
	public String Status;
	public String TlsProtocol;
	public Id UserId;
	public User User;

	public LoginHistory clone$() {throw new java.lang.UnsupportedOperationException();}
	public LoginHistory clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public LoginHistory clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public LoginHistory clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public LoginHistory clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
