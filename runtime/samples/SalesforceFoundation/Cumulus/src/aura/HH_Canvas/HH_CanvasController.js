({
    // called after jquery & jqueryui loaded.  initialize our jquery/ui handlers
    afterScriptsLoaded : function(component, event, helper) {
        helper.initJQueryHandlers(component);
    },
})