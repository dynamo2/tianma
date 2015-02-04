package com.dynamo2.tianma.dao;

public enum FieldType {
	INT(1,"int"),DOUBLE(2,"double"),STRING(3,"string"),DATE(4,"date"),FILE(5,"file");
	
	
	private int code;
	private String key;
	private FieldType(int c,String k){
		this.code = code;
		this.key = key;
	}
}
