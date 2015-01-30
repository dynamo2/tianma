package com.dynamo2.myerp.crm.ui.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.dynamo2.myerp.crm.dao.entities.Account;
import com.dynamo2.myerp.crm.dao.entities.Person;
import com.dynamo2.myerp.crm.service.AccountService;
import com.dynamo2.util.JSFUtils;

@FacesConverter("accountToDisplayNameConverter")
public class AccountToDisplayNameConverter implements Converter {

	//@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public String getAsString(FacesContext ctx, UIComponent cmp, Object value) {
		if (value == null || value.toString().isEmpty())
			return "NA";

		AccountService accountService = JSFUtils.getSpringBean("accountService", AccountService.class);
		if (accountService == null) {
			return "NA";
		}

		Account account = accountService.findByAccount(value.toString());
		if (account == null) {
			return "NA";
		}

		String displayName;
		Person p = account.getPerson();
		if (p == null || p.getFirstName() == null || p.getLastName() == null) {
			displayName = account.getAccount();
		} else {
			displayName = account.getPerson().getFirstName() + " " + account.getPerson().getLastName();
		}

		if (account.getAgentCompanyName() != null && !account.getAgentCompanyName().isEmpty()) {
			displayName += " [" + account.getAgentCompanyName() + "]";
		}

		return displayName;
	}

}
