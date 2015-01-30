package com.dynamo2.myerp.report.definition;

public class MySQLInThisMonthExpression implements Expression {

	/**
     * 
     */
	private static final long serialVersionUID = 6469199468730412181L;
	private ColumnExpression column;

	public MySQLInThisMonthExpression(ColumnExpression column) {
		super();
		this.column = column;
	}

	public ColumnExpression getColumn() {
		return column;
	}

	//@Override
	public String toQueryString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(MONTH(").append(column.toQueryString()).append(") = MONTH(CURDATE()) and YEAR(")
				.append(column.toQueryString()).append(") = YEAR(CURDATE()))");
		return sb.toString();
	}

}
