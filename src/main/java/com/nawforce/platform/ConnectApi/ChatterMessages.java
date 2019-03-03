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
package com.nawforce.platform.ConnectApi;

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class ChatterMessages {



	public static ChatterConversation getConversation(String communityId, String conversationId) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterConversation getConversation(String communityId, String conversationId, String pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterConversation getConversation(String conversationId) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterConversation getConversation(String conversationId, String pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterConversationPage getConversations() {throw new java.lang.UnsupportedOperationException();}
	public static ChatterConversationPage getConversations(String communityId) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterConversationPage getConversations(String communityId, String pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterConversationPage getConversations(String pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterMessage getMessage(String communityId, String messageId) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterMessage getMessage(String messageId) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterMessagePage getMessages() {throw new java.lang.UnsupportedOperationException();}
	public static ChatterMessagePage getMessages(String communityId) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterMessagePage getMessages(String communityId, String pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterMessagePage getMessages(String pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static UnreadConversationCount getUnreadCount() {throw new java.lang.UnsupportedOperationException();}
	public static UnreadConversationCount getUnreadCount(String communityId) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterConversationSummary markConversationRead(String communityId, String conversationId, Boolean read) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterConversationSummary markConversationRead(String conversationId, Boolean read) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterMessage replyToMessage(String communityId, String text, String inReplyTo) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterMessage replyToMessage(String text, String inReplyTo) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterConversation searchConversation(String communityId, String conversationId, String pageParam, Integer pageSize, String q) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterConversation searchConversation(String communityId, String conversationId, String q) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterConversation searchConversation(String conversationId, String pageParam, Integer pageSize, String q) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterConversation searchConversation(String conversationId, String q) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterConversationPage searchConversations(String communityId, String pageParam, Integer pageSize, String q) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterConversationPage searchConversations(String communityId, String q) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterConversationPage searchConversations(String pageParam, Integer pageSize, String q) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterConversationPage searchConversations(String q) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterMessagePage searchMessages(String communityId, String pageParam, Integer pageSize, String q) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterMessagePage searchMessages(String communityId, String q) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterMessagePage searchMessages(String pageParam, Integer pageSize, String q) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterMessagePage searchMessages(String q) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterMessage sendMessage(String communityId, String text, String recipients) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterMessage sendMessage(String text, String recipients) {throw new java.lang.UnsupportedOperationException();}

}
