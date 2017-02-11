({
    removeMessage: function (component) {
        var removeMessageEvent = $A.get('e.c:RemovePageMessageEvent');
        removeMessageEvent.setParams(
            {
                'index': component.get('v.index')
            }
        );
        removeMessageEvent.fire();
    },

    setMessageTheme: function (component) {
        var message = component.get('v.message');

        if (!message) {
            return;
        }

        var messageTheme;

        switch (message.severity) {
            case 'confirm':
                messageTheme = 'success';
                break;
            case 'warning':
                messageTheme = 'warning';
                break;
            case 'error':
            case 'fatal':
                messageTheme = 'error';
                break;
            case 'info':
            default:
                messageTheme = 'info';
                break;
        }

        component.set('v.messageTheme', messageTheme);
    }
})