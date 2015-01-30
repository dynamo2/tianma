package com.dynamo2.myerp.crm.ui.converter;

import java.math.BigInteger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.dynamo2.myerp.crm.dao.entities.EnumList;
import com.dynamo2.myerp.crm.service.EnumListService;
import com.dynamo2.util.JSFUtils;

@FacesConverter("enumListIdAndValueConverter")
public class EnumListIdAndValueConverter implements Converter {

	//@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		throw new UnsupportedOperationException();
	}

	//@Override
	public String getAsString(FacesContext ctx, UIComponent cmp, Object value) {
		EnumListService enumListService = JSFUtils.getSpringBean("enumListService", EnumListService.class);

		if (value == null) {
			return "NA";
		}

		if (value instanceof Long || value instanceof String || value instanceof BigInteger) {

			try {
				Long id;
				if (value instanceof String) {
					id = Long.parseLong((String) value);
				} else if (value instanceof BigInteger) {
					id = ((BigInteger) value).longValue();
				} else {
					id = (Long) value;
				}

				if (id != 0) {
					EnumList e = enumListService.loadById(id);
					if (e != null)
						return e.getValue();
				}
				return "NA";
			} catch (NumberFormatException e) {
				return "NA";
			}
		}

		return "NA";
	}

}
