/**
 * 
 */
package com.jtools.utils.messages.ptp;

/**
 * @author j4ckk0
 *
 */
public class DefaultPointToPointBus extends PointToPointBus {

	private static DefaultPointToPointBus instance;

	private static final String URL = "tcp://localhost:61616";

	private DefaultPointToPointBus() {
		super(URL);
	}

	public static DefaultPointToPointBus instance() {
		if(instance == null) {
			instance = new DefaultPointToPointBus();
		}
		return instance;
	}

}
