/**
 * 
 */
function getPageSize() {
	var xScroll, yScroll;

	if (window.innerHeight && window.scrollMaxY) {
		xScroll = window.innerWidth + window.scrollMaxX;
		yScroll = window.innerHeight + window.scrollMaxY;
	} else if (document.body.scrollHeight > document.body.offsetHeight) { // all
																			// but
																			// Explorer
																			// Mac
		xScroll = document.body.scrollWidth;
		yScroll = document.body.scrollHeight;
	} else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla
				// and Safari
		xScroll = document.body.offsetWidth;
		yScroll = document.body.offsetHeight;
	}

	var windowWidth, windowHeight;

	if (self.innerHeight) { // all except Explorer
		if (document.documentElement.clientWidth) {
			windowWidth = document.documentElement.clientWidth;
		} else {
			windowWidth = self.innerWidth;
		}
		windowHeight = self.innerHeight;
	} else if (document.documentElement
			&& document.documentElement.clientHeight) { // Explorer 6 Strict
														// Mode
		windowWidth = document.documentElement.clientWidth;
		windowHeight = document.documentElement.clientHeight;
	} else if (document.body) { // other Explorers
		windowWidth = document.body.clientWidth;
		windowHeight = document.body.clientHeight;
	}

	// for small pages with total height less then height of the viewport
	if (yScroll < windowHeight) {
		pageHeight = windowHeight;
	} else {
		pageHeight = yScroll;
	}

	// for small pages with total width less then width of the viewport
	if (xScroll < windowWidth) {
		pageWidth = xScroll;
	} else {
		pageWidth = windowWidth;
	}

	return [ pageWidth, pageHeight ];
}

function hide_vorhang(obj) {
	document.getElementById(obj).style.display = 'none';
	return false;
}

function show_vorhang(obj) {
	opactiy = 0.5;
	var xy = getPageSize();
	document.getElementById(obj).style.zIndex = 100;
	document.getElementById(obj).style.width = parseInt(xy[0]) + 'px';
	document.getElementById(obj).style.height = parseInt(xy[1]) + 'px';
	document.getElementById(obj).style.opacity = opactiy;
	document.getElementById(obj).style.MozOpacity = opactiy;
	document.getElementById(obj).style.filter = 'alpha(opacity='
			+ (opactiy * 100) + ')';
	document.getElementById(obj).style.display = 'block';
}

function show_feedbackform() {
	if (window.pageYOffset)
		document.getElementById('feed_back').style.top = parseInt(window.pageYOffset)
				+ 50 + 'px'
	else
		document.getElementById('feed_back').style.top = parseInt(document.documentElement.scrollTop)
				+ 50 + 'px';
	var xy = getPageSize();
	document.getElementById('feed_back').style.left = Math
			.floor((parseInt(xy[0]) - 722) / 2)
			+ 'px';
	document.getElementById('feed_back').style.display = 'block';
	document.getElementById('feed_back').style.zIndex = 102;
	return false;
}