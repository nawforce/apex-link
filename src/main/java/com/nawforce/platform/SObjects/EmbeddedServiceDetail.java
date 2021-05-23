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
public class EmbeddedServiceDetail extends SObject {
	public static SObjectType$<EmbeddedServiceDetail> SObjectType;
	public static SObjectFields$<EmbeddedServiceDetail> Fields;

	public String AvatarImg;
	public String CancelApptBookingFlowName;
	public String ContrastInvertedColor;
	public String ContrastPrimaryColor;
	public String CustomMinimizedComponent;
	public String CustomPrechatComponent;
	public String DurableId;
	public String FieldServiceConfirmCardImg;
	public String FieldServiceHomeImg;
	public String FieldServiceLogoImg;
	public String FlowDeveloperName;
	public String Font;
	public String FontSize;
	public String HeaderBackgroundImg;
	public Integer Height;
	public Id Id;
	public Boolean IsFieldServiceEnabled;
	public Boolean IsLiveAgentEnabled;
	public Boolean IsOfflineCaseEnabled;
	public Boolean IsPrechatEnabled;
	public Boolean IsQueuePositionEnabled;
	public String ModifyApptBookingFlowName;
	public String NavBarColor;
	public String OfflineCaseBackgroundImg;
	public String PrechatBackgroundImg;
	public String PrimaryColor;
	public String SecondaryColor;
	public Boolean ShouldHideAuthDialog;
	public Boolean ShouldShowExistingAppointment;
	public Boolean ShouldShowNewAppointment;
	public String Site;
	public String SmallCompanyLogoImg;
	public String WaitingStateBackgroundImg;
	public Integer Width;

	public EmbeddedServiceDetail clone$() {throw new java.lang.UnsupportedOperationException();}
	public EmbeddedServiceDetail clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public EmbeddedServiceDetail clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public EmbeddedServiceDetail clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public EmbeddedServiceDetail clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
