package com.dynamo2.myerp.dynamicform.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedProperty;
import javax.persistence.Column;
import javax.persistence.Query;

import com.dynamo2.myerp.crm.dao.entities.Account;
import com.dynamo2.myerp.crm.service.AccountService;
import com.dynamo2.myerp.dynamicform.dao.entities.resultmap.FormMDStatisticsEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.AbstractBaseService;
import com.dynamo2.myerp.crm.dao.CustomerDAO;
import com.dynamo2.myerp.crm.dao.entities.Customer;
import com.dynamo2.myerp.crm.service.constant.Roles_ENUM;
import com.dynamo2.myerp.dynamicform.dao.DynamicFormDefaultAuditLogDAO;
import com.dynamo2.myerp.dynamicform.dao.DynamicFormDefaultDAO;
import com.dynamo2.myerp.dynamicform.dao.FormFieldMetadataDAO;
import com.dynamo2.myerp.dynamicform.dao.FormMetadataDAO;
import com.dynamo2.myerp.dynamicform.dao.FormWorkflowDAO;
import com.dynamo2.myerp.dynamicform.dao.entities.DynamicFormDefault;
import com.dynamo2.myerp.dynamicform.dao.entities.DynamicFormDefaultAuditLog;
import com.dynamo2.myerp.dynamicform.dao.entities.DynamicFormDefaultAuditLog.LOG_OPERATIONS_ENUM;
import com.dynamo2.myerp.dynamicform.dao.entities.FormFieldMetadata;
import com.dynamo2.myerp.dynamicform.dao.entities.FormMetadata;
import com.dynamo2.myerp.dynamicform.dao.entities.FormWorkflow;
import com.dynamo2.myerp.dynamicform.dao.entities.resultmap.FormListEntry;
import com.dynamo2.myerp.dynamicform.service.DynamicFormServiceException.ERR_CODE;
import com.dynamo2.myerp.dynamicform.utils.JEPHelper;
import com.dynamo2.myerp.service.PERMISSION_DEFINITION_ENUM;
import com.dynamo2.myerp.service.ServiceException;

@Service("formMetadataService")
public class FormMetadataServiceImpl extends AbstractBaseService<FormMetadata> implements FormMetadataService {

	@Autowired
	private FormMetadataDAO formMetadataDAO;

	@Autowired
	private FormFieldMetadataDAO formFieldMetadataDAO;

	@Autowired
	private DynamicFormDefaultDAO dynamicFormDefaultDAO;

	@Autowired
	private DynamicFormDefaultAuditLogDAO dynamicFormDefaultAuditLogDAO;

	@Autowired
	private CustomerDAO customerDAO;

	@Autowired
	private FormWorkflowDAO formWorkFlowDAO;

    @Autowired
    protected AccountService accountService;

	@Override
	protected AbstractBaseDAO<FormMetadata> getDao() {
		return formMetadataDAO;
	}

	@Override
	public void newOrUpdate(FormMetadata entity) throws ServiceException {
		super.newOrUpdate(entity);

		if (entity.getId() == 0) {
			entity = formMetadataDAO.findFormMetadataByName(entity.getFormName());

			// TODO Fan add error handler in here.
		}
	}

