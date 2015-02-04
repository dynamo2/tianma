package com.dynamo2.tianma.model;


public class ObjectField extends MongodbModel {
	private String name;
	private String uniqueMark;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUniqueMark() {
		return uniqueMark;
	}
	public void setUniqueMark(String uniqueMark) {
		this.uniqueMark = uniqueMark;
	}
}
