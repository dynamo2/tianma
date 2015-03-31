package com.dynamo2.tianma.expression.element;

import com.dynamo2.tianma.expression.Operator;

public class ExistsExpression extends ElementExpression {
	public ExistsExpression(String field,boolean exits){
		super(field,exits,Operator.EXISTS);
	}
}
