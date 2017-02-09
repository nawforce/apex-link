/*
    Copyright (c) 2014, Salesforce.org
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
* @date 2014 (3.0)
* @group Utilities
* @description Tests for the JobProgress indicator component controller.
*/
@isTest
public with sharing class UTIL_JobProgress_CTRL_TEST {

    static testMethod void jobProgressController(){

        //queue up a batch job to render against
        //borrow method from existing account conversion util
        //this will need to be changed if we ever separate the component from the
        //application
        CONV_Account_Conversion_BATCH_TEST.setPreHHAccountModel(true);
        //set the HH Account model before entering the batch
        CONV_Account_Conversion_BATCH_TEST.setHHAccountModel();
        String query = CONV_Account_Conversion_CTRL.getBatchQuery();
        Test.startTest();
        CONV_Account_Conversion_BATCH cacv = new CONV_Account_Conversion_BATCH(query, null, null);
        ID batchprocessid = Database.executeBatch(cacv);
        Test.stopTest();

        UTIL_JobProgress_CTRL controller = new UTIL_JobProgress_CTRL();
        system.assertEquals(1, controller.getBatchJobs().size());
        system.assertNotEquals(null, controller.bj);
        system.assertEquals(20, controller.numberOfJobs);
        system.assertEquals('Batch Apex Jobs', controller.batchComponentLabel);
        system.assertEquals(false, controller.rerenderoncomplete);
        system.assertEquals(false, controller.hasExtendedStatus);
        system.assertEquals(false, controller.allCompleted);
    }

    //test behavior when no batch job is present for whatever reason
    static testMethod void checkNullBatchJobProgressBehavior(){
        UTIL_JobProgress_CTRL controller = new UTIL_JobProgress_CTRL();
        system.assertEquals(true, controller.getBatchJobs().isEmpty());
        system.assertEquals(null, controller.bj);
    }
}