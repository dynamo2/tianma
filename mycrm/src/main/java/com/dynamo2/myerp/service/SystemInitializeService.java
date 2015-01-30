package com.dynamo2.myerp.service;

import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dynamo2.myerp.crm.dao.entities.EnumList;
import com.dynamo2.myerp.crm.dao.entities.RoleToPermissions;
import com.dynamo2.myerp.crm.service.EnumListService;
import com.dynamo2.myerp.crm.service.RoleToPermissionsService;
import com.dynamo2.myerp.crm.service.constant.Roles_ENUM;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Lazy(value=false)
public class SystemInitializeService {

	@Autowired
	private RoleToPermissionsService roleToPermissionsService;
	
	@Autowired
	private EnumListService enumListService;
	
	private final static Log LOGGER = LogFactory.getLog(SystemInitializeService.class);
	
	@PostConstruct
	public void init() {
		initDefaultSystemRoles();
		initDefaultEnumListForCustomerServiceStatus();
	}

	private void initDefaultSystemRoles() {		
		for (Roles_ENUM role : Roles_ENUM.values()) {
			List<RoleToPermissions> rst = roleToPermissionsService.findByField("roleName", role.name());
			if (CollectionUtils.isEmpty(rst)) {
				RoleToPermissions r = new RoleToPermissions();
				r.setPermissions(role.getPermissions());
				r.setRoleName(role.name());
				roleToPermissionsService.newOrUpdateBySystem(r);
			}
		}
	}
	
	private void initDefaultEnumListForCustomerServiceStatus() {
		List<EnumList> rst = enumListService.findByField("type", EnumListService.ENUMLIST_TYPE.CUSTOMER_SERVICE_STATUS.name());
		if (CollectionUtils.isEmpty(rst)) {
			EnumList e = new EnumList();
			e.setType(EnumListService.ENUMLIST_TYPE.CUSTOMER_SERVICE_STATUS.name());
			e.setValue("请选择");
			e.setShowPosition(0);
			e.setLanguage(Locale.SIMPLIFIED_CHINESE.getLanguage());
			enumListService.newOrUpdateBySystem(e);
			
			e = new EnumList();
			e.setType(EnumListService.ENUMLIST_TYPE.CUSTOMER_SERVICE_STATUS.name());
			e.setValue("未处理");
			e.setShowPosition(1);
			e.setLanguage(Locale.SIMPLIFIED_CHINESE.getLanguage());
			enumListService.newOrUpdateBySystem(e);
			
			e = new EnumList();
			e.setType(EnumListService.ENUMLIST_TYPE.CUSTOMER_SERVICE_STATUS.name());
			e.setValue("处理中");
			e.setShowPosition(1);
			e.setLanguage(Locale.SIMPLIFIED_CHINESE.getLanguage());
			enumListService.newOrUpdateBySystem(e);
			
			e = new EnumList();
			e.setType(EnumListService.ENUMLIST_TYPE.CUSTOMER_SERVICE_STATUS.name());
			e.setValue("完成");
			e.setShowPosition(1);
			e.setLanguage(Locale.SIMPLIFIED_CHINESE.getLanguage());
			enumListService.newOrUpdateBySystem(e);
		}
	}

}
