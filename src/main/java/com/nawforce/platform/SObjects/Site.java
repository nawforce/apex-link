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
public class Site extends SObject {
	public Id AdminId;
	public User Admin;
	public String AnalyticsTrackingCode;
	public String ClickjackProtectionLevel;
	public Integer DailyBandwidthLimit;
	public Integer DailyBandwidthUsed;
	public Integer DailyRequestTimeLimit;
	public Integer DailyRequestTimeUsed;
	public String Description;
	public Id GuestUserId;
	public User GuestUser;
	public String MasterLabel;
	public Integer MonthlyPageViewsEntitlement;
	public String Name;
	public com.nawforce.platform.System.Boolean OptionsAllowGuestSupportApi;
	public com.nawforce.platform.System.Boolean OptionsAllowHomePage;
	public com.nawforce.platform.System.Boolean OptionsAllowStandardAnswersPages;
	public com.nawforce.platform.System.Boolean OptionsAllowStandardIdeasPages;
	public com.nawforce.platform.System.Boolean OptionsAllowStandardLookups;
	public com.nawforce.platform.System.Boolean OptionsAllowStandardPortalPages;
	public com.nawforce.platform.System.Boolean OptionsAllowStandardSearch;
	public com.nawforce.platform.System.Boolean OptionsBrowserXssProtection;
	public com.nawforce.platform.System.Boolean OptionsContentSniffingProtection;
	public com.nawforce.platform.System.Boolean OptionsCspUpgradeInsecureRequests;
	public com.nawforce.platform.System.Boolean OptionsEnableFeeds;
	public com.nawforce.platform.System.Boolean OptionsReferrerPolicyOriginWhenCrossOrigin;
	public Boolean OptionsRequireHttps;
	public String SiteType;
	public String Status;
	public String Subdomain;
	public String UrlPathPrefix;

	public AttachedContentDocument AttachedContentDocuments;
	public CombinedAttachment CombinedAttachments;
	public ContentDocumentLink ContentDocumentLinks;
	public EntitySubscription FeedSubscriptionsForEntity;
	public SiteFeed Feeds;
	public SiteHistory Histories;
	public DomainSite SiteDomainPaths;
	public SiteIframeWhiteListUrl SiteIframeWhiteListUrls;
}
