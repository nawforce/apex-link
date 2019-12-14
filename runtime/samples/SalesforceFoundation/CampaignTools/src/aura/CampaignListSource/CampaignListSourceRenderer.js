({
    afterRender: function(component, helper) {
        this.superAfterRender();
        var sourceSegment = component.get("v.source");
        helper.updateDependentInputs(component, helper, sourceSegment);
    }
})