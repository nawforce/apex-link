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

package com.nawforce.platform.SObjects;

import com.nawforce.platform.Internal.SObjectFields$;
import com.nawforce.platform.Internal.SObjectType$;
import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.String;
import com.nawforce.platform.System.*;

@SuppressWarnings("unused")
public class Refund extends SObject {
	public static SObjectType$<Refund> SObjectType;
	public static SObjectFields$<Refund> Fields;
	public Id AccountId;
	public Account Account;
	public Decimal Amount;
	public Decimal Balance;
	public Datetime CancellationDate;
	public Datetime CancellationEffectiveDate;
	public Datetime CancellationGatewayDate;
	public String CancellationGatewayRefNumber;
	public String CancellationGatewayResultCode;
	public String CancellationSfResultCode;
	public String Comments;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public Datetime Date;
	public Datetime EffectiveDate;
	public String Email;
	public Datetime GatewayDate;
	public String GatewayRefNumber;
	public String GatewayResultCode;
	public String GatewayResultCodeDescription;
	public Id Id;
	public Decimal ImpactAmount;
	public String IpAddress;
	public Boolean IsDeleted;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public Datetime LastReferencedDate;
	public Datetime LastViewedDate;
	public String MacAddress;
	public Decimal NetApplied;
	public Id PaymentGatewayId;
	public PaymentGateway PaymentGateway;
	public Id PaymentGroupId;
	public PaymentGroup PaymentGroup;
	public Id PaymentMethodId;
	public PaymentMethod PaymentMethod;
	public String Phone;
	public String ProcessingMode;
	public String RefundNumber;
	public String SfResultCode;
	public String Status;
	public Datetime SystemModstamp;
	public Decimal TotalApplied;
	public Decimal TotalUnapplied;
	public String Type;

	public PaymentGatewayLog[] PaymentGatewayLogs;
	public RefundLinePayment[] RefundLinePayments;

	public Refund clone$() {throw new java.lang.UnsupportedOperationException();}
	public Refund clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public Refund clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public Refund clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public Refund clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
