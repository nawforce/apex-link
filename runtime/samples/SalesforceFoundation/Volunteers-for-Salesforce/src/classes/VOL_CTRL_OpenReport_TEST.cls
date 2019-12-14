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
public with sharing class VOL_CTRL_OpenReport_TEST {

	//---------------------------------------------------------------------------------------------
	// TEST CODE
	//---------------------------------------------------------------------------------------------
	@isTest(SeeAllData=true) static void TestOpenReport() {
		PageReference pageRef = Page.OpenReport; 
		Test.setCurrentPage(pageRef); 

		// test no parameters
		VOL_CTRL_OpenReport ctrl = new VOL_CTRL_OpenReport();
		system.assertEquals(null, ctrl.OpenReportDevName());	

		// test null parameters
		ApexPages.currentPage().getParameters().put('ReportDevName', null);
		ctrl = new VOL_CTRL_OpenReport();
		system.assertEquals(null, ctrl.OpenReportDevName());	

		// test bogus parameters
		ApexPages.currentPage().getParameters().put('bogus', 'bogus');
		ctrl = new VOL_CTRL_OpenReport();
		system.assertEquals(null, ctrl.OpenReportDevName());	

		// test bad report name parameters
		ApexPages.currentPage().getParameters().put('ReportDevName', 'bogus report');
		ctrl = new VOL_CTRL_OpenReport();
		system.assertEquals(null, ctrl.OpenReportDevName());	

		// test good report name parameters
		ApexPages.currentPage().getParameters().put('ReportDevName', 'Upcoming_Shifts_with_Volunteers');
		ctrl = new VOL_CTRL_OpenReport();
		system.assertNotEquals(null, ctrl.OpenReportDevName());	
	}
	

}