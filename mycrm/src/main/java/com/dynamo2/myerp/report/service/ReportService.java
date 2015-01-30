package com.dynamo2.myerp.report.service;

import java.util.List;

import com.dynamo2.myerp.AbstractBaseServiceInterface;
import com.dynamo2.myerp.report.dao.entities.ReportDefinition;

public interface ReportService extends AbstractBaseServiceInterface<ReportDefinition> {

	List<Object[]> runReport(ReportDefinition reportDefinition);

}
