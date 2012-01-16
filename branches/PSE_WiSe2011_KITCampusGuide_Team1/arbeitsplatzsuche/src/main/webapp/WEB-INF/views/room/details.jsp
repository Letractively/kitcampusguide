<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
			readonly: true,
			businessHours: {start: 8, end: 22, limitDisplay: true}
		});
		
		var updateWorkplaceCount = function(){
			var count = $('input.workplace-checkbox:checked').size();
			var text = (count == 1 ? count + ' Platz' : count + " Pl�tze");
			$('span.workplaceCount').text(text);
		}
		
		$('input.workplace-checkbox').change(updateWorkplaceCount);
		
		
		updateWorkplaceCount();
	});
</script>
<div class="header2">
	<jsp:include page="/WEB-INF/views/logo.jsp"></jsp:include>
	<div class="right">
		<h1>
			Details f�r
			<c:out value="${room.name}" />
		</h1>
	</div>
</div>
<div class="page roomDetails">
	<c:if test="${formErrors}">
		<div class="msg-error">Fehler beim Buchen</div>
	</c:if>
	<c:if test="${notFree}">
		<div class="msg-error">
			<c:if test="${bookingFormModel.wholeRoom}">
				Der Raum ist zur angegebenen Zeit nicht frei.
			</c:if>
			<c:if test="${!bookingFormModel.wholeRoom}">
				Nicht alle angegebenen Arbeitspl�tze sind zur angegebenen Zeit frei.
			</c:if>
		</div>
	</c:if>
	<c:if test="${noFacilities}">
		<div class="msg-error">
				Sie haben keine Arbeitspl�tze ausgew�hlt.
		</div>
	</c:if>
<form:form method="post" commandName="bookingFormModel" action="${pageContext.request.contextPath}/room/${room.id}/details.html" id="searchForm">
	<div class="bookingForm">
		<h2>Reservieren</h2>
    		Am <form:input path="start" class="date" /> f�r 
     		<form:input path="duration" class="time"/> Stunden<br />
     		<form:radiobutton path="wholeRoom" value="true"/> ganzen Raum (${fn:length(workplaces)} Pl�tze)<br />
			<form:radiobutton path="wholeRoom" value="false"/> gew�hlte Arbeitspl�tze (<span class="workplaceCount"></span>)<br />
    		<input type='submit' value='Reservieren'>
		
	</div>
	<h2>Geb�ude:</h2>
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
	<h2>Arbeitspl�tze:</h2>
	<table id="workplaces">
		<thead>
			<tr>
				<th style="width: 75px;">Gew�hlt</th>
				<th style="width: 100px;">Platznr.</th>
				<th style="width: 500px;">zus�tzliche Ausstattung</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="workplace" items="${workplaces}" varStatus="status">
			<tr>
				<td>
					<input class="workplace-checkbox" type="checkbox" name="workplaces[${status.index}]" value="${workplace.id}" <c:if test="${checked[status.index]}">  checked="checked"  </c:if> ></input>
				</td>
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