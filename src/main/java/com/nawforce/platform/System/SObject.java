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

package com.nawforce.platform.System;

import com.nawforce.platform.Database.DMLOptions;
import com.nawforce.platform.Database.Error;
import com.nawforce.platform.SObjects.*;
import com.nawforce.platform.Schema.SObjectField;
import com.nawforce.platform.Schema.SObjectType;

@SuppressWarnings("unused")
public class SObject {
	// Future: Slim this set, move custom object specific to CustomSObject$
	public Id Id;
	public Id OwnerId;
	public String Name;
	public UserGroup Owner;
	public UserRecordAccess UserRecordAccess;
	public List<Task> Tasks;					// Future: Only if Allow Activities is selected
	public RecordType RecordType;
	public String CurrencyIsoCode;
	public Datetime LastReferencedDate;
	public Datetime LastViewedDate;
	public Datetime LastActivityDate;		// Future: Only if Allow Activities is selected

	public void addError(Exception exceptionError) {throw new java.lang.UnsupportedOperationException();}
	public void addError(Exception exceptionError, Boolean escape) {throw new java.lang.UnsupportedOperationException();}
	public void addError(String msg) {throw new java.lang.UnsupportedOperationException();}
	public void addError(String msg, Boolean escape) {throw new java.lang.UnsupportedOperationException();}
	public void addError(String fieldName, String msg) {throw new java.lang.UnsupportedOperationException();}
	public void addError(String fieldName, String msg, Boolean escape) {throw new java.lang.UnsupportedOperationException();}
	public void addError(SObjectField fieldToken, String msg) {throw new java.lang.UnsupportedOperationException();}
	public void addError(SObjectField fieldToken, String msg, Boolean escape) {throw new java.lang.UnsupportedOperationException();}
	public void clear() {throw new java.lang.UnsupportedOperationException();}
	public Object get(SObjectField field) {throw new java.lang.UnsupportedOperationException();}
	public Object get(String field) {throw new java.lang.UnsupportedOperationException();}
	public Map<String,SObject> getAll() {throw new java.lang.UnsupportedOperationException();}
	public Id getCloneSourceId() {throw new java.lang.UnsupportedOperationException();}
	public List<Error> getErrors() {throw new java.lang.UnsupportedOperationException();}
	public SObject getInstance() {throw new java.lang.UnsupportedOperationException();}
	public SObject getInstance(String id) {throw new java.lang.UnsupportedOperationException();}
	public DMLOptions getOptions() {throw new java.lang.UnsupportedOperationException();}
	public SObject getOrgDefaults() {throw new java.lang.UnsupportedOperationException();}
	public Map<String, SObject> getPopulatedFieldsAsMap() {throw new java.lang.UnsupportedOperationException();}
	public String getQuickActionName() {throw new java.lang.UnsupportedOperationException();}
	public SObject getSObject(SObjectField field) {throw new java.lang.UnsupportedOperationException();}
	public SObject getSObject(String field) {throw new java.lang.UnsupportedOperationException();}
	public SObjectType getSObjectType() {throw new java.lang.UnsupportedOperationException();}
	public List<SObject> getSObjects(SObjectField field) {throw new java.lang.UnsupportedOperationException();}
	public List<SObject> getSObjects(String field) {throw new java.lang.UnsupportedOperationException();}
	public SObject getValues(String id) {throw new java.lang.UnsupportedOperationException();}
	public Boolean hasErrors() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isClone() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isSet(SObjectField field) {throw new java.lang.UnsupportedOperationException();}
	public Boolean isSet(String fieldName) {throw new java.lang.UnsupportedOperationException();}
	public Object put(SObjectField field, Object value) {throw new java.lang.UnsupportedOperationException();}
	public Object put(String field, Object value) {throw new java.lang.UnsupportedOperationException();}
	public SObject putSObject(SObjectField field, SObject value) {throw new java.lang.UnsupportedOperationException();}
	public SObject putSObject(String field, SObject value) {throw new java.lang.UnsupportedOperationException();}
	public void recalculateFormulas() {throw new java.lang.UnsupportedOperationException();}
	public void setOptions(Object options) {throw new java.lang.UnsupportedOperationException();}

	public SObject clone$() {throw new java.lang.UnsupportedOperationException();}
	public SObject clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public SObject clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public SObject clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public SObject clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
