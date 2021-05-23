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

package com.nawforce.platform.Datacloud;

import com.nawforce.platform.Database.Error;
import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.List;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class MatchResult {
	public String EntityType;
	public List<Error> Errors;
	public String MatchEngine;
	public List<MatchRecord> MatchRecords;
	public String Rule;
	public Integer Size;
	public Boolean Success;

	public String getEntityType() {throw new java.lang.UnsupportedOperationException();}
	public List<Error> getErrors() {throw new java.lang.UnsupportedOperationException();}
	public String getMatchEngine() {throw new java.lang.UnsupportedOperationException();}
	public List<MatchRecord> getMatchRecords() {throw new java.lang.UnsupportedOperationException();}
	public String getRule() {throw new java.lang.UnsupportedOperationException();}
	public Integer getSize() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isSuccess() {throw new java.lang.UnsupportedOperationException();}
}
