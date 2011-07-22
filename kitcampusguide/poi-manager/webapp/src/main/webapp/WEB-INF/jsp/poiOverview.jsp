<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Overview of points of interest</title>
<link rel='stylesheet'
	href='<c:url value="/WEB-INF/jsp/style/poiOverview.css"></c:url>'
	type="text/css" />
</head>
<body>
	<fieldset>
		<legend>Overview</legend>

		Points of interest:

		<table>
			<tr class="poi header">
			<th class="name field title">Name</th>
			<th class="description fieldtitle">Description</th>
			<th class="longitude field title">Longitude</th>
			<th class="latitude field title">Latitude</th>
			</tr>
			<c:forEach var="poi" items="${pois}">
				<tr class="poi">
					<td class="name field">${poi.name}</td>
					<td class="description field">${poi.description}</td>
					<td class="longitude field">${poi.longitude}</td>
					<td class="latitude field">${poi.latitude}</td>
				</tr>
			</c:forEach>
		</table>

	</fieldset>
</body>
</html>