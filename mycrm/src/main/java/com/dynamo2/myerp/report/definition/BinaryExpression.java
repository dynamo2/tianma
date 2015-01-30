package com.dynamo2.myerp.report.definition;

public class BinaryExpression implements Expression {

	/**
     * 
     */
	private static final long serialVersionUID = -6388863271131012817L;

	private Expression left;

	private Expression right;

	private Operator operator;

	public BinaryExpression(Expression left, Expression right, Operator operator) {
		this.left = left;
		this.right = right;
		this.operator = operator;
	}

	//@Override
	public String toQueryString() {
		StringBuffer sb = new StringBuffer();
		if (left instanceof ColumnExpression || left instanceof ValueExpression) {
			sb.append(left.toQueryString());
		} else {
			sb.append('(').append(left.toQueryString()).append(')');
		}

		sb.append(operator.toQueryString());

		if (right instanceof ColumnExpression || right instanceof ValueExpression) {
			sb.append(right.toQueryString());
		} else {
			sb.append('(').append(right.toQueryString()).append(')');
		}

		return sb.toString();
	}

	public Expression getLeft() {
		return left;
	}

	public Expression getRight() {
		return right;
	}

	public Operator getOperator() {
		return operator;
	}

}
