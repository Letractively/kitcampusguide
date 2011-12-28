<div class="advanced-search">
	<jsp:include page="/WEB-INF/views/search/searchForm.jsp"></jsp:include>
</div>
<div style="position: fixed; float: left; top: 150px; width: 100%; clear: both;">
	<div class="filter" style="float: left; width: 225px; margin-right: -225px;height: 1%; padding: 20px;">
		<jsp:include page="/WEB-INF/views/search/searchFormFilter.jsp"></jsp:include>
	</div>
	<div class="results" style=" margin-left: 265px;height: 1%; padding: 20px;">
		<jsp:include page="/WEB-INF/views/search/searchResults.jsp"></jsp:include>
	</div>
</div>