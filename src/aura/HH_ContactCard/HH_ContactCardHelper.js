({
    /*******************************************************************************************************
    * @description Notifies the application that properties of the contact have changed
    */
    fireContactChangedEvent : function(component, event) {

        // note that the contact's Naming Exclusions field is
        // updated by NPSP triggers based off the checkbox fields.
        // so there is no reason to do it in the ui.

        // now notify other components the change occurred
        event = $A.get("e.c:HH_ContactChangedEvent");
        var con = component.get('v.contact');
        event.setParams({ "contact" : con });
        event.fire();
    },

    /*******************************************************************************************************
    * @description Notifies the application that the contact's remove button was pressed
    */
    fireContactRemoveEvent : function(component, event) {

        // now notify other components the remove request occurred
        event = $A.get("e.c:HH_ContactRemoveEvent");
        event.preventDefault();
        var con = component.get('v.contact');
        event.setParams({ "contact" : con });
        event.fire();
    },

})