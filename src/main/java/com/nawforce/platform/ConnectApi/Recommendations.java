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
import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class Recommendations {
	public static RecommendationAudience createRecommendationAudience(String communityId, RecommendationAudienceInput recommendationAudience) {throw new java.lang.UnsupportedOperationException();}
	public static RecommendationAudience createRecommendationAudience(String communityId, String name) {throw new java.lang.UnsupportedOperationException();}
	public static RecommendationDefinition createRecommendationDefinition(String communityId, RecommendationDefinitionInput recommendationDefinition) {throw new java.lang.UnsupportedOperationException();}
	public static RecommendationDefinition createRecommendationDefinition(String communityId, String name, String title, String actionUrl, String actionUrlName, String explanation) {throw new java.lang.UnsupportedOperationException();}
	public static ScheduledRecommendation createScheduledRecommendation(String communityId, ScheduledRecommendationInput scheduledRecommendation) {throw new java.lang.UnsupportedOperationException();}
	public static ScheduledRecommendation createScheduledRecommendation(String communityId, String recommendationDefinitionId, Integer rank, Boolean enabled, String recommendationAudienceId) {throw new java.lang.UnsupportedOperationException();}
	public static ScheduledRecommendation createScheduledRecommendation(String communityId, String recommendationDefinitionId, Integer rank, Boolean enabled, String recommendationAudienceId, RecommendationChannel channel) {throw new java.lang.UnsupportedOperationException();}
	public static void deleteRecommendationAudience(String communityId, String recommendationAudienceId) {throw new java.lang.UnsupportedOperationException();}
	public static void deleteRecommendationDefinition(String communityId, String recommendationDefinitionId) {throw new java.lang.UnsupportedOperationException();}
	public static void deleteRecommendationDefinitionPhoto(String communityId, String recommendationDefinitionId) {throw new java.lang.UnsupportedOperationException();}
	public static void deleteScheduledRecommendation(String communityId, String scheduledRecommendationId, Boolean deleteDefinitionIfLast) {throw new java.lang.UnsupportedOperationException();}
	public static RecommendationAudience getRecommendationAudience(String communityId, String recommendationAudienceId) {throw new java.lang.UnsupportedOperationException();}
	public static UserReferencePage getRecommendationAudienceMembership(String communityId, String recommendationAudienceId) {throw new java.lang.UnsupportedOperationException();}
	public static UserReferencePage getRecommendationAudienceMembership(String communityId, String recommendationAudienceId, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static RecommendationAudiencePage getRecommendationAudiences(String communityId) {throw new java.lang.UnsupportedOperationException();}
	public static RecommendationAudiencePage getRecommendationAudiences(String communityId, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static RecommendationDefinition getRecommendationDefinition(String communityId, String recommendationDefinitionId) {throw new java.lang.UnsupportedOperationException();}
	public static Photo getRecommendationDefinitionPhoto(String communityId, String recommendationDefinitionId) {throw new java.lang.UnsupportedOperationException();}
	public static RecommendationDefinitionPage getRecommendationDefinitions(String communityId) {throw new java.lang.UnsupportedOperationException();}
	public static RecommendationCollection getRecommendationForUser(String communityId, String userId, RecommendationActionType action, String objectId) {throw new java.lang.UnsupportedOperationException();}
	public static RecommendationCollection getRecommendationsForUser(String communityId, String userId, RecommendationActionType action, RecommendationActionType contextAction, String contextObjectId, RecommendationChannel channel, Integer maxResults) {throw new java.lang.UnsupportedOperationException();}
	public static RecommendationCollection getRecommendationsForUser(String communityId, String userId, RecommendationActionType action, RecommendationActionType contextAction, String contextObjectId, Integer maxResults) {throw new java.lang.UnsupportedOperationException();}
	public static RecommendationCollection getRecommendationsForUser(String communityId, String userId, RecommendationActionType action, String objectCategory, RecommendationActionType contextAction, String contextObjectId, RecommendationChannel channel, Integer maxResults) {throw new java.lang.UnsupportedOperationException();}
	public static RecommendationCollection getRecommendationsForUser(String communityId, String userId, RecommendationActionType action, String objectCategory, RecommendationActionType contextAction, String contextObjectId, Integer maxResults) {throw new java.lang.UnsupportedOperationException();}
	public static RecommendationCollection getRecommendationsForUser(String communityId, String userId, RecommendationActionType contextAction, String contextObjectId, RecommendationChannel channel, Integer maxResults) {throw new java.lang.UnsupportedOperationException();}
	public static RecommendationCollection getRecommendationsForUser(String communityId, String userId, RecommendationActionType contextAction, String contextObjectId, Integer maxResults) {throw new java.lang.UnsupportedOperationException();}
	public static ScheduledRecommendation getScheduledRecommendation(String communityId, String scheduledRecommendationId) {throw new java.lang.UnsupportedOperationException();}
	public static ScheduledRecommendationPage getScheduledRecommendations(String communityId) {throw new java.lang.UnsupportedOperationException();}
	public static ScheduledRecommendationPage getScheduledRecommendations(String communityId, RecommendationChannel channel) {throw new java.lang.UnsupportedOperationException();}
	public static void rejectRecommendationForUser(String communityId, String userId, RecommendationActionType action, RecommendedObjectType objectEnum) {throw new java.lang.UnsupportedOperationException();}
	public static void rejectRecommendationForUser(String communityId, String userId, RecommendationActionType action, String objectId) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestGetRecommendationForUser(String communityId, String userId, RecommendationActionType action, String objectId, RecommendationCollection result) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestGetRecommendationsForUser(String communityId, String userId, RecommendationActionType action, RecommendationActionType contextAction, String contextObjectId, RecommendationChannel channel, Integer maxResults, RecommendationCollection result) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestGetRecommendationsForUser(String communityId, String userId, RecommendationActionType action, RecommendationActionType contextAction, String contextObjectId, Integer maxResults, RecommendationCollection result) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestGetRecommendationsForUser(String communityId, String userId, RecommendationActionType action, String objectCategory, RecommendationActionType contextAction, String contextObjectId, RecommendationChannel channel, Integer maxResults, RecommendationCollection result) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestGetRecommendationsForUser(String communityId, String userId, RecommendationActionType action, String objectCategory, RecommendationActionType contextAction, String contextObjectId, Integer maxResults, RecommendationCollection result) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestGetRecommendationsForUser(String communityId, String userId, RecommendationActionType contextAction, String contextObjectId, RecommendationChannel channel, Integer maxResults, RecommendationCollection result) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestGetRecommendationsForUser(String communityId, String userId, RecommendationActionType contextAction, String contextObjectId, Integer maxResults, RecommendationCollection result) {throw new java.lang.UnsupportedOperationException();}
	public static RecommendationAudience updateRecommendationAudience(String communityId, String recommendationAudienceId, RecommendationAudienceInput recommendationAudience) {throw new java.lang.UnsupportedOperationException();}
	public static RecommendationDefinition updateRecommendationDefinition(String communityId, String recommendationDefinitionId, RecommendationDefinitionInput recommendationDefinition) {throw new java.lang.UnsupportedOperationException();}
	public static RecommendationDefinition updateRecommendationDefinition(String communityId, String recommendationDefinitionId, String name, String title, String actionUrl, String actionUrlName, String explanation) {throw new java.lang.UnsupportedOperationException();}
	public static Photo updateRecommendationDefinitionPhoto(String communityId, String recommendationDefinitionId, BinaryInput fileUpload) {throw new java.lang.UnsupportedOperationException();}
	public static Photo updateRecommendationDefinitionPhoto(String communityId, String recommendationDefinitionId, String fileId, Integer versionNumber) {throw new java.lang.UnsupportedOperationException();}
	public static Photo updateRecommendationDefinitionPhotoWithAttributes(String communityId, String recommendationDefinitionId, PhotoInput photo) {throw new java.lang.UnsupportedOperationException();}
	public static Photo updateRecommendationDefinitionPhotoWithAttributes(String communityId, String recommendationDefinitionId, PhotoInput photo, BinaryInput fileUpload) {throw new java.lang.UnsupportedOperationException();}
	public static ScheduledRecommendation updateScheduledRecommendation(String communityId, String scheduledRecommendationId, ScheduledRecommendationInput scheduledRecommendation) {throw new java.lang.UnsupportedOperationException();}
	public static ScheduledRecommendation updateScheduledRecommendation(String communityId, String scheduledRecommendationId, Integer rank, Boolean enabled, String recommendationAudienceId) {throw new java.lang.UnsupportedOperationException();}
}
