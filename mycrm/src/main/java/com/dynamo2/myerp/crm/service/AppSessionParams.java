package com.dynamo2.myerp.crm.service;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.dynamo2.myerp.crm.dao.RoleToPermissionsDAO;
import com.dynamo2.myerp.crm.dao.entities.Account;
import com.dynamo2.myerp.crm.dao.entities.RoleToPermissions;
import com.dynamo2.myerp.crm.service.constant.Roles_ENUM;
import com.dynamo2.myerp.service.PERMISSION_DEFINITION_ENUM;

@Component("appSessionParams")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class AppSessionParams implements Serializable {

	private static final long serialVersionUID = 8669616292243637297L;
	private String loginUserName;
	private Locale locale;
	private Map<String, PERMISSION_DEFINITION_ENUM> permissions;
	private static final Log LOGGER = LogFactory.getLog(AppSessionParams.class);

	@Autowired
	private transient RoleToPermissionsDAO roleToPermissionsDAO;

	public Account getCurrentAccount() {
		return (Account) SecurityContextHolder.getContext().getAuthentication().getDetails();
	}

	public Map<String, PERMISSION_DEFINITION_ENUM> getPermissions() {
		if (permissions == null) {
			permissions = new LinkedHashMap<String, PERMISSION_DEFINITION_ENUM>();
			Account acct = getCurrentAccount();
			List<String> roles = acct.getRoleList();
			
			// Get all permission for supervisor role
			if (roles.contains(Roles_ENUM.ROLE_SUPERVISOR.name())) {
				List<PERMISSION_DEFINITION_ENUM> permList = Roles_ENUM.ROLE_SUPERVISOR.getPermissions();
				for (PERMISSION_DEFINITION_ENUM p : permList) {
					permissions.put(p.name(), p);
				}

				return permissions;
			}

			for (String role : roles) {
				List<RoleToPermissions> ps = roleToPermissionsDAO.findByField("roleName", role);
				if (CollectionUtils.isNotEmpty(ps)) {
					RoleToPermissions roleToPermissions = ps.get(0);
					List<String> permissionStrList = roleToPermissions.getPermissionList();
					if (CollectionUtils.isNotEmpty(permissionStrList)) {
						for (String pstr : permissionStrList) {
							try {
								// Add permission
								PERMISSION_DEFINITION_ENUM p = PERMISSION_DEFINITION_ENUM.valueOf(pstr);

								permissions.put(p.name(), p);

								// Add base permissions of current
								List<PERMISSION_DEFINITION_ENUM> bp = p.getBasePermissions();
								if (CollectionUtils.isNotEmpty(bp)) {
									for (PERMISSION_DEFINITION_ENUM baseP : bp) {
										if (!permissions.containsKey(baseP.name())) {
											permissions.put(baseP.name(), baseP);
										}
									}
								}
							} catch (IllegalArgumentException e) {
								LOGGER.warn("Permission [" + pstr + "] is not exist in system.");
							}
						}
					}
				}
			}
		}

		return permissions;
	}

	public Locale getLocale() {
		if (locale == null) {
			String language = getCurrentAccount().getLanguage();
			if (language == null || language.isEmpty()) {
				locale = Locale.SIMPLIFIED_CHINESE;
			} else {
				try {
					if ("zh".equals(language) || "zh_cn".equalsIgnoreCase(language)) {
						locale = Locale.SIMPLIFIED_CHINESE;
					} else {
						locale = new Locale(language);
					}
				} catch (Exception e) {
					locale = Locale.SIMPLIFIED_CHINESE;
				}
			}
		}

		return locale;
	}

	public TimeZone getTimezone() {
		return TimeZone.getTimeZone("GMT+8:00");
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public boolean isRoleSalesDirector() {
		return checkRole(Roles_ENUM.ROLE_SALES_DIRECTOR);
	}

	public boolean isRoleAdmin() {
		return checkRole(Roles_ENUM.ROLE_ADMIN);
	}

	@Deprecated
	public boolean isRoleCustomer() {
		return false;
	}

	public boolean isRoleBusinessExpert() {
		return checkRole(Roles_ENUM.ROLE_BUSINESS_EXPERT);
	}

	public boolean isRoleFinanceDirector() {
		return checkRole(Roles_ENUM.ROLE_FINANCE_DIRECTOR);
	}

	public boolean isRoleSales() {
		return checkRole(Roles_ENUM.ROLE_SALES_DIRECTOR) || checkRole(Roles_ENUM.ROLE_SALES)
				|| checkRole(Roles_ENUM.ROLE_OUTSIDE_SALES) || checkRole(Roles_ENUM.ROLE_CUSTOMER_SERVICE);
	}

	public boolean isRoleCustomerService() {
		return checkRole(Roles_ENUM.ROLE_CUSTOMER_SERVICE)
				&& !(checkRole(Roles_ENUM.ROLE_SALES_DIRECTOR) || checkRole(Roles_ENUM.ROLE_SALES) || checkRole(Roles_ENUM.ROLE_OUTSIDE_SALES));
	}

	private boolean checkRole(Roles_ENUM role) {
		return getCurrentAccount().getRoles().contains(role.toString());
	}
	
	public boolean hasPermission(PERMISSION_DEFINITION_ENUM perm) {
		return getPermissions().containsKey(perm.name());
	}
}
