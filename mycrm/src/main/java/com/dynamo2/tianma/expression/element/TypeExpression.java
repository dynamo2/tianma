package com.dynamo2.tianma.expression.element;

import com.dynamo2.tianma.expression.Operator;

public class TypeExpression extends ElementExpression {
	public TypeExpression(String field,Integer type){
		super(field,type,Operator.TYPE);
	}
}
