package com.dynamo2.myerp.dynamicform.ui.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.dynamo2.myerp.dynamicform.dao.entities.FormMetadata;
import com.dynamo2.myerp.dynamicform.service.FormMetadataService;
import com.dynamo2.util.JSFUtils;

@FacesConverter("formMetadataIdToLabelConverter")
public class FormMetadataIdToLabelConverter implements Converter {

	//@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public String getAsString(FacesContext ctx, UIComponent cmp, Object value) {
		if (value == null || value.toString().isEmpty() || !(value instanceof Long))
			return "NA";

		FormMetadataService formMetadataService = JSFUtils.getSpringBean("formMetadataService",
				FormMetadataService.class);
		FormMetadata form = formMetadataService.loadById((Long) value);
		if (null != form) {
			return form.getFormLabel();
		}

		return "NA";
	}

}
