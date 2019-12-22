({
    handleAddPageMessage: function (component, event) {
        var message = {
            id: event.getParam('id'),
            severity: event.getParam('severity'),
            summary: event.getParam('summary'),
            detail: event.getParam('detail')
        };
        var messages = component.get('v.messages');
        messages.push(message);
        component.set('v.messages', messages);
    },

    handleRemovePageMessage: function (component, event) {
        var messages = component.get('v.messages');
        var indexToRemove = event.getParam('index');
        if (indexToRemove < 0 || indexToRemove >= messages.length) {
            return;
        }
        messages.splice(indexToRemove, 1);
        component.set('v.messages', messages);
    }

})