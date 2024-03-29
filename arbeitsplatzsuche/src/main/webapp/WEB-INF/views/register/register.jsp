<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="simple-logo">
	<jsp:include page="/WEB-INF/views/logo.jsp"></jsp:include>
</div>
<div class="page">
	<c:if test="${userExists}">
		<div class="msg-error"><spring:message code="register.userExists" /></div>
	</c:if>
	<div class="register-form">
		<h2><spring:message code="register.heading" /></h2>
		<div>
			<form:form method="POST" commandName="registerUser">
				<div>
					<form:errors path="email" cssClass="form-error"/>
				</div>
				<div>
					<label for="email"><spring:message code="register.email" /></label>
					<form:input path="email" />
				</div>	
				<div>
					<form:errors path="password" cssClass="form-error"/>
				</div>
				<div>
					<label for="password"><spring:message code="register.password" /></label>
					<form:password path="password"/>
				</div>		
				<div>
					<label for="submit">&nbsp;</label>
					<input name="submit" type='submit' value='<spring:message code="register.submitButton" />'>
				</div>
			</form:form>
		</div>
	</div>
</div>