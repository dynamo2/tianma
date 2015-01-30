package com.dynamo2.myerp.report.definition;

public class ColumnExpression implements Expression {

	/**
     * 
     */
	private static final long serialVersionUID = 6918542905016344530L;
	private ReportColumn column;

	public ColumnExpression(ReportColumn column) {
		super();
		this.column = column;
	}

	//@Override
	public String toQueryString() {
		return column.toQueryString();
	}

	public ReportColumn getColumn() {
		return column;
	}

}
