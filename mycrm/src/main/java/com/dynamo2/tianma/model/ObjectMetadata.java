package com.dynamo2.tianma.model;

import java.util.List;

public class ObjectMetadata extends MongodbModel {
	private String id;
	private String name;
	private String uniqueMark;
	
	@Deprecated
	private List<ObjectField> fields;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
