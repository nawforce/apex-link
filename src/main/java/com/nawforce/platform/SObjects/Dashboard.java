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
public class Dashboard extends SObject {
	public static SObjectType$<Dashboard> SObjectType;
	public static SObjectFields$<Dashboard> Fields;

	public String BackgroundDirection;
	public Integer BackgroundEnd;
	public Integer BackgroundStart;
	public String ChartTheme;
	public String ColorPalette;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String DashboardResultRefreshedDate;
	public String DashboardResultRunningUser;
	public String Description;
	public String DeveloperName;
	public Id FolderId;
	public Folder Folder;
	public String FolderName;
	public Id Id;
	public Boolean IsDeleted;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public Datetime LastReferencedDate;
	public Datetime LastViewedDate;
	public String LeftSize;
	public String MiddleSize;
	public String NamespacePrefix;
	public String RightSize;
	public Id RunningUserId;
	public User RunningUser;
	public Datetime SystemModstamp;
	public Integer TextColor;
	public String Title;
	public Integer TitleColor;
	public Integer TitleSize;
	public String Type;

	public AttachedContentDocument[] AttachedContentDocuments;
	public CombinedAttachment[] CombinedAttachments;
	public ContentDocumentLink[] ContentDocumentLinks;
	public DashboardComponent[] DashboardComponents;
	public EntitySubscription[] FeedSubscriptionsForEntity;
	public DashboardFeed[] Feeds;

	public Dashboard clone$() {throw new java.lang.UnsupportedOperationException();}
	public Dashboard clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public Dashboard clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public Dashboard clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public Dashboard clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
