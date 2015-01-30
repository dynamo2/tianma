package com.dynamo2.myerp.dynamicform.report;

import com.dynamo2.myerp.report.definition.BinaryExpression;
import com.dynamo2.myerp.report.definition.ColumnExpression;
import com.dynamo2.myerp.report.definition.Operator;
import com.dynamo2.myerp.report.definition.Operator.OPERATION_ENUM;
import com.dynamo2.myerp.report.definition.ReportCondition;
import com.dynamo2.myerp.report.definition.ValueExpression;

public class FormMetadataIdCondition extends ReportCondition {

	/**
     * 
     */
	private static final long serialVersionUID = -5524154666670112488L;
	private Long formMetadataId;

	public FormMetadataIdCondition(Long formMetadataId) {
		super(new BinaryExpression(new ColumnExpression(DynamicFormReportConstants.DYNAMIC_FORM_METADATA_ID_COLUMN),
				new ValueExpression(formMetadataId), new Operator(OPERATION_ENUM.EQ)));

		this.formMetadataId = formMetadataId;
	}

	public Long getFormMetadataId() {
		return formMetadataId;
	}

}
