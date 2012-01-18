<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<form:form method="post" commandName="updatedReservation" id="updateForm">
	<c:if test="${!reservation.bookedFacilityIsRoom() && reservation.workplaceCount > 1}">
		<div>
			<label for="workplaceCount"><spring:message code="reservation-details.updateFormWorkplaceCount" /></label>
			<form:select path="workplaceCount" >
				<c:forEach var="i" begin="1" end="${reservation.workplaceCount}">
					<form:option value="${i}"/>
				</c:forEach>
			</form:select>
			<form:errors path="workplaceCount" cssClass="form-error"/>
		</div>
	</c:if>
	<div>
		<label for="endTime"><spring:message code="reservation-details.updateFormEndTime" /></label>
		<form:input path="endTime" class="date" />
		<form:errors path="endTime" cssClass="form-error"/>
	</div>
	<div>
		<label for="submit">&nbsp;</label>
		<input name="submit" type='submit' value='<spring:message code="reservation-details.updateFormSubmit" />'>
	</div>
</form:form>