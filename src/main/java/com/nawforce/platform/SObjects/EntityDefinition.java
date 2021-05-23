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

import com.nawforce.platform.Internal.SObjectType$;
import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.String;
import com.nawforce.platform.System.*;

@SuppressWarnings("unused")
public class EntityDefinition extends SObject {
	public static SObjectType$<EntityDefinition> SObjectType;


	public Id DataStewardId;
	public Group DataSteward;
	public String DefaultCompactLayoutId;
	public String DeploymentStatus;
	public String DetailUrl;
	public String DeveloperName;
	public String DurableId;
	public String EditDefinitionUrl;
	public String EditUrl;
	public String ExternalSharingModel;
	public Boolean HasSubtypes;
	public String HelpSettingPageName;
	public String HelpSettingPageUrl;
	public Id Id;
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
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public String MasterLabel;
	public String NamespacePrefix;
	public String NewUrl;
	public String PluralLabel;
	public String PublisherId;
	public String QualifiedApiName;
	public Object RecordTypesSupported;
	public String RunningUserEntityAccessId;

	public RelationshipInfo[] ChildRelationships;
	public FieldDefinition[] Fields;
	public OwnerChangeOptionInfo[] OwnerChangeOptions;
	public EntityParticle[] Particles;
	public RelationshipDomain[] RelationshipDomains;
	public SearchLayout[] SearchLayouts;

	public EntityDefinition clone$() {throw new java.lang.UnsupportedOperationException();}
	public EntityDefinition clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public EntityDefinition clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public EntityDefinition clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public EntityDefinition clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
