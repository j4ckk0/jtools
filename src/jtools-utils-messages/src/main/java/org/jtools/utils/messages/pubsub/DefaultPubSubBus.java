/**
 * 
 */
package org.jtools.utils.messages.pubsub;

import org.jtools.utils.messages.DefaultBusConfig;

/**
 * @author j4ckk0
 *
 */
public class DefaultPubSubBus extends PubSubBus {

	private static DefaultPubSubBus instance;

	private DefaultPubSubBus() {
		super(DefaultBusConfig.getUrl());
	}

	public static DefaultPubSubBus instance() {
		if (instance == null) {
			instance = new DefaultPubSubBus();
		}
		return instance;
	}

}
