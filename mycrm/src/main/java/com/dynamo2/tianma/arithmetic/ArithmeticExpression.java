package com.dynamo2.tianma.arithmetic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArithmeticExpression {
	public static void main(String[] args) {
		String source = "D20031130 13:34:23-3d";
		Parentheses pt = Parentheses.compile(source,0);
		
		DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		System.out.println(df.format(pt.getValue(null)));
		
		pt = Parentheses.compile("'dafdsaf'+'fdasfad'", 0);
		System.out.println(pt.getValue(null));
		
		pt = Parentheses.compile("2+3*(3+2*(8-6))*(8-4)+8-98+72/(3*3)", 0);
		System.out.println(pt.getValue(null));
		
		Map<String,Object> params = new HashMap<String,Object>();
		Map<String,Object> goods = new HashMap<String,Object>();
		Map<String,Object> user = new HashMap<String,Object>();
		Map<String,Object> address = new HashMap<String,Object>();
		goods.put("num",2);
		goods.put("price", 3);
		
		user.put("age",8);
		user.put("address", address);
		
		address.put("no",6);
		
		params.put("goods",goods);
		params.put("user", user);
		
		pt = Parentheses.compile("goods.num+goods.price*(3+2*(user.age-user.address.no))*(8-4)+8-98+72/(3*3)", 0);
		System.out.println("params:"+pt.getValue(params));
		
		params = new HashMap<String,Object>();
		goods = new HashMap<String,Object>();
		try {
			goods.put("create_date", df.parseObject("2015年03月31日 14时04分32秒"));
			params.put("goods",goods);
			pt = Parentheses.compile("goods.create_date+1m", 0);
			System.out.println("goods.create_date+1y:"+df.format(pt.getValue(params)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	static class Node {
		public final static DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		
		public Object getValue(Map<String,Object> paramsMap){
			return null;
		}
		
		public static Date readDate(String source){
			if(source == null || source.isEmpty()){
				return null;
			}
			
			source = source.trim();
			if(source.startsWith("D")){
				try {
					String ds = source.substring(1,source.length());
					
					return dateFormat.parse(ds);
				} catch (ParseException e) {}
			}

			return null;
		}
		
		public static boolean isDateIncrement(String source){
			if(source == null || source.isEmpty()){
				return false;
			}
			
			source = source.trim();
			if(source.length() < 2){
				return false;
			}
			
			char lastChar = source.charAt(source.length()-1);
			if(lastChar == 'd' 
				|| lastChar == 'y'
				|| lastChar == 'm'
				|| lastChar == 'h'
				|| lastChar == 'i'
				|| lastChar == 's'){
				
				try {
					String str = source.substring(0, source.length()-1);
					new Integer(str);
					return true;
				}catch(Exception e){}
			}
			return false;
		}
	}
	
	static class Parentheses extends Node{
		private final String source;
		private final int startIndex;
		private int endIndex;
		private Node expression;
		
		public Parentheses(String source,int index){
			this.source = source;
			this.startIndex = index;
		}
		
		public Object getValue(Map<String,Object> paramsMap){
			return expression.getValue(paramsMap);
		}
		
		public String expressionStr(){
			return source.substring(this.startIndex,this.endIndex);
		}
		
		public static Parentheses compile(String source,int index) {
			Parentheses parentheses = new Parentheses(source,index);
			if(source.charAt(index) == '('){
				index++;
			}
			
			String[] sary = new String[source.length()-index];
			StringBuffer sb = null;
			int arrayIndex = -1;
			Map<Node,Set<Integer>> expreMap = new HashMap<Node,Set<Integer>>();
			Set<Integer> indexSet = null;
			List<Operator> priorityOpts = new ArrayList<Operator>();
			for(int i = index; i < source.length(); i++){
				char ch = source.charAt(i);
				
				if(Operator.isOperator(ch)){
					if(sb != null){
						sary[++arrayIndex] = sb.toString();
						sb = null;
					}
					
					sary[++arrayIndex] = ch+"";
					priorityOpts.add(new Operator(ch,arrayIndex));
					
					continue;
				}
				
				if(ch == '('){
					Parentheses pt = Parentheses.compile(source,i);
					i = pt.endIndex;
					sary[++arrayIndex] = pt.expressionStr();
					indexSet = new HashSet<Integer>();
					indexSet.add(arrayIndex);
					
					expreMap.put(pt.expression, indexSet);
					continue;
				}
				
				if(ch == ')'){
					parentheses.endIndex = i;
					break;
				}
				
				if(sb == null){
					sb = new StringBuffer(ch);
				}
				sb.append(ch);
			}
			
			if(sb != null){
				sary[++arrayIndex] = sb.toString();
			}
			
			Collections.sort(priorityOpts);
			
			Expression root = null;
			for(Operator op:priorityOpts){
				int opIndex = op.getIndex();
				
				int preIndex = opIndex-1;
				int nextIndex = opIndex+1;
				
				Node preNode = getExpression(expreMap,preIndex);
				Node nextNode = getExpression(expreMap,nextIndex);
				
				if(preNode == null){
					preNode = new Operand(sary[preIndex]);
				}
				
				if(nextNode == null){
					nextNode = new Operand(sary[nextIndex]);
				}
				
				root = new Expression();
				root.setLeft(preNode);
				root.setRight(nextNode);
				root.setOperator(op);
				
				indexSet = new HashSet<Integer>();
				if(expreMap.containsKey(preNode)){
					indexSet.addAll(expreMap.remove(preNode));
				}else {
					indexSet.add(preIndex);
				}
				
				if(expreMap.containsKey(nextNode)){
					indexSet.addAll(expreMap.remove(nextNode));
				}else {
					indexSet.add(nextIndex);
				}
				indexSet.add(op.getIndex());
				
				expreMap.put(root, indexSet);
			}
			
			parentheses.expression = root;
			return parentheses;
		}
		
		private static Node getExpression(Map<Node,Set<Integer>> expreMap,int index){
			if(expreMap == null || expreMap.isEmpty()){
				return null;
			}
			
			for(Map.Entry<Node, Set<Integer>> entry : expreMap.entrySet()){
				for(Integer i:entry.getValue()){
					if(i == index){
						return entry.getKey();
					}
				}
			}
			
			return null;
		}
	}
	
	static class Expression extends Node {
		private Node left;
		private Node right;
		private Operator operator;
		
		public Node getLeft() {
			return left;
		}
		public void setLeft(Node left) {
			this.left = left;
		}
		public Node getRight() {
			return right;
		}
		public void setRight(Node right) {
			this.right = right;
		}
		public Operator getOperator() {
			return operator;
		}
		public void setOperator(Operator operator) {
			this.operator = operator;
		}
		
		public Object getValue(Map<String,Object> paramsMap){
			Object valueLeft = this.left.getValue(paramsMap);
			Object valueRight = this.right.getValue(paramsMap);
			
			if(valueLeft instanceof Date){
				return calculateDate((Date)valueLeft,valueRight.toString());
			}
			
			if(valueRight instanceof Date){
				return calculateDate((Date)valueRight,valueLeft.toString());
			}
			
			if(valueLeft instanceof String || valueRight instanceof String){
				return calculateString(valueLeft.toString(),valueRight.toString());
			}

			if(valueLeft instanceof Integer && valueRight instanceof Integer){
				Integer il = (Integer)valueLeft;
				Integer ir = (Integer)valueRight;
				
				return calculate(il,ir);
			}

			double dl = ((Number)valueLeft).doubleValue();
			double dr = ((Number)valueRight).doubleValue();
			return calculate(dl,dr);
		}
		
		private Date calculateDate(Date date,String increment){
			if(!this.operator.isPlus() && !this.operator.isSubstruction()){
				throw new RuntimeException("Error operator '"+this.operator.optChar+"' for Date");
			}
			
			if(!Node.isDateIncrement(increment)){
				throw new RuntimeException("Error parameter '"+increment+"' for Date");
			}
			
			Calendar cal = Calendar.getInstance();  
			cal.setTime(date);
			char flagChar = increment.charAt(increment.length()-1);
			int incre = new Integer(increment.substring(0,increment.length()-1));
			incre = this.operator.isPlus() ? incre:-incre;
			
			if(flagChar == 'y'){
				cal.add(Calendar.YEAR,incre);
			}else if(flagChar == 'm'){
				cal.add(Calendar.MONTH,incre);
			}else if(flagChar == 'd'){
				cal.add(Calendar.DATE,incre);
			}else if(flagChar == 'h'){
				cal.add(Calendar.HOUR_OF_DAY,incre);
			}else if(flagChar == 'i'){
				cal.add(Calendar.MINUTE,incre);
			}else if(flagChar == 's'){
				cal.add(Calendar.SECOND,incre);
			}

			return cal.getTime();
		}
		
		private String calculateString(String left,String right){
			if(!this.operator.isPlus()){
				throw new RuntimeException("Error operator '"+this.operator.optChar+"' for String");
			}
			
			return left+right;
		}
		
		private Integer calculate(int x,int y){
			if(this.operator.isPlus()){
				return plus(x,y);
			}
			
			if(this.operator.isSubstruction()){
				return this.substruction(x,y);
			}
			
			if(this.operator.isMultiplication()){
				return this.multiplication(x, y);
			}
			
			if(this.operator.isDivision()){
				return this.division(x,y);
			}
			
			return null;
		}
		
		private Double calculate(double x,double y){
			if(this.operator.isPlus()){
				return plus(x,y);
			}
			
			if(this.operator.isSubstruction()){
				return this.substruction(x,y);
			}
			
			if(this.operator.isMultiplication()){
				return this.multiplication(x, y);
			}
			
			if(this.operator.isDivision()){
				return this.division(x,y);
			}
			
			return null;
		}
		
		private int plus(int x,int y){
			return x+y;
		}
		
		private double plus(double x,double y){
			return x+y;
		}
		
		private int substruction(int x,int y){
			return x-y;
		}
		
		private double substruction(double x,double y){
			return x-y;
		}
		
		private int multiplication(int x,int y){
			return x*y;
		}
		
		private double multiplication(double x,double y){
			return x*y;
		}
		
		private int division(int x,int y){
			return x/y;
		}
		
		private double division(double x,double y){
			return x/y;
		}

		public String toString(){
			return "("+this.left+this.operator+this.right+")";
		}
	}
	
	static class Operand extends Node {
		private String operand;
		String numberRegex = "^\\d\\d*\\.{0,1}\\d*\\d$";
		
		public Operand(String op){
			if(op != null){
				this.operand = op.trim();
			}
		}
		
		public Object getValue(Map<String,Object> paramsMap){
			if(this.isString()){
				return this.operand.subSequence(1,this.operand.length()-1);
			}
			
			if(this.operand.startsWith("D")){
				Date date = Node.readDate(this.operand);
				if(date != null){
					return date;
				}
			}
			
			if(Node.isDateIncrement(this.operand)){
				return this.operand;
			}
			
			try {
				return new Integer(this.operand);
			}catch(NumberFormatException e){
				try {
					return new Double(this.operand);
				}catch(NumberFormatException e1){}
			}
			
			return readValue(paramsMap);
		}
		
		private Object readValue(Map<String,Object> paramMap){
			String[] keys = this.operand.split("\\.");
			
			int i = 0;
			Map<String,Object> object = paramMap;
			Map<String,Object> parent = paramMap;
			Object value = null;
			for(String k:keys){
				if(i++ < keys.length-1){
					object = (Map<String,Object>)parent.get(k);
					parent = object;
				}else {
					value = object.get(k);
				}
			}
			return value;
		}
		
		public boolean isString(){
			return this.operand.startsWith("'") && this.operand.endsWith("'");
		}
		
		public boolean isDate(){
			if(this.operand.startsWith("D")){
				try {
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String ds = this.operand.substring(1,this.operand.length());
					
					format.parse(ds);
					return true;
				} catch (ParseException e) {}
			}
			return false;
		}
		
		public boolean isNumber(){
			Pattern pt = Pattern.compile(numberRegex);
			Matcher mt = pt.matcher(this.operand);
			//return mt.find();
			return true;
		}
		
		public String toString(){
			return this.operand;
		}
	}
	
	static class Operator extends Node implements Comparable<Operator> {
		private char optChar;
		private int arrayIndex;
		
		public Operator(char oc,int index){
			this.optChar = oc;
			this.arrayIndex = index;
		}
		
		public int getIndex(){
			return this.arrayIndex;
		}
		
		public boolean isPlus(){
			return this.optChar == '+';
		}
		
		public boolean isSubstruction(){
			return this.optChar == '-';
		}
		
		public boolean isMultiplication(){
			return this.optChar == '*';
		}
		
		public boolean isDivision(){
			return this.optChar == '/';
		}
		
		public int compareTo(Operator that) {
			return this.priority()-that.priority();
		}
		
		public String toString(){
			//return "index = "+this.arrayIndex+", operator = "+this.optChar;
			return this.optChar+"";
		}
		
		private int priority(){
			if(optChar == '*' || optChar == '/'){
				return 1;
			}
			
			if(optChar == '+' || optChar == '-'){
				return 2;
			}
			return 100;
		}
		
		public static boolean isOperator(char c){
			return c == '+' || c == '-' || c == '*' || c == '/';
		}
	}
	
	/*
	public static void main2(String[] args) {
		String source = "1+2*3*4*5/6-7+8*9+10-11";
		
		String[] sary = new String[source.length()];
		StringBuffer sb = null;
		int arrayIndex = -1;
		List<Operator> priorityOpts = new ArrayList<Operator>();
		for(int i = 0; i < source.length(); i++){
			char ch = source.charAt(i);
			
			if(isOperator(ch)){
				if(sb != null){
					sary[++arrayIndex] = sb.toString();
					sb = null;
				}
				
				sary[++arrayIndex] = ch+"";
				priorityOpts.add(new Operator(ch,arrayIndex));
				
				continue;
			}
			if(sb == null){
				sb = new StringBuffer(ch);
			}
			
			sb.append(ch);
			
			//The lasted
			if(i == source.length()-1){
				sary[++arrayIndex] = sb.toString();
			}
		}
		
		Collections.sort(priorityOpts);
		
		/*
		for(int ii = 0; ii < sary.length; ii++){
			if(sary[ii] != null){
				//System.out.println(sary[ii]);
			}
		}
		
		System.out.println("");
		System.out.println("********** Operators ***********");
		for(Operator opp : priorityOpts){
			System.out.println(opp);
		}*
		
		Map<Node,Set<Integer>> expreMap = new HashMap<Node,Set<Integer>>();
		Expression root = null;
		Set<Integer> indexSet = null;
		for(Operator op:priorityOpts){
			int opIndex = op.getIndex();
			
			int preIndex = opIndex-1;
			int nextIndex = opIndex+1;
			
			Node preNode = getExpression(expreMap,preIndex);
			Node nextNode = getExpression(expreMap,nextIndex);
			
			if(preNode == null){
				preNode = new Operand(sary[preIndex]);
			}
			
			if(nextNode == null){
				nextNode = new Operand(sary[nextIndex]);
			}
			
			root = new Expression();
			root.setLeft(preNode);
			root.setRight(nextNode);
			root.setOperator(op);
			
			indexSet = new HashSet<Integer>();
			if(expreMap.containsKey(preNode)){
				indexSet.addAll(expreMap.remove(preNode));
			}else {
				indexSet.add(preIndex);
			}
			
			if(expreMap.containsKey(nextNode)){
				indexSet.addAll(expreMap.remove(nextNode));
			}else {
				indexSet.add(nextIndex);
			}
			indexSet.add(op.getIndex());
			
			expreMap.put(root, indexSet);
		}
		
		System.out.println(root +" ="+root.getValue());
	}*/
}
