package com.dynamo2.myerp.dynamicform.service;

/**
 * Service Exception for Dyanmic Form module
 * 
 * @author fwang
 * 
 */
public class DynamicFormServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public enum ERR_CODE {
		CFG_FIELD_INVALID_EXPRESSION, CFG_FIELD_INVALID_EXPRESSION_FIELD_TYPE, CFG_FIELD_INVALID_EXPRESSION_VAR_FIELD_TYPE, CFG_FIELD_EXPRESSION_VAR_FIELD_SHOULD_BE_REQUIRED, CFG_FIELD_EXPRESSION_VAR_FIELD_CANNOT_FIND, CFG_FIELD_EXPRESSION_NOT_SUPPORT_EMBEDDED_EXPRESSION, CFG_FIELD_REFER_TO_FIELD_SHOULB_BE_STRING;
	}

	private ERR_CODE errCode;

	public DynamicFormServiceException(ERR_CODE errCode) {
		this.errCode = errCode;
	}

	public ERR_CODE getErrCode() {
		return errCode;
	}
}
