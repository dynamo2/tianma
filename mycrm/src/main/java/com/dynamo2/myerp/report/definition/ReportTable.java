package com.dynamo2.myerp.report.definition;

public class ReportTable implements QueryString {

	/**
     * 
     */
	private static final long serialVersionUID = 8094778306372153573L;
	private String name;
	private String alias;

	public ReportTable(String name, String alias) {
		this.name = name;
		this.alias = alias;
	}

	public String getName() {
		return name;
	}

	public String getAlias() {
		return alias;
	}

	//@Override
	public String toQueryString() {
		return " " + name + " " + alias + " ";
	}

}
