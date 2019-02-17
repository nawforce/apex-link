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
package io.github.nawforce.platform.SObjects;

import io.github.nawforce.platform.System.Integer;
import io.github.nawforce.platform.System.String;
import io.github.nawforce.platform.System.*;

@SuppressWarnings("unused")
public class Dashboard extends SObject {
	public String BackgroundDirection;
	public Integer BackgroundEnd;
	public Integer BackgroundStart;
	public String ChartTheme;
	public String ColorPalette;
	public String DashboardResultRefreshedDate;
	public String DashboardResultRunningUser;
	public String Description;
	public String DeveloperName;
	public Id FolderId;
	public Folder Folder;
	public String FolderName;
	public Datetime LastReferencedDate;
	public Datetime LastViewedDate;
	public String LeftSize;
	public String MiddleSize;
	public String NamespacePrefix;
	public String RightSize;
	public Id RunningUserId;
	public User RunningUser;
	public Integer TextColor;
	public String Title;
	public Integer TitleColor;
	public Integer TitleSize;
	public String Type;

	public AttachedContentDocument AttachedContentDocuments;
	public CombinedAttachment CombinedAttachments;
	public ContentDocumentLink ContentDocumentLinks;
	public DashboardComponent DashboardComponents;
	public EntitySubscription FeedSubscriptionsForEntity;
	public DashboardFeed Feeds;
}
