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

package com.nawforce.platform.applauncher;

import com.nawforce.platform.SObjects.AuthProvider;
import com.nawforce.platform.SObjects.SamlSsoConfig;
import com.nawforce.platform.System.List;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class SocialLoginController {
	public SocialLoginController() {throw new java.lang.UnsupportedOperationException();}

	public static List<AuthProvider> getAuthProviders() {throw new java.lang.UnsupportedOperationException();}
	public static List<SamlSsoConfig> getSamlProviders() {throw new java.lang.UnsupportedOperationException();}
	public static String getSamlSsoUrl(String startUrl, String samlId) {throw new java.lang.UnsupportedOperationException();}
	public static String getSsoUrl(String startUrl, String developerName) {throw new java.lang.UnsupportedOperationException();}
	public static String handleIdp() {throw new java.lang.UnsupportedOperationException();}
}
