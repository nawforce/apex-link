({
    handleSourceChange: function(component, event, helper) {
        var sourceSegment = event.getParam("value");
        helper.updateDependentInputs(component, helper, sourceSegment);
    },

    handleSourceType: function(component) {
        var selectCmp = component.find("sourceType");
        var segmentType = component.get('v.source.segmentType');
        segmentType = selectCmp.get("v.value");
        component.set("v.source.segmentType", segmentType);
    },

    handleColumnName: function(component) {
        var selectCmp = component.find("columnName");
        var source = component.get('v.source');
        var columnName = selectCmp.get("v.value");
        if (columnName === '') {
            source.columnName = null;
        } else {
            source.columnName = columnName;
        }
        component.set("v.source", source);
    },

    handleStatus: function(component) {
        var selectCmp = component.find("status");
        var source = component.get('v.source');
        var statusIds = selectCmp.get("v.value");
        if (statusIds === '') {
            source.statusIds = null;
        } else {
            source.statusIds = statusIds;
        }
        component.set("v.source", source);
    },

    deleteSource: function (component, event, helper) {
        helper.fireUpdateEvent(component, 'deleteSegmentEvent');
    },

    handleAutocomplete: function(component, event) {
        var selOpt = event.getParam('value');
        var sourceSegment = component.get("v.source");
        sourceSegment.sourceName = selOpt.label;
        sourceSegment.sourceId = selOpt.value;
        component.set("v.source", sourceSegment);
    }
})