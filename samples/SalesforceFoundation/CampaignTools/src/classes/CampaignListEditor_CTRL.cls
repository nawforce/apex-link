/*
    Copyright (c) 2016 Salesforce.org
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the Salesforce.org nor the names of
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
*
* @group CampaignTools
*
* @description Apex extension for the Visualforce CampaignList page.
*
*/
public with sharing class CampaignListEditor_CTRL {
    
    private static String plainNamespace;
    private static Boolean hasPerms;
    
    public CampaignListEditor_CTRL(ApexPages.StandardController controller) {}

    /*******************************************************************************************************
    * @description Finds the namespace for the current context.
    * @return string The current namespace as a string, or a blank string if we're not in a namespaced context.
    ********************************************************************************************************/
    public static String getNamespace() {       
        if (null == plainNamespace) {
            String currentClassName = CampaignListEditor_CTRL.class.getName();
    		plainNamespace = currentClassName.contains('.') ? currentClassName.substringBefore('.') : '';
        }

        return plainNamespace;
    }

    /*******************************************************************************************************
    * @description Checks all required permissions for the page to function correctly, user must have
    * proper access to the Campaign and Segment Objects and Fields
    * @return Boolean true when the user has all permissions
    ********************************************************************************************************/
    public static Boolean getHasPerms() {
    	if (null == hasPerms) {
    		try {
	    		CampaignTools_UTIL.checkCampaignListPermission();
	    		hasPerms = true;
	    	} catch (CampaignTools_UTIL.PermsException ex) {
	    		hasPerms = false;
	    	}
    	}
    	
    	return hasPerms;
    }
}