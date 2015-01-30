package com.dynamo2.myerp.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.AbstractBaseService;
import com.dynamo2.myerp.crm.dao.ContactDAO;
import com.dynamo2.myerp.crm.dao.entities.Contact;

@Service("contactService")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ContactServiceImpl extends AbstractBaseService<Contact> implements ContactService {

	@Autowired
	private ContactDAO contactDAO;

	public List<Contact> listAll(Long customerId) {
		return contactDAO.listAllByCustomerId(customerId);
	}

	@Override
	protected AbstractBaseDAO<Contact> getDao() {
		return contactDAO;
	}
}
