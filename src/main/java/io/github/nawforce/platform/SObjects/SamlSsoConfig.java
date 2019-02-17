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
package io.github.nawforce.platform.SObjects;

import io.github.nawforce.platform.System.Boolean;
import io.github.nawforce.platform.System.Id;
import io.github.nawforce.platform.System.SObject;
import io.github.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class SamlSsoConfig extends SObject {
	public String AttributeFormat;
	public String AttributeName;
	public String Audience;
	public String DeveloperName;
	public String ErrorUrl;
	public Id ExecutionUserId;
	public User ExecutionUser;
	public String IdentityLocation;
	public String IdentityMapping;
	public String Issuer;
	public String Language;
	public String LoginUrl;
	public String LogoutUrl;
	public String MasterLabel;
	public String NamespacePrefix;
	public Boolean OptionsSpInitBinding;
	public Boolean OptionsUserProvisioning;
	public String RequestSignatureMethod;
	public Id SamlJitHandlerId;
	public ApexClass SamlJitHandler;
	public String SingleLogoutBinding;
	public String SingleLogoutUrl;
	public String ValidationCert;
	public String Version;
}
