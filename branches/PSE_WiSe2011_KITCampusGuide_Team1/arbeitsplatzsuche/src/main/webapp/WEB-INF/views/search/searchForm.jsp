<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<form:form method="get" commandName="searchFormModel" action="${pageContext.request.contextPath}/search/advanced.html" id="searchForm">
     <form:select path="workplaceCount" >
       <form:option value="1"/>
       <form:option value="2"/>
       <form:option value="3"/>
       <form:option value="4"/>
       <form:option value="5"/>
       <form:option value="6"/>
     </form:select> Plätze ab 
     <form:input path="start" type="date" /> für 
     <form:input path="duration" /> Stunden<br />
     <form:checkbox path="wholeRoom" itemLabel="ganzer Raum"/>
     <label for="wholeRoom">ganzer Raum</label> 
     <span class="searchText">
     	<label for="searchText">Suchtext: </label>
     	<form:input path="searchText" />
     </span>
    <br />
    <input type='submit' value='Suche'>
</form:form>