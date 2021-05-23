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

package com.nawforce.platform.ConnectApi;

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Exception;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class BatchResult {
	public Exception Error;
	public String ErrorMessage;
	public String ErrorTypeName;
	public Boolean isSuccess;
	public Object Result;

	public BatchResult(Object result, Exception error) {throw new java.lang.UnsupportedOperationException();}

	public Exception getError() {throw new java.lang.UnsupportedOperationException();}
	public String getErrorMessage() {throw new java.lang.UnsupportedOperationException();}
	public String getErrorTypeName() {throw new java.lang.UnsupportedOperationException();}
	public Object getResult() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isSuccess() {throw new java.lang.UnsupportedOperationException();}
	public String toString$() {throw new java.lang.UnsupportedOperationException();}
}
