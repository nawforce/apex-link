({
    /*******************************************************************************************************
    * @description after render, we must re-initialize any jquery/ui libraries used
    */
    afterRender : function(component, helper) {
        this.superAfterRender();
        helper.initJQueryHandlers(component);
    },

})