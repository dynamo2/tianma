package com.dynamo2.tianma.expression;


public enum Operator {
	EQ(1,"$eq"),GT(2,"$gt"),GTE(3,"$gte"),LT(4,"$lt"),LTE(5,"$lte"),
	NE(6,"$ne"),IN(7,"$in"),NIN(8,"$nin"),OR(9,"$or"),AND(10,"$and"),
	NOT(11,"$not"),NOR(12,"$nor"),EXISTS(13,"$exists"),TYPE(14,"$type");
	
	private int code = -1;
	private String key;
	
	private Operator(int c,String k){
		this.code = c;
		this.key = k;
	}
	
	public String getKey(){
		return this.key;
	}
}
