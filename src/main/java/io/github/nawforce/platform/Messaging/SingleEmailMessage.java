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
package io.github.nawforce.platform.Messaging;

import io.github.nawforce.platform.System.Boolean;
import io.github.nawforce.platform.System.Id;
import io.github.nawforce.platform.System.List;
import io.github.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class SingleEmailMessage {
	public List<String> BccAddresses;
	public List<String> CcAddresses;
	public String Charset;
	public List<String> EntityAttachments;
	public List<EmailFileAttachment> FileAttachments;
	public String HtmlBody;
	public String InReplyTo;
	public String OptOutPolicy;
	public Id OrgWideEmailAddressId;
	public String PlainTextBody;
	public String References;
	public Id TargetObjectId;
	public Id TemplateId;
	public String TemplateName;
	public List<String> ToAddresses;
	public Boolean TreatBodiesAsTemplate;
	public Boolean TreatTargetObjectAsRecipient;
	public Boolean UserMail;
	public Id WhatId;

	public SingleEmailMessage() {throw new java.lang.UnsupportedOperationException();}

	public List<String> getBccAddresses() {throw new java.lang.UnsupportedOperationException();}
	public Boolean getBccSender() {throw new java.lang.UnsupportedOperationException();}
	public List<String> getCcAddresses() {throw new java.lang.UnsupportedOperationException();}
	public String getCharset() {throw new java.lang.UnsupportedOperationException();}
	public String getEmailPriority() {throw new java.lang.UnsupportedOperationException();}
	public List<String> getEntityAttachments() {throw new java.lang.UnsupportedOperationException();}
	public List<EmailFileAttachment> getFileAttachments() {throw new java.lang.UnsupportedOperationException();}
	public String getHtmlBody() {throw new java.lang.UnsupportedOperationException();}
	public String getInReplyTo() {throw new java.lang.UnsupportedOperationException();}
	public String getOptOutPolicy() {throw new java.lang.UnsupportedOperationException();}
	public Id getOrgWideEmailAddressId() {throw new java.lang.UnsupportedOperationException();}
	public String getPlainTextBody() {throw new java.lang.UnsupportedOperationException();}
	public String getReferences() {throw new java.lang.UnsupportedOperationException();}
	public String getReplyTo() {throw new java.lang.UnsupportedOperationException();}
	public Boolean getSaveAsActivity() {throw new java.lang.UnsupportedOperationException();}
	public String getSenderDisplayName() {throw new java.lang.UnsupportedOperationException();}
	public String getSubject() {throw new java.lang.UnsupportedOperationException();}
	public Id getTargetObjectId() {throw new java.lang.UnsupportedOperationException();}
	public Id getTemplateId() {throw new java.lang.UnsupportedOperationException();}
	public String getTemplateName() {throw new java.lang.UnsupportedOperationException();}
	public List<String> getToAddresses() {throw new java.lang.UnsupportedOperationException();}
	public Boolean getUseSignature() {throw new java.lang.UnsupportedOperationException();}
	public Id getWhatId() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isTreatBodiesAsTemplate() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isTreatTargetObjectAsRecipient() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isUserMail() {throw new java.lang.UnsupportedOperationException();}
	public void setBccAddresses(List<String> param1) {throw new java.lang.UnsupportedOperationException();}
	public void setBccSender(Boolean param1) {throw new java.lang.UnsupportedOperationException();}
	public void setCcAddresses(List<String> param1) {throw new java.lang.UnsupportedOperationException();}
	public void setCharset(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setEmailPriority(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setEntityAttachments(List<String> param1) {throw new java.lang.UnsupportedOperationException();}
	public void setFileAttachments(List<EmailFileAttachment> param1) {throw new java.lang.UnsupportedOperationException();}
	public void setHtmlBody(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setInReplyTo(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setOptOutPolicy(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setOrgWideEmailAddressId(Id param1) {throw new java.lang.UnsupportedOperationException();}
	public void setPlainTextBody(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setReferences(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setReplyTo(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setSaveAsActivity(Boolean param1) {throw new java.lang.UnsupportedOperationException();}
	public void setSenderDisplayName(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setSubject(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setTargetObjectId(Id param1) {throw new java.lang.UnsupportedOperationException();}
	public void setTemplateId(Id param1) {throw new java.lang.UnsupportedOperationException();}
	public void setToAddresses(List<String> param1) {throw new java.lang.UnsupportedOperationException();}
	public void setTreatBodiesAsTemplate(Boolean param1) {throw new java.lang.UnsupportedOperationException();}
	public void setTreatTargetObjectAsRecipient(Boolean param1) {throw new java.lang.UnsupportedOperationException();}
	public void setUseSignature(Boolean param1) {throw new java.lang.UnsupportedOperationException();}
	public void setWhatId(Id param1) {throw new java.lang.UnsupportedOperationException();}
}
