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
public with sharing class VOL_CTRL_PersonalSiteContactLookup_TEST {

    //==================== TEST METHOD(s) ======================================
    
    @isTest(SeeAllData=true)
    public static void TestPersonalSiteContactLookup() {

        //point to our VF page
        PageReference p = new PageReference('Page.PersonalSiteContactLookup');
        Test.setCurrentPageReference(p);
        
        //instantiate the controller for this page
        VOL_CTRL_PersonalSiteContactLookup ctrl = new VOL_CTRL_PersonalSiteContactLookup();
		system.assertNotEquals(null, ctrl.strURLtoCSSFile);
		system.assertNotEquals(null, ctrl.contact);
		system.assertNotEquals(null, ctrl.strLanguage);
		system.assertEquals(null, ctrl.strResult);
		system.assertNotEquals(null, ctrl.emailTemplateId);
		system.assert(ctrl.orgWideEmailId == ctrl.orgWideEmailId);
    	
    	// test empty contact
    	ctrl.contact.Firstname = null;
    	ctrl.contact.Lastname = null;
    	ctrl.contact.Email = null;
    	system.assertEquals(null, ctrl.LookupContact());
    	system.assertEquals(System.Label.labelContactLookupNotFound, ctrl.strResult);
    	
    	// test bogus contact
    	ctrl.contact.Firstname = 'Not There';
    	ctrl.contact.Lastname = 'Not There';
    	ctrl.contact.Email = 'NotThere@NotThere.com';
    	system.assertEquals(null, ctrl.LookupContact());
    	system.assertEquals(System.Label.labelContactLookupNotFound, ctrl.strResult);

		// create a temp contact
		Contact con = new Contact();
		con.FirstName = 'LookupTestFirstName';
		con.LastName = 'LookupTestLastName';
		con.Email = 'LookupTestEmail@email.com';
		insert con;
		
		// test existing contact
   		ctrl.contact.FirstName = 'LookupTestFirstName';
		ctrl.contact.LastName = 'LookupTestLastName';
		ctrl.contact.Email = 'LookupTestEmail@email.com';
    	system.assertEquals(null, ctrl.LookupContact());
    	system.assertEquals(System.Label.labelContactLookupSuccess, ctrl.strResult);
				    
		// test error email handling
		con.Email = null;
		if (VOL_SharedCode.IsNPSPInstalled) {
			con.put('npe01__AlternateEmail__c', null);
			con.put('npe01__HomeEmail__c', null);
			con.put('npe01__WorkEmail__c', null);
		}
		update con;
		ctrl.SendEmailToContact(con);
    	system.assertNotEquals(System.Label.labelContactLookupSuccess, ctrl.strResult);
    }
		
}