	//@Override
	public void addOrUpdateField(Long formId, FormFieldMetadata formFieldMetadata) throws DynamicFormServiceException {
		formFieldMetadata.setFormMetadataId(formId);

		// Check the refer to field
		if (formFieldMetadata.getReferToFormMetadataId() != null && formFieldMetadata.getReferToFormMetadataId() > 0L) {
			if (!FormFieldTypes_ENUM.VARCHAR.getSqlType().equals(formFieldMetadata.getFieldType())) {
				throw new DynamicFormServiceException(ERR_CODE.CFG_FIELD_REFER_TO_FIELD_SHOULB_BE_STRING);
			}

			formFieldMetadata.setInputColumns(null);
			formFieldMetadata.setInputRows(null);
			formFieldMetadata.setExpression(null);
			formFieldMetadata.setExpression(null);
		} else {
			formFieldMetadata.setReferToFormMetadataId(null);
		}

		if (formFieldMetadata.getColumnName() == null) {
			String columnName = formFieldMetadataDAO.findUnusedField(formId, formFieldMetadata.getFieldType());
			if (null == columnName) {
				throw new NoEnoughFieldTypeException();
			}
			formFieldMetadata.setColumnName(columnName);
		}

		if (FormFieldTypes_ENUM.DATETIME.getSqlType().equals(formFieldMetadata.getFieldType())
				|| FormFieldTypes_ENUM.INT.getSqlType().equals(formFieldMetadata.getFieldType())
				|| FormFieldTypes_ENUM.DOUBLE.getSqlType().equals(formFieldMetadata.getFieldType())) {
			formFieldMetadata.setInputColumns(0);
			formFieldMetadata.setInputRows(0);
		}
		formFieldMetadataDAO.saveOrUpdate(formFieldMetadata, getCurrentAccount().getAccount());
		// TODO Fan need to get field id back in here.
	}

	//@Override
	public FormMetadata findFormMetadataByName(String formName) {
		return formMetadataDAO.findFormMetadataByName(formName);
	}

	//@Override
	public List<FormMetadata> listAllFormMetadata() {
		List<FormMetadata> allFormMetaData = formMetadataDAO.listAll();
		// System Admin and Director can access data with any Form Metadata.
		if (isRoleAdmin() || isRoleSalesDirector()) {
			return allFormMetaData;
		}

		String currentAccount = getCurrentAccount().getAccount();
		List<FormMetadata> properFormMetaData = new LinkedList<FormMetadata>();
		for (FormMetadata formMetadata : allFormMetaData) {
			// Current user create, read, write or remove the data with this
			// FormMetaData
			if (formMetadata.getCanCreateAccounts().contains(currentAccount)
					|| formMetadata.getCanWriteAccounts().contains(currentAccount)
					|| formMetadata.getCanReadAccounts().contains(currentAccount)
					|| formMetadata.getCanRemoveAccounts().contains(currentAccount)) {
				properFormMetaData.add(formMetadata);
			}
		}

		return properFormMetaData;
	}

	//@Override
	public List<FormFieldMetadata> listAllFieldsOfFormMetadata(Long formMetadataId) {
		return formFieldMetadataDAO.listFieldsOfFormMetadata(formMetadataId);
	}

	//@Override
	public void addOrUpdateFormDefault(FormMetadata formMetadata, Map<String, Object> columnValueMap,
			DynamicFormDefault formObj) {
		DynamicFormDefault form = formObj;

		StringBuilder auditLog = new StringBuilder();
		LOG_OPERATIONS_ENUM op = LOG_OPERATIONS_ENUM.MODIFY_DATA;
		if (form.getId() == null) {
			form.setFormName(formMetadata.getFormName());
			form.setFormMetadataId(formMetadata.getId());
			form.setAssigneeAccount(getCurrentAccount().getAccount());

			// Change assignee to customer's sales rep.
			if (isRoleCustomer() && getCurrentAccount().getCustomerId() != null) {
				Customer customer = customerDAO.findById(getCurrentAccount().getCustomerId());
				if (customer != null) {
					if (customer.getSalesAccount() != null && !"na".equalsIgnoreCase(customer.getSalesAccount()))
						form.setAssigneeAccount(customer.getSalesAccount());
					else
						form.setAssigneeAccount(null);
				}
			}

			form.setStatus(FormRecordStatus_ENUM.NEW.name());
			form.setFormBizId(generateFormBizId(formMetadata.getFormShortName()));

			op = LOG_OPERATIONS_ENUM.CREATE_NEW;
		}

		// Add column to display map here for more friendly audit log text.
		Map<String, String> columnNameToDisplayNameMap = new HashMap<String, String>();
		Map<String, Object> fieldNameValueMap = new HashMap<String, Object>();
		List<FormFieldMetadata> expressionFieldsList = new LinkedList<FormFieldMetadata>();
		prepareFieldInformation(formMetadata, columnValueMap, columnNameToDisplayNameMap, fieldNameValueMap,
				expressionFieldsList);

		// Put user inputed value into form object
		putInputValueToFormObject(columnValueMap, form, auditLog, columnNameToDisplayNameMap);

		// Calculate Expression field
		calculateExpression(fieldNameValueMap, form, expressionFieldsList);

		// Save the form data
		dynamicFormDefaultDAO.saveOrUpdate(form, getCurrentAccount().getAccount());

		if (form.getId() == null || form.getId() == 0) {
			Long formId = dynamicFormDefaultDAO.getFormIdByFormBizId(form.getFormBizId());
			form.setId(formId);
		}

		// Save form data change log
		dynamicFormDefaultAuditLogDAO.addLog(op, form.getId(), getCurrentAccount(), auditLog.toString());

	}

