<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<form:form method="post" commandName="updatedReservation" id="updateForm">
	<!-- 
	<c:if test="${!reservation.bookedFacilityIsRoom()}">
		<div>
			<label for="workplaceCount" style="color: grey;"><spring:message code="reservation-details.updateFormWorkplaceCount" /></label>
			<form:select path="workplaceCount" >
				<form:option value="1"/>
				<form:option value="2"/>
				<form:option value="3"/>
				<form:option value="4"/>
				<form:option value="5"/>
				<form:option value="6"/>
			</form:select>
			<form:errors path="workplaceCount" cssClass="form-error"/>
		</div>
	</c:if>
	 -->
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