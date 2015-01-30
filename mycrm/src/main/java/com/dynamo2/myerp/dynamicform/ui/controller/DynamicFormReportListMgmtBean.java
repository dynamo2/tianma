package com.dynamo2.myerp.dynamicform.ui.controller;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.icefaces.bean.ViewRetained;
import org.icefaces.bean.WindowDisposed;

import com.dynamo2.myerp.report.dao.entities.ReportDefinition;
import com.dynamo2.myerp.report.service.ReportService;

@ManagedBean(name = "dynamicFormReportListMgmtBean")
@ViewScoped
@ViewRetained
@WindowDisposed
public class DynamicFormReportListMgmtBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<ReportDefinition> reportList;

	private Long formMetadataId;

	@ManagedProperty(value = "#{reportService}")
	private ReportService reportService;

	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public List<ReportDefinition> getReportList() {
		if (reportList == null) {
			reportList = reportService.listAll();
			if (reportList == null) {
				reportList = Collections.emptyList();
			}
		}

		return reportList;
	}

	public String createReport() {
		return "form_report_config.jsf?faces-redirect=true&formMetadataId=" + formMetadataId;
	}

	public Long getFormMetadataId() {
		return formMetadataId;
	}

	public void setFormMetadataId(Long formMetadataId) {
		this.formMetadataId = formMetadataId;
	}

}
