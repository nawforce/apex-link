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
package com.nawforce.platform.SObjects;

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.String;
import com.nawforce.platform.System.*;


@SuppressWarnings("unused")
public class LookedUpFromActivity extends SObject {
	public Id AccountId;
	public Account Account;
	public Date ActivityDate;
	public String ActivitySubtype;
	public String ActivityType;
	public String CallDisposition;
	public com.nawforce.platform.System.Integer CallDurationInSeconds;
	public String CallObject;
	public String CallType;
	public String CurrencyIsoCode;
	public String Description;
	public com.nawforce.platform.System.Integer DurationInMinutes;
	public Datetime EndDateTime;
	public com.nawforce.platform.System.Boolean IsAllDayEvent;
	public com.nawforce.platform.System.Boolean IsClosed;
	public com.nawforce.platform.System.Boolean IsHighPriority;
	public com.nawforce.platform.System.Boolean IsReminderSet;
	public com.nawforce.platform.System.Boolean IsTask;
	public Boolean IsVisibleInSelfService;
	public String Location;
	public Id OwnerId;
	public User Owner;
	public String Priority;
	public Datetime ReminderDateTime;
	public Datetime StartDateTime;
	public String Status;
	public String Subject;
	public Id WhatId;
	public Account What;
	public Id WhoId;
	public Contact Who;
}
