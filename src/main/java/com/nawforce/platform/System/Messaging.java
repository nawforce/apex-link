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
package com.nawforce.platform.System;

import com.nawforce.platform.Messaging.*;

@SuppressWarnings("unused")
public class Messaging {
	public static InboundEmail extractInboundEmail(Object source, Boolean includeForwardedAttachments) {throw new java.lang.UnsupportedOperationException();}
	public static List<RenderEmailTemplateBodyResult> renderEmailTemplate(String whoId, String whatId, List<String> bodies) {throw new java.lang.UnsupportedOperationException();}
	public static SingleEmailMessage renderStoredEmailTemplate(String templateId, String whoId, String whatId) {throw new java.lang.UnsupportedOperationException();}
	public static SingleEmailMessage renderStoredEmailTemplate(String templateId, String whoId, String whatId, Object attachmentRetrievalOption) {throw new java.lang.UnsupportedOperationException();}
	public static SingleEmailMessage renderStoredEmailTemplate(String templateId, String whoId, String whatId, Object attachmentRetrievalOption, Boolean updateEmailTemplateUsage) {throw new java.lang.UnsupportedOperationException();}
	public static void reserveMassEmailCapacity(Integer count) {throw new java.lang.UnsupportedOperationException();}
	public static void reserveSingleEmailCapacity(Integer count) {throw new java.lang.UnsupportedOperationException();}
	public static List<SendEmailResult> sendEmail(List<Email> emailMessages) {throw new java.lang.UnsupportedOperationException();}
	public static List<SendEmailResult> sendEmail(List<Email> emailMessages, Boolean allOrNothing) {throw new java.lang.UnsupportedOperationException();}
	public static List<SendEmailResult> sendEmailMessage(List<Id> emailMessagesIds) {throw new java.lang.UnsupportedOperationException();}
	public static List<SendEmailResult> sendEmailMessage(List<Id> emailMessagesIds, Boolean allOrNothing) {throw new java.lang.UnsupportedOperationException();}
}
