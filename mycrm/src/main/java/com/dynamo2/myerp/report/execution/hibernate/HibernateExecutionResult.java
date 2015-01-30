package com.dynamo2.myerp.report.execution.hibernate;

import java.util.List;

import com.dynamo2.myerp.report.execution.ExecutionResult;

public class HibernateExecutionResult extends ExecutionResult {

	public HibernateExecutionResult(List<Object[]> result) {
		this.resultset = result;
	}

}
