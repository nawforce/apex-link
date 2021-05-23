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

package com.nawforce.platform.System;

@SuppressWarnings("unused")
public class Continuation {
	public String ContinuationMethod;
	public Integer Timeout;
	public Object state;

	public Continuation(Integer timeoutInSecs) {throw new java.lang.UnsupportedOperationException();}

	public String addHttpRequest(HttpRequest request) {throw new java.lang.UnsupportedOperationException();}
	public Map<String,HttpRequest> getRequests() {throw new java.lang.UnsupportedOperationException();}
	public String toString$() {throw new java.lang.UnsupportedOperationException();}

	public static HttpResponse getResponse(String label) {throw new java.lang.UnsupportedOperationException();}
}
