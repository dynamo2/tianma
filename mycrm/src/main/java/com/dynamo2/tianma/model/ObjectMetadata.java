package com.dynamo2.tianma.model;

import java.util.List;

public class ObjectMetadata extends MongodbModel {
	private String name;
	private String uniqueMark;
	private List<ObjectField> fields;
	
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
	public List<ObjectField> getFields() {
		return fields;
	}
	public void setFields(List<ObjectField> fields) {
		this.fields = fields;
	}
}
