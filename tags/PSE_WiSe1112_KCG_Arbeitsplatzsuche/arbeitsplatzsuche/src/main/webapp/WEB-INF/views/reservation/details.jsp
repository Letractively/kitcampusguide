<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="header2">
	<jsp:include page="/WEB-INF/views/logo.jsp"></jsp:include>
	<div class="right">
		<h1><spring:message code="reservation-details.heading" /></h1>
	</div>
</div>
<div class="page">
	<c:choose>
		<c:when test="${errorReservationNotFound}">	
			<div class="msg-error"><spring:message code="reservation-details.notFound" /></div>
		</c:when>
		<c:otherwise>
			<div class="reservationDetails">
				<div class="room heading">
					<spring:message code="reservation-details.roomBuilding" arguments="${reservation.formattedRoomName},${reservation.buildingName}" />
				</div>
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
					<span class="button delete"><a href="<c:url value="/reservation/${reservation.id}/delete.html" />"><spring:message code="reservation-details.deleteLinkLabel" /></a></span>
					<span class="button link"><a href="<c:url value="/room/${reservation.room.id}/details.html" />"><spring:message code="reservation-details.roomLinkLabel" /></a></span>
				</div>
			</div>
			<c:if test="${updateSuccess}">
				<div class="msg-success"><spring:message code="reservation-details.updateMessageSuccess" /></div>
			</c:if>
			<c:if test="${updateErrorWorkplaceCount}">
				<div class="msg-error"><spring:message code="reservation-details.updateMessageWorkplaceCount" /></div>
			</c:if>
			<c:if test="${updateErrorFacilityOccupied}">
				<div class="msg-error"><spring:message code="reservation-details.updateMessageFacilityOccupied" /></div>
			</c:if>
			<c:if test="${formErrors}">
				<div class="msg-error"><spring:message code="reservation-details.updateMessageFormErrors" /></div>
			</c:if>
			<div><jsp:include page="/WEB-INF/views/reservation/updateForm.jsp"></jsp:include></div>
		</c:otherwise>
	</c:choose>
</div>