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
public class WorkFeedbackRequest extends SObject {
  public static SObjectType$<WorkFeedbackRequest> SObjectType;
  public static SObjectFields$<WorkFeedbackRequest> Fields;
  public String AdHocFeedback;
  public String AdHocQuestion;
  public Id CreatedById;
  public User CreatedBy;
  public Datetime CreatedDate;
  public String Description;
  public String FeedbackRequestState;
  public String FeedbackType;
  public Id Id;
  public Boolean IsDeleted;
  public Boolean IsDeployed;
  public Boolean IsShareWithSubject;
  public Boolean IsUnreadByOwner;
  public Boolean IsUnsolicited;
  public Id LastModifiedById;
  public User LastModifiedBy;
  public Datetime LastModifiedDate;
  public Datetime LastReferencedDate;
  public Datetime LastRemindDate;
  public Datetime LastSharedDate;
  public Datetime LastViewedDate;
  public String Name;
  public Id OwnerId;
  public Group Owner;
  public Id PerformanceCycleId;
  public WorkPerformanceCycle PerformanceCycle;
  public Id QuestionSetId;
  public WorkFeedbackQuestionSet QuestionSet;
  public Id RecipientId;
  public User Recipient;
  public Id RelatedObjectId;
  public Account RelatedObject;
  public String SharingScope;
  public Id SubjectId;
  public User Subject;
  public Id SubmitFeedbackToId;
  public User SubmitFeedbackTo;
  public Datetime SubmittedDate;
  public Datetime SystemModstamp;

  public AttachedContentDocument[] AttachedContentDocuments;
  public CombinedAttachment[] CombinedAttachments;
  public ContentDocumentLink[] ContentDocumentLinks;
  public EntitySubscription[] FeedSubscriptionsForEntity;
  public WorkFeedback[] Feedbacks;
  public WorkFeedbackRequestFeed[] Feeds;
  public WorkFeedbackRequestHistory[] Histories;
  public ProcessInstance[] ProcessInstances;
  public ProcessInstanceHistory[] ProcessSteps;
  public WorkFeedbackRequestShare[] Shares;

  public WorkFeedbackRequest clone$() {
    throw new java.lang.UnsupportedOperationException();
  }

  public WorkFeedbackRequest clone$(Boolean preserveId) {
    throw new java.lang.UnsupportedOperationException();
  }

  public WorkFeedbackRequest clone$(Boolean preserveId, Boolean isDeepClone) {
    throw new java.lang.UnsupportedOperationException();
  }

  public WorkFeedbackRequest clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {
    throw new java.lang.UnsupportedOperationException();
  }

  public WorkFeedbackRequest clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {
    throw new java.lang.UnsupportedOperationException();
  }
}
