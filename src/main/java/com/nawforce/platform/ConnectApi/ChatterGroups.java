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
public class ChatterGroups {
	public static GroupMember addMember(String communityId, String groupId, String userId) {throw new java.lang.UnsupportedOperationException();}
	public static GroupMember addMemberWithRole(String communityId, String groupId, String userId, GroupMembershipType role) {throw new java.lang.UnsupportedOperationException();}
	public static GroupRecord addRecord(String communityId, String groupId, String recordId) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterGroupDetail createGroup(String communityId, ChatterGroupInput groupInput) {throw new java.lang.UnsupportedOperationException();}
	public static void deleteBannerPhoto(String communityId, String groupId) {throw new java.lang.UnsupportedOperationException();}
	public static void deleteGroup(String communityId, String groupId) {throw new java.lang.UnsupportedOperationException();}
	public static void deleteMember(String communityId, String membershipId) {throw new java.lang.UnsupportedOperationException();}
	public static void deletePhoto(String communityId, String groupId) {throw new java.lang.UnsupportedOperationException();}
	public static Subscription follow(String communityId, String groupId, String subjectId) {throw new java.lang.UnsupportedOperationException();}
	public static AnnouncementPage getAnnouncements(String communityId, String groupId) {throw new java.lang.UnsupportedOperationException();}
	public static AnnouncementPage getAnnouncements(String communityId, String groupId, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static BannerPhoto getBannerPhoto(String communityId, String groupId) {throw new java.lang.UnsupportedOperationException();}
	public static FollowingPage getFollowings(String communityId, String groupId) {throw new java.lang.UnsupportedOperationException();}
	public static FollowingPage getFollowings(String communityId, String groupId, Integer pageParam) {throw new java.lang.UnsupportedOperationException();}
	public static FollowingPage getFollowings(String communityId, String groupId, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static FollowingPage getFollowings(String communityId, String groupId, String filterType) {throw new java.lang.UnsupportedOperationException();}
	public static FollowingPage getFollowings(String communityId, String groupId, String filterType, Integer pageParam) {throw new java.lang.UnsupportedOperationException();}
	public static FollowingPage getFollowings(String communityId, String groupId, String filterType, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterGroupDetail getGroup(String communityId, String groupId) {throw new java.lang.UnsupportedOperationException();}
	public static List<BatchResult> getGroupBatch(String communityId, List<String> groupIds) {throw new java.lang.UnsupportedOperationException();}
	public static GroupMembershipRequest getGroupMembershipRequest(String communityId, String requestId) {throw new java.lang.UnsupportedOperationException();}
	public static GroupMembershipRequests getGroupMembershipRequests(String communityId, String groupId) {throw new java.lang.UnsupportedOperationException();}
	public static GroupMembershipRequests getGroupMembershipRequests(String communityId, String groupId, GroupMembershipRequestStatus status) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterGroupPage getGroups(String communityId) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterGroupPage getGroups(String communityId, GroupArchiveStatus archiveStatus, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterGroupPage getGroups(String communityId, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static GroupMember getMember(String communityId, String membershipId) {throw new java.lang.UnsupportedOperationException();}
	public static GroupMemberPage getMembers(String communityId, String groupId) {throw new java.lang.UnsupportedOperationException();}
	public static GroupMemberPage getMembers(String communityId, String groupId, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static List<BatchResult> getMembershipBatch(String communityId, List<String> membershipIds) {throw new java.lang.UnsupportedOperationException();}
	public static GroupChatterSettings getMyChatterSettings(String communityId, String groupId) {throw new java.lang.UnsupportedOperationException();}
	public static Photo getPhoto(String communityId, String groupId) {throw new java.lang.UnsupportedOperationException();}
	public static GroupRecord getRecord(String communityId, String groupRecordId) {throw new java.lang.UnsupportedOperationException();}
	public static GroupRecordPage getRecords(String communityId, String groupId) {throw new java.lang.UnsupportedOperationException();}
	public static GroupRecordPage getRecords(String communityId, String groupId, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static Invitations inviteUsers(String groupId, InviteInput invite) {throw new java.lang.UnsupportedOperationException();}
	public static Announcement postAnnouncement(String communityId, String groupId, AnnouncementInput announcement) {throw new java.lang.UnsupportedOperationException();}
	public static void removeRecord(String communityId, String groupRecordId) {throw new java.lang.UnsupportedOperationException();}
	public static GroupMembershipRequest requestGroupMembership(String communityId, String groupId) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterGroupPage searchGroups(String communityId, String q) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterGroupPage searchGroups(String communityId, String q, GroupArchiveStatus archiveStatus, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterGroupPage searchGroups(String communityId, String q, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static BannerPhoto setBannerPhoto(String communityId, String groupId, BinaryInput fileUpload) {throw new java.lang.UnsupportedOperationException();}
	public static BannerPhoto setBannerPhoto(String communityId, String groupId, String fileId, Integer versionNumber) {throw new java.lang.UnsupportedOperationException();}
	public static BannerPhoto setBannerPhotoWithAttributes(String communityId, String groupId, BannerPhotoInput bannerPhoto) {throw new java.lang.UnsupportedOperationException();}
	public static BannerPhoto setBannerPhotoWithAttributes(String communityId, String groupId, BannerPhotoInput bannerPhoto, BinaryInput fileUpload) {throw new java.lang.UnsupportedOperationException();}
	public static Photo setPhoto(String communityId, String groupId, BinaryInput fileUpload) {throw new java.lang.UnsupportedOperationException();}
	public static Photo setPhoto(String communityId, String groupId, String fileId, Integer versionNumber) {throw new java.lang.UnsupportedOperationException();}
	public static Photo setPhotoWithAttributes(String communityId, String groupId, PhotoInput photo) {throw new java.lang.UnsupportedOperationException();}
	public static Photo setPhotoWithAttributes(String communityId, String groupId, PhotoInput photo, BinaryInput fileUpload) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestSearchGroups(String communityId, String q, ChatterGroupPage result) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestSearchGroups(String communityId, String q, GroupArchiveStatus archiveStatus, Integer pageParam, Integer pageSize, ChatterGroupPage result) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestSearchGroups(String communityId, String q, Integer pageParam, Integer pageSize, ChatterGroupPage result) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterGroupDetail updateGroup(String communityId, String groupId, ChatterGroupInput groupInput) {throw new java.lang.UnsupportedOperationException();}
	public static GroupMember updateGroupMember(String communityId, String membershipId, GroupMembershipType role) {throw new java.lang.UnsupportedOperationException();}
	public static GroupChatterSettings updateMyChatterSettings(String communityId, String groupId, GroupEmailFrequency emailFrequency) {throw new java.lang.UnsupportedOperationException();}
	public static GroupMembershipRequest updateRequestStatus(String communityId, String requestId, GroupMembershipRequestStatus status) {throw new java.lang.UnsupportedOperationException();}
	public static GroupMembershipRequest updateRequestStatus(String communityId, String requestId, GroupMembershipRequestStatus status, String responseMessage) {throw new java.lang.UnsupportedOperationException();}
}
