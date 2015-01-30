package com.dynamo2.myerp.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.AbstractBaseService;
import com.dynamo2.myerp.crm.dao.EnumListDAO;
import com.dynamo2.myerp.crm.dao.entities.EnumList;

@Service("enumListService")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EnumListServiceImpl extends AbstractBaseService<EnumList> implements EnumListService {

	@Autowired
	private EnumListDAO dao;

	@Override
	protected AbstractBaseDAO<EnumList> getDao() {
		return dao;
	}

}
