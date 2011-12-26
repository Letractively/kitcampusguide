<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Arbeitsplatz-Such-System</title>
<link href="<c:url value="/css/style.css" />" rel="stylesheet" type="text/css">
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