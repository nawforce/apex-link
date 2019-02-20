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
public class PageReference {
	public PageReference(SObject sobject) {throw new java.lang.UnsupportedOperationException();}
	public PageReference(String url) {throw new java.lang.UnsupportedOperationException();}

	public String getAnchor() {throw new java.lang.UnsupportedOperationException();}
	public Blob getContent() {throw new java.lang.UnsupportedOperationException();}
	public Blob getContentAsPDF() {throw new java.lang.UnsupportedOperationException();}
	public Map<String,Cookie> getCookies() {throw new java.lang.UnsupportedOperationException();}
	public Map<String,String> getHeaders() {throw new java.lang.UnsupportedOperationException();}
	public Map<String,String> getParameters() {throw new java.lang.UnsupportedOperationException();}
	public Boolean getRedirect() {throw new java.lang.UnsupportedOperationException();}
	public String getUrl() {throw new java.lang.UnsupportedOperationException();}
	public PageReference setAnchor(String anchor) {throw new java.lang.UnsupportedOperationException();}
	public void setCookies(List<Cookie> cookies) {throw new java.lang.UnsupportedOperationException();}
	public PageReference setRedirect(Boolean redirect) {throw new java.lang.UnsupportedOperationException();}

	public static PageReference forResource(String resourceName) {throw new java.lang.UnsupportedOperationException();}
	public static PageReference forResource(String resourceName, String path) {throw new java.lang.UnsupportedOperationException();}
}
