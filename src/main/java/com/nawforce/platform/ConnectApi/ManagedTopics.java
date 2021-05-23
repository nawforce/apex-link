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
public class ManagedTopics {
	public static ManagedTopic createManagedTopic(String communityId, String recordId, ManagedTopicType managedTopicType) {throw new java.lang.UnsupportedOperationException();}
	public static ManagedTopic createManagedTopic(String communityId, String recordId, ManagedTopicType managedTopicType, String parentId) {throw new java.lang.UnsupportedOperationException();}
	public static ManagedTopic createManagedTopicByName(String communityId, String name, ManagedTopicType managedTopicType) {throw new java.lang.UnsupportedOperationException();}
	public static ManagedTopic createManagedTopicByName(String communityId, String name, ManagedTopicType managedTopicType, String parentId) {throw new java.lang.UnsupportedOperationException();}
	public static void deleteManagedTopic(String communityId, String managedTopicId) {throw new java.lang.UnsupportedOperationException();}
	public static ManagedTopic getManagedTopic(String communityId, String managedTopicId) {throw new java.lang.UnsupportedOperationException();}
	public static ManagedTopic getManagedTopic(String communityId, String managedTopicId, Integer depth) {throw new java.lang.UnsupportedOperationException();}
	public static ManagedTopicCollection getManagedTopics(String communityId) {throw new java.lang.UnsupportedOperationException();}
	public static ManagedTopicCollection getManagedTopics(String communityId, ManagedTopicType managedTopicType) {throw new java.lang.UnsupportedOperationException();}
	public static ManagedTopicCollection getManagedTopics(String communityId, ManagedTopicType managedTopicType, Integer depth) {throw new java.lang.UnsupportedOperationException();}
	public static ManagedTopicCollection getManagedTopics(String communityId, ManagedTopicType managedTopicType, Integer pageParam, Integer pageSize) {throw new java.lang.UnsupportedOperationException();}
	public static ManagedTopicCollection getManagedTopics(String communityId, ManagedTopicType managedTopicType, List<String> recordIds, Integer depth) {throw new java.lang.UnsupportedOperationException();}
	public static ManagedTopicCollection getManagedTopics(String communityId, ManagedTopicType managedTopicType, String recordId, Integer depth) {throw new java.lang.UnsupportedOperationException();}
	public static ManagedTopicCollection reorderManagedTopics(String communityId, ManagedTopicPositionCollectionInput managedTopicPositionCollection) {throw new java.lang.UnsupportedOperationException();}
}
