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

import com.nawforce.platform.Internal.SObjectFields$;
import com.nawforce.platform.Internal.SObjectType$;
import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.String;
import com.nawforce.platform.System.*;

@SuppressWarnings("unused")
public class Individual extends SObject {
	public static SObjectType$<Individual> SObjectType;
	public static SObjectFields$<Individual> Fields;

	public Date BirthDate;
	public Boolean CanStorePiiElsewhere;
	public Integer ChildrenCount;
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
	public Boolean IsDeleted;
	public Boolean IsHomeOwner;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public String LastName;
	public Datetime LastViewedDate;
	public Id MasterRecordId;
	public Individual MasterRecord;
	public String MilitaryService;
	public String Name;
	public String Occupation;
	public Id OwnerId;
	public User Owner;
	public String Salutation;
	public Boolean SendIndividualData;
	public Boolean ShouldForget;
	public Datetime SystemModstamp;
	public String Website;

	public ContactPointEmail[] ContactPointEmails;
	public ContactPointPhone[] ContactPointPhones;
	public Contact[] Contacts;
	public DuplicateRecordItem[] DuplicateRecordItems;
	public IndividualHistory[] Histories;
	public ContactPointTypeConsent[] Individuals;
	public Lead[] Leads;
	public IndividualShare[] Shares;

	public Individual clone$() {throw new java.lang.UnsupportedOperationException();}
	public Individual clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public Individual clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public Individual clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public Individual clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
