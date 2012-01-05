<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="header2">
	<jsp:include page="/WEB-INF/views/logo.jsp"></jsp:include>
	<div class="right">
		<h1>
			Details für
			<c:out value="${room.name}" />
		</h1>
	</div>
</div>
<div class="page">
	<h2>Gebäude:</h2>
	<c:out value="${room.parentFacility.number}" />
	<c:out value="${room.parentFacility.name}" />
	<br />
	<h2>Raumausstattung:</h2>
	<ul>
		<c:forEach var="property" items="${room.properties}">
			<li><c:out value="${property.name}" /></li>
		</c:forEach>
	</ul>
	<h2>Beschreibung:</h2>
	<p>
		<c:out value="${room.description}" />
	</p>
	<h2>Arbeitsplätze:</h2>
	<table>
		<tr>
			<th>&nbsp;</th>
			<th>Platznr.</th>
			<th>Belegung</th>
			<th>zusätzliche Ausstattung</th>
		</tr>
		<c:forEach var="workplace" items="${workplaces}">
			<tr>
				<td><input type="checkbox" name="workplace[]" value="${workplace.id}"></input></td>
				<td><c:out value="${workplace.name}" /></td>
				<td>?</td>
				<td>
					<c:forEach var="property" items="${workplace.properties}">
						<c:out value="${property.name}" /> 
					</c:forEach>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>