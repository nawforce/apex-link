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
import com.nawforce.platform.System.List;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class DescribeLayoutItem {
	public Boolean EditableForNew;
	public Boolean EditableForUpdate;
	public String Label;
	public List<DescribeLayoutComponent> LayoutComponents;
	public Boolean Placeholder;
	public Boolean Required;

	public String getLabel() {throw new java.lang.UnsupportedOperationException();}
	public List<DescribeLayoutComponent> getLayoutComponents() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isEditableForNew() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isEditableForUpdate() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isPlaceholder() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isRequired() {throw new java.lang.UnsupportedOperationException();}
}
