package com.dynamo2.myerp.dynamicform.ui.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.dynamo2.myerp.dynamicform.dao.entities.FormMetadata;
import com.dynamo2.myerp.dynamicform.service.FormMetadataService;
import com.dynamo2.myerp.dynamicform.service.FormMetadataService.FORM_CATEGORY_ENUM;
import com.dynamo2.util.JSFUtils;

@ManagedBean(name = "dataCenterNavigationBarMgmtBean")
@SessionScoped
public class DataCenterNavigationBarMgmtBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{formMetadataService}")
	protected transient FormMetadataService formMetadataService;

	public class DataCenterNavigationItem {
		private String itemLabel;
		private List<FormMetadata> formMetatDataList;

		public String getItemLabel() {
			return itemLabel;
		}

		public void setItemLabel(String itemLabel) {
			this.itemLabel = itemLabel;
		}

		public List<FormMetadata> getFormMetatDataList() {
			return formMetatDataList;
		}

		public void setFormMetatDataList(List<FormMetadata> formMetatDataList) {
			this.formMetatDataList = formMetatDataList;
		}

		public DataCenterNavigationItem(String itemLabel, List<FormMetadata> formMetatDataList) {
			super();
			this.itemLabel = itemLabel;
			this.formMetatDataList = formMetatDataList;
		}
	}

	public List<DataCenterNavigationItem> getDataCenterNavigation() {
		FORM_CATEGORY_ENUM[] categatoris = FORM_CATEGORY_ENUM.values();
		List<DataCenterNavigationItem> items = new ArrayList<DataCenterNavigationBarMgmtBean.DataCenterNavigationItem>(
				categatoris.length);

		for (FORM_CATEGORY_ENUM cate : FORM_CATEGORY_ENUM.values()) {
			if (cate.isShowInNavigationBar()) {
				List<FormMetadata> formMetadataList = formMetadataService.listAllFormMetadataByCategory(cate);
				if (formMetadataList != null && !formMetadataList.isEmpty()) {
					items.add(new DataCenterNavigationItem(JSFUtils.getI18NMessage(cate.name()), formMetadataList));
				}
			}

		}

		return items;
	}

	public FormMetadataService getFormMetadataService() {
		return formMetadataService;
	}

	public void setFormMetadataService(FormMetadataService formMetadataService) {
		this.formMetadataService = formMetadataService;
	}

}
