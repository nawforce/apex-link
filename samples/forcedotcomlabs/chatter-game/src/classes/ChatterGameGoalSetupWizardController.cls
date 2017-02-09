/*
Copyright (c) 2011 salesforce.com, inc.
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

THIS SOFTWARE IS PROVIDED BY THE AUTHOR "AS IS" AND ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

By: Chris Kemp <ckemp@salesforce.com>
*/

public with sharing class ChatterGameGoalSetupWizardController {

    public String step1 {get; set;}
    public String name {get; set;}
    public String description {get; set;}
    public String rewardAmount {get; set;}
    public String status {get; set;}
    public Date startDate {get; set;}
    public Date endDate {get; set;}
    public String[] usersSelected = new String[]{};

    public List<SelectOption> getStep1Options() {
        List<SelectOption> options = new List<SelectOption>();
        options.add(new SelectOption('Post', 'Any Chatter post except status updates'));
        options.add(new SelectOption('PostFile', 'Uploading a file on a Chatter feed'));
        options.add(new SelectOption('PostLink', 'Adding a link on a Chatter feed'));
        options.add(new SelectOption('Comment', 'Commenting on a Chatter post'));
        options.add(new SelectOption('Likes', 'Having someone Like your Chatter post'));
        options.add(new SelectOption('FollowUser', 'Following another Chatter user'));
        options.add(new SelectOption('FollowItem', 'Following a record of any type in Chatter'));
        return options;
    }

    public List<SelectOption> getStatusOptions() {
        List<SelectOption> options = new List<SelectOption>();
        options.add(new SelectOption('Active', 'Active'));
        options.add(new SelectOption('Disabled', 'Disabled'));
        return options;
    }

    public PaginatedSelectList getUserOptions() {
        PaginatedSelectList options = new PaginatedSelectList();

        for (User thisUser: 
                [select Id, FirstName, LastName from User 
                where isActive = true and UserType = 'Standard' 
                order by LastName, FirstName]) {
            options.add(new SelectOption(thisUser.Id, thisUser.LastName + 
                ', ' + thisUser.FirstName));
        }
        
        return options;
    }

    public String[] getUsersSelected() {
        return usersSelected;
    }
                    
    public void setUsersSelected(String[] usersSelected) { 
        this.usersSelected = usersSelected; 
    }

    public PageReference action() {

        Boolean isValid = true;

        // Error checking
        if ((name.trim()).equals('')) {
            ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, 
                'Goal Name must not be blank'));
            isValid = false;
        }

        if (endDate != null && endDate < date.today()) {
            ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, 
                'End date cannot be in the past'));
            isValid = false;
        }

        if (startDate != null && endDate != null && endDate < startDate) {
            ApexPages.addMessage(new ApexPages.message(ApexPages.Severity.ERROR, 
                'End date cannot be before the start date'));
            isValid = false;
        }
                
        try {
            Decimal.valueOf(rewardAmount);
            if (Decimal.valueOf(rewardAmount) <= 0) {
                ApexPages.addMessage(new ApexPages.message(
                    ApexPages.Severity.ERROR, 
                    'Reward Amount must be greater than zero'));
                isValid = false;
            }
        } catch (System.TypeException e) {
            ApexPages.addMessage(new ApexPages.message(ApexPages.severity.ERROR, 
                'Reward Amount must be a numeric value'));
            isValid = false;
        }
        
        if (!isValid) {
            return null;
        }

        // Save new Goal
        Goal__c newGoal = new Goal__c();
        newGoal.Name = this.name;
        newGoal.Description__c = this.description;
        newGoal.Start_Date__c = this.startDate;
        newGoal.End_Date__c = this.endDate;
        newGoal.Reward_Amount__c = Decimal.valueOf(this.rewardAmount);
        newGoal.Status__c = this.Status;
        newGoal.Type__c = 'Chatter';
        newGoal.Chatter_Type__c = this.step1;
        insert newGoal;

        // Save Participant list associated with Goal
        List<Goal_Participant__c> participantList = 
            new List<Goal_Participant__c>();    
        for (String userId: usersSelected) {
            Goal_Participant__c participant = new Goal_Participant__c();
            participant.Goal__c = newGoal.Id;
            participant.User__c = userId;
            participantList.add(participant);
        }
        insert participantList;

        // Redirect the user to the success page
        PageReference pageRef = Page.ChatterGameGoalSetupSuccess;
        pageRef.setRedirect(true);
        return pageRef;
    }
}