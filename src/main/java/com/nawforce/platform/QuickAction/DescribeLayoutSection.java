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

package com.nawforce.platform.QuickAction;

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.String;
import com.nawforce.platform.System.*;


@SuppressWarnings("unused")
public class DescribeLayoutSection {
	public Boolean Collapsed;
	public Integer Columns;
	public String Heading;
	public List<DescribeLayoutRow> LayoutRows;
	public Id LayoutSectionId;
	public Id ParentLayoutId;
	public Integer Rows;
	public String TabOrder;
	public Boolean UseCollapsibleSection;
	public Boolean UseHeading;

	public Integer getColumns() {throw new java.lang.UnsupportedOperationException();}
	public String getHeading() {throw new java.lang.UnsupportedOperationException();}
	public List<DescribeLayoutRow> getLayoutRows() {throw new java.lang.UnsupportedOperationException();}
	public Id getLayoutSectionId() {throw new java.lang.UnsupportedOperationException();}
	public Id getParentLayoutId() {throw new java.lang.UnsupportedOperationException();}
	public Integer getRows() {throw new java.lang.UnsupportedOperationException();}
	public String getTabOrder() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isCollapsed() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isUseCollapsibleSection() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isUseHeading() {throw new java.lang.UnsupportedOperationException();}
}
