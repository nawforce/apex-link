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
package com.nawforce.platform.Messaging;

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Id;
import com.nawforce.platform.System.List;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class MassEmailMessage {
	public String Description;
	public List<Id> TargetObjectIds;
	public Id TemplateId;
	public List<Id> WhatIds;

	public MassEmailMessage() {throw new java.lang.UnsupportedOperationException();}

	public Boolean getBccSender() {throw new java.lang.UnsupportedOperationException();}
	public String getDescription() {throw new java.lang.UnsupportedOperationException();}
	public String getEmailPriority() {throw new java.lang.UnsupportedOperationException();}
	public String getReplyTo() {throw new java.lang.UnsupportedOperationException();}
	public Boolean getSaveAsActivity() {throw new java.lang.UnsupportedOperationException();}
	public String getSenderDisplayName() {throw new java.lang.UnsupportedOperationException();}
	public String getSubject() {throw new java.lang.UnsupportedOperationException();}
	public List<Id> getTargetObjectIds() {throw new java.lang.UnsupportedOperationException();}
	public Id getTemplateId() {throw new java.lang.UnsupportedOperationException();}
	public Boolean getUseSignature() {throw new java.lang.UnsupportedOperationException();}
	public List<Id> getWhatIds() {throw new java.lang.UnsupportedOperationException();}
	public void setBccSender(Boolean param1) {throw new java.lang.UnsupportedOperationException();}
	public void setDescription(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setEmailPriority(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setReplyTo(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setSaveAsActivity(Boolean param1) {throw new java.lang.UnsupportedOperationException();}
	public void setSenderDisplayName(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setSubject(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setTargetObjectIds(List<Id> param1) {throw new java.lang.UnsupportedOperationException();}
	public void setTemplateId(Id param1) {throw new java.lang.UnsupportedOperationException();}
	public void setUseSignature(Boolean param1) {throw new java.lang.UnsupportedOperationException();}
	public void setWhatIds(List<Id> param1) {throw new java.lang.UnsupportedOperationException();}
}
