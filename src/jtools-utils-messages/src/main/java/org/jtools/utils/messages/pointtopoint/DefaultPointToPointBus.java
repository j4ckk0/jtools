/**
 * 
 */
package org.jtools.utils.messages.pointtopoint;

import org.jtools.utils.messages.DefaultBusConfig;

/**
 * @author j4ckk0
 *
 */
public class DefaultPointToPointBus extends PointToPointBus {

	private static DefaultPointToPointBus instance;

	private DefaultPointToPointBus() {
		super(DefaultBusConfig.getUrl());
	}

	public static DefaultPointToPointBus instance() {
		if (instance == null) {
			instance = new DefaultPointToPointBus();
		}
		return instance;
	}

}
