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
package io.github.nawforce.platform.System;

import io.github.nawforce.platform.Namespaces.Dom;

@SuppressWarnings("unused")
public class Crypto {
	public Crypto() {throw new java.lang.UnsupportedOperationException();}

	public static Blob decrypt(String algorithmName, Blob secretKey, Blob initializationVector, Blob encryptedData) {throw new java.lang.UnsupportedOperationException();}
	public static Blob decryptWithManagedIV(String algorithmName, Blob secretKey, Blob encryptedData) {throw new java.lang.UnsupportedOperationException();}
	public static Blob encrypt(String algorithmName, Blob secretKey, Blob initializationVector, Blob clearData) {throw new java.lang.UnsupportedOperationException();}
	public static Blob encryptWithManagedIV(String algorithmName, Blob secretKey, Blob clearData) {throw new java.lang.UnsupportedOperationException();}
	public static Blob generateAesKey(Integer size) {throw new java.lang.UnsupportedOperationException();}
	public static Blob generateDigest(String algorithmName, Blob input) {throw new java.lang.UnsupportedOperationException();}
	public static Blob generateMac(String algorithmName, Blob input, Blob privateKey) {throw new java.lang.UnsupportedOperationException();}
	public static Integer getRandomInteger() {throw new java.lang.UnsupportedOperationException();}
	public static Long getRandomLong() {throw new java.lang.UnsupportedOperationException();}
	public static Blob sign(String algorithmName, Blob input, Blob privateKey) {throw new java.lang.UnsupportedOperationException();}
	public static Blob signWithCertificate(String algorithmName, Blob input, String certDevName) {throw new java.lang.UnsupportedOperationException();}
	public static void signXml(String algorithmName, Dom.XmlNode node, String idAttributeName, String certDevName) {throw new java.lang.UnsupportedOperationException();}
	public static void signXml(String algorithmName, Dom.XmlNode node, String idAttributeName, String certDevName, Dom.XmlNode refChild) {throw new java.lang.UnsupportedOperationException();}
	public static Boolean verify(String algorithmName, Blob data, Blob signature, Blob publicKey) {throw new java.lang.UnsupportedOperationException();}
	public static Boolean verify(String algorithmName, Blob data, Blob signature, String certDevName) {throw new java.lang.UnsupportedOperationException();}
	public static Boolean verifyHmac(String algorithmName, Blob data, Blob privateKey, Blob hmacToVerify) {throw new java.lang.UnsupportedOperationException();}
}
