package com.dynamo2.myerp.report.definition;

public class OrderByColumn implements QueryString {
	/**
     * 
     */
	private static final long serialVersionUID = 7042017724558306450L;
	private ReportColumn column;
	private boolean isASC;

	public OrderByColumn(ReportColumn column, boolean isASC) {
		super();
		this.column = column;
		this.isASC = isASC;
	}

	//@Override
	public String toQueryString() {
		StringBuilder sb = new StringBuilder();
		sb.append(column.toQueryString());
		if (isASC) {
			sb.append(" ASC");
		} else {
			sb.append(" DESC");
		}

		return sb.toString();
	}

}
