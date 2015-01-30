package com.dynamo2.myerp;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import com.dynamo2.myerp.crm.dao.entities.Account;
import com.dynamo2.myerp.crm.service.AccountService;
import com.dynamo2.myerp.crm.service.AppSessionParams;
import com.dynamo2.myerp.crm.service.constant.Roles_ENUM;
import com.dynamo2.myerp.service.PERMISSION_DEFINITION_ENUM;
import com.dynamo2.myerp.service.ServiceException;
import com.dynamo2.util.JSFUtils;

public abstract class AbstractBaseService<T> implements AbstractBaseServiceInterface<T> {

	protected abstract AbstractBaseDAO<T> getDao();

	protected AppSessionParams getAppSessionParams() {
		return JSFUtils.getSpringBean("appSessionParams", AppSessionParams.class);
	}
	
	protected boolean hasPermission(PERMISSION_DEFINITION_ENUM perm) {
		if (getAppSessionParams() == null) {
			return false;
		}
		
		return getAppSessionParams().getPermissions().containsKey(perm.name());
	}
	
	protected Account getCurrentAccount() {
		return (Account) SecurityContextHolder.getContext().getAuthentication().getDetails();
	}
	
	protected String getCurrentAccountString() {
		return getCurrentAccount().getAccount();
	}

	public void newOrUpdate(T entity) throws ServiceException {
		getDao().saveOrUpdate(entity, getCurrentAccount().getAccount());

	}

	public void newOrUpdateBySystem(T entity) {
		getDao().saveOrUpdate(entity, AccountService.ACCOUNT_SYSTEM);
	}

	public T loadById(Long id) {
		return getDao().findById(id);
	}

	public void deleteById(Long id) {
		getDao().removeById(id);
	}

	public boolean isRoleSalesDirector() {
		return checkRole(Roles_ENUM.ROLE_SALES_DIRECTOR);
	}

	public boolean isRoleAdmin() {
		return checkRole(Roles_ENUM.ROLE_ADMIN);
	}

	public boolean isRoleSales() {
		return checkRole(Roles_ENUM.ROLE_SALES_DIRECTOR) || checkRole(Roles_ENUM.ROLE_SALES)
				|| checkRole(Roles_ENUM.ROLE_OUTSIDE_SALES) || checkRole(Roles_ENUM.ROLE_CUSTOMER_SERVICE);
	}

	@Deprecated
	public boolean isRoleCustomer() {
		return false;
	}

	protected boolean checkRole(Roles_ENUM role) {
		String roles = getCurrentAccount().getRoles();
		if (StringUtils.isBlank(roles)) {
			return false;
		}

		return roles.contains(role.toString());
	}

	public List<T> listAll() {
		return getDao().listAll();
	}

	public List<T> findByField(String fieldName, Object value) {
		return getDao().findByField(fieldName, value);
	}
	
	public List<T> findByFields(String[] fieldNames, Object[] values) {
		return getDao().findByFields(fieldNames, values);
	}
}