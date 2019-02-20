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
package com.nawforce.platform.SObjects;

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.String;
import com.nawforce.platform.System.*;


@SuppressWarnings("unused")
public class AppMenuItem extends SObject {
	public Id ApplicationId;
	public SObject Application;
	public String CanvasAccessMethod;
	public Boolean CanvasEnabled;
	public String CanvasOptions;
	public String CanvasReferenceId;
	public String CanvasSelectedLocations;
	public String CanvasUrl;
	public String Description;
	public String IconUrl;
	public String InfoUrl;
	public Boolean IsAccessible;
	public Boolean IsRegisteredDeviceOnly;
	public Boolean IsUsingAdminAuthorization;
	public Boolean IsVisible;
	public String Label;
	public String LogoUrl;
	public String MobileAppBinaryId;
	public String MobileAppInstallUrl;
	public Datetime MobileAppInstalledDate;
	public String MobileAppInstalledVersion;
	public String MobileAppVer;
	public String MobileDeviceType;
	public String MobileMinOsVer;
	public String MobilePlatform;
	public String MobileStartUrl;
	public String Name;
	public String NamespacePrefix;
	public com.nawforce.platform.System.Integer SortOrder;
	public String StartUrl;
	public String Type;
	public Integer UserSortOrder;
}
