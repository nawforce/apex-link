// Written by Ryan Foster, copyright (c) 2011 Groundwire
// This program is released under the GNU Affero General Public License, Version 3. http://www.gnu.org/licenses/
// 04/01/2012 James Melville Changes made throughout to support NPSP HH

var j$ = jQuery.noConflict();
var pgController;
var fldPfx;

// Set the controller for APEX Remoting methods to handle managed/unmanaged
// todo: replace this and pgController throughout with Wes Nolte's cleaner code: http://th3silverlining.com/2012/02/26/salesforce-javascript-remoting-and-managed-packages/
try {
	pgController = npo02.ManageHousehold_EXT;
	fldPfx = 'npo02__';
} catch (err) {
	pgController = ManageHousehold_EXT;
	fldPfx = ''; //set to blank for unmanaged package
}
function fldNm (str) {
	return fldPfx+str;
}

j$(function () {
 	//make the contact cards sortable
	j$( 'div.hhMembers' ).sortable({ axis: 'x' , 
	//define a function that fires each time they are re-ordered
	update:function(event,ui){
	
	//write the order of the cards to their data attributes
	j$('div.hhCard').each(function(i) {
		var card = j$(this);
	    if (!card.hasClass('hhCard-new'))
		   	gwManageHH.state.activeContacts[card.attr('id')][fldNm('Household_Naming_Order__c')] = i; 
    	else
    		card.data('contact')[fldNm('Household_Naming_Order__c')] = i;
   		});
   	//end each
   	
   	//and regenerate the household names as appropriate
   	gwManageHH.jsonState = JSON.stringify(gwManageHH.state);
    gwManageHH.setInsertContacts();
	gwManageHH.setNaming();
   }
});

	
    /* Autocomplete contact search box for adding existing contacts to HH */
    
    j$('#hhSearch-input').autocomplete({
    	minLength: 1,
    	position: { my: "right top", at: "right bottom", collision: "none" },
    	source: function(request, response) {
    		var searchStr = request.term.toLowerCase();      			
			pgController.findContacts(searchStr,gwManageHH.jsonState, function(result, event) {       								
	        		if (event.type == 'exception') {
	        			alert(event.message);
	        		} else {
	        			if (result != null) {
	        				response(j$.map(result, function(item) {
	        					return {label: item.Name, value: item.Id, contact:item};
	        				}));
		        		}
	        		}
		   	});
	   	},
	   	select: function (event, ui) {
	   		j$(this).val(ui.item.label);
	   		return false;
	   	}
	}).data( "autocomplete" )._renderItem = function( ul, item ) {
    	var address = gwManageHH.addressStringify(item.contact);
    	
    	var linode = j$( '<li class="hhSearch-item"></li>' ).data( "item.autocomplete", item ).append( '<span class="hhSearch-item-name">' + item.label + '</span><span class="hhSearch-item-address">'+address+'</span>' );
    	
    	// If the contact is in a household add a visual indicator
    	if (item.contact[fldNm('Household__c')] != null) {
    		linode.find('.hhSearch-item-address').after('<span class="hhSearch-item-hh">' + gwManageHH.lblExistingHH + '</span>');
    	}
    	
    	// insert add button
    	linode.append('<button class="gwPreventUI gwButton hhSearch-item-add">' + gwManageHH.lblAdd + '</button>');
    	linode.find('.hhSearch-item-add').button();
    	
    	// insert icon
    	linode.prepend('<div class="hhCard-icon">Contact</div>');
    	
		return linode.appendTo(ul);
	}
	
	// setup search item add buttons
	j$('.hhSearch-item-add').live('click', gwManageHH.dialogAddMember);
    
    // setup search input
    j$('.gwTitleLabel').val(j$('.gwTitleLabel').attr('title'));
    j$('.gwTitleLabel').live('focusin focusout', function (event) {
    	var ht = j$(this).attr('title');
	    var v = j$(this).val();
    	if (event.type == 'focusin') {
	    	if (ht == v) {
	    		j$(this).val('');
	    	}
    	} else if (event.type == 'focusout') {
    		if (v == '') {
    			j$(this).val(ht);
    		}
    	}
    	
    });
    
    //Naming form
    j$('#hhNaming input[type="checkbox"]').change(gwManageHH.setNaming);
    j$('#hhNaming input[type="text"]').not('.gwField-disable').change(gwManageHH.setNaming);
    
    // Address change scripts
    //Accordions
    j$('.hhAddress-change').button();
    j$('.hhAddress-change').click(gwManageHH.dialogChangeAddress);


	// Handle the copy address to contacts flag.
	// this uses the useAddress boolean state value to decide whether to copy all values to the
   j$('#hhCopyAddressToContacts').change(function() {
	    gwManageHH.state.useAddress = j$(this).is(':checked');
	    gwManageHH.renderMembers();
	    if (gwManageHH.jsonState != gwManageHH.originalState) j$('.gwNotifications').gwNotify(gwManageHH.lblChangesNotSaved, {type:'info'});
    });
    
    // All Exclude from naming checkbox actions
    // Attaches event handler to all naming exclusion checkboxes on contact cards and writes any changes to the contact object
    j$('.hh-name-exclude-input, .hh-formal-exclude-input, .hh-informal-exclude-input').live('change', function (event) {
    	var card = j$(this).closest('.hhCard');
    	
    	/* NPSP uses a multiselect picklist for naming exclusions which is rendered as checkboxes for 3 possible values here
		 * the below code takes the checkboxes values populates the picklist text values accordingly */
		 
    	//build naming exclusions field
    	var namingExclusions = '';
    	if(card.find('.hh-name-exclude-input').is(':checked'))
    		namingExclusions += 'Household__c.Name;';
    	if(card.find('.hh-formal-exclude-input').is(':checked'))
			namingExclusions += 'Household__c.Formal_Greeting__c;';
		if(card.find('.hh-informal-exclude-input').is(':checked'))
    		namingExclusions += 'Household__c.Informal_Greeting__c;';
    	
    	if (!card.hasClass('hhCard-new')) {
    		var cid = card.attr('id');
    	
    		gwManageHH.setNameExclusion(cid, namingExclusions);
    	} else {
    		var contact = card.data('contact');
    		contact[fldNm('Naming_Exclusions__c')] = namingExclusions;
    		card.data('contact',contact);
    		gwManageHH.setInsertContacts();
    		gwManageHH.setNaming();
    	}
    });
    
    // Controls
    j$('.gwControls-save').click(function () {
    	gwManageHH.saveHousehold();
    	gwManageHH.renderControls(false);
    	return false;
    });
    
    
    // Cards
    j$('.hhCard').live('mouseenter mouseleave', function (event) {
    	if (event.type == 'mouseenter') {
    		j$(this).find('.hhCard-delete').show();
    	}
    	if (event.type == 'mouseleave') {
    		j$(this).find('.hhCard-delete').hide();
    	}
    });
    
    // Add new
    j$('.hhAddNew').live('click', function (event) {
    	gwManageHH.addNewContact();
    	return false;
    });
    
    //Deletes
    j$('.hhCard-delete').live('mouseenter mouseleave click', function (event) {
    	if (event.type == 'click') {
	    	var card = j$(this).closest('.hhCard');
	    	var isNew = card.hasClass('hhCard-new');
	    	if (!isNew) {
	    		var cid = card.attr('id');
	    		
	    		gwManageHH.removeMember(cid);
	    	} else {
	    		card.remove();
	    		gwManageHH.setInsertContacts();
	    		gwManageHH.setNaming();
	    	}
	    }
	    
	    if (event.type == 'mouseenter') {
	    	j$(this).addClass('ui-state-error');
	    }
	    if (event.type == 'mouseleave') {
	    	j$(this).removeClass('ui-state-error');
	    } 
    	
    });
    
    j$('.hhAddNew').button();
    
    // Render all
    gwManageHH.updateState(gwManageHH.jsonState);
    
});



