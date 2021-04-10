/*
 [The "BSD licence"]
 Copyright (c) 2021 Kevin Jones
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
package com.nawforce.platform.Auth;

import com.nawforce.platform.SObjects.AuthConfig;
import com.nawforce.platform.SObjects.AuthConfigProviders;
import com.nawforce.platform.SObjects.AuthProvider;
import com.nawforce.platform.SObjects.SamlSsoConfig;
import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Id;
import com.nawforce.platform.System.List;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class AuthConfiguration {

  public AuthConfiguration(String communityOrCustomUrl, String startUrl) {throw new java.lang.UnsupportedOperationException();}
  public AuthConfiguration(Id networkId, String startUrl) {throw new java.lang.UnsupportedOperationException();}

  public Boolean getAllowInternalUserLoginEnabled() {throw new java.lang.UnsupportedOperationException();}
  public AuthConfig getAuthConfig() {throw new java.lang.UnsupportedOperationException();}
  public List<AuthConfigProviders> getAuthConfigProviders() {throw new java.lang.UnsupportedOperationException();}
  public List<AuthProvider> getAuthProviders() {throw new java.lang.UnsupportedOperationException();}
  public String getBackgroundColor() {throw new java.lang.UnsupportedOperationException();}
  public Boolean getCertificateLoginEnabled(String domainUrl) {throw new java.lang.UnsupportedOperationException();}
  public String getDefaultProfileForRegistration() {throw new java.lang.UnsupportedOperationException();}
  public String getFooterText() {throw new java.lang.UnsupportedOperationException();}
  public String getForgotPasswordUrl() {throw new java.lang.UnsupportedOperationException();}
  public String getLogoUrl() {throw new java.lang.UnsupportedOperationException();}
  public String getLoginRightFrameUrl() {throw new java.lang.UnsupportedOperationException();}
  public List<SamlSsoConfig> getSamlProviders() {throw new java.lang.UnsupportedOperationException();}
  public Boolean getSelfRegistrationEnabled() {throw new java.lang.UnsupportedOperationException();}
  public String getSelfRegistrationUrl() {throw new java.lang.UnsupportedOperationException();}
  public String getStartUrl() {throw new java.lang.UnsupportedOperationException();}
  public Boolean getUsernamePasswordEnabled() {throw new java.lang.UnsupportedOperationException();}
  public Boolean isCommunityUsingSiteAsContainer() {throw new java.lang.UnsupportedOperationException();}

  public static String getAuthProviderSsoDomainUrl(String communityUrl, String startUrl, String developerName) {throw new java.lang.UnsupportedOperationException();}
  public static String getAuthProviderSsoUrl(String communityUrl, String startUrl, String developerName) {throw new java.lang.UnsupportedOperationException();}
  public static String getCertificateLoginUrl(String domainUrl, String startUrl) {throw new java.lang.UnsupportedOperationException();}
  public static String getSamlSsoUrl(String communityUrl, String startURL, String samlId) {throw new java.lang.UnsupportedOperationException();}
}
