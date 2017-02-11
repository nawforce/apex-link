/*
    Copyright (c) 2012, Salesforce.com Foundation
    All rights reserved.
    
    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are met:
    
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the Salesforce.com Foundation nor the names of
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
* @author Salesforce.com Foundation
* @date 2012
* @description Install script for the Relationships package
*/
global class Relationships_INST implements InstallHandler{

    //name of the static resource csv that contains the default reciprocal settings records
    global static final String RECIPROCAL_DEFAULT_RESOURCE_NAME = 'DefaultReciprocalRelationships';
    
    global void onInstall(InstallContext context){
        
        //only run if this is a user install & previous version is less than 2.0 
        if((context.previousVersion() == null || context.previousVersion().compareTo(new Version(2,0)) < 0) && !context.isPush()){
            
            String genderField = '';
            //load default reciprocals
            List<Relationship_Lookup__c> rlList = new List<Relationship_Lookup__c>();
            StaticResource sr = [select body from StaticResource where Name = :RECIPROCAL_DEFAULT_RESOURCE_NAME];
            List<List<String>> recipRelList = parseCSV(sr.Body.toString(), false);
            for (List<String> ls : recipRelList){
                Relationship_Lookup__c rl = new Relationship_Lookup__c();                
                rl.Name = ls[0];
                rl.Male__c = ls[1];
                rl.Female__c = ls[2];
                rl.Neutral__c = ls[3];
                rl.Active__c = true;                
                rlList.add(rl);             
            }
            
            if (!rlList.isEmpty()){
                try{
                   insert rlList;   
                }
                catch (Exception e){
                    Relationship_Error__c re = createRelationshipError(e);
                    insert re;                  
                }
            }
            //autodetect a gender field
            Map<String, Schema.SObjectField> contactFieldMap = Schema.SObjectType.Contact.fields.getMap();
            
            if((
                contactFieldMap.containsKey('Gender__c') &&
                contactFieldMap.get('Gender__c').getDescribe().getSoapType() == Schema.Soaptype.String &&
                contactFieldMap.get('Gender__c').getDescribe().isUpdateable()
                )||(
                contactFieldMap.containsKey('gender__c') &&
                contactFieldMap.get('gender__c').getDescribe().getSoapType() == Schema.Soaptype.String &&
                contactFieldMap.get('gender__c').getDescribe().isUpdateable()                                            
            )){                
                genderField = 'Gender__c';
            }        
               
            //load some default custom settings
            Relationship_Settings__c RelationshipSettings = Relationship_Settings__c.getOrgDefaults();
            
            Boolean doUpsert = false;
            //if we don't have org defaults...
            if (RelationshipSettings.Id == null){
                RelationshipSettings = new Relationship_Settings__c(Reciprocal_Method__c = 'List Setting');
                doUpsert = true;                                    
            }            
            
            if(genderField.length() > 0 && (RelationshipSettings.Gender_Field__c == null || RelationshipSettings.Gender_Field__c == '')){
                RelationshipSettings.Gender_Field__c = genderField;
                doUpsert = true;
            }
            
            if (doUpsert) upsert RelationshipSettings;
        }
    }
    
    // grabbed from Relationships_Utils, so we could remove that class for Cumulus.
    //modified csv parser to load initial static resource for recip settings
    //http://wiki.developerforce.com/page/Code_Samples#Parse_a_CSV_with_APEX
    public static List<List<String>> parseCSV(String contents,Boolean skipHeaders) {
        List<List<String>> allFields = new List<List<String>>();
    
        // replace instances where a double quote begins a field containing a comma
        // in this case you get a double quote followed by a doubled double quote
        // do this for beginning and end of a field
        contents = contents.replaceAll(',"""',',"DBLQT').replaceall('""",','DBLQT",');
        // now replace all remaining double quotes - we do this so that we can reconstruct
        // fields with commas inside assuming they begin and end with a double quote
        contents = contents.replaceAll('""','DBLQT');
        // we are not attempting to handle fields with a newline inside of them
        // so, split on newline to get the spreadsheet rows
        List<String> lines = new List<String>();
        try {
            lines = contents.split('\n');
        } catch (System.ListException e) {
            System.debug('Limits exceeded?' + e.getMessage());
        }
        Integer num = 0;
        for(String line : lines) {
            // check for blank CSV lines (only commas)
            if (line.replaceAll(',','').trim().length() == 0) break;
            
            List<String> fields = line.split(',');  
            List<String> cleanFields = new List<String>();
            String compositeField;
            Boolean makeCompositeField = false;
            for(String field : fields) {
                cleanFields.add(field.replaceAll('DBLQT','"'));             
            }
            
            allFields.add(cleanFields);
        }
        if (skipHeaders) allFields.remove(0);
        return allFields;       
    }  
    
    
    // grabbed from Relationships_Utils, so we could remove that class for Cumulus.
    public static Relationship_Error__c createRelationshipError(Exception e){
        Relationship_Error__c re = new Relationship_Error__c();
        re.Error_Date_Time__c = system.now();        
        re.Error_Type__c = e.getTypeName();
        
        //trim error message
        if (e.getMessage().length() > 255)
            re.Full_Error_Message__c = e.getMessage().substring(0, 254);
        else
            re.Full_Error_Message__c = e.getMessage();
        
        re.Error_Stack_Trace__c = e.getStackTraceString();
        
        return re;
    }
    
      
}