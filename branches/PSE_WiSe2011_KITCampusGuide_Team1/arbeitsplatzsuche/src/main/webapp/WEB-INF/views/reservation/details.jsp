<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="header2">
	<jsp:include page="/WEB-INF/views/logo.jsp"></jsp:include>
	<div class="right">
		<h1>
			Reservierungsdetails
		</h1>
	</div>
</div>
<div class="page">
	<div class="reservationDetails">
		<div class="room"><a href="<c:url value="/room/${reservation.room.id}/details.html" />"><c:out value="${reservation.roomName}" /></a> (<c:out value="${reservation.buildingName}" />)</div>
		<div class="time">
			<fmt:formatDate value="${reservation.startTime}" pattern="dd.MM.yyyy"/> 
			um <fmt:formatDate value="${reservation.startTime}" pattern="HH:mm"/> Uhr 
			(f&uuml;r <fmt:formatNumber pattern="00" value="${reservation.durationHours}" />:<fmt:formatNumber pattern="00" value="${reservation.durationMinutes}" /> Stunden)</div>
		<div class="info">
			<c:if test="${reservation.bookedFacilityIsRoom()}">
				Der ganze Raum wurde reserviert (<c:out value="${reservation.workplaceCount}" /> Arbeitpl&auml;tze).
			</c:if>
			<c:if test="${!reservation.bookedFacilityIsRoom()}">
				Es sind <c:out value="${reservation.workplaceCount}" /> Arbeitpl&auml;tze reserviert.
			</c:if>
		</div>
	</div>
	<div class="reservationDetails">
		<div>Reservierung l&ouml;schen</div>
		<div><a href="<c:url value="/reservation/${reservation.id}/delete.html" />">Diese Reservierung l&ouml;schen</a></div>
	</div>
	<div class="reservationDetails">
		<div>Diese Reservierung &auml;ndern</div>
		<c:if test="${updateSuccess}">
			<div class="notification">Die Reservierung wurde erfolgreich ge&auml;ndert!</div>
		</c:if>
		<c:if test="${updateErrorFacilityOccupied}">
			<div class="error">Die Reservierung konnte nicht ge&auml;ndert werden: Die gew&uuml;nschten Arbeitspl&auml;tze sind nicht frei.</div>
		</c:if>
		<div style="text-decoration: italic;">Hinweis: Die Reservierung kann nur dann ge&auml;ndert werden, wenn die Arbeitspl&auml;tze nicht von einem anderen Nutzer reserviert sind.</div>
		<div><jsp:include page="/WEB-INF/views/reservation/updateForm.jsp"></jsp:include></div>
	</div>
</div>