package com.dynamo2.myerp.dynamicform.utils;

import org.nfunk.jep.JEP;

public class JEPHelper {

	/**
	 * Calculate the expression.
	 * 
	 * @param expression
	 *            Expression string
	 * @return calculate result
	 * @throws IllegalArgumentException
	 */
	public static double calculateExpression(String expression) throws IllegalArgumentException {
		JEP myParser = new JEP();
		myParser.parseExpression(expression);

		if (myParser.hasError()) {
			throw new IllegalArgumentException();
		}

		return myParser.getValue();
	}

}
