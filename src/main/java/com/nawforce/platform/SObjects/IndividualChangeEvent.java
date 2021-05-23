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
public class IndividualChangeEvent extends SObject {
	public static SObjectType$<IndividualChangeEvent> SObjectType;
	public static SObjectFields$<IndividualChangeEvent> Fields;
	public Date BirthDate;
	public Boolean CanStorePiiElsewhere;
	public Object ChangeEventHeader;
	public Integer ChildrenCount;
	public Integer ConsumerCreditScore;
	public String ConsumerCreditScoreProviderName;
	public Integer ConvictionsCount;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public Date DeathDate;
	public String FirstName;
	public Boolean HasOptedOutGeoTracking;
	public Boolean HasOptedOutProcessing;
	public Boolean HasOptedOutProfiling;
	public Boolean HasOptedOutSolicit;
	public Boolean HasOptedOutTracking;
	public Id Id;
	public String IndividualsAge;
	public Integer InfluencerRating;
	public Boolean IsHomeOwner;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public String LastName;
	public String MilitaryService;
	public String Name;
	public String Occupation;
	public Id OwnerId;
	public User Owner;
	public String ReplayId;
	public String Salutation;
	public Boolean SendIndividualData;
	public Boolean ShouldForget;
	public String Website;

	public IndividualChangeEvent clone$() {throw new java.lang.UnsupportedOperationException();}
	public IndividualChangeEvent clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public IndividualChangeEvent clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public IndividualChangeEvent clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public IndividualChangeEvent clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
