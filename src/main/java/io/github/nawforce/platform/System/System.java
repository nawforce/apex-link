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

@SuppressWarnings("unused")
public class System {
	public static void abortJob(String jobId) {throw new java.lang.UnsupportedOperationException();}
	public static void Assert(Boolean condition) {throw new java.lang.UnsupportedOperationException();}
	public static void Assert(Boolean condition, Object msg) {throw new java.lang.UnsupportedOperationException();}
	public static void assertEquals(Object expected, Object actual) {throw new java.lang.UnsupportedOperationException();}
	public static void assertEquals(Object expected, Object actual, Object msg) {throw new java.lang.UnsupportedOperationException();}
	public static void assertNotEquals(Object expected, Object actual) {throw new java.lang.UnsupportedOperationException();}
	public static void assertNotEquals(Object expected, Object actual, Object msg) {throw new java.lang.UnsupportedOperationException();}
	public static void changeOwnPassword(String oldPassword, String newPassword) {throw new java.lang.UnsupportedOperationException();}
	public static PageReference currentPageReference() {throw new java.lang.UnsupportedOperationException();}
	public static Long currentTimeMillis() {throw new java.lang.UnsupportedOperationException();}
	public static void debug(Object o) {throw new java.lang.UnsupportedOperationException();}
	public static void debug(Object logLevel, Object o) {throw new java.lang.UnsupportedOperationException();}
	public static Id enqueueJob(Object queueable) {throw new java.lang.UnsupportedOperationException();}
	public static Boolean equals$(Object left, Object right) {throw new java.lang.UnsupportedOperationException();}
	public static ApplicationReadWriteMode getApplicationReadWriteMode() {throw new java.lang.UnsupportedOperationException();}
	public static Integer hashCode$(Object obj) {throw new java.lang.UnsupportedOperationException();}
	public static Boolean isBatch() {throw new java.lang.UnsupportedOperationException();}
	public static Boolean isFuture() {throw new java.lang.UnsupportedOperationException();}
	public static Boolean isQueueable() {throw new java.lang.UnsupportedOperationException();}
	public static Boolean isScheduled() {throw new java.lang.UnsupportedOperationException();}
	public static void movePassword(Id targetUserId, Id sourceUserId) {throw new java.lang.UnsupportedOperationException();}
	public static Datetime now() {throw new java.lang.UnsupportedOperationException();}
	public static List<Id> process(List workitemIds, String action, String commments, String nextApprover) {throw new java.lang.UnsupportedOperationException();}
	public static Integer purgeOldAsyncJobs(Date date) {throw new java.lang.UnsupportedOperationException();}
	public static Version requestVersion() {throw new java.lang.UnsupportedOperationException();}
	public static ResetPasswordResult resetPassword(Id userId, Boolean sendUserEmail) {throw new java.lang.UnsupportedOperationException();}
	public static ResetPasswordResult resetPasswordWithEmailTemplate(Id userId, Boolean sendUserEmail, String emailTemplateName) {throw new java.lang.UnsupportedOperationException();}
	public static void runAs(Package.Version version) {throw new java.lang.UnsupportedOperationException();}
	public static void runAs(SObject user, Object block) {throw new java.lang.UnsupportedOperationException();}
	public static String schedule(String jobName, String cronExp, Object schedulable) {throw new java.lang.UnsupportedOperationException();}
	public static String scheduleBatch(Object batchable, String jobName, Integer minutesFromNow) {throw new java.lang.UnsupportedOperationException();}
	public static String scheduleBatch(Object batchable, String jobName, Integer minutesFromNow, Integer scopeSize) {throw new java.lang.UnsupportedOperationException();}
	public static void setPassword(Id userId, String password) {throw new java.lang.UnsupportedOperationException();}
	public static List<Id> submit(List ids, String commments, String nextApprover) {throw new java.lang.UnsupportedOperationException();}
	public static Date today() {throw new java.lang.UnsupportedOperationException();}
}
