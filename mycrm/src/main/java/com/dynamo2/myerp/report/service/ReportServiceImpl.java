package com.dynamo2.myerp.report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.AbstractBaseService;
import com.dynamo2.myerp.report.dao.ReportDefinitionDAO;
import com.dynamo2.myerp.report.dao.entities.ReportDefinition;

@Service("reportService")
public class ReportServiceImpl extends AbstractBaseService<ReportDefinition> implements ReportService {

	@Autowired
	private ReportDefinitionDAO reportDefinitionDAO;

	@Override
	protected AbstractBaseDAO<ReportDefinition> getDao() {
		return reportDefinitionDAO;
	}

	//@Override
	public List<Object[]> runReport(ReportDefinition reportDefinition) {
		return reportDefinitionDAO.runReport(reportDefinition);
	}

}
