package com.dynamo2.tianma.expression;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dynamo2.tianma.arithmetic.ArithmeticExpression;
import com.dynamo2.tianma.expression.comparison.EqExpression;
import com.dynamo2.tianma.expression.comparison.GtExpression;
import com.dynamo2.tianma.expression.comparison.GteExpression;
import com.dynamo2.tianma.expression.comparison.InExpression;
import com.dynamo2.tianma.expression.comparison.LtExpression;
import com.dynamo2.tianma.expression.comparison.LteExpression;
import com.dynamo2.tianma.expression.comparison.NeExpression;
import com.dynamo2.tianma.expression.comparison.NinExpression;
import com.dynamo2.tianma.expression.logical.LogicalExpression;
import com.mongodb.BasicDBObject;

public class BasicExpression implements Expression {
	protected final Operator OPERATOR;
	protected String source;
	protected Map<String, Object> parameterMap;

	public BasicExpression(String source, Map<String, Object> paramMap,
			Operator key) {
		this(key);

		this.source = source;
		this.parameterMap = paramMap;
	}

	public BasicExpression(Operator key) {
		this.OPERATOR = key;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// String expre = "{name $eq 'wmx'} $and {age $eq 10}";
		// compile(expre,null);

		String str = "w334.w4444r";
		String regx = "^\\b\\b*\\.{0,1}\\b*\\b$";

//		Pattern pt = Pattern.compile(regx);
//		Matcher m = pt.matcher(str);
//		System.out.println(m.find());
		
		String nn = Normalizer.normalize(regx,Normalizer.Form.NFD);
		System.out.println(nn);
		
		System.out.println(str.codePointAt(0));
		System.out.println(Character.getType(str.codePointAt(0)));
		
		
		/*
		Map<String,Object> goods = new HashMap<String,Object>();
		goods.put("name", "wmx");
		goods.put("price", 23.09);
		goods.put("num",34f);
		
		Map<String,Object> user = new HashMap<String,Object>();
		user.put("goods",goods);
		user.put("age",23);
		
		Map<String,Object> pm = new HashMap<String,Object>();
		pm.put("user", user);
		
		String pk = "user.goods.name";
		ArithmeticExpression ae = new ArithmeticExpression(pk);
		
		System.out.println(ae.calculate(pm));
		*/
	}
	
