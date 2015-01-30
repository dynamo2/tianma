package com.dynamo2.myerp.report.definition;

public class ReportColumn implements QueryString {

	/**
     * 
     */
	private static final long serialVersionUID = 3001620899841405593L;
	private String name;
	private ReportTable table;

	public ReportColumn(String name, ReportTable table) {
		this.name = name;
		this.table = table;
	}

	//@Override
	public String toQueryString() {
		return table.getAlias() + "." + name;
	}

	public String getName() {
		return name;
	}

	public ReportTable getTable() {
		return table;
	}

}
