package com.dynamo2.myerp.dynamicform.ui.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.dynamo2.myerp.dynamicform.dao.entities.FormWorkflow;
import com.dynamo2.myerp.dynamicform.service.FormRecordStatus_ENUM;
import com.dynamo2.myerp.dynamicform.ui.controller.OrderMgmtBean;
import com.dynamo2.util.JSFUtils;

@FacesConverter("formWorkFlowStatusNameToLabelConverter")
public class FormWorkFlowStatusNameToLabelConverter implements Converter {

	//@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public String getAsString(FacesContext ctx, UIComponent cmp, Object value) {
		if (value == null || value.toString().isEmpty())
			return "NA";

		if (FormRecordStatus_ENUM.NEW.name().equalsIgnoreCase(value.toString())) {
			return JSFUtils.getI18NMessage(FormRecordStatus_ENUM.NEW.name());
		}

		if (FormRecordStatus_ENUM.BACK_REFINE.name().equalsIgnoreCase(value.toString())) {
			return JSFUtils.getI18NMessage(FormRecordStatus_ENUM.BACK_REFINE.name());
		}

		if (FormRecordStatus_ENUM.END.name().equalsIgnoreCase(value.toString())) {
			return JSFUtils.getI18NMessage(FormRecordStatus_ENUM.END.name());
		}

		if (FormRecordStatus_ENUM.INVALID.name().equalsIgnoreCase(value.toString())) {
			return JSFUtils.getI18NMessage(FormRecordStatus_ENUM.INVALID.name());
		}

		if (FormRecordStatus_ENUM.DELETED.name().equalsIgnoreCase(value.toString())) {
			return JSFUtils.getI18NMessage(FormRecordStatus_ENUM.DELETED.name());
		}

		OrderMgmtBean orderMgmtBean = JSFUtils.getManagementBean(ctx, OrderMgmtBean.class);
		List<FormWorkflow> steps = orderMgmtBean.getFormWorkflowSteps();
		for (FormWorkflow step : steps) {
			if (value.equals(step.getStatusName())) {
				return step.getStatusLabel();
			}
		}

		return "NA";
	}

}
