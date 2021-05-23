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

package com.nawforce.platform.System;

import com.nawforce.platform.Auth.VerificationMethod;
import com.nawforce.platform.Auth.VerificationResult;
import com.nawforce.platform.SObjects.User;

@SuppressWarnings("unused")
public class UserManagement {
	public UserManagement() {throw new java.lang.UnsupportedOperationException();}

	public static void deregisterVerificationMethod(Id userId, VerificationMethod method) {throw new java.lang.UnsupportedOperationException();}
	public static String formatPhoneNumber(String countryCode, String phoneNumber) {throw new java.lang.UnsupportedOperationException();}
	public static String initSelfRegistration(VerificationMethod method, User u) {throw new java.lang.UnsupportedOperationException();}
	public static void obfuscateUser(Id userId) {throw new java.lang.UnsupportedOperationException();}
	public static void obfuscateUser(Id userId, String username) {throw new java.lang.UnsupportedOperationException();}
	public static PageReference registerVerificationMethod(VerificationMethod method, String startUrl) {throw new java.lang.UnsupportedOperationException();}
	public static Boolean sendAsyncEmailConfirmation(String userId, String emailTemplateId, String networkId, String startUrl) {throw new java.lang.UnsupportedOperationException();}
	public static VerificationResult verifySelfRegistration(VerificationMethod method, String identifier, String code, String startUrl) {throw new java.lang.UnsupportedOperationException();}
}
