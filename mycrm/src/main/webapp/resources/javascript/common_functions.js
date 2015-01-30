/**
 * 
 */

function checkAjaxStatus(event) {
	if (event.status == 'success') {
		alert("Done | 完成");
	}
}

function ajaxEventFormConfigFieldEdit(event) {
	if (event.status == 'success') {
		newFieldDialog.show();
	}
}

function customerProfileAjax_showCustomerProfileFormDialog(event) {
	if (event.status == 'success') {
		customerProfileFormDialog.show();
	}
}

function ajaxEventFormConfigWorkFlowStepEdit(event) {
	if (event.status == 'success') {
		newWorkFlowStepDialog.show();
	}
}

function ajaxEventFormReportConfig_showConditionEditDialog(event) {
	if (event.status == 'success') {
		newConditionDialog.show();
	}
}

function ajaxEventFormReportConfig_showDateRangeConditionEditDialog(event) {
	if (event.status == 'success') {
		newDateRangeConditionDialog.show();
	}
}

function ajaxEventFormReportConfig_closeConditionEditDialog(event) {
	if (event.status == 'success') {
		newConditionDialog.hide();
	}
}

function ajaxEventFormReportConfig_closeDateRangeConditionEditDialog(event) {
	if (event.status == 'success') {
		newDateRangeConditionDialog.hide();
	}
}