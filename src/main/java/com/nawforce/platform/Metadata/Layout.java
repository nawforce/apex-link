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

package com.nawforce.platform.Metadata;

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.List;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class Layout extends Metadata {
	public List<String> customButtons;
	public CustomConsoleComponents customConsoleComponents;
	public Boolean emailDefault;
	public List<String> excludeButtons;
	public FeedLayout feedLayout;
	public List<LayoutHeader> headers;
	public List<LayoutSection> layoutSections;
	public MiniLayout miniLayout;
	public List<String> multilineLayoutFields;
	public PlatformActionList platformActionList;
	public QuickActionList quickActionList;
	public RelatedContent relatedContent;
	public List<RelatedListItem> relatedLists;
	public List<String> relatedObjects;
	public Boolean runAssignmentRulesDefault;
	public Boolean showEmailCheckbox;
	public Boolean showHighlightsPanel;
	public Boolean showInteractionLogPanel;
	public Boolean showKnowledgeComponent;
	public Boolean showRunAssignmentRulesCheckbox;
	public Boolean showSolutionSection;
	public Boolean showSubmitAndAttachButton;
	public SummaryLayout summaryLayout;

	public Layout() {throw new java.lang.UnsupportedOperationException();}
}

