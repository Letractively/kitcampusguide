/** The width of the campusguide */
var campusguideWidth = 760;

/** The height of the campusguide */
var campusguideHeight = 530;

/** The width of the sidebar */
var sidebarWidth = 200;

/** The height of the headline */
var headlineHeight = 30;

/** Holds if the campusguide is shown in fullscreen-mode or not */
var fullscreen = false;

/** Client ID */
var clientID;

/**
 * Initializes the CampusGuide and its map.
 */
function setup() {
	createCampusGuide(campusguideWidth, campusguideHeight, headlineHeight, sidebarWidth);
    createMap();
}

/**
 * Initializes the CampusGuide with given values.
 * @param width the width of the CampusGuide.
 * @param height the height of the CampusGuide.
 * @param headlineHeight the height of the headline of the CampusGuide.
 * @param sidebarWidth the width of the sidebar of the CampusGuide.
 */
function createCampusGuide(width, height, headlineHeight, sidebarWidth) {
	document.getElementById("campusguide").style.width = width +  "px";
	document.getElementById("campusguide").style.height = height +  "px";
	document.getElementById("headline").style.width = (width - 2) +  "px";
	document.getElementById("headline").style.height = (headlineHeight - 2) +  "px";
	document.getElementById("map").style.width = (width - sidebarWidth - 2) +  "px";
	document.getElementById("map").style.height = (height - headlineHeight - 1) +  "px";
	document.getElementById("sidebar").style.width = (sidebarWidth - 1) +  "px";
	document.getElementById("sidebar").style.height = (height - headlineHeight - 1) +  "px";
	document.getElementById("sidebar-pane").style.width = (sidebarWidth - 13) +  "px";
	document.getElementById("sidebar-pane").style.height = (height - headlineHeight - 11) +  "px";
	document.getElementById("show-sidebar").style.top = (headlineHeight + 5) +  "px";
	if (map) {
		map.updateSize();
	}
}

/**
 * Sets the current client ID.
 * 
 * @param id the new client ID
 */
function setClientID(id) {
    clientID = id;
}

/**
 * Parses the specified text into an object.
 * 
 * @param text JSON string to parse
 * @returns the Object represented by the specified text
 */
function createJSONObject(text) {
    return (text == "") ? null : JSON.parse(text);    
}

/**
 * Returns the DOM element with specified id.
 * 
 * @param id the id to search for
 * @returns the DOM element with specified id
 */
function getElement(id) {
    return document.getElementById(clientID + ":" + id);
}

/**
 * Simulates the in eventType specified user event on the given element.
 * 
 * @param eventType the event to simulate
 * @param element the element to simulate the event on
 */
function raiseEvent(eventType, element) { 
    if (document.createEvent) { 
        var evt = document.createEvent("Events"); 
        evt.initEvent(eventType, true, true); 
        element.dispatchEvent(evt); 
    } else if (document.createEventObject) {
        var evt = document.createEventObject(); 
        element.fireEvent('on' + eventType, evt); 
    } 
}

/**
 * Toggles the view of the map to full screen and back.
 */
function toggleFullscreen() {
	var campusguide = document.getElementById("campusguide");
	if (fullscreen) {
		campusguide.style.position = "relative";
		campusguide.style.left = "";
		campusguide.style.top = "";
		createCampusGuide(campusguideWidth, campusguideHeight, headlineHeight, sidebarWidth);
		map.minZoomLevel = 15;
	} else {
		campusguide.style.position = "fixed";
		campusguide.style.left = "0";
		campusguide.style.top = "0";
		createCampusGuide(window.innerWidth - 18, window.innerHeight, headlineHeight, (sidebarWidth * 1.25));
		map.minZoomLevel = 16;
	}
	fullscreen = !fullscreen;
}
