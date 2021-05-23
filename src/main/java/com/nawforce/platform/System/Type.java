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

import com.nawforce.platform.Schema.SObjectType;

@SuppressWarnings("unused")
public class Type {
	// TODO: Should only be visible on SObjects when immediately accessed
	public SObjectType SObjectType;

	public Boolean equals$(Object o) {throw new java.lang.UnsupportedOperationException();}
	public String getName() {throw new java.lang.UnsupportedOperationException();}
	public Integer hashCode$() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isAssignableFrom(Type sourceType) {throw new java.lang.UnsupportedOperationException();}
	public Object newInstance() {throw new java.lang.UnsupportedOperationException();}
	public String toString$() {throw new java.lang.UnsupportedOperationException();}

	public static Type forName(String clsName) {throw new java.lang.UnsupportedOperationException();}
	public static Type forName(String namespace, String clsName) {throw new java.lang.UnsupportedOperationException();}
}
