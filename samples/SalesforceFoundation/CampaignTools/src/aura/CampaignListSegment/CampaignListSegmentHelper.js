({
    fireUpdateEvent: function(component, eventName) {
        var event = component.getEvent(eventName);
        event.setParams({segment: component.get('{!v.segment}')});
        event.fire();
    }
})