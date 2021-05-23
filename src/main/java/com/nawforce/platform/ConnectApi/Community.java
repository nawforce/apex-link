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
public class Community {
	public Boolean allowChatterAccessWithoutLogin;
	public Boolean allowMembersToFlag;
	public String description;
	public String id;
	public Boolean invitationsEnabled;
	public Boolean knowledgeableEnabled;
	public String loginUrl;
	public Boolean memberVisibilityEnabled;
	public String name;
	public Boolean nicknameDisplayEnabled;
	public Boolean privateMessagesEnabled;
	public Boolean reputationEnabled;
	public Boolean sendWelcomeEmail;
	public Boolean siteAsContainerEnabled;
	public String siteUrl;
	public CommunityStatus status;
	public String url;
	public String urlPathPrefix;

	public Community() {throw new java.lang.UnsupportedOperationException();}

	public Boolean equals$(Object obj) {throw new java.lang.UnsupportedOperationException();}
	public Double getBuildVersion() {throw new java.lang.UnsupportedOperationException();}
	public Integer hashCode$() {throw new java.lang.UnsupportedOperationException();}
	public String toString$() {throw new java.lang.UnsupportedOperationException();}
}
