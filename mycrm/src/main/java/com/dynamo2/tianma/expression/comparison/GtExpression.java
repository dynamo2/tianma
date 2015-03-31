package com.dynamo2.tianma.expression.comparison;

import java.util.Map;

import com.dynamo2.tianma.expression.Operator;

public class GtExpression extends ComparisonExpression {
	public GtExpression(String f,Object fv){
		super(f,fv,Operator.GT);
	}
	public GtExpression(String source,Map<String,Object> paramMap){
		super(source,paramMap,Operator.GT);
		
	}
}
