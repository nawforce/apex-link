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
public class UserChangeEvent extends SObject {
	public String AboutMe;
	public Id AccountId;
	public Account Account;
	public com.nawforce.platform.System.Address Address;
	public String Alias;
	public Id CallCenterId;
	public CallCenter CallCenter;
	public Object ChangeEventHeader;
	public String City;
	public String CommunityNickname;
	public String CompanyName;
	public Id ContactId;
	public Contact Contact;
	public String Country;
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
	public com.nawforce.platform.System.Boolean EmailPreferencesAutoBcc;
	public com.nawforce.platform.System.Boolean EmailPreferencesAutoBccStayInTouch;
	public com.nawforce.platform.System.Boolean EmailPreferencesStayInTouchReminder;
	public String EmployeeNumber;
	public String Extension;
	public String Fax;
	public String FederationIdentifier;
	public String FirstName;
	public com.nawforce.platform.System.Boolean ForecastEnabled;
	public String GeocodeAccuracy;
	public com.nawforce.platform.System.Boolean IsActive;
	public com.nawforce.platform.System.Boolean IsProfilePhotoActive;
	public com.nawforce.platform.System.Integer JigsawImportLimitOverride;
	public String LanguageLocaleKey;
	public Datetime LastLoginDate;
	public String LastName;
	public Datetime LastPasswordChangeDate;
	public Decimal Latitude;
	public String LocaleSidKey;
	public Decimal Longitude;
	public Id ManagerId;
	public User Manager;
	public String MobilePhone;
	public String Name;
	public Datetime OfflinePdaTrialExpirationDate;
	public Datetime OfflineTrialExpirationDate;
	public String Phone;
	public String PostalCode;
	public Id ProfileId;
	public Profile Profile;
	public com.nawforce.platform.System.Boolean ReceivesAdminInfoEmails;
	public com.nawforce.platform.System.Boolean ReceivesInfoEmails;
	public String ReplayId;
	public String SenderEmail;
	public String SenderName;
	public String Signature;
	public String State;
	public String StayInTouchNote;
	public String StayInTouchSignature;
	public String StayInTouchSubject;
	public String Street;
	public String TimeZoneSidKey;
	public String Title;
	public com.nawforce.platform.System.Boolean UserPermissionsCallCenterAutoLogin;
	public com.nawforce.platform.System.Boolean UserPermissionsInteractionUser;
	public com.nawforce.platform.System.Boolean UserPermissionsJigsawProspectingUser;
	public com.nawforce.platform.System.Boolean UserPermissionsKnowledgeUser;
	public com.nawforce.platform.System.Boolean UserPermissionsMarketingUser;
	public com.nawforce.platform.System.Boolean UserPermissionsMobileUser;
	public com.nawforce.platform.System.Boolean UserPermissionsOfflineUser;
	public com.nawforce.platform.System.Boolean UserPermissionsSFContentUser;
	public com.nawforce.platform.System.Boolean UserPermissionsSiteforceContributorUser;
	public com.nawforce.platform.System.Boolean UserPermissionsSiteforcePublisherUser;
	public com.nawforce.platform.System.Boolean UserPermissionsSupportUser;
	public com.nawforce.platform.System.Boolean UserPermissionsWorkDotComUserFeature;
	public com.nawforce.platform.System.Boolean UserPreferencesActivityRemindersPopup;
	public com.nawforce.platform.System.Boolean UserPreferencesApexPagesDeveloperMode;
	public com.nawforce.platform.System.Boolean UserPreferencesCacheDiagnostics;
	public com.nawforce.platform.System.Boolean UserPreferencesContentEmailAsAndWhen;
	public com.nawforce.platform.System.Boolean UserPreferencesContentNoEmail;
	public com.nawforce.platform.System.Boolean UserPreferencesCreateLEXAppsWTShown;
	public com.nawforce.platform.System.Boolean UserPreferencesDisCommentAfterLikeEmail;
	public com.nawforce.platform.System.Boolean UserPreferencesDisMentionsCommentEmail;
	public com.nawforce.platform.System.Boolean UserPreferencesDisProfPostCommentEmail;
	public com.nawforce.platform.System.Boolean UserPreferencesDisableAllFeedsEmail;
	public com.nawforce.platform.System.Boolean UserPreferencesDisableBookmarkEmail;
	public com.nawforce.platform.System.Boolean UserPreferencesDisableChangeCommentEmail;
	public com.nawforce.platform.System.Boolean UserPreferencesDisableEndorsementEmail;
	public com.nawforce.platform.System.Boolean UserPreferencesDisableFeedbackEmail;
	public com.nawforce.platform.System.Boolean UserPreferencesDisableFileShareNotificationsForApi;
	public com.nawforce.platform.System.Boolean UserPreferencesDisableFollowersEmail;
	public com.nawforce.platform.System.Boolean UserPreferencesDisableLaterCommentEmail;
	public com.nawforce.platform.System.Boolean UserPreferencesDisableLikeEmail;
	public com.nawforce.platform.System.Boolean UserPreferencesDisableMentionsPostEmail;
	public com.nawforce.platform.System.Boolean UserPreferencesDisableMessageEmail;
	public com.nawforce.platform.System.Boolean UserPreferencesDisableProfilePostEmail;
	public com.nawforce.platform.System.Boolean UserPreferencesDisableSharePostEmail;
	public com.nawforce.platform.System.Boolean UserPreferencesDisableWorkEmail;
	public com.nawforce.platform.System.Boolean UserPreferencesEnableAutoSubForFeeds;
	public com.nawforce.platform.System.Boolean UserPreferencesEventRemindersCheckboxDefault;
	public com.nawforce.platform.System.Boolean UserPreferencesExcludeMailAppAttachments;
	public com.nawforce.platform.System.Boolean UserPreferencesFavoritesShowTopFavorites;
	public com.nawforce.platform.System.Boolean UserPreferencesFavoritesWTShown;
	public com.nawforce.platform.System.Boolean UserPreferencesGlobalNavBarWTShown;
	public com.nawforce.platform.System.Boolean UserPreferencesGlobalNavGridMenuWTShown;
	public com.nawforce.platform.System.Boolean UserPreferencesHasCelebrationBadge;
	public com.nawforce.platform.System.Boolean UserPreferencesHideBiggerPhotoCallout;
	public com.nawforce.platform.System.Boolean UserPreferencesHideCSNDesktopTask;
	public com.nawforce.platform.System.Boolean UserPreferencesHideCSNGetChatterMobileTask;
	public com.nawforce.platform.System.Boolean UserPreferencesHideChatterOnboardingSplash;
	public com.nawforce.platform.System.Boolean UserPreferencesHideEndUserOnboardingAssistantModal;
	public com.nawforce.platform.System.Boolean UserPreferencesHideLightningMigrationModal;
	public com.nawforce.platform.System.Boolean UserPreferencesHideS1BrowserUI;
	public com.nawforce.platform.System.Boolean UserPreferencesHideSecondChatterOnboardingSplash;
	public com.nawforce.platform.System.Boolean UserPreferencesHideSfxWelcomeMat;
	public com.nawforce.platform.System.Boolean UserPreferencesJigsawListUser;
	public com.nawforce.platform.System.Boolean UserPreferencesLightningExperiencePreferred;
	public com.nawforce.platform.System.Boolean UserPreferencesNewLightningReportRunPageEnabled;
	public com.nawforce.platform.System.Boolean UserPreferencesPathAssistantCollapsed;
	public com.nawforce.platform.System.Boolean UserPreferencesPipelineViewHideHelpPopover;
	public com.nawforce.platform.System.Boolean UserPreferencesPreviewCustomTheme;
	public com.nawforce.platform.System.Boolean UserPreferencesPreviewLightning;
	public com.nawforce.platform.System.Boolean UserPreferencesRecordHomeReservedWTShown;
	public com.nawforce.platform.System.Boolean UserPreferencesRecordHomeSectionCollapseWTShown;
	public com.nawforce.platform.System.Boolean UserPreferencesReminderSoundOff;
	public com.nawforce.platform.System.Boolean UserPreferencesShowCityToExternalUsers;
	public com.nawforce.platform.System.Boolean UserPreferencesShowCityToGuestUsers;
	public com.nawforce.platform.System.Boolean UserPreferencesShowCountryToExternalUsers;
	public com.nawforce.platform.System.Boolean UserPreferencesShowCountryToGuestUsers;
	public com.nawforce.platform.System.Boolean UserPreferencesShowEmailToExternalUsers;
	public com.nawforce.platform.System.Boolean UserPreferencesShowEmailToGuestUsers;
	public com.nawforce.platform.System.Boolean UserPreferencesShowFaxToExternalUsers;
	public com.nawforce.platform.System.Boolean UserPreferencesShowFaxToGuestUsers;
	public com.nawforce.platform.System.Boolean UserPreferencesShowManagerToExternalUsers;
	public com.nawforce.platform.System.Boolean UserPreferencesShowManagerToGuestUsers;
	public com.nawforce.platform.System.Boolean UserPreferencesShowMobilePhoneToExternalUsers;
	public com.nawforce.platform.System.Boolean UserPreferencesShowMobilePhoneToGuestUsers;
	public com.nawforce.platform.System.Boolean UserPreferencesShowPostalCodeToExternalUsers;
	public com.nawforce.platform.System.Boolean UserPreferencesShowPostalCodeToGuestUsers;
	public com.nawforce.platform.System.Boolean UserPreferencesShowProfilePicToGuestUsers;
	public com.nawforce.platform.System.Boolean UserPreferencesShowStateToExternalUsers;
	public com.nawforce.platform.System.Boolean UserPreferencesShowStateToGuestUsers;
	public com.nawforce.platform.System.Boolean UserPreferencesShowStreetAddressToExternalUsers;
	public com.nawforce.platform.System.Boolean UserPreferencesShowStreetAddressToGuestUsers;
	public com.nawforce.platform.System.Boolean UserPreferencesShowTitleToExternalUsers;
	public com.nawforce.platform.System.Boolean UserPreferencesShowTitleToGuestUsers;
	public com.nawforce.platform.System.Boolean UserPreferencesShowWorkPhoneToExternalUsers;
	public com.nawforce.platform.System.Boolean UserPreferencesShowWorkPhoneToGuestUsers;
	public com.nawforce.platform.System.Boolean UserPreferencesSortFeedByComment;
	public com.nawforce.platform.System.Boolean UserPreferencesSuppressEventSFXReminders;
	public com.nawforce.platform.System.Boolean UserPreferencesSuppressTaskSFXReminders;
	public com.nawforce.platform.System.Boolean UserPreferencesTaskRemindersCheckboxDefault;
	public Boolean UserPreferencesUserDebugModePref;
	public Id UserRoleId;
	public UserRole UserRole;
	public String UserType;
	public String Username;
}
