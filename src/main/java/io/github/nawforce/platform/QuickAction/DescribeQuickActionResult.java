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
package io.github.nawforce.platform.QuickAction;

import io.github.nawforce.platform.System.Boolean;
import io.github.nawforce.platform.System.Integer;
import io.github.nawforce.platform.System.String;
import io.github.nawforce.platform.Schema.DescribeColorResult;
import io.github.nawforce.platform.Schema.DescribeIconResult;
import io.github.nawforce.platform.System.*;

@SuppressWarnings("unused")
public class DescribeQuickActionResult {
	public String AccessLevelRequired;
	public String CanvasApplicationId;
	public String CanvasApplicationName;
	public List<DescribeColorResult> Colors;
	public String ContextSobjectType;
	public List<DescribeQuickActionDefaultValue> DefaultValues;
	public String FlowDevName;
	public String FlowRecordIdVar;
	public Integer Height;
	public String IconName;
	public List<DescribeIconResult> Icons;
	public String IconUrl;
	public DescribeLayoutSection Layout;
	public String LightningComponentBundleId;
	public String LightningComponentBundleName;
	public String LightningComponentQualifiedName;
	public String MiniIconUrl;
	public Boolean ShowQuickActionLcHeader;
	public Boolean ShowQuickActionVfHeader;
	public String TargetParentField;
	public String TargetRecordTypeId;
	public String TargetSobjectType;
	public String VisualforcePageName;
	public String VisualforcePageUrl;
	public Integer Width;

	public String getAccessLevelRequired() {throw new java.lang.UnsupportedOperationException();}
	public String getActionEnumOrId() {throw new java.lang.UnsupportedOperationException();}
	public String getCanvasApplicationId() {throw new java.lang.UnsupportedOperationException();}
	public String getCanvasApplicationName() {throw new java.lang.UnsupportedOperationException();}
	public List<DescribeColorResult> getColors() {throw new java.lang.UnsupportedOperationException();}
	public String getContextSobjectType() {throw new java.lang.UnsupportedOperationException();}
	public List<DescribeQuickActionDefaultValue> getDefaultValues() {throw new java.lang.UnsupportedOperationException();}
	public String getFlowDevName() {throw new java.lang.UnsupportedOperationException();}
	public String getFlowRecordIdVar() {throw new java.lang.UnsupportedOperationException();}
	public Integer getHeight() {throw new java.lang.UnsupportedOperationException();}
	public String getIconName() {throw new java.lang.UnsupportedOperationException();}
	public String getIconUrl() {throw new java.lang.UnsupportedOperationException();}
	public List<DescribeIconResult> getIcons() {throw new java.lang.UnsupportedOperationException();}
	public String getLabel() {throw new java.lang.UnsupportedOperationException();}
	public DescribeLayoutSection getLayout() {throw new java.lang.UnsupportedOperationException();}
	public String getLightningComponentBundleId() {throw new java.lang.UnsupportedOperationException();}
	public String getLightningComponentBundleName() {throw new java.lang.UnsupportedOperationException();}
	public String getLightningComponentQualifiedName() {throw new java.lang.UnsupportedOperationException();}
	public String getMiniIconUrl() {throw new java.lang.UnsupportedOperationException();}
	public String getName() {throw new java.lang.UnsupportedOperationException();}
	public Boolean getShowQuickActionLcHeader() {throw new java.lang.UnsupportedOperationException();}
	public Boolean getShowQuickActionVfHeader() {throw new java.lang.UnsupportedOperationException();}
	public String getTargetParentField() {throw new java.lang.UnsupportedOperationException();}
	public String getTargetRecordTypeId() {throw new java.lang.UnsupportedOperationException();}
	public String getTargetSobjectType() {throw new java.lang.UnsupportedOperationException();}
	public String getType() {throw new java.lang.UnsupportedOperationException();}
	public String getVisualforcePageName() {throw new java.lang.UnsupportedOperationException();}
	public String getVisualforcePageUrl() {throw new java.lang.UnsupportedOperationException();}
	public Integer getWidth() {throw new java.lang.UnsupportedOperationException();}
}
