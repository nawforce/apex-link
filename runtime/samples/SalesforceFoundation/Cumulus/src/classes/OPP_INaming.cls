/*
    Copyright (c) 2015, Salesforce.org
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
* @date 2015
* @group Opportunity
* @group-content ../../ApexDocContent/Opportunity.htm
* @description Interface for classes that will provide Opportunity Naming functionality.
*/
public interface OPP_INaming {
    /*******************************************************************************************************
    * @description Get the opportunity names for the current list of opportunities. For Contact fields, the
    * Opportunities should use the primary Contact Role.
    * @param oppList the list of Opportunities to name
    * @return The list of opportunities with an updated name.
    */ 
    void refreshOppNames(list<Opportunity> oppList);
    /*******************************************************************************************************
    * @description Get the opportunity names for the current list of opportunities. Returns a newly queried
    * list of opportunities to allow for updates in an after insert trigger. For Contact fields, the
    * Opportunities should use the Opportunity's Primary Contact lookup field (Primary_Contact__c), 
    * as contact roles don't exist before insert.
    * @param oppList the list of Opportunities to name
    * @return The list of opportunities with an updated name.
    */ 
    list<Opportunity> getOppNamesAfterInsert(list<Opportunity> oppList);
}