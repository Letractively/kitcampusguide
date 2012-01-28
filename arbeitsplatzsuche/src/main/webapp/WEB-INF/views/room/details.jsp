<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#workplaces").dataTable({
							"aoColumns" : [ {
								"bSortable" : false
							}, // Checkboxes
							null, null ],
							"bSortClasses" : false,
							"bPaginate" : false,
							"bJQueryUI" : true,
							"sDom" : 'lrtp'
						});

						$("#calendar")
								.weekCalendar(
										{
											data : function(start, end,
													callback) {
												$
														.getJSON(
																"${pageContext.request.contextPath}/room/${room.id}/calendar.html",
																{
																	start : start
																			.toString(),
																	end : end
																			.toString()
																},
																function(result) {
																	callback(result);
																});
											},
											timeslotsPerHour : 4,
											height : function($calendar) {
												return 600;
											},
											use24Hour : true,
											readonly : true,
											businessHours : {
												start : 8,
												end : 22,
												limitDisplay : true
											},
											overlapEventsSeparate : false
										});

						var updateWorkplaceCount = function() {
							var count = $('input.workplace-checkbox:checked')
									.size();
							var text = (count == 1 ? count + ' Platz' : count
									+ " Plätze");
							$('span.workplaceCount').text(text);
						}

						$('input.workplace-checkbox').change(
								updateWorkplaceCount);

						updateWorkplaceCount();
					});
</script>
<div class="header2">
	<jsp:include page="/WEB-INF/views/logo.jsp"></jsp:include>


	<form:form method="get" commandName="searchFormModel"
		action="${pageContext.request.contextPath}/search/advanced.html">
		<form:input type="hidden" path="workplaceCount" />
		<form:input type="hidden" path="start" />
		<form:input type="hidden" path="duration" />
		<form:input type="hidden" path="wholeRoom" />
		<form:input type="hidden" path="searchText" />
		<c:forEach var="filter" items="${filters}" varStatus="status">
			<input type="hidden" name="filters" value="${filter}" />
		</c:forEach>
		<input type="hidden" name="_filters" value="on" />
		<input type='submit' value="<spring:message code="book.backToSearch" />" class="back">
	</form:form>

	<div class="right">
		<h1>
			<spring:message code="roomDetails.headline" arguments="${room.name}" />
		</h1>
	</div>
</div>
<div class="page roomDetails">
	<form:form method="post" commandName="bookingFormModel"
		action="${pageContext.request.contextPath}/room/${room.id}/details.html"
		id="searchForm">
		
		<form:errors element="div" cssClass="msg-error"/>
		
		<div class="bookingForm">
			<h2>Reservieren</h2>
			Am
			<form:input path="start" class="date" />
			für
			<form:input path="duration" class="time" />
			Stunden<br />
			<form:radiobutton path="wholeRoom" value="true" />
			ganzen Raum (${fn:length(workplaces)} Plätze)<br />
			<form:radiobutton path="wholeRoom" value="false" />
			gewählte Arbeitsplätze (<span class="workplaceCount"></span>)<br />
			<input type='submit' value='Reservieren'>
			<%-- Pass searchformModel attributes for back link --%>
			<input type="hidden" name="searchText" value="${searchFormModel.searchText}" />
			<c:forEach var="filter" items="${filters}" varStatus="status">
				<input type="hidden" name="filters" value="${filter}" />
			</c:forEach>
			<input type="hidden" name="_filters" value="on" />
		</div>
		<h2>Gebäude:</h2>
		<c:out value="${room.parentFacility.number}" />
		<c:out value="${room.parentFacility.name}" />
		<br />
		<h2>Raumausstattung:</h2>
		<ul>
			<c:forEach var="property" items="${room.inheritedProperties}">
				<li><img
					src="${pageContext.request.contextPath}/${icons.getIconFileName(property, 16)}" />
					<c:out value="${property.name}" /></li>
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
					<th style="width: 700px;">zusätzliche Ausstattung</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="workplace" items="${workplaces}" varStatus="status">
					<tr>
						<td><input class="workplace-checkbox" type="checkbox"
							name="workplaces[${status.index}]" value="${workplace.id}"
							<c:if test="${checked[status.index]}">  checked="checked"  </c:if>></input>
						</td>
						<td><c:out value="${workplace.name}" /></td>
						<td>
							<c:if test="${fn:length(workplacesProps[status.index]) == 0}">
								<span style="color:gray;"><spring:message code="noWorkplaceProperties" /></span>
							</c:if>
							<c:forEach var="property"
								items="${workplacesProps[status.index]}" varStatus="status2">
								<img
									src="${pageContext.request.contextPath}/${icons.getIconFileName(property, 16)}" />
								<c:out value="${property.name}" />
								<c:if
									test="${status2.index != fn:length(workplacesProps[status.index])-1 }">, </c:if>
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