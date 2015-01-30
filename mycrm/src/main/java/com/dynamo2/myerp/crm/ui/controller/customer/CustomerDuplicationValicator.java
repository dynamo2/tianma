package com.dynamo2.myerp.crm.ui.controller.customer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.dynamo2.myerp.crm.dao.entities.Customer;
import com.dynamo2.myerp.crm.service.CustomerService;
import com.dynamo2.util.JSFUtils;

@FacesValidator("customerDuplicationValidator")
public class CustomerDuplicationValicator implements Validator {

	//@Override
	public void validate(FacesContext cnt, UIComponent cmp, Object obj) throws ValidatorException {

		CustomerService customerService = JSFUtils.getSpringBean("customerService", CustomerService.class);

		customerService.duplicationCheck((Customer) obj);

	}

}