gwManageHH.cardPrototype = '<div class="hhCard"><div class="hhCard-icon">' + gwManageHH.lblContact + '</div><div class="hhCard-details"><span class="hhCard-details-name"/><span class="hhCard-details-address"/><div class="gwDiscreet">' + gwManageHH.lblExcludeText + '<br/><span class="hh-name-exclude"><input class="gwPreventUI hh-name-exclude-input" type="checkbox"/><label>' + gwManageHH.lblExcludeName + '</label></span><br/><span class="hh-formal-exclude"><input class="gwPreventUI hh-formal-exclude-input" type="checkbox"/><label>' + gwManageHH.lblExcludeFormal + '</label></span><br/><span class="hh-informal-exclude"><input class="gwPreventUI hh-informal-exclude-input" type="checkbox"/><label>' + gwManageHH.lblExcludeInformal + '</label></span></div></div></div>';
gwManageHH.renderCard = function(contact) {
	
	var card = j$(gwManageHH.cardPrototype);
	
	// Insert name
	var cLink = j$('<a />');
	cLink.attr('href', '/'+contact.Id).attr('target','_blank').text(contact.Name);
	card.find('.hhCard-details-name').html(cLink);
	
	// Insert address
	card.find('.hhCard-details-address').html(gwManageHH.addressStringify(contact));

	/* NPSP uses a multiselect picklist for naming exclusions which is rendered as checkboxes for 3 possible values here
	 * the below code takes the text values in the picklist and renders the checkboxes accordingly */
	 
	var namingExclusions = contact[fldNm('Naming_Exclusions__c')];
	
	if (namingExclusions != null && namingExclusions.indexOf('Household__c.Name') != -1) {
		card.find('.hh-name-exclude').find('input').attr('checked','checked');
	} else {
		card.find('.hh-name-exclude').find('input').removeAttr('checked');
	}
	
	if (namingExclusions != null && namingExclusions.indexOf('Household__c.Formal_Greeting__c') != -1) {
		card.find('.hh-formal-exclude').find('input').attr('checked','checked');
	} else {
		card.find('.hh-formal-exclude').find('input').removeAttr('checked');
	}
	
	if (namingExclusions != null && namingExclusions.indexOf('Household__c.Informal_Greeting__c') != -1) {
		card.find('.hh-informal-exclude').find('input').attr('checked','checked');
	} else {
		card.find('.hh-informal-exclude').find('input').removeAttr('checked');
	}

	//each time we render a card order it
	contact[fldNm('Household_Naming_Order__c')] = j$('div.hhCard').size();
	
	// Add delete
	var del = j$('<button class="gwPreventUI hhCard-delete" />');
	del.button({
		icons: {
        	primary: "ui-icon-circle-minus"
        },
        text: false
    });
    del.hide();
	card.append(del);
	
	// Give card unique ID
	card.attr('id',contact.Id);
	
	//Hide the card
	card.css('opacity','0.0');
	
	// Add card to dom
	j$('div.hhMembers').append(card);
	
	// Blingify it
	card.animate({opacity: 1.0}, 500);
}

