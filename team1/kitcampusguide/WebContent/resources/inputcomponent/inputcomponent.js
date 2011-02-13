function getTranslation(label) {
	return document.getElementById(label).innerHTML;
};

function setSearchButtonLabel() {
	var routeFromField = document.getElementById('routeFromField');
	var routeToField = document.getElementById('routeToField');
	var label;
	if ((routeFromField == null || (routeFromField.value != null && routeFromField.value != ''))
		&& (routeToField == null || (routeToField.value != null && routeToField.value != ''))) {
			label = getTranslation("calculateRouteLabel");
	} else {
		label = getTranslation("searchLabel");
	}
	document.getElementById('searchButton').value = label;
}