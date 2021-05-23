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

package com.nawforce.platform.Internal;

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.*;

@SuppressWarnings("unused")
public class Trigger$<T> {
    public Boolean isAfter;
    public Boolean isBefore;
    public Boolean isDelete;
    public Boolean isExecuting;
    public Boolean isInsert;
    public Boolean isUndelete;
    public Boolean isUpdate;

    public List<T> New;
    public Map<Id, T> newMap;
    public List<T> old;
    public Map<Id, T> oldMap;
    public TriggerOperation operationType;
    public Integer size;
}
