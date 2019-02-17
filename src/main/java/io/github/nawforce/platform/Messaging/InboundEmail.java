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
package io.github.nawforce.platform.Messaging;

import io.github.nawforce.platform.System.Blob;
import io.github.nawforce.platform.System.Boolean;
import io.github.nawforce.platform.System.List;
import io.github.nawforce.platform.System.String;

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
