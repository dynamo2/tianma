package com.dynamo2.myerp.crm.dao.entities.resultmap;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CustomerTodo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long customerId;
	private String customerName;
	private Date planStart;
	private Date planEnd;
	private Date actualStart;
	private Date actualEnd;
	private String title;
	private String assigneeAccount;
	private String assignerAccount;
	private String createdBy;

	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getPlanStart() {
		return planStart;
	}

	public void setPlanStart(Date planStart) {
		this.planStart = planStart;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPlanEnd() {
		return planEnd;
	}

	public void setPlanEnd(Date planEnd) {
		this.planEnd = planEnd;
	}

	public Date getActualStart() {
		return actualStart;
	}

	public void setActualStart(Date actualStart) {
		this.actualStart = actualStart;
	}

	public Date getActualEnd() {
		return actualEnd;
	}

	public void setActualEnd(Date actualEnd) {
		this.actualEnd = actualEnd;
	}

	public String getAssigneeAccount() {
		return assigneeAccount;
	}

	public void setAssigneeAccount(String assigneeAccount) {
		this.assigneeAccount = assigneeAccount;
	}

	public String getAssignerAccount() {
		return assignerAccount;
	}

	public void setAssignerAccount(String assignerAccount) {
		this.assignerAccount = assignerAccount;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
