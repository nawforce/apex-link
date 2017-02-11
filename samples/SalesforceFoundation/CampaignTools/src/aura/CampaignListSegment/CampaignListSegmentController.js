({
    addSegment: function (component, event, helper) {
        helper.fireUpdateEvent(component, 'addSegmentEvent');
    },

    addGroup: function (component, event, helper) {
        helper.fireUpdateEvent(component, 'addGroupEvent');
    }
})