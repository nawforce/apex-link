/*
 * Copyright (c) 2018 FinancialForce.com, inc. All rights reserved.
 */

package io.github.nawforce.platform.System;

import com.financialforce.typex.platform.Schema.SObjectType;

@SuppressWarnings("unused")
public abstract class Map<K, V> {
    Map() {throw new java.lang.UnsupportedOperationException();}
    Map(Map<K,V> mapToCopy) {throw new java.lang.UnsupportedOperationException();}
    Map(List<SObject> recordList) {throw new java.lang.UnsupportedOperationException();}

    public Map<K, V> clone() {throw new java.lang.UnsupportedOperationException();}
    public void clear() {throw new java.lang.UnsupportedOperationException();}
    public Boolean containsKey(K key) {throw new java.lang.UnsupportedOperationException();}
    public Map<K, V> deepClone() {throw new java.lang.UnsupportedOperationException();}
    public V get(K key) {throw new java.lang.UnsupportedOperationException();}
    public SObjectType getSObjectType() {throw new java.lang.UnsupportedOperationException();}
    public Boolean isEmpty() {throw new java.lang.UnsupportedOperationException();}
    public Set<K> keySet() {throw new java.lang.UnsupportedOperationException();}
    public V put(K key, V value) {throw new java.lang.UnsupportedOperationException();}
    public void putAll(Map<K, V> fromMap) {throw new java.lang.UnsupportedOperationException();}
    public void putAll(List<SObject> sobjectArray) {throw new java.lang.UnsupportedOperationException();}
    public V remove(K key) {throw new java.lang.UnsupportedOperationException();}
    public Integer size() {throw new java.lang.UnsupportedOperationException();}
    public List<V> values() {throw new java.lang.UnsupportedOperationException();}
}
