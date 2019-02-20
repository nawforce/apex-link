/*
 * Copyright (c) 2018 FinancialForce.com, inc. All rights reserved.
 */

package com.nawforce.platform.System;

import com.nawforce.platform.Schema.SObjectType;

@SuppressWarnings("unused")
public class List<T> implements Iterable<T> {
    public List() {throw new java.lang.UnsupportedOperationException();}
    public List(List<T> listToCopy) {throw new java.lang.UnsupportedOperationException();}
    public List(Set<T> setToCopy) {throw new java.lang.UnsupportedOperationException();}

    public List<T> clone() {throw new java.lang.UnsupportedOperationException();}
    public void add(T listElement) {throw new java.lang.UnsupportedOperationException();}
    public void add(Integer index, T listElement) {throw new java.lang.UnsupportedOperationException();}
    public void addAll(List<T> fromList) {throw new java.lang.UnsupportedOperationException();}
    public void addAll(Set<T> fromSet) {throw new java.lang.UnsupportedOperationException();}
    public void clear() {throw new java.lang.UnsupportedOperationException();}
    public Boolean contains(T listElement) {throw new java.lang.UnsupportedOperationException();}
    public List<T> deepClone() {throw new java.lang.UnsupportedOperationException();}
    public List<T> deepClone(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
    public List<T> deepClone(Boolean preserveId, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
    public List<T> deepClone(Boolean preserveId, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
    public T get(Integer index) {throw new java.lang.UnsupportedOperationException();}
    public SObjectType getSObjectType() {throw new java.lang.UnsupportedOperationException();}
    public Integer indexOf(T listElement) {throw new java.lang.UnsupportedOperationException();}
    public Iterator<T> iterator() {throw new java.lang.UnsupportedOperationException();}
    public Boolean isEmpty() {throw new java.lang.UnsupportedOperationException();}
    public T remove(Integer index) {throw new java.lang.UnsupportedOperationException();}
    public void set(Integer index, T listElement) {throw new java.lang.UnsupportedOperationException();}
    public Integer size() {throw new java.lang.UnsupportedOperationException();}
    public void sort() {throw new java.lang.UnsupportedOperationException();}
}
