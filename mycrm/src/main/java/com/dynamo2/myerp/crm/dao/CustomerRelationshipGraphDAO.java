package com.dynamo2.myerp.crm.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.crm.dao.entities.CustomerRelationshipsGraph;

@Repository("customerRelationshipGraphDAO")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CustomerRelationshipGraphDAO extends AbstractBaseDAO<CustomerRelationshipsGraph> {

	@SuppressWarnings("unchecked")
	public List<CustomerRelationshipsGraph> findRightByLeft(Long leftCustomerId, String type) {
		Query q = em.createNamedQuery("findRightByLeft");
		q.setParameter(1, leftCustomerId);
		q.setParameter(2, type);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<CustomerRelationshipsGraph> findLeftByRight(Long rightCustomerId, String type) {
		Query q = em.createNamedQuery("findLeftByRight");
		q.setParameter(1, rightCustomerId);
		q.setParameter(2, type);
		return q.getResultList();
	}
}
