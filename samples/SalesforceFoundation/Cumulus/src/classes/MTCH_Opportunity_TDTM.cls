/*
    Copyright (c) 2015 Salesforce.org
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
* @date 2015
* @group Opportunity
* @description TDTM Trigger Handler on Opportunity for supporting Matched Gifts
*/
public class MTCH_Opportunity_TDTM extends TDTM_Runnable {

    /*******************************************************************************************************
    * @description Trigger Handler on Opportunity after update, that updates any Matched Gift opps that
    * refer to this Opp.
    * @param listNew the list of opps from trigger new. 
    * @param listOld the list of opps from trigger old. 
    * @param triggerAction Which action triggered the current context. (Before or After; Insert, Update, 
    * Delete, or Undelete.) After Update is expected in this case.
    * @param objResult The field describe object for the current trigger context. 
    * @return dmlWrapper Objects that need to be inserted or updated after the processing of the trigger.
    ********************************************************************************************************/
    public override DmlWrapper run(List<SObject> newlist, List<SObject> oldlist, 
        TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {        

        if (triggerAction != TDTM_Runnable.Action.AfterUpdate)
            return null;
            
        DmlWrapper dmlWrapper = new DmlWrapper();            

        list<Id> listOppIdClosed = new list<Id>();            
        for (integer i = 0; i < newlist.size(); i++) {
            Opportunity opp = (Opportunity)newlist[i];
            Opportunity oppOld = (Opportunity)oldlist[i];
            
            if (opp.isWon && !oppOld.isWon)
                listOppIdClosed.add(opp.Id);            
        }

        if (!listOppIdClosed.isEmpty()) {
            for (Opportunity opp : [select Id, Matching_Gift_Status__c from Opportunity
                where Matching_Gift_Status__C != 'Received' and Matching_Gift__c in :listOppIdClosed]) {
                opp.Matching_Gift_Status__c = 'Received';
                dmlWrapper.objectsToUpdate.add(opp);
            }
        }
        
        return dmlWrapper;
    }
}