	private void putInputValueToFormObject(Map<String, Object> columnValueMap, DynamicFormDefault form,
			StringBuilder auditLog, Map<String, String> columnNameToDisplayNameMap) {
		for (Map.Entry<String, Object> columnValue : columnValueMap.entrySet()) {
			if (columnValue.getValue() == null) {
				continue;
			}

			Method setter = findColumnSetter(form, columnValue.getKey());
			if (setter == null) {
				continue;
			}

			Class<?>[] types = setter.getParameterTypes();
			String valueString = columnValue.getValue().toString();

			auditLog.append(columnNameToDisplayNameMap.get(columnValue.getKey())).append("=[")
					.append(valueString.substring(0, valueString.length() > 20 ? 20 : valueString.length()))
					.append("] ");
			try {
				if (Integer.class.equals(types[0])) {
					setter.invoke(form, Integer.parseInt(valueString));
				} else if (Double.class.equals(types[0])) {
					setter.invoke(form, Double.parseDouble(valueString));
				} else if (Date.class.equals(types[0])) {
					setter.invoke(form, columnValue.getValue());
				} else {
					setter.invoke(form, valueString);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Re-orgnize the information for process later
	 * 
	 * @param formMetadata
	 * @param columnValueMap
	 * @param columnNameToDisplayNameMap
	 * @param fieldNameValueMap
	 * @param expressionField
	 */
	private void prepareFieldInformation(FormMetadata formMetadata, Map<String, Object> columnValueMap,
			Map<String, String> columnNameToDisplayNameMap, Map<String, Object> fieldNameValueMap,
			List<FormFieldMetadata> expressionField) {
		for (FormFieldMetadata field : listAllFieldsOfFormMetadata(formMetadata.getId())) {
			columnNameToDisplayNameMap.put(field.getColumnName(), field.getFieldLabel());

			fieldNameValueMap.put(field.getFieldName(), columnValueMap.get(field.getColumnName()));
			if (field.getExpression() != null && !field.getExpression().isEmpty()) {
				expressionField.add(field);
			}
		}
	}

	/**
	 * Calculate the Expression
	 * 
	 * @param fieldValueMap
	 *            Field -> value map
	 * @param form
	 *            form to put the calculate result
	 * @param expressionFieldsList
	 *            Fields list with Expression
	 */
	private void calculateExpression(Map<String, Object> fieldValueMap, DynamicFormDefault form,
			List<FormFieldMetadata> expressionFieldsList) {

		for (FormFieldMetadata f : expressionFieldsList) {
			String exp = f.getExpression();

			// Replace the field token with field value
			List<String> fieldsToken = extractFieldNameFromExpression(exp);
			for (String fToken : fieldsToken) {
				Object value = fieldValueMap.get(fToken.substring(1, fToken.length() - 1));
				if (value == null) {
					value = new Integer(0);
				}
				exp = exp.replace(fToken, value.toString());
			}

			// Do calculation by JEP
			Double rst = JEPHelper.calculateExpression(exp);

			// Get field setter and put the calculated result into it.
			Method setter = findColumnSetter(form, f.getColumnName());
			try {
				if (FormFieldTypes_ENUM.DOUBLE.getSqlType().equals(f.getFieldType())) {
					BigDecimal r = new BigDecimal(rst);
					setter.invoke(form, r.setScale(2, RoundingMode.HALF_UP).doubleValue());
				} else {
					setter.invoke(form, rst.intValue());
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * Extract the field name from expression. The expression could be
	 * "(#field1# + #field2#) * 0.5". We need to extract the field name and
	 * replace it with real value for those fields.
	 * 
	 * @param expression
	 * @return List<String> Field name list ordered by field name in expression.
	 */
	private List<String> extractFieldNameFromExpression(String expression) {
		Pattern p = Pattern.compile("(#\\w+#)");
		Matcher matcher = p.matcher(expression);

		List<String> fields = new LinkedList<String>();
		while (matcher.find()) {
			fields.add(matcher.group());
		}

		return fields;
	}

	private String generateFormBizId(String formShortName) {
		String id;
		synchronized (this) {
			id = Long.toString(new Date().getTime());
		}
		if (formShortName != null && !formShortName.trim().isEmpty()) {
			return formShortName.trim() + "-" + id;
		}

		return id;
	}

	private Method findColumnSetter(DynamicFormDefault form, String columnName) {
		String[] columnNameParts = columnName.split("_");
		if (columnNameParts.length != 3) {
			return null;
		}

		StringBuilder columnSetterName = new StringBuilder();
		columnSetterName.append("set");
		for (String part : columnNameParts) {
			columnSetterName.append(StringUtils.capitalize(part.toLowerCase()));
		}

		String columnType = columnNameParts[1];

		try {
			if (FormFieldTypes_ENUM.VARCHAR.getSqlType().equals(columnType)
					|| FormFieldTypes_ENUM.TEXT.getSqlType().equals(columnType)) {
				return form.getClass().getMethod(columnSetterName.toString(), String.class);
			} else if (FormFieldTypes_ENUM.DATETIME.getSqlType().equals(columnType)) {
				return form.getClass().getMethod(columnSetterName.toString(), Date.class);
			} else if (FormFieldTypes_ENUM.DOUBLE.getSqlType().equals(columnType)) {
				return form.getClass().getMethod(columnSetterName.toString(), Double.class);
			} else if (FormFieldTypes_ENUM.INT.getSqlType().equals(columnType)) {
				return form.getClass().getMethod(columnSetterName.toString(), Integer.class);
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			// TODO Error handling
		}

		return null;
	}

	private Method findColumnGetter(DynamicFormDefault form, String columnName) {
		String[] columnNameParts = columnName.split("_");
		if (columnNameParts.length != 3) {
			return null;
		}

		StringBuilder columnGetterName = new StringBuilder();
		columnGetterName.append("get");
		for (String part : columnNameParts) {
			columnGetterName.append(StringUtils.capitalize(part.toLowerCase()));
		}

		try {
			return form.getClass().getMethod(columnGetterName.toString());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		return null;
	}

	//@Override
	public List<FormListEntry> listFormDefault(Long formMetadataId) {
		if (getCurrentAccount().hasRole(Roles_ENUM.ROLE_SALES_DIRECTOR.name())) {
			return dynamicFormDefaultDAO.listAllForms(formMetadataId);
		}

        Account newAccount = accountService.findAllChildrenByAccount(this.getCurrentAccount().getAccount());
        return dynamicFormDefaultDAO.listBelongedFormsToMe(newAccount, formMetadataId);
		//return dynamicFormDefaultDAO.listFormsToMe(getCurrentAccount(), formMetadataId);
	}

	//@Override
	public DynamicFormDefault loadDynamicFormDefaultDataById(Long dynamicFormDefaultId) {
		DynamicFormDefault rst = dynamicFormDefaultDAO.findById(dynamicFormDefaultId);

		return rst;
	}

	//@Override
	public Map<String, Object> convertDynamicFormDefaultToMap(DynamicFormDefault order) {
		Map<String, Object> columnNameValueMapper = new HashMap<String, Object>();
		Method[] methods = DynamicFormDefault.class.getMethods();
		for (Method method : methods) {
			Column annotationColumn = method.getAnnotation(Column.class);
			if (annotationColumn != null) {
				String columnName = annotationColumn.name().toLowerCase();
				try {
					Object value = method.invoke(order);
					if (value instanceof Timestamp) {
						value = new Date(((Timestamp) value).getTime());
						columnNameValueMapper.put(columnName, value);
					} else {
						// TODO Why have to add a String object into map,
						// otherwise the validatorMessage cannot show up?
						columnNameValueMapper.put(columnName, value == null ? value : value.toString());
					}

				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return columnNameValueMapper;
	}

	//@Override
	public void deleteField(long fieldId) {
		formFieldMetadataDAO.removeById(fieldId);
	}

	//@Override
	public void changeFormDefaultAssignTo(DynamicFormDefault order) {
		dynamicFormDefaultDAO.updateDynamicFormDefaultAssignTo(order.getId(), order.getAssigneeAccount());

		dynamicFormDefaultAuditLogDAO.addLog(LOG_OPERATIONS_ENUM.MODIFY_ASSIGNEE, order.getId(), getCurrentAccount(),
				"Assign data to " + order.getAssigneeAccount());
	}

	//@Override
	public void changeFormDefaultStatus(DynamicFormDefault order) {
		dynamicFormDefaultDAO.updateDynamicFormDefaultStatus(order.getId(), order.getStatus());

		dynamicFormDefaultAuditLogDAO.addLog(LOG_OPERATIONS_ENUM.MODIFY_STATUS, order.getId(), getCurrentAccount(),
				"Change status to " + order.getStatus());
	}

	//@Override
	public List<FormListEntry> searchByCriteria(Map<String, String> searchCriteriaValueMap,Map<String, List<String>> notEqualCriteriaValueMap, Long formMetadataId) {
		if (isRoleSalesDirector()) {
			return dynamicFormDefaultDAO.searchByCriteria(searchCriteriaValueMap,notEqualCriteriaValueMap, formMetadataId);
		} else {
			return dynamicFormDefaultDAO.searchByCriteria(searchCriteriaValueMap,notEqualCriteriaValueMap, getCurrentAccount().getAccount(),
					formMetadataId);
		}
	}

	//@Override
	public List<DynamicFormDefaultAuditLog> listAuditLog(Long formId) {
		return dynamicFormDefaultAuditLogDAO.listAuditLog(formId);
	}

	//@Override
	public List<DynamicFormDefaultAuditLog> listAuditLogForCurrentAccount() {
		return dynamicFormDefaultAuditLogDAO.listAuditLogByAccount(getCurrentAccount());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dynamo2.myerp.dynamicform.service.FormMetadataService#
	 * validateExpressionField(java.lang.String)
	 */
	//@Override
	public String validateExpressionField(String expression, List<FormFieldMetadata> fields)
			throws DynamicFormServiceException {

		List<String> fieldsName = extractFieldNameFromExpression(expression);
		int foundFieldCount = 0;
		String testExp = expression;

		String expressionLabel = expression;
		for (FormFieldMetadata f : fields) {
			if (fieldsName.contains("#" + f.getFieldName() + "#")) {
				if (f.getExpression() != null && !f.getExpression().isEmpty()) {
					throw new DynamicFormServiceException(ERR_CODE.CFG_FIELD_EXPRESSION_NOT_SUPPORT_EMBEDDED_EXPRESSION);
				}

				if (!f.getIsRequired()) {
					throw new DynamicFormServiceException(ERR_CODE.CFG_FIELD_EXPRESSION_VAR_FIELD_SHOULD_BE_REQUIRED);
				}

				if (!FormFieldTypes_ENUM.DOUBLE.getSqlType().equalsIgnoreCase(f.getFieldType())
						&& !FormFieldTypes_ENUM.INT.getSqlType().equalsIgnoreCase(f.getFieldType())) {
					throw new DynamicFormServiceException(ERR_CODE.CFG_FIELD_INVALID_EXPRESSION_FIELD_TYPE);
				}

				foundFieldCount++;

				testExp = testExp.replace("#" + f.getFieldName() + "#", "1");
				expressionLabel = expressionLabel.replace("#" + f.getFieldName() + "#", " " + f.getFieldLabel() + " ");
			}
		}

		if (foundFieldCount != fieldsName.size()) {
			throw new DynamicFormServiceException(ERR_CODE.CFG_FIELD_EXPRESSION_VAR_FIELD_CANNOT_FIND);
		}

		try {
			JEPHelper.calculateExpression(testExp);
		} catch (IllegalArgumentException e) {
			throw new DynamicFormServiceException(ERR_CODE.CFG_FIELD_INVALID_EXPRESSION);
		}

		return expressionLabel;
	}

	//@Override
	public List<FormListEntry> listFormDefaultForCustomer(Long formMetadataId, Long customerId) {
		return dynamicFormDefaultDAO.listAllFormsForCustomer(customerId, formMetadataId);
	}

	//@Override
	public List<FormMetadata> listAllFormMetadataByCategory(FORM_CATEGORY_ENUM category) {
		List<FormMetadata> formMetadataList = formMetadataDAO.listAllByCategory(category.getLowerCasedName());
		List<FormMetadata> rst = new ArrayList<FormMetadata>(formMetadataList.size());
		for (FormMetadata formMetadata : formMetadataList) {
			if (FormMetadataStatus_ENUM.PUBLISHED.name().equalsIgnoreCase(formMetadata.getFormStatus())) {
				if (hasPermission(PERMISSION_DEFINITION_ENUM.FORM_DATA_QUERY_ALL) || 
						formMetadata.getCanReadAccountList().contains(getCurrentAccount().getAccount())) {
					rst.add(formMetadata);
				}
			}
		}

		return rst;
	}

	//@Override
	public List<FormWorkflow> listWorkFlowSteps(Long formMetadataId) {
		return formWorkFlowDAO.listWorkFlowSteps(formMetadataId);
	}

	//@Override
	public void addOrUpdateWorkFlowStep(FormWorkflow newWorkflowStep) {
		formWorkFlowDAO.saveOrUpdate(newWorkflowStep, null);

	}

	//@Override
	public void deleteWorkFlowStep(long id) {
		formWorkFlowDAO.removeById(id);
	}

	//@Override
	public Object[] searchCertification(String certificationSN, String verficationCode) {

		return dynamicFormDefaultDAO.searchCertification(certificationSN, verficationCode);
	}

	//@Override
	public void updateParentFormRecord(Long parentFormRecordId, String parentFormFieldColumnName,
			DynamicFormDefault childFormObject) {
		DynamicFormDefault parent = dynamicFormDefaultDAO.findById(parentFormRecordId);
		Method setter = findColumnSetter(parent, parentFormFieldColumnName);
		try {
			setter.invoke(parent, childFormObject.getFormBizId());
			dynamicFormDefaultDAO.saveOrUpdate(parent, getCurrentAccount().getAccount());

			childFormObject.setParentFormBizId(parent.getFormBizId());
			dynamicFormDefaultDAO.saveOrUpdate(childFormObject, getCurrentAccount().getAccount());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//@Override
	public DynamicFormDefault loadDynamicFormDefaultDataByBizKey(String formBizKey) {
		Long id = dynamicFormDefaultDAO.getFormIdByFormBizId(formBizKey);
		if (id != null)
			return dynamicFormDefaultDAO.findById(id);

		return null;
	}

	//@Override
	public List<List<List<FormFieldMetadata>>> extractGroupedFieldMetadataList(Long formMetadataId) {
		List<FormFieldMetadata> formFieldMetadataList = listAllFieldsOfFormMetadata(formMetadataId);

		List<List<List<FormFieldMetadata>>> groupedFields = new ArrayList<List<List<FormFieldMetadata>>>();
		int currentGroupNumber = formFieldMetadataList.get(0).getDisplayPositionGroup();
		int currentRowNumber = formFieldMetadataList.get(0).getDisplayPositionRow();
		List<FormFieldMetadata> currentRow = new ArrayList<FormFieldMetadata>();
		List<List<FormFieldMetadata>> currentGroup = new ArrayList<List<FormFieldMetadata>>();
		currentGroup.add(currentRow);
		groupedFields.add(currentGroup);
		for (FormFieldMetadata field : formFieldMetadataList) {
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

		return groupedFields;
	}

	//@Override
	public List<FormListEntry> listFormDefaultOperatedByCurrentUser() {
		return dynamicFormDefaultDAO.listFormsOperatedByMe(getCurrentAccount().getAccount());
	}

	//@Override
	public Object getFieldValueOfForm(DynamicFormDefault formObject, String fieldName) {
		if (formObject == null || formObject.getFormMetadataId() == null) {
			return null;
		}

		FormFieldMetadata field = formFieldMetadataDAO.findFieldByFormMetadataIdAndFieldName(
				formObject.getFormMetadataId(), fieldName);
		if (field == null) {
			return null;
		}

		Method getter = findColumnGetter(formObject, field.getColumnName());
		if (getter == null) {
			return null;
		}

		try {
			return getter.invoke(formObject);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	//@Override
	public void setFieldValueOfForm(DynamicFormDefault formObject, String fieldName, String value) {
		if (formObject == null || formObject.getFormMetadataId() == null) {
			return;
		}

		FormFieldMetadata field = formFieldMetadataDAO.findFieldByFormMetadataIdAndFieldName(
				formObject.getFormMetadataId(), fieldName);
		if (field == null) {
			return;
		}

		Method setter = findColumnSetter(formObject, field.getColumnName());
		if (setter == null) {
			return;
		}

		try {
			Class<?>[] types = setter.getParameterTypes();
			if (types != null && types.length > 0) {
				String typeName = types[0].getName();
				if (typeName.equals(Double.class.getName())) {
					setter.invoke(formObject, Double.parseDouble(value));
				} else if (typeName.equals(Integer.class.getName())) {
					setter.invoke(formObject, Integer.parseInt(value));
				} else if (typeName.equals(String.class.getName())) {
					setter.invoke(formObject, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//@Override
	public void fillInDefaultValue(DynamicFormDefault formObject, DynamicFormDefault parentFormObject) {

		Long formMetadataId = formObject.getFormMetadataId();

		if (formMetadataId != null && formMetadataId > 0L) {
			List<FormFieldMetadata> fields = formFieldMetadataDAO.listFieldsOfFormMetadata(formObject
					.getFormMetadataId());
			if (fields != null) {
				for (FormFieldMetadata formFieldMetadata : fields) {
					// Check if there have default value of that field
					String defaultValue = formFieldMetadata.getDefaultValue();
					if (defaultValue != null && !defaultValue.trim().isEmpty()) {
						// Check if value of that field is null
						Object v = getFieldValueOfForm(formObject, formFieldMetadata.getFieldName());
						if (v == null) {
							// Set default value only when value of that field
							// is null
							defaultValue = defaultValue.trim();
							if (parentFormObject != null
									&& defaultValue.startsWith("#" + parentFormObject.getFormName() + ".")) {
								String fieldName = defaultValue.replace("#" + parentFormObject.getFormName() + ".", "");
								v = getFieldValueOfForm(parentFormObject, fieldName);
								if (v != null) {
									setFieldValueOfForm(formObject, formFieldMetadata.getFieldName(), v.toString());
								}
							} else if (!defaultValue.startsWith("#")) {
								setFieldValueOfForm(formObject, formFieldMetadata.getFieldName(), defaultValue);
							}
						}
					}
				}
			}
		}
	}

    //@Override
    public List<FormListEntry> listFormsToParentForm(String parentBizID,Long formMetadataId) {
        return this.dynamicFormDefaultDAO.listFormsToParentForm(parentBizID,formMetadataId);
    }

    //@Override
    public List<FormMetadata> listFormMetadataByParent(Long parentID) {
        return this.formMetadataDAO.listByParentForm(parentID);
    }

    //@Override
    public List<FormMDStatisticsEntry> statisticsByStatus(){
        return this.formMetadataDAO.statisticsByStatus();
    }

    //@Override
    public void updateCategory(FormMetadata fm){
        this.formMetadataDAO.updateCategory(fm);
    }

}
