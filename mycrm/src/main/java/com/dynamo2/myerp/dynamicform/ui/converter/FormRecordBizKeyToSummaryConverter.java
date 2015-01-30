package com.dynamo2.myerp.dynamicform.ui.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.dynamo2.myerp.crm.ui.converter.AccountToDisplayNameConverter;
import com.dynamo2.myerp.dynamicform.dao.entities.DynamicFormDefault;
import com.dynamo2.myerp.dynamicform.service.FormMetadataService;
import com.dynamo2.util.JSFUtils;

@FacesConverter("formRecordBizKeyToSummaryConverter")
public class FormRecordBizKeyToSummaryConverter implements Converter {

	//@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public String getAsString(FacesContext ctx, UIComponent cmp, Object value) {
		if (value == null || value.toString().isEmpty() || !(value instanceof String))
			return "NA";

		FormMetadataService formMetadataService = JSFUtils.getSpringBean("formMetadataService",
				FormMetadataService.class);
		DynamicFormDefault form = formMetadataService.loadDynamicFormDefaultDataByBizKey((String) value);
		if (null != form) {
			FormWorkFlowStatusNameToLabelConverter statusToLabel = new FormWorkFlowStatusNameToLabelConverter();
			String statusLabel = statusToLabel.getAsString(ctx, cmp, form.getStatus());

			AccountToDisplayNameConverter accountConverter = new AccountToDisplayNameConverter();
			String accountInfo = accountConverter.getAsString(ctx, cmp, form.getAssigneeAccount());

			return " (" + value + " | " + JSFUtils.getI18NMessage("status") + " : " + statusLabel + " | "
					+ JSFUtils.getI18NMessage("form_wf_assigneeAccount") + " : " + accountInfo + ") ";
		}

		return "NA";
	}

}
