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
public class PermissionSetLicense extends SObject {
	public String DeveloperName;
	public Date ExpirationDate;
	public String Language;
	public String MasterLabel;
	public Boolean MaximumPermissionsAccessCMC;
	public Boolean MaximumPermissionsAccountIQUser;
	public Boolean MaximumPermissionsActivateContract;
	public Boolean MaximumPermissionsActivateOrder;
	public Boolean MaximumPermissionsActivitiesAccess;
	public Boolean MaximumPermissionsAddAnalyticsRemoteConnections;
	public Boolean MaximumPermissionsAddDirectMessageMembers;
	public Boolean MaximumPermissionsAllowLightningLogin;
	public Boolean MaximumPermissionsAllowUniversalSearch;
	public Boolean MaximumPermissionsAllowViewEditConvertedLeads;
	public Boolean MaximumPermissionsAllowViewKnowledge;
	public Boolean MaximumPermissionsApexRestServices;
	public Boolean MaximumPermissionsApiEnabled;
	public Boolean MaximumPermissionsAssignPermissionSets;
	public Boolean MaximumPermissionsAssignTopics;
	public Boolean MaximumPermissionsAuthorApex;
	public Boolean MaximumPermissionsAutomatedDataEntry;
	public Boolean MaximumPermissionsAutomaticActivityCapture;
	public Boolean MaximumPermissionsBulkApiHardDelete;
	public Boolean MaximumPermissionsBulkMacrosAllowed;
	public Boolean MaximumPermissionsCampaignInfluence2;
	public Boolean MaximumPermissionsCanApproveFeedPost;
	public Boolean MaximumPermissionsCanEditDataPrepRecipe;
	public Boolean MaximumPermissionsCanInsertFeedSystemFields;
	public Boolean MaximumPermissionsCanManageMaps;
	public Boolean MaximumPermissionsCanUseNewDashboardBuilder;
	public Boolean MaximumPermissionsCanVerifyComment;
	public Boolean MaximumPermissionsChangeDashboardColors;
	public Boolean MaximumPermissionsChatterComposeUiCodesnippet;
	public Boolean MaximumPermissionsChatterEditOwnPost;
	public Boolean MaximumPermissionsChatterEditOwnRecordPost;
	public Boolean MaximumPermissionsChatterFileLink;
	public Boolean MaximumPermissionsChatterInternalUser;
	public Boolean MaximumPermissionsChatterInviteExternalUsers;
	public Boolean MaximumPermissionsChatterOwnGroups;
	public Boolean MaximumPermissionsCloseConversations;
	public Boolean MaximumPermissionsConfigCustomRecs;
	public Boolean MaximumPermissionsConnectOrgToEnvironmentHub;
	public Boolean MaximumPermissionsContentAdministrator;
	public Boolean MaximumPermissionsContentWorkspaces;
	public Boolean MaximumPermissionsConvertLeads;
	public Boolean MaximumPermissionsCreateCustomizeDashboards;
	public Boolean MaximumPermissionsCreateCustomizeFilters;
	public Boolean MaximumPermissionsCreateCustomizeReports;
	public Boolean MaximumPermissionsCreateDashboardFolders;
	public Boolean MaximumPermissionsCreateLtngTempInPub;
	public Boolean MaximumPermissionsCreatePackaging;
	public Boolean MaximumPermissionsCreateReportFolders;
	public Boolean MaximumPermissionsCreateReportInLightning;
	public Boolean MaximumPermissionsCreateTopics;
	public Boolean MaximumPermissionsCreateWorkspaces;
	public Boolean MaximumPermissionsCustomMobileAppsAccess;
	public Boolean MaximumPermissionsCustomizeApplication;
	public Boolean MaximumPermissionsDataExport;
	public Boolean MaximumPermissionsDelegatedTwoFactor;
	public Boolean MaximumPermissionsDeleteActivatedContract;
	public Boolean MaximumPermissionsDeleteTopics;
	public Boolean MaximumPermissionsDistributeFromPersWksp;
	public Boolean MaximumPermissionsEditActivatedOrders;
	public Boolean MaximumPermissionsEditBrandTemplates;
	public Boolean MaximumPermissionsEditCaseComments;
	public Boolean MaximumPermissionsEditEvent;
	public Boolean MaximumPermissionsEditHtmlTemplates;
	public Boolean MaximumPermissionsEditKnowledge;
	public Boolean MaximumPermissionsEditMyDashboards;
	public Boolean MaximumPermissionsEditMyReports;
	public Boolean MaximumPermissionsEditOppLineItemUnitPrice;
	public Boolean MaximumPermissionsEditPublicDocuments;
	public Boolean MaximumPermissionsEditPublicFilters;
	public Boolean MaximumPermissionsEditPublicTemplates;
	public Boolean MaximumPermissionsEditReadonlyFields;
	public Boolean MaximumPermissionsEditTask;
	public Boolean MaximumPermissionsEditTopics;
	public Boolean MaximumPermissionsEmailAdministration;
	public Boolean MaximumPermissionsEmailMass;
	public Boolean MaximumPermissionsEmailSingle;
	public Boolean MaximumPermissionsEmailTemplateManagement;
	public Boolean MaximumPermissionsEnableCommunityAppLauncher;
	public Boolean MaximumPermissionsEnableNotifications;
	public Boolean MaximumPermissionsEnableSOS;
	public Boolean MaximumPermissionsExportReport;
	public Boolean MaximumPermissionsFeedPinning;
	public Boolean MaximumPermissionsFlowUFLRequired;
	public Boolean MaximumPermissionsForceTwoFactor;
	public Boolean MaximumPermissionsGiveRecognitionBadge;
	public Boolean MaximumPermissionsGovernNetworks;
	public Boolean MaximumPermissionsHideReadByList;
	public Boolean MaximumPermissionsIdentityConnect;
	public Boolean MaximumPermissionsIdentityEnabled;
	public Boolean MaximumPermissionsImportCustomObjects;
	public Boolean MaximumPermissionsImportLeads;
	public Boolean MaximumPermissionsImportPersonal;
	public Boolean MaximumPermissionsInsightsAppAdmin;
	public Boolean MaximumPermissionsInsightsAppDashboardEditor;
	public Boolean MaximumPermissionsInsightsAppEltEditor;
	public Boolean MaximumPermissionsInsightsAppUploadUser;
	public Boolean MaximumPermissionsInsightsAppUser;
	public Boolean MaximumPermissionsInsightsCreateApplication;
	public Boolean MaximumPermissionsInstallPackaging;
	public Boolean MaximumPermissionsLeadScoreUser;
	public Boolean MaximumPermissionsLightningConsoleAllowedForUser;
	public Boolean MaximumPermissionsLightningExperienceUser;
	public Boolean MaximumPermissionsListEmailSend;
	public Boolean MaximumPermissionsLtngPromoReserved01UserPerm;
	public Boolean MaximumPermissionsManageAnalyticSnapshots;
	public Boolean MaximumPermissionsManageAuthProviders;
	public Boolean MaximumPermissionsManageBusinessHourHolidays;
	public Boolean MaximumPermissionsManageCallCenters;
	public Boolean MaximumPermissionsManageCases;
	public Boolean MaximumPermissionsManageCategories;
	public Boolean MaximumPermissionsManageCertificates;
	public Boolean MaximumPermissionsManageChatterMessages;
	public Boolean MaximumPermissionsManageContentPermissions;
	public Boolean MaximumPermissionsManageContentProperties;
	public Boolean MaximumPermissionsManageContentTypes;
	public Boolean MaximumPermissionsManageCustomPermissions;
	public Boolean MaximumPermissionsManageCustomReportTypes;
	public Boolean MaximumPermissionsManageDashbdsInPubFolders;
	public Boolean MaximumPermissionsManageDataCategories;
	public Boolean MaximumPermissionsManageDataIntegrations;
	public Boolean MaximumPermissionsManageDynamicDashboards;
	public Boolean MaximumPermissionsManageEmailClientConfig;
	public Boolean MaximumPermissionsManageEncryptionKeys;
	public Boolean MaximumPermissionsManageExchangeConfig;
	public Boolean MaximumPermissionsManageHealthCheck;
	public Boolean MaximumPermissionsManageInteraction;
	public Boolean MaximumPermissionsManageInternalUsers;
	public Boolean MaximumPermissionsManageIpAddresses;
	public Boolean MaximumPermissionsManageKnowledge;
	public Boolean MaximumPermissionsManageKnowledgeImportExport;
	public Boolean MaximumPermissionsManageLeads;
	public Boolean MaximumPermissionsManageLoginAccessPolicies;
	public Boolean MaximumPermissionsManageMobile;
	public Boolean MaximumPermissionsManageNetworks;
	public Boolean MaximumPermissionsManagePasswordPolicies;
	public Boolean MaximumPermissionsManageProfilesPermissionsets;
	public Boolean MaximumPermissionsManagePvtRptsAndDashbds;
	public Boolean MaximumPermissionsManageRemoteAccess;
	public Boolean MaximumPermissionsManageReportsInPubFolders;
	public Boolean MaximumPermissionsManageRoles;
	public Boolean MaximumPermissionsManageSearchPromotionRules;
	public Boolean MaximumPermissionsManageSessionPermissionSets;
	public Boolean MaximumPermissionsManageSharing;
	public Boolean MaximumPermissionsManageSolutions;
	public Boolean MaximumPermissionsManageSurveys;
	public Boolean MaximumPermissionsManageSynonyms;
	public Boolean MaximumPermissionsManageTemplatedApp;
	public Boolean MaximumPermissionsManageTranslation;
	public Boolean MaximumPermissionsManageTwoFactor;
	public Boolean MaximumPermissionsManageUnlistedGroups;
	public Boolean MaximumPermissionsManageUsers;
	public Boolean MaximumPermissionsMassInlineEdit;
	public Boolean MaximumPermissionsMergeTopics;
	public Boolean MaximumPermissionsModerateChatter;
	public Boolean MaximumPermissionsModerateNetworkUsers;
	public Boolean MaximumPermissionsModifyAllData;
	public Boolean MaximumPermissionsModifyMetadata;
	public Boolean MaximumPermissionsModifySecureAgents;
	public Boolean MaximumPermissionsNewReportBuilder;
	public Boolean MaximumPermissionsOpportunityIQUser;
	public Boolean MaximumPermissionsOpportunityScoreUser;
	public Boolean MaximumPermissionsPackaging2;
	public Boolean MaximumPermissionsPasswordNeverExpires;
	public Boolean MaximumPermissionsPreventClassicExperience;
	public Boolean MaximumPermissionsPrivacyDataAccess;
	public Boolean MaximumPermissionsPublishPackaging;
	public Boolean MaximumPermissionsRecordVisibilityAPI;
	public Boolean MaximumPermissionsRemoveDirectMessageMembers;
	public Boolean MaximumPermissionsResetPasswords;
	public Boolean MaximumPermissionsRunFlow;
	public Boolean MaximumPermissionsRunReports;
	public Boolean MaximumPermissionsSalesAnalyticsUser;
	public Boolean MaximumPermissionsSalesConsole;
	public Boolean MaximumPermissionsSalesforceIQInbox;
	public Boolean MaximumPermissionsScheduleReports;
	public Boolean MaximumPermissionsSelectFilesFromSalesforce;
	public Boolean MaximumPermissionsSendAnnouncementEmails;
	public Boolean MaximumPermissionsSendSitRequests;
	public Boolean MaximumPermissionsShareInternalArticles;
	public Boolean MaximumPermissionsShowCompanyNameAsUserBadge;
	public Boolean MaximumPermissionsSolutionImport;
	public Boolean MaximumPermissionsStdAutomaticActivityCapture;
	public Boolean MaximumPermissionsSubmitMacrosAllowed;
	public Boolean MaximumPermissionsSubscribeDashboardToOtherUsers;
	public Boolean MaximumPermissionsSubscribeReportToOtherUsers;
	public Boolean MaximumPermissionsSubscribeReportsRunAsUser;
	public Boolean MaximumPermissionsSubscribeToLightningDashboards;
	public Boolean MaximumPermissionsSubscribeToLightningReports;
	public Boolean MaximumPermissionsTransferAnyCase;
	public Boolean MaximumPermissionsTransferAnyEntity;
	public Boolean MaximumPermissionsTransferAnyLead;
	public Boolean MaximumPermissionsTwoFactorApi;
	public Boolean MaximumPermissionsUseTeamReassignWizards;
	public Boolean MaximumPermissionsUseTemplatedApp;
	public Boolean MaximumPermissionsUseWebLink;
	public Boolean MaximumPermissionsViewAllActivities;
	public Boolean MaximumPermissionsViewAllData;
	public Boolean MaximumPermissionsViewAllUsers;
	public Boolean MaximumPermissionsViewContent;
	public Boolean MaximumPermissionsViewDataAssessment;
	public Boolean MaximumPermissionsViewDataCategories;
	public Boolean MaximumPermissionsViewEncryptedData;
	public Boolean MaximumPermissionsViewEventLogFiles;
	public Boolean MaximumPermissionsViewForecastingPredictive;
	public Boolean MaximumPermissionsViewHealthCheck;
	public Boolean MaximumPermissionsViewHelpLink;
	public Boolean MaximumPermissionsViewMyTeamsDashboards;
	public Boolean MaximumPermissionsViewOnlyEmbeddedAppUser;
	public Boolean MaximumPermissionsViewPublicDashboards;
	public Boolean MaximumPermissionsViewPublicReports;
	public Boolean MaximumPermissionsViewRoles;
	public Boolean MaximumPermissionsViewSetup;
	public Boolean MaximumPermissionsWaveTabularDownload;
	public Boolean MaximumPermissionsWorkCalibrationUser;
	public Boolean MaximumPermissionsWorkDotComUserPerm;
	public String PermissionSetLicenseKey;
	public String Status;
	public com.nawforce.platform.System.Integer TotalLicenses;
	public Integer UsedLicenses;

	public GrantedByLicense GrantedByLicenses;
	public PermissionSetLicenseAssign PermissionSetLicenseAssignments;
}
