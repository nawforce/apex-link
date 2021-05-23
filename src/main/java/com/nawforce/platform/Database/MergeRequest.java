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

package com.nawforce.platform.Database;

import com.nawforce.platform.Datacloud.AdditionalInformationMap;
import com.nawforce.platform.System.List;
import com.nawforce.platform.System.SObject;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class MergeRequest {
	public List<com.nawforce.platform.Datacloud.AdditionalInformationMap> AdditionalInformationMap;
	public SObject MasterRecord;
	public List<String> RecordToMergeIds;

	public MergeRequest() {throw new java.lang.UnsupportedOperationException();}

	public List<AdditionalInformationMap> getAdditionalInformationMap() {throw new java.lang.UnsupportedOperationException();}
	public SObject getMasterRecord() {throw new java.lang.UnsupportedOperationException();}
	public List<String> getRecordToMergeIds() {throw new java.lang.UnsupportedOperationException();}
	public void setAdditionalInformationMap(List<AdditionalInformationMap> param1) {throw new java.lang.UnsupportedOperationException();}
	public void setMasterRecord(SObject param1) {throw new java.lang.UnsupportedOperationException();}
	public void setRecordToMergeIds(List<String> param1) {throw new java.lang.UnsupportedOperationException();}
}
