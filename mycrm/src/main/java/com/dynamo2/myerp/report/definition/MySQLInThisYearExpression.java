package com.dynamo2.myerp.report.definition;

public class MySQLInThisYearExpression implements Expression {

	/**
     * 
     */
	private static final long serialVersionUID = 8693682949488568036L;
	private ColumnExpression column;

	public MySQLInThisYearExpression(ColumnExpression column) {
		super();
		this.column = column;
	}

	public ColumnExpression getColumn() {
		return column;
	}

	//@Override
	public String toQueryString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(YEAR(").append(column.toQueryString()).append(") = YEAR(CURDATE()))");
		return sb.toString();
	}

}
