package com.dynamo2.tianma.expression.comparison;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dynamo2.tianma.expression.BasicExpression;
import com.dynamo2.tianma.expression.Operator;
import com.mongodb.BasicDBObject;

public abstract class ComparisonExpression extends BasicExpression {
	protected String field;
	protected Object fieldValue;
	
	public ComparisonExpression(String f,Object fv,Operator key){
		super(key);
		
		this.field = f;
		this.fieldValue = fv;
	}
	
	public ComparisonExpression(String source,Map<String,Object> paramMap,Operator key){
		super(source,paramMap,key);
		
		readSource();
	}
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public Object getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}

	public BasicDBObject generateQuery() {
		return new BasicDBObject(field,new BasicDBObject(OPERATOR.getKey(),fieldValue));
	}
	
	protected void readSource(){
		String keyRegx = "\\"+this.OPERATOR.getKey();
		String str = source.substring(1,source.length()-1);
		String[] sa = str.split(keyRegx);
		this.setField(sa[0].trim());

		String vStr = sa[1].trim();
		Object value = null;
		if(isArray(vStr)){
			value = this.readArray(vStr);
		}else value = this.readSingleValue(vStr);

		this.setFieldValue(value);
	}
	
	public static boolean isString(String v){
		if(v == null || v.isEmpty() || v.length() < 2)
			return false;
		
		return v.startsWith("'") && v.endsWith("'");
	}
	
	public static boolean isArray(String v){
		if(v == null || v.isEmpty() || v.length() < 2)
			return false;
		
		return v.startsWith("[") && v.endsWith("]");
	}
	
	private Object readSingleValue(String vStr){
		if(isString(vStr)){
			return vStr.substring(1,vStr.length()-1);
		}else {
			return new Double(vStr);
		}
	}
	
	private List<Object> readArray(String sv){
		String vstr = sv.substring(1,sv.length()-1);
		String[] va = vstr.split(",");
		
		List<Object> vlist = new ArrayList<Object>();
		for(String v:va){
			v = v.trim();
			if(v.isEmpty())continue;
			
			vlist.add(this.readSingleValue(v));
		}
		
		return vlist;
	}
}