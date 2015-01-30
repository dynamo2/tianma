package com.dynamo2.myerp.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.AbstractBaseService;
import com.dynamo2.myerp.crm.dao.HelpContentDAO;
import com.dynamo2.myerp.crm.dao.entities.HelpContent;

@Service
public class HelpContentServiceImpl extends AbstractBaseService<HelpContent> implements HelpContentService {

	@Autowired
	private HelpContentDAO dao;

	@Override
	protected AbstractBaseDAO<HelpContent> getDao() {
		return dao;
	}

}
