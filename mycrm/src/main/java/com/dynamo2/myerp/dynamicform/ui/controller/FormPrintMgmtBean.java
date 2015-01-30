package com.dynamo2.myerp.dynamicform.ui.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;

import org.apache.commons.collections.CollectionUtils;

import com.dynamo2.myerp.crm.dao.entities.Contact;
import com.dynamo2.myerp.crm.dao.entities.Customer;
import com.dynamo2.myerp.crm.ui.controller.AbstractManagementBean;
import com.dynamo2.myerp.dynamicform.dao.entities.DynamicFormDefault;
import com.dynamo2.myerp.dynamicform.dao.entities.FormFieldMetadata;
import com.dynamo2.myerp.dynamicform.dao.entities.FormMetadata;
import com.dynamo2.myerp.dynamicform.service.FormMetadataService;
import com.icesoft.faces.component.ext.HtmlPanelGrid;

@ManagedBean(name = "formPrintMgmtBean")
@RequestScoped
public class FormPrintMgmtBean extends AbstractManagementBean implements Serializable {

	private static final String EMPTY_STRING = "                ";

	private static final long serialVersionUID = 1L;

	private Long formMetadataId;

	private Long formRecordId;

	private HtmlPanelGroup printContent;

	private FormMetadata formMetadata;

	private Map<String, Object> formRecordValueMap;

	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	private Customer customer;
	
	private Contact majorContact;
	
	public Map<String, Object> getFormRecordValueMap() {
		if (formRecordValueMap == null) {
			DynamicFormDefault formRecordObj = formMetadataService.loadDynamicFormDefaultDataById(formRecordId);
			formRecordValueMap = formMetadataService.convertDynamicFormDefaultToMap(formRecordObj);
		}
		return formRecordValueMap;
	}

	public Customer getCustomer() {
		if (customer == null) {
			DynamicFormDefault r = formMetadataService.loadDynamicFormDefaultDataById(formRecordId);
			customer = customerService.loadById(r.getCustomerId());
		}
		
		return customer;
	}
	
	public Contact getMajorContact() {
		if (majorContact == null) {
			Customer c = getCustomer();
			List<Contact> contacts = contactService.listAll(c.getId());
			if (!CollectionUtils.isEmpty(contacts)) {
				majorContact = contacts.get(0);
			}
		}
		
		return majorContact;
	}

	public void setFormRecordValueMap(Map<String, Object> formRecordValueMap) {
		this.formRecordValueMap = formRecordValueMap;
	}

	public FormMetadata getFormMetadata() {
		if (formMetadata == null) {
			formMetadata = formMetadataService.loadById(formMetadataId);
		}
		return formMetadata;
	}

	public void setFormMetadata(FormMetadata formMetadata) {
		this.formMetadata = formMetadata;
	}

	public FormMetadataService getFormMetadataService() {
		return formMetadataService;
	}

	public void setFormMetadataService(FormMetadataService formMetadataService) {
		this.formMetadataService = formMetadataService;
	}

	public Long getFormMetadataId() {
		return formMetadataId;
	}

	public void setFormMetadataId(Long formMetadataId) {
		this.formMetadataId = formMetadataId;
	}

	public Long getFormRecordId() {
		return formRecordId;
	}

	public void setFormRecordId(Long formRecordId) {
		this.formRecordId = formRecordId;
	}

	public HtmlPanelGroup getPrintContent() {
		if (null == printContent) {
			buildPrintContent();
		}

		return printContent;
	}

	public void setPrintContent(HtmlPanelGroup printContent) {
		this.printContent = printContent;
	}

	public void buildPrintContent() {
		printContent = new HtmlPanelGroup();
		printContent.setLayout("block");
		List<UIComponent> printContentChild = printContent.getChildren();

		List<List<List<FormFieldMetadata>>> groupedFieldMetadataList = formMetadataService
				.extractGroupedFieldMetadataList(formMetadataId);

		Map<String, Object> formRecordValueMap = getFormRecordValueMap();

		for (List<List<FormFieldMetadata>> group : groupedFieldMetadataList) {
			HtmlPanelGroup divForTable = new HtmlPanelGroup();
			divForTable.setLayout("block");
			divForTable.setStyle("border-bottom:1px solid #000000; width:100%; margin-top:20px; padding:5px;");

			HtmlPanelGrid table = new HtmlPanelGrid();
			table.setStyle("padding:2px;");
			table.setColumnClasses("td_min_width");
			table.setCellpadding("2px");
			int maxFieldCounts = extractMaxFieldsCountInRow(group);
			table.setColumns(maxFieldCounts * 2);
			List<UIComponent> tableChildrens = table.getChildren();
			for (List<FormFieldMetadata> row : group) {
				addOneRowToTable(row, tableChildrens, maxFieldCounts, formRecordValueMap);
			}

			if (table.getChildCount() > 0) {
				divForTable.getChildren().add(table);
				printContentChild.add(divForTable);
			}
		}
	}

	private int extractMaxFieldsCountInRow(List<List<FormFieldMetadata>> group) {
		int maxCount = 0;
		for (List<FormFieldMetadata> row : group) {
			maxCount = maxCount > row.size() ? maxCount : row.size();
		}

		return maxCount;
	}

	private void addOneRowToTable(List<FormFieldMetadata> row, List<UIComponent> tableChildrens, int maxFieldCounts,
			Map<String, Object> formRecordValueMap) {
		for (FormFieldMetadata field : row) {
			// Skip the reference field and non-printable field
			if ((field.getReferToFormMetadataId() != null && field.getReferToFormMetadataId() > 0)
					|| !field.getIsPrintable()) {
				continue;
			}

			HtmlOutputLabel label = new HtmlOutputLabel();
			label.setValue(field.getFieldLabel());
			HtmlOutputText text = new HtmlOutputText();
			Object value = formRecordValueMap.get(field.getColumnName());
			if (value instanceof Date) {
				value = simpleDateFormat.format(value);
			}
			text.setValue(value == null ? EMPTY_STRING : value.toString());
			tableChildrens.add(label);
			tableChildrens.add(text);
		}

		for (int i = row.size(); i < maxFieldCounts; i++) {
			HtmlOutputText emptyLabel = new HtmlOutputText();
			emptyLabel.setValue(EMPTY_STRING);
			HtmlOutputText emptyValue = new HtmlOutputText();
			emptyValue.setValue(EMPTY_STRING);
			tableChildrens.add(emptyLabel);
			tableChildrens.add(emptyValue);
		}
	}
}
