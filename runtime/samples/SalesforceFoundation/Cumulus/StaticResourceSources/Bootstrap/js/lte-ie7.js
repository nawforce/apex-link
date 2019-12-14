/* Load this script using conditional IE comments if you need to support IE 7 and IE 6. */

window.onload = function() {
	function addIcon(el, entity) {
		var html = el.innerHTML;
		el.innerHTML = '<span style="font-family: \'icon-utility\'">' + entity + '</span>' + html;
	}
	var icons = {
			'icon-utility-attach' : '&#xe000;',
			'icon-utility-bookmark' : '&#xe001;',
			'icon-utility-call' : '&#xe002;',
			'icon-utility-chat' : '&#xe003;',
			'icon-utility-checkin' : '&#xe004;',
			'icon-utility-chevron-left' : '&#xe005;',
			'icon-utility-chevron-right' : '&#xe006;',
			'icon-utility-close' : '&#xe007;',
			'icon-utility-down' : '&#xe008;',
			'icon-utility-download' : '&#xe009;',
			'icon-utility-email' : '&#xe00b;',
			'icon-utility-event' : '&#xe00c;',
			'icon-utility-favorite' : '&#xe00d;',
			'icon-utility-groups' : '&#xe00e;',
			'icon-utility-image' : '&#xe00f;',
			'icon-utility-left' : '&#xe010;',
			'icon-utility-like' : '&#xe011;',
			'icon-utility-link' : '&#xe012;',
			'icon-utility-list' : '&#xe013;',
			'icon-utility-location' : '&#xe014;',
			'icon-utility-logout' : '&#xe015;',
			'icon-utility-people' : '&#xe016;',
			'icon-utility-photo' : '&#xe017;',
			'icon-utility-reply' : '&#xe018;',
			'icon-utility-right' : '&#xe019;',
			'icon-utility-settings' : '&#xe01c;',
			'icon-utility-share' : '&#xe01d;',
			'icon-utility-up' : '&#xe01e;',
			'icon-utility-upload' : '&#xe01f;',
			'icon-utility-user' : '&#xe020;',
			'icon-utility-priority' : '&#xe021;',
			'icon-utility-filter' : '&#xe022;',
			'icon-utility-help' : '&#xe023;',
			'icon-utility-error' : '&#xe024;',
			'icon-utility-warning' : '&#xe025;',
			'icon-utility-adduser' : '&#xe026;',
			'icon-utility-add' : '&#xe027;',
			'icon-utility-edit' : '&#xe00a;',
			'icon-utility-back' : '&#xe028;',
			'icon-utility-lock' : '&#xe029;',
			'icon-utility-check' : '&#xe02a;',
			'icon-utility-notification' : '&#xe02c;',
			'icon-utility-info' : '&#xe02d;',
			'icon-utility-frozen' : '&#xe02e;',
			'icon-utility-spinner' : '&#xe02b;',
			'icon-utility-NewsHighlightAction' : '&#xe02f;',
			'icon-utility-dash' : '&#xe030;',
			'icon-utility-question' : '&#xe031;',
			'icon-utility-more-01' : '&#xe01a;',
			'icon-utility-rows' : '&#xe032;',
			'icon-utility-search' : '&#xe033;',
			'icon-utility-refresh' : '&#xe01b;',
			'icon-utility-socialshare' : '&#xe034;'
		},
		els = document.getElementsByTagName('*'),
		i, attr, html, c, el;
	for (i = 0; ; i += 1) {
		el = els[i];
		if(!el) {
			break;
		}
		attr = el.getAttribute('data-icon');
		if (attr) {
			addIcon(el, attr);
		}
		c = el.className;
		c = c.match(/icon-utility-[^\s'"]+/);
		if (c && icons[c[0]]) {
			addIcon(el, icons[c[0]]);
		}
	}
};