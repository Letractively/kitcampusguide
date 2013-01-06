<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="poiTitle" /></title>
<link rel='stylesheet'
	href='<spring:url value="/styles/poi.css" htmlEscape="true" />'
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
	<fieldset>
		<legend>
			<fmt:message key="poiTitle" />
		</legend>
		<form:form method="post" commandName="poi">
			<c:if test="${not empty updatePoi.uid}">
				<div class="field name">
					<label class="label" for="uid"> <fmt:message
							key="identifier" /> </label>
					<form:input path="uid" readonly="true" cssClass="uid field" />
					<form:errors path="uid" cssClass="uid error" />
				</div>
			</c:if>
			<div class="field name">
				<label class="label" for="name"> <fmt:message key="name" />
				</label>
				<form:input path="name" cssClass="name field" />
				<form:errors path="name" cssClass="name error" />
			</div>
			<div class="field description">
				<label class="label" for="description"> <fmt:message
						key="description" /> </label>
				<form:textarea path="description" rows="3" cols="26"
					cssClass="description field" />
				<form:errors path="description" cssClass="description error" />
			</div>
			<div class="field longitude">
				<label class="label" for="longitude"> <fmt:message
						key="longitude" /> </label>
				<form:input path="longitude" cssClass="longitude field" />
				<form:errors path="longitude" cssClass="longitude error" />
			</div>
			<div class="field latitude">
				<label class="label" for="latitude"> <fmt:message
						key="latitude" /> </label>
				<form:input path="latitude" cssClass="latitude field" />
				<form:errors path="latitude" cssClass="latitude error" />
			</div>
			<div class="field publicly">
				<label class="label" for="publicly"> <fmt:message
						key="publicly" /> </label>
				<form:checkbox path="publicly" cssClass="publicly field" />
				<form:errors path="publicly" cssClass="publicly error" />
			</div>
			<div class="field groupId">
				<label class="label" for="groupId"> <fmt:message
						key="groupId" /> </label>
				<form:select multiple="false" path="groupId" items="${groups}"
					itemValue="id" itemLabel="name" cssClass="groupId field" />
				<form:errors path="groupId" cssClass="groupId error" />
			</div>
			<div class="field parentId">
				<label class="label" for="parentId"> <fmt:message
						key="parentId" /> </label>
				<form:select multiple="false" path="parentId" items="${pois}"
					itemValue="uid" itemLabel="name" cssClass="parentId field" />
				<form:errors path="parentId" cssClass="parentId error" />
			</div>

			<input type='submit' value='<fmt:message key="save" />'>
			<br />
			<a href='<c:url value="/pois.htm" />'> <fmt:message
					key="backToPois" /> </a>
		</form:form>
	</fieldset>
</body>
</html>