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
import com.nawforce.platform.System.*;

@SuppressWarnings("unused")
public class Comment {
	public FeedBody body;
	public CommentCapabilities capabilities;
	public ClientInfo clientInfo;
	public Datetime createdDate;
	public Reference feedElement;
	public String id;
	public Boolean isDeleteRestricted;
	public ChatterLikePage likes;
	public MessageBody likesMessage;
	public ModerationFlags moderationFlags;
	public Reference myLike;
	public Reference parent;
	public String relativeCreatedDate;
	public Integer threadLevel;
	public String threadParentId;
	public CommentType type;
	public String url;
	public UserSummary user;

	public Comment() {throw new java.lang.UnsupportedOperationException();}

	public Boolean equals$(Object obj) {throw new java.lang.UnsupportedOperationException();}
	public Double getBuildVersion() {throw new java.lang.UnsupportedOperationException();}
	public Integer hashCode$() {throw new java.lang.UnsupportedOperationException();}
	public String toString$() {throw new java.lang.UnsupportedOperationException();}
}
