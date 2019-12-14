/*
    Copyright (c) 2011, Salesforce.org
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
* @date 2011 (2.3)
* @group Households
* @description test class for the campaign dedupe button from the campaign record 
*/
@isTest
private class HH_CampaignDedupeBTN_TEST {  
  
 
    /*******************************************************************************************************
    * @description test the Campaign Dedupe controller 
    * @return void
    ********************************************************************************************************/
    private static testMethod void testCampaignHHReport(){
      npo02__Households_Settings__c householdSettingsForTests = UTIL_CustomSettingsFacade.getHouseholdsSettingsForTests(
                new npo02__Households_Settings__c (
                    npo02__Household_Rules__c = HH_Households.ALL_PROCESSOR, 
                    npo02__Always_Rollup_to_Primary_Contact__c = false,
                    npo02__Enable_Opp_Rollup_Triggers__c = true,
                    npo02__Excluded_Account_Opp_Rectypes__c = null,
                    npo02__Excluded_Account_Opp_Types__c = null,
                    npo02__Excluded_Contact_Opp_Rectypes__c = null,
                    npo02__Excluded_Contact_Opp_Types__c = null,
                    npo02__Membership_Record_Types__c = null,                     
                    npo02__Use_Fiscal_Year_for_Rollups__c = true, 
                    npo02__Rollup_N_Day_Value__c = 10,
                    npo02__Household_Mailing_List_ID__c = '0000000'                    
                ));
            
    // create test data
    Account acct = new Account (
      Name='Test Account'
    );
    insert acct;
    
    Contact con = new Contact (
      FirstName='Kevin',
      LastName='Test',
      AccountId = acct.id,    
      npo02__TotalOppAmount__c = 100
    );
    insert con;
    id hhid = [select npo02__Household__r.id from Contact where id = :con.id].npo02__household__r.id;
    system.assert(hhid != null);
    UTIL_Debug.debug('HHID IS===================' + hhid);
    Contact con2 = new Contact (
      FirstName = 'Amy',
      LastName = 'Test',
      AccountId = acct.id,            
      npo02__household__c = hhid
    );
    UTIL_Debug.debug('HHID IS NOW===================' + hhid);
    
    system.assert(con2.npo02__household__c != null);
    insert con2;
    
    Campaign camp = new Campaign (
      Name = 'MyCampaign',
      Status = 'Planned',
      isActive = true
    );
    insert camp;
    
    CampaignMember campm1 = new CampaignMember(
      CampaignID = camp.id,
      ContactID = con.id,
      Status = 'Sent'      
    );
    insert campm1;
    
    CampaignMember campm2 = new CampaignMember(
      CampaignID = camp.id,
      ContactID = con2.id,
      Status = 'Sent'       
    );
    insert campm2;
        
    //now run our report
    Test.startTest();
    ApexPages.StandardController sc = new ApexPages.StandardController(camp);
    HH_CampaignDedupeBTN_CTRL deduper = new HH_CampaignDedupeBTN_CTRL(sc);
    PageReference pr = deduper.runReport();
    Test.stopTest();
    
    String campm1Status = [select Status from CampaignMember where id = :campm1.id].Status;
    String campm2Status = [select Status from CampaignMember where id = :campm2.id].Status;
    
    system.assertEquals('Sent', campm1Status);
    //We cannot test for "Sent -  Household duplicate", which should be the actual status that
    //this campaign member gets, because that status is not visible without seealldata=true
    system.assertEquals('Responded', campm2Status);  
  
  }
}