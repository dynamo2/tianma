package com.dynamo2.myerp.dynamicform.report;

import com.dynamo2.myerp.report.definition.ReportColumn;
import com.dynamo2.myerp.report.definition.ReportTable;

public class DynamicFormReportConstants {
	public static final ReportTable DYNAMIC_FORM_DEFAULT_TABLE = new ReportTable("dynamic_form_default",
			"dynamic_form_default");

	public static final ReportColumn DYNAMIC_FORM_METADATA_ID_COLUMN = new ReportColumn("form_metadata_id",
			DYNAMIC_FORM_DEFAULT_TABLE);
}
