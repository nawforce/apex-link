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

public with sharing class ChatterGameGoalAchievement {

    @future
    public static void checkPost(List<Id> feedItemIds) {

        List<FeedItem> postList = 
            [SELECT Id, InsertedById, ParentId, Type FROM FeedItem WHERE Id IN :feedItemIds];
    
        List<Achievement__c> achievementList = new List<Achievement__c>{};
    
        // Go through each updated record    
        for (FeedItem thisFeedItem : postList) {
    
            String currentUserId = thisFeedItem.InsertedById;
            String[] goalsParticipatingIn = new String[0];
            
            // Get all Goals that user is participating in
            for (Goal_Participant__c gp : 
                [SELECT Goal__c
                  FROM Goal_Participant__c
                  WHERE User__c = :currentUserId
                ]) {
                
                goalsParticipatingIn.add(gp.Goal__c);
            }
            
            // Get all active Goals that match criteria for this FeedItem and User who 
            // FeedItem is assigned to is participating in
            for (Goal__c thisGoal : 
                [SELECT Id, Name, Chatter_Type__c, Reward_Amount__c
                  FROM Goal__c
                  WHERE Type__c = 'Chatter'
                      AND Status__c = 'Active'
                      AND (Start_Date__c = NULL OR Start_Date__c <= TODAY)
                      AND (End_Date__c = NULL OR End_Date__c >= TODAY)
                      AND (Chatter_Type__c = 'Post' OR
                          Chatter_Type__c = 'PostFile' OR
                          Chatter_Type__c = 'PostLink')
                      AND Id IN :goalsParticipatingIn
                ]) {
    
                // Save new achievement if criteria are met (no check necessary for previous
                // achievements since you can only insert FeedItems)
                if (!thisFeedItem.Type.equals('TrackedChange') &&
                    (thisGoal.Chatter_Type__c.equals('Post') ||
                    (thisGoal.Chatter_Type__c.equals('PostFile') && 
                        thisFeedItem.Type.equals('ContentPost')) ||
                    (thisGoal.Chatter_Type__c.equals('PostLink') && 
                        thisFeedItem.Type.equals('LinkPost')))) {
                    
                    Achievement__c achievement = new Achievement__c();
                    achievement.Goal__c = thisGoal.Id;
                    achievement.Object_Id__c = thisFeedItem.ParentId + '?ChatterFeedItemId=' 
                        + thisFeedItem.Id;
                    achievement.Points_Awarded__c = thisGoal.Reward_Amount__c;
                    achievement.User__c = currentUserId;
                    achievementList.add(achievement);
                }
            }
        }
        
        insert achievementList;
    }


    @future
    public static void checkComment(List<Id> feedCommentIds, Map<Id, Id> feedItemIds, 
        Map<Id, Id> parentIds, Map<Id, Id> insertedByIds) {

        List<Achievement__c> achievementList = new List<Achievement__c>{};
    
        // Go through each updated record    
        for (Id feedCommentId : feedCommentIds) {
    
            String currentUserId = insertedByIds.get(feedCommentId);
            String[] goalsParticipatingIn = new String[0];
            
            // Get all Goals that user is participating in
            for (Goal_Participant__c gp : 
                [SELECT Goal__c
                  FROM Goal_Participant__c
                  WHERE User__c = :currentUserId
                ]) {
                
                goalsParticipatingIn.add(gp.Goal__c);
            }
                
            // Get all active Goals that match criteria for this FeedItem and User who 
            // FeedItem is assigned to is participating in
            for (Goal__c thisGoal : 
                [SELECT Id, Name, Chatter_Type__c, Reward_Amount__c
                  FROM Goal__c
                  WHERE Type__c = 'Chatter'
                      AND Status__c = 'Active'
                      AND (Start_Date__c = NULL OR Start_Date__c <= TODAY)
                      AND (End_Date__c = NULL OR End_Date__c >= TODAY)
                      AND Chatter_Type__c = 'Comment'
                      AND Id IN :goalsParticipatingIn
                ]) {
    
                // Save new achievement (no check necessary for previous
                // achievements since you can only insert FeedItems)
                Achievement__c achievement = new Achievement__c();
                achievement.Goal__c = thisGoal.Id;
                achievement.Object_Id__c = parentIds.get(feedCommentId) + '?ChatterFeedItemId=' 
                    + feedItemIds.get(feedCommentId);
                achievement.Points_Awarded__c = thisGoal.Reward_Amount__c;
                achievement.User__c = currentUserId;
                achievementList.add(achievement);
            }
        }
        
        insert achievementList;
    }


    @future
    public static void checkFollows() {

        List<Achievement__c> achievementList = new List<Achievement__c>{};
        Map<Id, Goal__c> goalList = new Map<Id, Goal__c>();
        Map<Id, List<Id>> goalParticipantList = new Map<Id, List<Id>>();
        
        // Get all Goals for following items in Chatter
        for (Goal__c thisGoal : [SELECT Id, Chatter_Type__c, Last_Batch_Check_Time__c, 
            Reward_Amount__c, Name FROM Goal__c
            WHERE Type__c = 'Chatter'
                AND Status__c = 'Active'
                AND (Start_Date__c = NULL OR Start_Date__c <= TODAY)
                AND (End_Date__c = NULL OR End_Date__c >= TODAY)
                AND (Chatter_Type__c = 'FollowUser' OR Chatter_Type__c = 'FollowItem')]) {
            
            goalList.put(thisGoal.Id, thisGoal);
        }
        
        if (goalList.size() == 0) {
            return;
        }

        // Get all participants in goals for following items
        for (Goal_Participant__c gp : [SELECT Goal__c, User__c
              FROM Goal_Participant__c WHERE Goal__c IN :goalList.keySet()]) {
            
            if (goalParticipantList.containsKey(gp.Goal__c)) {
                List<Id> currentList = goalParticipantList.get(gp.Goal__c);
                currentList.add(gp.User__c);
                goalParticipantList.put(gp.Goal__c, currentList);

            } else {
                goalParticipantList.put(gp.Goal__c, new List<Id>{gp.User__c});
            }
        }

        // Go through each goal and get EntitySubscriptions added between the
        // Last Batch Check Time and now
        DateTime now = DateTime.now();
        List<Goal__c> goalsToUpdate = new List<Goal__c>{};

        for (Goal__c thisGoal : goalList.values()) {
            if (goalParticipantList.get(thisGoal.Id) != null) {
                for (EntitySubscription es : 
                    [SELECT ParentId, SubscriberId FROM EntitySubscription 
                        WHERE SubscriberId IN :goalParticipantList.get(thisGoal.Id)
                        AND CreatedDate >= :thisGoal.Last_Batch_Check_Time__c
                        AND CreatedDate <= :now]) {

                     // Check that type of object followed is of the  correct type 
                     // for the rule (i.e. following users vs. records). 005 is the 
                     // prefix for a User object.
                     String parentId = es.ParentId;
                     if (thisGoal.Chatter_Type__c.equals('FollowUser') && 
                         parentId.substring(0, 3).equals('005') ||
                         thisGoal.Chatter_Type__c.equals('FollowItem') &&
                         !(parentId.substring(0, 3).equals('005'))) {
                         
                        // Save Achievement  
                        Achievement__c achievement = new Achievement__c();
                        achievement.Goal__c = thisGoal.Id;
                        achievement.Object_Id__c = es.ParentId;
                        achievement.Points_Awarded__c = thisGoal.Reward_Amount__c;
                        achievement.User__c = es.SubscriberId;
                        achievementList.add(achievement);
                     }    
                }
            }
            // Update last batch check time for all goals            
            thisGoal.Last_Batch_Check_Time__c = now;
            goalsToUpdate.add(thisGoal);

        }            
        insert achievementList;
        update goalsToUpdate;
    }    


    @future
    public static void checkLikes() {

        List<Achievement__c> achievementList = new List<Achievement__c>{};
        Map<Id, Goal__c> goalList = new Map<Id, Goal__c>();
        Map<Id, Set<Id>> goalParticipantList = new Map<Id, Set<Id>>();
        
        // Get all Goals for following items in Chatter
        for (Goal__c thisGoal : [SELECT Id, Chatter_Type__c, Last_Batch_Check_Time__c, 
            Reward_Amount__c, Name FROM Goal__c
            WHERE Type__c = 'Chatter' 
                AND Status__c = 'Active'
                AND (Start_Date__c = NULL OR Start_Date__c <= TODAY)
                AND (End_Date__c = NULL OR End_Date__c >= TODAY)
                AND Chatter_Type__c = 'Likes']) {
            
            goalList.put(thisGoal.Id, thisGoal);
        }
        
        if (goalList.size() == 0) {
            return;
        }

        // Get all participants in goals for following items
        for (Goal_Participant__c gp : [SELECT Goal__c, User__c
              FROM Goal_Participant__c WHERE Goal__c IN :goalList.keySet()]) {
            
            if (goalParticipantList.containsKey(gp.Goal__c)) {
                Set<Id> currentList = goalParticipantList.get(gp.Goal__c);
                currentList.add(gp.User__c);
                goalParticipantList.put(gp.Goal__c, currentList);

            } else {
                goalParticipantList.put(gp.Goal__c, new Set<Id>{gp.User__c});
            }
        }

        // Go through each goal and get EntitySubscriptions added between the
        // Last Batch Check Time and now
        DateTime now = DateTime.now();
        List<Goal__c> goalsToUpdate = new List<Goal__c>{};

        for (Goal__c thisGoal : goalList.values()) {

            // Have to get all FeedLikes because can't do WHERE on InsertedById
            for (NewsFeed nf : [SELECT nf.Id, nf.InsertedById, nf.ParentId,
                    (SELECT fl.Id, fl.InsertedById FROM nf.FeedLikes fl 
                    WHERE CreatedDate >= :thisGoal.Last_Batch_Check_Time__c AND CreatedDate <= :now) 
                FROM NewsFeed nf]) {
    
                // Check if poster is participating in this goal
                if (nf.FeedLikes.size() > 0 && goalParticipantList.get(thisGoal.Id) != null &&
                    (goalParticipantList.get(thisGoal.Id)).contains(nf.InsertedById)) {
                    
                    // Save Achievement  
                    Achievement__c achievement = new Achievement__c();
                    achievement.Goal__c = thisGoal.Id;
                    achievement.Object_Id__c = nf.ParentId + '?ChatterFeedItemId=' + nf.Id;
                    achievement.Points_Awarded__c = thisGoal.Reward_Amount__c * nf.FeedLikes.size();
                    achievement.User__c = nf.InsertedById;
                    achievementList.add(achievement);
                }
            }

            // Update last batch check time for all goals            
            thisGoal.Last_Batch_Check_Time__c = now;
            goalsToUpdate.add(thisGoal);
        }

        insert achievementList;
        update goalsToUpdate;
    }    
}