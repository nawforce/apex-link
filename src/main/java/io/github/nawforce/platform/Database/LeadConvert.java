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
package io.github.nawforce.platform.Database;

import io.github.nawforce.platform.System.Boolean;
import io.github.nawforce.platform.System.Id;
import io.github.nawforce.platform.System.SObject;
import io.github.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class LeadConvert {
	public Id AccountId;
	public SObject AccountRecord;
	public Boolean BypassAccountDedupeCheck;
	public Boolean BypassContactDedupeCheck;
	public Id ContactId;
	public SObject ContactRecord;
	public String ConvertedStatus;
	public Boolean DoNotCreateOpportunity;
	public Id LeadId;
	public Id OpportunityId;
	public String OpportunityName;
	public SObject OpportunityRecord;
	public Boolean OverwriteLeadSource;
	public Id OwnerId;
	public Boolean SendNotificationEmail;

	public LeadConvert() {throw new java.lang.UnsupportedOperationException();}

	public Id getAccountId() {throw new java.lang.UnsupportedOperationException();}
	public SObject getAccountRecord() {throw new java.lang.UnsupportedOperationException();}
	public Boolean getBypassAccountDedupeCheck() {throw new java.lang.UnsupportedOperationException();}
	public Boolean getBypassContactDedupeCheck() {throw new java.lang.UnsupportedOperationException();}
	public Id getContactId() {throw new java.lang.UnsupportedOperationException();}
	public SObject getContactRecord() {throw new java.lang.UnsupportedOperationException();}
	public String getConvertedStatus() {throw new java.lang.UnsupportedOperationException();}
	public Id getLeadId() {throw new java.lang.UnsupportedOperationException();}
	public Id getOpportunityId() {throw new java.lang.UnsupportedOperationException();}
	public String getOpportunityName() {throw new java.lang.UnsupportedOperationException();}
	public SObject getOpportunityRecord() {throw new java.lang.UnsupportedOperationException();}
	public Id getOwnerId() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isDoNotCreateOpportunity() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isOverwriteLeadSource() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isSendNotificationEmail() {throw new java.lang.UnsupportedOperationException();}
	public void setAccountId(Id param1) {throw new java.lang.UnsupportedOperationException();}
	public void setAccountRecord(SObject param1) {throw new java.lang.UnsupportedOperationException();}
	public void setBypassAccountDedupeCheck(Boolean param1) {throw new java.lang.UnsupportedOperationException();}
	public void setBypassContactDedupeCheck(Boolean param1) {throw new java.lang.UnsupportedOperationException();}
	public void setContactId(Id param1) {throw new java.lang.UnsupportedOperationException();}
	public void setContactRecord(SObject param1) {throw new java.lang.UnsupportedOperationException();}
	public void setConvertedStatus(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setDoNotCreateOpportunity(Boolean param1) {throw new java.lang.UnsupportedOperationException();}
	public void setLeadId(Id param1) {throw new java.lang.UnsupportedOperationException();}
	public void setOpportunityId(Id param1) {throw new java.lang.UnsupportedOperationException();}
	public void setOpportunityName(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setOpportunityRecord(SObject param1) {throw new java.lang.UnsupportedOperationException();}
	public void setOverwriteLeadSource(Boolean param1) {throw new java.lang.UnsupportedOperationException();}
	public void setOwnerId(Id param1) {throw new java.lang.UnsupportedOperationException();}
	public void setSendNotificationEmail(Boolean param1) {throw new java.lang.UnsupportedOperationException();}
}
