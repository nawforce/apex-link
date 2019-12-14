({
    /*******************************************************************************************************
     * @description initialize our component, which is just creating an empty new address for our popup.
     */
    doInit : function(component /* , event, helper */) {
        component.set('v.addrNew', {});
    },

    /*******************************************************************************************************
     * @description called whenever v.listAddr is modified, to update v.addrDefault.
     */
    onChangeListAddr : function(component /* , event, helper */) {
        var listAddr = component.get('v.listAddr');
        if (listAddr.length > 0) {

            // find the default address
            var iAddrDefault = 0;
            for (var i = 0; i < listAddr.length; i++) {
                if (listAddr[i].Default_Address__c) {
                    iAddrDefault = i;
                    break;
                }
            }
            component.set('v.addrDefault', listAddr[iAddrDefault]);
        }
    },

    /*******************************************************************************************************
     * @description open the Change Address popup
     */
    openChangeAddress : function(component /* , event, helper */) {
        component.set('v.showChangeAddressPopup', true);
    },

    /*******************************************************************************************************
     * @description close the Change Address popup, making no changes.
     */
    cancelChangeAddress : function(component /* , event, helper */) {
        component.set('v.showChangeAddressPopup', false);
    },

    /*******************************************************************************************************
     * @description Save the new or existing address as the default, and close the Change Address popup
     */
    saveChangeAddress : function(component, event, helper) {
        helper.setDefaultAddress(component);
        component.set('v.showChangeAddressPopup', false);
        component.set('v.addrNew', {});
        component.set('v.isExistingAddrSectionOpen', true);
    },

    /*******************************************************************************************************
     * @description tracks which existing address was last selected in the popup
     */
    onPressAddrTile : function(component, event /* , helper */) {
        var iAddr = Number(event.currentTarget.getAttribute('data-iAddr'));
        component.set('v.iAddrSelected', iAddr);
    },

    /*******************************************************************************************************
     * @description hides/shows the existing address/new address sections of the popup
     */
    toggleAddrSection : function(component /* , event, helper */) {
        var isOpen = component.get('v.isExistingAddrSectionOpen');
        component.set('v.isExistingAddrSectionOpen', !isOpen);
    },

})