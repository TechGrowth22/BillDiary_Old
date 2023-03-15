package com.billdiary.exception;



/**
 * @author gajanan
 *
 */
public class ApplicationException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7880357634540387348L;
	/**
	 * Constructor
	 * @param cause
	 */
	public ApplicationException (String cause) {
		super(cause);
	
	}
	


}
