({
    fireUpdateEvent: function(component, eventName) {
        var event = component.getEvent(eventName);
        event.setParams({segment: component.get('{!v.source}')});
        event.fire();
    },

    updateDependentInputs: function(component, helper, sourceSegment) {
        if (!sourceSegment || !(sourceSegment.segmentType === 'REPORT_SOURCE_SEGMENT' || sourceSegment.segmentType === 'CAMPAIGN_SOURCE_SEGMENT')) {
            return;
        }
        var inputComponent;
        var action;
        var params;
        var selectColumnLabel;
        var sourceVal;
        var options = [];

        if (sourceSegment.segmentType === 'REPORT_SOURCE_SEGMENT') {
            inputComponent = component.find("columnName");
            action = component.get("c.getReportIdColumns");
            params = {"reportId": sourceSegment.sourceId};
            sourceVal = sourceSegment.columnName;
            if (component.get('v.nsPrefix') === 'camptools') {
                selectColumnLabel = '$Label.camptools.CampaignToolsListEditorSelectIdColumn';
            } else {
                selectColumnLabel = '$Label.c.CampaignToolsListEditorSelectIdColumn';
            }
            options.push({
                    label: $A.get(selectColumnLabel),
                    value: ''
                });
        } else {
            inputComponent = component.find("status");
            action = component.get("c.getStatuses");
            params = {"campaignId": sourceSegment.sourceId};
            sourceVal = sourceSegment.statusIds;
            var sourceValEmpty = (sourceVal === undefined || sourceVal === null);
            if (component.get('v.nsPrefix') === 'camptools') {
                selectColumnLabel = '$Label.camptools.CampaignToolsListEditorAllStatus';
            } else {
                selectColumnLabel = '$Label.c.CampaignToolsListEditorAllStatus';
            }
            options.push({
                    label: $A.get(selectColumnLabel),
                    value: '',
                    selected: sourceValEmpty
                });
        }
        action.setParams(params);
        action.setCallback(this, function(response) {
            var state = response.getState();
            if (state === "SUCCESS") {
                var choices = response.getReturnValue();
                var values = Object.keys(choices);
                sourceVal = (sourceVal != undefined ? sourceVal.split(";") : '');
                for (var i = 0; i < values.length; i+=1) {
                    var label = choices[values[i]];
                    var value = values[i];
                    var isSelected = (sourceVal.indexOf(values[i]) > -1);
                    options.push({
                        label: label,
                        value: value,
                        selected: isSelected
                    });
                }
                if (inputComponent && inputComponent.isValid()) {
                    inputComponent.set("v.options", options);
                }
            } else if (state === "ERROR") {
                // @todo what do we do if we can't find any id columns or the
                // report doesn't exist, etc?
                if (inputComponent && inputComponent.isValid()) {
                    var emptyLabel;
                    if (component.get('v.nsPrefix') === 'camptools') {
                        emptyLabel = '$Label.camptools.CampaignToolsListEditorEmptyIdColumn';
                    } else {
                        emptyLabel = '$Label.c.CampaignToolsListEditorEmptyIdColumn';
                    }
                    inputComponent.set(
                        "v.options",
                        [{
                            label: $A.get(emptyLabel),
                            value: ''
                        }]
                    );
                }
            }
        });
        $A.enqueueAction(action);
    }
})