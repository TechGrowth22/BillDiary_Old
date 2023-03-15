package com.billdiary.exception;
		
/**
 * @author gajanan
 *
 */
public class BusinessException extends Exception{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3149320897105268973L;
	
	public BusinessException() {
		super();
	}
	
	/**
	 * Constructor
	 * @param s
	 */
	public BusinessException(String s) {
		super(s);
	}
	
	public String errorCode;
	public String message;
	
	public BusinessException(String errorCode,String message) {
		super();
		this.errorCode=errorCode;
		this.message=message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
