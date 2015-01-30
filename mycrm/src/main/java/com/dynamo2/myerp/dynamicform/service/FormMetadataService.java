package com.dynamo2.myerp.dynamicform.service;

import java.util.List;
import java.util.Map;

import com.dynamo2.myerp.dynamicform.dao.entities.resultmap.FormMDStatisticsEntry;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import com.dynamo2.myerp.AbstractBaseServiceInterface;
import com.dynamo2.myerp.dynamicform.dao.entities.DynamicFormDefault;
import com.dynamo2.myerp.dynamicform.dao.entities.DynamicFormDefaultAuditLog;
import com.dynamo2.myerp.dynamicform.dao.entities.FormFieldMetadata;
import com.dynamo2.myerp.dynamicform.dao.entities.FormMetadata;
import com.dynamo2.myerp.dynamicform.dao.entities.FormWorkflow;
import com.dynamo2.myerp.dynamicform.dao.entities.resultmap.FormListEntry;

public interface FormMetadataService extends AbstractBaseServiceInterface<FormMetadata> {

	/**
	 * Category type for form.
	 * 
	 * @author fwang
	 * 
	 */
	public static enum FORM_CATEGORY_ENUM {
		FORM_CATEGORY_NO_CATEGORY, FORM_CATEGORY_TECH_SUPPORT(true),FORM_CATEGORY_OA(true), FORM_CATEGORY_FINANCE(true), FORM_CATEGORY_ORDER(true), FORM_CATEGORY_CUSTOMER_PROFILE;

		private boolean showInNavigationBar = false;

		private FORM_CATEGORY_ENUM(boolean showInNavigationBar) {
			this.showInNavigationBar = showInNavigationBar;
		}

		private FORM_CATEGORY_ENUM() {
			// Do nothing;
		}

		public boolean isShowInNavigationBar() {
			return showInNavigationBar;
		}

		public String getLowerCasedName() {
			return this.name().toLowerCase();
		}
	}

	/**
	 * Pre-defined form
	 * 
	 * @author fwang
	 * 
	 */
	public static enum PREDEFINED_FORM {
		CUSTOMER_PROFILE;

		public String getLowcasedName() {
			return this.name().toLowerCase();
		}
	}

	@Transactional
	public void addOrUpdateField(Long formId, FormFieldMetadata formFieldMetadata) throws DynamicFormServiceException;

	public FormMetadata findFormMetadataByName(String formName);

	/**
	 * Get for Form Metadata information
	 * 
	 * @return Form Metadata list
	 */
	public List<FormMetadata> listAllFormMetadata();

	/**
	 * Get for Form Metadata information by category
	 * 
	 * @return Form Metadata list
	 */
	public List<FormMetadata> listAllFormMetadataByCategory(FORM_CATEGORY_ENUM category);

	public List<FormFieldMetadata> listAllFieldsOfFormMetadata(Long formId);

	@Transactional
	public void addOrUpdateFormDefault(FormMetadata formMetadata, Map<String, Object> columnValueMap,
			DynamicFormDefault order);

	public List<FormListEntry> listFormDefault(Long formMetaDataId);

	public DynamicFormDefault loadDynamicFormDefaultDataById(Long dynamicFormDefaultId);

	public Map<String, Object> convertDynamicFormDefaultToMap(DynamicFormDefault order);

	@PreAuthorize("hasAuthority('ROLE_BUSINESS_EXPERT')")
	@Transactional
	public void deleteField(long fieldId);

	@Transactional
	public void changeFormDefaultAssignTo(DynamicFormDefault order);

	@Transactional
	public void changeFormDefaultStatus(DynamicFormDefault order);

	public List<FormListEntry> searchByCriteria(Map<String, String> searchCriteriaValueMap,Map<String, List<String>> notEqualCriteriaValueMap, Long formMetadataId);

	public List<DynamicFormDefaultAuditLog> listAuditLog(Long formId);

	public List<DynamicFormDefaultAuditLog> listAuditLogForCurrentAccount();

