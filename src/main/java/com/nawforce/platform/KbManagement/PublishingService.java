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
package com.nawforce.platform.KbManagement;

import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Datetime;
import com.nawforce.platform.System.Integer;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class PublishingService {
	public PublishingService() {throw new java.lang.UnsupportedOperationException();}

	public static void archiveOnlineArticle(String articleId, Datetime scheduledDate) {throw new java.lang.UnsupportedOperationException();}
	public static void assignDraftArticleTask(String articleId, String assigneeId, String instructions, Datetime dueDate, Boolean sendEmailNotification) {throw new java.lang.UnsupportedOperationException();}
	public static void assignDraftTranslationTask(String translationVersionId, String assigneeId, String instructions, Datetime dueDate, Boolean sendEmailNotification) {throw new java.lang.UnsupportedOperationException();}
	public static void cancelScheduledArchivingOfArticle(String articleId) {throw new java.lang.UnsupportedOperationException();}
	public static void cancelScheduledPublicationOfArticle(String articleId) {throw new java.lang.UnsupportedOperationException();}
	public static void completeTranslation(String articleVersionId) {throw new java.lang.UnsupportedOperationException();}
	public static void deleteArchivedArticle(String articleId) {throw new java.lang.UnsupportedOperationException();}
	public static void deleteArchivedArticleVersion(String articleId, Integer versionNumber) {throw new java.lang.UnsupportedOperationException();}
	public static void deleteDraftArticle(String articleId) {throw new java.lang.UnsupportedOperationException();}
	public static void deleteDraftTranslation(String articleVersionId) {throw new java.lang.UnsupportedOperationException();}
	public static String editArchivedArticle(String articleId) {throw new java.lang.UnsupportedOperationException();}
	public static String editOnlineArticle(String articleId, Boolean unpublish) {throw new java.lang.UnsupportedOperationException();}
	public static String editPublishedTranslation(String articleId, String language, Boolean unpublish) {throw new java.lang.UnsupportedOperationException();}
	public static void publishArticle(String articleId, Boolean flagAsNew) {throw new java.lang.UnsupportedOperationException();}
	public static String restoreOldVersion(String articleId, Integer versionNumber) {throw new java.lang.UnsupportedOperationException();}
	public static void scheduleForPublication(String articleId, Datetime scheduledDate) {throw new java.lang.UnsupportedOperationException();}
	public static void setTranslationToIncomplete(String articleVersionId) {throw new java.lang.UnsupportedOperationException();}
	public static String submitForTranslation(String articleId, String language, String assigneeId, Datetime dueDate) {throw new java.lang.UnsupportedOperationException();}
}
