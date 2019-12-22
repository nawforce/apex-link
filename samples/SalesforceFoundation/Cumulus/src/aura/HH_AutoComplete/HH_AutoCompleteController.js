({
    handleNewContact: function(component, event, helper) {
        event.preventDefault();
        var con = {'sobjectType': 'Contact'};
        var autocomplete = component.find('autocomplete');
        var name = autocomplete.get('v.displayValue');
        var list = name.split(' ');
        if (list.length === 0) {
            con.LastName = name;
        } else if (list.length === 1) {
            con.LastName = list[0];
        } else {
            con.FirstName = list[0];
            con.LastName = list[1];
            for (var i = 2; i < list.length; i++) {
                con.LastName += ' ' + list[i];
            }
        }
        var evt = component.get('e.ContactNewEvent');
        evt.setParams({ "contact" : con });
        evt.fire();
    },
    
    clearList: function(component) {
        var autocomplete = component.find('autocomplete');
        autocomplete.clearList();
    }

})