package com.dynamo2.myerp.report.definition;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ValueExpression implements Expression {

	private static final long serialVersionUID = 7832933294433416677L;
	private Object value;
	private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public ValueExpression(Object value) {
		this.value = value;
	}

	//@Override
	public String toQueryString() {
		StringBuilder sb = new StringBuilder();
		if (value instanceof List) {
			sb.append("(");
			for (Object v : (List) value) {
				sb.append(handleValue(v)).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append(") ");
		} else {
			sb.append(handleValue(value));
		}

		return sb.toString();
	}

	private String handleValue(Object v) {
		if (v instanceof String)
			return "'" + v.toString() + "'";
		if (v instanceof Date) {
			String dateString;
			synchronized (df) {
				dateString = df.format(v);
			}
			return "'" + dateString + "'";
		} else
			return v.toString();
	}

	public Object getValue() {
		return value;
	}
}
