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
package com.nawforce.platform.eventbus;

import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.List;
import com.nawforce.platform.System.Long;
import com.nawforce.platform.System.String;


@SuppressWarnings("unused")
public class ChangeEventHeader {
	public String ChangeOrigin;
	public String ChangeType;
	public Long CommitNumber;
	public Long CommitTimestamp;
	public String CommitUser;
	public List<String> DiffFields;
	public String EntityName;
	public List<String> NulledFields;
	public List<String> RecordIds;
	public Integer SequenceNumber;
	public com.nawforce.platform.System.Boolean TransactionEnd;
	public String TransactionKey;

	public ChangeEventHeader() {throw new java.lang.UnsupportedOperationException();}

	public String getChangeOrigin() {throw new java.lang.UnsupportedOperationException();}
	public String getChangeType() {throw new java.lang.UnsupportedOperationException();}
	public Long getCommitNumber() {throw new java.lang.UnsupportedOperationException();}
	public Long getCommitTimestamp() {throw new java.lang.UnsupportedOperationException();}
	public String getCommitUser() {throw new java.lang.UnsupportedOperationException();}
	public List<String> getDiffFields() {throw new java.lang.UnsupportedOperationException();}
	public String getEntityName() {throw new java.lang.UnsupportedOperationException();}
	public List<String> getNulledFields() {throw new java.lang.UnsupportedOperationException();}
	public List<String> getRecordIds() {throw new java.lang.UnsupportedOperationException();}
	public Integer getSequenceNumber() {throw new java.lang.UnsupportedOperationException();}
	public String getTransactionKey() {throw new java.lang.UnsupportedOperationException();}
	public com.nawforce.platform.System.Boolean isTransactionEnd() {throw new java.lang.UnsupportedOperationException();}
	public void setChangeOrigin(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setChangeType(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setCommitNumber(Long param1) {throw new java.lang.UnsupportedOperationException();}
	public void setCommitTimestamp(Long param1) {throw new java.lang.UnsupportedOperationException();}
	public void setCommitUser(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setDiffFields(List<String> param1) {throw new java.lang.UnsupportedOperationException();}
	public void setEntityName(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setNulledFields(List<String> param1) {throw new java.lang.UnsupportedOperationException();}
	public void setRecordIds(List<String> param1) {throw new java.lang.UnsupportedOperationException();}
	public void setSequenceNumber(Integer param1) {throw new java.lang.UnsupportedOperationException();}
	public void setTransactionKey(String param1) {throw new java.lang.UnsupportedOperationException();}
}
