package com.dynamo2.tianma.expression.comparison;

import java.util.Map;

import com.dynamo2.tianma.expression.Operator;



public class LtExpression extends ComparisonExpression {
	public LtExpression(String f,Object fv){
		super(f,fv,Operator.LT);
	}
	public LtExpression(String source,Map<String,Object> paramMap){
		super(source,paramMap,Operator.LT);
		
	}
}
