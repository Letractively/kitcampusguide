$.setUpRoomDetails = function(calJSONUrl) {

	// Set up workplace Table:
	$("#workplaces").dataTable({
		"aoColumns" : [ {
			"bSortable" : false
		}, // Checkboxes
		null, null ],
		"bSortClasses" : false,
		"bPaginate" : false,
		"bJQueryUI" : true,
		"sDom" : 'lrtp'
	});

	$("#calendar").weekCalendar({
		data : function(start, end, callback) {
			$.getJSON(calJSONUrl, {
				start : start.toString(),
				end : end.toString()
			}, function(result) {
				callback(result);
			});
		},
		timeslotsPerHour : 4,
		height : function($calendar) {
			return 600;
		},
		use24Hour : true,
		readonly : true,
		businessHours : {
			start : 8,
			end : 22,
			limitDisplay : true
		},
		overlapEventsSeparate : true,
		allowCalEventOverlap : true,
		eventRender : function(calEvent, $event) {
			if (calEvent.wholeRoom == 1) {
				$event.css("background-color", "#ff4a4a");
				$event.find(".wc-time").css({
					"backgroundColor" : "#ff2424",
					"border" : "1px solid #e00000"
				});
			} else {
				$event.css("background-color", "#ffee5c");
				$event.find(".wc-time").css({
					"backgroundColor" : "#ffe933",
					"border" : "1px solid #ffe404"
				});
			}
		},
	});

	var updateWorkplaceCount = function() {
		var count = $('input.workplace-checkbox:checked').size();
		var text = (count == 1 ? count + ' Platz' : count + " Plätze");
		$('span.workplaceCount').text(text);
	};

	$('input.workplace-checkbox').change(updateWorkplaceCount);

	updateWorkplaceCount();

};
