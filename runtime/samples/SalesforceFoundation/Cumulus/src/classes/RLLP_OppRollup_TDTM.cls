/*  
    Copyright (c) 2014 Salesforce.org
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
* @date 2014 
* @group Rollups
* @group-content ../../ApexDocContent/Rollups.htm
* @description Trigger handler on Opportunity for supporting trigger based rollups.
*/
public class RLLP_OppRollup_TDTM extends TDTM_Runnable {

    /*******************************************************************************************************
    * @description Trigger Handler on Opportunity, runs rollups for current trigger set of opportunities.
    * @param listNew The list of opportunities from trigger new. 
    * @param listOld The list of opportunities from trigger old. 
    * @param triggerAction Which action triggered the current context. (Before or After; Insert, Update, 
    * Delete, or Undelete.)
    * @param objResult The field describe object for the current trigger context. 
    * @return dmlWrapper Objects that need to be inserted or updated after the processing of the trigger.
    * This method returns null as rollups handle updating directly.
    ********************************************************************************************************/
    public override DmlWrapper run(List<SObject> newlist, List<SObject> oldlist, 
    TDTM_Runnable.Action triggerAction, Schema.DescribeSObjectResult objResult) {

        map<Id, Opportunity> mapIdOppNew;
        map<Id, Opportunity> mapIdOppOld;
        if (newlist != null) 
           mapIdOppNew = new map<id, Opportunity>((list<Opportunity>) newlist);
        if (oldlist != null) 
           mapIdOppOld = new map<id, Opportunity>((list<Opportunity>) oldlist);
        RLLP_OppRollup rg = new RLLP_OppRollup(); 	           
        rg.rollupForOppTrigger(mapIdOppNew, mapIdOppOld);
	    
	    // we aren't using dmlWrapper to defer DML, due to the existing complexity of the rollup code.
	    return null;
    }
}