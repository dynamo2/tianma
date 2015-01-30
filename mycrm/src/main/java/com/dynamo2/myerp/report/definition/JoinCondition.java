package com.dynamo2.myerp.report.definition;

public class JoinCondition implements QueryString {

	/**
     * 
     */
	private static final long serialVersionUID = 7692070057179021937L;

	private ReportTable joinTable;

	private ReportCondition condition;

	private boolean isLeftJoin;

	private JoinCondition(ReportTable joinTable, ReportCondition condition, boolean isLeftJoin) {
		this.joinTable = joinTable;
		this.condition = condition;
		this.isLeftJoin = isLeftJoin;
	}

	//@Override
	public String toQueryString() {
		StringBuilder sb = new StringBuilder();
		if (isLeftJoin) {
			sb.append(" left join ");
		} else {
			sb.append(" join ");
		}

		sb.append(joinTable.toQueryString()).append(" on (").append(condition.toQueryString()).append(")");

		return sb.toString();
	}

}
