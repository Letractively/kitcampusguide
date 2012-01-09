<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="simple-logo">
	<jsp:include page="/WEB-INF/views/logo.jsp"></jsp:include>
</div>
<div class="register-form">
	<h2>Registrieren</h2>
	<div>
		<form:form method="POST" commandName="registerUser">
			<div>
				<label for="email">E-Mail-Adresse</label>
				<form:input path="email" />
				<form:errors path="email" cssClass="form-error"/>
			</div>	
			<div>
				<label for="password">Passwort</label>
				<form:password path="password"/>
				<form:errors path="password" cssClass="form-error"/>
			</div>		
			<div>
				<label for="submit">&nbsp;</label>
				<input name="submit" type='submit' value='Registrieren'>
			</div>
		</form:form>
	</div>
</div>