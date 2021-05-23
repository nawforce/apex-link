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
public class EmailMessageChangeEvent extends SObject {
  public static SObjectType$<EmailMessageChangeEvent> SObjectType;
  public static SObjectFields$<EmailMessageChangeEvent> Fields;
  public Id ActivityId;
  public Task Activity;
  public String BccAddress;
  public String CcAddress;
  public Object ChangeEventHeader;
  public Id CreatedById;
  public User CreatedBy;
  public Datetime CreatedDate;
  public Id EmailTemplateId;
  public EmailTemplate EmailTemplate;
  public Datetime FirstOpenedDate;
  public String FromAddress;
  public String FromName;
  public Boolean HasAttachment;
  public String Headers;
  public String HtmlBody;
  public Id Id;
  public Boolean Incoming;
  public Boolean IsBounced;
  public Boolean IsClientManaged;
  public Boolean IsExternallyVisible;
  public Boolean IsTracked;
  public Id LastModifiedById;
  public User LastModifiedBy;
  public Datetime LastModifiedDate;
  public Datetime LastOpenedDate;
  public Datetime MessageDate;
  public String MessageIdentifier;
  public Id ParentId;
  public Case Parent;
  public Id RelatedToId;
  public Account RelatedTo;
  public String ReplayId;
  public Id ReplyToEmailMessageId;
  public EmailMessage ReplyToEmailMessage;
  public String Status;
  public String Subject;
  public String TextBody;
  public String ThreadIdentifier;
  public String ToAddress;

  public EmailMessageChangeEvent clone$() {
    throw new java.lang.UnsupportedOperationException();
  }

  public EmailMessageChangeEvent clone$(Boolean preserveId) {
    throw new java.lang.UnsupportedOperationException();
  }

  public EmailMessageChangeEvent clone$(Boolean preserveId, Boolean isDeepClone) {
    throw new java.lang.UnsupportedOperationException();
  }

  public EmailMessageChangeEvent clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {
    throw new java.lang.UnsupportedOperationException();
  }

  public EmailMessageChangeEvent clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {
    throw new java.lang.UnsupportedOperationException();
  }
}
