<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Arbeitsplatz-Such-System</title>
<link href="/arbeitsplatzsuche/css/style.css" rel="stylesheet" type="text/css">
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