	private static Object readValue(Map<String,Object> paramMap,String paramKey){
		String[] keys = paramKey.split("\\.");
		
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

	public static Expression compile(String expreStr,
			Map<String, Object> paramMap) {
		// String str =
		// "{{{{name $eq 'wmx'} $and {age $eq 10} $and {email $eq 'wmx@bbl.com'}} $or {{name $eq 'wf'} $and {mobile $eq '13918533921'}}} $and {point $gt 100}}";

		List<Integer> leftBraces = new ArrayList<Integer>();
		Map<Integer, ExpressionNode> nodes = new HashMap<Integer, ExpressionNode>();
		ExpressionNode currNode = null;
		ExpressionNode parent = null;
		KeyNode currKey = null;
		for (int i = 0; i < expreStr.length(); i++) {
			char c = expreStr.charAt(i);
			if (c == '{') {
				leftBraces.add(i);

				currNode = new ExpressionNode(i, -1, expreStr, paramMap);
				nodes.put(i, currNode);
			} else if (c == '}') {
				int index = leftBraces.remove(leftBraces.size() - 1);

				currNode = nodes.get(index);
				currNode.setEnd(i + 1);
				if (leftBraces.isEmpty()) {
					parent = null;
				} else {
					parent = nodes.get(leftBraces.get(leftBraces.size() - 1));

					parent.addChild(currNode);
					currNode.setParent(parent);
					currNode = parent;
				}
			} else if (c == '$') {
				currKey = new KeyNode(i, -1, expreStr);
			} else if (c == ' ') {
				if (currKey != null) {
					currKey.setEnd(i);
					currNode.addKey(currKey);
				}
				currKey = null;
			}
		}

		// System.out.println(currNode);
		// System.out.println(currNode.buildExpression());

		return currNode.buildExpression();
	}

	static class Node {
		int start = -1;
		int end = -1;
		String source;

		public Node(int s, int e, String source) {
			this.start = s;
			this.end = e;
			this.source = source;
		}

		public int getStart() {
			return start;
		}

		public void setStart(int start) {
			this.start = start;
		}

		public int getEnd() {
			return end;
		}

		public void setEnd(int end) {
			this.end = end;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}
	}

	static class ExpressionNode extends Node implements
			Comparable<ExpressionNode> {

		Map<String, Object> paramMap;
		ExpressionNode parent;
		List<ExpressionNode> children = new ArrayList<ExpressionNode>();
		List<KeyNode> keys = new ArrayList<KeyNode>();

		public ExpressionNode(int s, int e, String source,
				Map<String, Object> mp) {
			super(s, e, source);
			this.paramMap = mp;
		}

		public ExpressionNode getParent() {
			return parent;
		}

		public void setParent(ExpressionNode parent) {
			this.parent = parent;
		}

		public List<ExpressionNode> getChildren() {
			return children;
		}

		public void setChildren(List<ExpressionNode> children) {
			this.children = children;
		}

		public void addChild(ExpressionNode node) {
			this.children.add(node);
		}

		public void addKey(KeyNode key) {
			this.keys.add(key);
		}

		private String expression() {
			return source.substring(start, end);
		}

		private String prex = "";

		public String toString() {
			StringBuffer sb = new StringBuffer(prex);
			// sb.append("[start="+start+",end="+end+",children="+children.size()+"]");
			sb.append(expression());
			sb.append("    ");
			printKeys(sb);
			sb.append("\n");
			printChildren(sb);

			return sb.toString();
		}

		private void printKeys(StringBuffer sb) {
			if (!this.keys.isEmpty()) {
				sb.append("keys[");
				int i = 0;
				for (KeyNode k : this.keys) {
					if (i++ > 0) {
						sb.append(",");
					}
					sb.append(k.toString());
				}
				sb.append("]");
			}
		}

		private void printChildren(StringBuffer sb) {
			if (!this.children.isEmpty()) {
				for (ExpressionNode n : children) {
					n.prex = prex + "  ";
					sb.append(n.toString());
				}
			}
		}

		public Expression buildExpression() {
			if (this.children.isEmpty()) {
				return buildSingleExpression();
			}

			return this.buildLogicalExpression();
		}

		private Expression buildSingleExpression() {
			KeyNode key = this.keys.get(0);
			Operator op = OperatorHelper.get(key.getKey());
			String expreStr = source.substring(start, end);

			if (Operator.EQ == op) {
				return new EqExpression(expreStr, this.paramMap);
			}
			if (Operator.GT == op) {
				return new GtExpression(expreStr, this.paramMap);
			}
			if (Operator.GTE == op) {
				return new GteExpression(expreStr, this.paramMap);
			}
			if (Operator.LT == op) {
				return new LtExpression(expreStr, this.paramMap);
			}
			if (Operator.LTE == op) {
				return new LteExpression(expreStr, this.paramMap);
			}
			if (Operator.IN == op) {
				return new InExpression(expreStr, this.paramMap);
			}
			if (Operator.NIN == op) {
				return new NinExpression(expreStr, this.paramMap);
			}
			if (Operator.NE == op) {
				return new NeExpression(expreStr, this.paramMap);
			}

			return null;
		}

		private Expression buildLogicalExpression() {
			LogicalExpression lastExpre = null;
			LogicalExpression currExpre = null;
			int keyIndex = 0;
			KeyNode currKey = null;
			for (ExpressionNode c : children) {
				if (currExpre == null) {
					currKey = this.keys.get(keyIndex++);
					currExpre = LogicalExpression.newLogicalExpression(currKey
							.getOperator());
				}

				currExpre.add(c.buildExpression());
				if (c.getStart() > currKey.getStart() && keyIndex < keys.size()) {
					lastExpre = currExpre;

					currKey = this.keys.get(keyIndex++);
					currExpre = LogicalExpression.newLogicalExpression(currKey
							.getOperator());

					currExpre.add(lastExpre);
				}
			}

			return currExpre;
		}

		public int compareTo(ExpressionNode o) {
			return this.start - o.start;
		}
	}

	static class KeyNode extends Node {
		public KeyNode(int s, int e, String source) {
			super(s, e, source);
		}

		public String getKey() {
			return source.substring(start, end);
		}

		public Operator getOperator() {
			return OperatorHelper.get(this.getKey());
		}

		public String toString() {
			return this.getKey();
		}
	}

	public static void readExpression() {

	}

	public String toString() {
		return source + "\n";
	}

	public BasicDBObject generateQuery() {
		return null;
	}
}
