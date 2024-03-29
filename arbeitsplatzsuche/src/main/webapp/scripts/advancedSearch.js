var resultTable;
	
	function calcTableHeight() {
		// Height of the result table
		return ($(window).height() - 170) + "px";
	}
	
	$(document).ready(function() {
		
		var lastQueryData;
		
		resultTable = $("#resultTable").dataTable({
			"aoColumns" : [ null, 		// Room
			null, 						// Building
			{ "bSortable" : false }, 	// Equipment
			null, 						// Available from
			{ "bVisible" : false } 		// ID
			],
			"aaSorting" : [ [ 3, 'asc' ] ], // sort on collumn "available from"
			"bServerSide" : true,
			"sAjaxSource" : "/arbeitsplatzsuche/search/results.html",
			"bProcessing" : true,
			"bJQueryUI" : true,
	        "sDom": 'lrtp',  			// Hide search
	        
	        "oLanguage": {
				"sZeroRecords": "Es wurden keine Arbeitspl&auml;tze mit diesen Suchkriterien gefunden.",
				"sProcessing": "Lade Suchergebnisse...",
	        },
	        
			"bPaginate" : true,
			"bScrollInfinite" : true,
			"iDisplayLength" : 40,
			"bScrollCollapse" : true,
			"sScrollY" : calcTableHeight(),
			"bSortClasses": false,

			//Is called each update, to add params:
	        "fnServerParams": function ( aoData ) {
	        	
	        	lastQueryData = [];
	        	
	        	//Add the values of the searchform:
	        	var searchFormData = $("#searchForm").serializeArray();
	        	$(searchFormData).each(function(i, o){
	        		aoData.push( o );
	        		lastQueryData.push( o );
	        	});
	        	
	        	//Add the values of the filterform:
	        	var filterFormData = $("#filterForm").serializeArray();
	        	$(filterFormData).each(function(i, o){
	        		aoData.push( o );
	        		lastQueryData.push( o );
	        	});
	        },
	        
	        "fnServerData": function ( sSource, aoData, fnCallback ) {
	            $.ajax( {
	                "dataType": 'json',
	                "type": "POST",
	                "url": sSource,
	                "data": aoData,
	                "success": function(data, textStatus, jqXHR){
	                	//If has errors, then show the error message
	                	if(data.asErrors){
	                		//show each error message
	                		$(data.asErrors).each(function(i, o){
	                			//show current error message
	                			jError(o, 
	                				{
	                				  autoHide : true,
	                				  clickOverlay : false,
	                				  MinWidth : 250,
	                				  TimeShown : 3000,
	                				  ShowTimeEffect : 600,
	                				  HideTimeEffect : 800,
	                				  LongTrip :20,
	                				  HorizontalPosition : 'center',
	                				  VerticalPosition : 'center',
	                				  ShowOverlay : false
	                				});
	                		});
	                	}
	                	fnCallback(data, textStatus, jqXHR);
	                }
	            } );
	        }

		});

		//Add click handler
		$("#resultTable tbody").click(function(event) {
			//get the row data
			var tr = $(event.target).parents('tr');
			var aData = resultTable.fnGetData( tr[0] );
			//id has index 4.
			var id = aData[4];
			var detailLink = "details.html?" + $.param(lastQueryData);
			var link = '/arbeitsplatzsuche/room/' + id + '/' + detailLink;
			document.location = link;
		});
		
		$("#searchForm").submit(function(event){
			resultTable.fnDraw();
			event.preventDefault();
			return false;
		});
		
		$(window).resize(function () {
			// change result table height on window resize
			resultTable.fnSettings().oScroll.sY = calcTableHeight(); 
			resultTable.fnDraw();
		});


	});