gwManageHH.updateCard = function (contact) {
	
	var card = j$('#'+contact.Id);
	
	var exAddy = card.find('.hhCard-details-address').html();
	var newAddy = gwManageHH.addressStringify(contact);
	
	if (exAddy != newAddy) {
		card.find('.hhCard-details-address').html(newAddy);
	}
	
	/* NPSP uses a multiselect picklist for naming exclusions which is rendered as checkboxes for 3 possible values here
	 * the below code takes the text values in the picklist and renders the checkboxes accordingly */
	  
	var namingExclusions = contact[fldNm('Naming_Exclusions__c')];
		
	if (namingExclusions != null && namingExclusions.indexOf('Household__c.Name') != -1) {
		card.find('.hh-name-exclude').find('input').attr('checked','checked');
	} else {
		card.find('.hh-name-exclude').find('input').removeAttr('checked');
	}
		
	if (namingExclusions != null && namingExclusions.indexOf('Household__c.Formal_Greeting__c') != -1) {
		card.find('.hh-formal-exclude').find('input').attr('checked','checked');
	} else {
		card.find('.hh-formal-exclude').find('input').removeAttr('checked');
	}
		
	if (namingExclusions != null && namingExclusions.indexOf('Household__c.Informal_Greeting__c') != -1) {
		card.find('.hh-informal-exclude').find('input').attr('checked','checked');
	} else {
		card.find('.hh-informal-exclude').find('input').removeAttr('checked');
	}
}
gwManageHH.addNewContact = function () {
	var card = j$(gwManageHH.cardPrototype);
	
	card.addClass('hhCard-new');
	
	card.find('.hhCard-details-address').remove();
	card.find('.hhCard-details-name').remove();
	
	var firstN = j$('<input class="gwTitleLabel hhCard-new-input" name="FirstName" title="' + gwManageHH.lblContactFirstName + '" type="text" />');
	var lastN = j$('<input class="gwTitleLabel hhCard-new-input" name="LastName" title="' + gwManageHH.lblContactLastName + '" type="text" />');
	
	var err = j$('<span class="hhCard-error" />');
	err.text(gwManageHH.lblContactNameRqd);
	
	var cardD = card.find('.hhCard-details');
	
	cardD.prepend(err);
	cardD.prepend(lastN);
	cardD.prepend(firstN);
	firstN.val(firstN.attr('title'));
	lastN.val(lastN.attr('title'));
	
	// Add delete
	var del = j$('<button class="gwPreventUI hhCard-delete" />');
	del.button({
		icons: {
        	primary: "ui-icon-circle-minus"
        },
        text: false
    });
    del.hide();
	card.append(del);
	
	var cntct = {"FirstName": "", "LastName": ""};
	cntct[fldNm('Naming_Exclusions__c')] = '';
	//last contact in naming order by default
	cntct[fldNm('Household_Naming_Order__c')] = j$('div.hhCard').size();
	card.data('contact',cntct);
	
	//hide the "no household members" message
	j$('.hhMembers-none').addClass('gwHidden');
	
	firstN.focus(function () {
		var obj = j$(this);
		var nm = obj.attr('name');
		if (nm == 'FirstName') obj.removeClass('gwTitleLabel');
	});
	
	card.find('.hhCard-new-input').change( function () {
		var obj = j$(this);
		var hhcard = obj.closest('.hhCard-new');
		var contact = hhcard.data('contact');
		
		var nm = obj.attr('name');
		
		contact[nm] = obj.val();
		if (contact.LastName == '' || contact.LastName == gwManageHH.lblContactLastName) {
			hhcard.find('.hhCard-error').text(gwManageHH.lblContactLastNameRqd);
		} else {
			hhcard.find('.hhCard-error').text('');
			hhcard.find('.hhCard-error').text(gwManageHH.lblContactCreatedOnSave);
			gwManageHH.setInsertContacts();
			gwManageHH.setNaming();
		}
		hhcard.data('contact',contact);
	});
	
	j$('div.hhMembers').append(card);
}

