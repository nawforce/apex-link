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

package com.nawforce.platform.Approval;

import com.nawforce.platform.Database.Error;
import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Id;
import com.nawforce.platform.System.List;
import com.nawforce.platform.System.String;


@SuppressWarnings("unused")
public class ProcessResult {
	public List<Id> ActorIds;
	public String EntityId;
	public List<Error> Errors;
	public String InstanceId;
	public String InstanceStatus;
	public List<Id> NewWorkitemIds;
	public Boolean Success;

	public List<Id> getActorIds() {throw new java.lang.UnsupportedOperationException();}
	public String getEntityId() {throw new java.lang.UnsupportedOperationException();}
	public List<Error> getErrors() {throw new java.lang.UnsupportedOperationException();}
	public String getInstanceId() {throw new java.lang.UnsupportedOperationException();}
	public String getInstanceStatus() {throw new java.lang.UnsupportedOperationException();}
	public List<Id> getNewWorkitemIds() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isSuccess() {throw new java.lang.UnsupportedOperationException();}
}
