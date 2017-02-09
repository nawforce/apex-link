({

    /*******************************************************************************************************
    * @description Initializes jquery and jqueryUI to support drag/drop sortable in our ui
    */
    initJQueryHandlers : function(component) {
        var j$;
        /*global jQuery*/
        if (typeof jQuery !== "undefined" && typeof j$ === "undefined") {
            j$ = jQuery.noConflict(true);
        }

        if (typeof j$ === "undefined")
            return;
        
        if (!j$('.slds-has-dividers--around-space').sortable)
            return;

        // turn on jqueryui drag/sortable support
        j$('.slds-has-dividers--around-space').sortable( {
            tolerance: "pointer",

            // called after DOM has been updated after a drag/drop sort
            update: $A.getCallback(function(/* event, ui */) {
                // update our listCon to the new order
                var listCon = component.get('v.listCon');
                var listConNew = [];
                for (var i = 0; i < this.children.length; i++) {
                    var icon = this.children[i].getAttribute("data-icontact");
                    listConNew.push(listCon[icon]);
                    listCon[icon].npo02__Household_Naming_Order__c = i;
                }
                component.set('v.listCon', listConNew);

                // now notify other components the reorder occurred
                var evt = $A.get("e.c:HH_ContactReorderEvent");
                evt.setParams({ "listCon" : listConNew });
                evt.fire();
            })
        });
        j$('.slds-has-dividers--around-space').disableSelection();
    },

})