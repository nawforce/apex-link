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

trigger VOL_Campaign_CreateStatuses on Campaign (after insert) {

	// this will hold the id's of all volunteer campaigns.
	set<ID> setCmpId = new set<ID>();
	
    // this list will hold all the new CampaignMembers we will add.
    list<CampaignMemberStatus> listCMSToAdd = new list<CampaignMemberStatus>();
    
    for (Campaign cmp : trigger.new) {
    	
    	// only do work if it's a volunteer campaign
    	if (cmp.RecordTypeId == VOL_SharedCode.recordtypeIdVolunteersCampaign) {
    		
    		// remember the campaign
    		setCmpId.add(cmp.Id);
	    
		    CampaignMemberStatus cms1 = new CampaignMemberStatus(
		        Label = 'Prospect',
		        CampaignId = cmp.Id,
		        HasResponded = false,
		        SortOrder = 100,
		        IsDefault = true
		    );
		    listCMSToAdd.add(cms1);
		
		    CampaignMemberStatus cms2 = new CampaignMemberStatus(
		        Label = 'Confirmed',
		        CampaignId = cmp.Id,
		        HasResponded = true,
		        SortOrder = 200
		    );
		    listCMSToAdd.add(cms2);
		
		    CampaignMemberStatus cms3 = new CampaignMemberStatus(
		        Label = 'Completed',
		        CampaignId = cmp.Id,
		        HasResponded = true,
		        SortOrder = 300
		    );
		    listCMSToAdd.add(cms3);
		    
		    CampaignMemberStatus cms4 = new CampaignMemberStatus(
		        Label = 'No-Show',
		        CampaignId = cmp.Id,
		        HasResponded = false,
		        SortOrder = 400
		    );
		    listCMSTOAdd.add(cms4);

		    CampaignMemberStatus cms5 = new CampaignMemberStatus(
		        Label = 'Canceled',
		        CampaignId = cmp.Id,
		        HasResponded = false,
		        SortOrder = 500
		    );
		    listCMSTOAdd.add(cms5);
    	}		        
    }

    // now get the default CampaignMembers from all Volunteer Campaigns, that we will remove.    
    list<CampaignMemberStatus> listCMSToDel = [Select Id From CampaignMemberStatus WHERE CampaignId in :setCmpId]; 
    
    insert listCMSToAdd;
    delete listCMSToDel;
}