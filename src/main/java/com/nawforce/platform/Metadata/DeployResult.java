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

package com.nawforce.platform.Metadata;

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.String;
import com.nawforce.platform.System.*;

@SuppressWarnings("unused")
public class DeployResult {
	public String canceledBy;
	public String canceledByName;
	public Boolean checkOnly;
	public Datetime completedDate;
	public String createdBy;
	public String createdByName;
	public Datetime createdDate;
	public DeployDetails details;
	public Boolean done;
	public String errorMessage;
	public StatusCode errorStatusCode;
	public Id id;
	public Boolean ignoreWarnings;
	public Datetime lastModifiedDate;
	public List<DeployMessage> messages;
	public Integer numberComponentErrors;
	public Integer numberComponentsDeployed;
	public Integer numberComponentsTotal;
	public Boolean rollbackOnError;
	public Datetime startDate;
	public String stateDetail;
	public DeployStatus status;
	public Boolean success;

	public DeployResult() {throw new java.lang.UnsupportedOperationException();}
}
