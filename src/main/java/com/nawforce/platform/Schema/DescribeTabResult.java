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
public class DescribeTabResult {
	public List<DescribeColorResult> Colors;
	public Boolean Custom;
	public List<DescribeIconResult> Icons;
	public String IconUrl;
	public String Label;
	public String MiniIconUrl;
	public String Name;
	public String SobjectName;
	public String TabEnumOrId;
	public String Url;

	public List<DescribeColorResult> getColors() {throw new java.lang.UnsupportedOperationException();}
	public String getIconUrl() {throw new java.lang.UnsupportedOperationException();}
	public List<DescribeIconResult> getIcons() {throw new java.lang.UnsupportedOperationException();}
	public String getLabel() {throw new java.lang.UnsupportedOperationException();}
	public String getMiniIconUrl() {throw new java.lang.UnsupportedOperationException();}
	public String getName() {throw new java.lang.UnsupportedOperationException();}
	public String getSobjectName() {throw new java.lang.UnsupportedOperationException();}
	public String getTabEnumOrId() {throw new java.lang.UnsupportedOperationException();}
	public String getUrl() {throw new java.lang.UnsupportedOperationException();}
	public Boolean isCustom() {throw new java.lang.UnsupportedOperationException();}
}
