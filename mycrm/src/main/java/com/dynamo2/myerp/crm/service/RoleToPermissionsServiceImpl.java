package com.dynamo2.myerp.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.AbstractBaseService;
import com.dynamo2.myerp.crm.dao.RoleToPermissionsDAO;
import com.dynamo2.myerp.crm.dao.entities.RoleToPermissions;

@Service("roleToPermissionsService")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RoleToPermissionsServiceImpl extends AbstractBaseService<RoleToPermissions> implements RoleToPermissionsService {

	@Autowired
	private RoleToPermissionsDAO roleToPermissionsDAO;

	@Override
	protected AbstractBaseDAO<RoleToPermissions> getDao() {
		return roleToPermissionsDAO;
	}


}
