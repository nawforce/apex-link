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

package com.nawforce.platform.Schema;

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.List;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class DescribeTabSetResult {
	public String Description;
	public String Label;
	public String LogoUrl;
	public String Namespace;
	public Boolean Selected;
	public List<DescribeTabResult> Tabs;
	public String TabSetId;

	public String getDescription() {throw new java.lang.UnsupportedOperationException();}
	public String getLabel() {throw new java.lang.UnsupportedOperationException();}
	public String getLogoUrl() {throw new java.lang.UnsupportedOperationException();}
	public String getNamespace() {throw new java.lang.UnsupportedOperationException();}
	public String getTabSetId() {throw new java.lang.UnsupportedOperationException();}
	public List<DescribeTabResult> getTabs() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isSelected() {throw new java.lang.UnsupportedOperationException();}
}
