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
public class JSON {
	public JSON() {throw new java.lang.UnsupportedOperationException();}

	public static JSONGenerator createGenerator(Boolean pretty) {throw new java.lang.UnsupportedOperationException();}
	public static JSONParser createParser(String jsonString) {throw new java.lang.UnsupportedOperationException();}
	public static Object deserialize(String jsonString, Type apexType) {throw new java.lang.UnsupportedOperationException();}
	public static Object deserializeStrict(String jsonString, Type apexType) {throw new java.lang.UnsupportedOperationException();}
	public static Object deserializeUntyped(String jsonString) {throw new java.lang.UnsupportedOperationException();}
	public static String serialize(Object o) {throw new java.lang.UnsupportedOperationException();}
	public static String serialize(Object o, Boolean suppressApexObjectNulls) {throw new java.lang.UnsupportedOperationException();}
	public static String serializePretty(Object o) {throw new java.lang.UnsupportedOperationException();}
	public static String serializePretty(Object o, Boolean suppressApexObjectNulls) {throw new java.lang.UnsupportedOperationException();}
}