gwManageHH.updateState = function (state) {
	// First set the state variables
	gwManageHH.state = JSON.parse(state);
	gwManageHH.jsonState = state;
	
	// Now update the interface
	gwManageHH.renderMembers();
	
	// Set the address
	gwManageHH.renderAddress();
	
	// Setup the naming fields
	gwManageHH.renderNaming();
	
	// Controls
	gwManageHH.renderControls(true);
}
gwManageHH.dialogAddMember = function(event) {
	var linode = j$(this).closest('.hhSearch-item');
	var contact = linode.data('item.autocomplete').contact;
	
	if (contact[fldNm('Household__c')] != null) {
		var dialog = j$('#dialog-addmember');
		dialog.data('contact',contact);
		
		// Tweak the text of the dialog to show the contact's name
		dialog.find('#dialog-addmember-name').text(contact.Name);
		
		//merge button labels
		var btns = {};
		btns[gwManageHH.lblAddAndMerge] = function() {
					j$( this ).dialog( "close" );
					j$(this).fadeOut();
					linode.toggleClass('hhSearch-item-added');
					gwManageHH.addMember(contact.Id, true);
				};
		btns[gwManageHH.lblAddAndRemove] = function() {
					j$( this ).dialog( "close" );
					j$(this).fadeOut();
					linode.toggleClass('hhSearch-item-added');
					gwManageHH.addMember(contact.Id, false);
				};
		btns[gwManageHH.lblCancel] = function() {
					j$( this ).dialog( "close" );
				};
		dialog.dialog({
			buttons: btns});
	} else {
		linode.toggleClass('hhSearch-item-added');
		j$(this).fadeOut();
		gwManageHH.addMember(contact.Id, false);
	}
}

