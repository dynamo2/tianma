package com.dynamo2.myerp.report.definition;

import java.util.LinkedList;
import java.util.List;

public class ReportQueryDefinition implements QueryString {

	/**
     * 
     */
	private static final long serialVersionUID = -2744667629996557510L;

	private List<ReportColumn> selectColumns = new LinkedList<ReportColumn>();

	private List<ReportCondition> whereConditions = new LinkedList<ReportCondition>();

	private List<OrderByColumn> orderByColumns = new LinkedList<OrderByColumn>();

	private List<JoinCondition> joinConditions = new LinkedList<JoinCondition>();

	private ReportTable fromTable;

	public List<ReportColumn> getSelectColumns() {
		return selectColumns;
	}

	public List<ReportCondition> getWhereConditions() {
		return whereConditions;
	}

	public List<OrderByColumn> getOrderByColumns() {
		return orderByColumns;
	}

	public List<JoinCondition> getJoinConditions() {
		return joinConditions;
	}

	public ReportTable getFromTable() {
		return fromTable;
	}

	public void addSelectColumn(ReportColumn c) {
		selectColumns.add(c);
	}

	public void addOrderByColumn(OrderByColumn c) {
		orderByColumns.add(c);
	}

	public void addJoinCondition(JoinCondition c) {
		joinConditions.add(c);
	}

	public void addWhereCondition(ReportCondition c) {
		whereConditions.add(c);
	}

	public void setFromTable(ReportTable fromTable) {
		this.fromTable = fromTable;
	}

	//@Override
	public String toQueryString() {
		StringBuilder sb = new StringBuilder();

		sb.append("select ");
		for (ReportColumn column : selectColumns) {
			sb.append(column.toQueryString()).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);

		sb.append(" from ").append(fromTable.toQueryString()).append(" ");
		if (joinConditions != null) {
			for (JoinCondition con : joinConditions) {
				sb.append(con.toQueryString());
			}
		}

		if (!whereConditions.isEmpty()) {
			sb.append(" where 1=1 ");
			for (ReportCondition c : whereConditions) {
				sb.append(" and (").append(c.toQueryString()).append(") ");
			}
		}

		if (!orderByColumns.isEmpty()) {
			sb.append(" order by ");
			for (OrderByColumn column : orderByColumns) {
				sb.append(column.toQueryString()).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}

		return sb.toString();
	}

}
