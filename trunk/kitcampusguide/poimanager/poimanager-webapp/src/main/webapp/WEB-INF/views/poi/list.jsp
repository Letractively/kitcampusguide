<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="poisTitle" /></title>
<link rel='stylesheet'
	href='<spring:url value="/styles/pois.css" htmlEscape="true" />'
	type="text/css" />
</head>
<body>
	<div id="header">
		<fmt:message key="theCurrentUserIs" />
		<sec:authentication property="principal.name" />
		<a href="<spring:url value="/logout" htmlEscape="true" />"><fmt:message
				key="logout" /> </a>
	</div>
	<div class="message">
		<c:if test="${not empty faultMessage}">
			<div class="message fault">
				<fmt:message key="${faultMessage}" />
			</div>
		</c:if>
		<c:if test="${not empty successMessage}">
			<div class="message fault">
				<fmt:message key="${successMessage}" />
			</div>
		</c:if>
	</div>
	<fieldset id="content">
		<legend>
			<fmt:message key="poisTitle" />
		</legend>
		<table>
			<tr class="poi header">
				<th class="uid field title"><fmt:message key="uid" /></th>
				<th class="name field title"><fmt:message key="name" /></th>
				<th class="description fieldtitle"><fmt:message
						key="description" /></th>
				<th class="longitude field title"><fmt:message key="longitude" />
				</th>
				<th class="latitude field title"><fmt:message key="latitude" />
				</th>
				<th class="publicly field title"><fmt:message key="publicly" />
				</th>
				<th class="groupIds field title"><fmt:message key="groupIds" />
				</th>
				<th class="poiFunctions field title"><fmt:message
						key="poiFunctions" />
				</th>
			</tr>
			<c:forEach var="poi" items="${pois}">
				<tr class="poi">
					<td class="uid field">${poi.uid}</td>
					<td class="name field">${poi.name}</td>
					<td class="description field">${poi.description}</td>
					<td class="longitude field">${poi.longitude}</td>
					<td class="latitude field">${poi.latitude}</td>
					<td class="publicly field">${poi.publicly}</td>
					<td class="groupIds field">${poi.groupIds.id}</td>
					<td class="poiFunctions"><a href="poi/${poi.uid}/update.htm">update</a>
						<a href="poi/${poi.uid}/delete.htm">delete</a>
					</td>
				</tr>
			</c:forEach>
		</table>

		<br /> <a href="poi/create.htm"><fmt:message key="newPoiLink" />
		</a>
	</fieldset>
</body>
</html>