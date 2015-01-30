package com.dynamo2.myerp.report.definition;

public class Operator implements QueryString {

	/**
     * 
     */
	private static final long serialVersionUID = 7747431534931065664L;

	public enum OPERATION_ENUM {
		IN("in"), LIKE("like"), EQ("="), NE("<>"), IS_NULL("is null"), NOT("not"), GT(">"), LT("<"), AND("and"), OR(
				"or");

		private String queryString;

		private OPERATION_ENUM(String queryString) {
			this.queryString = queryString;
		}

		public String toQueryString() {
			return queryString;
		}
	}

	private OPERATION_ENUM op;

	public Operator(OPERATION_ENUM op) {
		super();
		this.op = op;
	}

	//@Override
	public String toQueryString() {
		return op.toQueryString();
	}

}
