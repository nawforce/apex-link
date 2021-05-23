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

@SuppressWarnings("unused")
public class FeedLayout {
	public Boolean autocollapsePublisher;
	public Boolean compactFeed;
	public FeedLayoutFilterPosition feedFilterPosition;
	public List<FeedLayoutFilter> feedFilters;
	public Boolean fullWidthFeed;
	public Boolean hideSidebar;
	public Boolean highlightExternalFeedItems;
	public List<FeedLayoutComponent> leftComponents;
	public List<FeedLayoutComponent> rightComponents;
	public Boolean useInlineFiltersInConsole;

	public FeedLayout() {throw new java.lang.UnsupportedOperationException();}
}
