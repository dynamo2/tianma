package com.dynamo2.tianma.expression.comparison;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dynamo2.tianma.expression.Operator;

public class NinExpression extends ComparisonExpression {
	public NinExpression(String f,List<Object> fv){
		super(f,fv,Operator.NIN);
		
		if(fv != null){
			this.fieldValue = fv;
		}else this.fieldValue = new ArrayList<Object>();
	}
	public NinExpression(String source,Map<String,Object> paramMap){
		super(source,paramMap,Operator.NIN);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addValue(Object v){
		if(this.fieldValue instanceof List){
			if(this.fieldValue == null){
				this.fieldValue = new ArrayList<Object>();
			}
			
			((List)this.fieldValue).add(v);
		}
	}
	
//	public BasicDBObject generateQuery() {
//		return new BasicDBObject(field,new BasicDBObject(this.OPERATOR_KEY,this.values));
//	}
}
