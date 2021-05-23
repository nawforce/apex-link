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
public class SamlSsoConfig extends SObject {
	public static SObjectType$<SamlSsoConfig> SObjectType;
	public static SObjectFields$<SamlSsoConfig> Fields;

	public String AttributeFormat;
	public String AttributeName;
	public String Audience;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String DeveloperName;
	public String ErrorUrl;
	public Id ExecutionUserId;
	public User ExecutionUser;
	public Id Id;
	public String IdentityLocation;
	public String IdentityMapping;
	public Boolean IsDeleted;
	public String Issuer;
	public String Language;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
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
	public Datetime SystemModstamp;
	public String ValidationCert;
	public String Version;

	public SamlSsoConfig clone$() {throw new java.lang.UnsupportedOperationException();}
	public SamlSsoConfig clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public SamlSsoConfig clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public SamlSsoConfig clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public SamlSsoConfig clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
