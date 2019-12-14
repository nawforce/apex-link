/*
 [The "BSD licence"]
 Copyright (c) 2019 Kevin Jones
 All rights reserved.

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

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.nawforce.platform.reports;

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.String;
import com.nawforce.platform.System.*;


@SuppressWarnings("unused")
public class ReportMetadata {
	public ReportMetadata() {throw new java.lang.UnsupportedOperationException();}
	public ReportMetadata(String name, String developerName, Id id, List<GroupingInfo> groupingsAcross, List<GroupingInfo> groupingsDown, List<String> aggregates, List<BucketField> buckets, List<String> detailColumns, String reportBooleanFilter, List<ReportFilter> reportFilters, List<String> historicalSnapshotDates, ReportFormat reportFormat, ReportType reportType, String currencyCode, String scope, String division, List<SortColumn> sortBy, StandardDateFilter standardDateFilter, Boolean hasDetailRows, Boolean hasRecordCount, List<StandardFilter> standardFilters, String description, Boolean showSubtotals, Boolean showGrandTotal, TopRows topRows, Map<String,ReportCsf> customSummaryFormula, List<CrossFilter> crossFilters) {throw new java.lang.UnsupportedOperationException();}

	public List<String> getAggregates() {throw new java.lang.UnsupportedOperationException();}
	public List<BucketField> getBuckets() {throw new java.lang.UnsupportedOperationException();}
	public List<CrossFilter> getCrossFilters() {throw new java.lang.UnsupportedOperationException();}
	public String getCurrencyCode() {throw new java.lang.UnsupportedOperationException();}
	public Map<String,ReportCsf> getCustomSummaryFormula() {throw new java.lang.UnsupportedOperationException();}
	public String getDescription() {throw new java.lang.UnsupportedOperationException();}
	public List<String> getDetailColumns() {throw new java.lang.UnsupportedOperationException();}
	public String getDeveloperName() {throw new java.lang.UnsupportedOperationException();}
	public String getDivision() {throw new java.lang.UnsupportedOperationException();}
	public List<GroupingInfo> getGroupingsAcross() {throw new java.lang.UnsupportedOperationException();}
	public List<GroupingInfo> getGroupingsDown() {throw new java.lang.UnsupportedOperationException();}
	public Boolean getHasDetailRows() {throw new java.lang.UnsupportedOperationException();}
	public Boolean getHasRecordCount() {throw new java.lang.UnsupportedOperationException();}
	public List<String> getHistoricalSnapshotDates() {throw new java.lang.UnsupportedOperationException();}
	public Id getId() {throw new java.lang.UnsupportedOperationException();}
	public String getName() {throw new java.lang.UnsupportedOperationException();}
	public String getReportBooleanFilter() {throw new java.lang.UnsupportedOperationException();}
	public List<ReportFilter> getReportFilters() {throw new java.lang.UnsupportedOperationException();}
	public ReportFormat getReportFormat() {throw new java.lang.UnsupportedOperationException();}
	public ReportType getReportType() {throw new java.lang.UnsupportedOperationException();}
	public String getScope() {throw new java.lang.UnsupportedOperationException();}
	public Boolean getShowGrandTotal() {throw new java.lang.UnsupportedOperationException();}
	public Boolean getShowSubtotals() {throw new java.lang.UnsupportedOperationException();}
	public List<SortColumn> getSortBy() {throw new java.lang.UnsupportedOperationException();}
	public StandardDateFilter getStandardDateFilter() {throw new java.lang.UnsupportedOperationException();}
	public List<StandardFilter> getStandardFilters() {throw new java.lang.UnsupportedOperationException();}
	public TopRows getTopRows() {throw new java.lang.UnsupportedOperationException();}
	public void setAggregates(List<String> aggregates) {throw new java.lang.UnsupportedOperationException();}
	public void setBuckets(List<BucketField> buckets) {throw new java.lang.UnsupportedOperationException();}
	public void setCrossFilters(List<CrossFilter> crossFilters) {throw new java.lang.UnsupportedOperationException();}
	public void setCurrencyCode(String currencyCode) {throw new java.lang.UnsupportedOperationException();}
	public void setCustomSummaryFormula(Map<String,ReportCsf> customSummaryFormula) {throw new java.lang.UnsupportedOperationException();}
	public void setDescription(String description) {throw new java.lang.UnsupportedOperationException();}
	public void setDetailColumns(List<String> detailColumns) {throw new java.lang.UnsupportedOperationException();}
	public void setDeveloperName(String developerName) {throw new java.lang.UnsupportedOperationException();}
	public void setDivision(String division) {throw new java.lang.UnsupportedOperationException();}
	public void setGroupingsAcross(List<GroupingInfo> groupingsAcross) {throw new java.lang.UnsupportedOperationException();}
	public void setGroupingsDown(List<GroupingInfo> groupingsDown) {throw new java.lang.UnsupportedOperationException();}
	public void setHasDetailRows(Boolean hasDetailRows) {throw new java.lang.UnsupportedOperationException();}
	public void setHasRecordCount(Boolean hasRecordCount) {throw new java.lang.UnsupportedOperationException();}
	public void setHistoricalSnapshotDates(List<String> historicalSnapshotDates) {throw new java.lang.UnsupportedOperationException();}
	public void setId(Id id) {throw new java.lang.UnsupportedOperationException();}
	public void setName(String name) {throw new java.lang.UnsupportedOperationException();}
	public void setReportBooleanFilter(String reportBooleanFilter) {throw new java.lang.UnsupportedOperationException();}
	public void setReportFilters(List<ReportFilter> reportFilters) {throw new java.lang.UnsupportedOperationException();}
	public void setReportFormat(String value) {throw new java.lang.UnsupportedOperationException();}
	public void setReportFormat(ReportFormat reportFormat) {throw new java.lang.UnsupportedOperationException();}
	public void setReportType(ReportType reportType) {throw new java.lang.UnsupportedOperationException();}
	public void setScope(String scope) {throw new java.lang.UnsupportedOperationException();}
	public void setShowGrandTotal(Boolean showGrandTotal) {throw new java.lang.UnsupportedOperationException();}
	public void setShowSubtotals(Boolean showSubtotals) {throw new java.lang.UnsupportedOperationException();}
	public void setSortBy(List<SortColumn> sortBy) {throw new java.lang.UnsupportedOperationException();}
	public void setStandardDateFilter(StandardDateFilter standardDateFilter) {throw new java.lang.UnsupportedOperationException();}
	public void setStandardFilters(List<StandardFilter> standardFilters) {throw new java.lang.UnsupportedOperationException();}
	public void setTopRows(TopRows topRows) {throw new java.lang.UnsupportedOperationException();}
	public String toString$() {throw new java.lang.UnsupportedOperationException();}
}
