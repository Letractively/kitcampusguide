/**
 * Returns the time, in milliseconds, needed to show the sidebar.
 * 
 * @returns the time, in milliseconds, needed to show the sidebar
 */
function getSidebarDelay() {
    var sidebarLeft = parseInt(document.getElementById("sidebar").style.left.replace(/px/g, "")) + 1;
    return sidebarLeft * 2;
}

/**
 * Hides the sidebar.
 */
function hideSidebar() {
    var map = document.getElementById("map");
    var sidebar = document.getElementById("sidebar");
    var mapWidth = parseInt(map.style.width.replace(/px/g, ""));
    var sidebarWidth = parseInt(sidebar.style.width.replace(/px/g, "")) + 1;
    var sidebarLeft = parseInt(sidebar.style.left.replace(/px/g, ""));
    if (sidebarLeft == -1) {
        map.style.width = (mapWidth + sidebarWidth) + "px";
    }
    if (sidebarLeft >= sidebarWidth) {
        document.getElementById("hide-sidebar").style.visibility = "hidden";
        document.getElementById("show-sidebar").style.visibility = "visible";
        document.getElementById("sidebar-pane").style.zIndex = 0;
    }
    if (sidebarLeft < sidebarWidth) {
        sidebar.style.left = (sidebarLeft + 10) + "px";
        setTimeout(hideSidebar, 20);
    }
}

/**
 * Shows the sidebar.
 */
function showSidebar() {
    var map = document.getElementById("map");
    var sidebar = document.getElementById("sidebar");
    var mapWidth = parseInt(map.style.width.replace(/px/g, ""));
    var sidebarWidth = parseInt(sidebar.style.width.replace(/px/g, "")) + 1;
    var sidebarLeft = parseInt(sidebar.style.left.replace(/px/g, ""));
    if (sidebarLeft <= -1 && document.getElementById("show-sidebar").style.visibility != "hidden") {
        map.style.width = (mapWidth - sidebarWidth) + "px";
        document.getElementById("hide-sidebar").style.visibility = "visible";
        document.getElementById("show-sidebar").style.visibility = "hidden";
    }
    if (sidebarLeft >= sidebarWidth) {
        document.getElementById("sidebar-pane").style.zIndex = 9999;
    }
    if (sidebarLeft > -1) {
        sidebar.style.left = (sidebarLeft - 10) + "px";
        setTimeout(showSidebar, 20);
    }
    if (sidebarLeft < -1) {
        sidebar.style.left = "-1px";
    }
}

/**
 * Changes the value of the input element with specified id to the given value.
 * 
 * @param id the id of the element to change
 * @param value the new value 
 */
function changeValue(id, value) {
    hidePOI();
    var delay = getSidebarDelay();
    if (delay > 0) {
        showSidebar();
        setTimeout(function(){setRouteFrom(id, value);}, delay);
    } else {
        getElement(id).focus();
        getElement(id).setValue(value);
        getElement('route:route').focus();
    }
}
