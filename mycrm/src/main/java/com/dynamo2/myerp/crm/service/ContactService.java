package com.dynamo2.myerp.crm.service;

import java.util.List;

import com.dynamo2.myerp.AbstractBaseServiceInterface;
import com.dynamo2.myerp.crm.dao.entities.Contact;

public interface ContactService extends AbstractBaseServiceInterface<Contact> {

	public List<Contact> listAll(Long customerId);
}
