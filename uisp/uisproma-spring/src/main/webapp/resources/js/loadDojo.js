dojo.addOnLoad(function() {
	var items = dojo.query("#selectSolutionsForm select");
	
	items.forEach(function(element) {
		Spring.addDecoration(new Spring.AjaxEventDecoration({
			elementId : element.id,
			event : "onchange",
			formId: "selectSolutionsForm"
		}));
	});
});