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

package com.nawforce.platform.cache;

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Double;
import com.nawforce.platform.System.Exception;
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

  public static class OrgCacheException extends Exception {
    public OrgCacheException() {throw new java.lang.UnsupportedOperationException();}
    public OrgCacheException(Exception param1) {throw new java.lang.UnsupportedOperationException();}
    public OrgCacheException(String msg) {throw new java.lang.UnsupportedOperationException();}
    public OrgCacheException(String param1, Exception param2) {throw new java.lang.UnsupportedOperationException();}
  }

}
