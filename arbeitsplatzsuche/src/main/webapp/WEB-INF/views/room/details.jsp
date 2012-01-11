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
		
		$("#calendar").weekCalendar({
			data: function(start, end, callback) {
				  $.getJSON("${pageContext.request.contextPath}/room/${room.id}/calendar.html", {
				     start: start.toString(),
				     end: end.toString()
				   },  function(result) {
				     callback(result);
				   });
				},
			timeslotsPerHour: 4,
			height: function($calendar){
				return 600;
			},
			use24Hour: true,
			businessHours: {start: 8, end: 22, limitDisplay: true}
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
<div class="page roomDetails">
<form:form method="post" commandName="bookingFormModel" action="${pageContext.request.contextPath}/room/${room.id}/details.html" id="searchForm">
	<div class="bookingForm">
		<h2>Reservieren</h2>
    		Am <form:input path="start" class="date" /> für 
     		<form:input path="duration" class="time"/> Stunden<br />
     		<form:radiobutton path="wholeRoom" value="true"/> ganzen Raum <br />
			<form:radiobutton path="wholeRoom" value="false"/> gewählte Arbeitsplätze <br />
    		<input type='submit' value='Reservieren'>
		
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
				<th style="width: 75px;">Gewählt</th>
				<th style="width: 100px;">Platznr.</th>
				<th style="width: 500px;">zusätzliche Ausstattung</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="workplace" items="${workplaces}" varStatus="i">
			<tr>
				<td><input type="checkbox" name="workplaces[${i.count}]" value="${workplace.id}"></input></td>
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
	</form:form>
	<h2>Belegung:</h2>
	<div id="calendar"></div>
</div>