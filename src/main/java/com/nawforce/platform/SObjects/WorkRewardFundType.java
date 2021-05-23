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
import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.String;
import com.nawforce.platform.System.*;

@SuppressWarnings("unused")
public class WorkRewardFundType extends SObject {
  public static SObjectType$<WorkRewardFundType> SObjectType;
  public static SObjectFields$<WorkRewardFundType> Fields;
  public String CodeValidationPattern;
  public Id CreatedById;
  public User CreatedBy;
  public Datetime CreatedDate;
  public String CreditSystem;
  public String CurrencyCode;
  public Id Id;
  public Boolean IsActive;
  public Boolean IsDeleted;
  public Boolean IsPredefined;
  public Id LastModifiedById;
  public User LastModifiedBy;
  public Datetime LastModifiedDate;
  public Datetime LastReferencedDate;
  public Datetime LastViewedDate;
  public String Name;
  public Id OwnerId;
  public Group Owner;
  public String RedemptionDisclaimer;
  public String RedemptionInfo;
  public String RedemptionUrl;
  public Datetime SystemModstamp;
  public Integer UploadCodeColumn;
  public Integer UploadValueColumn;

  public AttachedContentDocument[] AttachedContentDocuments;
  public CombinedAttachment[] CombinedAttachments;
  public ContentDocumentLink[] ContentDocumentLinks;
  public EntitySubscription[] FeedSubscriptionsForEntity;
  public WorkRewardFundTypeFeed[] Feeds;
  public WorkRewardFundTypeHistory[] Histories;
  public ProcessInstance[] ProcessInstances;
  public ProcessInstanceHistory[] ProcessSteps;
  public WorkRewardFund[] RewardFunds;
  public WorkRewardFundTypeShare[] Shares;

  public WorkRewardFundType clone$() {
    throw new java.lang.UnsupportedOperationException();
  }

  public WorkRewardFundType clone$(Boolean preserveId) {
    throw new java.lang.UnsupportedOperationException();
  }

  public WorkRewardFundType clone$(Boolean preserveId, Boolean isDeepClone) {
    throw new java.lang.UnsupportedOperationException();
  }

  public WorkRewardFundType clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {
    throw new java.lang.UnsupportedOperationException();
  }

  public WorkRewardFundType clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {
    throw new java.lang.UnsupportedOperationException();
  }
}
