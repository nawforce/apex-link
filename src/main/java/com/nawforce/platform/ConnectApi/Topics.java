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
import com.nawforce.platform.System.List;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class Topics {
	public static Topic assignTopic(String communityId, String recordId, String topicId) {throw new java.lang.UnsupportedOperationException();}
	public static Topic assignTopicByName(String communityId, String recordId, String topicName) {throw new java.lang.UnsupportedOperationException();}
	public static Topic createTopic(String communityId, String name, String description) {throw new java.lang.UnsupportedOperationException();}
	public static TopicPage createTopicDataCategoryRules(String communityId, String dataCategoryGroup, String dataCategory, TopicNamesInput topicNames) {throw new java.lang.UnsupportedOperationException();}
	public static void deleteTopic(String communityId, String topicId) {throw new java.lang.UnsupportedOperationException();}
	public static ChatterGroupSummaryPage getGroupsRecentlyTalkingAboutTopic(String communityId, String topicId) {throw new java.lang.UnsupportedOperationException();}
	public static TopicPage getRecentlyTalkingAboutTopicsForGroup(String communityId, String groupId) {throw new java.lang.UnsupportedOperationException();}
	public static TopicPage getRecentlyTalkingAboutTopicsForUser(String communityId, String userId) {throw new java.lang.UnsupportedOperationException();}
	public static TopicPage getRelatedTopics(String communityId, String topicId) {throw new java.lang.UnsupportedOperationException();}
	public static Topic getTopic(String communityId, String topicId) {throw new java.lang.UnsupportedOperationException();}
	public static TopicPage getTopicDataCategoryRules(String communityId, String dataCategoryGroup, String dataCategory) {throw new java.lang.UnsupportedOperationException();}
	public static TopicSuggestionPage getTopicSuggestions(String communityId, String recordId) {throw new java.lang.UnsupportedOperationException();}
	public static TopicSuggestionPage getTopicSuggestions(String communityId, String recordId, Integer maxResults) {throw new java.lang.UnsupportedOperationException();}
	public static TopicSuggestionPage getTopicSuggestionsForText(String communityId, String text) {throw new java.lang.UnsupportedOperationException();}
	public static TopicSuggestionPage getTopicSuggestionsForText(String communityId, String text, Integer maxResults) {throw new java.lang.UnsupportedOperationException();}
	public static TopicPage getTopics(String communityId) {throw new java.lang.UnsupportedOperationException();}
	public static TopicPage getTopics(String communityId, TopicSort sortParam) {throw new java.lang.UnsupportedOperationException();}
	public static TopicPage getTopics(String communityId, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static TopicPage getTopics(String communityId, Integer pageParam, Integer pageSize, TopicSort sortParam) {throw new java.lang.UnsupportedOperationException();}
	public static TopicPage getTopics(String communityId, String q, Boolean exactMatch) {throw new java.lang.UnsupportedOperationException();}
	public static TopicPage getTopics(String communityId, String q, TopicSort sortParam) {throw new java.lang.UnsupportedOperationException();}
	public static TopicPage getTopics(String communityId, String q, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static TopicPage getTopics(String communityId, String q, Integer pageParam, Integer pageSize, TopicSort sortParam) {throw new java.lang.UnsupportedOperationException();}
	public static TopicPage getTopics(String communityId, String recordId) {throw new java.lang.UnsupportedOperationException();}
	public static TopicPage getTopicsOrFallBackToRenamedTopics(String communityId, String q, Boolean exactMatch, Boolean fallBackToRenamedTopics) {throw new java.lang.UnsupportedOperationException();}
	public static TopicPage getTrendingTopics(String communityId) {throw new java.lang.UnsupportedOperationException();}
	public static TopicPage getTrendingTopics(String communityId, Integer maxResults) {throw new java.lang.UnsupportedOperationException();}
	public static Topic mergeTopics(String communityId, String topicId, List<String> idsToMerge) {throw new java.lang.UnsupportedOperationException();}
	public static TopicPage reassignTopicDataCategoryRules(String communityId, String dataCategoryGroup, String dataCategory, TopicNamesInput topicNames) {throw new java.lang.UnsupportedOperationException();}
	public static TopicPage reassignTopicsByName(String communityId, String recordId, TopicNamesInput topicNames) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestGetGroupsRecentlyTalkingAboutTopic(String communityId, String topicId, ChatterGroupSummaryPage result) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestGetRecentlyTalkingAboutTopicsForGroup(String communityId, String groupId, TopicPage result) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestGetRecentlyTalkingAboutTopicsForUser(String communityId, String userId, TopicPage result) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestGetRelatedTopics(String communityId, String topicId, TopicPage result) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestGetTopicSuggestions(String communityId, String recordId, TopicSuggestionPage result) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestGetTopicSuggestions(String communityId, String recordId, Integer maxResults, TopicSuggestionPage result) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestGetTopicSuggestionsForText(String communityId, String text, TopicSuggestionPage result) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestGetTopicSuggestionsForText(String communityId, String text, Integer maxResults, TopicSuggestionPage result) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestGetTrendingTopics(String communityId, TopicPage result) {throw new java.lang.UnsupportedOperationException();}
	public static void setTestGetTrendingTopics(String communityId, Integer maxResults, TopicPage result) {throw new java.lang.UnsupportedOperationException();}
	public static void unassignTopic(String communityId, String recordId, String topicId) {throw new java.lang.UnsupportedOperationException();}
	public static Topic updateTopic(String communityId, String topicId, TopicInput topic) {throw new java.lang.UnsupportedOperationException();}
	public static TopicPage updateTopicsForArticlesInDataCategory(String communityId, String dataCategoryGroup, String dataCategory, ArticleTopicAssignmentJobInput articleTopicAssignmentJob) {throw new java.lang.UnsupportedOperationException();}
}
