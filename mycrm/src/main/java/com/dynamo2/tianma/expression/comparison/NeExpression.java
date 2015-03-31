package com.dynamo2.tianma.expression.comparison;

import java.util.Map;

import com.dynamo2.tianma.expression.Operator;



public class NeExpression extends ComparisonExpression {
	public NeExpression(String f,Object fv){
		super(f,fv,Operator.NE);
	}
	public NeExpression(String source,Map<String,Object> paramMap){
		super(source,paramMap,Operator.NE);
	}
}
