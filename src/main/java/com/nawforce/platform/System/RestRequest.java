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
public class RestRequest {
	public Map<String, String> headers;
	public String httpMethod;
	public Map<String, String> params;
	public String remoteAddress;
	public Blob requestBody;
	public String requestURI;
	public String resourcePath;

	public RestRequest() {throw new java.lang.UnsupportedOperationException();}

	public void addHeader(String name, String value) {throw new java.lang.UnsupportedOperationException();}
	public void addParameter(String name, String value) {throw new java.lang.UnsupportedOperationException();}
}
