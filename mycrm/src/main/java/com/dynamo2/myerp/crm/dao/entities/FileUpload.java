package com.dynamo2.myerp.crm.dao.entities;

// Generated Jan 8, 2013 11:28:19 AM by Hibernate Tools 3.4.0.CR1

import com.dynamo2.myerp.dynamicform.dao.DynamicFormDefaultDAO;

import java.util.Date;
import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * FileUpload generated by hbm2java
 */
@Entity
@Table(name = "file_upload")
@NamedQueries(value = {
        @NamedQuery(name = "findFilesByCustomerAndAccount", query = "from FileUpload f where f.customerId=? " +
                "and (f.forType='CUSTOMER' or (f.forType='FORM' and f.forId in (" +
                        "select d.id from DynamicFormDefault d where d.customerId=? and (d.createdBy = ? or d.lastModifiedBy = ? or d.assigneeAccount = ?))))")})
public class FileUpload implements java.io.Serializable {

	private Long id;
	private Long customerId;
	private String forType;
	private Long forId;
	private String keywords;
	private String comments;
	private String filename;
	private String absolutePath;
	private String fileDownloadUrl;
	private String folders;
	private Date created;
	private String lastModifiedBy;
	private String createdBy;
	private Date lastModified;

	public FileUpload() {
	}

	public FileUpload(String absolutePath, String folders, Date lastModified) {
		this.absolutePath = absolutePath;
		this.folders = folders;
		this.lastModified = lastModified;
	}

	public FileUpload(Long customerId, String forType, Long forId, String keywords, String comments, String filename,
			String absolutePath, String fileDownloadUrl, String folders, Date created, String lastModifiedBy,
			String createdBy, Date lastModified) {
		this.customerId = customerId;
		this.forType = forType;
		this.forId = forId;
		this.keywords = keywords;
		this.comments = comments;
		this.filename = filename;
		this.absolutePath = absolutePath;
		this.fileDownloadUrl = fileDownloadUrl;
		this.folders = folders;
		this.created = created;
		this.lastModifiedBy = lastModifiedBy;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "customer_id")
	public Long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Column(name = "for_type", length = 45)
	public String getForType() {
		return this.forType;
	}

	public void setForType(String forType) {
		this.forType = forType;
	}

	@Column(name = "for_id")
	public Long getForId() {
		return this.forId;
	}

	public void setForId(Long forId) {
		this.forId = forId;
	}

	@Column(name = "keywords", length = 45)
	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Column(name = "comments", length = 45)
	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Column(name = "filename", length = 45)
	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "absolute_path", nullable = false)
	public String getAbsolutePath() {
		return this.absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	@Column(name = "file_download_url")
	public String getFileDownloadUrl() {
		return this.fileDownloadUrl;
	}

	public void setFileDownloadUrl(String fileDownloadUrl) {
		this.fileDownloadUrl = fileDownloadUrl;
	}

	@Column(name = "folders", nullable = false)
	public String getFolders() {
		return this.folders;
	}

	public void setFolders(String folders) {
		this.folders = folders;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", length = 19)
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Column(name = "last_modified_by", length = 45)
	public String getLastModifiedBy() {
		return this.lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	@Column(name = "created_by", length = 45)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_modified", nullable = false, length = 19)
	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

}
