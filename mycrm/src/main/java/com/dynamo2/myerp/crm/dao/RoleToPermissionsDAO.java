package com.dynamo2.myerp.crm.dao;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.crm.dao.entities.RoleToPermissions;

@Repository("roleToPermissionsDAO")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RoleToPermissionsDAO extends AbstractBaseDAO<RoleToPermissions> {

}
