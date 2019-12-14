/*
    Written by Ryan Foster, copyright (c) 2011 Groundwire
    This program is released under the GNU Affero General Public License, Version 3. http://www.gnu.org/licenses/

    04/01/2012 James Melville
    Changes made throughout to support NPSP HH
    * String.escapeSingleQuotes() added (bug fix for names including ' etc.)
    * class renamed ManageHousehold_EXT
    * Fields / objects updated to NPSP values / names e.g. npo02__Household__c (namespace prefix required?)
    * Naming methods updated to support NPSP's Name / Formal / Informal structure and npo02__SYSTEM_CUSTOM_NAMING__c setting field
    * Address object updated with viableAddress class to store household or contact addresses
    * use Mailing* instead of Other* fields for addresses
    * Set Household address based on address selected in UI
    * Use "useAddress" flag to set or not set address on contacts
    * 15/01/2012 Fixed household addressing, added tests for household addressing code
    * 16/01/2012 Fixed defect when contacts were removed and re-added
*/
/**
* @author Ryan Foster, James Melville
* @date 2012 (2.4)
* @description This class is the VF page extension for the Manage Household interface. This allows users to
* setup their household members, name, and greeting before creating the household. It will also allow the user
* to set the address on the household.  
*/
//cumulusci-managed@deprecated
global with sharing class ManageHousehold_EXT { 

    @RemoteAction
    global static list<Contact> findContacts(string subString, String state) {return null;}

    @RemoteAction
    global static string remoteAddMember(Id cid, Boolean includeHH, String state) {return null;}

    @RemoteAction
    global static string remoteRemoveMember(Id cid, String state) {return null;}

    @RemoteAction
    global static string remoteSetNaming(Boolean aN, String nN, Boolean aFG, String nFG, Boolean aIG, String nIG, String state) {return null;}

    @RemoteAction
    global static string remoteSave(String state) {return null;}
    
}