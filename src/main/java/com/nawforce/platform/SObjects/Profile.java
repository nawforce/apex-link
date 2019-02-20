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
import com.nawforce.platform.System.String;
import com.nawforce.platform.System.*;

@SuppressWarnings("unused")
public class Profile extends SObject {
	public String Description;
	public Datetime LastReferencedDate;
	public Datetime LastViewedDate;
	public String Name;
	public Boolean PermissionsAccessCMC;
	public Boolean PermissionsAccountIQUser;
	public Boolean PermissionsActivateContract;
	public Boolean PermissionsActivateOrder;
	public Boolean PermissionsActivitiesAccess;
	public Boolean PermissionsAddAnalyticsRemoteConnections;
	public Boolean PermissionsAddDirectMessageMembers;
	public Boolean PermissionsAllowLightningLogin;
	public Boolean PermissionsAllowUniversalSearch;
	public Boolean PermissionsAllowViewEditConvertedLeads;
	public Boolean PermissionsAllowViewKnowledge;
	public Boolean PermissionsApexRestServices;
	public Boolean PermissionsApiEnabled;
	public Boolean PermissionsAssignPermissionSets;
	public Boolean PermissionsAssignTopics;
	public Boolean PermissionsAuthorApex;
	public Boolean PermissionsAutomatedDataEntry;
	public Boolean PermissionsAutomaticActivityCapture;
	public Boolean PermissionsBulkApiHardDelete;
	public Boolean PermissionsBulkMacrosAllowed;
	public Boolean PermissionsCampaignInfluence2;
	public Boolean PermissionsCanApproveFeedPost;
	public Boolean PermissionsCanEditDataPrepRecipe;
	public Boolean PermissionsCanInsertFeedSystemFields;
	public Boolean PermissionsCanManageMaps;
	public Boolean PermissionsCanUseNewDashboardBuilder;
	public Boolean PermissionsCanVerifyComment;
	public Boolean PermissionsChangeDashboardColors;
	public Boolean PermissionsChatterComposeUiCodesnippet;
	public Boolean PermissionsChatterEditOwnPost;
	public Boolean PermissionsChatterEditOwnRecordPost;
	public Boolean PermissionsChatterFileLink;
	public Boolean PermissionsChatterInternalUser;
	public Boolean PermissionsChatterInviteExternalUsers;
	public Boolean PermissionsChatterOwnGroups;
	public Boolean PermissionsCloseConversations;
	public Boolean PermissionsConfigCustomRecs;
	public Boolean PermissionsConnectOrgToEnvironmentHub;
	public Boolean PermissionsContentAdministrator;
	public Boolean PermissionsContentWorkspaces;
	public Boolean PermissionsConvertLeads;
	public Boolean PermissionsCreateCustomizeDashboards;
	public Boolean PermissionsCreateCustomizeFilters;
	public Boolean PermissionsCreateCustomizeReports;
	public Boolean PermissionsCreateDashboardFolders;
	public Boolean PermissionsCreateLtngTempInPub;
	public Boolean PermissionsCreateMultiforce;
	public Boolean PermissionsCreateReportFolders;
	public Boolean PermissionsCreateReportInLightning;
	public Boolean PermissionsCreateTopics;
	public Boolean PermissionsCreateWorkspaces;
	public Boolean PermissionsCustomMobileAppsAccess;
	public Boolean PermissionsCustomizeApplication;
	public Boolean PermissionsDataExport;
	public Boolean PermissionsDelegatedTwoFactor;
	public Boolean PermissionsDeleteActivatedContract;
	public Boolean PermissionsDeleteTopics;
	public Boolean PermissionsDistributeFromPersWksp;
	public Boolean PermissionsEditActivatedOrders;
	public Boolean PermissionsEditBrandTemplates;
	public Boolean PermissionsEditCaseComments;
	public Boolean PermissionsEditEvent;
	public Boolean PermissionsEditHtmlTemplates;
	public Boolean PermissionsEditKnowledge;
	public Boolean PermissionsEditMyDashboards;
	public Boolean PermissionsEditMyReports;
	public Boolean PermissionsEditOppLineItemUnitPrice;
	public Boolean PermissionsEditPublicDocuments;
	public Boolean PermissionsEditPublicFilters;
	public Boolean PermissionsEditPublicTemplates;
	public Boolean PermissionsEditReadonlyFields;
	public Boolean PermissionsEditTask;
	public Boolean PermissionsEditTopics;
	public Boolean PermissionsEmailAdministration;
	public Boolean PermissionsEmailMass;
	public Boolean PermissionsEmailSingle;
	public Boolean PermissionsEmailTemplateManagement;
	public Boolean PermissionsEnableCommunityAppLauncher;
	public Boolean PermissionsEnableNotifications;
	public Boolean PermissionsEnableSOS;
	public Boolean PermissionsExportReport;
	public Boolean PermissionsFeedPinning;
	public Boolean PermissionsFlowUFLRequired;
	public Boolean PermissionsForceTwoFactor;
	public Boolean PermissionsGiveRecognitionBadge;
	public Boolean PermissionsGovernNetworks;
	public Boolean PermissionsHideReadByList;
	public Boolean PermissionsIdentityConnect;
	public Boolean PermissionsIdentityEnabled;
	public Boolean PermissionsImportCustomObjects;
	public Boolean PermissionsImportLeads;
	public Boolean PermissionsImportPersonal;
	public Boolean PermissionsInsightsAppAdmin;
	public Boolean PermissionsInsightsAppDashboardEditor;
	public Boolean PermissionsInsightsAppEltEditor;
	public Boolean PermissionsInsightsAppUploadUser;
	public Boolean PermissionsInsightsAppUser;
	public Boolean PermissionsInsightsCreateApplication;
	public Boolean PermissionsInstallMultiforce;
	public Boolean PermissionsLeadScoreUser;
	public Boolean PermissionsLightningConsoleAllowedForUser;
	public Boolean PermissionsLightningExperienceUser;
	public Boolean PermissionsListEmailSend;
	public Boolean PermissionsLtngPromoReserved01UserPerm;
	public Boolean PermissionsManageAnalyticSnapshots;
	public Boolean PermissionsManageAuthProviders;
	public Boolean PermissionsManageBusinessHourHolidays;
	public Boolean PermissionsManageCallCenters;
	public Boolean PermissionsManageCases;
	public Boolean PermissionsManageCategories;
	public Boolean PermissionsManageCertificates;
	public Boolean PermissionsManageChatterMessages;
	public Boolean PermissionsManageContentPermissions;
	public Boolean PermissionsManageContentProperties;
	public Boolean PermissionsManageContentTypes;
	public Boolean PermissionsManageCustomPermissions;
	public Boolean PermissionsManageCustomReportTypes;
	public Boolean PermissionsManageDashbdsInPubFolders;
	public Boolean PermissionsManageDataCategories;
	public Boolean PermissionsManageDataIntegrations;
	public Boolean PermissionsManageDynamicDashboards;
	public Boolean PermissionsManageEmailClientConfig;
	public Boolean PermissionsManageEncryptionKeys;
	public Boolean PermissionsManageExchangeConfig;
	public Boolean PermissionsManageHealthCheck;
	public Boolean PermissionsManageInteraction;
	public Boolean PermissionsManageInternalUsers;
	public Boolean PermissionsManageIpAddresses;
	public Boolean PermissionsManageKnowledge;
	public Boolean PermissionsManageKnowledgeImportExport;
	public Boolean PermissionsManageLeads;
	public Boolean PermissionsManageLoginAccessPolicies;
	public Boolean PermissionsManageMobile;
	public Boolean PermissionsManageNetworks;
	public Boolean PermissionsManagePasswordPolicies;
	public Boolean PermissionsManageProfilesPermissionsets;
	public Boolean PermissionsManagePvtRptsAndDashbds;
	public Boolean PermissionsManageRemoteAccess;
	public Boolean PermissionsManageReportsInPubFolders;
	public Boolean PermissionsManageRoles;
	public Boolean PermissionsManageSearchPromotionRules;
	public Boolean PermissionsManageSessionPermissionSets;
	public Boolean PermissionsManageSharing;
	public Boolean PermissionsManageSolutions;
	public Boolean PermissionsManageSurveys;
	public Boolean PermissionsManageSynonyms;
	public Boolean PermissionsManageTemplatedApp;
	public Boolean PermissionsManageTranslation;
	public Boolean PermissionsManageTwoFactor;
	public Boolean PermissionsManageUnlistedGroups;
	public Boolean PermissionsManageUsers;
	public Boolean PermissionsMassInlineEdit;
	public Boolean PermissionsMergeTopics;
	public Boolean PermissionsModerateChatter;
	public Boolean PermissionsModerateNetworkUsers;
	public Boolean PermissionsModifyAllData;
	public Boolean PermissionsModifyMetadata;
	public Boolean PermissionsModifySecureAgents;
	public Boolean PermissionsNewReportBuilder;
	public Boolean PermissionsOpportunityIQUser;
	public Boolean PermissionsOpportunityScoreUser;
	public Boolean PermissionsPackaging2;
	public Boolean PermissionsPasswordNeverExpires;
	public Boolean PermissionsPreventClassicExperience;
	public Boolean PermissionsPrivacyDataAccess;
	public Boolean PermissionsPublishMultiforce;
	public Boolean PermissionsRecordVisibilityAPI;
	public Boolean PermissionsRemoveDirectMessageMembers;
	public Boolean PermissionsResetPasswords;
	public Boolean PermissionsRunFlow;
	public Boolean PermissionsRunReports;
	public Boolean PermissionsSalesAnalyticsUser;
	public Boolean PermissionsSalesConsole;
	public Boolean PermissionsSalesforceIQInbox;
	public Boolean PermissionsScheduleReports;
	public Boolean PermissionsSelectFilesFromSalesforce;
	public Boolean PermissionsSendAnnouncementEmails;
	public Boolean PermissionsSendSitRequests;
	public Boolean PermissionsShareInternalArticles;
	public Boolean PermissionsShowCompanyNameAsUserBadge;
	public Boolean PermissionsSolutionImport;
	public Boolean PermissionsStdAutomaticActivityCapture;
	public Boolean PermissionsSubmitMacrosAllowed;
	public Boolean PermissionsSubscribeDashboardToOtherUsers;
	public Boolean PermissionsSubscribeReportToOtherUsers;
	public Boolean PermissionsSubscribeReportsRunAsUser;
	public Boolean PermissionsSubscribeToLightningDashboards;
	public Boolean PermissionsSubscribeToLightningReports;
	public Boolean PermissionsTransferAnyCase;
	public Boolean PermissionsTransferAnyEntity;
	public Boolean PermissionsTransferAnyLead;
	public Boolean PermissionsTwoFactorApi;
	public Boolean PermissionsUseTeamReassignWizards;
	public Boolean PermissionsUseTemplatedApp;
	public Boolean PermissionsUseWebLink;
	public Boolean PermissionsViewAllActivities;
	public Boolean PermissionsViewAllData;
	public Boolean PermissionsViewAllUsers;
	public Boolean PermissionsViewContent;
	public Boolean PermissionsViewDataAssessment;
	public Boolean PermissionsViewDataCategories;
	public Boolean PermissionsViewEncryptedData;
	public Boolean PermissionsViewEventLogFiles;
	public Boolean PermissionsViewForecastingPredictive;
	public Boolean PermissionsViewHealthCheck;
	public Boolean PermissionsViewHelpLink;
	public Boolean PermissionsViewMyTeamsDashboards;
	public Boolean PermissionsViewOnlyEmbeddedAppUser;
	public Boolean PermissionsViewPublicDashboards;
	public Boolean PermissionsViewPublicReports;
	public Boolean PermissionsViewRoles;
	public Boolean PermissionsViewSetup;
	public Boolean PermissionsWaveTabularDownload;
	public Boolean PermissionsWorkCalibrationUser;
	public Boolean PermissionsWorkDotComUserPerm;
	public Id UserLicenseId;
	public UserLicense UserLicense;
	public String UserType;

	public User Users;
}
