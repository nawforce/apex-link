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
public class EmailTemplateChangeEvent extends SObject {
	public static SObjectType$<EmailTemplateChangeEvent> SObjectType;
	public static SObjectFields$<EmailTemplateChangeEvent> Fields;
	public Decimal ApiVersion;
	public String Body;
	public Id BrandTemplateId;
	public BrandTemplate BrandTemplate;
	public Object ChangeEventHeader;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String Description;
	public String DeveloperName;
	public String Encoding;
	public Id EnhancedLetterheadId;
	public EnhancedLetterhead EnhancedLetterhead;
	public Id FolderId;
	public Folder Folder;
	public String HtmlValue;
	public Id Id;
	public Boolean IsActive;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public Datetime LastUsedDate;
	public String Markup;
	public String Name;
	public String NamespacePrefix;
	public Id OwnerId;
	public User Owner;
	public String RelatedEntityType;
	public String ReplayId;
	public String Subject;
	public String TemplateStyle;
	public String TemplateType;
	public Integer TimesUsed;
	public String UiType;

	public EmailTemplateChangeEvent clone$() {throw new java.lang.UnsupportedOperationException();}
	public EmailTemplateChangeEvent clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public EmailTemplateChangeEvent clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public EmailTemplateChangeEvent clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public EmailTemplateChangeEvent clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
