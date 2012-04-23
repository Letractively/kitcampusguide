<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="header2">
	<jsp:include page="/WEB-INF/views/logo.jsp"></jsp:include>
	<div class="right">
		<h1><spring:message code="reservation-list.heading" /></h1>
	</div>
</div>
<div class="page">
	<c:if test="${deleteNotification}">
		<div class="msg-success"><spring:message code="reservation-delete.success" /></div>
	</c:if>
	<c:if test="${reservations.size() == 0}">
		<div><spring:message code="reservation-list.noCurrentEntries" /></div>
	</c:if>
	<c:forEach var="reservation" items="${reservations}">
		<div class="reservationDetails">
			<div class="room heading"><spring:message code="reservation-details.roomBuilding" arguments="${reservation.formattedRoomName},${reservation.buildingName}" /></div>
			<div class="time">
				<fmt:formatDate var="formattedStartDate" value="${reservation.startTime}" pattern="dd.MM.yyyy"/> 
				<fmt:formatDate var="formattedStartHour" value="${reservation.startTime}" pattern="HH:mm"/>
				<fmt:formatNumber var="formattedHours" pattern="00" value="${reservation.durationHours}" />
				<fmt:formatNumber var="formattedMinutes" pattern="00" value="${reservation.durationMinutes}" />
				<spring:message code="reservation-details.time" arguments="${formattedStartDate},${formattedStartHour},${formattedHours},${formattedMinutes}"/>
			</div>
			<div class="info">
				<c:choose>
					<c:when test="${reservation.bookedFacilityIsRoom()}">
						<spring:message code="reservation-details.roomIsBooked" arguments="${reservation.workplaceCount}" />
					</c:when>
					<c:otherwise>
						<spring:message code="reservation-details.workplacesAreBooked" arguments="${reservation.workplaceCount}" />
					</c:otherwise>
				</c:choose>
				<c:if test="">
					
				</c:if>
				<c:if test="${!reservation.bookedFacilityIsRoom()}">
				</c:if>
			</div>
			<div class="button">
				<span class="button link"><a href="<c:url value="/reservation/${reservation.id}/details.html" />"><spring:message code="reservation-details.details" /></a></span>
			</div>
		</div>
	</c:forEach>
	<c:if test="${pastReservations.size() > 0}">
		<br/>
		<h2><spring:message code="reservation-list.pastReservations" /></h2>
		<c:forEach var="reservation" items="${pastReservations}">
			<div class="reservationDetails">
				<div class="room heading"><spring:message code="reservation-details.roomBuilding" arguments="${reservation.formattedRoomName},${reservation.buildingName}" /></div>
				<div class="time">
					<fmt:formatDate var="formattedStartDate" value="${reservation.startTime}" pattern="dd.MM.yyyy"/> 
					<fmt:formatDate var="formattedStartHour" value="${reservation.startTime}" pattern="HH:mm"/>
					<fmt:formatNumber var="formattedHours" pattern="00" value="${reservation.durationHours}" />
					<fmt:formatNumber var="formattedMinutes" pattern="00" value="${reservation.durationMinutes}" />
					<spring:message code="reservation-details.time" arguments="${formattedStartDate},${formattedStartHour},${formattedHours},${formattedMinutes}"/>
				</div>
				<div class="info">
					<c:choose>
						<c:when test="${reservation.bookedFacilityIsRoom()}">
							<spring:message code="reservation-details.roomIsBooked" arguments="${reservation.workplaceCount}" />
						</c:when>
						<c:otherwise>
							<spring:message code="reservation-details.workplacesAreBooked" arguments="${reservation.workplaceCount}" />
						</c:otherwise>
					</c:choose>
					<c:if test="">
						
					</c:if>
					<c:if test="${!reservation.bookedFacilityIsRoom()}">
					</c:if>
				</div>
				<div class="options">
					<a href="<c:url value="/reservation/${reservation.id}/details.html" />"><spring:message code="reservation-details.details" /></a>
				</div>
			</div>
		</c:forEach>
	</c:if>
</div>