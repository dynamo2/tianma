package com.dynamo2.myerp.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.MapUtils;

import com.dynamo2.myerp.crm.service.AppSessionParams;

public enum PERMISSION_DEFINITION_ENUM {
	/** Permission for customer */
	CUSTOMER(null, true),
	/** Permission for customer query */
	CUSTOMER_QUERY(CUSTOMER, true),
	/** Permission for customer edit */
	CUSTOMER_EDIT(CUSTOMER, true),
	/** Permission for customer all */
	CUSTOMER_QUERY_ALL(CUSTOMER_QUERY, false),
	/** Permission for customer assigned to me */
	CUSTOMER_QUERY_ASSINGED_TO_ME(CUSTOMER_QUERY, false),
	/** Permission for customer new */
	CUSTOMER_NEW(CUSTOMER_EDIT, false),
	/** Permission for customer edit any */
	CUSTOMER_EDIT_ALL(CUSTOMER_EDIT, false),
	/** Permission for customer edit to me */
	CUSTOMER_EDIT_BY_SELF(CUSTOMER_EDIT, false),
	/** Permission for Assign Customer to a account */
	CUSTOMER_ASSIGN_TO(CUSTOMER, false),
	/** Permission for Assign Customer to a account */
	CUSTOMER_CONNECT_TO_DISTRIBUTOR(CUSTOMER, false),
	/** Permission for new contact */
	CUSTOMER_NEW_CONTACT(CUSTOMER, false),
	/** Permission for view contact */
	CUSTOMER_VIEW_CONTACT(CUSTOMER, false),
	/** Permission for delete contact */
	CUSTOMER_DELETE_CONTACT(CUSTOMER, false),
	/** Change service status */
	CUSTOMER_CHANGE_SERVICE_STATUS(CUSTOMER, false),
	
	/** Permission of Notes */
	NOTES(null, true), NOTES_QUERY(NOTES, true), NOTES_EDIT(NOTES, true), NOTES_QUERY_ALL(NOTES_QUERY, false), NOTES_QUERY_WITH_ROLE(
			NOTES_QUERY, false), NOTES_NEW(NOTES_EDIT, false),
			
	/** Permission of TODO */
	TODO(null, true),
	/** Create new TODO */
	TODO_NEW(TODO, false),

	/** Permission of Account */
	ACCOUNT(null, true), 
	/** Permission of Account */
	ACCOUNT_QUERY(ACCOUNT, true),
	/** Permission of Account */
	ACCOUNT_EDIT(ACCOUNT, true),
	/** Permission of Account */
	ACCOUNT_NEW(ACCOUNT_EDIT, false),
	/** Permission of Account */
	ACOUNT_QUERY_ALL(ACCOUNT_QUERY, false),
	/** Permission of Account */
	ACCOUNT_DELETE(ACCOUNT_EDIT, false),

	/** Permission of Business Form */
	FORM(null, true),
	/** Permission of Business Form */
	FORM_QUERY(FORM, true),
	/** Permission of Business Form */
	FORM_EDIT(FORM, true),
	/** Permission of Business Form */
	FORM_NEW(FORM_EDIT, false),
	/** Permission of Business Form */
	FORM_EDIT_ALL(FORM_EDIT, false),
	/** Permission of Business Form */
	FORM_EDIT_BY_SELF(FORM_EDIT, false),
	/** Permission of Business Form */
	FORM_QUERY_BY_SELF(FORM_QUERY, false),
	/** Permission of Business Form Data */
	FORM_DATA(null, true),
	/** Permission of Business Form Data */
	FORM_DATA_QUERY_ALL(FORM_DATA, false),

	/** Permission of System Role Management */
	SYSTEM_ROLE(null, true), SYSTEM_ROLE_QUERY(SYSTEM_ROLE, true), SYSTEM_ROLE_EDIT(SYSTEM_ROLE, false), SYSTEM_ROLE_NEW(
			SYSTEM_ROLE_EDIT, false),

	/** Permission of System Report */
	REPORT(null, true), REPORT_QUERY(REPORT, true), REPORT_EDIT(REPORT, false), REPORT_NEW(REPORT_EDIT, false),

	/** Dumy Permission */
	EMPTY(null, true);

	public final static List<String> allPermissions;

	private PERMISSION_DEFINITION_ENUM basePermission;

	private boolean abstractPermission;

	static {
		allPermissions = new ArrayList<String>(PERMISSION_DEFINITION_ENUM.values().length);
		for (PERMISSION_DEFINITION_ENUM p : PERMISSION_DEFINITION_ENUM.values()) {
			if (!p.abstractPermission)
				allPermissions.add(p.name());
		}
	}

	private PERMISSION_DEFINITION_ENUM(PERMISSION_DEFINITION_ENUM basePermission, boolean abstractPermission) {
		this.basePermission = basePermission;
		this.abstractPermission = abstractPermission;
	}

	public PERMISSION_DEFINITION_ENUM getBasePermission() {
		return basePermission;
	}

	public boolean isAbstractPermission() {
		return abstractPermission;
	}

	public boolean equal(PERMISSION_DEFINITION_ENUM pd) {
		return pd.name().equals(this.name());
	}

	public List<PERMISSION_DEFINITION_ENUM> getBasePermissions() {
		List<PERMISSION_DEFINITION_ENUM> bases = new LinkedList<PERMISSION_DEFINITION_ENUM>();
		PERMISSION_DEFINITION_ENUM c = this;
		while (c.basePermission != null) {
			bases.add(c.basePermission);
			c = c.basePermission;
		}

		return bases;
	}

	public boolean hasPermission(AppSessionParams sess) {
		if (sess == null || MapUtils.isEmpty(sess.getPermissions())) {
			return false;
		}

		return sess.getPermissions().containsKey(this.name());
	}
}
