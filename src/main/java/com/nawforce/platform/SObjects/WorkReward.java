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
public class WorkReward extends SObject {
  public static SObjectType$<WorkReward> SObjectType;
  public static SObjectFields$<WorkReward> Fields;
  public String Code;
  public Id CreatedById;
  public User CreatedBy;
  public Datetime CreatedDate;
  public Id Id;
  public Boolean IsDeleted;
  public Id LastModifiedById;
  public User LastModifiedBy;
  public Datetime LastModifiedDate;
  public Id OwnerId;
  public Group Owner;
  public Id RecipientId;
  public User Recipient;
  public String RedemptionDisclaimer;
  public String RedemptionInfo;
  public String RedemptionUrl;
  public Id RewardFundId;
  public WorkRewardFund RewardFund;
  public Id RewardFundTypeId;
  public WorkRewardFundType RewardFundType;
  public Datetime SystemModstamp;
  public Decimal Value;

  public WorkBadge[] Badges;
  public WorkRewardHistory[] Histories;
  public ProcessInstance[] ProcessInstances;
  public ProcessInstanceHistory[] ProcessSteps;
  public WorkRewardShare[] Shares;

  public WorkReward clone$() {
    throw new java.lang.UnsupportedOperationException();
  }

  public WorkReward clone$(Boolean preserveId) {
    throw new java.lang.UnsupportedOperationException();
  }

  public WorkReward clone$(Boolean preserveId, Boolean isDeepClone) {
    throw new java.lang.UnsupportedOperationException();
  }

  public WorkReward clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {
    throw new java.lang.UnsupportedOperationException();
  }

  public WorkReward clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {
    throw new java.lang.UnsupportedOperationException();
  }
}
