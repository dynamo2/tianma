package com.dynamo2.myerp.dynamicform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.AbstractBaseService;
import com.dynamo2.myerp.dynamicform.dao.EmailBoxDAO;
import com.dynamo2.myerp.dynamicform.dao.entities.EmailBox;

@Service("mailBoxService")
public class MailBoxServiceImpl extends AbstractBaseService<EmailBox> implements MailBoxService {
	@Autowired
	private EmailBoxDAO emailBoxDAO;

	@Override
	protected AbstractBaseDAO<EmailBox> getDao() {
		return emailBoxDAO;
	}

}
