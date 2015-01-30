package com.dynamo2.myerp.dynamicform.dao.entities;

// Generated May 20, 2012 10:01:52 AM by Hibernate Tools 3.4.0.CR1

import org.apache.commons.lang.StringUtils;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 * FormFieldMetadata generated by hbm2java
 */
@Entity
@Table(name = "form_field_metadata", uniqueConstraints = @UniqueConstraint(columnNames = { "field_name",
		"form_metadata_id" }))
@NamedQueries(value = {
		@NamedQuery(name = "listFieldsOfForm", query = "from FormFieldMetadata where formMetadataId=? order by displayPositionGroup, displayPositionRow, displayPositionColumn"),
		@NamedQuery(name = "findFieldByFormMetadataIdAndFieldName", query = "from FormFieldMetadata where formMetadataId=? and fieldName=?") })
public class FormFieldMetadata implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long formMetadataId;
	private String fieldName;
	private String fieldType;
	private String fieldLabel;
	private String columnName;
	private Integer displayPositionRow;
	private Integer displayPositionColumn;
	private Integer displayPositionGroup;
	private boolean isRequired;
	private String optionValues;
	private Integer inputColumns = 20;
	private Integer inputRows = 1;
	private String expression;
	private String expressionLabel;
	private Long referToFormMetadataId;
	private boolean isPrintable;
	private String defaultValue;
    private boolean isLabel;

	public FormFieldMetadata() {
	}

	public FormFieldMetadata(Long id, Long formMetadataId, String fieldName, String fieldType, String fieldLabel,
			String columnName, Integer displayPositionRow, Integer displayPositionColumn, Integer displayPositionGroup,
			boolean isRequired) {
		this.id = id;
		this.formMetadataId = formMetadataId;
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.fieldLabel = fieldLabel;
		this.columnName = columnName;
		this.displayPositionRow = displayPositionRow;
		this.displayPositionColumn = displayPositionColumn;
		this.displayPositionGroup = displayPositionGroup;
		this.isRequired = isRequired;
	}

	public FormFieldMetadata(Long id, Long formMetadataId, String fieldName, String fieldType, String fieldLabel,
			String columnName, Integer displayPositionRow, Integer displayPositionColumn, Integer displayPositionGroup,
			boolean isRequired, String optionValues) {
		this.id = id;
		this.formMetadataId = formMetadataId;
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.fieldLabel = fieldLabel;
		this.columnName = columnName;
		this.displayPositionRow = displayPositionRow;
		this.displayPositionColumn = displayPositionColumn;
		this.displayPositionGroup = displayPositionGroup;
		this.isRequired = isRequired;
		this.optionValues = optionValues;
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

	@Column(name = "form_metadata_id", nullable = false)
	public Long getFormMetadataId() {
		return this.formMetadataId;
	}

	public void setFormMetadataId(Long formMetadataId) {
		this.formMetadataId = formMetadataId;
	}

	@Column(name = "field_name", nullable = false)
	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Column(name = "field_type", nullable = false)
	public String getFieldType() {
		return this.fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	@Column(name = "field_label", nullable = false)
	public String getFieldLabel() {
		return this.fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	@Column(name = "column_name", nullable = false, length = 45)
	public String getColumnName() {

		return this.columnName == null ? null : this.columnName.toLowerCase();
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	@Column(name = "display_position_row", nullable = false)
	public Integer getDisplayPositionRow() {
		return this.displayPositionRow;
	}

	public void setDisplayPositionRow(Integer displayPositionRow) {
		this.displayPositionRow = displayPositionRow;
	}

	@Column(name = "display_position_column", nullable = false)
	public Integer getDisplayPositionColumn() {
		return this.displayPositionColumn;
	}

	public void setDisplayPositionColumn(Integer displayPositionColumn) {
		this.displayPositionColumn = displayPositionColumn;
	}

	@Column(name = "display_position_group", nullable = false)
	public Integer getDisplayPositionGroup() {
		return this.displayPositionGroup;
	}

	public void setDisplayPositionGroup(Integer displayPositionGroup) {
		this.displayPositionGroup = displayPositionGroup;
	}

	@Column(name = "is_required", nullable = false)
	public boolean getIsRequired() {
		return this.isRequired;
	}

	public void setIsRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	@Column(name = "option_values", length = 65535)
	public String getOptionValues() {
		return this.optionValues;
	}

	public void setOptionValues(String optionValues) {
		this.optionValues = optionValues;
	}

	@Column(name = "input_columns")
	public Integer getInputColumns() {
		return inputColumns;
	}

	public void setInputColumns(Integer inputColumns) {
		this.inputColumns = inputColumns;
	}

	@Column(name = "input_rows")
	public Integer getInputRows() {
		return inputRows;
	}

	public void setInputRows(Integer inputRows) {
		this.inputRows = inputRows;
	}

	@Column(name = "expression")
	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	@Column(name = "expression_label")
	public String getExpressionLabel() {
		return expressionLabel;
	}

	public void setExpressionLabel(String expressionLabel) {
		this.expressionLabel = expressionLabel;
	}

	@Column(name = "refer_to_form_metadata_id")
	public Long getReferToFormMetadataId() {
		return referToFormMetadataId;
	}

	public void setReferToFormMetadataId(Long referToFormMetadataId) {
		this.referToFormMetadataId = referToFormMetadataId;
	}

	@Column(name = "is_printable")
	public boolean getIsPrintable() {
		return isPrintable;
	}

	public void setIsPrintable(boolean isPrintable) {
		this.isPrintable = isPrintable;
	}

	@Column(name = "default_value")
	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

    @Column(name = "is_label")
    public boolean getIsLabel() {
        return this.isLabel;
    }

    public void setIsLabel(boolean isLabel) {
        this.isLabel = isLabel;
    }

    public void setLabel(boolean isLabel) {
        this.isLabel = isLabel;
    }

	@Transient
	public List<String[]> getOptionValuesList() {
		// The format of Option Item is "label1=value1,label2=value2"
		// Should no space between "=" and ","
		if (!StringUtils.isEmpty(optionValues)) {
			String[] values = optionValues.split(",");
			List<String[]> optionItems = new ArrayList<String[]>(values.length);
			for (String v : values) {
				String[] labelValuePair = v.trim().split("=");
				if (labelValuePair.length == 2) {
					optionItems.add(labelValuePair);
				} else {
					optionItems.add(new String[] { v, v });
				}
			}

			return optionItems;
		}

		return Collections.emptyList();
	}
}
