package com.dynamo2.myerp.dynamicform.ui.controller;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.dynamo2.myerp.dynamicform.dao.entities.FormMetadata;
import com.dynamo2.myerp.dynamicform.service.FormMetadataService;
import com.dynamo2.util.JSFUtils;

@FacesValidator("dynamicFormNameValidator")
public class DynamicFormNameValidator implements Validator {

	//@Override
	public void validate(FacesContext cnt, UIComponent cmp, Object obj) throws ValidatorException {
		if (null == obj || obj.toString().isEmpty()) {
			return;
		}

		FormMetadataService formMetadataService = JSFUtils.getSpringBean("formMetadataService",
				FormMetadataService.class);

		FormMetadata form = formMetadataService.findFormMetadataByName(obj.toString());
		if (form != null) {
			FacesMessage msg = new FacesMessage(JSFUtils.getI18NMessage("duplicatedFormName"));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}
}
