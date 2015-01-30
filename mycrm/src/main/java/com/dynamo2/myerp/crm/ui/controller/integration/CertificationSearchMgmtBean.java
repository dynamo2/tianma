package com.dynamo2.myerp.crm.ui.controller.integration;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.dynamo2.myerp.crm.service.CustomerService;
import com.dynamo2.myerp.dynamicform.service.FormMetadataService;

@ManagedBean(name = "certificationSearchMgmtBean")
@RequestScoped
public class CertificationSearchMgmtBean {

	@ManagedProperty(value = "#{formMetadataService}")
	protected transient FormMetadataService formMetadataService;

	@ManagedProperty(value = "#{customerService}")
	protected transient CustomerService customerService;

	private Object[] searchResult;

	// Certificate_SN varchar 证书编号 位置-组:0 | 位置-行:0 | 位置-列:0 1 | 60
	// custom_varchar_1 true
	// Certificate_Verification_Code varchar 证书校验码 位置-组:0 | 位置-行:1 | 位置-列:0 1 |
	// 60 custom_varchar_2 true
	// Certificate_Type varchar 证书类型 位置-组:0 | 位置-行:4 | 位置-列:0 1 | 60
	// custom_varchar_5 true
	// Registration_Date datetime 证书登记日期 位置-组:0 | 位置-行:5 | 位置-列:0 0 | 0
	// custom_datetime_1 true
	// Expiration_Date datetime 证书有效期至 位置-组:0 | 位置-行:5 | 位置-列:1 0 | 0
	// custom_datetime_2 true
	// Owner_Tel varchar 持证单位联系电话 位置-组:0 | 位置-行:6 | 位置-列:0 1 | 60
	// custom_varchar_6 true
	// Owner_Add varchar 持证单位所在地 位置-组:0 | 位置-行:7 | 位置-列:0 1 | 60
	// custom_varchar_7 true
	// Warrant_Info varchar 授权内容 位置-组:0 | 位置-行:8 | 位置-列:0 2 | 80
	// custom_varchar_8 true

	private String certificationSN;
	private String verficationCode;

	public String getCertificationSN() {
		return certificationSN;
	}

	public void setCertificationSN(String certificationSN) {
		this.certificationSN = certificationSN;
	}

	public String getVerficationCode() {
		return verficationCode;
	}

	public void setVerficationCode(String verficationCode) {
		this.verficationCode = verficationCode;
	}

	public void search() {
		if (searchResult != null) {
			searchResult = null;
		}

		searchResult = formMetadataService.searchCertification(certificationSN, verficationCode);

	}

	public FormMetadataService getFormMetadataService() {
		return formMetadataService;
	}

	public void setFormMetadataService(FormMetadataService formMetadataService) {
		this.formMetadataService = formMetadataService;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public Object[] getSearchResult() {
		return searchResult;
	}
}
