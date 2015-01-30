package com.dynamo2.myerp.dynamicform.ui.controller;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;

import com.dynamo2.myerp.crm.dao.entities.Customer;
import com.dynamo2.myerp.dynamicform.ui.view.GroupView;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.icefaces.bean.ViewRetained;
import org.icefaces.bean.WindowDisposed;

import com.dynamo2.myerp.crm.dao.entities.Account;
import com.dynamo2.myerp.crm.dao.entities.FileUpload;
import com.dynamo2.myerp.crm.service.AppSessionParams;
import com.dynamo2.myerp.crm.service.constant.FILE_FOR_ENUM;
import com.dynamo2.myerp.crm.ui.controller.AbstractManagementBean;
import com.dynamo2.myerp.dynamicform.dao.entities.DynamicFormDefault;
import com.dynamo2.myerp.dynamicform.dao.entities.DynamicFormDefaultAuditLog;
import com.dynamo2.myerp.dynamicform.dao.entities.EmailBox;
import com.dynamo2.myerp.dynamicform.dao.entities.FormFieldMetadata;
import com.dynamo2.myerp.dynamicform.dao.entities.FormMetadata;
import com.dynamo2.myerp.dynamicform.dao.entities.FormWorkflow;
import com.dynamo2.myerp.dynamicform.dao.entities.resultmap.FormListEntry;
import com.dynamo2.myerp.dynamicform.service.FormFieldTypes_ENUM;
import com.dynamo2.myerp.dynamicform.service.FormMetadataService;
import com.dynamo2.myerp.dynamicform.service.FormMetadataStatus_ENUM;
import com.dynamo2.myerp.dynamicform.service.FormRecordStatus_ENUM;
import com.dynamo2.myerp.dynamicform.service.MailSenderBBean;
import com.dynamo2.myerp.service.ServiceException;
import com.dynamo2.util.JSFUtils;

