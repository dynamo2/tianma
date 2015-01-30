package com.dynamo2.myerp.dynamicform.ui.controller;

import java.io.Serializable;
import java.util.*;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import com.dynamo2.myerp.dynamicform.ui.view.GroupView;
import org.icefaces.bean.ViewRetained;
import org.icefaces.bean.WindowDisposed;

import com.dynamo2.myerp.dynamicform.dao.entities.FormFieldMetadata;
import com.dynamo2.myerp.dynamicform.dao.entities.FormMetadata;
import com.dynamo2.myerp.dynamicform.dao.entities.FormWorkflow;
import com.dynamo2.myerp.dynamicform.service.DynamicFormServiceException;
import com.dynamo2.myerp.dynamicform.service.FormFieldTypes_ENUM;
import com.dynamo2.myerp.dynamicform.service.FormMetadataService;
import com.dynamo2.myerp.dynamicform.service.FormMetadataStatus_ENUM;
import com.dynamo2.myerp.dynamicform.service.NoEnoughFieldTypeException;
import com.dynamo2.myerp.dynamicform.service.DynamicFormServiceException.ERR_CODE;
import com.dynamo2.myerp.service.ServiceException;
import com.dynamo2.util.JSFUtils;

@ManagedBean(name = "formMetadataMgmtBean")
@ViewScoped
@ViewRetained
@WindowDisposed
public class FormMetadataMgmtBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private FormMetadata formMetadata;
	private List<FormFieldMetadata> formFieldMetadataList;
	private FormFieldMetadata newField;
	private List<FormMetadata> formMetadataList;
	private Long formMetadataId;
	private boolean editable = true;
	private boolean formPreview = false;
	// Group -> Row -> Column
    // private List<List<List<FormFieldMetadata>>> groupedFields;
    private List<GroupField> groupedFields;

    private GroupView groupView;

    //GroupField.groupNumber-> GroupField
    private Map<Integer,GroupField> groupFieldNumberMap;

	private Map<String, Object> columnValueMap = new HashMap<String, Object>();
	// Work flow steps list
	private List<FormWorkflow> workFlowSteps;
	// New work flow step or work flow step for edit
	private FormWorkflow newWorkflowStep;

	@ManagedProperty(value = "#{formMetadataService}")
	protected transient FormMetadataService formMetadataService;

	public void saveFormMetadata() throws ServiceException {
		formMetadataService.newOrUpdate(formMetadata);

		formFieldMetadataList = formMetadataService.listAllFieldsOfFormMetadata(formMetadata.getId());

		formMetadataId = formMetadata.getId();
		formMetadata = null;
		formMetadata = getFormMetadata();

		JSFUtils.addMessagesToComponent("dynamicFormConfigForm:formMetadataPanelGrid",
				JSFUtils.getI18NMessage("operationFinishedSuccessfully"));
	}

	public void saveField() {

		try {
			// TODO Fan this validatio should be added into Service layer.
			if (newField.getExpression() != null && !newField.getExpression().isEmpty()) {
				if (!FormFieldTypes_ENUM.DOUBLE.getSqlType().equalsIgnoreCase(newField.getFieldType())
						&& !FormFieldTypes_ENUM.INT.getSqlType().equalsIgnoreCase(newField.getFieldType())) {
					JSFUtils.addMessagesToComponent("dynamicFormConfigForm:saveFormFieldButton",
							JSFUtils.getI18NMessage(ERR_CODE.CFG_FIELD_INVALID_EXPRESSION_FIELD_TYPE.name()));

					return;
				}
			} else {
				newField.setExpression(null);
			}

			formMetadataService.addOrUpdateField(formMetadata.getId(), newField);
			newField = null;

			formFieldMetadataList = formMetadataService.listAllFieldsOfFormMetadata(formMetadata.getId());

			JSFUtils.addMessagesToComponent("dynamicFormConfigForm:saveFormFieldButton",
					JSFUtils.getI18NMessage("operationFinishedSuccessfully"));
		} catch (NoEnoughFieldTypeException e) {
			JSFUtils.addMessagesToComponent("dynamicFormConfigForm:saveFormFieldButton",
					JSFUtils.getI18NMessage("form_err_no_enough_type"));
		} catch (DynamicFormServiceException e) {
			JSFUtils.addMessagesToComponent("dynamicFormConfigForm:saveFormFieldButton",
					JSFUtils.getI18NMessage(e.getErrCode().name()));
		}

		newField = null;
	}

	public FormFieldMetadata getNewField() {
		if (null == newField)
			newField = new FormFieldMetadata();

		return newField;
	}

	public void setNewField(FormFieldMetadata newField) {
		this.newField = newField;
	}

	public FormWorkflow getNewWorkflowStep() {
		if (newWorkflowStep == null) {
			newWorkflowStep = new FormWorkflow();
			newWorkflowStep.setFormMetadataId(formMetadataId);
			List<FormWorkflow> steps = getWorkFlowSteps();
			if (steps != null && !steps.isEmpty()) {
				newWorkflowStep.setStepSequenceNumber(steps.get(steps.size() - 1).getStepSequenceNumber() + 1);
			}
		}

		return newWorkflowStep;
	}

	public void setNewWorkflowStep(FormWorkflow newWorkflowStep) {
		this.newWorkflowStep = newWorkflowStep;
	}

	public FormMetadata getFormMetadata() {
		if (null == formMetadata) {
			if (formMetadataId == null || formMetadataId == 0) {
				formMetadata = new FormMetadata();
			} else {
				formMetadata = formMetadataService.loadById(formMetadataId);
				formFieldMetadataList = formMetadataService.listAllFieldsOfFormMetadata(formMetadataId);
				editable = FormMetadataStatus_ENUM.NEW.name().equals(formMetadata.getFormStatus());
			}
		}

		return formMetadata;
	}

	public void setFormMetadata(FormMetadata formMetadata) {
		this.formMetadata = formMetadata;
	}

	public List<FormFieldMetadata> getFormFieldMetadataList() {
		if (null == formFieldMetadataList)
			formFieldMetadataList = new LinkedList<FormFieldMetadata>();
		return formFieldMetadataList;
	}

	public void setFormFieldMetadataList(List<FormFieldMetadata> formFieldMetadataList) {
		this.formFieldMetadataList = formFieldMetadataList;
	}

	public FormMetadataService getFormMetadataService() {
		return formMetadataService;
	}

	public void setFormMetadataService(FormMetadataService formMetadataService) {
		this.formMetadataService = formMetadataService;
	}

	public List<FormMetadata> getFormMetadataList() {
		if (formMetadataList == null) {
			formMetadataList = formMetadataService.listAllFormMetadata();
		}
		return formMetadataList;
	}

	public void setFormMetadataList(List<FormMetadata> formMetadataList) {
		this.formMetadataList = formMetadataList;
	}

	public Long getFormMetadataId() {
		return formMetadataId;
	}

	public void setFormMetadataId(Long formMetadataId) {
		this.formMetadataId = formMetadataId;
	}

	public boolean isEditable() {
		return editable;
	}

	public void delFieldById() {
		String fieldId = JSFUtils.getHttpRequestParam("delFieldId");
		long id = Long.parseLong(fieldId);
		formMetadataService.deleteField(id);
		for (Iterator<FormFieldMetadata> iterator = formFieldMetadataList.iterator(); iterator.hasNext();) {
			FormFieldMetadata type = iterator.next();
			if (type.getId() == id) {
				iterator.remove();
				break;
			}
		}
	}

	public void delWorkFlowStepById() {
		String stepId = JSFUtils.getHttpRequestParam("stepId");
		long id = Long.parseLong(stepId);
		formMetadataService.deleteWorkFlowStep(id);
		for (Iterator<FormFieldMetadata> iterator = formFieldMetadataList.iterator(); iterator.hasNext();) {
			FormFieldMetadata type = iterator.next();
			if (type.getId() == id) {
				iterator.remove();
				break;
			}
		}
	}

	public void editFieldById() {
		String fieldId = JSFUtils.getHttpRequestParam("fieldId");

		for (FormFieldMetadata f : formFieldMetadataList) {
			if (f.getId().equals(Long.parseLong(fieldId))) {
				newField = f;
			}
		}
	}

	public void editWorkFlowStepById() {
		String stepId = JSFUtils.getHttpRequestParam("stepId");

		for (FormWorkflow step : getWorkFlowSteps()) {
			if (step.getId().equals(Long.parseLong(stepId))) {
				newWorkflowStep = step;
			}
		}
	}

