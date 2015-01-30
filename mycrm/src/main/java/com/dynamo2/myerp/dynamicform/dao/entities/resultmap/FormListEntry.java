package com.dynamo2.myerp.dynamicform.dao.entities.resultmap;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FormListEntry implements Serializable {

	@Id
	private long id;
	private String formLabel;
	private String status;
	private String createdBy;
	private String created;
	private String lastModifiedBy;
	private String lastModified;
	private String assigneeAccount;
	private String formBizId;
	private Long customerId;
	private String summary;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFormLabel() {
		return formLabel;
	}

	public void setFormLabel(String formLabel) {
		this.formLabel = formLabel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public String getAssigneeAccount() {
		return assigneeAccount;
	}

	public void setAssigneeAccount(String assigneeAccount) {
		this.assigneeAccount = assigneeAccount;
	}

	public String getFormBizId() {
		return formBizId;
	}

	public void setFormBizId(String formBizId) {
		this.formBizId = formBizId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
}
