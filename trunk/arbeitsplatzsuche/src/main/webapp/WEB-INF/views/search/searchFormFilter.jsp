<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
Filter<br><br>
<form:form commandName="searchFilterModel" id="filterForm">
	<!-- not yet implemented -->
	<!--
	<div style="color: grey; padding-bottom: 10px;">
		Umkreissuche in<br>
		<form:select path="searchNearDistance">
			<form:option value="100" />
			<form:option value="200" />
			<form:option value="300" />
			<form:option value="500" />
			<form:option value="1000" />
			<form:option value="2000" />
			<form:option value="3000" />
		</form:select> m zu<br>
		<form:input path="searchNearLocation" />
	</div>
	-->
	<div>
	<form:checkboxes items="${filterList}" path="filters" delimiter="<br>" onchange="resultTable.fnDraw();" />
	</div>
</form:form>