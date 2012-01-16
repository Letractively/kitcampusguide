<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="title.${fn:replace(view, '/', '-')}" text="KIT Arbeitsplatz-Such-System" arguments="${titleArguments}"/></title>
<%-- Main Style sheet --%>
<link href="<c:url value="/css/style.css" />" rel="stylesheet" type="text/css">
<%-- Include jQuery UI Stylesheet --%>
<link href="<c:url value="/libs/jquery/ui-lightness/jquery-ui-1.8.16.custom.css" />" rel="stylesheet" type="text/css">
<%-- Include jQuery, jQuery UI and Modernizr --%>
<script src="<c:url value="/libs/jquery/jquery-1.6.2.min.js" />"></script>
<script src="<c:url value="/libs/jquery/jquery-ui-1.8.16.custom.min.js" />"></script>
<%-- doc: http://trentrichardson.com/examples/timepicker/ --%>
<script src="<c:url value="/libs/jquery/jquery-ui-timepicker-addon.js" />"></script>
<%-- doc: www.modernizr.com --%>
<script src="<c:url value="/libs/modernizr/modernizr-2.0.6.js" />"></script>
<%-- doc: http://www.myjqueryplugins.com/jNotify --%>
<script src="<c:url value="/libs/jnotify/jNotify.jquery.js" />"></script>
<link href="<c:url value="/libs/jnotify/jNotify.jquery.css" />" rel="stylesheet" type="text/css">
<%-- doc: https://github.com/robmonie/jquery-week-calendar/wiki/ --%>
<script src="<c:url value="/libs/weekcalendar/jquery.weekcalendar.js" />"></script>
<link href="<c:url value="/libs/weekcalendar/jquery.weekcalendar.css" />" rel="stylesheet" type="text/css">
<%-- page specific CSS and JS files --%>
<c:forEach var="file" items="${cssFiles}">
<link href="<c:url value="${file}" />" rel="stylesheet" type="text/css">
</c:forEach>
<c:forEach var="file" items="${jsFiles}">
<script src="<c:url value="${file}" />"></script>
</c:forEach>
<script>
$(function() {
	$.datepicker.regional['de'] = {
			closeText: 'schließen',
			prevText: '&#x3c;zurück',
			nextText: 'Vor&#x3e;',
			currentText: 'heute',
			monthNames: ['Januar','Februar','März','April','Mai','Juni',
			'Juli','August','September','Oktober','November','Dezember'],
			monthNamesShort: ['Jan','Feb','Mär','Apr','Mai','Jun',
			'Jul','Aug','Sep','Okt','Nov','Dez'],
			dayNames: ['Sonntag','Montag','Dienstag','Mittwoch','Donnerstag','Freitag','Samstag'],
			dayNamesShort: ['So','Mo','Di','Mi','Do','Fr','Sa'],
			dayNamesMin: ['So','Mo','Di','Mi','Do','Fr','Sa'],
			weekHeader: 'Wo',
			dateFormat: 'dd.mm.yy',
			firstDay: 1,
			isRTL: false,
			showMonthAfterYear: false,
			yearSuffix: ''
			};
	$.datepicker.setDefaults($.datepicker.regional['de']);
	

       $('input.date').datetimepicker({
           	dateFormat: 'dd-mm-yy',
           	timeFormat: 'hh:mm',
           	stepMinute: 15,
			timeText: 'Zeit',
			hourText: 'Stunde',
			minuteText: 'Minute'
       });
       $('input.time').timepicker({
           	timeFormat: 'hh:mm',
           	stepMinute: 15,
          	timeOnlyTitle: "Dauer:",
			timeText: 'Zeit',
			hourText: 'Stunde',
			minuteText: 'Minute'
       });
    
});
</script>

</head>
<body>
<header>
	<div class="right">
		KIT Arbeitsplatz-Such-System
	</div>
	<nav>
		<ul>
		<security:authorize access="isAnonymous()">
			<li><a href="<c:url value="/login.html" />">Anmelden</a></li>
			<li><a href="<c:url value="/register.html" />">Registrieren</a></li>
		</security:authorize>
		<security:authorize access="isAuthenticated()">
			<li><a href="<c:url value="/search/simple.html" />">Suche</a></li>
			<li><a href="<c:url value="/reservation/list.html" />">Meine Reservierungen</a></li>
			<li><a href="<spring:url value="/j_spring_security_logout" htmlEscape="true" />">Abmelden</a></li>
		</security:authorize>
		</ul>
	</nav>
</header>
<jsp:include page="/WEB-INF/views/${view}.jsp"></jsp:include>
</body>
</html>