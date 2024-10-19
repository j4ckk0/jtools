/**
 * 
 */
package com.jtools.generic.data;

/**
 * @author j4ckk0
 *
 */
public class DataException extends Exception {

	private static final long serialVersionUID = -750168772367146161L;

	public DataException() {
		super();
	}

	public DataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DataException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataException(String message) {
		super(message);
	}

	public DataException(Throwable cause) {
		super(cause);
	}

}
