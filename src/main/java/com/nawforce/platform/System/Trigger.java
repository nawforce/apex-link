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

@SuppressWarnings("unused")
public class Trigger {
    public static Boolean isAfter;
    public static Boolean isBefore;
    public static Boolean isDelete;
    public static Boolean isExecuting;
    public static Boolean isInsert;
    public static Boolean isUndelete;
    public static Boolean isUpdate;

    public static List<SObject> New;
    public static Map<Id, SObject> newMap;
    public static List<SObject> old;
    public static Map<Id, SObject> oldMap;
    public static TriggerOperation operationType;
    public static Integer size;
}
