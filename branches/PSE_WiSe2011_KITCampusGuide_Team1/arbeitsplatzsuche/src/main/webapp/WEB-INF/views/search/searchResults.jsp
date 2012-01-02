
<script type="text/javascript">
	
	function calcTableHeight() {
		// Height of the result table
		return ($(window).height() - 216) + "px";
	}
	
	$(window).resize(function () {
		// change result table height on window resize
		resultTable.fnSettings().oScroll.sY = calcTableHeight(); 
		resultTable.fnDraw();
	});
	
	$(document).ready(function() {

		resultTable = $("#resultTable").dataTable({
			"aoColumns" : [ null, 		// Room
			null, 						// Building
			{ "bSortable" : false }, 	// Equipment
			null, 						// Available from
			{ "bVisible" : false } 		// ID
			],
			"aaSorting" : [ [ 3, 'desc' ] ], // sort on collumn "available from"
			"bServerSide" : true,
			"sAjaxSource" : "/arbeitsplatzsuche/search/results.html",
			"bProcessing" : true,
			"bJQueryUI" : true,
	        "sDom": 'lfrtp',

			"bPaginate" : true,
			"bScrollInfinite" : true,
			"iDisplayLength" : 40,
			"bScrollCollapse" : true,
			"sScrollY" : calcTableHeight(),

			//Is called each update, to add params:
			"fnServerParams" : function(aoData) {
				//Add the values of the searchform:
				var searchFormData = $("#searchForm").serializeArray();
				$(searchFormData).each(function(i, o) {
					aoData.push({
						"name" : o.name,
						"value" : o.value
					});
				});

				//Add the values of the filterform:
				var filterFormData = $("#filterForm").serializeArray();
				$(filterFormData).each(function(i, o) {
					aoData.push({
						"name" : o.name,
						"value" : o.value
					});
				});
			}
		});

		var search = function() {

		}

		search();
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
					<th>RoomID</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</div>