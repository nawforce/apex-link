({
    // initialize the container by loading up our Household and Contacts
    doInit : function(component, event, helper) {
        helper.warnOnLockerService(component);
        helper.fixupCustomLabels(component);
        helper.loadObjects(component);
        helper.initNewContact(component);
    },

    // display the spinner
    showSpinner : function(component /* , event, helper */) {
        component.set("v.showSpinner", true);
    },

    // hide the spinner
    hideSpinner : function(component /* , event, helper */) {
        component.set("v.showSpinner", false);
    },

    // a Contact has changed, so update our naming
    handleContactChangedEvent : function(component, event, helper) {
        helper.updateHHNames(component);
    },

    // proceed with removing the contact
    doRemoveContact : function(component, event, helper) {
        helper.doRemoveContact(component, event);
    },

    // cancel removing the contact
    cancelRemoveContact : function(component, event, helper) {
        helper.cancelRemoveContact(component, event);
    },

    // a Contact has been requested to Remove, so prompt the user and track the remove
    handleContactRemoveEvent : function(component, event, helper) {
        helper.promptRemoveContact(component, event);
    },

    // Contact ordering has changed, so update our naming
    handleContactReorderEvent : function(component, event, helper) {
        helper.updateHHNames(component);
    },

    // an autoName checkbox has changed, so update our naming
    onCheck : function(component, event, helper) {
        helper.updateHHNames(component);
    },

    // a Name/Greeting textbox has changed, so update our autoName state
    onTextChange : function(component, event, helper) {
        helper.updateAutoNaming(component, event);
    },

    // save all changes and return to the household
    save : function(component, event, helper) {
        helper.save(component);
    },

    // close and return to the household
    close : function(component, event, helper) {
        helper.closePage(component);
    },

    // the default Address has changed, so update our Contacts
    handleAddressChangedEvent : function(component, event, helper) {
        helper.updateDefaultAddress(component, event.getParam('addrDefault'));
    },

    // show the new contact popup
    handleContactNewEvent : function(component, event, helper) {
        helper.showNewContactPopup(component, event);
    },

    // proceed with create a new contact
    doNewContact : function(component, event, helper) {
        helper.createNewContact(component, event);
    },

    // cancel New Contact
    cancelNewContact : function(component /* , event, helper */) {
        component.set('v.showNewContactPopup', false);
    },

    // Salutation select list has changed, so update conNew
    onSalutationChange : function(component, event, helper) {
        helper.onSalutationChange(component);
    },

    // add just the contact to the Household
    doAddContact : function(component, event, helper) {
        var conAdd = component.get('v.conAdd');
        helper.addContact(component, conAdd);
        component.set('v.showMergeHHPopup', false);
    },

    // merge the new household into the household
    doMergeHH : function(component, event, helper) {
        var hhMerge = component.get('v.hhMerge');
        helper.mergeHousehold(component, hhMerge);
        component.set('v.showMergeHHPopup', false);
    },

    // cancel Merge HH Popup
    cancelMergeHHPopup : function(component /* , event, helper */) {
        component.set('v.showMergeHHPopup', false);
    },

    // a Contact has been selected in the autocomplete
    handleAutoCompleteOptionSelectedEvent : function(component, event, helper) {
        // collapse and clear the autocomplete list
        var autoComplete = component.find('HHAutoComplete');
        autoComplete.set('v.isListVisible', false);
        autoComplete.clearList();
        
        // do the work.
        helper.addOrMergeContact(component, event);
    },
})