@ManagedBean(name = "orderMgmtBean")
@ViewScoped
@ViewRetained
@WindowDisposed
public class OrderMgmtBean extends AbstractManagementBean implements
		Serializable {
	private static final long serialVersionUID = 1L;

	private FormMetadata formMetadata;
	private List<FormFieldMetadata> formFieldMetadataList;
	private Long formMetadataId;
	private List<FormMetadata> publishedFormMetadataList;

	/* Parent form record to current one. */
	private Long parentFormRecordId;

	/* Parent form record field column name to hold current one's business key */
	private String parentFormFieldColumnName;

	/* Form record business key */
	private String formBizKey;

	// List for published form with customer_profile category.
	// This list will be display in Customer Database tab in Customer Management
	// UI
	private List<FormMetadata> publishedCustomerProfileFormMetadataList;

	// Group -> Row -> Column
	private List<List<List<FormFieldMetadata>>> groupedFields;

    private GroupView groupView;

	private Map<String, Object> columnValue = new HashMap<String, Object>();
	private List<FormListEntry> formDataList;
	// It's actually form id.
	private Long orderId;
	private DynamicFormDefault order;
	private DynamicFormDefault orderSearchCriteria;
	private Map<String, String> searchCriteriaValueMap = new HashMap<String, String>();
	private List<FormFieldMetadata> searchCriteriaFields = new LinkedList<FormFieldMetadata>();
	private Long customerId;
    private Customer customer;

	// List to hold all creatable form metadata
	private List<FormMetadata> creatableFormMetadata;

	// List of form Audit log
	private List<DynamicFormDefaultAuditLog> formAuditLog;

	// List of work flow steps of a form
	private List<FormWorkflow> formWorkflowSteps;

	// List of work flow steps which are mapped by step name
	private Map<String, FormWorkflow> workflowStepsMappedByStepName;

	// List of work flow steps which are mapped by status name
	private Map<String, FormWorkflow> workflowStepsMappedByStatusName;

	// List of work flow step position index in formWorkflowSteps by step name
	private Map<String, Integer> workflowStepIndexMappedByStepName;

	private HtmlPanelGrid dataSearchPanelGrid = new HtmlPanelGrid();

	private String printReviewPage;

	private Properties printTemplates;

	private List<FileUpload> attachedFiles;

	@PostConstruct
	public void init() {
		printTemplates = new Properties();
		try {
			printTemplates.load(this.getClass().getClassLoader()
					.getResourceAsStream("/form_print_template.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public AppSessionParams getAppSessionParams() {
		return appSessionParams;
	}

	public void setAppSessionParams(AppSessionParams appSessionParams) {
		this.appSessionParams = appSessionParams;
	}

	public Long getOrderId() {
		if (orderId == null && formBizKey != null) {
			DynamicFormDefault o = getOrder();
			if (o != null) {
				orderId = o.getId();
			} else {
				formBizKey = null;
			}
		}
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public List<FormListEntry> getFormDataList() {
		if (null == formDataList && formMetadataId != null) {
			listFormData();
		}

		return formDataList;
	}

	public FormMetadata getFormMetadata() {
		if (formMetadata == null && formMetadataId != null) {
			formMetadata = formMetadataService.loadById(formMetadataId);
		}
		return formMetadata;
	}

	public void setFormMetadata(FormMetadata formMetadata) {
		this.formMetadata = formMetadata;
	}

	public List<FormFieldMetadata> getFormFieldMetadataList() {
		return formFieldMetadataList;
	}

	public void setFormFieldMetadataList(
			List<FormFieldMetadata> formFieldMetadataList) {
		this.formFieldMetadataList = formFieldMetadataList;
	}

	public Long getFormMetadataId() {
		return formMetadataId;
	}

	public void setFormMetadataId(Long formMetadataId) {
		this.formMetadataId = formMetadataId;
	}

	public FormMetadataService getFormMetadataService() {
		return formMetadataService;
	}

	public void setFormMetadataService(FormMetadataService formMetadataService) {
		this.formMetadataService = formMetadataService;
	}

	public List<FormMetadata> getPublishedFormMetadataList() {
		if (null == publishedFormMetadataList) {
			List<FormMetadata> forms = formMetadataService
					.listAllFormMetadata();
			publishedFormMetadataList = new ArrayList<FormMetadata>(
					forms.size());
			for (FormMetadata form : forms) {
				if (FormMetadataStatus_ENUM.PUBLISHED.name().equalsIgnoreCase(
						form.getFormStatus())) {
					publishedFormMetadataList.add(form);
				}
			}
		}
		return publishedFormMetadataList;
	}

	public List<FormMetadata> getPublishedCreatableFormMetadataList() {
		if (creatableFormMetadata != null) {
			return creatableFormMetadata;
		}

		creatableFormMetadata = new LinkedList<FormMetadata>();
		List<FormMetadata> forms = getPublishedFormMetadataList();
		for (FormMetadata formMetadata : forms) {
			if (formMetadata.getCanCreateAccounts().contains(
					appSessionParams.getCurrentAccount().getAccount())) {
				creatableFormMetadata.add(formMetadata);
			}
		}

		return creatableFormMetadata;
	}

	public void setPublishedFormMetadataList(
			List<FormMetadata> publishedFormMetadataList) {
		this.publishedFormMetadataList = publishedFormMetadataList;
	}

	public void swithForm() {
		formMetadata = formMetadataService.loadById(formMetadataId);
		formFieldMetadataList = formMetadataService
				.listAllFieldsOfFormMetadata(formMetadataId);

		if (formFieldMetadataList == null || formFieldMetadataList.isEmpty()) {
			groupedFields = null;
			return;
		}

		groupedFields = new ArrayList<List<List<FormFieldMetadata>>>();
		int currentGroupNumber = formFieldMetadataList.get(0)
				.getDisplayPositionGroup();
		int currentRowNumber = formFieldMetadataList.get(0)
				.getDisplayPositionRow();
		List<FormFieldMetadata> currentRow = new ArrayList<FormFieldMetadata>();
		List<List<FormFieldMetadata>> currentGroup = new ArrayList<List<FormFieldMetadata>>();
		currentGroup.add(currentRow);
		groupedFields.add(currentGroup);
		for (FormFieldMetadata field : formFieldMetadataList) {
			columnValue.put(field.getColumnName(), null);

			if (currentGroupNumber != field.getDisplayPositionGroup()) {
				currentGroup = new ArrayList<List<FormFieldMetadata>>();
				groupedFields.add(currentGroup);
				currentGroupNumber = field.getDisplayPositionGroup();

				currentRow = new ArrayList<FormFieldMetadata>();
				currentGroup.add(currentRow);
				currentRowNumber = field.getDisplayPositionRow();
			}

			if (currentRowNumber == field.getDisplayPositionRow()) {
				currentRow.add(field);
			} else {
				currentRow = new ArrayList<FormFieldMetadata>();
				currentGroup.add(currentRow);
				currentRowNumber = field.getDisplayPositionRow();
				currentRow.add(field);
			}
		}
	}

    public GroupView getGroupView(){
        if(this.groupView == null){
            if (orderId != null) {
                formMetadataId = getOrder().getFormMetadataId();

                formMetadata = formMetadataService.loadById(formMetadataId);
                formFieldMetadataList = formMetadataService.listAllFieldsOfFormMetadata(formMetadataId);

                this.groupView = new GroupView(this.formFieldMetadataList);
                fillinData(getOrder());
            } else if (formMetadataId != null) {

                formMetadata = formMetadataService.loadById(formMetadataId);
                formFieldMetadataList = formMetadataService.listAllFieldsOfFormMetadata(formMetadataId);

                this.groupView = new GroupView(this.formFieldMetadataList);
                fillinData(getOrder());
            }
        }

        return this.groupView;
    }

	public List<List<List<FormFieldMetadata>>> getGroupedFields() {

		if (groupedFields == null) {
			if (orderId != null) {
				formMetadataId = getOrder().getFormMetadataId();
				swithForm();
				fillinData(getOrder());
			} else if (formMetadataId != null) {
				swithForm();
				fillinData(getOrder());
			}
		}

		return groupedFields;
	}

	private void fillinData(DynamicFormDefault order) {
		DynamicFormDefault parentForm = null;

		if (order.getParentFormBizId() != null) {
			parentForm = formMetadataService
					.loadDynamicFormDefaultDataByBizKey(order
							.getParentFormBizId());
		} else {
			Long parentFormId = getParentFormRecordId();
			if (parentFormId != null && parentFormId > 0L) {
				parentForm = formMetadataService
						.loadDynamicFormDefaultDataById(parentFormId);
			}
		}

		formMetadataService.fillInDefaultValue(order, parentForm);

		columnValue = formMetadataService.convertDynamicFormDefaultToMap(order);
	}

	public Map<String, Object> getColumnValue() {
		return columnValue;
	}

	public void saveOrder() {
        setSubmitedCustomerToOrder();

		formMetadataService.addOrUpdateFormDefault(formMetadata, columnValue,
				order);

		// Update the current form record's business key to parent form record
		if (parentFormRecordId != null && parentFormFieldColumnName != null) {
			formMetadataService.updateParentFormRecord(parentFormRecordId,
					parentFormFieldColumnName, order);
		}

		JSFUtils.addMessagesToComponent("newOrderForm:saveOrderMenuItem",
				JSFUtils.getI18NMessage("operationFinishedSuccessfully"));

		columnValue.clear();
		fillinData(getOrder());

		orderId = getOrder().getId();

		// Send modified form data's email
		sendModifyFormDataEmail();
	}

	public void saveCustomerProfile() {
		order.setCustomerId(customerId);
		saveOrder();
	}

	public DynamicFormDefault getOrder() {
		if (order == null) {
			if (orderId == null && formBizKey == null) {
				order = new DynamicFormDefault();
				if (appSessionParams.isRoleCustomer()) {
					order.setCustomerId(appSessionParams.getCurrentAccount()
							.getCustomerId());
				}
				order.setCustomerId(customerId);
				order.setFormMetadataId(formMetadataId);
			} else if (formBizKey != null) {
				order = formMetadataService
						.loadDynamicFormDefaultDataByBizKey(formBizKey);
				formMetadataId = order.getFormMetadataId();
			} else {
				order = formMetadataService
						.loadDynamicFormDefaultDataById(orderId);
				formMetadataId = order.getFormMetadataId();
			}
		}

		return order;
	}

	public void assignTo() {
		formMetadataService.changeFormDefaultAssignTo(order);

		JSFUtils.addMessagesToComponent("newOrderForm:assignOrderTo",
				JSFUtils.getI18NMessage("operationFinishedSuccessfully"));
	}

	public boolean isToMeOrder() {
		return appSessionParams.getCurrentAccount().getAccount()
				.equals(order.getAssigneeAccount());
	}

	public void changeStatus() {
		formMetadataService.changeFormDefaultStatus(order);

		JSFUtils.addMessagesToComponent("newOrderForm:changeOrderStatus",
				JSFUtils.getI18NMessage("operationFinishedSuccessfully"));
	}

	public DynamicFormDefault getOrderSearchCriteria() {
		return orderSearchCriteria;
	}

	public void setOrderSearchCriteria(DynamicFormDefault orderSearchCriteria) {
		this.orderSearchCriteria = orderSearchCriteria;
	}

	public void listFormData() {
//        JSFUtils.getHttpRequestParam("id");
		if (formMetadataId == 0) {
			formMetadataId = null;
			formDataList = null;
		} else {
			if (customerId != null && customerId != 0) {
				formDataList = formMetadataService.listFormDefaultForCustomer(
						formMetadataId, customerId);
			} else {
				customerId = null;
				formDataList = formMetadataService
						.listFormDefault(formMetadataId);
			}

			replaceStatusToLabel(formDataList);
		}
	}

	private void replaceStatusToLabel(List<FormListEntry> forms) {
		getFormWorkflowSteps();

		for (FormListEntry f : forms) {
			FormWorkflow step = null;
			if (workflowStepsMappedByStatusName != null) {
				step = workflowStepsMappedByStatusName.get(f.getStatus());
			}

			if (step != null) {
				f.setStatus(step.getStatusLabel());
			} else {
				f.setStatus(JSFUtils.getI18NMessage(f.getStatus()));
			}
		}

	}

	public HtmlPanelGrid getDataSearchPanelGrid() {
		return dataSearchPanelGrid;
	}

	public void showSearchCriteria() {

		List<FormFieldMetadata> fields = formFieldMetadataList = formMetadataService
				.listAllFieldsOfFormMetadata(formMetadataId);

		for (FormFieldMetadata f : fields) {
			FormFieldTypes_ENUM t = FormFieldTypes_ENUM.valueOf(f
					.getFieldType().toUpperCase());
			switch (t) {
			case VARCHAR:
				searchCriteriaValueMap.put(f.getColumnName(), null);
				searchCriteriaFields.add(f);
				break;

			default:
				break;
			}
		}
	}

	public Map<String, String> getSearchCriteriaValueMap() {
		return searchCriteriaValueMap;
	}

	public List<FormFieldMetadata> getSearchCriteriaFields() {
		return searchCriteriaFields;
	}

	public void search() {
		formDataList = formMetadataService.searchByCriteria(
				searchCriteriaValueMap,null, formMetadataId);
	}

	public List<DynamicFormDefaultAuditLog> getFormAuditLog() {

		if (orderId != null && formAuditLog == null) {
			formAuditLog = formMetadataService.listAuditLog(orderId);
		}

		return formAuditLog;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public List<FormMetadata> getPublishedCustomerProfileFormMetadataList() {
		if (publishedCustomerProfileFormMetadataList == null) {
			publishedCustomerProfileFormMetadataList = formMetadataService
					.listAllFormMetadata();
		}

		return publishedCustomerProfileFormMetadataList;
	}

	public void changeCurrentFormId() {
		orderId = Long.parseLong(JSFUtils.getHttpRequestParam("formId"));
		order = formMetadataService.loadDynamicFormDefaultDataById(orderId);

		if (groupedFields != null) {
			groupedFields.clear();
			groupedFields = null;
		}
	}

	public List<FormWorkflow> getFormWorkflowSteps() {
		if (formWorkflowSteps == null && getOrder() != null) {
			formWorkflowSteps = formMetadataService
					.listWorkFlowSteps(formMetadataId);
			if (formWorkflowSteps != null && !formWorkflowSteps.isEmpty()) {
				workflowStepsMappedByStatusName = new HashMap<String, FormWorkflow>();
				workflowStepsMappedByStepName = new HashMap<String, FormWorkflow>();
				workflowStepIndexMappedByStepName = new HashMap<String, Integer>();
				int index = 0;
				for (FormWorkflow step : formWorkflowSteps) {
					workflowStepsMappedByStatusName.put(step.getStatusName(),
							step);
					workflowStepsMappedByStepName.put(step.getStepName(), step);
					workflowStepIndexMappedByStepName.put(step.getStepName(),
							index++);
				}
			}
		}

		return formWorkflowSteps;
	}

	public boolean isWorkFlowApprovable() {
		if (order == null
				|| !appSessionParams.getCurrentAccount().getAccount()
						.equals(order.getAssigneeAccount())) {
			return false;
		}

		return getWorkFlowApproveToStep() != null;
	}

	public FormWorkflow getWorkFlowApproveToStep() {
		String currentStatus = getOrder().getStatus();
		List<FormWorkflow> steps = getFormWorkflowSteps();
		if (steps == null || steps.isEmpty()) {
			return null;
		}

		FormWorkflow step = workflowStepsMappedByStatusName.get(currentStatus);
		FormWorkflow nextStep = null;
		if (step == null) {
			nextStep = steps.get(0);
		} else {
			String nextStepName = step.getApproveToStepName();
			if (nextStepName != null && !nextStepName.isEmpty()) {
				nextStep = workflowStepsMappedByStepName.get(nextStepName);
			} else {
				Integer currentStepIndex = workflowStepIndexMappedByStepName
						.get(step.getStepName());
				if (currentStepIndex == steps.size() - 1) {
					return null;
				} else {
					nextStep = steps.get(currentStepIndex + 1);
				}
			}
		}

		return nextStep;
	}

	public void workFlowApprove() {
		changeAssigneeAndStatus(order, getWorkFlowApproveToStep());
	}

	public boolean isWorkFlowRejectable() {
		if (order == null
				|| !appSessionParams.getCurrentAccount().getAccount()
						.equals(order.getAssigneeAccount())) {
			return false;
		}

		return getWorkFlowRejectToStep() != null;
	}

	public FormWorkflow getWorkFlowRejectToStep() {
		String currentStatus = getOrder().getStatus();
		List<FormWorkflow> steps = getFormWorkflowSteps();
		if (steps == null || steps.isEmpty()) {
			return null;
		}

		FormWorkflow step = workflowStepsMappedByStatusName.get(currentStatus);
		if (step != null) {
			String nextStepName = step.getRejectToStepName();
			if (nextStepName != null && !nextStepName.isEmpty()) {
				return workflowStepsMappedByStepName.get(nextStepName);
			}
		}

		return null;
	}

	public void workFlowReject() {
		changeAssigneeAndStatus(order, getWorkFlowRejectToStep());
	}

	public boolean isWorkFlowBackable() {
		if (order == null
				|| !appSessionParams.getCurrentAccount().getAccount()
						.equals(order.getAssigneeAccount())) {
			return false;
		}

		return getWorkFlowBackToStep() != null;
	}

	public FormWorkflow getWorkFlowBackToStep() {
		String currentStatus = getOrder().getStatus();
		List<FormWorkflow> steps = getFormWorkflowSteps();
		if (steps == null || steps.isEmpty()) {
			return null;
		}

		FormWorkflow step = workflowStepsMappedByStatusName.get(currentStatus);
		if (step != null) {
			Integer index = workflowStepIndexMappedByStepName.get(step
					.getStepName());
			if (index > 0) {
				return formWorkflowSteps.get(index - 1);
			} else if (index == 0) { // Back to init creator
				step = new FormWorkflow();
				step.setAssigneeAccount(getOrder().getCreatedBy());
				step.setStatusName(FormRecordStatus_ENUM.BACK_REFINE.name());
				return step;
			}
		}

		return null;
	}

	public void workFlowBack() {
		changeAssigneeAndStatus(order, getWorkFlowBackToStep());
	}

	public boolean isWorkFlowEndable() {
		if (order == null
				|| !appSessionParams.getCurrentAccount().getAccount()
						.equals(order.getAssigneeAccount())) {
			return false;
		}

		return getWorkFlowApproveToStep() == null;
	}

	public void workFlowEnd() {
		order.setStatus(FormRecordStatus_ENUM.END.name());
		formMetadataService.changeFormDefaultStatus(order);
	}

	public void workFlowInvalid() {
		order.setStatus(FormRecordStatus_ENUM.INVALID.name());
		formMetadataService.changeFormDefaultStatus(order);
	}

	private void changeAssigneeAndStatus(DynamicFormDefault o,
			FormWorkflow nextStep) {
		if (nextStep == null) {
			return;
		}

		o.setStatus(nextStep.getStatusName());
		o.setAssigneeAccount(nextStep.getAssigneeAccount());

		formMetadataService.changeFormDefaultAssignTo(o);
		formMetadataService.changeFormDefaultStatus(o);
		
		sendCAASEmail();
	}

	public boolean isFroze() {
		if (null == orderId) {
			return false;
		}

		return FormRecordStatus_ENUM.END.name().equalsIgnoreCase(
				order.getStatus())
				|| FormRecordStatus_ENUM.INVALID.name().equalsIgnoreCase(
						order.getStatus());
	}

	public boolean isEditable() {
		if (orderId == null) {
			return true;
		}

		return isToMeOrder();
	}

	public boolean isDeletable() {
		if (orderId == null) {
			return false;
		}

		return isToMeOrder();
	}

	public boolean isAssignable() {
		if (orderId == null) {
			return false;
		}

		return isToMeOrder();
	}

	public boolean isStatusChangable() {
		if (orderId == null) {
			return false;
		}

		return isToMeOrder();
	}

	public Long getParentFormRecordId() {
		return parentFormRecordId;
	}

	public void setParentFormRecordId(Long parentFormRecordId) {
		this.parentFormRecordId = parentFormRecordId;
	}

	public String getParentFormFieldColumnName() {
		return parentFormFieldColumnName;
	}

	public void setParentFormFieldColumnName(String parentFormFieldColumnName) {
		this.parentFormFieldColumnName = parentFormFieldColumnName;
	}

	public String getFormBizKey() {
		return formBizKey;
	}

	public void setFormBizKey(String formBizKey) {
		try {
			this.formBizKey = new String(formBizKey.getBytes("ISO-8859-1"),
					"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public boolean isReadable() {
		getOrder();

        String accStr = appSessionParams.getCurrentAccount().getAccount();
        if(this.order.getId() == null){
            return this.getFormMetadata().isCreatable(accStr);
        }else {
            return getFormMetadata().isReadable(accStr) || getFormMetadata().isWritable(accStr);
        }
	}

    public boolean isSavable(){
        getOrder();

        String accStr = appSessionParams.getCurrentAccount().getAccount();

        return this.isEditable() && !isFroze() &&
                (this.getFormMetadata().isCreatable(accStr) || getFormMetadata().isWritable(accStr));
    }

	public String getPrintReviewPage() {

		if (printReviewPage == null) {
			if (getOrder() != null && getOrder().getFormName() != null) {
				if (printTemplates.containsKey(getOrder().getFormName())) {
					printReviewPage = "../dynamic_form/report_template/"
							+ printTemplates.getProperty(getOrder()
									.getFormName()) + "?formMetadataId="
							+ formMetadataId + "&formRecordId=" + orderId;
				} else {
					printReviewPage = "../dynamic_form/form_print.jsf?formMetadataId="
							+ formMetadataId + "&formRecordId=" + orderId;
				}
			} else {
				return "#";
			}
		}

		return printReviewPage;
	}

	public List<FileUpload> getAttachedFiles() {
		if (attachedFiles == null && order != null && order.getId() != null) {
			attachedFiles = fileuploadService.findByFields(new String[] {
					"forId", "forType" }, new Object[] { order.getId(),
					FILE_FOR_ENUM.FORM.name() });
		}

		return attachedFiles;
	}
	
	private void sendModifyFormDataEmail() {
		EmailBox eb = generateModifyFormDataEmail();
		
		try {
			mailBoxService.newOrUpdate(eb);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendCAASEmail() {
		sendCAASEmailByAccount(order.getCreatedBy());
        sendCAASEmailByAccount(order.getAssigneeAccount());
	}

    private void sendCAASEmailByAccount(String accountString) {
        EmailBox eb = generateCAASEmail(accountString);
        try {
            mailBoxService.newOrUpdate(eb);
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
	private EmailBox generateCAASEmail(String accountString) {
		Account account = accountService.findByAccount(accountString);
		Account modifiedBy = accountService.findByAccount(order.getLastModifiedBy());
		
		if(account == null){
			return null;
		}

		String mailTo = account.getPerson().getEmail();
		String emailTitle = String.format("%s(%s)",
				JSFUtils.getI18NMessage("CHANGE_ASSIGNEE_STATUS_EMAIL_TITLE"),
				formMetadata.getFormLabel());
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("formName", formMetadata.getFormLabel());
		params.put("summary", order.getSummary());
		params.put("modifyRealName", modifiedBy.getPerson().getRealName());
		params.put("modifyAccount", modifiedBy.getAccount());
		params.put("modifyTime", String.format("%1$tF %1$tT", order.getLastModified()));
		params.put("status", getStatusAsString(order.getStatus()));
		
		String emailMsg = mailSenderBBean.buildVMMessage("vm/inform_email_change_assignee_status.vm", params);

		EmailBox eb = new EmailBox();
		eb.setMailTo(mailTo);
		eb.setTitle(emailTitle);
		eb.setMessages(emailMsg);
		eb.setMailFrom(MailSenderBBean.SYSTEM_EMAIL);
		eb.setStatus(EmailBox.STATUS.WAITING.name());
		
		return eb;
	}

	private EmailBox generateModifyFormDataEmail() {
		Account createBy = accountService.findByAccount(order.getCreatedBy());
		Account modifiedBy = accountService.findByAccount(order.getLastModifiedBy());
		
		if(createBy == null){
			return null;
		}

		String mailTo = createBy.getPerson().getEmail();
		String emailTitle = String.format("%s(%s)",
				JSFUtils.getI18NMessage("FORM_CHANGE_EMAIL_TITLE"),
				formMetadata.getFormLabel());
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("formName", formMetadata.getFormLabel());
		params.put("summary", order.getSummary());
		params.put("modifyRealName", modifiedBy.getPerson().getRealName());
		params.put("modifyAccount", modifiedBy.getAccount());
		params.put("modifyTime", String.format("%1$tF %1$tT", order.getLastModified()));
		
		String emailMsg = mailSenderBBean.buildVMMessage("vm/inform_email_modify_form_data.vm", params);

		EmailBox eb = new EmailBox();
		eb.setMailTo(mailTo);
		eb.setTitle(emailTitle);
		eb.setMessages(emailMsg);
		eb.setMailFrom(MailSenderBBean.SYSTEM_EMAIL);
		eb.setStatus(EmailBox.STATUS.WAITING.name());
		
		return eb;
	}

    private String getStatusAsString(Object value) {
        if (value == null || value.toString().isEmpty())
            return "NA";

        String label = getSystemStatusLabel(value);
        if(label != null && !label.isEmpty()){
            return label;
        }

        List<FormWorkflow> steps = getFormWorkflowSteps();
        if(workflowStepsMappedByStatusName.containsKey(value)){
            return workflowStepsMappedByStatusName.get(value).getStatusLabel();
        }

//        for (FormWorkflow step : steps) {
//            if (value.equals(step.getStatusName())) {
//                return step.getStatusLabel();
//            }
//        }

        return "NA";
    }

    private String getSystemStatusLabel(Object value){
        if (FormRecordStatus_ENUM.NEW.name().equalsIgnoreCase(value.toString())) {
            return JSFUtils.getI18NMessage(FormRecordStatus_ENUM.NEW.name());
        }

        if (FormRecordStatus_ENUM.BACK_REFINE.name().equalsIgnoreCase(value.toString())) {
            return JSFUtils.getI18NMessage(FormRecordStatus_ENUM.BACK_REFINE.name());
        }

        if (FormRecordStatus_ENUM.END.name().equalsIgnoreCase(value.toString())) {
            return JSFUtils.getI18NMessage(FormRecordStatus_ENUM.END.name());
        }

        if (FormRecordStatus_ENUM.INVALID.name().equalsIgnoreCase(value.toString())) {
            return JSFUtils.getI18NMessage(FormRecordStatus_ENUM.INVALID.name());
        }

        if (FormRecordStatus_ENUM.DELETED.name().equalsIgnoreCase(value.toString())) {
            return JSFUtils.getI18NMessage(FormRecordStatus_ENUM.DELETED.name());
        }

        return null;
    }

    private Map<Long,List<FormListEntry>> kidsMap = null;
    private List<FormMetadata> kidMetadataList = null;
    private Map<Long,FormFieldMetadata> kidFormFields = null;

    public List<FormMetadata> getKidMetadataList(){
        if(kidMetadataList == null || this.kidsMap == null){
            initKidsMap();
        }
        return this.kidMetadataList;
    }

    public Map<Long,List<FormListEntry>> getKidsMap(){
        if(kidMetadataList == null || this.kidsMap == null){
            initKidsMap();
        }

        return this.kidsMap;
    }

    public Map<Long,FormFieldMetadata> getKidFormFields(){
        if(kidMetadataList == null || this.kidsMap == null){
            initKidsMap();
        }

        return kidFormFields;
    }

    public Customer getCustomer() {
        if(customer == null){
            if(customerId != null){
                customer = customerService.loadById(customerId);
            }else {
                customer = new Customer();
            }
        }
        return customer;
    }

    private void setSubmitedCustomerToOrder(){
        if(this.customer != null && !StringUtils.isEmpty(this.customer.getName())){
            Customer c = this.customerService.findCustomerByName(this.customer.getName());
            order.setCustomerId(c.getId());
        }
    }

    private void initKidsMap(){
        if(this.orderId != null){
            kidsMap = new HashMap<Long, List<FormListEntry>>();

            kidMetadataList = this.formMetadataService.listFormMetadataByParent(this.order.getFormMetadataId());
            if(!CollectionUtils.isEmpty(kidMetadataList)){
                for(FormMetadata meta:kidMetadataList){
                    List<FormListEntry> formList = this.formMetadataService.listFormsToParentForm(this.order.getFormBizId(),meta.getId());

                    this.replaceStatusToLabel(formList);
                    kidsMap.put(meta.getId(),formList);
                }
            }else {
                kidMetadataList = new ArrayList<FormMetadata>();
            }

            initKidFormFields();
        }
    }

    private void initKidFormFields(){
        if(!CollectionUtils.isEmpty(kidMetadataList)){
            kidFormFields = new HashMap<Long, FormFieldMetadata>();

            for(FormMetadata form:kidMetadataList){
                for(FormFieldMetadata formField: this.formFieldMetadataList){
                    if(formField.getReferToFormMetadataId() == form.getId()){
                        kidFormFields.put(form.getId(),formField);
                        break;
                    }
                }
            }
        }
    }
}
