$(document).ready(function() {
	pollStatus();
});


function pollStatus()
{
	$.ajax({
		url: "/AuthTester/status",
		dataType: 'json',
		cache: false,
		error: function(data) {
			alert("Error: " + data);
		},
		success: function(data) {
			if (data.userId) {
				showBubble();
			}
				
			setTimeout(function() { pollStatus(); }, 1500);
		},
		contentType: 'application/json'
	});
}


function showBubble()
{
	$('.b3').removeClass('btn-warning').addClass('btn-primary');
	$('.bubble').fadeIn().delay(10000).fadeOut(2000, function() {
		$('.b3').removeClass('btn-primary').addClass('btn-warning');
	});
}