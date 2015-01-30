package com.dynamo2.myerp.crm.ui.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.dynamo2.util.JSFUtils;

@FacesConverter("tokenToLabelConverter")
public class TokenToLabelConverter implements Converter {

	//@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public String getAsString(FacesContext ctx, UIComponent cmp, Object value) {
		if (value == null || value.toString().isEmpty())
			return value.toString();
		return JSFUtils.getI18NMessage((String) value);
	}

}
