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
package io.github.nawforce.platform.System;

@SuppressWarnings("unused")
public class Database {
	public static LeadConvertResult convertLead(LeadConvert leadConvert) {throw new java.lang.UnsupportedOperationException();}
	public static LeadConvertResult convertLead(LeadConvert leadConvert, Object DmlOptions) {throw new java.lang.UnsupportedOperationException();}
	public static LeadConvertResult convertLead(LeadConvert leadConvert, Boolean allOrNothing) {throw new java.lang.UnsupportedOperationException();}
	public static List<LeadConvertResult> convertLead(List<LeadConvert> leadConverts) {throw new java.lang.UnsupportedOperationException();}
	public static List<LeadConvertResult> convertLead(List<LeadConvert> leadConverts, Object DmlOptions) {throw new java.lang.UnsupportedOperationException();}
	public static List<LeadConvertResult> convertLead(List<LeadConvert> leadConverts, Boolean allOrNothing) {throw new java.lang.UnsupportedOperationException();}
	public static Integer countQuery(String query) {throw new java.lang.UnsupportedOperationException();}
	public static DeleteResult delete(Id id) {throw new java.lang.UnsupportedOperationException();}
	public static DeleteResult delete(Id id, Boolean allOrNothing) {throw new java.lang.UnsupportedOperationException();}
	public static List<DeleteResult> delete(List<Id> ids) {throw new java.lang.UnsupportedOperationException();}
	public static List<DeleteResult> delete(List<Id> ids, Boolean allOrNothing) {throw new java.lang.UnsupportedOperationException();}
	public static List<DeleteResult> delete(List<SObject> sobjects) {throw new java.lang.UnsupportedOperationException();}
	public static List<DeleteResult> delete(List<SObject> sobjects, Boolean allOrNothing) {throw new java.lang.UnsupportedOperationException();}
	public static DeleteResult delete(SObject sobject) {throw new java.lang.UnsupportedOperationException();}
	public static DeleteResult delete(SObject sobject, Boolean allOrNothing) {throw new java.lang.UnsupportedOperationException();}
	public static List<DeleteResult> deleteAsync(List<SObject> sobjects) {throw new java.lang.UnsupportedOperationException();}
	public static List<DeleteResult> deleteAsync(List<SObject> sobjects, Object callback) {throw new java.lang.UnsupportedOperationException();}
	public static DeleteResult deleteAsync(SObject sobject) {throw new java.lang.UnsupportedOperationException();}
	public static DeleteResult deleteAsync(SObject sobject, Object callback) {throw new java.lang.UnsupportedOperationException();}
	public static List<DeleteResult> deleteImmediate(List<SObject> sobjects) {throw new java.lang.UnsupportedOperationException();}
	public static DeleteResult deleteImmediate(SObject sobject) {throw new java.lang.UnsupportedOperationException();}
	public static List<EmptyRecycleBinResult> emptyRecycleBin(List<Id> ids) {throw new java.lang.UnsupportedOperationException();}
	public static List<EmptyRecycleBinResult> emptyRecycleBin(List<SObject> sobjects) {throw new java.lang.UnsupportedOperationException();}
	public static EmptyRecycleBinResult emptyRecycleBin(SObject sobject) {throw new java.lang.UnsupportedOperationException();}
	public static String executeBatch(Object batchable) {throw new java.lang.UnsupportedOperationException();}
	public static String executeBatch(Object batchable, Integer batchSize) {throw new java.lang.UnsupportedOperationException();}
	public static DeleteResult getAsyncDeleteResult(Object deleteResult) {throw new java.lang.UnsupportedOperationException();}
	public static DeleteResult getAsyncDeleteResult(String asyncLocator) {throw new java.lang.UnsupportedOperationException();}
	public static String getAsyncLocator(Object result) {throw new java.lang.UnsupportedOperationException();}
	public static SaveResult getAsyncSaveResult(Object saveResult) {throw new java.lang.UnsupportedOperationException();}
	public static SaveResult getAsyncSaveResult(String asyncLocator) {throw new java.lang.UnsupportedOperationException();}
	public static GetDeletedResult getDeleted(String sobjectType, Datetime startDate, Datetime endDate) {throw new java.lang.UnsupportedOperationException();}
	public static QueryLocator getQueryLocator(List<SObject> query) {throw new java.lang.UnsupportedOperationException();}
	public static QueryLocator getQueryLocator(String query) {throw new java.lang.UnsupportedOperationException();}
	public static GetUpdatedResult getUpdated(String sobjectType, Datetime startDate, Datetime endDate) {throw new java.lang.UnsupportedOperationException();}
	public static List<SaveResult> insert(List<SObject> sobjects) {throw new java.lang.UnsupportedOperationException();}
	public static List<SaveResult> insert(List<SObject> sobjects, Object DmlOptions) {throw new java.lang.UnsupportedOperationException();}
	public static List<SaveResult> insert(List<SObject> sobjects, Boolean allOrNothing) {throw new java.lang.UnsupportedOperationException();}
	public static SaveResult insert(SObject sobject) {throw new java.lang.UnsupportedOperationException();}
	public static SaveResult insert(SObject sobject, Object DmlOptions) {throw new java.lang.UnsupportedOperationException();}
	public static SaveResult insert(SObject sobject, Boolean allOrNothing) {throw new java.lang.UnsupportedOperationException();}
	public static List<SaveResult> insertAsync(List<SObject> sobjects) {throw new java.lang.UnsupportedOperationException();}
	public static List<SaveResult> insertAsync(List<SObject> sobjects, Object callback) {throw new java.lang.UnsupportedOperationException();}
	public static SaveResult insertAsync(SObject sobject) {throw new java.lang.UnsupportedOperationException();}
	public static SaveResult insertAsync(SObject sobject, Object callback) {throw new java.lang.UnsupportedOperationException();}
	public static List<SaveResult> insertImmediate(List<SObject> sobjects) {throw new java.lang.UnsupportedOperationException();}
	public static SaveResult insertImmediate(SObject sobject) {throw new java.lang.UnsupportedOperationException();}
	public static MergeResult merge(SObject master, Id duplicate) {throw new java.lang.UnsupportedOperationException();}
	public static MergeResult merge(SObject master, Id duplicate, Boolean allOrNothing) {throw new java.lang.UnsupportedOperationException();}
	public static List<MergeResult> merge(SObject master, List<Id> duplicates) {throw new java.lang.UnsupportedOperationException();}
	public static List<MergeResult> merge(SObject master, List<Id> duplicates, Boolean allOrNothing) {throw new java.lang.UnsupportedOperationException();}
	public static List<MergeResult> merge(SObject master, List<SObject> duplicates) {throw new java.lang.UnsupportedOperationException();}
	public static List<MergeResult> merge(SObject master, List<SObject> duplicates, Boolean allOrNothing) {throw new java.lang.UnsupportedOperationException();}
	public static MergeResult merge(SObject master, SObject duplicate) {throw new java.lang.UnsupportedOperationException();}
	public static MergeResult merge(SObject master, SObject duplicate, Boolean allOrNothing) {throw new java.lang.UnsupportedOperationException();}
	public static List<SObject> query(String query) {throw new java.lang.UnsupportedOperationException();}
	public static void rollback(Savepoint savepoint) {throw new java.lang.UnsupportedOperationException();}
	public static Savepoint setSavepoint() {throw new java.lang.UnsupportedOperationException();}
	public static UndeleteResult undelete(Id id) {throw new java.lang.UnsupportedOperationException();}
	public static UndeleteResult undelete(Id id, Boolean allOrNothing) {throw new java.lang.UnsupportedOperationException();}
	public static List<UndeleteResult> undelete(List<Id> ids) {throw new java.lang.UnsupportedOperationException();}
	public static List<UndeleteResult> undelete(List<Id> ids, Boolean allOrNothing) {throw new java.lang.UnsupportedOperationException();}
	public static List<UndeleteResult> undelete(List<SObject> sobjects) {throw new java.lang.UnsupportedOperationException();}
	public static List<UndeleteResult> undelete(List<SObject> sobjects, Boolean allOrNothing) {throw new java.lang.UnsupportedOperationException();}
	public static UndeleteResult undelete(SObject sobject) {throw new java.lang.UnsupportedOperationException();}
	public static UndeleteResult undelete(SObject sobject, Boolean allOrNothing) {throw new java.lang.UnsupportedOperationException();}
	public static List<SaveResult> update(List<SObject> sobjects) {throw new java.lang.UnsupportedOperationException();}
	public static List<SaveResult> update(List<SObject> sobjects, Object allOrNothing) {throw new java.lang.UnsupportedOperationException();}
	public static List<SaveResult> update(List<SObject> sobjects, Boolean allOrNothing) {throw new java.lang.UnsupportedOperationException();}
	public static SaveResult update(SObject sobject) {throw new java.lang.UnsupportedOperationException();}
	public static SaveResult update(SObject sobject, Object allOrNothing) {throw new java.lang.UnsupportedOperationException();}
	public static SaveResult update(SObject sobject, Boolean allOrNothing) {throw new java.lang.UnsupportedOperationException();}
	public static List<SaveResult> updateAsync(List<SObject> sobjects) {throw new java.lang.UnsupportedOperationException();}
	public static List<SaveResult> updateAsync(List<SObject> sobjects, Object callback) {throw new java.lang.UnsupportedOperationException();}
	public static SaveResult updateAsync(SObject sobject) {throw new java.lang.UnsupportedOperationException();}
	public static SaveResult updateAsync(SObject sobject, Object callback) {throw new java.lang.UnsupportedOperationException();}
	public static List<SaveResult> updateImmediate(List<SObject> sobjects) {throw new java.lang.UnsupportedOperationException();}
	public static SaveResult updateImmediate(SObject sobject) {throw new java.lang.UnsupportedOperationException();}
	public static List<UpsertResult> upsert(List<SObject> sobjects) {throw new java.lang.UnsupportedOperationException();}
	public static List<UpsertResult> upsert(List<SObject> sobjects, Boolean allOrNothing) {throw new java.lang.UnsupportedOperationException();}
	public static List<UpsertResult> upsert(List<SObject> sobjects, Schema.SObjectField field) {throw new java.lang.UnsupportedOperationException();}
	public static List<UpsertResult> upsert(List<SObject> sobjects, Schema.SObjectField field, Boolean allOrNothing) {throw new java.lang.UnsupportedOperationException();}
	public static UpsertResult upsert(SObject sobject) {throw new java.lang.UnsupportedOperationException();}
	public static UpsertResult upsert(SObject sobject, Boolean allOrNothing) {throw new java.lang.UnsupportedOperationException();}
	public static UpsertResult upsert(SObject sobject, Schema.SObjectField field) {throw new java.lang.UnsupportedOperationException();}
	public static UpsertResult upsert(SObject sobject, Schema.SObjectField field, Boolean allOrNothing) {throw new java.lang.UnsupportedOperationException();}
}
