/*
    Copyright (c) 2014, Salesforce.org
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
* @date 2014
* @group Allocations
* @group-content ../../ApexDocContent/Allocations.htm
* @description Controller for the Manage Allocations page.
*/
public with sharing class ALLO_ManageAllocations_CTRL {

    /** @description The current list of allocations we're managing, as displayed on the page.*/
    public list<Allocation__c> listAllo {get;set;}
    /** @description If defaults are enabled, the default allocation is here, and not in listAllo. */
    public Allocation__c defaultAllo {get;set;}

    /** @description The number of allocations in listAllo. */
    public integer listAlloSize {
        get{
            return listAllo.size();
        }
        set;
    }

    /** @description List of allocations to delete when the user clicks Save.*/
    public list<Allocation__c> listAlloForDelete = new list<Allocation__c>();

    /** @description The id of the parent object; Opportunity, Campaign, or Recurring Donation.*/
    public id parentId {get;set;}
    /** @description The parent opportunity, if we're dealing with opportunity allocations.*/
    public Opportunity opp {get;set;}
    /** @description The parent campaign, if we're dealing with campaign allocations.*/
    public Campaign cam {get;set;}
    /** @description The parent recurring donation, if we're dealing with recurring donation allocations.*/
    public npe03__Recurring_Donation__c rd {get;set;}
    /** @description The amount of the parent opportunity, if these are opportunity allocations.*/
    public decimal parentAmount {get;set;}
    /** @description The allocations settings object. */
    public Allocations_Settings__c settings {
        get {
            if (settings==null) {
                settings = UTIL_CustomSettingsFacade.getAllocationsSettings();
            }
            return settings;
        }
        set;
    }

    /** @description The page title based on which type of allocation we're using. */
    public string pageTitle {get;set;}
    /** @description The name of the object we're adding allocations to. */
    public string objectName {get;set;}
    /** @description The key prefix for the object, for navigation. */
    public string keyPrefix {get;set;}
    /** @description The API name of the object. */
    public string objectAPIName {get;set;}
    /** @description The API name of the object. */
    public string objectLabelPlural {get;set;}

    /** @description Row number sent back by page for add and delete row methods.*/
    public integer rowNumber {get;set;}

    /**
     * @description The currency symbol or ISO code of the related record or
     * org default
     */
    @TestVisible
    private String currencySymbol;

    /** @description Constructor queries for the parent object and the allocations, and fills out attributes.*/
    public ALLO_ManageAllocations_CTRL(ApexPages.StandardSetController ssc) {

        parentId = ApexPages.currentPage().getParameters().get('id');
        parentAmount = 0;

        if (parentId.getSObjectType() == Opportunity.sObjectType) {
            opp = [SELECT Id, Name, Amount FROM Opportunity WHERE Id = :parentId];

            pageTitle = Label.alloManageOppAllocations;
            objectName = opp.Name;

            populateObjectParameters(Opportunity.sObjectType);

            //only Opportunities have parent amounts and enforce being below this amount
            if (opp.Amount!=null) {
                parentAmount = opp.Amount;
            }
            if (parentAmount==0) {
                ApexPages.addMessage(new ApexPages.Message(ApexPages.Severity.Warning, Label.alloCantAllocateNothing));
            }
        } else if (parentId.getSObjectType() == Campaign.sObjectType) {
            cam = [SELECT Id, Name FROM Campaign WHERE Id = :parentId];

            pageTitle = Label.alloManageCampaignAllocations;
            objectName = cam.Name;

            populateObjectParameters(Campaign.sObjectType);

        } else if (parentId.getSObjectType() == npe03__Recurring_Donation__c.sObjectType) {
            rd = [SELECT Id, Name FROM npe03__Recurring_Donation__c WHERE Id = :parentId];

            pageTitle = Label.alloManageRecurringDonationAllocations;
            objectName = rd.Name;

            populateObjectParameters(npe03__Recurring_Donation__c.sObjectType);
        }

        defaultAllo = new Allocation__c();
        listAllo = [SELECT Id, Name, Amount__c, Percent__c, General_Accounting_Unit__c FROM Allocation__c WHERE Opportunity__c = :parentId OR Campaign__c = :parentId OR Recurring_Donation__c = :parentId];

        //find default allocation if enabled, remove from list
        if (settings.Default_Allocations_Enabled__c && opp!=null) {
            for (integer i=0; i<listAllo.size(); i++) {
                if (listAllo[i].General_Accounting_Unit__c == settings.Default__c) {
                    defaultAllo = listAllo[i];
                    listAllo.remove(i);
                    break;
                }
            }
        }

        if (listAllo.isEmpty())
            addRow();
    }

    /**
     * @description Private default constructor
     */
    @TestVisible
    private ALLO_ManageAllocations_CTRL() {}

    /** @description Adds an empty row to the bottom of the list and refreshes the page.*/
    private void populateObjectParameters(Schema.sObjectType st) {
        DescribeSObjectResult describe = st.getDescribe();
        keyPrefix = describe.getKeyPrefix();
        objectAPIName = describe.getName();
        objectLabelPlural = describe.getLabelPlural();
    }

    /**
     * @description Return the currency symbol appropriate for the current
     * user/org/record.  If the org is multi currency enabled, it will use the
     * currency iso code from the related record.  If the org is not multi
     * currency enabled, it will return the symbol for the currency of the org,
     * or the currency iso code if no symbol is known.
     *
     * @return String A currency symbol or currency ISO code
     */
    public String getCurrencySymbol() {
        if (currencySymbol == null) {
            currencySymbol = UTIL_Currency.getInstance().getCurrencySymbol(parentId);
        }
        return currencySymbol;
    }

    /** @description Adds an empty row to the bottom of the list and refreshes the page.*/
    public pageReference addRow() {
        Allocation__c allo = new Allocation__c();

        if (opp!=null)
            allo.Opportunity__c = parentId;
        else if (cam!=null)
            allo.Campaign__c = parentId;
        else if (rd!=null)
            allo.Recurring_Donation__c = parentId;

        //enable adding to the last row
        if (rowNumber == null || rowNumber >= listAllo.size()-1)
            listAllo.add(allo);
        else
            listAllo.add(rowNumber+1, allo);

        return null;
    }

    /** @description Removes a row from the page, and adds to the list for deletion once the user saves.*/
    public pageReference delRow() {
        //add to deletion list if the allocation has an id
        if (listAllo[rowNumber].id!=null)
            listAlloForDelete.add(listAllo[rowNumber]);
        listAllo.remove(rowNumber);

        return null;
    }

    /** @description Saves changes and returns user to parent object detail page.*/
    public pageReference saveClose() {
        list<Allocation__c> listAlloForInsert = new list<Allocation__c>();
        list<Allocation__c> listAlloForUpdate = new list<Allocation__c>();
        pageReference source = new pageReference('/'+parentId);
        source.setRedirect(true);
        source.getParameters().put('t',''+(System.currentTimeMillis()));

        //split into insert and update cases so we can reset recursion flags for each operation
        for (Allocation__c allo : listAllo) {
            if (allo.id!=null) listAlloForUpdate.add(allo);
            else listAlloForInsert.add(allo);
        }

        try {
            if (!listAlloForDelete.isEmpty()) {
                ALLO_Allocations_TDTM.resetFlags(false);
                delete listAlloForDelete;

                //allow user to delete all GAUs and save with one empty GAU
                if (listAllo.size()==1 && listAllo[0].General_Accounting_Unit__c==null && listAllo[0].Amount__c==null && listAllo[0].Percent__c==null)
                    return source;
            }
            if (!listAlloForUpdate.isEmpty()) {
                ALLO_Allocations_TDTM.resetFlags(false);
                update listAlloForUpdate;
            }
            if (!listAlloForInsert.isEmpty()) {
                ALLO_Allocations_TDTM.resetFlags(false);
                insert listAlloForInsert;
            }
            
            return source;
        } catch (Exception e) {
            ApexPages.addMessages(e);
            return null;
        }
    }

    /** @description Discards all changes and returns user to parent object detail page.*/
    public pageReference cancel() {
        pageReference source = new pageReference('/'+parentId);
        return source;
    }
}