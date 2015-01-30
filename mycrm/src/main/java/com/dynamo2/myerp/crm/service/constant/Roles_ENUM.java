package com.dynamo2.myerp.crm.service.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dynamo2.myerp.service.PERMISSION_DEFINITION_ENUM;

/**
 * Default Roles
 * @author fwang
 *
 */
public enum Roles_ENUM {
	ROLE_SUPERVISOR(Arrays.asList(PERMISSION_DEFINITION_ENUM.values())),
	ROLE_ADMIN(Arrays.asList(
			PERMISSION_DEFINITION_ENUM.ACCOUNT_NEW, 
			PERMISSION_DEFINITION_ENUM.ACCOUNT_EDIT,
			PERMISSION_DEFINITION_ENUM.ACOUNT_QUERY_ALL,
			PERMISSION_DEFINITION_ENUM.ACCOUNT_DELETE)), 
	ROLE_SALES(Arrays.asList(
			PERMISSION_DEFINITION_ENUM.CUSTOMER_NEW, 
			PERMISSION_DEFINITION_ENUM.CUSTOMER_EDIT_BY_SELF,
			PERMISSION_DEFINITION_ENUM.CUSTOMER_QUERY_ASSINGED_TO_ME,
			PERMISSION_DEFINITION_ENUM.NOTES_NEW,
			PERMISSION_DEFINITION_ENUM.NOTES_QUERY_WITH_ROLE)), 
	ROLE_OUTSIDE_SALES(ROLE_SALES.getPermissions()),
	ROLE_DISTRIBUTOR(ROLE_SALES.getPermissions()),
	ROLE_SALES_DIRECTOR(ROLE_SALES.getPermissions()), 
	ROLE_CUSTOMER_SERVICE(Arrays.asList(
			PERMISSION_DEFINITION_ENUM.CUSTOMER_QUERY_ASSINGED_TO_ME,
			PERMISSION_DEFINITION_ENUM.NOTES_NEW,
			PERMISSION_DEFINITION_ENUM.NOTES_QUERY_WITH_ROLE)), 
	ROLE_FINANCE(Arrays.asList(
			PERMISSION_DEFINITION_ENUM.CUSTOMER_QUERY_ALL, 
			PERMISSION_DEFINITION_ENUM.NOTES_QUERY_ALL)), 
	ROLE_FINANCE_DIRECTOR(ROLE_FINANCE.getPermissions()),  
	ROLE_BUSINESS_EXPERT(Arrays.asList(
			PERMISSION_DEFINITION_ENUM.FORM_NEW, 
			PERMISSION_DEFINITION_ENUM.FORM_EDIT_BY_SELF,
			PERMISSION_DEFINITION_ENUM.FORM_QUERY_BY_SELF)),
	ROLE_EMPTY(Arrays.asList(
			PERMISSION_DEFINITION_ENUM.EMPTY));
			
	

	private static Log LOGGER = LogFactory.getLog(Roles_ENUM.class);
	
	private List<PERMISSION_DEFINITION_ENUM> permissions;

	private Roles_ENUM(List<PERMISSION_DEFINITION_ENUM> ps) {
		permissions = ps;
	}
	
	public static boolean contains(String roleStr) {

		try {
			Roles_ENUM.valueOf(roleStr);
			return true;
		} catch (IllegalArgumentException e) {
			LOGGER.warn("Unexpected role for [" + roleStr + "]");
			return false;
		}
	}

	public List<PERMISSION_DEFINITION_ENUM> getPermissions() {
		return Collections.unmodifiableList(permissions);
	}
	
	
}
