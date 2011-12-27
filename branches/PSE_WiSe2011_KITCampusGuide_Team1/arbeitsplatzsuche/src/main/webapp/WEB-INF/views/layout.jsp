<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Arbeitsplatz-Such-System</title>
<%-- Main Style sheet --%>
<link href="<c:url value="/css/style.css" />" rel="stylesheet" type="text/css">
<%-- Include jQuery UI Stylesheet --%>
<link href="<c:url value="/css/ui-lightness/jquery-ui-1.8.16.custom.css" />" rel="stylesheet" type="text/css">
<%-- Include jQuery, jQuery UI and Modernizr --%>
<script src="<c:url value="/scripts/lib/jquery-1.6.2.min.js" />"></script>
<script src="<c:url value="/scripts/lib/jquery-ui-1.8.16.custom.min.js" />"></script>
<%-- doc: http://trentrichardson.com/examples/timepicker/ --%>
<script src="<c:url value="/scripts/lib/jquery-ui-timepicker-addon.js" />"></script>
<%-- doc: www.modernizr.com --%>
<script src="<c:url value="/scripts/lib/modernizr-2.0.6.js" />"></script>
<script>
$(function() {
    // Check if the browser supports the date input type
    if (!Modernizr.inputtypes.date){
        // Add the jQuery UI DatePicker to all
        // input tags that have their type attributes
        // set to 'date'
        $('input[type=date]').datetimepicker({
            // specify the same format as the spec
            dateFormat: 'yy-mm-dd',
            timeFormat: 'h:m',
            stepMinute: 15
        });
    }
});
</script>

</head>
<body>
<header>
	<div class="right">
		KIT Arbeitsplats-Such-System
	</div>
	<nav>
		<ul>
			<li><a href="#">Suche</a></li>
			<li><a href="#">Meine Reservierungen</a></li>
			<li><a href="#">Abmelden</a></li>
		</ul>
	</nav>
</header>
<jsp:include page="/WEB-INF/views/${view}.jsp"></jsp:include>
</body>
</html>