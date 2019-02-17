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

@SuppressWarnings("unused")
public class Site {
	public Site() {throw new java.lang.UnsupportedOperationException();}

	public static PageReference changePassword(String newPassword, String verifyNewPassword) {throw new java.lang.UnsupportedOperationException();}
	public static PageReference changePassword(String newPassword, String verifyNewPassword, String oldPassword) {throw new java.lang.UnsupportedOperationException();}
	public static Id createExternalUser(SObject user, String accountId) {throw new java.lang.UnsupportedOperationException();}
	public static Id createExternalUser(SObject user, String accountId, String password) {throw new java.lang.UnsupportedOperationException();}
	public static Id createExternalUser(SObject user, String accountId, String password, Boolean sendEmailConfirmation) {throw new java.lang.UnsupportedOperationException();}
	public static Id createPersonAccountPortalUser(SObject user, String ownerId, String password) {throw new java.lang.UnsupportedOperationException();}
	public static Id createPersonAccountPortalUser(SObject user, String ownerId, String recordTypeId, String password) {throw new java.lang.UnsupportedOperationException();}
	public static Id createPortalUser(SObject user, String accountId) {throw new java.lang.UnsupportedOperationException();}
	public static Id createPortalUser(SObject user, String accountId, String password) {throw new java.lang.UnsupportedOperationException();}
	public static Id createPortalUser(SObject user, String accountId, String password, Boolean sendEmailConfirmation) {throw new java.lang.UnsupportedOperationException();}
	public static Boolean forgotPassword(String username) {throw new java.lang.UnsupportedOperationException();}
	public static Boolean forgotPassword(String username, String emailTemplateName) {throw new java.lang.UnsupportedOperationException();}
	public static String getAdminEmail() {throw new java.lang.UnsupportedOperationException();}
	public static Id getAdminId() {throw new java.lang.UnsupportedOperationException();}
	public static String getAnalyticsTrackingCode() {throw new java.lang.UnsupportedOperationException();}
	public static String getBaseCustomUrl() {throw new java.lang.UnsupportedOperationException();}
	public static String getBaseInsecureUrl() {throw new java.lang.UnsupportedOperationException();}
	public static String getBaseRequestUrl() {throw new java.lang.UnsupportedOperationException();}
	public static String getBaseSecureUrl() {throw new java.lang.UnsupportedOperationException();}
	public static String getBaseUrl() {throw new java.lang.UnsupportedOperationException();}
	public static String getCurrentSiteUrl() {throw new java.lang.UnsupportedOperationException();}
	public static String getCustomWebAddress() {throw new java.lang.UnsupportedOperationException();}
	public static String getDomain() {throw new java.lang.UnsupportedOperationException();}
	public static String getErrorDescription() {throw new java.lang.UnsupportedOperationException();}
	public static String getErrorMessage() {throw new java.lang.UnsupportedOperationException();}
	public static String getExperienceId() {throw new java.lang.UnsupportedOperationException();}
	public static String getMasterLabel() {throw new java.lang.UnsupportedOperationException();}
	public static String getName() {throw new java.lang.UnsupportedOperationException();}
	public static String getOriginalUrl() {throw new java.lang.UnsupportedOperationException();}
	public static String getPasswordPolicyStatement() {throw new java.lang.UnsupportedOperationException();}
	public static String getPathPrefix() {throw new java.lang.UnsupportedOperationException();}
	public static String getPrefix() {throw new java.lang.UnsupportedOperationException();}
	public static Id getSiteId() {throw new java.lang.UnsupportedOperationException();}
	public static String getSiteType() {throw new java.lang.UnsupportedOperationException();}
	public static String getSiteTypeLabel() {throw new java.lang.UnsupportedOperationException();}
	public static PageReference getTemplate() {throw new java.lang.UnsupportedOperationException();}
	public static Boolean isLoginEnabled() {throw new java.lang.UnsupportedOperationException();}
	public static Boolean isPasswordExpired() {throw new java.lang.UnsupportedOperationException();}
	public static Boolean isRegistrationEnabled() {throw new java.lang.UnsupportedOperationException();}
	public static Boolean isValidUsername(String username) {throw new java.lang.UnsupportedOperationException();}
	public static PageReference login(String username, String password, String startUrl) {throw new java.lang.UnsupportedOperationException();}
	public static PageReference passwordlessLogin(Id userId, List<VerificationMethod> methods, String startUrl) {throw new java.lang.UnsupportedOperationException();}
	public static void setExperienceId(String expIdValue) {throw new java.lang.UnsupportedOperationException();}
	public static void setPortalUserAsAuthProvider(SObject user, String accountId) {throw new java.lang.UnsupportedOperationException();}
	public static void validatePassword(SObject user, String password, String confirmPassword) {throw new java.lang.UnsupportedOperationException();}
}
