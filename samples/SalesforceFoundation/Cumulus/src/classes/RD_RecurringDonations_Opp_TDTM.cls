/*
    Copyright (c) 2012,2013,2014 Salesforce.org
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
* @author Salesforce.org
* @date 2013
* @group Recurring Donations
* @description Trigger Handler on Opportunity for managing Recurring Donations 
*/
public class RD_RecurringDonations_Opp_TDTM extends TDTM_Runnable {
    
    /*******************************************************************************************************
    * @description Trigger Handler for Recurring Donations called on an Opportunity trigger AfterUpdate.
    * Needs to see if the Opp is changing to closed, and if it has open RD's, update them.
    * @param newlist the list of Opportunities from trigger new. 
    * @param oldlist the list of Opportunities from trigger old. 
    * @param triggerAction which trigger event (BeforeInsert, AfterInsert, etc.). 
    * @param objResult the describe for Opportunity 
    * @return dmlWrapper. 
    ********************************************************************************************************/
    public override DmlWrapper run(List<SObject> newlist, List<SObject> oldlist, 
        TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {
            
        RD_ProcessControl.hasRun = true;
        DmlWrapper dmlWrapper = new DmlWrapper();
        
        set<id> rdIDs = new set<id>();        
        if (triggerAction == TDTM_Runnable.Action.AfterUpdate) {
            integer i = 0;
            for (SObject sobj : newlist) {
                Opportunity o = (Opportunity)sobj;
                //does it have a recurring donation reference?
                //has it changed closed state or amount?
                Opportunity oppOld = (Opportunity)oldlist[i];
                if (o.npe03__Recurring_Donation__c != null && (o.isClosed != oppOld.isClosed || o.Amount != oppOld.Amount)){
                    rdIds.add(o.npe03__Recurring_Donation__c);
                }
                i++;
            }                  
        }

        if (triggerAction == TDTM_Runnable.Action.AfterInsert) {
            integer i = 0;
            for (SObject sobj : newlist) {
                Opportunity o = (Opportunity)sobj;
                //does it have a recurring donation reference?
                //is it in closed state?
                if (o.npe03__Recurring_Donation__c != null && o.isClosed && o.Amount != null && o.Amount > 0){
                    rdIds.add(o.npe03__Recurring_Donation__c);
                }
                i++;
            }                  
        }           
        
        //Get the open label for opps
        string closedLabel = system.label.npe03.RecurringDonationClosedStatus;
        Integer rdcount = 0;
        if (RDids.size() > 0) {
            rdcount = [select count() from npe03__Recurring_Donation__c where npe03__Open_Ended_Status__c <> :closedLabel and id IN :RDids];
        }
                                                                                          
        //recurring donations that need to be updated      
        if (rdcount > 0) {
            if (rdcount == 1 || system.isBatch() || system.isFuture() || RD_ProcessControl.batchButton)
                RD_RecurringDonations.updateRecurringDonationOnOppChange(rdIds, dmlWrapper); 
            else
                RD_RecurringDonations.updateRecurringDonationOnOppChangeFuture(rdIds);                                        
        }
        
        return dmlWrapper;
    }
}