gwManageHH.dialogChangeAddress = function (event) {
	
	var state = gwManageHH.state;
	var dialog = j$('#dialog-changeaddress');
	var displayAddresses = new Array();
	
	if (state.viableAddresses != {}) {
		var addrs = new Array();
		// Get viable addresses if there are any and build an array of address strings
		for (as in state.viableAddresses) {
			addrs.push(state.viableAddresses[as]);
		}
		
		var adList = j$('<ul />');
		j$(addrs).each( function () {
			var adItem = j$('<li />');
			displayAddresses.push(gwManageHH.addressStringify(this));
			adItem.addClass('hhAddress-list-item').html(gwManageHH.addressStringify(this));
			
			adItem.data('cid',this.AddressKey);
			
			if (state.activeAddressKey != null && this.AddressKey == state.activeAddressKey) adItem.addClass('selected');
			
			adItem.bind('mouseover mouseout click', function(event) {
		    	switch (event.type) {
		    		case 'mouseover':
		    			j$(this).addClass('hhAddress-list-item-hover');
		    			break;
		    		case 'mouseout':
		    			j$(this).removeClass('hhAddress-list-item-hover');
		    			break;
		    		case 'click':
		    			j$('.hhAddress-list-item.selected').removeClass('selected');
		    			j$(this).addClass('selected');
		    			break;
		    	}
		    });
			
			adList.append(adItem);
		});
		
		j$('#hhExistingAddress').html(adList);
		
	}
	
	
	//merge button labels
	var btns = {};
	btns[gwManageHH.lblSetAddress] = function() {
				
				var createNew = j$('#hhExistingAddress').hasClass('ui-accordion-content-active');
				
				if (!createNew) {
					state.activeAddressKey = null;
					state.activeStreet = j$('#hhAddressStreet').val();
					state.activeCity = j$('#hhAddressCity').val();
					state.activePostalCode = j$('#hhAddressPostalCode').val();
					state.activeState = j$('#hhAddressState').val();
					state.activeCountry = j$('#hhAddressCountry').val();
				} else {
					
					var cid = j$('#hhExistingAddress li.selected').data('cid');
					
					if (cid != undefined) {
						var addr = state.viableAddresses[cid];
						
						state.activeAddressKey = addr.AddressKey;
						state.activeStreet = addr.MailingStreet;
						state.activeCity = addr.MailingCity;
						state.activePostalCode = addr.MailingPostalCode;
						state.activeState = addr.MailingState;
						state.activeCountry = addr.MailingCountry;
					}
				}
				
				//write the address back to the household fields to use vanilla save functionality
				j$('.sfField-MailingStreet__c').val(state.activeStreet);
				j$('.sfField-MailingCity__c').val(state.activeCity);
				j$('.sfField-MailingPostalCode__c').val(state.activePostalCode);
				j$('.sfField-MailingState__c').val(state.activeState);
				j$('.sfField-MailingCountry__c').val(state.activeCountry);
				
				gwManageHH.updateState(JSON.stringify(state));
				if (gwManageHH.jsonState != gwManageHH.originalState) j$('.gwNotifications').gwNotify(gwManageHH.lblChangesNotSaved, {type:'info'});
				j$( this ).dialog( "close" );
			};
	btns[gwManageHH.lblCancel] = function() {
				j$( this ).dialog( "close" );
			};
	dialog.dialog({
			modal: true,
			buttons: btns
		});
	var icons = {
		header: "ui-icon-bullet",
		headerSelected: "ui-icon-circle-check"
	};
	
	if(state.activeAddressKey == null)
		dialog.find('.gwAccordion').accordion({height: 350, icons: icons, active : 1});
	else
		dialog.find('.gwAccordion').accordion({height: 350, icons: icons});
}

gwManageHH.addMember = function (cid, wHH) {
	
	gwManageHH.jsonState = JSON.stringify(gwManageHH.state);
	
	j$('.gwPreventUI').gwsetui('disable');
	
	pgController.remoteAddMember(cid, wHH, gwManageHH.jsonState, function(result, event) {       								
     	if (event.type == 'exception') {
     		j$('.gwNotifications').gwNotify(event.message, {type:'alert'});
     	} else {
     		if (result != null) {
     			gwManageHH.updateState(result);
     			if (gwManageHH.jsonState != gwManageHH.originalState) j$('.gwNotifications').gwNotify(gwManageHH.lblChangesNotSaved, {type:'info'});
      		}
     	}
     	j$('.gwPreventUI').gwsetui('enable');
	}, {escape:false});
	
}

gwManageHH.setInsertContacts = function () {
	var state = gwManageHH.state;
	var cards = j$('.hhCard-new');
	
	state.insertContacts = new Array();
	cards.each(function () {
		var obj = j$(this);
		var contact = obj.data('contact');
		if (contact.LastName != '' && contact.LastName != 'Last Name') {
			state.insertContacts.push(contact);
		}
	});
}

gwManageHH.removeMember = function (cid) {
	
	gwManageHH.jsonState = JSON.stringify(gwManageHH.state);
	
	j$('.gwPreventUI').gwsetui('disable');
	
	pgController.remoteRemoveMember(cid, gwManageHH.jsonState, function(result, event) {
		       								
     	if (event.type == 'exception') {
     		j$('.gwNotifications').gwNotify(event.message, {type:'alert'});
     	} else {
     		if (result != null) {
     			gwManageHH.updateState(result);
     			if (gwManageHH.jsonState != gwManageHH.originalState) j$('.gwNotifications').gwNotify(gwManageHH.lblChangesNotSaved, {type:'info'});
      		}
     	}
     	j$('.gwPreventUI').gwsetui('enable');
	}, {escape:false});
	
}

