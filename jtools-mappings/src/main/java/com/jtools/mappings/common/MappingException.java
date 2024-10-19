/**
 * 
 */
package com.jtools.mappings.common;

/**
 * @author j4ckk0
 *
 */
public class MappingException extends Exception {

	private static final long serialVersionUID = -750168772367146161L;

	public MappingException() {
		super();
	}

	public MappingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MappingException(String message, Throwable cause) {
		super(message, cause);
	}

	public MappingException(String message) {
		super(message);
	}

	public MappingException(Throwable cause) {
		super(cause);
	}

}
