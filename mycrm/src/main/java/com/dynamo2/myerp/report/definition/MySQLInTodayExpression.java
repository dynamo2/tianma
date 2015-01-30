package com.dynamo2.myerp.report.definition;

public class MySQLInTodayExpression implements Expression {

	/**
     * 
     */
	private static final long serialVersionUID = 4374265013899327840L;
	private ColumnExpression column;

	public MySQLInTodayExpression(ColumnExpression column) {
		super();
		this.column = column;
	}

	public ColumnExpression getColumn() {
		return column;
	}

	//@Override
	public String toQueryString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(DATE(").append(column.toQueryString()).append(") = CURDATE())");
		return sb.toString();
	}

}