gwManageHH.setNameExclusion = function (cid, ex) {
	
	gwManageHH.state.activeContacts[cid][fldNm('Naming_Exclusions__c')] = ex;
	
	gwManageHH.jsonState = JSON.stringify(gwManageHH.state);
	
	gwManageHH.setNaming();
}

/* Takes the values entered in the UI in relation to household naming and passes them to the remoteaction
endpoint for the state to be updated */

gwManageHH.setNaming = function () {
	
	var aN = j$('#hhAutoName:checked').length > 0 ? true : false;
	var nN = j$('#hhName').val();
	
	var aFG = j$('#hhAutoFormalGreeting:checked').length > 0 ? true : false;
	var nFG = j$('#hhFormalGreeting').val();
	
	var aIG = j$('#hhAutoInformalGreeting:checked').length > 0 ? true : false;
	var nIG = j$('#hhInformalGreeting').val();
	
	gwManageHH.jsonState = JSON.stringify(gwManageHH.state);
	
	j$('.gwPreventUI').gwsetui('disable');
	
	pgController.remoteSetNaming(aN, nN, aFG, nFG, aIG, nIG, gwManageHH.jsonState, function(result, event) {
		if (event.type == 'exception') {
     		j$('.gwNotifications').gwNotify(event.message, {type:'alert'});
     	} else {
     		if (result != null) {
     			gwManageHH.updateState(result);
     			if (gwManageHH.jsonState != gwManageHH.originalState) j$('.gwNotifications').gwNotify(gwManageHH.lblChangesNotSaved, {type:'info'});
      		}
     	}
     	j$('.gwPreventUI').gwsetui('enable');
	}, {escape: false});
}

gwManageHH.saveHousehold = function () {
	
	gwManageHH.jsonState = JSON.stringify(gwManageHH.state);
	
	j$('.gwPreventUI').gwsetui('disable');
	
	var newHH = gwManageHH.state.hh.Id == null;
	
	pgController.remoteSave(gwManageHH.jsonState, function(result, event) {
		if (event.type == 'exception') {
     		j$('.gwNotifications').gwNotify(event.message, {type:'alert'});
     	} else {
     		if (result != null) {
     			gwManageHH.updateState(result);
     			gwManageHH.originalState = JSON.stringify(gwManageHH.state);
     			
     			//j$('.gwNotifications').gwNotify('Changes saved.', {type:'success'});
     			if (!newHH) {
	     			j$('input[id$="gwControls-StdSave"]').click();
	     		} else {
	     			window.location = '/'+gwManageHH.state.hh.Id;
	     		}
      		}
     	}
     	j$('.gwPreventUI').gwsetui('enable');
	}, {escape: false});
}


gwManageHH.renderMembers = function() {
	
	var members = gwManageHH.getHHMembers();
	var cids = new Array();

	//sort members by household naming order
	//members.sort(function(a,b){return a.Household_Naming_Order__c === null ? 1 : b.Household_Naming_Order__c === null ? -1 : (a.Household_Naming_Order__c > b.Household_Naming_Order__c) ? 1 : ((b.Household_Naming_Order__c > a.Household_Naming_Order__c) ? -1 : 0);} );
    members.sort(function(a,b){return a[fldNm('Household_Naming_Order__c')] === null ? 1 : b[fldNm('Household_Naming_Order__c')] === null ? -1 : (a[fldNm('Household_Naming_Order__c')] > b[fldNm('Household_Naming_Order__c')]) ? 1 : ((b[fldNm('Household_Naming_Order__c')] > a[fldNm('Household_Naming_Order__c')]) ? -1 : 0);} );
	
	// Add or update cards
	j$(members).each( function (i) {
		
		var hasCard = j$('#'+members[i].Id);
		cids.push(members[i].Id);
		
		if (hasCard.length < 1) {
			gwManageHH.renderCard(members[i]);
		} else {
			gwManageHH.updateCard(members[i]);
		}
				
	});
	
	
	j$('.hhCard').not('.hhCard-new').each(function () {
		var cid = j$(this).attr('id');
		
		// Check to see if the address is different from the active address
		if(gwManageHH.state.useAddress == false || gwManageHH.compareAddress(cid) === true)
			j$(this).find('.hhCard-details-address').removeClass('gwModified');
		else
			j$(this).find('.hhCard-details-address').addClass('gwModified');

		
		// Cleanup any cards that were removed
		if (j$.inArray(cid, cids) < 0) {
			j$(this).hide().remove();
		}
	});
	if (members.length > 0) {
		j$('.hhMembers-none').addClass('gwHidden');
	} else {
		j$('.hhMembers-none').removeClass('gwHidden');
	}
}

