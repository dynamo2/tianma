package com.dynamo2.tianma.dao;

/**
 * 数据库字段类型
 * **/
public enum FieldType {
	INT(1,"int"),DOUBLE(2,"double"),STRING(3,"string"),DATE(4,"date"),
	FILE(5,"file"),//文件
	DICT(6,"dict"), //数据字典
	INNER_COLLECTION(7,"innerCollection"),//内嵌集合
	LINK_COLLECTION(8,"linkCollection");//关联集合
	
	
	private int code;
	private String key;
	private FieldType(int c,String k){
		this.code = code;
		this.key = key;
	}
}
