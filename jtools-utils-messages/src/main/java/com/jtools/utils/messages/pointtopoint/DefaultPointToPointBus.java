/**
 * 
 */
package com.jtools.utils.messages.pointtopoint;

import com.jtools.utils.messages.DefaultBusConfig;

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
