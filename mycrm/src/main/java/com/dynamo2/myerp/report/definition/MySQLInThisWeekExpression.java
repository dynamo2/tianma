package com.dynamo2.myerp.report.definition;

public class MySQLInThisWeekExpression implements Expression {

	/**
     * 
     */
	private static final long serialVersionUID = -361313574333326278L;
	private ColumnExpression column;

	public MySQLInThisWeekExpression(ColumnExpression column) {
		super();
		this.column = column;
	}

	public ColumnExpression getColumn() {
		return column;
	}

	//@Override
	public String toQueryString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(WEEKOFYEAR(").append(column.toQueryString()).append(") = WEEKOFYEAR(CURDATE()) and YEAR(")
				.append(column.toQueryString()).append(") = YEAR(CURDATE()))");
		return sb.toString();
	}

}
