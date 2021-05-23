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

import com.nawforce.platform.System.Id;
import com.nawforce.platform.System.List;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class ProcessWorkitemRequest extends ProcessRequest {
	public String Action;
	public String WorkitemId;

	public ProcessWorkitemRequest() {throw new java.lang.UnsupportedOperationException();}

	public String getAction() {throw new java.lang.UnsupportedOperationException();}
	public String getComments() {throw new java.lang.UnsupportedOperationException();}
	public List<Id> getNextApproverIds() {throw new java.lang.UnsupportedOperationException();}
	public String getWorkitemId() {throw new java.lang.UnsupportedOperationException();}
	public void setAction(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setComments(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setNextApproverIds(List<Id> param1) {throw new java.lang.UnsupportedOperationException();}
	public void setWorkitemId(String param1) {throw new java.lang.UnsupportedOperationException();}
}
