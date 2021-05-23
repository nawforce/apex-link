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

import com.nawforce.platform.QuickAction.SendEmailQuickActionDefaults;
import com.nawforce.platform.Schema.SObjectType;
import com.nawforce.platform.eventbus.TestBroker;

@SuppressWarnings("unused")
public class Test {
	public Test() {throw new java.lang.UnsupportedOperationException();}

	public static void clearApexPageMessages() {throw new java.lang.UnsupportedOperationException();}
	public static Object createStub(Type parentType, StubProvider stubProvider) {throw new java.lang.UnsupportedOperationException();}
	public static List<Id> enqueueBatchJobs(Integer n) {throw new java.lang.UnsupportedOperationException();}
	public static TestBroker getEventBus() {throw new java.lang.UnsupportedOperationException();}
	public static List<Id> getFlexQueueOrder() {throw new java.lang.UnsupportedOperationException();}
	public static Id getStandardPricebookId() {throw new java.lang.UnsupportedOperationException();}
	public static Object invokeContinuationMethod(Object controller, Continuation continuation) {throw new java.lang.UnsupportedOperationException();}
	public static Boolean isRunningTest() {throw new java.lang.UnsupportedOperationException();}
	public static List<SObject> loadData(SObjectType sobjectType, String staticResourceName) {throw new java.lang.UnsupportedOperationException();}
	public static SendEmailQuickActionDefaults newSendEmailQuickActionDefaults(Id contextId, Id replyToId) {throw new java.lang.UnsupportedOperationException();}
	public static void setContinuationResponse(String label, HttpResponse response) {throw new java.lang.UnsupportedOperationException();}
	public static void setCreatedDate(Id id, Datetime dt) {throw new java.lang.UnsupportedOperationException();}
	public static void setCurrentPage(Object pageReference) {throw new java.lang.UnsupportedOperationException();}
	public static void setCurrentPageReference(Object pageReference) {throw new java.lang.UnsupportedOperationException();}
	public static void setFixedSearchResults(List<String> searchResultsIds) {throw new java.lang.UnsupportedOperationException();}
	public static void setMock(Type interfaceType, Object mock) {throw new java.lang.UnsupportedOperationException();}
	public static void setReadOnlyApplicationMode(Boolean readOnlyApplicationMode) {throw new java.lang.UnsupportedOperationException();}
	public static void startTest() {throw new java.lang.UnsupportedOperationException();}
	public static void stopTest() {throw new java.lang.UnsupportedOperationException();}
	public static void testInstall(InstallHandler script, Version version) {throw new java.lang.UnsupportedOperationException();}
	public static void testInstall(InstallHandler script, Version version, Boolean isPush) {throw new java.lang.UnsupportedOperationException();}
	public static void testSandboxPostCopyScript(SandboxPostCopy script, Id organizationId, Id sandboxId, String sandboxName) {throw new java.lang.UnsupportedOperationException();}
	public static void testUninstall(UninstallHandler script) {throw new java.lang.UnsupportedOperationException();}
}
