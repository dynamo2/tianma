package com.dynamo2.myerp.dynamicform.ui.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.Table;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.icefaces.bean.ViewRetained;
import org.icefaces.bean.WindowDisposed;

import au.com.bytecode.opencsv.CSVWriter;

import com.dynamo2.myerp.dynamicform.dao.entities.DynamicFormDefault;
import com.dynamo2.myerp.dynamicform.dao.entities.FormFieldMetadata;
import com.dynamo2.myerp.dynamicform.dao.entities.FormMetadata;
import com.dynamo2.myerp.dynamicform.dao.entities.FormWorkflow;
import com.dynamo2.myerp.dynamicform.report.FormMetadataIdCondition;
import com.dynamo2.myerp.dynamicform.service.FormFieldTypes_ENUM;
import com.dynamo2.myerp.dynamicform.service.FormMetadataService;
import com.dynamo2.myerp.dynamicform.service.FormRecordStatus_ENUM;
import com.dynamo2.myerp.report.dao.entities.ReportDefinition;
import com.dynamo2.myerp.report.definition.BetweenExpression;
import com.dynamo2.myerp.report.definition.BinaryExpression;
import com.dynamo2.myerp.report.definition.ColumnExpression;
import com.dynamo2.myerp.report.definition.Expression;
import com.dynamo2.myerp.report.definition.MySQLInThisMonthExpression;
import com.dynamo2.myerp.report.definition.MySQLInThisWeekExpression;
import com.dynamo2.myerp.report.definition.MySQLInThisYearExpression;
import com.dynamo2.myerp.report.definition.MySQLInTodayExpression;
import com.dynamo2.myerp.report.definition.Operator;
import com.dynamo2.myerp.report.definition.Operator.OPERATION_ENUM;
import com.dynamo2.myerp.report.definition.OrderByColumn;
import com.dynamo2.myerp.report.definition.ReportColumn;
import com.dynamo2.myerp.report.definition.ReportCondition;
import com.dynamo2.myerp.report.definition.ReportQueryDefinition;
import com.dynamo2.myerp.report.definition.ReportTable;
import com.dynamo2.myerp.report.definition.ValueExpression;
import com.dynamo2.myerp.report.service.ReportService;
import com.dynamo2.myerp.service.ServiceException;
import com.dynamo2.util.JSFUtils;

@ManagedBean(name = "dynamicFormReportDefinitionMgmtBean")
@ViewScoped
@ViewRetained
@WindowDisposed
public class DynamicFormReportDefinitionMgmtBean implements Serializable {

	public class FormFieldInReport {

		private FormFieldMetadata field;
		private boolean isInSelection;
		private boolean isInWhere;
		private List<ConditionForField> conditionList;

		public FormFieldMetadata getField() {
			return field;
		}

		public void setField(FormFieldMetadata field) {
			this.field = field;
		}

		public boolean isInSelection() {
			return isInSelection;
		}

		public void setInSelection(boolean isInSelection) {
			this.isInSelection = isInSelection;
		}

		public boolean isInWhere() {
			return isInWhere;
		}

		public void setInWhere(boolean isInWhere) {
			this.isInWhere = isInWhere;
		}

		public List<ConditionForField> getConditionList() {
			return conditionList;
		}

		public void setConditionList(List<ConditionForField> conditionList) {
			this.conditionList = conditionList;
		}

		public void addCondition(ConditionForField c) {
			this.conditionList.add(c);
		}

		public void removeCondition(int index) {
			this.conditionList.remove(index);
		}
	}

	public class ConditionForField {
		private String op;

		private Object value;

		private FormFieldInReport fieldInReport;

		private Date startDate;

		private Date endDate;

		private boolean dateRangeCondition;

		public boolean isDateRangeCondition() {
			return dateRangeCondition;
		}

		public void setDateRangeCondition(boolean dateRangeCondition) {
			this.dateRangeCondition = dateRangeCondition;
		}

		public Date getStartDate() {
			return startDate;
		}

		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}

