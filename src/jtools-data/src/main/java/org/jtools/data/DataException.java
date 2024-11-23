package org.jtools.data;

// TODO: Auto-generated Javadoc
/**
 * The Class DataException.
 */
/*-
 * #%L
 * Java Tools - Data
 * %%
 * Copyright (C) 2024 jtools.org
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
public class DataException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -750168772367146161L;

	/**
	 * Instantiates a new data exception.
	 */
	public DataException() {
		super();
	}

	/**
	 * Instantiates a new data exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param enableSuppression the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public DataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Instantiates a new data exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public DataException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new data exception.
	 *
	 * @param message the message
	 */
	public DataException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new data exception.
	 *
	 * @param cause the cause
	 */
	public DataException(Throwable cause) {
		super(cause);
	}

}
