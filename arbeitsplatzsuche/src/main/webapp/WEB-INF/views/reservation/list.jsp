<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="header2">
	<jsp:include page="/WEB-INF/views/logo.jsp"></jsp:include>
	<div class="right">
		<h1>
			Meine Reservierungen
		</h1>
	</div>
</div>
<div class="page">
	<c:if test="${deleteNotification}">
		<div class="notification">Die Reservierung wurde gel&ouml;scht!</div>
	</c:if>
	<c:forEach var="reservation" items="${reservations}">
		<div class="reservationDetails">
			<div class="room"><c:out value="${reservation.roomName}" /> (<c:out value="${reservation.buildingName}" />)</div>
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
			<div class="options">
				<a href="<c:url value="/reservation/${reservation.id}/details.html" />">Details</a>
			</div>
		</div>
	</c:forEach>
	<c:if test="${pastReservations.size() > 0}">
		<br/>
		<h2>Vergangene Reservierungen</h2>
		<c:forEach var="reservation" items="${pastReservations}">
			<div class="reservationDetails">
				<div class="room"><c:out value="${reservation.roomName}" /> (<c:out value="${reservation.buildingName}" />)</div>
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
				<div class="options">
					<a href="<c:url value="/reservation/${reservation.id}/details.html" />">Details</a>
				</div>
			</div>
		</c:forEach>
	</c:if>
</div>