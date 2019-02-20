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
import com.nawforce.platform.System.Id;
import com.nawforce.platform.System.SObject;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class EntityDefinition extends SObject {
	public Id DataStewardId;
	public Group DataSteward;
	public String DefaultCompactLayoutId;
	public String DetailUrl;
	public String DeveloperName;
	public String DurableId;
	public String EditDefinitionUrl;
	public String EditUrl;
	public String ExternalSharingModel;
	public Boolean HasSubtypes;
	public String HelpSettingPageName;
	public String HelpSettingPageUrl;
	public String InternalSharingModel;
	public Boolean IsApexTriggerable;
	public Boolean IsAutoActivityCaptureEnabled;
	public Boolean IsCompactLayoutable;
	public Boolean IsCustomSetting;
	public Boolean IsCustomizable;
	public Boolean IsDeprecatedAndHidden;
	public Boolean IsEverCreatable;
	public Boolean IsEverDeletable;
	public Boolean IsEverUpdatable;
	public Boolean IsFeedEnabled;
	public Boolean IsIdEnabled;
	public Boolean IsLayoutable;
	public Boolean IsMruEnabled;
	public Boolean IsProcessEnabled;
	public Boolean IsQueryable;
	public Boolean IsReplicateable;
	public Boolean IsRetrieveable;
	public Boolean IsSearchLayoutable;
	public Boolean IsSearchable;
	public Boolean IsSubtype;
	public Boolean IsTriggerable;
	public Boolean IsWorkflowEnabled;
	public String KeyPrefix;
	public String Label;
	public String MasterLabel;
	public String NamespacePrefix;
	public String NewUrl;
	public String PluralLabel;
	public String PublisherId;
	public String QualifiedApiName;
	public Object RecordTypesSupported;
	public String RunningUserEntityAccessId;

	public RelationshipInfo ChildRelationships;
	public FieldDefinition Fields;
	public OwnerChangeOptionInfo OwnerChangeOptions;
	public EntityParticle Particles;
	public RelationshipDomain RelationshipDomains;
	public SearchLayout SearchLayouts;
}
