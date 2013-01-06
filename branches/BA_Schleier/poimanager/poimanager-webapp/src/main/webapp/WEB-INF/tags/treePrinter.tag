<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>  
<%@ attribute name="pois" type="java.util.List"%>  
<%@ attribute name="level" type="java.lang.Long"%>  
<c:forEach var="poi" items="${pois}">
	<tr class="poi">
		
		<td class="hierarchy field">
		<% if(level == 1) { %>-<% } %>
		<% for(int i = 1; i < level; i++) { %>+<% } %>
		</td>
		<td class="uid field">${poi.uid}</td>
		<td class="parentId field">${poi.parentId}</td>
		<td class="name field">${poi.name}</td>
		<td class="description field">${poi.description}</td>
		<td class="longitude field">${poi.longitude}</td>
		<td class="latitude field">${poi.latitude}</td>
		<td class="publicly field">${poi.publicly}</td>
		<td class="groupId field">${poi.groupId}</td>
		<td class="poiFunctions"><a href="poi/${poi.uid}/update.htm">update</a>
			<a href="poi/${poi.uid}/delete.htm">delete</a>
		</td>
	</tr>
	<c:if test="${not empty poi.children.pois}">  
	  	<tag:treePrinter pois="${poi.children.pois}" level="${level+1}"/>  
	</c:if>
</c:forEach>