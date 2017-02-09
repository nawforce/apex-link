/*
    Copyright (c) 2012, Salesforce.org
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
* @date 2012 
* @description Controller for Relationships viewer VF page 
*/
global with sharing class REL_RelationshipsViewer_CTRL{
	
    public REL_RelationshipsViewer_CTRL(ApexPages.StandardController controller) {}
    
    @RemoteAction
    global static List<Node> getRelationships(id cid, boolean isInitialLoad, List<String> relIds) {
        list<Node> returnList = new list<Node>();
        list<npe4__Relationship__c> relationshipList = new list<npe4__Relationship__c>();
        
        List<Id> renderedRelationships;
        if (relIds != null)
            renderedRelationships = (List<Id>)relIds;
        else
            renderedRelationships = new List<Id>();
        
        if (isInitialLoad){         
            //base node
            Node basenode = new Node();
      
            Contact c = [select LastName, FirstName, id, Title, Account.Name from Contact where id = :cid];
            basenode.LastName = c.LastName;
            basenode.FirstName = c.FirstName;
            basenode.Title = c.Title;
            basenode.AccountName = c.Account.Name;
            basenode.id = c.id;
            returnList.add(basenode);
        }        
        else{
            relationshipList.clear();
            returnList.clear();
        }        
        
        relationshipList = [select id, npe4__RelatedContact__r.LastName, toLabel(npe4__Type__c), npe4__Status__c, npe4__RelatedContact__r.FirstName, 
                                    npe4__RelatedContact__r.id, npe4__RelatedContact__r.Account.Name, npe4__RelatedContact__r.Title,
                                    npe4__ReciprocalRelationship__r.id 
                                    from npe4__Relationship__c 
                                    where npe4__Contact__c = :cid and id NOT IN :renderedRelationships 
                                    and npe4__ReciprocalRelationship__r.id NOT IN :renderedRelationships];

        for (npe4__Relationship__c r : relationshipList){
            Node n = new Node();             
            n.LastName = r.npe4__RelatedContact__r.LastName;
            n.id = r.npe4__RelatedContact__r.id;
            n.Type = r.npe4__Type__c;
            n.Status = r.npe4__Status__c;
            n.FirstName = r.npe4__RelatedContact__r.FirstName;
            n.link =  URL.getSalesforceBaseUrl().toExternalForm() + '/' + r.npe4__RelatedContact__r.id;
            n.AccountName = r.npe4__RelatedContact__r.Account.Name;
            n.Title = r.npe4__RelatedContact__r.Title;
            n.relId = r.id;            
            returnList.add(n);
        }
        return returnList;
    }
        
    //get Relationship object prefix for links
    @RemoteAction
    global static RelationshipInfo getRelationshipInfo(){
        return new RelationshipInfo();
    }
    
    global class RelationshipInfo{
        String prefix;
        String NameField;
        String NameIdField;
        public RelationshipInfo() {
            npe4__Relationship_Settings__c rs = UTIL_CustomSettingsFacade.getRelationshipSettings();
            
            this.prefix = npe4__Relationship__c.sObjectType.getDescribe().getKeyPrefix();
            if (rs.npe4__Relationship_Name_Field_Id__c != null){
                this.NameField = rs.npe4__Relationship_Name_Field_Id__c;
            }
            if (rs.npe4__Relationship_Name_Id_Field_Id__c != null){
                this.NameIdField = rs.npe4__Relationship_Name_Id_Field_Id__c;
            }           
        }       
    }

    global class Node { 
        public String FirstName;
        public String LastName;
        public String id;              
        public String Status;
        public String Type;
        public String link;    
        public String AccountName;
        public String Title;
        public String relId;
    }  
    
}