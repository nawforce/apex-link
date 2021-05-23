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

import com.nawforce.platform.ApexPages.Message;
import com.nawforce.platform.ApexPages.Severity;

@SuppressWarnings("unused")
public class ApexPages {
	public static void addMessage(Message message) {throw new java.lang.UnsupportedOperationException();}
	public static void addMessages(Object ex) {throw new java.lang.UnsupportedOperationException();}
	public static PageReference currentPage() {throw new java.lang.UnsupportedOperationException();}
	public static List<Message> getMessages() {throw new java.lang.UnsupportedOperationException();}
	public static Boolean hasMessages() {throw new java.lang.UnsupportedOperationException();}
	public static Boolean hasMessages(Severity severity) {throw new java.lang.UnsupportedOperationException();}
}
