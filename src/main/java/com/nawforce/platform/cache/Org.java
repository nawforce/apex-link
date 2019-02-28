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
package com.nawforce.platform.cache;

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Double;
import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.Long;
import com.nawforce.platform.System.String;
import com.nawforce.platform.System.*;

@SuppressWarnings("unused")
public class Org {
	public Integer MAX_TTL_SECS;

	public Org() {throw new java.lang.UnsupportedOperationException();}

	public static List<Boolean> contains(List<String> keys) {throw new java.lang.UnsupportedOperationException();}
	public static Boolean contains(String key) {throw new java.lang.UnsupportedOperationException();}
	public static List<Object> get(List<String> keys) {throw new java.lang.UnsupportedOperationException();}
	public static Object get(String key) {throw new java.lang.UnsupportedOperationException();}
	public static Object get(Type cacheBuilder, String key) {throw new java.lang.UnsupportedOperationException();}
	public static Long getAvgGetTime() {throw new java.lang.UnsupportedOperationException();}
	public static Long getAvgValueSize() {throw new java.lang.UnsupportedOperationException();}
	public static Double getCapacity() {throw new java.lang.UnsupportedOperationException();}
	public static Set<String> getKeys() {throw new java.lang.UnsupportedOperationException();}
	public static Long getMaxGetTime() {throw new java.lang.UnsupportedOperationException();}
	public static Long getMaxValueSize() {throw new java.lang.UnsupportedOperationException();}
	public static Double getMissRate() {throw new java.lang.UnsupportedOperationException();}
	public static String getName() {throw new java.lang.UnsupportedOperationException();}
	public static Long getNumKeys() {throw new java.lang.UnsupportedOperationException();}
	public static OrgPartition getPartition(String partitionName) {throw new java.lang.UnsupportedOperationException();}
	public static void put(String key, Object value) {throw new java.lang.UnsupportedOperationException();}
	public static void put(String key, Object value, Integer ttlSecs) {throw new java.lang.UnsupportedOperationException();}
	public static void put(String key, Object value, Integer ttlSecs, Visibility visibility, Boolean immutable) {throw new java.lang.UnsupportedOperationException();}
	public static void put(String key, Object value, Visibility visibility) {throw new java.lang.UnsupportedOperationException();}
	public static List<Boolean> remove(List<String> keys) {throw new java.lang.UnsupportedOperationException();}
	public static Boolean remove(String key) {throw new java.lang.UnsupportedOperationException();}
	public static Boolean remove(Type cacheBuilder, String key) {throw new java.lang.UnsupportedOperationException();}
}