	/**
	 * Validate the Expression.
	 * 
	 * Validation Rule:
	 * <ul>
	 * <li>Fields used as variable in expression should be int or double type
	 * and have to be required=true.</li>
	 * <li>Expression could pass the testing with JEP</li>
	 * <li>Field, which holds expression, should be int or double type</li>
	 * </ul>
	 * 
	 * @param expression
	 *            Expression
	 * @param fieldsList
	 *            Field List
	 * @return
	 * @throws DyamicFormServiceException
	 *             Throw exception if cannot pass the validation
	 */
	public String validateExpressionField(String expression, List<FormFieldMetadata> fieldsList)
			throws DynamicFormServiceException;

	/**
	 * Get form list by category
	 * 
	 * @param category
	 *            Category should get from FORM_CATEGORY_ENUM
	 * @return form list
	 */
	public List<FormListEntry> listFormDefaultForCustomer(Long formMetadataId, Long customerId);

	/**
	 * Get work flow steps of Form
	 * 
	 * @param formMetadataId
	 *            form metadata id
	 * @return work flow steps
	 */
	public List<FormWorkflow> listWorkFlowSteps(Long formMetadataId);

	/**
	 * Add or update a work flow step
	 * 
	 * @param newWorkflowStep
	 *            Work Flow Step
	 */
	@Transactional
	public void addOrUpdateWorkFlowStep(FormWorkflow newWorkflowStep);

	/**
	 * Delete one work flow step by step id
	 * 
	 * @param id
	 *            Work flow step id
	 */
	@Transactional
	public void deleteWorkFlowStep(long id);

	/**
	 * Search certification. This method will only be used by
	 * CertificationSearch page in front end. Need to use a more general
	 * solution to expose a form in front end for search.
	 * 
	 * @param certificationSN
	 * @param verficationCode
	 * @return Certification data
	 */
	public Object[] searchCertification(String certificationSN, String verficationCode);

	/**
	 * Set child form's business key into parent form field
	 * 
	 * @param parentFormRecordId
	 *            Parent form record id
	 * @param parentFormFieldColumnName
	 *            Parent form column name to hold the child form record's
	 *            business key
	 * @param order
	 *            Child form record's object
	 */
	@Transactional
	public void updateParentFormRecord(Long parentFormRecordId, String parentFormFieldColumnName,
			DynamicFormDefault order);

	/**
	 * Load form record by form Biz Key
	 * 
	 * @param formBizKey
	 *            Form Busienss Key
	 * @return Form record object
	 */
	public DynamicFormDefault loadDynamicFormDefaultDataByBizKey(String formBizKey);

	/**
	 * Retrieve fields of a Form Metadata and put it as following structure
	 * Group -> Row -> Field
	 * 
	 * @param formMetadataId
	 *            Form Metadata Id
	 * @return Grouped fields
	 */
	public List<List<List<FormFieldMetadata>>> extractGroupedFieldMetadataList(Long formMetadataId);

	/**
	 * Retrieve all forms which were operated by current user.
	 * 
	 * @return Form List
	 */
	public List<FormListEntry> listFormDefaultOperatedByCurrentUser();

	/**
	 * Get value of a field in a form object
	 * 
	 * @param formData
	 * @param fieldName
	 * @return
	 */
	public Object getFieldValueOfForm(DynamicFormDefault formData, String fieldName);

	/**
	 * Set value of a field in a form object
	 * 
	 * @param formData
	 * @param fieldName
	 * @param value
	 */
	public void setFieldValueOfForm(DynamicFormDefault formData, String fieldName, String value);

	/**
	 * Fill in default value
	 * 
	 * @param formObject
	 * @param parentFormObject
	 */
	public void fillInDefaultValue(DynamicFormDefault formObject, DynamicFormDefault parentFormObject);

    public List<FormListEntry> listFormsToParentForm(String parentBizID,Long formMetadataId);

    public List<FormMetadata> listFormMetadataByParent(Long parentID);

    /**
     * Statistics DynamicFormDefault's count by DynamicFormDefault.status for published FormMetadata
     *
     * **/
    public List<FormMDStatisticsEntry> statisticsByStatus();

    public void updateCategory(FormMetadata fm);
}
