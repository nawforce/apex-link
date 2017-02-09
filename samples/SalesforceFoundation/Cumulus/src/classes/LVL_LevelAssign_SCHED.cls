/*
    Copyright (c) 2016, Salesforce.org
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
* @date 2016
* @group Levels
* @description Schedulable class that creates the batch processes to compute Levels for each Target object
*/
public without sharing class LVL_LevelAssign_SCHED implements Schedulable {
    
    /*******************************************************************************************************
    * @description Schedulable execute method executes this batch job.
    */
    public void execute(SchedulableContext context) {
        runBatch();
    }
    
    /*******************************************************************************************************
    * @description Starts one batch job per level object.
    */
    public void runBatch() {
        try {
            cLvlAssignBatch = 0;

            // for a given Object (Contact, Account, etc), provides the set of fields from that object that need to be queried.
            map<String, set<String>> mapStrObjectToQueryFields = LVL_LevelAssign_BATCH.getMapStrObjectToQueryFields();
            
            // create batch for each Object
            for (String strObj : mapStrObjectToQueryFields.keySet()) {
                
                // get the fields needed for that object
                set<String> setFlds = mapStrObjectToQueryFields.get(strObj);
                
                // construct our query
                String strSoql = 'select Id';
                for (String strFld : setFlds) {
                    strSoql += ', ' + strFld;
                }
                strSoql += ' from ' + strObj;
                
                // kick off our batch
                LVL_LevelAssign_BATCH batch = new LVL_LevelAssign_BATCH(strSoql, strObj);
                Database.executeBatch(batch);
                cLvlAssignBatch++;
            }
        } catch(Exception e) {
            ERR_Handler.processError(e, ERR_Handler_API.Context.LVL);
            cLvlAssignBatch = 0;
        }
    }

    /*********************************************************************************************************
    * @description How many Level Assign Batches were kicked off
    */
    public integer cLvlAssignBatch { get; set; }
}