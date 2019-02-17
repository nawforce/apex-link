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

import io.github.nawforce.platform.System.Boolean;
import io.github.nawforce.platform.System.Integer;
import io.github.nawforce.platform.System.String;
import io.github.nawforce.platform.System.*;

@SuppressWarnings("unused")
public class Solution extends SObject {
	public String CurrencyIsoCode;
	public Boolean IsHtml;
	public Boolean IsPublished;
	public Boolean IsPublishedInPublicKb;
	public Boolean IsReviewed;
	public Datetime LastReferencedDate;
	public Datetime LastViewedDate;
	public Id OwnerId;
	public User Owner;
	public String SolutionName;
	public String SolutionNote;
	public String SolutionNumber;
	public String Status;
	public Integer TimesUsed;

	public ActivityHistory ActivityHistories;
	public AttachedContentDocument AttachedContentDocuments;
	public Attachment Attachments;
	public CaseSolution CaseSolutions;
	public CombinedAttachment CombinedAttachments;
	public ContentDocumentLink ContentDocumentLinks;
	public EmailMessage Emails;
	public Event Events;
	public EntitySubscription FeedSubscriptionsForEntity;
	public SolutionFeed Feeds;
	public SolutionHistory Histories;
	public OpenActivity OpenActivities;
	public ProcessInstance ProcessInstances;
	public ProcessInstanceHistory ProcessSteps;
	public Task Tasks;
	public TopicAssignment TopicAssignments;
	public Vote Votes;
}
