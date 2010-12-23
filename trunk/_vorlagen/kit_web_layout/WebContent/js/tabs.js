/**
 * 
 */
function show_tableiste_54(no) {
	// Alle DIVS mit der id 'rd_kit_tab_' + irgendwas
	alltabs = document.getElementById('content').getElementsByTagName('div');
	count = 0;
	tabs = new Array();
	for (i = 0; i < alltabs.length; i++) {
		if (alltabs[i].id.substr(0, tab_marker_54.length) == tab_marker_54) {
			tabs[count] = alltabs[i];
			count++;
		}
	}
	if (no >= count)
		no = 0;
	if (no < tabs.length) { // Falls im Tab_Container kein Tab definiert ist
		// Alle DIVS mit der id 'tableiste_li_' + irgendwas
		alltab_lis = document.getElementById('content').getElementsByTagName(
				'li');
		count = 0;
		tab_lis = new Array();
		for (i = 0; i < alltab_lis.length; i++) {
			if (alltab_lis[i].id.substr(0, tab_leiste_li_54.length) == tab_leiste_li_54) {
				tab_lis[count] = alltab_lis[i];
				count++;
			}
		}
		for (i = 0; i < tabs.length - 1; i++) {
			tabs[i].style.display = 'none';
			if (i % tabs_in_a_row_54 == (tabs_in_a_row_54 - 1))
				tab_lis[i].className = '';
			else
				tab_lis[i].className = 'inactive border_right';
		}
		tabs[tabs.length - 1].style.display = 'none';
		tab_lis[tabs.length - 1].className = '';
		if ((no == tabs.length - 1)
				|| (no % tabs_in_a_row_54 == (tabs_in_a_row_54 - 1)))
			tab_lis[no].className = 'active_last';
		else
			tab_lis[no].className = 'active border_right';
		tabs[no].style.display = 'block';
	}
}

function init_tabs_54() {
	// Alle DIVS mit der id 'rd_kit_tab_' + irgendwas finden.
	alltabs = document.getElementById('content').getElementsByTagName('div');
	count = 0;
	tabs = new Array();
	for (i = 0; i < alltabs.length; i++) {
		if (alltabs[i].id.substr(0, tab_marker_54.length) == tab_marker_54) {
			tabs[count] = alltabs[i];
			alltabs[i].className = 'tabborder';
			count++;
		}
	}
	inner = '';
	if (tabs.length > 0) {
		inner = '<ul class="tabs">';
		for (i = 0; i < tabs.length - 1; i++) {
			if (i % tabs_in_a_row_54 == (tabs_in_a_row_54 - 1))
				inner += '<li id="'
						+ tab_leiste_li_54
						+ i
						+ '" class=""><a href="javascript:void(0)" onclick="show_tableiste_54('
						+ i + '); blur();"><nobr>&nbsp;'
						+ document.getElementById(tab_titel_54 + i).innerHTML
						+ '&nbsp;</nobr></a></li>';
			else
				inner += '<li id="'
						+ tab_leiste_li_54
						+ i
						+ '" class="inactive border_right"><a href="javascript:void(0)" onclick="show_tableiste_54('
						+ i + '); blur();"><nobr>&nbsp;'
						+ document.getElementById(tab_titel_54 + i).innerHTML
						+ '&nbsp;</nobr></a></li>';
			tabs[i].style.display = 'none';
		}
		inner += '<li id="'
				+ tab_leiste_li_54
				+ (tabs.length - 1)
				+ '" class=""><a href="javascript:void(0)" onclick="show_tableiste_54('
				+ (tabs.length - 1)
				+ '); blur();"><nobr>&nbsp;'
				+ document.getElementById(tab_titel_54 + (tabs.length - 1)).innerHTML
				+ '&nbsp;</nobr></a></li>';
		tabs[tabs.length - 1].style.display = 'none';
		inner += '</ul>';
	}
	document.getElementById(tab_leiste_54).innerHTML = inner;
	show_tableiste_54(0);
}