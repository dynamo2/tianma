package com.dynamo2.tianma.model;


public class ObjectField extends MongodbModel {
	private String name;
	private String uniqueMark;
	private int type;
	private String linkedObject;
	
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getLinkedObject() {
		return linkedObject;
	}
	public void setLinkedObject(String linkedObject) {
		this.linkedObject = linkedObject;
	}
}
