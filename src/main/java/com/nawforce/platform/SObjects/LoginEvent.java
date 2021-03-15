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
package com.nawforce.platform.SObjects;

import com.nawforce.platform.Internal.SObjectFields$;
import com.nawforce.platform.Internal.SObjectType$;
import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.String;
import com.nawforce.platform.System.*;


@SuppressWarnings("unused")
public class LoginEvent extends SObject {
	public static SObjectType$<LoginEvent> SObjectType;
	public static SObjectFields$<LoginEvent> Fields;
	public String AdditionalInfo;
	public String ApiType;
	public String ApiVersion;
	public String Application;
	public Id AuthServiceId;
	public SObject AuthService;
	public String Browser;
	public String CipherSuite;
	public String City;
	public String ClientVersion;
	public String Country;
	public String CountryIso;
	public Datetime CreatedDate;
	public Decimal EvaluationTime;
	public Datetime EventDate;
	public String EventIdentifier;
	public String HttpMethod;
	public Id Id;
	public Id LoginGeoId;
	public SObject LoginGeo;
	public Id LoginHistoryId;
	public SObject LoginHistory;
	public String LoginKey;
	public Decimal LoginLatitude;
	public Decimal LoginLongitude;
	public String LoginType;
	public String LoginUrl;
	public String Platform;
	public Id PolicyId;
	public TransactionSecurityPolicy Policy;
	public String PolicyOutcome;
	public String PostalCode;
	public String RelatedEventIdentifier;
	public String SessionKey;
	public String SessionLevel;
	public String SourceIp;
	public String Status;
	public String Subdivision;
	public String TlsProtocol;
	public Id UserId;
	public User User;
	public String UserType;
	public String Username;

	public LoginEvent clone$() {throw new java.lang.UnsupportedOperationException();}
	public LoginEvent clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public LoginEvent clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public LoginEvent clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public LoginEvent clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
