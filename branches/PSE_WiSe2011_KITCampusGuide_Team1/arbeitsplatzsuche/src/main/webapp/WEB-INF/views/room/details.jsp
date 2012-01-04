<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="header">
	<div class="advanced-logo">
		<jsp:include page="/WEB-INF/views/logo.jsp"></jsp:include>
	</div>
</div>
<h1>Raum <c:out value="${room.name}" /></h1>