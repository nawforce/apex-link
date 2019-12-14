({
    /*******************************************************************************************************
    * @description called by the autocomplete component to retrieve the list of contacts that matches
    * the name the user has typed in.
    */
    provide: function (component, event, helper) {
        var args = event.getParam('arguments');
        if (args && args.keyword && args.callback) {
            var queryAction = component.get('c.queryObjects');
            var listCon = component.get('v.listCon');
            queryAction.setParams({
                queryValue: args.keyword,
                listCon: listCon
            });
            queryAction.setAbortable();  
            queryAction.setCallback(
                this,
                function (response) {
                    var state = response.getState();

                    if ('SUCCESS' === state) {
                        args.callback(
                            null,
                            args.idDataCallback,
                            response.getReturnValue()
                        );
                    } else if ('ERROR' === state) {
                        var auraErrors = response.getError();
                        var errors = [];
                        if (auraErrors) {
                            for (var error in auraErrors) {
                                if (error.message) {
                                    errors.push(new Error(error.message));
                                }
                            }
                        }
                        args.callback(
                            errors,
                            args.idDataCallback,
                            []
                        );
                    }
                }
            );
            $A.enqueueAction(queryAction);
        } else {
            args.callback(null, []);
        }
    },
})