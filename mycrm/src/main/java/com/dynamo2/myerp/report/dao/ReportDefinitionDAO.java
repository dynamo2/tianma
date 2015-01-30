package com.dynamo2.myerp.report.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.report.dao.entities.ReportDefinition;

@Repository
public class ReportDefinitionDAO extends AbstractBaseDAO<ReportDefinition> {

	public List<Object[]> runReport(ReportDefinition reportDefinition) {
		String sql = reportDefinition.getReportDefinitionObj().toQueryString();
		Query q = em.createNativeQuery(sql);
		return q.getResultList();
	}

}
