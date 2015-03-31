package com.dynamo2.tianma.expression.comparison;

import java.util.Map;

import com.dynamo2.tianma.expression.Operator;



public class GteExpression extends ComparisonExpression {
	public GteExpression(String f,Object fv){
		super(f,fv,Operator.GTE);
	}
	public GteExpression(String source,Map<String,Object> paramMap){
		super(source,paramMap,Operator.GTE);
		
	}
}
