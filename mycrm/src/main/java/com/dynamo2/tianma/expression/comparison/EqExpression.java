package com.dynamo2.tianma.expression.comparison;

import java.util.Map;

import com.dynamo2.tianma.expression.Operator;



public class EqExpression extends ComparisonExpression {
	public EqExpression(String f,Object fv){
		super(f,fv,Operator.EQ);
	}
	public EqExpression(String source,Map<String,Object> paramMap){
		super(source,paramMap,Operator.EQ);
		
	}
}
