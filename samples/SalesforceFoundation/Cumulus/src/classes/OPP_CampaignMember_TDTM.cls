/*
    Copyright (c) 2015, Salesforce.org
    All rights reserved.
    
    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are met:
    
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of Salesforce.org nor the names of
      its contributors may be used to endorse or promote products derived
      from this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT 
    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS 
    FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE 
    COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
    BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
    LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER 
    CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT 
    LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN 
    ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
    POSSIBILITY OF SUCH DAMAGE.
*/
/**
* @author Salesforce.com Foundation
* @date 2015
* @group Opportunity
* @group-content ../../ApexDocContent/Opportunity.htm
* @description Handles adding and updating campaign members based on campaign donations.
*/
public class OPP_CampaignMember_TDTM extends TDTM_Runnable {
    
    /**@description lists to pass to CON_AddToCampaign*/
    private List<Id> listContactId = new List<Id>();
    private List<Id> listCampaignId = new List<Id>();
    private List<String> listMemberStatus = new List<String>();

    /**@description Static flag to prevent recursion. */
    public static Boolean hasRun = false;

    /*******************************************************************************************************
    * @description Creates and updates campaign members based on opportunity primary contact, primary 
    * campaign source, and the primary contact status field.
    * @param listNew the list of Opportunities from trigger new. 
    * @param listOld the list of Opportunities from trigger old. 
    * @param triggerAction which trigger event (BeforeInsert, AfterInsert, etc.). 
    * @param objResult the describe for Opportunities.
    * @return dmlWrapper Campaign Members to insert or update.
    ********************************************************************************************************/
    public override DmlWrapper run(List<SObject> newlist, List<SObject> oldlist, 
        TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {
        
        dmlWrapper dmlWrapper = new DmlWrapper();

        if (hasRun) {
            return dmlWrapper;
        }

        if (!UTIL_CustomSettingsFacade.getContactsSettings().Automatic_Campaign_Member_Management__c) {
            return dmlWrapper;
        }

        //Due to how OpportunityContactRoles are created by the New Opportunity button, the Primary_Contact__c
        //field can be blank in an after insert context, so we have to use the OCR directly
        if (triggerAction == TDTM_Runnable.Action.AfterInsert) {
            list<Opportunity> oppsWithConRoles = getOppsWithConroles((list<Opportunity>) newlist);
            for (Opportunity opp : oppsWithConRoles) {
                //if status is "omit", do nothing for this opportunity
                if (isOmit(opp)) {
                    continue;
                }

                addOppMemberFields(opp);
            }
        }

        if (triggerAction == TDTM_Runnable.Action.AfterUpdate) {
            for (integer i=0; i<newlist.size(); i++) {

                Opportunity opp = (Opportunity)newlist[i];
                Opportunity oldOpp = (Opportunity)oldlist[i];
                
                //if status is "omit", do nothing for this opportunity
                if (isOmit(opp)) {
                    continue;
                }

                //the status is blank and we're changing closed/won status
                if (String.isBlank(opp.Primary_Contact_Campaign_Member_Status__c) && 
                    (opp.IsClosed != oldOpp.isClosed || opp.IsWon != oldOpp.isWon)) {
                    
                    addOppMemberFields(opp);
                //the primary contact, campaign, or status changed
                } else if (opp.Primary_Contact__c != oldOpp.Primary_Contact__c || 
                    opp.CampaignId != oldOpp.CampaignId || 
                    opp.Primary_Contact_Campaign_Member_Status__c != oldOpp.Primary_Contact_Campaign_Member_Status__c) {
                    
                    addOppMemberFields(opp);
                }
            }
        }
        
        if (!listContactId.isEmpty()) {
            dmlWrapper = CON_AddToCampaign.CreateCampaignMembers(listContactId, listCampaignId, listMemberStatus);
        }

        hasRun = true;
        return dmlWrapper;
    }

    /*******************************************************************************************************
    * @description For a given list of opportunities, clones them and adds the primary opportunity contact
    * role's contact id to the Primary_Contact__c field.
    * @param listOpps A list of Opportunities.
    * @return list<Opportunity> A cloned list of opportunities with the primary OCR's contact copied to the 
    * Primary_Contact__c field.
    ********************************************************************************************************/
    private list<Opportunity> getOppsWithConroles(list<Opportunity> listOpps) {
        Map<Id,Opportunity> mapResults = new Map<Id,Opportunity>();
        //make a map of cloned opportunities so we can update the Primary_Contact__c field, which can't be written in an after insert context
        for (Opportunity opp : listOpps) {
            mapResults.put(opp.id, opp.clone(true,true,false,true));
        }

        for (OpportunityContactRole oppConRole : [SELECT OpportunityId, ContactId FROM OpportunityContactRole WHERE IsPrimary=TRUE AND OpportunityId IN :listOpps]) {
            mapResults.get(oppConRole.OpportunityId).Primary_Contact__c = oppConRole.ContactId;
        }
        return mapResults.values();
    }

    /*******************************************************************************************************
    * @description Checks if an opportunity's Primary_Contact_Campaign_Member_Status__c is the "omit" value.
    * @param Opp The current opportunity.
    * @return Boolean True if Primary_Contact_Campaign_Member_Status__c == Label.CampaignMemberStatusOmit
    ********************************************************************************************************/
    private Boolean isOmit(Opportunity opp) {
        if (!String.isBlank(opp.Primary_Contact_Campaign_Member_Status__c) && opp.Primary_Contact_Campaign_Member_Status__c.equalsIgnoreCase(Label.CampaignMemberStatusOmit)) {
            return true;
        }
        return false;
    }

    /*******************************************************************************************************
    * @description Adds a Contact Id, Campaign Id, and Status to our three lists. If the opportunity has a
    * status it is used, otherwise a default is used based on if the opportunity is closed/won or not.
    * @param Opp The current opportunity.
    * @return void This method adds to three lists and returns nothing.
    ********************************************************************************************************/
    private void addOppMemberFields(Opportunity opp) {
        if (opp.Primary_Contact__c == null || opp.CampaignId == null) {
            return;
        }

        //if no status is provided, default to a responded status if the opp is closed/won
        //otherwise default to a non-responded status
        if (String.isBlank(opp.Primary_Contact_Campaign_Member_Status__c)) {
            if (opp.isClosed && opp.isWon) {
                listMemberStatus.add(Label.campaignMemberStatusResponded);
            } else {
                listMemberStatus.add(Label.campaignMemberStatusNonResponded);
            }
        } else listMemberStatus.add(opp.Primary_Contact_Campaign_Member_Status__c);

        listCampaignId.add(opp.CampaignId);
        listContactId.add(opp.Primary_Contact__c);
    }
}