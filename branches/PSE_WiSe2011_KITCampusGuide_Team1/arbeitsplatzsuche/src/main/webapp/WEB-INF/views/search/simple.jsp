<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<h1>KIT ASS</h1>
<div class="simple-search">
       <form:form method="get" commandName="searchFormModel">
           <div class="field searchText">
               <label class="label" for="searchText">Suchtext: </label>
               <form:input path="searchText" />
           </div>
           <br />
           <input type='submit' value='Suche'>
       </form:form>
</div>	
