package com.dynamo2.myerp.crm.ui.controller.customer;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dynamo2.myerp.crm.dao.entities.Customer;
import com.dynamo2.myerp.crm.service.CustomerService;
import com.dynamo2.util.JSFUtils;

@ManagedBean(name = "customersList")
@ViewScoped
public class CustomerListBean implements Serializable {

	private static Log LOGGER = LogFactory.getLog(CustomerListBean.class);

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{customerService}")
	private transient CustomerService customerService;

	private List<Customer> customers;

	private Customer customer;

	public List<Customer> getAllCustomers() {
		if (customers == null) {
			customers = customerService.listAll();
		}

		return customers;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public Customer getCustomer() {
		if (customer == null) {
			customer = new Customer();
		}
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void search() {
		customers = customerService.searchBy(customer);
		if (customers == null) {
			customers = Collections.emptyList();
		}
	}

	public void downloadResult() {

		List<String> ignoredFieldList = Arrays.asList("getLastModified", "getLastModifiedBy", "getCreated", "getId",
				"getCreatedBy", "getAgentAccount", "getCustomerSeqNum", "getClass", "getNotes", "getSalesAccount",
				"getOutsideSalesAccount", "getCustomerServiceAccount","getHearFrom");

		List<Method> methodList = new LinkedList<Method>();
		for (Method m : Customer.class.getMethods()) {
			if (m.getName().startsWith("get") && !ignoredFieldList.contains(m.getName()))
				methodList.add(m);
		}

		String[] header = new String[methodList.size()];
		int i = 0;
		for (Method m : methodList) {
			header[i++] = m.getName().substring(3);
		}

		List<String[]> data = new ArrayList<String[]>(customers.size());
		for (Customer c : customers) {
			String[] row = new String[methodList.size()];
			i = 0;
			for (Method m : methodList) {
				Object v;
				try {
					v = m.invoke(c);
					if (v != null) {
						row[i++] = v.toString();
					} else {
						row[i++] = "NA";
					}
				} catch (Exception e) {
					LOGGER.error("Export customer info failed.", e);
				}
			}
			data.add(row);
		}

		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmm");
		try {
			JSFUtils.downloadFileCSVFileUtf8("customer_export_" + sf.format(new Date()), header, data);
		} catch (IOException e) {
			LOGGER.error("Export customer info failed.", e);
		}
	}
}
