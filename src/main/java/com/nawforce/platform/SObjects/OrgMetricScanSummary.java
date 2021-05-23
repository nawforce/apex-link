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
public class OrgMetricScanSummary extends SObject {
  public static SObjectType$<OrgMetricScanSummary> SObjectType;
  public static SObjectFields$<OrgMetricScanSummary> Fields;
  public Id CreatedById;
  public User CreatedBy;
  public Datetime CreatedDate;
  public String ErrorMessage;
  public Integer FeatureLimit;
  public Id Id;
  public String ImplementationEffort;
  public Boolean IsDeleted;
  public Integer ItemCount;
  public Id LastModifiedById;
  public User LastModifiedBy;
  public Datetime LastModifiedDate;
  public String Name;
  public Id OrgMetricId;
  public OrgMetric OrgMetric;
  public Decimal PercentUsage;
  public Datetime ScanDate;
  public String Status;
  public Datetime SystemModstamp;
  public String Unit;

  public OrgMetricScanResult[] OrgMetricScanSummary;
  public ProcessInstance[] ProcessInstances;
  public ProcessInstanceHistory[] ProcessSteps;

  public OrgMetricScanSummary clone$() {
    throw new java.lang.UnsupportedOperationException();
  }

  public OrgMetricScanSummary clone$(Boolean preserveId) {
    throw new java.lang.UnsupportedOperationException();
  }

  public OrgMetricScanSummary clone$(Boolean preserveId, Boolean isDeepClone) {
    throw new java.lang.UnsupportedOperationException();
  }

  public OrgMetricScanSummary clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {
    throw new java.lang.UnsupportedOperationException();
  }

  public OrgMetricScanSummary clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {
    throw new java.lang.UnsupportedOperationException();
  }
}
