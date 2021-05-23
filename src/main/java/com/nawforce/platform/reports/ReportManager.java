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

package com.nawforce.platform.reports;

import com.nawforce.platform.System.Id;
import com.nawforce.platform.System.List;
import com.nawforce.platform.System.Map;
import com.nawforce.platform.System.String;


@SuppressWarnings("unused")
public class ReportManager {
	public ReportManager() {throw new java.lang.UnsupportedOperationException();}

	public static ReportDescribeResult describeReport(Id reportId) {throw new java.lang.UnsupportedOperationException();}
	public static Map<String, List<FilterOperator>> getDatatypeFilterOperatorMap() {throw new java.lang.UnsupportedOperationException();}
	public static ReportInstance getReportInstance(Id instanceId) {throw new java.lang.UnsupportedOperationException();}
	public static List<ReportInstance> getReportInstances(Id reportId) {throw new java.lang.UnsupportedOperationException();}
	public static ReportInstance runAsyncReport(Id reportId) {throw new java.lang.UnsupportedOperationException();}
	public static ReportInstance runAsyncReport(Id reportId, com.nawforce.platform.System.Boolean includeDetails) {throw new java.lang.UnsupportedOperationException();}
	public static ReportInstance runAsyncReport(Id reportId, ReportMetadata rmData) {throw new java.lang.UnsupportedOperationException();}
	public static ReportInstance runAsyncReport(Id reportId, ReportMetadata rmData, com.nawforce.platform.System.Boolean includeDetails) {throw new java.lang.UnsupportedOperationException();}
	public static ReportResults runReport(Id reportId) {throw new java.lang.UnsupportedOperationException();}
	public static ReportResults runReport(Id reportId, com.nawforce.platform.System.Boolean includeDetails) {throw new java.lang.UnsupportedOperationException();}
	public static ReportResults runReport(Id reportId, ReportMetadata rmData) {throw new java.lang.UnsupportedOperationException();}
	public static ReportResults runReport(Id reportId, ReportMetadata rmData, com.nawforce.platform.System.Boolean includeDetails) {throw new java.lang.UnsupportedOperationException();}
}
