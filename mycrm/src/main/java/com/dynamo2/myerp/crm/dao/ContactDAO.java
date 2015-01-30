package com.dynamo2.myerp.crm.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.crm.dao.entities.Contact;

@Repository("contactDAO")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ContactDAO extends AbstractBaseDAO<Contact> {

	@SuppressWarnings("unchecked")
	public List<Contact> listAllByCustomerId(Long customerId) {
		Query query = em.createQuery("select cnt, person from Contact cnt "
				+ "left join cnt.person person where cnt.customerId=?");
		query.setParameter(1, customerId);

		List<Object[]> rst = query.getResultList();
		if (rst == null || rst.isEmpty()) {
			return Collections.emptyList();
		}

		List<Contact> contacts = new ArrayList<Contact>();
		for (Object[] objs : rst) {
			contacts.add((Contact) objs[0]);
		}

		return contacts;
	}

}
