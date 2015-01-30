package com.dynamo2.myerp.report.definition;

public class UnaryExpression implements Expression {

	/**
     * 
     */
	private static final long serialVersionUID = -3473107911945364757L;

	private Expression expression;

	private Operator operator;

	private UnaryExpression(Expression expression, Operator operator) {
		super();
		this.expression = expression;
		this.operator = operator;
	}

	//@Override
	public String toQueryString() {
		StringBuilder sb = new StringBuilder();
		sb.append(operator.toQueryString()).append(" (").append(expression.toQueryString()).append(") ");
		return sb.toString();
	}

}
