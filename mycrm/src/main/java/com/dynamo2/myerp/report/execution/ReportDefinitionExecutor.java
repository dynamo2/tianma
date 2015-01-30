package com.dynamo2.myerp.report.execution;

import java.util.Map;

import com.dynamo2.myerp.report.definition.ReportQueryDefinition;

public interface ReportDefinitionExecutor {
	ExecutionResult execute(ReportQueryDefinition report, Map<String, Object> executionContext);
}
