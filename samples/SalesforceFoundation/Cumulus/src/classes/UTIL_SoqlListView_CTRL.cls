/*
    Copyright (c) 2011, Salesforce.org
    All rights reserved.
    
    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are met:
    
    * Redistributions of source code must retain the above copyright
      notice, this List of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this List of conditions and the following disclaimer in the
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
* @group Utilities
* @description Controller class for the SOQL List View component
*/// Written by David Habib, copyright (c) 2013 DJH Consulting, djhconsulting.com 
// This program is released under the GNU Affero General Public License, Version 3. http://www.gnu.org/licenses/

public with sharing class UTIL_SoqlListView_CTRL {

    public UTIL_iSoqlListViewConsumer pageController {   
        get; 
        set {
            if (value != null) {
                pageController = value;
                pageController.setComponentController(this);
            }
        }
    }

    // the set controller allows us to do paging in our pageTable
    public ApexPages.StandardSetController setCon {
        get { 
            if(setCon == null) {
                string strSoql = pageController.getStrSoql();
                
                // start out sorting on the main name field.
                if (SortItemField == null) {
                    SortItemField = pageController.getstrNameField();
                    SortItemAsc = true;                 
                }

                if (SortItemField != null) {
                    strSoql += ' ORDER BY ' + SortItemField + (SortItemAsc ? ' ASC' : ' DESC');
                }
                strSoql += ' LIMIT 10000';
                setCon = new ApexPages.StandardSetController(Database.getQueryLocator(strSoql));
                setCon.setPageSize(cRecordsPerPage);
            }
            return setCon;
        } 
        set;
    }
        
    // how many records per page to display
    public integer cRecordsPerPage { 
        get {
            if (cRecordsPerPage == null) cRecordsPerPage = 10; 
            return cRecordsPerPage;
        }
        set; 
    }
    
    // actionMethod to increase the number of records displayed.
    public PageReference showMoreRecordsPerPage() {
        cRecordsPerPage += cRecordsPerPage;
        if (setCon != null) setCon.setPageSize(cRecordsPerPage);
        return null;
    }
    
    // how many pages in our current result set
    public integer NumberOfPages {
        get {
            if (setCon != null) {
                NumberOfPages = (setCon.getResultSize() / setCon.getPageSize()) + 1;
            }
            return NumberOfPages;
        }
        set;
    }
    
    // how many records in our current result set
    public integer NumberOfItems {
        get {
            if (setCon != null) {
                NumberOfItems = setCon.getResultSize();
            }
            return NumberOfItems;
        }
        set;
    }
    
    // returns a list of SLVObj's for the current page view.
    // note that this property always refreshes state from the current controller's set of records
    public list<SLVObj> listSLVObj {
        get {
                listSLVObj = new list<SLVObj>();
                
                // UNDONE: This is where I could potentially query for each contacts next shift, and add
                // it to the wrapper object.
                
                for (SObject sobj : setCon.getRecords()) {
                    // wrapper object stores the sobj and maintains a checked property.
                    SLVObj slvobj = new SLVObj(sobj);
                    listSLVObj.add(slvobj);         
                }
            listSLVObjCurrent = listSLVObj;
            return (listSLVObj);
        }
        set;
    } 
    
    // returns a list of SLVObj's for the current page view.
    // note that this property holds the current state of the SLVObj's before listSLVObj gets refreshed.
    // this is the list to use to detect which are checked.
    public list<SLVObj> listSLVObjCurrent { get; set; }
    
    // holds the current letter we are filtering on 
    public string strAlphaFilter { get; set; }
    
    // force the page to refresh when the AlphaFilter changes
    public PageReference AlphaFilter() {
        setCon = null;
        return null;
    }
    
    // a hack to provide a list of the alphabet so the component
    // can iterate over the list to create links, rather than
    // explictly listing each link in the component.
    public list<string> listAlphaFilters {
        get {
            if (listAlphaFilters == null) {
                listAlphaFilters = new list<string>{'A','B','C','D','E','F','G','H',
                        'I','J','K','L','M','N','O','P','Q','R',
                        'S','T','U','V','W','X','Y','Z'};
            }
            return listAlphaFilters;
        }
        set;
    }
    
    // will hold the ID of the item to be deleted
    public ID idDeleteItem { get; set; }
    
    // action method to delete an item from the database.
    public PageReference DeleteItem() {
        if (idDeleteItem != null) {
            database.delete(idDeleteItem);
            idDeleteItem = null;
            setCon = null;
        }
        return null;    
    }

    // will hold the ID of the item to be edit
    public ID idEditItem { get; set; }
    
    // action method to edit an item.  defer to the page controller.
    public PageReference EditItem() {
        return pageController.EditItem(idEditItem);
    }
    
    // will hold the developer name of the field to be sorted
    public string SortItemField { get; set; }
    
    // whether current sorted ascending or descending.
    public boolean SortItemAsc { get; set; }
    
    // action method to sort a column.
    public PageReference SortItem() {
        SortItemAsc = SortItemAsc ? false : true;
        // force a requery
        setCon = null;      
        return null;
    }

    public boolean showCheckboxes { get; set; }
    
    //==================================================================================
    // SoqlListViewObject - a simple class to wrap each object, so the list view can 
    // maintain its own checked status for each item.
    public with sharing class SLVObj {
    
        public SLVObj(SObject objExisting) {
            sobj = objExisting;
            checked = false;
        }
        
        public SObject sobj { get; set; }
        public boolean checked { get; set; }
        public Error__c err { get { return (Error__c) sobj; } }
    }

    //==================== TEST METHOD(s) ======================================
    // tested by tests in VOL_CTRL_VolunteersFind.cls
    
}