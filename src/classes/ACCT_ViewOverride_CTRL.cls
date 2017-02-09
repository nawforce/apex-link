/*
    Copyright (c) 2009, Salesforce.org
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
* @date 2011 (1.x)
* @group Accounts
* @description Controller for overriding account view VF page for 1x1 contacts 
*/
public with sharing class  ACCT_ViewOverride_CTRL {

    /*******************************************************************************************************
    * @description The Account object being viewed.
    */ 
    private final Account account;

    /*******************************************************************************************************
    * @description constructor for the class
    * @param controller the standard Account controller
    */ 
    public ACCT_ViewOverride_CTRL (ApexPages.StandardController controller)
    {
        //get the account with the npe01__one2oneContact__c field for use in the rederict if necessary
        List <Account> accounts = [select id, npe01__one2oneContact__c, npe01__SYSTEM_AccountType__c from Account where id = :ApexPages.currentPage().getParameters().get('id')];
        if (accounts.size() > 0)
        {
            account = accounts[0];
        }
        else
        {
            account = null;
        }
    }

    /*******************************************************************************************************
    * @description returns the Account object
    */ 
    public Account getAccount()
    {
        return account;
    }
    

}