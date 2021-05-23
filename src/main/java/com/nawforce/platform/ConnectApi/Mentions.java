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

import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.List;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class Mentions {
	public static MentionCompletionPage getMentionCompletions(String communityId, String q, String contextId) {throw new java.lang.UnsupportedOperationException();}
	public static MentionCompletionPage getMentionCompletions(String communityId, String q, String contextId, MentionCompletionType type, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static MentionValidations getMentionValidations(String communityId, String parentId, List<String> recordIds, FeedItemVisibilityType visibility) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestGetMentionCompletions(String communityId, String q, String contextId, MentionCompletionPage result) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestGetMentionCompletions(String communityId, String q, String contextId, MentionCompletionType type, Integer pageParam, Integer pageSize, MentionCompletionPage result) {throw new java.lang.UnsupportedOperationException();}
}
