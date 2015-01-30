package com.dynamo2.myerp.report.definition;

public class ReportCondition implements QueryString {

	/**
     * 
     */
	private static final long serialVersionUID = -5585780303682710046L;
	private Expression exp;

	public ReportCondition(Expression exp) {
		this.exp = exp;
	}

	//@Override
	public String toQueryString() {
		return exp.toQueryString();
	}

	public Expression getExp() {
		return exp;
	}

}