gwManageHH.compareAddress = function (cid) {
	var state, contact, activeCon, newAddress, oldAddress;
	
	state = gwManageHH.state;
	contact = state.activeContacts[cid];
	
	if (contact != null && contact != undefined && (state.activeAddressKey != null || state.useAddress == true)) {
		if (state.useAddress === true) {
			activeCon = {
				MailingStreet: state.activeStreet,
				MailingCity: state.activeCity,
				MailingState: state.activeState,
				MailingPostalCode: state.activePostalCode,
				MailingCountry: state.activeCountry
				}
		} else {
			activeCon = state.viableAddresses[state.activeAddressKey];
		}
		
		newAddress = gwManageHH.addressStringify(contact);
		oldAddress = gwManageHH.addressStringify(activeCon);
		
		return newAddress == oldAddress;
	}
	return true;
}

gwManageHH.renderNaming = function () {
	var state = gwManageHH.state;
	var customNaming ='';
	
	j$('.sfField-Name').val(state.hhName);
	j$('.sfField-Formal_Greeting__c').val(state.hhFormalGreeting);
	j$('.sfField-Informal_Greeting__c').val(state.hhInformalGreeting);
	
	if (state.autoName == true) {
		
		j$('#hhAutoName').attr('checked','checked');
		
		j$('#hhName').val(state.hhName);
		j$('#hhName').attr('disabled','disabled').addClass('gwField-disabled', 200);
		
	} else {
		j$('#hhAutoName').removeAttr('checked');
		
		j$('#hhName').val(state.hhName);
		j$('#hhName').removeAttr('disabled').removeClass('gwField-disabled', 200);
		
		customNaming += 'Household__c.Name;';
		
	}
	
	if (state.autoFormalGreeting == true) {
		j$('#hhAutoFormalGreeting').attr('checked','checked');
		
		j$('#hhFormalGreeting').val(state.hhFormalGreeting);
		j$('#hhFormalGreeting').attr('disabled','disabled').addClass('gwField-disabled', 200);
		
	} else {
		j$('#hhAutoFormalGreeting').removeAttr('checked');
		
		j$('#hhFormalGreeting').val(state.hhFormalGreeting);
		j$('#hhFormalGreeting').removeAttr('disabled').removeClass('gwField-disabled', 200);
		
		customNaming += 'Household__c.Formal_Greeting__c;';
	}
	
	if (state.autoInformalGreeting == true) {
		j$('#hhAutoInformalGreeting').attr('checked','checked');
		
		j$('#hhInformalGreeting').val(state.hhInformalGreeting);
		j$('#hhInformalGreeting').attr('disabled','disabled').addClass('gwField-disabled', 200);
		
	} else {
		j$('#hhAutoInformalGreeting').removeAttr('checked');
		
		j$('#hhInformalGreeting').val(state.hhInformalGreeting);
		j$('#hhInformalGreeting').removeAttr('disabled').removeClass('gwField-disabled', 200);
		
		customNaming += 'Household__c.Informal_Greeting__c;';
	}
	
	j$('.sfField-SYSTEM_CUSTOM_NAMING__c').val(customNaming);
}

gwManageHH.renderControls = function (enabled) {
	var hasContacts = j$('.hhCard').length > 0;
	var inpts = j$('.gwControls input[type="submit"]').not('.gwHidden');
	if (enabled) {
		inpts.removeAttr('disabled');
		inpts.css('opacity','1');
		if (!hasContacts) {
			j$('.gwControls-save').addClass('gwHidden');
			j$('input[id$="gwControls-StdSave"]').attr('disabled','disabled');
		} else {
			j$('.gwControls-save').removeClass('gwHidden');
			j$('input[id$="gwControls-StdSave"]').removeAttr('disabled','disabled');
		}
	} else {
		inpts.attr('disabled','disabled');
		inpts.css('opacity','0.5');
		inpts.val('Saving ...');
	}
}

gwManageHH.getHHMembers = function() {
	
	var members = new Array();
	var state = gwManageHH.state;
	
	var cmap = state.activeContacts;
	
	for (id in cmap) {
		members.push(cmap[id]);
	}
	return members;
}

