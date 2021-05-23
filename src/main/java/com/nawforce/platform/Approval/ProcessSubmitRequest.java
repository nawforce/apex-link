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

package com.nawforce.platform.Approval;

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Id;
import com.nawforce.platform.System.List;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class ProcessSubmitRequest extends ProcessRequest {
	public String ObjectId;
	public String ProcessDefinitionNameOrId;
	public Boolean SkipEntryCriteria;
	public String SubmitterId;

	public ProcessSubmitRequest() {throw new java.lang.UnsupportedOperationException();}

	public String getComments() {throw new java.lang.UnsupportedOperationException();}
	public List<Id> getNextApproverIds() {throw new java.lang.UnsupportedOperationException();}
	public String getObjectId() {throw new java.lang.UnsupportedOperationException();}
	public String getProcessDefinitionNameOrId() {throw new java.lang.UnsupportedOperationException();}
	public Boolean getSkipEntryCriteria() {throw new java.lang.UnsupportedOperationException();}
	public String getSubmitterId() {throw new java.lang.UnsupportedOperationException();}
	public void setComments(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setNextApproverIds(List<Id> param1) {throw new java.lang.UnsupportedOperationException();}
	public void setObjectId(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setProcessDefinitionNameOrId(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setSkipEntryCriteria(Boolean param1) {throw new java.lang.UnsupportedOperationException();}
	public void setSubmitterId(String param1) {throw new java.lang.UnsupportedOperationException();}
}
