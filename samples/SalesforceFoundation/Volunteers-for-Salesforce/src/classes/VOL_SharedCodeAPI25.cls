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

public with sharing class VOL_SharedCodeAPI25 {

	// global utility to load up an existing object and copy it to the provided object
	// this code moved to VOL_SharedCodeAPI25 to keep it running with api 25
	// behavior, which is that the Sites Guest User Profile can still edit
	// this new contact object we created.  Under api 31, the contact object is readonly.
	// we needed to update the rest of the VOL_SharedCode class to api 31 to handle state & country picklists.	
	public static SObject LoadAndCopyObject(ID id, SObject sobj, list<String> listStrFields) {
		

        Schema.DescribeSObjectResult des = sobj.getSObjectType().getDescribe();

		// if fields not provided, get all Contact fields
		if (listStrFields == null) {
            listStrFields = new list<string>();
			// get the fields for the object
			Map<String, Schema.SObjectField> mapS = des.fields.getMap().clone();
			// avoid any of the API version 30 compound fields
			// we only worry about Contact ones, since all callers are giving us contacts to copy.
			mapS.remove('mailingaddress');
			mapS.remove('otheraddress');
    		listStrFields.addAll(mapS.keySet());
		}
		
        string strSoql = 'select ';
        string strComma = '';
        for (string strF : listStrFields) {
        	strSoql += strComma + strF;
        	strComma = ', ';
        }
        strSoql += ' from ' + des.getName() + ' where Id = :id ';
        strSoql += ' limit 1';      
        list<SObject> listSObj = Database.Query(strSoql); 
        
        if (listSObj.size() > 0) {        	
        	SObject sobjT = listSObj[0];
            //  now copy over all the non-null fields from the form's contact to the existing contact.
            for (string strF : listStrFields) {
            	if (sobjT.get(strF) != null) {
            		try {
            			sobj.put(strF, sobjT.get(strF));
            		} catch(exception ex) { 
            			
            		}
            	}
            }
            return sobjT;
        }
        return null;
	}

}