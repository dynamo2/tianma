package com.dynamo2.tianma.model;

import org.bson.types.ObjectId;

public class MongodbModel {
	protected ObjectId id;
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
}
