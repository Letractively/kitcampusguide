<%-- JSP Page header --%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%-- JSP import statement for the "List" and "POI" class files--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>KCG POI Management Results</title>
</head>
<body>
	<c:forEach var="p" items="${results}">
		<br />
		Name: <c:out value="${p.name}"/><br />
		ID: <c:out value="${p.id}"/><br />
	</c:forEach>
	<%
		//invalidate the session to be able to make a new request
		session.invalidate();
	%>
	<br />
	<%-- the following comment will be included in the output HTML, this one is not --%>
	<!-- Here we invoke some javascript to create a back button-->
	<button onClick="window.location='./index.html'">Back home!</button>
</body>
</html>