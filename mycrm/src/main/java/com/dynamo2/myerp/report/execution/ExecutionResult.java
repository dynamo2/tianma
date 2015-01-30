package com.dynamo2.myerp.report.execution;

import java.util.List;

public abstract class ExecutionResult {

	private int currentPosition = 0;

	protected List<Object[]> resultset;

	protected Object singleResultObject;

	public int size() {
		if (resultset != null) {
			return resultset.size();
		} else {
			return 0;
		}
	}

	public Object[] next() {
		if (resultset != null) {
			if (currentPosition < resultset.size()) {
				return resultset.get(currentPosition++);
			}
		}
		return null;
	}

	public boolean hasNext() {
		return resultset.size() - currentPosition > 0;
	}

	public Object[] rowAt(int index) {
		if (resultset != null && index > 0 && index < resultset.size()) {
			return resultset.get(index);
		}

		return null;
	}

	public Object getSingleResultObject() {
		return singleResultObject;
	}

}
