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
package io.github.nawforce.platform.System;

import io.github.nawforce.platform.Auth.VerificationMethod;
import io.github.nawforce.platform.SObjects.User;
import io.github.nawforce.platform.Auth.VerificationResult;

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
