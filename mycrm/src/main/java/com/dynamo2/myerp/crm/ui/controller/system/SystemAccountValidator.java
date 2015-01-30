package com.dynamo2.myerp.crm.ui.controller.system;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.dynamo2.myerp.crm.service.AccountService;
import com.dynamo2.util.JSFUtils;

@FacesValidator("systemAccountValidator")
public class SystemAccountValidator implements Validator {

	//@Override
	public void validate(FacesContext cnt, UIComponent cmp, Object account) throws ValidatorException {

		AccountService accountService = JSFUtils.getSpringBean("accountService", AccountService.class);
		if (accountService.findByAccount(account.toString()) != null) {
			FacesMessage msg = new FacesMessage(JSFUtils.getI18NMessage("duplicatedAccount"));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

	}

}
