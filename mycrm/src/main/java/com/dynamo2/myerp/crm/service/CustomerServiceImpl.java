package com.dynamo2.myerp.crm.service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.AbstractBaseService;
import com.dynamo2.myerp.crm.dao.CustomerAccountRelationshipDAO;
import com.dynamo2.myerp.crm.dao.CustomerDAO;
import com.dynamo2.myerp.crm.dao.CustomerRelationshipGraphDAO;
import com.dynamo2.myerp.crm.dao.entities.Customer;
import com.dynamo2.myerp.crm.dao.entities.CustomerAccountRelationship;
import com.dynamo2.myerp.crm.dao.entities.CustomerRelationshipsGraph;
import com.dynamo2.myerp.service.PERMISSION_DEFINITION_ENUM;
import com.dynamo2.myerp.service.ServiceException;

@Service("customerService")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CustomerServiceImpl extends AbstractBaseService<Customer> implements CustomerService {

	@Autowired
	private CustomerDAO customerDAO;

	@Autowired
	private CustomerRelationshipGraphDAO customerRelationshipGraphDAO;

	@Autowired
	private CustomerAccountRelationshipDAO customerAccountRelationshipDAO;

	public List<Customer> listAll() {
		if (hasPermission(PERMISSION_DEFINITION_ENUM.CUSTOMER_QUERY_ALL)) {
			return customerDAO.listAllCustomers();
		} else if (hasPermission(PERMISSION_DEFINITION_ENUM.CUSTOMER_QUERY_ASSINGED_TO_ME)) {
			return listCustomersToMe();
		}

		return Collections.emptyList();
	}

	//@Override
	public List<Customer> listCustomersToMe() {
		return customerAccountRelationshipDAO.listCustomersToMe(getCurrentAccount().getAccount());
	}

	@Override
	protected AbstractBaseDAO<Customer> getDao() {
		return customerDAO;
	}

	//@Override
	public List<String> listAllBoundAccounts(Long customerId) {
		return customerAccountRelationshipDAO.listAccountsBoundWithCustomer(customerId);
	}

	//@Override
	public void bindAccountWithCustomerId(CustomerAccountRelationship car) {
		customerAccountRelationshipDAO.saveOrUpdate(car, getCurrentAccount().getAccount());
	}

	//@Override
	public void unBindAccountWithCustomerId(String account, Long customerId) {
		customerAccountRelationshipDAO.deleteByCustomerIdAndAccountId(customerId, account);

	}

	@Override
	public void newOrUpdate(Customer customer) throws ServiceException {
		if ((customer.getId() == null) && !hasPermission(PERMISSION_DEFINITION_ENUM.CUSTOMER_NEW)) {
			throw new NOPermissionException();
		} else {
			if (!hasPermission(PERMISSION_DEFINITION_ENUM.CUSTOMER_EDIT_ALL)) {
				if (hasPermission(PERMISSION_DEFINITION_ENUM.CUSTOMER_EDIT_BY_SELF)
						|| hasPermission(PERMISSION_DEFINITION_ENUM.CUSTOMER_CHANGE_SERVICE_STATUS)) {
					if (!isAssignedToMe(customer)) {
						throw new NOPermissionException();
					}
				} else {
					throw new NOPermissionException();
				}
			}
		}

		if (customer.getIsDistributor()) {
			customer.setQuality("NA");
		}
		super.newOrUpdate(customer);

		// Replace the current customer object
		customer = customerDAO.findCustomerByName(customer.getName());

		saveTheRelationships(customer);
	}

	private void saveTheRelationships(Customer customer) {
		String[] boundAccounts = new String[] { customer.getSalesAccount(), customer.getCustomerServiceAccount(),
				customer.getAgentAccount(), customer.getOutsideSalesAccount() };

		for (String acct : boundAccounts) {
			if (acct != null && !acct.isEmpty()) {
				customerAccountRelationshipDAO.addCustomerAccountRelationship(customer.getId(), acct);
			}
		}
	}

	//@Override
	public Customer findCustomerByName(String customerName) {
		return customerDAO.findCustomerByName(customerName);
	}

	//@Override
	public List<Customer> searchBy(Customer customer) {
		if (hasPermission(PERMISSION_DEFINITION_ENUM.CUSTOMER_QUERY_ALL)) {
			return customerDAO.searchBy(customer, null);
		} else if (hasPermission(PERMISSION_DEFINITION_ENUM.CUSTOMER_QUERY_ASSINGED_TO_ME)) {
			return customerDAO.searchBy(customer, getCurrentAccount());
		}

		return Collections.emptyList();
	}

	//@Override
	public List<Object[]> duplicationCheck(Customer customer) {
		return customerDAO.checkDuplicatedCustomer(customer);
	}

	//@Override
	public Customer loadByCustomerId(String customerId) {
		return customerDAO.findCustomerByCustomerId(customerId);
	}

	//@Override
	public void saveCustomersRelationship(CustomerRelationshipsGraph customerRelationshipGraph) {

		List<CustomerRelationshipsGraph> rst = customerRelationshipGraphDAO.findRightByLeft(
				customerRelationshipGraph.getLeftCustomerId(), customerRelationshipGraph.getType());
		// This relationship already exsits.
		for (CustomerRelationshipsGraph r : rst) {
			if (r.getRightCustomerId().equals(customerRelationshipGraph.getRightCustomerId())) {
				return;
			}
		}

		customerRelationshipGraphDAO.saveOrUpdate(customerRelationshipGraph, getCurrentAccount().getAccount());
	}

	//@Override
	public List<Customer> listDistributors() {
		List<Customer> distributors = customerDAO.listDistributors();

		// Filter distributors
		if (hasPermission(PERMISSION_DEFINITION_ENUM.CUSTOMER_QUERY_ALL)) {
			return distributors;
		} else if (hasPermission(PERMISSION_DEFINITION_ENUM.CUSTOMER_QUERY_ASSINGED_TO_ME)) {
			List<Customer> rst = new LinkedList<Customer>();
			for (Customer customer : distributors) {
				if (isAssignedToMe(customer)) {
					rst.add(customer);
				}
			}
			return rst;
		} else {
			return Collections.emptyList();
		}
	}

	//@Override
	public List<CustomerRelationshipsGraph> listCustomerOnLeft(Long customerId, String type) {
		return customerRelationshipGraphDAO.findLeftByRight(customerId, type);
	}

	//@Override
	public List<CustomerRelationshipsGraph> listCustomerOnRight(Long customerId, String type) {
		return customerRelationshipGraphDAO.findRightByLeft(customerId, type);
	}

	//@Override
	public void disconnectCustomerRelationship(Long relationshipId) {
		customerRelationshipGraphDAO.removeById(relationshipId);
	}

	private boolean isAssignedToMe(Customer c) {
		return getCurrentAccountString().equals(c.getAgentAccount())
				|| getCurrentAccountString().equals(c.getSalesAccount())
				|| getCurrentAccountString().equals(c.getOutsideSalesAccount())
				|| getCurrentAccountString().equals(c.getCustomerServiceAccount());
	}
}
