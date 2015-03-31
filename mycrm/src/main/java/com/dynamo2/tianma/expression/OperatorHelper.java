package com.dynamo2.tianma.expression;

import java.util.HashMap;
import java.util.Map;

public class OperatorHelper {
	public static final String EQ = "$eq";
	public static final String GT = "$gt";
	public static final String GTE = "$gte";
	public static final String LT = "$lt";
	public static final String LTE = "$lte";
	public static final String NE = "$ne";
	public static final String IN = "$in";
	public static final String NIN = "$nin";
	public static final String OR = "$or";
	public static final String AND = "$and";
	public static final String NOT = "$not";
	public static final String NOR = "$nor";
	public static final String EXISTS = "$exists";
	public static final String TYPE = "$type";
	
	private static Map<String,Operator> MAP_OPERATOR_BY_KEY = new HashMap<String,Operator>();
	static {
		MAP_OPERATOR_BY_KEY.put(EQ,Operator.EQ);
		MAP_OPERATOR_BY_KEY.put(GT,Operator.GT);
		MAP_OPERATOR_BY_KEY.put(GTE,Operator.GTE);
		MAP_OPERATOR_BY_KEY.put(LT,Operator.LT);
		MAP_OPERATOR_BY_KEY.put(LTE,Operator.LTE);
		MAP_OPERATOR_BY_KEY.put(NE,Operator.NE);
		MAP_OPERATOR_BY_KEY.put(IN,Operator.IN);
		MAP_OPERATOR_BY_KEY.put(NIN,Operator.NIN);
		MAP_OPERATOR_BY_KEY.put(OR,Operator.OR);
		MAP_OPERATOR_BY_KEY.put(AND,Operator.AND);
		MAP_OPERATOR_BY_KEY.put(NOT,Operator.NOT);
		MAP_OPERATOR_BY_KEY.put(NOR,Operator.NOR);
		MAP_OPERATOR_BY_KEY.put(EXISTS,Operator.EXISTS);
		MAP_OPERATOR_BY_KEY.put(TYPE,Operator.TYPE);
	}
	
	public static Operator get(String key){
		return MAP_OPERATOR_BY_KEY.get(key);
	}
}
