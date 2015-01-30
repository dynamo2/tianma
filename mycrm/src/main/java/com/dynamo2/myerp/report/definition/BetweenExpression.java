package com.dynamo2.myerp.report.definition;

public class BetweenExpression implements Expression {

	private static final long serialVersionUID = 1889791427050858340L;

	private ColumnExpression column;

	private ValueExpression start;

	private ValueExpression end;

	public BetweenExpression(ColumnExpression column, ValueExpression start, ValueExpression end) {
		super();
		this.column = column;
		this.start = start;
		this.end = end;
	}

	public ColumnExpression getColumn() {
		return column;
	}

	public ValueExpression getStart() {
		return start;
	}

	public ValueExpression getEnd() {
		return end;
	}

	//@Override
	public String toQueryString() {
		StringBuilder sb = new StringBuilder();
		sb.append(column.toQueryString()).append(" between ").append(start.toQueryString()).append(" and ")
				.append(end.toQueryString());
		return sb.toString();
	}

}
