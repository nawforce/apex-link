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
/*******************************************************************************************************
* @author David Habib
* @date 11/1/2012
* @description Batchable and Schedulable class to find the list of active Job Recurrence Schedules,
* and process them to add shifts into the future. 
********************************************************************************************************/

global with sharing class VOL_BATCH_Recurrence implements Database.Batchable<Sobject>, Schedulable {

	private string strSoql { get; set; }

    /*******************************************************************************************************
	* @description This constructor is needed to allow this class to be schedulable.
	*/
    global VOL_BATCH_Recurrence() { }
    
    /*******************************************************************************************************
	* @description Standard schedulable execute method.  This simply gets the batch started when called from 
	* a scheduled job.  
	*/    
    global void execute(SchedulableContext sc) {
    	// choose a very small scope (1 JRS at a time) to avoid statement limits we can hit.
		database.executebatch(this, 1);                  
    }
    
    /*******************************************************************************************************
	* @description Standard batch start method.    
	*/    
    global Database.QueryLocator start(Database.BatchableContext bc) { 
    	Date dt = system.today();
    	strSoql = 'select Id, Name, Days_of_Week__c, Desired_Number_of_Volunteers__c, Duration__c, Description__c, ' +
    		' Schedule_End_Date__c, Schedule_Start_Date_Time__c, Volunteer_Job__c, Weekly_Occurrence__c ' +
    		' from Job_Recurrence_Schedule__c where ' +
    		' Schedule_End_Date__c = null or Schedule_End_Date__c > :dt';
        return Database.getQueryLocator(strSoql);
    }

    /*******************************************************************************************************
	* @description The main batch execute method.  
	*/
    global void execute(Database.BatchableContext BC, List<Sobject> listSObj) {
    	VOL_JRS.ProcessListJRS(listSObj, false);	
    }

    /*******************************************************************************************************
	* @description The standard batch finish method.  
	*/      
    global void finish(Database.BatchableContext bc) {

    }
}