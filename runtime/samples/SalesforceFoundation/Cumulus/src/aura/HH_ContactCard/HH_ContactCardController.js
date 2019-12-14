({
    // a naming exclusion checkbox has been modified, notify our container
    onCheck : function(component, event, helper) {
        helper.fireContactChangedEvent(component, event);
    },

    // the remove button has been pressed, notify our container
    removeContact : function(component, event, helper) {
        event.preventDefault();
        helper.fireContactRemoveEvent(component, event);
    },
})