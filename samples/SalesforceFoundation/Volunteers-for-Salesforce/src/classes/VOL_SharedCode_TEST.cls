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

@isTest
public with sharing class VOL_SharedCode_TEST {


    //==================== TEST METHOD(s) ======================================
    static testmethod void CodeCoverageTests() {
        // since this class is all shared code, it gets heavily exercised by all other test code.       
        // we just need to add a test for hitting the scenario where there aren't any settings specified in the instance.
        
        //clear out whatever settings exist
        delete [select id from Volunteers_Settings__c];
        System.Assert(VOL_SharedCode.VolunteersSettings != null);
        System.Assert(VOL_SharedCode.VolunteersSettings.Signup_Bucket_Account_On_Create__c == null);
        
        Contact c = new Contact(lastname='foo');
        insert c;
        Contact c2 = new Contact();
        VOL_SharedCode.LoadAndCopyObject(c.Id, c2);
        System.assertEquals('foo', c2.LastName);
            
        Contact c3 = new Contact();
        VOL_SharedCodeAPI25.LoadAndCopyObject(c.Id, c3, null);
        System.assertEquals('foo', c3.LastName);
    }
    
    /*******************************************************************************************************
    * @description test methods to test all permutations of the Contact Matching Rule.
    * @return void
    */ 
    static testmethod void testContactMatchRule1() {
    	testContactMatchRule('Firstname;Lastname;Email');
    }
    static testmethod void testContactMatchRule2() {
    	testContactMatchRule('Firstname;Lastname');
    }
    static testmethod void testContactMatchRule3() {
    	testContactMatchRule('Firstname;Email');
    }
    static testmethod void testContactMatchRule4() {
    	testContactMatchRule('Lastname;Email');
    }
    static testmethod void testContactMatchRule5() {
    	testContactMatchRule('');
    }
    static testmethod void testContactMatchRule6() {
    	testContactMatchRule('Firstname;');
    }
    static testmethod void testContactMatchRule7() {
    	testContactMatchRule('Lastname');
    }
    static testmethod void testContactMatchRule8() {
    	testContactMatchRule('Email');
    }
    static void testContactMatchRule(string strRule) {

        Volunteers_Settings__c settings = new Volunteers_Settings__c();
        settings.Signup_Creates_Contacts_If_No_Match__c = true;
        settings.Signup_Matches_Existing_Contacts__c = true;
        settings.Contact_Matching_Rule__c = strRule;
        VOL_SharedCode.getVolunteersSettingsForTests(settings);

		// test data
		list<Contact> listCon = new list<Contact>();
		listCon.add(new Contact(Firstname='Fred', Lastname='Smith', Email='fred@smith.com'));
		listCon.add(new Contact(Firstname='Fred', Lastname='Smith', Email=null));
		listCon.add(new Contact(Firstname='Fred', Lastname='Smith', Email='NOTfred@smith.com'));
		listCon.add(new Contact(Firstname='NOTFred', Lastname='Smith', Email='fred@smith.com'));
		listCon.add(new Contact(Firstname='Fred', Lastname='NOTSmith', Email='fred@smith.com'));
		insert listCon;
		
        list<string> listStrFields = new list<string>{'Id', 'Firstname', 'Lastname', 'Email'};        
		list<Contact> listCon2 = VOL_SharedCode.LookupContact(listCon[0], listStrFields);
		
		if (strRule == 'Firstname;Lastname;Email') {
			system.assertEquals(1, listCon2.size());
		}
		if (strRule == 'Firstname;Email') {
			system.assertEquals(2, listCon2.size());
		}
		if (strRule == 'Lastname;Email') {
			system.assertEquals(2, listCon2.size());
		}
		if (strRule == 'Firstname;Lastname') {
			system.assertEquals(3, listCon2.size());
		}
		if (strRule == 'Email') {
			system.assertEquals(3, listCon2.size());
		}
		if (strRule == 'Firstname') {
			system.assertEquals(4, listCon2.size());
		}
		if (strRule == 'Lastname') {
			system.assertEquals(4, listCon2.size());
		}
		if (strRule == '') {  // we treat blank as 'Firstname;Lastname;Email'
			system.assertEquals(1, listCon2.size());
		}
    }
    
    
	/*******************************************************************************************************
	* @description creates a hierarchy of volunteer campaigns, each with a job and a shift.
	* @return a map of campaigns created, with name as their key.
	*
    * └── grandparent
    *     ├── parent1
    *     │   ├── child1
    *     │   ├── child2
    *     │   └── child3
    *     └── parent2
    *         ├── child4
    *         └── child5
    *             ├── grandchild1
    *             ├── grandchild2
    *             └── grandchild3
	* 
    */
    public static map<string, Campaign> mapCampaignTestHierarchy() {
        // build campaign hierarchy as shown above
        Campaign grandParent = new Campaign(recordtypeid=VOL_SharedCode.recordtypeIdVolunteersCampaign, name='Grandparent', IsActive=true);
        insert grandParent;
        Campaign parent1 = new Campaign(recordtypeid=VOL_SharedCode.recordtypeIdVolunteersCampaign, ParentId = grandParent.Id, name='Parent 1 of 2', IsActive=true);
        Campaign parent2 = new Campaign(recordtypeid=VOL_SharedCode.recordtypeIdVolunteersCampaign, ParentId = grandParent.Id, name='Parent 2 of 2', IsActive=true);
        insert new List<Campaign> {parent1, parent2};
        Campaign child1 = new Campaign(recordtypeid=VOL_SharedCode.recordtypeIdVolunteersCampaign, ParentId = parent1.Id, name='Child 1 of 5', IsActive=true);
        Campaign child2 = new Campaign(recordtypeid=VOL_SharedCode.recordtypeIdVolunteersCampaign, ParentId = parent1.Id, name='Child 2 of 5', IsActive=true);
        Campaign child3 =  new Campaign(recordtypeid=VOL_SharedCode.recordtypeIdVolunteersCampaign, ParentId = parent1.Id, name='Child 3 of 5', IsActive=true);
        Campaign child4 =  new Campaign(recordtypeid=VOL_SharedCode.recordtypeIdVolunteersCampaign, ParentId = parent2.Id, name='Child 4 of 5', IsActive=true);
        Campaign child5 =  new Campaign(recordtypeid=VOL_SharedCode.recordtypeIdVolunteersCampaign, ParentId = parent2.Id, name='Child 5 of 5', IsActive=true);
        insert new List<Campaign> {child1,child2,child3,child4,child5};
        Campaign grandchild1 =  new Campaign(recordtypeid=VOL_SharedCode.recordtypeIdVolunteersCampaign, ParentId = child5.Id, name='Grandchild 1 of 3', IsActive=true);
        Campaign grandchild2 =  new Campaign(recordtypeid=VOL_SharedCode.recordtypeIdVolunteersCampaign, ParentId = child5.Id, name='Grandchild 2 of 3', IsActive=true);
        Campaign grandchild3 =  new Campaign(recordtypeid=VOL_SharedCode.recordtypeIdVolunteersCampaign, ParentId = child5.Id, name='Grandchild 3 of 3', IsActive=true);
        insert new List<Campaign> {grandchild1,grandchild2,grandchild3};
    
    	list<Campaign> listCampaign = new list<Campaign> {grandParent, parent1, parent2, child1, child2, child3, child4, child5, grandchild1, grandchild2, grandchild3};
    	list<Volunteer_Job__c> listJob = new list<Volunteer_Job__c>();
    	map<string, Campaign> mapCampaign = new map<string, Campaign>();
    	for (Campaign cmp : listCampaign) {
    		listJob.add(new Volunteer_Job__c(name=cmp.Name, Campaign__c=cmp.Id, Display_on_Website__c=true));
    		mapCampaign.put(cmp.Name, cmp);
    	}
    	insert listJob;
    	
    	
    	list<Volunteer_Shift__c> listShift = new list<Volunteer_Shift__c>();
    	for (Volunteer_Job__c job : listJob) {
    		listShift.add(new Volunteer_Shift__c(Volunteer_Job__c=job.Id, Duration__c=1, Start_Date_Time__c=System.Now().addDays(1)));
    	}
    	insert listShift;
    	
    	return mapCampaign;
    }


    static testmethod void testCampaignHierarchy() {
        // build campaign hierarchy as shown above
		map<string, Campaign> mapCmp = mapCampaignTestHierarchy();

        // get a few trees
        List<Id> parent2Tree = VOL_SharedCode.listIdsCampaignsInHierarchy(mapCmp.get('Parent 2 of 2').Id);
        List<Id> parent1Tree = VOL_SharedCode.listIdsCampaignsInHierarchy(mapCmp.get('Parent 1 of 2').Id);
        List<Id> grandparentTree = VOL_SharedCode.listIdsCampaignsInHierarchy(mapCmp.get('Grandparent').Id);
        List<Id> child5Tree = VOL_SharedCode.listIdsCampaignsInHierarchy(mapCmp.get('Child 5 of 5').Id);

        // check tree sizes
        System.assertEquals(4,child5Tree.size());
        System.assertEquals(6, parent2Tree.size());
        System.assertEquals(4, parent1Tree.size());
        System.assertEquals(11, grandparentTree.size());

        // check that tree is legit
        Set<Id> child5Set = new Set<Id>(child5Tree);
        System.assert(child5Set.contains(mapCmp.get('Grandchild 3 of 3').Id));
        System.assert(!child5Set.contains(mapCmp.get('Parent 2 of 2').Id));

        // check that it won't screw up w/ no children
        System.assertEquals(1, VOL_SharedCode.listIdsCampaignsInHierarchy(mapCmp.get('Grandchild 3 of 3').Id).size());

    }

	// tests for the isValidContactIdAndEmail(ID contactId, string strEmail) method
	static testmethod void testIsValidContactIdAndEmail() {
		// create an account and contact
		Account acc = new Account(Name='my test account');
		insert acc;
		Contact con = new Contact(Lastname='test contact', AccountId=acc.Id, Email='foo@bar.com');
		insert con;
		Contact con2 = new Contact(Lastname='another test contact', AccountId=acc.Id, Email='bar@baz.com');
		insert con2;
		
		// tests with email matching not required
		VOL_SharedCode.VolunteersSettings.Personal_Site_Requires_URL_Email_Match__c = false;
		system.assertEquals(false, VOL_SharedCode.isValidContactIdAndEmail(null, null));
		system.assertEquals(true, VOL_SharedCode.isValidContactIdAndEmail(con.Id, null));
		system.assertEquals(true, VOL_SharedCode.isValidContactIdAndEmail(con.Id, 'bar@baz.com'));
		system.assertEquals(true, VOL_SharedCode.isValidContactIdAndEmail(con.Id, 'foo@bar.com'));

		// tests with email matching required
		VOL_SharedCode.VolunteersSettings.Personal_Site_Requires_URL_Email_Match__c = true;
		system.assertEquals(false, VOL_SharedCode.isValidContactIdAndEmail(null, null));
		system.assertEquals(false, VOL_SharedCode.isValidContactIdAndEmail(con.Id, null));
		system.assertEquals(false, VOL_SharedCode.isValidContactIdAndEmail(con.Id, 'bar@baz.com'));
		system.assertEquals(true, VOL_SharedCode.isValidContactIdAndEmail(con.Id, 'foo@bar.com'));
	}
}