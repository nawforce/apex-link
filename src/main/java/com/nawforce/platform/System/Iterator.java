/*
 * Copyright (c) 2018 FinancialForce.com, inc. All rights reserved.
 */

package com.nawforce.platform.System;

@SuppressWarnings("unused")
public interface Iterator<T> {
    Boolean hasNext();
    T next();
}