		public Date getEndDate() {
			return endDate;
		}

		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}

		public String getOp() {
			return op;
		}

		public void setOp(String op) {
			this.op = op;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public FormFieldInReport getFieldInReport() {
			return fieldInReport;
		}

		public void setFieldInReport(FormFieldInReport fieldInReport) {
			this.fieldInReport = fieldInReport;
		}
	}

	public class DateRangeConditionForField {

	}

	/**
	 * Data structure to represent the fixed column in a Form
	 * 
	 * @author fwang
	 * 
	 */
	public class FormFixedColumn {
		String columnName;
		String columnLabel;
		boolean isInWhere = false;
		boolean isDateTypedColumn = false;
		ConditionForField condition = new ConditionForField();
		List<SelectItem> whereOptions;

		public boolean isDateTypedColumn() {
			return isDateTypedColumn;
		}

		public void setDateTypedColumn(boolean isDateTypedColumn) {
			this.isDateTypedColumn = isDateTypedColumn;
		}

		public ConditionForField getCondition() {
			return condition;
		}

		public void setCondition(ConditionForField condition) {
			this.condition = condition;
		}

		public List<SelectItem> getWhereOptions() {
			return whereOptions;
		}

		public void setWhereOptions(List<SelectItem> whereOptions) {
			this.whereOptions = whereOptions;
		}

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}

		public String getColumnLabel() {
			return columnLabel;
		}

		public void setColumnLabel(String columnLabel) {
			this.columnLabel = columnLabel;
		}

		public boolean isInWhere() {
			return isInWhere;
		}

		public void setInWhere(boolean isInWhere) {
			this.isInWhere = isInWhere;
		}
	}

	private static final long serialVersionUID = 1L;

	private Long formMetadataId;

	private FormMetadata formMetadata;

	private Long reportDefinitionId;

	private ReportDefinition reportDefinition;

	private String orderByFieldName;

	private boolean orderByASC;

	@ManagedProperty(value = "#{formMetadataService}")
	private FormMetadataService formMetadataService;

	@ManagedProperty(value = "#{reportService}")
	private ReportService reportService;

	private List<FormFieldInReport> formFieldsInReport;

	private Map<String, FormFieldInReport> formFieldMappingByFieldName;

	private List<String> selectedFieldNameList = new LinkedList<String>();

	private List<String> selectedFixedColumnNameList = new LinkedList<String>();

	private Map<String, FormFixedColumn> formFixedColumnMapByColumnName;

	private List<String> formFixedColumnNameList = new LinkedList<String>();

	private Map<String, String> selectedColumnName2LabelMap = new LinkedHashMap<String, String>();

	private Map<String, List<ConditionForField>> conditionColumnNameMap = new LinkedHashMap<String, List<ConditionForField>>();

	private List<Object[]> reportResult;

	private FormFieldInReport currentFieldInReport;

	private ConditionForField currentFieldCondition = new ConditionForField();

	private void initReport() {
		initFormFixedColumn();

		if (reportDefinitionId != null) {
			reportDefinition = reportService.loadById(reportDefinitionId);
			if (reportDefinition != null) {
				parseReportDefinition(reportDefinition);
				initFormMetadata();
			}
		}
	}

	private void initFormMetadata() {
		initFormFixedColumn();

		// formMetadataId could be find from ReportDefinition, or setted from
		// request parameter for a new report creation request.
		if (formMetadataId != null) {
			if (formMetadata == null) {
				List<FormFieldMetadata> fields = formMetadataService.listAllFieldsOfFormMetadata(formMetadataId);
				formMetadata = formMetadataService.loadById(formMetadataId);

				formFieldsInReport = new ArrayList<FormFieldInReport>(fields.size());
				formFieldMappingByFieldName = new LinkedHashMap<String, DynamicFormReportDefinitionMgmtBean.FormFieldInReport>();

				for (FormFieldMetadata f : fields) {
					// Ignore the process for reference in report for now
					if (f.getReferToFormMetadataId() != null) {
						continue;
					}

					FormFieldInReport e = new FormFieldInReport();
					e.setField(f);
					formFieldsInReport.add(e);
					formFieldMappingByFieldName.put(f.getFieldName(), e);

					if (selectedColumnName2LabelMap.containsKey(f.getColumnName())) {
						e.setInSelection(true);
						selectedFieldNameList.add(f.getFieldName());
						selectedColumnName2LabelMap.put(f.getColumnName(), f.getFieldLabel());
					}

					if (conditionColumnNameMap.containsKey(f.getColumnName())) {
						List<ConditionForField> conds = conditionColumnNameMap.get(f.getColumnName());
						for (ConditionForField c : conds) {
							c.setFieldInReport(e);
						}

						e.setInWhere(true);
						e.setConditionList(conds);
					} else {
						e.setConditionList(new LinkedList<ConditionForField>());
					}
				}
			}
		} else {
			// Should never run into here.
			formFieldsInReport = Collections.emptyList();
		}
	}

	private void initFormFixedColumn() {
		if (formFieldMappingByFieldName != null) {
			return;
		}

		formFixedColumnMapByColumnName = new LinkedHashMap<String, FormFixedColumn>();

		FormFixedColumn formBizIdColumn = new FormFixedColumn();
		formBizIdColumn.setColumnName("form_biz_id");
		formBizIdColumn.setColumnLabel(JSFUtils.getI18NMessage(formBizIdColumn.getColumnName()));
		formBizIdColumn.setInWhere(true);
		formFixedColumnMapByColumnName.put(formBizIdColumn.getColumnName(), formBizIdColumn);
		formFixedColumnNameList.add(formBizIdColumn.getColumnName());

		FormFixedColumn customerIdColumn = new FormFixedColumn();
		customerIdColumn.setColumnName("customer_id");
		customerIdColumn.setColumnLabel(JSFUtils.getI18NMessage("customer_name"));
		formFixedColumnMapByColumnName.put(customerIdColumn.getColumnName(), customerIdColumn);
		formFixedColumnNameList.add(customerIdColumn.getColumnName());

		FormFixedColumn statusColumn = new FormFixedColumn();
		statusColumn.setColumnName("status");
		statusColumn.setColumnLabel(JSFUtils.getI18NMessage(statusColumn.getColumnName()));
		List<FormWorkflow> formWorkflowSteps = formMetadataService.listWorkFlowSteps(formMetadataId);
		List<SelectItem> whereOptions = new LinkedList<SelectItem>();
		for (FormRecordStatus_ENUM r : FormRecordStatus_ENUM.values()) {
			whereOptions.add(new SelectItem(r.name(), JSFUtils.getI18NMessage(r.name())));
		}
		for (FormWorkflow step : formWorkflowSteps) {
			whereOptions.add(new SelectItem(step.getStatusName(), step.getStatusLabel()));
		}
		statusColumn.setWhereOptions(whereOptions);
		statusColumn.setInWhere(true);
		formFixedColumnMapByColumnName.put(statusColumn.getColumnName(), statusColumn);
		formFixedColumnNameList.add(statusColumn.getColumnName());

		FormFixedColumn createdColumn = new FormFixedColumn();
		createdColumn.setColumnName("created");
		createdColumn.setColumnLabel(JSFUtils.getI18NMessage(createdColumn.getColumnName()));
		createdColumn.setDateTypedColumn(true);
		formFixedColumnMapByColumnName.put(createdColumn.getColumnName(), createdColumn);
		formFixedColumnNameList.add(createdColumn.getColumnName());

		FormFixedColumn createdByColumn = new FormFixedColumn();
		createdByColumn.setColumnName("created_by");
		createdByColumn.setColumnLabel(JSFUtils.getI18NMessage(createdByColumn.getColumnName()));
		formFixedColumnMapByColumnName.put(createdByColumn.getColumnName(), createdByColumn);
		formFixedColumnNameList.add(createdByColumn.getColumnName());

		FormFixedColumn lastModifiedColumn = new FormFixedColumn();
		lastModifiedColumn.setColumnName("last_modified");
		lastModifiedColumn.setColumnLabel(JSFUtils.getI18NMessage(lastModifiedColumn.getColumnName()));
		lastModifiedColumn.setDateTypedColumn(true);
		formFixedColumnMapByColumnName.put(lastModifiedColumn.getColumnName(), lastModifiedColumn);
		formFixedColumnNameList.add(lastModifiedColumn.getColumnName());

		FormFixedColumn lastModifiedByColumn = new FormFixedColumn();
		lastModifiedByColumn.setColumnName("last_modified_by");
		lastModifiedByColumn.setColumnLabel(JSFUtils.getI18NMessage(lastModifiedByColumn.getColumnName()));
		formFixedColumnMapByColumnName.put(lastModifiedByColumn.getColumnName(), lastModifiedByColumn);
		formFixedColumnNameList.add(lastModifiedByColumn.getColumnName());

		FormFixedColumn assigneeAccountColumn = new FormFixedColumn();
		assigneeAccountColumn.setColumnName("assignee_account");
		assigneeAccountColumn.setColumnLabel(JSFUtils.getI18NMessage(assigneeAccountColumn.getColumnName()));
		formFixedColumnMapByColumnName.put(assigneeAccountColumn.getColumnName(), assigneeAccountColumn);
		formFixedColumnNameList.add(assigneeAccountColumn.getColumnName());
	}

	private void parseReportDefinition(ReportDefinition def) {
		ReportQueryDefinition queryDef = def.getReportDefinitionObj();
		if (queryDef != null) {
			// Parse selections
			List<ReportColumn> selections = queryDef.getSelectColumns();
			for (ReportColumn reportColumn : selections) {
				selectedColumnName2LabelMap.put(reportColumn.getName(), reportColumn.getName());

				// If column is a Form fixed column, then put it into
				// selectedFixedColumnNameList
				FormFixedColumn fixedColumn = formFixedColumnMapByColumnName.get(reportColumn.getName());
				if (fixedColumn != null) {
					selectedFixedColumnNameList.add(fixedColumn.getColumnName());
					selectedColumnName2LabelMap.put(fixedColumn.getColumnName(), fixedColumn.getColumnLabel());
				}
			}

			// Parse conditions
			List<ReportCondition> conditions = queryDef.getWhereConditions();
			for (ReportCondition reportCondition : conditions) {
				// Parse FormMedataId condition
				if (reportCondition instanceof FormMetadataIdCondition) {
					formMetadataId = ((FormMetadataIdCondition) reportCondition).getFormMetadataId();
					continue;
				}

				// Parse other conditions
				Expression exp = reportCondition.getExp();
				if (exp instanceof BinaryExpression) {
					if (((BinaryExpression) exp).getLeft() instanceof ColumnExpression
							&& ((BinaryExpression) exp).getRight() instanceof ValueExpression) {
						ColumnExpression c = (ColumnExpression) ((BinaryExpression) exp).getLeft();
						ValueExpression v = (ValueExpression) ((BinaryExpression) exp).getRight();

						addToConditionColumnNameMap(((BinaryExpression) exp).getOperator().toQueryString(), c
								.getColumn().getName(), v.getValue().toString());
					}
				} else if (exp instanceof MySQLInTodayExpression) {
					addToConditionColumnNameMap("=", ((MySQLInTodayExpression) exp).getColumn().getColumn().getName(),
							"today");
				} else if (exp instanceof MySQLInThisWeekExpression) {
					addToConditionColumnNameMap("=", ((MySQLInThisWeekExpression) exp).getColumn().getColumn()
							.getName(), "this_week");

				} else if (exp instanceof MySQLInThisMonthExpression) {
					addToConditionColumnNameMap("=", ((MySQLInThisMonthExpression) exp).getColumn().getColumn()
							.getName(), "this_month");

				} else if (exp instanceof MySQLInThisYearExpression) {
					addToConditionColumnNameMap("=", ((MySQLInThisYearExpression) exp).getColumn().getColumn()
							.getName(), "this_year");

				} else if (exp instanceof BetweenExpression) {
					addToConditionColumnNameMap(((BetweenExpression) exp).getColumn().getColumn().getName(),
							((BetweenExpression) exp).getStart().getValue(), ((BetweenExpression) exp).getEnd()
									.getValue());
				}
			}
		}
	}

	private void addToConditionColumnNameMap(String columnName, Object start, Object end) {
		ConditionForField condition = new ConditionForField();
		condition.setDateRangeCondition(true);
		condition.setStartDate((Date) start);
		condition.setEndDate((Date) end);

		if (!conditionColumnNameMap.containsKey(columnName)) {
			conditionColumnNameMap.put(columnName, new LinkedList<ConditionForField>());
		}

		conditionColumnNameMap.get(columnName).add(condition);
	}

	private void addToConditionColumnNameMap(String op, String columnName, String value) {
		ConditionForField condition = new ConditionForField();
		condition.setOp(op);
		condition.setValue(value);

		// Check if condition is for Form fixed column
		FormFixedColumn fixedColumn = formFixedColumnMapByColumnName.get(columnName);
		if (fixedColumn != null) {
			fixedColumn.setInWhere(true);
			fixedColumn.setCondition(condition);
		} else {
			// Condition is for configured field in Form
			if (!conditionColumnNameMap.containsKey(columnName)) {
				conditionColumnNameMap.put(columnName, new LinkedList<ConditionForField>());
			}

			conditionColumnNameMap.get(columnName).add(condition);
		}
	}

	public List<String> getSelectedFieldNameList() {
		return selectedFieldNameList;
	}

	public void setSelectedFieldNameList(List<String> selectedFieldNameList) {
		this.selectedFieldNameList = selectedFieldNameList;
	}

	public FormMetadataService getFormMetadataService() {
		return formMetadataService;
	}

	public void setFormMetadataService(FormMetadataService formMetadataService) {
		this.formMetadataService = formMetadataService;
	}

	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public String getOrderByFieldName() {
		return orderByFieldName;
	}

	public void setOrderByFieldName(String orderByFieldName) {
		this.orderByFieldName = orderByFieldName;
	}

	public boolean isOrderByASC() {
		return orderByASC;
	}

	public void setOrderByASC(boolean orderByASC) {
		this.orderByASC = orderByASC;
	}

	public ReportDefinition getReportDefinition() {
		if (reportDefinition == null) {
			if (reportDefinitionId != null) {
				reportDefinition = reportService.loadById(reportDefinitionId);
			} else {
				reportDefinition = new ReportDefinition();
			}
		}
		return reportDefinition;
	}

	public List<FormFieldInReport> getFormFieldsInReport() {
		return formFieldsInReport;
	}

	public Long getFormMetadataId() {
		return formMetadataId;
	}

	public void setFormMetadataId(Long formMetadataId) {
		if (this.formMetadataId == null || !this.formMetadataId.equals(formMetadataId)) {
			this.formMetadataId = formMetadataId;
			initFormMetadata();
		}
	}

	public Long getReportDefinitionId() {
		return reportDefinitionId;
	}

	public void setReportDefinitionId(Long reportDefinitionId) {
		if (this.reportDefinitionId == null || !this.reportDefinitionId.equals(reportDefinitionId)) {
			this.reportDefinitionId = reportDefinitionId;
			initReport();
		}
	}

	public void loadCurrentField() {
		String fieldName = JSFUtils.getHttpRequestParam("fieldName");
		currentFieldInReport = formFieldMappingByFieldName.get(fieldName);
	}

	public void loadConditionForCurrentFieldByIndex() {
		int index = Integer.parseInt(JSFUtils.getHttpRequestParam("conditionIndex"));
		currentFieldCondition = currentFieldInReport.getConditionList().get(index);
	}

	public ConditionForField getCurrentFieldCondition() {
		return currentFieldCondition;
	}

	public void setCurrentFieldCondition(ConditionForField currentFieldCondition) {
		this.currentFieldCondition = currentFieldCondition;
	}

	public void saveConditionForCurrentField() {
		if (currentFieldCondition.getFieldInReport() == null) {
			currentFieldCondition.setFieldInReport(currentFieldInReport);
			currentFieldInReport.addCondition(currentFieldCondition);
		}

		currentFieldCondition = null;
		currentFieldInReport.setInWhere(true);
	}

	public void removeConditionForCurrentFieldByIndex() {
		int index = Integer.parseInt(JSFUtils.getHttpRequestParam("conditionIndex"));
		currentFieldInReport.removeCondition(index);

		if (currentFieldInReport.getConditionList().isEmpty()) {
			currentFieldInReport.setInSelection(false);
		}
	}

	public void newConditionForCurrentField() {
		currentFieldCondition = new ConditionForField();
	}

	public void newDateRangeConditionForCurrentField() {
		currentFieldCondition = new ConditionForField();
		currentFieldCondition.setDateRangeCondition(true);
	}

	public FormFieldInReport getCurrentFieldInReport() {
		return currentFieldInReport;
	}

	public void saveReport() throws ServiceException {
		String tableName = DynamicFormDefault.class.getAnnotation(Table.class).name();
		ReportTable table = new ReportTable(tableName, tableName);

		ReportQueryDefinition query = new ReportQueryDefinition();
		query.setFromTable(table);

		// Save the selection and conditions for fixed column
		addSelectionCondition4FormFixedColumns(table, query);

		// Save the selection and conditions for form configured fields
		addSelectionCondition4FormConfiguredFields(table, query);

		// Add condition to filter form_metadata_id
		query.addWhereCondition(new FormMetadataIdCondition(formMetadataId));

		// Add condition to filter DELETED record
		query.addWhereCondition(new ReportCondition(new BinaryExpression(new ColumnExpression(new ReportColumn(
				"status", table)), new ValueExpression(FormRecordStatus_ENUM.DELETED.name()), new Operator(
				OPERATION_ENUM.NE))));

		// Add order by for last_modified
		ReportColumn lastModifed = new ReportColumn("last_modified", table);
		OrderByColumn order = new OrderByColumn(lastModifed, false);
		query.addOrderByColumn(order);

		// Save query
		reportDefinition.setReportDefinitionObj(query);
		reportService.newOrUpdate(reportDefinition);

		JSFUtils.addMessagesToComponent("dynamicFormReportConfigForm",
				JSFUtils.getI18NMessage("operationFinishedSuccessfully"));
	}

	private void addSelectionCondition4FormConfiguredFields(ReportTable table, ReportQueryDefinition query) {
		for (FormFieldInReport fieldInReport : formFieldsInReport) {
			ReportColumn c = new ReportColumn(fieldInReport.field.getColumnName(), table);

			if (selectedFieldNameList.contains(fieldInReport.getField().getFieldName())) {
				query.addSelectColumn(c);
			}

			if (fieldInReport.isInWhere) {
				for (ConditionForField cond : fieldInReport.conditionList) {
					boolean isDateTyped = fieldInReport.getField().getFieldType()
							.equalsIgnoreCase(FormFieldTypes_ENUM.DATETIME.name());
					ReportCondition whereCondition = createWhereCondition(c, cond, isDateTyped);
					query.addWhereCondition(whereCondition);
				}

			}
		}
	}

	private void addSelectionCondition4FormFixedColumns(ReportTable table, ReportQueryDefinition query) {
		for (String fixedColumnName : selectedFixedColumnNameList) {
			query.addSelectColumn(new ReportColumn(fixedColumnName, table));
		}

		for (FormFixedColumn fixedColumn : formFixedColumnMapByColumnName.values()) {
			if (fixedColumn.getCondition().getValue() != null
					&& fixedColumn.getCondition().getValue().toString().trim().length() > 0) {
				ReportCondition whereCondition = createWhereCondition(new ReportColumn(fixedColumn.columnName, table),
						fixedColumn.condition, fixedColumn.isDateTypedColumn);
				query.addWhereCondition(whereCondition);
			}
		}
	}

	private ReportCondition createWhereCondition(ReportColumn c, ConditionForField cond, boolean isDateTyped) {

		ColumnExpression columnExp = new ColumnExpression(c);
		Expression exp = null;

		if (cond.isDateRangeCondition()) {
			exp = new BetweenExpression(columnExp, new ValueExpression(cond.getStartDate()), new ValueExpression(
					cond.getEndDate()));
		} else if (isDateTyped) {
			// Process the datetime typed condition
			String dateValue = cond.getValue().toString();

			if (dateValue.equalsIgnoreCase("today")) {
				exp = new MySQLInTodayExpression(columnExp);
			} else if (dateValue.equalsIgnoreCase("this_month")) {
				exp = new MySQLInThisMonthExpression(columnExp);
			} else if (dateValue.equalsIgnoreCase("this_week")) {
				exp = new MySQLInThisWeekExpression(columnExp);
			} else if (dateValue.equalsIgnoreCase("this_year")) {
				exp = new MySQLInThisYearExpression(columnExp);
			}
		} else {
			Operator operator = null;
			Expression right = new ValueExpression(cond.value);
			if (cond.op.equals("=")) {
				operator = new Operator(OPERATION_ENUM.EQ);
			} else if (cond.op.equals(">")) {
				operator = new Operator(OPERATION_ENUM.GT);
			} else if (cond.op.equals("<")) {
				operator = new Operator(OPERATION_ENUM.LT);
			} else if (cond.op.equals("<>")) {
				operator = new Operator(OPERATION_ENUM.NE);
			}

			exp = new BinaryExpression(columnExp, right, operator);
		}

		return new ReportCondition(exp);
	}

	public Collection<String> getSelectedFieldLabelList() {
		return selectedColumnName2LabelMap.values();
	}

	public List<Object[]> getReportResult() {
		if (reportResult == null) {
			reportResult = reportService.runReport(reportDefinition);
		}

		return reportResult;
	}

	public void downloadReportResult() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

		response.reset(); // Some JSF component library or some Filter might
		// have set some headers in the buffer beforehand. We
		// want to get rid of them, else it may collide.
		response.setContentType("text/csv;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-disposition", "attachment; filename=\"report.csv\""); // The
																							// Save
																							// As
																							// popup
		// magic
		// is done here. You can
		// give it any filename
		// you want, this only
		// won't work in MSIE,
		// it
		// will use current
		// request URL as
		// filename
		// instead.

		CSVWriter writer = null;
		try {
			// Write utf-8 header in output
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });
			outputStream.flush();

			writer = new CSVWriter(new OutputStreamWriter(outputStream));
			// Write Header
			Collection<String> labels = getSelectedFieldLabelList();
			String[] csvHeader = labels.toArray(new String[labels.size()]);
			List<Object[]> result = getReportResult();
			List<String[]> csvRst = new ArrayList<String[]>(result.size() + 1);
			csvRst.add(csvHeader);
			for (Object[] rst : result) {
				String[] columns = new String[rst.length];
				for (int i = 0; i < rst.length; i++) {
					if (rst[i] != null) {
						columns[i] = rst[i].toString();
					}
				}
				csvRst.add(columns);
			}
			writer.writeAll(csvRst);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		facesContext.responseComplete(); // Important! Else JSF will attempt to
		// render the response which obviously
		// will fail since it's already written
		// with a file and closed.

	}

	public String getDebugSql() {
		return reportDefinition.getReportDefinitionObj().toQueryString();
	}

	public List<String> getSelectedFixedColumnNameList() {
		return selectedFixedColumnNameList;
	}

	public void setSelectedFixedColumnNameList(List<String> selectedFixedColumnNameList) {
		this.selectedFixedColumnNameList = selectedFixedColumnNameList;
	}

	public List<String> getFormFixedColumnNameList() {
		return formFixedColumnNameList;
	}

	public Map<String, FormFixedColumn> getFormFixedColumnMapByColumnName() {
		return formFixedColumnMapByColumnName;
	}

	public Collection<FormFixedColumn> getFormFixedColumnList() {
		return formFixedColumnMapByColumnName.values();
	}

	public Map<String, String> getSelectedColumnName2LabelMap() {
		return selectedColumnName2LabelMap;
	}

	public FormMetadata getFormMetadata() {
		return formMetadata;
	}
}
