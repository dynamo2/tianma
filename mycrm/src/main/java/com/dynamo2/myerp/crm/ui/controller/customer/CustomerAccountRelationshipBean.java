package com.dynamo2.myerp.crm.ui.controller.customer;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;

import com.dynamo2.myerp.crm.dao.entities.CustomerAccountRelationship;
import com.dynamo2.myerp.crm.service.CustomerService;

public class CustomerAccountRelationshipBean {

	@ManagedProperty(value = "#{customerService}")
	private CustomerService customerService;

	private CustomerAccountRelationship car;

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostConstruct
	void init() {
		car = new CustomerAccountRelationship();
	}

	public CustomerAccountRelationship getCar() {
		return car;
	}

	public void setCar(CustomerAccountRelationship car) {
		this.car = car;
	}

}
