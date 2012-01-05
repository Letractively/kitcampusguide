<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="header2">
	<jsp:include page="/WEB-INF/views/logo.jsp"></jsp:include>
	<div class="right">
		<h1>Details für <c:out value="${room.name}" /></h1>
	</div>
</div>
<div class="page">
	<strong>Gebäude: </strong><c:out value="${room.parentFacility.number}" /> <c:out value="${room.parentFacility.name}" /><br />
	<strong>Raumausstattung:</strong>
	<ul>
		<c:forEach var="property" items="@{room.properties}">
			<li><c:out value="@{property.name}" /></li>
		</c:forEach>
	</ul>
</div>