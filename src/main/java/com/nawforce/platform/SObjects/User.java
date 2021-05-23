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
public class User extends SObject {
	public static SObjectType$<User> SObjectType;
	public static SObjectFields$<User> Fields;

	public String AboutMe;
	public Id AccountId;
	public Account Account;
	public Address Address;
	public String Alias;
	public String BadgeText;
	public String BannerPhotoUrl;
	public Id CallCenterId;
	public CallCenter CallCenter;
	public String City;
	public String CommunityNickname;
	public String CompanyName;
	public Id ContactId;
	public Contact Contact;
	public String Country;
	public String CountryCode;
	public Id CreatedById;
	public User CreatedBy;
	public Datetime CreatedDate;
	public String CurrencyIsoCode;
	public String DefaultCurrencyIsoCode;
	public String DefaultGroupNotificationFrequency;
	public Id DelegatedApproverId;
	public Group DelegatedApprover;
	public String Department;
	public String DigestFrequency;
	public String Division;
	public String Email;
	public String EmailEncodingKey;
	public Boolean EmailPreferencesAutoBcc;
	public Boolean EmailPreferencesAutoBccStayInTouch;
	public Boolean EmailPreferencesStayInTouchReminder;
	public String EmployeeNumber;
	public String Extension;
	public String Fax;
	public String FederationIdentifier;
	public String FirstName;
	public Boolean ForecastEnabled;
	public String FullPhotoUrl;
	public String GeocodeAccuracy;
	public Id Id;
	public Id IndividualId;
	public Individual Individual;
	public Boolean IsActive;
	public Boolean IsExtIndicatorVisible;
	public Boolean IsPortalEnabled;           // TODO: Only if communities?
	public Boolean IsProfilePhotoActive;
	public Integer JigsawImportLimitOverride;
	public String LanguageLocaleKey;
	public Datetime LastLoginDate;
	public Id LastModifiedById;
	public User LastModifiedBy;
	public Datetime LastModifiedDate;
	public String LastName;
	public Datetime LastPasswordChangeDate;
	public Datetime LastReferencedDate;
	public Datetime LastViewedDate;
	public Decimal Latitude;
	public String LocaleSidKey;
	public Decimal Longitude;
	public Id ManagerId;
	public User Manager;
	public String MediumBannerPhotoUrl;
	public String MediumPhotoUrl;
	public String MobilePhone;
	public String Name;
	public Datetime OfflinePdaTrialExpirationDate;
	public Datetime OfflineTrialExpirationDate;
	public String OutOfOfficeMessage;
	public String Phone;
	public String PostalCode;
	public Id ProfileId;
	public Profile Profile;
	public Boolean ReceivesAdminInfoEmails;
	public Boolean ReceivesInfoEmails;
	public String SenderEmail;
	public String SenderName;
	public String Signature;
	public String SmallBannerPhotoUrl;
	public String SmallPhotoUrl;
	public String State;
	public String StateCode;
	public String StayInTouchNote;
	public String StayInTouchSignature;
	public String StayInTouchSubject;
	public String Street;
	public Datetime SystemModstamp;
	public String TimeZoneSidKey;
	public String Title;
	public Boolean UserPermissionsCallCenterAutoLogin;
	public Boolean UserPermissionsInteractionUser;
	public Boolean UserPermissionsJigsawProspectingUser;
	public Boolean UserPermissionsKnowledgeUser;
	public Boolean UserPermissionsMarketingUser;
	public Boolean UserPermissionsMobileUser;
	public Boolean UserPermissionsOfflineUser;
	public Boolean UserPermissionsSFContentUser;
	public Boolean UserPermissionsSiteforceContributorUser;
	public Boolean UserPermissionsSiteforcePublisherUser;
	public Boolean UserPermissionsSupportUser;
	public Boolean UserPermissionsWorkDotComUserFeature;
	public Boolean UserPreferencesActivityRemindersPopup;
	public Boolean UserPreferencesApexPagesDeveloperMode;
	public Boolean UserPreferencesCacheDiagnostics;
	public Boolean UserPreferencesContentEmailAsAndWhen;
	public Boolean UserPreferencesContentNoEmail;
	public Boolean UserPreferencesCreateLEXAppsWTShown;
	public Boolean UserPreferencesDisCommentAfterLikeEmail;
	public Boolean UserPreferencesDisMentionsCommentEmail;
	public Boolean UserPreferencesDisProfPostCommentEmail;
	public Boolean UserPreferencesDisableAllFeedsEmail;
	public Boolean UserPreferencesDisableBookmarkEmail;
	public Boolean UserPreferencesDisableChangeCommentEmail;
	public Boolean UserPreferencesDisableEndorsementEmail;
	public Boolean UserPreferencesDisableFeedbackEmail;
	public Boolean UserPreferencesDisableFileShareNotificationsForApi;
	public Boolean UserPreferencesDisableFollowersEmail;
	public Boolean UserPreferencesDisableLaterCommentEmail;
	public Boolean UserPreferencesDisableLikeEmail;
	public Boolean UserPreferencesDisableMentionsPostEmail;
	public Boolean UserPreferencesDisableMessageEmail;
	public Boolean UserPreferencesDisableProfilePostEmail;
	public Boolean UserPreferencesDisableSharePostEmail;
	public Boolean UserPreferencesDisableWorkEmail;
	public Boolean UserPreferencesEnableAutoSubForFeeds;
	public Boolean UserPreferencesEventRemindersCheckboxDefault;
	public Boolean UserPreferencesExcludeMailAppAttachments;
	public Boolean UserPreferencesFavoritesShowTopFavorites;
	public Boolean UserPreferencesFavoritesWTShown;
	public Boolean UserPreferencesGlobalNavBarWTShown;
	public Boolean UserPreferencesGlobalNavGridMenuWTShown;
	public Boolean UserPreferencesHasCelebrationBadge;
	public Boolean UserPreferencesHideBiggerPhotoCallout;
	public Boolean UserPreferencesHideCSNDesktopTask;
	public Boolean UserPreferencesHideCSNGetChatterMobileTask;
	public Boolean UserPreferencesHideChatterOnboardingSplash;
	public Boolean UserPreferencesHideEndUserOnboardingAssistantModal;
	public Boolean UserPreferencesHideLightningMigrationModal;
	public Boolean UserPreferencesHideS1BrowserUI;
	public Boolean UserPreferencesHideSecondChatterOnboardingSplash;
	public Boolean UserPreferencesHideSfxWelcomeMat;
	public Boolean UserPreferencesJigsawListUser;
	public Boolean UserPreferencesLightningExperiencePreferred;
	public Boolean UserPreferencesNewLightningReportRunPageEnabled;
	public Boolean UserPreferencesPathAssistantCollapsed;
	public Boolean UserPreferencesPipelineViewHideHelpPopover;
	public Boolean UserPreferencesPreviewCustomTheme;
	public Boolean UserPreferencesPreviewLightning;
	public Boolean UserPreferencesRecordHomeReservedWTShown;
	public Boolean UserPreferencesRecordHomeSectionCollapseWTShown;
	public Boolean UserPreferencesReminderSoundOff;
	public Boolean UserPreferencesShowCityToExternalUsers;
	public Boolean UserPreferencesShowCityToGuestUsers;
	public Boolean UserPreferencesShowCountryToExternalUsers;
	public Boolean UserPreferencesShowCountryToGuestUsers;
	public Boolean UserPreferencesShowEmailToExternalUsers;
	public Boolean UserPreferencesShowEmailToGuestUsers;
	public Boolean UserPreferencesShowFaxToExternalUsers;
	public Boolean UserPreferencesShowFaxToGuestUsers;
	public Boolean UserPreferencesShowManagerToExternalUsers;
	public Boolean UserPreferencesShowManagerToGuestUsers;
	public Boolean UserPreferencesShowMobilePhoneToExternalUsers;
	public Boolean UserPreferencesShowMobilePhoneToGuestUsers;
	public Boolean UserPreferencesShowPostalCodeToExternalUsers;
	public Boolean UserPreferencesShowPostalCodeToGuestUsers;
	public Boolean UserPreferencesShowProfilePicToGuestUsers;
	public Boolean UserPreferencesShowStateToExternalUsers;
	public Boolean UserPreferencesShowStateToGuestUsers;
	public Boolean UserPreferencesShowStreetAddressToExternalUsers;
	public Boolean UserPreferencesShowStreetAddressToGuestUsers;
	public Boolean UserPreferencesShowTitleToExternalUsers;
	public Boolean UserPreferencesShowTitleToGuestUsers;
	public Boolean UserPreferencesShowWorkPhoneToExternalUsers;
	public Boolean UserPreferencesShowWorkPhoneToGuestUsers;
	public Boolean UserPreferencesSortFeedByComment;
	public Boolean UserPreferencesSuppressEventSFXReminders;
	public Boolean UserPreferencesSuppressTaskSFXReminders;
	public Boolean UserPreferencesTaskRemindersCheckboxDefault;
	public Boolean UserPreferencesUserDebugModePref;
	public Id UserRoleId;
	public UserRole UserRole;
	public String UserType;
	public String Username;

