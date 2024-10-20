/**
 * 
 */
package com.jtools.utils.messages;

/**
 * @author j4ckk0
 *
 */
public class DefaultPTPBus extends PTPBus {

	private static DefaultPTPBus instance;

	private static final String URL = "tcp://localhost:61616";

	private DefaultPTPBus() {
		super(URL);
	}

	public static DefaultPTPBus instance() {
		if(instance == null) {
			instance = new DefaultPTPBus();
		}
		return instance;
	}

}
