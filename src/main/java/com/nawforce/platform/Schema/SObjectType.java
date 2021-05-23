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

package com.nawforce.platform.Schema;

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Id;
import com.nawforce.platform.System.SObject;

@SuppressWarnings("unused")
public class SObjectType {
	public DescribeSObjectResult getDescribe() {throw new java.lang.UnsupportedOperationException();}
	public DescribeSObjectResult getDescribe(Object options) {throw new java.lang.UnsupportedOperationException();}
	public SObject newSObject() {throw new java.lang.UnsupportedOperationException();}
	public SObject newSObject(Id id) {throw new java.lang.UnsupportedOperationException();}
	public SObject newSObject(Id recordTypeId, Boolean loadDefaultValues) {throw new java.lang.UnsupportedOperationException();}
}