	public AcceptedEventRelation[] AcceptedEventRelations;
	public AccountUserTerritory2View[] AccountUserTerritory2Views;
	public AttachedContentDocument[] AttachedContentDocuments;
	public CombinedAttachment[] CombinedAttachments;
	public ContactRequest[] ContactRequests;
	public ContentDocumentLink[] ContentDocumentLinks;
	public Contract[] ContractsSigned;
	public DeclinedEventRelation[] DeclinedEventRelations;
	public User[] DelegatedUsers;
	public EmailMessageRelation[] EmailMessageRelations;
	public EventRelation[] EventRelations;
	public ExternalDataUserAuth[] ExternalDataUserAuths;
	public EntitySubscription[] FeedSubscriptions;
	public EntitySubscription[] FeedSubscriptionsForEntity;
	public UserFeed[] Feeds;
	public CollaborationGroupMemberRequest[] GroupMembershipRequests;
	public CollaborationGroupMember[] GroupMemberships;
	public InstalledMobileApp[] InstalledMobileApps;
	public User[] ManagedUsers;
	public OpportunitySplit[] OpportunitySplits;
	public OpportunityTeamMember[] OpportunityTeams;
	public OutgoingEmailRelation[] OutgoingEmailRelations;
	public OwnedContentDocument[] OwnedContentDocuments;
	public PermissionSetAssignment[] PermissionSetAssignments;
	public PermissionSetLicenseAssign[] PermissionSetLicenseAssignments;
	public UserEmailPreferredPerson[] PersonRecord;
	public RecordActionHistory[] RecordActionHistories;
	public RecordAction[] RecordActions;
	public SessionPermSetActivation[] SessionPermSetActivations;
	public UserShare[] Shares;
	public UndecidedEventRelation[] UndecidedEventRelations;
	public UserEntityAccess[] UserEntityAccessRights;
	public UserFieldAccess[] UserFieldAccessRights;
	public UserPreference[] UserPreferences;
	public Site[] UserSites;
	public UserTeamMember[] UserTeams;
	public UserTerritory2Association[] UserTerritory2Associations;

	public User clone$() {throw new java.lang.UnsupportedOperationException();}
	public User clone$(Boolean preserveId) {throw new java.lang.UnsupportedOperationException();}
	public User clone$(Boolean preserveId, Boolean isDeepClone) {throw new java.lang.UnsupportedOperationException();}
	public User clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps) {throw new java.lang.UnsupportedOperationException();}
	public User clone$(Boolean preserveId, Boolean isDeepClone, Boolean preserveReadonlyTimestamps, Boolean preserveAutonumber) {throw new java.lang.UnsupportedOperationException();}
}
