$(function () {
	var bindEvents = function() {
		var submit = $('#selectSolutionsForm select');

		submit.on('change', function() {
			var form = $('#selectSolutionsForm');
			var event = '_eventId_refresh';
			var data = form.serialize() + '&' + event + '=';

			$.ajax({ 
				type: "POST",
				dataType: 'text',
		    	url: form.attr( 'action' ),
		    	data: data,
	        	success : function(result) {
		        	$('#selectedSolutions').replaceWith($(result).find('#selectedSolutions'));
		        	bindEvents();
		        }
			});
			return false;
		});
	};
	
	bindEvents();
});