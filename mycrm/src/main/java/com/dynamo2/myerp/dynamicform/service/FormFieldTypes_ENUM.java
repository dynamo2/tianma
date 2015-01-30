package com.dynamo2.myerp.dynamicform.service;

import java.util.Date;

public enum FormFieldTypes_ENUM {
	VARCHAR("varchar", "string", String.class), INT("int", "number", Integer.class), DOUBLE("double", "double",
			Double.class), DATETIME("datetime", "datetime", Date.class), TEXT("text", "text", String.class);

	private String sqlType;
	private String token;
	private Class javaTypeClass;

	private FormFieldTypes_ENUM(String sqlType, String token, Class javaTypeClass) {
		this.sqlType = sqlType;
		this.token = token;
		this.javaTypeClass = javaTypeClass;
	}

	public String getSqlType() {
		return sqlType;
	}

	public String getToken() {
		return token;
	}

	public Class getJavaTypeClass() {
		return javaTypeClass;
	}

}
