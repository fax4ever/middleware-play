UispClient = function() {
	
	var submitForm = function(id) {
		JQuery("#"+id).submit();
	};
	
	return {
		submitForm : submitForm
	};	
		
}();