gwManageHH.renderAddress = function () {
	
	//Helper for state
	var state = gwManageHH.state;
	
	var hasContacts = false;
	var addString = '';
	for (p in state.activeContacts) {
		hasContacts = true;
		break;
	}
	
	var addr;
	
	if (state.activeAddressKey == null) {
		addr = {
			MailingStreet: state.activeStreet,
			MailingCity: state.activeCity,
			MailingState: state.activeState,
			MailingPostalCode: state.activePostalCode,
			MailingCountry: state.activeCountry
		}
	} else {
		addr = state.viableAddresses[state.activeAddressKey];
	}
	
	// Primary address node in the DOM
	var prnode = j$('.hhAddress');
	
	var adnode = j$('.hhAddress-address');
	
	addString = gwManageHH.addressStringify(addr);
	
	//now use the useAddess flag to determine if address saved to contacts (always saved to household)
	if (state.useAddress == true)
		j$('#hhCopyAddressToContacts').attr('checked','checked');
	else
		j$('#hhCopyAddressToContacts').removeAttr('checked');
	
	adnode.html(addString);
	prnode.fadeIn(200);
	
}

gwManageHH.addressStringify = function(contact) {
	
	var address = new String();
	
	
	if (contact.MailingStreet != null && contact.MailingStreet != '') {
		var strt = contact.MailingStreet.replace(/(\r\n|\n|\r)/gm, '<br />');
		address += strt;
		if (contact.MailingCity != null ||
		    contact.MailingState != null ||
		    contact.MailingPostalCode != null ||
		    contact.MailingCountry != null) {
			address += '<br />';
		}
	}
	if (contact.MailingCity != null && contact.MailingCity != '') {
		address += contact.MailingCity + '<br />';
		//address += contact.MailingState != null && contact.MailingState != '' ? ', ' : '';
	}
	if (contact.MailingState != null && contact.MailingState != '') {
		address += contact.MailingState + '<br />';
	}
	if (contact.MailingPostalCode != null && contact.MailingPostalCode != '') {
		address += contact.MailingPostalCode + '<br />';
	}
	if (contact.MailingCountry != null && contact.MailingCountry != '') {
		address += contact.MailingCountry;
	}
	
	return address;
}

j$.fn.gwNotify = function (message, options) {
	
	var obj, config, cur_msg;
	
	obj = j$(this);
	
	cur_msg = obj.find('p.gwNotify-message').length > 0 ? obj.find('p.gwNotify-message').text() : '';
	
	if (cur_msg === message && message != '') return obj;
	
	if (message == undefined || message == '') {
		obj.fadeOut(200, function() {
			obj.find('p.gwNotify-message').text('');
		});
	}
	
	// set the default config
	config = j$.extend({
		type: 'info',
		icons: {
			info: 'ui-icon-info',
			alert: 'ui-icon-alert',
			success: 'ui-icon-check'
		},
		cssClass: {
			info: 'ui-state-highlight',
			alert: 'gwError',
			success: 'gwSuccess'
		}
	}, options);
	
	
	
	if (obj.is(':hidden')) obj.css('opacity','0').removeClass('gwHidden');
	
	if (obj.find('.gwNotify-message').length < 1) obj.html('<p class="gwNotify-message"></p>');
	
	/*if (obj.find('gwNotify-icon').length < 1) obj.prepend('<span class="gwNotify-icon"></span>');
	
	var ico = obj.find('.gwNotify-icon');
	ico.addClass(config.icons[config.type]);
	ico.addClass('ui-icon');
	obj.prepend(ico);*/
	
	obj.animate({opacity: 0}, 200, function() {
		// Insert message
		obj.find('p.gwNotify-message').text(message);
		// Apply classes
		obj.addClass('ui-corner-all');
		for (c in config.cssClass) {
			obj.removeClass( config.cssClass[c] );
		}
		obj.addClass( config.cssClass[config.type] );
		obj.animate({opacity: 1.0}, 200);
	});
	
	return obj;
}

j$.fn.gwsetui = function (options) {
	
	return j$(this).each(function () {
		var obj;
		
		obj = j$(this);
		
		if (options == 'enable') {
			
			if (obj.is('button')) {
				obj.button('enable').button('refresh');
			}
			if (obj.is('input')) {
				if (obj.is('.gwUIDisabled')) {
					obj.removeAttr('disabled');
				}
			}
			obj.removeClass('gwUIDisabled');
		}
		
		if (options == 'disable') {
			
			obj.not(':disabled').addClass('gwUIDisabled');
			
			if (obj.is('button')) {
				obj.button('disable').button('refresh');
			}
			if (obj.is('input')) {
				obj.attr('disabled','disabled');
			}
		}
		
		if (options == 'toggle') {
		
		}
	});
}