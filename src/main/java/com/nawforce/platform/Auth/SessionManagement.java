/*
 Copyright (c) 2021 Kevin Jones, All rights reserved.
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

package com.nawforce.platform.Auth;

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.String;
import com.nawforce.platform.System.*;

@SuppressWarnings("unused")
public class SessionManagement {
  public static PageReference finishLoginDiscovery(LoginDiscoveryMethod method, Id userId) {throw new java.lang.UnsupportedOperationException();}
  public static PageReference finishLoginFlow() {throw new java.lang.UnsupportedOperationException();}
  public static PageReference finishLoginFlow(String startUrl) {throw new java.lang.UnsupportedOperationException();}
  public static String generateVerificationUrl(VerificationPolicy policy, String description, String destinationUrl) {throw new java.lang.UnsupportedOperationException();}
  public static Map<String, String> getCurrentSession() {throw new java.lang.UnsupportedOperationException();}
  public static LightningLoginEligibility getLightningLoginEligibility(Id userId) {throw new java.lang.UnsupportedOperationException();}
  public static Map<String, String> getQrCode() {throw new java.lang.UnsupportedOperationException();}
  public static SessionLevel getRequiredSessionLevelForProfile(String profileId) {throw new java.lang.UnsupportedOperationException();}
  public static Map<String,String> ignoreForConcurrentSessionLimit(Object sessions) {throw new java.lang.UnsupportedOperationException();}
  public static Boolean inOrgNetworkRange(String ipAddress) {throw new java.lang.UnsupportedOperationException();}
  public static Boolean isIpAllowedForProfile(String profileId, String ipAddress) {throw new java.lang.UnsupportedOperationException();}
  public static void setSessionLevel(SessionLevel level) {throw new java.lang.UnsupportedOperationException();}
  public static Boolean validateTotpTokenForKey(String sharedKey, String totpCode) {throw new java.lang.UnsupportedOperationException();}
  public static Boolean validateTotpTokenForKey(String totpSharedKey, String totpCode, String description) {throw new java.lang.UnsupportedOperationException();}
  public static Boolean validateTotpTokenForUser(String totpCode) {throw new java.lang.UnsupportedOperationException();}
  public static Boolean validateTotpTokenForUser(String totpCode, String description) {throw new java.lang.UnsupportedOperationException();}
  public static PageReference verifyDeviceFlow(String userCode, String startUrl) {throw new java.lang.UnsupportedOperationException();}
}
