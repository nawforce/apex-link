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

package com.nawforce.platform.Messaging;

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Id;
import com.nawforce.platform.System.List;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class SingleEmailMessage extends Email {
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
	public String Subject;
	public Id TargetObjectId;
	public Id TemplateId;
	public String TemplateName;
	public List<String> ToAddresses;
	public Boolean TreatBodiesAsTemplate;
	public Boolean TreatTargetObjectAsRecipient;
	public Boolean UserMail;
	public Id WhatId;
	public Boolean SaveAsActivity;

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
