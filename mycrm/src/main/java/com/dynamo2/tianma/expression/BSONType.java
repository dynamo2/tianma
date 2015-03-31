package com.dynamo2.tianma.expression;

public enum BSONType {
	DOUBLE(1,"double"),STRING(2,"string"),OBJECT(3,"object"),ARRAY(4,"array"),
	BINARY_DATA(5,"binaryData"),UNDEFINED(6,"undefined"),OBJECT_ID(7,"objectId"),BOOLEAN(8,"boolean"),
	DATE(9,"date"),NULL(10,"null"),REGULAR(11,"regular"),JAVA_SCRIPT(13,"javaScript"),
	SYMBOL(14,"symbol"),JAVA_SCRIPT_WITH_SCOPE(15,"javaScript with scope"),INTEGER_32(16,"32-bit integer"),
	TIMESTAMP(17,"timestamp"),INTEGER_64(18,"64-bit integer"),MIN(255,"string"),MAX(127,"object");
	
	private int code;
	private String label;
	private BSONType(int c,String l){
		this.code = c;
		this.label = l;
	}
	
	public int getCode(){
		return this.code;
	}
}