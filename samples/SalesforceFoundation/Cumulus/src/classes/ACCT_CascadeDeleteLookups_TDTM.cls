/*
    Copyright (c) 2017, Salesforce.org
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
 * @date 2017
 * @group Util
 * @description Cascade deletion extension for Account object.
 */
public with sharing class ACCT_CascadeDeleteLookups_TDTM extends UTIL_CascadeDeleteLookups_TDTM {

    /*******************************************************************************************************
    * @description Cascade deletion definitions for Account.
    ********************************************************************************************************/
    public static List<Cascade> cascades = new List<Cascade>{
        new Cascade('npe03__Recurring_Donation__c','npe03__Organization__c'),
        new Cascade('npe03__Recurring_Donation__c','npe03__Contact__r.AccountId'),
        new Cascade(UTIL_Namespace.StrTokenNSPrefix('Allocation__c'),'Opportunity__r.AccountId'),
        new Cascade(UTIL_Namespace.StrTokenNSPrefix('Allocation__c'),'Recurring_Donation__r.npe03__Organization__c'),
        new Cascade(UTIL_Namespace.StrTokenNSPrefix('Allocation__c'),'Recurring_Donation__r.npe03__Contact__r.AccountId'),
        new Cascade('npe4__Relationship__c', 'npe4__ReciprocalRelationship__r.npe4__Contact__r.AccountId')
    };

    /*******************************************************************************************************
    * @description Override provides super class with cascades for this triger.
    * @return List<Cascade> The cascade deletions to perform for this trigger.
    ********************************************************************************************************/    
    public override List<Cascade> getCascades() {
        return cascades;
    }

    /*******************************************************************************************************
    * @description This method override detects error conditions in cascade deleted objects. Opportunities 
    * that are Closed/Won prevent deletion of the parent Account. 
    * This only applies for the CON_DeleteContactOverride page as the platform handles this for accounts
    * otherwise.
    * @return void This method adds to the errors property of the Cascades objects and returns nothing.
    ********************************************************************************************************/
    public override void addDeletionErrors() {
        for (Cascade cc : getCascades()) {
            //special case deleting opportunities
            if (cc.objectName == 'Opportunity') {
                for (SObject child : cc.children.values()) {
                    //don't cascade closed/won opportunities
                    if ((Boolean)child.get('IsClosed') && (Boolean)child.get('IsWon')) {
                        putError(cc, child.id, Label.ClosedWonOpportunities);
                    }
                }
            }
            if (cc.objectName == 'Case') {
                for (SObject child : cc.children.values()) {
                    putError(cc, child.id, Case.SObjectType.getDescribe().getLabelPlural());
                }
            }
        }
    }
}