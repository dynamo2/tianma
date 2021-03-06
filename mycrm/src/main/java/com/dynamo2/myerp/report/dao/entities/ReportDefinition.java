package com.dynamo2.myerp.report.dao.entities;

// Generated Aug 23, 2012 5:04:28 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.dynamo2.myerp.report.definition.ReportQueryDefinition;
import com.thoughtworks.xstream.XStream;

/**
 * ReportDefinition generated by hbm2java
 */
@Entity
@Table(name = "report_definition")
public class ReportDefinition implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String reportLabel;
	private String reportDescription;
	private String reportDefinition;
	private Date created;
	private String createdBy;
	private Date lastModified;
	private String lastModifiedBy;

	@Transient
	private ReportQueryDefinition reportDefinitionObj;

	public ReportDefinition() {
	}

	public ReportDefinition(String reportLabel, String reportDefinition, Date lastModified) {
		this.reportLabel = reportLabel;
		this.reportDefinition = reportDefinition;
		this.lastModified = lastModified;
	}

	public ReportDefinition(String reportLabel, String reportDescription, String reportDefinition, Date created,
			String createdBy, Date lastModified, String lastModifiedBy) {
		this.reportLabel = reportLabel;
		this.reportDescription = reportDescription;
		this.reportDefinition = reportDefinition;
		this.created = created;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
		this.lastModifiedBy = lastModifiedBy;
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

	@Column(name = "report_label", nullable = false)
	public String getReportLabel() {
		return this.reportLabel;
	}

	public void setReportLabel(String reportLabel) {
		this.reportLabel = reportLabel;
	}

	@Column(name = "report_description", length = 65535)
	public String getReportDescription() {
		return this.reportDescription;
	}

	public void setReportDescription(String reportDescription) {
		this.reportDescription = reportDescription;
	}

	@Column(name = "report_definition", nullable = false, length = 65535)
	public String getReportDefinition() {
		return this.reportDefinition;
	}

	public void setReportDefinition(String reportDefinition) {
		this.reportDefinition = reportDefinition;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", length = 19)
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
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

	@Column(name = "last_modified_by", length = 45)
	public String getLastModifiedBy() {
		return this.lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	@Transient
	public ReportQueryDefinition getReportDefinitionObj() {
		if (reportDefinitionObj == null && reportDefinition != null) {
			XStream xstream = new XStream();
			reportDefinitionObj = (ReportQueryDefinition) xstream.fromXML(reportDefinition);
		}

		return reportDefinitionObj;
	}

	public void setReportDefinitionObj(ReportQueryDefinition reportDefinitionObj) {
		this.reportDefinitionObj = reportDefinitionObj;

		XStream xstream = new XStream();
		reportDefinition = xstream.toXML(reportDefinitionObj);
	}

}
