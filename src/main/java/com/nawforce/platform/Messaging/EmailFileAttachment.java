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

package com.nawforce.platform.Messaging;

import com.nawforce.platform.System.Blob;
import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Id;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class EmailFileAttachment {
	public Blob Body;
	public String ContentType;
	public String FileName;
	public com.nawforce.platform.System.Id Id;
	public Boolean Inline;

	public EmailFileAttachment() {throw new java.lang.UnsupportedOperationException();}

	public Blob getBody() {throw new java.lang.UnsupportedOperationException();}
	public String getContentType() {throw new java.lang.UnsupportedOperationException();}
	public String getFileName() {throw new java.lang.UnsupportedOperationException();}
	public Id getId() {throw new java.lang.UnsupportedOperationException();}
	public Boolean getInline() {throw new java.lang.UnsupportedOperationException();}
	public void setBody(Blob param1) {throw new java.lang.UnsupportedOperationException();}
	public void setContentType(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setFileName(String param1) {throw new java.lang.UnsupportedOperationException();}
	public void setInline(Boolean param1) {throw new java.lang.UnsupportedOperationException();}
}
