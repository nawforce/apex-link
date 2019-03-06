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

import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.List;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class ChatterUsers {
	public static void deletePhoto(String communityId, String userId) {throw new java.lang.UnsupportedOperationException();}
	public static UserActivitiesJob exportUserActivities(String communityId, String userId) {throw new java.lang.UnsupportedOperationException();}
	public static Subscription follow(String communityId, String userId, String subjectId) {throw new java.lang.UnsupportedOperationException();}
	public static UserChatterSettings getChatterSettings(String communityId, String userId) {throw new java.lang.UnsupportedOperationException();}
	public static FollowerPage getFollowers(String communityId, String userId) {throw new java.lang.UnsupportedOperationException();}
	public static FollowerPage getFollowers(String communityId, String userId, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static FollowingPage getFollowings(String communityId, String userId) {throw new java.lang.UnsupportedOperationException();}
	public static FollowingPage getFollowings(String communityId, String userId, Integer pageParam) {throw new java.lang.UnsupportedOperationException();}
	public static FollowingPage getFollowings(String communityId, String userId, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static FollowingPage getFollowings(String communityId, String userId, String filterType) {throw new java.lang.UnsupportedOperationException();}
	public static FollowingPage getFollowings(String communityId, String userId, String filterType, Integer pageParam) {throw new java.lang.UnsupportedOperationException();}
	public static FollowingPage getFollowings(String communityId, String userId, String filterType, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static UserGroupPage getGroups(String communityId, String userId) {throw new java.lang.UnsupportedOperationException();}
	public static UserGroupPage getGroups(String communityId, String userId, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static Photo getPhoto(String communityId, String userId) {throw new java.lang.UnsupportedOperationException();}
	public static Reputation getReputation(String communityId, String userId) {throw new java.lang.UnsupportedOperationException();}
	public static UserDetail getUser(String communityId, String userId) {throw new java.lang.UnsupportedOperationException();}
	public static List<BatchResult> getUserBatch(String communityId, List<String> userIds) {throw new java.lang.UnsupportedOperationException();}
	public static UserGroupDetailPage getUserGroups(String communityId, String userId) {throw new java.lang.UnsupportedOperationException();}
	public static UserGroupDetailPage getUserGroups(String communityId, String userId, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static UserPage getUsers(String communityId) {throw new java.lang.UnsupportedOperationException();}
	public static UserPage getUsers(String communityId, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static UserActivitiesJob purgeUserActivities(String communityId, String userId) {throw new java.lang.UnsupportedOperationException();}
	public static UserGroupDetailPage searchUserGroupDetails(String communityId, String userId, String q) {throw new java.lang.UnsupportedOperationException();}
	public static UserGroupDetailPage searchUserGroupDetails(String communityId, String userId, String q, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static UserGroupPage searchUserGroups(String communityId, String userId, String q) {throw new java.lang.UnsupportedOperationException();}
	public static UserGroupPage searchUserGroups(String communityId, String userId, String q, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static UserPage searchUsers(String communityId, String q) {throw new java.lang.UnsupportedOperationException();}
	public static UserPage searchUsers(String communityId, String q, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static UserPage searchUsers(String communityId, String q, String searchContextId, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static Photo setPhoto(String communityId, String userId, BinaryInput fileUpload) {throw new java.lang.UnsupportedOperationException();}
	public static Photo setPhoto(String communityId, String userId, String fileId, Integer versionNumber) {throw new java.lang.UnsupportedOperationException();}
	public static Photo setPhotoWithAttributes(String communityId, String userId, PhotoInput photo) {throw new java.lang.UnsupportedOperationException();}
	public static Photo setPhotoWithAttributes(String communityId, String userId, PhotoInput photo, BinaryInput fileUpload) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestSearchUsers(String communityId, String q, UserPage result) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestSearchUsers(String communityId, String q, Integer pageParam, Integer pageSize, UserPage result) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestSearchUsers(String communityId, String q, String searchContextId, Integer pageParam, Integer pageSize, UserPage result) {throw new java.lang.UnsupportedOperationException();}
	public static UserChatterSettings updateChatterSettings(String communityId, String userId, GroupEmailFrequency defaultGroupEmailFrequency) {throw new java.lang.UnsupportedOperationException();}
	public static UserDetail updateUser(String communityId, String userId, UserInput userInput) {throw new java.lang.UnsupportedOperationException();}
}
