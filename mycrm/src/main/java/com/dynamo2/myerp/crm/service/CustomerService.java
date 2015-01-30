package com.dynamo2.myerp.crm.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dynamo2.myerp.AbstractBaseServiceInterface;
import com.dynamo2.myerp.crm.dao.entities.Customer;
import com.dynamo2.myerp.crm.dao.entities.CustomerAccountRelationship;
import com.dynamo2.myerp.crm.dao.entities.CustomerRelationshipsGraph;

public interface CustomerService extends AbstractBaseServiceInterface<Customer> {

	enum CUSTOMER_RELATIONSHIP_TYPE {
		DISTRIBUTOR_2_CUSTOMER;
	}

	Customer loadByCustomerId(String customerId);

	List<String> listAllBoundAccounts(Long customerId);

	@Transactional
	void bindAccountWithCustomerId(CustomerAccountRelationship car);

	@Transactional
	void unBindAccountWithCustomerId(String account, Long customerId);

	List<Customer> listCustomersToMe();

	List<Customer> listDistributors();

	Customer findCustomerByName(String string);

	List<Customer> searchBy(Customer customer);

	List<Object[]> duplicationCheck(Customer obj);

	@Transactional
	void saveCustomersRelationship(CustomerRelationshipsGraph currentCustomerConnectAsRight);

	List<CustomerRelationshipsGraph> listCustomerOnRight(Long customerId, String type);

	List<CustomerRelationshipsGraph> listCustomerOnLeft(Long customerId, String type);

	@Transactional
	void disconnectCustomerRelationship(Long relationshipId);
}
