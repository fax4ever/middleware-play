var SportRoma = function() {

	var initJTable = function() {
		$(".jtable > thead > tr > th").each(function() {

			$(this).addClass("ui-state-default");

		});
		$(".jtable > tbody > tr > td").each(function() {

			$(this).addClass("ui-widget-content");

		});
	};

	var initJSelect = function() {
		$(".jselect tr").hover(function() {
			$(this).children("td").addClass("ui-state-hover");
		}, function() {
			$(this).children("td").removeClass("ui-state-hover");
		});

		$(".jselect tr").click(function() {
			$(this).children("td").toggleClass("ui-state-highlight");
		});
	};

	var initBodyButton = function() {
		$('.button').button();
		$('.buttonset').buttonset();
	};

	var initDate = function() {
		$(".date").datepicker();
	};
	
	var initSelect = function() {
		$(".select:enabled").chosen({
			allow_single_deselect : true
		});
		$(".middleselect:enabled").chosen({
			allow_single_deselect : true
		});
		$(".miniselect:enabled").chosen({
			allow_single_deselect : true
		});
	};
	
	var resetSelect = function(id) {
		$("#"+id).show();
		$("#"+id+"_chzn").remove();
	};

	return {

		initTable : function() {
			initJTable();
			initJSelect();
			initBodyButton();
			initDate();
		},
		
		initSelect : initSelect,
		resetSelect : resetSelect
	};

}();

$(function() {

	// Menu
	$('#toolbar input').button();
	$('#toolbar a').button();

	// Dialog
	$('#dialog').dialog({
		autoOpen : false,
		title : "Cancellare l'elemento?",
		buttons : {
			"Si" : function() {
				$('.delete').click();
				$(this).dialog("close");
			},
			"Annulla" : function() {
				$(this).dialog("close");
			}
		}
	});

	$('#ciao').hide();

	// Dialog Link
	$('.dialog_link').click(function() {
		$('#dialog').dialog('open');
		return false;
	});

	if ($('.ciao').length > 0) {
		jsf.ajax.addOnEvent(function(data) {
			if (data.status === 'success') {
				SportRoma.initTable();
			}
		});
	}
	
	if ($('.spartaco').length > 0) {
		jsf.ajax.addOnEvent(function(data) {
			if (data.status === 'success') {
				SportRoma.initSelect();
			}
		});
	}

	SportRoma.initTable();

});
