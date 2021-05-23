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
import com.nawforce.platform.System.List;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class InboundEmail {
	public List<BinaryAttachment> binaryAttachments;
	public List<String> ccAddresses;
	public String fromAddress;
	public String fromName;
	public List<Header> headers;
	public String htmlBody;
	public Boolean htmlBodyIsTruncated;
	public String inReplyTo;
	public String messageId;
	public String plainTextBody;
	public Boolean plainTextBodyIsTruncated;
	public List<String> references;
	public String replyTo;
	public String subject;
	public List<TextAttachment> textAttachments;
	public List<String> toAddresses;

	public InboundEmail() {throw new java.lang.UnsupportedOperationException();}

	public static class BinaryAttachment
	{
		public Blob body;
		public String fileName;
		public List<InboundEmail.Header> headers;
		public String mimeTypeSubType;

		public BinaryAttachment() {throw new java.lang.UnsupportedOperationException();}
	}

	public static class Header
	{
		public String name;
		public String value;

		public Header() {throw new java.lang.UnsupportedOperationException();}
	}

	public static class TextAttachment
	{
		public String body;
		public Boolean bodyIsTruncated;
		public String charset;
		public String fileName;
		public List<Header> headers;
		public String mimeTypeSubType;

		public TextAttachment() {throw new java.lang.UnsupportedOperationException();}
	}
}
