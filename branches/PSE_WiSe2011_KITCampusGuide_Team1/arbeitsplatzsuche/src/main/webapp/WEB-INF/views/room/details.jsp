<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#workplaces").dataTable({
			"aoColumns" : [{ "bSortable" : false }, // Checkboxes
			    			null, 
			    			null
			    			],
			"bSortClasses": false,
			"bPaginate" : false,
			"bJQueryUI": true,
			"sDom": 'lrtp'
		});
	});
</script>
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
	<div class="bookingForm">
		<form:form method="get" commandName="searchFormModel" action="${pageContext.request.contextPath}/search/advanced.html" id="searchForm">
    		<form:input path="start" class="date" /> für 
     		<form:input path="duration" class="time"/> Stunden<br />
     		<form:radiobutton path="wholeRoom" value="true"/> ganzen Raum <br />
			<form:radiobutton path="wholeRoom" value="false"/> gewählte Arbeitsplätze <br />
    		<input type='submit' value='Reservieren'>
		</form:form>
	</div>
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
	<table id="workplaces">
		<thead>
			<tr>
				<th>&nbsp;</th>
				<th style="width: 100px;">Platznr.</th>
				<th style="width: 500px;">zusätzliche Ausstattung</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="workplace" items="${workplaces}">
			<tr>
				<td><input type="checkbox" name="workplace[]" value="${workplace.id}"></input></td>
				<td><c:out value="${workplace.name}" /></td>
				<td>
					<c:forEach var="property" items="${workplace.properties}">
						<c:out value="${property.name}" /> 
					</c:forEach>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>