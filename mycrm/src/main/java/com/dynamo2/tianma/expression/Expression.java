package com.dynamo2.tianma.expression;

import com.mongodb.BasicDBObject;

public interface Expression {
	public BasicDBObject generateQuery();
}
