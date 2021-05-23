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
public class SurveyResponse extends SObject {
	public static SObjectType$<SurveyResponse> SObjectType;
	public static SObjectFields$<SurveyResponse> Fields;
	public Datetime CompletionDateTime;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public Id Id;
	public String InterviewGuid;
	public Id InterviewId;
	public FlowInterview Interview;
	public Id InvitationId;
	public SurveyInvitation Invitation;
	public String IpAddress;
	public Boolean IsDeleted;
	public String Language;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public Datetime LastReferencedDate;
	public Datetime LastViewedDate;
	public Decimal Latitude;
	public Location Location;
	public Decimal Longitude;
	public String Name;
	public String Status;
	public Id SubmitterId;
	public Contact Submitter;
	public Id SurveyId;
	public Survey Survey;
	public Id SurveyVersionId;
	public SurveyVersion SurveyVersion;
	public Datetime SystemModstamp;

	public ProcessInstance[] ProcessInstances;
	public ProcessInstanceHistory[] ProcessSteps;
	public SurveyQuestionResponse[] SurveyQuestionResponses;
	public SurveySubject[] SurveySubjectResponse;
	public SurveySubject[] SurveySubjects;

	public SurveyResponse clone$() {throw new java.lang.UnsupportedOperationException();}
	public SurveyResponse clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public SurveyResponse clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public SurveyResponse clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public SurveyResponse clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
