package com.dynamo2.myerp.crm.service;

import com.dynamo2.myerp.AbstractBaseServiceInterface;
import com.dynamo2.myerp.crm.dao.entities.EnumList;

public interface EnumListService extends AbstractBaseServiceInterface<EnumList> {
	public enum ENUMLIST_TYPE {
		CUSTOMER_SERVICE_STATUS;
	}
}
