({
    /*******************************************************************************************************
     * @description From the Change Address popup, finds the selected existing or new address, and makes it
     * the new default, and fires the HH_AddressChangedEvent, so the container can update the contacts and
     * hh with the new default address.
     */
    setDefaultAddress : function(component) {
        var addr;
        var listAddr = component.get('v.listAddr');
        var iAddrSelected;

        // figure out if changing default address or creating a new one
        if (component.get('v.isExistingAddrSectionOpen')) {
            iAddrSelected = component.get('v.iAddrSelected');
            addr = listAddr[iAddrSelected];
        } else {
            addr = component.get('v.addrNew');
            iAddrSelected = listAddr.length;
            listAddr.push(addr);
        }

        // clear out current default address
        for (var i = 0; i < listAddr.length; i++) {
            listAddr[i].Default_Address__c = false;
        }

        // update our hh default address and list of addresses
        addr.Default_Address__c = true;
        component.set('v.listAddr', listAddr);
        // this will get set in onChangeListAddr()
        //component.set('v.addrDefault', addr);

        // now notify other components the change occurred
        var event = $A.get("e.c:HH_AddressChangedEvent");
        event.setParams({ "addrDefault" : addr });
        event.fire();
    },
        
})