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
* @date 2015
* @group Settings
* @description Controller for the Level Assignment Batch settings panel.
*/
public with sharing class STG_PanelLvlAssignBatch_CTRL extends STG_Panel {

    /*********************************************************************************************************
    * @description The panel's constructor 
    */
    public STG_PanelLvlAssignBatch_CTRL() {
        isRunningBatch = false;
    }

    /*********************************************************************************************************
    * @description Returns the string Id of the panel. 
    */
    public override string idPanel() { return 'idPanelLvlAssignBatch'; }

    /*********************************************************************************************************
    * @description Whether we are running the LvlAssignBatch process 
    */
    public boolean isRunningBatch { get; set; }

    /*********************************************************************************************************
    * @description Action Method to run the Level Assignment batches.  Scheduler kicks off one batch per
    * Target object type.
    * @return null 
    */
    public PageReference runBatch() {
        isRunningBatch = true;
        LVL_LevelAssign_SCHED sched = new LVL_LevelAssign_SCHED();
        sched.runBatch();
        cLvlAssignBatch = sched.cLvlAssignBatch;
        return null;
    }

    /*********************************************************************************************************
    * @description How many Level Assign Batches were kicked off
    */
    public integer cLvlAssignBatch { get; set; }
}