//	public void showPreview() {
//		formPreview = true;
//
//		groupedFields = new ArrayList<List<List<FormFieldMetadata>>>();
//		int currentGroupNumber = formFieldMetadataList.get(0).getDisplayPositionGroup();
//		int currentRowNumber = formFieldMetadataList.get(0).getDisplayPositionRow();
//		List<FormFieldMetadata> currentRow = new ArrayList<FormFieldMetadata>();
//		List<List<FormFieldMetadata>> currentGroup = new ArrayList<List<FormFieldMetadata>>();
//		currentGroup.add(currentRow);
//		groupedFields.add(currentGroup);
//		for (FormFieldMetadata field : formFieldMetadataList) {
//			columnValueMap.put(field.getColumnName(), null);
//
//			if (currentGroupNumber != field.getDisplayPositionGroup()) {
//				currentGroup = new ArrayList<List<FormFieldMetadata>>();
//				groupedFields.add(currentGroup);
//				currentGroupNumber = field.getDisplayPositionGroup();
//
//				currentRow = new ArrayList<FormFieldMetadata>();
//				currentGroup.add(currentRow);
//				currentRowNumber = field.getDisplayPositionRow();
//			}
//
//			if (currentRowNumber == field.getDisplayPositionRow()) {
//				currentRow.add(field);
//			} else {
//				currentRow = new ArrayList<FormFieldMetadata>();
//				currentGroup.add(currentRow);
//				currentRowNumber = field.getDisplayPositionRow();
//				currentRow.add(field);
//			}
//		}
//	}

    public GroupView getGroupView(){
        if(this.groupView == null){
            this.groupView = new GroupView(this.formFieldMetadataList);
        }

        return this.groupView;
    }

    public void preview() {
        formPreview = true;

        this.getGroupView().reloadGroups(this.formFieldMetadataList);
    }

    public void showPreview() {
        formPreview = true;

        groupedFields = new ArrayList<GroupField>();
        groupFieldNumberMap = new HashMap<Integer, GroupField>();

        int currentGroupNumber = formFieldMetadataList.get(0).getDisplayPositionGroup();
        int currentRowNumber = formFieldMetadataList.get(0).getDisplayPositionRow();

        List<FormFieldMetadata> currentRow = new ArrayList<FormFieldMetadata>();
        GroupField currentGroup = new GroupField(currentGroupNumber);
        currentGroup.addRow(currentRow,currentRowNumber);

        groupedFields.add(currentGroup);
        groupFieldNumberMap.put(currentGroup.getGroupNumber(),currentGroup);
        for (FormFieldMetadata field : formFieldMetadataList) {
            columnValueMap.put(field.getColumnName(), null);



            if (currentGroupNumber != field.getDisplayPositionGroup()) {
                currentGroupNumber = field.getDisplayPositionGroup();

                currentGroup = new GroupField(currentGroupNumber);
                groupedFields.add(currentGroup);
                groupFieldNumberMap.put(currentGroup.getGroupNumber(),currentGroup);

                currentRow = new ArrayList<FormFieldMetadata>();
                currentGroup.addRow(currentRow,currentRowNumber);
                currentRowNumber = field.getDisplayPositionRow();
            }

            if(field.getIsLabel()){
                currentGroup.setGroupLabel(field.getFieldLabel());
            }else if (currentRowNumber == field.getDisplayPositionRow()) {
                currentRow.add(field);
            } else {
                currentRow = new ArrayList<FormFieldMetadata>();
                currentRowNumber = field.getDisplayPositionRow();

                currentGroup.addRow(currentRow,currentRowNumber);
                currentRow.add(field);
            }
        }
    }

	public boolean isFormPreview() {
		return formPreview;
	}

	public List<GroupField> getGroupedFields() {
		return groupedFields;
	}

	public Map<String, Object> getColumnValueMap() {
		return columnValueMap;
	}

	public void validateExpression(FacesContext cnt, UIComponent cmp, Object obj) {
		String expression = obj.toString();

		try {
			String expressionLabel = formMetadataService
					.validateExpressionField(expression, getFormFieldMetadataList());
			newField.setExpressionLabel(expressionLabel);
		} catch (DynamicFormServiceException e) {
			throw new ValidatorException(new FacesMessage(JSFUtils.getI18NMessage(e.getErrCode().name())));
		}
	}

	public void validateUniqueOfWorkFlowStepName(FacesContext cnt, UIComponent cmp, Object obj) {
		String stepName = obj.toString();
		if (stepName.equalsIgnoreCase(newWorkflowStep.getStepName())) {
			return;
		}

		if (stepName != null && !stepName.isEmpty()) {
			List<FormWorkflow> steps = getWorkFlowSteps();
			if (steps != null && !steps.isEmpty()) {
				for (FormWorkflow step : steps) {
					if (step.getStepName().equalsIgnoreCase(stepName)) {
						throw new ValidatorException(new FacesMessage(JSFUtils.getI18NMessage("SHOULD_BE_UNIQUE")));
					}
				}
			}
		}
	}

	public void validateUniqueOfWorkFlowSequenceNumber(FacesContext cnt, UIComponent cmp, Object obj) {
		try {
			int seqNum = Integer.parseInt(obj.toString());
			if (seqNum == newWorkflowStep.getStepSequenceNumber()) {
				return;
			}

			List<FormWorkflow> steps = getWorkFlowSteps();
			if (steps != null && !steps.isEmpty()) {
				for (FormWorkflow step : steps) {
					if (step.getStepSequenceNumber() == seqNum) {
						throw new ValidatorException(new FacesMessage(JSFUtils.getI18NMessage("SHOULD_BE_UNIQUE")));
					}
				}
			}

		} catch (NumberFormatException e) {
			throw new ValidatorException(new FacesMessage(JSFUtils.getI18NMessage("SHOULD_BE_NUMBER")));
		}
	}

	public void validateUniqueOfWorkFlowStatusName(FacesContext cnt, UIComponent cmp, Object obj) {

		String statusName = obj.toString();
		if (statusName.equalsIgnoreCase(newWorkflowStep.getStatusName())) {
			return;
		}

		List<FormWorkflow> steps = getWorkFlowSteps();
		if (steps != null && !steps.isEmpty()) {
			for (FormWorkflow step : steps) {
				if (step.getStatusName() == statusName) {
					throw new ValidatorException(new FacesMessage(JSFUtils.getI18NMessage("SHOULD_BE_UNIQUE")));
				}
			}
		}

	}

	public void validatorToStepName(FacesContext cnt, UIComponent cmp, Object obj) {
		if (obj != null && obj.equals(newWorkflowStep.getStepName())) {
			throw new ValidatorException(new FacesMessage(JSFUtils.getI18NMessage("SHOULD_NOT_TO_SELF")));
		}
	}

	public List<FormWorkflow> getWorkFlowSteps() {
		if (workFlowSteps == null && formMetadataId != null) {
			workFlowSteps = formMetadataService.listWorkFlowSteps(formMetadataId);
		}

		return workFlowSteps;
	}

	public void saveWorkFlowStep() {
		if (newWorkflowStep.getApproveToStepName() == null || newWorkflowStep.getApproveToStepName().isEmpty()) {
			newWorkflowStep.setApproveToStepName(null);
		}

		if (newWorkflowStep.getRejectToStepName() == null || newWorkflowStep.getRejectToStepName().isEmpty()) {
			newWorkflowStep.setRejectToStepName(null);
		}

		if (newWorkflowStep.getAssigneeAccount() == null || newWorkflowStep.getAssigneeAccount().isEmpty()) {
			newWorkflowStep.setAssigneeAccount(null);
		}

		if (newWorkflowStep.getAssigneeRole() == null || newWorkflowStep.getAssigneeRole().isEmpty()) {
			newWorkflowStep.setAssigneeRole(null);
		}

		formMetadataService.addOrUpdateWorkFlowStep(newWorkflowStep);
		newWorkflowStep = null;
		workFlowSteps.clear();
		workFlowSteps = null;

		JSFUtils.addMessagesToComponent("dynamicFormConfigForm:saveWorkFlowStepButton",
				JSFUtils.getI18NMessage("operationFinishedSuccessfully"));
	}

    public class GroupField {
        private String groupLabel;
        private int groupNumber;
        private List<List<FormFieldMetadata>> rows = new ArrayList<List<FormFieldMetadata>>();
        private Map<Integer,List<FormFieldMetadata>> rowNumberMap = new HashMap<Integer, List<FormFieldMetadata>>();

        public GroupField() {
        }

        public GroupField(int groupNumber) {
            this.groupNumber = groupNumber;
        }

        public String getGroupLabel() {
            return groupLabel;
        }

        public List<List<FormFieldMetadata>> getRows() {
            return rows;
        }

        public List<FormFieldMetadata> getRow(int rowNumber) {
            return this.rowNumberMap.get(rowNumber);
        }

        public boolean hasRow(int rowNumber){
            return this.rowNumberMap.containsKey(rowNumber);
        }

        public void setGroupLabel(String groupLabel) {
            this.groupLabel = groupLabel;
        }

        public void addRow(List<FormFieldMetadata> row,int rowNumber) {
            this.rows.add(row);
            this.rowNumberMap.put(rowNumber,row);
        }

        public int getGroupNumber() {
            return groupNumber;
        }

        public void setGroupNumber(int groupNumber) {
            this.groupNumber = groupNumber;
        }
    }
}
