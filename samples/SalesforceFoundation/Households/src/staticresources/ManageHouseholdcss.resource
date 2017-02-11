/*
Written by Ryan Foster, copyright (c) 2011 Groundwire
This program is released under the GNU Affero General Public License, Version 3. http://www.gnu.org/licenses/
*/

.ui-autocomplete {
	max-height: 300px;
	width: 290px;
	overflow-y: auto;
	padding: 0.4em;
	/* prevent horizontal scrollbar */
	overflow-x: hidden;
	/* add padding to account for vertical scrollbar */
	background-color: rgba(255,255,255,0.8);
}



.gwNotifications {
	position: relative;
	font-size: 1.1em;
	padding: 7px 15px;
	margin-bottom: 18px;
	display:inline-block;
	color: #404040;
	background-color: #EEDC94;
	background-repeat: repeat-x;
	background-image: -khtml-gradient(linear, left top, left bottom, from(#fceec1), to(#eedc94));
	background-image: -moz-linear-gradient(top, #fceec1, #eedc94);
	background-image: -ms-linear-gradient(top, #fceec1, #eedc94);
	background-image: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #fceec1), color-stop(100%, #eedc94));
	background-image: -webkit-linear-gradient(top, #fceec1, #eedc94);
	background-image: -o-linear-gradient(top, #fceec1, #eedc94);
	background-image: linear-gradient(top, #fceec1, #eedc94);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#fceec1', endColorstr='#eedc94', GradientType=0);
	text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
	border-color: #EEDC94 #EEDC94 #E4C652;
	border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
	text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);
	border-width: 1px;
	border-style: solid;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
	-webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.25);
	-moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.25);
	box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.25);
	max-width: 540px;
}
.gwNotifications p {
	margin: 0.2em 0;
	padding: 0;
}
.gwNotifications .ui-icon {
	float: left;
	margin-left: -20px;
}
.gwSuccess {
	color: white;
	background-color: #57A957;
	background-repeat: repeat-x;
	background-image: -khtml-gradient(linear, left top, left bottom, from(#62c462), to(#57a957));
	background-image: -moz-linear-gradient(top, #62c462, #57a957);
	background-image: -ms-linear-gradient(top, #62c462, #57a957);
	background-image: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #62c462), color-stop(100%, #57a957));
	background-image: -webkit-linear-gradient(top, #62c462, #57a957);
	background-image: -o-linear-gradient(top, #62c462, #57a957);
	background-image: linear-gradient(top, #62c462, #57a957);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#62c462', endColorstr='#57a957', GradientType=0);
	text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
	border-color: #57A957 #57A957 #3D773D;
	border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
}
.gwError {
	color: white;
	background-color: #C43C35;
	background-repeat: repeat-x;
	background-image: -khtml-gradient(linear, left top, left bottom, from(#ee5f5b), to(#c43c35));
	background-image: -moz-linear-gradient(top, #ee5f5b, #c43c35);
	background-image: -ms-linear-gradient(top, #ee5f5b, #c43c35);
	background-image: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #ee5f5b), color-stop(100%, #c43c35));
	background-image: -webkit-linear-gradient(top, #ee5f5b, #c43c35);
	background-image: -o-linear-gradient(top, #ee5f5b, #c43c35);
	background-image: linear-gradient(top, #ee5f5b, #c43c35);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ee5f5b', endColorstr='#c43c35', GradientType=0);
	text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
	border-color: #C43C35 #C43C35 #882A25;
	border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
}

.gwHidden {
	display: none;
}

.gwSection {
    background-color: #F8F8F8;
    border: 1px solid #fff;
    margin: 0.5em 0 1em 0;
    
    border-radius: 8px;
    box-shadow: 0px 0px 2px rgba(0,0,0,0.5);
}

.gwSection-header {
    border-bottom: 1px solid #DBDBDB;
    display: block;
    padding: 1em;
}

.gwSection-title {
    font-size: 1.25em;
}

.gwSection-content {
    padding: 1em;
}

.gwDialog-hidden {
	display: none;
}

.gwDiscreet {
	font-size: 0.9em;
	color: #444;
}

.hhCard {
	display: inline-block;
	width: 180px;
	position: relative;
	border: 1px solid #fff;
	margin: 0 1em 1em 0;
	vertical-align: top;
	
	border-radius: 8px;
	box-shadow: 1px 1px 4px rgba(0,0,0,0.6);
	
	background-color: #fff;
	
	background-image: linear-gradient(bottom, rgb(255,255,255) 30%, rgb(230,230,255) 100%);
    background-image: -o-linear-gradient(bottom, rgb(255,255,255) 30%, rgb(230,230,255) 100%);
    background-image: -moz-linear-gradient(bottom, rgb(255,255,255) 30%, rgb(230,230,255) 100%);
    background-image: -webkit-linear-gradient(bottom, rgb(255,255,255) 30%, rgb(230,230,255) 100%);
    background-image: -ms-linear-gradient(bottom, rgb(255,255,255) 30%, rgb(230,230,255) 100%);
    
}
.hhCard-prototype {
	display: none;
}
.hhCard-new {
	background-color: rgb(230,255,230) ;
	
	background-image: linear-gradient(bottom, rgb(255,255,255) 30%, rgb(230,255,230) 100%);
    background-image: -o-linear-gradient(bottom, rgb(255,255,255) 30%, rgb(230,255,230) 100%);
    background-image: -moz-linear-gradient(bottom, rgb(255,255,255) 30%, rgb(230,255,230) 100%);
    background-image: -webkit-linear-gradient(bottom, rgb(255,255,255) 30%, rgb(230,255,230) 100%);
    background-image: -ms-linear-gradient(bottom, rgb(255,255,255) 30%, rgb(230,255,230) 100%);

}
.hhCard-new-input {
	width: 115px;
	opacity: 0.85;
	margin-bottom: 2px;
}
.hhCard-new-input:focus {
	opacity: 1;
}
.hhCard-error {
	font-size: 0.9em;
	font-color: #666;
}
.hhCard-icon {
	display: block;
	width: 32px;
	height: 23px;
	background: transparent url('/img/sprites/master.png') no-repeat left -503px;
	position: absolute;
	font-size: 1px;
	text-indent: -9999px;
	overflow: hidden;
	margin: 0px;
	left: 5px;
	top: 5px;
}

.hhCard-details {
	margin: 5px 5px 5px 40px;
	line-height: 1.4em;
	display: inline-block;
}


.hhCard-details-name {
	font-size: 1.1em;
	font-weight: bold;
	display:block;
	word-wrap: break-word;
	width: 130px;
}
.hhCard-details-address {

}

button.hhCard-delete {
	border: none;
	background: transparent;
	position: absolute;
	right: 0;
	top: 5px;
	width: 15px;
}

.hhSearch {
	width: 540px;
	float: right;
	text-align: right;
}
.hhSearch label {
	font-weight: bold;
}
.hhSearch-input {
	border-radius: 5px;
	border: 1px solid #999;
	font-size: 1.05em;
	width: 200px;
	color: #777;
	padding: 0.4em 0em 0.3em 0.3em;
}
.hhSearch-input:focus {
	color: black;
	border: 1px solid #333;
}

.hhSearch ul {
	width: 340px;
}
#hhExistingAddress ul,
#hhExistingAddress li {
	list-style: none;
	padding: 0;
	margin: 0;
}

.hhSearch-item {
	padding: 0.5em 5em 0.5em 3em;
	margin: 0 0 0.5em 0;
	position: relative;
	font-size: 0.9em;
	min-height: 2em;
}

#hhExistingAddress li {
	padding: 0.5em;
	margin-bottom: 0.5em;
	border-bottom: 1px solid #d8d8d8;
}
.hhAddress-list-item-hover {
	background-color: #b8d8f8;
}
#hhExistingAddress li.selected {
	background-color: #1797C0;
	color: white;
}

.hhSearch-item-added {
	background-color: rgba(239,139,62,0.4);
}
.hhSearch-item-name {
	font-weight: bold;
}
.hhSearch-item-address {
	font-size: 0.9em;
	color: #777;
	display: block;
}
.hhSearch-item-add {
	position: absolute;
	right: 0.5em;
	top: 1em;
	font-size: 0.9em;
}
.hhSearch-item-hh {
	color: #AC6207;
	font-size: 0.9em;
	display: block;
	height: 20px;
	line-height: 20px;
	padding-left: 20px;
	background: transparent url('/img/sprites/master.png') no-repeat left -260px;
}

.hhAddress {
	margin-top: 1em;
	width: 50%;
	min-width: 420px;
	max-width: 500px;
}
.hhAddress-title {
	color: #666;
	font-size: 1em;
	padding-left: 0.5em;
}
.hhAddress-content {
	background-color: #fff;
	border: 2px solid #ccc;
	padding: 0.8em 200px 0.8em 1em;
	border-radius: 8px;
	position: relative;
	min-height: 2em;
}
.hhAddress-details {
	border-right: 1px solid #d8d8d8;
	padding: 0 6em 0 0;
	position: relative;
	min-height: 2em;
}
.hhAddress-change {
	position: absolute;
	right: 1em;
	top: 0;
}
.hhAddress-help {
	display: block;
	width: 190px;
	position: absolute;
	right: 0;
	top: 0;
}
.hh-name-exclude .hh-formal-exclude .hh-informal-exclude {
	margin-top: 0.4em;
	display:block;
	font-size: 0.9em;
	padding-left: 1.4em;
	line-height: 1.1em;
}
.hh-name-exclude .hh-formal-exclude .hh-informal-exclude input {
	margin-left: -1.4em;
}

.gwField {
	margin: 0.5em 0;
}
.gwField-label {
	font-weight: bold;
	display: inline-block;
	vertical-align: top;
	padding: 0.4em 0 0.3em 0.2em;
	width: 11em;
	font-size: 0.9em;
}
.gwField-input {
	opacity: 1.0;
	border: 1px solid #333;
	padding: 0.4em 0 0.3em 0.2em;
	vertical-align: middle;
}
textarea.gwField-input {
	resize: vertical;
}
.gwField-input-active {
	border-color: #EF8B3D;
	outline: none;
}
.gwField-disabled { 
	opacity: 0.8;
	border: 1px solid #777;
}

.gwButton {
	font-size: 0.9em;
	background-image: none;
	background-image: linear-gradient(bottom, rgb(220,220,220) 0%, rgb(255,255,255) 100%);
    background-image: -o-linear-gradient(bottom, rgb(220,220,220) 0%, rgb(255,255,255) 100%);
    background-image: -moz-linear-gradient(bottom, rgb(220,220,220) 0%, rgb(255,255,255) 100%);
    background-image: -webkit-linear-gradient(bottom, rgb(220,220,220) 0%, rgb(255,255,255) 100%);
    background-image: -ms-linear-gradient(bottom, rgb(220,220,220) 0%, rgb(255,255,255) 100%);
    border: 1px solid #777;
    border-bottom-color: #444;
    color: #222;
}

#hhNaming .gwField-input {
	width: 250px;
}


h3 {
	display: block;
}

#dialog-changeaddress .ui-accordion-content {
	
}
#hhExistingAddress.ui-accordion-content {
	padding-left: 1em;
	padding-right: 1em;
}

#hhNewAddress .gwField {
	font-size: 0.9em;
}
#hhNewAddress .gwField-label {
	width: 10em;
	font-weight: normal;
	margin-right: 1em;
}
#hhNewAddress .gwField-input {
	width: 14em;
}
#hhNewAddress textarea.gwField-input {
	display: inline-block;
}

.gwModified {
	font-style: italic;
	text-decoration: line-through;
}

.gwUIDisabled {
	
}

.hhMembers-none {
	text-align: center;
	font-size: 1.4em;
	color: #555;
	padding: 2em 4em;
	}

.gwControls {
text-align: center;
}
.gwClear { clear:both; }
