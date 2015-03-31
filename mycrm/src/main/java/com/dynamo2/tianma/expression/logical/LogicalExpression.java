package com.dynamo2.tianma.expression.logical;

import java.util.ArrayList;
import java.util.List;

import com.dynamo2.tianma.expression.Expression;
import com.dynamo2.tianma.expression.Operator;
import com.mongodb.BasicDBObject;

public abstract class LogicalExpression implements Expression {
	protected String KEY;
	
	private List<Expression> expressions = new ArrayList<Expression>();
	
	public void add(Expression expres){
		this.expressions.add(expres);
	}
	
	public static LogicalExpression newLogicalExpression(Operator op){
		if(op == Operator.OR){
			return new OrExpression();
		}
		
		return new AndExpression();
	}
	
	public BasicDBObject generateQuery(){
		List<BasicDBObject> queryList = new ArrayList<BasicDBObject>();
		
		for(Expression expre:expressions){
			queryList.add(expre.generateQuery());
		}
		
		return new BasicDBObject(KEY,queryList);
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer(KEY).append("\n");
		for(Expression e : expressions){
			sb.append("  ").append(e.toString());
		}
		
		return sb.toString();
	}
}
