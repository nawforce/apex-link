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

package com.nawforce.platform.QuickAction;

import com.nawforce.platform.System.Id;
import com.nawforce.platform.System.SObject;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class QuickActionRequest {
	public Id ContextId;
	public String QuickActionName;
	public SObject Record;

	public QuickActionRequest() {throw new java.lang.UnsupportedOperationException();}

	public Id getContextId() {throw new java.lang.UnsupportedOperationException();}
	public String getQuickActionName() {throw new java.lang.UnsupportedOperationException();}
	public SObject getRecord() {throw new java.lang.UnsupportedOperationException();}
	public void setContextId(Id param1) {throw new java.lang.UnsupportedOperationException();}
	public void setQuickActionName(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setRecord(SObject param1) {throw new java.lang.UnsupportedOperationException();}
}
