package com.dynamo2.myerp.crm.ui.converter;

import java.math.BigInteger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.dynamo2.myerp.crm.dao.entities.Customer;
import com.dynamo2.myerp.crm.service.CustomerService;
import com.dynamo2.util.JSFUtils;

@FacesConverter("customerNameAndIdConverter")
public class CustomerNameAndIdConverter implements Converter {

	//@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if (arg2 == null) {
			return null;
		}

		if (arg2.equals("system")) {
			return 0;
		}

		String[] rst = arg2.split("\\(");
		if (rst.length > 0) {
			arg2 = rst[0].trim();
		}

		CustomerService customerService = JSFUtils.getSpringBean("customerService", CustomerService.class);

		Customer customer = customerService.findCustomerByName(arg2);

		if (null == customer) {
			JSFUtils.addMessagesToComponent(arg1.getClientId(), JSFUtils.getI18NMessage("cannot_find_customer"));

			return null;
		}

		return customer.getId();
	}

	//@Override
	public String getAsString(FacesContext ctx, UIComponent cmp, Object value) {
		CustomerService customerService = JSFUtils.getSpringBean("customerService", CustomerService.class);

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

				if (id == 0) {
					return "system";
				}

				Customer customer = customerService.loadById((Long) id);
				if (customer == null) {
					return "NA";
				}

				return customer.getName() + " (" + customer.getCustomerId() + ", " + JSFUtils.getI18NMessage(customer.getQuality()) + ")";
			} catch (NumberFormatException e) {
				return (String) value;
			}
		} else if (value instanceof Customer) {
			return ((Customer) value).getName();
		} else {
			return value.toString();
		}
	}

}
