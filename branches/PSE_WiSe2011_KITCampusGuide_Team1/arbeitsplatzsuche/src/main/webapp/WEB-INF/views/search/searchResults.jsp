
<link href="/arbeitsplatzsuche/datatables/css/demo_page.css" rel="stylesheet"
	type="text/css" />
<link href="/arbeitsplatzsuche/datatables/css/demo_table.css" rel="stylesheet"
	type="text/css" />
<link href="/arbeitsplatzsuche/datatables/css/demo_table_jui.css" rel="stylesheet"
	type="text/css" />
<link href="/arbeitsplatzsuche/datatables/themes/base/jquery-ui.css" rel="stylesheet"
	type="text/css" media="all" />
<link href="/arbeitsplatzsuche/datatables/themdes/smoothness/jquery-ui-1.7.2.custom.css"
	rel="stylesheet" type="text/css" media="all" />
<script src="/arbeitsplatzsuche/scripts/lib/jquery.dataTables.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		resultTable = $("#resultTable").dataTable({
			"bServerSide" : true,
			"sAjaxSource" : "/arbeitsplatzsuche/search/results.html",
			"bProcessing" : true,
			"sPaginationType" : "full_numbers",
			"bJQueryUI" : true
		});
	});
</script>
<div id="container">
	<div id="searchResults">
		<table id="resultTable" class="display">
			<thead>
				<tr>
					<th>Raum</th>
					<th>Gebäude</th>
					<th>Ausstattung</th>
					<th>Frei ab</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</div>
<!-- <div><input value="update" type="button" onclick="resultTable.fnDraw();"></div>-->