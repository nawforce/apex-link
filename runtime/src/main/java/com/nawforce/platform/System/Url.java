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
package com.nawforce.platform.System;

@SuppressWarnings("unused")
public class Url {
	public Url(String protocol, String host, Integer port, String file) {throw new java.lang.UnsupportedOperationException();}
	public Url(String protocol, String host, String file) {throw new java.lang.UnsupportedOperationException();}
	public Url(String spec) {throw new java.lang.UnsupportedOperationException();}
	public Url(Url context, String spec) {throw new java.lang.UnsupportedOperationException();}

	public String getAuthority() {throw new java.lang.UnsupportedOperationException();}
	public Integer getDefaultPort() {throw new java.lang.UnsupportedOperationException();}
	public String getFile() {throw new java.lang.UnsupportedOperationException();}
	public String getHost() {throw new java.lang.UnsupportedOperationException();}
	public String getPath() {throw new java.lang.UnsupportedOperationException();}
	public Integer getPort() {throw new java.lang.UnsupportedOperationException();}
	public String getProtocol() {throw new java.lang.UnsupportedOperationException();}
	public String getQuery() {throw new java.lang.UnsupportedOperationException();}
	public String getRef() {throw new java.lang.UnsupportedOperationException();}
	public String getUserInfo() {throw new java.lang.UnsupportedOperationException();}
	public Boolean sameFile(Url other) {throw new java.lang.UnsupportedOperationException();}
	public String toExternalForm() {throw new java.lang.UnsupportedOperationException();}
	public String toString$() {throw new java.lang.UnsupportedOperationException();}

	public static Url getCurrentRequestUrl() {throw new java.lang.UnsupportedOperationException();}
	public static String getFileFieldURL(String objectId, String fieldName) {throw new java.lang.UnsupportedOperationException();}
	public static Url getOrgDomainUrl() {throw new java.lang.UnsupportedOperationException();}
	public static Url getSalesforceBaseUrl() {throw new java.lang.UnsupportedOperationException();}
}
