package com.dynamo2.myerp.crm.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.crm.dao.entities.Customer;
import com.dynamo2.myerp.crm.dao.entities.CustomerAccountRelationship;

@Repository
public class CustomerAccountRelationshipDAO extends AbstractBaseDAO<CustomerAccountRelationship> {

	public List<String> listAccountsBoundWithCustomer(Long customerId) {
		return list("listAccountsBoundWithCustomer", new Object[] { customerId });
	}

	public void deleteByCustomerIdAndAccountId(Long customerId, String account) {
		executeSQLName("deleteByCustomerIdAndAccount", new Object[] { customerId, account });
	}

	public List<Customer> listCustomersToMe(String account) {
		return list("listCustomersToMe", new Object[] { account });
	}

	public void addCustomerAccountRelationship(Long customerId, String account) {
		Query q = em.createNativeQuery("{CALL add_customer_account_relationship(?,?)}");
		q.setParameter(1, customerId);
		q.setParameter(2, account);
		q.executeUpdate();
	}
}
