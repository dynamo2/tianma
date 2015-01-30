package com.dynamo2.myerp.crm.dao;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;
import javax.persistence.Query;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.crm.dao.entities.Account;
import com.dynamo2.myerp.crm.dao.entities.Customer;

@Repository("customerDAO")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CustomerDAO extends AbstractBaseDAO<Customer> {

	/**
	 * Save the max number of Customer Sequence number.
	 */
	public final AtomicLong maxCustomerSeqNumer = new AtomicLong();

	@PostConstruct
	public void init() {
		Query q = em.createNamedQuery("getMaxCustomerSeqNumer");
		Object value = q.getSingleResult();
		value = value == null ?"0":value;
		maxCustomerSeqNumer.set(Long.parseLong(value.toString()));
	}

	@Override
	public void saveOrUpdate(Customer ob, String currentAccount) {

		if (ob.getCustomerSeqNum() == null) {
			ob.setCustomerSeqNum(maxCustomerSeqNumer.incrementAndGet());
			if (ob.getIsDistributor()) {
				ob.setCustomerId(String.format("D%06d", ob.getCustomerSeqNum()));
			} else {
				ob.setCustomerId(String.format("C%06d", ob.getCustomerSeqNum()));
			}
		} else {
			if (ob.getIsDistributor() && ob.getCustomerId().startsWith("C")) {
				ob.setCustomerId(ob.getCustomerId().replace('C', 'D'));
			} else if (!ob.getIsDistributor() && ob.getCustomerId().startsWith("D")) {
				ob.setCustomerId(ob.getCustomerId().replace('D', 'C'));
			}
		}

		super.saveOrUpdate(ob, currentAccount);
	}

	@SuppressWarnings("unchecked")
	public List<Customer> listAllCustomers() {
		// TODO Optimize to return Account only.
		return em.createQuery("from Customer").getResultList();
	}

	public Customer findCustomerByName(String name) {
		Query q = em.createNamedQuery("findCustomerByName");
		q.setParameter(1, name);

		List rst = q.getResultList();
		if (rst == null || rst.isEmpty()) {
			return null;
		}

		return (Customer) rst.get(0);
	}

	public List<Customer> listDistributors() {
		Query q = em.createNamedQuery("listDistributors");
		return q.getResultList();
	}

	public List<Customer> searchBy(Customer customer, Account account) {
		StringBuilder hql = new StringBuilder();

		if (account != null) {
			hql.append("select c from CustomerAccountRelationship car left join car.customer c where car.boundAccount='"
					+ account.getAccount() + "'");
		} else {
			hql.append("from Customer c where 1=1");
		}

		if (customer.getCustomerId() != null && !customer.getCustomerId().trim().isEmpty()) {
			hql.append(" and c.customerId like '%" + customer.getCustomerId() + "%'");
		}

		if (customer.getName() != null && !customer.getName().trim().isEmpty()) {
			hql.append(" and c.name like '%" + customer.getName() + "%'");
		}

		if (customer.getType() != null && !customer.getType().trim().isEmpty()) {
			hql.append(" and c.type like '%" + customer.getType() + "%'");
		}

		if (customer.getIndustry() != null && !customer.getIndustry().trim().isEmpty()) {
			hql.append(" and c.industry like '%" + customer.getIndustry() + "%'");
		}

		if (customer.getQuality() != null && !customer.getQuality().trim().isEmpty()) {
			hql.append(" and c.quality = '" + customer.getQuality() + "'");
		}

		if (customer.getCity() != null && !customer.getCity().trim().isEmpty()) {
			hql.append(" and c.city like '%" + customer.getCity() + "%'");
		}

		if (customer.getArea() != null && !customer.getArea().trim().isEmpty()) {
			hql.append(" and c.area like '%" + customer.getArea() + "%'");
		}

		if (customer.getAgentAccount() != null && !customer.getAgentAccount().trim().isEmpty()) {
			hql.append(" and c.agentAccount = '" + customer.getAgentAccount() + "'");
		}

		if (customer.getSalesAccount() != null && !customer.getSalesAccount().trim().isEmpty()) {
			hql.append(" and c.salesAccount = '" + customer.getSalesAccount() + "'");
		}

		if (customer.getOutsideSalesAccount() != null && !customer.getOutsideSalesAccount().trim().isEmpty()) {
			hql.append(" and c.outsideSalesAccount = '" + customer.getOutsideSalesAccount() + "'");
		}

		if (customer.getCustomerServiceAccount() != null && !customer.getCustomerServiceAccount().trim().isEmpty()) {
			hql.append(" and c.customerServiceAccount = '" + customer.getCustomerServiceAccount() + "'");
		}

		hql.append(" order by c.created desc ");

		return em.createQuery(hql.toString()).getResultList();
	}

	public List<Object[]> checkDuplicatedCustomer(Customer customer) {

		Query q = em.createNativeQuery("{ call find_duplicated_customer(?,?,?,?,?,?,?,?,?,?,?) }");

		q.setParameter(1, customer.getId());
		q.setParameter(2, customer.getName());
		q.setParameter(3, customer.getAddress1());
		q.setParameter(4, customer.getAddress2());
		q.setParameter(5, customer.getAddress3());
		q.setParameter(6, customer.getFax1());
		q.setParameter(7, customer.getFax2());
		q.setParameter(8, customer.getFax3());
		q.setParameter(9, customer.getWebsite());
		q.setParameter(10, customer.getPhone());
		q.setParameter(11, customer.getEmail());

		// Return customer.id and customer.name
		return q.getResultList();
	}

	public Customer findCustomerByCustomerId(String customerId) {
		Query q = em.createNamedQuery("findCustomerByCustomerId");
		q.setParameter(1, customerId);

		List rst = q.getResultList();
		if (rst == null || rst.isEmpty()) {
			return null;
		}

		return (Customer) rst.get(0);
	}
}
