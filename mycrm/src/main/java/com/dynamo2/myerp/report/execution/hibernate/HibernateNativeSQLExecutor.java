package com.dynamo2.myerp.report.execution.hibernate;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.dynamo2.myerp.report.definition.ReportQueryDefinition;
import com.dynamo2.myerp.report.execution.ExecutionResult;
import com.dynamo2.myerp.report.execution.ReportDefinitionExecutor;

@Component
public class HibernateNativeSQLExecutor implements ReportDefinitionExecutor {

	@PersistenceContext
	protected EntityManager em;

	//@Override
	public ExecutionResult execute(ReportQueryDefinition report, Map<String, Object> executionContext) {

		Query q = em.createNativeQuery(report.toQueryString());
		List result = q.getResultList();

		return new HibernateExecutionResult(result);
	}

}
