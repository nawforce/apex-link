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
import com.nawforce.platform.System.Double;
import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class SocialPostCapability extends FeedElementCapability {
	public SocialAccount author;
	public String content;
	public UserSummary deletedBy;
	public UserSummary hiddenBy;
	public Icon icon;
	public String id;
	public Boolean isOutbound;
	public String likedBy;
	public SocialPostMessageType messageType;
	public String name;
	public String postUrl;
	public SocialNetworkProvider provider;
	public SocialAccount recipient;
	public String recipientId;
	public Double reviewScale;
	public Double reviewScore;
	public SocialPostStatus status;

	public SocialPostCapability() {throw new java.lang.UnsupportedOperationException();}

	public Boolean equals$(Object obj) {throw new java.lang.UnsupportedOperationException();}
	public Double getBuildVersion() {throw new java.lang.UnsupportedOperationException();}
	public Integer hashCode$() {throw new java.lang.UnsupportedOperationException();}
	public String toString$() {throw new java.lang.UnsupportedOperationException();}
}
