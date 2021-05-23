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

package com.nawforce.platform.ApexPages;

import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class Message {
	public Message(Severity severity, String message) {throw new java.lang.UnsupportedOperationException();}
	public Message(Severity severity, String summary, String detail) {throw new java.lang.UnsupportedOperationException();}
	public Message(Severity severity, String summary, String detail, String id) {throw new java.lang.UnsupportedOperationException();}

	public String getComponentLabel() {throw new java.lang.UnsupportedOperationException();}
	public String getDetail() {throw new java.lang.UnsupportedOperationException();}
	public Severity getSeverity() {throw new java.lang.UnsupportedOperationException();}
	public String getSummary() {throw new java.lang.UnsupportedOperationException();}
}
