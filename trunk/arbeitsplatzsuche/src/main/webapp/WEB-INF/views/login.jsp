<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="simple-logo">
	<jsp:include page="/WEB-INF/views/logo.jsp"></jsp:include>
</div>
<div class="page">
	<c:if test="${registerSuccess}">
		<div class="msg-success"><spring:message code="register.success" /></div>
	</c:if>
	<div class="login-form">
	<p><strong>Demo-user: </strong><br />
	ubbbb@student.kit.edu / bbbbbbbb<br />
	ubbbd@student.kit.edu / bbbdbbbd<br />
	<strong>Tutor: </strong>ututo@student.kit.edu / tutotuto<br />
	... (see ...ass.testdata.TestData.java)
	</p>
		<form name="f" action="<c:url value='j_spring_security_check'/>" method="POST">
	        <label for="j_username"><spring:message code="login.email" /></label><input type='text' id='j_username' name='j_username' value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}" escapeXml="false" /></c:if>' /><br />
	        <label for="j_password"><spring:message code="login.password" /></label><input type='password' id='j_password' name='j_password' /><br />
	        <input type="checkbox" name="_spring_security_remember_me" id="_spring_security_remember_me"><label for="_spring_security_remember_me"><spring:message code="login.stayLoggedIn" /></label>
	
	        <div class="buttons">
		        <input name="submit" type="submit" />
		        <input name="reset" type="reset" />
	        </div>
	    </form>
	    <c:if test="${not empty param.login_error}">
	      <font color="red">
	        <spring:message code="login.fail" /><br/>
	        <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
	      </font>
	    </c:if>
	</div>
</div>