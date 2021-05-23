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

package com.nawforce.platform.Wave;

import com.nawforce.platform.ConnectApi.LiteralJson;
import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.List;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class QueryNode {
	public String build(String streamName) {throw new java.lang.UnsupportedOperationException();}
	public QueryNode cap(Integer cap) {throw new java.lang.UnsupportedOperationException();}
	public LiteralJson execute(String streamName) {throw new java.lang.UnsupportedOperationException();}
	public QueryNode filter(List<String> filterConditions) {throw new java.lang.UnsupportedOperationException();}
	public QueryNode filter(String filterCondition) {throw new java.lang.UnsupportedOperationException();}
	public QueryNode foreach(List<ProjectionNode> projections) {throw new java.lang.UnsupportedOperationException();}
	public QueryNode group() {throw new java.lang.UnsupportedOperationException();}
	public QueryNode group(List<String> groups) {throw new java.lang.UnsupportedOperationException();}
	public QueryNode order(List<List<String>> orders) {throw new java.lang.UnsupportedOperationException();}
}
