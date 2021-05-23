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
public class FeedElementCapabilities {
	public ApprovalCapability approval;
	public AssociatedActionsCapability associatedActions;
	public BannerCapability banner;
	public BookmarksCapability bookmarks;
	public BundleCapability bundle;
	public CanvasCapability canvas;
	public CaseCommentCapability caseComment;
	public ChatterLikesCapability chatterLikes;
	public CloseCapability close;
	public CommentsCapability comments;
	public ContentCapability content;
	public DashboardComponentSnapshotCapability dashboardComponentSnapshot;
	public DirectMessageCapability directMessage;
	public EditCapability edit;
	public EmailMessageCapability emailMessage;
	public EnhancedLinkCapability enhancedLink;
	public ExtensionsCapability extensions;
	public FeedEntityShareCapability feedEntityShare;
	public FilesCapability files;
	public InteractionsCapability interactions;
	public LinkCapability link;
	public MediaReferenceCapability mediaReferences;
	public ModerationCapability moderation;
	public MuteCapability mute;
	public OriginCapability origin;
	public PinCapability pin;
	public PollCapability poll;
	public QuestionAndAnswersCapability questionAndAnswers;
	public ReadByCapability readBy;
	public RecommendationsCapability recommendations;
	public RecordSnapshotCapability recordSnapshot;
	public SocialPostCapability socialPost;
	public StatusCapability status;
	public TopicsCapability topics;
	public TrackedChangesCapability trackedChanges;
	public UpDownVoteCapability upDownVote;

	public FeedElementCapabilities() {throw new java.lang.UnsupportedOperationException();}

	public Boolean equals$(Object obj) {throw new java.lang.UnsupportedOperationException();}
	public Double getBuildVersion() {throw new java.lang.UnsupportedOperationException();}
	public Integer hashCode$() {throw new java.lang.UnsupportedOperationException();}
	public String toString$() {throw new java.lang.UnsupportedOperationException();}
}
